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

package com.liferay.marketplace.app.manager.web.internal.util;

import com.liferay.marketplace.constants.MarketplaceStorePortletKeys;
import com.liferay.marketplace.model.App;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ryan Park
 */
public class MarketplaceAppDisplay extends BaseAppDisplay {

	public MarketplaceAppDisplay() {
		_app = null;
	}

	public MarketplaceAppDisplay(App app) {
		_app = app;
	}

	public App getApp() {
		return _app;
	}

	@Override
	public String getDescription() {
		return _app.getDescription();
	}

	@Override
	public String getDisplayURL(MimeResponse mimeResponse) {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			mimeResponse
		).setMVCPath(
			"/view_modules.jsp"
		).setParameter(
			"app", String.valueOf(_app.getAppId())
		).build();

		return portletURL.toString();
	}

	@Override
	public String getIconURL(HttpServletRequest httpServletRequest) {
		return _app.getIconURL();
	}

	@Override
	public String getStoreURL(HttpServletRequest httpServletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PortletURL portletURL = PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					httpServletRequest,
					MarketplaceStorePortletKeys.MARKETPLACE_STORE,
					themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
			).setParameter(
				"appEntryId", String.valueOf(_app.getRemoteAppId())
			).setWindowState(
				LiferayWindowState.MAXIMIZED
			).build();

			return portletURL.toString();
		}
		catch (Exception exception) {
		}

		return StringPool.BLANK;
	}

	@Override
	public String getTitle() {
		return _app.getTitle();
	}

	@Override
	public String getVersion() {
		return _app.getVersion();
	}

	@Override
	public boolean isRequired() {
		return _app.isRequired();
	}

	private final App _app;

}