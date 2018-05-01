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

package com.liferay.message.boards.uad.anonymizer;

import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.uad.constants.MBUADConstants;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;

import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * Provides the base implementation for the message boards thread UAD anonymizer.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom service methods should be put in
 * {@link MBThreadUADAnonymizer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseMBThreadUADAnonymizer
	extends DynamicQueryUADAnonymizer<MBThread> {
	@Override
	public void autoAnonymize(MBThread mbThread, long userId, User anonymousUser)
		throws PortalException {
		if (mbThread.getUserId() == userId) {
			mbThread.setUserId(anonymousUser.getUserId());
			mbThread.setUserName(anonymousUser.getFullName());
		}

		if (mbThread.getRootMessageUserId() == userId) {
			mbThread.setRootMessageUserId(anonymousUser.getUserId());
		}

		if (mbThread.getLastPostByUserId() == userId) {
			mbThread.setLastPostByUserId(anonymousUser.getUserId());
		}

		if (mbThread.getStatusByUserId() == userId) {
			mbThread.setStatusByUserId(anonymousUser.getUserId());
			mbThread.setStatusByUserName(anonymousUser.getFullName());
		}

		mbThreadLocalService.updateMBThread(mbThread);
	}

	@Override
	public void delete(MBThread mbThread) throws PortalException {
		mbThreadLocalService.deleteThread(mbThread);
	}

	@Override
	public List<String> getNonanonymizableFieldNames() {
		return Arrays.asList();
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return mbThreadLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return MBUADConstants.USER_ID_FIELD_NAMES_MB_THREAD;
	}

	@Reference
	protected MBThreadLocalService mbThreadLocalService;
}