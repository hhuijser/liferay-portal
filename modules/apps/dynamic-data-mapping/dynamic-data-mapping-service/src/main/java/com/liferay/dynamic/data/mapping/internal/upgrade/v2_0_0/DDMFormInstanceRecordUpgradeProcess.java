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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Pedro Queiroz
 */
public class DDMFormInstanceRecordUpgradeProcess extends UpgradeProcess {

	public DDMFormInstanceRecordUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	protected void addAssetEntry(
			String uuid, long formInstanceRecordId, long groupId, long userId,
			Timestamp createDate, Timestamp modifiedDate,
			String formInstanceName)
		throws Exception {

		Locale defautLocale = LocaleUtil.fromLanguageId(
			LocalizationUtil.getDefaultLanguageId(formInstanceName));
		Map<Locale, String> localizationMap =
			LocalizationUtil.getLocalizationMap(formInstanceName);

		if ((defautLocale != null) &&
			localizationMap.containsKey(defautLocale)) {

			String title = LanguageUtil.format(
				getResourceBundle(defautLocale), "form-record-for-form-x",
				localizationMap.get(defautLocale), false);

			_assetEntryLocalService.updateEntry(
				userId, groupId, createDate, modifiedDate,
				DDMFormInstanceRecord.class.getName(), formInstanceRecordId,
				uuid, 0, new long[0], new String[0], true, true, null, null,
				null, null, ContentTypes.TEXT_HTML, title, null,
				StringPool.BLANK, null, null, 0, 0, 0.0);
		}
	}

	protected void deleteDDLRecord(long recordId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from DDLRecord where recordId = ?")) {

			preparedStatement.setLong(1, recordId);

			preparedStatement.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(6);

		sb1.append("select DDLRecord.*, DDMFormInstance.groupId as ");
		sb1.append("formInstanceGroupId, DDMFormInstance.version as ");
		sb1.append("formInstanceVersion, DDMFormInstance.name as ");
		sb1.append("formInstanceName from DDLRecord inner join ");
		sb1.append("DDMFormInstance on DDLRecord.recordSetId = ");
		sb1.append("DDMFormInstance.formInstanceId");

		StringBundler sb2 = new StringBundler(6);

		sb2.append("insert into DDMFormInstanceRecord(uuid_, ");
		sb2.append("formInstanceRecordId, groupId, companyId, userId, ");
		sb2.append("userName, versionUserId, versionUserName, createDate, ");
		sb2.append("modifiedDate, formInstanceId, formInstanceVersion, ");
		sb2.append("storageId, version, lastPublishDate) values(?, ?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb1.toString());
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			while (resultSet.next()) {
				String uuid = PortalUUIDUtil.generate();
				long recordId = resultSet.getLong("recordId");
				long groupId = resultSet.getLong("formInstanceGroupId");
				long userId = resultSet.getLong("userId");
				Timestamp createDate = resultSet.getTimestamp("createDate");
				Timestamp modifiedDate = resultSet.getTimestamp("modifiedDate");

				preparedStatement2.setString(1, uuid);
				preparedStatement2.setLong(2, recordId);
				preparedStatement2.setLong(3, groupId);
				preparedStatement2.setLong(4, resultSet.getLong("companyId"));
				preparedStatement2.setLong(5, userId);
				preparedStatement2.setString(
					6, resultSet.getString("userName"));
				preparedStatement2.setLong(
					7, resultSet.getLong("versionUserId"));
				preparedStatement2.setString(
					8, resultSet.getString("versionUserName"));
				preparedStatement2.setTimestamp(9, createDate);
				preparedStatement2.setTimestamp(10, modifiedDate);

				preparedStatement2.setLong(
					11, resultSet.getLong("recordSetId"));
				preparedStatement2.setString(
					12, resultSet.getString("formInstanceVersion"));
				preparedStatement2.setLong(
					13, resultSet.getLong("DDMStorageId"));
				preparedStatement2.setString(
					14, resultSet.getString("version"));
				preparedStatement2.setTimestamp(
					15, resultSet.getTimestamp("lastPublishDate"));

				deleteDDLRecord(recordId);

				addAssetEntry(
					uuid, recordId, groupId, userId, createDate, modifiedDate,
					resultSet.getString("formInstanceName"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	protected ResourceBundle getResourceBundle(Locale defaultLocale) {
		return PortalUtil.getResourceBundle(defaultLocale);
	}

	private final AssetEntryLocalService _assetEntryLocalService;

}