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

package com.liferay.portlet.mobiledevicerules.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.service.LayoutSetService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.LayoutSetPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalService;
import com.liferay.portlet.mobiledevicerules.service.MDRActionService;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceService;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupLocalService;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupService;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalService;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleService;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRActionPersistence;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupFinder;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupInstancePersistence;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRuleGroupPersistence;
import com.liferay.portlet.mobiledevicerules.service.persistence.MDRRulePersistence;

import javax.sql.DataSource;

/**
 * The base implementation of the m d r rule group instance remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupInstanceServiceImpl}.
 * </p>
 *
 * @author Edward C. Han
 * @see com.liferay.portlet.mobiledevicerules.service.impl.MDRRuleGroupInstanceServiceImpl
 * @see com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil
 * @generated
 */
public abstract class MDRRuleGroupInstanceServiceBaseImpl extends PrincipalBean
	implements MDRRuleGroupInstanceService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupInstanceServiceUtil} to access the m d r rule group instance remote service.
	 */

	/**
	 * Returns the m d r action local service.
	 *
	 * @return the m d r action local service
	 */
	public MDRActionLocalService getMDRActionLocalService() {
		return mdrActionLocalService;
	}

	/**
	 * Sets the m d r action local service.
	 *
	 * @param mdrActionLocalService the m d r action local service
	 */
	public void setMDRActionLocalService(
		MDRActionLocalService mdrActionLocalService) {
		this.mdrActionLocalService = mdrActionLocalService;
	}

	/**
	 * Returns the m d r action remote service.
	 *
	 * @return the m d r action remote service
	 */
	public MDRActionService getMDRActionService() {
		return mdrActionService;
	}

	/**
	 * Sets the m d r action remote service.
	 *
	 * @param mdrActionService the m d r action remote service
	 */
	public void setMDRActionService(MDRActionService mdrActionService) {
		this.mdrActionService = mdrActionService;
	}

	/**
	 * Returns the m d r action persistence.
	 *
	 * @return the m d r action persistence
	 */
	public MDRActionPersistence getMDRActionPersistence() {
		return mdrActionPersistence;
	}

	/**
	 * Sets the m d r action persistence.
	 *
	 * @param mdrActionPersistence the m d r action persistence
	 */
	public void setMDRActionPersistence(
		MDRActionPersistence mdrActionPersistence) {
		this.mdrActionPersistence = mdrActionPersistence;
	}

	/**
	 * Returns the m d r rule local service.
	 *
	 * @return the m d r rule local service
	 */
	public MDRRuleLocalService getMDRRuleLocalService() {
		return mdrRuleLocalService;
	}

	/**
	 * Sets the m d r rule local service.
	 *
	 * @param mdrRuleLocalService the m d r rule local service
	 */
	public void setMDRRuleLocalService(MDRRuleLocalService mdrRuleLocalService) {
		this.mdrRuleLocalService = mdrRuleLocalService;
	}

	/**
	 * Returns the m d r rule remote service.
	 *
	 * @return the m d r rule remote service
	 */
	public MDRRuleService getMDRRuleService() {
		return mdrRuleService;
	}

	/**
	 * Sets the m d r rule remote service.
	 *
	 * @param mdrRuleService the m d r rule remote service
	 */
	public void setMDRRuleService(MDRRuleService mdrRuleService) {
		this.mdrRuleService = mdrRuleService;
	}

	/**
	 * Returns the m d r rule persistence.
	 *
	 * @return the m d r rule persistence
	 */
	public MDRRulePersistence getMDRRulePersistence() {
		return mdrRulePersistence;
	}

	/**
	 * Sets the m d r rule persistence.
	 *
	 * @param mdrRulePersistence the m d r rule persistence
	 */
	public void setMDRRulePersistence(MDRRulePersistence mdrRulePersistence) {
		this.mdrRulePersistence = mdrRulePersistence;
	}

	/**
	 * Returns the m d r rule group local service.
	 *
	 * @return the m d r rule group local service
	 */
	public MDRRuleGroupLocalService getMDRRuleGroupLocalService() {
		return mdrRuleGroupLocalService;
	}

	/**
	 * Sets the m d r rule group local service.
	 *
	 * @param mdrRuleGroupLocalService the m d r rule group local service
	 */
	public void setMDRRuleGroupLocalService(
		MDRRuleGroupLocalService mdrRuleGroupLocalService) {
		this.mdrRuleGroupLocalService = mdrRuleGroupLocalService;
	}

	/**
	 * Returns the m d r rule group remote service.
	 *
	 * @return the m d r rule group remote service
	 */
	public MDRRuleGroupService getMDRRuleGroupService() {
		return mdrRuleGroupService;
	}

	/**
	 * Sets the m d r rule group remote service.
	 *
	 * @param mdrRuleGroupService the m d r rule group remote service
	 */
	public void setMDRRuleGroupService(MDRRuleGroupService mdrRuleGroupService) {
		this.mdrRuleGroupService = mdrRuleGroupService;
	}

	/**
	 * Returns the m d r rule group persistence.
	 *
	 * @return the m d r rule group persistence
	 */
	public MDRRuleGroupPersistence getMDRRuleGroupPersistence() {
		return mdrRuleGroupPersistence;
	}

	/**
	 * Sets the m d r rule group persistence.
	 *
	 * @param mdrRuleGroupPersistence the m d r rule group persistence
	 */
	public void setMDRRuleGroupPersistence(
		MDRRuleGroupPersistence mdrRuleGroupPersistence) {
		this.mdrRuleGroupPersistence = mdrRuleGroupPersistence;
	}

	/**
	 * Returns the m d r rule group finder.
	 *
	 * @return the m d r rule group finder
	 */
	public MDRRuleGroupFinder getMDRRuleGroupFinder() {
		return mdrRuleGroupFinder;
	}

	/**
	 * Sets the m d r rule group finder.
	 *
	 * @param mdrRuleGroupFinder the m d r rule group finder
	 */
	public void setMDRRuleGroupFinder(MDRRuleGroupFinder mdrRuleGroupFinder) {
		this.mdrRuleGroupFinder = mdrRuleGroupFinder;
	}

	/**
	 * Returns the m d r rule group instance local service.
	 *
	 * @return the m d r rule group instance local service
	 */
	public MDRRuleGroupInstanceLocalService getMDRRuleGroupInstanceLocalService() {
		return mdrRuleGroupInstanceLocalService;
	}

	/**
	 * Sets the m d r rule group instance local service.
	 *
	 * @param mdrRuleGroupInstanceLocalService the m d r rule group instance local service
	 */
	public void setMDRRuleGroupInstanceLocalService(
		MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService) {
		this.mdrRuleGroupInstanceLocalService = mdrRuleGroupInstanceLocalService;
	}

	/**
	 * Returns the m d r rule group instance remote service.
	 *
	 * @return the m d r rule group instance remote service
	 */
	public MDRRuleGroupInstanceService getMDRRuleGroupInstanceService() {
		return mdrRuleGroupInstanceService;
	}

	/**
	 * Sets the m d r rule group instance remote service.
	 *
	 * @param mdrRuleGroupInstanceService the m d r rule group instance remote service
	 */
	public void setMDRRuleGroupInstanceService(
		MDRRuleGroupInstanceService mdrRuleGroupInstanceService) {
		this.mdrRuleGroupInstanceService = mdrRuleGroupInstanceService;
	}

	/**
	 * Returns the m d r rule group instance persistence.
	 *
	 * @return the m d r rule group instance persistence
	 */
	public MDRRuleGroupInstancePersistence getMDRRuleGroupInstancePersistence() {
		return mdrRuleGroupInstancePersistence;
	}

	/**
	 * Sets the m d r rule group instance persistence.
	 *
	 * @param mdrRuleGroupInstancePersistence the m d r rule group instance persistence
	 */
	public void setMDRRuleGroupInstancePersistence(
		MDRRuleGroupInstancePersistence mdrRuleGroupInstancePersistence) {
		this.mdrRuleGroupInstancePersistence = mdrRuleGroupInstancePersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the layout local service.
	 *
	 * @return the layout local service
	 */
	public LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	/**
	 * Sets the layout local service.
	 *
	 * @param layoutLocalService the layout local service
	 */
	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	/**
	 * Returns the layout remote service.
	 *
	 * @return the layout remote service
	 */
	public LayoutService getLayoutService() {
		return layoutService;
	}

	/**
	 * Sets the layout remote service.
	 *
	 * @param layoutService the layout remote service
	 */
	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	/**
	 * Returns the layout persistence.
	 *
	 * @return the layout persistence
	 */
	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	/**
	 * Sets the layout persistence.
	 *
	 * @param layoutPersistence the layout persistence
	 */
	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	/**
	 * Returns the layout finder.
	 *
	 * @return the layout finder
	 */
	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	/**
	 * Sets the layout finder.
	 *
	 * @param layoutFinder the layout finder
	 */
	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
	}

	/**
	 * Returns the layout set local service.
	 *
	 * @return the layout set local service
	 */
	public LayoutSetLocalService getLayoutSetLocalService() {
		return layoutSetLocalService;
	}

	/**
	 * Sets the layout set local service.
	 *
	 * @param layoutSetLocalService the layout set local service
	 */
	public void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {
		this.layoutSetLocalService = layoutSetLocalService;
	}

	/**
	 * Returns the layout set remote service.
	 *
	 * @return the layout set remote service
	 */
	public LayoutSetService getLayoutSetService() {
		return layoutSetService;
	}

	/**
	 * Sets the layout set remote service.
	 *
	 * @param layoutSetService the layout set remote service
	 */
	public void setLayoutSetService(LayoutSetService layoutSetService) {
		this.layoutSetService = layoutSetService;
	}

	/**
	 * Returns the layout set persistence.
	 *
	 * @return the layout set persistence
	 */
	public LayoutSetPersistence getLayoutSetPersistence() {
		return layoutSetPersistence;
	}

	/**
	 * Sets the layout set persistence.
	 *
	 * @param layoutSetPersistence the layout set persistence
	 */
	public void setLayoutSetPersistence(
		LayoutSetPersistence layoutSetPersistence) {
		this.layoutSetPersistence = layoutSetPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource remote service.
	 *
	 * @return the resource remote service
	 */
	public ResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * Sets the resource remote service.
	 *
	 * @param resourceService the resource remote service
	 */
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * Returns the resource persistence.
	 *
	 * @return the resource persistence
	 */
	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	/**
	 * Sets the resource persistence.
	 *
	 * @param resourcePersistence the resource persistence
	 */
	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	/**
	 * Returns the resource finder.
	 *
	 * @return the resource finder
	 */
	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	/**
	 * Sets the resource finder.
	 *
	 * @param resourceFinder the resource finder
	 */
	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	protected Class<?> getModelClass() {
		return MDRRuleGroupInstance.class;
	}

	protected String getModelClassName() {
		return MDRRuleGroupInstance.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = mdrRuleGroupInstancePersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = MDRActionLocalService.class)
	protected MDRActionLocalService mdrActionLocalService;
	@BeanReference(type = MDRActionService.class)
	protected MDRActionService mdrActionService;
	@BeanReference(type = MDRActionPersistence.class)
	protected MDRActionPersistence mdrActionPersistence;
	@BeanReference(type = MDRRuleLocalService.class)
	protected MDRRuleLocalService mdrRuleLocalService;
	@BeanReference(type = MDRRuleService.class)
	protected MDRRuleService mdrRuleService;
	@BeanReference(type = MDRRulePersistence.class)
	protected MDRRulePersistence mdrRulePersistence;
	@BeanReference(type = MDRRuleGroupLocalService.class)
	protected MDRRuleGroupLocalService mdrRuleGroupLocalService;
	@BeanReference(type = MDRRuleGroupService.class)
	protected MDRRuleGroupService mdrRuleGroupService;
	@BeanReference(type = MDRRuleGroupPersistence.class)
	protected MDRRuleGroupPersistence mdrRuleGroupPersistence;
	@BeanReference(type = MDRRuleGroupFinder.class)
	protected MDRRuleGroupFinder mdrRuleGroupFinder;
	@BeanReference(type = MDRRuleGroupInstanceLocalService.class)
	protected MDRRuleGroupInstanceLocalService mdrRuleGroupInstanceLocalService;
	@BeanReference(type = MDRRuleGroupInstanceService.class)
	protected MDRRuleGroupInstanceService mdrRuleGroupInstanceService;
	@BeanReference(type = MDRRuleGroupInstancePersistence.class)
	protected MDRRuleGroupInstancePersistence mdrRuleGroupInstancePersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = LayoutLocalService.class)
	protected LayoutLocalService layoutLocalService;
	@BeanReference(type = LayoutService.class)
	protected LayoutService layoutService;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutFinder.class)
	protected LayoutFinder layoutFinder;
	@BeanReference(type = LayoutSetLocalService.class)
	protected LayoutSetLocalService layoutSetLocalService;
	@BeanReference(type = LayoutSetService.class)
	protected LayoutSetService layoutSetService;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = ResourceService.class)
	protected ResourceService resourceService;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceFinder.class)
	protected ResourceFinder resourceFinder;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	private String _beanIdentifier;
}