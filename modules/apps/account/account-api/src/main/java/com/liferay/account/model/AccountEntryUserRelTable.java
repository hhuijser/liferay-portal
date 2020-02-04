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

package com.liferay.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the AccountEntryUserRel.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryUserRelTable
	extends BaseTable<AccountEntryUserRelTable> {

	public static final AccountEntryUserRelTable INSTANCE =
		new AccountEntryUserRelTable();

	public final Column<AccountEntryUserRelTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<AccountEntryUserRelTable, Long> accountEntryUserRelId =
		createColumn("accountEntryUserRelId", Long.class, Types.BIGINT);
	public final Column<AccountEntryUserRelTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<AccountEntryUserRelTable, Long> accountEntryId =
		createColumn("accountEntryId", Long.class, Types.BIGINT);
	public final Column<AccountEntryUserRelTable, Long> accountUserId =
		createColumn("accountUserId", Long.class, Types.BIGINT);

	private AccountEntryUserRelTable() {
		super("AccountEntryUserRel", AccountEntryUserRelTable::new);
	}

}