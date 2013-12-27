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

package com.liferay.portlet.sites.action;

import com.liferay.portal.MembershipRequestCommentsException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;
import com.liferay.portlet.social.util.MembersRequestKeys;

import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jorge Ferrer
 */
public class PostMembershipRequestAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String comments = ParamUtil.getString(actionRequest, "comments");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			Group group = GroupLocalServiceUtil.getGroup(
				themeDisplay.getScopeGroupId());

			Role siteAdminRole = RoleLocalServiceUtil.getRole(
				themeDisplay.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

			LinkedHashMap<String, Object> userParams =
				new LinkedHashMap<String, Object>();

			userParams.put(
				"userGroupRole", new Long[] {group.getGroupId(),
				siteAdminRole.getRoleId()});

			List<User> users = UserLocalServiceUtil.search(
				themeDisplay.getCompanyId(), null,
				WorkflowConstants.STATUS_APPROVED, userParams,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, (OrderByComparator)null);

			if (users.isEmpty()) {
				Role adminRole = RoleLocalServiceUtil.getRole(
					themeDisplay.getCompanyId(), RoleConstants.ADMINISTRATOR);

				userParams.clear();

				userParams.put("usersRoles", adminRole.getRoleId());

				users = UserLocalServiceUtil.search(
					themeDisplay.getCompanyId(), null,
					WorkflowConstants.STATUS_APPROVED, userParams,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					(OrderByComparator)null);
			}

			for (User user : users) {
				SocialRequestLocalServiceUtil.addRequest(
					themeDisplay.getUserId(), 0, Group.class.getName(),
					group.getGroupId(), MembersRequestKeys.ADD_MEMBER,
					StringPool.BLANK, user.getUserId());
			}

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			MembershipRequestServiceUtil.addMembershipRequest(
				groupId, comments, serviceContext);

			SessionMessages.add(actionRequest, "membershipRequestSent");

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.sites_admin.error");
			}
			else if (e instanceof MembershipRequestCommentsException) {
				SessionErrors.add(actionRequest, e.getClass());

				setForward(
					actionRequest,
					"portlet.sites_admin.post_membership_request");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.sites_admin.post_membership_request"));
	}

}