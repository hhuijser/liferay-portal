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

package com.liferay.portal.model;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Andrew Betts
 */
public class UserGroupGroupRoleModelListener
	extends BaseModelListener<UserGroupGroupRole> {

	@Override
	public void onAfterCreate(UserGroupGroupRole userGroupGroupRole)
		throws ModelListenerException {

		reindexUsers(userGroupGroupRole);
	}

	@Override
	public void onAfterRemove(UserGroupGroupRole userGroupGroupRole)
		throws ModelListenerException {

		reindexUsers(userGroupGroupRole);
	}

	@Override
	public void onAfterUpdate(UserGroupGroupRole userGroupGroupRole)
		throws ModelListenerException {

		reindexUsers(userGroupGroupRole);
	}

	protected void reindexUsers(UserGroupGroupRole userGroupGroupRole) {
		long[] userIds = UserLocalServiceUtil.getUserGroupPrimaryKeys(
			userGroupGroupRole.getUserGroupId());

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class.getName());

		indexer.commitCallbackReindex(userIds);
	}

}