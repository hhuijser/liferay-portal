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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class DDMFormInstanceRecordVersionUpgradeProcess extends UpgradeProcess {

	protected void deleteDDLRecordVersion(long recordVersionId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from DDLRecordVersion where recordVersionId = ?")) {

			preparedStatement.setLong(1, recordVersionId);

			preparedStatement.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(5);

		sb1.append("select DDLRecordVersion.* , DDMFormInstance.groupId as ");
		sb1.append("formInstanceGroupId, DDMFormInstance.version as ");
		sb1.append("formInstanceVersion from DDLRecordVersion inner join ");
		sb1.append("DDMFormInstance on DDLRecordVersion.recordSetId = ");
		sb1.append("DDMFormInstance.formInstanceId");

		StringBundler sb2 = new StringBundler(7);

		sb2.append("insert into DDMFormInstanceRecordVersion(");
		sb2.append("formInstanceRecordVersionId, groupId, companyId, userId, ");
		sb2.append("userName, createDate, formInstanceId, ");
		sb2.append("formInstanceVersion, formInstanceRecordId, version, ");
		sb2.append("status, statusByUserId, statusByUserName, statusDate, ");
		sb2.append("storageId) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sb2.append("?, ?)");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb1.toString());
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			while (resultSet.next()) {
				long recordVersionId = resultSet.getLong("recordVersionId");

				preparedStatement2.setLong(1, recordVersionId);

				preparedStatement2.setLong(
					2, resultSet.getLong("formInstanceGroupId"));
				preparedStatement2.setLong(3, resultSet.getLong("companyId"));
				preparedStatement2.setLong(4, resultSet.getLong("userId"));
				preparedStatement2.setString(
					5, resultSet.getString("userName"));
				preparedStatement2.setTimestamp(
					6, resultSet.getTimestamp("createDate"));
				preparedStatement2.setLong(7, resultSet.getLong("recordSetId"));
				preparedStatement2.setString(
					8, resultSet.getString("formInstanceVersion"));
				preparedStatement2.setLong(9, resultSet.getLong("recordId"));
				preparedStatement2.setString(
					10, resultSet.getString("version"));
				preparedStatement2.setInt(11, resultSet.getInt("status"));
				preparedStatement2.setLong(
					12, resultSet.getLong("statusByUserId"));
				preparedStatement2.setString(
					13, resultSet.getString("statusByUserName"));
				preparedStatement2.setTimestamp(
					14, resultSet.getTimestamp("statusDate"));
				preparedStatement2.setLong(
					15, resultSet.getLong("DDMStorageId"));

				deleteDDLRecordVersion(recordVersionId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}