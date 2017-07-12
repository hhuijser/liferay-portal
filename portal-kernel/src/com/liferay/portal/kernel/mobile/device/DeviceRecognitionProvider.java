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

package com.liferay.portal.kernel.mobile.device;

import aQute.bnd.annotation.ProviderType;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 */
@ProviderType
public interface DeviceRecognitionProvider {

	public Device detectDevice(HttpServletRequest request);

	public KnownDevices getKnownDevices();

	public void reload() throws Exception;

	/**
	 * @deprecated As of com.liferay.portal.kernel#NEXT-VERSION, with no direct replacement
	 */
	@Deprecated
	public void setDeviceCapabilityFilter(
		DeviceCapabilityFilter deviceCapabilityFilter);

}