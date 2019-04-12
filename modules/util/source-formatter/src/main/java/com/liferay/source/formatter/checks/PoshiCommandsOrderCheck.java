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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PoshiCommandsOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		int commandStartLineNumber = 0;

		Matcher matcher = _commandBlockPattern.matcher(content);

		List<String> commandBlocks = new ArrayList<>();

		while (matcher.find()) {
			if (commandStartLineNumber == 0) {
				commandStartLineNumber = getLineNumber(
					content, matcher.start());
			}

			commandBlocks.add(matcher.group());
		}

		if (commandBlocks.isEmpty()) {
			return content;
		}

		Collections.sort(commandBlocks, new CommandComparator());

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				if (lineNumber == commandStartLineNumber) {
					break;
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		sb.append(ListUtil.toString(commandBlocks, StringPool.BLANK, "\n"));
		sb.append("\n}");

		return sb.toString();
	}

	private static final Pattern _commandBlockPattern = Pattern.compile(
		"(^\t@.+?=.+?\n)*\t(function|macro|test) [\\s\\S]*?\n\t\\}\n",
		Pattern.MULTILINE);
	private static final Pattern _commandNamePattern = Pattern.compile(
		"(?:^\t(function|macro|test)( +))[\\s\\S]*?(?:\\{)", Pattern.MULTILINE);

	private class CommandComparator implements Comparator<String> {

		@Override
		public int compare(String s1, String s2) {
			String name1 = StringPool.BLANK;
			String name2 = StringPool.BLANK;

			Matcher matcher = _commandNamePattern.matcher(s1);

			if (matcher.find()) {
				name1 = matcher.group();
			}

			matcher = _commandNamePattern.matcher(s2);

			if (matcher.find()) {
				name2 = matcher.group();
			}

			return name1.compareTo(name2);
		}

	}

}