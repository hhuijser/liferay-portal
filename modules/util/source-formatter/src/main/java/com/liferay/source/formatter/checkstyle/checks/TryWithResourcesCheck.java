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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class TryWithResourcesCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_TRY};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST literalFinallyDetailAST = detailAST.findFirstToken(
			TokenTypes.LITERAL_FINALLY);

		if (literalFinallyDetailAST != null) {
			_checkFinallyStatement(detailAST, literalFinallyDetailAST);
		}
	}

	private void _checkFinallyStatement(
		DetailAST literalTryDetailAST, DetailAST literalFinallyDetailAST) {

		DetailAST slistDetailAST = literalFinallyDetailAST.findFirstToken(
			TokenTypes.SLIST);

		List<DetailAST> methodCallDetailASTList = getAllChildTokens(
			slistDetailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			List<String> cleanUpVariableNames = _getCleanUpVariableNames(
				methodCallDetailAST);

			for (String cleanUpVariableName : cleanUpVariableNames) {
				if (_useTryWithResources(
						cleanUpVariableName, literalTryDetailAST)) {

					log(
						methodCallDetailAST, _MSG_USE_TRY_WITH_RESOURCES,
						cleanUpVariableName, "DataAccess.cleanUp");
				}
			}

			String closeVariableName = _getCloseVariableName(
				methodCallDetailAST, literalFinallyDetailAST);

			if (closeVariableName == null) {
				continue;
			}

			if ((closeVariableName != null) &&
				_useTryWithResources(closeVariableName, literalTryDetailAST)) {

				log(
					methodCallDetailAST, _MSG_USE_TRY_WITH_RESOURCES,
					closeVariableName, closeVariableName + ".close");
			}
		}
	}

	private List<String> _getCleanUpVariableNames(
		DetailAST methodCallDetailAST) {

		List<String> variableNames = new ArrayList<>();

		DetailAST dotDetailAST = methodCallDetailAST.getFirstChild();

		if (dotDetailAST.getType() != TokenTypes.DOT) {
			return variableNames;
		}

		DetailAST lastChildDetailAST = dotDetailAST.getLastChild();

		if (!Objects.equals(lastChildDetailAST.getText(), "cleanUp")) {
			return variableNames;
		}

		DetailAST firstChildDetailAST = dotDetailAST.getFirstChild();

		if (!Objects.equals(firstChildDetailAST.getText(), "DataAccess")) {
			return variableNames;
		}

		DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
			TokenTypes.ELIST);

		List<DetailAST> exprDetailASTList = getAllChildTokens(
			elistDetailAST, false, TokenTypes.EXPR);

		for (DetailAST exprDetailAST : exprDetailASTList) {
			firstChildDetailAST = exprDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() == TokenTypes.IDENT) {
				variableNames.add(firstChildDetailAST.getText());
			}
		}

		return variableNames;
	}

	private String _getCloseVariableName(
		DetailAST methodCallDetailAST, DetailAST literalFinallyDetailAST) {

		DetailAST dotDetailAST = methodCallDetailAST.getFirstChild();

		if (dotDetailAST.getType() != TokenTypes.DOT) {
			return null;
		}

		DetailAST lastChildDetailAST = dotDetailAST.getLastChild();

		if (!Objects.equals(lastChildDetailAST.getText(), "close")) {
			return null;
		}

		DetailAST firstChildDetailAST = dotDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return null;
		}

		DetailAST parentDetailAST = methodCallDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.EXPR) {
			return null;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.SLIST) {
			return null;
		}

		String variableName = firstChildDetailAST.getText();

		parentDetailAST = parentDetailAST.getParent();

		if (equals(parentDetailAST, literalFinallyDetailAST)) {
			return variableName;
		}

		if (parentDetailAST.getType() != TokenTypes.LITERAL_IF) {
			return null;
		}

		DetailAST exprDetailAST = parentDetailAST.findFirstToken(
			TokenTypes.EXPR);

		firstChildDetailAST = exprDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.NOT_EQUAL) {
			return null;
		}

		lastChildDetailAST = firstChildDetailAST.getLastChild();

		if (lastChildDetailAST.getType() != TokenTypes.LITERAL_NULL) {
			return null;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (!variableName.equals(firstChildDetailAST.getText())) {
			return null;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.SLIST) {
			return null;
		}

		parentDetailAST = parentDetailAST.getParent();

		if (equals(parentDetailAST, literalFinallyDetailAST)) {
			return variableName;
		}

		return null;
	}

	private boolean _useTryWithResources(
		String variableName, DetailAST literalTryDetailAST) {

		DetailAST typeDetailAST = getVariableTypeDetailAST(
			literalTryDetailAST, variableName, false);

		if (typeDetailAST == null) {
			return false;
		}

		DetailAST parentDetailAST = typeDetailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.VARIABLE_DEF) {
			return false;
		}

		int lineNumber = literalTryDetailAST.getLineNo();

		List<DetailAST> variableCallerDetailASTList =
			getVariableCallerDetailASTList(parentDetailAST, variableName);

		for (DetailAST variableCallerDetailAST : variableCallerDetailASTList) {
			if (variableCallerDetailAST.getLineNo() > lineNumber) {
				continue;
			}

			DetailAST callerLiteralTryDetailAST = getParentWithTokenType(
				variableCallerDetailAST, TokenTypes.LITERAL_TRY);

			if ((callerLiteralTryDetailAST == null) ||
				(callerLiteralTryDetailAST.getLineNo() > lineNumber)) {

				continue;
			}

			DetailAST literalCatchDetailAST =
				callerLiteralTryDetailAST.findFirstToken(
					TokenTypes.LITERAL_CATCH);

			if ((literalCatchDetailAST != null) &&
				(callerLiteralTryDetailAST.getLineNo() < lineNumber)) {

				return false;
			}
		}

		return true;
	}

	private static final String _MSG_USE_TRY_WITH_RESOURCES =
		"try.with.resources.use";

}