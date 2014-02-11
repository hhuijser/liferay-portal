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

package com.liferay.portal.tools.sourceformatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author André de Oliveira
 */
abstract class ImportsFormatter {

	protected String format(String imports)
		throws IOException {

		if (imports.contains("/*") || imports.contains("*/") ||
			imports.contains("//")) {

			return imports + "\n";
		}

		Set<ImportPackage> importPackages = new HashSet<ImportPackage>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));
		try {
			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {

				ImportPackage importPackage = toImportPackage(line);

				if (importPackage != null) {
					importPackages.add(importPackage);
				}

			}
		}
		finally {
			unsyncBufferedReader.close();
		}

		List<ImportPackage> importPackagesSorted = ListUtil.sort(
			new ArrayList<ImportPackage>(importPackages));

		StringBundler sb = new StringBundler(3 * importPackagesSorted.size());

		ImportPackage previous = null;

		for (ImportPackage current : importPackagesSorted) {

			if (!current.staysTogetherWith(previous)) {
				sb.append("\n");
			}

			sb.append(current.getLine());
			sb.append("\n");

			previous = current;
		}

		return sb.toString();
	}

	protected abstract ImportPackage toImportPackage(String line);

}