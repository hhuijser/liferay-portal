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

package com.liferay.portal.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alberto Montero
 * @author Julio Camarero
 */
public class NetvibesServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			String content = getContent(httpServletRequest);

			if (content == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), httpServletRequest,
					httpServletResponse);
			}
			else {
				httpServletRequest.setAttribute(WebKeys.NETVIBES, Boolean.TRUE);

				httpServletResponse.setContentType(ContentTypes.TEXT_HTML);

				ServletResponseUtil.write(httpServletResponse, content);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception,
				httpServletRequest, httpServletResponse);
		}
	}

	protected String getContent(HttpServletRequest httpServletRequest)
		throws Exception {

		String path = GetterUtil.getString(httpServletRequest.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		String portletId = path.substring(
			pos + Portal.FRIENDLY_URL_SEPARATOR.length());

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PortalUtil.getCompanyId(httpServletRequest), portletId);

		String title = HtmlUtil.escape(portlet.getDisplayName());

		String portalURL = PortalUtil.getPortalURL(httpServletRequest);

		String iconURL =
			portalURL + PortalUtil.getPathContext() + portlet.getIcon();

		String widgetURL = String.valueOf(httpServletRequest.getRequestURL());

		widgetURL = widgetURL.replaceFirst(
			PropsValues.NETVIBES_SERVLET_MAPPING,
			PropsValues.WIDGET_SERVLET_MAPPING);
		widgetURL = HtmlUtil.escapeJS(widgetURL);

		return StringBundler.concat(
			"<!DOCTYPE html><html><head><link href=\"", _NETVIBES_CSS,
			"\" rel=\"stylesheet\" type=\"text/css\" /><script src=\"",
			_NETVIBES_JS, "\" type=\"text/javascript\"></script><title>", title,
			"</title><link href=\"", iconURL,
			"\" rel=\"icon\" type=\"image/png",
			"\" /></head><body><iframe frameborder=\"0\" height=\"100%\" src=",
			"\"", widgetURL, "\" width=\"100%\"></iframe></body></html>");
	}

	private static final String _NETVIBES_CSS =
		"http://www.netvibes.com/themes/uwa/style.css";

	private static final String _NETVIBES_JS =
		"http://www.netvibes.com/js/UWA/load.js.php?env=Standalone";

	private static final Log _log = LogFactoryUtil.getLog(
		NetvibesServlet.class);

}