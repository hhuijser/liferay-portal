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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_0_0.util.OrganizationTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Brian Wing Shun Chan
 * @author Christopher Kian
 */
public class UpgradeOrganization extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(OrganizationTable.class, new AlterColumnType("statusId", "LONG"));

		upgradeOrganizationLogoId();
	}

	protected void upgradeOrganizationLogoId() throws SQLException {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select groupId, logoId from LayoutSet where logoId > 0 and " +
					"privateLayout = ?");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select classPK from Group_ where groupId = ?");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"update Organization_ set logoId = ? where organizationId = " +
					"?")) {

			preparedStatement1.setBoolean(1, false);

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				long groupId = resultSet1.getLong("groupId");
				long logoId = resultSet1.getLong("logoId");

				preparedStatement2.setLong(1, groupId);

				ResultSet resultSet2 = preparedStatement2.executeQuery();

				while (resultSet2.next()) {
					long classPK = resultSet2.getLong("classPK");

					preparedStatement3.setLong(1, logoId);
					preparedStatement3.setLong(2, classPK);

					preparedStatement3.executeUpdate();
				}
			}
		}
	}

}