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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaClassNameCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (javaTerm.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.isAnonymous() || javaClass.hasAnnotation("Deprecated")) {
			return javaTerm.getContent();
		}

		String className = javaClass.getName();

		List<String> expectedPackagePathDataEntries = getAttributeValues(
			_EXPECTED_PACKAGE_PATH_DATA_KEY, absolutePath);

		for (String expectedPackagePathDataEntry :
				expectedPackagePathDataEntries) {

			String[] array = StringUtil.split(
				expectedPackagePathDataEntry, CharPool.COLON);

			if (array.length != 2) {
				continue;
			}

			String packageName = javaClass.getPackageName();

			String expectedClassNameEnding = array[1];

			if (packageName.endsWith("." + array[0]) &&
				!className.endsWith(expectedClassNameEnding)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Name of class in package '", packageName,
						"' should end with '", expectedClassNameEnding, "'"));
			}
		}

		List<String> implementedClassNames =
			javaClass.getImplementedClassNames();

		if (implementedClassNames.isEmpty()) {
			return javaTerm.getContent();
		}

		List<String> enforceImplementedClassNames = getAttributeValues(
			_ENFORCE_IMPLEMENTED_CLASS_NAMES_KEY, absolutePath);

		for (String implementedClassName : enforceImplementedClassNames) {
			if (!implementedClassNames.contains(implementedClassName)) {
				continue;
			}

			if (!className.endsWith(implementedClassName)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Name of class implementing '", implementedClassName,
						"' should end with '", implementedClassName, "'"));
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private static final String _ENFORCE_IMPLEMENTED_CLASS_NAMES_KEY =
		"enforceImplementedClassNames";

	private static final String _EXPECTED_PACKAGE_PATH_DATA_KEY =
		"expectedPackagePathData";

}