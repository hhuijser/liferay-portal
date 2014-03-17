<%--
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
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-resource:cssClass"));
String hideAccessibleLabel = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-resource:hideAccessibleLabel"));
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-resource:id"));
String title = (String)request.getAttribute("liferay-ui:input-resource:title");
String url = (String)request.getAttribute("liferay-ui:input-resource:url");

if (Validator.isNull(id) && Validator.isNotNull(hideAccessibleLabel)) {
	id = namespace + PortalUtil.generateRandomKey(request, url);
}
else {
	id = namespace + id;
}
%>

<c:if test="<%= Validator.isNotNull(hideAccessibleLabel) %>">
	<label class="hide-accessible" for="<%= id %>"><liferay-ui:message key="<%= hideAccessibleLabel %>" /></label>
</c:if>

<input class="form-text lfr-input-resource <%= cssClass %>" <%= "id=\"" + id + "\"" %> onClick="Liferay.Util.selectAndCopy(this);" readonly="true" <%= Validator.isNotNull(title) ? "title=\"" + HtmlUtil.escapeAttribute(title) + "\"" : StringPool.BLANK %> type="text" value="<%= HtmlUtil.escapeAttribute(url) %>" />