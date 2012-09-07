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

package com.liferay.portalweb.portlet.shopping.coupon.addcouponmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCoupon3Test extends BaseTestCase {
	public void testAddCoupon3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Shopping Test Page");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Coupons", RuntimeVariables.replace("Coupons"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Coupon']",
			RuntimeVariables.replace("Add Coupon"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked("//input[@id='_34_autoCodeCheckbox']"));
		selenium.clickAt("//input[@id='_34_autoCodeCheckbox']",
			RuntimeVariables.replace("Autogenerate Code"));
		assertTrue(selenium.isChecked("//input[@id='_34_autoCodeCheckbox']"));
		selenium.type("//input[@id='_34_name']",
			RuntimeVariables.replace("Shopping Coupon3 Name"));
		selenium.type("//textarea[@id='_34_description']",
			RuntimeVariables.replace("Shopping Coupon3 Description"));
		selenium.type("//input[@id='_34_discount']",
			RuntimeVariables.replace("0.50"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Shopping Coupon1 Name\nShopping Coupon1 Description"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace(
				"Shopping Coupon2 Name\nShopping Coupon2 Description"),
			selenium.getText("//tr[4]/td[3]/a"));
		assertEquals(RuntimeVariables.replace(
				"Shopping Coupon3 Name\nShopping Coupon3 Description"),
			selenium.getText("//tr[5]/td[3]/a"));
	}
}