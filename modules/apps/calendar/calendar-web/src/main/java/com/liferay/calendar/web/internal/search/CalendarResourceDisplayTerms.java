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

package com.liferay.calendar.web.internal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 */
public class CalendarResourceDisplayTerms extends DisplayTerms {

	public static final String ACTIVE = "active";

	public static final String CODE = "code";

	public static final String DESCRIPTION = "description";

	public static final String NAME = "name";

	public static final String SCOPE = "scope";

	public CalendarResourceDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		_active = ParamUtil.getBoolean(portletRequest, ACTIVE, true);
		_code = ParamUtil.getString(portletRequest, CODE);
		_description = ParamUtil.getString(portletRequest, DESCRIPTION);
		_name = ParamUtil.getString(portletRequest, NAME);
		_scope = ParamUtil.getLong(portletRequest, SCOPE);
	}

	public String getCode() {
		return _code;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public long getScope() {
		return _scope;
	}

	public boolean isActive() {
		return _active;
	}

	private final boolean _active;
	private final String _code;
	private final String _description;
	private final String _name;
	private final long _scope;

}