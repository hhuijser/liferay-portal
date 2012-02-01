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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebyconnections;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewConnectionWHContentViewableByConnectionsTest
	extends BaseTestCase {
	public void testSOUs_ViewConnectionWHContentViewableByConnections()
		throws Exception {
		selenium.open("/user/socialoffice01/home1");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Microblogs Status Update"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='_2_WAR_microblogsportlet_highlighterContent']"));
		assertEquals(RuntimeVariables.replace("You have no microblogs entry."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("//div[@id='_2_WAR_microblogsportlet_highlighterContent']",
			RuntimeVariables.replace("Update your status..."));
		assertEquals(RuntimeVariables.replace("Connections"),
			selenium.getText("link=Connections"));
		selenium.clickAt("link=Connections",
			RuntimeVariables.replace("Connections"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='activity-title']"));
		assertEquals(RuntimeVariables.replace("Microblogs"),
			selenium.getText("//nav/ul/li[contains(.,'Microblogs')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='user-name']/span"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));
		selenium.open("/web/joebloggs");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='activity-title']"));
		assertEquals(RuntimeVariables.replace("Microblogs"),
			selenium.getText("//nav/ul/li[contains(.,'Microblogs')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='user-name']/span"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));
	}
}