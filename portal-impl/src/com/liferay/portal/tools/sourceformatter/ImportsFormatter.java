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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class ImportsFormatter {

	static String formatImports(String imports, int classStartPos)
		throws IOException {

		if (imports.contains("/*") || imports.contains("*/") ||
			imports.contains("//")) {

			return imports + "\n";
		}

		HashSet<ImportPackage> noDuplicates = new HashSet<ImportPackage>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(imports));
		try {
	
			String line = null;
	
			while ((line = unsyncBufferedReader.readLine()) != null) {
				ImportPackage importPackage = 
					ImportPackageFactoryUtil.create(line);
	
				if (importPackage != null) {
					noDuplicates.add(importPackage);
				}
			}
			
		} 
		finally {
			unsyncBufferedReader.close();
		}
 
		List<ImportPackage> sorted = ListUtil.sort(
			new ArrayList<ImportPackage>(noDuplicates));

		StringBundler sb = new StringBundler(3 * sorted.size());

		String temp = null;

		for (int i = 0; i < sorted.size(); i++) {
			ImportPackage importPackage = sorted.get(i);

			String s = importPackage.getLine();

			int firstDot = s.indexOf(".");
			int secondDot = s.indexOf(".", firstDot + 1);
			int upToDot = secondDot != -1 ? secondDot : firstDot;

			String packageLevel = s.substring(classStartPos, upToDot);

			if ((i != 0) && !packageLevel.equals(temp)) {
				sb.append("\n");
			}

			temp = packageLevel;

			sb.append(s);
			sb.append("\n");
		}

		return sb.toString();
	}

}