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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class TLDSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		content = trimContent(content, false);

		Matcher matcher = _TYPE_PATTERN.matcher(content);

		while (matcher.find()) {
			String type = matcher.group(1);

			if (_PRIMITIVE_CLASS_NAMES.contains(type) ||
				(type.indexOf(CharPool.PERIOD) != -1)) {

				continue;
			}

			String beforeMatch = content.substring(0, matcher.start());

			int lineCount = StringUtil.count(beforeMatch, "\n") + 1;

			processErrorMessage(
				fileName,
				"Use fully qualified classType: " + fileName + " " + lineCount);
		}

		return StringUtil.replace(content, "\n\n\n", "\n\n");
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {"**/WEB-INF/tld/**"};

		return getFileNames(excludes, getIncludes());
	}

	private static final String[] _INCLUDES = new String[] {"**/*.tld"};

	private static final Set<String> _PRIMITIVE_CLASS_NAMES = new HashSet<>(
		Arrays.asList(
			boolean.class.getName(), byte.class.getName(), char.class.getName(),
			short.class.getName(), int.class.getName(), long.class.getName(),
			float.class.getName(), double.class.getName()));

	private static final Pattern _TYPE_PATTERN = Pattern.compile(
		"<type>([\\w\\.]*)</type>");

}