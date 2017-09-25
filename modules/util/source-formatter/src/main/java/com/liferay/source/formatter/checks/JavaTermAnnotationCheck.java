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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.parser.JavaTerm;

/**
 * @author Peter Shin
 */
public class JavaTermAnnotationCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		if (!StringUtil.startsWith(
				StringUtil.trim(javaTerm.getContent()), StringPool.AT)) {

			return javaTerm.getContent();
		}

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(javaTerm.getContent()));

		String firstLine = null;
		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (firstLine == null) {
				firstLine = line;
			}

			if (Validator.isNull(line) &&
				StringUtil.equals(sb.toString(), firstLine + "\n")) {

				continue;
			}

			sb.append(line);
			sb.append("\n");
		}

		return sb.toString();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS, JAVA_METHOD, JAVA_VARIABLE};
	}

}