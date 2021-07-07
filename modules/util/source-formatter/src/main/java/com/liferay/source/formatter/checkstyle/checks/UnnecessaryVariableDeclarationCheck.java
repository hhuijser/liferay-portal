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
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class UnnecessaryVariableDeclarationCheck
	extends BaseUnnecessaryStatementCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (modifiersDetailAST.branchContains(TokenTypes.ANNOTATION)) {
			return;
		}

		DetailAST semiDetailAST = detailAST.getNextSibling();

		if ((semiDetailAST == null) ||
			(semiDetailAST.getType() != TokenTypes.SEMI)) {

			return;
		}

		String variableName = nameDetailAST.getText();

		_checkUnnecessaryListVariableDeclarationBeforeReturn(
			detailAST, semiDetailAST, variableName,
			_MSG_UNNECESSARY_LIST_VARIABLE_DECLARATION_BEFORE_RETURN);

		checkUnnecessaryStatementBeforeReturn(
			detailAST, semiDetailAST, variableName,
			_MSG_UNNECESSARY_VARIABLE_DECLARATION_BEFORE_RETURN);

		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() != TokenTypes.SLIST) {
			return;
		}

		checkUnnecessaryToString(
			detailAST.findFirstToken(TokenTypes.ASSIGN),
			_MSG_UNNECESSARY_VARIABLE_DECLARATION_TO_STRING);

		List<DetailAST> variableCallerDetailASTList =
			getVariableCallerDetailASTList(detailAST, variableName);

		if (variableCallerDetailASTList.isEmpty()) {
			return;
		}

		DetailAST firstVariableCallerDetailAST =
			variableCallerDetailASTList.get(0);

		DetailAST secondVariableCallerDetailAST = null;

		if (variableCallerDetailASTList.size() > 1) {
			secondVariableCallerDetailAST = variableCallerDetailASTList.get(1);
		}

		checkUnnecessaryStatementBeforeReassign(
			detailAST, firstVariableCallerDetailAST,
			secondVariableCallerDetailAST, parentDetailAST, variableName,
			_MSG_UNNECESSARY_VARIABLE_DECLARATION_BEFORE_REASSIGN);
	}

	private void _checkUnnecessaryListVariableDeclarationBeforeReturn(
		DetailAST detailAST, DetailAST semiDetailAST, String variableName,
		String messageKey) {

		String variableTypeName = getVariableTypeName(
			detailAST, variableName, false);

		if (!variableTypeName.equals("List") ||
			!_isAssignNewArrayList(detailAST)) {

			return;
		}

		DetailAST nextSiblingDetailAST = semiDetailAST.getNextSibling();

		if (nextSiblingDetailAST == null) {
			return;
		}

		while (nextSiblingDetailAST.getType() == TokenTypes.EXPR) {
			DetailAST firstChildDetailAST =
				nextSiblingDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.METHOD_CALL) {
				return;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.DOT) {
				return;
			}

			FullIdent fullIdent = FullIdent.createFullIdent(
				firstChildDetailAST);

			String fullyQualifiedName = fullIdent.getText();

			if (!fullyQualifiedName.equals(variableName + ".add")) {
				return;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if ((nextSiblingDetailAST != null) &&
				(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

				return;
			}

			nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

			if (nextSiblingDetailAST == null) {
				return;
			}

			if (nextSiblingDetailAST.getType() == TokenTypes.EXPR) {
				continue;
			}

			if (nextSiblingDetailAST.getType() == TokenTypes.LITERAL_RETURN) {
				firstChildDetailAST = nextSiblingDetailAST.getFirstChild();

				if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
					return;
				}

				firstChildDetailAST = firstChildDetailAST.getFirstChild();

				if ((firstChildDetailAST.getType() == TokenTypes.IDENT) &&
					variableName.equals(firstChildDetailAST.getText())) {

					log(detailAST, messageKey, variableName);
				}
			}

			return;
		}
	}

	private boolean _isAssignNewArrayList(DetailAST detailAST) {
		DetailAST assignDetailAST = detailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return false;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return false;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return false;
		}

		DetailAST identDetailAST = firstChildDetailAST.getFirstChild();

		if ((identDetailAST.getType() != TokenTypes.IDENT) ||
			!Objects.equals(identDetailAST.getText(), "ArrayList")) {

			return false;
		}

		DetailAST elistDetailAST = firstChildDetailAST.findFirstToken(
			TokenTypes.ELIST);

		if (elistDetailAST == null) {
			return false;
		}

		firstChildDetailAST = elistDetailAST.getFirstChild();

		if (firstChildDetailAST == null) {
			return true;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() == TokenTypes.NUM_INT) {
			return true;
		}

		return false;
	}

	private static final String
		_MSG_UNNECESSARY_LIST_VARIABLE_DECLARATION_BEFORE_RETURN =
			"list.variable.declaration.unnecessary.before.return";

	private static final String
		_MSG_UNNECESSARY_VARIABLE_DECLARATION_BEFORE_REASSIGN =
			"variable.declaration.unnecessary.before.reassign";

	private static final String
		_MSG_UNNECESSARY_VARIABLE_DECLARATION_BEFORE_RETURN =
			"variable.declaration.unnecessary.before.return";

	private static final String
		_MSG_UNNECESSARY_VARIABLE_DECLARATION_TO_STRING =
			"variable.declaration.unnecessary.to.string";

}