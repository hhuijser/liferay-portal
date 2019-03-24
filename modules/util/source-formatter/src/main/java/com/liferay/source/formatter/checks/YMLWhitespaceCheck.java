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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.YMLSourceUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 * @author Alan Huang
 */
public class YMLWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = StringUtil.replace(
			content, CharPool.TAB, StringPool.FOUR_SPACES);

		content = _formatDefinitions(fileName, content, StringPool.BLANK, 0);

		content = _formatSequencesAndMappings(content);

		return super.doProcess(fileName, absolutePath, content);
	}

	private String _formatDefinition(
		String fileName, String definition, String indent, int level,
		boolean hasNestedDefinitions) {

		String expectedIndent = StringPool.BLANK;

		for (int j = 0; j < level; j++) {
			expectedIndent = expectedIndent + StringPool.FOUR_SPACES;
		}

		String newDefinition = definition;

		if (!expectedIndent.equals(indent)) {
			newDefinition = expectedIndent + StringUtil.trimLeading(definition);
		}

		if (hasNestedDefinitions) {
			return newDefinition;
		}

		String[] lines = StringUtil.splitLines(newDefinition);

		if (lines.length <= 1) {
			return newDefinition;
		}

		String firstLine = lines[1];

		String newNestedContent = StringPool.BLANK;
		String oldNestedContent = StringPool.BLANK;

		String nestedIndent = firstLine.replaceAll("^(\\s+).+", "$1");

		if (nestedIndent.equals(firstLine)) {
			nestedIndent = StringPool.BLANK;
		}

		for (int j = 1; j < lines.length; j++) {
			String line = lines[j];

			if (j > 1) {
				newNestedContent = newNestedContent + StringPool.NEW_LINE;
				oldNestedContent = oldNestedContent + StringPool.NEW_LINE;
			}

			newNestedContent = newNestedContent + line;
			oldNestedContent = oldNestedContent + line;

			if (Validator.isNull(line)) {
				continue;
			}

			String curIndent = line.replaceAll("^(\\s+).+", "$1");

			if (curIndent.equals(line)) {
				curIndent = StringPool.BLANK;
			}

			if (!curIndent.equals(nestedIndent)) {
				continue;
			}

			String trimmedLine = StringUtil.trimLeading(line);

			newNestedContent = StringUtil.replaceLast(
				newNestedContent, line,
				expectedIndent + StringPool.FOUR_SPACES + trimmedLine);
		}

		if (!newNestedContent.equals(oldNestedContent)) {
			if (!_hasMapInsideList(lines)) {
				newDefinition = StringUtil.replaceFirst(
					newDefinition, oldNestedContent, newNestedContent);
			}
			else {
				String message = StringBundler.concat(
					"Incorrect whitespace, expected '",
					expectedIndent + StringPool.FOUR_SPACES, "'\n",
					oldNestedContent);

				addMessage(fileName, message);
			}
		}

		return newDefinition;
	}

	private String _formatDefinitions(
		String fileName, String content, String indent, int level) {

		List<String> definitions = YMLSourceUtil.getDefinitions(
			content, indent);
		String lines[] = content.split("\n");
		int pos = lines[0].length();
//		for (String definition : definitions) {
		for (int i = 0; i< definitions.size(); i ++) {
			
			String definition = definitions.get(i);
			
			if (StringUtil.startsWith(definition.trim(), "- '")) {
				continue;
			}

			String nestedDefinitionIndent =
				YMLSourceUtil.getNestedDefinitionIndent(definition);

			List<String> nestedDefinitions = Collections.emptyList();

			if (!nestedDefinitionIndent.equals(StringPool.BLANK)) {
				nestedDefinitions = YMLSourceUtil.getDefinitions(
					definition, nestedDefinitionIndent);

				String newDefinition = _formatDefinitions(
					fileName, definition, nestedDefinitionIndent, level + 1);

				if (!newDefinition.equals(definition)) {
					content = StringUtil.replaceFirst(
						content, definition, newDefinition,
						0);

					definition = newDefinition;
				}
				
//				if (i > 0) {
//					pos = pos + definition.length();
//				}

			}

			String newDefinition = _formatDefinition(
				fileName, definition, indent, level,
				!nestedDefinitions.isEmpty());

			if (!newDefinition.equals(definition)) {
				content = StringUtil.replaceFirst(
					content, definition, newDefinition,
					pos);
			}
			

			pos = pos + newDefinition.length();
		}

		return content;
	}

	private String _formatSequencesAndMappings(String content) {
		Matcher matcher = _mappingEntryPattern.matcher(content);

		while (matcher.find()) {
			String s = matcher.group();

			String[] lines = s.split("\n");

			if (lines.length <= 1) {
				continue;
			}

			if (StringUtil.startsWith(lines[0].trim(), "- '")) {
				continue;
			}

			StringBundler sb = new StringBundler();

			for (int i = 1; i < lines.length; i++) {
				sb.append(StringPool.NEW_LINE);

				if (lines[0].endsWith(StringPool.COLON)) {
					sb.append(StringPool.DOUBLE_SPACE);
					sb.append(lines[i]);
				}
				else {
					sb.append(lines[i].substring(2));
				}
			}

			sb.append(StringPool.NEW_LINE);

			content = StringUtil.replaceFirst(
				content, matcher.group(),
				lines[0] + _formatSequencesAndMappings(sb.toString()));
		}

		return content;
	}

	private boolean _hasMapInsideList(String[] lines) {
		if (lines.length <= 1) {
			return false;
		}

		String trimmedFirstLine = StringUtil.trimLeading(lines[1]);

		if (!trimmedFirstLine.startsWith(StringPool.DASH)) {
			return false;
		}

		for (int j = 1; j < lines.length; j++) {
			String trimmedLine = StringUtil.trimLeading(lines[j]);

			if (trimmedLine.matches("\\w+:.*")) {
				return true;
			}
		}

		return false;
	}

	private static final Pattern _mappingEntryPattern = Pattern.compile(
//		"^( +)- .+(\n|\\Z)((\\1 +.+)(\n|\\Z))+", Pattern.MULTILINE);
		"^( *)-(\n|\\Z)((\\1 +.+)(\n|\\Z))+", Pattern.MULTILINE);

}