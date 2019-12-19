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

package com.liferay.journal.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleItemSelectorViewManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public JournalArticleItemSelectorViewManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			JournalArticleItemSelectorViewDisplayContext
				journalArticleItemSelectorViewDisplayContext)
		throws PortletException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			journalArticleItemSelectorViewDisplayContext.getSearchContainer());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #JournalArticleItemSelectorViewManagementToolbarDisplayContext(
	 *             HttpServletRequest, LiferayPortletRequest,
	 *             LiferayPortletResponse,
	 *             JournalArticleItemSelectorViewDisplayContext)}
	 */
	@Deprecated
	public JournalArticleItemSelectorViewManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		JournalArticleItemSelectorViewDisplayContext
			journalArticleItemSelectorViewDisplayContext) {

		this(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			journalArticleItemSelectorViewDisplayContext);
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "descriptive";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive", "icon"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"modified-date", "title"};
	}

}