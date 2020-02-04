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

package com.liferay.fragment.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the FragmentEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryTable extends BaseTable<FragmentEntryTable> {

	public static final FragmentEntryTable INSTANCE = new FragmentEntryTable();

	public final Column<FragmentEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<FragmentEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<FragmentEntryTable, Long> fragmentEntryId =
		createColumn("fragmentEntryId", Long.class, Types.BIGINT);
	public final Column<FragmentEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<FragmentEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<FragmentEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<FragmentEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<FragmentEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<FragmentEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<FragmentEntryTable, Long> fragmentCollectionId =
		createColumn("fragmentCollectionId", Long.class, Types.BIGINT);
	public final Column<FragmentEntryTable, String> fragmentEntryKey =
		createColumn("fragmentEntryKey", String.class, Types.VARCHAR);
	public final Column<FragmentEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<FragmentEntryTable, Clob> css = createColumn(
		"css", Clob.class, Types.CLOB);
	public final Column<FragmentEntryTable, Clob> html = createColumn(
		"html", Clob.class, Types.CLOB);
	public final Column<FragmentEntryTable, Clob> js = createColumn(
		"js", Clob.class, Types.CLOB);
	public final Column<FragmentEntryTable, Clob> configuration = createColumn(
		"configuration", Clob.class, Types.CLOB);
	public final Column<FragmentEntryTable, Long> previewFileEntryId =
		createColumn("previewFileEntryId", Long.class, Types.BIGINT);
	public final Column<FragmentEntryTable, Boolean> readOnly = createColumn(
		"readOnly", Boolean.class, Types.BOOLEAN);
	public final Column<FragmentEntryTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER);
	public final Column<FragmentEntryTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);
	public final Column<FragmentEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<FragmentEntryTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT);
	public final Column<FragmentEntryTable, String> statusByUserName =
		createColumn("statusByUserName", String.class, Types.VARCHAR);
	public final Column<FragmentEntryTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP);

	private FragmentEntryTable() {
		super("FragmentEntry", FragmentEntryTable::new);
	}

}