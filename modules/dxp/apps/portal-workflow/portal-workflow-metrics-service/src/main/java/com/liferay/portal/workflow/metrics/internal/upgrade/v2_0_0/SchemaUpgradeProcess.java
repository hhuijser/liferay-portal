/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.internal.upgrade.v2_0_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rafael Praxedes
 */
public class SchemaUpgradeProcess extends UpgradeProcess {

	public SchemaUpgradeProcess(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String template = StringUtil.read(
				SchemaUpgradeProcess.class.getResourceAsStream(
					"dependencies/update.sql"));

			runSQLTemplateString(template, false);

			if (!hasTable("WorkflowMetricsSLADefinition")) {
				return;
			}

			StringBundler sb1 = new StringBundler(9);

			sb1.append("insert into WMSLADefinition (mvccVersion, uuid_, ");
			sb1.append("wmSLADefinitionId, groupId, companyId, userId, ");
			sb1.append("userName, createDate, modifiedDate, active_, ");
			sb1.append("calendarKey, description, duration, name, ");
			sb1.append("pauseNodeKeys, processId, processVersion, ");
			sb1.append("startNodeKeys, stopNodeKeys, version, status, ");
			sb1.append("statusByUserId, statusByUserName, statusDate) ");
			sb1.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb1.append("?, ?, ?, ?, ?, ?, ?, ?, ?)");

			StringBundler sb2 = new StringBundler(10);

			sb2.append("insert into WMSLADefinitionVersion (mvccVersion, ");
			sb2.append("uuid_, wmSLADefinitionVersionId, groupId, companyId, ");
			sb2.append("userId, userName, createDate, modifiedDate, active_, ");
			sb2.append("calendarKey, description, duration, name, ");
			sb2.append("pauseNodeKeys, processId, processVersion, ");
			sb2.append("startNodeKeys, stopNodeKeys, version, ");
			sb2.append("wmSLADefinitionId, status, statusByUserId, ");
			sb2.append("statusByUserName, statusDate) values (?, ?, ?, ?, ?, ");
			sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb2.append("?, ?, ?)");

			try (PreparedStatement ps1 = connection.prepareStatement(
					"select WorkflowMetricsSLADefinition.* from " +
						"WorkflowMetricsSLADefinition");
				ResultSet resultSet = ps1.executeQuery();
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb1.toString());
				PreparedStatement ps3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb2.toString())) {

				while (resultSet.next()) {
					ps2.setLong(1, resultSet.getLong("mvccVersion"));
					ps2.setString(2, resultSet.getString("uuid_"));
					ps2.setLong(
						3, resultSet.getLong("workflowMetricsSLADefinitionId"));
					ps2.setLong(4, resultSet.getLong("groupId"));
					ps2.setLong(5, resultSet.getLong("companyId"));
					ps2.setLong(6, resultSet.getLong("userId"));
					ps2.setString(7, resultSet.getString("userName"));
					ps2.setTimestamp(8, resultSet.getTimestamp("createDate"));
					ps2.setTimestamp(9, resultSet.getTimestamp("modifiedDate"));
					ps2.setBoolean(10, true);
					ps2.setString(11, resultSet.getString("calendarKey"));
					ps2.setString(12, resultSet.getString("description"));
					ps2.setLong(13, resultSet.getLong("duration"));
					ps2.setString(14, resultSet.getString("name"));
					ps2.setString(15, resultSet.getString("pauseNodeKeys"));
					ps2.setLong(16, resultSet.getLong("processId"));
					ps2.setString(17, resultSet.getString("processVersion"));
					ps2.setString(18, resultSet.getString("startNodeKeys"));
					ps2.setString(19, resultSet.getString("stopNodeKeys"));
					ps2.setString(20, _DEFAULT_VERSION);
					ps2.setInt(21, resultSet.getInt("status"));
					ps2.setLong(22, resultSet.getLong("userId"));
					ps2.setString(23, resultSet.getString("userName"));
					ps2.setTimestamp(
						24, resultSet.getTimestamp("modifiedDate"));

					ps2.addBatch();

					ps3.setLong(1, 0);
					ps3.setString(2, PortalUUIDUtil.generate());
					ps3.setLong(3, _counterLocalService.increment());
					ps3.setLong(4, resultSet.getLong("groupId"));
					ps3.setLong(5, resultSet.getLong("companyId"));
					ps3.setLong(6, resultSet.getLong("userId"));
					ps3.setString(7, resultSet.getString("userName"));
					ps3.setTimestamp(8, resultSet.getTimestamp("createDate"));
					ps3.setTimestamp(9, resultSet.getTimestamp("modifiedDate"));
					ps3.setBoolean(10, true);
					ps3.setString(11, resultSet.getString("calendarKey"));
					ps3.setString(12, resultSet.getString("description"));
					ps3.setLong(13, resultSet.getLong("duration"));
					ps3.setString(14, resultSet.getString("name"));
					ps3.setString(15, resultSet.getString("pauseNodeKeys"));
					ps3.setLong(16, resultSet.getLong("processId"));
					ps3.setString(17, resultSet.getString("processVersion"));
					ps3.setString(18, resultSet.getString("startNodeKeys"));
					ps3.setString(19, resultSet.getString("stopNodeKeys"));
					ps3.setString(20, _DEFAULT_VERSION);
					ps3.setLong(
						21,
						resultSet.getLong("workflowMetricsSLADefinitionId"));
					ps3.setInt(22, resultSet.getInt("status"));
					ps3.setLong(23, resultSet.getLong("userId"));
					ps3.setString(24, resultSet.getString("userName"));
					ps3.setTimestamp(
						25, resultSet.getTimestamp("modifiedDate"));

					ps3.addBatch();
				}

				ps2.executeBatch();
				ps3.executeBatch();
			}
		}
	}

	private static final String _DEFAULT_VERSION = "1.0";

	private final CounterLocalService _counterLocalService;

}