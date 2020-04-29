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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

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
		if ((detailAST.getType() == TokenTypes.METHOD_DEF) &&
			AnnotationUtil.containsAnnotation(detailAST, "Override")) {

			return;
		}

		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		_checkName(
			detailAST, name, "non", "nonProxyHost",
			"nonSerializableObjectHandler", "nonSpringServlet");
		_checkName(detailAST, name, "re", "reCaptcha");
		_checkName(detailAST, name, "sub", "subSelect");
	}

	private void _checkName(
		DetailAST detailAST, String name, String s, String... allowedNames) {

		String lowerCaseName = StringUtil.toLowerCase(name);

		for (String allowedName : allowedNames) {
			if (lowerCaseName.contains(StringUtil.toLowerCase(allowedName))) {
				return;
			}

			String formattedAllowedName = StringUtil.replace(
				TextFormatter.format(allowedName, TextFormatter.K),
				StringPool.DASH, StringPool.UNDERLINE);

			if (name.contains(StringUtil.toUpperCase(formattedAllowedName))) {
				return;
			}
		}

		if (name.matches(
				StringBundler.concat(
					"(^_?", s, "|.*", TextFormatter.format(s, TextFormatter.G),
					")[A-Z].*"))) {

			if (detailAST.getType() == TokenTypes.METHOD_DEF) {
				log(detailAST, _MSG_METHOD_INVALID_NAME, s, name);
			}
			else if (detailAST.getType() == TokenTypes.PARAMETER_DEF) {
				log(detailAST, _MSG_PARAMETER_INVALID_NAME, s, name);
			}
			else {
				log(detailAST, _MSG_VARIABLE_INVALID_NAME, s, name);
			}
		}
		else if ((detailAST.getType() == TokenTypes.VARIABLE_DEF) &&
				 name.matches(
					 StringBundler.concat(
						 "(.*_)?", StringUtil.toUpperCase(s), "_[A-Z].*"))) {

			log(
				detailAST, _MSG_CONSTANT_INVALID_NAME,
				StringUtil.toUpperCase(s), name);
		}
	}

	private static final String _MSG_CONSTANT_INVALID_NAME =
		"constant.invalidName";

	private static final String _MSG_METHOD_INVALID_NAME = "method.invalidName";

	private static final String _MSG_PARAMETER_INVALID_NAME =
		"parameter.invalidName";

	private static final String _MSG_VARIABLE_INVALID_NAME =
		"variable.invalidName";

}