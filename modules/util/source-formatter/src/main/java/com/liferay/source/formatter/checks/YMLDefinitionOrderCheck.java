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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.YMLSourceUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Alan Huang
 */
public class YMLDefinitionOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortDefinitions(fileName, content, StringPool.BLANK);
	}

	private String _sortDefinitions(
		String fileName, String content, String indent) {

		List<String> definitions = YMLSourceUtil.getDefinitions(
			content, indent);

		if ((definitions.size() == 1) && !content.contains("\n")) {
			return content;
		}

		String oldDefinitions = definitions.toString();

		Collections.sort(
			definitions,
			new Comparator<String>() {

				@Override
				public int compare(String definition1, String definition2) {
					String[] definition1Lines = StringUtil.splitLines(
						definition1);
					String[] definition2Lines = StringUtil.splitLines(
						definition2);

					if (definition1Lines[0].equals("- ") &&
						definition2Lines[0].equals("- ")) {

						String trimmedDefinitin1Line =
							definition1Lines[1].trim();
						String trimmedDefinitin2Line =
							definition2Lines[1].trim();

						if (trimmedDefinitin1Line.equals("in: query") &&
							trimmedDefinitin1Line.equals(
								trimmedDefinitin2Line)) {

							return _sortSpecificDefinitions(
								definition1, definition2, "name");
						}

						return definition1Lines[1].compareTo(
							definition2Lines[1]);
					}

					return definition1Lines[0].compareTo(definition2Lines[0]);
				}

			});

		if (!oldDefinitions.equals(definitions.toString())) {
			StringBundler sb = new StringBundler();

			for (String definition : definitions) {
				sb.append(definition);
				sb.append("\n");
			}

			sb.setIndex(sb.index() - 1);

			String[] lines = content.split("\n");

			if (!indent.equals("")) {
				content = lines[0] + "\n" + sb.toString();
			}
			else {
				content = sb.toString();
			}
		}

		definitions = YMLSourceUtil.getDefinitions(content, indent);

		for (String definition : definitions) {
			String nestedDefinitionIndent =
				YMLSourceUtil.getNestedDefinitionIndent(definition);

			if (!nestedDefinitionIndent.equals(StringPool.BLANK)) {
				content = StringUtil.replaceFirst(
					content, definition,
					_sortDefinitions(
						fileName, definition, nestedDefinitionIndent));
			}
		}

		return content;
	}

	private int _sortSpecificDefinitions(
		String definition1, String definition2, String key) {

		Pattern pattern = Pattern.compile(
			"^ *" + key + ": *(\\w*)(\n|\\Z)", Pattern.MULTILINE);

		String value1 = StringPool.BLANK;

		Matcher matcher = pattern.matcher(definition1);

		if (matcher.find()) {
			value1 = matcher.group(1);
		}

		String value2 = StringPool.BLANK;

		matcher = pattern.matcher(definition2);

		if (matcher.find()) {
			value2 = matcher.group(1);
		}

		return value1.compareTo(value2);
	}

}