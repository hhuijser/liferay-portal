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

package com.liferay.opensocial.shindig.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.constants.JavaConstants;

import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dennis Ju
 */
public class JsServlet extends org.apache.shindig.gadgets.servlet.JsServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		String requestURI = httpServletRequest.getRequestURI();

		if (!requestURI.equals("/combo")) {
			super.doGet(httpServletRequest, httpServletResponse);

			return;
		}

		StringBundler sb = new StringBundler(3);

		sb.append(
			httpServletRequest.getAttribute(
				JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI));
		sb.append(CharPool.QUESTION);
		sb.append(
			httpServletRequest.getAttribute(
				JavaConstants.JAVAX_SERVLET_INCLUDE_QUERY_STRING));

		String urlString = PortalUtil.getAbsoluteURL(
			httpServletRequest, sb.toString());

		URL url = new URL(urlString);

		URLConnection urlConnection = url.openConnection();

		ServletResponseUtil.write(
			httpServletResponse, urlConnection.getInputStream());
	}

}