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

package com.liferay.petra.sql.dsl.query;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.impl.OrderBy;
import com.liferay.petra.sql.dsl.query.impl.OrderByExpressionImpl;
import com.liferay.petra.string.StringBundler;

/**
 * @author Preston Crary
 */
public interface OrderByStep extends LimitStep {

	public default LimitStep orderBy(OrderByExpression... orderByExpressions) {
		if ((orderByExpressions == null) || (orderByExpressions.length == 0)) {
			return this;
		}

		return new OrderBy(this, orderByExpressions);
	}

	public default LimitStep orderBy(Table<?> table, OrderByInfo orderByInfo) {
		if (orderByInfo == null) {
			return this;
		}

		String[] orderByFields = orderByInfo.getOrderByFields();

		OrderByExpression[] orderByExpressions =
			new OrderByExpression[orderByFields.length];

		for (int i = 0; i < orderByFields.length; i++) {
			String field = orderByFields[i];

			Column<?, ?> column = table.getColumn(field);

			if (column == null) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"No column \"", field, "\" for table ",
						table.getTableName()));
			}

			orderByExpressions[i] = new OrderByExpressionImpl(
				column, orderByInfo.isAscending(field));
		}

		return new OrderBy(this, orderByExpressions);
	}

}