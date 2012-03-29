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

package com.liferay.portlet.calendar.util.comparator;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.util.CalUtil;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Janghyun Kim
 * @author Samuel Kong
 */
public class EventTimeComparator implements Comparator<CalEvent> {

	public EventTimeComparator(TimeZone timeZone, Locale locale) {
		_timeZone = timeZone;
		_locale = locale;
	}

	public int compare(CalEvent event1, CalEvent event2) {
		boolean allDay1 = CalUtil.isAllDay(event1, _timeZone, _locale);
		boolean allDay2 = CalUtil.isAllDay(event2, _timeZone, _locale);

		if (allDay1 && allDay2) {
			return compareTitle(event1, event2);
		}
		else if (allDay1) {
			return -1;
		}
		else if (allDay2) {
			return 1;
		}

		int value = 0;

		if (event1.isRepeating() || event2.isRepeating()) {
			Date startDate1 = getNormaizedDate(
				event1.getStartDate(), event1.isTimeZoneSensitive());
			Date startDate2 = getNormaizedDate(
				event2.getStartDate(), event2.isTimeZoneSensitive());

			value = startDate1.compareTo(startDate2);

			if (value != 0) {
				return value;
			}

			Date endDate1 = getNormaizedDate(
				event1.getEndDate(), event1.isTimeZoneSensitive());
			Date endDate2 = getNormaizedDate(
				event2.getEndDate(), event2.isTimeZoneSensitive());

			value = endDate1.compareTo(endDate2);

			if (value != 0) {
				return value;
			}
		}
		else {
			Date startDate1 = getStartDate(event1, _timeZone);
			Date startDate2 = getStartDate(event2, _timeZone);

			value = startDate1.compareTo(startDate2);

			if (value != 0) {
				return value;
			}

			Date endDate1 = getEndDate(event1, _timeZone);
			Date endDate2 = getEndDate(event2, _timeZone);

			value = endDate1.compareTo(endDate2);

			if (value != 0) {
				return value;
			}
		}

		return compareTitle(event1, event2);
	}

	protected int compareTitle(CalEvent event1, CalEvent event2) {
		return event1.getTitle().toLowerCase().compareTo(
			event2.getTitle().toLowerCase());
	}

	protected Date getEndDate(CalEvent event, TimeZone timeZone) {
		if (event.isTimeZoneSensitive()) {
			return Time.getDate(CalUtil.getEndTime(event), timeZone);
		}
		else {
			return CalUtil.getEndTime(event);
		}
	}

	protected Date getNormaizedDate(Date date, boolean timeZoneSensitive) {
		Calendar cal = Calendar.getInstance();

		if (timeZoneSensitive) {
			cal = CalendarFactoryUtil.getCalendar(_timeZone);
		}
		else {
			cal = CalendarFactoryUtil.getCalendar();
		}

		cal.setTime(date);

		// Normalize Year, Month and Date to compare recurring time

		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 0);

		return Time.getDate(cal);
	}

	protected Date getStartDate(CalEvent event, TimeZone timeZone) {
		if (event.isTimeZoneSensitive()) {
			return Time.getDate(event.getStartDate(), timeZone);
		}
		else {
			return event.getStartDate();
		}
	}

	private Locale _locale;
	private TimeZone _timeZone;

}