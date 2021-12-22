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

package com.liferay.source.formatter.checks;

import java.io.StringReader;

import java.util.Properties;

/**
 * @author Hugo Huijser
 */
public class UpgradeMissingWorkspaceCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		String workspacePath = (String)properties.get("workspace.path");

		System.out.println("FILENAME: " + fileName);
		System.out.println("WORKSPACEPATH: " + workspacePath);

		// All the logic from CreateWorkspaceProvider, including running blade
		// commands goes in this class

		return content;
	}

}