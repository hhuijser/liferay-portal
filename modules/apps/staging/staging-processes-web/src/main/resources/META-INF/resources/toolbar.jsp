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

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1");

String searchContainerId = ParamUtil.getString(request, "searchContainerId");

boolean hasPublishStagingPermission = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PUBLISH_STAGING);
%>

<clay:management-toolbar
	actionDropdownItems="<%= stagingProcessesWebToolbarDisplayContext.getActionDropdownItems(hasPublishStagingPermission) %>"
	creationMenu="<%= stagingProcessesWebToolbarDisplayContext.getCreationMenu(hasPublishStagingPermission) %>"
	filterDropdownItems="<%= stagingProcessesWebToolbarDisplayContext.getFilterDropdownItems() %>"
	propsTransformer="js/StagingProcessesWebToolbarPropsTransformer"
	searchContainerId="<%= searchContainerId %>"
	selectable="<%= hasPublishStagingPermission %>"
	showCreationMenu='<%= tabs1.equals("processes") && hasPublishStagingPermission %>'
	showSearch="<%= false %>"
	sortingOrder="<%= stagingProcessesWebToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= stagingProcessesWebToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= stagingProcessesWebToolbarDisplayContext.getViewTypeItems() %>"
/>