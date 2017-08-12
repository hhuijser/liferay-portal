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

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Peter Shin
 */
public class PropertiesServiceKeysCheck extends BaseFileCheck {

	@Override
	public void init() throws Exception {
		_projectPathPrefix = getProjectPathPrefix();
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("/service.properties")) {
			return content;
		}

		if (isPortalSource() &&
			isModulesApp(absolutePath, _projectPathPrefix, true)) {

			return content;
		}

		for (String legacyServiceKey : _LEGACY_SERVICE_KEYS) {
			content = content.replaceAll(
				"(\\A|\n)\\s*" + legacyServiceKey + "=.*(\\Z|\n)",
				StringPool.NEW_LINE);
		}

		return content;
	}

	private static final String[] _LEGACY_SERVICE_KEYS = {"build.auto.upgrade"};

	private String _projectPathPrefix;

}