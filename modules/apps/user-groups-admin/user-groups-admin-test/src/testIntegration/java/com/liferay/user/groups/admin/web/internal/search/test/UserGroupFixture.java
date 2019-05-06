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

package com.liferay.user.groups.admin.web.internal.search.test;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
public class UserGroupFixture {

	public UserGroupFixture(Group group) {
		_group = group;
	}

	public UserGroup createUserGroup() throws Exception {
		return createUserGroup(Collections.emptyMap());
	}

	public UserGroup createUserGroup(Map<String, Serializable> expandoValues)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		serviceContext.setExpandoBridgeAttributes(expandoValues);

		UserGroup userGroup = UserGroupLocalServiceUtil.addUserGroup(
			serviceContext.getUserId(), serviceContext.getCompanyId(),
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			RandomTestUtil.randomString(50), serviceContext);

		_userGroups.add(userGroup);

		return userGroup;
	}

	public ServiceContext getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), getUserId());
	}

	public List<UserGroup> getUserGroups() {
		return _userGroups;
	}

	protected long getUserId() throws Exception {
		return TestPropsValues.getUserId();
	}

	private final Group _group;
	private final List<UserGroup> _userGroups = new ArrayList<>();

}