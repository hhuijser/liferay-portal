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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class JournalFolderServiceTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();

		RoleTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@Test
	public void testContent() throws Exception {
		Group group = GroupTestUtil.addGroup();

		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), 0, "Test Folder");

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), folder.getFolderId(), "Test Article",
			"This is a test article.");

		Assert.assertEquals(article.getFolderId(), folder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(folder);
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	public void testGetFolderWithoutRootPermission() throws Exception {
		checkFolderRootPermission(false);
	}

	@Test
	public void testGetFolderWithRootPermission() throws Exception {
		checkFolderRootPermission(true);
	}

	@Test
	@Transactional
	public void testSubfolders() throws Exception {
		Group group = GroupTestUtil.addGroup();

		JournalFolder folder1 = JournalTestUtil.addFolder(
			group.getGroupId(), 0, "Test 1");

		JournalFolder folder11 = JournalTestUtil.addFolder(
			group.getGroupId(), folder1.getFolderId(), "Test 1.1");

		JournalFolder folder111 = JournalTestUtil.addFolder(
			group.getGroupId(), folder11.getFolderId(), "Test 1.1.1");

		Assert.assertTrue(folder1.isRoot());
		Assert.assertFalse(folder11.isRoot());
		Assert.assertFalse(folder111.isRoot());

		Assert.assertEquals(
			folder1.getFolderId(), folder11.getParentFolderId());

		Assert.assertEquals(
			folder11.getFolderId(), folder111.getParentFolderId());
	}

	protected void checkFolderRootPermission(boolean hasRootPermission)
		throws Exception {

		User user = UserTestUtil.addUser();

		if (!hasRootPermission) {
			RoleTestUtil.removeResourcePermission(
				RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder");

		JournalFolder subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test SubFolder");

		ServiceTestUtil.setUser(user);

		try {
			JournalFolderServiceUtil.getFolder(folder.getFolderId());

			if (!hasRootPermission) {
				Assert.fail("User is able to get folder");
			}
		}
		catch (PrincipalException pe) {
			if (hasRootPermission) {
				Assert.fail("User is unable to get folder");
			}
		}

		try {
			JournalFolderServiceUtil.getFolder(subfolder.getFolderId());

			if (!hasRootPermission) {
				Assert.fail("User is able to get subfolder");
			}
		}
		catch (PrincipalException pe) {
			if (hasRootPermission) {
				Assert.fail("User is unable to get subfolder");
			}
		}

		if (!hasRootPermission) {
			RoleTestUtil.addResourcePermission(
				RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		JournalFolderLocalServiceUtil.deleteFolder(subfolder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(folder.getFolderId());
	}

	private Group _group;

}