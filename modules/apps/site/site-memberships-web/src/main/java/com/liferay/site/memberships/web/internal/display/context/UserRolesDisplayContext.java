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

package com.liferay.site.memberships.web.internal.display.context;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.rolesadmin.search.RoleSearch;
import com.liferay.portlet.rolesadmin.search.RoleSearchTerms;
import com.liferay.portlet.sites.search.UserGroupRoleRoleChecker;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UserRolesDisplayContext {

	public UserRolesDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "icon");

		return _displayStyle;
	}

	public String getEventName() {
		if (Validator.isNotNull(_eventName)) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, "eventName",
			_renderResponse.getNamespace() + "selectUsersRoles");

		return _eventName;
	}

	public SearchContainer<Role> getRoleSearchSearchContainer()
		throws PortalException {

		if (_roleSearch != null) {
			return _roleSearch;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		RoleSearch roleSearch = new RoleSearch(
			_renderRequest, _getPortletURL());

		Group group = GroupLocalServiceUtil.fetchGroup(_getGroupId());

		roleSearch.setRowChecker(
			new UserGroupRoleRoleChecker(
				_renderResponse,
				PortalUtil.getSelectedUser(_httpServletRequest, false), group));

		RoleSearchTerms searchTerms =
			(RoleSearchTerms)roleSearch.getSearchTerms();

		List<Role> roles = RoleLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			new Integer[] {_getRoleType()}, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, roleSearch.getOrderByComparator());

		if (group.getType() == GroupConstants.TYPE_DEPOT) {
			roles = _filterGroupRoles(
				themeDisplay.getPermissionChecker(), _getGroupId(), roles);
		}
		else {
			roles = UsersAdminUtil.filterGroupRoles(
				themeDisplay.getPermissionChecker(), _getGroupId(), roles);
		}

		int rolesCount = roles.size();

		roleSearch.setTotal(rolesCount);

		roles = ListUtil.subList(
			roles, roleSearch.getStart(), roleSearch.getEnd());

		roleSearch.setResults(roles);

		_roleSearch = roleSearch;

		return _roleSearch;
	}

	/**
	 * @see com.liferay.depot.web.internal.display.context.DepotAdminMembershipsDisplayContext.Step2#_filterGroupRoles(
	 *      List)
	 */
	private List<Role> _filterGroupRoles(
			PermissionChecker permissionChecker, long groupId, List<Role> roles)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin() ||
			permissionChecker.isGroupOwner(groupId)) {

			Stream<Role> stream = roles.stream();

			return stream.filter(
				role ->
					!Objects.equals(
						role.getName(),
						DepotRolesConstants.
							ASSET_LIBRARY_CONNECTED_SITE_MEMBER) &&
					!Objects.equals(
						role.getName(),
						DepotRolesConstants.ASSET_LIBRARY_MEMBER)
			).collect(
				Collectors.toList()
			);
		}

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.ASSIGN_USER_ROLES)) {

			return Collections.emptyList();
		}

		Stream<Role> stream = roles.stream();

		return stream.filter(
			role ->
				!Objects.equals(
					role.getName(),
					DepotRolesConstants.ASSET_LIBRARY_CONNECTED_SITE_MEMBER) &&
				!Objects.equals(
					role.getName(), DepotRolesConstants.ASSET_LIBRARY_MEMBER) &&
				!Objects.equals(
					role.getName(),
					DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR) &&
				!Objects.equals(
					role.getName(), DepotRolesConstants.ASSET_LIBRARY_OWNER) &&
				RolePermissionUtil.contains(
					permissionChecker, groupId, role.getRoleId(),
					ActionKeys.ASSIGN_MEMBERS)
		).collect(
			Collectors.toList()
		);
	}

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		_groupId = ParamUtil.getLong(
			_httpServletRequest, "groupId",
			themeDisplay.getSiteGroupIdOrLiveGroupId());

		return _groupId;
	}

	private PortletURL _getPortletURL() throws PortalException {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/users_roles.jsp"
		).setParameter(
			"p_u_i_d", _getUserId()
		).build();

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = ParamUtil.getString(_renderRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "title");

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		int roleType = _getRoleType();

		if (roleType > 0) {
			portletURL.setParameter("roleType", String.valueOf(roleType));
		}

		return portletURL;
	}

	private int _getRoleType() {
		if (_roleType != null) {
			return _roleType;
		}

		_roleType = ParamUtil.getInteger(
			_httpServletRequest, "roleType", RoleConstants.TYPE_SITE);

		return _roleType;
	}

	private long _getUserId() throws PortalException {
		User selUser = PortalUtil.getSelectedUser(_httpServletRequest, false);

		if (selUser != null) {
			return selUser.getUserId();
		}

		return 0;
	}

	private String _displayStyle;
	private String _eventName;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private RoleSearch _roleSearch;
	private Integer _roleType;

}