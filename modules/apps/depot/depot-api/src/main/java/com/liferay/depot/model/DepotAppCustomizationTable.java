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

package com.liferay.depot.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the DepotAppCustomization.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DepotAppCustomizationTable
	extends BaseTable<DepotAppCustomizationTable> {

	public static final DepotAppCustomizationTable INSTANCE =
		new DepotAppCustomizationTable();

	public final Column<DepotAppCustomizationTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<DepotAppCustomizationTable, Long>
		depotAppCustomizationId = createColumn(
			"depotAppCustomizationId", Long.class, Types.BIGINT);
	public final Column<DepotAppCustomizationTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<DepotAppCustomizationTable, Long> depotEntryId =
		createColumn("depotEntryId", Long.class, Types.BIGINT);
	public final Column<DepotAppCustomizationTable, Boolean> enabled =
		createColumn("enabled", Boolean.class, Types.BOOLEAN);
	public final Column<DepotAppCustomizationTable, String> portletId =
		createColumn("portletId", String.class, Types.VARCHAR);

	private DepotAppCustomizationTable() {
		super("DepotAppCustomization", DepotAppCustomizationTable::new);
	}

}