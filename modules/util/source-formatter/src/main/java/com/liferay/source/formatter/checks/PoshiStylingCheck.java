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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

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

		return _formatComments(content);
	}

	private void _checkLineBreak(String fileName, String content) {
		int x = -1;

		int[] multiLineCommentsPositions = SourceUtil.getMultiLinePositions(
			content, _multiLineCommentsPattern);
		int[] multiLineStringPositions = SourceUtil.getMultiLinePositions(
			content, _multiLineStringPattern);

		while (true) {
			x = content.indexOf(CharPool.SEMICOLON, x + 1);

			if (x == -1) {
				return;
			}

			int lineNumber = getLineNumber(content, x);

			String line = getLine(content, lineNumber);

			if ((content.charAt(x + 1) != CharPool.NEW_LINE) &&
				!ToolsUtil.isInsideQuotes(content, x) &&
				!SourceUtil.isInsideMultiLines(
					lineNumber, multiLineCommentsPositions) &&
				!SourceUtil.isInsideMultiLines(
					lineNumber, multiLineStringPositions) &&
				!StringUtil.startsWith(line.trim(), StringPool.DOUBLE_SLASH)) {

				addMessage(
					fileName, "There should be a line break after ';'",
					getLineNumber(content, x));
			}
		}
	}

	private String _formatComments(String content) throws IOException {
		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousComment = "";

			while ((line = unsyncBufferedReader.readLine()) != null) {
				Matcher matcher = _singleLineCommentPattern.matcher(line);

				if (!matcher.find()) {
					previousComment = "";

					sb.append(line);
					sb.append("\n");

					continue;
				}

				String comment = matcher.group(2);

				if (Validator.isNull(comment)) {
					previousComment = "";

					continue;
				}

				sb.append(matcher.group(1));

				if (comment.startsWith("Ignore") ||
					comment.startsWith("Ignored") ||
					comment.startsWith("Ignoring") ||
					comment.startsWith("Quarantine") ||
					comment.startsWith("TODO") ||
					comment.startsWith("Workaround") ||
					(!comment.endsWith(StringPool.COMMA) &&
					 !comment.endsWith(StringPool.OPEN_CURLY_BRACE) &&
					 !comment.endsWith(StringPool.OPEN_PARENTHESIS) &&
					 !comment.endsWith(StringPool.SEMICOLON) &&
					 !comment.equals(StringPool.CLOSE_CURLY_BRACE)) ||
					(comment.endsWith(StringPool.COMMA) &&
					 !comment.contains(" = "))) {

					sb.append(StringPool.SPACE);

					String trimmedComment = comment.trim();

					if (Validator.isNull(previousComment)) {
						String upperCaseFirstChar = StringUtil.toUpperCase(
							trimmedComment.substring(0, 1));

						trimmedComment =
							upperCaseFirstChar + trimmedComment.substring(1);
					}

					sb.append(trimmedComment);

					previousComment = trimmedComment;
				}
				else {
					sb.append(comment);
					previousComment = "";
				}

				sb.append("\n");
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private static final Pattern _multiLineCommentsPattern = Pattern.compile(
		"[ \t]/\\*.*?\\*/", Pattern.DOTALL);
	private static final Pattern _multiLineStringPattern = Pattern.compile(
		"'''.*?'''", Pattern.DOTALL);
	private static final Pattern _singleLineCommentPattern = Pattern.compile(
		"^([ \t]*//) *(\t*.*)");

}