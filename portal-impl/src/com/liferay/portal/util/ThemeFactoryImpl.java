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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ThemeFactory;

/**
 * @author Harrison Schueler
 */
public class ThemeFactoryImpl implements ThemeFactory {

	public String getDefaultRegularThemeId(long companyId)
		throws SystemException {

		String defaultRegularThemeId = PrefsPropsUtil.getString(
			companyId, com.liferay.portal.kernel.util.
				PropsKeys.DEFAULT_REGULAR_THEME_ID);

		return PortalUtil.getJsSafePortletId(defaultRegularThemeId);
	}

	public String getDefaultWapThemeId(long companyId) throws SystemException {
		String defaultWapThemeId = PrefsPropsUtil.getString(
			companyId, com.liferay.portal.kernel.util.
				PropsKeys.DEFAULT_WAP_THEME_ID);

		return PortalUtil.getJsSafePortletId(defaultWapThemeId);
	}

}