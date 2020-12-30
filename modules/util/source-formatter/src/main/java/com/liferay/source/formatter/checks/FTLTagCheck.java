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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class FTLTagCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _formatTags(content);

		content = _formatAssignTags(content);

		return _formatMacroTags(content);
	}

	private String _formatAssignTags(String content) {
		Matcher matcher = _incorrectAssignTagPattern.matcher(content);

		content = matcher.replaceAll("$1 />\n");

		matcher = _assignTagsBlockPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String tabs = matcher.group(2);

			String replacement = StringUtil.removeSubstrings(
				match, "<#assign ", "<#assign\n", "/>");

			replacement = StringUtil.removeChar(replacement, CharPool.TAB);

			String[] lines = StringUtil.splitLines(replacement);

			StringBundler sb = new StringBundler((3 * lines.length) + 5);

			sb.append(tabs);
			sb.append("<#assign");

			for (String line : lines) {
				sb.append("\n\t");
				sb.append(tabs);
				sb.append(line);
			}

			sb.append(StringPool.NEW_LINE);
			sb.append(tabs);
			sb.append("/>\n\n");

			content = StringUtil.replace(content, match, sb.toString());
		}

		return content;
	}

	private String _formatMacroTags(String content) {
		Matcher matcher = _macroTagPattern.matcher(content);

		while (matcher.find()) {
			String tag = matcher.group();

			String newTag = _parseMacroTags(
				matcher.group(3), matcher.group(1), matcher.group(2));

			if (!tag.equals(newTag)) {
				return StringUtil.replace(content, tag, newTag);
			}
		}

		return content;
	}

	private String _formatTags(String content) {
		Matcher matcher = _tagPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group(3);

			Map<String, String> attributesMap = _getAttributesMap(match);

			if (attributesMap.isEmpty()) {
				continue;
			}

			String tabs = matcher.group(2);

			String delimeter = StringPool.SPACE;

			if (attributesMap.size() > 1) {
				delimeter = "\n" + tabs + "\t";
			}

			StringBundler sb = new StringBundler(
				(attributesMap.size() * 4) + 4);

			sb.append(_getTagName(match));
			sb.append(delimeter);

			for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
				sb.append(entry.getKey());
				sb.append(StringPool.EQUAL);
				sb.append(entry.getValue());
				sb.append(delimeter);
			}

			sb.setIndex(sb.index() - 1);

			String closingTag = matcher.group(4);

			if (attributesMap.size() > 1) {
				sb.append("\n");
				sb.append(tabs);
			}
			else if (closingTag.equals("/>")) {
				sb.append(StringPool.SPACE);
			}

			String replacement = sb.toString();

			if (!replacement.equals(match)) {
				return StringUtil.replaceFirst(
					content, match, replacement, matcher.start());
			}
		}

		return content;
	}

	private Map<String, String> _getAttributesMap(String s) {
		Map<String, String> attributesMap = new TreeMap<>();

		String attributeName = null;

		while (true) {
			boolean match = false;

			Matcher matcher = _tagAttributePattern.matcher(s);

			while (matcher.find()) {
				if (ToolsUtil.isInsideQuotes(s, matcher.end() - 1)) {
					continue;
				}

				match = true;

				break;
			}

			if (!match) {
				break;
			}

			if (attributeName != null) {
				attributesMap.put(
					attributeName,
					StringUtil.trim(s.substring(0, matcher.start())));
			}

			attributeName = matcher.group(1);

			s = s.substring(matcher.end());
		}

		if (attributeName != null) {
			attributesMap.put(attributeName, StringUtil.trim(s));
		}

		return attributesMap;
	}

	private String _getTagName(String s) {
		StringBundler sb = new StringBundler();

		for (char c : s.toCharArray()) {
			if (Character.isWhitespace(c)) {
				return sb.toString();
			}

			sb.append(c);
		}

		return sb.toString();
	}

	private String _parseMacroTags(
		String attributes, String indent, String tagName) {

		int x = -1;

		Map<String, String> attributesMap = new HashMap<>();

		String s = attributes.trim();

		s = s.substring(x + 1);

		while (true) {
			if (s.length() == 0) {
				break;
			}

			x = s.indexOf(CharPool.EQUAL);

			String attributeName = StringPool.BLANK;

			List<String> attributeNamesList = new ArrayList<>();

			if (x != -1) {
				attributeName = s.substring(0, x);
				s = StringUtil.trimLeading(s.substring(x + 1));
			}
			else {
				attributeName = s.replaceAll("\\s+", "\n");

				attributeNamesList = ListUtil.fromArray(
					attributeName.split("\n"));

				for (String name : attributeNamesList) {
					attributesMap.put(name.trim(), StringPool.BLANK);
				}

				break;
			}

			attributeName = attributeName.replaceAll("\\s+", "\n");

			attributeNamesList = ListUtil.fromArray(attributeName.split("\n"));

			if (attributeNamesList.size() > 1) {
				for (int i = 0; i < (attributeNamesList.size() - 1); i++) {
					attributeName = attributeNamesList.get(i);

					attributesMap.put(attributeName, StringPool.BLANK);
				}

				attributeName = attributeNamesList.get(
					attributeNamesList.size() - 1);
			}

			char delimeter = s.charAt(0);

			if ((delimeter != CharPool.APOSTROPHE) &&
				(delimeter != CharPool.QUOTE)) {

				String tmp = StringPool.BLANK;

				x = 0;

				while (x != s.length()) {
					tmp = s.substring(x, x + 1);

					if (!tmp.matches("\\s")) {
						x = x + 1;

						continue;
					}

					break;
				}

				attributesMap.put(attributeName.trim(), s.substring(0, x));

				if (x != s.length()) {
					s = StringUtil.trim(s.substring(x + 1));

					continue;
				}

				break;
			}

			x = 0;

			while (true) {
				x = s.indexOf(delimeter, x + 1);

				if (x == -1) {
					return null;
				}

				String attributeValue = s.substring(0, x + 1);

				if (attributeValue.startsWith("\"") &&
					(getLevel(attributeValue, "\"", "\"") == 0)) {

					attributesMap.put(attributeName.trim(), attributeValue);

					s = StringUtil.trim(s.substring(x + 1));

					break;
				}
			}
		}

		return _sortMacroTags(attributesMap, indent, tagName);
	}

	private String _sortMacroTags(
		Map<String, String> attributesMap, String indent, String tagName) {

		List<Map.Entry<String, String>> attributeEntries = new ArrayList<>();

		attributeEntries.addAll(attributesMap.entrySet());

		Collections.sort(
			attributeEntries,
			new Comparator<Map.Entry>() {

				@Override
				public int compare(Map.Entry entry1, Map.Entry entry2) {
					String entryValue1 = (String)entry1.getValue();
					String entryValue2 = (String)entry2.getValue();

					if ((entryValue1.length() == 0) &&
						(entryValue2.length() != 0)) {

						return -1;
					}

					if ((entryValue1.length() != 0) &&
						(entryValue2.length() == 0)) {

						return 1;
					}

					String entryName1 = (String)entry1.getKey();
					String entryName2 = (String)entry2.getKey();

					return entryName1.compareTo(entryName2);
				}

			});

		StringBundler sb = new StringBundler((attributeEntries.size() * 7) + 5);

		sb.append(indent + tagName);
		sb.append(StringPool.NEW_LINE);

		for (Map.Entry<String, String> attributeEntrie : attributeEntries) {
			sb.append(indent + StringPool.TAB);
			sb.append(attributeEntrie.getKey());

			String attributeValue = attributeEntrie.getValue();

			if (Validator.isNotNull(attributeValue)) {
				sb.append(StringPool.SPACE);
				sb.append(StringPool.EQUAL);
				sb.append(StringPool.SPACE);
				sb.append(attributeValue);
			}

			sb.append(StringPool.NEW_LINE);
		}

		sb.append(indent);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private static final Pattern _assignTagsBlockPattern = Pattern.compile(
		"((\t*)<#assign(.(?!<[#@]))+?/>(\n|$)+){2,}",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _incorrectAssignTagPattern = Pattern.compile(
		"(<#assign .*=.*[^/])>(\n|$)");
	private static final Pattern _macroTagPattern = Pattern.compile(
		"([ \t]*)(<#macro \\w+)\n(.*?)>(\n|$)", Pattern.DOTALL);
	private static final Pattern _tagAttributePattern = Pattern.compile(
		"\\s(\\S+)\\s*=");
	private static final Pattern _tagPattern = Pattern.compile(
		"(\\A|\n)(\t*)<@(\\S[^>]*?)(/?>)(\n|\\Z)", Pattern.DOTALL);

}