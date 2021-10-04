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

package com.liferay.commerce.order.rule.service.base;

import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryLocalService;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryLocalServiceUtil;
import com.liferay.commerce.order.rule.service.persistence.CommerceOrderRuleEntryPersistence;
import com.liferay.commerce.order.rule.service.persistence.CommerceOrderRuleEntryRelPersistence;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the commerce order rule entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.order.rule.service.impl.CommerceOrderRuleEntryLocalServiceImpl}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see com.liferay.commerce.order.rule.service.impl.CommerceOrderRuleEntryLocalServiceImpl
 * @generated
 */
public abstract class CommerceOrderRuleEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, CommerceOrderRuleEntryLocalService,
			   IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceOrderRuleEntryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceOrderRuleEntryLocalServiceUtil</code>.
	 */

	/**
	 * Adds the commerce order rule entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderRuleEntry addCommerceOrderRuleEntry(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		commerceOrderRuleEntry.setNew(true);

		return commerceOrderRuleEntryPersistence.update(commerceOrderRuleEntry);
	}

	/**
	 * Creates a new commerce order rule entry with the primary key. Does not add the commerce order rule entry to the database.
	 *
	 * @param commerceOrderRuleEntryId the primary key for the new commerce order rule entry
	 * @return the new commerce order rule entry
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceOrderRuleEntry createCommerceOrderRuleEntry(
		long commerceOrderRuleEntryId) {

		return commerceOrderRuleEntryPersistence.create(
			commerceOrderRuleEntryId);
	}

	/**
	 * Deletes the commerce order rule entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 * @throws NoSuchOrderRuleEntryException
	 * @throws PortalException if a commerce order rule entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws NoSuchOrderRuleEntryException, PortalException {

		return commerceOrderRuleEntryPersistence.remove(
			commerceOrderRuleEntryId);
	}

	/**
	 * Deletes the commerce order rule entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		return commerceOrderRuleEntryPersistence.remove(commerceOrderRuleEntry);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return commerceOrderRuleEntryPersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntry.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceOrderRuleEntryPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return commerceOrderRuleEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return commerceOrderRuleEntryPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commerceOrderRuleEntryPersistence.countWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return commerceOrderRuleEntryPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CommerceOrderRuleEntry fetchCommerceOrderRuleEntry(
		long commerceOrderRuleEntryId) {

		return commerceOrderRuleEntryPersistence.fetchByPrimaryKey(
			commerceOrderRuleEntryId);
	}

	/**
	 * Returns the commerce order rule entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order rule entry's external reference code
	 * @return the matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	@Override
	public CommerceOrderRuleEntry
		fetchCommerceOrderRuleEntryByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return commerceOrderRuleEntryPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceOrderRuleEntryByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public CommerceOrderRuleEntry fetchCommerceOrderRuleEntryByReferenceCode(
		long companyId, String externalReferenceCode) {

		return fetchCommerceOrderRuleEntryByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order rule entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order rule entry's external reference code
	 * @return the matching commerce order rule entry
	 * @throws PortalException if a matching commerce order rule entry could not be found
	 */
	@Override
	public CommerceOrderRuleEntry
			getCommerceOrderRuleEntryByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException {

		return commerceOrderRuleEntryPersistence.findByC_ERC(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order rule entry with the primary key.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry
	 * @throws PortalException if a commerce order rule entry with the primary key could not be found
	 */
	@Override
	public CommerceOrderRuleEntry getCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws PortalException {

		return commerceOrderRuleEntryPersistence.findByPrimaryKey(
			commerceOrderRuleEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			commerceOrderRuleEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceOrderRuleEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceOrderRuleEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			commerceOrderRuleEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CommerceOrderRuleEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceOrderRuleEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			commerceOrderRuleEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceOrderRuleEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceOrderRuleEntryId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceOrderRuleEntryPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return commerceOrderRuleEntryLocalService.deleteCommerceOrderRuleEntry(
			(CommerceOrderRuleEntry)persistedModel);
	}

	@Override
	public BasePersistence<CommerceOrderRuleEntry> getBasePersistence() {
		return commerceOrderRuleEntryPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceOrderRuleEntryPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns a range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of commerce order rule entries
	 */
	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		int start, int end) {

		return commerceOrderRuleEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce order rule entries.
	 *
	 * @return the number of commerce order rule entries
	 */
	@Override
	public int getCommerceOrderRuleEntriesCount() {
		return commerceOrderRuleEntryPersistence.countAll();
	}

	/**
	 * Updates the commerce order rule entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderRuleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 * @return the commerce order rule entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderRuleEntry updateCommerceOrderRuleEntry(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		return commerceOrderRuleEntryPersistence.update(commerceOrderRuleEntry);
	}

	@Deactivate
	protected void deactivate() {
		_setLocalServiceUtilService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			CommerceOrderRuleEntryLocalService.class,
			IdentifiableOSGiService.class, PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		commerceOrderRuleEntryLocalService =
			(CommerceOrderRuleEntryLocalService)aopProxy;

		_setLocalServiceUtilService(commerceOrderRuleEntryLocalService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceOrderRuleEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceOrderRuleEntry.class;
	}

	protected String getModelClassName() {
		return CommerceOrderRuleEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				commerceOrderRuleEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _setLocalServiceUtilService(
		CommerceOrderRuleEntryLocalService commerceOrderRuleEntryLocalService) {

		try {
			Field field =
				CommerceOrderRuleEntryLocalServiceUtil.class.getDeclaredField(
					"_service");

			field.setAccessible(true);

			field.set(null, commerceOrderRuleEntryLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	protected CommerceOrderRuleEntryLocalService
		commerceOrderRuleEntryLocalService;

	@Reference
	protected CommerceOrderRuleEntryPersistence
		commerceOrderRuleEntryPersistence;

	@Reference
	protected CommerceOrderRuleEntryRelPersistence
		commerceOrderRuleEntryRelPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

}