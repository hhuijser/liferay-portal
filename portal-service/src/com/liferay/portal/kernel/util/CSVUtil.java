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

package com.liferay.portal.kernel.util;

/**
 * @author Samuel Kong
 */
public class CSVUtil {

	public static String encode(Object obj) {
		Class<?> clazz = obj.getClass();

		if (!clazz.isArray()) {
			return encode(String.valueOf(obj));
		}

		Object[] array = (Object[])obj;

		if (array.length == 0) {
			return null;
		}

		String s = toString(array, StringPool.COMMA);

		return encode(s);
	}

	public static String encode(String s) {
		if (s == null) {
			return null;
		}

		if ((s.indexOf(CharPool.COMMA) < 0) &&
			(s.indexOf(CharPool.QUOTE) < 0) &&
			(s.indexOf(CharPool.NEW_LINE) < 0) &&
			(s.indexOf(CharPool.RETURN) < 0)) {

			return s;
		}

		s = StringUtil.replace(s, StringPool.QUOTE, StringPool.DOUBLE_QUOTE);

		return StringPool.QUOTE.concat(s.concat(StringPool.QUOTE));
	}

	public static String toString(Object[] array, String delimiter) {
		if (ArrayUtil.isEmpty(array)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2 * array.length - 1);

		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

}