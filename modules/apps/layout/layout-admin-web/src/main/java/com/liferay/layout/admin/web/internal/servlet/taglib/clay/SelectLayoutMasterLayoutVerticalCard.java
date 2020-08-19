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
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
	public Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();

		try {
			String redirect = ParamUtil.getString(
				_httpServletRequest, "redirect");

			long groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

			long selPlid = ParamUtil.getLong(_httpServletRequest, "selPlid");

			boolean privateLayout = ParamUtil.getBoolean(
				_httpServletRequest, "privateLayout");

			String collectionPK = ParamUtil.getString(
				_httpServletRequest, "collectionPK");

			String collectionType = ParamUtil.getString(
				_httpServletRequest, "collectionType");

			PortletURL addLayoutURL = PortletURLBuilder.createRenderURL(
				_renderResponse
			).setParameter(
				"mvcRenderCommandName", "/layout/add_layout"
			).setParameter(
				"redirect", redirect
			).setParameter(
				"groupId", String.valueOf(groupId)
			).setParameter(
				"selPlid", String.valueOf(selPlid)
			).setParameter(
				"privateLayout", String.valueOf(privateLayout)
			).setParameter(
				"type", LayoutConstants.TYPE_COLLECTION
			).setParameter(
				"collectionPK", collectionPK
			).setParameter(
				"collectionType", collectionType
			).setParameter(
				"masterLayoutPlid",
				String.valueOf(_layoutPageTemplateEntry.getPlid())
			).setWindowState(
				LiferayWindowState.POP_UP
			).build();

			data.put("add-layout-url", addLayoutURL.toString());
		}
		catch (Exception exception) {
		}

		return data;
	}

	@Override
	public String getElementClasses() {
		return "add-layout-action-option card-interactive " +
			"card-interactive-primary";
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

	private final HttpServletRequest _httpServletRequest;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}