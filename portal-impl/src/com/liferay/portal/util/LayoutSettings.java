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

package com.liferay.portal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class LayoutSettings {

	public static void addLayoutSetting(String type) {
		_layoutSettingsMap.put(type, new LayoutSettings(type));
	}

	public static LayoutSettings getInstance(Layout layout) {
		return getInstance(layout.getType());
	}

	public static LayoutSettings getInstance(String type) {
		return _layoutSettingsMap.get(type);
	}

	public static Map<String, LayoutSettings> getLayoutSettingsMap() {
		return _layoutSettingsMap;
	}

	public String[] getConfigurationActionDelete() {
		return _configurationActionDelete;
	}

	public String[] getConfigurationActionUpdate() {
		return _configurationActionUpdate;
	}

	public String getEditPage() {
		return _editPage;
	}

	public String getType() {
		return _type;
	}

	public String getURL() {
		return _url;
	}

	public String getURL(Map<String, String> variables) {
		return StringUtil.replace(
			_url, StringPool.DOLLAR_AND_OPEN_CURLY_BRACE,
			StringPool.CLOSE_CURLY_BRACE, variables);
	}

	public String getViewPage() {
		return _viewPage;
	}

	public boolean isFirstPageable() {
		return _firstPageable;
	}

	public boolean isParentable() {
		return _parentable;
	}

	public boolean isSitemapable() {
		return _sitemapable;
	}

	public boolean isURLFriendliable() {
		return _urlFriendliable;
	}

	private LayoutSettings(String type) {
		_type = type;

		Filter filter = new Filter(type);

		_configurationActionDelete = StringUtil.split(
			GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_DELETE, filter)));
		_configurationActionUpdate = StringUtil.split(
			GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE, filter)));
		_editPage = GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_EDIT_PAGE, filter));
		_firstPageable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_FIRST_PAGEABLE, filter));
		_parentable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_PARENTABLE, filter), true);
		_sitemapable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_SITEMAPABLE, filter), true);
		_url = GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_URL, filter));
		_urlFriendliable = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_URL_FRIENDLIABLE, filter), true);
		_viewPage = GetterUtil.getString(
			PropsUtil.get(PropsKeys.LAYOUT_VIEW_PAGE, filter));
	}

	private static final Map<String, LayoutSettings> _layoutSettingsMap =
		new HashMap<String, LayoutSettings>();

	static {
		_layoutSettingsMap.put(
			LayoutConstants.TYPE_CONTROL_PANEL,
			new LayoutSettings(LayoutConstants.TYPE_CONTROL_PANEL));

		for (String type : PropsValues.LAYOUT_TYPES) {
			_layoutSettingsMap.put(type, new LayoutSettings(type));
		}
	}

	private final String[] _configurationActionDelete;
	private final String[] _configurationActionUpdate;
	private final String _editPage;
	private final boolean _firstPageable;
	private final boolean _parentable;
	private final boolean _sitemapable;
	private final String _type;
	private final String _url;
	private final boolean _urlFriendliable;
	private final String _viewPage;

}