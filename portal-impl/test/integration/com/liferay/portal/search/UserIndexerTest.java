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

package com.liferay.portal.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.OrganizationTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.RoleTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserGroupTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Andrew Betts
 */
@Sync
public class UserIndexerTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testAddRemoveGroups() throws Exception {
		Group group = GroupTestUtil.addGroup();

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersGroups", group.getGroupId());

		int initialCount = getUsersCount(params);

		User user = UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		long[] groupIds = ArrayUtil.remove(
			user.getGroupIds(), group.getGroupId());

		UserTestUtil.updateUser(user, groupIds, null, null, null, null);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveOrganizations() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersOrgs", organization.getOrganizationId());

		int initialCount = getUsersCount(params);

		User user = UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_USER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		long[] organizationIds = ArrayUtil.remove(
			user.getOrganizationIds(), organization.getOrganizationId());

		UserTestUtil.updateUser(user, null, organizationIds, null, null, null);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemovePasswordPolicies() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(TestPropsValues.getUserId());

		PasswordPolicy passwordPolicy =
			PasswordPolicyTestUtil.addPasswordPolicy(serviceContext);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put(
			"usersPasswordPolicies", passwordPolicy.getPasswordPolicyId());

		int initialCount = getUsersCount(params);

		User user = UserTestUtil.addUser();

		long[] userIds = new long[] {user.getUserId()};

		UserLocalServiceUtil.addPasswordPolicyUsers(
			passwordPolicy.getPasswordPolicyId(), userIds);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		UserLocalServiceUtil.unsetPasswordPolicyUsers(
			passwordPolicy.getPasswordPolicyId(), userIds);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveRoles() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersRoles", role.getRoleId());

		int initialCount = getUsersCount(params);

		User user = UserTestUtil.addUser();

		long[] roleIds = ArrayUtil.append(user.getRoleIds(), role.getRoleId());

		UserTestUtil.updateUser(user, null, null, roleIds, null, null);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		roleIds = ArrayUtil.remove(roleIds, role.getRoleId());

		UserTestUtil.updateUser(user, null, null, roleIds, null, null);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveTeams() throws Exception {
		Team team = TeamLocalServiceUtil.addTeam(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), null);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersTeams", team.getTeamId());

		int initialCount = getUsersCount(params);

		User user = UserTestUtil.addUser();

		UserLocalServiceUtil.addTeamUser(team.getTeamId(), user.getUserId());

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		UserLocalServiceUtil.deleteTeamUser(team.getTeamId(), user.getUserId());

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveUserGroupRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put(
			"userGroupRole", new Long[] {group.getGroupId(), role.getRoleId()});

		int initialCount = getUsersCount(params);

		User user = UserTestUtil.addGroupAdminUser(group);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.getUserGroupRole(
				new UserGroupRolePK(
					user.getUserId(), group.getGroupId(), role.getRoleId()));

		List<UserGroupRole> userGroupRoles =
			new ArrayList<UserGroupRole> (
				UserGroupRoleLocalServiceUtil.getUserGroupRoles(
					user.getUserId(), group.getGroupId()));

		userGroupRoles.remove(userGroupRole);

		UserTestUtil.updateUser(user, null, null, null, userGroupRoles, null);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveUserGroups() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersUserGroups", userGroup.getUserGroupId());

		int initialCount = getUsersCount(params);

		User user = UserTestUtil.addUser();

		long[] userGroupIds = ArrayUtil.append(
			user.getUserGroupIds(), userGroup.getUserGroupId());

		UserTestUtil.updateUser(user, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		userGroupIds = ArrayUtil.remove(
			userGroupIds, userGroup.getUserGroupId());

		UserTestUtil.updateUser(user, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testAddRemoveUsersOrganizationTree() throws Exception {
		Organization parentOrgnaization =
			OrganizationTestUtil.addOrganization();

		Organization testOrganization = OrganizationTestUtil.addOrganization(
			parentOrgnaization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersOrgsTree", testOrganization.getOrganizationId());

		int initialCount = getUsersCount(params);

		UserTestUtil.addOrganizationUser(
			parentOrgnaization, RoleConstants.ORGANIZATION_USER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		OrganizationTestUtil.updateOrganization(
			testOrganization.getOrganizationId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			testOrganization.getName(), false);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testInheritedGroups() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		Group group = GroupTestUtil.addGroup();

		GroupLocalServiceUtil.addOrganizationGroup(
			organization.getOrganizationId(), group);
		GroupLocalServiceUtil.addUserGroupGroup(
			userGroup.getUserGroupId(), group);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put("usersGroups", group.getGroupId());

		int initialCount = getUsersCount(params);

		User organizationUser = UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_USER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		User userGroupUser = UserTestUtil.addUser();

		long[] userGroupIds = ArrayUtil.append(
			userGroupUser.getUserGroupIds(), userGroup.getUserGroupId());

		UserTestUtil.updateUser(
			userGroupUser, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount + 2, getUsersCount(params));

		long[] organizationIds = ArrayUtil.remove(
			organizationUser.getOrganizationIds(),
			organization.getOrganizationId());

		UserTestUtil.updateUser(
			organizationUser, null, organizationIds, null, null, null);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		userGroupIds = ArrayUtil.remove(
			userGroupIds, userGroup.getUserGroupId());

		UserTestUtil.updateUser(
			userGroupUser, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Test
	public void testInheritedRoles() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		Group group = GroupTestUtil.addGroup();

		GroupLocalServiceUtil.addOrganizationGroup(
			organization.getOrganizationId(), group);
		GroupLocalServiceUtil.addUserGroupGroup(
			userGroup.getUserGroupId(), group);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(group.getGroupId(), role);

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("inherit", Boolean.TRUE);
		params.put("usersRoles", role.getRoleId());

		int initialCount = getUsersCount(params);

		User organizationUser = UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_USER);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		User userGroupUser = UserTestUtil.addUser();

		long[] userGroupIds = ArrayUtil.append(
			userGroupUser.getUserGroupIds(), userGroup.getUserGroupId());

		UserTestUtil.updateUser(
			userGroupUser, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount + 2, getUsersCount(params));

		long[] organizationIds = ArrayUtil.remove(
			organizationUser.getOrganizationIds(),
			organization.getOrganizationId());

		UserTestUtil.updateUser(
			organizationUser, null, organizationIds, null, null, null);

		Assert.assertEquals(initialCount + 1, getUsersCount(params));

		userGroupIds = ArrayUtil.remove(
			userGroupIds, userGroup.getUserGroupId());

		UserTestUtil.updateUser(
			userGroupUser, null, null, null, null, userGroupIds);

		Assert.assertEquals(initialCount, getUsersCount(params));
	}

	@Rule
	public final SynchronousDestinationTestRule synchronousDestinationTestRule =
		SynchronousDestinationTestRule.INSTANCE;

	protected int getUsersCount(LinkedHashMap<String, Object> params)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(true);

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(TestPropsValues.getCompanyId());

		searchContext.setStart(QueryUtil.ALL_POS);
		searchContext.setEnd(QueryUtil.ALL_POS);

		if (params != null) {
			String keywords = (String) params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		Hits hits = indexer.search(searchContext);

		return hits.getLength();
	}

}