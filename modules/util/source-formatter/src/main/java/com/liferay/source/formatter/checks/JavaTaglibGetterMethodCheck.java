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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checks.util.TaglibUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class JavaTaglibGetterMethodCheck extends BaseJavaTermCheck {

	@Override
	public boolean isPortalCheck() {
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

		String className = javaClass.getName();

		if (!className.endsWith("Tag")) {
			return javaTerm.getContent();
		}

		List<JavaClass> extendedTagJavaClasses = _getExtendedTagJavaClasses(
			javaClass, absolutePath);

		if (!_containsClass(
				extendedTagJavaClasses, "com.liferay.taglib.util.IncludeTag")) {

			return javaTerm.getContent();
		}

		if (extendedTagJavaClasses == null) {
			return javaTerm.getContent();
		}

		List<String> tagAttributeNames = _getTagAttributeNames(
			javaClass.getPackageName(), javaClass.getName());

		if (tagAttributeNames == null) {
			return javaTerm.getContent();
		}

		List<JavaMethod> javaMethods = _getJavaMethods(
			javaClass, extendedTagJavaClasses);

		for (String tagAttributeName : tagAttributeNames) {
			JavaMethod setterJavaMethod = _getJavaMethod(
				javaMethods, "set" + tagAttributeName);

			if (setterJavaMethod == null) {
				addMessage(
					fileName,
					"Missing setter for attribute '" + tagAttributeName + "'");

				continue;
			}

			JavaMethod getterJavaMethod = _getJavaMethod(
				javaMethods, "get" + tagAttributeName);

			if (getterJavaMethod != null) {
				continue;
			}

			JavaClass setterMethodJavaClass =
				setterJavaMethod.getParentJavaClass();

			if (!Objects.equals(
					javaClass.getName(true),
					setterMethodJavaClass.getName(true))) {

				addMessage(
					fileName,
					"Missing getter for attribute '" + tagAttributeName + "'");

				continue;
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private boolean _containsClass(
		List<JavaClass> javaClasses, String className) {

		for (JavaClass javaClass : javaClasses) {
			if (className.equals(javaClass.getName(true))) {
				return true;
			}
		}

		return false;
	}

	private synchronized List<JavaClass> _getExtendedTagJavaClasses(
			JavaClass javaClass, String absolutePath)
		throws Exception {

		List<JavaClass> extendedTagJavaClasses = _extendedTagJavaClassesMap.get(
			absolutePath);

		if (extendedTagJavaClasses != null) {
			return extendedTagJavaClasses;
		}

		return _getExtendedTagJavaClasses(
			javaClass, absolutePath, _getUtilTaglibSrcDirName());
	}

	private List<JavaClass> _getExtendedTagJavaClasses(
			JavaClass javaClass, String absolutePath,
			String utilTaglibSrcDirName)
		throws Exception {

		List<String> extendedTagFileNames = TaglibUtil.getExtendedTagFileNames(
			javaClass, absolutePath, utilTaglibSrcDirName);

		if (extendedTagFileNames.isEmpty()) {
			_extendedTagJavaClassesMap.put(
				absolutePath, Collections.emptyList());

			return Collections.emptyList();
		}

		List<JavaClass> extendedTagJavaClasses = new ArrayList<>();

		for (String extendedTagFileName : extendedTagFileNames) {
			File extendedTagFile = new File(extendedTagFileName);

			if (!extendedTagFile.exists()) {
				continue;
			}

			String extendedTagContent = FileUtil.read(extendedTagFile);

			JavaClass extendedTagJavaClass = JavaClassParser.parseJavaClass(
				extendedTagFileName, extendedTagContent);

			extendedTagJavaClasses.add(extendedTagJavaClass);

			extendedTagJavaClasses.addAll(
				_getExtendedTagJavaClasses(
					extendedTagJavaClass, extendedTagFileName,
					utilTaglibSrcDirName));
		}

		_extendedTagJavaClassesMap.put(absolutePath, extendedTagJavaClasses);

		return extendedTagJavaClasses;
	}

	private JavaMethod _getJavaMethod(
		List<JavaMethod> javaMethods, String name) {

		for (JavaMethod javaMethod : javaMethods) {
			if (StringUtil.equalsIgnoreCase(javaMethod.getName(), name)) {
				return javaMethod;
			}
		}

		return null;
	}

	private synchronized List<JavaMethod> _getJavaMethods(JavaClass javaClass) {
		String className = javaClass.getName(true);

		List<JavaMethod> javaMethods = _javaMethodsMap.get(className);

		if (javaMethods != null) {
			return javaMethods;
		}

		javaMethods = new ArrayList<>();

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (javaTerm.isJavaMethod()) {
				javaMethods.add((JavaMethod)javaTerm);
			}
		}

		_javaMethodsMap.put(className, javaMethods);

		return javaMethods;
	}

	private List<JavaMethod> _getJavaMethods(
		JavaClass javaClass, List<JavaClass> extendedTagJavaClasses) {

		List<JavaMethod> javaMethods = new ArrayList<>();

		javaMethods.addAll(_getJavaMethods(javaClass));

		for (JavaClass extendedTagJavaClass : extendedTagJavaClasses) {
			javaMethods.addAll(_getJavaMethods(extendedTagJavaClass));
		}

		return javaMethods;
	}

	private synchronized List<String> _getTagAttributeNames(
			String packageName, String className)
		throws Exception {

		String fullyQualifiedClassName = StringBundler.concat(
			packageName, StringPool.PERIOD, className);

		if (_tagAttributesMap != null) {
			return _tagAttributesMap.get(fullyQualifiedClassName);
		}

		_tagAttributesMap = new HashMap<>();

		List<String> tldFileNames = TaglibUtil.getTLDFileNames(
			getBaseDirName(), _allFileNames, getSourceFormatterExcludes(),
			isPortalSource());

		if (tldFileNames.isEmpty()) {
			return _tagAttributesMap.get(fullyQualifiedClassName);
		}

		for (String tldFileName : tldFileNames) {
			tldFileName = StringUtil.replace(
				tldFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File tldFile = new File(tldFileName);

			String content = FileUtil.read(tldFile);

			Document document = SourceUtil.readXML(content);

			Element rootElement = document.getRootElement();

			List<Element> tagElements = rootElement.elements("tag");

			for (Element tagElement : tagElements) {
				Element tagClassElement = tagElement.element("tag-class");

				String tagClassName = tagClassElement.getStringValue();

				if (!tagClassName.startsWith("com.liferay")) {
					continue;
				}

				List<String> tagAttributeNames = new ArrayList<>();

				List<Element> attributeElements = tagElement.elements(
					"attribute");

				for (Element attributeElement : attributeElements) {
					Element attributeNameElement = attributeElement.element(
						"name");

					tagAttributeNames.add(
						attributeNameElement.getStringValue());
				}

				_tagAttributesMap.put(tagClassName, tagAttributeNames);
			}
		}

		return _tagAttributesMap.get(fullyQualifiedClassName);
	}

	private synchronized String _getUtilTaglibSrcDirName() {
		if (_utilTaglibSrcDirName != null) {
			return _utilTaglibSrcDirName;
		}

		_utilTaglibSrcDirName = TaglibUtil.getUtilTaglibSrcDirName(
			getBaseDirName());

		return _utilTaglibSrcDirName;
	}

	private List<String> _allFileNames;
	private final Map<String, List<JavaClass>> _extendedTagJavaClassesMap =
		new HashMap<>();
	private final Map<String, List<JavaMethod>> _javaMethodsMap =
		new HashMap<>();
	private Map<String, List<String>> _tagAttributesMap;
	private String _utilTaglibSrcDirName;

}