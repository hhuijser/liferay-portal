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
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class SelectGlobalTemplatesVerticalCard implements VerticalCard {

	public SelectGlobalTemplatesVerticalCard(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	@Override
	public Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();

		try {
			PortletURL addLayoutURL = PortletURLBuilder.createRenderURL(
				_renderResponse
			).setMVCRenderCommandName(
				"/layout/add_layout"
			).setParameter(
				"backURL", ParamUtil.getString(_renderRequest, "redirect")
			).setParameter(
				"selPlid", ParamUtil.getLong(_renderRequest, "selPlid")
			).setParameter(
				"privateLayout",
				ParamUtil.getBoolean(_renderRequest, "privateLayout")
			).setParameter(
				"layoutPageTemplateEntryId",
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()
			).setParameter(
				"layoutPrototypeId",
				_layoutPageTemplateEntry.getLayoutPrototypeId()
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
			"card-interactive-primary card-type-template template-card";
	}

	@Override
	public String getIcon() {
		return "page-template";
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_layoutPageTemplateEntry.getName());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}