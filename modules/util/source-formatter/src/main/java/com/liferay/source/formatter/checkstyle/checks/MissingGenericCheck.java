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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Alan Huang
 */
public class MissingGenericCheck extends BaseCheck {

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

		firstChildDetailAST = firstChildDetailAST.getFirstChild();

		if (firstChildDetailAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST identDetailAST = firstChildDetailAST.getFirstChild();

		if (!(identDetailAST.getType() == TokenTypes.IDENT) ||
			!ArrayUtil.contains(_GENERIC_CLASSES, identDetailAST.getText())) {

			return;
		}

		DetailAST siblingDetailAST = identDetailAST.getNextSibling();

		if (siblingDetailAST.getType() == TokenTypes.LPAREN) {
			siblingDetailAST = siblingDetailAST.getNextSibling();

			if ((siblingDetailAST.getType() == TokenTypes.ELIST) &&
				(siblingDetailAST.getChildCount() == 0)) {

				log(detailAST, _MSG_MISSING_MODIFIER, identDetailAST.getText());
			}
		}
	}

	private static final String[] _GENERIC_CLASSES = {
		"ArrayList", "ConcurrentHashMap", "ConcurrentSkipListMap",
		"ConcurrentSkipListSet", "CopyOnWriteArraySet", "HashMap", "HashSet",
		"Hashtable", "IdentityHashMap", "LinkedHashMap", "LinkedHashSet",
		"LinkedList", "Stack", "TreeMap", "TreeSet", "Vector"
	};

	private static final String _MSG_MISSING_MODIFIER = "generic.missing";

}