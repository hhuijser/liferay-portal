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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionLinkException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editCPDefinitionLink"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionLinkMVCActionCommand extends BaseMVCActionCommand {

	protected void addCPDefinitionLinks(ActionRequest actionRequest)
		throws Exception {

		long[] cpDefinitionIds2 = null;

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long cpDefinitionLinkId = ParamUtil.getLong(
			actionRequest, "cpDefinitionLinkId");

		if (cpDefinitionLinkId > 0) {
			cpDefinitionIds2 = new long[] {cpDefinitionLinkId};
		}
		else {
			cpDefinitionIds2 = StringUtil.split(
				ParamUtil.getString(actionRequest, "cpDefinitionIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionLink.class.getName(), actionRequest);

		_cpDefinitionLinkService.updateCPDefinitionLinks(
			cpDefinitionId, cpDefinitionIds2, 0, serviceContext);
	}

	protected void deleteCPDefinitionLinks(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPDefinitionLinkIds = null;

		long cpDefinitionLinkId = ParamUtil.getLong(
			actionRequest, "cpDefinitionLinkId");

		if (cpDefinitionLinkId > 0) {
			deleteCPDefinitionLinkIds = new long[] {cpDefinitionLinkId};
		}
		else {
			deleteCPDefinitionLinkIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPDefinitionLinkIds"),
				0L);
		}

		for (long deleteCPDefinitionLinkId : deleteCPDefinitionLinkIds) {
			_cpDefinitionLinkService.deleteCPDefinitionLink(
				deleteCPDefinitionLinkId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		String backURL = ParamUtil.getString(
			actionRequest, "backURL", redirect);

		redirect = getSaveAndContinueRedirect(
			actionRequest, cpDefinitionId, redirect, backURL);

		try {
			if (cmd.equals(Constants.ADD)) {
				addCPDefinitionLinks(actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPDefinitionLinks(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCPDefinitionLink(actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPDefinitionLinkException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, long cpDefinitionId, String redirect,
			String backURL)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CPDefinition.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCPDefinitionLinks");
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("backURL", backURL);
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinitionId));

		return portletURL.toString();
	}

	protected CPDefinitionLink updateCPDefinitionLink(
			ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionLinkId = ParamUtil.getLong(
			actionRequest, "cpDefinitionLinkId");

		double priority = ParamUtil.getDouble(actionRequest, "priority");

		return _cpDefinitionLinkService.updateCPDefinitionLink(
			cpDefinitionLinkId, priority);
	}

	@Reference
	private CPDefinitionLinkService _cpDefinitionLinkService;

}