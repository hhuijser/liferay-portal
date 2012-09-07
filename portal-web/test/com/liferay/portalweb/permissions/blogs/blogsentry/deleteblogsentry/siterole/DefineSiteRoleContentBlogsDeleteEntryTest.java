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

package com.liferay.portalweb.permissions.blogs.blogsentry.deleteblogsentry.siterole;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineSiteRoleContentBlogsDeleteEntryTest extends BaseTestCase {
	public void testDefineSiteRoleContentBlogsDeleteEntry()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Siterole"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Siterole Name"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-name_row-1']/a"));
		assertEquals(RuntimeVariables.replace("Site"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-type_row-1']/a"));
		selenium.clickAt("//td[@id='_128_ocerSearchContainer_col-name_row-1']/a",
			RuntimeVariables.replace("Roles Siterole Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Siterole Name"),
			selenium.getText("//h1[@class='header-title']"));
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("label=Blogs"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked(
				"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryDELETE']"));
		selenium.check(
			"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryDELETE']");
		assertTrue(selenium.isChecked(
				"//input[@value='com.liferay.portlet.blogs.model.BlogsEntryDELETE']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Summary"),
			selenium.getText("//section[@id='portlet_128']/div/div/div/h3"));
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-resource-set_row-1']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-resource_row-1']"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-action_row-1']"));
	}
}