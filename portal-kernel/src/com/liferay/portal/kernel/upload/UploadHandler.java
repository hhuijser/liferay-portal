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

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.exception.PortalException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author     Adolfo Pérez
 * @deprecated As of com.liferay.portal.kernel#NEXT-VERSION, replaced by {@link
 *             com.liferay.upload.UploadHandler}
 */
@Deprecated
public interface UploadHandler {

	public void upload(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException;

}