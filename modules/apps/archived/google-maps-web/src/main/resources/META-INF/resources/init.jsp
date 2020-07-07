<%--
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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.google.maps.web.internal.constants.GoogleMapsConstants" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.constants.Constants" %>

<%@ page import="javax.portlet.PortletPreferences" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Group group = themeDisplay.getScopeGroup();

String apiKey = GetterUtil.getString(group.getTypeSettingsProperty("googleMapsAPIKey"), null);

if (Validator.isNull(apiKey)) {
	PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId());

	apiKey = companyPortletPreferences.getValue("googleMapsAPIKey", null);
}

String directionsAddress = GetterUtil.getString(portletPreferences.getValue("directionsAddress", null));
boolean directionsInputEnabled = GetterUtil.getBoolean(portletPreferences.getValue("directionsInputEnabled", null));
String mapAddress = GetterUtil.getString(portletPreferences.getValue("mapAddress", null));
boolean mapInputEnabled = GetterUtil.getBoolean(portletPreferences.getValue("mapInputEnabled", null));

boolean enableChangingTravelingMode = false;

if (directionsInputEnabled) {
	enableChangingTravelingMode = GetterUtil.getBoolean(portletPreferences.getValue("enableChangingTravelingMode", null));
}

int height = GetterUtil.getInteger(portletPreferences.getValue("height", null), 400);
boolean showDirectionSteps = GetterUtil.getBoolean(portletPreferences.getValue("showDirectionSteps", null));
boolean showGoogleMapsLink = GetterUtil.getBoolean(portletPreferences.getValue("showGoogleMapsLink", null));
%>