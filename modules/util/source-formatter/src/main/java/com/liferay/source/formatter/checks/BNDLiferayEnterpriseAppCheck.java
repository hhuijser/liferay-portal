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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
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

		if (!fileName.endsWith("/bnd.bnd") || absolutePath.contains("-test/") ||
			absolutePath.contains("-test-util/")) {

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

			return BNDSourceUtil.updateInstruction(
				content, "Liferay-Enterprise-App",
				_sortProperties(liferayEnterpriseApp));
		}

		return content;
	}

	private String _sortProperties(String liferayEnterpriseApp) {
		String[] propertiesArray = StringUtil.split(
			liferayEnterpriseApp, StringPool.SEMICOLON);

		PropertyComparator comparator = new PropertyComparator();

		for (int i = 1; i < propertiesArray.length; i++) {
			String property = propertiesArray[i];
			String previousProperty = propertiesArray[i - 1];

			int compare = comparator.compare(previousProperty, property);

			if (compare > 0) {
				liferayEnterpriseApp = StringUtil.replaceFirst(
					liferayEnterpriseApp, previousProperty, property);
				liferayEnterpriseApp = StringUtil.replaceLast(
					liferayEnterpriseApp, property, previousProperty);
			}
		}

		return liferayEnterpriseApp;
	}

	private static final String _ENTERPRISE_APP_MODULE_PATH_NAMES_KEY =
		"enterpriseAppModulePathNames";

	private class PropertyComparator extends NaturalOrderStringComparator {

		public int compare(String property1, String property2) {
			return super.compare(
				_getPropertyName(property1), _getPropertyName(property2));
		}

		private String _getPropertyName(String property) {
			int x = property.indexOf(StringPool.EQUAL);

			if (x != -1) {
				return property.substring(0, x);
			}

			return property;
		}

	}

}