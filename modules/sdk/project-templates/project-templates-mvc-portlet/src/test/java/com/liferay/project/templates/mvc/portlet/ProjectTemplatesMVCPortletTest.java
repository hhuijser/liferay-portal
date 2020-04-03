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

package com.liferay.project.templates.mvc.portlet;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.net.URI;

import java.util.Arrays;
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
 * @author Lawrence Lee
 */
@RunWith(Parameterized.class)
public class ProjectTemplatesMVCPortletTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(name = "Testcase-{index}: testing {0}, {1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"portlet", "7.0.6"}, {"portlet", "7.1.3"},
				{"portlet", "7.2.1"}, {"portlet", "7.3.0"},
				{"customPackage", "7.0.6"}, {"customPackage", "7.1.3"},
				{"customPackage", "7.2.1"}, {"customPackage", "7.3.0"},
				{"portletName", "7.0.6"}, {"portletName", "7.1.3"},
				{"portletName", "7.2.1"}, {"portletName", "7.3.0"},
				{"portletSuffix", "7.0.6"}, {"portletSuffix", "7.1.3"},
				{"portletSuffix", "7.2.1"}, {"portletSuffix", "7.3.0"}
			});
	}

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

	public ProjectTemplatesMVCPortletTest(
		String testModifier, String liferayVersion) {

		_testModifier = testModifier;
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testBuildTemplateMVCPortlet() throws Exception {
		testBuildTemplatePortlet(
			temporaryFolder, _testModifier, "mvc-portlet", "MVCPortlet",
			_liferayVersion, mavenExecutor, _gradleDistribution);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

	private final String _liferayVersion;
	private final String _testModifier;

}