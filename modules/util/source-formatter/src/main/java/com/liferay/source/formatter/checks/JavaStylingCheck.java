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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaStylingCheck extends StylingCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

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

		content = _fix(content);

		return formatStyling(content);
	}

	private String _fix(String content) {
		Pattern pattern = Pattern.compile("JSONArray (\\w+)\\s+=\\s+JSONFactoryUtil\\.createJSONArray\\(\\);");

		Matcher matcher = pattern.matcher(content);

		outerLoop:
		while (matcher.find()) {
			String varName = matcher.group(1);

			int x = matcher.end();

			List<String> params = new ArrayList<>();

			while (true) {
				String s = StringUtil.trim(content.substring(x));

				if (!s.startsWith(varName + ".put(")) {
					if (!params.isEmpty()) {
						String s2 = content.substring(matcher.start(), x);

						StringBundler sb = new StringBundler();

						sb.append("JSONArray ");
						sb.append(varName);
						sb.append(" = JSONUtil.put(");

						for (String param : params) {
							sb.append(param);
							sb.append(", ");
						}

						sb.setIndex(sb.index() -1);

						sb.append(");");

						content = StringUtil.replaceFirst(content, "import com.liferay.portal.kernel.json.JSONArray;", "import com.liferay.portal.kernel.json.JSONArray;\nimport com.liferay.portal.kernel.json.JSONUtil;");
						//System.out.println("================================");
						//System.out.println(s2);
						//System.out.println("===========");
						//System.out.println(sb.toString());

						//return content;
						return StringUtil.replaceFirst(content, s2, sb.toString());
					}

					break;
				}

				List<String> list = JavaSourceUtil.getParameterList(s);

				if (list.size() != 1) {
					continue outerLoop;
				}

				params.add(list.get(0));

				int y = x;

				while (true) {
					y = content.indexOf(")", y + 1);

					if (y == -1) {
						continue outerLoop;
					}

					if (ToolsUtil.isInsideQuotes(content, y)) {
						continue;
					}

					String asdf = content.substring(x, y + 1);

					int level = getLevel(asdf);

					if (level == 0) {
						char nextChar = content.charAt(y + 1);

						if (nextChar != CharPool.SEMICOLON) {
							continue outerLoop;
						}

						x = y + 2;

						break;
					}
				}
			}
		}

		return content;
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