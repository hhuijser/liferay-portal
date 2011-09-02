/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.security.permission;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.permission.BasePermissionPropagator;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Hugo Huijser
 * @author Angelo Jefferson
 */
public class WikiPermissionPropagatorImpl extends BasePermissionPropagator {

	@Override
	public void propagateRolePermissions(ActionRequest actionRequest) 
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String selResourceNode = "com.liferay.portlet.wiki.model.WikiNode";
		String selResourcePage = "com.liferay.portlet.wiki.model.WikiPage";

		String resourcePrimKeyNode = ParamUtil.getString(
			actionRequest, "resourcePrimKey");

		Long nodeId = Long.parseLong(resourcePrimKeyNode);

		List<String> nodeActionList = ResourceActionsUtil.
			getModelResourceActions(selResourceNode);

		List<String> pageActionList = ResourceActionsUtil.
			getModelResourceActions(selResourcePage);

		List<String> pageActionExclusive = ListUtil.copy(pageActionList);

		List<String> pageNodeActionsShared = new ArrayList<String>();

		long[] roleIds = StringUtil.split(ParamUtil.getString(
			actionRequest, "rolesSearchContainerPrimaryKeys"), 0L);

		for (String pageActionId: pageActionList) {
			boolean actionIdFound = false;

			for(String nodeActionId: nodeActionList) {
				if (nodeActionId.equals(pageActionId)) {
					actionIdFound = true;
				}
			}

			if (actionIdFound) {
				pageActionExclusive.remove(pageActionId);
				pageNodeActionsShared.add(pageActionId);
			}
		}

		int count = WikiPageLocalServiceUtil.getPagesCount(nodeId);

		List<WikiPage> nodePages =
			WikiPageLocalServiceUtil.getPages(nodeId, 0, count);

		for (WikiPage currentPage: nodePages) {
			String resourcePrimKeyPage = String.valueOf(
				currentPage.getResourcePrimKey());

			for (long roleId : roleIds) {
				List<String> actionIdsFinal = new ArrayList<String>();

				List<String> nodeActionIds =
					ResourcePermissionLocalServiceUtil.
						getAvailableResourcePermissionActionIds(
							themeDisplay.getCompanyId(), selResourceNode,
							ResourceConstants.SCOPE_INDIVIDUAL,
							resourcePrimKeyNode, roleId, nodeActionList);

				List<String> pageActionIds =
					ResourcePermissionLocalServiceUtil.
						getAvailableResourcePermissionActionIds(
							themeDisplay.getCompanyId(), selResourcePage,
							ResourceConstants.SCOPE_INDIVIDUAL,
							resourcePrimKeyPage, roleId, pageActionList);

				for (String actionId: pageActionIds) {
					boolean actionIdFound = false;
					for (String pageExclusiveActionId: pageActionExclusive){
						if ( actionId.equals(pageExclusiveActionId)){
							actionIdFound = true;
						}
					}
					if (actionIdFound) {
						actionIdsFinal.add(actionId);
					}
				}

				for (String actionId: nodeActionIds) {
					boolean actionIdFound = false;
					for (String pageNodeActionShared: pageNodeActionsShared) {
						if ( actionId.equals(pageNodeActionShared)){
							actionIdFound = true;
						}
					}
					if (actionIdFound) {
						actionIdsFinal.add(actionId);
					}
				}

				String[]actionIds = actionIdsFinal.toArray(new String[]{});

				ResourcePermissionServiceUtil.
					setIndividualResourcePermissions(
						themeDisplay.getScopeGroupId(),
						themeDisplay.getCompanyId(),
						selResourcePage, resourcePrimKeyPage,
						roleId, actionIds);
			}
		}
	} 
}