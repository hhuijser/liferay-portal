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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class DLFileEntryTypePersistenceTest {
	@BeforeClass
	public static void setupClass() throws TemplateException {
		TemplateManagerUtil.init();

		PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = false;
	}

	public static void tearDownClass() {
		PropsValues.SPRING_HIBERNATE_SESSION_DELEGATED = true;
	}

	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule();

	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<DLFileEntryType> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DLFileEntryType> iterator = _dlFileEntryTypes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}

		for (ModelListener<DLFileEntryType> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryType dlFileEntryType = _persistence.create(pk);

		Assert.assertNotNull(dlFileEntryType);

		Assert.assertEquals(dlFileEntryType.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		_persistence.remove(newDLFileEntryType);

		DLFileEntryType existingDLFileEntryType = _persistence.fetchByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertNull(existingDLFileEntryType);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileEntryType();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryType newDLFileEntryType = _persistence.create(pk);

		newDLFileEntryType.setUuid(RandomTestUtil.randomString());

		newDLFileEntryType.setGroupId(RandomTestUtil.nextLong());

		newDLFileEntryType.setCompanyId(RandomTestUtil.nextLong());

		newDLFileEntryType.setUserId(RandomTestUtil.nextLong());

		newDLFileEntryType.setUserName(RandomTestUtil.randomString());

		newDLFileEntryType.setCreateDate(RandomTestUtil.nextDate());

		newDLFileEntryType.setModifiedDate(RandomTestUtil.nextDate());

		newDLFileEntryType.setFileEntryTypeKey(RandomTestUtil.randomString());

		newDLFileEntryType.setName(RandomTestUtil.randomString());

		newDLFileEntryType.setDescription(RandomTestUtil.randomString());

		_dlFileEntryTypes.add(_persistence.update(newDLFileEntryType));

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType.getUuid(),
			newDLFileEntryType.getUuid());
		Assert.assertEquals(existingDLFileEntryType.getFileEntryTypeId(),
			newDLFileEntryType.getFileEntryTypeId());
		Assert.assertEquals(existingDLFileEntryType.getGroupId(),
			newDLFileEntryType.getGroupId());
		Assert.assertEquals(existingDLFileEntryType.getCompanyId(),
			newDLFileEntryType.getCompanyId());
		Assert.assertEquals(existingDLFileEntryType.getUserId(),
			newDLFileEntryType.getUserId());
		Assert.assertEquals(existingDLFileEntryType.getUserName(),
			newDLFileEntryType.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntryType.getCreateDate()),
			Time.getShortTimestamp(newDLFileEntryType.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntryType.getModifiedDate()),
			Time.getShortTimestamp(newDLFileEntryType.getModifiedDate()));
		Assert.assertEquals(existingDLFileEntryType.getFileEntryTypeKey(),
			newDLFileEntryType.getFileEntryTypeKey());
		Assert.assertEquals(existingDLFileEntryType.getName(),
			newDLFileEntryType.getName());
		Assert.assertEquals(existingDLFileEntryType.getDescription(),
			newDLFileEntryType.getDescription());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupIdArrayable() {
		try {
			_persistence.countByGroupId(new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F() {
		try {
			_persistence.countByG_F(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_F(0L, StringPool.NULL);

			_persistence.countByG_F(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchFileEntryTypeException");
		}
		catch (NoSuchFileEntryTypeException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<DLFileEntryType> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DLFileEntryType", "uuid",
			true, "fileEntryTypeId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "fileEntryTypeKey", true, "name", true,
			"description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DLFileEntryType existingDLFileEntryType = _persistence.fetchByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryType missingDLFileEntryType = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileEntryType);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DLFileEntryType newDLFileEntryType1 = addDLFileEntryType();
		DLFileEntryType newDLFileEntryType2 = addDLFileEntryType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryType1.getPrimaryKey());
		primaryKeys.add(newDLFileEntryType2.getPrimaryKey());

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, dlFileEntryTypes.size());
		Assert.assertEquals(newDLFileEntryType1,
			dlFileEntryTypes.get(newDLFileEntryType1.getPrimaryKey()));
		Assert.assertEquals(newDLFileEntryType2,
			dlFileEntryTypes.get(newDLFileEntryType2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlFileEntryTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryType.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlFileEntryTypes.size());
		Assert.assertEquals(newDLFileEntryType,
			dlFileEntryTypes.get(newDLFileEntryType.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlFileEntryTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryType.getPrimaryKey());

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlFileEntryTypes.size());
		Assert.assertEquals(newDLFileEntryType,
			dlFileEntryTypes.get(newDLFileEntryType.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DLFileEntryTypeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DLFileEntryType dlFileEntryType = (DLFileEntryType)object;

					Assert.assertNotNull(dlFileEntryType);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryTypeId",
				newDLFileEntryType.getFileEntryTypeId()));

		List<DLFileEntryType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileEntryType existingDLFileEntryType = result.get(0);

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryTypeId",
				RandomTestUtil.nextLong()));

		List<DLFileEntryType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryTypeId"));

		Object newFileEntryTypeId = newDLFileEntryType.getFileEntryTypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryTypeId",
				new Object[] { newFileEntryTypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileEntryTypeId = result.get(0);

		Assert.assertEquals(existingFileEntryTypeId, newFileEntryTypeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryTypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryTypeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		_persistence.clearCache();

		DLFileEntryTypeModelImpl existingDLFileEntryTypeModelImpl = (DLFileEntryTypeModelImpl)_persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDLFileEntryTypeModelImpl.getUuid(),
				existingDLFileEntryTypeModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDLFileEntryTypeModelImpl.getGroupId(),
			existingDLFileEntryTypeModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDLFileEntryTypeModelImpl.getGroupId(),
			existingDLFileEntryTypeModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingDLFileEntryTypeModelImpl.getFileEntryTypeKey(),
				existingDLFileEntryTypeModelImpl.getOriginalFileEntryTypeKey()));
	}

	protected DLFileEntryType addDLFileEntryType() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryType dlFileEntryType = _persistence.create(pk);

		dlFileEntryType.setUuid(RandomTestUtil.randomString());

		dlFileEntryType.setGroupId(RandomTestUtil.nextLong());

		dlFileEntryType.setCompanyId(RandomTestUtil.nextLong());

		dlFileEntryType.setUserId(RandomTestUtil.nextLong());

		dlFileEntryType.setUserName(RandomTestUtil.randomString());

		dlFileEntryType.setCreateDate(RandomTestUtil.nextDate());

		dlFileEntryType.setModifiedDate(RandomTestUtil.nextDate());

		dlFileEntryType.setFileEntryTypeKey(RandomTestUtil.randomString());

		dlFileEntryType.setName(RandomTestUtil.randomString());

		dlFileEntryType.setDescription(RandomTestUtil.randomString());

		_dlFileEntryTypes.add(_persistence.update(dlFileEntryType));

		return dlFileEntryType;
	}

	private List<DLFileEntryType> _dlFileEntryTypes = new ArrayList<DLFileEntryType>();
	private ModelListener<DLFileEntryType>[] _modelListeners;
	private DLFileEntryTypePersistence _persistence = (DLFileEntryTypePersistence)PortalBeanLocatorUtil.locate(DLFileEntryTypePersistence.class.getName());
}