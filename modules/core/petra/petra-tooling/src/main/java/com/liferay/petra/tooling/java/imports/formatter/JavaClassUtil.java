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

package com.liferay.petra.tooling.java.imports.formatter;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.tooling.ToolingUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public class JavaClassUtil {

	public static Set<String> getClasses(File file) throws IOException {
		String fileName = file.getName();

		if (fileName.endsWith(".java")) {
			fileName = fileName.substring(0, fileName.length() - 5);
		}

		return getClasses(
			new UnsyncBufferedReader(new FileReader(file)), fileName);
	}

	public static Set<String> getClasses(Reader reader, String className)
		throws IOException {

		Set<String> classes = new HashSet<>();

		StreamTokenizer st = new StreamTokenizer(reader);

		_setupParseTableForAnnotationProcessing(st);

		while (st.nextToken() != StreamTokenizer.TT_EOF) {
			if (st.ttype == StreamTokenizer.TT_WORD) {
				if (st.sval.equals("class") || st.sval.equals("enum") ||
					st.sval.equals("interface") ||
					st.sval.equals("@interface")) {

					break;
				}
				else if (st.sval.startsWith("@")) {
					st.ordinaryChar(' ');
					st.wordChars('=', '=');
					st.wordChars('+', '+');

					String[] annotationClasses = _processAnnotation(
						st.sval, st);

					for (String annotationClass : annotationClasses) {
						classes.add(annotationClass);
					}

					_setupParseTableForAnnotationProcessing(st);
				}
			}
		}

		_setupParseTable(st);

		while (st.nextToken() != StreamTokenizer.TT_EOF) {
			if (st.ttype == StreamTokenizer.TT_WORD) {
				Matcher matcher = _fullyQualifiedNamePattern.matcher(st.sval);

				if (matcher.find()) {
					continue;
				}

				int firstIndex = st.sval.indexOf('.');

				if (firstIndex >= 0) {
					classes.add(st.sval.substring(0, firstIndex));
				}

				int lastIndex = st.sval.lastIndexOf('.');

				if (lastIndex >= 0) {
					classes.add(st.sval.substring(lastIndex + 1));
				}

				if ((firstIndex < 0) && (lastIndex < 0)) {
					classes.add(st.sval);
				}
			}
			else if ((st.ttype != StreamTokenizer.TT_NUMBER) &&
					 (st.ttype != StreamTokenizer.TT_EOL)) {

				if (Character.isUpperCase((char)st.ttype)) {
					classes.add(String.valueOf((char)st.ttype));
				}
			}
		}

		classes.remove(className);

		return classes;
	}

	private static String[] _processAnnotation(String s, StreamTokenizer st)
		throws IOException {

		s = s.trim();

		List<String> tokens = new ArrayList<>();

		Matcher annotationNameMatcher = _annotationNamePattern.matcher(s);
		Matcher annotationParametersMatcher =
			_annotationParametersPattern.matcher(s);

		if (annotationNameMatcher.matches()) {
			tokens.add(annotationNameMatcher.group(1));
		}
		else if (annotationParametersMatcher.matches()) {
			tokens.add(annotationParametersMatcher.group(1));

			String annotationParameters = StringPool.BLANK;

			String trimmedString = s.trim();

			if (trimmedString.endsWith(")")) {
				annotationParameters = annotationParametersMatcher.group(3);
			}
			else {
				int pos = s.indexOf('{');

				if (pos != -1) {
					annotationParameters += s.substring(pos + 1);
				}

				while (st.nextToken() != StreamTokenizer.TT_EOF) {
					if (st.ttype != StreamTokenizer.TT_WORD) {
						continue;
					}

					annotationParameters += st.sval;

					String stringValue = st.sval;

					String trimmedValue = stringValue.trim();

					if (!trimmedValue.endsWith(")")) {
						continue;
					}

					int closeParenthesesCount = ToolingUtil.count(
						annotationParameters, ')');
					int openParenthesesCount = ToolingUtil.count(
						annotationParameters, '(');

					if (closeParenthesesCount > openParenthesesCount) {
						break;
					}
				}
			}

			tokens = _processAnnotationParameters(annotationParameters, tokens);
		}

		return tokens.toArray(new String[tokens.size()]);
	}

	private static List<String> _processAnnotationParameters(
			String s, List<String> tokens)
		throws IOException {

		StreamTokenizer st = new StreamTokenizer(new UnsyncStringReader(s));

		_setupParseTable(st);

		while (st.nextToken() != StreamTokenizer.TT_EOF) {
			if (st.ttype == StreamTokenizer.TT_WORD) {
				if (st.sval.indexOf('.') >= 0) {
					tokens.add(st.sval.substring(0, st.sval.indexOf('.')));
				}
				else {
					tokens.add(st.sval);
				}
			}
			else if ((st.ttype != StreamTokenizer.TT_NUMBER) &&
					 (st.ttype != StreamTokenizer.TT_EOL)) {

				if (Character.isUpperCase((char)st.ttype)) {
					tokens.add(String.valueOf((char)st.ttype));
				}
			}
		}

		return tokens;
	}

	private static void _setupParseTable(StreamTokenizer st) {
		st.resetSyntax();
		st.slashSlashComments(true);
		st.slashStarComments(true);
		st.wordChars('a', 'z');
		st.wordChars('A', 'Z');
		st.wordChars('.', '.');
		st.wordChars('0', '9');
		st.wordChars('_', '_');
		st.lowerCaseMode(false);
		st.eolIsSignificant(false);
		st.quoteChar('"');
		st.quoteChar('\'');
		st.parseNumbers();
	}

	private static void _setupParseTableForAnnotationProcessing(
		StreamTokenizer st) {

		_setupParseTable(st);

		st.wordChars('@', '@');
		st.wordChars('(', '(');
		st.wordChars(')', ')');
		st.wordChars('{', '{');
		st.wordChars('}', '}');
		st.wordChars(',', ',');
	}

	private static final Pattern _annotationNamePattern = Pattern.compile(
		"@(\\w+)\\.?(\\w*)$");
	private static final Pattern _annotationParametersPattern = Pattern.compile(
		"@(\\w+)\\.?(\\w*)\\({0,1}\\{{0,1}([^)}]+)\\}{0,1}\\){0,1}");
	private static final Pattern _fullyQualifiedNamePattern = Pattern.compile(
		"^([a-z]\\w*\\.){2,}([A-Z]\\w*)(\\.|\\Z)");

}