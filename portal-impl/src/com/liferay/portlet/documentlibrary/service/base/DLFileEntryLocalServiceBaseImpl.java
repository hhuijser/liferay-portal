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

package com.liferay.portlet.documentlibrary.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.documentlibrary.service.DLLocalService;
import com.liferay.documentlibrary.service.DLService;

import com.liferay.lock.service.LockService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.WebDAVPropsLocalService;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.ExpandoValueService;
import com.liferay.portlet.expando.service.persistence.ExpandoValueFinder;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.RatingsEntryLocalService;
import com.liferay.portlet.ratings.service.RatingsEntryService;
import com.liferay.portlet.ratings.service.RatingsStatsLocalService;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.tags.service.TagsAssetLocalService;
import com.liferay.portlet.tags.service.TagsAssetService;
import com.liferay.portlet.tags.service.TagsEntryLocalService;
import com.liferay.portlet.tags.service.TagsEntryService;
import com.liferay.portlet.tags.service.persistence.TagsAssetFinder;
import com.liferay.portlet.tags.service.persistence.TagsAssetPersistence;
import com.liferay.portlet.tags.service.persistence.TagsEntryFinder;
import com.liferay.portlet.tags.service.persistence.TagsEntryPersistence;

import java.util.List;

/**
 * <a href="DLFileEntryLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class DLFileEntryLocalServiceBaseImpl
	implements DLFileEntryLocalService {
	public DLFileEntry addDLFileEntry(DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntry.setNew(true);

		return dlFileEntryPersistence.update(dlFileEntry, false);
	}

	public DLFileEntry createDLFileEntry(long fileEntryId) {
		return dlFileEntryPersistence.create(fileEntryId);
	}

	public void deleteDLFileEntry(long fileEntryId)
		throws PortalException, SystemException {
		dlFileEntryPersistence.remove(fileEntryId);
	}

	public void deleteDLFileEntry(DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntryPersistence.remove(dlFileEntry);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return dlFileEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return dlFileEntryPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	public DLFileEntry getDLFileEntry(long fileEntryId)
		throws PortalException, SystemException {
		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	public List<DLFileEntry> getDLFileEntries(int start, int end)
		throws SystemException {
		return dlFileEntryPersistence.findAll(start, end);
	}

	public int getDLFileEntriesCount() throws SystemException {
		return dlFileEntryPersistence.countAll();
	}

	public DLFileEntry updateDLFileEntry(DLFileEntry dlFileEntry)
		throws SystemException {
		dlFileEntry.setNew(false);

		return dlFileEntryPersistence.update(dlFileEntry, true);
	}

	public DLFileEntry updateDLFileEntry(DLFileEntry dlFileEntry, boolean merge)
		throws SystemException {
		dlFileEntry.setNew(false);

		return dlFileEntryPersistence.update(dlFileEntry, merge);
	}

	public DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	public DLFileEntryService getDLFileEntryService() {
		return dlFileEntryService;
	}

	public void setDLFileEntryService(DLFileEntryService dlFileEntryService) {
		this.dlFileEntryService = dlFileEntryService;
	}

	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	public DLFileEntryFinder getDLFileEntryFinder() {
		return dlFileEntryFinder;
	}

	public void setDLFileEntryFinder(DLFileEntryFinder dlFileEntryFinder) {
		this.dlFileEntryFinder = dlFileEntryFinder;
	}

	public DLFileRankLocalService getDLFileRankLocalService() {
		return dlFileRankLocalService;
	}

	public void setDLFileRankLocalService(
		DLFileRankLocalService dlFileRankLocalService) {
		this.dlFileRankLocalService = dlFileRankLocalService;
	}

	public DLFileRankPersistence getDLFileRankPersistence() {
		return dlFileRankPersistence;
	}

	public void setDLFileRankPersistence(
		DLFileRankPersistence dlFileRankPersistence) {
		this.dlFileRankPersistence = dlFileRankPersistence;
	}

	public DLFileRankFinder getDLFileRankFinder() {
		return dlFileRankFinder;
	}

	public void setDLFileRankFinder(DLFileRankFinder dlFileRankFinder) {
		this.dlFileRankFinder = dlFileRankFinder;
	}

	public DLFileShortcutLocalService getDLFileShortcutLocalService() {
		return dlFileShortcutLocalService;
	}

	public void setDLFileShortcutLocalService(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		this.dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	public DLFileShortcutService getDLFileShortcutService() {
		return dlFileShortcutService;
	}

	public void setDLFileShortcutService(
		DLFileShortcutService dlFileShortcutService) {
		this.dlFileShortcutService = dlFileShortcutService;
	}

	public DLFileShortcutPersistence getDLFileShortcutPersistence() {
		return dlFileShortcutPersistence;
	}

	public void setDLFileShortcutPersistence(
		DLFileShortcutPersistence dlFileShortcutPersistence) {
		this.dlFileShortcutPersistence = dlFileShortcutPersistence;
	}

	public DLFileShortcutFinder getDLFileShortcutFinder() {
		return dlFileShortcutFinder;
	}

	public void setDLFileShortcutFinder(
		DLFileShortcutFinder dlFileShortcutFinder) {
		this.dlFileShortcutFinder = dlFileShortcutFinder;
	}

	public DLFileVersionLocalService getDLFileVersionLocalService() {
		return dlFileVersionLocalService;
	}

	public void setDLFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {
		this.dlFileVersionLocalService = dlFileVersionLocalService;
	}

	public DLFileVersionPersistence getDLFileVersionPersistence() {
		return dlFileVersionPersistence;
	}

	public void setDLFileVersionPersistence(
		DLFileVersionPersistence dlFileVersionPersistence) {
		this.dlFileVersionPersistence = dlFileVersionPersistence;
	}

	public DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	public DLFolderService getDLFolderService() {
		return dlFolderService;
	}

	public void setDLFolderService(DLFolderService dlFolderService) {
		this.dlFolderService = dlFolderService;
	}

	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	public void setDLFolderPersistence(DLFolderPersistence dlFolderPersistence) {
		this.dlFolderPersistence = dlFolderPersistence;
	}

	public DLFolderFinder getDLFolderFinder() {
		return dlFolderFinder;
	}

	public void setDLFolderFinder(DLFolderFinder dlFolderFinder) {
		this.dlFolderFinder = dlFolderFinder;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public DLLocalService getDLLocalService() {
		return dlLocalService;
	}

	public void setDLLocalService(DLLocalService dlLocalService) {
		this.dlLocalService = dlLocalService;
	}

	public DLService getDLService() {
		return dlService;
	}

	public void setDLService(DLService dlService) {
		this.dlService = dlService;
	}

	public LockService getLockService() {
		return lockService;
	}

	public void setLockService(LockService lockService) {
		this.lockService = lockService;
	}

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public WebDAVPropsLocalService getWebDAVPropsLocalService() {
		return webDAVPropsLocalService;
	}

	public void setWebDAVPropsLocalService(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		this.webDAVPropsLocalService = webDAVPropsLocalService;
	}

	public WebDAVPropsPersistence getWebDAVPropsPersistence() {
		return webDAVPropsPersistence;
	}

	public void setWebDAVPropsPersistence(
		WebDAVPropsPersistence webDAVPropsPersistence) {
		this.webDAVPropsPersistence = webDAVPropsPersistence;
	}

	public ExpandoValueLocalService getExpandoValueLocalService() {
		return expandoValueLocalService;
	}

	public void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {
		this.expandoValueLocalService = expandoValueLocalService;
	}

	public ExpandoValueService getExpandoValueService() {
		return expandoValueService;
	}

	public void setExpandoValueService(ExpandoValueService expandoValueService) {
		this.expandoValueService = expandoValueService;
	}

	public ExpandoValuePersistence getExpandoValuePersistence() {
		return expandoValuePersistence;
	}

	public void setExpandoValuePersistence(
		ExpandoValuePersistence expandoValuePersistence) {
		this.expandoValuePersistence = expandoValuePersistence;
	}

	public ExpandoValueFinder getExpandoValueFinder() {
		return expandoValueFinder;
	}

	public void setExpandoValueFinder(ExpandoValueFinder expandoValueFinder) {
		this.expandoValueFinder = expandoValueFinder;
	}

	public MBDiscussionLocalService getMBDiscussionLocalService() {
		return mbDiscussionLocalService;
	}

	public void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {
		this.mbDiscussionLocalService = mbDiscussionLocalService;
	}

	public MBDiscussionPersistence getMBDiscussionPersistence() {
		return mbDiscussionPersistence;
	}

	public void setMBDiscussionPersistence(
		MBDiscussionPersistence mbDiscussionPersistence) {
		this.mbDiscussionPersistence = mbDiscussionPersistence;
	}

	public MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	public MBMessageService getMBMessageService() {
		return mbMessageService;
	}

	public void setMBMessageService(MBMessageService mbMessageService) {
		this.mbMessageService = mbMessageService;
	}

	public MBMessagePersistence getMBMessagePersistence() {
		return mbMessagePersistence;
	}

	public void setMBMessagePersistence(
		MBMessagePersistence mbMessagePersistence) {
		this.mbMessagePersistence = mbMessagePersistence;
	}

	public MBMessageFinder getMBMessageFinder() {
		return mbMessageFinder;
	}

	public void setMBMessageFinder(MBMessageFinder mbMessageFinder) {
		this.mbMessageFinder = mbMessageFinder;
	}

	public RatingsEntryLocalService getRatingsEntryLocalService() {
		return ratingsEntryLocalService;
	}

	public void setRatingsEntryLocalService(
		RatingsEntryLocalService ratingsEntryLocalService) {
		this.ratingsEntryLocalService = ratingsEntryLocalService;
	}

	public RatingsEntryService getRatingsEntryService() {
		return ratingsEntryService;
	}

	public void setRatingsEntryService(RatingsEntryService ratingsEntryService) {
		this.ratingsEntryService = ratingsEntryService;
	}

	public RatingsEntryPersistence getRatingsEntryPersistence() {
		return ratingsEntryPersistence;
	}

	public void setRatingsEntryPersistence(
		RatingsEntryPersistence ratingsEntryPersistence) {
		this.ratingsEntryPersistence = ratingsEntryPersistence;
	}

	public RatingsStatsLocalService getRatingsStatsLocalService() {
		return ratingsStatsLocalService;
	}

	public void setRatingsStatsLocalService(
		RatingsStatsLocalService ratingsStatsLocalService) {
		this.ratingsStatsLocalService = ratingsStatsLocalService;
	}

	public RatingsStatsPersistence getRatingsStatsPersistence() {
		return ratingsStatsPersistence;
	}

	public void setRatingsStatsPersistence(
		RatingsStatsPersistence ratingsStatsPersistence) {
		this.ratingsStatsPersistence = ratingsStatsPersistence;
	}

	public SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	public void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
	}

	public SocialActivityPersistence getSocialActivityPersistence() {
		return socialActivityPersistence;
	}

	public void setSocialActivityPersistence(
		SocialActivityPersistence socialActivityPersistence) {
		this.socialActivityPersistence = socialActivityPersistence;
	}

	public SocialActivityFinder getSocialActivityFinder() {
		return socialActivityFinder;
	}

	public void setSocialActivityFinder(
		SocialActivityFinder socialActivityFinder) {
		this.socialActivityFinder = socialActivityFinder;
	}

	public TagsAssetLocalService getTagsAssetLocalService() {
		return tagsAssetLocalService;
	}

	public void setTagsAssetLocalService(
		TagsAssetLocalService tagsAssetLocalService) {
		this.tagsAssetLocalService = tagsAssetLocalService;
	}

	public TagsAssetService getTagsAssetService() {
		return tagsAssetService;
	}

	public void setTagsAssetService(TagsAssetService tagsAssetService) {
		this.tagsAssetService = tagsAssetService;
	}

	public TagsAssetPersistence getTagsAssetPersistence() {
		return tagsAssetPersistence;
	}

	public void setTagsAssetPersistence(
		TagsAssetPersistence tagsAssetPersistence) {
		this.tagsAssetPersistence = tagsAssetPersistence;
	}

	public TagsAssetFinder getTagsAssetFinder() {
		return tagsAssetFinder;
	}

	public void setTagsAssetFinder(TagsAssetFinder tagsAssetFinder) {
		this.tagsAssetFinder = tagsAssetFinder;
	}

	public TagsEntryLocalService getTagsEntryLocalService() {
		return tagsEntryLocalService;
	}

	public void setTagsEntryLocalService(
		TagsEntryLocalService tagsEntryLocalService) {
		this.tagsEntryLocalService = tagsEntryLocalService;
	}

	public TagsEntryService getTagsEntryService() {
		return tagsEntryService;
	}

	public void setTagsEntryService(TagsEntryService tagsEntryService) {
		this.tagsEntryService = tagsEntryService;
	}

	public TagsEntryPersistence getTagsEntryPersistence() {
		return tagsEntryPersistence;
	}

	public void setTagsEntryPersistence(
		TagsEntryPersistence tagsEntryPersistence) {
		this.tagsEntryPersistence = tagsEntryPersistence;
	}

	public TagsEntryFinder getTagsEntryFinder() {
		return tagsEntryFinder;
	}

	public void setTagsEntryFinder(TagsEntryFinder tagsEntryFinder) {
		this.tagsEntryFinder = tagsEntryFinder;
	}

	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService.impl")
	protected DLFileEntryLocalService dlFileEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileEntryService.impl")
	protected DLFileEntryService dlFileEntryService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence.impl")
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder.impl")
	protected DLFileEntryFinder dlFileEntryFinder;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileRankLocalService.impl")
	protected DLFileRankLocalService dlFileRankLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence.impl")
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankFinder.impl")
	protected DLFileRankFinder dlFileRankFinder;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService.impl")
	protected DLFileShortcutLocalService dlFileShortcutLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileShortcutService.impl")
	protected DLFileShortcutService dlFileShortcutService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence.impl")
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutFinder.impl")
	protected DLFileShortcutFinder dlFileShortcutFinder;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService.impl")
	protected DLFileVersionLocalService dlFileVersionLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence.impl")
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFolderLocalService.impl")
	protected DLFolderLocalService dlFolderLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFolderService.impl")
	protected DLFolderService dlFolderService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence.impl")
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder.impl")
	protected DLFolderFinder dlFolderFinder;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.documentlibrary.service.DLLocalService.impl")
	protected DLLocalService dlLocalService;
	@BeanReference(name = "com.liferay.documentlibrary.service.DLService.impl")
	protected DLService dlService;
	@BeanReference(name = "com.liferay.lock.service.LockService.impl")
	protected LockService lockService;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService.impl")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService.impl")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder.impl")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService.impl")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService.impl")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder.impl")
	protected UserFinder userFinder;
	@BeanReference(name = "com.liferay.portal.service.WebDAVPropsLocalService.impl")
	protected WebDAVPropsLocalService webDAVPropsLocalService;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence.impl")
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.ExpandoValueLocalService.impl")
	protected ExpandoValueLocalService expandoValueLocalService;
	@BeanReference(name = "com.liferay.portlet.expando.service.ExpandoValueService.impl")
	protected ExpandoValueService expandoValueService;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValueFinder.impl")
	protected ExpandoValueFinder expandoValueFinder;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBDiscussionLocalService.impl")
	protected MBDiscussionLocalService mbDiscussionLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence.impl")
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageLocalService.impl")
	protected MBMessageLocalService mbMessageLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageService.impl")
	protected MBMessageService mbMessageService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence.impl")
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFinder.impl")
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsEntryLocalService.impl")
	protected RatingsEntryLocalService ratingsEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsEntryService.impl")
	protected RatingsEntryService ratingsEntryService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence.impl")
	protected RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsStatsLocalService.impl")
	protected RatingsStatsLocalService ratingsStatsLocalService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence.impl")
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.SocialActivityLocalService.impl")
	protected SocialActivityLocalService socialActivityLocalService;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityFinder.impl")
	protected SocialActivityFinder socialActivityFinder;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsAssetLocalService.impl")
	protected TagsAssetLocalService tagsAssetLocalService;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsAssetService.impl")
	protected TagsAssetService tagsAssetService;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected TagsAssetPersistence tagsAssetPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsAssetFinder.impl")
	protected TagsAssetFinder tagsAssetFinder;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsEntryLocalService.impl")
	protected TagsEntryLocalService tagsEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.tags.service.TagsEntryService.impl")
	protected TagsEntryService tagsEntryService;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected TagsEntryPersistence tagsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.tags.service.persistence.TagsEntryFinder.impl")
	protected TagsEntryFinder tagsEntryFinder;
}