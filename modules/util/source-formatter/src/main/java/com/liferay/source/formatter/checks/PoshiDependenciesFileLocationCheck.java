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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Alan Huang
 */
public class PoshiDependenciesFileLocationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith(".testcase")) {
			return content;
		}

		_getTestCaseDependenciesFileLocations();

		_getTestCaseFileNames();

		_checkDependenciesFileReferences(fileName);

		return content;
	}

	private synchronized void _checkDependenciesFileReferences(String fileName)
		throws IOException {

		if (!_dependenciesFileLocationsMapIsReady) {
			for (String testCaseFileName : _testCaseFileNames) {
				File testCaseFile = new File(testCaseFileName);

				String testCaseFileContent = FileUtil.read(testCaseFile);

				for (Map.Entry<String, Set<String>> entry :
						_dependenciesFileLocationsMap.entrySet()) {

					String dependenciesFileLocation = entry.getKey();

					String dependenciesFileName =
						dependenciesFileLocation.replaceFirst(".*/(.+)", "$1");

					if (testCaseFileContent.contains(
							StringPool.QUOTE + dependenciesFileName +
								StringPool.QUOTE)) {

						Set<String> referencesFiles = entry.getValue();

						referencesFiles.add(testCaseFileName);

						_dependenciesFileLocationsMap.put(
							dependenciesFileLocation, referencesFiles);
					}
				}
			}
		}

		for (Map.Entry<String, Set<String>> entry :
				_dependenciesFileLocationsMap.entrySet()) {

			Set<String> referencesFiles = entry.getValue();

			if (referencesFiles.size() > 1) {
				for (String referencesFile : referencesFiles) {
					if (referencesFile.equals(fileName)) {
						addMessage(
							fileName,
							StringBundler.concat(
								"Test dependencies file '", entry.getKey(),
								"' is referenced by multiple modules, move it ",
								"to global dependencies directory"));

						break;
					}
				}
			}
		}

		_dependenciesFileLocationsMapIsReady = true;
	}

	private synchronized void _getTestCaseDependenciesFileLocations()
		throws IOException {

		if (!_dependenciesFileLocationsMap.isEmpty()) {
			return;
		}

		for (String dependenciesFileLocation : _DEPENDENCIES_FILE_LOCATIONS) {
			File directory = new File(getPortalDir(), dependenciesFileLocation);

			Path dirPath = directory.toPath();

			Files.walkFileTree(
				dirPath, EnumSet.noneOf(FileVisitOption.class), 25,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path dirPath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (ArrayUtil.contains(
								_SKIP_DIR_NAMES,
								String.valueOf(dirPath.getFileName()))) {

							return FileVisitResult.SKIP_SUBTREE;
						}

						String absolutePath = SourceUtil.getAbsolutePath(
							dirPath);

						if (absolutePath.contains("/test/") ||
							absolutePath.contains("/tests/")) {

							if (absolutePath.endsWith("/dependencies")) {
								File dirFile = dirPath.toFile();

								File[] dependenciesFiles = dirFile.listFiles(
									new FileFilter() {

										@Override
										public boolean accept(File file) {
											if (!file.isFile()) {
												return false;
											}

											return true;
										}

									});

								for (File dependenciesFile :
										dependenciesFiles) {

									_dependenciesFileLocationsMap.put(
										SourceUtil.getAbsolutePath(
											dependenciesFile.getPath()),
										new TreeSet<>());
								}
							}

							if (absolutePath.matches(
									".+/dependencies/.+\\..+")) {

								_dependenciesFileLocationsMap.put(
									SourceUtil.getAbsolutePath(absolutePath),
									new TreeSet<>());

								return FileVisitResult.SKIP_SUBTREE;
							}
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	private synchronized void _getTestCaseFileNames() throws IOException {
		if (!_testCaseFileNames.isEmpty()) {
			return;
		}

		for (String testcasesFileLocation : _TESECASES_FILE_LOCATIONS) {
			File directory = new File(getPortalDir(), testcasesFileLocation);

			Path dirPath = directory.toPath();

			Files.walkFileTree(
				dirPath, EnumSet.noneOf(FileVisitOption.class), 25,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path dirPath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						if (ArrayUtil.contains(
								_SKIP_DIR_NAMES,
								String.valueOf(dirPath.getFileName()))) {

							return FileVisitResult.SKIP_SUBTREE;
						}

						String absolutePath = SourceUtil.getAbsolutePath(
							dirPath);

						if (!absolutePath.contains("portal-web") &&
							!absolutePath.matches(
								".+/modules/.+-test/src/testFunctional(/.*)" +
									"?")) {

							return FileVisitResult.CONTINUE;
						}

						File dirFile = dirPath.toFile();

						File[] testcaseFiles = dirFile.listFiles(
							new FileFilter() {

								@Override
								public boolean accept(File file) {
									if (!file.isFile()) {
										return false;
									}

									String fileName = file.getName();

									if (fileName.endsWith(".testcase")) {
										return true;
									}

									return false;
								}

							});

						for (File testcaseFile : testcaseFiles) {
							_testCaseFileNames.add(
								SourceUtil.getAbsolutePath(
									testcaseFile.getPath()));
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

	private static final String[] _DEPENDENCIES_FILE_LOCATIONS = {
		"modules", "portal-web/test/functional/com/liferay/portalweb/tests"
	};

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".releng", ".settings", "bin",
		"build", "classes", "node_modules", "node_modules_cache", "poshi",
		"private", "source-formatter"
	};

	private static final String[] _TESECASES_FILE_LOCATIONS = {
		"modules", "portal-web/test/functional/com/liferay/portalweb/tests"
	};

	private static final Map<String, Set<String>>
		_dependenciesFileLocationsMap = new HashMap<>();
	private static boolean _dependenciesFileLocationsMapIsReady;
	private static final List<String> _testCaseFileNames = new ArrayList<>();

}