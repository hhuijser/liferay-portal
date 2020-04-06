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

package com.liferay.project.templates.modules.ext;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Gregory Amerson
 */
public class ProjectTemplatesModulesExtTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@BeforeClass
	public static void setUpClass() throws Exception {
		String gradleDistribution = System.getProperty("gradle.distribution");

		if (Validator.isNull(gradleDistribution)) {
			Properties properties = FileTestUtil.readProperties(
				"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

			gradleDistribution = properties.getProperty("distributionUrl");
		}

		Assert.assertTrue(gradleDistribution.contains(GRADLE_WRAPPER_VERSION));

		_gradleDistribution = URI.create(gradleDistribution);
	}

	@Test
	public void testBuildTemplateModuleExtInWorkspace() throws Exception {
		String liferayVersion = getDefaultLiferayVersion();

		File workspaceDir = buildWorkspace(temporaryFolder, liferayVersion);

		enableTargetPlatformInWorkspace(workspaceDir, liferayVersion);

		File workspaceProjectDir = buildTemplateWithGradle(
			new File(workspaceDir, "ext"), "modules-ext", "loginExt",
			"--original-module-name", "com.liferay.login.web",
			"--original-module-version", "1.0.0",
			"--dependency-management-enabled");

		testContains(
			workspaceProjectDir, "build.gradle",
			"originalModule group: \"com.liferay\", name: " +
				"\"com.liferay.login.web\"");
		testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, _gradleDistribution, ":ext:loginExt:build");

			File gradleOutputDir = new File(workspaceProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));

		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateModulesExtMaven() throws Exception {
		String liferayVersion = getDefaultLiferayVersion();
		String name = "foo-ext";
		String template = "modules-ext";

		File mavenWorkspaceDir = newBuildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenExtDir = new File(mavenWorkspaceDir, "ext");

		buildTemplateWithMaven(
			mavenExtDir, mavenExtDir, template, name, "com.test",
			mavenExecutor, "-DclassName=" + name, "-Dpackage=" + name,
			"-DliferayVersion=" + liferayVersion,
			"-DoriginalModuleName=com.liferay.login.web");

	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();


	private static URI _gradleDistribution;

}