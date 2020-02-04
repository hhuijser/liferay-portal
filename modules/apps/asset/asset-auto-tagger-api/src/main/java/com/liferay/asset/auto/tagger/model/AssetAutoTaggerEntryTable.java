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

package com.liferay.asset.auto.tagger.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the AssetAutoTaggerEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetAutoTaggerEntryTable
	extends BaseTable<AssetAutoTaggerEntryTable> {

	public static final AssetAutoTaggerEntryTable INSTANCE =
		new AssetAutoTaggerEntryTable();

	public final Column<AssetAutoTaggerEntryTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<AssetAutoTaggerEntryTable, Long>
		assetAutoTaggerEntryId = createColumn(
			"assetAutoTaggerEntryId", Long.class, Types.BIGINT);
	public final Column<AssetAutoTaggerEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<AssetAutoTaggerEntryTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<AssetAutoTaggerEntryTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetAutoTaggerEntryTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AssetAutoTaggerEntryTable, Long> assetEntryId =
		createColumn("assetEntryId", Long.class, Types.BIGINT);
	public final Column<AssetAutoTaggerEntryTable, Long> assetTagId =
		createColumn("assetTagId", Long.class, Types.BIGINT);

	private AssetAutoTaggerEntryTable() {
		super("AssetAutoTaggerEntry", AssetAutoTaggerEntryTable::new);
	}

}