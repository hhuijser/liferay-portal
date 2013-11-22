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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

String type = ParamUtil.getString(request, "type");
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
	<liferay-util:param name="tabs1" value="history" />
</liferay-util:include>

<liferay-util:include page="/html/portlet/wiki/page_tabs_history.jsp" />

<liferay-ui:header
	backURL="<%= backURL %>"
	title="compare-versions"
/>

<liferay-util:include page="/html/portlet/wiki/history_navigation.jsp">
	<liferay-util:param name="mode" value="<%= type %>" />
</liferay-util:include>

<c:choose>
	<c:when test='<%= type.equals("html") %>'>

		<%
		String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);
		%>

		<liferay-ui:diff-html diffHtmlResults="<%= diffHtmlResults %>" />
	</c:when>
	<c:otherwise>

		<%
		String title = (String)request.getAttribute(WebKeys.TITLE);
		Version sourceVersion = (Version)request.getAttribute(WebKeys.SOURCE_VERSION);
		Version targetVersion = (Version)request.getAttribute(WebKeys.TARGET_VERSION);
		List[] diffResults = (List[])request.getAttribute(WebKeys.DIFF_RESULTS);
		%>

		<liferay-ui:diff
			diffResults="<%= diffResults %>"
			sourceName="<%= title + StringPool.SPACE + sourceVersion.toString() %>"
			targetName="<%= title + StringPool.SPACE + targetVersion.toString() %>"
		/>
	</c:otherwise>
</c:choose>