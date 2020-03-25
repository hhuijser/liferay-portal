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

package com.liferay.project.templates.service;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Gregory Amerson
 * @author Lawrence Lee
 */
@RunWith(Parameterized.class)
public class ProjectTemplatesServiceTest
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

	@Parameterized.Parameters(
			name = "Testcase-{index}: testing {0}"
		)
		public static Iterable<Object[]> data() {
			return Arrays.asList(
				new Object[][] {
					{"7.0.6"},
					{"7.1.3"},
					{"7.2.1"},
					{"7.3.0"}
				});
		}


		public ProjectTemplatesServiceTest(
				String liferayVersion) {

				_liferayVersion = liferayVersion;
			}

	private final String _liferayVersion;

	@Test
	public void testBuildTemplateService() throws Exception {
		String template = "service";
		String name = "servicepreaction";

		File gradleWorkspaceDir = newBuildWorkspace(temporaryFolder, "gradle", "gradleWS", _liferayVersion, mavenExecutor);

		File gradleWorkspaceModulesDir = new File(gradleWorkspaceDir, "modules");

		File gradleProjectDir = buildTemplateWithGradle(gradleWorkspaceModulesDir, template, name, "--liferay-version", _liferayVersion, "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction");

		testContains(
			gradleProjectDir, "build.gradle",
			DEPENDENCY_PORTAL_KERNEL);

		testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		File mavenWorkspaceDir =
				newBuildWorkspace(temporaryFolder, "maven", "mavenWS", _liferayVersion, mavenExecutor);

			File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

			File mavenProjectDir = buildTemplateWithMaven(mavenModulesDir, mavenModulesDir, template, name, "com.test", mavenExecutor, "-DclassName=FooAction", "-Dpackage=servicepreaction",
					"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction", "-DliferayVersion=" + _liferayVersion);

			if (isBuildProjects()) {
				_writeServiceClass(gradleProjectDir);
				_writeServiceClass(mavenProjectDir);

				File gradleOutputDir = new File(gradleProjectDir, "build/libs");
				File mavenOutputDir = new File(mavenProjectDir, "target");

				buildProjects(
					_gradleDistribution, mavenExecutor, gradleWorkspaceDir,
					mavenProjectDir, gradleOutputDir, mavenOutputDir, ":modules:" + name + GRADLE_TASK_PATH_BUILD);
			}

	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _writeServiceClass(File projectDir) throws IOException {
		String importLine =
			"import com.liferay.portal.kernel.events.LifecycleAction;";
		String classLine =
			"public class FooAction implements LifecycleAction {";

		File actionJavaFile = testContains(
			projectDir, "src/main/java/servicepreaction/FooAction.java",
			"package servicepreaction;", importLine,
			"service = LifecycleAction.class", classLine);

		Path actionJavaPath = actionJavaFile.toPath();

		List<String> lines = Files.readAllLines(
			actionJavaPath, StandardCharsets.UTF_8);

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				actionJavaPath, StandardCharsets.UTF_8)) {

			for (String line : lines) {
				FileTestUtil.write(bufferedWriter, line);

				if (line.equals(classLine)) {
					FileTestUtil.write(
						bufferedWriter, "@Override",
						"public void processLifecycleEvent(",
						"LifecycleEvent lifecycleEvent)",
						"throws ActionException {", "System.out.println(",
						"\"login.event.pre=\" + lifecycleEvent);", "}");
				}
				else if (line.equals(importLine)) {
					FileTestUtil.write(
						bufferedWriter,
						"import com.liferay.portal.kernel.events." +
							"LifecycleEvent;",
						"import com.liferay.portal.kernel.events." +
							"ActionException;");
				}
			}
		}
	}

	private static URI _gradleDistribution;

}