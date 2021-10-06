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
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.uad.constants.JournalUADConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the journal article UAD anonymizer.
 *
 * <p>
 * This implementation exists only as a container for the default methods
 * generated by ServiceBuilder. All custom service methods should be put in
 * {@link JournalArticleUADAnonymizer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseJournalArticleUADAnonymizer
	extends DynamicQueryUADAnonymizer<JournalArticle> {

	@Override
	public void autoAnonymize(
			JournalArticle journalArticle, long userId, User anonymousUser)
		throws PortalException {

		if (journalArticle.getUserId() == userId) {
			journalArticle.setUserId(anonymousUser.getUserId());
			journalArticle.setUserName(anonymousUser.getFullName());

			autoAnonymizeAssetEntry(journalArticle, anonymousUser);
		}

		if (journalArticle.getStatusByUserId() == userId) {
			journalArticle.setStatusByUserId(anonymousUser.getUserId());
			journalArticle.setStatusByUserName(anonymousUser.getFullName());
		}

		journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	@Override
	public void delete(JournalArticle journalArticle) throws PortalException {
		journalArticleLocalService.deleteArticle(journalArticle);
	}

	@Override
	public Class<JournalArticle> getTypeClass() {
		return JournalArticle.class;
	}

	protected void autoAnonymizeAssetEntry(
		JournalArticle journalArticle, User anonymousUser) {

		AssetEntry assetEntry = fetchAssetEntry(journalArticle);

		if (assetEntry != null) {
			assetEntry.setUserId(anonymousUser.getUserId());
			assetEntry.setUserName(anonymousUser.getFullName());

			assetEntryLocalService.updateAssetEntry(assetEntry);
		}
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return journalArticleLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return JournalUADConstants.USER_ID_FIELD_NAMES_JOURNAL_ARTICLE;
	}

	protected AssetEntry fetchAssetEntry(JournalArticle journalArticle) {
		return assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), journalArticle.getId());
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected JournalArticleLocalService journalArticleLocalService;

}