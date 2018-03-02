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
import com.liferay.source.formatter.checks.util.JSPSourceUtil;
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
 * @author Hugo Huijser
 */
public class JSPSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = {"**/null.jsp", "**/tools/**"};

		List<String> fileNames = getFileNames(excludes, getIncludes());

		if (fileNames.isEmpty() ||
			(!sourceFormatterArgs.isFormatCurrentBranch() &&
			 !sourceFormatterArgs.isFormatLatestAuthor() &&
			 !sourceFormatterArgs.isFormatLocalChanges())) {

			return fileNames;
		}

		excludes = new String[] {"**/null.jsp", "**/tools/**"};

		List<String> allJSPFileNames = getFileNames(
			excludes, getIncludes(), true);

		return JSPSourceUtil.addIncludedAndReferencedFileNames(
			fileNames, new HashSet<String>(),
			JSPSourceUtil.getContentsMap(allJSPFileNames));
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected File format(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		file = super.format(file, fileName, absolutePath, content);

		_processCheckstyle(absolutePath, content);

		return file;
	}

	@Override
	protected void postFormat() throws Exception {
		_processCheckstyle(_ungeneratedFiles);

		_ungeneratedFiles.clear();

		for (SourceFormatterMessage sourceFormatterMessage :
				_sourceFormatterMessages) {

			processMessage(
				sourceFormatterMessage.getFileName(), sourceFormatterMessage);

			printError(
				sourceFormatterMessage.getFileName(),
				sourceFormatterMessage.toString());
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

			_suppressionsFiles = SourceFormatterUtil.getSuppressionsFiles(
				sourceFormatterArgs.getBaseDirName(),
				"checkstyle-suppressions.xml", getAllFileNames(),
				getSourceFormatterExcludes());

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

		if (!absolutePath.contains(CheckstyleUtil.ALLOY_MVC_SRC_DIR)) {
			return;
		}

		if (!absolutePath.endsWith(".jspf")) {
			return;
		}

		if (!content.matches("(?s)<%--.*--%>(\\s*<%@[^\\n]*)*\\s*<%!\\s.*")) {
			return;
		}

		if (StringUtil.count(content, "<%!") != 1) {
			return;
		}

		if (!content.endsWith("\n%>")) {
			return;
		}

		File tmpFile = new File(CheckstyleUtil.getJavaFileName(absolutePath));

		String tmpContent = StringUtil.replace(
			content, new String[] {"<%--", "--%>", "<%@", "<%!"},
			new String[] {"//<%--", "//--%>", "//<%@", "//<%!"});

		tmpContent = StringUtil.replaceLast(tmpContent, "\n%>", "");

		FileUtil.write(tmpFile, tmpContent);

		_ungeneratedFiles.add(tmpFile);

		if (_ungeneratedFiles.size() == CheckstyleUtil.BATCH_SIZE) {
			_processCheckstyle(_ungeneratedFiles);

			_ungeneratedFiles.clear();
		}
	}

	private static final String[] _INCLUDES =
		{"**/*.jsp", "**/*.jspf", "**/*.tag", "**/*.tpl", "**/*.vm"};

	private Configuration _configuration;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new HashSet<>();
	private List<File> _suppressionsFiles;
	private final List<File> _ungeneratedFiles = new ArrayList<>();

}