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

package com.liferay.portal.util.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Alberto Chaparro
 * @author Manuel de la Peña
 * @author Sampsa Sohlman
 */
public class UserTestUtil {

	public static User addGroupAdminUser(Group group) throws Exception {
		return UserTestUtil.addGroupUser(
			group, RoleConstants.SITE_ADMINISTRATOR);
	}

	public static User addGroupOwnerUser(Group group) throws Exception {
		return UserTestUtil.addGroupUser(group, RoleConstants.SITE_OWNER);
	}

	public static User addGroupUser(Group group, String roleName)
		throws Exception {

		User groupUser = addUser(
			RandomTestUtil.randomString(), group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		long[] userIds = {groupUser.getUserId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, group.getGroupId(), role.getRoleId());

		return groupUser;
	}

	public static User addOmniAdmin() throws Exception {
		User user = addUser();

		Company defaultCompany = CompanyLocalServiceUtil.getCompanyByMx(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		user.setCompanyId(defaultCompany.getCompanyId());

		UserLocalServiceUtil.updateUser(user);

		Role administratorRole = RoleLocalServiceUtil.getRole(
			defaultCompany.getCompanyId(), RoleConstants.ADMINISTRATOR);

		UserLocalServiceUtil.addRoleUser(administratorRole.getRoleId(), user);

		return user;
	}

	public static User addOrganizationAdminUser(Organization organization)
		throws Exception {

		return UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_ADMINISTRATOR);
	}

	public static User addOrganizationOwnerUser(Organization organization)
		throws Exception {

		return UserTestUtil.addOrganizationUser(
			organization, RoleConstants.ORGANIZATION_OWNER);
	}

	public static User addOrganizationUser(
			Organization organization, String roleName)
		throws Exception {

		User organizationUser = addUser(
			RandomTestUtil.randomString(), organization.getGroupId());

		long[] userIds = {organizationUser.getUserId()};

		UserLocalServiceUtil.addOrganizationUsers(
			organization.getOrganizationId(), userIds);

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, organization.getGroupId(), role.getRoleId());

		return organizationUser;
	}

	public static User addUser() throws Exception {
		return addUser(
			RandomTestUtil.randomString(), TestPropsValues.getGroupId());
	}

	public static User addUser(long groupId, Locale locale) throws Exception {
		return addUser(
			RandomTestUtil.randomString(), false, locale,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {groupId});
	}

	public static User addUser(
			long companyId, long userId, String screenName,
			boolean autoScreenName, Locale locale, String firstName,
			String lastName, long[] groupIds, ServiceContext serviceContext)
		throws Exception {

		User user = UserLocalServiceUtil.fetchUserByScreenName(
			companyId, screenName);

		if (user != null) {
			return user;
		}

		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		String emailAddress =
			RandomTestUtil.randomString() + RandomTestUtil.nextLong() +
				"@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String middleName = StringPool.BLANK;
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		return UserLocalServiceUtil.addUser(
			userId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendMail, serviceContext);
	}

	public static User addUser(
			String screenName, boolean autoScreenName, Locale locale,
			String firstName, String lastName, long[] groupIds)
		throws Exception {

		return addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			screenName, autoScreenName, locale, firstName, lastName, groupIds,
			ServiceContextTestUtil.getServiceContext());
	}

	public static User addUser(
			String screenName, boolean autoScreenName, long[] groupIds)
		throws Exception {

		return addUser(
			screenName, autoScreenName, "ServiceTestSuite", "ServiceTestSuite",
			groupIds);
	}

	public static User addUser(
			String screenName, boolean autoScreenName, String firstName,
			String lastName, long[] groupIds)
		throws Exception {

		return addUser(
			screenName, autoScreenName, LocaleUtil.getDefault(), firstName,
			lastName, groupIds);
	}

	public static User addUser(String screenName, long groupId)
		throws Exception {

		if (Validator.isNull(screenName)) {
			return addUser(null, true, new long[] {groupId});
		}
		else {
			return addUser(screenName, false, new long[] {groupId});
		}
	}

	public static User getAdminUser(long companyId) throws PortalException {
		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		List<User> users = UserLocalServiceUtil.getRoleUsers(
			role.getRoleId(), 0, 1);

		if (!users.isEmpty()) {
			return users.get(0);
		}

		return null;
	}

}