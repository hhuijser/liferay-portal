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

/**
 * @author John Zhao
 */
package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MBUtilTest {

	@Before
	public void setUp() throws Exception {
		_messageIdString = "<mb_message.10640.20646.1425017183884@gmail.com>";
		_recipient = "mb_message.10640.20646.1425017183884@event.gmail.com";
		_categoryId = 10640;
		_messageId = 20646;
	}

	@Test
	public void testGetCategoryId() throws Exception {
		ReflectionTestUtil.setFieldValue(PropsValues.class,
			"POP_SERVER_SUBDOMAIN", StringPool.BLANK);

		Assert.assertEquals(_categoryId, MBUtil.getCategoryId(_messageIdString));

		ReflectionTestUtil.setFieldValue(PropsValues.class,
			"POP_SERVER_SUBDOMAIN", "events");

		Assert.assertEquals(_categoryId, MBUtil.getCategoryId(_recipient));
	}

	@Test
	public void testGetMessageId() throws Exception {
		ReflectionTestUtil.setFieldValue(PropsValues.class,
			"POP_SERVER_SUBDOMAIN", StringPool.BLANK);

		Assert.assertEquals(_messageId, MBUtil.getMessageId(_messageIdString));

		ReflectionTestUtil.setFieldValue(PropsValues.class,
			"POP_SERVER_SUBDOMAIN", "events");

		Assert.assertEquals(_messageId, MBUtil.getMessageId(_recipient));
	}

	private String _messageIdString;
	private String _recipient;
	private long _messageId;
	private long _categoryId;

}