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

package com.liferay.content.dashboard.web.internal.display.context;

import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardAdminDisplayContext {

	public ContentDashboardAdminDisplayContext(
		Http http, Language language,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, Portal portal,
		SearchContainer<ContentDashboardItem<?>> searchContainer) {

		_http = http;
		_language = language;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portal = portal;
		_searchContainer = searchContainer;

		_currentURL = String.valueOf(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse));
	}

	public List<DropdownItem> getDropdownItems(
		ContentDashboardItem<?> contentDashboardItem) {

		Locale locale = _portal.getLocale(_liferayPortletRequest);

		return DropdownItemList.of(
			() -> {
				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(_liferayPortletRequest);

				if (!contentDashboardItem.isViewURLEnabled(
						httpServletRequest)) {

					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setHref(
					_getURLWithBackURL(
						contentDashboardItem.getViewURL(httpServletRequest)));
				dropdownItem.setLabel(_language.get(locale, "view"));

				return dropdownItem;
			},
			() -> {
				HttpServletRequest httpServletRequest =
					_portal.getHttpServletRequest(_liferayPortletRequest);

				if (!contentDashboardItem.isEditURLEnabled(
						httpServletRequest)) {

					return null;
				}

				DropdownItem dropdownItem = new DropdownItem();

				dropdownItem.setHref(
					_getURLWithBackURL(
						contentDashboardItem.getEditURL(httpServletRequest)));
				dropdownItem.setLabel(_language.get(locale, "edit"));

				return dropdownItem;
			});
	}

	public SearchContainer<ContentDashboardItem<?>> getSearchContainer() {
		return _searchContainer;
	}

	public Integer getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			_liferayPortletRequest, "status", WorkflowConstants.STATUS_ANY);

		return _status;
	}

	private String _getURLWithBackURL(String url) {
		String backURL = ParamUtil.getString(_liferayPortletRequest, "backURL");

		if (Validator.isNotNull(backURL)) {
			return _http.setParameter(url, "p_l_back_url", backURL);
		}

		return _http.setParameter(url, "p_l_back_url", _currentURL);
	}

	private final String _currentURL;
	private final Http _http;
	private final Language _language;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Portal _portal;
	private final SearchContainer<ContentDashboardItem<?>> _searchContainer;
	private Integer _status;

}