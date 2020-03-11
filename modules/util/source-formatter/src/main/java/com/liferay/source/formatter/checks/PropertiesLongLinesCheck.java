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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class PropertiesLongLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (absolutePath.endsWith("-portal.properties") ||
			absolutePath.matches(
				".*\\/portal-impl\\/src\\/portal[\\w-]+\\.properties")) {

			return content;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				_checkMaxLineLength(line, fileName, lineNumber);
			}
		}

		Matcher matcher = _multiLineCommentsPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String mergedCommentLines = match.replace("\n    #", "");

			String spiltCommentLines = _splitCommentLines(mergedCommentLines);

			if (!StringUtil.equals(match, spiltCommentLines)) {
				return StringUtil.replaceFirst(
					content, match, spiltCommentLines, matcher.start());
			}
		}

		return content;
	}

	private void _checkMaxLineLength(
		String line, String fileName, int lineNumber) {

		String trimmedLine = StringUtil.trimLeading(line);

		if (!trimmedLine.startsWith("# ")) {
			return;
		}

		if (trimmedLine.matches("# Env: \\w*")) {
			return;
		}

		int lineLength = getLineLength(line);

		if (lineLength <= getMaxLineLength()) {
			return;
		}

		int x = line.indexOf("# ");
		int y = line.lastIndexOf(StringPool.SPACE, getMaxLineLength());

		if ((x + 1) == y) {
			return;
		}

		int z = line.indexOf(StringPool.SPACE, getMaxLineLength() + 1);

		if (z == -1) {
			z = lineLength;
		}

		if ((z - y + x + 2) <= getMaxLineLength()) {
			addMessage(fileName, "> " + getMaxLineLength(), lineNumber);
		}
	}

	private String _splitCommentLines(String mergedCommentLines) {
		if (mergedCommentLines.length() <= getMaxLineLength()) {
			return mergedCommentLines;
		}

		int x = mergedCommentLines.indexOf(" ", 6);

		if (x == -1) {
			return mergedCommentLines;
		}

		if (x > 80) {
			return mergedCommentLines.substring(0, x) + "\n" +
				_splitCommentLines(
					"    # " + mergedCommentLines.substring(x + 1));
		}

		x = mergedCommentLines.lastIndexOf(" ", getMaxLineLength());

		return mergedCommentLines.substring(0, x) + "\n" +
			_splitCommentLines("    # " + mergedCommentLines.substring(x + 1));
	}

	private static final Pattern _multiLineCommentsPattern = Pattern.compile(
		"(    # .+)(\n    # .+)+");

}