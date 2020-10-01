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

package com.liferay.knowledge.base.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Peter Shin
 */
public class UpgradeRatingsStats extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateRatingsStats();
	}

	protected long getClassNameId(String className) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select classNameId from ClassName_ where value = ?")) {

			ps.setString(1, className);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("classNameId");
				}

				return 0;
			}
		}
	}

	protected void updateRatingsStats() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select statsId, totalScore, averageScore from RatingsStats " +
					"where classNameId = " +
						getClassNameId(_CLASS_NAME_ARTICLE));
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long statsId = rs.getLong("statsId");
				double totalScore = rs.getDouble("totalScore");
				double averageScore = rs.getDouble("averageScore");

				StringBundler sb = new StringBundler(6);

				sb.append("update RatingsStats set totalScore = ");
				sb.append(totalScore * 2);
				sb.append(", averageScore = ");
				sb.append(averageScore * 2);
				sb.append(" where statsId = ");
				sb.append(statsId);

				runSQL(sb.toString());
			}
		}
	}

	private static final String _CLASS_NAME_ARTICLE =
		"com.liferay.knowledgebase.model.Article";

}