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

package com.liferay.flags.taglib.servlet.taglib.soy;

import com.liferay.flags.configuration.FlagsGroupServiceConfiguration;
import com.liferay.flags.taglib.internal.frontend.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Julio Camarero
 * @author Ambrín Chaudhary
 */
public class FlagsTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

		try {
			Map<String, Object> context = getContext();

			boolean enabled = GetterUtil.getBoolean(
				context.get("enabled"), true);

			Company company = themeDisplay.getCompany();

			putValue("companyName", company.getName());

			putValue("flagsEnabled", _isFlagsEnabled(themeDisplay));

			putValue("formData", _getDataJSONObject(context));

			putValue("id", randomNamespace + "id");

			putValue("enabled", enabled);

			boolean label = GetterUtil.getBoolean(context.get("label"), true);

			putValue("label", label);

			putValue(
				"pathTermsOfUse",
				themeDisplay.getPathMain() + "/portal/terms_of_use");

			putValue("pathThemeImages", themeDisplay.getPathThemeImages());

			putValue(
				"portletNamespace",
				PortalUtil.getPortletNamespace(PortletKeys.FLAGS));

			boolean signedIn = themeDisplay.isSignedIn();

			putValue("signedIn", signedIn);

			if (signedIn) {
				User user = themeDisplay.getUser();

				putValue("reporterEmailAddress", user.getEmailAddress());
			}

			putValue("uri", _getURI());

			putValue("reasons", _getReasons(themeDisplay.getCompanyId()));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		setTemplateNamespace("Flags.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName("flags-taglib/flags/Flags.es");
	}

	public void setClassName(String className) {
		putValue("className", className);
	}

	public void setClassPK(long classPK) {
		putValue("classPK", classPK);
	}

	public void setContentTitle(String contentTitle) {
		putValue("contentTitle", contentTitle);
	}

	public void setElementClasses(String elementClasses) {
		putValue("elementClasses", elementClasses);
	}

	public void setEnabled(boolean enabled) {
		putValue("enabled", enabled);
	}

	public void setLabel(boolean label) {
		putValue("label", label);
	}

	public void setMessage(String message) {
		putValue("message", LanguageUtil.get(request, message));
	}

	public void setReportedUserId(long reportedUserId) {
		putValue("reportedUserId", reportedUserId);
	}

	private String _getCurrentURL() {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			return PortalUtil.getCurrentURL(request);
		}

		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			PortalUtil.getLiferayPortletRequest(portletRequest),
			PortalUtil.getLiferayPortletResponse(portletResponse));

		return currentURLObj.toString();
	}

	private JSONObject _getDataJSONObject(Map<String, Object> context) {
		String namespace = PortalUtil.getPortletNamespace(PortletKeys.FLAGS);

		JSONObject dataJSONObject = JSONUtil.put(
			namespace + "className", context.get("className")
		).put(
			namespace + "classPK", context.get("classPK")
		).put(
			namespace + "contentTitle", context.get("contentTitle")
		).put(
			namespace + "contentURL", _getCurrentURL()
		).put(
			namespace + "reportedUserId", context.get("reportedUserId")
		);

		return dataJSONObject;
	}

	private Map<String, String> _getReasons(long companyId)
		throws PortalException {

		FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				FlagsGroupServiceConfiguration.class, companyId);

		Map<String, String> reasons = new HashMap<>();

		for (String reason : flagsGroupServiceConfiguration.reasons()) {
			reasons.put(reason, LanguageUtil.get(request, reason));
		}

		return reasons;
	}

	private String _getURI() {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, PortletKeys.FLAGS, PortletRequest.ACTION_PHASE);

		portletURL.setParameter(ActionRequest.ACTION_NAME, "/flags/edit_entry");

		return portletURL.toString();
	}

	private boolean _isFlagsEnabled(ThemeDisplay themeDisplay)
		throws PortalException {

		FlagsGroupServiceConfiguration flagsGroupServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				FlagsGroupServiceConfiguration.class,
				themeDisplay.getCompanyId());

		if (flagsGroupServiceConfiguration.guestUsersEnabled() ||
			themeDisplay.isSignedIn()) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(FlagsTag.class);

}