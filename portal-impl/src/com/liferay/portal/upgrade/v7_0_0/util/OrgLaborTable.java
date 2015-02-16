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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class OrgLaborTable {

	public static final String TABLE_NAME = "OrgLabor";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"orgLaborId", Types.BIGINT},
		{"organizationId", Types.BIGINT},
		{"typeId", Types.BIGINT},
		{"sunOpen", Types.INTEGER},
		{"sunClose", Types.INTEGER},
		{"monOpen", Types.INTEGER},
		{"monClose", Types.INTEGER},
		{"tueOpen", Types.INTEGER},
		{"tueClose", Types.INTEGER},
		{"wedOpen", Types.INTEGER},
		{"wedClose", Types.INTEGER},
		{"thuOpen", Types.INTEGER},
		{"thuClose", Types.INTEGER},
		{"friOpen", Types.INTEGER},
		{"friClose", Types.INTEGER},
		{"satOpen", Types.INTEGER},
		{"satClose", Types.INTEGER}
	};

	public static final String TABLE_SQL_CREATE = "create table OrgLabor (mvccVersion LONG default 0,orgLaborId LONG not null primary key,organizationId LONG,typeId LONG,sunOpen INTEGER,sunClose INTEGER,monOpen INTEGER,monClose INTEGER,tueOpen INTEGER,tueClose INTEGER,wedOpen INTEGER,wedClose INTEGER,thuOpen INTEGER,thuClose INTEGER,friOpen INTEGER,friClose INTEGER,satOpen INTEGER,satClose INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table OrgLabor";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_6AF0D434 on OrgLabor (organizationId)"
	};

}