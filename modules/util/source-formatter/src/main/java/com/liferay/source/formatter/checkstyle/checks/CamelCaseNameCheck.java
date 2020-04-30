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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class CamelCaseNameCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.METHOD_DEF, TokenTypes.PARAMETER_DEF,
			TokenTypes.VARIABLE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (AnnotationUtil.containsAnnotation(detailAST, "Deprecated") ||
			((detailAST.getType() == TokenTypes.METHOD_DEF) &&
			 AnnotationUtil.containsAnnotation(detailAST, "Override"))) {

			return;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		_checkIncorrectCamelCase(
			detailAST, name, "non", "nonProxyHost",
			"nonSerializableObjectHandler", "nonSpringServlet");
		_checkIncorrectCamelCase(detailAST, name, "re", "reCaptcha");
		_checkIncorrectCamelCase(detailAST, name, "sub", "subSelect");

		_checkRequiredCamelCase(
			detailAST, name, "name", "filenameFilter", "hostname", "rename",
			"subname");
	}

	private void _checkIncorrectCamelCase(
		DetailAST detailAST, String name, String s, String... allowedNames) {

		if (_isAllowedName(name, allowedNames)) {
			return;
		}

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"(^_?", s, "|", TextFormatter.format(s, TextFormatter.G),
				")([A-Z]([a-z]*|[A-Z]*))"));

		Matcher matcher = pattern.matcher(name);

		if (matcher.find()) {
			if (_containsNameInAssignStatement(
					detailAST, s + matcher.group(2))) {

				return;
			}

			if (detailAST.getType() == TokenTypes.METHOD_DEF) {
				log(
					detailAST, _MSG_INCORRECT_FOLLOWING_UPPERCASE, s, "method",
					name);
			}
			else if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
				log(
					detailAST, _MSG_INCORRECT_FOLLOWING_UPPERCASE, s,
					"parameter", name);
			}
			else {
				log(
					detailAST, _MSG_INCORRECT_FOLLOWING_UPPERCASE, s,
					"variable", name);
			}
		}

		if (detailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return;
		}

		pattern = Pattern.compile(
			StringBundler.concat(
				"(\\A|_)(", StringUtil.toUpperCase(s), "_[A-Z]+)"));

		matcher = pattern.matcher(name);

		if (matcher.find() &&
			!_containsNameInAssignStatement(detailAST, matcher.group(2))) {

			log(
				detailAST, _MSG_INCORRECT_FOLLOWING_UNDERSCORE,
				StringUtil.toUpperCase(s), name);
		}
	}

	private void _checkRequiredCamelCase(
		DetailAST detailAST, String name, String s, String... allowedNames) {

		if (_isAllowedName(name, allowedNames)) {
			return;
		}

		Pattern pattern = Pattern.compile(
			"(((\\A|_)[a-z]+|[A-Z]([A-Z]+|[a-z]+))" + s + ")");

		Matcher matcher = pattern.matcher(name);

		if (matcher.find()) {
			if (_containsNameInAssignStatement(
					detailAST, StringUtil.toLowerCase(matcher.group(1)))) {

				return;
			}

			if (detailAST.getType() == TokenTypes.METHOD_DEF) {
				log(
					detailAST, _MSG_REQUIRED_STARTING_UPPERCASE, s, "method",
					name);
			}
			else if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
				log(
					detailAST, _MSG_REQUIRED_STARTING_UPPERCASE, s, "parameter",
					name);
			}
			else {
				log(
					detailAST, _MSG_REQUIRED_STARTING_UPPERCASE, s, "variable",
					name);
			}
		}

		if (detailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return;
		}

		pattern = Pattern.compile(
			StringBundler.concat(
				"(\\A|_)([A-Z]+", StringUtil.toUpperCase(s), ").*"));

		matcher = pattern.matcher(name);

		if (matcher.find() &&
			!_containsNameInAssignStatement(detailAST, matcher.group(2))) {

			log(
				detailAST, _MSG_REQUIRED_PRECEDING_UNDERSCORE,
				StringUtil.toUpperCase(s), name);
		}
	}

	private boolean _containsNameInAssignStatement(
		DetailAST detailAST, String name) {

		DetailAST assignDetailAST = detailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return false;
		}

		String constantFormatName = _getConstantFormatName(name);

		List<DetailAST> stringLiteralDetailASTlist = getAllChildTokens(
			assignDetailAST, true, TokenTypes.STRING_LITERAL);

		if (!stringLiteralDetailASTlist.isEmpty()) {
			String camelCaseFormatName = _getCamelCaseFormatName(name);

			if (camelCaseFormatName == null) {
				return false;
			}

			String propertyFormatName = _getPropertyFormatName(
				camelCaseFormatName);

			for (DetailAST stringLiteralDetailAST :
					stringLiteralDetailASTlist) {

				String text = stringLiteralDetailAST.getText();

				if (text.contains(camelCaseFormatName) ||
					text.contains(constantFormatName) ||
					text.contains(propertyFormatName)) {

					return true;
				}
			}
		}

		List<DetailAST> identDetailASTlist = getAllChildTokens(
			assignDetailAST, true, TokenTypes.IDENT);

		for (DetailAST identDetailAST : identDetailASTlist) {
			String text = identDetailAST.getText();

			if (text.contains(constantFormatName)) {
				return true;
			}
		}

		return false;
	}

	private String _getCamelCaseFormatName(String name) {
		if (name.startsWith(StringPool.UNDERLINE)) {
			name = name.substring(1);
		}

		if (!StringUtil.isUpperCase(name)) {
			return name;
		}

		name = TextFormatter.format(name, TextFormatter.O);

		name = StringUtil.toLowerCase(name);

		return TextFormatter.format(name, TextFormatter.M);
	}

	private String _getConstantFormatName(String name) {
		if (name.startsWith(StringPool.UNDERLINE)) {
			name = name.substring(1);
		}

		if (StringUtil.isUpperCase(name)) {
			return name;
		}

		name = TextFormatter.format(name, TextFormatter.K);

		name = TextFormatter.format(name, TextFormatter.N);

		return StringUtil.toUpperCase(name);
	}

	private String _getPropertyFormatName(String camelCaseName) {
		String propertyFormatName = TextFormatter.format(
			camelCaseName, TextFormatter.K);

		return StringUtil.replace(
			propertyFormatName, CharPool.DASH, CharPool.PERIOD);
	}

	private boolean _isAllowedName(String name, String[] allowedNames) {
		for (String allowedName : allowedNames) {
			if (name.startsWith(allowedName) ||
				name.startsWith("_" + allowedName) ||
				name.contains(
					TextFormatter.format(allowedName, TextFormatter.G))) {

				return true;
			}

			String constantFormatName = _getConstantFormatName(allowedName);

			if (name.startsWith(constantFormatName) ||
				name.contains("_" + constantFormatName)) {

				return true;
			}
		}

		return false;
	}

	private static final String _MSG_INCORRECT_FOLLOWING_UNDERSCORE =
		"following.underscore.incorrect";

	private static final String _MSG_INCORRECT_FOLLOWING_UPPERCASE =
		"following.uppercase.incorrect";

	private static final String _MSG_REQUIRED_PRECEDING_UNDERSCORE =
		"preceding.underscore.required";

	private static final String _MSG_REQUIRED_STARTING_UPPERCASE =
		"starting.uppercase.required";

}