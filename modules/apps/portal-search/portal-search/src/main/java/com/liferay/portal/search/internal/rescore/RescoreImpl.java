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

package com.liferay.portal.search.internal.rescore;

import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.Rescore;

/**
 * @author Bryan Engler
 */
public class RescoreImpl implements Rescore {

	public RescoreImpl(Query query, Integer windowSize) {
		_query = query;
		_windowSize = windowSize;
	}

	@Override
	public Query getQuery() {
		return _query;
	}

	@Override
	public Integer getWindowSize() {
		return _windowSize;
	}

	private final Query _query;
	private final Integer _windowSize;

}