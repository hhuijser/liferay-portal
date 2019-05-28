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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.util.test.LayoutTestUtil;

import org.junit.Before;

/**
 * @author Vilmos Papp
 * @author Akos Thurzo
 */
public abstract class BasePortalImplURLTestCase {

	@Before
	public void setUp() throws Exception {
		company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			company.getCompanyId());

		controlPanelLayout = LayoutLocalServiceUtil.getLayout(controlPanelPlid);

		group = GroupTestUtil.addGroup();

		privateLayout = LayoutTestUtil.addLayout(group, true);
		publicLayout = LayoutTestUtil.addLayout(group);
	}

	protected ThemeDisplay initThemeDisplay(
			Company company, Group group, Layout layout,
			String companyVirtualHostname)
		throws Exception {

		return initThemeDisplay(
			company, group, layout, companyVirtualHostname,
			companyVirtualHostname);
	}

	protected ThemeDisplay initThemeDisplay(
			Company company, Group group, Layout layout,
			String companyVirtualHostname, String serverName)
		throws Exception {

		company.setVirtualHostname(companyVirtualHostname);

		ThemeDisplay themeDisplay = new ThemeDisplay() {
			{
				setCompany(company);
				setI18nLanguageId(StringPool.BLANK);
				setLayout(layout);
				setLayoutSet(layout.getLayoutSet());
				setSecure(false);
				setServerName(serverName);
				setServerPort(8080);
				setSiteGroupId(group.getGroupId());
				setUser(TestPropsValues.getUser());
				setWidget(false);
			}
		};

		return themeDisplay;
	}

	protected static final String LOCALHOST = "localhost";

	protected static final String VIRTUAL_HOSTNAME = "test.com";

	protected Company company;
	protected Layout controlPanelLayout;

	@DeleteAfterTestRun
	protected Group group;

	protected Layout privateLayout;
	protected Layout publicLayout;

}