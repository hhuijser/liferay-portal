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

import com.liferay.petra.tooling.ToolingUtil;

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

		if ((imports == null) || imports.isEmpty()) {
			return content;
		}

		String newImports = stripUnusedImports(
			imports, content, packagePath, className, "\\*");

		newImports = sortAndGroupImports(newImports);

		if (!imports.equals(newImports)) {
			content = content.replaceFirst(imports, newImports);
		}

		content = ToolingUtil.stripFullyQualifiedClassNames(
			content, newImports, packagePath);

		return content.replaceFirst(
			"(?m)^[ \t]*(package .*;)\\s*^[ \t]*import", "$1\n\nimport");
	}

	private static final Pattern _importsPattern = Pattern.compile(
		"(^[ \t]*import\\s+.*;\n+)+", Pattern.MULTILINE);

}