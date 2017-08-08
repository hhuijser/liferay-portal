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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class GradleApplyPluginsCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (absolutePath.contains("/project-templates-")) {
			return content;
		}

		String applyPlugins = _getApplyPlugins(content);

		if (Validator.isNull(applyPlugins)) {
			return content;
		}

		return _sortContent(content, applyPlugins);
	}

	private String _getApplyPlugins(String content) {
		Matcher matcher = _applyPluginsPattern.matcher(content);

		if (matcher.find()) {
			return matcher.group();
		}

		return null;
	}

	private String _getBuildscriptBlock(String content) throws IOException {
		List<String> lines = new ArrayList<>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (lines.isEmpty()) {
				if (line.startsWith("buildscript {")) {
					lines.add(line);
				}
			}
			else {
				lines.add(line);

				if (!line.startsWith("}")) {
					continue;
				}

				String s = _merge(lines);

				if (StringUtil.count(s, "{") == StringUtil.count(s, "}")) {
					return s;
				}

				return null;
			}
		}

		return null;
	}

	private String _getImports(String content) {
		Matcher matcher = _importsPattern.matcher(content);

		if (matcher.find()) {
			return matcher.group();
		}

		return null;
	}

	private String _merge(Collection<String> lines) {
		if (lines == null) {
			return null;
		}

		if (lines.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * lines.size());

		for (String s : lines) {
			sb.append(s);
			sb.append("\n");
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private String _sortApplyPlugins(String applyPlugins) throws IOException {
		Set<String> lines = new TreeSet<>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(applyPlugins));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (Validator.isNotNull(line)) {
				lines.add(line);
			}
		}

		return _merge(lines);
	}

	private String _sortContent(String content, String applyPlugins)
		throws IOException {

		StringBundler sb = new StringBundler(6);

		String imports = _getImports(content);

		if (Validator.isNotNull(imports)) {
			sb.append(imports);
		}

		String buildscriptBlock = _getBuildscriptBlock(content);

		if (Validator.isNotNull(buildscriptBlock)) {
			sb.append(buildscriptBlock);
			sb.append("\n\n");
		}

		sb.append(_sortApplyPlugins(applyPlugins));

		if (content.startsWith(sb.toString())) {
			return content;
		}

		String s = StringUtil.removeSubstrings(
			content, applyPlugins, buildscriptBlock, imports);

		if (Validator.isNotNull(s.trim())) {
			sb.append("\n\n");
			sb.append(s.trim());
		}

		return sb.toString();
	}

	private static final Pattern _applyPluginsPattern = Pattern.compile(
		"(^apply plugin:\\s+.*(\n*|\\Z))+", Pattern.MULTILINE);
	private static final Pattern _importsPattern = Pattern.compile(
		"(^[ \t]*import\\s+.*\n+)+", Pattern.MULTILINE);

}