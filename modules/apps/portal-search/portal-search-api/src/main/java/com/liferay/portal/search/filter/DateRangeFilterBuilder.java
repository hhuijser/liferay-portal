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

package com.liferay.portal.search.filter;

import aQute.bnd.annotation.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface DateRangeFilterBuilder {

	public DateRangeFilter build();

	public void setFieldName(String fieldName);

	public void setFormat(String format);

	public void setFrom(String from);

	public void setIncludeLower(boolean includeLower);

	public void setIncludeUpper(boolean includeUpper);

	public void setTimeZoneId(String timeZoneId);

	public void setTo(String to);

}