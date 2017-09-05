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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaExceptionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _renameVariableNames(content);
		content = _sortExceptions(
			content, _THROWS_EXCEPTIONS_PATTERN, StringPool.COMMA_AND_SPACE);
		content = _sortExceptions(content, _CATCH_EXCEPTIONS_PATTERN, " |");

		return content;
	}

	private String _renameVariableNames(String content) {
		Matcher matcher = _CATCH_EXCEPTION_PATTERN.matcher(content);

		int skipVariableNameCheckEndPos = -1;

		while (matcher.find()) {
			if (Validator.isNotNull(matcher.group(2))) {
				return StringUtil.replaceFirst(
					content, "final ", StringPool.BLANK, matcher.start());
			}

			String exceptionClassName = matcher.group(3);
			String exceptionVariableName = matcher.group(4);
			String tabs = matcher.group(1);

			String expectedExceptionVariableName = "e";

			if (!exceptionClassName.contains(" |")) {
				Matcher lowerCaseNumberOrPeriodMatcher =
					_LOWER_CASE_NUMBER_OR_PERIOD_PATTERN.matcher(
						exceptionClassName);

				expectedExceptionVariableName = StringUtil.toLowerCase(
					lowerCaseNumberOrPeriodMatcher.replaceAll(
						StringPool.BLANK));
			}

			Pattern exceptionVariablePattern = RegexUtil.getPattern(
				"(\\W)" + exceptionVariableName + "(\\W)");

			int pos = content.indexOf(
				"\n" + tabs + StringPool.CLOSE_CURLY_BRACE, matcher.end() - 1);

			String insideCatchCode = content.substring(matcher.end(), pos + 1);

			if (insideCatchCode.contains("catch (" + exceptionClassName)) {
				skipVariableNameCheckEndPos = pos;
			}

			if ((skipVariableNameCheckEndPos < matcher.start()) &&
				!expectedExceptionVariableName.equals(exceptionVariableName)) {

				String catchExceptionCodeBlock = content.substring(
					matcher.start(), pos + 1);

				Matcher exceptionVariableMatcher =
					exceptionVariablePattern.matcher(catchExceptionCodeBlock);

				String catchExceptionReplacement =
					exceptionVariableMatcher.replaceAll(
						"$1" + expectedExceptionVariableName + "$2");

				return StringUtil.replaceFirst(
					content, catchExceptionCodeBlock, catchExceptionReplacement,
					matcher.start() - 1);
			}
		}

		return content;
	}

	private String _sortExceptions(
		String content, Pattern pattern, String delimeter) {

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String exceptions = matcher.group(1);

			exceptions = StringUtil.replace(
				exceptions, new String[] {StringPool.TAB, StringPool.NEW_LINE},
				new String[] {StringPool.SPACE, StringPool.SPACE});

			String previousException = StringPool.BLANK;

			for (String exception : StringUtil.split(exceptions, delimeter)) {
				exception = StringUtil.trim(exception);

				if (Validator.isNotNull(previousException) &&
					(previousException.compareToIgnoreCase(exception) > 0)) {

					String replacement = match.replaceAll(
						"(\\W)" + exception + "(\\W)",
						"$1" + previousException + "$2");

					replacement = replacement.replaceFirst(
						"(\\W)" + previousException + "(\\W)",
						"$1" + exception + "$2");

					return _sortExceptions(
						StringUtil.replace(content, match, replacement),
						pattern, delimeter);
				}

				previousException = exception;
			}
		}

		return content;
	}

	private static final Pattern _CATCH_EXCEPTION_PATTERN =
		RegexUtil.getPattern(
			"\n(\t+)catch \\((final )?(.+Exception) (.+)\\) \\{\n");

	private static final Pattern _CATCH_EXCEPTIONS_PATTERN =
		RegexUtil.getPattern(
			"\n\t+catch \\((?:final )?((\\w+Exception \\|\\s+)+" +
				"(\\w+Exception\\s+))\\w+\\)");

	private static final Pattern _LOWER_CASE_NUMBER_OR_PERIOD_PATTERN =
		RegexUtil.getPattern("[a-z0-9.]");

	private static final Pattern _THROWS_EXCEPTIONS_PATTERN =
		RegexUtil.getPattern("\\sthrows ([\\s\\w,]*)[;{]\n");

}