/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.SubscriptionSender;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 */
public class SubscriptionSenderMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		SubscriptionSender subscriptionSender =
			(SubscriptionSender)message.getPayload();

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();

			_log.info(
				"Sending notifications for {mailId=" +
					subscriptionSender.getMailId() + "}");
		}

		subscriptionSender.flushNotifications();

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("Sending notifications for {mailId=");
			sb.append(subscriptionSender.getMailId());

			if (stopWatch != null) {
				sb.append("} took ");
				sb.append(stopWatch.getTime() / Time.SECOND);
				sb.append(" seconds");
			}
			else {
				sb.append("} is finished");
			}

			_log.info(sb.toString());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SubscriptionSenderMessageListener.class);

}