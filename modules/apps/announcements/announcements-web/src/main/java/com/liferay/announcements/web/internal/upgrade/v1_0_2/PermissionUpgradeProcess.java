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

package com.liferay.announcements.web.internal.upgrade.v1_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Roberto Díaz
 */
public class PermissionUpgradeProcess extends UpgradeProcess {

	public PermissionUpgradeProcess() {
		this(false);
	}

	public PermissionUpgradeProcess(
		boolean ignoreMissingAddEntryResourceAction) {

		_ignoreMissingAddEntryResourceAction =
			ignoreMissingAddEntryResourceAction;
	}

	protected void addAnnouncementsAdminResourceActions() {
		addResourceAction(
			ActionKeys.ACCESS_IN_CONTROL_PANEL,
			_BITWISE_VALUE_ACCESS_IN_CONTROL_PANEL);
		addResourceAction(ActionKeys.VIEW, _BITWISE_VALUE_VIEW);
	}

	protected void addAnnouncementsAdminViewResourcePermission(
			long companyId, int scope, String primKey, long primKeyId,
			long roleId)
		throws Exception {

		String key = getKey(companyId, scope, primKey, roleId);

		if (_resourcePermissions.contains(key)) {
			return;
		}

		_resourcePermissions.add(key);

		long resourcePermissionId = increment(
			ResourcePermission.class.getName());

		long actionBitwiseValue =
			_BITWISE_VALUE_VIEW | _BITWISE_VALUE_ACCESS_IN_CONTROL_PANEL;

		String name =
			"com_liferay_announcements_web_portlet_AnnouncementsAdminPortlet";
		long ownerId = 0;

		StringBundler sb = new StringBundler(4);

		sb.append("insert into ResourcePermission (mvccVersion, ");
		sb.append("resourcePermissionId, companyId, name, scope, primKey, ");
		sb.append("primKeyId, roleId, ownerId, actionIds, viewActionId) ");
		sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, resourcePermissionId);
			preparedStatement.setLong(3, companyId);
			preparedStatement.setString(4, name);
			preparedStatement.setInt(5, scope);
			preparedStatement.setString(6, primKey);
			preparedStatement.setLong(7, primKeyId);
			preparedStatement.setLong(8, roleId);
			preparedStatement.setLong(9, ownerId);
			preparedStatement.setLong(10, actionBitwiseValue);
			preparedStatement.setBoolean(11, true);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource permission", exception);
			}
		}
	}

	protected void addResourceAction(String actionId, long bitwiseValue) {
		long resourceActionId = increment(ResourceAction.class.getName());

		StringBundler sb = new StringBundler(3);

		sb.append("insert into ResourceAction (mvccVersion, ");
		sb.append("resourceActionId, name, actionId, bitwiseValue) values ");
		sb.append("(?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, resourceActionId);
			preparedStatement.setString(
				3,
				"com_liferay_announcements_web_portlet_" +
					"AnnouncementsAdminPortlet");
			preparedStatement.setString(4, actionId);
			preparedStatement.setLong(5, bitwiseValue);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource action", exception);
			}
		}
	}

	protected void deleteResourceAction(long resourceActionId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from ResourceAction where resourceActionId = ?")) {

			preparedStatement.setLong(1, resourceActionId);

			preparedStatement.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		addAnnouncementsAdminResourceActions();

		upgradeAlertsResourcePermission();
		upgradeAnnouncementsResourcePermission();
	}

	protected String getKey(
		long companyId, int scope, String primKey, long roleId) {

		StringBundler sb = new StringBundler(7);

		sb.append(companyId);
		sb.append(StringPool.PERIOD);
		sb.append(scope);
		sb.append(StringPool.PERIOD);
		sb.append(primKey);
		sb.append(StringPool.PERIOD);
		sb.append(roleId);

		return sb.toString();
	}

	protected void updateResourcePermission(
			long resourcePermissionId, long bitwiseValue)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ResourcePermission set actionIds = ? where " +
					"resourcePermissionId = ?")) {

			preparedStatement.setLong(1, bitwiseValue);
			preparedStatement.setLong(2, resourcePermissionId);

			preparedStatement.executeUpdate();
		}
	}

	protected void upgradeAlertsResourcePermission() throws Exception {
		upgradeResourcePermission(
			"com_liferay_announcements_web_portlet_AlertsPortlet");
	}

	protected void upgradeAnnouncementsResourcePermission() throws Exception {
		upgradeResourcePermission(
			"com_liferay_announcements_web_portlet_AnnouncementsPortlet");
	}

	protected void upgradeResourcePermission(String name) throws Exception {
		StringBundler sb1 = new StringBundler(4);

		sb1.append("select resourceActionId, bitwiseValue from ");
		sb1.append("ResourceAction where actionId = 'ADD_ENTRY' and name = '");
		sb1.append(name);
		sb1.append("'");

		StringBundler sb2 = new StringBundler(5);

		sb2.append("select resourcePermissionId, companyId, scope, primKey, ");
		sb2.append("primKeyId, roleId, actionIds from ResourcePermission ");
		sb2.append("where name = '");
		sb2.append(name);
		sb2.append("'");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb1.toString());
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			if (!resultSet1.next()) {
				if (!_ignoreMissingAddEntryResourceAction) {
					_log.error(
						StringBundler.concat(
							"Unable to upgrade ADD_ENTRY action, ",
							"ResourceAction for ", name,
							" is not initialized"));
				}

				return;
			}

			long bitwiseValue = resultSet1.getLong("bitwiseValue");

			try (PreparedStatement preparedStatement2 =
					connection.prepareStatement(sb2.toString());
				ResultSet resultSet = preparedStatement2.executeQuery()) {

				while (resultSet.next()) {
					long actionIds = resultSet.getLong("actionIds");

					if ((bitwiseValue & actionIds) == 0) {
						continue;
					}

					long resourcePermissionId = resultSet.getLong(
						"resourcePermissionId");
					long companyId = resultSet.getLong("companyId");
					int scope = resultSet.getInt("scope");
					String primKey = resultSet.getString("primKey");
					long primKeyId = resultSet.getLong("primKeyId");

					updateResourcePermission(
						resourcePermissionId, actionIds - bitwiseValue);

					if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
						if (primKey.contains("_LAYOUT_")) {
							primKey = String.valueOf(companyId);
							primKeyId = companyId;
							scope = ResourceConstants.SCOPE_COMPANY;
						}
						else {
							continue;
						}
					}

					long roleId = resultSet.getLong("roleId");

					addAnnouncementsAdminViewResourcePermission(
						companyId, scope, primKey, primKeyId, roleId);
				}
			}

			long resourceActionId = resultSet1.getLong("resourceActionId");

			deleteResourceAction(resourceActionId);
		}
	}

	/**
	 * @see com.liferay.portal.service.impl.ResourceActionLocalServiceImpl#checkResourceActions
	 */
	private static final long _BITWISE_VALUE_ACCESS_IN_CONTROL_PANEL = 1 << 1;

	private static final long _BITWISE_VALUE_VIEW = 1;

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionUpgradeProcess.class);

	private final boolean _ignoreMissingAddEntryResourceAction;
	private final Set<String> _resourcePermissions = new HashSet<>();

}