/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.messageboards.message.gmailviewmbthreadmessagegmail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPageSiteTest extends BaseTestCase {
	public void testTearDownPageSite() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Site Name")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[4]/div/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[4]/div/ul/li[1]/a",
					RuntimeVariables.replace("Manage Pages"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Public Pages")
												.equals(selenium.getText(
										"//div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

				boolean Page1Present = selenium.isElementPresent(
						"//li/ul/li/div/div[3]");

				if (!Page1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//li/ul/li/div/div[3]",
					RuntimeVariables.replace("Page Name"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (!RuntimeVariables.replace("/home")
												 .equals(selenium.getValue(
										"//input[@id='_88_friendlyURL']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[3]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[3]"));
				selenium.click(RuntimeVariables.replace("//button[3]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

				boolean Page2Present = selenium.isElementPresent(
						"//li/ul/li/div/div[3]");

				if (!Page2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li/ul/li/div/div[3]",
					RuntimeVariables.replace("Page Name"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (!RuntimeVariables.replace("/home")
												 .equals(selenium.getValue(
										"//input[@id='_88_friendlyURL']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[3]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[3]"));
				selenium.click(RuntimeVariables.replace("//button[3]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

				boolean Page3Present = selenium.isElementPresent(
						"//li/ul/li/div/div[3]");

				if (!Page3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//li/ul/li/div/div[3]",
					RuntimeVariables.replace("Page Name"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (!RuntimeVariables.replace("/home")
												 .equals(selenium.getValue(
										"//input[@id='_88_friendlyURL']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[3]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[3]"));
				selenium.click(RuntimeVariables.replace("//button[3]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

				boolean Page4Present = selenium.isElementPresent(
						"//li/ul/li/div/div[3]");

				if (!Page4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//li/ul/li/div/div[3]",
					RuntimeVariables.replace("Page Name"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (!RuntimeVariables.replace("/home")
												 .equals(selenium.getValue(
										"//input[@id='_88_friendlyURL']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[3]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[3]"));
				selenium.click(RuntimeVariables.replace("//button[3]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

				boolean Page5Present = selenium.isElementPresent(
						"//li/ul/li/div/div[3]");

				if (!Page5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//li/ul/li/div/div[3]",
					RuntimeVariables.replace("Page Name"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (!RuntimeVariables.replace("/home")
												 .equals(selenium.getValue(
										"//input[@id='_88_friendlyURL']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[3]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[3]"));
				selenium.click(RuntimeVariables.replace("//button[3]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}