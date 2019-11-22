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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<%@ page import="java.util.Optional" %>

<portlet:defineObjects />

<%
SiteFacetPortletPreferences siteFacetPortletPreferences = new SiteFacetPortletPreferencesImpl(Optional.ofNullable(portletPreferences));
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<aui:input label="site-parameter-name" name="<%= PortletPreferencesJspUtil.getInputName(SiteFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME) %>" value="<%= siteFacetPortletPreferences.getParameterName() %>" />

			<aui:input label="max-terms" name="<%= PortletPreferencesJspUtil.getInputName(SiteFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS) %>" value="<%= siteFacetPortletPreferences.getMaxTerms() %>" />

			<aui:input label="frequency-threshold" name="<%= PortletPreferencesJspUtil.getInputName(SiteFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD) %>" value="<%= siteFacetPortletPreferences.getFrequencyThreshold() %>" />

			<aui:input label="display-frequencies" name="<%= PortletPreferencesJspUtil.getInputName(SiteFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE) %>" type="checkbox" value="<%= siteFacetPortletPreferences.isFrequenciesVisible() %>" />
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>