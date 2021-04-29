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

package com.liferay.journal.internal.upgrade.v0_0_7;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * @author Mariano Álvaro Sáiz
 */
public class JournalArticleDatesUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateCreateDate();
		_updateModifiedDate();
	}

	private void _updateCreateDate() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select resourcePrimKey, min(createDate) from ");
		sb.append("JournalArticle group by resourcePrimKey having count(*) > ");
		sb.append("1");

		try (Statement s = connection.createStatement();
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set createDate = ? where " +
						"resourcePrimKey = ?")) {

			try (ResultSet resultSet = s.executeQuery(sb.toString())) {
				while (resultSet.next()) {
					long resourcePrimKey = resultSet.getLong(1);

					Timestamp createDate = resultSet.getTimestamp(2);

					preparedStatement.setTimestamp(1, createDate);

					preparedStatement.setLong(2, resourcePrimKey);

					preparedStatement.addBatch();
				}

				preparedStatement.executeBatch();
			}
		}
	}

	private void _updateModifiedDate() throws Exception {
		StringBundler sb = new StringBundler(11);

		sb.append("select classPK, version, AssetEntry.modifiedDate from ");
		sb.append("AssetEntry, (select modifiedDate, ");
		sb.append("JournalArticle.resourcePrimKey, version from ");
		sb.append("JournalArticle, (select resourcePrimKey, max(version) as ");
		sb.append("maxVersion from JournalArticle where status = ? group by ");
		sb.append("resourcePrimKey) LatestVersion where ");
		sb.append("JournalArticle.resourcePrimKey = ");
		sb.append("LatestVersion.resourcePrimKey and version = maxVersion) ");
		sb.append("JournalArticle where classNameId = ? and classPK = ");
		sb.append("JournalArticle.resourcePrimKey and ");
		sb.append("AssetEntry.modifiedDate != JournalArticle.modifiedDate");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString());
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update JournalArticle set modifiedDate = ? where " +
						"resourcePrimKey = ? and version = ?")) {

			preparedStatement1.setInt(1, WorkflowConstants.STATUS_APPROVED);
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long resourcePrimKey = resultSet.getLong(1);
					Double latestVersion = resultSet.getDouble(2);

					Timestamp assetModifiedDate = resultSet.getTimestamp(3);

					preparedStatement2.setTimestamp(1, assetModifiedDate);

					preparedStatement2.setLong(2, resourcePrimKey);

					preparedStatement2.setDouble(3, latestVersion);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

}