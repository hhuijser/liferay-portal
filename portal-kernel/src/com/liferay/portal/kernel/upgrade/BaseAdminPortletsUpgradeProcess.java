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

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public abstract class BaseAdminPortletsUpgradeProcess extends UpgradeProcess {

	protected void addResourcePermission(
			long resourcePermissionId, long companyId, String name, int scope,
			String primKey, long roleId, long actionIds)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into ResourcePermission (resourcePermissionId, " +
					"companyId, name, scope, primKey, roleId, actionIds) " +
						"values (?, ?, ?, ?, ?, ?, ?)")) {

			ps.setLong(1, resourcePermissionId);
			ps.setLong(2, companyId);
			ps.setString(3, name);
			ps.setInt(4, scope);
			ps.setString(5, primKey);
			ps.setLong(6, roleId);
			ps.setLong(7, actionIds);

			ps.executeUpdate();
		}
	}

	protected long getBitwiseValue(String name, String actionId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ? and " +
					"actionId = ?")) {

			ps.setString(1, name);
			ps.setString(2, actionId);

			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("bitwiseValue");
				}

				return 0;
			}
		}
	}

	protected long getControlPanelGroupId() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId from Group_ where name = '" +
					GroupConstants.CONTROL_PANEL + "'");
			ResultSet resultSet = ps.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getLong("groupId");
			}

			return 0;
		}
	}

	protected void updateAccessInControlPanelPermission(
			String portletFrom, String portletTo)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long bitwiseValue = getBitwiseValue(
				portletFrom, ActionKeys.ACCESS_IN_CONTROL_PANEL);

			try (PreparedStatement ps = connection.prepareStatement(
					"select * from ResourcePermission where name = ?")) {

				ps.setString(1, portletFrom);

				try (ResultSet resultSet = ps.executeQuery()) {
					while (resultSet.next()) {
						long actionIds = resultSet.getLong("actionIds");

						if ((actionIds & bitwiseValue) == 0) {
							continue;
						}

						actionIds = actionIds & ~bitwiseValue;

						long resourcePermissionId = resultSet.getLong(
							"resourcePermissionId");

						runSQL(
							StringBundler.concat(
								"update ResourcePermission set actionIds = ",
								actionIds, " where resourcePermissionId = ",
								resourcePermissionId));

						resourcePermissionId = increment(
							ResourcePermission.class.getName());

						long companyId = resultSet.getLong("companyId");
						int scope = resultSet.getInt("scope");
						String primKey = resultSet.getString("primKey");
						long roleId = resultSet.getLong("roleId");

						actionIds = resultSet.getLong("actionIds");

						actionIds |= bitwiseValue;

						addResourcePermission(
							resourcePermissionId, companyId, portletTo, scope,
							primKey, roleId, actionIds);
					}
				}
			}
		}
	}

}