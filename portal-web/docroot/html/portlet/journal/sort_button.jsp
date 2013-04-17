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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

String reverseOrderByType = "asc";

if (orderByType.equals("asc")) {
	reverseOrderByType = "desc";
}
%>

<liferay-ui:icon-menu align="left" direction="down" icon="" message="sort-by" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">

	<%
	String taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'title','" + reverseOrderByType + "')";
	%>

	<liferay-ui:icon
		message="title"
		url="<%= taglibURL %>"
	/>

	<%
	taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'display-date','" + reverseOrderByType + "')";
	%>

	<liferay-ui:icon
		message="display-date"
		url="<%= taglibURL %>"
	/>

	<%
	taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'modified-date','" + reverseOrderByType + "')";
	%>

	<liferay-ui:icon
		message="modified-date"
		url="<%= taglibURL %>"
	/>

	<%
	taglibURL = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'id','" + reverseOrderByType + "')";
	%>

	<liferay-ui:icon
		message="id"
		url="<%= taglibURL %>"
	/>


</liferay-ui:icon-menu>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />sortEntries',
		function(folderId, orderByCol, reverseOrderByType) {
			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: {
						'<portlet:namespace />folderId': folderId,
						'<portlet:namespace />struts_action': '/journal/view',
						'<portlet:namespace />viewEntries': <%= Boolean.FALSE.toString() %>,
						'<portlet:namespace />viewEntriesPage': <%= Boolean.TRUE.toString() %>,
						'<portlet:namespace />viewFolders': <%= Boolean.FALSE.toString() %>,
						'<portlet:namespace />orderByCol': orderByCol,
						'<portlet:namespace />orderByType': reverseOrderByType,
						'<portlet:namespace />saveOrderBy': <%= Boolean.TRUE.toString() %>
					}
				}
			);
		},
		['aui-base']
	);
</aui:script>