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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the Subscription.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SubscriptionTable extends BaseTable<SubscriptionTable> {

	public static final SubscriptionTable INSTANCE = new SubscriptionTable();

	public final Column<SubscriptionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<SubscriptionTable, Long> subscriptionId = createColumn(
		"subscriptionId", Long.class, Types.BIGINT);
	public final Column<SubscriptionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<SubscriptionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SubscriptionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SubscriptionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SubscriptionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SubscriptionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SubscriptionTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<SubscriptionTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<SubscriptionTable, String> frequency = createColumn(
		"frequency", String.class, Types.VARCHAR);

	private SubscriptionTable() {
		super("Subscription", SubscriptionTable::new);
	}

}