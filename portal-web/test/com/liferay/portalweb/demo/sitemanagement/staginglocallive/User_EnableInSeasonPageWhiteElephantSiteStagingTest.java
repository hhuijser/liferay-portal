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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_EnableInSeasonPageWhiteElephantSiteStagingTest
	extends BaseTestCase {
	public void testUser_EnableInSeasonPageWhiteElephantSiteStaging()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Site Name");
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));

				boolean christmasPresent = selenium.isElementPresent(
						"link=Christmas");

				if (!christmasPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Christmas",
					RuntimeVariables.replace("Christmas"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Christmas Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				selenium.waitForVisible("link=White Elephant");
				selenium.clickAt("link=White Elephant",
					RuntimeVariables.replace("White Elephant"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Christmas"),
					selenium.getText("//li[1]/span/span/span[1]"));
				assertEquals(RuntimeVariables.replace("Season"),
					selenium.getText("//li[2]/span/span/a"));
				selenium.clickAt("//li[2]/span/span/a",
					RuntimeVariables.replace("Season"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The page White Elephant is not enabled in Season, but is available for other pages variations."),
					selenium.getText(
						"//div[@id='_170_layoutRevisionDetails']/div"));
				assertEquals(RuntimeVariables.replace("Enable in Season"),
					selenium.getText("//button/span[.='Enable in Season']"));
				selenium.clickAt("//button/span[.='Enable in Season']",
					RuntimeVariables.replace("Enable in Season"));
				selenium.waitForText("//span[@class='workflow-status']/strong",
					"Draft");
				assertEquals(RuntimeVariables.replace("Draft"),
					selenium.getText("//span[@class='workflow-status']/strong"));

			case 100:
				label = -1;
			}
		}
	}
}