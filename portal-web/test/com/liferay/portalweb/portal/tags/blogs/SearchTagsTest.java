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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchTagsTest extends BaseTestCase {
	public void testSearchTags() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Blogs Tags Test Page");
		selenium.clickAt("link=Blogs Tags Test Page",
			RuntimeVariables.replace("Blogs Tags Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_33_keywords']",
			RuntimeVariables.replace("\"selenium1 liferay1\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Tags Blog Entry1 Title"));
		assertFalse(selenium.isTextPresent("Tags Blog Entry2 Title"));
		assertFalse(selenium.isTextPresent("Tags Blog Entry3 Title"));
		selenium.type("//input[@id='_33_keywords']",
			RuntimeVariables.replace("\"selenium2 liferay2\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Tags Blog Entry1 Title"));
		assertFalse(selenium.isTextPresent("Tags Blog Entry2 Title"));
		assertTrue(selenium.isTextPresent("Tags Blog Entry3 Title"));
		selenium.type("//input[@id='_33_keywords']",
			RuntimeVariables.replace("\"selenium3 liferay3\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Tags Blog Entry1 Title"));
		assertTrue(selenium.isTextPresent("Tags Blog Entry2 Title"));
		assertTrue(selenium.isTextPresent("Tags Blog Entry3 Title"));
		selenium.type("//input[@id='_33_keywords']",
			RuntimeVariables.replace("\"selenium4 liferay4\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Tags Blog Entry1 Title"));
		assertTrue(selenium.isTextPresent("Tags Blog Entry2 Title"));
		assertFalse(selenium.isTextPresent("Tags Blog Entry3 Title"));
	}
}