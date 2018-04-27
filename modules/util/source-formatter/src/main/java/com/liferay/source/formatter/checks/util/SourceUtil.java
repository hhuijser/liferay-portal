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

package com.liferay.source.formatter.checks.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * @author Hugo Huijser
 */
public class SourceUtil {

	public static String getAbsolutePath(File file) {
		return getAbsolutePath(file.toPath());
	}

	public static String getAbsolutePath(Path filePath) {
		filePath = filePath.toAbsolutePath();

		filePath = filePath.normalize();

		return StringUtil.replace(
			filePath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

	public static String getAbsolutePath(String fileName) {
		return getAbsolutePath(Paths.get(fileName));
	}

	public static String getIndent(String s) {
		StringBundler sb = new StringBundler(s.length());

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != CharPool.TAB) {
				break;
			}

			sb.append(CharPool.TAB);
		}

		return sb.toString();
	}

	public static int getLevel(String s) {
		return getLevel(
			s, new String[] {StringPool.OPEN_PARENTHESIS},
			new String[] {StringPool.CLOSE_PARENTHESIS}, 0);
	}

	public static int getLevel(
		String s, String increaseLevelString, String decreaseLevelString) {

		return getLevel(
			s, new String[] {increaseLevelString},
			new String[] {decreaseLevelString}, 0);
	}

	public static int getLevel(
		String s, String[] increaseLevelStrings,
		String[] decreaseLevelStrings) {

		return getLevel(s, increaseLevelStrings, decreaseLevelStrings, 0);
	}

	public static int getLevel(
		String s, String[] increaseLevelStrings, String[] decreaseLevelStrings,
		int startLevel) {

		int level = startLevel;

		for (String increaseLevelString : increaseLevelStrings) {
			level = _adjustLevel(level, s, increaseLevelString, 1);
		}

		for (String decreaseLevelString : decreaseLevelStrings) {
			level = _adjustLevel(level, s, decreaseLevelString, -1);
		}

		return level;
	}

	public static String getTitleCase(String s, String[] exceptions) {
		String[] words = s.split("\\s+");

		if (ArrayUtil.isEmpty(words)) {
			return s;
		}

		StringBundler sb = new StringBundler(words.length * 2);

		outerLoop:
		for (int i = 0; i < words.length; i++) {
			String word = words[i];

			if (Validator.isNull(word)) {
				continue;
			}

			for (String exception : exceptions) {
				if (StringUtil.equalsIgnoreCase(exception, word)) {
					sb.append(exception);
					sb.append(CharPool.SPACE);

					continue outerLoop;
				}
			}

			if ((i != 0) && (i != words.length)) {
				String lowerCaseWord = StringUtil.toLowerCase(word);

				if (ArrayUtil.contains(_ARTICLES, lowerCaseWord) ||
					ArrayUtil.contains(_CONJUNCTIONS, lowerCaseWord) ||
					ArrayUtil.contains(_PREPOSITIONS, lowerCaseWord)) {

					sb.append(lowerCaseWord);
					sb.append(CharPool.SPACE);

					continue;
				}
			}

			if (Character.isUpperCase(word.charAt(0))) {
				sb.append(word);
			}
			else {
				sb.append(StringUtil.upperCaseFirstLetter(word));
			}

			sb.append(CharPool.SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	public static boolean isInsideQuotes(String s, int pos) {
		return isInsideQuotes(s, pos, true);
	}

	public static boolean isInsideQuotes(
		String s, int pos, boolean allowEscapedQuotes) {

		int start = s.lastIndexOf(CharPool.NEW_LINE, pos);

		if (start == -1) {
			start = 0;
		}

		int end = s.indexOf(CharPool.NEW_LINE, pos);

		if (end == -1) {
			end = s.length();
		}

		String line = s.substring(start, end);

		pos -= start;

		char delimeter = CharPool.SPACE;
		boolean insideQuotes = false;

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (insideQuotes) {
				if (c == delimeter) {
					if (!allowEscapedQuotes) {
						insideQuotes = false;
					}
					else {
						int precedingBackSlashCount = 0;

						for (int j = i - 1; j >= 0; j--) {
							if (line.charAt(j) == CharPool.BACK_SLASH) {
								precedingBackSlashCount += 1;
							}
							else {
								break;
							}
						}

						if ((precedingBackSlashCount == 0) ||
							((precedingBackSlashCount % 2) == 0)) {

							insideQuotes = false;
						}
					}
				}
			}
			else if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
				delimeter = c;
				insideQuotes = true;
			}

			if (pos == i) {
				return insideQuotes;
			}
		}

		return false;
	}

	public static Document readXML(File file) throws Exception {
		SAXReader saxReader = _getSAXReader();

		return saxReader.read(file);
	}

	public static Document readXML(String content) throws Exception {
		SAXReader saxReader = _getSAXReader();

		return saxReader.read(new UnsyncStringReader(content));
	}

	private static int _adjustLevel(
		int level, String text, String s, int diff) {

		String[] lines = StringUtil.splitLines(text);

		forLoop:
		for (String line : lines) {
			line = StringUtil.trim(line);

			if (line.startsWith("//") || line.startsWith("*")) {
				continue;
			}

			int x = -1;

			while (true) {
				x = line.indexOf(s, x + 1);

				if (x == -1) {
					continue forLoop;
				}

				if (!isInsideQuotes(line, x)) {
					level += diff;
				}
			}
		}

		return level;
	}

	private static SAXReader _getSAXReader() throws Exception {
		SAXReader saxReader = new SAXReader(false);

		saxReader.setFeature(
			"http://apache.org/xml/features/disallow-doctype-decl", false);
		saxReader.setFeature(
			"http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
			false);
		saxReader.setFeature(
			"http://apache.org/xml/features/nonvalidating/load-external-dtd",
			false);

		return saxReader;
	}

	private static final String[] _ARTICLES = {"a", "an", "the"};

	private static final String[] _CONJUNCTIONS =
		{"and", "but", "for", "nor", "or", "yet"};

	private static final String[] _PREPOSITIONS = {
		"a", "abaft", "aboard", "about", "above", "absent", "across", "afore",
		"after", "against", "along", "alongside", "amid", "amidst", "among",
		"amongst", "an", "apropos", "apud", "around", "as", "aside", "astride",
		"at", "athwart", "atop", "barring", "before", "behind", "below",
		"beneath", "beside", "besides", "between", "beyond", "but", "by",
		"circa", "concerning", "despite", "down", "during", "except",
		"excluding", "failing", "for", "from", "given", "in", "including",
		"inside", "into", "lest", "mid", "midst", "modulo", "near", "next",
		"notwithstanding", "of", "off", "on", "onto", "opposite", "out",
		"outside", "over", "pace", "past", "per", "plus", "pro", "qua",
		"regarding", "sans", "since", "through", "throughout", "thru",
		"thruout", "till", "to", "toward", "towards", "under", "underneath",
		"unlike", "until", "unto", "up", "upon", "v", "versus", "via", "vice",
		"vs", "with", "within", "without", "worth"
	};

}