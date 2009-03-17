/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.util.Indexer;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.tags.model.TagsEntryConstants;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * For DLFileEntries, the naming convention for some of the variables is not
 * very informative, due to legacy code. Each DLFileEntry has a corresponding
 * name and title. The "name" is a unique identifier for a given file and
 * usually follows the format "DLFE-1234.xls" whereas the "title" is the actual
 * name specified by the user (e.g., "Budget.xls").
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 *
 */
public class DLFileEntryLocalServiceImpl
	extends DLFileEntryLocalServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long userId, long folderId, String name, String title,
			String description, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addFileEntry(
			null, userId, folderId, name, title, description,
			extraSettings, bytes, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long userId, long folderId, String name, String title,
			String description, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.WEBDAV_LITMUS) {
			if (file == null) {
				throw new FileSizeException();
			}
		}

		InputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(file));

			return addFileEntry(
				null, userId, folderId, name, title, description,
				extraSettings, is, file.length(), serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
		finally {
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}
	}

	public DLFileEntry addFileEntry(
			String uuid, long userId, long folderId, String name, String title,
			String description, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.WEBDAV_LITMUS) {
			if ((bytes == null) || (bytes.length == 0)) {
				throw new FileSizeException();
			}
		}

		InputStream is = new ByteArrayInputStream(bytes);

		return addFileEntry(
			uuid, userId, folderId, name, title, description, extraSettings, is,
			bytes.length, serviceContext);
	}

	public DLFileEntry addFileEntry(
			String uuid, long userId, long folderId, String name, String title,
			String description, String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);
		Date now = new Date();

		if (Validator.isNull(title)) {
			title = name;
		}

		name = getName(name);
		title = DLFileEntryImpl.stripExtension(name, title);

		validate(folder.getGroupId(), folderId, name, title, is);

		long fileEntryId = counterLocalService.increment();

		DLFileEntry fileEntry = dlFileEntryPersistence.create(fileEntryId);

		fileEntry.setUuid(uuid);
		fileEntry.setCompanyId(user.getCompanyId());
		fileEntry.setUserId(user.getUserId());
		fileEntry.setUserName(user.getFullName());
		fileEntry.setVersionUserId(user.getUserId());
		fileEntry.setVersionUserName(user.getFullName());
		fileEntry.setCreateDate(now);
		fileEntry.setModifiedDate(now);
		fileEntry.setFolderId(folderId);
		fileEntry.setName(name);
		fileEntry.setTitle(title);
		fileEntry.setDescription(description);
		fileEntry.setVersion(DLFileEntryImpl.DEFAULT_VERSION);
		fileEntry.setSize((int)size);
		fileEntry.setReadCount(DLFileEntryImpl.DEFAULT_READ_COUNT);
		fileEntry.setExtraSettings(extraSettings);

		dlFileEntryPersistence.update(fileEntry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
				serviceContext.getAddGuestPermissions()) {

			addFileEntryResources(
				folder, fileEntry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFileEntryResources(
				folder, fileEntry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Expando

		ExpandoBridge expandoBridge = fileEntry.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// File

		dlLocalService.addFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			folder.getGroupId(), folderId, name, fileEntryId,
			fileEntry.getLuceneProperties(), fileEntry.getModifiedDate(),
			serviceContext.getTagsCategories(), serviceContext.getTagsEntries(),
			is);

		// Social

		socialActivityLocalService.addActivity(
			userId, folder.getGroupId(), DLFileEntry.class.getName(),
			fileEntryId, DLActivityKeys.ADD_FILE_ENTRY, StringPool.BLANK, 0);

		// Tags

		updateTagsAsset(
			userId, fileEntry, serviceContext.getTagsCategories(),
			serviceContext.getTagsEntries());

		// Folder

		folder.setLastPostDate(fileEntry.getModifiedDate());

		dlFolderPersistence.update(folder, false);

		return fileEntry;
	}

	public void addFileEntryResources(
			long folderId, String name, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);
		DLFileEntry fileEntry = dlFileEntryPersistence.findByF_N(
			folderId, name);

		addFileEntryResources(
			folder, fileEntry, addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(
			DLFolder folder, DLFileEntry fileEntry,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			fileEntry.getCompanyId(), folder.getGroupId(),
			fileEntry.getUserId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFileEntryResources(
			long folderId, String name, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);
		DLFileEntry fileEntry = dlFileEntryPersistence.findByF_N(
			folderId, name);

		addFileEntryResources(
			folder, fileEntry, communityPermissions, guestPermissions);
	}

	public void addFileEntryResources(
			DLFolder folder, DLFileEntry fileEntry,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			fileEntry.getCompanyId(), folder.getGroupId(),
			fileEntry.getUserId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), communityPermissions, guestPermissions);
	}

	public DLFileEntry addOrOverwriteFileEntry(
			long userId, long folderId, String name, String sourceName,
			String title, String description, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean update = false;

		String extension = FileUtil.getExtension(name);

		List<DLFileEntry> fileEntries = dlFileEntryPersistence.findByF_T(
			folderId, title);

		for (DLFileEntry fileEntry : fileEntries) {
			String curExtension = FileUtil.getExtension(fileEntry.getName());

			if (PropsValues.WEBDAV_LITMUS && Validator.isNull(extension)) {
				if (Validator.isNull(curExtension)) {
					update = true;

					name = fileEntry.getName();

					break;
				}
			}
			else if (extension.equals(curExtension)) {
				update = true;

				break;
			}
		}

		if (update) {
			return updateFileEntry(
				userId, folderId, folderId, name, sourceName, title,
				description, extraSettings, file, serviceContext);
		}
		else {
			return addFileEntry(
				userId, folderId, name, title, description,
				extraSettings, file, serviceContext);
		}
	}

	public void deleteFileEntries(long folderId)
		throws PortalException, SystemException {

		List<DLFileEntry> fileEntries = dlFileEntryPersistence.findByFolderId(
			folderId);

		for (DLFileEntry fileEntry : fileEntries) {
			deleteFileEntry(fileEntry);
		}
	}

	public void deleteFileEntry(long folderId, String name)
		throws PortalException, SystemException {

		deleteFileEntry(folderId, name, -1);
	}

	public void deleteFileEntry(long folderId, String name, double version)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByF_N(
			folderId, name);

		if (version > 0) {
			try {
				dlService.deleteFile(
					fileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					fileEntry.getFolderId(), fileEntry.getName(), version);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			dlFileVersionPersistence.removeByF_N_V(folderId, name, version);
		}
		else {
			deleteFileEntry(fileEntry);
		}
	}

	public void deleteFileEntry(DLFileEntry fileEntry)
		throws PortalException, SystemException {

		// File

		try {
			dlService.deleteFile(
				fileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				fileEntry.getFolderId(), fileEntry.getName());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		// File ranks

		dlFileRankLocalService.deleteFileRanks(
			fileEntry.getFolderId(), fileEntry.getName());

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(
			fileEntry.getFolderId(), fileEntry.getName());

		// File versions

		List<DLFileVersion> fileVersions = dlFileVersionPersistence.findByF_N(
			fileEntry.getFolderId(), fileEntry.getName());

		for (DLFileVersion fileVersion : fileVersions) {
			dlFileVersionPersistence.remove(fileVersion);
		}

		// Expando

		expandoValueLocalService.deleteValues(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Tags

		tagsAssetLocalService.deleteAsset(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Social

		socialActivityLocalService.deleteActivities(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFileEntry.class.getName(), fileEntry.getPrimaryKey());

		// Resources

		resourceLocalService.deleteResource(
			fileEntry.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, fileEntry.getFileEntryId());

		// File entry

		dlFileEntryPersistence.remove(fileEntry);
	}

	public List<DLFileEntry> getCompanyFileEntries(
			long companyId, int start, int end)
		throws SystemException {

		return dlFileEntryPersistence.findByCompanyId(companyId, start, end);
	}

	public List<DLFileEntry> getCompanyFileEntries(
			long companyId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByCompanyId(
			companyId, start, end, obc);
	}

	public int getCompanyFileEntriesCount(long companyId)
		throws SystemException {

		return dlFileEntryPersistence.countByCompanyId(companyId);
	}

	public InputStream getFileAsStream(
			long companyId, long userId, long folderId, String name)
		throws PortalException, SystemException {

		return getFileAsStream(companyId, userId, folderId, name, 0);
	}

	public InputStream getFileAsStream(
			long companyId, long userId, long folderId, String name,
			double version)
		throws PortalException, SystemException {

		if (userId > 0) {
			DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

			dlFileRankLocalService.updateFileRank(
				folder.getGroupId(), companyId, userId, folderId, name);
		}

		DLFileEntry fileEntry = dlFileEntryPersistence.findByF_N(
			folderId, name);

		fileEntry.setReadCount(fileEntry.getReadCount() + 1);

		dlFileEntryPersistence.update(fileEntry, false);

		if ((version > 0) && (fileEntry.getVersion() != version)) {
			return dlLocalService.getFileAsStream(
				companyId, folderId, name, version);
		}
		else {
			return dlLocalService.getFileAsStream(companyId, folderId, name);
		}
	}

	public List<DLFileEntry> getFileEntries(long folderId)
		throws SystemException {

		return dlFileEntryPersistence.findByFolderId(folderId);
	}

	public List<DLFileEntry> getFileEntries(long folderId, int start, int end)
		throws SystemException {

		return dlFileEntryPersistence.findByFolderId(folderId, start, end);
	}

	public List<DLFileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByFolderId(folderId, start, end, obc);
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		return dlFileEntryPersistence.countByFolderId(folderId);
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	public DLFileEntry getFileEntry(long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByF_N(folderId, name);
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		return dlFileEntryFinder.findByUuid_G(uuid, groupId);
	}

	public DLFileEntry getFileEntryByTitle(
			long folderId, String titleWithExtension)
		throws PortalException, SystemException {

		String title = DLFileEntryImpl.stripExtension(
			titleWithExtension, titleWithExtension);
		String extension = FileUtil.getExtension(titleWithExtension);

		List<DLFileEntry> fileEntries = dlFileEntryPersistence.findByF_T(
			folderId, title);

		for (DLFileEntry fileEntry : fileEntries) {
			String curExtension = FileUtil.getExtension(fileEntry.getName());

			if (PropsValues.WEBDAV_LITMUS && Validator.isNull(extension)) {
				if (Validator.isNull(curExtension)) {
					return fileEntry;
				}
			}
			else if (extension.equals(curExtension)) {
				return fileEntry;
			}
		}

		throw new NoSuchFileEntryException();
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds)
		throws SystemException {

		return dlFileEntryFinder.countByFolderIds(folderIds);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end)
		throws SystemException {

		return dlFileEntryFinder.findByGroupId(groupId, start, end);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryFinder.findByGroupId(groupId, start, end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryFinder.findByGroupId(groupId, start, end);
		}
		else {
			return dlFileEntryFinder.findByG_U(groupId, userId, start, end);
		}
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryFinder.findByGroupId(groupId, start, end, obc);
		}
		else {
			return dlFileEntryFinder.findByG_U(
				groupId, userId, start, end, obc);
		}
	}

	public int getGroupFileEntriesCount(long groupId) throws SystemException {
		return dlFileEntryFinder.countByGroupId(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryFinder.countByGroupId(groupId);
		}
		else {
			return dlFileEntryFinder.countByG_U(groupId, userId);
		}
	}

	public List<DLFileEntry> getNoAssetFileEntries() throws SystemException {
		return dlFileEntryFinder.findByNoAssets();
	}

	public void reIndex(long fileEntryId) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		DLFileEntry fileEntry = dlFileEntryPersistence.fetchByPrimaryKey(
			fileEntryId);

		if (fileEntry == null) {
			return;
		}

		DLFolder folder = fileEntry.getFolder();

		long companyId = fileEntry.getCompanyId();
		String portletId = PortletKeys.DOCUMENT_LIBRARY;
		long groupId = folder.getGroupId();
		long folderId = folder.getFolderId();
		String fileName = fileEntry.getName();
		String properties = fileEntry.getLuceneProperties();
		Date modifiedDate = fileEntry.getModifiedDate();

		String[] tagsCategories = tagsEntryLocalService.getEntryNames(
			DLFileEntry.class.getName(), fileEntryId,
			TagsEntryConstants.FOLKSONOMY_CATEGORY);
		String[] tagsEntries = tagsEntryLocalService.getEntryNames(
			DLFileEntry.class.getName(), fileEntryId);

		try {
			Indexer.updateFile(
				companyId, portletId, groupId, folderId, fileName, fileEntryId,
				properties, modifiedDate, tagsCategories, tagsEntries);
		}
		catch (SearchException se) {
			_log.error("Reindexing " + fileEntryId, se);
		}
	}

	public DLFileEntry updateFileEntry(
			long userId, long folderId, long newFolderId, String name,
			String sourceFileName, String title, String description,
			String extraSettings, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;

		try {
			long size = 0;

			if ((file != null) && (file.length() > 0)) {
				is = new BufferedInputStream(new FileInputStream(file));
				size = file.length();
			}

			return updateFileEntry(
				userId, folderId, newFolderId, name, sourceFileName, title,
				description, extraSettings, is, size, serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
		finally {
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}
	}

	public DLFileEntry updateFileEntry(
			long userId, long folderId, long newFolderId, String name,
			String sourceFileName, String title, String description,
			String extraSettings, byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		if ((bytes != null) && (bytes.length > 0)) {
			is = new ByteArrayInputStream(bytes);
			size = bytes.length;
		}

		return updateFileEntry(
			userId, folderId, newFolderId, name, sourceFileName, title,
			description, extraSettings, is, size, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long userId, long folderId, long newFolderId, String name,
			String sourceFileName, String title, String description,
			String extraSettings, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

		if (Validator.isNull(title)) {
			title = sourceFileName;

			if (Validator.isNull(title)) {
				title = name;
			}
		}

		title = DLFileEntryImpl.stripExtension(name, title);

		validate(
			folder.getGroupId(), folderId, newFolderId, name, title,
			sourceFileName, is);

		DLFileEntry fileEntry = dlFileEntryPersistence.findByF_N(
			folderId, name);

		fileEntry.setTitle(title);
		fileEntry.setDescription(description);
		fileEntry.setExtraSettings(extraSettings);

		dlFileEntryPersistence.update(fileEntry, false);

		// Move file entry

		if ((newFolderId > 0) && (folderId != newFolderId)) {
			long oldFileEntryId = fileEntry.getFileEntryId();

			DLFolder newFolder = dlFolderPersistence.findByPrimaryKey(
				newFolderId);

			if (folder.getGroupId() != newFolder.getGroupId()) {
				throw new NoSuchFolderException();
			}

			if (dlLocalService.hasFile(
					user.getCompanyId(), newFolderId, name, 0)) {

				throw new DuplicateFileException(name);
			}

			long newFileEntryId = counterLocalService.increment();

			DLFileEntry newFileEntry = dlFileEntryPersistence.create(
				newFileEntryId);

			newFileEntry.setCompanyId(fileEntry.getCompanyId());
			newFileEntry.setUserId(fileEntry.getUserId());
			newFileEntry.setUserName(fileEntry.getUserName());
			newFileEntry.setVersionUserId(fileEntry.getVersionUserId());
			newFileEntry.setVersionUserName(fileEntry.getVersionUserName());
			newFileEntry.setCreateDate(fileEntry.getCreateDate());
			newFileEntry.setModifiedDate(fileEntry.getModifiedDate());
			newFileEntry.setFolderId(newFolderId);
			newFileEntry.setName(name);
			newFileEntry.setTitle(fileEntry.getTitle());
			newFileEntry.setDescription(fileEntry.getDescription());
			newFileEntry.setVersion(fileEntry.getVersion());
			newFileEntry.setSize(fileEntry.getSize());
			newFileEntry.setReadCount(fileEntry.getReadCount());
			newFileEntry.setExtraSettings(extraSettings);

			dlFileEntryPersistence.update(newFileEntry, false);

			dlFileEntryPersistence.remove(fileEntry);

			List<DLFileVersion> fileVersions =
				dlFileVersionPersistence.findByF_N(folderId, name);

			for (DLFileVersion fileVersion : fileVersions) {
				long newFileVersionId = counterLocalService.increment();

				DLFileVersion newFileVersion = dlFileVersionPersistence.create(
					newFileVersionId);

				newFileVersion.setCompanyId(fileVersion.getCompanyId());
				newFileVersion.setUserId(fileVersion.getUserId());
				newFileVersion.setUserName(fileVersion.getUserName());
				newFileVersion.setCreateDate(fileVersion.getCreateDate());
				newFileVersion.setFolderId(newFolderId);
				newFileVersion.setName(name);
				newFileVersion.setVersion(fileVersion.getVersion());
				newFileVersion.setSize(fileVersion.getSize());

				dlFileVersionPersistence.update(newFileVersion, false);

				dlFileVersionPersistence.remove(fileVersion);
			}

			dlFileShortcutLocalService.updateFileShortcuts(
				folderId, name, newFolderId, name);

			// Resources

			Resource resource = resourceLocalService.getResource(
				fileEntry.getCompanyId(), DLFileEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(fileEntry.getPrimaryKey()));

			resource.setPrimKey(String.valueOf(newFileEntryId));

			resourcePersistence.update(resource, false);

			// File

			dlService.updateFile(
				user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				folder.getGroupId(), folderId, newFolderId, name,
				newFileEntryId);

			// Ratings

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntry.class.getName());

			RatingsStats stats = ratingsStatsPersistence.fetchByC_C(
				classNameId, oldFileEntryId);

			stats.setClassPK(newFileEntryId);

			ratingsStatsPersistence.update(stats, false);

			List<RatingsEntry> entries = ratingsEntryPersistence.findByC_C(
				classNameId, oldFileEntryId);

			for (RatingsEntry entry : entries) {
				entry.setClassPK(newFileEntryId);

				ratingsEntryPersistence.update(entry, false);
			}

			// Message boards

			MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
				classNameId, oldFileEntryId);

			if (discussion != null) {
				discussion.setClassPK(newFileEntryId);

				mbDiscussionPersistence.update(discussion, false);
			}

			// Expando

			expandoValueLocalService.deleteValues(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			// Social

			socialActivityLocalService.deleteActivities(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			// Tags

			tagsAssetLocalService.deleteAsset(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			folderId = newFolderId;
			folder = newFolder;
			fileEntry = newFileEntry;
		}

		// Expando

		ExpandoBridge expandoBridge = fileEntry.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Social

		socialActivityLocalService.addActivity(
			userId, folder.getGroupId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), DLActivityKeys.UPDATE_FILE_ENTRY,
			StringPool.BLANK, 0);

		// Tags

		updateTagsAsset(
			userId, fileEntry, serviceContext.getTagsCategories(),
			serviceContext.getTagsEntries());

		// File version

		double oldVersion = fileEntry.getVersion();
		double newVersion = MathUtil.format(oldVersion + 0.1, 1, 1);

		if (is == null) {
			fileEntry.setVersion(newVersion);

			dlFileEntryPersistence.update(fileEntry, false);

			is = dlLocalService.getFileAsStream(
				user.getCompanyId(), folderId, name);

			dlLocalService.updateFile(
				user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				folder.getGroupId(), folderId, name, newVersion, name,
				fileEntry.getFileEntryId(), fileEntry.getLuceneProperties(),
				fileEntry.getModifiedDate(), serviceContext.getTagsCategories(),
				serviceContext.getTagsEntries(), is);

			return fileEntry;
		}

		long fileVersionId = counterLocalService.increment();

		DLFileVersion fileVersion = dlFileVersionPersistence.create(
			fileVersionId);

		long versionUserId = fileEntry.getVersionUserId();

		if (versionUserId <= 0) {
			versionUserId = fileEntry.getUserId();
		}

		String versionUserName = GetterUtil.getString(
			fileEntry.getVersionUserName(), fileEntry.getUserName());

		fileVersion.setCompanyId(fileEntry.getCompanyId());
		fileVersion.setUserId(versionUserId);
		fileVersion.setUserName(versionUserName);
		fileVersion.setCreateDate(fileEntry.getModifiedDate());
		fileVersion.setFolderId(folderId);
		fileVersion.setName(name);
		fileVersion.setVersion(oldVersion);
		fileVersion.setSize(fileEntry.getSize());

		dlFileVersionPersistence.update(fileVersion, false);

		// File entry

		fileEntry.setVersionUserId(user.getUserId());
		fileEntry.setVersionUserName(user.getFullName());
		fileEntry.setModifiedDate(new Date());
		fileEntry.setVersion(newVersion);
		fileEntry.setSize((int)size);

		dlFileEntryPersistence.update(fileEntry, false);

		// File

		dlLocalService.updateFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			folder.getGroupId(), folderId, name, newVersion, sourceFileName,
			fileEntry.getFileEntryId(), fileEntry.getLuceneProperties(),
			fileEntry.getModifiedDate(), serviceContext.getTagsCategories(),
			serviceContext.getTagsEntries(), is);

		// Folder

		folder.setLastPostDate(fileEntry.getModifiedDate());

		dlFolderPersistence.update(folder, false);

		return fileEntry;
	}

	public void updateTagsAsset(
			long userId, DLFileEntry fileEntry, String[] tagsCategories,
			String[] tagsEntries)
		throws PortalException, SystemException {

		String mimeType = MimeTypesUtil.getContentType(fileEntry.getName());

		tagsAssetLocalService.updateAsset(
			userId, fileEntry.getFolder().getGroupId(),
			DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
			tagsCategories, tagsEntries, true, null, null, null, null, mimeType,
			fileEntry.getTitle(), fileEntry.getDescription(), null, null, 0, 0,
			null, false);
	}

	protected long getFolderId(long companyId, long folderId)
		throws SystemException {

		if (folderId != DLFolderImpl.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder folder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((folder == null) || (companyId != folder.getCompanyId())) {
				folderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	protected String getName(String name) throws SystemException {
		String extension = StringPool.BLANK;

		int pos = name.lastIndexOf(StringPool.PERIOD);

		if (pos != -1) {
			extension = name.substring(pos + 1, name.length()).toLowerCase();
		}

		name = String.valueOf(counterLocalService.increment(
			DLFileEntry.class.getName()));

		if (Validator.isNotNull(extension)) {
			name = "DLFE-" + name + StringPool.PERIOD + extension;
		}

		return name;
	}

	protected void validate(
			long groupId, long folderId, long newFolderId, String name,
			String title, String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		if (Validator.isNotNull(sourceFileName)) {
			dlLocalService.validate(name, sourceFileName, is);
		}

		if (newFolderId > 0 && (folderId != newFolderId)) {
			folderId = newFolderId;
		}

		String extension = FileUtil.getExtension(name);

		try {
			String titleWithExtension = title;

			if (Validator.isNotNull(extension)) {
				titleWithExtension += StringPool.PERIOD + extension;
			}

			dlFolderLocalService.getFolder(
				groupId, folderId, titleWithExtension);

			throw new DuplicateFolderNameException();
		}
		catch (NoSuchFolderException nsfe) {
		}

		List<DLFileEntry> fileEntries = dlFileEntryPersistence.findByF_T(
			folderId, title);

		for (DLFileEntry fileEntry : fileEntries) {
			if (!name.equals(fileEntry.getName())) {
				String curExtension = FileUtil.getExtension(
					fileEntry.getName());

				if (PropsValues.WEBDAV_LITMUS && Validator.isNull(extension)) {
					if (Validator.isNull(curExtension)) {
						throw new DuplicateFileException(
							fileEntry.getTitleWithExtension());
					}
				}
				else if (extension.equals(curExtension)) {
					throw new DuplicateFileException(
						fileEntry.getTitleWithExtension());
				}
			}
		}
	}

	protected void validate(
			long groupId, long folderId, String name, String title,
			InputStream is)
		throws PortalException, SystemException {

		dlLocalService.validate(name, is);

		String extension = FileUtil.getExtension(name);

		try {
			String titleWithExtension = title;

			if (Validator.isNotNull(extension)) {
				titleWithExtension += StringPool.PERIOD + extension;
			}

			dlFolderLocalService.getFolder(
				groupId, folderId, titleWithExtension);

			throw new DuplicateFolderNameException();
		}
		catch (NoSuchFolderException nsfe) {
		}

		List<DLFileEntry> fileEntries = dlFileEntryPersistence.findByF_T(
			folderId, title);

		for (DLFileEntry fileEntry : fileEntries) {
			String curExtension = FileUtil.getExtension(fileEntry.getName());

			if (PropsValues.WEBDAV_LITMUS && Validator.isNull(extension)) {
				if (Validator.isNull(curExtension)) {
					throw new DuplicateFileException(
						fileEntry.getTitleWithExtension());
				}
			}
			else if (extension.equals(curExtension)) {
				throw new DuplicateFileException(
					fileEntry.getTitleWithExtension());
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(DLFileEntryLocalServiceImpl.class);

}