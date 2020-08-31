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

import com.liferay.portal.kernel.util.StringUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class MethodNamingCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (AnnotationUtil.containsAnnotation(detailAST, "Override")) {
			return;
		}

		String methodName = _getMethodName(detailAST);

		_checkDoMethodName(detailAST, methodName);
		_checkTypeName(detailAST, methodName);
	}

	private void _checkDoMethodName(DetailAST detailAST, String methodName) {
		Matcher matcher = _doMethodNamePattern.matcher(methodName);

		if (!matcher.find()) {
			return;
		}

		String noDoName =
			"_" + StringUtil.toLowerCase(matcher.group(1)) + matcher.group(2);
		String noUnderscoreName = methodName.substring(1);

		DetailAST parentDetailAST = detailAST.getParent();

		List<DetailAST> methodDefinitionDetailASTList = getAllChildTokens(
			parentDetailAST, false, TokenTypes.METHOD_DEF);

		for (DetailAST methodDefinitionDetailAST :
				methodDefinitionDetailASTList) {

			String curMethodName = _getMethodName(methodDefinitionDetailAST);

			if (curMethodName.equals(noUnderscoreName) ||
				(curMethodName.equals(noDoName) &&
				 Objects.equals(
					 getSignature(detailAST),
					 getSignature(methodDefinitionDetailAST)))) {

				return;
			}
		}

		log(detailAST, _MSG_RENAME_METHOD, methodName, noDoName);
	}

	private void _checkTypeName(DetailAST detailAST, String methodName) {
		String absolutePath = getAbsolutePath();

		if ((!methodName.matches("get[A-Z].*") ||
			 !absolutePath.contains("/internal/")) &&
			!methodName.matches("_get[A-Z].*")) {

			return;
		}

		String returnTypeName = getTypeName(detailAST, true);

		if (returnTypeName.contains("[]") ||
			methodName.matches(".*" + returnTypeName + "[0-9]*") ||
			methodName.matches("_?get" + returnTypeName + ".*")) {

			return;
		}

		List<String> enforceTypeNames = getAttributeValues(
			_ENFORCE_TYPE_NAMES_KEY);

		for (String enforceTypeName : enforceTypeNames) {
			if (returnTypeName.matches(enforceTypeName)) {
				log(detailAST, _MSG_INCORRECT_ENDING_METHOD, returnTypeName);

				return;
			}
		}
	}

	private String _getMethodName(DetailAST detailAST) {
		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		return nameDetailAST.getText();
	}

	private static final String _ENFORCE_TYPE_NAMES_KEY = "enforceTypeNames";

	private static final String _MSG_INCORRECT_ENDING_METHOD =
		"method.incorrect.ending";

	private static final String _MSG_RENAME_METHOD = "method.rename";

	private static final Pattern _doMethodNamePattern = Pattern.compile(
		"^_do([A-Z])(.*)$");

}