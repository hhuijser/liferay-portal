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

package com.liferay.portal.tools.java.parser.util.comparator;

import com.liferay.portal.tools.java.parser.JavaSimpleValue;

import java.util.Comparator;

/**
 * @author Hugo Huijser
 */
public class ModifierComparator implements Comparator<JavaSimpleValue> {

	@Override
	public int compare(JavaSimpleValue modifier1, JavaSimpleValue modifier2) {
		return _getIndex(modifier1) - _getIndex(modifier2);
	}

	private int _getIndex(JavaSimpleValue modifier) {
		String name = modifier.getName();

		if (name.equals("public")) {
			return 1;
		}

		if (name.equals("protected")) {
			return 2;
		}

		if (name.equals("private")) {
			return 3;
		}

		if (name.equals("abstract")) {
			return 4;
		}

		if (name.equals("default")) {
			return 5;
		}

		if (name.equals("static")) {
			return 6;
		}

		if (name.equals("final")) {
			return 7;
		}

		if (name.equals("transient")) {
			return 8;
		}

		if (name.equals("volatile")) {
			return 9;
		}

		if (name.equals("synchronized")) {
			return 10;
		}

		if (name.equals("native")) {
			return 11;
		}

		return 12;
	}

}