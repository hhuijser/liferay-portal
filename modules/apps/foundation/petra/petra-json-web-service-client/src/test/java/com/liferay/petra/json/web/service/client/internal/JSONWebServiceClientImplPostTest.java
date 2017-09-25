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

package com.liferay.petra.json.web.service.client.internal;

import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.petra.json.web.service.client.serversimulator.HTTPServerSimulator;
import com.liferay.petra.json.web.service.client.serversimulator.SimulatorConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class JSONWebServiceClientImplPostTest
	extends JSONWebServiceClientBaseTest {

	@Before
	public void setUp() throws Exception {
		HTTPServerSimulator.start();
	}

	@After
	public void tearDown() {
		HTTPServerSimulator.stop();
	}

	@Test
	public void testResponse200OnPost() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		Map<String, String> params = new HashMap<String, String>();

		params.put(
			SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, "200");
		params.put(
			SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON, "true");

		String json = jsonWebServiceClientImpl.doPost("/testPost/", params);

		Assert.assertTrue(
			json,
			json.contains(
				SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS));
	}

	@Test
	public void testResponse201OnPost() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		Map<String, String> params = new HashMap<String, String>();

		params.put(
			SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, "201");
		params.put(
			SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON, "true");

		String json = jsonWebServiceClientImpl.doPost("/testPost/", params);

		Assert.assertTrue(
			json,
			json.contains(
				SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS));
	}

	@Test
	public void testResponse202OnPost() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "headerKey1=headerValue1;Accept=application/json;");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		Map<String, String> params = new HashMap<String, String>();

		params.put(
			SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, "202");
		params.put(
			SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON, "true");

		String json = jsonWebServiceClientImpl.doPost("/testPost/", params);

		Assert.assertEquals(SimulatorConstants.RESPONSE_SUCCESS_IN_JSON, json);
	}

	@Test
	public void testResponse204OnPost() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "Accept=application/json;headerKey1=headerValue1");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		Map<String, String> params = new HashMap<String, String>();

		params.put(
			SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, "204");
		params.put(
			SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON, "true");

		String json = jsonWebServiceClientImpl.doPost("/testPost/", params);

		Assert.assertNull(json);
	}

	@Test(expected = JSONWebServiceInvocationException.class)
	public void testResponse400OnPost() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		jsonWebServiceClientImpl.doPost(
			"/", Collections.<String, String>emptyMap());
	}

	@Test(
		expected = JSONWebServiceTransportException.AuthenticationFailure.class
	)
	public void testResponse401OnPost() throws Exception {
		Map<String, Object> clientProperties = getBaseProperties();

		clientProperties.put("protocol", "http");
		clientProperties.put(
			"headers", "Accept=application/json;headerKey1=headerValue1");

		JSONWebServiceClientImpl jsonWebServiceClientImpl =
			new JSONWebServiceClientImpl();

		jsonWebServiceClientImpl.activate(clientProperties);

		Map<String, String> params = new HashMap<String, String>();

		params.put(
			SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, "401");
		params.put(
			SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON, "true");

		jsonWebServiceClientImpl.doPost("/testPost/", params);
	}

}