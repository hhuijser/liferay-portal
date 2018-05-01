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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.tooling.java.imports.formatter.JavaClassUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public class ClassUtil extends JavaClassUtil {

	public static String getClassName(Object object) {
		if (object == null) {
			return null;
		}

		Class<?> clazz = object.getClass();

		return clazz.getName();
	}

	public static String getParentPath(
		ClassLoader classLoader, String className) {

		if (_log.isDebugEnabled()) {
			_log.debug("Class name " + className);
		}

		if (!className.endsWith(_CLASS_EXTENSION)) {
			className += _CLASS_EXTENSION;
		}

		className = StringUtil.replace(
			className, CharPool.PERIOD, CharPool.SLASH);

		className = StringUtil.replace(className, "/class", _CLASS_EXTENSION);

		URL url = classLoader.getResource(className);

		String path = null;

		try {
			path = url.getPath();

			URI uri = new URI(path);

			String scheme = uri.getScheme();

			if (path.contains(StringPool.EXCLAMATION) &&
				((scheme == null) || (scheme.length() <= 1))) {

				if (!path.startsWith(StringPool.SLASH)) {
					path = StringPool.SLASH + path;
				}
			}
			else {
				path = uri.getPath();

				if (path == null) {
					path = url.getFile();
				}
			}
		}
		catch (URISyntaxException urise) {
			path = url.getFile();
		}

		if (ServerDetector.isJBoss() || ServerDetector.isWildfly()) {
			if (path.startsWith("file:") && !path.startsWith("file:/")) {
				path = path.substring(5);

				path = "file:/".concat(path);

				path = StringUtil.replace(path, "%5C", StringPool.SLASH);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Path " + path);
		}

		int pos = path.indexOf(className);

		String parentPath = path.substring(0, pos);

		if (parentPath.startsWith("jar:")) {
			parentPath = parentPath.substring(4);
		}

		if (parentPath.startsWith("file:/")) {
			parentPath = parentPath.substring(6);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Parent path " + parentPath);
		}

		return parentPath;
	}

	public static boolean isSubclass(Class<?> a, Class<?> b) {
		if (a == b) {
			return true;
		}

		if ((a == null) || (b == null)) {
			return false;
		}

		for (Class<?> x = a; x != null; x = x.getSuperclass()) {
			if (x == b) {
				return true;
			}

			if (b.isInterface()) {
				Class<?>[] interfaceClasses = x.getInterfaces();

				for (Class<?> interfaceClass : interfaceClasses) {
					if (isSubclass(interfaceClass, b)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isSubclass(Class<?> a, String s) {
		if ((a == null) || (s == null)) {
			return false;
		}

		String name = a.getName();

		if (name.equals(s)) {
			return true;
		}

		for (Class<?> x = a; x != null; x = x.getSuperclass()) {
			name = x.getName();

			if (name.equals(s)) {
				return true;
			}

			Class<?>[] interfaceClasses = x.getInterfaces();

			for (Class<?> interfaceClass : interfaceClasses) {
				if (isSubclass(interfaceClass, s)) {
					return true;
				}
			}
		}

		return false;
	}

	private static final String _CLASS_EXTENSION = ".class";

	private static final Log _log = LogFactoryUtil.getLog(ClassUtil.class);

}