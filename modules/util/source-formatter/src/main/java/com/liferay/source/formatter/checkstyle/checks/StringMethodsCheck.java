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

import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Preston Crary
 */
public class StringMethodsCheck extends AbstractCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF};
	}

	@Override
	public void visitToken(DetailAST detailAST) {
		_checkMethod(detailAST, "matches", "(String)");
		_checkMethod(detailAST, "replace", "(CharSequence, CharSequence)");
		_checkMethod(detailAST, "replaceAll", "(String, String)");
		_checkMethod(detailAST, "replaceFirst", "(String, String)");
	}

	private void _checkMethod(
		DetailAST detailAST, String methodName, String methodSignature) {

		List<DetailAST> methodCallASTList = DetailASTUtil.getMethodCalls(
			detailAST, "String", methodName);

		for (DetailAST methodCallAST : methodCallASTList) {
			log(
				methodCallAST.getLineNo(), _MSG_METHOD_INVALID_NAME,
				"String." + methodName + methodSignature);
		}
	}

	private static final String _MSG_METHOD_INVALID_NAME = "method.invalidName";

}