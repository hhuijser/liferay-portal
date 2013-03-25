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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetStagingHandler;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;

import java.io.IOException;

/**
 * @author Raymond Augé
 * @author Julio Camarero
 */
public class LayoutSetBranchImpl extends LayoutSetBranchBaseImpl {

	public LayoutSetBranchImpl() {
	}

	public ColorScheme getColorScheme() throws SystemException {
		return ThemeLocalServiceUtil.getColorScheme(
			getCompanyId(), getTheme().getThemeId(), getColorSchemeId(), false);
	}

	public Group getGroup() throws PortalException, SystemException {
		return GroupLocalServiceUtil.getGroup(getGroupId());
	}

	public LayoutSet getLayoutSet() {
		if (_layoutSet != null) {
			return _layoutSet;
		}

		try {
			_layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroupId(), getPrivateLayout());

			LayoutSetStagingHandler layoutSetStagingHandler =
				LayoutStagingUtil.getLayoutSetStagingHandler(_layoutSet);

			if (layoutSetStagingHandler == null) {
				return _layoutSet;
			}

			_layoutSet = layoutSetStagingHandler.getLayoutSet();

			return _layoutSet;
		}
		catch (SystemException se) {
		}
		catch (PortalException pe) {
		}

		return _layoutSet;
	}

	public long getLiveLogoId() {
		long logoId = getLayoutSet().getLogoId();

		if (logoId == 0) {
			logoId = getLayoutSet().getLiveLogoId();
		}

		return logoId;
	}

	@Override
	public String getSettings() {
		if (_settingsProperties == null) {
			return super.getSettings();
		}
		else {
			return _settingsProperties.toString();
		}
	}

	public UnicodeProperties getSettingsProperties() {
		if (_settingsProperties == null) {
			_settingsProperties = new UnicodeProperties(true);

			try {
				_settingsProperties.load(super.getSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _settingsProperties;
	}

	public String getSettingsProperty(String key) {
		UnicodeProperties settingsProperties = getSettingsProperties();

		return settingsProperties.getProperty(key);
	}

	public Theme getTheme() throws SystemException {

		long companyId = getCompanyId();

		String themeId = ThemeFactoryUtil.getDefaultRegularThemeId(companyId);

		return ThemeLocalServiceUtil.getTheme(companyId, themeId, false);
	}

	public String getThemeSetting(String key, String device)
		throws SystemException {

		UnicodeProperties settingsProperties = getSettingsProperties();

		String value = settingsProperties.getProperty(
			ThemeSettingImpl.namespaceProperty(device, key));

		if (value != null) {
			return value;
		}

		Theme theme = null;

		boolean controlPanel = false;

		try {
			Group group = getGroup();

			controlPanel = group.isControlPanel();
		}
		catch (Exception e) {
		}

		if (controlPanel) {
			String themeId = PrefsPropsUtil.getString(
				getCompanyId(),
				PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID);

			theme = ThemeLocalServiceUtil.getTheme(
				getCompanyId(), themeId, !device.equals("regular"));
		}
		else if (device.equals("regular")) {
			theme = getTheme();
		}
		else {
			theme = getWapTheme();
		}

		value = theme.getSetting(key);

		return value;
	}

	public ColorScheme getWapColorScheme() throws SystemException {
		return ThemeLocalServiceUtil.getColorScheme(
			getCompanyId(), getWapTheme().getThemeId(), getWapColorSchemeId(),
			true);
	}

	public Theme getWapTheme() throws SystemException {

		long companyId = getCompanyId();

		String themeId = ThemeFactoryUtil.getDefaultWapThemeId(companyId);

		return ThemeLocalServiceUtil.getTheme(companyId, themeId, true);
	}

	public boolean isLayoutSetPrototypeLinkActive() {
		if (isLayoutSetPrototypeLinkEnabled() &&
			Validator.isNotNull(getLayoutSetPrototypeUuid())) {

			return true;
		}

		return false;
	}

	@Override
	public void setSettings(String settings) {
		_settingsProperties = null;

		super.setSettings(settings);
	}

	public void setSettingsProperties(UnicodeProperties settingsProperties) {
		_settingsProperties = settingsProperties;

		super.setSettings(_settingsProperties.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutSetImpl.class);

	private LayoutSet _layoutSet;
	private UnicodeProperties _settingsProperties;

}