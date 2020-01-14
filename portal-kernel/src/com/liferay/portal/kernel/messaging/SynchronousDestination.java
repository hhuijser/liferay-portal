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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             com.liferay.portal.messaging.internal.SynchronousDestination}
 */
@Deprecated
public class SynchronousDestination extends BaseDestination {

	@Override
	public DestinationStatistics getDestinationStatistics() {
		DestinationStatistics destinationStatistics =
			new DestinationStatistics();

		destinationStatistics.setSentMessageCount(_sentMessageCounter.get());

		return destinationStatistics;
	}

	@Override
	public void send(Message message) {
		for (MessageListener messageListener : messageListeners) {
			try {
				messageListener.receive(message);
			}
			catch (MessageListenerException messageListenerException) {
				_log.error(
					"Unable to process message " + message,
					messageListenerException);
			}
		}

		_sentMessageCounter.incrementAndGet();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SynchronousDestination.class);

	private final AtomicLong _sentMessageCounter = new AtomicLong();

}