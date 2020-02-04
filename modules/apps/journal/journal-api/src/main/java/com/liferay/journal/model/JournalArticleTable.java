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

package com.liferay.journal.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the JournalArticle.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class JournalArticleTable extends BaseTable<JournalArticleTable> {

	public static final JournalArticleTable INSTANCE =
		new JournalArticleTable();

	public final Column<JournalArticleTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, Long> ctCollectionId =
		createColumn("ctCollectionId", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, Long> id = createColumn(
		"id_", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, Long> resourcePrimKey =
		createColumn("resourcePrimKey", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<JournalArticleTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<JournalArticleTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, String> articleId = createColumn(
		"articleId", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, Double> version = createColumn(
		"version", Double.class, Types.DOUBLE);
	public final Column<JournalArticleTable, String> urlTitle = createColumn(
		"urlTitle", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, Clob> content = createColumn(
		"content", Clob.class, Types.CLOB);
	public final Column<JournalArticleTable, String> DDMStructureKey =
		createColumn("DDMStructureKey", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, String> DDMTemplateKey =
		createColumn("DDMTemplateKey", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, String> defaultLanguageId =
		createColumn("defaultLanguageId", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, String> layoutUuid = createColumn(
		"layoutUuid", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP);
	public final Column<JournalArticleTable, Date> expirationDate =
		createColumn("expirationDate", Date.class, Types.TIMESTAMP);
	public final Column<JournalArticleTable, Date> reviewDate = createColumn(
		"reviewDate", Date.class, Types.TIMESTAMP);
	public final Column<JournalArticleTable, Boolean> indexable = createColumn(
		"indexable", Boolean.class, Types.BOOLEAN);
	public final Column<JournalArticleTable, Boolean> smallImage = createColumn(
		"smallImage", Boolean.class, Types.BOOLEAN);
	public final Column<JournalArticleTable, Long> smallImageId = createColumn(
		"smallImageId", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, String> smallImageURL =
		createColumn("smallImageURL", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);
	public final Column<JournalArticleTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<JournalArticleTable, Long> statusByUserId =
		createColumn("statusByUserId", Long.class, Types.BIGINT);
	public final Column<JournalArticleTable, String> statusByUserName =
		createColumn("statusByUserName", String.class, Types.VARCHAR);
	public final Column<JournalArticleTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP);

	private JournalArticleTable() {
		super("JournalArticle", JournalArticleTable::new);
	}

}