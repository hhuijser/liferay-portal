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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class PrincipalExceptionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _formatPrincipalException(content);
	}

	private String _formatPrincipalException(String content) {
		Matcher matcher = _PRINCIPAL_EXCEPTION_PATTERN.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String replacement = StringUtil.replace(
				match, "class.getName", "getNestedClasses");

			content = StringUtil.replace(content, match, replacement);
		}

		return content;
	}

	private static final Pattern _PRINCIPAL_EXCEPTION_PATTERN =
		RegexUtil.getPattern(
			"SessionErrors\\.contains\\(\n?\t*(renderR|r)equest, " +
				"PrincipalException\\.class\\.getName\\(\\)");

}