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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class JavaComponentServiceCheck extends BaseJavaTermCheck {

	public void setAllowedFileNames(String allowedFileNames) {
		Collections.addAll(
			_allowedFileNames, StringUtil.split(allowedFileNames));
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (absolutePath.contains("/test/") ||
			absolutePath.endsWith("Test.java")) {

			return javaTerm.getContent();
		}

		if (!javaTerm.hasAnnotation("Component")) {
			return javaTerm.getContent();
		}

		for (String allowedFileName : _allowedFileNames) {
			if (absolutePath.endsWith(allowedFileName)) {
				return javaTerm.getContent();
			}
		}

		if (!_hasComponentServiceElement(javaTerm.getContent())) {
			addMessage(
				fileName,
				"The @Component should have the 'service' element declared");
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private boolean _hasComponentServiceElement(String javaTermContent) {
		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"(\\A|\n)", SourceUtil.getIndent(javaTermContent),
				"@Component\\s*\\([^\\)]*\\)"),
			Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(javaTermContent);

		while (matcher.find()) {
			String content = matcher.group();

			if (content.matches("(?is).*service\\s*=.*")) {
				continue;
			}

			return false;
		}

		matcher = matcher.reset();

		if (!matcher.find()) {
			return false;
		}

		return true;
	}

	private final List<String> _allowedFileNames = new ArrayList<>();

}