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

package com.liferay.notifications.uad.anonymizer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.notifications.uad.constants.NotificationsUADConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDelivery;
import com.liferay.portal.kernel.service.UserNotificationDeliveryLocalService;
import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the user notification delivery UAD anonymizer.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom service methods should be put in
 * {@link UserNotificationDeliveryUADAnonymizer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseUserNotificationDeliveryUADAnonymizer
	extends DynamicQueryUADAnonymizer<UserNotificationDelivery> {

	@Override
	public void autoAnonymize(
			UserNotificationDelivery userNotificationDelivery, long userId,
			User anonymousUser)
		throws PortalException {

		if (userNotificationDelivery.getUserId() == userId) {
			delete(userNotificationDelivery);

			autoAnonymizeAssetEntry(userNotificationDelivery, anonymousUser);
		}
	}

	@Override
	public void delete(UserNotificationDelivery userNotificationDelivery)
		throws PortalException {

		userNotificationDeliveryLocalService.deleteUserNotificationDelivery(
			userNotificationDelivery);
	}

	@Override
	public Class<UserNotificationDelivery> getTypeClass() {
		return UserNotificationDelivery.class;
	}

	protected void autoAnonymizeAssetEntry(
		UserNotificationDelivery userNotificationDelivery, User anonymousUser) {

		AssetEntry assetEntry = fetchAssetEntry(userNotificationDelivery);

		if (assetEntry != null) {
			assetEntry.setUserId(anonymousUser.getUserId());
			assetEntry.setUserName(anonymousUser.getFullName());

			assetEntryLocalService.updateAssetEntry(assetEntry);
		}
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return userNotificationDeliveryLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return NotificationsUADConstants.
			USER_ID_FIELD_NAMES_USER_NOTIFICATION_DELIVERY;
	}

	protected AssetEntry fetchAssetEntry(
		UserNotificationDelivery userNotificationDelivery) {

		return assetEntryLocalService.fetchEntry(
			UserNotificationDelivery.class.getName(),
			userNotificationDelivery.getUserNotificationDeliveryId());
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected UserNotificationDeliveryLocalService
		userNotificationDeliveryLocalService;

}