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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class DeprecatedAPICheck extends BaseAPICheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {
			TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF
		};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST parentDetailAST = detailAST.getParent();

		if ((parentDetailAST != null) ||
			AnnotationUtil.containsAnnotation(detailAST, "Deprecated")) {

			return;
		}

		try {
			JSONObject javaClassesJSONObject = _getJavaClassesJSONObject();

			List<String> deprecatedImportNames = _getDeprecatedImportNames(
				detailAST, javaClassesJSONObject);

			_checkDeprecatedConstructors(
				detailAST, deprecatedImportNames, javaClassesJSONObject);
			_checkDeprecatedMethods(
				detailAST, deprecatedImportNames, javaClassesJSONObject);
			_checkDeprecatedTypes(
				detailAST, deprecatedImportNames, javaClassesJSONObject);
			_checkDeprecatedVariables(
				detailAST, deprecatedImportNames, javaClassesJSONObject);
		}
		catch (Exception exception) {
		}
	}

	private void _checkDeprecatedConstructors(
		DetailAST detailAST, List<String> deprecatedImportNames,
		JSONObject javaClassesJSONObject) {

		List<ConstructorCall> constructorCalls = getConstructorCalls(
			detailAST, deprecatedImportNames, true);

		outerLoop:
		for (ConstructorCall constructorCall : constructorCalls) {
			List<JSONObject> constructorJSONObjects = getConstructorJSONObjects(
				constructorCall, javaClassesJSONObject);

			if (constructorJSONObjects.isEmpty()) {
				continue;
			}

			for (JSONObject constructorJSONObject : constructorJSONObjects) {
				if (!constructorJSONObject.has("deprecated")) {
					continue outerLoop;
				}
			}

			log(
				constructorCall.getLineNumber(),
				_MSG_DEPRECATED_CONSTRUCTOR_CALL,
				constructorCall.getTypeName());
		}
	}

	private void _checkDeprecatedMethods(
		DetailAST detailAST, List<String> deprecatedImportNames,
		JSONObject javaClassesJSONObject) {

		List<MethodCall> methodCalls = getMethodCalls(
			detailAST, deprecatedImportNames, true);

		outerLoop:
		for (MethodCall methodCall : methodCalls) {
			List<JSONObject> methodJSONObjects = getMethodJSONObjects(
				methodCall, javaClassesJSONObject);

			if (methodJSONObjects.isEmpty()) {
				continue;
			}

			for (JSONObject methodJSONObject : methodJSONObjects) {
				if (!methodJSONObject.has("deprecated")) {
					continue outerLoop;
				}
			}

			log(
				methodCall.getLineNumber(), _MSG_DEPRECATED_METHOD_CALL,
				methodCall.getName());
		}
	}

	private void _checkDeprecatedTypes(
		DetailAST detailAST, List<String> deprecatedImportNames,
		JSONObject javaClassesJSONObject) {

		Map<String, Set<Integer>> typeNamesMap = getTypeNamesMap(
			detailAST, deprecatedImportNames, true);

		for (Map.Entry<String, Set<Integer>> entry : typeNamesMap.entrySet()) {
			String typeName = entry.getKey();

			JSONObject classJSONObject = javaClassesJSONObject.getJSONObject(
				typeName);

			if ((classJSONObject != null) &&
				classJSONObject.has("deprecated")) {

				Set<Integer> lineNumbers = entry.getValue();

				for (int lineNumber : lineNumbers) {
					log(lineNumber, _MSG_DEPRECATED_TYPE_CALL, typeName);
				}
			}
		}
	}

	private void _checkDeprecatedVariables(
		DetailAST detailAST, List<String> deprecatedImportNames,
		JSONObject javaClassesJSONObject) {

		List<VariableCall> variableCalls = getVariableCalls(
			detailAST, deprecatedImportNames, true);

		for (VariableCall variableCall : variableCalls) {
			JSONObject variableJSONObject = getVariableJSONObject(
				variableCall, javaClassesJSONObject);

			if ((variableJSONObject != null) &&
				variableJSONObject.has("deprecated")) {

				log(
					variableCall.getLineNumber(), _MSG_DEPRECATED_FIELD_CALL,
					variableCall.getName());
			}
		}
	}

	private List<String> _getDeprecatedImportNames(
		DetailAST detailAST, JSONObject javaClassesJSONObject) {

		List<String> deprecatedImportNames = new ArrayList<>();

		List<String> importNames = getImportNames(detailAST);

		for (String importName : importNames) {
			JSONObject classJSONObject = javaClassesJSONObject.getJSONObject(
				importName);

			if ((classJSONObject != null) &&
				classJSONObject.has("deprecated")) {

				log(detailAST, _MSG_DEPRECATED_TYPE_CALL, importName);

				deprecatedImportNames.add(importName);
			}
		}

		return deprecatedImportNames;
	}

	private synchronized JSONObject _getJavaClassesJSONObject()
		throws Exception {

		if (_javaClassesJSONObject != null) {
			return _javaClassesJSONObject;
		}

		JSONObject portalJSONObject = SourceFormatterUtil.getPortalJSONObject(
			getBaseDirName());

		_javaClassesJSONObject = portalJSONObject.getJSONObject("javaClasses");

		return _javaClassesJSONObject;
	}

	private static final String _MSG_DEPRECATED_CONSTRUCTOR_CALL =
		"constructor.call.deprecated";

	private static final String _MSG_DEPRECATED_FIELD_CALL =
		"field.call.deprecated";

	private static final String _MSG_DEPRECATED_METHOD_CALL =
		"method.call.deprecated";

	private static final String _MSG_DEPRECATED_TYPE_CALL =
		"type.call.deprecated";

	private JSONObject _javaClassesJSONObject;

}