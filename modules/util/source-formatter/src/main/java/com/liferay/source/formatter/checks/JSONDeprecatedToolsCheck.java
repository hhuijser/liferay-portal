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

/**
 * @author Alan Huang
 */
public class JSONDeprecatedToolsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.endsWith("/package.json")) {
			return content;
		}

		for (String s : _DEPRECATED_TOOLS) {
			if (content.contains("\"" + s + "\":")) {
				addMessage(fileName, "Do not use deprecated tools '" + s + "'");
			}
		}

		return content;
	}

	private static final String[] _DEPRECATED_TOOLS = {
		"liferay-module-config-generator", "metal-cli"
	};

}