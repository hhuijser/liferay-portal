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

package com.liferay.knowledge.base.web.internal.portlet.configuration.icon;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
		"path=/admin/view_article.jsp", "path=/admin/view_articles.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class HistoryKBArticlePortletConfigurationIcon
	extends BaseGetKBArticlePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "history");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				portletRequest, KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/admin/history.jsp"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).build();

		KBArticle kbArticle = getKBArticle(portletRequest);

		portletURL.setParameter(
			"resourceClassNameId", String.valueOf(kbArticle.getClassNameId()));
		portletURL.setParameter(
			"resourcePrimKey", String.valueOf(kbArticle.getResourcePrimKey()));

		portletURL.setParameter(
			"status",
			String.valueOf(
				portletRequest.getAttribute(KBWebKeys.KNOWLEDGE_BASE_STATUS)));

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 109;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		KBArticle kbArticle = getKBArticle(portletRequest);

		if (kbArticle.isApproved() || !kbArticle.isFirstVersion()) {
			return true;
		}

		return false;
	}

	@Reference
	private Portal _portal;

}