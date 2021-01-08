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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.BaseImportsFormatter;
import com.liferay.portal.tools.ImportPackage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class PythonImportsFormatter extends BaseImportsFormatter {

	protected static List<String> getImportsList(
		String content, Pattern importPattern) {

		List<String> importsList = new ArrayList<>();

		Matcher matcher = importPattern.matcher(content);

		while (matcher.find()) {
			importsList.add(matcher.group());
		}

		return importsList;
	}

	@Override
	protected ImportPackage createImportPackage(String line) {
		return _createPythonImportPackage(line);
	}

	@Override
	protected String doFormat(
			String content, Pattern importPattern, String packageDir,
			String className)
		throws IOException {

		List<String> importsList = getImportsList(content, importPattern);

		for (String imports : importsList) {
			content = doFormat(content, packageDir, className, imports);
		}

		return content;
	}

	protected String doFormat(
			String content, String packageDir, String className, String imports)
		throws IOException {

		if (Validator.isNull(imports)) {
			return content;
		}

		String newImports = _mergeImports(imports);

		newImports = sortAndGroupImports(newImports);

		newImports = _splitImports(newImports);

		if (!imports.equals(newImports)) {
			content = StringUtil.replaceFirst(content, imports, newImports);
		}

		return content;
	}

	private ImportPackage _createPythonImportPackage(String line) {
		Matcher matcher = _importPattern.matcher(line);

		if (!matcher.find()) {
			return null;
		}

		String packageName = matcher.group(2);

		return new ImportPackage(packageName, false, line);
	}

	private String _mergeImports(String imports) {
		imports = imports.replaceAll("\\\\\n", StringPool.BLANK);

		Map<String, List<String>> packageImportsMap = new TreeMap<>();

		Matcher matcher = _importPattern.matcher(imports);

		List<String> importNamesList = new ArrayList<>();

		String importNames = StringPool.BLANK;
		String indent = StringPool.BLANK;
		String packageName = StringPool.BLANK;

		while (matcher.find()) {
			importNames = matcher.group(3);
			indent = matcher.group(1);
			packageName = matcher.group(2);

			for (String importName : importNames.split(StringPool.COMMA)) {
				importNamesList.add(importName.trim());
			}

			if (packageImportsMap.containsKey(packageName)) {
				importNamesList.addAll(packageImportsMap.get(packageName));
			}

			packageImportsMap.put(packageName, importNamesList);

			importNamesList = new ArrayList<>();
		}

		if (MapUtil.isEmpty(packageImportsMap)) {
			return imports;
		}

		StringBundler sb = new StringBundler();

		for (Map.Entry<String, List<String>> entry :
				packageImportsMap.entrySet()) {

			sb.append(indent);
			sb.append("from ");
			sb.append(entry.getKey());
			sb.append(" import ");

			importNamesList = entry.getValue();

			Collections.sort(importNamesList);

			sb.append(
				ListUtil.toString(
					importNamesList, StringPool.BLANK,
					StringPool.COMMA_AND_SPACE));

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private String _splitImports(String imports) {
		String[] newImports = imports.split("\n");

		StringBundler sb = new StringBundler((newImports.length * 2) + 1);

		for (String newImport : newImports) {
			String indent = newImport.replaceFirst("(\t*).*", "$1");

			sb.append(newImport.replaceAll(", ", ", \\\\\n\t" + indent));

			sb.append(StringPool.NEW_LINE);
		}

		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private static final Pattern _importPattern = Pattern.compile(
		"([ \t]*)from (.*) import (.*)");

}