/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FileImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jesse Rao
 * @author James Hinkey
 */
public class PropertiesConverter {

	public static void main(String[] args) {
		try {
			new PropertiesConverter(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PropertiesConverter(String[] args) throws IOException {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String propertiesDestDir = GetterUtil.getString(
			arguments.get("properties.dest.dir"));
		String propertiesFileName = GetterUtil.getString(
			arguments.get("properties.file"));
		String title = GetterUtil.getString(arguments.get("properties.title"));
		boolean toc = GetterUtil.getBoolean(arguments.get("properties.toc"));

		System.out.println("Converting " + propertiesFileName + " to HTML");

		File propertiesFile = new File(propertiesFileName);

		// Create a data model for Freemarker

		Map context = new HashMap();
		context.put("pageTitle", title);
		context.put("toc", toc);

		int pos = propertiesFileName.lastIndexOf(StringPool.SLASH);

		if (pos != -1) {
			propertiesFileName = propertiesFileName.substring(pos + 1);
		}

		context.put("propertiesFileName", propertiesFileName);

		String propertiesString = _fileUtil.read(propertiesFile);

		String[] sectionStrings = propertiesString.split("\n\n");
		List<PropertiesSection> sections = new ArrayList<PropertiesSection>(
				sectionStrings.length);

		PropertiesSection section;

		for (int i = 0; i < sectionStrings.length; i++) {
			String sectionString = StringUtil.trimLeading(
					sectionStrings[i], ' ');

			section = new PropertiesSection(sectionString);

			if (sectionString.startsWith("##")) {
				int numLines = _countLines(sectionString);

				if (numLines == 3) {
					section.setTitle(_extractTitle(section));

					sections.add(section);
				}
				else if (numLines > 3) {
					section.setComments(_extractComments(section));

					sections.add(section);
				}
				else {
					System.out.println(
							"Error: PropertiesSection should consist of 3 or " +
							"more lines:\n" +
							"##\n" +
							"## Comment(s)\n" +
							"##");

					return;
				}

			}
			else {
				section.setPropertyComments(_extractPropertyComments(section));

				section.setDefaultProperties(
					_extractDefaultProperties(section));

				section.setExampleProperties(
					_extractExampleProperties(section));

				sections.add(section);
			}

		}

		context.put("sections", sections);

		try {
			String htmlFilePath =
				new StringBundler(propertiesDestDir).append(
					StringPool.SLASH).append(propertiesFileName).append(
						".html").toString();

			System.out.println("Writing " + htmlFilePath);

			File propertiesHTMLFile = new File(htmlFilePath);

			Writer writer = new FileWriter(propertiesHTMLFile);

			// Get the Freemarker template and merge it with the data model

			try {
				FreeMarkerUtil.process(_TPL_PROPERTIES_HTML, context, writer);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			writer.flush();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public class PropertyComment {

		public PropertyComment(String comment) {
			_comment = comment;

			String[] lines = comment.split(StringPool.NEW_LINE);

			for (int i = 0; i < lines.length; i++) {
				if (lines[i].startsWith(_INDENT)) {
					_isPreFormatted = true;
					break;
				}
			}
		}

		public String getComment() {
			return _comment;
		}

		public boolean isPreFormatted() {
			return _isPreFormatted;
		}

		private boolean _isPreFormatted;
		private String _comment;

	}

	private void _appendParagraphAsPropertyComment(
		List<PropertyComment> propertyComments, StringBundler paragraph) {

		if (paragraph.length() > 0) {
			propertyComments.add(new PropertyComment(paragraph.toString()));
		}
	}

	private int _countLines(String str) {
		String[] lines = str.split("\r\n|\r|\n");
		return lines.length;
	}

	private List<String> _extractComments(PropertiesSection section) {

		List<String> sectionComments = new ArrayList<String>();
		StringBundler paragraph = new StringBundler();

		String[] lines = section.getText().split(StringPool.NEW_LINE);

		for (int i = 0; i < lines.length; i++) {

			if (lines[i].trim().startsWith("## ")) {
				String line = lines[i].substring(2);

				paragraph.append(line.trim());
			}

			if (lines[i].trim().length() < 3) {
				if (paragraph.length() == 0) {
					continue;
				}
				else {
					sectionComments.add(paragraph.toString());
					paragraph = new StringBundler();
				}
			}
		}

		return sectionComments;
	}

	private String _extractDefaultProperties(PropertiesSection section) {

		StringBundler defaultProperties = new StringBundler();

		String[] lines = section.getText().split(StringPool.NEW_LINE);

		boolean prevIsDefaultProperty = false;

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			if (!prevIsDefaultProperty) {
				if (line.startsWith("#") || line.startsWith(_INDENT + "#")) {
					continue;
				}
				else {
					prevIsDefaultProperty = true;

					defaultProperties.append(line + StringPool.NEW_LINE);
				}
			}
			else {
				if (line.startsWith("#") || line.startsWith(_INDENT + "#")) {
					prevIsDefaultProperty = false;

					continue;
				}
				else {
					defaultProperties.append(line + StringPool.NEW_LINE);
				}
			}
		}

		return defaultProperties.toString();
	}

	private String _extractExampleProperties(PropertiesSection section) {
		StringBundler exampleProperties = new StringBundler();

		boolean prevIsExample = false;
		String[] lines = section.getText().split(StringPool.NEW_LINE);

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			if (!prevIsExample) {
				if (line.startsWith(_INDENT + "# ") ||
					line.trim().equals("#")) {

					continue;
				}
				else if (line.startsWith(_INDENT + "#")) {
					prevIsExample = true;

					String exampleProperty =
						line.replaceFirst("#", StringPool.BLANK) +
							StringPool.NEW_LINE;

					exampleProperties.append(exampleProperty);
				}
			}
			else {
				if (!line.trim().startsWith("#")) {
					prevIsExample = false;

					continue;
				}
				else {
					String exampleProperty =
						line.replaceFirst("#", StringPool.BLANK) +
							StringPool.NEW_LINE;

					exampleProperties.append(exampleProperty);
				}
			}
		}

		return exampleProperties.toString();
	}

	private List<PropertyComment> _extractPropertyComments(
		PropertiesSection propertiesSection) {

		List<PropertyComment> propertyComments =
			new ArrayList<PropertyComment>();

		StringBundler paragraph = new StringBundler();

		boolean prevIsPreformatted = false;

		// Add property comments as paragraphs. Stop on the first property
		// assignment.

		String[] lines = propertiesSection.getText().split(StringPool.NEW_LINE);

		for (int i = 0; i < lines.length; i++) {
			String line = StringUtil.trimTrailing(lines[i]);

			if (line.startsWith(_INDENT_DOUBLE + "#")) {
				break;
			}
			else if (line.trim().startsWith("# " + _INDENT)) {

				if (prevIsPreformatted) {
					paragraph.append(
							line.trim().replaceFirst("#", StringPool.BLANK));
				}
				else {
					_appendParagraphAsPropertyComment(
						propertyComments, paragraph);

					paragraph = new StringBundler(
						line.trim().replaceFirst("#", StringPool.BLANK));
				}

				paragraph.append(StringPool.NEW_LINE);
				prevIsPreformatted = true;
			}
			else if (line.trim().startsWith("# ")) {

				if (prevIsPreformatted) {
					_appendParagraphAsPropertyComment(
						propertyComments, paragraph);

					paragraph = new StringBundler(
						line.trim().replaceFirst("#", StringPool.BLANK).trim());
				}
				else {
					if (paragraph.length() > 0) {
						paragraph.append(StringPool.SPACE);
					}

					paragraph.append(
							line.replaceFirst("#", StringPool.BLANK).trim());
				}

				paragraph.append(StringPool.NEW_LINE);
				prevIsPreformatted = false;
			}
			else {
				String trimmedLine = line.trim();

				if (trimmedLine.startsWith("#")) {
					if (trimmedLine.length() < 2) {
						_appendParagraphAsPropertyComment(
							propertyComments, paragraph);

						paragraph = new StringBundler();

						continue;
					}
					else {
						_appendParagraphAsPropertyComment(
							propertyComments, paragraph);

						break;
					}
				}
				else {
					_appendParagraphAsPropertyComment(
						propertyComments, paragraph);

					break;
				}
			}
		}

		return propertyComments;
	}

	private String _extractTitle(PropertiesSection section) {
		String[] lines = section.getText().split(StringPool.NEW_LINE);

		String title = null;

		if ((lines != null) && (lines.length > 1)) {
			title = lines[1].replaceFirst("##", StringPool.BLANK).trim();
		}

		return title;
	}

	private static final String _INDENT =
			new StringBundler(StringPool.SPACE).append(StringPool.SPACE).append(
					StringPool.SPACE).append(StringPool.SPACE).toString();
	private static final String _INDENT_DOUBLE = _INDENT + _INDENT;
	private static final String _TPL_PROPERTIES_HTML =
		"com/liferay/portal/tools/dependencies/properties_html.ftl";

	private static FileImpl _fileUtil = FileImpl.getInstance();

}