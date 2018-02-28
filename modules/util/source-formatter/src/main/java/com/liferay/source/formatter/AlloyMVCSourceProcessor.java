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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Peter Shin
 */
public class AlloyMVCSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected void format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		_processCheckstyle(absolutePath, content);
	}

	@Override
	protected void postFormat() throws Exception {
		_processCheckstyle(_ungeneratedFiles);

		_ungeneratedFiles.clear();

		for (SourceFormatterMessage sourceFormatterMessage :
				_sourceFormatterMessages) {

			String s = sourceFormatterMessage.getFileName();

			s = s.replace(_CHECKSTYLE_TMP_DIR, _CHECKSTYLE_SRC_DIR);

			String fileName = s.substring(0, s.lastIndexOf(".")) + ".jspf";

			sourceFormatterMessage.setFileName(fileName);

			processMessage(fileName, sourceFormatterMessage);

			printError(fileName, sourceFormatterMessage.toString());
		}
	}

	private void _processCheckstyle(List<File> files) throws Exception {
		if (files.isEmpty()) {
			return;
		}

		if (_configuration == null) {
			Configuration configuration = CheckstyleUtil.getConfiguration(
				"checkstyle.xml");

			configuration = CheckstyleUtil.addAttribute(
				configuration, "maxLineLength",
				String.valueOf(sourceFormatterArgs.getMaxLineLength()),
				"com.liferay.source.formatter.checkstyle.checks.Append");
			configuration = CheckstyleUtil.addAttribute(
				configuration, "maxLineLength",
				String.valueOf(sourceFormatterArgs.getMaxLineLength()),
				"com.liferay.source.formatter.checkstyle.checks.Concat");
			configuration = CheckstyleUtil.addAttribute(
				configuration, "maxLineLength",
				String.valueOf(sourceFormatterArgs.getMaxLineLength()),
				"com.liferay.source.formatter.checkstyle.checks.PlusStatement");
			configuration = CheckstyleUtil.addAttribute(
				configuration, "showDebugInformation",
				String.valueOf(sourceFormatterArgs.isShowDebugInformation()),
				"com.liferay.*");

			_configuration = configuration;

			List<File> suppressionsFiles =
				SourceFormatterUtil.getSuppressionsFiles(
					sourceFormatterArgs.getBaseDirName(),
					"checkstyle-suppressions.xml", getAllFileNames(),
					getSourceFormatterExcludes(), portalSource, subrepository);

			_suppressionsFiles = suppressionsFiles;

			if (sourceFormatterArgs.isShowDebugInformation()) {
				DebugUtil.addCheckNames(
					CheckType.CHECKSTYLE,
					CheckstyleUtil.getCheckNames(configuration));
			}
		}

		Set<SourceFormatterMessage> sourceFormatterMessages =
			CheckstyleUtil.getSourceFormatterMessages(
				_configuration, _suppressionsFiles, files, sourceFormatterArgs);

		_sourceFormatterMessages.addAll(sourceFormatterMessages);
	}

	private synchronized void _processCheckstyle(
			String absolutePath, String content)
		throws Exception {

		if (!content.matches("(?s)<%--.*--%>(\\s*<%@[^\\n]*)*\\s*<%!\\s.*")) {
			return;
		}

		if (StringUtil.count(content, "<%!") != 1) {
			return;
		}

		if (!content.endsWith("%>")) {
			return;
		}

		String tmpPath = absolutePath.replace(
			_CHECKSTYLE_SRC_DIR, _CHECKSTYLE_TMP_DIR);

		File tmpFile = new File(
			tmpPath.substring(0, tmpPath.lastIndexOf(".")) + ".java");

		String tmpContent = StringUtil.replace(
			content, new String[] {"<%--", "--%>", "<%@", "<%!", "%>"},
			new String[] {"//<%--", "//--%>", "//<%@", "//<%!", "//%>"});

		FileUtil.write(tmpFile, tmpContent);

		_ungeneratedFiles.add(tmpFile);

		if (_ungeneratedFiles.size() == _CHECKSTYLE_BATCH_SIZE) {
			_processCheckstyle(_ungeneratedFiles);

			_ungeneratedFiles.clear();
		}
	}

	private static final int _CHECKSTYLE_BATCH_SIZE = 1000;

	private static final String _CHECKSTYLE_SRC_DIR =
		"/src/main/resources/alloy_mvc/jsp/";

	private static final String _CHECKSTYLE_TMP_DIR =
		"/tmp/main/resources/alloy_mvc/jsp/";

	private static final String[] _INCLUDES =
		{"**/src/main/resources/alloy_mvc/jsp/**/*.jspf"};

	private Configuration _configuration;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new HashSet<>();
	private List<File> _suppressionsFiles;
	private final List<File> _ungeneratedFiles = new ArrayList<>();

}