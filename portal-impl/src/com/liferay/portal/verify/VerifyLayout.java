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
import com.liferay.portal.kernel.util.StringBundler;
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

			StringBundler sb = new StringBundler(7);

			sb.append("update assetentry t1, layout t2 set t1.layoutUuid = ");
			sb.append("t2.sourcePrototypeLayoutUuid where t1.layoutUuid in ");
			sb.append("(select uuid_ from layout where ");
			sb.append("sourcePrototypeLayoutUuid is not null and ");
			sb.append("sourcePrototypeLayoutUuid not like '' and uuid_ not ");
			sb.append("like sourcePrototypeLayoutUuid) and t2.uuid_ = ");
			sb.append("t1.layoutUuid");

			runSQL(sb.toString());

			sb = new StringBundler(7);

			sb.append("update journalarticle t1, layout t2 set ");
			sb.append("t1.layoutUuid = t2.sourcePrototypeLayoutUuid where ");
			sb.append("t1.layoutUuid in (select uuid_ from layout where ");
			sb.append("sourcePrototypeLayoutUuid is not null and ");
			sb.append("sourcePrototypeLayoutUuid not like '' and uuid_ not ");
			sb.append("like sourcePrototypeLayoutUuid) and t2.uuid_ = ");
			sb.append("t1.layoutUuid");

			runSQL(sb.toString());

			sb = new StringBundler(4);

			sb.append("update layout set uuid_ = sourcePrototypeLayoutUuid ");
			sb.append("where sourcePrototypeLayoutUuid is not null and ");
			sb.append("sourcePrototypeLayoutUuid not like '' and uuid_ not ");
			sb.append("like sourcePrototypeLayoutUuid");

			runSQL(sb.toString());
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}