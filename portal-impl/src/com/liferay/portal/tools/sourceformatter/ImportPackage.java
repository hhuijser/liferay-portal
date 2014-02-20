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

/**
 * @author Carlos Sierra Andrés
 */
public class ImportPackage implements Comparable<ImportPackage> {

	@Override
	public int compareTo(ImportPackage importPackage) {
		return _import.compareTo(importPackage._import);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ImportPackage)) {
			return false;
		}

		ImportPackage importPackage = (ImportPackage)obj;

		if (_static != importPackage._static) {
			return false;
		}

		return _import.equals(importPackage._import);
	}

	public String getLine() {
		return _line;
	}

	public String getPackageLevel() {

		String s = _import;

		int pos = s.indexOf(".");

		pos = s.indexOf(".", pos + 1);

		if (pos == -1) {
			pos = s.indexOf(".");
		}

		String packageLevel = s.substring(0, pos);
		return packageLevel;
	}

	@Override
	public int hashCode() {
		return _import.hashCode();
	}

	protected ImportPackage(String importString, boolean isStatic, String line)
	{
		_import = importString;
		_static = isStatic;
		_line = line;
	}

	private String _import;
	private String _line;
	private boolean _static;

}