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

package com.liferay.dynamic.data.mapping.service.base;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceServiceUtil;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceFinder;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstancePersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.util.PortalUtil;

import java.lang.reflect.Field;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the ddm form instance remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceServiceImpl
 * @generated
 */
public abstract class DDMFormInstanceServiceBaseImpl
	extends BaseServiceImpl
	implements AopService, DDMFormInstanceService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>DDMFormInstanceService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>DDMFormInstanceServiceUtil</code>.
	 */
	@Deactivate
	protected void deactivate() {
		_setServiceUtilService(null);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			DDMFormInstanceService.class, IdentifiableOSGiService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		ddmFormInstanceService = (DDMFormInstanceService)aopProxy;

		_setServiceUtilService(ddmFormInstanceService);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DDMFormInstanceService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DDMFormInstance.class;
	}

	protected String getModelClassName() {
		return DDMFormInstance.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddmFormInstancePersistence.getDataSource();

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

	private void _setServiceUtilService(
		DDMFormInstanceService ddmFormInstanceService) {

		try {
			Field field = DDMFormInstanceServiceUtil.class.getDeclaredField(
				"_service");

			field.setAccessible(true);

			field.set(null, ddmFormInstanceService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Reference
	protected
		com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService
			ddmFormInstanceLocalService;

	protected DDMFormInstanceService ddmFormInstanceService;

	@Reference
	protected DDMFormInstancePersistence ddmFormInstancePersistence;

	@Reference
	protected DDMFormInstanceFinder ddmFormInstanceFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

}