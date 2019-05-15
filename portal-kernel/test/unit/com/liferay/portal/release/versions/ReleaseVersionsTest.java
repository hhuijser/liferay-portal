/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.release.versions;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class ReleaseVersionsTest {

	@BeforeClass
	public static void setUpClass() {
		_portalPath = Paths.get(System.getProperty("user.dir"));
	}

	@Test
	public void testReleaseVersions() throws IOException {
		Assume.assumeTrue(Validator.isNull(System.getenv("JENKINS_HOME")));

		String otherDirName = System.getProperty(
			"release.versions.test.other.dir");

		Assert.assertTrue(
			"Please set the property \"release.versions.test.other.dir\"",
			Validator.isNotNull(otherDirName));

		final Path otherPath = Paths.get(otherDirName);

		Assert.assertTrue(
			otherPath + " is not a valid Git repository",
			Files.exists(otherPath.resolve(".git")));

		final boolean otherRelease = _isRelease(otherPath);

		boolean differentTypes = false;

		if (otherRelease != _isRelease(_portalPath)) {
			differentTypes = true;
		}

		Assert.assertTrue(
			StringBundler.concat(
				_portalPath, " and ", otherPath, " must be different types"),
			differentTypes);

		final Set<Path> ignorePaths = new HashSet<>(
			Arrays.asList(_portalPath.resolve("modules/third-party")));

		Path workingDirPropertiesPath = _portalPath.resolve(
			"working.dir.properties");

		if (Files.exists(workingDirPropertiesPath)) {
			Properties properties = _loadProperties(workingDirPropertiesPath);

			for (String name : properties.stringPropertyNames()) {
				if (!name.startsWith("working.dir.checkout.private.apps.") ||
					!name.endsWith(".dirs")) {

					continue;
				}

				String[] dirNames = StringUtil.split(
					properties.getProperty(name));

				for (String dirName : dirNames) {
					Path dirPath = _portalPath.resolve(dirName);

					if (Files.exists(dirPath)) {
						ignorePaths.add(dirPath);
					}
				}
			}
		}

		List<String> messages = new ArrayList<>();

		Files.walkFileTree(
			_portalPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (ignorePaths.contains(dirPath)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					String dirName = String.valueOf(dirPath.getFileName());

					if (Objects.equals(dirName, "node_modules")) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path versionPath = _getVersionPath(dirPath);

					if (versionPath == null) {
						return FileVisitResult.CONTINUE;
					}

					Path lfrbuildRelengIgnorePath = dirPath.resolve(
						".lfrbuild-releng-ignore");

					if (Files.exists(lfrbuildRelengIgnorePath)) {
						return FileVisitResult.CONTINUE;
					}

					if (dirName.endsWith("-test") ||
						dirName.endsWith("-test-api") ||
						dirName.endsWith("-test-impl") ||
						dirName.endsWith("-test-service")) {

						return FileVisitResult.CONTINUE;
					}

					if (_isInGitRepoReadOnly(dirPath)) {
						return FileVisitResult.CONTINUE;
					}

					Path versionRelativePath = _portalPath.relativize(
						versionPath);

					Path otherVersionPath = otherPath.resolve(
						versionRelativePath);

					if (Files.notExists(otherVersionPath)) {
						if (_log.isInfoEnabled()) {
							_log.info(
								StringBundler.concat(
									"Ignoring ", versionRelativePath,
									" as it does not exist in ", otherPath));
						}

						return FileVisitResult.SKIP_SUBTREE;
					}

					String message = _checkReleaseVersion(
						versionPath, otherVersionPath, otherRelease, dirPath);

					if (message != null) {
						messages.add(message);
					}

					return FileVisitResult.SKIP_SUBTREE;
				}

			});

		StringBundler sb = new StringBundler(messages.size() * 2);

		for (String message : messages) {
			sb.append(message);
			sb.append(StringPool.NEW_LINE);
		}

		Assert.assertTrue(sb.toString(), messages.isEmpty());
	}

	private String _checkReleaseVersion(
			Path versionPath, Path otherVersionPath, boolean otherRelease,
			Path dirPath)
		throws IOException {

		String fileName = String.valueOf(versionPath.getFileName());

		ObjectValuePair<Version, Path> otherVersionPathPair = null;
		ObjectValuePair<Version, Path> versionPathPair = null;

		if (Objects.equals(fileName, "bnd.bnd")) {
			Properties bndProperties = _loadProperties(versionPath);
			Properties otherBndProperties = _loadProperties(otherVersionPath);

			String bundleSymbolicName = bndProperties.getProperty(
				"Bundle-SymbolicName");
			String otherBundleSymbolicName = otherBndProperties.getProperty(
				"Bundle-SymbolicName");

			Assert.assertEquals(bundleSymbolicName, otherBundleSymbolicName);

			otherVersionPathPair = _getVersion(
				otherVersionPath, otherBndProperties);
			versionPathPair = _getVersion(versionPath, bndProperties);
		}
		else {
			Matcher matcher = _versionPattern.matcher(_read(otherVersionPath));

			if (matcher.find()) {
				otherVersionPathPair = new ObjectValuePair<>(
					Version.parseVersion(matcher.group(1)), otherVersionPath);
			}

			matcher = _versionPattern.matcher(_read(versionPath));

			if (matcher.find()) {
				versionPathPair = new ObjectValuePair<>(
					Version.parseVersion(matcher.group(1)), versionPath);
			}

			if ((otherVersionPathPair == null) || (versionPathPair == null)) {
				return null;
			}
		}

		ObjectValuePair<Version, Path> masterVersionPair = otherVersionPathPair;
		ObjectValuePair<Version, Path> releaseVersionPair = versionPathPair;

		if (otherRelease) {
			masterVersionPair = versionPathPair;
			releaseVersionPair = otherVersionPathPair;
		}

		Version masterVersion = masterVersionPair.getKey();
		Version releaseVersion = releaseVersionPair.getKey();

		if (!releaseVersion.equals(new Version(1, 0, 0)) &&
			(masterVersion.getMajor() != (releaseVersion.getMajor() + 1))) {

			StringBundler sb = new StringBundler(18);

			sb.append("The version for ");
			sb.append(_portalPath.relativize(dirPath));
			sb.append(" on the 'master' branch (");
			sb.append(masterVersion);
			sb.append(", defined in ");

			Path masterVersionPath = masterVersionPair.getValue();

			sb.append(masterVersionPath.getFileName());

			sb.append(") must be 1 major version greater than the 'release' ");
			sb.append("branch (");
			sb.append(releaseVersion);
			sb.append(", defined in ");

			Path releaseVersionPath = releaseVersionPair.getValue();

			sb.append(releaseVersionPath.getFileName());

			sb.append("). Please ");

			Path updateVersionPath = null;

			Path gitRepoPath = _getParentFile(dirPath, ".gitrepo");

			if (gitRepoPath != null) {
				String gitRepo = _read(gitRepoPath);

				if (!gitRepo.contains("mode = pull")) {
					gitRepoPath = null;
				}
			}

			if (gitRepoPath != null) {
				updateVersionPath = gitRepoPath.getParent();

				updateVersionPath = updateVersionPath.getParent();

				updateVersionPath = updateVersionPath.resolve(
					_getVersionOverrideFileName(dirPath));
			}
			else {
				updateVersionPath = dirPath.resolve(fileName);
			}

			if (Files.exists(updateVersionPath)) {
				sb.append("update");
			}
			else {
				sb.append("add");
			}

			sb.append(" the version to ");
			sb.append(new Version(releaseVersion.getMajor() + 1, 0, 0));
			sb.append(" in ");
			sb.append(_portalPath.relativize(updateVersionPath));
			sb.append(" for the 'master' branch.");

			return sb.toString();
		}

		return null;
	}

	private boolean _contains(Path path, String... strings) throws IOException {
		try (FileReader fileReader = new FileReader(path.toFile());
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(fileReader)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				for (String s : strings) {
					if (line.contains(s)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private Path _getGitRepoPath(Path dirPath) {
		while (dirPath != null) {
			Path gitRepoPath = dirPath.resolve(_GIT_REPO_FILE_NAME);

			if (Files.exists(gitRepoPath)) {
				return gitRepoPath;
			}

			dirPath = dirPath.getParent();
		}

		return null;
	}

	private Path _getParentFile(Path dirPath, String fileName) {
		while (true) {
			Path path = dirPath.resolve(fileName);

			if (Files.exists(path)) {
				return path;
			}

			dirPath = dirPath.getParent();

			if (dirPath == null) {
				return null;
			}
		}
	}

	private ObjectValuePair<Version, Path> _getVersion(
			Path bndBndPath, Properties bndProperties)
		throws IOException {

		Path dirPath = bndBndPath.getParent();

		Path versionOverridePath = _getParentFile(
			dirPath, _getVersionOverrideFileName(dirPath));

		if (versionOverridePath != null) {
			Properties versionOverrides = _loadProperties(versionOverridePath);

			String version = versionOverrides.getProperty("Bundle-Version");

			if (Validator.isNotNull(version)) {
				return new ObjectValuePair<>(
					Version.parseVersion(version), versionOverridePath);
			}
		}

		String version = bndProperties.getProperty("Bundle-Version");

		return new ObjectValuePair<>(Version.parseVersion(version), bndBndPath);
	}

	private String _getVersionOverrideFileName(Path dirPath) {
		return ".version-override-" + String.valueOf(dirPath.getFileName()) +
			".properties";
	}

	private Path _getVersionPath(Path dirPath) {
		Path bndBndPath = dirPath.resolve("bnd.bnd");

		if (Files.exists(bndBndPath)) {
			return bndBndPath;
		}

		String dirName = String.valueOf(dirPath.getFileName());
		Path packageJsonPath = dirPath.resolve("package.json");

		if (Files.exists(packageJsonPath) && dirName.contains("-theme")) {
			return packageJsonPath;
		}

		return null;
	}

	private boolean _isInGitRepoReadOnly(Path dirPath) throws IOException {
		Path gitRepoPath = _getGitRepoPath(dirPath);

		if (gitRepoPath == null) {
			return false;
		}

		if (_contains(gitRepoPath, "mode = pull")) {
			return true;
		}

		return false;
	}

	private boolean _isRelease(Path path) {
		if (Files.exists(path.resolve("modules/.releng"))) {
			return true;
		}

		return false;
	}

	private Properties _loadProperties(Path path) throws IOException {
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		}

		return properties;
	}

	private String _read(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	private static final String _GIT_REPO_FILE_NAME = ".gitrepo";

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseVersionsTest.class);

	private static Path _portalPath;
	private static final Pattern _versionPattern = Pattern.compile(
		"\"version\": \"(\\w+\\.\\w+\\.\\w+)\"");

}