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

package com.liferay.portal.upload;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Brian Myunghun Kim
 * @author Brian Wing Shun Chan
 */
public class LiferayServletRequest extends HttpServletRequestWrapper {

	public LiferayServletRequest(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);

		_httpServletRequest = httpServletRequest;
	}

	public void cleanUp() {
		if (_liferayInputStream != null) {
			_liferayInputStream.cleanUp();
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (_liferayInputStream == null) {
			_liferayInputStream = new LiferayInputStream(_httpServletRequest);
		}

		if (_finishedReadingOriginalStream) {

			// Return the cached input stream the second time the user requests
			// the input stream, otherwise, it will return an empty input stream
			// because it has already been parsed

			if (_cachedServletInputStream == null) {
				_cachedServletInputStream =
					_liferayInputStream.getCachedInputStream();
			}

			return _cachedServletInputStream;
		}

		return _liferayInputStream;
	}

	public void setFinishedReadingOriginalStream(
		boolean finishedReadingOriginalStream) {

		_finishedReadingOriginalStream = finishedReadingOriginalStream;
	}

	private ServletInputStream _cachedServletInputStream;
	private boolean _finishedReadingOriginalStream;
	private final HttpServletRequest _httpServletRequest;
	private LiferayInputStream _liferayInputStream;

}