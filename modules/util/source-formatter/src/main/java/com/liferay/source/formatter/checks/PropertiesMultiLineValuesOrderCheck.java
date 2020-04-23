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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PropertiesMultiLineValuesOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("/test.properties")) {
			return content;
		}

		return _sortValues(content);
	}

	private String _sortValues(String content) throws IOException {
		Matcher matcher1 = _keyValuesPattern.matcher(content);

		while (matcher1.find()) {
			String match = matcher1.group();

			Matcher matcher2 = _multiLineValuesPattern.matcher(match);

			outerLoop:
			while (matcher2.find()) {
				String values = matcher2.group();

				List<String> valuesList = ListUtil.fromArray(
					values.split(",\\\\"));

				if (StringUtil.count(values, "\n") != valuesList.size()) {
					continue outerLoop;
				}

				Collections.sort(
					valuesList, new NaturalOrderStringComparator());

				String newValues = _splitValues(values, valuesList);

				if (!values.equals(newValues)) {
					return StringUtil.replaceFirst(
						content, values, newValues, matcher1.start());
				}
			}
		}

		return content;
	}

	private String _splitValues(String values, List<String> valuesList) {
		StringBundler sb = new StringBundler(valuesList.size() * 2);

		for (String value : valuesList) {
			sb.append(value);
			sb.append(",\\");
		}

		if (!values.endsWith(",\\")) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static final Pattern _keyValuesPattern = Pattern.compile(
		"(?<=\n)( *).+=\\\\(\n\\1    .+){2,}");
	private static final Pattern _multiLineValuesPattern = Pattern.compile(
		"(\n +(?![\\\\# ]).*){2,}");

}