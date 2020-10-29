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

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class MBNavigationDisplayContext {

	public MBNavigationDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		MBGroupServiceSettings mbGroupServiceSettings) {

		_renderResponse = renderResponse;
		_mbGroupServiceSettings = mbGroupServiceSettings;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				String mvcRenderCommandName = ParamUtil.getString(
					_httpServletRequest, "mvcRenderCommandName",
					"/message_boards/view");

				PortletURL messageBoardsHomeURL =
					_renderResponse.createRenderURL();

				messageBoardsHomeURL.setParameter(
					"mvcRenderCommandName", "/message_boards/view");
				messageBoardsHomeURL.setParameter("tag", StringPool.BLANK);

				add(
					navigationItem -> {
						boolean active = false;

						if (mvcRenderCommandName.equals(
								"/message_boards/edit_category") ||
							mvcRenderCommandName.equals(
								"/message_boards/edit_message") ||
							mvcRenderCommandName.equals(
								"/message_boards/view") ||
							mvcRenderCommandName.equals(
								"/message_boards/view_category") ||
							mvcRenderCommandName.equals(
								"/message_boards/view_message")) {

							active = true;
						}

						navigationItem.setActive(active);
						navigationItem.setHref(messageBoardsHomeURL);
						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "categories"));
					});

				PortletURL viewRecentPostsURL =
					_renderResponse.createRenderURL();

				viewRecentPostsURL.setParameter(
					"mvcRenderCommandName",
					"/message_boards/view_recent_posts");

				add(
					navigationItem -> {
						boolean active = false;

						if (mvcRenderCommandName.equals(
								"/message_boards/view_recent_posts")) {

							active = true;
						}

						navigationItem.setActive(active);
						navigationItem.setHref(viewRecentPostsURL);
						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "recent-posts"));
					});

				if (_themeDisplay.isSignedIn()) {
					PortletURL viewMyPostsURL =
						_renderResponse.createRenderURL();

					viewMyPostsURL.setParameter(
						"mvcRenderCommandName",
						"/message_boards/view_my_posts");

					add(
						navigationItem -> {
							boolean active = false;

							if (mvcRenderCommandName.equals(
									"/message_boards/view_my_posts")) {

								active = true;
							}

							navigationItem.setActive(active);
							navigationItem.setHref(viewMyPostsURL);
							navigationItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "my-posts"));
						});

					if (_mbGroupServiceSettings.isEmailMessageAddedEnabled() ||
						_mbGroupServiceSettings.
							isEmailMessageUpdatedEnabled()) {

						PortletURL viewMySubscriptionsURL =
							_renderResponse.createRenderURL();

						viewMySubscriptionsURL.setParameter(
							"mvcRenderCommandName",
							"/message_boards/view_my_subscriptions");

						add(
							navigationItem -> {
								boolean active = false;

								if (mvcRenderCommandName.equals(
										"/message_boards" +
											"/view_my_subscriptions")) {

									active = true;
								}

								navigationItem.setActive(active);
								navigationItem.setHref(viewMySubscriptionsURL);
								navigationItem.setLabel(
									LanguageUtil.get(
										_httpServletRequest,
										"my-subscriptions"));
							});
					}
				}

				PortletURL viewStatisticsURL =
					_renderResponse.createRenderURL();

				PortletDisplay portletDisplay =
					_themeDisplay.getPortletDisplay();

				if (Objects.equals(
						portletDisplay.getPortletName(),
						MBPortletKeys.MESSAGE_BOARDS)) {

					viewStatisticsURL.setParameter(
						"mvcRenderCommandName",
						"/message_boards/view_statistics");
				}
				else {
					viewStatisticsURL.setParameter(
						"mvcRenderCommandName",
						"/message_boards/view_statistics");
				}

				add(
					navigationItem -> {
						boolean active = false;

						if (mvcRenderCommandName.equals(
								"/message_boards/view_statistics") ||
							mvcRenderCommandName.equals(
								"/message_boards_admin/view_statistics")) {

							active = true;
						}

						navigationItem.setActive(active);
						navigationItem.setHref(viewStatisticsURL);
						navigationItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "statistics"));
					});
			}
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final MBGroupServiceSettings _mbGroupServiceSettings;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}