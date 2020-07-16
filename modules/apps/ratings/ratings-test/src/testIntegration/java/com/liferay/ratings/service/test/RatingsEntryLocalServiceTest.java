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

package com.liferay.ratings.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.ratings.kernel.exception.EntryScoreException;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class RatingsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testRatingScore0IsValidScore() throws Exception {
		RatingsEntry ratingsEntry = RatingsEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong(), 0,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(0, ratingsEntry.getScore(), 0.001);
	}

	@Test
	public void testRatingScore1IsValidScore() throws Exception {
		RatingsEntry ratingsEntry = RatingsEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong(), 1,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(1, ratingsEntry.getScore(), 0.001);
	}

	@Test(expected = EntryScoreException.class)
	public void testRatingScoreGreaterThan1IsInvalidScore() throws Exception {
		RatingsEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong(), 4,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = EntryScoreException.class)
	public void testRatingScoreLessThan0IsInvalidScore() throws Exception {
		RatingsEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomLong(), -1,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@DeleteAfterTestRun
	private Group _group;

}