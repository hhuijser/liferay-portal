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

package com.liferay.portalweb.portal.dbupgrade.sampledata523.social.relation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SRl_ConfirmFriendRequestTest extends BaseTestCase {
	public void testSRl_ConfirmFriendRequest() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialrelationsn1/home/");
		selenium.waitForVisible("link=Requests Test Page");
		selenium.clickAt("link=Requests Test Page",
			RuntimeVariables.replace("Requests Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"socialrelationfn2 socialrelationmn2 socialrelationln2"),
			selenium.getText("//div/a[2]"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//td[2]/ul/li[1]/a[2]"));
		selenium.clickAt("//td[2]/ul/li[1]/a[2]",
			RuntimeVariables.replace("Confirm"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Confirm"));
	}
}