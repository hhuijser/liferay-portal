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

package com.liferay.sharing.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SharingEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SharingEntryTable extends BaseTable<SharingEntryTable> {

	public static final SharingEntryTable INSTANCE = new SharingEntryTable();

	public final Column<SharingEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<SharingEntryTable, Long> sharingEntryId = createColumn(
		"sharingEntryId", Long.class, Types.BIGINT);
	public final Column<SharingEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<SharingEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SharingEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SharingEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SharingEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SharingEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SharingEntryTable, Long> toUserId = createColumn(
		"toUserId", Long.class, Types.BIGINT);
	public final Column<SharingEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<SharingEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<SharingEntryTable, Boolean> shareable = createColumn(
		"shareable", Boolean.class, Types.BOOLEAN);
	public final Column<SharingEntryTable, Long> actionIds = createColumn(
		"actionIds", Long.class, Types.BIGINT);
	public final Column<SharingEntryTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP);

	private SharingEntryTable() {
		super("SharingEntry", SharingEntryTable::new);
	}

}