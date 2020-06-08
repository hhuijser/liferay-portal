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

import com.liferay.source.formatter.parser.JavaTerm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Minhchau Dang
 */
public class JavaStringStartsWithSubstringCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		StringBuffer sb = new StringBuffer();

		String javaTermContent = javaTerm.getContent();

		Matcher matcher = _substringContainsPattern.matcher(javaTermContent);

		while (matcher.find()) {
			String var1 = matcher.group(1);
			String var2 = matcher.group(3);
			String var3 = matcher.group(4);
			String var4 = matcher.group(5);

			if (!var1.equals(var3) || !var2.equals(var4)) {
				matcher.appendReplacement(sb, "$0");

				continue;
			}

			matcher.appendReplacement(sb, "$1.startsWith$2");
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private static final Pattern _substringContainsPattern = Pattern.compile(
		"(\\S*)\\.contains(\\(([^))]*)\\).*?=\\s*(\\S*)\\.substring\\(" +
			"([^\\)]*)\\.length\\(\\)\\))",
		Pattern.DOTALL);

}