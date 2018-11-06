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

package com.liferay.calendar.internal.exportimport.data.handler;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author arthurchan35
 */
@Component(
	property = "javax.portlet.name=" + CalendarPortletKeys.CALENDAR,
	service = PortletDataHandler.class
)
public class CalendarPortletDataHandler extends BasePortletDataHandler {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link CalendarAdminPortletDataHandler#CLASS_NAMES}
	 */
	@Deprecated
	public static final String[] CLASS_NAMES = {
		Calendar.class.getName(), CalendarBooking.class.getName(),
		CalendarNotificationTemplate.class.getName(),
		CalendarResource.class.getName()
	};

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link CalendarAdminPortletDataHandler#NAMESPACE}
	 */
	@Deprecated
	public static final String NAMESPACE = "calendar";

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link CalendarAdminPortletDataHandler#SCHEMA_VERSION}
	 */
	@Deprecated
	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public PortletPreferences deleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return _calendarAdminPortletDataHandler.deleteData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	public String exportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		return _calendarAdminPortletDataHandler.exportData(
			portletDataContext, portletId, portletPreferences);
	}

	@Override
	public String[] getClassNames() {
		return _calendarAdminPortletDataHandler.getClassNames();
	}

	@Override
	public String getNamespace() {
		return _calendarAdminPortletDataHandler.getNamespace();
	}

	@Override
	public String getSchemaVersion() {
		return _calendarAdminPortletDataHandler.getSchemaVersion();
	}

	@Override
	public PortletPreferences importData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws PortletDataException {

		return _calendarAdminPortletDataHandler.importData(
			portletDataContext, portletId, portletPreferences, data);
	}

	@Override
	public void prepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		_calendarAdminPortletDataHandler.prepareManifestSummary(
			portletDataContext, portletPreferences);
	}

	@Activate
	protected void activate() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataLocalized(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(Calendar.class),
			new StagedModelType(CalendarBooking.class),
			new StagedModelType(CalendarNotificationTemplate.class),
			new StagedModelType(CalendarResource.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				getNamespace(), "calendars", true, false, null,
				Calendar.class.getName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "calendar-resources", true, false, null,
				CalendarResource.class.getName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "calendar-bookings", true, false, null,
				CalendarBooking.class.getName()),
			new PortletDataHandlerBoolean(
				getNamespace(), "calendar-notification-templates", true, false,
				new PortletDataHandlerBoolean[] {
					new PortletDataHandlerBoolean(
						getNamespace(), "referenced-content")
				},
				CalendarNotificationTemplate.class.getName()));
		setStagingControls(getExportControls());
	}

	@Reference(
		target = "(javax.portlet.name=" + CalendarPortletKeys.CALENDAR_ADMIN + ")"
	)
	private PortletDataHandler _calendarAdminPortletDataHandler;

}