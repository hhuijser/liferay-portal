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

package com.liferay.content.dashboard.web.internal.application.list.test;

import com.liferay.application.list.PanelApp;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class ContentDashboardAdminPanelAppTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetPortletURL() throws PortalException {
		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.USER_ID, TestPropsValues.getUserId());

		Assert.assertEquals(
			String.valueOf(TestPropsValues.getUserId()),
			_http.getParameter(
				String.valueOf(_panelApp.getPortletURL(mockHttpServletRequest)),
				"_com_liferay_content_dashboard_web_portlet_" +
					"ContentDashboardAdminPortlet_authorIds",
				false));
	}

	@Test
	public void testGetPortletURLWithMissingUser() throws PortalException {
		PortletURL portletURL = _panelApp.getPortletURL(
			_getMockHttpServletRequest());

		Assert.assertEquals(
			"0",
			_http.getParameter(
				String.valueOf(portletURL),
				"_com_liferay_content_dashboard_web_portlet_" +
					"ContentDashboardAdminPortlet_authorIds",
				false));
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private Http _http;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.web.internal.application.list.ContentDashboardAdminPanelApp"
	)
	private PanelApp _panelApp;

}