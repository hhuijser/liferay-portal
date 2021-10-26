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

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.TomcatAppServer;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.test.integration.TestIntegrationPlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationTomcatExtension;
import com.liferay.gradle.plugins.test.integration.tasks.SetUpTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StartTestableTomcatTask;
import com.liferay.gradle.plugins.test.integration.tasks.StopAppServerTask;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import java.util.Arrays;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 */
public class TestIntegrationDefaultsPlugin
	extends BaseDefaultsPlugin<TestIntegrationPlugin> {

	public static final Plugin<Project> INSTANCE =
		new TestIntegrationDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, TestIntegrationPlugin testIntegrationPlugin) {

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		LiferayExtension liferayExtension = extensionContainer.getByType(
			LiferayExtension.class);
		TestIntegrationTomcatExtension testIntegrationTomcatExtension =
			extensionContainer.getByType(TestIntegrationTomcatExtension.class);

		TomcatAppServer tomcatAppServer =
			(TomcatAppServer)liferayExtension.getAppServer("tomcat");

		_configureExtensionTestIntegrationTomcat(
			liferayExtension, testIntegrationTomcatExtension, tomcatAppServer);

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration testModulesConfiguration =
			configurationContainer.getByName(
				TestIntegrationPlugin.TEST_MODULES_CONFIGURATION_NAME);

		_configureConfigurationTestModules(project, testModulesConfiguration);

		// Tasks

		TaskProvider<Task> copyTestModulesTaskProvider =
			GradleUtil.getTaskProvider(
				project, TestIntegrationPlugin.COPY_TEST_MODULES_TASK_NAME);
		TaskProvider<SetUpTestableTomcatTask> setUpTestableTomcatTaskProvider =
			GradleUtil.getTaskProvider(
				project, TestIntegrationPlugin.SET_UP_TESTABLE_TOMCAT_TASK_NAME,
				SetUpTestableTomcatTask.class);
		TaskProvider<StartTestableTomcatTask> startTestableTomcatTaskProvider =
			GradleUtil.getTaskProvider(
				project, TestIntegrationPlugin.START_TESTABLE_TOMCAT_TASK_NAME,
				StartTestableTomcatTask.class);
		TaskProvider<StopAppServerTask> stopTestableTomcatTaskProvider =
			GradleUtil.getTaskProvider(
				project, TestIntegrationPlugin.STOP_TESTABLE_TOMCAT_TASK_NAME,
				StopAppServerTask.class);

		_configureTaskCopyTestModulesProvider(copyTestModulesTaskProvider);
		_configureTaskSetUpTestableTomcatProvider(
			project, setUpTestableTomcatTaskProvider, tomcatAppServer);
		_configureTaskStartTestableTomcatProvider(
			project, startTestableTomcatTaskProvider, tomcatAppServer);
		_configureTaskStopTestableTomcatProvider(
			stopTestableTomcatTaskProvider, tomcatAppServer);
	}

	@Override
	protected Class<TestIntegrationPlugin> getPluginClass() {
		return TestIntegrationPlugin.class;
	}

	private TestIntegrationDefaultsPlugin() {
	}

	private void _configureConfigurationTestModules(
		final Project project, Configuration testModulesConfiguration) {

		testModulesConfiguration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					String version = PortalTools.getPortalVersion(project);

					if (PortalTools.PORTAL_VERSION_7_0_X.equals(version) ||
						PortalTools.PORTAL_VERSION_7_1_X.equals(version) ||
						PortalTools.PORTAL_VERSION_7_2_X.equals(version)) {

						GradleUtil.addDependency(
							project,
							TestIntegrationPlugin.
								TEST_MODULES_CONFIGURATION_NAME,
							"com.liferay.portal",
							"com.liferay.portal.test.integration", "3.0.0");
					}
				}

			});
	}

	private void _configureExtensionTestIntegrationTomcat(
		LiferayExtension liferayExtension,
		TestIntegrationTomcatExtension testIntegrationTomcatExtension,
		TomcatAppServer tomcatAppServer) {

		testIntegrationTomcatExtension.setCheckPath(
			tomcatAppServer::getCheckPath);

		testIntegrationTomcatExtension.setPortNumber(
			tomcatAppServer::getPortNumber);

		testIntegrationTomcatExtension.setDir(tomcatAppServer::getDir);

		testIntegrationTomcatExtension.setJmxRemotePort(
			liferayExtension::getJmxRemotePort);

		testIntegrationTomcatExtension.setLiferayHome(
			liferayExtension::getLiferayHome);

		testIntegrationTomcatExtension.setManagerPassword(
			tomcatAppServer::getManagerPassword);

		testIntegrationTomcatExtension.setManagerUserName(
			tomcatAppServer::getManagerUserName);
	}

	private void _configureTaskCopyTestModulesProvider(
		TaskProvider<Task> copyTestModulesTaskProvider) {

		copyTestModulesTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task copyTestModulesTask) {
					GradleUtil.setProperty(
						copyTestModulesTask,
						LiferayOSGiPlugin.AUTO_CLEAN_PROPERTY_NAME, false);
				}

			});
	}

	private void _configureTaskSetUpTestableTomcatProvider(
		final Project project,
		TaskProvider<SetUpTestableTomcatTask> setUpTestableTomcatTaskProvider,
		final TomcatAppServer tomcatAppServer) {

		setUpTestableTomcatTaskProvider.configure(
			new Action<SetUpTestableTomcatTask>() {

				@Override
				public void execute(
					SetUpTestableTomcatTask setUpTestableTomcatTask) {

					setUpTestableTomcatTask.setJaCoCoAgentConfiguration(
						GradleUtil.getProperty(
							project, "jacoco.agent.configuration",
							(String)null));
					setUpTestableTomcatTask.setJaCoCoAgentFile(
						GradleUtil.getProperty(
							project, "jacoco.agent.jar", (String)null));
					setUpTestableTomcatTask.setAspectJAgent(
						GradleUtil.getProperty(
							project, "aspectj.agent", (String)null));
					setUpTestableTomcatTask.setAspectJConfiguration(
						GradleUtil.getProperty(
							project, "aspectj.configuration", (String)null));

					setUpTestableTomcatTask.setZipUrl(
						tomcatAppServer::getZipUrl);
				}

			});
	}

	private void _configureTaskStartTestableTomcatProvider(
		final Project project,
		TaskProvider<StartTestableTomcatTask> startTestableTomcatTaskProvider,
		final TomcatAppServer tomcatAppServer) {

		startTestableTomcatTaskProvider.configure(
			new Action<StartTestableTomcatTask>() {

				@Override
				public void execute(
					StartTestableTomcatTask startTestableTomcatTask) {

					Object checkTimeout = GradleUtil.getProperty(
						project, "timeout.app.server.wait");

					if (checkTimeout != null) {
						startTestableTomcatTask.setCheckTimeout(
							GradleUtil.toInteger(checkTimeout) * 1000);
					}

					startTestableTomcatTask.setExecutable(
						tomcatAppServer::getStartExecutable);

					startTestableTomcatTask.setExecutableArgs(
						() -> {
							String argLine = System.getProperty(
								"app.server.start.executable.arg.line");

							if (Validator.isNotNull(argLine)) {
								return Arrays.asList(argLine.split(" "));
							}

							return tomcatAppServer.getStartExecutableArgs();
						});
				}

			});
	}

	private void _configureTaskStopTestableTomcatProvider(
		TaskProvider<StopAppServerTask> stopTestableTomcatTaskProvider,
		final TomcatAppServer tomcatAppServer) {

		stopTestableTomcatTaskProvider.configure(
			new Action<StopAppServerTask>() {

				@Override
				public void execute(StopAppServerTask stopAppServerTask) {
					stopAppServerTask.setExecutable(
						tomcatAppServer::getStopExecutable);

					stopAppServerTask.setExecutableArgs(
						tomcatAppServer::getStopExecutableArgs);
				}

			});
	}

}