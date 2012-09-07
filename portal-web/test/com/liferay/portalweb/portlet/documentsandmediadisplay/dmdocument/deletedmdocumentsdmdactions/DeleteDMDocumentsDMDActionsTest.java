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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.deletedmdocumentsdmdactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteDMDocumentsDMDActionsTest extends BaseTestCase {
	public void testDeleteDMDocumentsDMDActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Display Test Page",
			RuntimeVariables.replace("Documents and Media Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Document1 Title"),
			selenium.getText("//span[@class='entry-title']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span"));
		selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]"));
		selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]",
			RuntimeVariables.replace("Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to move this to the Recycle Bin[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Document2 Title"),
			selenium.getText("//span[@class='entry-title']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span"));
		selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]"));
		selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]",
			RuntimeVariables.replace("Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to move this to the Recycle Bin[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Document3 Title"),
			selenium.getText("//span[@class='entry-title']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span"));
		selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]"));
		selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]",
			RuntimeVariables.replace("Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to move this to the Recycle Bin[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Recycle Bin",
			RuntimeVariables.replace("Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Document1 Title"),
			selenium.getText("//tr[3]/td[2]/a/span/span"));
		assertEquals(RuntimeVariables.replace("DM Document2 Title"),
			selenium.getText("//tr[4]/td[2]/a/span/span"));
		assertEquals(RuntimeVariables.replace("DM Document3 Title"),
			selenium.getText("//tr[5]/td[2]/a/span/span"));
		assertFalse(selenium.isChecked("//input[@name='_182_allRowIds']"));
		selenium.clickAt("//input[@name='_182_allRowIds']",
			RuntimeVariables.replace("All Entries Check Box"));
		assertTrue(selenium.isChecked("//input[@name='_182_allRowIds']"));
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete the selected entries[\\s\\S] They will be deleted immediately.$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("The Recycle Bin is empty."),
			selenium.getText(
				"//div[@class='portlet-msg-info' and contains(.,'The Recycle Bin is empty.')]"));
	}
}