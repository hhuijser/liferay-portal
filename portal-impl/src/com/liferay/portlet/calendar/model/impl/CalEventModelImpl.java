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

package com.liferay.portlet.calendar.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventModel;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the CalEvent service. Represents a row in the &quot;CalEvent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.calendar.model.CalEventModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CalEventImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CalEventImpl
 * @see com.liferay.portlet.calendar.model.CalEvent
 * @see com.liferay.portlet.calendar.model.CalEventModel
 * @generated
 */
public class CalEventModelImpl extends BaseModelImpl<CalEvent>
	implements CalEventModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cal event model instance should use the {@link com.liferay.portlet.calendar.model.CalEvent} interface instead.
	 */
	public static final String TABLE_NAME = "CalEvent";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "eventId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "title", Types.VARCHAR },
			{ "description", Types.CLOB },
			{ "location", Types.VARCHAR },
			{ "startDate", Types.TIMESTAMP },
			{ "endDate", Types.TIMESTAMP },
			{ "durationHour", Types.INTEGER },
			{ "durationMinute", Types.INTEGER },
			{ "allDay", Types.BOOLEAN },
			{ "timeZoneSensitive", Types.BOOLEAN },
			{ "type_", Types.VARCHAR },
			{ "repeating", Types.BOOLEAN },
			{ "recurrence", Types.CLOB },
			{ "remindBy", Types.INTEGER },
			{ "firstReminder", Types.INTEGER },
			{ "secondReminder", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table CalEvent (uuid_ VARCHAR(75) null,eventId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(75) null,description TEXT null,location STRING null,startDate DATE null,endDate DATE null,durationHour INTEGER,durationMinute INTEGER,allDay BOOLEAN,timeZoneSensitive BOOLEAN,type_ VARCHAR(75) null,repeating BOOLEAN,recurrence TEXT null,remindBy INTEGER,firstReminder INTEGER,secondReminder INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table CalEvent";
	public static final String ORDER_BY_JPQL = " ORDER BY calEvent.startDate ASC, calEvent.title ASC";
	public static final String ORDER_BY_SQL = " ORDER BY CalEvent.startDate ASC, CalEvent.title ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.calendar.model.CalEvent"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.calendar.model.CalEvent"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.calendar.model.CalEvent"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long GROUPID_COLUMN_BITMASK = 2L;
	public static long REMINDBY_COLUMN_BITMASK = 4L;
	public static long REPEATING_COLUMN_BITMASK = 8L;
	public static long TYPE_COLUMN_BITMASK = 16L;
	public static long UUID_COLUMN_BITMASK = 32L;
	public static long STARTDATE_COLUMN_BITMASK = 64L;
	public static long TITLE_COLUMN_BITMASK = 128L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.calendar.model.CalEvent"));

	public CalEventModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _eventId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setEventId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _eventId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CalEvent.class;
	}

	@Override
	public String getModelClassName() {
		return CalEvent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("eventId", getEventId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("description", getDescription());
		attributes.put("location", getLocation());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("durationHour", getDurationHour());
		attributes.put("durationMinute", getDurationMinute());
		attributes.put("allDay", getAllDay());
		attributes.put("timeZoneSensitive", getTimeZoneSensitive());
		attributes.put("type", getType());
		attributes.put("repeating", getRepeating());
		attributes.put("recurrence", getRecurrence());
		attributes.put("remindBy", getRemindBy());
		attributes.put("firstReminder", getFirstReminder());
		attributes.put("secondReminder", getSecondReminder());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long eventId = (Long)attributes.get("eventId");

		if (eventId != null) {
			setEventId(eventId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String location = (String)attributes.get("location");

		if (location != null) {
			setLocation(location);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Integer durationHour = (Integer)attributes.get("durationHour");

		if (durationHour != null) {
			setDurationHour(durationHour);
		}

		Integer durationMinute = (Integer)attributes.get("durationMinute");

		if (durationMinute != null) {
			setDurationMinute(durationMinute);
		}

		Boolean allDay = (Boolean)attributes.get("allDay");

		if (allDay != null) {
			setAllDay(allDay);
		}

		Boolean timeZoneSensitive = (Boolean)attributes.get("timeZoneSensitive");

		if (timeZoneSensitive != null) {
			setTimeZoneSensitive(timeZoneSensitive);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean repeating = (Boolean)attributes.get("repeating");

		if (repeating != null) {
			setRepeating(repeating);
		}

		String recurrence = (String)attributes.get("recurrence");

		if (recurrence != null) {
			setRecurrence(recurrence);
		}

		Integer remindBy = (Integer)attributes.get("remindBy");

		if (remindBy != null) {
			setRemindBy(remindBy);
		}

		Integer firstReminder = (Integer)attributes.get("firstReminder");

		if (firstReminder != null) {
			setFirstReminder(firstReminder);
		}

		Integer secondReminder = (Integer)attributes.get("secondReminder");

		if (secondReminder != null) {
			setSecondReminder(secondReminder);
		}
	}

	@Override
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getEventId() {
		return _eventId;
	}

	@Override
	public void setEventId(long eventId) {
		_eventId = eventId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() throws SystemException {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@Override
	public String getTitle() {
		if (_title == null) {
			return StringPool.BLANK;
		}
		else {
			return _title;
		}
	}

	@Override
	public void setTitle(String title) {
		_columnBitmask = -1L;

		_title = title;
	}

	@Override
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public String getLocation() {
		if (_location == null) {
			return StringPool.BLANK;
		}
		else {
			return _location;
		}
	}

	@Override
	public void setLocation(String location) {
		_location = location;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		_columnBitmask = -1L;

		_startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@Override
	public int getDurationHour() {
		return _durationHour;
	}

	@Override
	public void setDurationHour(int durationHour) {
		_durationHour = durationHour;
	}

	@Override
	public int getDurationMinute() {
		return _durationMinute;
	}

	@Override
	public void setDurationMinute(int durationMinute) {
		_durationMinute = durationMinute;
	}

	@Override
	public boolean getAllDay() {
		return _allDay;
	}

	@Override
	public boolean isAllDay() {
		return _allDay;
	}

	@Override
	public void setAllDay(boolean allDay) {
		_allDay = allDay;
	}

	@Override
	public boolean getTimeZoneSensitive() {
		return _timeZoneSensitive;
	}

	@Override
	public boolean isTimeZoneSensitive() {
		return _timeZoneSensitive;
	}

	@Override
	public void setTimeZoneSensitive(boolean timeZoneSensitive) {
		_timeZoneSensitive = timeZoneSensitive;
	}

	@Override
	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	@Override
	public void setType(String type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (_originalType == null) {
			_originalType = _type;
		}

		_type = type;
	}

	public String getOriginalType() {
		return GetterUtil.getString(_originalType);
	}

	@Override
	public boolean getRepeating() {
		return _repeating;
	}

	@Override
	public boolean isRepeating() {
		return _repeating;
	}

	@Override
	public void setRepeating(boolean repeating) {
		_columnBitmask |= REPEATING_COLUMN_BITMASK;

		if (!_setOriginalRepeating) {
			_setOriginalRepeating = true;

			_originalRepeating = _repeating;
		}

		_repeating = repeating;
	}

	public boolean getOriginalRepeating() {
		return _originalRepeating;
	}

	@Override
	public String getRecurrence() {
		if (_recurrence == null) {
			return StringPool.BLANK;
		}
		else {
			return _recurrence;
		}
	}

	@Override
	public void setRecurrence(String recurrence) {
		_recurrence = recurrence;
	}

	@Override
	public int getRemindBy() {
		return _remindBy;
	}

	@Override
	public void setRemindBy(int remindBy) {
		_columnBitmask |= REMINDBY_COLUMN_BITMASK;

		if (!_setOriginalRemindBy) {
			_setOriginalRemindBy = true;

			_originalRemindBy = _remindBy;
		}

		_remindBy = remindBy;
	}

	public int getOriginalRemindBy() {
		return _originalRemindBy;
	}

	@Override
	public int getFirstReminder() {
		return _firstReminder;
	}

	@Override
	public void setFirstReminder(int firstReminder) {
		_firstReminder = firstReminder;
	}

	@Override
	public int getSecondReminder() {
		return _secondReminder;
	}

	@Override
	public void setSecondReminder(int secondReminder) {
		_secondReminder = secondReminder;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				CalEvent.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CalEvent.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CalEvent toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CalEvent)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CalEventImpl calEventImpl = new CalEventImpl();

		calEventImpl.setUuid(getUuid());
		calEventImpl.setEventId(getEventId());
		calEventImpl.setGroupId(getGroupId());
		calEventImpl.setCompanyId(getCompanyId());
		calEventImpl.setUserId(getUserId());
		calEventImpl.setUserName(getUserName());
		calEventImpl.setCreateDate(getCreateDate());
		calEventImpl.setModifiedDate(getModifiedDate());
		calEventImpl.setTitle(getTitle());
		calEventImpl.setDescription(getDescription());
		calEventImpl.setLocation(getLocation());
		calEventImpl.setStartDate(getStartDate());
		calEventImpl.setEndDate(getEndDate());
		calEventImpl.setDurationHour(getDurationHour());
		calEventImpl.setDurationMinute(getDurationMinute());
		calEventImpl.setAllDay(getAllDay());
		calEventImpl.setTimeZoneSensitive(getTimeZoneSensitive());
		calEventImpl.setType(getType());
		calEventImpl.setRepeating(getRepeating());
		calEventImpl.setRecurrence(getRecurrence());
		calEventImpl.setRemindBy(getRemindBy());
		calEventImpl.setFirstReminder(getFirstReminder());
		calEventImpl.setSecondReminder(getSecondReminder());

		calEventImpl.resetOriginalValues();

		return calEventImpl;
	}

	@Override
	public int compareTo(CalEvent calEvent) {
		int value = 0;

		value = DateUtil.compareTo(getStartDate(), calEvent.getStartDate());

		if (value != 0) {
			return value;
		}

		value = getTitle().compareToIgnoreCase(calEvent.getTitle());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CalEvent)) {
			return false;
		}

		CalEvent calEvent = (CalEvent)obj;

		long primaryKey = calEvent.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		CalEventModelImpl calEventModelImpl = this;

		calEventModelImpl._originalUuid = calEventModelImpl._uuid;

		calEventModelImpl._originalGroupId = calEventModelImpl._groupId;

		calEventModelImpl._setOriginalGroupId = false;

		calEventModelImpl._originalCompanyId = calEventModelImpl._companyId;

		calEventModelImpl._setOriginalCompanyId = false;

		calEventModelImpl._originalType = calEventModelImpl._type;

		calEventModelImpl._originalRepeating = calEventModelImpl._repeating;

		calEventModelImpl._setOriginalRepeating = false;

		calEventModelImpl._originalRemindBy = calEventModelImpl._remindBy;

		calEventModelImpl._setOriginalRemindBy = false;

		calEventModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CalEvent> toCacheModel() {
		CalEventCacheModel calEventCacheModel = new CalEventCacheModel();

		calEventCacheModel.uuid = getUuid();

		String uuid = calEventCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			calEventCacheModel.uuid = null;
		}

		calEventCacheModel.eventId = getEventId();

		calEventCacheModel.groupId = getGroupId();

		calEventCacheModel.companyId = getCompanyId();

		calEventCacheModel.userId = getUserId();

		calEventCacheModel.userName = getUserName();

		String userName = calEventCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			calEventCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			calEventCacheModel.createDate = createDate.getTime();
		}
		else {
			calEventCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			calEventCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			calEventCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		calEventCacheModel.title = getTitle();

		String title = calEventCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			calEventCacheModel.title = null;
		}

		calEventCacheModel.description = getDescription();

		String description = calEventCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			calEventCacheModel.description = null;
		}

		calEventCacheModel.location = getLocation();

		String location = calEventCacheModel.location;

		if ((location != null) && (location.length() == 0)) {
			calEventCacheModel.location = null;
		}

		Date startDate = getStartDate();

		if (startDate != null) {
			calEventCacheModel.startDate = startDate.getTime();
		}
		else {
			calEventCacheModel.startDate = Long.MIN_VALUE;
		}

		Date endDate = getEndDate();

		if (endDate != null) {
			calEventCacheModel.endDate = endDate.getTime();
		}
		else {
			calEventCacheModel.endDate = Long.MIN_VALUE;
		}

		calEventCacheModel.durationHour = getDurationHour();

		calEventCacheModel.durationMinute = getDurationMinute();

		calEventCacheModel.allDay = getAllDay();

		calEventCacheModel.timeZoneSensitive = getTimeZoneSensitive();

		calEventCacheModel.type = getType();

		String type = calEventCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			calEventCacheModel.type = null;
		}

		calEventCacheModel.repeating = getRepeating();

		calEventCacheModel.recurrence = getRecurrence();

		String recurrence = calEventCacheModel.recurrence;

		if ((recurrence != null) && (recurrence.length() == 0)) {
			calEventCacheModel.recurrence = null;
		}

		calEventCacheModel.remindBy = getRemindBy();

		calEventCacheModel.firstReminder = getFirstReminder();

		calEventCacheModel.secondReminder = getSecondReminder();

		return calEventCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", eventId=");
		sb.append(getEventId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", location=");
		sb.append(getLocation());
		sb.append(", startDate=");
		sb.append(getStartDate());
		sb.append(", endDate=");
		sb.append(getEndDate());
		sb.append(", durationHour=");
		sb.append(getDurationHour());
		sb.append(", durationMinute=");
		sb.append(getDurationMinute());
		sb.append(", allDay=");
		sb.append(getAllDay());
		sb.append(", timeZoneSensitive=");
		sb.append(getTimeZoneSensitive());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", repeating=");
		sb.append(getRepeating());
		sb.append(", recurrence=");
		sb.append(getRecurrence());
		sb.append(", remindBy=");
		sb.append(getRemindBy());
		sb.append(", firstReminder=");
		sb.append(getFirstReminder());
		sb.append(", secondReminder=");
		sb.append(getSecondReminder());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(73);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.calendar.model.CalEvent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>eventId</column-name><column-value><![CDATA[");
		sb.append(getEventId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>location</column-name><column-value><![CDATA[");
		sb.append(getLocation());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>startDate</column-name><column-value><![CDATA[");
		sb.append(getStartDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>endDate</column-name><column-value><![CDATA[");
		sb.append(getEndDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>durationHour</column-name><column-value><![CDATA[");
		sb.append(getDurationHour());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>durationMinute</column-name><column-value><![CDATA[");
		sb.append(getDurationMinute());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allDay</column-name><column-value><![CDATA[");
		sb.append(getAllDay());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>timeZoneSensitive</column-name><column-value><![CDATA[");
		sb.append(getTimeZoneSensitive());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>repeating</column-name><column-value><![CDATA[");
		sb.append(getRepeating());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>recurrence</column-name><column-value><![CDATA[");
		sb.append(getRecurrence());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>remindBy</column-name><column-value><![CDATA[");
		sb.append(getRemindBy());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>firstReminder</column-name><column-value><![CDATA[");
		sb.append(getFirstReminder());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>secondReminder</column-name><column-value><![CDATA[");
		sb.append(getSecondReminder());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = CalEvent.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			CalEvent.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _eventId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private String _location;
	private Date _startDate;
	private Date _endDate;
	private int _durationHour;
	private int _durationMinute;
	private boolean _allDay;
	private boolean _timeZoneSensitive;
	private String _type;
	private String _originalType;
	private boolean _repeating;
	private boolean _originalRepeating;
	private boolean _setOriginalRepeating;
	private String _recurrence;
	private int _remindBy;
	private int _originalRemindBy;
	private boolean _setOriginalRemindBy;
	private int _firstReminder;
	private int _secondReminder;
	private long _columnBitmask;
	private CalEvent _escapedModel;
}