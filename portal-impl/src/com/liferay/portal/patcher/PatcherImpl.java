/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.patcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.patcher.Patcher;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class PatcherImpl implements Patcher {

	public boolean applyPatch(File patch) {
		File patchesFolder = getPatchesFolder();

		try {
			FileUtil.copyFile(
				patch, new File(patchesFolder + StringPool.SLASH +
					patch.getName()));

			return true;
		}
		catch (Exception e) {
			_log.error(
				"Couldn't copy " + patch.getAbsolutePath() + " patch to the " +
					"patches folder: " + patchesFolder);

			return false;
		}
	}

	public String[] getFixedIssues() {
		if (_fixedIssues != null) {
			return _fixedIssues;
		}

		String property = getProperties().getProperty(Patcher.FIXED_ISSUES, "");

		_fixedIssues = property.split(",");

		return _fixedIssues;
	}

	public String[] getInstalledPatches() {
		if (_installedPatches != null) {
			return _installedPatches;
		}

		String property = getProperties().getProperty(
			Patcher.INSTALLED_PATCHES);

		_installedPatches = property.split(",");

		return _installedPatches;
	}

	public File getPatchesFolder() {
		if (_patchesFolder != null) {
			return _patchesFolder;
		}

		String property = getProperties().getProperty(Patcher.PATCHES_FOLDER);

		if (Validator.isNotNull(property)) {
			_patchesFolder = new File(property);
		}

		return _patchesFolder;
	}

	public Properties getProperties() {
		if (_properties != null) {
			return _properties;
		}

		_properties = new Properties();

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
			Patcher.PATCHING_INFO_PROPERTIES);

		if (is != null) {
			try {
				_properties.load(is);
				is.close();

				_configured = true;
			} catch (IOException e) {
				_log.error(e, e);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Couldn't load patching.properties");
			}
		}

		return _properties;
	}

	public boolean isConfigured() {
		return _configured;
	}

	private static Log _log = LogFactoryUtil.getLog(PatcherImpl.class);

	private static boolean _configured;
	private static String[] _fixedIssues;
	private static String[] _installedPatches;
	private static File _patchesFolder;
	private static Properties _properties;

}