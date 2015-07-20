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

package com.liferay.portal.workflow.kaleo.manager;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.workflow.kaleo.service.configuration.configurator.KaleoServiceConfigurator;

/**
 * @author Marcellus Tavares
 * @author Michael C. Han
 */
@Component(immediate = true)
public class KaleoEngineActivator {

	@Activate
	protected void activate() throws Exception {
		PortalKaleoManagerUtil.deployKaleoDefaults();
	}
	
	@Reference
	protected void setKaleoServiceConfigurator(
		KaleoServiceConfigurator kaleoServiceConfigurator) {
	}
	
}
