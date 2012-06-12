/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public interface FinderCache {

	public void clearCache();

	public void clearCache(String className);

	public void clearLocalCache();

	public Object getResult(
		FinderPath finderPath, Object[] args, SessionFactory sessionFactory);

	public void invalidate();

	public void putResult(FinderPath finderPath, Object[] args, Object result);

	public void removeCache(String className);

	public void removeResult(FinderPath finderPath, Object[] args);

	public void removeResults(FinderPath finderPath);

}