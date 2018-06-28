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

package com.liferay.exportimport.resources.importer.internal.portlet.configuration.icon;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"path=/edit_article.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class ViewJournalSourcePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "view-source");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		JournalArticle article = null;

		article = getArticle(portletRequest);

		if (article == null) {
			return StringPool.BLANK;
		}

		return "var sourceModal = " + getWindowJS(portletRequest, article) +
			" return false;";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public double getWeight() {
		return 100.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			if (getArticle(portletRequest) != null) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	protected JournalArticle getArticle(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			request, "groupId", themeDisplay.getScopeGroupId());

		long classNameId = ParamUtil.getLong(request, "classNameId");
		long classPK = ParamUtil.getLong(request, "classPK");
		String articleId = ParamUtil.getString(request, "articleId");
		int status = ParamUtil.getInteger(
			request, "status", WorkflowConstants.STATUS_ANY);

		JournalArticle article = null;

		if (Validator.isNotNull(articleId)) {
			article = _journalArticleLocalService.fetchLatestArticle(
				groupId, articleId, status);
		}
		else if ((classNameId > 0) &&
				 (classPK > JournalArticleConstants.CLASSNAME_ID_DEFAULT)) {

			String className = _portal.getClassName(classNameId);

			try {
				article = _journalArticleLocalService.getLatestArticle(
					groupId, className, classPK);
			}
			catch (PortalException pe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				return null;
			}
		}

		return article;
	}

	protected JournalArticle getArticle(PortletRequest request) {
		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			request);

		return getArticle(httpServletRequest);
	}

	protected String getDialogJS(JournalArticle article) {
		StringBundler sb = new StringBundler(6);

		sb.append("{bodyContent: '<pre>");
		sb.append(HtmlUtil.escapeJS(HtmlUtil.escape(article.getContent())));
		sb.append("</pre>', modal: true, toolbars: {footer: ");
		sb.append("[{label:Liferay.Language.get('close'), on: {click: ");
		sb.append("function(event) {event.domEvent.preventDefault(); ");
		sb.append("sourceModal.hide();}}}]}}");

		return sb.toString();
	}

	protected String getWindowJS(
		PortletRequest portletRequest, JournalArticle article) {

		StringBundler sb = new StringBundler(14);

		sb.append("Liferay.Util.Window.getWindow({bodyCssClass: ");
		sb.append("'dialog-with-footer', destroyOnHide: true, dialog: ");
		sb.append(getDialogJS(article));
		sb.append(", id: '");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		sb.append(HtmlUtil.escape(portletDisplay.getNamespace()));

		sb.append("viewSource', namespace: '");
		sb.append(portletDisplay.getNamespace());
		sb.append("', portlet: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(
			HtmlUtil.escapeJS(
				HtmlUtil.escape(article.getTitle(themeDisplay.getLocale()))));
		sb.append("'});");

		return sb.toString();
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewJournalSourcePortletConfigurationIcon.class);

	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private Portal _portal;

}