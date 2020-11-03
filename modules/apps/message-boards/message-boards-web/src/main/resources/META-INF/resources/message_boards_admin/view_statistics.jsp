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

<%@ include file="/message_boards/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view_statistics");
%>

<%@ include file="/message_boards_admin/nav.jspf" %>

<%
long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));

MBCategoryDisplay categoryDisplay = new MBCategoryDisplay(scopeGroupId, categoryId);
%>

<clay:container-fluid>
	<%@ include file="/message_boards/view_statistics_panel.jspf" %>
</clay:container-fluid>

<%
PortalUtil.setPageSubtitle(LanguageUtil.get(request, "statistics"), request);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format("statistics", TextFormatter.O)), portletURL.toString());
%>