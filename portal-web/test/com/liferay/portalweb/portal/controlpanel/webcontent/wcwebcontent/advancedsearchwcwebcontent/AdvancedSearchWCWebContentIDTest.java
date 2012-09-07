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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.advancedsearchwcwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchWCWebContentIDTest extends BaseTestCase {
	public void testAdvancedSearchWCWebContentID() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");

				String webContentID = selenium.getText("//td[2]/a");
				RuntimeVariables.setValue("webContentID", webContentID);

				boolean advancedVisible = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace("Advanced \u00bb"));

			case 2:
				selenium.waitForVisible("//input[@id='_15_searchArticleId']");
				selenium.type("//input[@id='_15_searchArticleId']",
					RuntimeVariables.replace(RuntimeVariables.getValue(
							"webContentID")));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_15_searchArticleId']",
					RuntimeVariables.replace(""));
				assertEquals(RuntimeVariables.replace("WC WebContent Title"),
					selenium.getText("//td[3]/a"));
				selenium.type("//input[@id='_15_searchArticleId']",
					RuntimeVariables.replace("11111"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_15_searchArticleId']",
					RuntimeVariables.replace(""));
				assertFalse(selenium.isTextPresent("WC WebContent Title"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));

			case 100:
				label = -1;
			}
		}
	}
}