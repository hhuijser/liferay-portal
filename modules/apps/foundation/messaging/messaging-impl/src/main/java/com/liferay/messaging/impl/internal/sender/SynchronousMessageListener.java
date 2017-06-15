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

package com.liferay.messaging.impl.internal.sender;

import com.liferay.messaging.Message;
import com.liferay.messaging.MessageBusException;
import com.liferay.messaging.MessageListener;
import com.liferay.messaging.impl.internal.DefaultMessageBus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Michael C. Han
 */
public class SynchronousMessageListener implements MessageListener {

	public SynchronousMessageListener(
		DefaultMessageBus messageBus, Message message, long timeout) {

		_messageBus = messageBus;
		_message = message;
		_timeout = timeout;

		_responseId = _message.getResponseId();
	}

	public Object getResults() {
		return _results;
	}

	@Override
	public void receive(Message message) {
		if (!message.getResponseId().equals(_responseId)) {
			return;
		}

		_results = message.getPayload();

		_countDownLatch.countDown();
	}

	public Object send() throws MessageBusException {
		String destinationName = _message.getDestinationName();
		String responseDestinationName = _message.getResponseDestinationName();

		_messageBus.registerMessageListener(responseDestinationName, this);

		try {
			_messageBus.sendMessage(destinationName, _message);

			_countDownLatch.await(_timeout, TimeUnit.MILLISECONDS);

			if (_results == null) {
				throw new MessageBusException(
					"No reply received for message: " + _message);
			}

			return _results;
		}
		catch (InterruptedException ie) {
			throw new MessageBusException(
				"Message sending interrupted for: " + _message, ie);
		}
		finally {
			_messageBus.unregisterMessageListener(
				responseDestinationName, this);
		}
	}

	private final CountDownLatch _countDownLatch = new CountDownLatch(1);
	private final Message _message;
	private final DefaultMessageBus _messageBus;
	private final String _responseId;
	private Object _results;
	private final long _timeout;

}