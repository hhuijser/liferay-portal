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

package com.liferay.portalweb.portlet.pollsdisplay.question.votequestion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectQuestionTest extends BaseTestCase {
	public void testSelectQuestion() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Polls Display Test Page");
		selenium.clickAt("link=Polls Display Test Page",
			RuntimeVariables.replace("Polls Display Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible("//select[@id='_86_questionId']");
		selenium.select("//select[@id='_86_questionId']",
			RuntimeVariables.replace("PD Question Title"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("PD Question Title",
			selenium.getSelectedLabel("//select[@id='_86_questionId']"));
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Polls Display Test Page");
		selenium.clickAt("link=Polls Display Test Page",
			RuntimeVariables.replace("Polls Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//div/span[1]/span/span/input"));
		assertTrue(selenium.isElementPresent("//div/span[2]/span/span/input"));
		assertTrue(selenium.isElementPresent("//div/span[3]/span/span/input"));
		assertEquals(RuntimeVariables.replace("a. PD Question ChoiceA"),
			selenium.getText("//div/span[1]/span/label"));
		assertEquals(RuntimeVariables.replace("b. PD Question ChoiceB"),
			selenium.getText("//div/span[2]/span/label"));
		assertEquals(RuntimeVariables.replace("c. PD Question ChoiceC"),
			selenium.getText("//div/span[3]/span/label"));
	}
}