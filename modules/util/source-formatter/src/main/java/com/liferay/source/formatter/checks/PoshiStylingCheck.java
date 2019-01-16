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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		_checkLineBreak(fileName, content);

		content = _sortParameterNames(content);

		return content;
	}

	private void _checkLineBreak(String fileName, String content) {
		int x = -1;

		while (true) {
			x = content.indexOf(CharPool.SEMICOLON, x + 1);

			if (x == -1) {
				return;
			}

			if ((content.charAt(x + 1) != CharPool.NEW_LINE) &&
				!ToolsUtil.isInsideQuotes(content, x)) {

				addMessage(
					fileName, "There should be a line break after ';'",
					getLineNumber(content, x));
			}
		}
	}

	private String _sortParameterNames(String content) {
		Matcher matcher = _variableNamesPattern.matcher(content);

		while (matcher.find()) {
			String indent = SourceUtil.getIndent(matcher.group());

			String[] lines;
			lines = StringUtil.splitLines(matcher.group(1));

			Map<String, String> parametersMap = new TreeMap<>();

			for (String line : lines) {
				line = StringUtil.trim(line);

				int equalPos = line.indexOf(" = ");

				if (equalPos > 0) {
					String parameterName;
					String parameterValue;

					parameterName = line.substring(0, equalPos);

					if (line.endsWith(StringPool.COMMA)) {
						parameterValue = line.substring(
							equalPos + 3, line.length() - 1);
					}
					else {
						parameterValue = line.substring(equalPos + 3);
					}

					parametersMap.put(parameterName, parameterValue);
				}
			}

			StringBundler sb = new StringBundler();

			for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
				sb.append(indent);
				sb.append(CharPool.TAB);
				sb.append(entry.getKey());
				sb.append(" = ");
				sb.append(entry.getValue());
				sb.append(CharPool.COMMA);
				sb.append(CharPool.NEW_LINE);
			}

			if (!parametersMap.isEmpty()) {
				sb.setIndex(sb.index() - 2);
				sb.append(CharPool.NEW_LINE);
			}

			content = StringUtil.replaceFirst(
				content, matcher.group(1), sb.toString(), matcher.start());
		}

		return content;
	}

	private static final Pattern _variableNamesPattern = Pattern.compile(
		"[ \t]*\\w+\\.\\w+\\(\n((([ \t]*\\w+ = .*\n)){2,})[ \t]*\\);\n");

}