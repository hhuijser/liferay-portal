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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.BNDSettings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.artifact.versioning.ComparableVersion;

/**
 * @author Hugo Huijser
 */
public class JavaDeprecatedJavadocCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		content = _formatDeprecatedJavadoc(fileName, content);

		return content;
	}

	private String _formatDeprecatedJavadoc(String fileName, String content)
		throws Exception {

		BNDSettings bndSettings = getBNDSettings(fileName);

		ComparableVersion releaseComparableVersion =
			bndSettings.getReleaseComparableVersion();

		if (releaseComparableVersion == null) {
			return content;
		}

		Matcher matcher = _deprecatedPattern.matcher(content);

		while (matcher.find()) {
			if (matcher.group(2) == null) {
				return StringUtil.insert(
					content, " As of " + _NEXT_VERSION, matcher.end(1));
			}

			String version = matcher.group(3);

			if (!version.equals(_NEXT_VERSION)) {
				ComparableVersion comparableVersion = new ComparableVersion(
					version);

				if (comparableVersion.compareTo(releaseComparableVersion) > 0) {
					return StringUtil.replaceFirst(
						content, version, _NEXT_VERSION, matcher.start());
				}

				if (StringUtil.count(version, CharPool.PERIOD) == 1) {
					return StringUtil.insert(content, ".0", matcher.end(3));
				}
			}

			String deprecatedInfo = matcher.group(4);

			if (Validator.isNull(deprecatedInfo)) {
				return content;
			}

			if (!deprecatedInfo.startsWith(StringPool.COMMA)) {
				return StringUtil.insert(
					content, StringPool.COMMA, matcher.end(3));
			}

			if (deprecatedInfo.endsWith(StringPool.PERIOD) &&
				!deprecatedInfo.matches("[\\S\\s]*\\.[ \n][\\S\\s]*")) {

				return StringUtil.replaceFirst(
					content, StringPool.PERIOD, StringPool.BLANK,
					matcher.end(4) - 1);
			}
		}

		return content;
	}

	private static final String _NEXT_VERSION = "NEXT-VERSION";

	private final Pattern _deprecatedPattern = Pattern.compile(
		"(\n\\s*\\* @deprecated)( As of ([0-9\\.]+|NEXT-VERSION)(.*?)\n" +
			"\\s*\\*( @|/))?",
		Pattern.DOTALL);

}