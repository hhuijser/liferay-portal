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

import com.liferay.source.formatter.checks.util.BNDSourceUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class BNDLiferayEnterpriseAppCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/bnd.bnd")) {
			return content;
		}

		List<String> enterpriseAppModulePathNames = getAttributeValues(
			_ENTERPRISE_APP_MODULE_PATH_NAMES_KEY, absolutePath);

		if (enterpriseAppModulePathNames.isEmpty()) {
			return content;
		}

		for (String enterpriseAppModulePathName :
				enterpriseAppModulePathNames) {

			if (!absolutePath.contains(enterpriseAppModulePathName)) {
				continue;
			}

			String liferayEnterpriseApp = BNDSourceUtil.getDefinitionValue(
				content, "Liferay-Enterprise-App");

			if (liferayEnterpriseApp == null) {
				addMessage(fileName, "Missing Liferay-Enterprise-App");
			}

			return content;
		}

		return content;
	}

	private static final String _ENTERPRISE_APP_MODULE_PATH_NAMES_KEY =
		"enterpriseAppModulePathNames";

}