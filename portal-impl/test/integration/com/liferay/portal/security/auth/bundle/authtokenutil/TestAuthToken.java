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

package com.liferay.portal.security.auth.bundle.authtokenutil;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.auth.AuthToken;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = AuthToken.class
)
public class TestAuthToken implements AuthToken {

	@Override
	public void addCSRFToken(
		HttpServletRequest httpServletRequest,
		LiferayPortletURL liferayPortletURL) {

		liferayPortletURL.setParameter("p_auth", "TEST_TOKEN");
	}

	@Override
	public void addPortletInvocationToken(
		HttpServletRequest httpServletRequest,
		LiferayPortletURL liferayPortletURL) {

		liferayPortletURL.setParameter(
			"p_p_auth", "TEST_TOKEN_BY_PLID_AND_PORTLET_ID");
	}

	@Override
	public void checkCSRFToken(
		HttpServletRequest httpServletRequest, String origin) {
	}

	@Override
	public String getToken(HttpServletRequest httpServletRequest) {
		return "TEST_TOKEN";
	}

	@Override
	public String getToken(
		HttpServletRequest httpServletRequest, long plid, String portletId) {

		return "TEST_TOKEN_BY_PLID_AND_PORTLET_ID";
	}

	@Override
	public boolean isValidPortletInvocationToken(
		HttpServletRequest httpServletRequest, Layout layout, Portlet portlet) {

		String tokenValue = httpServletRequest.getParameter("p_p_auth");

		return "VALID_PORTLET_INVOCATION_TOKEN".equals(tokenValue);
	}

}