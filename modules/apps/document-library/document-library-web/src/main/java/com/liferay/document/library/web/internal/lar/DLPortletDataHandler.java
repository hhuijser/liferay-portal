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

package com.liferay.document.library.web.internal.lar;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author arthurchan35
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
	service = PortletDataHandler.class
)
public class DLPortletDataHandler extends DLAdminPortletDataHandler {

	@Override
	public boolean isDisplayPortlet() {
		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		super.activate(bundleContext);
	}

}