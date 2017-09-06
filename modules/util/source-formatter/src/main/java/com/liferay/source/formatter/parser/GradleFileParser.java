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

package com.liferay.source.formatter.parser;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.RegexUtil;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class GradleFileParser {

	public static GradleFile parse(String fileName, String content)
		throws Exception {

		Set<String> applyPlugins = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		String bodyBlock = StringPool.BLANK;
		String buildScriptBlock = StringPool.BLANK;
		String extScriptBlock = StringPool.BLANK;
		Set<String> imports = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		String initializeBlock = StringPool.BLANK;
		Set<String> properties = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		Set<String> tasks = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

		Matcher taskMatcher = _TASK_PATTERN.matcher(content);

		for (String line : StringUtil.splitLines(content)) {
			if (Validator.isNull(buildScriptBlock) &&
				RegexUtil.matches(line, "^buildscript\\s+.*\\{")) {

				buildScriptBlock = line;

				continue;
			}

			if (Validator.isNotNull(buildScriptBlock) &&
				!buildScriptBlock.endsWith("\n}")) {

				buildScriptBlock = buildScriptBlock + "\n" + line;

				continue;
			}

			if (Validator.isNull(extScriptBlock) &&
				RegexUtil.matches(line, "^ext\\s+.*\\{")) {

				extScriptBlock = line;

				continue;
			}

			if (Validator.isNotNull(extScriptBlock) &&
				!extScriptBlock.endsWith("\n}")) {

				extScriptBlock = extScriptBlock + "\n" + line;

				continue;
			}

			if (RegexUtil.matches(line, "^\\s*import\\s+.*$")) {
				imports.add(line);
			}
			else if (RegexUtil.matches(line, "^apply plugin.*")) {
				applyPlugins.add(line);
			}
			else if (RegexUtil.matches(line, "^task\\s+.*$")) {
				tasks.add(line);
			}
			else if (RegexUtil.matches(line, "^sourceCompatibility\\s*=.*$")) {
				properties.add(line);
			}
			else if (RegexUtil.matches(line, "^targetCompatibility\\s*=.*$")) {
				properties.add(line);
			}
			else {
				if (taskMatcher.matches() && tasks.isEmpty()) {
					initializeBlock = initializeBlock + "\n" + line;
				}
				else {
					bodyBlock = bodyBlock + "\n" + line;
				}
			}
		}

		bodyBlock = StringUtil.trim(bodyBlock);
		buildScriptBlock = StringUtil.trim(buildScriptBlock);
		extScriptBlock = StringUtil.trim(extScriptBlock);
		initializeBlock = StringUtil.trim(initializeBlock);

		GradleFile gradleFile = new GradleFile(
			applyPlugins, bodyBlock, buildScriptBlock, content, extScriptBlock,
			fileName, imports, initializeBlock, properties, tasks);

		return gradleFile;
	}

	private static final Pattern _TASK_PATTERN = RegexUtil.getPattern(
		".*^task\\s+.*$.*", Pattern.DOTALL | Pattern.MULTILINE);

}