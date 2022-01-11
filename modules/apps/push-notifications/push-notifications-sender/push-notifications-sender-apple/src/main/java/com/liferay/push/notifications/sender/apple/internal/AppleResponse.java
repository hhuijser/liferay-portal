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

package com.liferay.push.notifications.sender.apple.internal;

import com.eatthepath.pushy.apns.ApnsPushNotification;

import com.liferay.push.notifications.sender.BaseResponse;

import java.time.Instant;

/**
 * @author Bruno Farache
 */
public class AppleResponse extends BaseResponse {

	public AppleResponse(
		ApnsPushNotification apnsPushNotification, boolean resent) {

		this(apnsPushNotification);

		_resent = resent;

		succeeded = true;
	}

	public AppleResponse(
		ApnsPushNotification apnsPushNotification, Throwable throwable) {

		this(apnsPushNotification);

		status = throwable.getMessage();
	}

	public int getExpiry() {
		return _expiry;
	}

	public boolean isResent() {
		return _resent;
	}

	protected AppleResponse(ApnsPushNotification apnsPushNotification) {
		super(ApplePushNotificationsSender.PLATFORM);

		if (apnsPushNotification != null) {
			Instant instant = apnsPushNotification.getExpiration();

			_expiry = instant.getNano();

			id = apnsPushNotification.getCollapseId();
			payload = apnsPushNotification.getPayload();
			token = apnsPushNotification.getToken();
		}
	}

	private int _expiry;
	private boolean _resent;

}