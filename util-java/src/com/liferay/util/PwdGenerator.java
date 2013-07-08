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

package com.liferay.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.SecureRandom;

/**
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public class PwdGenerator {

	public static final String KEY1 = "0123456789";

	public static final String KEY2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String KEY3 = "abcdefghijklmnopqrstuvwxyz";

	public static String getPassword() {
		return getPassword(8);
	}

	public static String getPassword(int length) {
		return _getPassword(false, KEY1 + KEY2 + KEY3, length, true);
	}

	public static String getPassword(String key, int length) {
		return getPassword(key, length, true);
	}

	public static String getPassword(
		String key, int length, boolean useAllKeys) {

		return _getPassword(false, key, length, useAllKeys);
	}

	public static String getPinNumber() {
		return _getPassword(false, KEY1, 4, true);
	}

	public static String getSecurePassword() {
		return getSecurePassword(8);
	}

	public static String getSecurePassword(int length) {
		return _getPassword(true, KEY1 + KEY2 + KEY3, length, true);
	}

	public static String getSecurePassword(String key, int length) {
		return getSecurePassword(key, length, true);
	}

	public static String getSecurePassword(
		String key, int length, boolean useAllKeys) {

		return _getPassword(true, key, length, useAllKeys);
	}

	private static String _getPassword(
		boolean secure, String key, int length, boolean useAllKeys) {

		int keysCount = 0;

		if (key.contains(KEY1)) {
			keysCount++;
		}

		if (key.contains(KEY2)) {
			keysCount++;
		}

		if (key.contains(KEY3)) {
			keysCount++;
		}

		if (keysCount > length) {
			if (_log.isWarnEnabled()) {
				_log.warn("Length is too short");
			}

			length = keysCount;
		}

		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			sb.append(key.charAt((int)(_random(secure) * key.length())));
		}

		String password = sb.toString();

		if (!useAllKeys) {
			return password;
		}

		boolean invalidPassword = false;

		if (key.contains(KEY1)) {
			if (Validator.isNull(StringUtil.extractDigits(password))) {
				invalidPassword = true;
			}
		}

		if (key.contains(KEY2)) {
			if (password.equals(password.toLowerCase())) {
				invalidPassword = true;
			}
		}

		if (key.contains(KEY3)) {
			if (password.equals(password.toUpperCase())) {
				invalidPassword = true;
			}
		}

		if (invalidPassword) {
			return _getPassword(secure, key, length, useAllKeys);
		}

		return password;
	}

	private static double _random(boolean secure) {
		try {
			if (secure) {
				if (_secureRandom == null) {
					_secureRandom = new SecureRandom();
				}

				return _secureRandom.nextDouble();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return Math.random();
	}

	private static Log _log = LogFactoryUtil.getLog(PwdGenerator.class);

	private static SecureRandom _secureRandom;

}