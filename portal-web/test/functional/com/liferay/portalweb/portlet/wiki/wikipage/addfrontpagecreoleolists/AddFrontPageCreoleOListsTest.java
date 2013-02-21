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

package com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleolists;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFrontPageCreoleOListsTest extends BaseTestCase {
	public void testAddFrontPageCreoleOLists() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This page is empty. Edit it to add some text."),
			selenium.getText("//div[@class='wiki-body']/div/a"));
		selenium.clickAt("//div[@class='wiki-body']/div/a",
			RuntimeVariables.replace(
				"This page is empty. Edit it to add some text."));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace(
				"# Item1\n## Subitem1a\n# Item2\n## Subitem2a\n## Subitem2b\n# Item3\n## Subitem3a\n## Subitem3b\n## Subitem3c"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Item1 Subitem1a Item2 Subitem2a Subitem2b Item3 Subitem3a Subitem3b Subitem3c"),
			selenium.getText("//div[@class='wiki-body']/ol"));
		assertEquals(RuntimeVariables.replace("Item1 Subitem1a"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ol/li)"));
		assertEquals(RuntimeVariables.replace("Subitem1a"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ol/li/ol)"));
		assertEquals(RuntimeVariables.replace("Item2 Subitem2a Subitem2b"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ol/li[2])"));
		assertEquals(RuntimeVariables.replace("Subitem2a Subitem2b"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ol/li[2]/ol)"));
		assertEquals(RuntimeVariables.replace("Subitem2a"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ol/li[2]/ol/li)"));
		assertEquals(RuntimeVariables.replace("Subitem2b"),
			selenium.getText(
				"xPath=(//div[@class='wiki-body']/ol/li[2]/ol/li[2])"));
		assertEquals(RuntimeVariables.replace(
				"Item3 Subitem3a Subitem3b Subitem3c"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ol/li[3])"));
		assertEquals(RuntimeVariables.replace("Subitem3a Subitem3b Subitem3c"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ol/li[3]/ol)"));
		assertEquals(RuntimeVariables.replace("Subitem3a"),
			selenium.getText("xPath=(//div[@class='wiki-body']/ol/li[3]/ol/li)"));
		assertEquals(RuntimeVariables.replace("Subitem3b"),
			selenium.getText(
				"xPath=(//div[@class='wiki-body']/ol/li[3]/ol/li[2])"));
		assertEquals(RuntimeVariables.replace("Subitem3c"),
			selenium.getText(
				"xPath=(//div[@class='wiki-body']/ol/li[3]/ol/li[3])"));
	}
}