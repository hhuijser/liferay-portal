/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class UpgradeCountry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateZipCodeRequired();
	}

	protected void updateZipCodeRequired() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			for (String country : countriesWithoutZips) {
				ps = con.prepareStatement(
					"update Country set zipCodeRequired = false where name = " +
						country);
			}

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private String[] countriesWithoutZips = {"Angola", "Antigua", "Aruba",
			"Bahamas", "Belize", "Benin", "Botswana", "Burkina Faso",
			"Burundi", "Central African Republic", "Comoros",
			"Republic of Congo", "Democratic Republic of Congo",
			"Cook Islands", "Djibouti", "Dominica", "Equatorial Guinea",
			"Eritrea", "Fiji Islands", "Gambia", "Ghana", "Grenada", "Guinea",
			"Guyana", "Ireland", "Kiribati", "North Korea", "Macau", "Malawi",
			"Mali", "Mauritania", "Mauritius", "Montserrat", "Nauru", "Niue",
			"Qatar", "Rwanda", "St. Kitts", "St. Lucia", "Sao Tome & Principe",
			"Seychelles", "Sierra Leone", "Solomon Islands", "Somalia",
			"Suriname", "Syria", "Tanzania", "Tonga", "Trinidad & Tobago",
			"Tuvalu", "Uganda", "United Arab Emirates", "Vanuatu", "Yemen",
			"Zimbabwe"};
}