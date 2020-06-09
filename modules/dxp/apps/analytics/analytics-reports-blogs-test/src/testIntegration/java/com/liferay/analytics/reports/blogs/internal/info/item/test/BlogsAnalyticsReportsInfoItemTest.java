/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.blogs.internal.info.item.test;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class BlogsAnalyticsReportsInfoItemTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetAuthorName() throws Exception {
		User user = TestPropsValues.getUser();

		BlogsEntry blogsEntry = _addBlogEntry(user);

		Assert.assertEquals(
			user.getFullName(),
			_analyticsReportsInfoItem.getAuthorName(blogsEntry));
	}

	@Test
	public void testGetAuthorNameWithDeletedUser() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		BlogsEntry blogsEntry = _addBlogEntry(user);

		_userLocalService.deleteUser(user);

		Assert.assertEquals(
			StringPool.BLANK,
			_analyticsReportsInfoItem.getAuthorName(blogsEntry));
	}

	@Test
	public void testGetPublishDate() throws Exception {
		BlogsEntry blogsEntry = _addBlogEntry();

		Assert.assertEquals(
			blogsEntry.getCreateDate(),
			_analyticsReportsInfoItem.getPublishDate(blogsEntry));
	}

	@Test
	public void testGetPublishDateWithDisplayPage() throws Exception {
		BlogsEntry blogsEntry = _addBlogEntry();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0,
				_portal.getClassNameId(BlogsEntry.class.getName()), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0, serviceContext);

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_portal.getClassNameId(BlogsEntry.class.getName()),
				blogsEntry.getEntryId(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				AssetDisplayPageConstants.TYPE_SPECIFIC,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			assetDisplayPageEntry.getModifiedDate(),
			_analyticsReportsInfoItem.getPublishDate(blogsEntry));
	}

	@Test
	public void testGetTitle() throws Exception {
		BlogsEntry blogsEntry = _addBlogEntry();

		Assert.assertEquals(
			blogsEntry.getTitle(),
			_analyticsReportsInfoItem.getTitle(blogsEntry, LocaleUtil.US));
	}

	private BlogsEntry _addBlogEntry() throws Exception {
		return _addBlogEntry(TestPropsValues.getUser());
	}

	private BlogsEntry _addBlogEntry(User user) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, user.getUserId());

		return _blogsEntryLocalService.addEntry(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), serviceContext);
	}

	@Inject(filter = "component.name=*.BlogsAnalyticsReportsInfoItem")
	private AnalyticsReportsInfoItem<BlogsEntry> _analyticsReportsInfoItem;

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserLocalService _userLocalService;

}