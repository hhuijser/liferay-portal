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
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);
%>

<clay:content-row>
	<clay:content-col
		expand="<%= true %>"
	>
		<h4 class="sheet-tertiary-title">
			<liferay-ui:message key="custom-fields" />
		</h4>
	</clay:content-col>

	<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, PortletKeys.EXPANDO, ActionKeys.ACCESS_IN_CONTROL_PANEL) %>">
		<clay:content-col>

			<%
			boolean hasVisibleAttributes = ExpandoAttributesUtil.hasVisibleAttributes(company.getCompanyId(), AccountEntry.class);

			PortletProvider.Action action = PortletProvider.Action.EDIT;

			if (hasVisibleAttributes) {
				action = PortletProvider.Action.MANAGE;
			}

			PortletURL customFieldsURL = PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(request, ExpandoColumn.class.getName(), action)
			).setRedirect(
				currentURL
			).setParameter(
				"modelResource", AccountEntry.class.getName()
			).build();
			%>

			<liferay-ui:icon
				cssClass="modify-link"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message='<%= hasVisibleAttributes ? "manage" : "add" %>'
				method="get"
				url="<%= customFieldsURL.toString() %>"
			/>
		</clay:content-col>
	</c:if>
</clay:content-row>

<liferay-expando:custom-attribute-list
	className="<%= AccountEntry.class.getName() %>"
	classPK="<%= accountEntryDisplay.getAccountEntryId() %>"
	editable="<%= true %>"
	label="<%= true %>"
/>