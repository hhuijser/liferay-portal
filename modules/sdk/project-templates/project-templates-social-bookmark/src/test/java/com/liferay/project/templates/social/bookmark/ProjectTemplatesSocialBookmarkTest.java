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

package com.liferay.project.templates.social.bookmark;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 */
public class ProjectTemplatesSocialBookmarkTest
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

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateSocialBookmark70() throws Exception {
		File gradleWorkspaceDir = buildWorkspaceWithTPEnabled(
			temporaryFolder, "gradle", "gradleWS", "7.0.6",
			mavenExecutor);

		File gradleWorkspaceModulesDir = new File(
			gradleWorkspaceDir, "modules");

		buildTemplateWithGradle(gradleWorkspaceModulesDir, "social-bookmark", "foo", "--package-name", "com.liferay.test",
			"--liferay-version", "7.0.6");
	}

	@Test
	public void testBuildTemplateSocialBookmark() throws Exception {
		String liferayVersion = "7.1.3";
		String template = "social-bookmark";
		String name = "foo";
		String packageName = "com.liferay.test";

		File gradleWorkspaceDir = buildWorkspaceWithTPEnabled(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		File gradleWorkspaceModulesDir = new File(
			gradleWorkspaceDir, "modules");

		File gradleProjectDir = buildTemplateWithGradle(
			gradleWorkspaceModulesDir, template, name, "--liferay-version",
			liferayVersion, "--package-name", packageName);

		testExists(gradleProjectDir, "bnd.bnd");
		testExists(gradleProjectDir, "build.gradle");

		testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"public class FooSocialBookmark implements SocialBookmark");
		testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/page.jsp",
			"<clay:link");
		testContains(
			gradleProjectDir, "src/main/resources/content/Language.properties",
			"foo=Foo");

		testNotContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"private ResourceBundleLoader");
		testNotContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"protected ResourceBundleLoader");

		testNotContains(gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		File mavenWorkspaceDir = buildWorkspaceWithTPEnabled(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-DclassName=Foo", "-Dpackage=" + packageName,
			"-DliferayVersion=" + liferayVersion);

		if (isBuildProjects()) {
			File gradleOutputDir = new File(gradleProjectDir, "build/libs");
			File mavenOutputDir = new File(mavenProjectDir, "target");

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleWorkspaceDir,
				mavenProjectDir, gradleOutputDir, mavenOutputDir,
				":modules:" + name + GRADLE_TASK_PATH_BUILD);
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

}