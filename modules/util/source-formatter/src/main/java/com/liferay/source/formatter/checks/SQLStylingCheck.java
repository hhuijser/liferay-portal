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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class SQLStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		for (String line : StringUtil.splitByLines(content)) {
			String strippedQuotesLine = stripQuotes(line, CharPool.APOSTROPHE);

			if (strippedQuotesLine.contains(StringPool.QUOTE)) {
				String newLine = StringUtil.replace(
					line, CharPool.QUOTE, CharPool.APOSTROPHE);

				return StringUtil.replace(content, line, newLine);
			}
		}

		return content;
	}

}