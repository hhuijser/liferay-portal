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

package com.liferay.commerce.order.rule.internal.search;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry",
	service = BaseSearcher.class
)
public class CommerceOrderRuleEntrySearcher extends BaseSearcher {

	public CommerceOrderRuleEntrySearcher() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.NAME);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return _CLASS_NAME;
	}

	private static final String _CLASS_NAME =
		CommerceOrderRuleEntry.class.getName();

}