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

package com.liferay.portal.template.soy.internal.data;

import com.google.template.soy.data.SanitizedContent;
import com.google.template.soy.data.UnsafeSanitizedContentOrdainer;

import com.liferay.portal.template.soy.data.SoyHTMLData;

/**
 * @author     Iván Zaera Avellón
 * @deprecated As of Mueller (7.2.x), replaced by {@link SoyRawDataImpl}
 */
@Deprecated
public class SoyHTMLDataImpl implements SoyHTMLData {

	public SoyHTMLDataImpl(String html) {
		_sanitizedContent = UnsafeSanitizedContentOrdainer.ordainAsSafe(
			html, SanitizedContent.ContentKind.HTML);
	}

	@Override
	public Object getValue() {
		return _sanitizedContent;
	}

	private final SanitizedContent _sanitizedContent;

}