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

package com.liferay.source.formatter;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.ImportPackage;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Carlos Sierra Andrés
 * @author André de Oliveira
 * @author Raymond Augé
 * @author Hugo Huijser
 */
public class JavaImportsFormatter extends BaseImportsFormatter {

	public static String getImports(String content) {
		Matcher matcher = _importsPattern.matcher(content);

		if (matcher.find()) {
			String imports = matcher.group();

			if (imports.endsWith("\n\n")) {
				imports = imports.substring(0, imports.length() - 1);
			}

			return imports;
		}

		return null;
	}

	@Override
	protected ImportPackage createImportPackage(String line) {
		return createJavaImportPackage(line);
	}

	@Override
	protected String doFormat(
			String content, Pattern importPattern, String packagePath,
			String className)
		throws IOException {

		String imports = getImports(content);

		if (Validator.isNull(imports)) {
			return content;
		}

		String newImports = stripUnusedImports(
			imports, content, packagePath, className, "\\*");

		newImports = sortAndGroupImports(newImports);

		if (!imports.equals(newImports)) {
			content = StringUtil.replaceFirst(content, imports, newImports);
		}

		content = _stripFullyQualifiedClassNames(
			content, newImports, packagePath);

		return content.replaceFirst(
			"(?m)^[ \t]*(package .*;)\\s*^[ \t]*import", "$1\n\nimport");
	}

	private String _getAfterImportsContent(
		String imports, String afterImportsContent, String packagePath) {

		Pattern pattern1 = Pattern.compile(
			"\n(.*)" + StringUtil.replace(packagePath, CharPool.PERIOD, "\\.") +
				"\\.([A-Z]\\w+)\\W");

		outerLoop:
		while (true) {
			Matcher matcher1 = pattern1.matcher(afterImportsContent);

			while (matcher1.find()) {
				String lineStart = StringUtil.trimLeading(matcher1.group(1));

				if (lineStart.contains("//") ||
					SourceUtil.isInsideQuotes(
						afterImportsContent, matcher1.start(2))) {

					continue;
				}

				String className = matcher1.group(2);

				Pattern pattern2 = Pattern.compile(
					"import [\\w.]+\\." + className + ";");

				Matcher matcher2 = pattern2.matcher(imports);

				if (matcher2.find()) {
					continue;
				}

				afterImportsContent = StringUtil.replaceFirst(
					afterImportsContent, packagePath + ".", StringPool.BLANK,
					matcher1.start());

				continue outerLoop;
			}

			break;
		}

		return afterImportsContent;
	}

	private String _stripFullyQualifiedClassNames(
			String content, String imports, String packagePath)
		throws IOException {

		if (Validator.isNull(content) || Validator.isNull(imports)) {
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

		afterImportsContent = _getAfterImportsContent(
			imports, afterImportsContent, packagePath);
		afterImportsContent = _getAfterImportsContent(
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

					if (SourceUtil.isInsideQuotes(s, x - y)) {
						continue;
					}

					s = StringUtil.trim(s);

					if (s.startsWith("//")) {
						continue;
					}

					String importClassName =
						importPackageAndClassName.substring(
							importPackageAndClassName.lastIndexOf(
								StringPool.PERIOD) + 1);

					afterImportsContent = StringUtil.replaceFirst(
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

	private static final Pattern _importsPattern = Pattern.compile(
		"(^[ \t]*import\\s+.*;\n+)+", Pattern.MULTILINE);

}