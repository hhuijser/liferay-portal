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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.List;

/**
 * @author Alan Huang
 */
public class OSGiResourceBuilderCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.CLASS_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST annotationDetailAST = AnnotationUtil.getAnnotation(
			detailAST, "Component");

		if (annotationDetailAST == null) {
			return;
		}

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			detailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			String methodName = getMethodName(methodCallDetailAST);

			if (!methodName.equals("builder")) {
				continue;
			}

			String classOrVariableName = _getClassOrVariableName(
				methodCallDetailAST);

			if (classOrVariableName == null) {
				return;
			}

			String fullyQualifiedClassName = classOrVariableName;

			for (String importName : getImportNames(methodCallDetailAST)) {
				if (importName.endsWith("." + classOrVariableName)) {
					fullyQualifiedClassName = importName;

					break;
				}
			}

			if (fullyQualifiedClassName.contains(".client.")) {
				continue;
			}

			log(methodCallDetailAST, _MSG_AVOID_METHOD_CALL);
		}
	}

	private String _getClassOrVariableName(DetailAST methodCallDetailAST) {
		DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.DOT);

		if (dotDetailAST == null) {
			return null;
		}

		DetailAST firstChildDetailAST = dotDetailAST.getFirstChild();

		FullIdent fullIdent = null;

		if (firstChildDetailAST.getType() == TokenTypes.LITERAL_NEW) {
			fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST.getFirstChild());
		}
		else {
			fullIdent = FullIdent.createFullIdent(dotDetailAST);
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if ((firstChildDetailAST != null) &&
			(firstChildDetailAST.getType() == TokenTypes.DOT)) {

			return fullIdent.getText();
		}

		String s = fullIdent.getText();

		int x = s.lastIndexOf(CharPool.PERIOD);

		if (x == -1) {
			return s;
		}

		return s.substring(0, x);
	}

	private static final String _MSG_AVOID_METHOD_CALL = "method.call.avoid";

}