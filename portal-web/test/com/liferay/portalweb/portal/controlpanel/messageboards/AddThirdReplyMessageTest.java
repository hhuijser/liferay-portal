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

package com.liferay.portalweb.portal.controlpanel.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddThirdReplyMessageTest extends BaseTestCase {
	public void testAddThirdReplyMessage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Message Boards",
			RuntimeVariables.replace("Message Boards"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
			selenium.getText("//td[2]/a/strong"));
		selenium.clickAt("//td[2]/a/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//td[2]/a/strong"));
		selenium.clickAt("//td[2]/a/strong",
			RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=T\u00e9st M\u00e9ssag\u00e9",
			RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText(
				"//div[6]/table/tbody/tr[1]/td[2]/div[1]/ul/li[2]/span/a/span"));
		selenium.clickAt("//div[6]/table/tbody/tr[1]/td[2]/div[1]/ul/li[2]/span/a/span",
			RuntimeVariables.replace("Reply"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[@id='cke_34_label' and .='Source']"));
		selenium.clickAt("//span[@id='cke_34_label' and .='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/textarea");
		selenium.type("//td[@id='cke_contents__162_editor']/textarea",
			RuntimeVariables.replace("This is a third reply message."));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[@id='cke_34_label' and .='Source']"));
		selenium.clickAt("//span[@id='cke_34_label' and .='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__162_editor']/iframe");
		selenium.waitForText("//body", "This is a third reply message.");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"exact:RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText(
				"//div[7]/table/tbody/tr[1]/td[2]/div[1]/div/a/strong"));
		assertEquals(RuntimeVariables.replace("This is a third reply message."),
			selenium.getText("//div[7]/table/tbody/tr[1]/td[2]/div[2]"));
	}
}