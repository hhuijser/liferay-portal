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
public class SelectTagThroughAutoSuggestionTest extends BaseTestCase {
	public void testSelectTagThroughAutoSuggestion() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Blogs Tags Test Page");
				selenium.clickAt("link=Blogs Tags Test Page",
					RuntimeVariables.replace("Blogs Tags Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Tags Blog Entry3 Title"),
					selenium.getText(
						"xPath=(//div[@class='entry-title']/h2/a)[1]"));
				selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[1]",
					RuntimeVariables.replace("Tags Blog Entry3 Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText("//span/a/span"));
				selenium.click(RuntimeVariables.replace("//span/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//td[@id='cke_contents__33_editor']/iframe");

				boolean tagsVisible = selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']");

				if (tagsVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText(
						"xPath=(//div[@class='lfr-panel-title'])[2]/span"));
				selenium.clickAt("xPath=(//div[@class='lfr-panel-title'])[2]/span",
					RuntimeVariables.replace("Categorization"));
				selenium.waitForVisible(
					"//input[@class='lfr-tag-selector-input aui-field-input-text']");
				assertTrue(selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']"));

			case 2:
				selenium.type("//input[@class='lfr-tag-selector-input aui-field-input-text']",
					RuntimeVariables.replace(""));
				selenium.typeKeys("//input[@class='lfr-tag-selector-input aui-field-input-text focus']",
					RuntimeVariables.replace("selenium3*"));
				selenium.waitForText("//li[contains(@class,'aui-autocomplete-list-item')]",
					"selenium3 liferay3");
				assertEquals(RuntimeVariables.replace("selenium3 liferay3"),
					selenium.getText(
						"//li[contains(@class,'aui-autocomplete-list-item')]"));
				selenium.clickAt("//li[contains(@class,'aui-autocomplete-list-item')]",
					RuntimeVariables.replace("selenium3 liferay3"));
				selenium.waitForText("xPath=(//span[@class='aui-textboxlistentry-text'])[2]",
					"selenium3 liferay3");
				assertEquals(RuntimeVariables.replace("selenium3 liferay3"),
					selenium.getText(
						"xPath=(//span[@class='aui-textboxlistentry-text'])[2]"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("selenium2 liferay2"),
					selenium.getText(
						"xPath=(//span[@class='taglib-asset-tags-summary']/a)[1]"));
				assertEquals(RuntimeVariables.replace("selenium3 liferay3"),
					selenium.getText(
						"xPath=(//span[@class='taglib-asset-tags-summary']/a)[2]"));

			case 100:
				label = -1;
			}
		}
	}
}