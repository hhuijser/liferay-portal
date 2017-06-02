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

package com.liferay.petra.io.internal.loader;

import com.liferay.petra.io.spi.loader.ClassLoaderPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class DefaultClassLoaderPool implements ClassLoaderPool {

	/**
	 * Returns the class loader associated with the context name.
	 *
	 * <p>
	 * If no class loader is found for the context name, the thread's context
	 * class loader is returned as a fallback.
	 * </p>
	 *
	 * @param  contextName the servlet context's name
	 * @return the class loader associated with the context name
	 */
	public ClassLoader getClassLoader(String contextName) {
		ClassLoader classLoader = null;

		if ((contextName != null) && !contextName.equals(NULL)) {
			classLoader = _classLoaders.get(contextName);
		}

		if (classLoader == null) {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();
		}

		return classLoader;
	}

	/**
	 * Returns the context name associated with the class loader.
	 *
	 * <p>
	 * If the class loader is <code>null</code> or if no context name is
	 * associated with the class loader, {@link <code>NULL</code>} is
	 * returned.
	 * </p>
	 *
	 * @param  classLoader the class loader
	 * @return the context name associated with the class loader
	 */
	public String getContextName(ClassLoader classLoader) {
		if (classLoader == null) {
			return NULL;
		}

		String contextName = _contextNames.get(classLoader);

		if (contextName == null) {
			contextName = NULL;
		}

		return contextName;
	}

	public void register(String contextName, ClassLoader classLoader) {
		_classLoaders.put(contextName, classLoader);
		_contextNames.put(classLoader, contextName);
	}

	public void unregister(ClassLoader classLoader) {
		String contextName = _contextNames.remove(classLoader);

		if (contextName != null) {
			_classLoaders.remove(contextName);
		}
	}

	public void unregister(String contextName) {
		ClassLoader classLoader = _classLoaders.remove(contextName);

		if (classLoader != null) {
			_contextNames.remove(classLoader);
		}
	}

	private static final String NULL = "null";

	private final Map<String, ClassLoader> _classLoaders =
		new ConcurrentHashMap<>();
	private final Map<ClassLoader, String> _contextNames =
		new ConcurrentHashMap<>();

}