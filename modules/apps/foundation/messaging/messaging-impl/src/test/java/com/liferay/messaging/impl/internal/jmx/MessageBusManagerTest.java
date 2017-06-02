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

package com.liferay.messaging.impl.internal.jmx;

import com.liferay.messaging.MessageBus;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Michael C. Han
 * @author Miguel Pastor
 */
@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
public class MessageBusManagerTest {

	@Before
	public void setUp() throws Exception {
		_mBeanServer = ManagementFactory.getPlatformMBeanServer();
	}

	@Test
	public void testRegisterMBean() throws Exception {
		MessageBusManager messageBusManager = new MessageBusManager();

		messageBusManager.setMessageBus(_messageBus);

		ObjectName objectName = new ObjectName(
			"com.liferay.portal.messaging:classification=message_bus," +
				"name=MessageBusManager");

		_mBeanServer.registerMBean(messageBusManager, objectName);

		Assert.assertTrue(_mBeanServer.isRegistered(objectName));
	}

	private MBeanServer _mBeanServer;

	@Mock
	private MessageBus _messageBus;

}