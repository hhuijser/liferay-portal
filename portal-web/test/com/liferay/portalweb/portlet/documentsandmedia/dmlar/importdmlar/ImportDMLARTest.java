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

package com.liferay.portalweb.portlet.documentsandmedia.dmlar.importdmlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportDMLARTest extends BaseTestCase {
	public void testImportDMLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent("//li[7]/a");
		assertEquals(RuntimeVariables.replace("Site Content"),
			selenium.getText("//li[7]/a"));
		selenium.clickAt("//li[7]/a", RuntimeVariables.replace("Site Content"));
		selenium.waitForVisible("//ul[@class='category-portlets']/li[3]/a");
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText("//ul[@class='category-portlets']/li[3]/a"));
		selenium.clickAt("//ul[@class='category-portlets']/li[3]/a",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//a[@id='_20_bbln_menuButton']"));
		selenium.clickAt("//a[@id='_20_bbln_menuButton']",
			RuntimeVariables.replace("Options"));
		Thread.sleep(5000);
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a");
		assertEquals(RuntimeVariables.replace("Export / Import"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a",
			RuntimeVariables.replace("Export / Import"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Import", RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_86_importFileName']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\documentsandmedia\\dependencies\\Document_Library-Selenium.portlet.lar"));
		selenium.check("//input[@id='_86_DELETE_PORTLET_DATACheckbox']");
		selenium.check("//input[@id='_86_PORTLET_DATACheckbox']");
		selenium.check("//input[@id='_86_PERMISSIONSCheckbox']");
		selenium.check("//input[@id='_86_CATEGORIESCheckbox']");
		selenium.clickAt("//input[@value='Import']",
			RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}