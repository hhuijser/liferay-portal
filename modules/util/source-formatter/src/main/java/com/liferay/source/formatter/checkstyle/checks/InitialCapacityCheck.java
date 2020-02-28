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

import java.util.List;

/**
 * @author Alan Huang
 */
public class InitialCapacityCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST modifiersDetailAST = detailAST.findFirstToken(
			TokenTypes.MODIFIERS);

		if (modifiersDetailAST.branchContains(TokenTypes.FINAL)) {
			return;
		}

		DetailAST assignDetailAST = detailAST.findFirstToken(TokenTypes.ASSIGN);

		if (assignDetailAST == null) {
			return;
		}

		DetailAST firstChildDetailAST = assignDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
			return;
		}

		DetailAST literalNewDetailAST = firstChildDetailAST.getFirstChild();

		if (literalNewDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		firstChildDetailAST = literalNewDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
			return;
		}

		List<String> initialCapacityClassNames = getAttributeValues(
			_INITIAL_CAPACITY_CLASS_NAMES);

		String className = firstChildDetailAST.getText();

		if (!initialCapacityClassNames.contains(className)) {
			return;
		}

		DetailAST lparenDetailAST = literalNewDetailAST.findFirstToken(
			TokenTypes.LPAREN);

		if (lparenDetailAST == null) {
			return;
		}

		DetailAST siblingDetailAST = lparenDetailAST.getNextSibling();

		if (siblingDetailAST.getType() != TokenTypes.ELIST) {
			return;
		}

		firstChildDetailAST = siblingDetailAST.getFirstChild();

		if ((firstChildDetailAST == null) ||
			(firstChildDetailAST.getType() != TokenTypes.EXPR)) {

			return;
		}

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.NUM_INT) {
			return;
		}

		log(detailAST, _MSG_INITIAL_CAPACITY_INCORRECT, className);
	}

	private static final String _INITIAL_CAPACITY_CLASS_NAMES =
		"initialCapacityClassNames";

	private static final String _MSG_INITIAL_CAPACITY_INCORRECT =
		"initial.capacity.incorrect";

}