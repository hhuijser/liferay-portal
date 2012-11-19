/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.patcher;

import java.io.File;

import java.util.Properties;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 */
public class PatcherUtil {

	public static boolean applyPatch(File patch) {
		return getPatcher().applyPatch(patch);
	}

	public static String[] getFixedIssues() {
		return getPatcher().getFixedIssues();
	}

	public static String[] getInstalledPatches() {
		return getPatcher().getInstalledPatches();
	}

	public static Patcher getPatcher() {
		return _patcher;
	}

	public static File getPatchesFolder() {
		return getPatcher().getPatchesFolder();
	}

	public static Properties getProperties() {
		return getPatcher().getProperties();
	}

	public void setPatcher(Patcher patcher) {
		_patcher = patcher;
	}

	private static Patcher _patcher;

}