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

package com.liferay.portalweb.portal.dbupgrade.sampledata6010.community.membershiprequest;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserMRTest extends BaseTestCase {
	public void testAddUserMR() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Users", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Add", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("_125_prefixId", RuntimeVariables.replace("label=Mr."));
		selenium.type("_125_screenName",
			RuntimeVariables.replace("requestmembersn"));
		selenium.type("_125_emailAddress",
			RuntimeVariables.replace("requestmemberea@liferay.com"));
		selenium.type("_125_firstName",
			RuntimeVariables.replace("requestmemberfn"));
		selenium.type("_125_lastName",
			RuntimeVariables.replace("requestmemberln"));
		selenium.select("_125_birthdayMonth",
			RuntimeVariables.replace("label=April"));
		selenium.select("_125_birthdayDay", RuntimeVariables.replace("label=10"));
		selenium.select("_125_birthdayYear",
			RuntimeVariables.replace("label=1986"));
		selenium.select("_125_male", RuntimeVariables.replace("label=Male"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("requestmembersn", selenium.getValue("_125_screenName"));
		assertEquals("requestmemberea@liferay.com",
			selenium.getValue("_125_emailAddress"));
		assertEquals("requestmemberfn", selenium.getValue("_125_firstName"));
		assertEquals("requestmemberln", selenium.getValue("_125_lastName"));
	}
}