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

package com.liferay.portal.osgi.web.portlet.container.upload.servlet.request.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadServletRequestImpl;

import java.io.InputStream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class UploadServletRequestWhenGettingFilesAsStreamTest {

	@ClassRule
	@Rule
	public static final TestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_fileNameParameter = RandomTestUtil.randomString();
	}

	@Test
	public void testShouldReturnArrayWithStreamsFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(10, _BYTES);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _BYTES);

		UploadServletRequestImpl uploadServletRequestImpl =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Map<String, FileItem[]> map =
			uploadServletRequestImpl.getMultipartParameterMap();

		Assert.assertEquals(map.toString(), 10, map.size());

		for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
			String key = entry.getKey();

			InputStream[] inputStreams =
				uploadServletRequestImpl.getFilesAsStream(key);

			FileItem[] fileItems = entry.getValue();

			Assert.assertEquals(
				Arrays.toString(inputStreams), fileItems.length,
				inputStreams.length);

			Assert.assertEquals(
				Arrays.toString(inputStreams), 2, inputStreams.length);

			for (int i = 0; i < inputStreams.length; i++) {
				Assert.assertTrue(
					IOUtils.contentEquals(
						fileItems[i].getInputStream(), inputStreams[i]));
			}
		}
	}

	@Test
	public void testShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _BYTES);

		UploadServletRequestImpl uploadServletRequestImpl =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequestImpl.getFilesAsStream("irrelevantName"));
	}

	@Test
	public void testShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(1, _BYTES);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _BYTES);

		UploadServletRequestImpl uploadServletRequestImpl =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequestImpl.getFilesAsStream("nonexistentFile"));
	}

	private static final byte[] _BYTES =
		"Enterprise. Open Source. For Life.".getBytes();

	private static String _fileNameParameter;

}