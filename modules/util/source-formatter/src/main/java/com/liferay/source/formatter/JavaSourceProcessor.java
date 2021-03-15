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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.portal.tools.java.parser.JavaParser;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleLogger;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;
import com.liferay.source.formatter.util.DebugUtil;
import com.liferay.source.formatter.util.FileUtil;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaSourceProcessor extends BaseSourceProcessor {

	@Override
	protected List<String> doGetFileNames() throws IOException {
		String[] includes = getIncludes();

		if (ArrayUtil.isEmpty(includes)) {
			return new ArrayList<>();
		}

		Collection<String> fileNames = null;

		if (isPortalSource() || isSubrepository()) {
			fileNames = _getPortalJavaFiles(includes);
		}
		else {
			fileNames = _getPluginJavaFiles(includes);
		}

		return new ArrayList<>(fileNames);
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

		_processCheckstyle(file);

		return file;
	}

	@Override
	protected String parse(
			File file, String fileName, String content,
			Set<String> modifiedMessages)
		throws Exception {

		SourceFormatterArgs sourceFormatterArgs = getSourceFormatterArgs();

		String newContent = JavaParser.parse(
			file, content, sourceFormatterArgs.getMaxLineLength(), false);

		if (!content.equals(newContent)) {
			modifiedMessages.add(file.toString() + " (JavaParser)");

			if (sourceFormatterArgs.isShowDebugInformation()) {
				DebugUtil.printContentModifications(
					"JavaParser", fileName, content, newContent);
			}
		}

		return newContent;
	}

	/*
	private boolean _fixFile(String fileName, String message)
		throws IOException {

		String[] parts = StringUtil.split(message, ":");

		if ((parts.length != 4) && (parts.length != 5) && (parts.length != 6)) {
			return false;
		}

		String s = parts[0];

		File file = new File(fileName);

		String content = FileUtil.read(file);

		String newContent = null;

		if (s.equals("PART1")) {
			newContent = _fixPart1(fileName, content, parts);
		}
		else if (s.equals("PART2")) {
			newContent = _fixPart2(content, parts);
		}
		else if (s.equals("PART3")) {
			newContent = _fixPart3(content, parts, "setActionName");

			if (newContent.equals(content)) {
				newContent = _fixPart3(content, parts, "setMVCPath");
			}

			if (newContent.equals(content)) {
				newContent = _fixPart3(content, parts, "setMVCRenderCommandName");
			}

			if (newContent.equals(content)) {
				newContent = _fixPart3(content, parts, "setParameter");
			}

			if (newContent.equals(content)) {
				newContent = _fixPart3(content, parts, "setRedirect");
			}
		}
		else if (s.equals("PART4")) {
			newContent = _fixPart4(content, parts);
		}
		else if (s.equals("PART5")) {
			newContent = _fixPart5(content, parts);
		}
		else {
			return false;
		}

		if (newContent.equals(content)) {
			return false;
		}

		FileUtil.write(file, newContent);

		System.out.println("MODIFIED: " + fileName);

		return true;
	}
	*/

	private boolean _fixFile(
			String fileName, List<String> messages, String prefix)
		throws IOException {

		File file = new File(fileName);

		String content = FileUtil.read(file);

		int originalSize = StringUtil.count(content, "\n");

		String newContent = content;

		for (int i = 0; i < messages.size(); i++) {
			String message = messages.get(i);

			String[] parts = StringUtil.split(message, ":");

			int diff = StringUtil.count(newContent, "\n") - originalSize;
			if (prefix.equals("PART1")) {
				newContent = _fixPart1(fileName, newContent, parts, diff);
			}
			else if (prefix.equals("PART2")) {
				newContent = _fixPart2(newContent, parts, diff);
			}
			else if (prefix.equals("PART3") && (i == 0)) {
				newContent = _fixPart3(newContent, parts, "setActionName", diff);
		
				newContent = _fixPart3(newContent, parts, "setMVCPath", diff);
		
				newContent = _fixPart3(
					newContent, parts, "setMVCRenderCommandName", diff);
		
				newContent = _fixPart3(newContent, parts, "setParameter", diff);
		
				newContent = _fixPart3(newContent, parts, "setRedirect", diff);
			}
			else if (prefix.equals("PART4") && (i == 0)) {
				newContent = _fixPart4(newContent, parts, diff);
			}
			else if (prefix.equals("PART5")) {
				newContent = _fixPart5(newContent, parts, diff);
			}
			else if (prefix.equals("PART6")) {
				newContent = _fixPart6(newContent, parts, diff);
			}
		}

		if (newContent.equals(content)) {
			return false;
		}

		FileUtil.write(file, newContent);

		System.out.println("MODIFIED: " + fileName);

		return true;
	}

	String _getContent(String content, int start, int end) {
		StringBundler sb = new StringBundler();

		for (int i = start; i <= end; i++) {
			sb.append(SourceUtil.getLine(content, i));
			sb.append("\n");
		}

		return sb.toString();
	}

	private synchronized String _fixPart1(
			String fileName, String content, String[] parts, int diff)
		throws IOException {

		String variableName = parts[1];

		int lineNumber1 = GetterUtil.getInteger(parts[2]) + diff;
		int lineNumber2 = GetterUtil.getInteger(parts[3]) + diff;
		int lineNumber3 = GetterUtil.getInteger(parts[4]) + diff;
		int lineNumber4 = GetterUtil.getInteger(parts[5]) + diff;

		String assignStatement = _getContent(content, lineNumber1, lineNumber2);

		int i = assignStatement.indexOf(" =");

		if (i == -1) {
			return content;
		}

		String variableTypeAndName = assignStatement.substring(0, i + 3);

		String assignValue = StringUtil.trim(assignStatement.substring(i + 2));

		if (!assignValue.endsWith(";")) {
			return content;
		}

		assignValue = StringUtil.replaceLast(assignValue, ";", "");

		String firstMethodCall = null;

		if (assignValue.contains("renderResponse.createActionURL(") ||
			assignValue.contains("renderResponse.createRenderURL(") ||
			assignValue.contains("resourceResponse.createActionURL(") ||
			assignValue.contains("mimeResponse.createRenderURL(") ||
			assignValue.contains("liferayActionResponse.createRenderURL(") ||
			assignValue.contains("liferayPortletResponse.createActionURL(") ||
			assignValue.contains("liferayPortletResponse.createRenderURL(") ||
			assignValue.contains("liferayPortletResponse.createLiferayPortletURL(")) {

			int i1 = assignValue.indexOf(".");

			int i2 = assignValue.indexOf("(");

			String s1 = assignValue.substring(0, i1);

			char c = assignValue.charAt(i2 + 1);

			if (c == ')') {
				firstMethodCall = StringUtil.replaceFirst(assignValue, "(", "(" + s1);
			}
			else {
				firstMethodCall = StringUtil.replaceFirst(assignValue, "(", "(" + s1 + ", ");
			}

			firstMethodCall = firstMethodCall.substring(i1);

			firstMethodCall = StringUtil.replaceLast(firstMethodCall, ")", "");

			String sasdf = "asdf";

			//System.out.println("-------------------------------------");
			//System.out.println(assignValue);
		}

		if (true) {
			//return content;
		}

		String methodCall = _getContent(content, lineNumber3, lineNumber4);

		i = methodCall.indexOf(".");

		int j = methodCall.indexOf("(");

		String methodName = StringUtil.trim(methodCall.substring(i + 1, j));

		List<String> parameters = JavaSourceUtil.getParameterList(methodCall);

		StringBundler sb = new StringBundler();

		sb.append("PortletURLBuilder");

		if (firstMethodCall != null) {
			sb.append(firstMethodCall);
		}
		else {
			sb.append(".create(");
			sb.append(assignValue);
		}

		if (methodName.equals("setParameter")) {
			String p1 = parameters.get(0);

			String p2 = parameters.get(1);

			String trimmed = StringUtil.trim(p2);

			if (trimmed.equals(variableName) ||
				trimmed.matches(variableName + "\\W.*") ||
				trimmed.matches(".*\\W" + variableName) ||
				trimmed.matches(".*\\W" + variableName + "\\W.")) {

				return content;
			}

			if (p1.equals("\"mvcRenderCommandName\"")) {
				methodName = "setMVCRenderCommandName";

				parameters.clear();

				parameters.add(p2);
			}
			else if (p1.equals("\"mvcPath\"")) {
				methodName = "setMVCPath";

				parameters.clear();

				parameters.add(p2);
			}
			else if (p1.equals("\"redirect\"")) {
				methodName = "setRedirect";

				parameters.clear();

				parameters.add(p2);
			}
			else if (p1.equals("ActionRequest.ACTION_NAME")) {
				methodName = "setActionName";

				parameters.clear();

				parameters.add(p2);
			}
		}

		sb.append(").");
		sb.append(methodName);
		sb.append("(");

		for (String parameter : parameters) {
			sb.append(parameter);
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(").build();\n\n\n");

		content = StringUtil.replaceFirst(
			content, methodCall, variableTypeAndName + sb.toString(),
			SourceUtil.getLineStartPos(content, lineNumber3) - 1);

		content = StringUtil.replaceFirst(
			content, assignStatement, "",
			SourceUtil.getLineStartPos(content, lineNumber1));

		if (!content.contains("import com.liferay.petra.portlet.url.builder.PortletURLBuilder")) {
			int pos1 = content.indexOf("\nimport ");

			content = StringUtil.insert(content, "\nimport com.liferay.petra.portlet.url.builder.PortletURLBuilder;", pos1);

			String buildGradleFileLocation = fileName;

			while (true) {
				int pos2 = buildGradleFileLocation.lastIndexOf(StringPool.SLASH);

				if (pos2 == -1) {
					break;
				}

				buildGradleFileLocation = buildGradleFileLocation.substring(0, pos2 + 1);

				File file = new File(buildGradleFileLocation + "build.gradle");

				if (file.exists()) {
					String abs = file.getAbsolutePath();

					if (abs.endsWith("modules/build.gradle")) {
						return content;
					}

					String buildGradleContent = FileUtil.read(file);

					if (!buildGradleContent.contains("petra:petra-portlet-url-builder")) {
						buildGradleContent = StringUtil.replaceFirst(buildGradleContent, "dependencies {", "dependencies {\n\tcompileOnly project(\":apps:petra:petra-portlet-url-builder\")");

						FileUtil.write(file, buildGradleContent);
					}

					break;
				}

				buildGradleFileLocation = StringUtil.replaceLast(
					buildGradleFileLocation, CharPool.SLASH, StringPool.BLANK);
			}
		}

		return content;
	}

	private String _fixPart6(
		String content, String[] parts, int diff) {

		int start = GetterUtil.getInteger(parts[1]) + diff;
		int end = GetterUtil.getInteger(parts[2]) + diff;

		String line1 = StringUtil.trim(SourceUtil.getLine(content, start - 1));
		String line2 = StringUtil.trim(SourceUtil.getLine(content, start));
		String line3 = StringUtil.trim(SourceUtil.getLine(content, end - 1));
		String line4 = StringUtil.trim(SourceUtil.getLine(content, end));

		if (!line1.endsWith("{") || !line2.startsWith("return") ||
			!line3.endsWith(";") || !line4.startsWith("}")) {

			return content;
		}

		int x = SourceUtil.getLineStartPos(content, end);

		content = StringUtil.replaceFirst(content, "}", "", x);

		x = SourceUtil.getLineStartPos(content, end - 1);

		content = StringUtil.replaceFirst(content, ";\n", "\n", x);

		x = SourceUtil.getLineStartPos(content, start);

		content = StringUtil.replaceFirst(content, "return", "", x);

		x = SourceUtil.getLineStartPos(content, start - 1);

		content = StringUtil.replaceFirst(content, "{\n", "\n", x);

		return content;
	}

	private String _fixPart5(
		String content, String[] parts, int diff) {

		String variableName = parts[1];

		int start = GetterUtil.getInteger(parts[2]) + diff;
		int end = GetterUtil.getInteger(parts[3]) + diff;
		int toStringLineNumber = GetterUtil.getInteger(parts[4]) + diff;

		String s1 = _getContent(content, start, end);

		if (!s1.endsWith(";\n")) {
			return content;
		}

		String s2 = StringUtil.replaceLast(s1, ";\n", "");

		int x = s2.indexOf("=");

		if (x == -1) {
			return content;
		}

		s2 = StringUtil.trim(s2.substring(x + 1));

		if (!s2.endsWith("build()")) {
			return content;
		}

		s2 = StringUtil.replaceLast(s2, "build", "buildString");

		String line = _getContent(content, toStringLineNumber, toStringLineNumber);

		String s3 = variableName + ".toString()";

		if (!line.contains(s3)) {
			return content;
		}

		x = SourceUtil.getLineStartPos(content, toStringLineNumber);

		content = StringUtil.replaceFirst(content, s3, s2, x);

		x = SourceUtil.getLineStartPos(content, start);

		return StringUtil.replaceFirst(content, s1, "", x);
	}

	private String _fixPart4(
		String content, String[] parts, int diff) {

		String variableName = parts[1];

		int startLineNumber = GetterUtil.getInteger(parts[2]) + diff;
		int endLineNumber = GetterUtil.getInteger(parts[3]) + diff;
		int lineNumber = GetterUtil.getInteger(parts[4]) + diff;

		String line = StringUtil.trim(SourceUtil.getLine(content, lineNumber));

		if (!line.equals(variableName) &&
			!line.startsWith(variableName + ",")) {

			return content;
		}

		String varDeclaration = _getContent(content, startLineNumber, endLineNumber);

		int i = varDeclaration.indexOf(variableName + " =");

		if ((i == -1) || !varDeclaration.endsWith(";\n")) {
			return content;
		}

		String value = StringUtil.trim(varDeclaration.substring(i + variableName.length() + 2));

		value = StringUtil.replaceLast(value, ";", "");

		content = StringUtil.replaceFirst(
			content, variableName, value,
			SourceUtil.getLineStartPos(content, lineNumber));

		content = StringUtil.replaceFirst(
			content, varDeclaration, "",
			SourceUtil.getLineStartPos(content, startLineNumber - 1));

		return content;
	}

	private String _fixPart3(
		String content, String[] parts, String methodName, int diff) {

		String varName = parts[1];

		List<String> lineNumbers = ListUtil.fromString(parts[2], ",");

		int builderStartLineNumber = GetterUtil.getInteger(parts[3]) + diff;
		int builderEndLineNumber = GetterUtil.getInteger(parts[4]) + diff;

		int i = builderStartLineNumber;

		int pos = -1;
		String parameterValue = null;
		String call = null;

		outerLoop:
		while (true) {
			String line = SourceUtil.getLine(content, i);

			if (line == null) {
				return content;
			}

			if (i > builderEndLineNumber) {
				return content;
			}

			if (!Objects.equals(StringUtil.trim(line), ")." + methodName + "(")) {
				i++;

				continue;
			}

			int x = SourceUtil.getLineStartPos(content, i);
			int y = content.indexOf(").", x);

			List<String> parameters = null;

			//try {
				parameters = JavaSourceUtil.getParameterList(
					content.substring(y + 2));
			//}
			//catch (Exception exception) {
			//	System.out.println("NPE");

			//	return content;
			//}

			for (int j = 0; j < parameters.size(); j++) {
				String parameter = parameters.get(j);

				Pattern pattern = Pattern.compile(
					"(\\W|\\A)" + varName + "(\\W|\\Z)");

				Matcher matcher = pattern.matcher(parameter);

				while (matcher.find()) {
					if (ToolsUtil.isInsideQuotes(parameter, matcher.start() + 1)) {
						continue;
					}

					if ((j == 0) && methodName.equals("setParameter")) {
						return content;
					}

					parameterValue = parameter;
					pos = x;

					int z = content.indexOf(
						"\n" + SourceUtil.getIndent(line) + ").", y + 1);

					if (z == -1) {
						return content;
					}

					call = content.substring(x, z);

					break outerLoop;
				}
			}

			i++;
		}

		String newParameterValue = null;

		if (varName.equals(StringUtil.trim(parameterValue)) &&
			(lineNumbers.size() == 2)) {

			String s = StringUtil.trim(
				_getContent(
					content, GetterUtil.getInteger(lineNumbers.get(0)) + diff,
					GetterUtil.getInteger(lineNumbers.get(1)) + diff));

			if (!s.endsWith(";")) {
				return content;
			}

			s = StringUtil.replaceLast(s, ";", "");

			int pos1 = s.indexOf(" =");

			if (pos1 == -1) {
				return content;
			}

			newParameterValue = StringUtil.trim(s.substring(pos1 + 2));
		}
		else {
			if (parameterValue.startsWith("() -> {")) {
				StringBundler sb = new StringBundler();

				int start = 0;
				int end = 0;

				for (int j = 0; j < lineNumbers.size(); j++) {
					start = GetterUtil.getInteger(lineNumbers.get(j)) + diff;

					if ((j != 0) && (start > (end + 1))) {
						sb.append("\n");
					}

					end = GetterUtil.getInteger(lineNumbers.get(j + 1)) + diff;

					sb.append(_getContent(content, start, end));

					j++;
				}

				newParameterValue = StringUtil.replaceFirst(
					parameterValue, "() -> {", "() -> {\n" + sb.toString());
			}
			else {
				StringBundler sb = new StringBundler();
	
				sb.append("() -> {\n");

				int start = 0;
				int end = 0;

				for (int j = 0; j < lineNumbers.size(); j++) {
					start = GetterUtil.getInteger(lineNumbers.get(j)) + diff;

					if ((j != 0) && (start > (end + 1))) {
						sb.append("\n");
					}

					end = GetterUtil.getInteger(lineNumbers.get(j + 1)) + diff;

					sb.append(_getContent(content, start, end));
	
					j++;
				}
	
				sb.append("\n");
	
				sb.append("return ");
				sb.append(parameterValue);
				sb.append(";\n");
				sb.append("}\n");
	
				newParameterValue = sb.toString();
			}
		}

		String newCall = StringUtil.replaceLast(
			call, parameterValue, newParameterValue);

		content = StringUtil.replaceFirst(content, call, newCall, pos);

		for (int j = lineNumbers.size() - 1; j > 0; j--) {
			int start = GetterUtil.getInteger(lineNumbers.get(j - 1)) + diff;
			int end = GetterUtil.getInteger(lineNumbers.get(j)) + diff;

			String s = _getContent(content, start, end);

			content = StringUtil.replaceFirst(
				content, s, "", SourceUtil.getLineStartPos(content, start)  - 1);

			j--;
		}

		return content;

		/*
		int lineNumber2 = GetterUtil.getInteger(parts[3]);
		int lineNumber3 = GetterUtil.getInteger(parts[4]);

		String assigmentCall = _getContent(content, lineNumber1, lineNumber2);

		int i = lineNumber3;

		while (true) {
			String line = SourceUtil.getLine(content, i);

			if (line == null) {
				return content;
			}

			if (!Objects.equals(StringUtil.trim(line), ").setParameter(")) {
				i++;

				continue;
			}

			int x = SourceUtil.getLineStartPos(content, i);
			int y = content.indexOf(").", x);

			List<String> parameters = JavaSourceUtil.getParameterList(content.substring(y + 2));

			for (int j = 0; j < parameters.size(); j++) {
				String parameter = parameters.get(j);

				Pattern pattern = Pattern.compile(
					"(\\W|\\A)" + varName + "(\\W|\\Z)");

				Matcher matcher = pattern.matcher(parameter);

				while (matcher.find()) {
					if (ToolsUtil.isInsideQuotes(parameter, matcher.start() + 1)) {
						continue;
					}

					if (j == 0) {
						return content;
					}

					StringBundler sb = new StringBundler();

					sb.append("() -> {\n");
					sb.append(assigmentCall);
					sb.append("\n");
					sb.append("return ");
					sb.append(parameter);
					sb.append(";\n");
					sb.append("}\n");

					int z = content.indexOf(").", y + 1);

					String call = content.substring(x, z);

					String newCall = StringUtil.replaceLast(
						call, parameter, sb.toString());

					content = StringUtil.replaceFirst(
						content, call, newCall, x);

					content = StringUtil.replaceFirst(
						content, assigmentCall, "",
						SourceUtil.getLineStartPos(content, lineNumber1) - 1);

					return content;
				}
			}

			i++;
		}
		*/
	}

	private String _fixPart2(String content, String[] parts, int diff) {
		String variableName = parts[1];

		int lineNumber1 = GetterUtil.getInteger(parts[2]) + diff;
		int lineNumber2 = GetterUtil.getInteger(parts[3]) + diff;
		int lineNumber3 = GetterUtil.getInteger(parts[4]) + diff;
		int lineNumber4 = GetterUtil.getInteger(parts[5]) + diff;

		String builderStatement = _getContent(
			content, lineNumber1, lineNumber2);

		if (!builderStatement.endsWith(").build();\n")) {
			return content;
		}

		String methodCall = _getContent(content, lineNumber3, lineNumber4);

		int i = methodCall.indexOf(".");
		int j = methodCall.indexOf("(");

		String methodName = StringUtil.trim(methodCall.substring(i + 1, j));

		List<String> parameters = JavaSourceUtil.getParameterList(methodCall);

		if (methodName.equals("setParameter")) {
			String p1 = parameters.get(0);

			String p2 = parameters.get(1);

			String trimmed = StringUtil.trim(p2);

			if (trimmed.equals(variableName) ||
				trimmed.matches(variableName + "\\W.*") ||
				trimmed.matches(".*\\W" + variableName) ||
				trimmed.matches(".*\\W" + variableName + "\\W.")) {

				return content;
			}

			if (p1.equals("\"mvcRenderCommandName\"")) {
				methodName = "setMVCRenderCommandName";

				parameters.clear();

				parameters.add(p2);
			}
			else if (p1.equals("\"mvcPath\"")) {
				methodName = "setMVCPath";

				parameters.clear();

				parameters.add(p2);
			}
			else if (p1.equals("\"redirect\"")) {
				methodName = "setRedirect";

				parameters.clear();

				parameters.add(p2);
			}
			else if (p1.equals("ActionRequest.ACTION_NAME")) {
				methodName = "setActionName";

				parameters.clear();

				parameters.add(p2);
			}
		}

		StringBundler sb = new StringBundler();

		sb.append(").");
		sb.append(methodName);
		sb.append("(");

		for (String parameter : parameters) {
			sb.append(parameter);
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		String newBuilderStatement = StringUtil.replaceLast(
			builderStatement, ").build();", sb.toString() + ").build();");

		content = StringUtil.replaceFirst(
			content, methodCall, newBuilderStatement,
			SourceUtil.getLineStartPos(content, lineNumber3) - 1);

		content = StringUtil.replaceFirst(
			content, builderStatement, "",
			SourceUtil.getLineStartPos(content, lineNumber1) - 1);

		return content;
	}

	@Override
	protected void postFormat() throws CheckstyleException, IOException {
		_processCheckstyle(_ungeneratedFiles.toArray(new File[0]));

		_ungeneratedFiles.clear();

		String currentFileName = "";

		String prefix = "PART5";

		List<String> messages = new ArrayList<>();

		for (SourceFormatterMessage sourceFormatterMessage :
				_sourceFormatterMessages) {

			String fileName = sourceFormatterMessage.getFileName();

			String message = sourceFormatterMessage.getMessage();

			if (!message.startsWith(prefix)) {
				processMessage(fileName, sourceFormatterMessage.getMessage());

				printError(fileName, sourceFormatterMessage.toString());

				continue;
			}

			if (!currentFileName.equals(fileName)) {
				if (Validator.isNotNull(currentFileName)) {
					_fixFile(currentFileName, messages, prefix);
				}

				currentFileName = fileName;

				messages.clear();
			}

			messages.add(message);
		}

		if (Validator.isNotNull(currentFileName)) {
			_fixFile(currentFileName, messages, prefix);
		}
	}

	@Override
	protected void preFormat() throws CheckstyleException {
		SourceFormatterArgs sourceFormatterArgs = getSourceFormatterArgs();

		_checkstyleLogger = new CheckstyleLogger(
			sourceFormatterArgs.getBaseDirName());
		_checkstyleConfiguration = CheckstyleUtil.getConfiguration(
			"checkstyle.xml", getPropertiesMap(), sourceFormatterArgs);
	}

	private String[] _getPluginExcludes(String pluginDirectoryName) {
		return new String[] {
			pluginDirectoryName + "**/model/*Clp.java",
			pluginDirectoryName + "**/model/impl/*BaseImpl.java",
			pluginDirectoryName + "**/model/impl/*Model.java",
			pluginDirectoryName + "**/model/impl/*ModelImpl.java",
			pluginDirectoryName + "**/service/**/service/*Service.java",
			pluginDirectoryName + "**/service/**/service/*ServiceClp.java",
			pluginDirectoryName + "**/service/**/service/*ServiceFactory.java",
			pluginDirectoryName + "**/service/**/service/*ServiceUtil.java",
			pluginDirectoryName + "**/service/**/service/*ServiceWrapper.java",
			pluginDirectoryName + "**/service/**/service/ClpSerializer.java",
			pluginDirectoryName +
				"**/service/**/service/messaging/*ClpMessageListener.java",
			pluginDirectoryName +
				"**/service/**/service/persistence/*Finder.java",
			pluginDirectoryName +
				"**/service/**/service/persistence/*Util.java",
			pluginDirectoryName + "**/service/base/*ServiceBaseImpl.java",
			pluginDirectoryName + "**/service/base/*ServiceClpInvoker.java",
			pluginDirectoryName + "**/service/http/*JSONSerializer.java",
			pluginDirectoryName + "**/service/http/*ServiceHttp.java",
			pluginDirectoryName + "**/service/http/*ServiceJSON.java",
			pluginDirectoryName + "**/service/http/*ServiceSoap.java",
			pluginDirectoryName + "**/tools/templates/**"
		};
	}

	private Collection<String> _getPluginJavaFiles(String[] includes)
		throws IOException {

		Collection<String> fileNames = new TreeSet<>();

		String[] excludes = _getPluginExcludes(StringPool.BLANK);

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	private Collection<String> _getPortalJavaFiles(String[] includes)
		throws IOException {

		Collection<String> fileNames = new TreeSet<>();

		String[] excludes = {
			"**/*_IW.java", "**/counter/service/**",
			"**/model/impl/*Model.java", "**/model/impl/*ModelImpl.java",
			"**/portal/service/**", "**/portal-client/**",
			"**/portal-web/test/**/*Test.java", "**/test/*-generated/**"
		};

		for (String directoryName : getPluginsInsideModulesDirectoryNames()) {
			excludes = ArrayUtil.append(
				excludes, _getPluginExcludes("**" + directoryName));
		}

		fileNames.addAll(getFileNames(excludes, includes));

		excludes = new String[] {
			"**/portal-client/**", "**/tools/ext_tmpl/**", "**/*_IW.java",
			"**/test/**/*PersistenceTest.java"
		};
		includes = new String[] {
			"**/com/liferay/portal/kernel/service/ServiceContext*.java",
			"**/model/BaseModel.java", "**/model/impl/BaseModelImpl.java",
			"**/portal-test/**/portal/service/**/*.java",
			"**/portal-test-integration/**/portal/service/**/*.java",
			"**/service/*.java", "**/service/configuration/**/*.java",
			"**/service/http/*HttpTest.java", "**/service/http/*SoapTest.java",
			"**/service/http/TunnelUtil.java", "**/service/impl/*.java",
			"**/service/jms/*.java", "**/service/permission/*.java",
			"**/service/persistence/BasePersistence.java",
			"**/service/persistence/BatchSession*.java",
			"**/service/persistence/*FinderImpl.java",
			"**/service/persistence/*Query.java",
			"**/service/persistence/impl/*.java",
			"**/portal-impl/test/**/*.java", "**/util-bridges/**/*.java"
		};

		fileNames.addAll(getFileNames(excludes, includes));

		return fileNames;
	}

	private synchronized void _processCheckstyle(File file)
		throws CheckstyleException, IOException {

		_ungeneratedFiles.add(file);

		if (_ungeneratedFiles.size() == CheckstyleUtil.BATCH_SIZE) {
			_processCheckstyle(_ungeneratedFiles.toArray(new File[0]));

			_ungeneratedFiles.clear();
		}
	}

	private void _processCheckstyle(File[] files)
		throws CheckstyleException, IOException {

		if (ArrayUtil.isEmpty(files)) {
			return;
		}

		_sourceFormatterMessages.addAll(
			processCheckstyle(
				_checkstyleConfiguration, _checkstyleLogger, files));
	}

	private static final String[] _INCLUDES = {"**/*.java"};

	private Configuration _checkstyleConfiguration;
	private CheckstyleLogger _checkstyleLogger;
	private final Set<SourceFormatterMessage> _sourceFormatterMessages =
		new TreeSet<>();
	private final List<File> _ungeneratedFiles = new ArrayList<>();

}