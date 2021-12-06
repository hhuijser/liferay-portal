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

import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public class UpgradeRemovedAPICheck extends BaseAPICheck {

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

		String upgradeFromVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_FROM_VERSION);
		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION);

		try {
			JSONObject upgradeFromJavaClassesJSONObject =
				_getJavaClassesJSONObject(upgradeFromVersion);
			JSONObject upgradeToJavaClassesJSONObject =
				_getJavaClassesJSONObject(upgradeToVersion);

			List<String> removedImportNames = _getRemovedImportNames(
				detailAST, upgradeFromJavaClassesJSONObject,
				upgradeToJavaClassesJSONObject, upgradeToVersion);

			_checkRemovedMethods(
				detailAST, removedImportNames, upgradeFromJavaClassesJSONObject,
				upgradeToJavaClassesJSONObject, upgradeToVersion);
			_checkRemovedTypes(
				detailAST, removedImportNames, upgradeFromJavaClassesJSONObject,
				upgradeToJavaClassesJSONObject, upgradeToVersion);
			_checkRemovedVariables(
				detailAST, removedImportNames, upgradeFromJavaClassesJSONObject,
				upgradeToJavaClassesJSONObject, upgradeToVersion);
		}
		catch (Exception exception) {
		}
	}

	private void _checkRemovedMethods(
		DetailAST detailAST, List<String> removedImportNames,
		JSONObject upgradeFromJavaClassesJSONObject,
		JSONObject upgradeToJavaClassesJSONObject, String upgradeToVersion) {

		List<MethodCall> methodCalls = getMethodCalls(
			detailAST, removedImportNames);

		for (MethodCall methodCall : methodCalls) {
			List<JSONObject> upgradeFromMethodJSONObjects =
				getMethodJSONObjects(
					methodCall, upgradeFromJavaClassesJSONObject);

			if (upgradeFromMethodJSONObjects.isEmpty()) {
				continue;
			}

			JSONObject classJSONObject =
				upgradeToJavaClassesJSONObject.getJSONObject(
					methodCall.getVariableTypeName());

			if (classJSONObject == null) {
				log(
					methodCall.getLineNumber(), _MSG_CLASS_NOT_FOUND,
					methodCall.getVariableTypeName(), upgradeToVersion);
			}
			else {
				List<JSONObject> upgradeToMethodJSONObjects =
					getMethodJSONObjects(
						methodCall, upgradeFromJavaClassesJSONObject);

				if (upgradeToMethodJSONObjects.isEmpty()) {
					log(
						methodCall.getLineNumber(), _MSG_METHOD_NOT_FOUND,
						methodCall.getName(), methodCall.getVariableTypeName(),
						upgradeToVersion);
				}
			}
		}
	}

	private void _checkRemovedTypes(
		DetailAST detailAST, List<String> removedImportNames,
		JSONObject upgradeFromJavaClassesJSONObject,
		JSONObject upgradeToJavaClassesJSONObject, String upgradeToVersion) {

		Map<String, Set<Integer>> typeNamesMap = getTypeNamesMap(
			detailAST, removedImportNames);

		for (Map.Entry<String, Set<Integer>> entry : typeNamesMap.entrySet()) {
			String typeName = entry.getKey();

			JSONObject upgradeFromClassJSONObject =
				upgradeFromJavaClassesJSONObject.getJSONObject(typeName);
			JSONObject upgradeToClassJSONObject =
				upgradeToJavaClassesJSONObject.getJSONObject(typeName);

			if ((upgradeFromClassJSONObject != null) &&
				(upgradeToClassJSONObject == null)) {

				Set<Integer> lineNumbers = entry.getValue();

				for (int lineNumber : lineNumbers) {
					log(
						lineNumber, _MSG_CLASS_NOT_FOUND, typeName,
						upgradeToVersion);
				}
			}
		}
	}

	private void _checkRemovedVariables(
		DetailAST detailAST, List<String> removedImportNames,
		JSONObject upgradeFromJavaClassesJSONObject,
		JSONObject upgradeToJavaClassesJSONObject, String upgradeToVersion) {

		List<VariableCall> variableCalls = getVariableCalls(
			detailAST, removedImportNames);

		for (VariableCall variableCall : variableCalls) {
			JSONObject upgradeFromVariableJSONObject = getVariableJSONObject(
				variableCall, upgradeFromJavaClassesJSONObject);

			if (upgradeFromVariableJSONObject == null) {
				continue;
			}

			JSONObject classJSONObject =
				upgradeToJavaClassesJSONObject.getJSONObject(
					variableCall.getTypeName());

			if (classJSONObject == null) {
				log(
					variableCall.getLineNumber(), _MSG_CLASS_NOT_FOUND,
					variableCall.getTypeName(), upgradeToVersion);
			}
			else {
				JSONObject upgradeToVariableJSONObject = getVariableJSONObject(
					variableCall, upgradeToJavaClassesJSONObject);

				if (upgradeToVariableJSONObject == null) {
					log(
						variableCall.getLineNumber(), _MSG_VARIABLE_NOT_FOUND,
						variableCall.getName(), variableCall.getTypeName(),
						upgradeToVersion);
				}
			}
		}
	}

	private synchronized JSONObject _getJavaClassesJSONObject(String version)
		throws Exception {

		JSONObject javaClassesJSONObject = _javaClassesJSONObjectMap.get(
			version);

		if (javaClassesJSONObject != null) {
			return javaClassesJSONObject;
		}

		JSONObject portalJSONObject =
			SourceFormatterUtil.getPortalJSONObjectByVersion(version);

		if (portalJSONObject.has("javaClasses")) {
			javaClassesJSONObject = portalJSONObject.getJSONObject(
				"javaClasses");
		}
		else {
			javaClassesJSONObject = new JSONObjectImpl();
		}

		_javaClassesJSONObjectMap.put(version, javaClassesJSONObject);

		return javaClassesJSONObject;
	}

	private List<String> _getRemovedImportNames(
		DetailAST detailAST, JSONObject upgradeFromJavaClassesJSONObject,
		JSONObject upgradeToJavaClassesJSONObject, String upgradeToVersion) {

		List<String> removedImportNames = new ArrayList<>();

		List<String> importNames = getImportNames(detailAST);

		for (String importName : importNames) {
			JSONObject upgradeFromClassJSONObject =
				upgradeFromJavaClassesJSONObject.getJSONObject(importName);
			JSONObject upgradeToClassJSONObject =
				upgradeToJavaClassesJSONObject.getJSONObject(importName);

			if ((upgradeFromClassJSONObject != null) &&
				(upgradeToClassJSONObject == null)) {

				log(
					detailAST, _MSG_CLASS_NOT_FOUND, importName,
					upgradeToVersion);

				removedImportNames.add(importName);
			}
		}

		return removedImportNames;
	}

	private static final String _MSG_CLASS_NOT_FOUND = "class.not.found";

	private static final String _MSG_METHOD_NOT_FOUND = "method.not.found";

	private static final String _MSG_VARIABLE_NOT_FOUND = "variable.not.found";

	private final Map<String, JSONObject> _javaClassesJSONObjectMap =
		new HashMap<>();

}