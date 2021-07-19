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

import com.liferay.petra.string.StringPool;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Alan Huangs
 */
public class RedundantIfStatementCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_IF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if (parentDetailAST.getType() == TokenTypes.LITERAL_ELSE) {
			return;
		}

		DetailAST nextSiblingDetailAST = detailAST.getNextSibling();

		if ((nextSiblingDetailAST.getType() != TokenTypes.LITERAL_IF) ||
			(_getClosingCurlyBraceLineNumber(detailAST) == -1) ||
			(_getClosingCurlyBraceLineNumber(nextSiblingDetailAST) == -1)) {

			return;
		}

		DetailAST lastChildDetailAST1 = detailAST.getLastChild();
		DetailAST lastChildDetailAST2 = nextSiblingDetailAST.getLastChild();

		if (!_isSameExpressions(
				lastChildDetailAST1.getFirstChild(),
				lastChildDetailAST2.getFirstChild())) {

			return;
		}

		log(
			detailAST, _MSG_COMBINE_IF_STATEMENTS, detailAST.getLineNo(),
			nextSiblingDetailAST.getLineNo());
	}

	private int _getClosingCurlyBraceLineNumber(DetailAST literalIfDetailAST) {
		DetailAST lastChildDetailAST = literalIfDetailAST.getLastChild();

		if (lastChildDetailAST.getType() != TokenTypes.SLIST) {
			return -1;
		}

		lastChildDetailAST = lastChildDetailAST.getLastChild();

		if (lastChildDetailAST.getType() == TokenTypes.RCURLY) {
			return lastChildDetailAST.getLineNo();
		}

		return -1;
	}

	private boolean _isSameExpressions(
		DetailAST detailAST1, DetailAST detailAST2) {

		int endLineNumber1 = getEndLineNumber(detailAST1);
		int startLineNumber1 = getStartLineNumber(detailAST1);

		int endLineNumber2 = getEndLineNumber(detailAST2);
		int startLineNumber2 = getStartLineNumber(detailAST2);

		if ((endLineNumber1 - startLineNumber1) !=
				(endLineNumber2 - startLineNumber2)) {

			return false;
		}

		String line1 = StringPool.BLANK;
		String line2 = StringPool.BLANK;

		for (int i = startLineNumber1; i <= endLineNumber1; i++) {
			line1 = getLine(startLineNumber1);
			line2 = getLine(startLineNumber2);

			if (!line1.equals(line2)) {
				return false;
			}
		}

		return true;
	}

	private static final String _MSG_COMBINE_IF_STATEMENTS =
		"if.statements.combine";

}