/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class WikiPageTable {

	public static final String TABLE_NAME = "WikiPage";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"pageId", Types.BIGINT},
		{"resourcePrimKey", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"nodeId", Types.BIGINT},
		{"title", Types.VARCHAR},
		{"version", Types.VARCHAR},
		{"minorEdit", Types.BOOLEAN},
		{"content", Types.CLOB},
		{"summary", Types.VARCHAR},
		{"format", Types.VARCHAR},
		{"head", Types.BOOLEAN},
		{"parentTitle", Types.VARCHAR},
		{"redirectTitle", Types.VARCHAR},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final String TABLE_SQL_CREATE = "create table WikiPage (uuid_ VARCHAR(75) null,pageId LONG not null primary key,resourcePrimKey LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,nodeId LONG,title VARCHAR(255) null,version VARCHAR(75) null,minorEdit BOOLEAN,content TEXT null,summary STRING null,format VARCHAR(75) null,head BOOLEAN,parentTitle VARCHAR(255) null,redirectTitle VARCHAR(255) null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table WikiPage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A2001730 on WikiPage (format)",
		"create index IX_16E99B0A on WikiPage (groupId, nodeId, head)",
		"create index IX_BA72B89A on WikiPage (groupId, nodeId, head, parentTitle, status)",
		"create index IX_E0092FF0 on WikiPage (groupId, nodeId, head, status)",
		"create index IX_941E429C on WikiPage (groupId, nodeId, status)",
		"create index IX_5FF21CE6 on WikiPage (groupId, nodeId, title, head)",
		"create index IX_CAA451D6 on WikiPage (groupId, userId, nodeId, status)",
		"create index IX_C8A9C476 on WikiPage (nodeId)",
		"create index IX_E7F635CA on WikiPage (nodeId, head)",
		"create index IX_65E84AF4 on WikiPage (nodeId, head, parentTitle)",
		"create index IX_9F7655DA on WikiPage (nodeId, head, parentTitle, status)",
		"create index IX_40F94F68 on WikiPage (nodeId, head, redirectTitle, status)",
		"create index IX_432F0AB0 on WikiPage (nodeId, head, status)",
		"create index IX_46EEF3C8 on WikiPage (nodeId, parentTitle)",
		"create index IX_1ECC7656 on WikiPage (nodeId, redirectTitle)",
		"create index IX_546F2D5C on WikiPage (nodeId, status)",
		"create index IX_997EEDD2 on WikiPage (nodeId, title)",
		"create index IX_E745EA26 on WikiPage (nodeId, title, head)",
		"create index IX_BEA33AB8 on WikiPage (nodeId, title, status)",
		"create unique index IX_3D4AF476 on WikiPage (nodeId, title, version)",
		"create index IX_85E7CC76 on WikiPage (resourcePrimKey)",
		"create index IX_B771D67 on WikiPage (resourcePrimKey, nodeId)",
		"create index IX_E1F55FB on WikiPage (resourcePrimKey, nodeId, head)",
		"create index IX_94D1054D on WikiPage (resourcePrimKey, nodeId, status)",
		"create unique index IX_2CD67C81 on WikiPage (resourcePrimKey, nodeId, version)",
		"create index IX_1725355C on WikiPage (resourcePrimKey, status)",
		"create index IX_FBBE7C96 on WikiPage (userId, nodeId, status)",
		"create index IX_9C0E478F on WikiPage (uuid_)",
		"create index IX_5DC4BD39 on WikiPage (uuid_, companyId)",
		"create unique index IX_899D3DFB on WikiPage (uuid_, groupId)"
	};

}