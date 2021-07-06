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

package com.liferay.portal.search.searcher;

import java.util.concurrent.TimeUnit;

/**
 * @author Wade Cao
 */
public final class SearchTimeValue {

	public long getDuration() {
		return _duration;
	}

	public TimeUnit getTimeUnit() {
		return _timeUnit;
	}

	public static class Builder {

		public static Builder newBuilder() {
			return new Builder();
		}

		public SearchTimeValue build() {
			return _searchTimeValue;
		}

		public Builder duration(long duration) {
			_searchTimeValue._duration = duration;

			return this;
		}

		public Builder timeUnit(TimeUnit timeUnit) {
			_searchTimeValue._timeUnit = timeUnit;

			return this;
		}

		private final SearchTimeValue _searchTimeValue = new SearchTimeValue();

	}

	private long _duration;
	private TimeUnit _timeUnit;

}