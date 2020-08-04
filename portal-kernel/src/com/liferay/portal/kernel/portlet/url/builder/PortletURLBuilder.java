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

package com.liferay.portal.kernel.portlet.url.builder;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;

import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Hugo Huijser
 */
public class PortletURLBuilder {

	public static PortletURLWrapper create(PortletURL portletURL) {
		return new PortletURLWrapper(portletURL);
	}

	public static PortletURLWrapper createRenderURL(
		RenderResponse renderResponse) {

		return new PortletURLWrapper(renderResponse.createRenderURL());
	}

	public static final class PortletURLWrapper {

		public PortletURLWrapper(PortletURL portletURL) {
			_portletURL = portletURL;
		}

		public PortletURLWrapper addProperty(String key, String value) {
			_portletURL.addProperty(key, value);

			return this;
		}

		public PortletURL build() {
			return _portletURL;
		}

		public PortletURLWrapper setParameter(String name, String value) {
			_portletURL.setParameter(name, value);

			return this;
		}

		public PortletURLWrapper setParameter(String name, String... values) {
			_portletURL.setParameter(name, values);

			return this;
		}

		public PortletURLWrapper setParameter(
			String key, UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

			try {
				String value = valueUnsafeSupplier.get();

				if (value != null) {
					_portletURL.setParameter(key, value);
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public PortletURLWrapper setParameters(
			Map<String, String[]> parameters) {

			_portletURL.setParameters(parameters);

			return this;
		}

		public PortletURLWrapper setProperty(String key, String value) {
			_portletURL.setProperty(key, value);

			return this;
		}

		public PortletURLWrapper setSecure(boolean secure)
			throws PortletSecurityException {

			_portletURL.setSecure(secure);

			return this;
		}

		public PortletURLWrapper write(Writer writer) throws IOException {
			_portletURL.write(writer);

			return this;
		}

		public PortletURLWrapper write(Writer writer, boolean escapeXML)
			throws IOException {

			_portletURL.write(writer, escapeXML);

			return this;
		}

		private final PortletURL _portletURL;

	}

	@FunctionalInterface
	public interface UnsafeSupplier<T, E extends Throwable> {

		public T get() throws E;

	}

}