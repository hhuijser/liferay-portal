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

<%@ include file="/admin/init.jsp" %>

<%
PortletURL portletURL = ddmFormAdminDisplayContext.getPortletURL();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure ddmStructure = (DDMStructure)row.getObject();

FieldSetPermissionCheckerHelper fieldSetPermissionCheckerHelper = ddmFormAdminDisplayContext.getPermissionCheckerHelper();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= fieldSetPermissionCheckerHelper.isShowEditIcon(ddmStructure) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/dynamic_data_mapping_form/edit_element_set_instance" />
			<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
			<portlet:param name="structureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= fieldSetPermissionCheckerHelper.isShowPermissionsIcon(ddmStructure) %>">
		<liferay-security:permissionsURL
			modelResource="<%= DDMStructure.class.getName() %>"
			modelResourceDescription="<%= ddmStructure.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(ddmStructure.getStructureId()) %>"
			var="permissionsElementSetURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsElementSetURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= fieldSetPermissionCheckerHelper.isShowDeleteIcon(ddmStructure) %>">
		<portlet:actionURL name="deleteStructure" var="deleteURL">
			<portlet:param name="structureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>