/*
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: LGPL-2.1-or-later
 */

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CopyrightCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		String copyright = _getCopyright(absolutePath);

		if (Validator.isNull(copyright)) {
			return content;
		}

		if (isModulesApp(absolutePath, true)) {
			String commercialCopyright = _getCommercialCopyright();

			if (Validator.isNotNull(commercialCopyright)) {
				if (content.contains(copyright)) {
					content = StringUtil.replace(
						content, copyright, commercialCopyright);
				}

				copyright = commercialCopyright;
			}
		}

		if (!fileName.endsWith(".tpl") && !fileName.endsWith(".vm")) {
			content = _fixCopyright(fileName, absolutePath, content, copyright);
		}

		return content;
	}

	private String _fixCopyright(
			String fileName, String absolutePath, String content,
			String copyright)
		throws IOException {

		String customCopyright = _getCustomCopyright(absolutePath);

		StringBuffer stringBuffer = new StringBuffer();
		Matcher matcher = _yearPattern.matcher(content);
		String year = null;

		if (matcher.find()) {
			year = matcher.group();
			matcher.appendReplacement(stringBuffer, _YEAR_VARIABLE);
			matcher.appendTail(stringBuffer);
			content = stringBuffer.toString();
		}

		if (!content.contains(copyright) &&
			((customCopyright == null) || !content.contains(customCopyright))) {

			addMessage(fileName, "Missing copyright");
		}
		else if (!content.startsWith(copyright) &&
				 !content.startsWith("<%--\n" + copyright) &&
				 ((customCopyright == null) ||
				  (!content.startsWith(customCopyright) &&
				   !content.startsWith("<%--\n" + customCopyright)))) {

			addMessage(fileName, "File must start with copyright");
		}

		if (fileName.endsWith(".jsp") || fileName.endsWith(".jspf") ||
			fileName.endsWith(".tag")) {

			content = StringUtil.replace(
				content, "<%\n" + copyright + "\n%>",
				"<%--\n" + copyright + "\n--%>");

			content = StringUtil.replace(
				content, "<%\n" + customCopyright + "\n%>",
				"<%--\n" + customCopyright + "\n--%>");
		}

		if (year != null) {
			content = StringUtil.replaceFirst(content, _YEAR_VARIABLE, year);
		}

		return content;
	}

	private synchronized String _getCommercialCopyright() {
		if (_commercialCopyright != null) {
			return _commercialCopyright;
		}

		try {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			_commercialCopyright = StringUtil.read(
				classLoader.getResourceAsStream(
					"dependencies/copyright-commercial.txt"));
		}
		catch (Exception exception) {
			_commercialCopyright = StringPool.BLANK;
		}

		return _commercialCopyright;
	}

	private synchronized String _getCopyright(String absolutePath)
		throws IOException {

		if (_copyright != null) {
			return _copyright;
		}

		String copyRightFileName = getAttributeValue(
			_COPYRIGHT_FILE_NAME_KEY, "copyright.txt", absolutePath);

		_copyright = getContent(
			copyRightFileName, ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (Validator.isNotNull(_copyright)) {
			return _copyright;
		}

		try {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			_copyright = StringUtil.read(
				classLoader.getResourceAsStream("dependencies/copyright.txt"));
		}
		catch (Exception exception) {
			_copyright = StringPool.BLANK;
		}

		return _copyright;
	}

	private String _getCustomCopyright(String absolutePath) throws IOException {
		for (int x = absolutePath.length();;) {
			x = absolutePath.lastIndexOf(CharPool.SLASH, x);

			if (x == -1) {
				break;
			}

			String copyright = FileUtil.read(
				new File(absolutePath.substring(0, x + 1) + "copyright.txt"));

			if (Validator.isNotNull(copyright)) {
				return copyright;
			}

			x = x - 1;
		}

		return null;
	}

	private static final String _COPYRIGHT_FILE_NAME_KEY = "copyrightFileName";

	private static final String _YEAR_VARIABLE = "<%= YEAR %>";

	private static final Pattern _yearPattern = Pattern.compile("\\d{4}");

	private String _commercialCopyright;
	private String _copyright;

}
