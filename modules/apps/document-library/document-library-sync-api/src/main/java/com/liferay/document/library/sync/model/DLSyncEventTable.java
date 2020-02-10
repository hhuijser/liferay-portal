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

package com.liferay.document.library.sync.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the DLSyncEvent.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLSyncEventTable extends BaseTable<DLSyncEventTable> {

	public static final DLSyncEventTable INSTANCE = new DLSyncEventTable();

	public final Column<DLSyncEventTable, Long> syncEventId = createColumn(
		"syncEventId", Long.class, Types.BIGINT);
	public final Column<DLSyncEventTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<DLSyncEventTable, Long> modifiedTime = createColumn(
		"modifiedTime", Long.class, Types.BIGINT);
	public final Column<DLSyncEventTable, String> event = createColumn(
		"event", String.class, Types.VARCHAR);
	public final Column<DLSyncEventTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR);
	public final Column<DLSyncEventTable, Long> typePK = createColumn(
		"typePK", Long.class, Types.BIGINT);

	private DLSyncEventTable() {
		super("DLSyncEvent", DLSyncEventTable::new);
	}

}