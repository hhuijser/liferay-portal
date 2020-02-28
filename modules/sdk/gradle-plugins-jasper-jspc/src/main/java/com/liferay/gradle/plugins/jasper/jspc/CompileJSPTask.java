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

package com.liferay.gradle.plugins.jasper.jspc;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;
import java.io.OutputStream;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SkipWhenEmpty;

/**
 * @author Andrea Di Giorgi
 */
public class CompileJSPTask extends JavaExec {

	@Override
	public void exec() {
		FileCollection classpath = getClasspath();
		FileCollection jspCClasspath = getJspCClasspath();

		try {
			_runWithClassPath(
				classpath.getAsPath() + File.pathSeparator +
					jspCClasspath.getAsPath(),
				_getCompleteArgs());
		}
		catch (Exception exception) {
			throw new GradleException(exception.getMessage(), exception);
		}
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@InputFiles
	public FileCollection getJspCClasspath() {
		return _jspCClasspath;
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getJSPFiles() {
		Project project = getProject();

		Map<String, Object> args = new HashMap<>();

		args.put("dir", getWebAppDir());

		List<String> excludes = new ArrayList<>();

		excludes.add("**/custom_jsps/**/*");
		excludes.add("**/dependencies/**/*");

		args.put("excludes", excludes);

		args.put("include", "**/*.jsp");

		return project.fileTree(args);
	}

	public File getWebAppDir() {
		return GradleUtil.toFile(getProject(), _webAppDir);
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setJspCClasspath(FileCollection jspCClasspath) {
		_jspCClasspath = jspCClasspath;
	}

	@Override
	public JavaExec setStandardOutput(OutputStream outputStream) {
		throw new UnsupportedOperationException();
	}

	public void setWebAppDir(Object webAppDir) {
		_webAppDir = webAppDir;
	}

	private static void _runWithClassPath(String classpath, String[] args)
		throws Exception {

		classpath =
			System.getProperty("java.class.path") + File.pathSeparator +
				classpath;

		String[] files = classpath.split(File.pathSeparator);

		URL[] urls = new URL[files.length];

		for (int i = 0; i < files.length; i++) {
			File file = new File(files[i]);

			URI uri = file.toURI();

			urls[i] = uri.toURL();
		}

		ClassLoader classLoader = new URLClassLoader(urls, null);

		Class<?> jspCClass = classLoader.loadClass("org.apache.jasper.JspC");

		Object jspC = jspCClass.newInstance();

		Method setArgsMethod = jspCClass.getMethod("setArgs", String[].class);

		setArgsMethod.invoke(jspC, new Object[] {args});

		Method setClassPathMethod = jspCClass.getMethod(
			"setClassPath", String.class);

		setClassPathMethod.invoke(jspC, classpath);

		Method executeMethod = jspCClass.getMethod("execute");

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(classLoader);

		try {
			executeMethod.invoke(jspC);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private String[] _getCompleteArgs() {
		return new String[] {
			"-d", FileUtil.getAbsolutePath(getDestinationDir()), "-webapp",
			FileUtil.getAbsolutePath(getWebAppDir())
		};
	}

	private Object _destinationDir;
	private FileCollection _jspCClasspath;
	private Object _webAppDir;

}