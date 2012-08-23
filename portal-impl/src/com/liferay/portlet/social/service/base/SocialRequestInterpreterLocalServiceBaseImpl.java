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

package com.liferay.portlet.social.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.social.service.SocialActivityAchievementLocalService;
import com.liferay.portlet.social.service.SocialActivityCounterLocalService;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalService;
import com.liferay.portlet.social.service.SocialActivityLimitLocalService;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.SocialActivitySettingLocalService;
import com.liferay.portlet.social.service.SocialActivitySettingService;
import com.liferay.portlet.social.service.SocialRelationLocalService;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalService;
import com.liferay.portlet.social.service.SocialRequestLocalService;
import com.liferay.portlet.social.service.SocialRequestService;
import com.liferay.portlet.social.service.persistence.SocialActivityAchievementPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityLimitPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivitySettingPersistence;
import com.liferay.portlet.social.service.persistence.SocialRelationPersistence;
import com.liferay.portlet.social.service.persistence.SocialRequestPersistence;

import javax.sql.DataSource;

/**
 * The base implementation of the social request interpreter local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.impl.SocialRequestInterpreterLocalServiceImpl
 * @see com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil
 * @generated
 */
public abstract class SocialRequestInterpreterLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements SocialRequestInterpreterLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil} to access the social request interpreter local service.
	 */

	/**
	 * Returns the social activity local service.
	 *
	 * @return the social activity local service
	 */
	public SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	/**
	 * Sets the social activity local service.
	 *
	 * @param socialActivityLocalService the social activity local service
	 */
	public void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
	}

	/**
	 * Returns the social activity persistence.
	 *
	 * @return the social activity persistence
	 */
	public SocialActivityPersistence getSocialActivityPersistence() {
		return socialActivityPersistence;
	}

	/**
	 * Sets the social activity persistence.
	 *
	 * @param socialActivityPersistence the social activity persistence
	 */
	public void setSocialActivityPersistence(
		SocialActivityPersistence socialActivityPersistence) {
		this.socialActivityPersistence = socialActivityPersistence;
	}

	/**
	 * Returns the social activity finder.
	 *
	 * @return the social activity finder
	 */
	public SocialActivityFinder getSocialActivityFinder() {
		return socialActivityFinder;
	}

	/**
	 * Sets the social activity finder.
	 *
	 * @param socialActivityFinder the social activity finder
	 */
	public void setSocialActivityFinder(
		SocialActivityFinder socialActivityFinder) {
		this.socialActivityFinder = socialActivityFinder;
	}

	/**
	 * Returns the social activity achievement local service.
	 *
	 * @return the social activity achievement local service
	 */
	public SocialActivityAchievementLocalService getSocialActivityAchievementLocalService() {
		return socialActivityAchievementLocalService;
	}

	/**
	 * Sets the social activity achievement local service.
	 *
	 * @param socialActivityAchievementLocalService the social activity achievement local service
	 */
	public void setSocialActivityAchievementLocalService(
		SocialActivityAchievementLocalService socialActivityAchievementLocalService) {
		this.socialActivityAchievementLocalService = socialActivityAchievementLocalService;
	}

	/**
	 * Returns the social activity achievement persistence.
	 *
	 * @return the social activity achievement persistence
	 */
	public SocialActivityAchievementPersistence getSocialActivityAchievementPersistence() {
		return socialActivityAchievementPersistence;
	}

	/**
	 * Sets the social activity achievement persistence.
	 *
	 * @param socialActivityAchievementPersistence the social activity achievement persistence
	 */
	public void setSocialActivityAchievementPersistence(
		SocialActivityAchievementPersistence socialActivityAchievementPersistence) {
		this.socialActivityAchievementPersistence = socialActivityAchievementPersistence;
	}

	/**
	 * Returns the social activity counter local service.
	 *
	 * @return the social activity counter local service
	 */
	public SocialActivityCounterLocalService getSocialActivityCounterLocalService() {
		return socialActivityCounterLocalService;
	}

	/**
	 * Sets the social activity counter local service.
	 *
	 * @param socialActivityCounterLocalService the social activity counter local service
	 */
	public void setSocialActivityCounterLocalService(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {
		this.socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	/**
	 * Returns the social activity counter persistence.
	 *
	 * @return the social activity counter persistence
	 */
	public SocialActivityCounterPersistence getSocialActivityCounterPersistence() {
		return socialActivityCounterPersistence;
	}

	/**
	 * Sets the social activity counter persistence.
	 *
	 * @param socialActivityCounterPersistence the social activity counter persistence
	 */
	public void setSocialActivityCounterPersistence(
		SocialActivityCounterPersistence socialActivityCounterPersistence) {
		this.socialActivityCounterPersistence = socialActivityCounterPersistence;
	}

	/**
	 * Returns the social activity counter finder.
	 *
	 * @return the social activity counter finder
	 */
	public SocialActivityCounterFinder getSocialActivityCounterFinder() {
		return socialActivityCounterFinder;
	}

	/**
	 * Sets the social activity counter finder.
	 *
	 * @param socialActivityCounterFinder the social activity counter finder
	 */
	public void setSocialActivityCounterFinder(
		SocialActivityCounterFinder socialActivityCounterFinder) {
		this.socialActivityCounterFinder = socialActivityCounterFinder;
	}

	/**
	 * Returns the social activity interpreter local service.
	 *
	 * @return the social activity interpreter local service
	 */
	public SocialActivityInterpreterLocalService getSocialActivityInterpreterLocalService() {
		return socialActivityInterpreterLocalService;
	}

	/**
	 * Sets the social activity interpreter local service.
	 *
	 * @param socialActivityInterpreterLocalService the social activity interpreter local service
	 */
	public void setSocialActivityInterpreterLocalService(
		SocialActivityInterpreterLocalService socialActivityInterpreterLocalService) {
		this.socialActivityInterpreterLocalService = socialActivityInterpreterLocalService;
	}

	/**
	 * Returns the social activity limit local service.
	 *
	 * @return the social activity limit local service
	 */
	public SocialActivityLimitLocalService getSocialActivityLimitLocalService() {
		return socialActivityLimitLocalService;
	}

	/**
	 * Sets the social activity limit local service.
	 *
	 * @param socialActivityLimitLocalService the social activity limit local service
	 */
	public void setSocialActivityLimitLocalService(
		SocialActivityLimitLocalService socialActivityLimitLocalService) {
		this.socialActivityLimitLocalService = socialActivityLimitLocalService;
	}

	/**
	 * Returns the social activity limit persistence.
	 *
	 * @return the social activity limit persistence
	 */
	public SocialActivityLimitPersistence getSocialActivityLimitPersistence() {
		return socialActivityLimitPersistence;
	}

	/**
	 * Sets the social activity limit persistence.
	 *
	 * @param socialActivityLimitPersistence the social activity limit persistence
	 */
	public void setSocialActivityLimitPersistence(
		SocialActivityLimitPersistence socialActivityLimitPersistence) {
		this.socialActivityLimitPersistence = socialActivityLimitPersistence;
	}

	/**
	 * Returns the social activity setting local service.
	 *
	 * @return the social activity setting local service
	 */
	public SocialActivitySettingLocalService getSocialActivitySettingLocalService() {
		return socialActivitySettingLocalService;
	}

	/**
	 * Sets the social activity setting local service.
	 *
	 * @param socialActivitySettingLocalService the social activity setting local service
	 */
	public void setSocialActivitySettingLocalService(
		SocialActivitySettingLocalService socialActivitySettingLocalService) {
		this.socialActivitySettingLocalService = socialActivitySettingLocalService;
	}

	/**
	 * Returns the social activity setting remote service.
	 *
	 * @return the social activity setting remote service
	 */
	public SocialActivitySettingService getSocialActivitySettingService() {
		return socialActivitySettingService;
	}

	/**
	 * Sets the social activity setting remote service.
	 *
	 * @param socialActivitySettingService the social activity setting remote service
	 */
	public void setSocialActivitySettingService(
		SocialActivitySettingService socialActivitySettingService) {
		this.socialActivitySettingService = socialActivitySettingService;
	}

	/**
	 * Returns the social activity setting persistence.
	 *
	 * @return the social activity setting persistence
	 */
	public SocialActivitySettingPersistence getSocialActivitySettingPersistence() {
		return socialActivitySettingPersistence;
	}

	/**
	 * Sets the social activity setting persistence.
	 *
	 * @param socialActivitySettingPersistence the social activity setting persistence
	 */
	public void setSocialActivitySettingPersistence(
		SocialActivitySettingPersistence socialActivitySettingPersistence) {
		this.socialActivitySettingPersistence = socialActivitySettingPersistence;
	}

	/**
	 * Returns the social relation local service.
	 *
	 * @return the social relation local service
	 */
	public SocialRelationLocalService getSocialRelationLocalService() {
		return socialRelationLocalService;
	}

	/**
	 * Sets the social relation local service.
	 *
	 * @param socialRelationLocalService the social relation local service
	 */
	public void setSocialRelationLocalService(
		SocialRelationLocalService socialRelationLocalService) {
		this.socialRelationLocalService = socialRelationLocalService;
	}

	/**
	 * Returns the social relation persistence.
	 *
	 * @return the social relation persistence
	 */
	public SocialRelationPersistence getSocialRelationPersistence() {
		return socialRelationPersistence;
	}

	/**
	 * Sets the social relation persistence.
	 *
	 * @param socialRelationPersistence the social relation persistence
	 */
	public void setSocialRelationPersistence(
		SocialRelationPersistence socialRelationPersistence) {
		this.socialRelationPersistence = socialRelationPersistence;
	}

	/**
	 * Returns the social request local service.
	 *
	 * @return the social request local service
	 */
	public SocialRequestLocalService getSocialRequestLocalService() {
		return socialRequestLocalService;
	}

	/**
	 * Sets the social request local service.
	 *
	 * @param socialRequestLocalService the social request local service
	 */
	public void setSocialRequestLocalService(
		SocialRequestLocalService socialRequestLocalService) {
		this.socialRequestLocalService = socialRequestLocalService;
	}

	/**
	 * Returns the social request remote service.
	 *
	 * @return the social request remote service
	 */
	public SocialRequestService getSocialRequestService() {
		return socialRequestService;
	}

	/**
	 * Sets the social request remote service.
	 *
	 * @param socialRequestService the social request remote service
	 */
	public void setSocialRequestService(
		SocialRequestService socialRequestService) {
		this.socialRequestService = socialRequestService;
	}

	/**
	 * Returns the social request persistence.
	 *
	 * @return the social request persistence
	 */
	public SocialRequestPersistence getSocialRequestPersistence() {
		return socialRequestPersistence;
	}

	/**
	 * Sets the social request persistence.
	 *
	 * @param socialRequestPersistence the social request persistence
	 */
	public void setSocialRequestPersistence(
		SocialRequestPersistence socialRequestPersistence) {
		this.socialRequestPersistence = socialRequestPersistence;
	}

	/**
	 * Returns the social request interpreter local service.
	 *
	 * @return the social request interpreter local service
	 */
	public SocialRequestInterpreterLocalService getSocialRequestInterpreterLocalService() {
		return socialRequestInterpreterLocalService;
	}

	/**
	 * Sets the social request interpreter local service.
	 *
	 * @param socialRequestInterpreterLocalService the social request interpreter local service
	 */
	public void setSocialRequestInterpreterLocalService(
		SocialRequestInterpreterLocalService socialRequestInterpreterLocalService) {
		this.socialRequestInterpreterLocalService = socialRequestInterpreterLocalService;
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

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = InfrastructureUtil.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = SocialActivityLocalService.class)
	protected SocialActivityLocalService socialActivityLocalService;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityFinder.class)
	protected SocialActivityFinder socialActivityFinder;
	@BeanReference(type = SocialActivityAchievementLocalService.class)
	protected SocialActivityAchievementLocalService socialActivityAchievementLocalService;
	@BeanReference(type = SocialActivityAchievementPersistence.class)
	protected SocialActivityAchievementPersistence socialActivityAchievementPersistence;
	@BeanReference(type = SocialActivityCounterLocalService.class)
	protected SocialActivityCounterLocalService socialActivityCounterLocalService;
	@BeanReference(type = SocialActivityCounterPersistence.class)
	protected SocialActivityCounterPersistence socialActivityCounterPersistence;
	@BeanReference(type = SocialActivityCounterFinder.class)
	protected SocialActivityCounterFinder socialActivityCounterFinder;
	@BeanReference(type = SocialActivityInterpreterLocalService.class)
	protected SocialActivityInterpreterLocalService socialActivityInterpreterLocalService;
	@BeanReference(type = SocialActivityLimitLocalService.class)
	protected SocialActivityLimitLocalService socialActivityLimitLocalService;
	@BeanReference(type = SocialActivityLimitPersistence.class)
	protected SocialActivityLimitPersistence socialActivityLimitPersistence;
	@BeanReference(type = SocialActivitySettingLocalService.class)
	protected SocialActivitySettingLocalService socialActivitySettingLocalService;
	@BeanReference(type = SocialActivitySettingService.class)
	protected SocialActivitySettingService socialActivitySettingService;
	@BeanReference(type = SocialActivitySettingPersistence.class)
	protected SocialActivitySettingPersistence socialActivitySettingPersistence;
	@BeanReference(type = SocialRelationLocalService.class)
	protected SocialRelationLocalService socialRelationLocalService;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestLocalService.class)
	protected SocialRequestLocalService socialRequestLocalService;
	@BeanReference(type = SocialRequestService.class)
	protected SocialRequestService socialRequestService;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = SocialRequestInterpreterLocalService.class)
	protected SocialRequestInterpreterLocalService socialRequestInterpreterLocalService;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
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