/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.BaseAutoDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 */
public class HookAutoDeployListener extends BaseAutoDeployListener {

	public HookAutoDeployListener() {
		_autoDeployer = new HookAutoDeployer();
	}

	public void deploy(File file, String context) throws AutoDeployException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + file.getPath());
		}

		if (!isHookPlugin(file)) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Copying hook plugin for " + file.getPath());
		}

		_autoDeployer.autoDeploy(file, context);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Hook for " + file.getPath() + " copied successfully. " +
					"Deployment will start in a few seconds.");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		HookAutoDeployListener.class);

	private AutoDeployer _autoDeployer;

}