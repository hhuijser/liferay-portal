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

package com.liferay.fragment.processor;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import javax.portlet.PortletMode;

/**
 * @author Pavel Savinov
 */
public interface FragmentEntryProcessor {

	public default JSONObject getDefaultEditableValuesJSONObject(String html) {
		return null;
	}

	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html)
		throws PortalException {

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, html, PortletMode.EDIT.toString());
	}

	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode)
		throws PortalException;

	public void validateFragmentEntryHTML(String html) throws PortalException;

}