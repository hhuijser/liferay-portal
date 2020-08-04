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

import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Map;

import javax.portlet.ActionURL;
import javax.portlet.MimeResponse;
import javax.portlet.MutableActionParameters;
import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Hugo Huijser
 */
public class PortletURLBuilder {

	public static PortletURLWrapper create(PortletURL portletURL) {
		return new PortletURLWrapper(portletURL);
	}

	public static PortletURLWrapper createActionURL(
		LiferayPortletResponse liferayPortletResponse) {

		return new PortletURLWrapper(liferayPortletResponse.createActionURL());
	}

	public static PortletURLWrapper createActionURL(
		LiferayPortletResponse liferayPortletResponse, MimeResponse.Copy copy) {

		return new PortletURLWrapper(
			liferayPortletResponse.createActionURL(copy));
	}

	public static PortletURLWrapper createActionURL(
		LiferayPortletResponse liferayPortletResponse, String portletName) {

		return new PortletURLWrapper(
			liferayPortletResponse.createActionURL(portletName));
	}

	public static PortletURLWrapper createActionURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		MimeResponse.Copy copy) {

		return new PortletURLWrapper(
			liferayPortletResponse.createActionURL(portletName, copy));
	}

	public static PortletURLWrapper createActionURL(MimeResponse mimeResponse) {
		return new PortletURLWrapper(mimeResponse.createActionURL());
	}

	public static PortletURLWrapper createActionURL(
		MimeResponse mimeResponse, MimeResponse.Copy copy) {

		return new PortletURLWrapper(mimeResponse.createActionURL(copy));
	}

	public static PortletURLWrapper createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, long plid,
		String portletName, String lifecycle) {

		return new PortletURLWrapper(
			liferayPortletResponse.createLiferayPortletURL(
				plid, portletName, lifecycle));
	}

	public static PortletURLWrapper createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, long plid,
		String portletName, String lifecycle, boolean includeLinkToLayoutUuid) {

		return new PortletURLWrapper(
			liferayPortletResponse.createLiferayPortletURL(
				plid, portletName, lifecycle, includeLinkToLayoutUuid));
	}

	public static PortletURLWrapper createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, long plid,
		String portletName, String lifecycle, MimeResponse.Copy copy) {

		return new PortletURLWrapper(
			liferayPortletResponse.createLiferayPortletURL(
				plid, portletName, lifecycle, copy));
	}

	public static PortletURLWrapper createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, long plid,
		String portletName, String lifecycle, MimeResponse.Copy copy,
		boolean includeLinkToLayoutUuid) {

		return new PortletURLWrapper(
			liferayPortletResponse.createLiferayPortletURL(
				plid, portletName, lifecycle, copy, includeLinkToLayoutUuid));
	}

	public static PortletURLWrapper createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, String lifecycle) {

		return new PortletURLWrapper(
			liferayPortletResponse.createLiferayPortletURL(lifecycle));
	}

	public static PortletURLWrapper createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		String lifecycle) {

		return new PortletURLWrapper(
			liferayPortletResponse.createLiferayPortletURL(
				portletName, lifecycle));
	}

	public static PortletURLWrapper createLiferayPortletURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		String lifecycle, MimeResponse.Copy copy) {

		return new PortletURLWrapper(
			liferayPortletResponse.createLiferayPortletURL(
				portletName, lifecycle, copy));
	}

	public static PortletURLWrapper createRenderURL(
		LiferayPortletResponse liferayPortletResponse) {

		return new PortletURLWrapper(liferayPortletResponse.createRenderURL());
	}

	public static PortletURLWrapper createRenderURL(
		LiferayPortletResponse liferayPortletResponse, MimeResponse.Copy copy) {

		return new PortletURLWrapper(
			liferayPortletResponse.createRenderURL(copy));
	}

	public static PortletURLWrapper createRenderURL(
		LiferayPortletResponse liferayPortletResponse, String portletName) {

		return new PortletURLWrapper(
			liferayPortletResponse.createRenderURL(portletName));
	}

	public static PortletURLWrapper createRenderURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		MimeResponse.Copy copy) {

		return new PortletURLWrapper(
			liferayPortletResponse.createRenderURL(portletName, copy));
	}

	public static PortletURLWrapper createRenderURL(MimeResponse mimeResponse) {
		return new PortletURLWrapper(mimeResponse.createRenderURL());
	}

	public static PortletURLWrapper createRenderURL(
		MimeResponse mimeResponse, MimeResponse.Copy copy) {

		return new PortletURLWrapper(mimeResponse.createRenderURL(copy));
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

		public PortletURLWrapper setParameter(String name, Object value) {
			setParameter(String.valueOf(value));

			return this;
		}

		public PortletURLWrapper setParameter(String name, String value) {
			if (_portletURL instanceof ActionURL) {
				ActionURL actionURL = (ActionURL)_portletURL;

				MutableActionParameters mutableActionParameters =
					actionURL.getActionParameters();

				mutableActionParameters.setValue(name, value);
			}
			else {
				MutableRenderParameters mutableRenderParameters =
					_portletURL.getRenderParameters();

				mutableRenderParameters.setValue(name, value);
			}

			return this;
		}

		public PortletURLWrapper setParameter(String name, String... values) {
			if (_portletURL instanceof ActionURL) {
				ActionURL actionURL = (ActionURL)_portletURL;

				MutableActionParameters mutableActionParameters =
					actionURL.getActionParameters();

				mutableActionParameters.setValues(name, values);
			}
			else {
				MutableRenderParameters mutableRenderParameters =
					_portletURL.getRenderParameters();

				mutableRenderParameters.setValues(name, values);
			}

			return this;
		}

		public PortletURLWrapper setParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			try {
				Object value = valueUnsafeSupplier.get();

				if (value == null) {
					return this;
				}

				if (value instanceof String[]) {
					setParameter(key, (String[])value);
				}
				else {
					setParameter(key, String.valueOf(value));
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}

			return this;
		}

		public PortletURLWrapper setParameters(
			Map<String, String[]> parameters) {

			if (_portletURL instanceof ActionURL) {
				ActionURL actionURL = (ActionURL)_portletURL;

				MutableActionParameters mutableActionParameters =
					actionURL.getActionParameters();

				mutableActionParameters.clear();

				for (Map.Entry<String, String[]> entry :
						parameters.entrySet()) {

					mutableActionParameters.setValues(
						entry.getKey(), entry.getValue());
				}
			}
			else {
				MutableRenderParameters mutableRenderParameters =
					_portletURL.getRenderParameters();

				mutableRenderParameters.clear();

				for (Map.Entry<String, String[]> entry :
						parameters.entrySet()) {

					mutableRenderParameters.setValues(
						entry.getKey(), entry.getValue());
				}
			}

			return this;
		}

		public PortletURLWrapper setPortletMode(PortletMode portletMode)
			throws PortletModeException {

			_portletURL.setPortletMode(portletMode);

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

		public PortletURLWrapper setWindowState(WindowState windowState)
			throws WindowStateException {

			_portletURL.setWindowState(windowState);

			return this;
		}

		private final PortletURL _portletURL;

	}

	@FunctionalInterface
	public interface UnsafeSupplier<T, E extends Throwable> {

		public T get() throws E;

	}

}