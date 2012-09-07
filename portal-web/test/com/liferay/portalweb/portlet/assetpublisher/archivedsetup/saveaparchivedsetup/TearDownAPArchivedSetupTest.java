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

package com.liferay.portalweb.portlet.assetpublisher.archivedsetup.saveaparchivedsetup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownAPArchivedSetupTest extends BaseTestCase {
	public void testTearDownAPArchivedSetup() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Asset Publisher Test Page");
				selenium.clickAt("link=Asset Publisher Test Page",
					RuntimeVariables.replace("Asset Publisher Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent(
					"//nav[@class='site-breadcrumbs aui-helper-hidden lfr-hudcrumbs']");
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				selenium.waitForVisible(
					"//div[@class='archived-setups']/span/a/span");
				assertEquals(RuntimeVariables.replace("Archive/Restore Setup"),
					selenium.getText(
						"//div[@class='archived-setups']/span/a/span"));
				selenium.clickAt("//div[@class='archived-setups']/span/a/span",
					RuntimeVariables.replace("Archive/Restore Setup"));
				selenium.waitForPageToLoad("30000");

				boolean archive1Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong");

				if (!archive1Present) {
					label = 2;

					continue;
				}

				selenium.waitForElementPresent(
					"//script[contains(@src,'liferay/navigation_interaction.js')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean archive2Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong");

				if (!archive2Present) {
					label = 3;

					continue;
				}

				selenium.waitForElementPresent(
					"//script[contains(@src,'liferay/navigation_interaction.js')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean archive3Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong");

				if (!archive3Present) {
					label = 4;

					continue;
				}

				selenium.waitForElementPresent(
					"//script[contains(@src,'liferay/navigation_interaction.js')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean archive4Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong");

				if (!archive4Present) {
					label = 5;

					continue;
				}

				selenium.waitForElementPresent(
					"//script[contains(@src,'liferay/navigation_interaction.js')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean archive5Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong");

				if (!archive5Present) {
					label = 6;

					continue;
				}

				selenium.waitForElementPresent(
					"//script[contains(@src,'liferay/navigation_interaction.js')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}