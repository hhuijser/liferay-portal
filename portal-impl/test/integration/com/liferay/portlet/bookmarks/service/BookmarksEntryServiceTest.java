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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.permission.BookmarksPermission;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class BookmarksEntryServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_entry = BookmarksTestUtil.addEntry(_group.getGroupId(), true);

		_folder = BookmarksTestUtil.addFolder(
			_group.getGroupId(), "Test Folder Permission");

		RoleTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, BookmarksPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.POWER_USER, BookmarksPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@Test
	public void testAddEntry() throws Exception {
		BookmarksTestUtil.addEntry(_group.getGroupId(), true);
	}

	@Test
	public void testDeleteEntry() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		BookmarksEntryServiceUtil.deleteEntry(entry.getEntryId());
	}

	@Test
	public void testGetEntry() throws Exception {
		BookmarksEntry entry = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		BookmarksEntryServiceUtil.getEntry(entry.getEntryId());
	}

	@Test
	@Transactional(enabled = false)
	public void testGetEntryWithoutRootPermission() throws Exception {
		checkEntryRootPermission(false);
	}

	@Test
	@Transactional(enabled = false)
	public void testGetEntryWithRootPermission() throws Exception {
		checkEntryRootPermission(false);
	}

	protected void checkEntryRootPermission(boolean hasRootPermission)
		throws Exception {

		User user = UserTestUtil.addUser();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		BookmarksEntry subentry = BookmarksTestUtil.addEntry(
			_folder.getFolderId(), true, serviceContext);

		ServiceTestUtil.setUser(user);

		if (!hasRootPermission) {
			RoleTestUtil.removeResourcePermission(
				RoleConstants.POWER_USER, BookmarksPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		try {
			BookmarksEntryServiceUtil.getEntry(_entry.getEntryId());

			if (!hasRootPermission) {
				Assert.fail("User is able to get entry");
			}
		}
		catch (PrincipalException pe) {
			if (hasRootPermission) {
				Assert.fail("User is unable to get entry");
			}
		}

		try {
			BookmarksEntryServiceUtil.getEntry(subentry.getEntryId());

			if (!hasRootPermission) {
				Assert.fail("User is able to get subentry");
			}
		}
		catch (PrincipalException pe) {
			if (hasRootPermission) {
				Assert.fail("User is unable to get subentry");
			}
		}

		if (!hasRootPermission) {
			RoleTestUtil.addResourcePermission(
				RoleConstants.POWER_USER, BookmarksPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	private BookmarksEntry _entry;
	private BookmarksFolder _folder;
	private Group _group;

}