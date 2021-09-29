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

package com.liferay.portal.service.base;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.RegionLocalization;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.RegionLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.RegionLocalizationPersistence;
import com.liferay.portal.kernel.service.persistence.RegionPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the region local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.RegionLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.RegionLocalServiceImpl
 * @generated
 */
public abstract class RegionLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements IdentifiableOSGiService, RegionLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>RegionLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>RegionLocalServiceUtil</code>.
	 */

	/**
	 * Adds the region to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param region the region
	 * @return the region that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Region addRegion(Region region) {
		region.setNew(true);

		return regionPersistence.update(region);
	}

	/**
	 * Creates a new region with the primary key. Does not add the region to the database.
	 *
	 * @param regionId the primary key for the new region
	 * @return the new region
	 */
	@Override
	@Transactional(enabled = false)
	public Region createRegion(long regionId) {
		return regionPersistence.create(regionId);
	}

	/**
	 * Deletes the region with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param regionId the primary key of the region
	 * @return the region that was removed
	 * @throws PortalException if a region with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Region deleteRegion(long regionId) throws PortalException {
		return regionPersistence.remove(regionId);
	}

	/**
	 * Deletes the region from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param region the region
	 * @return the region that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Region deleteRegion(Region region) {
		return regionPersistence.remove(region);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return regionPersistence.dslQuery(dslQuery);
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
			Region.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return regionPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RegionModelImpl</code>.
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

		return regionPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RegionModelImpl</code>.
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

		return regionPersistence.findWithDynamicQuery(
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
		return regionPersistence.countWithDynamicQuery(dynamicQuery);
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

		return regionPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public Region fetchRegion(long regionId) {
		return regionPersistence.fetchByPrimaryKey(regionId);
	}

	/**
	 * Returns the region with the matching UUID and company.
	 *
	 * @param uuid the region's UUID
	 * @param companyId the primary key of the company
	 * @return the matching region, or <code>null</code> if a matching region could not be found
	 */
	@Override
	public Region fetchRegionByUuidAndCompanyId(String uuid, long companyId) {
		return regionPersistence.fetchByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns the region with the primary key.
	 *
	 * @param regionId the primary key of the region
	 * @return the region
	 * @throws PortalException if a region with the primary key could not be found
	 */
	@Override
	public Region getRegion(long regionId) throws PortalException {
		return regionPersistence.findByPrimaryKey(regionId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(regionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Region.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("regionId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(regionLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(Region.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("regionId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(regionLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(Region.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("regionId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Region>() {

				@Override
				public void performAction(Region region)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, region);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(Region.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return regionPersistence.create(((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return regionLocalService.deleteRegion((Region)persistedModel);
	}

	@Override
	public BasePersistence<Region> getBasePersistence() {
		return regionPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return regionPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the region with the matching UUID and company.
	 *
	 * @param uuid the region's UUID
	 * @param companyId the primary key of the company
	 * @return the matching region
	 * @throws PortalException if a matching region could not be found
	 */
	@Override
	public Region getRegionByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException {

		return regionPersistence.findByUuid_C_First(uuid, companyId, null);
	}

	/**
	 * Returns a range of all the regions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RegionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of regions
	 * @param end the upper bound of the range of regions (not inclusive)
	 * @return the range of regions
	 */
	@Override
	public List<Region> getRegions(int start, int end) {
		return regionPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of regions.
	 *
	 * @return the number of regions
	 */
	@Override
	public int getRegionsCount() {
		return regionPersistence.countAll();
	}

	/**
	 * Updates the region in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param region the region
	 * @return the region that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Region updateRegion(Region region) {
		return regionPersistence.update(region);
	}

	@Override
	public RegionLocalization fetchRegionLocalization(
		long regionId, String languageId) {

		return regionLocalizationPersistence.fetchByRegionId_LanguageId(
			regionId, languageId);
	}

	@Override
	public RegionLocalization getRegionLocalization(
			long regionId, String languageId)
		throws PortalException {

		return regionLocalizationPersistence.findByRegionId_LanguageId(
			regionId, languageId);
	}

	@Override
	public List<RegionLocalization> getRegionLocalizations(long regionId) {
		return regionLocalizationPersistence.findByRegionId(regionId);
	}

	@Override
	public RegionLocalization updateRegionLocalization(
			Region region, String languageId, String title)
		throws PortalException {

		region = regionPersistence.findByPrimaryKey(region.getPrimaryKey());

		RegionLocalization regionLocalization =
			regionLocalizationPersistence.fetchByRegionId_LanguageId(
				region.getRegionId(), languageId);

		return _updateRegionLocalization(
			region, regionLocalization, languageId, title);
	}

	@Override
	public List<RegionLocalization> updateRegionLocalizations(
			Region region, Map<String, String> titleMap)
		throws PortalException {

		region = regionPersistence.findByPrimaryKey(region.getPrimaryKey());

		Map<String, String[]> localizedValuesMap =
			new HashMap<String, String[]>();

		for (Map.Entry<String, String> entry : titleMap.entrySet()) {
			String languageId = entry.getKey();

			String[] localizedValues = localizedValuesMap.get(languageId);

			if (localizedValues == null) {
				localizedValues = new String[1];

				localizedValuesMap.put(languageId, localizedValues);
			}

			localizedValues[0] = entry.getValue();
		}

		List<RegionLocalization> regionLocalizations =
			new ArrayList<RegionLocalization>(localizedValuesMap.size());

		for (RegionLocalization regionLocalization :
				regionLocalizationPersistence.findByRegionId(
					region.getRegionId())) {

			String[] localizedValues = localizedValuesMap.remove(
				regionLocalization.getLanguageId());

			if (localizedValues == null) {
				regionLocalizationPersistence.remove(regionLocalization);
			}
			else {
				regionLocalization.setCompanyId(region.getCompanyId());

				regionLocalization.setTitle(localizedValues[0]);

				regionLocalizations.add(
					regionLocalizationPersistence.update(regionLocalization));
			}
		}

		long batchCounter =
			counterLocalService.increment(
				RegionLocalization.class.getName(), localizedValuesMap.size()) -
					localizedValuesMap.size();

		for (Map.Entry<String, String[]> entry :
				localizedValuesMap.entrySet()) {

			String languageId = entry.getKey();
			String[] localizedValues = entry.getValue();

			RegionLocalization regionLocalization =
				regionLocalizationPersistence.create(++batchCounter);

			regionLocalization.setRegionId(region.getRegionId());
			regionLocalization.setCompanyId(region.getCompanyId());

			regionLocalization.setLanguageId(languageId);

			regionLocalization.setTitle(localizedValues[0]);

			regionLocalizations.add(
				regionLocalizationPersistence.update(regionLocalization));
		}

		return regionLocalizations;
	}

	private RegionLocalization _updateRegionLocalization(
			Region region, RegionLocalization regionLocalization,
			String languageId, String title)
		throws PortalException {

		if (regionLocalization == null) {
			long regionLocalizationId = counterLocalService.increment(
				RegionLocalization.class.getName());

			regionLocalization = regionLocalizationPersistence.create(
				regionLocalizationId);

			regionLocalization.setRegionId(region.getRegionId());
			regionLocalization.setLanguageId(languageId);
		}

		regionLocalization.setCompanyId(region.getCompanyId());

		regionLocalization.setTitle(title);

		return regionLocalizationPersistence.update(regionLocalization);
	}

	/**
	 * Returns the region local service.
	 *
	 * @return the region local service
	 */
	public RegionLocalService getRegionLocalService() {
		return regionLocalService;
	}

	/**
	 * Sets the region local service.
	 *
	 * @param regionLocalService the region local service
	 */
	public void setRegionLocalService(RegionLocalService regionLocalService) {
		this.regionLocalService = regionLocalService;
	}

	/**
	 * Returns the region persistence.
	 *
	 * @return the region persistence
	 */
	public RegionPersistence getRegionPersistence() {
		return regionPersistence;
	}

	/**
	 * Sets the region persistence.
	 *
	 * @param regionPersistence the region persistence
	 */
	public void setRegionPersistence(RegionPersistence regionPersistence) {
		this.regionPersistence = regionPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the region localization persistence.
	 *
	 * @return the region localization persistence
	 */
	public RegionLocalizationPersistence getRegionLocalizationPersistence() {
		return regionLocalizationPersistence;
	}

	/**
	 * Sets the region localization persistence.
	 *
	 * @param regionLocalizationPersistence the region localization persistence
	 */
	public void setRegionLocalizationPersistence(
		RegionLocalizationPersistence regionLocalizationPersistence) {

		this.regionLocalizationPersistence = regionLocalizationPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.portal.kernel.model.Region", regionLocalService);

		_setLocalServiceUtilService(regionLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.kernel.model.Region");

		_setLocalServiceUtilService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return RegionLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return Region.class;
	}

	protected String getModelClassName() {
		return Region.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = regionPersistence.getDataSource();

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
		RegionLocalService regionLocalService) {

		try {
			Field field = RegionLocalServiceUtil.class.getDeclaredField(
				"_service");

			field.setAccessible(true);

			field.set(null, regionLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@BeanReference(type = RegionLocalService.class)
	protected RegionLocalService regionLocalService;

	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;

	@BeanReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@BeanReference(type = RegionLocalizationPersistence.class)
	protected RegionLocalizationPersistence regionLocalizationPersistence;

	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}