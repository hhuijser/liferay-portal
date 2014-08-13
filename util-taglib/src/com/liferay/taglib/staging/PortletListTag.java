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

package com.liferay.taglib.staging;

import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.model.Portlet;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Levente Hudák
 */
public class PortletListTag extends IncludeTag {

	public void setDateRange(DateRange dateRange) {
		_dateRange = dateRange;
	}

	public void setDisableInputs(boolean disableInputs) {
		_disableInputs = disableInputs;
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	public void setPortlets(List<Portlet> portlets) {
		_portlets = portlets;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		_dateRange = null;
		_disableInputs = false;
		_parameterMap = null;
		_portlets = null;
		_type = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-staging:portlet-list:dateRange", _dateRange);
		request.setAttribute(
			"liferay-staging:portlet-list:disableInputs", _disableInputs);
		request.setAttribute(
			"liferay-staging:portlet-list:parameterMap", _parameterMap);
		request.setAttribute(
			"liferay-staging:portlet-list:portlets", _portlets);
		request.setAttribute("liferay-staging:portlet-list:type", _type);
	}

	private static final String _PAGE =
		"/html/taglib/staging/portlet_list/page.jsp";

	private DateRange _dateRange;
	private boolean _disableInputs;
	private Map<String, String[]> _parameterMap;
	private List<Portlet> _portlets;
	private String _type;

}