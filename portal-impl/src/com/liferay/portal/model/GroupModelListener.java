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
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Andrew Betts
 */
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			long groupId = ((Long)classPK).longValue();

			reindexUsers(groupId, associationClassName);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			long groupId = ((Long)classPK).longValue();

			reindexUsers(groupId, associationClassName);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void reindexUsers(long groupId, String associationClassName) {
		if (!_TABLE_MAPPER_CLASSES.contains(associationClassName)) {
			return;
		}

		final Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class.getName());

		final long[] userIds = GroupLocalServiceUtil.getUserPrimaryKeys(
			groupId);

		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				for (long userId : userIds) {
					User user = UserLocalServiceUtil.fetchUser(userId);

					if (user != null) {
						indexer.reindex(user);
					}
				}

				return null;
			}
		};

		TransactionCommitCallbackRegistryUtil.registerCallback(callable);
	}

	private static final List<String> _TABLE_MAPPER_CLASSES =
		new ArrayList<String>();

	static {
		_TABLE_MAPPER_CLASSES.add(Role.class.getName());
	}

}