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

package com.liferay.portalweb.portlet.shopping.item.updatecartcategoryitemquantity;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class UpdateCartCategoryItemQuantityTest extends BaseTestCase {
	public void testUpdateCartCategoryItemQuantity() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Shopping Test Page");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Cart", RuntimeVariables.replace("Cart"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Shopping Category Item Name\nShopping Category Item Description\n\nAvailability: In Stock\n\n\nPrice for 1 to 1 Items:$9.99"),
			selenium.getText("//td[2]/a"));
		selenium.select("//select", RuntimeVariables.replace("10"));
		selenium.clickAt("//input[@value='Update Cart']",
			RuntimeVariables.replace("Update Cart"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Subtotal $99.90"),
			selenium.getText("//fieldset/div/div[1]/div"));
		selenium.select("//select", RuntimeVariables.replace("5"));
		selenium.clickAt("//input[@value='Update Cart']",
			RuntimeVariables.replace("Update Cart"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Subtotal $49.95"),
			selenium.getText("//fieldset/div/div[1]/div"));
		selenium.select("//select", RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@value='Update Cart']",
			RuntimeVariables.replace("Update Cart"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Subtotal $9.99"),
			selenium.getText("//fieldset/div/div[1]/div"));
	}
}