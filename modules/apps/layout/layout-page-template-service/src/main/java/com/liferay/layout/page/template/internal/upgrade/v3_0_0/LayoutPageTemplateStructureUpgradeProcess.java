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

package com.liferay.layout.page.template.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author David Arques
 */
public class LayoutPageTemplateStructureUpgradeProcess extends UpgradeProcess {

	protected void alterTable() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL("alter table LayoutPageTemplateStructure drop column data_");
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		upgradeLayoutPageTemplatesStructures();

		alterTable();
	}

	protected void upgradeLayoutPageTemplatesStructures() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select layoutPageTemplateStructureId, groupId, companyId, ");
		sb.append("userId, userName, createDate, data_ from ");
		sb.append("LayoutPageTemplateStructure");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long layoutPageTemplateStructureId = resultSet.getLong(
						"layoutPageTemplateStructureId");
					long groupId = resultSet.getLong("groupId");
					long companyId = resultSet.getLong("companyId");
					long userId = resultSet.getLong("userId");
					String userName = resultSet.getString("userName");
					Timestamp createDate = resultSet.getTimestamp("createDate");
					String data = resultSet.getString("data_");

					_updateLayoutPageTemplateStructureRels(
						groupId, companyId, userId, userName, createDate,
						layoutPageTemplateStructureId, data);
				}
			}
		}
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			LayoutPageTemplateStructureUpgradeProcess.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private void _updateLayoutPageTemplateStructureRels(
		long groupId, long companyId, long userId, String userName,
		Timestamp createDate, long layoutPageTemplateStructureId, String data) {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into LayoutPageTemplateStructureRel (uuid_, ");
		sb.append("lPageTemplateStructureRelId, groupId, companyId, userId, ");
		sb.append("userName, createDate, modifiedDate, ");
		sb.append("layoutPageTemplateStructureId, segmentsExperienceId, ");
		sb.append("data_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setString(1, PortalUUIDUtil.generate());
			preparedStatement.setLong(2, increment());
			preparedStatement.setLong(3, groupId);
			preparedStatement.setLong(4, companyId);
			preparedStatement.setLong(5, userId);
			preparedStatement.setString(6, userName);
			preparedStatement.setTimestamp(7, createDate);
			preparedStatement.setTimestamp(8, createDate);
			preparedStatement.setLong(9, layoutPageTemplateStructureId);
			preparedStatement.setLong(
				10, SegmentsExperienceConstants.ID_DEFAULT);
			preparedStatement.setString(11, data);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateStructureUpgradeProcess.class);

}