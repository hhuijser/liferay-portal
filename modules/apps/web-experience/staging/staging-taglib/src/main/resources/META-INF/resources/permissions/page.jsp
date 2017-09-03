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

<%@ include file="/permissions/init.jsp" %>

<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" cssClass="options-group" label="permissions" markupView="lexicon">
	<span class="<%= labelCSSClass %>">
		<c:choose>
			<c:when test='<%= action.equals("publish") %>'>
				<liferay-ui:message key="publish-permissions" />
			</c:when>
			<c:when test='<%= action.equals("export") %>'>
				<liferay-ui:message key="export-permissions" />
			</c:when>
			<c:when test='<%= action.equals("import") %>'>
				<liferay-ui:message key="import-permissions" />
			</c:when>
		</c:choose>
	</span>

	<aui:input disabled="<%= disableInputs %>" label="<%= StringPool.BLANK %>" helpMessage='<%= global ? "publish-global-permissions-help" : "export-import-permissions-help" %>' name="<%= PortletDataHandlerKeys.PERMISSIONS %>" type="toggle-switch" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PERMISSIONS, false) %>" />
</aui:fieldset>