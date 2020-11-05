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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
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

		content = _formatComments(content);

		return _formatProperties(content);
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
			String newComment = StringPool.BLANK;
			String previousComment = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				Matcher matcher = _singleLineCommentPattern.matcher(line);

				if (!matcher.find()) {
					previousComment = StringPool.BLANK;

					sb.append(line);
					sb.append("\n");

					continue;
				}

				String comment = matcher.group(2);

				if (Validator.isNull(comment)) {
					previousComment = StringPool.BLANK;

					continue;
				}

				String indent = matcher.group(1);

				if (comment.startsWith("Ignore") ||
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

					String trimmedComment = comment.trim();

					if (!previousComment.startsWith(
							StringPool.DOUBLE_SLASH + StringPool.SPACE)) {

						String upperCaseFirstChar = StringUtil.toUpperCase(
							trimmedComment.substring(0, 1));

						trimmedComment =
							upperCaseFirstChar + trimmedComment.substring(1);
					}

					if (previousComment.matches("//[^ ].+")) {
						sb.append("\n");
					}

					newComment =
						StringPool.DOUBLE_SLASH + StringPool.SPACE +
							trimmedComment;
				}
				else {
					if (previousComment.startsWith(
							StringPool.DOUBLE_SLASH + StringPool.SPACE)) {

						sb.append("\n");
					}

					newComment = StringPool.DOUBLE_SLASH + comment;
				}

				sb.append(indent);
				sb.append(newComment);

				sb.append("\n");
				previousComment = newComment;
			}
		}

		if (sb.length() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _formatProperties(String content) {
		Matcher matcher = _propertyPattern.matcher(content);

		while (matcher.find()) {
			String properties = matcher.group();

			List<String> propertiesList = ListUtil.fromArray(
				properties.split("\n"));

			Collections.sort(propertiesList);

			StringBundler sb = new StringBundler(
				(propertiesList.size() * 2) + 1);

			for (String property : propertiesList) {
				sb.append(property);
				sb.append("\n");
			}

			sb.append("\n");

			String newProperties = sb.toString();

			if (!properties.equals(newProperties)) {
				return StringUtil.replaceFirst(
					content, properties, newProperties, matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _multiLineCommentsPattern = Pattern.compile(
		"[ \t]/\\*.*?\\*/", Pattern.DOTALL);
	private static final Pattern _multiLineStringPattern = Pattern.compile(
		"'''.*?'''", Pattern.DOTALL);
	private static final Pattern _propertyPattern = Pattern.compile(
		"(?<=\n)(\t+property .+;\n+)+");
	private static final Pattern _singleLineCommentPattern = Pattern.compile(
		"^([ \t]*)// *(\t*.*)");

}