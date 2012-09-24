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

package com.liferay.portalweb.portal.permissions.webcontent.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertViewWCLTest extends BaseTestCase {
	public void testMember_AssertViewWCL() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content List Permissions Page",
			RuntimeVariables.replace("Web Content List Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//section"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[1]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[2]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[3]/a"));
		assertFalse(selenium.isTextPresent(
				"You do not have the roles required to access this portlet."));
	}
}