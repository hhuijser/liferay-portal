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

package com.liferay.source.formatter.checkstyle.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checkstyle.Checker;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.filters.SuppressionsLoader;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.xml.sax.InputSource;

/**
 * @author Hugo Huijser
 */
public class CheckstyleUtil {

	public static Configuration addAttribute(
		Configuration configuration, String key, String value,
		String... regexChecks) {

		if (!(configuration instanceof DefaultConfiguration)) {
			return configuration;
		}

		DefaultConfiguration defaultConfiguration =
			(DefaultConfiguration)configuration;

		DefaultConfiguration treeWalkerModule = null;

		for (Configuration childConfiguration :
				defaultConfiguration.getChildren()) {

			String name = childConfiguration.getName();

			if (name.equals("TreeWalker") &&
				(childConfiguration instanceof DefaultConfiguration)) {

				treeWalkerModule = (DefaultConfiguration)childConfiguration;

				break;
			}
		}

		if (treeWalkerModule == null) {
			return configuration;
		}

		for (Configuration childConfiguration :
				treeWalkerModule.getChildren()) {

			if (!(childConfiguration instanceof DefaultConfiguration)) {
				continue;
			}

			String name = childConfiguration.getName();

			for (String regexCheck : regexChecks) {
				if (name.matches(regexCheck)) {
					DefaultConfiguration defaultChildConfiguration =
						(DefaultConfiguration)childConfiguration;

					defaultChildConfiguration.addAttribute(key, value);
				}
			}
		}

		return defaultConfiguration;
	}

	public static List<String> getCheckNames(Configuration configuration) {
		List<String> checkNames = new ArrayList<>();

		String name = configuration.getName();

		if (name.startsWith("com.liferay.")) {
			int pos = name.lastIndexOf(CharPool.PERIOD);

			if (!name.endsWith("Check")) {
				name = name.concat("Check");
			}

			checkNames.add(name.substring(pos + 1));
		}

		for (Configuration childConfiguration : configuration.getChildren()) {
			checkNames.addAll(getCheckNames(childConfiguration));
		}

		return checkNames;
	}

	public static Configuration getConfiguration(String configurationFileName)
		throws Exception {

		ClassLoader classLoader = CheckstyleUtil.class.getClassLoader();

		return ConfigurationLoader.loadConfiguration(
			new InputSource(
				classLoader.getResourceAsStream(configurationFileName)),
			new PropertiesExpander(System.getProperties()), false);
	}

	public static synchronized Set<SourceFormatterMessage>
			getSourceFormatterMessages(
				Configuration configuration, List<File> suppressionsFiles,
				List<File> checkFiles, SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		return getSourceFormatterMessages(
			configuration, null, suppressionsFiles, checkFiles,
			sourceFormatterArgs);
	}

	public static synchronized Set<SourceFormatterMessage>
			getSourceFormatterMessages(
				Configuration configuration, List<String> enabledCheckstyles,
				List<File> suppressionsFiles, List<File> checkFiles,
				SourceFormatterArgs sourceFormatterArgs)
		throws Exception {

		Checker checker = new Checker();

		ClassLoader classLoader = CheckstyleUtil.class.getClassLoader();

		checker.setModuleClassLoader(classLoader);

		for (File suppressionsFile : suppressionsFiles) {
			checker.addFilter(
				SuppressionsLoader.loadSuppressions(
					suppressionsFile.getAbsolutePath()));
		}

		checker.configure(configuration);

		CheckstyleLogger checkstyleLogger = new CheckstyleLogger(
			new UnsyncByteArrayOutputStream(), true,
			sourceFormatterArgs.getBaseDirName());

		checker.addListener(checkstyleLogger);
		checker.setCheckstyleLogger(checkstyleLogger);

		if (enabledCheckstyles != null) {
			checker.addFilter(new CheckstyleFilter(enabledCheckstyles));
		}

		checker.process(checkFiles);

		return checker.getSourceFormatterMessages();
	}

	private static class CheckstyleFilter
		extends AutomaticBean implements Filter {

		public CheckstyleFilter(List<String> enabledCheckstyles) {
			_enabledCheckstyles = enabledCheckstyles;
		}

		public boolean accept(AuditEvent auditEvent) {
			String sourceName = auditEvent.getSourceName();

			for (String checkstyle : _enabledCheckstyles) {
				if (sourceName.equals(checkstyle)) {
					return true;
				}
			}

			return false;
		}

		private final List<String> _enabledCheckstyles;

	}

}