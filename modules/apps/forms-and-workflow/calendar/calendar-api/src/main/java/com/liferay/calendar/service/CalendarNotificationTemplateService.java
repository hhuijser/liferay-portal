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

package com.liferay.calendar.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.notification.NotificationType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the remote service interface for CalendarNotificationTemplate. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Eduardo Lundgren
 * @see CalendarNotificationTemplateServiceUtil
 * @see com.liferay.calendar.service.base.CalendarNotificationTemplateServiceBaseImpl
 * @see com.liferay.calendar.service.impl.CalendarNotificationTemplateServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=calendar", "json.web.service.context.path=CalendarNotificationTemplate"}, service = CalendarNotificationTemplateService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CalendarNotificationTemplateService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CalendarNotificationTemplateServiceUtil} to access the calendar notification template remote service. Add custom service methods to {@link com.liferay.calendar.service.impl.CalendarNotificationTemplateServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CalendarNotificationTemplate addCalendarNotificationTemplate(
		long calendarId, NotificationType notificationType,
		java.lang.String notificationTypeSettings,
		NotificationTemplateType notificationTemplateType,
		java.lang.String subject, java.lang.String body,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public CalendarNotificationTemplate updateCalendarNotificationTemplate(
		long calendarNotificationTemplateId,
		java.lang.String notificationTypeSettings, java.lang.String subject,
		java.lang.String body, ServiceContext serviceContext)
		throws PortalException;
}