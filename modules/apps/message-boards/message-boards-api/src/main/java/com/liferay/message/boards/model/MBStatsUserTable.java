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

package com.liferay.message.boards.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the MBStatsUser.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBStatsUserTable extends BaseTable<MBStatsUserTable> {

	public static final MBStatsUserTable INSTANCE = new MBStatsUserTable();

	public final Column<MBStatsUserTable, Long> statsUserId = createColumn(
		"statsUserId", Long.class, Types.BIGINT);
	public final Column<MBStatsUserTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<MBStatsUserTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<MBStatsUserTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<MBStatsUserTable, Integer> messageCount = createColumn(
		"messageCount", Integer.class, Types.INTEGER);
	public final Column<MBStatsUserTable, Date> lastPostDate = createColumn(
		"lastPostDate", Date.class, Types.TIMESTAMP);

	private MBStatsUserTable() {
		super("MBStatsUser", MBStatsUserTable::new);
	}

}