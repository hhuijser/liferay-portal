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

package com.liferay.portal.kernel.bean;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletBeanLocatorUtil {

	public static BeanLocator getBeanLocator(String servletContextName) {
		return _beanLocators.get(servletContextName);
	}

	public static Object locate(String servletContextName, String name)
		throws BeanLocatorException {

		BeanLocator beanLocator = getBeanLocator(servletContextName);

		if (beanLocator == null) {
			_log.error(
				"BeanLocator is null for servlet context " +
					servletContextName);

			throw new BeanLocatorException(
				"BeanLocator is not set for servlet context " +
					servletContextName);
		}

		return beanLocator.locate(name);
	}

	public static void setBeanLocator(
		String servletContextName, BeanLocator beanLocator) {

		if (_log.isDebugEnabled()) {
			if (beanLocator != null) {
				_log.debug(
					StringBundler.concat(
						"Setting BeanLocator ",
						String.valueOf(beanLocator.hashCode()),
						" for servlet context ", servletContextName));
			}
			else {
				_log.debug(
					"Removing BeanLocator for servlet context " +
						servletContextName);
			}
		}

		_beanLocators.put(servletContextName, beanLocator);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletBeanLocatorUtil.class);

	private static final Map<String, BeanLocator> _beanLocators =
		new HashMap<>();

}