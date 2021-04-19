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

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public abstract class PluginsTestSuiteJob
	extends PluginsGitRepositoryJob implements TestSuiteJob {

	public String getPluginName() {
		return _pluginName;
	}

	@Override
	public List<File> getPluginsTestBaseDirs() {
		return Arrays.asList(_getPluginTestBaseDir());
	}

	@Override
	public String getTestSuiteName() {
		return _pluginName;
	}

	protected PluginsTestSuiteJob(
		String jobName, String pluginName, BuildProfile buildProfile,
		String branchName) {

		super(jobName, buildProfile, branchName);

		_pluginName = pluginName;

		jobPropertiesFiles.add(
			new File(_getPluginTestBaseDir(), "test.properties"));
	}

	@Override
	protected Set<String> getRawBatchNames() {
		return getSetFromString(
			JenkinsResultsParserUtil.getProperty(
				getJobProperties(), "test.batch.names", getJobName(),
				_pluginName));
	}

	private File _getPluginTestBaseDir() {
		GitWorkingDirectory pluginsGitWorkingDirectory =
			getGitWorkingDirectory();

		return new File(
			pluginsGitWorkingDirectory.getWorkingDirectory(),
			JenkinsResultsParserUtil.combine(
				"portlets/", _pluginName, "/test/functional"));
	}

	private final String _pluginName;

}