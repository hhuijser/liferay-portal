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

package com.liferay.petra.tooling;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.io.unsync.UnsyncStringReader;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.tooling.java.imports.formatter.JavaImportsFormatter;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Alexander Chow
 * @author Harry Mark
 * @author Tariq Dweik
 * @author Glenn Powell
 * @author Raymond Augé
 * @author Prashant Dighe
 * @author Shuyang Zhou
 * @author James Lefeu
 * @author Miguel Pastor
 * @author Cody Hoag
 * @author James Hinkey
 * @author Hugo Huijser
 */
public class ToolingUtil {

	public static final int PLUGINS_MAX_DIR_LEVEL = 3;

	public static final int PORTAL_MAX_DIR_LEVEL = 7;

	public static int count(String s, char c) {
		if ((s == null) || s.isEmpty() || (s.length() < 1)) {
			return 0;
		}

		int count = 0;
		int pos = 0;

		while ((pos < s.length()) && ((pos = s.indexOf(c, pos)) != -1)) {
			if (pos < s.length()) {
				count++;
			}

			pos++;
		}

		return count;
	}

	public static String getPackagePath(File file) {
		String fileName = file.toString();

		fileName = fileName.replace(CharPool.BACK_SLASH, CharPool.SLASH);

		return getPackagePath(fileName);
	}

	public static String getPackagePath(String fileName) {
		int x = Math.max(
			fileName.lastIndexOf("/com/"), fileName.lastIndexOf("/org/"));
		int y = fileName.lastIndexOf(CharPool.SLASH);

		String packagePath = fileName.substring(x + 1, y);

		return packagePath.replace(CharPool.SLASH, CharPool.PERIOD);
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

	public static String stripFullyQualifiedClassNames(
			String content, String packagePath)
		throws IOException {

		String imports = JavaImportsFormatter.getImports(content);

		return stripFullyQualifiedClassNames(content, imports, packagePath);
	}

	public static String stripFullyQualifiedClassNames(
			String content, String imports, String packagePath)
		throws IOException {

		if ((content == null) || content.isEmpty() || (imports == null) ||
			imports.isEmpty()) {

			return content;
		}

		String afterImportsContent = null;

		int pos = content.indexOf(imports);

		if (pos == -1) {
			afterImportsContent = content;
		}
		else {
			pos += imports.length();

			afterImportsContent = content.substring(pos);
		}

		afterImportsContent = _stripFullyQualifiedClassNames(
			imports, afterImportsContent, packagePath);
		afterImportsContent = _stripFullyQualifiedClassNames(
			imports, afterImportsContent, "java.lang");

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(imports))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				int x = line.indexOf("import ");

				if (x == -1) {
					continue;
				}

				String importPackageAndClassName = line.substring(
					x + 7, line.lastIndexOf(StringPool.SEMICOLON));

				if (importPackageAndClassName.contains(StringPool.STAR)) {
					continue;
				}

				while (true) {
					x = afterImportsContent.indexOf(
						importPackageAndClassName, x + 1);

					if (x == -1) {
						break;
					}

					char nextChar = afterImportsContent.charAt(
						x + importPackageAndClassName.length());

					if (Character.isLetterOrDigit(nextChar)) {
						continue;
					}

					int y = afterImportsContent.lastIndexOf(
						CharPool.NEW_LINE, x);

					if (y == -1) {
						y = 0;
					}

					String s = afterImportsContent.substring(y, x + 1);

					if (isInsideQuotes(s, x - y)) {
						continue;
					}

					s = s.trim();

					if (s.startsWith("//")) {
						continue;
					}

					String importClassName =
						importPackageAndClassName.substring(
							importPackageAndClassName.lastIndexOf(
								StringPool.PERIOD) + 1);

					afterImportsContent = _replaceFirst(
						afterImportsContent, importPackageAndClassName,
						importClassName, x);
				}
			}

			if (pos == -1) {
				return afterImportsContent;
			}

			return content.substring(0, pos) + afterImportsContent;
		}
	}

	private static String _replaceFirst(
		String s, String oldSub, String newSub, int fromIndex) {

		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		if (oldSub.equals(newSub)) {
			return s;
		}

		int y = s.indexOf(oldSub, fromIndex);

		if (y >= 0) {
			return s.substring(0, y).concat(newSub).concat(
				s.substring(y + oldSub.length()));
		}
		else {
			return s;
		}
	}

	private static String _stripFullyQualifiedClassNames(
		String imports, String afterImportsContent, String packagePath) {

		Pattern pattern1 = Pattern.compile(
			"\n(.*)" + packagePath.replace(StringPool.PERIOD, "\\.") +
				"\\.([A-Z]\\w+)\\W");

		outerLoop:
		while (true) {
			Matcher matcher1 = pattern1.matcher(afterImportsContent);

			while (matcher1.find()) {
				String value = matcher1.group(1);

				if (value.contains("//") ||
					isInsideQuotes(afterImportsContent, matcher1.start(2))) {

					continue;
				}

				String className = matcher1.group(2);

				Pattern pattern2 = Pattern.compile(
					"import [\\w.]+\\." + className + ";");

				Matcher matcher2 = pattern2.matcher(imports);

				if (matcher2.find()) {
					continue;
				}

				afterImportsContent = _replaceFirst(
					afterImportsContent, packagePath + ".", StringPool.BLANK,
					matcher1.start());

				continue outerLoop;
			}

			break;
		}

		return afterImportsContent;
	}

}