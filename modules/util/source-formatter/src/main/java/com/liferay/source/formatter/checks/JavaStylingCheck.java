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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaStylingCheck extends BaseStylingCheck {

	private String _fixReturn(String content) {
		Pattern pattern = Pattern.compile(
			"\n(\t+)((PortletURL )?(\\w+) =) PortletURLBuilder.create");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String indent = matcher.group(1);
			String varName = matcher.group(4);

			int x = content.indexOf(
				"\n" + indent + ").build()", matcher.start());

			if (x == -1) {
				return content;
			}

			int lineNumber = SourceUtil.getLineNumber(content, x + 1);

			String nextLine = SourceUtil.getLine(content, lineNumber + 2);

			String trimmedNextLine = StringUtil.trim(nextLine);

			if (trimmedNextLine.equals("return " + varName + ";")) {
				content = StringUtil.replaceFirst(
					content, nextLine, "",
					SourceUtil.getLineStartPos(content, lineNumber + 1));

				content = StringUtil.replaceFirst(
					content, matcher.group(2), "return", matcher.start());

				return content;
			}
		}

		return content;
	}

	private String _fixStringValueof(String content) {
		int x = -1;

		while (true) {
			x = content.indexOf("\t).setParameter(\n", x + 1);

			if (x == -1) {
				break;
			}

			List<String> parameters = JavaSourceUtil.getParameterList(content.substring(x + 3));

			if (parameters.size() != 2) {
				continue;
			}

			String parameter = StringUtil.trim(parameters.get(1));

			if (parameter.startsWith("String.valueOf(")) {
				String newParameter = StringUtil.replaceFirst(parameter, "String.valueOf(", "");

				newParameter = StringUtil.replaceLast(newParameter, ")", "");

				return StringUtil.replaceFirst(content, parameter, newParameter, x);
			}
		}

		return content;
	}

	private String _fixOrder(
		String fileName, String content, String methodName,
		String... otherMethodNames) {

		Pattern pattern = Pattern.compile("\n(\t+)\\)\\." + methodName + "\\(");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			int x = content.indexOf(
				"\n" + matcher.group(1) + ").", matcher.end() - 1);

			if (x == -1) {
				System.out.println("NOT GOOD1: " + fileName);
			}

			int lineNumber = SourceUtil.getLineNumber(content, x + 1);

			String line = SourceUtil.getLine(content, lineNumber);

			String trimmedLine = StringUtil.trim(line);

			boolean match = false;

			for (String otherMethodName : otherMethodNames) {
				if (trimmedLine.startsWith(")." + otherMethodName + "(")) {
					match = true;

					break;
				}
			}

			if (!match) {
				continue;
			}

			int y = content.indexOf("\n" + matcher.group(1) + ").", x + 1);

			if (y == -1) {
				System.out.println("NOT GOOD2: " + fileName);
			}

			String part1 = content.substring(matcher.start(), x);
			String part2 = content.substring(x, y);

			content = StringUtil.replaceFirst(content, part2, part1, matcher.start() - 1);
			content = StringUtil.replaceFirst(content, part1, part2, matcher.start() - 1);

			return content;
		}

		return content;
	}

	private String _fixOrder(String fileName, String content) {

		//setWindowState
		//setSecure
		//setPortletMode
		//setParameter
		//setParameters
		//setRedirect
		//setActionName
		//setMVCRenderCommandName
		//setMVCPath

		content = _fixOrder(
			fileName, content, "setWindowState", "setSecure", "setPortletMode",
			"setParameter", "setParameters", "setRedirect", "setActionName",
			"setMVCRenderCommandName", "setMVCPath");
		content = _fixOrder(
			fileName, content, "setSecure", "setPortletMode",
			"setParameter", "setParameters", "setRedirect", "setActionName",
			"setMVCRenderCommandName", "setMVCPath");
		content = _fixOrder(
			fileName, content, "setPortletMode", "setParameter",
			"setParameters", "setRedirect", "setActionName",
			"setMVCRenderCommandName", "setMVCPath");
		content = _fixOrder(
			fileName, content, "setParameter", "setRedirect", "setActionName",
			"setMVCRenderCommandName", "setMVCPath");
		content = _fixOrder(
			fileName, content, "setParameters", "setRedirect", "setActionName",
			"setMVCRenderCommandName", "setMVCPath");
		content = _fixOrder(
			fileName, content, "setRedirect", "setActionName",
			"setMVCRenderCommandName", "setMVCPath");
		content = _fixOrder(
			fileName, content, "setActionName", "setMVCRenderCommandName",
			"setMVCPath");
		content = _fixOrder(
			fileName, content, "setMVCRenderCommandName", "setMVCPath");

		return content;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _fixStringValueof(content);

		content = _fixReturn(content);

		content = _fixOrder(fileName, content);

		if (content.contains("$\n */")) {
			content = StringUtil.replace(content, "$\n */", "$\n *\n */");
		}

		content = _fixAuthorNames(content);

		content = StringUtil.replace(
			content, " final static ", " static final ");

		content = StringUtil.replace(
			content, new String[] {";\n/**", ";;\n", "\n */\npackage "},
			new String[] {";\n\n/**", ";\n", "\n */\n\npackage "});

		Matcher matcher = _incorrectSynchronizedPattern.matcher(content);

		content = matcher.replaceAll("$1$3 $2");

		matcher = _incorrectJavadocPattern.matcher(content);

		content = matcher.replaceAll("$1*$3");

		return formatStyling(content);
	}

	private String _fixAuthorNames(String content) {
		content = content.replaceFirst(
			"(@author +)Adolfo P.rez", "$1Adolfo P\u00e9rez");
		content = content.replaceFirst(
			"(@author +)Alejandro Hern.ndez", "$1Alejandro Hern\u00e1ndez");
		content = content.replaceFirst(
			"(@author +)Alejandro Tard.n", "$1Alejandro Tard\u00edn");
		content = content.replaceFirst(
			"(@author +)Ambr.n Chaudhary", "$1Ambr\u00edn Chaudhary");
		content = content.replaceFirst(
			"(@author +)Andr. de Oliveira", "$1Andr\u00e9 de Oliveira");
		content = content.replaceFirst(
			"(@author +)Bal.zs S.fr.ny-Kovalik",
			"$1Bal\u00e1zs S\u00e1fr\u00e1ny-Kovalik");
		content = content.replaceFirst(
			"(@author +)Carlos Sierra Andr.s", "$1Carlos Sierra Andr\u00e9s");
		content = content.replaceFirst(
			"(@author +)Cristina Gonz.lez", "$1Cristina Gonz\u00e1lez");
		content = content.replaceFirst(
			"(@author +)Cristina Rodr.guez", "$1Cristina Rodr\u00edguez");
		content = content.replaceFirst(
			"(@author +)Eduardo Garc.a", "$1Eduardo Garc\u00eda");
		content = content.replaceFirst(
			"(@author +)Eduardo P.rez", "$1Eduardo P\u00e9rez");
		content = content.replaceFirst(
			"(@author +)Herv. M.nage", "$1Herv\u00e9 M\u00e9nage");
		content = content.replaceFirst(
			"(@author +)In.cio Nery", "$1In\u00e1cio Nery");
		content = content.replaceFirst(
			"(@author +)Istv.n Andr.s D.zsi",
			"$1Istv\u00e1n Andr\u00e1s D\u00e9zsi");
		content = content.replaceFirst(
			"(@author +)Iv.n Zaera", "$1Iv\u00e1n Zaera");
		content = content.replaceFirst(
			"(@author +)Jorge Gonz.lez", "$1Jorge Gonz\u00e1lez");
		content = content.replaceFirst(
			"(@author +)Jos. .ngel Jim.nez",
			"$1Jos\u00e9 \u00c1ngel Jim\u00e9nez");
		content = content.replaceFirst(
			"(@author +)Jos. Manuel Navarro", "$1Jos\u00e9 Manuel Navarro");
		content = content.replaceFirst(
			"(@author +)Jos. Mar.a Mu.oz", "$1Jos\u00e9 Mar\u00eda Mu\u00f1oz");
		content = content.replaceFirst(
			"(@author +)Juan Fern.ndez", "$1Juan Fern\u00e1ndez");
		content = content.replaceFirst(
			"(@author +)Juan Gonz.lez", "$1Juan Gonz\u00e1lez");
		content = content.replaceFirst(
			"(@author +)J.rgen Kappler", "$1J\u00fcrgen Kappler");
		content = content.replaceFirst(
			"(@author +)L.szl. Csontos", "$1L\u00e1szl\u00f3 Csontos");
		content = content.replaceFirst(
			"(@author +)Levente Hud.k", "$1Levente Hud\u00e1k");
		content = content.replaceFirst(
			"(@author +)Manuel de la Pe.a", "$1Manuel de la Pe\u00f1a");
		content = content.replaceFirst(
			"(@author +)Mariano .lvaro S.iz",
			"$1Mariano \u00c1lvaro S\u00e1iz");
		content = content.replaceFirst(
			"(@author +)M.t. Thurz.", "$1M\u00e1t\u00e9 Thurz\u00f3");
		content = content.replaceFirst(
			"(@author +)P.ter Alius", "$1P\u00e9ter Alius");
		content = content.replaceFirst(
			"(@author +)P.ter Borkuti", "$1P\u00e9ter Borkuti");
		content = content.replaceFirst(
			"(@author +)Raymond Aug.", "$1Raymond Aug\u00e9");
		content = content.replaceFirst(
			"(@author +)Roberto D.az", "$1Roberto D\u00edaz");
		content = content.replaceFirst(
			"(@author +)Rub.n Pulido", "$1Rub\u00e9n Pulido");
		content = content.replaceFirst(
			"(@author +)Sarai D.az", "$1Sarai D\u00edaz");
		content = content.replaceFirst(
			"(@author +)V.ctor Gal.n", "$1V\u00edctor Gal\u00e1n");
		content = content.replaceFirst(
			"(@author +)Zolt.n Tak.cs", "$1Zolt\u00e1n Tak\u00e1cs");
		content = content.replaceFirst(
			"(@author +)Zsolt Ol.h", "$1Zsolt Ol\u00e1h");
		content = content.replaceFirst(
			"(@author +)Zsolt Szab.", "$1Zsolt Szab\u00f3");

		return content;
	}

	private static final Pattern _incorrectJavadocPattern = Pattern.compile(
		"(\n([\t ]*)/\\*)(\n\\2 \\*)");
	private static final Pattern _incorrectSynchronizedPattern =
		Pattern.compile("([\n\t])(synchronized) (private|public|protected)");

}