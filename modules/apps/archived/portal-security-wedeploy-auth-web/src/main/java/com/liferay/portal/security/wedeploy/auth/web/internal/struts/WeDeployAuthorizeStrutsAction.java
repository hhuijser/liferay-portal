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

package com.liferay.portal.security.wedeploy.auth.web.internal.struts;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.wedeploy.auth.web.internal.constants.WeDeployAuthPortletKeys;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Supritha Sundaram
 */
@Component(
	immediate = true, property = "path=/portal/wedeploy/authorize",
	service = StrutsAction.class
)
public class WeDeployAuthorizeStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			sendLoginRedirect(
				httpServletRequest, httpServletResponse,
				themeDisplay.getPlid());

			return null;
		}

		String redirectURI = ParamUtil.getString(
			httpServletRequest, "redirect_uri");
		String clientId = ParamUtil.getString(httpServletRequest, "client_id");

		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				httpServletRequest, WeDeployAuthPortletKeys.WEDEPLOY_AUTH,
				themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
		).setParameter(
			"redirectURI", redirectURI
		).setParameter(
			"clientId", clientId
		).setParameter(
			"saveLastPath", Boolean.FALSE.toString()
		).setPortletMode(
			PortletMode.VIEW
		).build();

		httpServletResponse.sendRedirect(portletURL.toString());

		return null;
	}

	protected void sendLoginRedirect(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long plid)
		throws Exception {

		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				httpServletRequest, PortletKeys.LOGIN, plid,
				PortletRequest.RENDER_PHASE)
		).setParameter(
			"saveLastPath", Boolean.FALSE.toString()
		).setMVCRenderCommandName(
			"/login/login"
		).setPortletMode(
			PortletMode.VIEW
		).setWindowState(
			LiferayWindowState.MAXIMIZED
		).build();

		httpServletResponse.sendRedirect(portletURL.toString());
	}

}