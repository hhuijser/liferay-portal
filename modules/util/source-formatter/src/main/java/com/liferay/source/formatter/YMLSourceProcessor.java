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

package com.liferay.source.formatter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class YMLSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected File format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		Set<String> modifiedContents = new HashSet<>();
		Set<String> modifiedMessages = new TreeSet<>();

		content = preLogic(content);

		String newContent = format(
			file, fileName, absolutePath, content, content,
			new ArrayList<>(_getSourceChecks()), modifiedContents, modifiedMessages,
			0);

		newContent = postLogic(newContent);

		return processFormattedFile(
			file, fileName, content, newContent, modifiedMessages);
	}

	private String preLogic(String content) {
		content = _fixIncorrectIndentation(content);		
		
		Matcher matcher = _mappingEntryPattern.matcher(content);

		while (matcher.find()) {
			String s = matcher.group();
			String g1 = matcher.group(1);
			String g2 = matcher.group(2);
			String g3 = matcher.group(3);
//			String ss = StringUtil.insert(
//				content,
//				StringBundler.concat(
//					"\n", matcher.group(2), " "),
//				matcher.end(1) - 1);
			String sss = StringUtil.replaceFirst(
				content,
				matcher.group(),
				StringBundler.concat(
					matcher.group(1) + "\n" + matcher.group(2) + "  " + matcher.group(3))
							);

			content = sss;
		}


		return content;
	}

	private String postLogic(String content) {
		
//		content = content.replaceAll("((^ *)-(\n)) +(.*)(\n)", "$2 $4");
//		content = content.replaceAll("(\n *-)\n +(.*\n)", "$1 $2");
//		content = content.replaceAll("(^ *-)\n +(.*)", "$1 $2\n");
//		content = content.replaceAll("(^( *)-)\n +(.*)", "AAA");
		content = content.replaceAll("( +-)\n +(.*)", "$1 $2");

//		content = content.replaceAll("-\n +", "ZZZZ");
//		content = content.replaceAll("\n", "ZZZZ");
//		content.replaceAll("cc", "XXXXX");

		return content;
	}

	private String _fixIncorrectIndentation(String content) {
		Matcher matcher = _incorrectIndentationPattern.matcher(content);

		while (matcher.find()) {
			String s = matcher.group();

			String[] lines = s.split("\n");

			StringBundler sb = new StringBundler();

			for (int i = 1; i < lines.length; i++) {
				sb.append(StringPool.NEW_LINE);
				sb.append("  ");
				sb.append(lines[i]);
			}

			content = StringUtil.replaceFirst(
				content, matcher.group(),
				lines[0] + _fixIncorrectIndentation(sb.toString()));
		}

		return content;
	}

	private static final String[] _INCLUDES = {"**/*.yaml", "**/*.yml"};
	
	private static final Pattern _mappingEntryPattern = Pattern.compile(
		"(^( *)- )(.+(\n|\\Z))", Pattern.MULTILINE);

	private static final Pattern _incorrectIndentationPattern = Pattern.compile(
			"^( *)[^ -].+(\n\\1- .+(\n\\1 .+)*)+", Pattern.MULTILINE);

}