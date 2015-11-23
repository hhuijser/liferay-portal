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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Mohit Soni
 */
public class UpgradeLayoutSet extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeLayoutSet();
	}

	protected int getLayoutsByGroupAndPrivateLayout(
			long groupId, boolean privateLayout)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select count(*) from Layout " +
					"where groupId = ? and privateLayout = ?");

			ps.setLong(1, groupId);
			ps.setBoolean(2, privateLayout);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);

				return count;
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void hasGroupAnOrganization(
		long orgClassNameId, long classNameId) {

		if (orgClassNameId == classNameId) {
			_isOrganization = true;
		}
	}

	protected boolean hasGroupAnySite(long groupId) throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.model.Organization");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select site, classNameId from Group_ " + "where groupId = ?");

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				boolean site = rs.getBoolean("site");

				hasGroupAnOrganization(classNameId, rs.getLong("classNameId"));

				return site;
			}

			return false;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateGroupSite(long groupId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update Group_ set site = ? where groupId = ?");

			ps.setInt(1, 0);
			ps.setLong(2, groupId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update Group " + groupId, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLayoutSet(long layoutSetId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			ps = con.prepareStatement(
				"update LayoutSet set layoutSetPrototypeLinkEnabled = ? ," +
					" layoutSetPrototypeUuid = '' , modifiedDate = ?," +
						"pageCount = ? where layoutSetId = ?");

			ps.setInt(1, 0);
			ps.setTimestamp(2, timestamp);
			ps.setInt(3, 0);
			ps.setLong(4, layoutSetId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update layoutSet " + layoutSetId, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void upgradeLayoutSet() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select layoutSetId, groupId, privateLayout from layoutset " +
					"where layoutSetPrototypeUuid != ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long layoutSetId = rs.getLong("layoutSetId");
				long groupId = rs.getLong("groupId");
				boolean privateLayout = rs.getBoolean("privateLayout");

				boolean updateLayoutSetRequired = false;

				if (hasGroupAnySite(groupId)) {
					if ((getLayoutsByGroupAndPrivateLayout(
							groupId, privateLayout) == 0 ) &&
						_isOrganization) {
							updateLayoutSetRequired = true;
						}
				}
				else if (_isOrganization) {
					updateLayoutSetRequired = true;
				}

				if (updateLayoutSetRequired) {
					updateLayoutSet(layoutSetId);

					updateGroupSite(groupId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeLayoutSet.class);

	private boolean _isOrganization = false;

}