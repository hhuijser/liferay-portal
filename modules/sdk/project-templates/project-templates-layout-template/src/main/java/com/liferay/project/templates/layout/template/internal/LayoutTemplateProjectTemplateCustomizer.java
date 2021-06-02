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

package com.liferay.project.templates.layout.template.internal;

import java.io.File;
import java.nio.file.Path;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;
import com.liferay.project.templates.extensions.util.FileUtil;

import aQute.bnd.version.Version;

/**
 * @author Lawrence Lee
 */
public class LayoutTemplateProjectTemplateCustomizer implements ProjectTemplateCustomizer{

	@Override
	public void onAfterGenerateProject(ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult) throws Exception {
		
		Path destinationDirPath = destinationDir.toPath();

		Path projectPath = destinationDirPath.resolve(
			projectTemplatesArgs.getName());

		File buildDir = projectPath.toFile();

		File webINFDir = new File(buildDir, "src/main/webapp/WEB-INF");
		
		Version version = Version.parseVersion(
				projectTemplatesArgs.getLiferayVersion());

			int minorVersion = version.getMinor();

			String minorVersionString = String.valueOf(minorVersion);

			File liferayLayoutTemplatesXML = new File(
					webINFDir, "liferay-layout-templates.xml");

			FileUtil.replaceString(
					liferayLayoutTemplatesXML, "7.0", "7." + minorVersionString);
			FileUtil.replaceString(
					liferayLayoutTemplatesXML, "7_0", "7_" + minorVersionString);
	}

	@Override
	public void onBeforeGenerateProject(ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest) throws Exception {
		
	}

}
