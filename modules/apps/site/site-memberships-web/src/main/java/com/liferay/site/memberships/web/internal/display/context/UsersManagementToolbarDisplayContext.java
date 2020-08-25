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

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.membershippolicy.SiteMembershipPolicyUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.memberships.web.internal.util.GroupUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class UsersManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public UsersManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			UsersDisplayContext usersDisplayContext)
		throws PortalException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			usersDisplayContext.getUserSearchContainer());

		_usersDisplayContext = usersDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSelectedUsers");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});

				try {
					if (GroupPermissionUtil.contains(
							themeDisplay.getPermissionChecker(),
							_usersDisplayContext.getGroupId(),
							ActionKeys.ASSIGN_USER_ROLES)) {

						add(
							dropdownItem -> {
								dropdownItem.putData("action", "selectRole");

								PortletURL editUsersRolesURL =
									PortletURLBuilder.createActionURL(
										liferayPortletResponse
									).setActionName(
										"editUsersRoles"
									).build();

								dropdownItem.putData(
									"editUsersRolesURL",
									editUsersRolesURL.toString());

								dropdownItem.putData(
									"selectRoleURL",
									_getSelectorURL("/site_roles.jsp"));

								dropdownItem.setIcon("add-role");
								dropdownItem.setLabel(
									LanguageUtil.get(request, "assign-roles"));
								dropdownItem.setQuickAction(true);
							});

						Role role = _usersDisplayContext.getRole();

						if (role != null) {
							String label = LanguageUtil.format(
								request, "remove-role-x",
								role.getTitle(themeDisplay.getLocale()), false);

							add(
								dropdownItem -> {
									dropdownItem.putData(
										"action", "removeUserRole");
									dropdownItem.putData(
										"message",
										LanguageUtil.format(
											request,
											"are-you-sure-you-want-to-remove-" +
												"x-role-to-selected-users",
											role.getTitle(
												themeDisplay.getLocale())));

									PortletURL removeUserRoleURL =
										PortletURLBuilder.create(
											liferayPortletResponse.
												createActionURL()
										).setActionName(
											"removeUserRole"
										).build();

									dropdownItem.putData(
										"removeUserRoleURL",
										removeUserRoleURL.toString());

									dropdownItem.setIcon("remove-role");
									dropdownItem.setLabel(label);
									dropdownItem.setQuickAction(true);
								});
						}
					}
				}
				catch (Exception exception) {
				}
			}
		};
	}

	public String getAvailableActions(User user) throws PortalException {
		List<String> availableActions = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				_usersDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS) &&
			!SiteMembershipPolicyUtil.isMembershipProtected(
				themeDisplay.getPermissionChecker(), user.getUserId(),
				_usersDisplayContext.getGroupId()) &&
			!SiteMembershipPolicyUtil.isMembershipRequired(
				user.getUserId(), _usersDisplayContext.getGroupId())) {

			availableActions.add("deleteSelectedUsers");
		}

		if (GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getSiteGroupIdOrLiveGroupId(),
				ActionKeys.ASSIGN_USER_ROLES)) {

			availableActions.add("selectRole");
		}

		return StringUtil.merge(availableActions, StringPool.COMMA);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"navigation", "all"
		).setParameter(
			"keywords", StringPool.BLANK
		).setParameter(
			"roleId", "0"
		).build();

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "usersManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		try {
			PortletURL selectUsersURL = PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCPath(
				"/select_users.jsp"
			).setWindowState(
				LiferayWindowState.POP_UP
			).build();

			return CreationMenuBuilder.addDropdownItem(
				dropdownItem -> {
					dropdownItem.putData("action", "selectUsers");

					ThemeDisplay themeDisplay =
						(ThemeDisplay)request.getAttribute(
							WebKeys.THEME_DISPLAY);

					dropdownItem.putData(
						"groupTypeLabel",
						GroupUtil.getGroupTypeLabel(
							_usersDisplayContext.getGroupId(),
							themeDisplay.getLocale()));

					dropdownItem.putData(
						"selectUsersURL", selectUsersURL.toString());
					dropdownItem.setLabel(LanguageUtil.get(request, "add"));
				}
			).build();
		}
		catch (Exception exception) {
			return null;
		}
	}

	@Override
	public String getDefaultEventHandler() {
		return "usersManagementToolbarDefaultEventHandler";
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Role role = _usersDisplayContext.getRole();

		return LabelItemListBuilder.add(
			() -> role != null,
			labelItem -> {
				PortletURL removeLabelURL = PortletURLBuilder.create(
					PortletURLUtil.clone(currentURLObj, liferayPortletResponse)
				).setParameter(
					"roleId", "0"
				).build();

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);

				labelItem.setLabel(role.getTitle(themeDisplay.getLocale()));
			}
		).build();
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "users";
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (GroupPermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					_usersDisplayContext.getGroupId(),
					ActionKeys.ASSIGN_MEMBERS)) {

				return true;
			}
		}
		catch (Exception exception) {
		}

		return false;
	}

	@Override
	public Boolean isShowInfoButton() {
		return true;
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "icon";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive", "icon"};
	}

	@Override
	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(Objects.equals(getNavigation(), "all"));
				dropdownItem.setHref(
					getPortletURL(), "navigation", "all", "roleId", "0");
				dropdownItem.setLabel(LanguageUtil.get(request, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.putData("action", "selectRoles");
				dropdownItem.putData(
					"selectRolesURL", _getSelectorURL("/select_site_role.jsp"));

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				PortletURL viewRoleURL = PortletURLBuilder.createRenderURL(
					liferayPortletResponse
				).setMVCPath(
					"/view.jsp"
				).setRedirect(
					themeDisplay.getURLCurrent()
				).setParameter(
					"tabs1", "users"
				).setParameter(
					"navigation", "roles"
				).setParameter(
					"groupId", _usersDisplayContext.getGroupId()
				).build();

				dropdownItem.putData("viewRoleURL", viewRoleURL.toString());

				dropdownItem.setActive(
					Objects.equals(getNavigation(), "roles"));
				dropdownItem.setLabel(LanguageUtil.get(request, "roles"));
			}
		).build();
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"first-name", "screen-name"};
	}

	private String _getSelectorURL(String mvcPath) throws Exception {
		PortletURL selectURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCPath(
			mvcPath
		).setParameter(
			"groupId", _usersDisplayContext.getGroupId()
		).build();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.getType() == GroupConstants.TYPE_DEPOT) {
			selectURL.setParameter(
				"roleType", String.valueOf(RoleConstants.TYPE_DEPOT));
		}

		selectURL.setWindowState(LiferayWindowState.POP_UP);

		return selectURL.toString();
	}

	private final UsersDisplayContext _usersDisplayContext;

}