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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the remote service interface for LayoutSetPrototype. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototypeServiceUtil
 * @see com.liferay.portal.service.base.LayoutSetPrototypeServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutSetPrototypeServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutSetPrototypeService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetPrototypeServiceUtil} to access the layout set prototype remote service. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutSetPrototypeServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* @deprecated As of 7.0.0, replaced by {@link #addLayoutSetPrototype(Map,
	Map, boolean, boolean, ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		boolean layoutsUpdateable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, boolean layoutsUpdateable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.LayoutSetPrototype fetchLayoutSetPrototype(
		long layoutSetPrototypeId) throws PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.LayoutSetPrototype getLayoutSetPrototype(
		long layoutSetPrototypeId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> search(
		long companyId, java.lang.Boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.LayoutSetPrototype> obc)
		throws PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#updateLayoutSetPrototype(long, Map, Map, boolean, boolean,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description, boolean active,
		boolean layoutsUpdateable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, boolean layoutsUpdateable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId, java.lang.String settings)
		throws PortalException;
}