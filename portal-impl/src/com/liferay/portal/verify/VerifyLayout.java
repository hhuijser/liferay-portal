/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyLayout extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		List<Layout> layouts =
			LayoutLocalServiceUtil.getNullFriendlyURLLayouts();

		for (Layout layout : layouts) {
			String friendlyURL = StringPool.SLASH + layout.getLayoutId();

			LayoutLocalServiceUtil.updateFriendlyURL(
				layout.getPlid(), friendlyURL);
		}

		verifyLayoutUuid();
	}

	protected void verifyLayoutUuid() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select uuid_, sourcePrototypeLayoutUuid from layout where " +
					"sourcePrototypeLayoutUuid is not null and " +
						"sourcePrototypeLayoutUuid not like '' and uuid_ " +
							"not like sourcePrototypeLayoutUuid");

			rs = ps.executeQuery();

			while (rs.next()) {
				String sourcePrototypeUuid = rs.getString(
					"sourcePrototypeLayoutUuid");
				String uuid = rs.getString("uuid_");

				runSQL(
					"update assetentry set layoutUuid = '" +
						sourcePrototypeUuid + "' where layoutUuid = '" +
							uuid + "'");
				runSQL(
					"update journalarticle set layoutUuid = '" +
						sourcePrototypeUuid + "' where layoutUuid = '" +
							uuid + "'");
				runSQL(
					"update layout set uuid_ = sourcePrototypeLayoutUuid " +
						"where sourcePrototypeLayoutUuid = '" +
							sourcePrototypeUuid + "'");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}