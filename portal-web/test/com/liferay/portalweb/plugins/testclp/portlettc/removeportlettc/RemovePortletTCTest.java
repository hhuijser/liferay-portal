/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.plugins.testclp.portlettc.removeportlettc;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemovePortletTCTest extends BaseTestCase {
	public void testRemovePortletTC() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Test CLP Test Page");
		selenium.clickAt("link=Test CLP Test Page",
			RuntimeVariables.replace("Test CLP Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//img[@alt='Remove']");
		selenium.click("//img[@alt='Remove']");
		selenium.waitForConfirmation(
			"Are you sure you want to remove this component?");
		selenium.waitForElementNotPresent("//section");
		assertTrue(selenium.isElementNotPresent("//section"));
	}
}