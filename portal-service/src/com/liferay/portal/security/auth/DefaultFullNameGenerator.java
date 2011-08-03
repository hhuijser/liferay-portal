/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.impl.UserLocalServiceImpl;

/**
 * @author Michael C. Han
 */
public class DefaultFullNameGenerator implements FullNameGenerator {

	public String getFullName(
		String firstName, String middleName, String lastName) {

		StringBundler sb = new StringBundler();

		if (Validator.isNull(middleName)) {
			middleName = "";
		}

		if (Validator.isNull(lastName)) {
			lastName = "";
		}

		int totalLength = firstName.length() + middleName.length() +
							lastName.length() + 2;

		boolean tooLong = false;

		while (totalLength > 75) {
			if (middleName.length() > 1) {
				middleName = middleName.substring(0,1);
			}
			else if (firstName.length() > 1) {
				firstName = firstName.substring(0,1);
			}
			else {
				lastName = lastName.substring(0, 71);
			}

			totalLength = firstName.length() + middleName.length() +
							lastName.length() + 2;

			tooLong = true;
		}

		sb.append(firstName);

		if (!middleName.equals("")) {
			sb.append(StringPool.SPACE);
			sb.append(middleName);
		}

		if (!lastName.equals("")) {
			sb.append(StringPool.SPACE);
			sb.append(lastName);
		}

		if (tooLong) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Full name length is greater than 75 characters. " +
						"Generating truncated version.");
			}
		}

		return sb.toString();
	}

	public String[] splitFullName(String fullName) {
		String firstName = StringPool.BLANK;
		String middleName = StringPool.BLANK;
		String lastName = StringPool.BLANK;

		if (Validator.isNotNull(fullName)) {
			String[] name = StringUtil.split(fullName, CharPool.SPACE);

			firstName = name[0];
			middleName = StringPool.BLANK;
			lastName = name[name.length - 1];

			if (name.length > 2) {
				for (int i = 1; i < name.length - 1; i++) {
					if (Validator.isNull(name[i].trim())) {
						continue;
					}

					if (i != 1) {
						middleName += StringPool.SPACE;
					}

					middleName += name[i].trim();
				}
			}
		}
		else {
			firstName = GetterUtil.getString(firstName, lastName);
			lastName = firstName;
		}

		return new String[] {firstName, middleName, lastName};
	}

	private static Log _log = LogFactoryUtil.getLog(DefaultFullNameGenerator.class);
}