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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.PythonSourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alan Huang
 */
public class PythonStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _sortItems(fileName, content, StringPool.BLANK);
	}

	private List<String> _combineAnnotationsAndComments(
		List<String> statements, String indent) {

		List<String> statementsList = new ArrayList<>();

		StringBundler sb = new StringBundler();

		String previousStatement = StringPool.BLANK;

		for (String statement : statements) {
			if (statement.startsWith(indent + StringPool.AT) ||
				statement.startsWith(indent + StringPool.POUND)) {

				sb.append(statement);
				sb.append("\n");
			}
			else if (previousStatement.startsWith(indent + StringPool.AT) ||
					 previousStatement.startsWith(indent + StringPool.POUND)) {

				sb.append(statement);

				statementsList.add(sb.toString());

				sb.setIndex(0);
			}
			else {
				statementsList.add(statement);
			}

			previousStatement = statement;
		}

		if (sb.index() > 0) {
			statementsList.add(StringUtil.trimTrailing(sb.toString()));
		}

		return statementsList;
	}

	private int _sortItems(String statement1, String statement2) {
		String trimmedStatement1 = StringUtil.trimLeading(
			statement1.replaceAll("(\t*[#@].*(\\Z|\n))*(.*)", "$3"));
		String trimmedStatement2 = StringUtil.trimLeading(
			statement2.replaceAll("(\t*[#@].*(\\Z|\n))*(.*)", "$3"));

		if (Validator.isNull(trimmedStatement1) ||
			Validator.isNull(trimmedStatement2)) {

			return 0;
		}

		if (trimmedStatement1.startsWith("def ") &&
			trimmedStatement2.startsWith("def ")) {

			return _sortMethods(trimmedStatement1, trimmedStatement2);
		}

		return 0;
	}

	private String _sortItems(String fileName, String content, String indent) {
		List<String> statements = PythonSourceUtil.getPythonStatements(
			content, indent);

		statements = _combineAnnotationsAndComments(statements, indent);

		List<String> oldStatements = new ArrayList<>(statements);

		Collections.sort(
			statements,
			new Comparator<String>() {

				@Override
				public int compare(String statement1, String statement2) {
					return _sortItems(statement1, statement2);
				}

			});

		if (!oldStatements.equals(statements)) {
			StringBundler sb = new StringBundler(statements.size() * 2);

			for (String statement : statements) {
				sb.append(statement);
				sb.append("\n");
			}

			sb.setIndex(sb.index() - 1);

			String[] lines = content.split("\n");

			if (!indent.equals("")) {
				content = lines[0] + "\n" + sb.toString();
			}
			else {
				content = sb.toString();
			}
		}

		statements = PythonSourceUtil.getPythonStatements(content, indent);

		for (String statement : statements) {
			String nestedStatementIndent =
				PythonSourceUtil.getNestedStatementIndent(statement);

			if (!nestedStatementIndent.equals(StringPool.BLANK)) {
				content = StringUtil.replaceFirst(
					content, statement,
					_sortItems(fileName, statement, nestedStatementIndent));
			}
		}

		return content;
	}

	private int _sortMethods(String method1, String method2) {
		String[] lines1 = method1.split("\n", 2);

		String methodName1 = lines1[0].replaceFirst("def (\\w+).*", "$1");

		String[] lines2 = method2.split("\n", 2);

		String methodName2 = lines2[0].replaceFirst("def (\\w+).*", "$1");

		return methodName1.compareTo(methodName2);
	}

}