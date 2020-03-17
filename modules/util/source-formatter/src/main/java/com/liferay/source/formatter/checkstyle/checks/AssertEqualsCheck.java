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

import com.liferay.debug.SFDebugHelper;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class AssertEqualsCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_DEF, TokenTypes.LITERAL_FINALLY};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.LITERAL_FINALLY) {
			DetailAST slistDetailAST = detailAST.findFirstToken(
				TokenTypes.SLIST);

			List<DetailAST> exprDetailASTList = getAllChildTokens(
				slistDetailAST, false, TokenTypes.EXPR);

			for (DetailAST exprDetailAST : exprDetailASTList) {
				DetailAST firstChildDetailAST = exprDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
					continue;
				}

				firstChildDetailAST = firstChildDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() != TokenTypes.DOT) {
					continue;
				}

				DetailAST lastChildDetailAST =
					firstChildDetailAST.getLastChild();

				if (Objects.equals(lastChildDetailAST.getText(), "close")) {
					//log(lastChildDetailAST, "CLOSE");
				}
				else if (Objects.equals(lastChildDetailAST.getText(), "cleanUp")) {
					firstChildDetailAST = firstChildDetailAST.getFirstChild();

					if (Objects.equals(firstChildDetailAST.getText(), "DataAccess")) {
						log(firstChildDetailAST, "CLEANUP");
					}
				}
			}
		}

		List<DetailAST> list = getMethodCalls(
			detailAST, "DataAccess", "cleanUp");

		for (DetailAST da : list) {
			//log(da, "cleanUp");
		}

		List<DetailAST> methodCallDetailASTList = getMethodCalls(
			detailAST, "Assert", "assertEquals");

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			List<DetailAST> exprDetailASTList = getAllChildTokens(
				elistDetailAST, false, TokenTypes.EXPR);

			if (exprDetailASTList.size() != 2) {
				continue;
			}

			DetailAST secondExprDetailAST = exprDetailASTList.get(1);

			DetailAST firstChildDetailAST = secondExprDetailAST.getFirstChild();

			String variableName = _getVariableNameForMethodCall(
				firstChildDetailAST, "getLength");

			if (variableName != null) {
				DetailAST typeDetailAST = getVariableTypeDetailAST(
					methodCallDetailAST, variableName);

				if ((typeDetailAST != null) && _isHits(typeDetailAST)) {
					log(
						methodCallDetailAST, _MSG_ASSERT_ADD_INFORMATION,
						variableName + ".toString()");
				}

				continue;
			}

			variableName = _getVariableNameForCall(
				firstChildDetailAST, "length");

			if (variableName != null) {
				DetailAST typeDetailAST = getVariableTypeDetailAST(
					methodCallDetailAST, variableName);

				if ((typeDetailAST != null) && isArray(typeDetailAST)) {
					log(
						methodCallDetailAST, _MSG_ASSERT_ADD_INFORMATION,
						"Arrays.toString(" + variableName + ")");
				}

				continue;
			}

			variableName = _getVariableNameForMethodCall(
				firstChildDetailAST, "size");

			if (variableName != null) {
				DetailAST typeDetailAST = getVariableTypeDetailAST(
					methodCallDetailAST, variableName);

				if ((typeDetailAST != null) && isCollection(typeDetailAST)) {
					log(
						methodCallDetailAST, _MSG_ASSERT_ADD_INFORMATION,
						variableName + ".toString()");
				}
			}
		}
	}

	private String _getVariableNameForCall(
		DetailAST detailAST, String methodName) {

		if (detailAST.getType() != TokenTypes.DOT) {
			return null;
		}

		List<DetailAST> nameDetailASTList = getAllChildTokens(
			detailAST, false, TokenTypes.IDENT);

		if (nameDetailASTList.size() != 2) {
			return null;
		}

		DetailAST methodNameDetailAST = nameDetailASTList.get(1);

		if (!methodName.equals(methodNameDetailAST.getText())) {
			return null;
		}

		DetailAST variableNameDetailAST = nameDetailASTList.get(0);

		return variableNameDetailAST.getText();
	}

	private String _getVariableNameForMethodCall(
		DetailAST detailAST, String methodName) {

		if (detailAST.getType() != TokenTypes.METHOD_CALL) {
			return null;
		}

		DetailAST firstChildDetailAST = detailAST.getFirstChild();

		return _getVariableNameForCall(firstChildDetailAST, methodName);
	}

	private boolean _isHits(DetailAST detailAST) {
		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		String name = nameDetailAST.getText();

		if (name.equals("Hits")) {
			return true;
		}

		return false;
	}

	private static final String _MSG_ASSERT_ADD_INFORMATION =
		"assert.add.information";

}