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

package com.liferay.source.formatter.checks.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.portal.tools.java.parser.JavaParser;
import com.liferay.portal.xml.SAXReaderFactory;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * @author Hugo Huijser
 */
public class SourceUtil {

	public static boolean containsUnquoted(String s, String text) {
		int x = -1;

		while (true) {
			x = s.indexOf(text, x + 1);

			if (x == -1) {
				return false;
			}

			if (!ToolsUtil.isInsideQuotes(s, x)) {
				return true;
			}
		}
	}

	public static String getAbsolutePath(File file) {
		return getAbsolutePath(file.toPath());
	}

	public static String getAbsolutePath(Path filePath) {
		filePath = filePath.toAbsolutePath();

		filePath = filePath.normalize();

		return StringUtil.replace(
			filePath.toString(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

	public static String getAbsolutePath(String fileName) {
		return getAbsolutePath(Paths.get(fileName));
	}

	public static String getIndent(String s) {
		StringBundler sb = new StringBundler(s.length());

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != CharPool.TAB) {
				break;
			}

			sb.append(CharPool.TAB);
		}

		return sb.toString();
	}

	public static String getLine(String content, int lineNumber) {
		int nextLineStartPos = getLineStartPos(content, lineNumber);

		if (nextLineStartPos == -1) {
			return null;
		}

		int nextLineEndPos = content.indexOf(
			CharPool.NEW_LINE, nextLineStartPos);

		if (nextLineEndPos == -1) {
			return content.substring(nextLineStartPos);
		}

		return content.substring(nextLineStartPos, nextLineEndPos);
	}

	public static int getLineNumber(String content, int pos) {
		return StringUtil.count(content, 0, pos, CharPool.NEW_LINE) + 1;
	}

	public static int getLineStartPos(String content, int lineNumber) {
		if (lineNumber <= 0) {
			return -1;
		}

		if (lineNumber == 1) {
			return 0;
		}

		int x = -1;

		for (int i = 1; i < lineNumber; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
	}

	public static int[] getMultiLinePositions(
		String content, Pattern multiLinePattern) {

		List<Integer> multiLinePositions = new ArrayList<>();

		Matcher matcher = multiLinePattern.matcher(content);

		while (matcher.find()) {
			multiLinePositions.add(getLineNumber(content, matcher.start()));
			multiLinePositions.add(getLineNumber(content, matcher.end() - 1));
		}

		return ArrayUtil.toIntArray(multiLinePositions);
	}

	public static JSONObject getPortalJSONObject(
			String dirName, SourceFormatterExcludes sourceFormatterExcludes,
			int maxLineLength)
		throws IOException {

		JSONObject portalJSONObject = new JSONObjectImpl();

		ExecutorService executorService = Executors.newFixedThreadPool(1);

		List<Future<Tuple>> futures = new ArrayList<>();

		List<String> fileNames = SourceFormatterUtil.scanForFiles(
			dirName, new String[0], new String[] {"**/*.java"},
			sourceFormatterExcludes, true);

		for (String fileName : fileNames) {
			if (!fileName.contains("/com/liferay/")) {
				continue;
			}

			Future<Tuple> future = executorService.submit(
				new Callable<Tuple>() {

					@Override
					public Tuple call() throws Exception {
						return _getClassTuple(fileName, maxLineLength);
					}

				});

			futures.add(future);
		}

		for (Future<Tuple> future : futures) {
			try {
				Tuple tuple = future.get(1, TimeUnit.MINUTES);

				if (tuple != null) {
					portalJSONObject.put(
						(String)tuple.getObject(0),
						(JSONObject)tuple.getObject(1));
				}
			}
			catch (Exception exception) {
				future.cancel(true);
			}
		}

		return portalJSONObject;
	}

	public static String getRootDirName(String absolutePath) {
		while (true) {
			int x = absolutePath.lastIndexOf(CharPool.SLASH);

			if (x == -1) {
				return StringPool.BLANK;
			}

			absolutePath = absolutePath.substring(0, x);

			File file = new File(absolutePath + "/portal-impl");

			if (file.exists()) {
				return absolutePath;
			}
		}
	}

	public static String getTitleCase(
		String s, boolean allowDash, String... exceptions) {

		if (!allowDash) {
			s = StringUtil.replace(s, CharPool.DASH, CharPool.SPACE);
		}

		String[] words = s.split("\\s+");

		if (ArrayUtil.isEmpty(words)) {
			return s;
		}

		StringBundler sb = new StringBundler(words.length * 2);

		outerLoop:
		for (int i = 0; i < words.length; i++) {
			String word = words[i];

			if (Validator.isNull(word)) {
				continue;
			}

			for (String exception : exceptions) {
				if (StringUtil.equalsIgnoreCase(exception, word)) {
					sb.append(exception);
					sb.append(CharPool.SPACE);

					continue outerLoop;
				}
			}

			if ((i != 0) && (i != words.length)) {
				String lowerCaseWord = StringUtil.toLowerCase(word);

				if (ArrayUtil.contains(_ARTICLES, lowerCaseWord) ||
					ArrayUtil.contains(_CONJUNCTIONS, lowerCaseWord) ||
					ArrayUtil.contains(_PREPOSITIONS, lowerCaseWord)) {

					sb.append(lowerCaseWord);
					sb.append(CharPool.SPACE);

					continue;
				}
			}

			if (Character.isUpperCase(word.charAt(0))) {
				sb.append(word);
			}
			else {
				sb.append(StringUtil.upperCaseFirstLetter(word));
			}

			sb.append(CharPool.SPACE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	public static boolean hasTypo(String s1, String s2) {
		if (Validator.isNull(s1) || Validator.isNull(s2) || s1.equals(s2) ||
			(s1.charAt(0) != s2.charAt(0)) ||
			(s1.charAt(s1.length() - 1) != s2.charAt(s2.length() - 1))) {

			return false;
		}

		int min = Math.min(s1.length(), s2.length());
		int diff = Math.abs(s1.length() - s2.length());

		if ((min < 5) || (diff > 1)) {
			return false;
		}

		int i = StringUtil.startsWithWeight(s1, s2);

		s1 = s1.substring(i);

		if (s1.startsWith(StringPool.UNDERLINE)) {
			return false;
		}

		s2 = s2.substring(i);

		if (s2.startsWith(StringPool.UNDERLINE)) {
			return false;
		}

		for (int j = 1;; j++) {
			if ((j > s1.length()) || (j > s2.length())) {
				return true;
			}

			if (s1.charAt(s1.length() - j) != s2.charAt(s2.length() - j)) {
				char[] chars1 = s1.toCharArray();
				char[] chars2 = s2.toCharArray();

				Arrays.sort(chars1);
				Arrays.sort(chars2);

				if (!Arrays.equals(chars1, chars2)) {
					return false;
				}

				return true;
			}
		}
	}

	public static boolean isInsideMultiLines(
		int lineNumber, int[] multiLinePositions) {

		for (int i = 0; i < (multiLinePositions.length - 1); i += 2) {
			if (lineNumber < multiLinePositions[i]) {
				return false;
			}

			if (lineNumber <= multiLinePositions[i + 1]) {
				return true;
			}
		}

		return false;
	}

	public static boolean isXML(String content) {
		try {
			readXML(content);

			return true;
		}
		catch (DocumentException documentException) {
			if (_log.isDebugEnabled()) {
				_log.debug(documentException, documentException);
			}

			return false;
		}
	}

	public static Document readXML(File file) throws DocumentException {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		return saxReader.read(file);
	}

	public static Document readXML(String content) throws DocumentException {
		SAXReader saxReader = SAXReaderFactory.getSAXReader(null, false, false);

		return saxReader.read(new UnsyncStringReader(content));
	}

	private static Tuple _getClassTuple(String fileName, int maxLineLength)
		throws Exception {

		String content = JavaParser.parse(
			new File(fileName), maxLineLength, false);

		JavaClass javaClass = null;

		try {
			javaClass = JavaClassParser.parseJavaClass(fileName, content);
		}
		catch (Exception exception) {
			return null;
		}

		String className = javaClass.getName(true);

		if (!className.startsWith("com.liferay.")) {
			return null;
		}

		JSONObject classJSONObject = new JSONObjectImpl();

		if (javaClass.hasAnnotation("Deprecated")) {
			classJSONObject.put("deprecated", true);
		}

		JSONArray extendedClassesJSONArray = _getExtendedClassesJSONArray(
			javaClass);

		if (extendedClassesJSONArray.length() > 0) {
			classJSONObject.put("extendedClassNames", extendedClassesJSONArray);
		}

		classJSONObject.put("fileName", fileName);

		JSONArray implementedClassesJSONArray = _getImplementedClassesJSONArray(
			javaClass);

		if (implementedClassesJSONArray.length() > 0) {
			classJSONObject.put(
				"implementedClassNames", implementedClassesJSONArray);
		}

		JSONArray methodsJSONArray = _getMethodsJSONArray(javaClass);

		if (methodsJSONArray.length() > 0) {
			classJSONObject.put("methods", methodsJSONArray);
		}

		return new Tuple(javaClass.getName(true), classJSONObject);
	}

	private static JSONArray _getExtendedClassesJSONArray(JavaClass javaClass) {
		JSONArray extendedClassesJSONArray = new JSONArrayImpl();

		for (String extendedClassName : javaClass.getExtendedClassNames(true)) {
			extendedClassesJSONArray.put(extendedClassName);
		}

		return extendedClassesJSONArray;
	}

	private static JSONArray _getImplementedClassesJSONArray(
		JavaClass javaClass) {

		JSONArray implementedClassesJSONArray = new JSONArrayImpl();

		for (String implementedClassName :
				javaClass.getImplementedClassNames(true)) {

			implementedClassesJSONArray.put(implementedClassName);
		}

		return implementedClassesJSONArray;
	}

	private static JSONArray _getMethodsJSONArray(JavaClass javaClass) {
		JSONArray methodsJSONArray = new JSONArrayImpl();

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod() || childJavaTerm.isPrivate()) {
				continue;
			}

			JavaMethod javaMethod = (JavaMethod)childJavaTerm;

			JSONObject methodJSONObject = new JSONObjectImpl();

			methodJSONObject.put(
				"accessModifier", javaMethod.getAccessModifier());

			if (javaMethod.hasAnnotation("Deprecated")) {
				methodJSONObject.put("deprecated", true);
			}

			methodJSONObject.put("name", javaMethod.getName());

			if (javaMethod.hasAnnotation("Override")) {
				methodJSONObject.put("override", true);
			}

			JavaSignature javaSignature = null;

			try {
				javaSignature = javaMethod.getSignature();
			}
			catch (Exception exception) {
				continue;
			}

			List<JavaParameter> parameters = javaSignature.getParameters();

			if (!parameters.isEmpty()) {
				JSONArray parametersJSONArray = new JSONArrayImpl();

				for (JavaParameter javaParameter : parameters) {
					parametersJSONArray.put(javaParameter.getParameterType());
				}

				methodJSONObject.put("parameters", parametersJSONArray);
			}

			methodJSONObject.put("returnType", javaSignature.getReturnType());

			methodsJSONArray.put(methodJSONObject);
		}

		return methodsJSONArray;
	}

	private static final String[] _ARTICLES = {"a", "an", "the"};

	private static final String[] _CONJUNCTIONS = {
		"and", "but", "for", "nor", "or", "yet"
	};

	private static final String[] _PREPOSITIONS = {
		"a", "abaft", "aboard", "about", "above", "absent", "across", "afore",
		"after", "against", "along", "alongside", "amid", "amidst", "among",
		"amongst", "an", "apropos", "apud", "around", "as", "aside", "astride",
		"at", "athwart", "atop", "barring", "before", "behind", "below",
		"beneath", "beside", "besides", "between", "beyond", "but", "by",
		"circa", "concerning", "despite", "down", "during", "except",
		"excluding", "failing", "for", "from", "given", "in", "including",
		"inside", "into", "lest", "mid", "midst", "modulo", "near", "next",
		"notwithstanding", "of", "off", "on", "onto", "opposite", "out",
		"outside", "over", "pace", "past", "per", "plus", "pro", "qua",
		"regarding", "sans", "since", "through", "throughout", "thru",
		"thruout", "till", "to", "toward", "towards", "under", "underneath",
		"unlike", "until", "unto", "up", "upon", "v", "versus", "via", "vice",
		"vs", "with", "within", "without", "worth"
	};

	private static final Log _log = LogFactoryUtil.getLog(SourceUtil.class);

}