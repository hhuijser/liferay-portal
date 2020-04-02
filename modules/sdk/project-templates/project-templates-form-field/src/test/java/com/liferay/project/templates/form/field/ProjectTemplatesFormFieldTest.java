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

package com.liferay.project.templates.form.field;

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
 * @author Gabriel Ibson
 */
public class ProjectTemplatesFormFieldTest
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
	public void testBuildTemplateFormField70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foobar", "--liferay-version", "7.0.6");

		testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foobar",
			"Web-ContextPath: /dynamic-data-foobar-form-field");
		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=foobar\"",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"ddm.Foobar", "/META-INF/resources/foobar.soy");
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Foobar",
			"ddm.form.field.type.js.module=foobar-form-field",
			"ddm.form.field.type.label=foobar-label",
			"ddm.form.field.type.name=foobar",
			"public class FoobarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"foobar\";");
		testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"foobar-group", "'foobar-form-field': {",
			"path: 'foobar_field.js',", "'foobar-form-field-template': {");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{namespace ddm}", "{template .Foobar autoescape",
			"<div class=\"form-group foobar-form-field\"");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar_field.js",
			"'foobar-form-field',", "var FoobarField",
			"value: 'foobar-form-field'", "NAME: 'foobar-form-field'",
			"Liferay.namespace('DDM.Field').Foobar = FoobarField;");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "form-field", "foobar", "com.test", mavenExecutor,
			"-DclassName=Foobar", "-Dpackage=foobar", "-DliferayVersion=7.0.6");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateFormField71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foobar", "--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foobar",
			"Web-ContextPath: /dynamic-data-foobar-form-field");
		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		testContains(
			gradleProjectDir, "package.json",
			"\"name\": \"dynamic-data-foobar-form-field\"",
			",foobar_field.js &&");
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=foobar\"",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"DDMFoobar.render", "/META-INF/resources/foobar.soy");
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"ddm.form.field.type.description=foobar-description",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Foobar",
			"ddm.form.field.type.js.module=foobar-form-field",
			"ddm.form.field.type.label=foobar-label",
			"ddm.form.field.type.name=foobar",
			"public class FoobarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"foobar\";");
		testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"field-foobar", "'foobar-form-field': {",
			"path: 'foobar_field.js',");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{namespace DDMFoobar}", "variant=\"'foobar'\"",
			"foobar-form-field");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.es.js",
			"import templates from './foobar.soy';", "* Foobar Component",
			"class Foobar extends Component", "Soy.register(Foobar,",
			"!window.DDMFoobar", "window.DDMFoobar",
			"window.DDMFoobar.render = Foobar;", "export default Foobar;");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar_field.js",
			"'foobar-form-field',", "var FoobarField",
			"value: 'foobar-form-field'", "NAME: 'foobar-form-field'",
			"Liferay.namespace('DDM.Field').Foobar = FoobarField;");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "form-field", "foobar", "com.test", mavenExecutor,
			"-DclassName=Foobar", "-Dpackage=foobar", "-DliferayVersion=7.1.3");

		testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateFormField71WithHyphen() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foo-bar", "--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foo-bar",
			"Web-ContextPath: /dynamic-data-foo-bar-form-field");
		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		testContains(
			gradleProjectDir, "package.json",
			"\"name\": \"dynamic-data-foo-bar-form-field\"",
			",foo-bar_field.js &&");
		testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/form/field/FooBarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=fooBar\"",
			"public class FooBarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"DDMFooBar.render", "/META-INF/resources/foo-bar.soy");
		testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/form/field/FooBarDDMFormFieldType.java",
			"ddm.form.field.type.description=foo-bar-description",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.FooBar",
			"ddm.form.field.type.js.module=foo-bar-form-field",
			"ddm.form.field.type.label=foo-bar-label",
			"ddm.form.field.type.name=fooBar",
			"public class FooBarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"fooBar\";");
		testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"field-foo-bar", "'foo-bar-form-field': {",
			"path: 'foo-bar_field.js',");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar.soy",
			"{namespace DDMFooBar}", "variant=\"'fooBar'\"",
			"foo-bar-form-field");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar.es.js",
			"import templates from './foo-bar.soy';", "* FooBar Component",
			"class FooBar extends Component", "Soy.register(FooBar,",
			"!window.DDMFooBar", "window.DDMFooBar",
			"window.DDMFooBar.render = FooBar;", "export default FooBar;");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar_field.js",
			"'foo-bar-form-field',", "var FooBarField",
			"value: 'foo-bar-form-field'", "NAME: 'foo-bar-form-field'",
			"Liferay.namespace('DDM.Field').FooBar = FooBarField;");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "form-field", "foo-bar", "com.test", mavenExecutor,
			"-DclassName=FooBar", "-Dpackage=foo.bar",
			"-DliferayVersion=7.1.3");

		testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateFormField72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foobar", "--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, "bnd.bnd", "Provide-Capability:", "soy;",
			"type:String=\"LiferayFormField\"");
		testContains(
			gradleProjectDir, "build.gradle",
			"compileOnly group: \"com.liferay\",",
			"name: \"com.liferay.frontend.js.loader.modules.extender.api\"",
			"jsCompile group: \"com.liferay\",",
			"name: \"com.liferay.dynamic.data.mapping.form.field.type\"",
			"compileOnly group: \"com.liferay\",",
			"name: \"com.liferay.dynamic.data.mapping.api\",",
			"version: \"5.7.0\"", "compileOnly group: \"com.liferay\",",
			"name: \"com.liferay.frontend.js.loader.modules.extender.api\",",
			"version: \"3.0.0\"", "jsCompile group: \"com.liferay\",",
			"name: \"com.liferay.dynamic.data.mapping.form.field.type\",",
			"version: \"4.0.25\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");
		testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;",
			"org.osgi.service.component.annotations.Reference;",
			"ddm.form.field.type.description=foobar-description",
			"ddm.form.field.type.display.order:Integer=10",
			"ddm.form.field.type.group=customized",
			"public String getModuleName()",
			"public boolean isCustomDDMFormFieldType()",
			"private NPMResolver _npmResolver;");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{template .content}", "ddm-field-foobar", "form-control foobar");
		testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.es.js",
			"'dynamic-data-mapping-form-field-type/FieldBase/FieldBase.es';",
			"import './foobarRegister.soy.js';",
			"import {Config} from 'metal-state'",
			"import templates from './foobar.soy.js';", "* Foobar Component",
			"class Foobar extends Component", "Foobar.STATE",
			"Soy.register(Foobar, templates);");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "form-field", "foobar", "com.test", mavenExecutor,
			"-DclassName=Foobar", "-Dpackage=foobar", "-DliferayVersion=7.2.1");

		testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateFormFieldInWorkspace70() throws Exception {
		_testBuildTemplateWithWorkspace(
			"form-field", "foobar", "build/libs/foobar-1.0.0.jar",
			"--liferay-version", "7.0.6", "--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateFormFieldInWorkspace71() throws Exception {
		_testBuildTemplateWithWorkspace(
			"form-field", "foobar", "build/libs/foobar-1.0.0.jar",
			"--liferay-version", "7.1.3", "--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateFormFieldInWorkspace72() throws Exception {
		_testBuildTemplateWithWorkspace(
			"form-field", "foobar", "build/libs/foobar-1.0.0.jar",
			"--liferay-version", "7.2.0", "--dependency-management-enabled");
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		return buildTemplateWithGradle(temporaryFolder, template, name, args);
	}

	private void _testBuildTemplateWithWorkspace(
			String template, String name, String jarFilePath, String... args)
		throws Exception {

		testBuildTemplateWithWorkspace(
			temporaryFolder, _gradleDistribution, template, name, jarFilePath,
			args);
	}

	private static URI _gradleDistribution;

}