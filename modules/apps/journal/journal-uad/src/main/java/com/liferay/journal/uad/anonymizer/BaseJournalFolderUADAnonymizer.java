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

package com.liferay.journal.uad.anonymizer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.uad.constants.JournalUADConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the journal folder UAD anonymizer.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom service methods should be put in
 * {@link JournalFolderUADAnonymizer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseJournalFolderUADAnonymizer
	extends DynamicQueryUADAnonymizer<JournalFolder> {

	@Override
	public void autoAnonymize(
			JournalFolder journalFolder, long userId, User anonymousUser)
		throws PortalException {

		if (journalFolder.getUserId() == userId) {
			journalFolder.setUserId(anonymousUser.getUserId());
			journalFolder.setUserName(anonymousUser.getFullName());

			autoAnonymizeAssetEntry(journalFolder, anonymousUser);
		}

		if (journalFolder.getStatusByUserId() == userId) {
			journalFolder.setStatusByUserId(anonymousUser.getUserId());
			journalFolder.setStatusByUserName(anonymousUser.getFullName());
		}

		journalFolderLocalService.updateJournalFolder(journalFolder);
	}

	@Override
	public void delete(JournalFolder journalFolder) throws PortalException {
		journalFolderLocalService.deleteFolder(journalFolder);
	}

	@Override
	public Class<JournalFolder> getTypeClass() {
		return JournalFolder.class;
	}

	protected void autoAnonymizeAssetEntry(
		JournalFolder journalFolder, User anonymousUser) {

		AssetEntry assetEntry = fetchAssetEntry(journalFolder);

		if (assetEntry != null) {
			assetEntry.setUserId(anonymousUser.getUserId());
			assetEntry.setUserName(anonymousUser.getFullName());

			assetEntryLocalService.updateAssetEntry(assetEntry);
		}
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return journalFolderLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return JournalUADConstants.USER_ID_FIELD_NAMES_JOURNAL_FOLDER;
	}

	protected AssetEntry fetchAssetEntry(JournalFolder journalFolder) {
		return assetEntryLocalService.fetchEntry(
			JournalFolder.class.getName(), journalFolder.getFolderId());
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected JournalFolderLocalService journalFolderLocalService;

}