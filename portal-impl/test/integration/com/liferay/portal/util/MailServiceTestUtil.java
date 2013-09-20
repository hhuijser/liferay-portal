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

package com.liferay.portal.util;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class MailServiceTestUtil {

	public static int getInboxSize() {
		return _simpleSmtpServer.getReceivedEmailSize();
	}

	public static List<SmtpMessage> getMessages(
		String headerName, String headerValue) {

		List<SmtpMessage> smtpMessages = new ArrayList<SmtpMessage>();

		Iterator<SmtpMessage> iterator = _simpleSmtpServer.getReceivedEmail();

		while (iterator.hasNext()) {
			SmtpMessage smtpMessage = iterator.next();

			if (headerName.equals("Body")) {
				String body = smtpMessage.getBody();

				if (body.equals(headerValue)) {
					smtpMessages.add(smtpMessage);
				}
			}
			else {
				String smtpMessageHeaderValue = smtpMessage.getHeaderValue(
					headerName);

				if (smtpMessageHeaderValue.equals(headerValue)) {
					smtpMessages.add(smtpMessage);
				}
			}
		}

		return smtpMessages;
	}

	public static void start() {
		if (_simpleSmtpServer != null) {
			throw new IllegalStateException("Server is already running");
		}

		_simpleSmtpServer = SimpleSmtpServer.start(
			PropsValues.MAIL_SESSION_MAIL_SMTP_PORT);
	}

	public static void stop() {
		if ((_simpleSmtpServer != null) && _simpleSmtpServer.isStopped()) {
			throw new IllegalStateException("Server is already stopped");
		}

		_simpleSmtpServer.stop();

		_simpleSmtpServer = null;
	}

	private static SimpleSmtpServer _simpleSmtpServer;

}