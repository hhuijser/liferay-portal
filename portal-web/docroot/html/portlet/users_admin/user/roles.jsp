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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = (User)request.getAttribute("user.selUser");
List<Group> groups = (List<Group>)request.getAttribute("user.groups");
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");
Long[] organizationIds = UsersAdminUtil.getOrganizationIds(organizations);
List<Role> roles = (List<Role>)request.getAttribute("user.roles");
List<UserGroupRole> organizationRoles = (List<UserGroupRole>)request.getAttribute("user.organizationRoles");
List<UserGroupRole> siteRoles = (List<UserGroupRole>)request.getAttribute("user.siteRoles");
List<UserGroupGroupRole> inheritedSiteRoles = (List<UserGroupGroupRole>)request.getAttribute("user.inheritedSiteRoles");
List<Group> allGroups = (List<Group>)request.getAttribute("user.allGroups");

List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

userGroupRoles.addAll(organizationRoles);
userGroupRoles.addAll(siteRoles);
%>

<liferay-ui:error-marker key="errorSection" value="roles" />

<liferay-ui:membership-policy-error />

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:input name="groupRolesRoleIds" type="hidden" value="<%= ListUtil.toString(userGroupRoles, UserGroupRole.ROLE_ID_ACCESSOR) %>" />
<aui:input name="groupRolesGroupIds" type="hidden" value="<%= ListUtil.toString(userGroupRoles, UserGroupRole.GROUP_ID_ACCESSOR) %>" />

<h3><liferay-ui:message key="regular-roles" /></h3>

<%
Map<Role, List<String>> roleGroupNames = new HashMap<Role, List<String>>();

for (Group group : allGroups) {
	List<Role> groupRoles = RoleLocalServiceUtil.getGroupRoles(group.getGroupId());

	for (Role groupRole : groupRoles) {
		if (roleGroupNames.containsKey(groupRole)) {
			List<String> groupNames = roleGroupNames.get(groupRole);

			groupNames.add(group.getDescriptiveName(locale));
		}
		else {
			roleGroupNames.put(groupRole, new ArrayList<String>(Arrays.asList(group.getDescriptiveName(locale))));
		}
	}
}

List<Role> regularRoles = new ArrayList<Role>(roles);

for (Role role : roleGroupNames.keySet()) {
	if (!regularRoles.contains(role)) {
		regularRoles.add(role);
	}
}
%>

<liferay-ui:search-container
	headerNames="title,null"
	id="rolesSearchContainer"
>
	<liferay-ui:search-container-results
		results="<%= regularRoles %>"
		total="<%= regularRoles.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		keyProperty="roleId"
		modelVar="role"
	>

		<%
		boolean hasImplicitRole = roleGroupNames.containsKey(role);
		%>

		<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(role) %>" />
		<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(role) %>" />

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="title"
		>

			<%
			buffer.append(HtmlUtil.escape(role.getTitle(locale)));

			if (hasImplicitRole) {
				List<String> names = roleGroupNames.get(role);

				String message = StringPool.BLANK;

				if (names.size() == 1) {
					message = LanguageUtil.format(pageContext, "this-role-is-assigned-to-x", new Object[] {names.get(0)}, false);
				}
				else {
					message = LanguageUtil.format(pageContext, "this-role-is-assigned-to-x-and-x", new Object[] {StringUtil.merge(names.subList(0, names.size() - 1).toArray(new String[names.size() - 1]), ", "), names.get(names.size() - 1)}, false);
				}
			%>

				<liferay-util:buffer var="iconHelp">
					<liferay-ui:icon-help message="<%= message %>" />
				</liferay-util:buffer>

			<%
				buffer.append(iconHelp);
			}
			%>

		</liferay-ui:search-container-column-text>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !RoleMembershipPolicyUtil.isRoleRequired(selUser.getUserId(), role.getRoleId()) %>">
			<liferay-ui:search-container-column-text>
				<c:if test="<%= !hasImplicitRole %>">
					<a class="modify-link" data-rowId="<%= role.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
				</c:if>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator paginate="<%= false %>" />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectRegularRoleLink"
		label="<%= true %>"
		linkCssClass="btn"
		message="select"
		method="get"
		url="javascript:;"
	/>

	<aui:script use="aui-base">
		A.one('#<portlet:namespace />selectRegularRoleLink').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							width: 600
						},
						id: '<portlet:namespace />selectRegularRole',
						title: '<liferay-ui:message arguments="regular-role" key="select-x" />',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/users_admin/select_regular_role" /><portlet:param name="p_u_i_d" value='<%= (selUser == null) ? "0" : String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>'
					},
					function(event) {
						<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupdescriptivename, event.groupid);
					}
				);
			}
		);
	</aui:script>
</c:if>

<h3><liferay-ui:message key="organization-roles" /></h3>

<c:if test="<%= organizations.isEmpty() && organizationRoles.isEmpty() %>">
	<liferay-ui:message key="this-user-does-not-belong-to-an-organization-to-which-an-organization-role-can-be-assigned" />
</c:if>

<c:if test="<%= !organizations.isEmpty() %>">
	<liferay-ui:search-container
		headerNames="title,organization,null"
		id="organizationRolesSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= organizationRoles %>"
			total="<%= organizationRoles.size() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroupRole"
			keyProperty="roleId"
			modelVar="userGroupRole"
		>
			<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(userGroupRole.getRole()) %>" />
			<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(userGroupRole.getRole()) %>" />

			<liferay-ui:search-container-column-text
				name="title"
				value="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="organization"
				value="<%= HtmlUtil.escape(userGroupRole.getGroup().getDescriptiveName(locale)) %>"
			/>

			<%
			boolean membershipProtected = false;

			Group group = userGroupRole.getGroup();

			Role role = userGroupRole.getRole();

			if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
				membershipProtected = OrganizationMembershipPolicyUtil.isMembershipProtected(permissionChecker, userGroupRole.getUserId(), group.getOrganizationId());
			}
			else {
				membershipProtected = SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, userGroupRole.getUserId(), group.getGroupId());
			}
			%>

			<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !membershipProtected %>">
				<liferay-ui:search-container-column-text>
					<a class="modify-link" data-groupId="<%= userGroupRole.getGroupId() %>" data-rowId="<%= userGroupRole.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator paginate="<%= false %>" />
	</liferay-ui:search-container>

	<aui:script use="liferay-search-container">
		var Util = Liferay.Util;

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />organizationRolesSearchContainer');

		var searchContainerContentBox = searchContainer.get('contentBox');

		searchContainerContentBox.delegate(
			'click',
			function(event) {
				var link = event.currentTarget;
				var tr = link.ancestor('tr');

				var rowId = link.getAttribute('data-rowId');
				var groupId = link.getAttribute('data-groupId');

				var selectOrganizationRole = Util.getWindow('<portlet:namespace />selectOrganizationRole');

				if (selectOrganizationRole) {
					var selectButton = selectOrganizationRole.iframe.node.get('contentWindow.document').one('.selector-button[data-groupid="' + groupId + '"][data-roleid="' + rowId + '"]');

					Util.toggleDisabled(selectButton, false);
				}

				searchContainer.deleteRow(tr, rowId);

				<portlet:namespace />deleteGroupRole(rowId, groupId);
			},
			'.modify-link'
		);

		Liferay.on(
			'<portlet:namespace />syncOrganizationRoles',
			function(event) {
				event.selectors.each(
					function(item, index, collection) {
						var groupId = item.attr('data-groupid');
						var roleId = item.attr('data-roleid');

						var modifyLink = searchContainerContentBox.one('.modify-link[data-groupid="' + groupId + '"][data-rowid="' + roleId + '"]');

						Util.toggleDisabled(item, !!modifyLink);
					}
				);
			}
		);
	</aui:script>
</c:if>

<c:if test="<%= !organizations.isEmpty() && !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<liferay-ui:icon
		cssClass="modify-link"
		iconCssClass="icon-search"
		id="selectOrganizationRoleLink"
		label="<%= true %>"
		linkCssClass="btn"
		message="select"
		method="get"
		url="javascript:;"
	/>

	<aui:script use="aui-base">
		A.one('#<portlet:namespace />selectOrganizationRoleLink').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							modal: true
						},
						id: '<portlet:namespace />selectOrganizationRole',
						title: '<liferay-ui:message arguments="organization-role" key="select-x" />',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/users_admin/select_organization_role" /><portlet:param name="step" value="1" /><portlet:param name="organizationIds" value="<%= StringUtil.merge(organizationIds) %>" /><portlet:param name="p_u_i_d" value='<%= (selUser == null) ? "0" : String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>'
					},
					function(event) {
						<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupdescriptivename, event.groupid);
					}
				);
			}
		);
	</aui:script>
</c:if>

<h3><liferay-ui:message key="site-roles" /></h3>

<c:choose>
	<c:when test="<%= groups.isEmpty() %>">
		<liferay-ui:message key="this-user-does-not-belong-to-a-site-to-which-a-site-role-can-be-assigned" />
	</c:when>
	<c:otherwise>
		<liferay-ui:search-container
			headerNames="title,site,null"
			id="siteRolesSearchContainer"
		>
			<liferay-ui:search-container-results
				results="<%= siteRoles %>"
				total="<%= siteRoles.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.UserGroupRole"
				keyProperty="roleId"
				modelVar="userGroupRole"
			>
				<liferay-util:param name="className" value="<%= RolesAdminUtil.getCssClassName(userGroupRole.getRole()) %>" />
				<liferay-util:param name="classHoverName" value="<%= RolesAdminUtil.getCssClassName(userGroupRole.getRole()) %>" />

				<liferay-ui:search-container-column-text
					name="title"
					value="<%= HtmlUtil.escape(userGroupRole.getRole().getTitle(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					name="site"
					value="<%= HtmlUtil.escape(userGroupRole.getGroup().getDescriptiveName(locale)) %>"
				/>

				<%
				boolean membershipProtected = false;

				Group group = userGroupRole.getGroup();

				Role role = userGroupRole.getRole();

				if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
					membershipProtected = OrganizationMembershipPolicyUtil.isMembershipProtected(permissionChecker, userGroupRole.getUserId(), group.getOrganizationId());
				}
				else {
					membershipProtected = SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, userGroupRole.getUserId(), group.getGroupId());
				}
				%>

				<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) && !membershipProtected %>">
					<liferay-ui:search-container-column-text>
						<a class="modify-link" data-groupId="<%= userGroupRole.getGroupId() %>" data-rowId="<%= userGroupRole.getRoleId() %>" href="javascript:;"><%= removeRoleIcon %></a>
					</liferay-ui:search-container-column-text>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator paginate="<%= false %>" />
		</liferay-ui:search-container>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
			<liferay-ui:icon
				cssClass="modify-link"
				iconCssClass="icon-search"
				id="selectSiteRoleLink"
				label="<%= true %>"
				linkCssClass="btn"
				message="select"
				method="get"
				url="javascript:;"
			/>

			<aui:script use="liferay-search-container">
				var Util = Liferay.Util;

				var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />siteRolesSearchContainer');

				var searchContainerContentBox = searchContainer.get('contentBox');

				searchContainerContentBox.delegate(
					'click',
					function(event) {
						var link = event.currentTarget;
						var tr = link.ancestor('tr');

						var rowId = link.getAttribute('data-rowId');
						var groupId =link.getAttribute('data-groupId');

						var selectSiteRole = Util.getWindow('<portlet:namespace />selectSiteRole');

						if (selectSiteRole) {
							var selectButton = selectSiteRole.iframe.node.get('contentWindow.document').one('.selector-button[data-groupid="' + groupId + '"][data-roleid="' + rowId + '"]');

							Util.toggleDisabled(selectButton, false);
						}

						searchContainer.deleteRow(tr, rowId);

						<portlet:namespace />deleteGroupRole(rowId, groupId);
					},
					'.modify-link'
				);

				Liferay.on(
					'<portlet:namespace />syncSiteRoles',
					function(event) {
						event.selectors.each(
							function(item, index, collection) {
								var groupId = item.attr('data-groupid');
								var roleId = item.attr('data-roleid');

								var modifyLink = searchContainerContentBox.one('.modify-link[data-groupid="' + groupId + '"][data-rowid="' + roleId + '"]');

								Util.toggleDisabled(item, !!modifyLink);
							}
						);
					}
				);

				A.one('#<portlet:namespace />selectSiteRoleLink').on(
					'click',
					function(event) {
						Util.selectEntity(
							{
								dialog: {
									constrain: true,
									modal: true,
									width: 600
								},
								id: '<portlet:namespace />selectSiteRole',
								title: '<liferay-ui:message arguments="site-role" key="select-x" />',
								uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/users_admin/select_site_role" /><portlet:param name="step" value="1" /><portlet:param name="p_u_i_d" value='<%= (selUser == null) ? "0" : String.valueOf(selUser.getUserId()) %>' /></portlet:renderURL>'
							},
							function(event) {
								<portlet:namespace />selectRole(event.roleid, event.roletitle, event.searchcontainername, event.groupdescriptivename, event.groupid);
							}
						);
					}
				);
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>

<aui:script>
	var <portlet:namespace />groupRolesGroupIds = ['<%= ListUtil.toString(userGroupRoles, UserGroupRole.GROUP_ID_ACCESSOR, "', '") %>'];
	var <portlet:namespace />groupRolesRoleIds = ['<%= ListUtil.toString(userGroupRoles, UserGroupRole.ROLE_ID_ACCESSOR, "', '") %>'];

	function <portlet:namespace />deleteGroupRole(roleId, groupId) {
		for (var i = 0; i < <portlet:namespace />groupRolesRoleIds.length; i++) {
			if ((<portlet:namespace />groupRolesRoleIds[i] == roleId) && (<portlet:namespace />groupRolesGroupIds[i] == groupId)) {
				<portlet:namespace />groupRolesGroupIds.splice(i, 1);
				<portlet:namespace />groupRolesRoleIds.splice(i, 1);

				break;
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />groupRolesGroupIds.value = <portlet:namespace />groupRolesGroupIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />groupRolesRoleIds.value = <portlet:namespace />groupRolesRoleIds.join(',');
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectRole',
		function(roleId, name, searchContainer, groupName, groupId) {
			var A = AUI();

			var searchContainerName = '<portlet:namespace />' + searchContainer + 'SearchContainer';

			searchContainer = Liferay.SearchContainer.get(searchContainerName);

			var rowColumns = [];

			rowColumns.push(name);

			if (groupName) {
				rowColumns.push(groupName);
			}

			if (groupId) {
				rowColumns.push('<a class="modify-link" data-groupId="' + groupId + '" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');

				<portlet:namespace />groupRolesRoleIds.push(roleId);
				<portlet:namespace />groupRolesGroupIds.push(groupId);

				document.<portlet:namespace />fm.<portlet:namespace />groupRolesRoleIds.value = <portlet:namespace />groupRolesRoleIds.join(',');
				document.<portlet:namespace />fm.<portlet:namespace />groupRolesGroupIds.value = <portlet:namespace />groupRolesGroupIds.join(',');
			}
			else {
				rowColumns.push('<a class="modify-link" data-rowId="' + roleId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeRoleIcon) %></a>');
			}

			searchContainer.addRow(rowColumns, roleId);
			searchContainer.updateDataStore();
		},
		['liferay-search-container']
	);
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />rolesSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			var selectRegularRole = Util.getWindow('<portlet:namespace />selectRegularRole');

			if (selectRegularRole) {
				var selectButton = selectRegularRole.iframe.node.get('contentWindow.document').one('.selector-button[data-roleid="' + rowId + '"]');

				Util.toggleDisabled(selectButton, false);
			}

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);

	Liferay.on(
		'<portlet:namespace />enableRemovedRegularRoles',
		function(event) {
			event.selectors.each(
				function(item, index, collection) {
					var modifyLink = searchContainerContentBox.one('.modify-link[data-rowid="' + item.attr('data-roleid') + '"]');

					if (!modifyLink) {
						Util.toggleDisabled(item, false);
					}
				}
			);
		}
	);
</aui:script>