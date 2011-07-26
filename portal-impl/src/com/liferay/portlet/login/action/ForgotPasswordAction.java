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

package com.liferay.portlet.login.action;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.RequiredReminderQueryException;
import com.liferay.portal.SendPasswordException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.UserReminderQueryException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.login.util.LoginUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Tibor Kovacs
 */
public class ForgotPasswordAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			if (PropsValues.USERS_REMINDER_QUERIES_ENABLED) {
				int step = ParamUtil.getInteger(actionRequest, "step");

				if (PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD &&
					(step != 2)) {

					CaptchaUtil.check(actionRequest);
				}

				User user = getUser(actionRequest);

				if(PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD ||
					user.hasReminderQuery()) {

					actionRequest.setAttribute(
						ForgotPasswordAction.class.getName(), user);

					PortletSession portletSession =
						actionRequest.getPortletSession();

					int failedAttempts = GetterUtil.getInteger((Integer)
						portletSession.getAttribute("failed-attempts"), 0);

					if (step == 2) {
						if (PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD &&
							( failedAttempts > 2)) {

							CaptchaUtil.check(actionRequest);
						}

						sendPassword(actionRequest, actionResponse);
					}
				}
			}
			else {
				if (PropsValues.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD) {
					CaptchaUtil.check(actionRequest);
				}

				sendPassword(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof CaptchaTextException ||
				e instanceof NoSuchUserException ||
				e instanceof RequiredReminderQueryException ||
				e instanceof SendPasswordException ||
				e instanceof UserEmailAddressException ||
				e instanceof UserReminderQueryException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else {
				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		renderResponse.setTitle(themeDisplay.translate("forgot-password"));

		return mapping.findForward("portlet.login.forgot_password");
	}

	protected User getUser(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = ParamUtil.getLong(actionRequest, "userId");
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");

		User user = null;

		if (Validator.isNotNull(emailAddress)) {
			user = UserLocalServiceUtil.getUserByEmailAddress(
				themeDisplay.getCompanyId(), emailAddress);
		}
		else if (Validator.isNotNull(screenName)) {
			user = UserLocalServiceUtil.getUserByScreenName(
				themeDisplay.getCompanyId(), screenName);
		}
		else if (userId > 0) {
			user = UserLocalServiceUtil.getUserById(userId);
		}
		else {
			throw new NoSuchUserException();
		}

		return user;
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void sendPassword(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		User user = getUser(actionRequest);

		if (PropsValues.USERS_REMINDER_QUERIES_ENABLED) {
			if (PropsValues.USERS_REMINDER_QUERIES_REQUIRED &&
				!user.hasReminderQuery()) {

				throw new RequiredReminderQueryException(
					"No reminder query or answer is defined for user " +
						user.getUserId());
			}

			String answer = ParamUtil.getString(actionRequest, "answer");

			if (!user.getReminderQueryAnswer().equals(answer)) {
				throw new UserReminderQueryException();
			}
		}

		PortletPreferences preferences = actionRequest.getPreferences();

		String languageId = LanguageUtil.getLanguageId(actionRequest);

		String emailFromName = preferences.getValue("emailFromName", null);
		String emailFromAddress = preferences.getValue(
			"emailFromAddress", null);
		String emailToAddress = user.getEmailAddress();

		String emailParam = "emailPasswordSent";

		if (company.isSendPasswordResetLink()) {
			emailParam = "emailPasswordReset";
		}

		String subject = preferences.getValue(
			emailParam + "Subject_" + languageId, null);
		String body = preferences.getValue(
			emailParam + "Body_" + languageId, null);

		LoginUtil.sendPassword(
			actionRequest, emailFromName, emailFromAddress, emailToAddress,
			subject, body);

		sendRedirect(actionRequest, actionResponse);
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}