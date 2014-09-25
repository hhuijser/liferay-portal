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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Role;

/**
 * @author Brian Wing Shun Chan
 */
public class RoleTypeComparator extends OrderByComparator<Role> {

	public static final String ORDER_BY_ASC = "Role_.type_ ASC, Role_.name ASC";

	public static final String ORDER_BY_DESC =
		"Role_.type_ DESC, Role_.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"type", "name"};

	public RoleTypeComparator() {
		this(false);
	}

	public RoleTypeComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Role role1, Role role2) {
		int value = 0;

		if (role1.getType() > role2.getType()) {
			value = 1;
		}
		else if (role1.getType() < role2.getType()) {
			value = -1;
		}

		if (value == 0) {
			String name1 = role1.getName();
			String name2 = role2.getName();

			value = name1.compareTo(name2);
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}