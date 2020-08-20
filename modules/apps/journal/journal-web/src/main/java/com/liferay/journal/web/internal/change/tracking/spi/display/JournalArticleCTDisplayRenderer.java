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

package com.liferay.journal.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class JournalArticleCTDisplayRenderer
	implements CTDisplayRenderer<JournalArticle> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			JournalArticle journalArticle)
		throws PortalException {

		Group group = _groupLocalService.getGroup(journalArticle.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, JournalPortletKeys.JOURNAL, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_article.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"groupId", String.valueOf(journalArticle.getGroupId())
		).setParameter(
			"articleId", journalArticle.getArticleId()
		).setParameter(
			"version", String.valueOf(journalArticle.getVersion())
		).build();

		return portletURL.toString();
	}

	@Override
	public Class<JournalArticle> getModelClass() {
		return JournalArticle.class;
	}

	@Override
	public String getTitle(Locale locale, JournalArticle journalArticle) {
		return journalArticle.getTitle(locale);
	}

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, JournalArticleCTDisplayRenderer.class);

		return _language.get(
			resourceBundle,
			"model.resource.com.liferay.journal.model.JournalArticle");
	}

	@Override
	public void render(DisplayContext<JournalArticle> displayContext)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/ct_display/render.jsp");

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			WebKeys.JOURNAL_ARTICLE_DISPLAY,
			_journalContent.getDisplay(
				displayContext.getModel(), "", "",
				_language.getLanguageId(httpServletRequest), 1, null,
				themeDisplay));

		requestDispatcher.include(
			httpServletRequest, displayContext.getHttpServletResponse());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.journal.web)")
	private ServletContext _servletContext;

}