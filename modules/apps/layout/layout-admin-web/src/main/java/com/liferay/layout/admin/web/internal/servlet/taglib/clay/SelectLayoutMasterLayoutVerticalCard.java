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

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class SelectLayoutMasterLayoutVerticalCard implements VerticalCard {

	public SelectLayoutMasterLayoutVerticalCard(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getCssClass() {
		return "add-layout-action-option card-interactive " +
			"card-interactive-primary";
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		Map<String, String> data = new HashMap<>();

		try {
			PortletURL addLayoutURL = _renderResponse.createRenderURL();

			addLayoutURL.setParameter(
				"mvcRenderCommandName", "/layout_admin/add_layout");

			String redirect = ParamUtil.getString(
				_httpServletRequest, "redirect");

			addLayoutURL.setParameter("redirect", redirect);

			long groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

			addLayoutURL.setParameter("groupId", String.valueOf(groupId));

			long selPlid = ParamUtil.getLong(_httpServletRequest, "selPlid");

			addLayoutURL.setParameter("selPlid", String.valueOf(selPlid));

			boolean privateLayout = ParamUtil.getBoolean(
				_httpServletRequest, "privateLayout");

			addLayoutURL.setParameter(
				"privateLayout", String.valueOf(privateLayout));

			addLayoutURL.setParameter("type", LayoutConstants.TYPE_COLLECTION);

			String collectionPK = ParamUtil.getString(
				_httpServletRequest, "collectionPK");

			addLayoutURL.setParameter("collectionPK", collectionPK);

			String collectionType = ParamUtil.getString(
				_httpServletRequest, "collectionType");

			addLayoutURL.setParameter("collectionType", collectionType);

			addLayoutURL.setParameter(
				"masterLayoutPlid",
				String.valueOf(_layoutPageTemplateEntry.getPlid()));
			addLayoutURL.setWindowState(LiferayWindowState.POP_UP);

			data.put("data-add-layout-url", addLayoutURL.toString());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return data;
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getImageSrc() {
		return _layoutPageTemplateEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectLayoutMasterLayoutVerticalCard.class);

	private final HttpServletRequest _httpServletRequest;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}