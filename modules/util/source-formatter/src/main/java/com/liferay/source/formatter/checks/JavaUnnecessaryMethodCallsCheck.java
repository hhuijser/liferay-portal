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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alan Huang
 */
public class JavaUnnecessaryMethodCallsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws CheckstyleException, IOException {

		FileText fileText = new FileText(
			new File(fileName), CheckstyleUtil.getLines(content));

		FileContents fileContents = new FileContents(fileText);

		DetailAST rootDetailAST = JavaParser.parse(fileContents);

		DetailAST nextSiblingDetailAST = rootDetailAST.getNextSibling();

		Map<String, String> methodReturnsMap = new HashMap<>();

		while (true) {
			if (nextSiblingDetailAST == null) {
				return content;
			}

			if (nextSiblingDetailAST.getType() != TokenTypes.CLASS_DEF) {
				nextSiblingDetailAST = nextSiblingDetailAST.getNextSibling();

				continue;
			}

			methodReturnsMap = _getMethodReturnsMap(nextSiblingDetailAST);

			if (!methodReturnsMap.isEmpty()) {
				content = _replaceMethods(
					content, methodReturnsMap, nextSiblingDetailAST);
			}

			methodReturnsMap.clear();

			break;
		}

		List<DetailAST> classDefinitionDetailASTList =
			DetailASTUtil.getAllChildTokens(
				nextSiblingDetailAST, true, TokenTypes.CLASS_DEF);

		for (DetailAST classDefinitionDetailAST :
				classDefinitionDetailASTList) {

			methodReturnsMap = _getMethodReturnsMap(classDefinitionDetailAST);

			if (methodReturnsMap.isEmpty()) {
				continue;
			}

			content = _replaceMethods(
				content, methodReturnsMap, classDefinitionDetailAST);

			methodReturnsMap.clear();
		}

		return content;
	}

	private String _getMethodName(DetailAST detailAST) {
		DetailAST nameDetailAST = detailAST.findFirstToken(TokenTypes.IDENT);

		return nameDetailAST.getText();
	}

	private Map<String, String> _getMethodReturnsMap(DetailAST classDetailAST) {
		Map<String, String> methodReturnsMap = new HashMap<>();

		DetailAST objBlockDetailAST = classDetailAST.findFirstToken(
			TokenTypes.OBJBLOCK);

		if (objBlockDetailAST == null) {
			return methodReturnsMap;
		}

		List<DetailAST> methodDefinitionDetailASTList =
			DetailASTUtil.getAllChildTokens(
				objBlockDetailAST, false, TokenTypes.METHOD_DEF);

		for (DetailAST methodDefinitionDetailAST :
				methodDefinitionDetailASTList) {

			List<DetailAST> parameterDefs = _getParameterDefs(
				methodDefinitionDetailAST);

			if (!parameterDefs.isEmpty()) {
				continue;
			}

			DetailAST slistDetailAST = methodDefinitionDetailAST.findFirstToken(
				TokenTypes.SLIST);

			if (slistDetailAST == null) {
				continue;
			}

			DetailAST firstChildDetailAST = slistDetailAST.getFirstChild();

			if ((firstChildDetailAST == null) ||
				(firstChildDetailAST.getType() != TokenTypes.LITERAL_RETURN)) {

				continue;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.EXPR) {
				continue;
			}

			DetailAST nextSiblingDetailAST =
				firstChildDetailAST.getNextSibling();

			if ((nextSiblingDetailAST == null) ||
				(nextSiblingDetailAST.getType() != TokenTypes.SEMI)) {

				continue;
			}

			firstChildDetailAST = firstChildDetailAST.getFirstChild();

			if (firstChildDetailAST.getType() != TokenTypes.IDENT) {
				continue;
			}

			methodReturnsMap.put(
				_getMethodName(methodDefinitionDetailAST),
				firstChildDetailAST.getText());
		}

		return methodReturnsMap;
	}

	private List<DetailAST> _getParameterDefs(DetailAST detailAST) {
		if ((detailAST.getType() != TokenTypes.CTOR_DEF) &&
			(detailAST.getType() != TokenTypes.METHOD_DEF)) {

			return new ArrayList<>();
		}

		DetailAST parametersDetailAST = detailAST.findFirstToken(
			TokenTypes.PARAMETERS);

		return DetailASTUtil.getAllChildTokens(
			parametersDetailAST, false, TokenTypes.PARAMETER_DEF);
	}

	private String _getReplacement(
		DetailAST methodCallDetailAST, String methodReturn) {

		DetailAST previousDetailAST = methodCallDetailAST.getParent();

		while ((previousDetailAST.getType() != TokenTypes.METHOD_DEF) &&
			   (previousDetailAST.getType() != TokenTypes.CTOR_DEF)) {

			previousDetailAST = previousDetailAST.getParent();
		}

		List<DetailAST> variableDefDetailASTList =
			DetailASTUtil.getAllChildTokens(
				previousDetailAST, true, TokenTypes.VARIABLE_DEF);

		for (DetailAST variableDefDetailAST : variableDefDetailASTList) {
			DetailAST nameDetailAST = variableDefDetailAST.findFirstToken(
				TokenTypes.IDENT);

			if (Objects.equals(nameDetailAST.getText(), methodReturn)) {
				DetailAST modifiersDetailAST = previousDetailAST.findFirstToken(
					TokenTypes.MODIFIERS);

				if (modifiersDetailAST.branchContains(
						TokenTypes.LITERAL_STATIC)) {

					return "";
				}

				if (variableDefDetailAST.getLineNo() <=
						methodCallDetailAST.getLineNo()) {

					return "this." + methodReturn;
				}

				return methodReturn;
			}
		}

		return methodReturn;
	}

	private String _replaceMethods(
		String content, Map<String, String> methodReturnsMap,
		DetailAST classDetailAST) {

		List<DetailAST> methodCallDetailASTList =
			DetailASTUtil.getAllChildTokens(
				classDetailAST, true, TokenTypes.METHOD_CALL);

		for (DetailAST methodCallDetailAST : methodCallDetailASTList) {
			DetailAST dotDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.DOT);

			if (dotDetailAST != null) {
				continue;
			}

			DetailAST elistDetailAST = methodCallDetailAST.findFirstToken(
				TokenTypes.ELIST);

			List<DetailAST> exprDetailASTList = DetailASTUtil.getAllChildTokens(
				elistDetailAST, false, TokenTypes.EXPR);

			if (!exprDetailASTList.isEmpty()) {
				continue;
			}

			String methodName = _getMethodName(methodCallDetailAST);

			if (methodReturnsMap.containsKey(methodName)) {
				DetailAST parentDetailAST = methodCallDetailAST.getParent();

				while (parentDetailAST.getType() != TokenTypes.CLASS_DEF) {
					parentDetailAST = parentDetailAST.getParent();
				}

				if (parentDetailAST.equals(classDetailAST)) {
					String replacement = _getReplacement(
						methodCallDetailAST, methodReturnsMap.get(methodName));

					if (Validator.isNull(replacement)) {
						continue;
					}

					return StringUtil.replaceFirst(
						content, methodName + "()", replacement,
						getLineStartPos(
							content, methodCallDetailAST.getLineNo()));
				}

				break;
			}
		}

		return content;
	}

}