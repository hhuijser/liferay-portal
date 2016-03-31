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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.upgrade.v6_2_0.util.LayoutTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Harrison Schueler
 */
public class UpgradeLayout extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(LayoutTable.class, new AlterColumnType("css", "TEXT null"));

		updateLayoutOwners();
	}

	protected String getUserName(long userId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select firstName, middleName, lastName from User_ where " +
					"userId = ?")) {

			ps.setLong(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String firstName = rs.getString("firstName");
					String middleName = rs.getString("middleName");
					String lastName = rs.getString("lastName");

					FullNameGenerator fullNameGenerator =
						FullNameGeneratorFactory.getInstance();

					return fullNameGenerator.getFullName(
						firstName, middleName, lastName);
				}

				return StringPool.BLANK;
			}
		}
	}

	protected void updateLayoutOwner(
			long companyId, String primKey, long ownerId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Layout set userId = ?, userName = ? where companyId =" +
					" ? and plid = ?")) {

			ps.setLong(1, ownerId);
			ps.setString(2, getUserName(ownerId));
			ps.setLong(3, companyId);
			ps.setLong(4, Long.valueOf(primKey));

			ps.executeUpdate();
		}
	}

	protected void updateLayoutOwners() throws Exception {
		StringBundler sb = new StringBundler(10);

		sb.append("select ResourcePermission.companyId, ");
		sb.append("ResourcePermission.primKey, ");
		sb.append("ResourcePermission.ownerId ");
		sb.append("from ResourcePermission inner join Role_ on ");
		sb.append("ResourcePermission.roleId = Role_.roleId ");
		sb.append("where ResourcePermission.name = '");
		sb.append(Layout.class.getName());
		sb.append("' and Role_.name = '");
		sb.append(RoleConstants.OWNER);
		sb.append("'");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String primKey = rs.getString("primKey");
				long ownerId = rs.getLong("ownerId");

				updateLayoutOwner(companyId, primKey, ownerId);
			}
		}
	}

}