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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaAccessModifierCheck extends BaseJavaTermCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	public void setAllFileNames(List<String> allFileNames) {
		_allFileNames = allFileNames;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		JavaClass javaClass = (JavaClass)javaTerm;

		String packageName = javaClass.getPackageName();

		if (packageName == null) {
			return javaTerm.getContent();
		}

		if (packageName.contains(".internal.") ||
			packageName.endsWith(".internal")) {

			_checkProtectedMethods(fileName, javaClass);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkProtectedMethods(String fileName, JavaClass javaClass)
		throws Exception {

		Set<JavaClass> relatedJavaClasses = null;

		outerLoop:
		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod() || !childJavaTerm.isProtected() ||
				childJavaTerm.isAbstract() || childJavaTerm.hasAnnotation()) {

				continue;
			}

			String methodName = childJavaTerm.getName();

			if (methodName.startsWith("_")) {
				methodName = methodName.substring(1);
			}

			if (_hasBindMethod(javaClass, methodName)) {
				continue;
			}

			if (relatedJavaClasses == null) {
				relatedJavaClasses = _getRelatedJavaClasses(javaClass);
			}

			Pattern pattern = Pattern.compile(
				StringBundler.concat("([^\\w_])(", methodName, "[^\\w_])"));

			for (JavaClass relatedJavaClass : relatedJavaClasses) {
				Matcher matcher = pattern.matcher(
					relatedJavaClass.getContent());

				if (matcher.find()) {
					continue outerLoop;
				}
			}

			addMessage(
				fileName,
				StringBundler.concat(
					"Access modifier of method '", methodName,
					"' should be 'private'"),
				childJavaTerm.getLineNumber());
		}
	}

	private synchronized List<String> _getAllJavaFileNames() {
		if (_allJavaFileNames != null) {
			return _allJavaFileNames;
		}

		_allJavaFileNames = SourceFormatterUtil.filterFileNames(
			_allFileNames, new String[0], new String[] {"**/*.java"},
			getSourceFormatterExcludes(), false);

		return _allJavaFileNames;
	}

	private Set<JavaClass> _getExtendsJavaClasses(
		String fullyQualifiedName, List<JavaClass> internalPackageJavaClasses) {

		Set<JavaClass> extendsJavaClasses = new HashSet<>();

		for (JavaClass internalPackageJavaClass : internalPackageJavaClasses) {
			List<String> extendedClassNames =
				internalPackageJavaClass.getExtendedClassNames(true);

			for (JavaTerm javaTerm :
					internalPackageJavaClass.getChildJavaTerms()) {

				if (javaTerm.isJavaClass()) {
					JavaClass innerJavaClass = (JavaClass)javaTerm;

					extendedClassNames.addAll(
						innerJavaClass.getExtendedClassNames(true));
				}
			}

			for (String extendedClassName : extendedClassNames) {
				if (extendedClassName.equals(fullyQualifiedName)) {
					extendsJavaClasses.add(internalPackageJavaClass);

					extendsJavaClasses.addAll(
						_getExtendsJavaClasses(
							internalPackageJavaClass.getName(true),
							internalPackageJavaClasses));
				}
			}
		}

		return extendsJavaClasses;
	}

	private synchronized Set<JavaClass> _getRelatedJavaClasses(
			JavaClass javaClass)
		throws Exception {

		Set<JavaClass> relatedJavaClasses = new HashSet<>();

		String packageName = javaClass.getPackageName();

		String internalPackageName = null;

		if (packageName.endsWith(".internal")) {
			internalPackageName = packageName;
		}
		else {
			internalPackageName = packageName.substring(
				0, packageName.indexOf(".internal.") + 9);
		}

		List<JavaClass> internalPackageJavaClasses = _javaClassesMap.get(
			internalPackageName);

		if (internalPackageJavaClasses == null) {
			internalPackageJavaClasses = new ArrayList<>();

			String internalDirectoryName = StringUtil.replace(
				internalPackageName, '.', '/');

			List<String> internalPackageFileNames =
				SourceFormatterUtil.filterFileNames(
					_getAllJavaFileNames(), new String[0],
					new String[] {
						"**/" + internalDirectoryName + "/*.java",
						"**/" + internalDirectoryName + "/**/*.java"
					},
					getSourceFormatterExcludes(), false);

			for (String internalPackageFileName : internalPackageFileNames) {
				String content = FileUtil.read(
					new File(internalPackageFileName));

				try {
					internalPackageJavaClasses.add(
						JavaClassParser.parseJavaClass(
							internalPackageFileName, content));

					internalPackageJavaClasses.addAll(
						JavaClassParser.parseAnonymousClasses(content));
				}
				catch (Exception exception) {
				}
			}

			_javaClassesMap.put(
				internalPackageName, internalPackageJavaClasses);
		}

		for (JavaClass internalPackageJavaClass : internalPackageJavaClasses) {
			if (internalPackageJavaClass.isAnonymous()) {
				String anonymousJavaClassContent =
					internalPackageJavaClass.getContent();

				if (anonymousJavaClassContent.startsWith(
						StringBundler.concat(
							"new ", javaClass.getName(), "("))) {

					relatedJavaClasses.add(internalPackageJavaClass);
				}
			}
			else if (!Objects.equals(
						javaClass.getName(true),
						internalPackageJavaClass.getName(true)) &&
					 packageName.equals(
						 internalPackageJavaClass.getPackageName())) {

				relatedJavaClasses.add(internalPackageJavaClass);
			}
		}

		relatedJavaClasses.addAll(
			_getExtendsJavaClasses(
				javaClass.getName(true), internalPackageJavaClasses));

		return relatedJavaClasses;
	}

	private boolean _hasBindMethod(JavaClass javaClass, String methodName) {
		String javaClassContent = javaClass.getContent();

		if (javaClassContent.contains(
				StringBundler.concat("unbind = \"", methodName, "\""))) {

			return true;
		}

		String bindMethodName = null;

		if (methodName.startsWith("un")) {
			bindMethodName = methodName.substring(2);
		}
		else if (methodName.startsWith("remove")) {
			bindMethodName = StringUtil.replaceFirst(
				methodName, "remove", "add");
		}
		else {
			return false;
		}

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (childJavaTerm.isJavaMethod() &&
				childJavaTerm.hasAnnotation("Reference") &&
				bindMethodName.equals(childJavaTerm.getName())) {

				return true;
			}
		}

		return false;
	}

	private List<String> _allFileNames;
	private List<String> _allJavaFileNames;
	private final Map<String, List<JavaClass>> _javaClassesMap =
		new HashMap<>();

}