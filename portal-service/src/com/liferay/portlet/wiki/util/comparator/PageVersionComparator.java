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

package com.liferay.portlet.wiki.util.comparator;

import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.wiki.model.WikiPage;

/**
 * @author Bruno Farache
 */
public class PageVersionComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC = "WikiPage.version ASC";

	public static final String ORDER_BY_DESC = "WikiPage.version DESC";

	public static final String[] ORDER_BY_FIELDS = {"version"};

	public PageVersionComparator() {
		this(false);
	}

	public PageVersionComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		WikiPage page1 = (WikiPage)obj1;
		WikiPage page2 = (WikiPage)obj2;

		Version version1 = page1.getVersion();
		Version version2 = page2.getVersion();

		if (_ascending) {
			return version1.compareTo(version2);
		}
		else {
			return -version1.compareTo(version2);
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}