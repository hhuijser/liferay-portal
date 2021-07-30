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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class XMLServiceFinderNameCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		if (!fileName.endsWith("/service.xml")) {
			return content;
		}

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		for (Element entityElement :
				(List<Element>)rootElement.elements("entity")) {

			String entityName = entityElement.attributeValue("name");

			for (Element finderElement :
					(List<Element>)entityElement.elements("finder")) {

				String finderName = finderElement.attributeValue("name");

				List<Map<String, String>> finderColumns = new ArrayList<>();

				for (Element finderColumnElement :
						(List<Element>)finderElement.elements(
							"finder-column")) {

					Map<String, String> attributesMap = new LinkedHashMap<>();

					String comparator = finderColumnElement.attributeValue(
						"comparator");

					if (Validator.isNotNull(comparator)) {
						attributesMap.put(
							"comparator",
							finderColumnElement.attributeValue("comparator"));
					}

					attributesMap.put(
						"name", finderColumnElement.attributeValue("name"));

					finderColumns.add(attributesMap);
				}

				String[] splitFinderName = finderName.split(
					StringPool.UNDERLINE);

				if (splitFinderName.length < finderColumns.size()) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Finder name '", entityName, "#", finderName,
							"' should be combined by finder colume names with ",
							"delimiter '_'"));
				}
			}
		}

		return content;
	}

	private static final Map<String, String> _comparatorNamesMap =
		HashMapBuilder.put(
			"!=", "Not"
		).put(
			"&gt;", "Gt"
		).put(
			"&lt;", "Lt"
		).put(
			"is", "Is"
		).put(
			"LIKE", "Like"
		).build();

}