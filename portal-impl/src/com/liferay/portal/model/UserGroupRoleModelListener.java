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

/**
 * @author Andrew Betts
 */
public class UserGroupRoleModelListener
	extends BaseModelListener<UserGroupRole> {

	@Override
	public void onAfterCreate(UserGroupRole userGroupRole)
		throws ModelListenerException {

		reindexUsers(userGroupRole.getUserId());
	}

	@Override
	public void onAfterRemove(UserGroupRole userGroupRole)
		throws ModelListenerException {

		reindexUsers(userGroupRole.getUserId());
	}

	@Override
	public void onAfterUpdate(UserGroupRole userGroupRole)
		throws ModelListenerException {

		reindexUsers(userGroupRole.getUserId());
	}

	protected void reindexUsers(final long userId) {
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class.getName());

		indexer.commitCallbackReindex(userId);
	}

}