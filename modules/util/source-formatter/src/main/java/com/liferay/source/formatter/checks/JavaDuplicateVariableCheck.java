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

import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class JavaDuplicateVariableCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.isAnonymous() ||
			(javaClass.getParentJavaClass() != null)) {

			return javaTerm.getContent();
		}

		List<String> extendedClassNames = javaClass.getExtendedClassNames(true);

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if ((childJavaTerm instanceof JavaVariable) &&
				!childJavaTerm.hasAnnotation("Deprecated") &&
				(childJavaTerm.isPublic() || childJavaTerm.isProtected())) {

				_checkDuplicateVariable(
					fileName, absolutePath, extendedClassNames,
					childJavaTerm.getName(), javaClass.getPackageName());
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkDuplicateVariable(
			String fileName, String absolutePath,
			List<String> extendedClassNames, String variableName,
			String packageName)
		throws IOException {

		for (String extendedClassName : extendedClassNames) {
			List<String> extendedVariableNames = _getVariableNames(
				absolutePath, extendedClassName);

			if (extendedVariableNames.contains(variableName) ||
				extendedVariableNames.contains(
					packageName + "." + variableName)) {

				addMessage(fileName, variableName);
			}
		}
	}

	private synchronized Map<String, String> _getBundleSymbolicNamesMap(
		String absolutePath) {

		if (_bundleSymbolicNamesMap != null) {
			return _bundleSymbolicNamesMap;
		}

		_bundleSymbolicNamesMap = BNDSourceUtil.getBundleSymbolicNamesMap(
			_getRootDirName(absolutePath));

		return _bundleSymbolicNamesMap;
	}

	private synchronized String _getRootDirName(String absolutePath) {
		if (_rootDirName != null) {
			return _rootDirName;
		}

		_rootDirName = SourceUtil.getRootDirName(absolutePath);

		return _rootDirName;
	}

	private List<String> _getVariableNames(
			String absolutePath, JavaClass javaClass)
		throws IOException {

		List<String> variableNames = new ArrayList<>();

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!(javaTerm instanceof JavaVariable)) {
				continue;
			}

			if (javaTerm.isPublic()) {
				variableNames.add(javaTerm.getName());
			}
			else if (javaTerm.isProtected()) {
				variableNames.add(
					javaClass.getPackageName() + "." + javaTerm.getName());
			}
		}

		List<String> extendedClassNames = javaClass.getExtendedClassNames(true);

		for (String extendedClassName : extendedClassNames) {
			variableNames.addAll(
				_getVariableNames(absolutePath, extendedClassName));
		}

		return variableNames;
	}

	private List<String> _getVariableNames(
			String absolutePath, String fullyQualifiedClassName)
		throws IOException {

		if (_variableNamesMap.containsKey(fullyQualifiedClassName)) {
			return _variableNamesMap.get(fullyQualifiedClassName);
		}

		List<String> variableNames = new ArrayList<>();

		File file = JavaSourceUtil.getJavaFile(
			fullyQualifiedClassName, _getRootDirName(absolutePath),
			_getBundleSymbolicNamesMap(absolutePath));

		if (file != null) {
			try {
				JavaClass javaClass = JavaClassParser.parseJavaClass(
					SourceUtil.getAbsolutePath(file), FileUtil.read(file));

				variableNames.addAll(
					_getVariableNames(absolutePath, javaClass));
			}
			catch (Exception exception) {
			}
		}

		_variableNamesMap.put(fullyQualifiedClassName, variableNames);

		return variableNames;
	}

	private Map<String, String> _bundleSymbolicNamesMap;
	private String _rootDirName;
	private final Map<String, List<String>> _variableNamesMap = new HashMap<>();

}