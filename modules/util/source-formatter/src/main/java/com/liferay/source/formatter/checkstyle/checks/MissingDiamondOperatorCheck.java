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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Alan Huang
 */
public class MissingDiamondOperatorCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST typeDetailAST = detailAST.findFirstToken(TokenTypes.TYPE);

		if (typeDetailAST == null) {
			return;
		}

		DetailAST typeArgumentDetailAST = typeDetailAST.findFirstToken(
			TokenTypes.TYPE_ARGUMENTS);

		if (typeArgumentDetailAST == null) {
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

		DetailAST leteralNewDetailAST = firstChildDetailAST.getFirstChild();

		if (leteralNewDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST identDetailAST = leteralNewDetailAST.getFirstChild();

		if (!(identDetailAST.getType() == TokenTypes.IDENT) ||
			!ArrayUtil.contains(_GENERIC_CLASSES, identDetailAST.getText())) {

			return;
		}

		DetailAST siblingDetailAST = identDetailAST.getNextSibling();

		String types = DetailASTUtil.getTypeName(detailAST, true);

		if (siblingDetailAST.getType() != TokenTypes.TYPE_ARGUMENTS) {
			if (leteralNewDetailAST.findFirstToken(TokenTypes.OBJBLOCK) !=
					null) {

				log(
					detailAST, _MSG_MISSING_DIAMOND_TYPES_OPERATOR,
					types.substring(types.indexOf("<")),
					identDetailAST.getText());
			}
			else {
				log(
					detailAST, _MSG_MISSING_DIAMOND_OPERATOR,
					identDetailAST.getText());
			}
		}
	}

	private static final String[] _GENERIC_CLASSES = {
		"ArrayList", "ConcurrentHashMap", "ConcurrentSkipListMap",
		"ConcurrentSkipListSet", "CopyOnWriteArraySet", "EnumMap", "HashMap",
		"HashSet", "Hashtable", "IdentityHashMap", "LinkedHashMap",
		"LinkedHashSet", "LinkedList", "Stack", "TreeMap", "TreeSet", "Vector"
	};

	private static final String _MSG_MISSING_DIAMOND_OPERATOR =
		"diamond.operator.missing";

	private static final String _MSG_MISSING_DIAMOND_TYPES_OPERATOR =
		"diamond.operator.types.missing";

}