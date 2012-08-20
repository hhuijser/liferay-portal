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

package com.liferay.portlet.bookmarks.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.bookmarks.EntryURLException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.base.BookmarksEntryLocalServiceBaseImpl;
import com.liferay.portlet.bookmarks.social.BookmarksActivityKeys;
import com.liferay.portlet.bookmarks.util.BookmarksUtil;
import com.liferay.portlet.bookmarks.util.comparator.EntryModifiedDateComparator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class BookmarksEntryLocalServiceImpl
	extends BookmarksEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public BookmarksEntry addEntry(
			long userId, long groupId, long folderId, String name, String url,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(name)) {
			name = url;
		}

		Date now = new Date();

		validate(url);

		long entryId = counterLocalService.increment();

		BookmarksEntry entry = bookmarksEntryPersistence.create(entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(serviceContext.getCreateDate(now));
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setFolderId(folderId);
		entry.setName(name);
		entry.setUrl(url);
		entry.setDescription(description);
		entry.setExpandoBridgeAttributes(serviceContext);

		bookmarksEntryPersistence.update(entry, false);

		// Resources

		resourceLocalService.addModelResources(entry, serviceContext);

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getName());

		socialActivityLocalService.addActivity(
			userId, groupId, BookmarksEntry.class.getName(), entryId,
			BookmarksActivityKeys.ADD_ENTRY, extraDataJSONObject.toString(), 0);

		// Subscriptions

		notifySubscribers(entry, serviceContext);

		return entry;
	}

	public void deleteEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		List<BookmarksEntry> entries = bookmarksEntryPersistence.findByG_F(
			groupId, folderId);

		for (BookmarksEntry entry : entries) {
			bookmarksEntryLocalService.deleteEntry(entry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	public BookmarksEntry deleteEntry(BookmarksEntry entry)
		throws PortalException, SystemException {

		// Entry

		bookmarksEntryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry, ResourceConstants.SCOPE_INDIVIDUAL);

		// Asset

		assetEntryLocalService.deleteEntry(
			BookmarksEntry.class.getName(), entry.getEntryId());

		// Expando

		expandoValueLocalService.deleteValues(
			BookmarksEntry.class.getName(), entry.getEntryId());

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			entry.getCompanyId(), BookmarksEntry.class.getName(),
			entry.getEntryId());

		return entry;
	}

	@Indexable(type = IndexableType.DELETE)
	public BookmarksEntry deleteEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		return deleteEntry(entry);
	}

	public List<BookmarksEntry> getEntries(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return bookmarksEntryPersistence.findByG_F(
			groupId, folderId, start, end);
	}

	public List<BookmarksEntry> getEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return bookmarksEntryPersistence.findByG_F(
			groupId, folderId, start, end, orderByComparator);
	}

	public int getEntriesCount(long groupId, long folderId)
		throws SystemException {

		return bookmarksEntryPersistence.countByG_F(groupId, folderId);
	}

	public BookmarksEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return bookmarksEntryPersistence.findByPrimaryKey(entryId);
	}

	public int getFoldersEntriesCount(long groupId, List<Long> folderIds)
		throws SystemException {

		return bookmarksEntryPersistence.countByG_F(
			groupId,
			ArrayUtil.toArray(folderIds.toArray(new Long[folderIds.size()])));
	}

	public List<BookmarksEntry> getGroupEntries(
			long groupId, int start, int end)
		throws SystemException {

		return bookmarksEntryPersistence.findByGroupId(
			groupId, start, end, new EntryModifiedDateComparator());
	}

	public List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		OrderByComparator orderByComparator = new EntryModifiedDateComparator();

		if (userId <= 0) {
			return bookmarksEntryPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}
		else {
			return bookmarksEntryPersistence.findByG_U(
				groupId, userId, start, end, orderByComparator);
		}
	}

	public int getGroupEntriesCount(long groupId) throws SystemException {
		return bookmarksEntryPersistence.countByGroupId(groupId);
	}

	public int getGroupEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return bookmarksEntryPersistence.countByGroupId(groupId);
		}
		else {
			return bookmarksEntryPersistence.countByG_U(groupId, userId);
		}
	}

	public List<BookmarksEntry> getNoAssetEntries() throws SystemException {
		return bookmarksEntryFinder.findByNoAssets();
	}

	public BookmarksEntry openEntry(long userId, long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		entry.setVisits(entry.getVisits() + 1);

		bookmarksEntryPersistence.update(entry, false);

		assetEntryLocalService.incrementViewCounter(
			userId, BookmarksEntry.class.getName(), entryId, 1);

		return entry;
	}

	public void subscribeEntry(long userId, long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		subscriptionLocalService.addSubscription(
			userId, entry.getGroupId(), BookmarksEntry.class.getName(),
			entryId);
	}

	public void unsubscribeEntry(long userId, long entryId)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(
			userId, BookmarksEntry.class.getName(), entryId);
	}

	public void updateAsset(
			long userId, BookmarksEntry entry, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, entry.getGroupId(), BookmarksEntry.class.getName(),
			entry.getEntryId(), entry.getUuid(), 0, assetCategoryIds,
			assetTagNames, true, null, null, null, ContentTypes.TEXT_PLAIN,
			entry.getName(), entry.getDescription(), null, entry.getUrl(), null,
			0, 0, null, false);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Indexable(type = IndexableType.REINDEX)
	public BookmarksEntry updateEntry(
			long userId, long entryId, long groupId, long folderId, String name,
			String url, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		if (Validator.isNull(name)) {
			name = url;
		}

		validate(url);

		entry.setModifiedDate(serviceContext.getModifiedDate(null));
		entry.setFolderId(folderId);
		entry.setName(name);
		entry.setUrl(url);
		entry.setDescription(description);
		entry.setExpandoBridgeAttributes(serviceContext);

		bookmarksEntryPersistence.update(entry, false);

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getName());

		socialActivityLocalService.addActivity(
			userId, entry.getGroupId(), BookmarksEntry.class.getName(), entryId,
			BookmarksActivityKeys.UPDATE_ENTRY, extraDataJSONObject.toString(),
			0);

		// Subscriptions

		notifySubscribers(entry, serviceContext);

		return entry;
	}

	protected long getFolder(BookmarksEntry entry, long folderId)
		throws SystemException {

		if ((entry.getFolderId() != folderId) &&
			(folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			BookmarksFolder newFolder =
				bookmarksFolderPersistence.fetchByPrimaryKey(folderId);

			if ((newFolder == null) ||
				(entry.getGroupId() != newFolder.getGroupId())) {

				folderId = entry.getFolderId();
			}
		}

		return folderId;
	}

	protected void notifySubscribers(
			BookmarksEntry entry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String layoutFullURL = serviceContext.getLayoutFullURL();

		if (Validator.isNull(layoutFullURL)) {
			return;
		}

		PortletPreferences preferences =
			ServiceContextUtil.getPortletPreferences(serviceContext);

		if (preferences == null) {
			long ownerId = entry.getGroupId();
			int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			long plid = PortletKeys.PREFS_PLID_SHARED;
			String portletId = PortletKeys.BOOKMARKS;
			String defaultPreferences = null;

			preferences = portletPreferencesLocalService.getPreferences(
				entry.getCompanyId(), ownerId, ownerType, plid, portletId,
				defaultPreferences);
		}

		if ((serviceContext.isCommandAdd() &&
			 !BookmarksUtil.getEmailEntryAddedEnabled(preferences)) ||
			(serviceContext.isCommandUpdate() &&
			 !BookmarksUtil.getEmailEntryUpdatedEnabled(preferences))) {

			return;
		}

		String statusByUserName = StringPool.BLANK;

		try {
			User user = userLocalService.getUserById(
				serviceContext.getGuestOrUserId());

			statusByUserName = user.getFullName();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		String entryURL =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "bookmarks" +
				StringPool.SLASH + entry.getEntryId();

		String fromAddress = BookmarksUtil.getEmailFromAddress(
			preferences, entry.getCompanyId());
		String fromName = BookmarksUtil.getEmailFromName(
			preferences, entry.getCompanyId());

		Map<Locale, String> localizedSubjectMap = null;
		Map<Locale, String> localizedBodyMap = null;

		if (serviceContext.isCommandUpdate()) {
			localizedSubjectMap = BookmarksUtil.getEmailEntryUpdatedSubjectMap(
				preferences);
			localizedBodyMap = BookmarksUtil.getEmailEntryUpdatedBodyMap(
				preferences);
		}
		else {
			localizedSubjectMap = BookmarksUtil.getEmailEntryAddedSubjectMap(
				preferences);
			localizedBodyMap = BookmarksUtil.getEmailEntryAddedBodyMap(
				preferences);
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(entry.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$BOOKMARKS_ENTRY_STATUS_BY_USER_NAME$]", statusByUserName,
			"[$BOOKMARKS_ENTRY_URL$]", entryURL);
		subscriptionSender.setContextUserPrefix("BOOKMARKS_ENTRY");
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(localizedBodyMap);
		subscriptionSender.setLocalizedSubjectMap(localizedSubjectMap);
		subscriptionSender.setMailId("bookmarks_entry", entry.getEntryId());
		subscriptionSender.setPortletId(PortletKeys.BOOKMARKS);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(entry.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setUserId(entry.getUserId());

		subscriptionSender.addPersistedSubscribers(
			BookmarksEntry.class.getName(), entry.getEntryId());

		BookmarksFolder folder = entry.getFolder();

		if (folder.getFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			subscriptionSender.addPersistedSubscribers(
				BookmarksFolder.class.getName(), folder.getFolderId());

			for (BookmarksFolder ancestor : folder.getAncestors()) {
				subscriptionSender.addPersistedSubscribers(
					BookmarksFolder.class.getName(), ancestor.getFolderId());
			}
		}

		subscriptionSender.addPersistedSubscribers(
			BookmarksFolder.class.getName(), folder.getGroupId());

		subscriptionSender.flushNotificationsAsync();
	}

	protected void validate(String url) throws PortalException {
		if (!Validator.isUrl(url)) {
			throw new EntryURLException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BookmarksEntryLocalServiceImpl.class);

}