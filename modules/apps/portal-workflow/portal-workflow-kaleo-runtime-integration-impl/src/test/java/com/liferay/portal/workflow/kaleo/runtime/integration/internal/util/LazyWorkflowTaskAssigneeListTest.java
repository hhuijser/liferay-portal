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

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.util;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceTokenWrapper;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalServiceWrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class LazyWorkflowTaskAssigneeListTest {

	@Test
	public void testGetSizeWhenWorkflowTaskAssigneesIsLoaded() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken(
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					Role.class.getName(), 1),
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					User.class.getName(), 2));

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService =
				new KaleoTaskAssignmentInstanceLocalServiceWrapper(null) {

					@Override
					public int getKaleoTaskAssignmentInstancesCount(
						long kaleoTaskInstanceTokenId) {

						_executedMethodsNames.add(
							"getKaleoTaskAssignmentInstancesCount");

						return -1;
					}

				};

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		lazyWorkflowTaskAssigneeList.initWorkflowTaskAssignees();

		Assert.assertEquals(2, lazyWorkflowTaskAssigneeList.size());

		Assert.assertFalse(
			_executedMethodsNames.contains(
				"getKaleoTaskAssignmentInstancesCount"));
	}

	@Test
	public void testGetSizeWhenWorkflowTaskAssigneesIsNotLoaded() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken();

		long expectedKaleoTaskInstanceTokenId = RandomTestUtil.randomLong();

		kaleoTaskInstanceToken = new KaleoTaskInstanceTokenWrapper(
			kaleoTaskInstanceToken) {

			@Override
			public long getKaleoTaskInstanceTokenId() {
				return expectedKaleoTaskInstanceTokenId;
			}

		};

		int expectedCount = RandomTestUtil.randomInt();

		KaleoTaskAssignmentInstanceLocalService
			kaleoTaskAssignmentInstanceLocalService =
				new KaleoTaskAssignmentInstanceLocalServiceWrapper(null) {

					@Override
					public int getKaleoTaskAssignmentInstancesCount(
						long kaleoTaskInstanceTokenId) {

						_executedMethodsNames.add(
							"getKaleoTaskAssignmentInstancesCount");

						if (kaleoTaskInstanceTokenId ==
								expectedKaleoTaskInstanceTokenId) {

							return expectedCount;
						}

						return -1;
					}

				};

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(
				kaleoTaskInstanceToken,
				kaleoTaskAssignmentInstanceLocalService);

		Assert.assertEquals(expectedCount, lazyWorkflowTaskAssigneeList.size());

		Assert.assertTrue(
			_executedMethodsNames.contains(
				"getKaleoTaskAssignmentInstancesCount"));
	}

	@Test
	public void testGetWhenIndexIsGreaterThanZero() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_getKaleoTaskInstanceToken(
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					Role.class.getName(), 1),
				KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
					User.class.getName(), 2));

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(kaleoTaskInstanceToken, null);

		KaleoRuntimeTestUtil.assertWorkflowTaskAssignee(
			User.class.getName(), 2, lazyWorkflowTaskAssigneeList.get(1));

		Assert.assertFalse(
			_executedMethodsNames.contains(
				"getFirstKaleoTaskAssignmentInstance"));
		Assert.assertTrue(
			_executedMethodsNames.contains("getKaleoTaskAssignmentInstances"));
	}

	@Test
	public void testGetWhenIndexIsZeroAndAssignmentIsNotNull() {
		String expectedAssigneeClassName = StringUtil.randomString();

		long expectedAssigneeClassPK = RandomTestUtil.randomLong();

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			KaleoRuntimeTestUtil.mockKaleoTaskAssignmentInstance(
				expectedAssigneeClassName, expectedAssigneeClassPK);

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			_getKaleoTaskInstanceToken(kaleoTaskAssignmentInstance);

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(kaleoTaskInstanceToken, null);

		KaleoRuntimeTestUtil.assertWorkflowTaskAssignee(
			expectedAssigneeClassName, expectedAssigneeClassPK,
			lazyWorkflowTaskAssigneeList.get(0));

		Assert.assertFalse(
			_executedMethodsNames.contains("getKaleoTaskAssignmentInstances"));

		Assert.assertTrue(
			_executedMethodsNames.contains(
				"getFirstKaleoTaskAssignmentInstance"));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetWhenIndexIsZeroAndAssignmentIsNull() {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken();

		LazyWorkflowTaskAssigneeList lazyWorkflowTaskAssigneeList =
			new LazyWorkflowTaskAssigneeList(kaleoTaskInstanceToken, null);

		lazyWorkflowTaskAssigneeList.get(0);
	}

	private KaleoTaskInstanceToken _getKaleoTaskInstanceToken(
		KaleoTaskAssignmentInstance... kaleoTaskAssignmentInstances) {

		return new KaleoTaskInstanceTokenWrapper(
			KaleoRuntimeTestUtil.mockKaleoTaskInstanceToken(
				kaleoTaskAssignmentInstances)) {

			@Override
			public KaleoTaskAssignmentInstance
				getFirstKaleoTaskAssignmentInstance() {

				_executedMethodsNames.add("getFirstKaleoTaskAssignmentInstance");

				return super.getFirstKaleoTaskAssignmentInstance();
			}

			@Override
			public List<KaleoTaskAssignmentInstance>
				getKaleoTaskAssignmentInstances() {

				_executedMethodsNames.add("getKaleoTaskAssignmentInstances");

				return super.getKaleoTaskAssignmentInstances();
			}

		};
	}

	private final Set<String> _executedMethodsNames = new HashSet<>();

}