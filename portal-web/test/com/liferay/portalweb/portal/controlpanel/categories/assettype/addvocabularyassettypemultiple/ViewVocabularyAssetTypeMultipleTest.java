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

package com.liferay.portalweb.portal.controlpanel.categories.assettype.addvocabularyassettypemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewVocabularyAssetTypeMultipleTest extends BaseTestCase {
	public void testViewVocabularyAssetTypeMultiple() throws Exception {
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
				selenium.clickAt("link=Categories",
					RuntimeVariables.replace("Categories"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Vocabulary Name"),
					selenium.getText("//span[@class='vocabulary-item']/a"));
				selenium.clickAt("//a[@class='vocabulary-item-actions-trigger']",
					RuntimeVariables.replace("Edit"));
				selenium.waitForVisible(
					"//div[@id='vocabularyExtraFieldsPanelContainer']/div/div/span");
				assertEquals(RuntimeVariables.replace("Associated Asset Types"),
					selenium.getText(
						"//div[@id='vocabularyExtraFieldsPanelContainer']/div/div/span"));

				boolean chooseAssetTypeNotVisible = selenium.isVisible(
						"_147_classNameId0");

				if (chooseAssetTypeNotVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='vocabularyExtraFieldsPanelContainer']/div/div/span",
					RuntimeVariables.replace("Associated Asset Types"));
				selenium.waitForVisible("//select[@id='_147_classNameId0']");

			case 2:
				assertEquals("Blogs Entry",
					selenium.getSelectedLabel(
						"//select[@id='_147_classNameId0']"));
				assertEquals("Web Content",
					selenium.getSelectedLabel(
						"//select[@id='_147_classNameId1']"));

			case 100:
				label = -1;
			}
		}
	}
}