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

package com.liferay.source.formatter.regex;

/**
 * @author Hugo Huijser
 */
public class Pattern {

	public static final int CANON_EQ = java.util.regex.Pattern.CANON_EQ;

	public static final int CASE_INSENSITIVE =
		java.util.regex.Pattern.CASE_INSENSITIVE;

	public static final int COMMENTS = java.util.regex.Pattern.COMMENTS;

	public static final int DOTALL = java.util.regex.Pattern.DOTALL;

	public static final int LITERAL = java.util.regex.Pattern.LITERAL;

	public static final int MULTILINE = java.util.regex.Pattern.MULTILINE;

	public static final int UNICODE_CASE = java.util.regex.Pattern.UNICODE_CASE;

	public static final int UNICODE_CHARACTER_CLASS =
		java.util.regex.Pattern.UNICODE_CHARACTER_CLASS;

	public static final int UNIX_LINES = java.util.regex.Pattern.UNIX_LINES;

	public static Pattern compile(String regex) {
		return new Pattern(java.util.regex.Pattern.compile(regex));
	}

	public static Pattern compile(String regex, int flags) {
		return new Pattern(java.util.regex.Pattern.compile(regex, flags));
	}

	public Pattern(java.util.regex.Pattern pattern) {
		_pattern = pattern;
	}

	public Matcher matcher(CharSequence input) {
		return new Matcher(_pattern.matcher(input));
	}

	public boolean matches(String regex, CharSequence input) {
		return java.util.regex.Pattern.matches(regex, input);
	}

	private final java.util.regex.Pattern _pattern;

}