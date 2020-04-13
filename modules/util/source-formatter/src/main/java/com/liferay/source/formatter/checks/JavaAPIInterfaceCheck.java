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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.BNDSettings;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class JavaAPIInterfaceCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		JavaClass javaClass = (JavaClass)javaTerm;

		if ((javaClass.getParentJavaClass() == null) &&
			javaClass.isInterface()) {

			_checkInterface(fileName, absolutePath, javaClass);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	protected boolean hasGeneratedTag(String content) {
		if (SourceUtil.containsUnquoted(content, "@generated") ||
			SourceUtil.containsUnquoted(content, "$ANTLR")) {

			return true;
		}

		return false;
	}

	private void _checkInterface(
			String fileName, String absolutePath, JavaClass javaClass)
		throws Exception {

		BNDSettings bndSettings = getBNDSettings(fileName);

		if (bndSettings == null) {
			return;
		}

		List<String> exportPackageNames = bndSettings.getExportPackageNames();

		if (!exportPackageNames.contains(javaClass.getPackageName())) {
			return;
		}

		InterfaceType interfaceType = _getInterfaceType(
			absolutePath, javaClass, bndSettings);

		if (interfaceType == null) {
			return;
		}

		if (javaClass.hasAnnotation("ProviderType")) {
			if (interfaceType.equals(InterfaceType.CALLBACK) ||
				interfaceType.equals(InterfaceType.EXTENSION_POINT)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Interface of type '", interfaceType.getValue(),
						"' should not have annotation 'ProviderType'"));
			}
		}
		else if (interfaceType.equals(InterfaceType.ENTRY_POINT) ||
				 interfaceType.equals(InterfaceType.PRIVATE_INTERFACE)) {

			addMessage(
				fileName,
				StringBundler.concat(
					"Interface of type '", interfaceType.getValue(),
					"' should have annotation 'ProviderType'"));
		}

		if (!absolutePath.contains("-api/") &&
			interfaceType.equals(InterfaceType.ENTRY_POINT)) {

			addMessage(
				fileName,
				StringBundler.concat(
					"Interface of type '", interfaceType.getValue(),
					"' should be in 'api' module"));
		}
		else if (!absolutePath.contains("-spi/") &&
				 (interfaceType.equals(InterfaceType.EXTENSION_POINT) ||
				  interfaceType.equals(InterfaceType.PRIVATE_INTERFACE))) {

			addMessage(
				fileName,
				StringBundler.concat(
					"Interface of type '", interfaceType.getValue(),
					"' should be in 'spi' module"));
		}
	}

	private synchronized Map<String, String> _getBuildGradleContentsMap()
		throws IOException {

		if (_buildGradleContentsMap != null) {
			return _buildGradleContentsMap;
		}

		_buildGradleContentsMap = new HashMap<>();

		String moduleAppsDirLocation = "modules/apps/";

		for (int i = 0; i < (ToolsUtil.PORTAL_MAX_DIR_LEVEL - 1); i++) {
			File file = new File(getBaseDirName() + moduleAppsDirLocation);

			if (!file.exists()) {
				moduleAppsDirLocation = "../" + moduleAppsDirLocation;

				continue;
			}

			List<String> buildGradleFileNames =
				SourceFormatterUtil.scanForFiles(
					getBaseDirName() + moduleAppsDirLocation, new String[0],
					new String[] {"**/build.gradle"},
					getSourceFormatterExcludes(), false);

			for (String buildGradleFileName : buildGradleFileNames) {
				buildGradleFileName = StringUtil.replace(
					buildGradleFileName, CharPool.BACK_SLASH, CharPool.SLASH);

				int x = buildGradleFileName.lastIndexOf(CharPool.SLASH);

				_buildGradleContentsMap.put(
					buildGradleFileName.substring(0, x + 1),
					FileUtil.read(new File(buildGradleFileName)));
			}

			break;
		}

		return _buildGradleContentsMap;
	}

	private InterfaceType _getInterfaceType(
			String absolutePath, JavaClass javaClass, BNDSettings bndSettings)
		throws Exception {

		int consumedCount = 0;
		int consumedInsideFrameworkCount = 0;
		int implementedCount = 0;
		int implementedAsComponentCount = 0;
		int implementedInsideFrameworkCount = 0;

		String bndSettingsFileName = bndSettings.getFileName();

		int pos1 = bndSettingsFileName.lastIndexOf("/modules/");

		if (pos1 == -1) {
			return null;
		}

		int pos2 = bndSettingsFileName.lastIndexOf(CharPool.SLASH);

		String projectName = StringUtil.replace(
			bndSettingsFileName.substring(pos1 + 8, pos2), CharPool.SLASH,
			CharPool.COLON);

		int pos3 = bndSettingsFileName.lastIndexOf(CharPool.SLASH, pos2 - 1);

		String moduleName = bndSettingsFileName.substring(pos3 + 1, pos2);

		int pos4 = moduleName.lastIndexOf("-");

		if (pos4 == -1) {
			return null;
		}

		String moduleFrameworkName = moduleName.substring(0, pos4);

		Map<String, String> buildGradleContentsMap =
			_getBuildGradleContentsMap();

		for (Map.Entry<String, String> entry :
				buildGradleContentsMap.entrySet()) {

			String buildGradleContent = entry.getValue();

			if (!buildGradleContent.contains(projectName)) {
				continue;
			}

			String moduleDirectoryLocation = entry.getKey();

			boolean insideModuleFramework = _isInsideModuleFramework(
				moduleDirectoryLocation, moduleFrameworkName);

			List<JavaClass> moduleJavaClasses = _getModuleJavaClasses(
				moduleDirectoryLocation);

			for (JavaClass moduleJavaClass : moduleJavaClasses) {
				List<String> implementedClassNames =
					moduleJavaClass.getImplementedClassNames(true);

				if (implementedClassNames.contains(
						javaClass.getPackageName() + "." +
							javaClass.getName())) {

					if (insideModuleFramework) {
						implementedInsideFrameworkCount++;
					}
					else {
						implementedCount++;

						if (moduleJavaClass.hasAnnotation("Component")) {
							implementedAsComponentCount++;
						}
					}

					continue;
				}

				List<String> importNames = moduleJavaClass.getImports();

				if (importNames.contains(
						javaClass.getPackageName() + "." +
							javaClass.getName())) {

					if (insideModuleFramework) {
						consumedInsideFrameworkCount++;
					}
					else {
						consumedCount++;
					}
				}
			}
		}

		if ((implementedInsideFrameworkCount > 0) &&
			(implementedAsComponentCount > 0)) {

			if (implementedCount == 0) {
				//This one should be annotated with ProviderType

				return InterfaceType.ENTRY_POINT;
			}

			/*If it is also implemented both by the framework and outside we
			can't tell if it is a private interface or an extension point
			because the difference is in the developers that make a
			conscious decision about making an interface an extension.*/
			if (javaClass.hasAnnotation("ProviderType")) {
				return InterfaceType.PRIVATE_INTERFACE;
			}

			return InterfaceType.EXTENSION_POINT;
		}
		else if ((implementedCount > 0) && (implementedAsComponentCount > 0)) {
			//Same thing here

			if (javaClass.hasAnnotation("ProviderType")) {
				return InterfaceType.PRIVATE_INTERFACE;
			}

			return InterfaceType.EXTENSION_POINT;
		}
		else if ((consumedInsideFrameworkCount > 0) &&
				 (implementedAsComponentCount == 0) && (implementedCount > 0)) {

			return InterfaceType.CALLBACK;
		}
		else {
			return null; //¯\_(ツ)_/¯
		}
	}

	private synchronized List<JavaClass> _getModuleJavaClasses(
			String moduleDirectoryLocation)
		throws Exception {

		List<JavaClass> moduleJavaClasses = _moduleJavaClassesMap.get(
			moduleDirectoryLocation);

		if (moduleJavaClasses != null) {
			return moduleJavaClasses;
		}

		moduleJavaClasses = new ArrayList<>();

		List<String> javaFileNames = SourceFormatterUtil.scanForFiles(
			moduleDirectoryLocation, new String[0], new String[] {"**/*.java"},
			getSourceFormatterExcludes(), false);

		for (String javaFileName : javaFileNames) {
			javaFileName = StringUtil.replace(
				javaFileName, CharPool.BACK_SLASH, CharPool.SLASH);

			String content = FileUtil.read(new File(javaFileName));

			if (!hasGeneratedTag(content)) {
				JavaClass javaClass = JavaClassParser.parseJavaClass(
					javaFileName, content);

				moduleJavaClasses.add(javaClass);
			}
		}

		_moduleJavaClassesMap.put(moduleDirectoryLocation, moduleJavaClasses);

		return moduleJavaClasses;
	}

	private boolean _isInsideModuleFramework(
		String moduleDirectoryLocation, String frameworkName) {

		int x = moduleDirectoryLocation.lastIndexOf(
			CharPool.SLASH, moduleDirectoryLocation.length() - 2);

		if (StringUtil.startsWith(
				moduleDirectoryLocation.substring(x + 1),
				frameworkName + "-")) {

			return true;
		}

		return false;
	}

	private Map<String, String> _buildGradleContentsMap;
	private final Map<String, List<JavaClass>> _moduleJavaClassesMap =
		new HashMap<>();

	private enum InterfaceType {

		CALLBACK("Callback"), ENTRY_POINT("Entry Point"),
		EXTENSION_POINT("Extention Point"),
		PRIVATE_INTERFACE("Private Interface");

		public String getValue() {
			return _value;
		}

		private InterfaceType(String value) {
			_value = value;
		}

		private final String _value;

	}

}