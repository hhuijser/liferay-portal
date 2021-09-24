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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin Lee
 */
public class JavaUpgradeAlterCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!(absolutePath.contains("/upgrade/") &&
			  content.contains("alter("))) {

			return content;
		}

		Matcher matcher1 = _alterPattern.matcher(content);

		while (matcher1.find()) {
			List<String> parameterList = JavaSourceUtil.getParameterList(
				_getMethodCall(content, matcher1.start()));

			ListIterator<String> iterator = parameterList.listIterator(1);

			while (iterator.hasNext()) {
				String alterObject = iterator.next();

				Matcher matcher3 = _alterObjectPattern.matcher(alterObject);

				while (matcher3.find()) {
					String alterType = matcher3.group(1);

					int pos = content.indexOf(alterObject, matcher1.start());

					if (Objects.equals(alterType, "AlterColumnType")) {
						_checkAlterColumnType(
							pos, fileName, content, alterObject);
					}
				}
			}
		}

		return content;
	}

	private void _checkAlterColumnType(
		int pos, String fileName, String fileContent, String content) {

		Matcher matcher = _alterColumnTypePattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		String dataType = matcher.group(3);

		if (!ArrayUtil.contains(_VALID_TYPES, dataType)) {
			String message = String.format(
				"Only the following data types are valid: %s",
				Arrays.toString(_VALID_TYPES));

			addMessage(
				fileName, message,
				getLineNumber(fileContent, pos + matcher.start(3)));

			return;
		}

		if (!ArrayUtil.contains(_STRING_TYPES, dataType)) {
			return;
		}

		String newType = matcher.group(2);

		if (!(newType.contains("null") || newType.contains("not null"))) {
			String message = String.format(
				"Specify whether the new type for \"%s\" is nullable",
				matcher.group(1));

			addMessage(
				fileName, message,
				getLineNumber(fileContent, pos + matcher.start(2)));
		}
	}

	private String _getMethodCall(String content, int start) {
		int x = start;

		while (true) {
			x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

			if (ToolsUtil.isInsideQuotes(content, x + 1)) {
				continue;
			}

			String methodCall = content.substring(start, x + 1);

			if (getLevel(methodCall) == 0) {
				return methodCall;
			}
		}
	}

	private static final String[] _STRING_TYPES = {"STRING", "TEXT", "VARCHAR"};

	private static final String[] _VALID_TYPES = {
		"BLOB", "SBLOB", "BOOLEAN", "DATE", "DOUBLE", "INTEGER", "LONG",
		"STRING", "TEXT", "VARCHAR"
	};

	private static final Pattern _alterColumnTypePattern = Pattern.compile(
		"AlterColumnType\\(\\s*\"(\\w+)\",\\s*\"((\\w+).+)\"\\)");
	private static final Pattern _alterObjectPattern = Pattern.compile(
		"new (Alter\\w+)\\(");
	private static final Pattern _alterPattern = Pattern.compile(
		"alter\\(\\s*");

}