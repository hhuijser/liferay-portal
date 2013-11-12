/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.messageboards.util.MBTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBCategoryServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_category = MBTestUtil.addCategory(_group.getGroupId());

		RoleTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, MBPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		MBCategoryLocalServiceUtil.deleteCategory(_category.getCategoryId());

		RoleTestUtil.removeResourcePermission(
			RoleConstants.POWER_USER, MBPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@Test
	public void testGetCategoryWithoutRootPermission() throws Exception {
		checkCategoryRootPermission(false);
	}

	@Test
	public void testGetCategoryWithRootPermission() throws Exception {
		checkCategoryRootPermission(true);
	}

	protected void checkCategoryRootPermission(boolean hasRootPermission)
		throws Exception {

		User user = UserTestUtil.addUser();

		MBCategory subcategory = MBTestUtil.addCategory(
			_group.getGroupId(), _category.getCategoryId());

		if (!hasRootPermission) {
			RoleTestUtil.removeResourcePermission(
				RoleConstants.POWER_USER, MBPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		ServiceTestUtil.setUser(user);

		try {
			MBCategoryServiceUtil.getCategory(_category.getCategoryId());

			if (!hasRootPermission) {
				Assert.fail("User is able to get category");
			}
		}
		catch (PrincipalException pe) {
			if (hasRootPermission) {
				Assert.fail("User is unable to get category");
			}
		}

		try {
			MBCategoryServiceUtil.getCategory(subcategory.getCategoryId());

			if (!hasRootPermission) {
				Assert.fail("User is able to get subcategory");
			}
		}
		catch (PrincipalException pe) {
			if (hasRootPermission) {
				Assert.fail("User is unable to get subcategory");
			}
		}

		if (!hasRootPermission) {
			RoleTestUtil.addResourcePermission(
				RoleConstants.POWER_USER, MBPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		MBCategoryLocalServiceUtil.deleteCategory(subcategory.getCategoryId());
	}

	private MBCategory _category;
	private Group _group;

}