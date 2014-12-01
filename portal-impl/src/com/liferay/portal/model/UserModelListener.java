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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.ldap.LDAPUserTransactionThreadLocal;
import com.liferay.portal.security.ldap.PortalLDAPExporterUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Scott Lee
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Vilmos Papp
 */
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			long userId = ((Long)classPK).longValue();

			if (associationClassName.equals(Group.class.getName())) {
				long groupId = ((Long)associationClassPK).longValue();

				updateMembershipRequestStatus(userId, groupId);
			}

			reindexUsers(userId, associationClassName);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {
		try {
			exportToLDAP(user);
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
			long userId = ((Long)classPK).longValue();

			reindexUsers(userId, associationClassName);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(User user) throws ModelListenerException {
		try {
			exportToLDAP(user);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onBeforeUpdate(User user) {
		UserModelImpl userModelImpl = (UserModelImpl)user;

		LDAPUserTransactionThreadLocal.setOriginalEmailAddress(
			userModelImpl.getOriginalEmailAddress());
	}

	protected void exportToLDAP(User user) throws Exception {
		if (user.isDefaultUser() ||
			LDAPUserTransactionThreadLocal.isOriginatesFromLDAP()) {

			return;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Map<String, Serializable> expandoBridgeAttributes = null;

		if (serviceContext != null) {
			expandoBridgeAttributes =
				serviceContext.getExpandoBridgeAttributes();
		}

		PortalLDAPExporterUtil.exportToLDAP(user, expandoBridgeAttributes);
	}

	protected void reindexUsers(
		final long userId, String associationClassName) {

		if (!_TABLE_MAPPER_CLASSES.contains(associationClassName)) {
			return;
		}

		final Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			User.class.getName());

		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				User user = UserLocalServiceUtil.fetchUser(userId);

				if (user != null) {
					indexer.reindex(user);
				}

				return null;
			}
		};

		TransactionCommitCallbackRegistryUtil.registerCallback(callable);
	}

	protected void updateMembershipRequestStatus(long userId, long groupId)
		throws Exception {

		long principalUserId = GetterUtil.getLong(
			PrincipalThreadLocal.getName());

		User user = UserLocalServiceUtil.getUser(userId);

		List<MembershipRequest> membershipRequests =
			MembershipRequestLocalServiceUtil.getMembershipRequests(
				userId, groupId, MembershipRequestConstants.STATUS_PENDING);

		for (MembershipRequest membershipRequest : membershipRequests) {
			MembershipRequestLocalServiceUtil.updateStatus(
				principalUserId, membershipRequest.getMembershipRequestId(),
				LanguageUtil.get(
					user.getLocale(), "your-membership-has-been-approved"),
				MembershipRequestConstants.STATUS_APPROVED, false,
				new ServiceContext());
		}
	}

	private static final List<String> _TABLE_MAPPER_CLASSES =
		new ArrayList<String>();

	static {
		_TABLE_MAPPER_CLASSES.add(Group.class.getName());
		_TABLE_MAPPER_CLASSES.add(Organization.class.getName());
		_TABLE_MAPPER_CLASSES.add(Role.class.getName());
		_TABLE_MAPPER_CLASSES.add(Team.class.getName());
		_TABLE_MAPPER_CLASSES.add(UserGroup.class.getName());
	}

}