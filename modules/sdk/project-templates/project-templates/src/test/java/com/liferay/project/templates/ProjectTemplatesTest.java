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

package com.liferay.project.templates;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.extensions.util.FileUtil;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.WorkspaceUtil;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 */
public class ProjectTemplatesTest implements BaseProjectTemplatesTestCase {

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
	public void testBuildTemplate() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			null, "hello-world-portlet");

		testExists(gradleProjectDir, "bnd.bnd");
		testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		testContains(
			gradleProjectDir,
			"src/main/java/hello/world/portlet/portlet/HelloWorldPortlet.java",
			"public class HelloWorldPortlet extends MVCPortlet {");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "mvc-portlet", "hello-world-portlet", "com.test",
			mavenExecutor, "-DclassName=HelloWorld",
			"-Dpackage=hello.world.portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentDTDVersionLayoutTemplate70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo-bar", "--liferay-version", "7.0.6");

		testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"liferay-layout-templates_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionLayoutTemplate71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo-bar", "--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"liferay-layout-templates_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionLayoutTemplate72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo-bar", "--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"liferay-layout-templates_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionLayoutTemplate73()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo-bar", "--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"liferay-layout-templates_7_3_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarHook70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "foo-bar", "--liferay-version", "7.0.6");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml",
			"liferay-hook_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarHook71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "foo-bar", "--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml",
			"liferay-hook_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarHook72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "foo-bar", "--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml",
			"liferay-hook_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarHook73() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "foo-bar", "--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml",
			"liferay-hook_7_3_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarMVCPortlet70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "foo-bar", "--liferay-version", "7.0.6");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_0_0.dtd");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarMVCPortlet71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "foo-bar", "--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_1_0.dtd");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarMVCPortlet72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "foo-bar", "--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_2_0.dtd");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarMVCPortlet73()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "foo-bar", "--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_3_0.dtd");

		testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_3_0.dtd");
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

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Ignore
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

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Ignore
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

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateFormFieldInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"form-field", "foobar", "build/libs/foobar-1.0.0.jar",
			"--liferay-version", "7.1.3", "--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			null, "hello-world-portlet",
			"build/libs/hello.world.portlet-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateLiferayVersionInvalid62() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "6.2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateLiferayVersionInvalid70test()
		throws Exception {

		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "7.0test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateLiferayVersionInvalid74() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "7.4");
	}

	@Test
	public void testBuildTemplateLiferayVersionValid70() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "7.0.6");
	}

	@Test
	public void testBuildTemplateLiferayVersionValid712() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "7.1.2");
	}

	@Test
	public void testBuildTemplateNAPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-angular-portlet", "angular-dependency-management",
			"--dependency-management-enabled", "--liferay-version", "7.1.3");

		testNotContains(gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		testContains(
			gradleProjectDir, "build.gradle", DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateOnExistingDirectory() throws Exception {
		File destinationDir = temporaryFolder.newFolder("gradle");

		buildTemplateWithGradle(destinationDir, "activator", "dup-activator");
		buildTemplateWithGradle(destinationDir, "activator", "dup-activator");
	}

	@Test
	public void testBuildTemplatePortletInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"portlet", "foo.test", "build/libs/foo.test-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplatePortletWithPortletName() throws Exception {
		_testBuildTemplateWithWorkspace(
			"portlet", "portlet", "build/libs/portlet-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateSocialBookmark70() throws Exception {
		_buildTemplateWithGradle(
			"social-bookmark", "foo", "--package-name", "com.liferay.test",
			"--liferay-version", "7.0.6");
	}

	@Test
	public void testBuildTemplateSocialBookmark71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"social-bookmark", "foo", "--package-name", "com.liferay.test",
			"--liferay-version", "7.1.3");

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

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "social-bookmark", "foo", "com.test",
			mavenExecutor, "-DclassName=Foo", "-Dpackage=com.liferay.test",
			"-DliferayVersion=7.1.3");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortletWithPackage() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet", "--package-name",
			"com.liferay.test");

		testExists(gradleProjectDir, "src/main/webapp/init.jsp");
		testExists(gradleProjectDir, "src/main/webapp/view.jsp");

		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVCPortlet");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "war-mvc-portlet", "WarMVCPortlet",
			"com.liferay.test", mavenExecutor, "-DclassName=WarMVCPortlet",
			"-Dpackage=com.liferay.test");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortletWithPortletName()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet");

		testExists(gradleProjectDir, "src/main/webapp/init.jsp");
		testExists(gradleProjectDir, "src/main/webapp/view.jsp");

		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVCPortlet");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "war-mvc-portlet", "WarMVCPortlet",
			"warmvcportlet", mavenExecutor, "-DclassName=WarMVCPortlet",
			"-Dpackage=WarMVCPortlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortletWithPortletSuffix()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVC-portlet");

		testExists(gradleProjectDir, "src/main/webapp/init.jsp");
		testExists(gradleProjectDir, "src/main/webapp/view.jsp");

		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVC-portlet");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "war-mvc-portlet", "WarMVC-portlet",
			"warmvc.portlet", mavenExecutor, "-DclassName=WarMVCPortlet",
			"-Dpackage=WarMVC.portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWithGradle() throws Exception {
		buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", false, false);
		buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", false, true);
		buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", true, false);
		buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", true, true);
	}

	@Test
	public void testBuildTemplateWithPackageName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"", "barfoo", "--package-name", "foo.bar");

		testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: foo.bar");
		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "mvc-portlet", "barfoo", "com.test", mavenExecutor,
			"-DclassName=Barfoo", "-Dpackage=foo.bar");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testCompareAntBndPluginVersions() throws Exception {
		String template = "mvc-portlet";
		String name = "foo";

		File gradleProjectDir = _buildTemplateWithGradle(template, name);

		Optional<String> gradleResult = executeGradle(
			gradleProjectDir, true, ProjectTemplatesTest._gradleDistribution,
			GRADLE_TASK_PATH_BUILD);

		String gradleAntBndVersion = null;

		Matcher matcher =
			BaseProjectTemplatesTestCase.antBndPluginVersionPattern.matcher(
				gradleResult.get());

		if (matcher.matches()) {
			gradleAntBndVersion = matcher.group(1);
		}

		File mavenProjectDir = _buildTemplateWithMaven(
			template, name, name, "-DclassName=foo");

		testContains(
			mavenProjectDir, "pom.xml",
			"<artifactId>com.liferay.ant.bnd</artifactId>\n\t\t\t\t\t\t" +
				"<version>" + gradleAntBndVersion);
	}

	@Test
	public void testComparePortalToolsBundleSupportPluginVersions()
		throws Exception {

		File workspaceDir = buildWorkspace(
			temporaryFolder, getDefaultLiferayVersion());

		Optional<String> result = executeGradle(
			workspaceDir, true, ProjectTemplatesTest._gradleDistribution,
			":tasks");

		Matcher matcher =
			BaseProjectTemplatesTestCase.portalToolsBundleSupportVersionPattern.
				matcher(result.get());

		String portalToolsBundleSupportVersion = null;

		if (matcher.matches()) {
			portalToolsBundleSupportVersion = matcher.group(1);
		}

		File mavenWorkspaceDir = _buildTemplateWithMaven(
			"workspace", "mavenWS", "liferayMaven",
			"-DliferayVersion=" + getDefaultLiferayVersion());

		testContains(
			mavenWorkspaceDir, "pom.xml",
			"<artifactId>com.liferay.portal.tools.bundle.support</artifactId>" +
				"\n\t\t\t\t<version>" + portalToolsBundleSupportVersion);
	}

	@Test
	public void testListTemplates() throws Exception {
		final Map<String, String> expectedTemplates = new TreeMap<>();

		try (DirectoryStream<Path> directoryStream =
				FileTestUtil.getProjectTemplatesDirectoryStream()) {

			for (Path path : directoryStream) {
				String fileName = String.valueOf(path.getFileName());

				String template = fileName.substring(
					FileTestUtil.PROJECT_TEMPLATE_DIR_PREFIX.length());

				if (!template.equals(WorkspaceUtil.WORKSPACE)) {
					Properties properties = FileUtil.readProperties(
						path.resolve("bnd.bnd"));

					String bundleDescription = properties.getProperty(
						"Bundle-Description");

					expectedTemplates.put(template, bundleDescription);
				}
			}
		}

		Assert.assertEquals(expectedTemplates, ProjectTemplates.getTemplates());
	}

	@Test
	public void testListTemplatesWithCustomArchetypesDir() throws Exception {
		File customArchetypesDir = temporaryFolder.newFolder();

		Path customArchetypesDirPath = customArchetypesDir.toPath();

		String jarName = "custom.template.jar";

		Files.write(
			customArchetypesDirPath.resolve(jarName),
			FileTestUtil.readAllBytes(
				"com/liferay/project/templates/dependencies/" + jarName));

		Map<String, String> customTemplatesMap = ProjectTemplates.getTemplates(
			Collections.singletonList(customArchetypesDir));

		Map<String, String> templatesMap = ProjectTemplates.getTemplates();

		Assert.assertEquals(
			customTemplatesMap.toString(), templatesMap.size() + 1,
			customTemplatesMap.size());
	}

	@Test
	public void testSassCompilerMavenWorkspace() throws Exception {
		File nativeSassWorkspaceDir = _buildTemplateWithMaven(
			"workspace", "nativeSassMavenWS", "liferayMaven",
			"-DliferayVersion=" + getDefaultLiferayVersion());

		File nativeSassModulesDir = new File(nativeSassWorkspaceDir, "modules");

		File nativeSassProjectDir = buildTemplateWithMaven(
			nativeSassWorkspaceDir.getParentFile(), nativeSassModulesDir,
			"mvc-portlet", "foo-portlet", "com.test", mavenExecutor,
			"-DclassName=Foo", "-Dpackage=foo.portlet",
			"-DprojectType=workspace");

		String nativeSassOutput = executeMaven(
			nativeSassProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

		Assert.assertTrue(
			nativeSassOutput,
			nativeSassOutput.contains("Using native Sass compiler"));

		File rubySassWorkspaceDir = _buildTemplateWithMaven(
			"workspace", "rubySassMavenWS", "liferayMaven",
			"-DliferayVersion=" + getDefaultLiferayVersion());

		File rubySassModulesDir = new File(rubySassWorkspaceDir, "modules");

		File rubySassProjectDir = buildTemplateWithMaven(
			rubySassWorkspaceDir.getParentFile(), rubySassModulesDir,
			"mvc-portlet", "foo-portlet", "com.test", mavenExecutor,
			"-DclassName=Foo", "-Dpackage=foo.portlet",
			"-DprojectType=workspace");

		File pomXmlFile = new File(rubySassProjectDir, "pom.xml");

		if (pomXmlFile.exists()) {
			editXml(
				pomXmlFile,
				document -> addCssBuilderConfigurationElement(
					document, "sassCompilerClassName", "ruby"));
		}

		String rubySassOutput = executeMaven(
			rubySassProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

		Assert.assertTrue(
			rubySassOutput,
			rubySassOutput.contains("Using Ruby Sass compiler"));

		File nativeSassOutputFile = testExists(
			nativeSassProjectDir, "target/foo-portlet-1.0.0.jar");
		File rubySassOutputFile = testExists(
			rubySassProjectDir, "target/foo-portlet-1.0.0.jar");

		testBundlesDiff(nativeSassOutputFile, rubySassOutputFile);
	}

	@Test
	public void testSassCompilerWorkspace() throws Exception {
		File nativeSassWorkspaceDir = buildWorkspace(
			temporaryFolder, getDefaultLiferayVersion());

		File nativeSassModulesDir = new File(nativeSassWorkspaceDir, "modules");

		File nativeSassProjectDir = buildTemplateWithGradle(
			nativeSassModulesDir, "mvc-portlet", "foo-portlet");

		Optional<String> nativeSassResult = executeGradle(
			nativeSassModulesDir, true,
			ProjectTemplatesTest._gradleDistribution,
			":modules:foo-portlet" + GRADLE_TASK_PATH_BUILD);

		String nativeSassOutput = nativeSassResult.toString();

		Assert.assertTrue(
			nativeSassOutput,
			nativeSassOutput.contains("Using native Sass compiler"));

		File rubySassWorkspaceDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "rubySassWorkspace");

		writeGradlePropertiesInWorkspace(
			rubySassWorkspaceDir, "sass.compiler.class.name=ruby");

		File rubySassModulesDir = new File(rubySassWorkspaceDir, "modules");

		File rubySassProjectDir = buildTemplateWithGradle(
			rubySassModulesDir, "mvc-portlet", "foo-portlet");

		Optional<String> rubySassResult = executeGradle(
			rubySassModulesDir, true, ProjectTemplatesTest._gradleDistribution,
			":modules:foo-portlet" + GRADLE_TASK_PATH_BUILD);

		String rubySassOutput = rubySassResult.toString();

		Assert.assertTrue(
			rubySassOutput,
			rubySassOutput.contains("Using Ruby Sass compiler"));

		File nativeSassOutputFile = testExists(
			nativeSassProjectDir, "build/libs/foo.portlet-1.0.0.jar");
		File rubySassOutputFile = testExists(
			rubySassProjectDir, "build/libs/foo.portlet-1.0.0.jar");

		testBundlesDiff(nativeSassOutputFile, rubySassOutputFile);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _buildProjects(File gradleProjectDir, File mavenProjectDir)
		throws Exception {

		buildProjects(
			ProjectTemplatesTest._gradleDistribution, mavenExecutor,
			gradleProjectDir, mavenProjectDir);
	}

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		return buildTemplateWithGradle(temporaryFolder, template, name, args);
	}

	private File _buildTemplateWithMaven(
			String template, String name, String groupId, String... args)
		throws Exception {

		return buildTemplateWithMaven(
			temporaryFolder, template, name, groupId, mavenExecutor, args);
	}

	private void _testBuildTemplateWithWorkspace(
			String template, String name, String jarFilePath, String... args)
		throws Exception {

		testBuildTemplateWithWorkspace(
			temporaryFolder, ProjectTemplatesTest._gradleDistribution, template,
			name, jarFilePath, args);
	}

	private static URI _gradleDistribution;

}