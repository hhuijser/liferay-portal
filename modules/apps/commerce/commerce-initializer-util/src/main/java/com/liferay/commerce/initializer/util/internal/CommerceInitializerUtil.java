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

package com.liferay.commerce.initializer.util.internal;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.TextFormatter;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceInitializerUtil {

	public static String getValue(
		JSONObject jsonObject, String propertyName, String key) {

		String value = GetterUtil.getString(
			jsonObject.getString(propertyName),
			TextFormatter.format(key, TextFormatter.J));

		return value;
	}

}