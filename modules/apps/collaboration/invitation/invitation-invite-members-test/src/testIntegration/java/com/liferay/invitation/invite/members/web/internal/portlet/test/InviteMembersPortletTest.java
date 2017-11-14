/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.invitation.invite.members.web.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Hugo Huijser
 */
@RunWith(Arquillian.class)
@Sync
public class InviteMembersPortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();
	}

	@Test
	public void testGetAvailableUsers() throws Exception {
		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		List<User> availableUsers = _getAvailableUsers(
			_userGroup.getCompanyId(), _userGroup.getUserGroupId());

		Assert.assertEquals(
			availableUsers.toString(), 2, availableUsers.size());

		UserLocalServiceUtil.addUserGroupUser(
			_userGroup.getUserGroupId(), user1.getUserId());
		UserLocalServiceUtil.addUserGroupUser(
			_userGroup.getUserGroupId(), user2.getUserId());

		availableUsers = _getAvailableUsers(
			_userGroup.getCompanyId(), _userGroup.getUserGroupId());

		Assert.assertEquals(
			availableUsers.toString(), 0, availableUsers.size());

		UserLocalServiceUtil.deleteUserGroupUser(
			_userGroup.getUserGroupId(), user1.getUserId());

		availableUsers = _getAvailableUsers(
			_userGroup.getCompanyId(), _userGroup.getUserGroupId());

		Assert.assertEquals(
			availableUsers.toString(), 1, availableUsers.size());
		Assert.assertEquals(
			availableUsers.toString(), user2, availableUsers.get(0));
	}

	private List<User> _getAvailableUsers(long companyId, long groupId)
		throws Exception {

		LinkedHashMap usersParams = new LinkedHashMap();

		usersParams.put(
			"usersInvited",
			new CustomSQLParam(
				CustomSQLUtil.get(
					getClass(),
					"com.liferay.portal.service.persistence.UserFinder." +
						"filterByUsersGroupsGroupId"),
				groupId));

		return UserLocalServiceUtil.search(
			companyId, null, WorkflowConstants.STATUS_ANY, usersParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserFirstNameComparator(true));
	}

	@DeleteAfterTestRun
	private UserGroup _userGroup;

}