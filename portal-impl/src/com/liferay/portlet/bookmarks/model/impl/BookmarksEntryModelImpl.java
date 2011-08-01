/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntryModel;
import com.liferay.portlet.bookmarks.model.BookmarksEntrySoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the BookmarksEntry service. Represents a row in the &quot;BookmarksEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.bookmarks.model.BookmarksEntryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link BookmarksEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryImpl
 * @see com.liferay.portlet.bookmarks.model.BookmarksEntry
 * @see com.liferay.portlet.bookmarks.model.BookmarksEntryModel
 * @generated
 */
@JSON(strict = true)
public class BookmarksEntryModelImpl extends BaseModelImpl<BookmarksEntry>
	implements BookmarksEntryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a bookmarks entry model instance should use the {@link com.liferay.portlet.bookmarks.model.BookmarksEntry} interface instead.
	 */
	public static final String TABLE_NAME = "BookmarksEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "entryId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "folderId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "url", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "visits", Types.INTEGER },
			{ "priority", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table BookmarksEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(255) null,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(255) null,url STRING null,description STRING null,visits INTEGER,priority INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table BookmarksEntry";
	public static final String ORDER_BY_JPQL = " ORDER BY bookmarksEntry.folderId ASC, bookmarksEntry.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY BookmarksEntry.folderId ASC, BookmarksEntry.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.bookmarks.model.BookmarksEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.bookmarks.model.BookmarksEntry"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static BookmarksEntry toModel(BookmarksEntrySoap soapModel) {
		BookmarksEntry model = new BookmarksEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setEntryId(soapModel.getEntryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFolderId(soapModel.getFolderId());
		model.setName(soapModel.getName());
		model.setUrl(soapModel.getUrl());
		model.setDescription(soapModel.getDescription());
		model.setVisits(soapModel.getVisits());
		model.setPriority(soapModel.getPriority());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<BookmarksEntry> toModels(BookmarksEntrySoap[] soapModels) {
		List<BookmarksEntry> models = new ArrayList<BookmarksEntry>(soapModels.length);

		for (BookmarksEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public Class<?> getModelClass() {
		return BookmarksEntry.class;
	}

	public String getModelClassName() {
		return BookmarksEntry.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.bookmarks.model.BookmarksEntry"));

	public BookmarksEntryModelImpl() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long primaryKey) {
		setEntryId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_entryId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@JSON
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	@JSON
	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@JSON
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@JSON
	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;
	}

	@JSON
	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	@JSON
	public String getUrl() {
		if (_url == null) {
			return StringPool.BLANK;
		}
		else {
			return _url;
		}
	}

	public void setUrl(String url) {
		_url = url;
	}

	@JSON
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	public int getVisits() {
		return _visits;
	}

	public void setVisits(int visits) {
		_visits = visits;
	}

	@JSON
	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	@Override
	public BookmarksEntry toEscapedModel() {
		if (isEscapedModel()) {
			return (BookmarksEntry)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (BookmarksEntry)Proxy.newProxyInstance(_classLoader,
						_escapedModelProxyInterfaces,
						new AutoEscapeBeanHandler(this));
			}

			return _escapedModelProxy;
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					BookmarksEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		BookmarksEntryImpl bookmarksEntryImpl = new BookmarksEntryImpl();

		bookmarksEntryImpl.setUuid(getUuid());
		bookmarksEntryImpl.setEntryId(getEntryId());
		bookmarksEntryImpl.setGroupId(getGroupId());
		bookmarksEntryImpl.setCompanyId(getCompanyId());
		bookmarksEntryImpl.setUserId(getUserId());
		bookmarksEntryImpl.setUserName(getUserName());
		bookmarksEntryImpl.setCreateDate(getCreateDate());
		bookmarksEntryImpl.setModifiedDate(getModifiedDate());
		bookmarksEntryImpl.setFolderId(getFolderId());
		bookmarksEntryImpl.setName(getName());
		bookmarksEntryImpl.setUrl(getUrl());
		bookmarksEntryImpl.setDescription(getDescription());
		bookmarksEntryImpl.setVisits(getVisits());
		bookmarksEntryImpl.setPriority(getPriority());

		bookmarksEntryImpl.resetOriginalValues();

		return bookmarksEntryImpl;
	}

	public int compareTo(BookmarksEntry bookmarksEntry) {
		int value = 0;

		if (getFolderId() < bookmarksEntry.getFolderId()) {
			value = -1;
		}
		else if (getFolderId() > bookmarksEntry.getFolderId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getName().toLowerCase()
					.compareTo(bookmarksEntry.getName().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		BookmarksEntry bookmarksEntry = null;

		try {
			bookmarksEntry = (BookmarksEntry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = bookmarksEntry.getPrimaryKey();

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
	public void resetOriginalValues() {
		BookmarksEntryModelImpl bookmarksEntryModelImpl = this;

		bookmarksEntryModelImpl._originalUuid = bookmarksEntryModelImpl._uuid;

		bookmarksEntryModelImpl._originalGroupId = bookmarksEntryModelImpl._groupId;

		bookmarksEntryModelImpl._setOriginalGroupId = false;
	}

	@Override
	public CacheModel<BookmarksEntry> toCacheModel() {
		BookmarksEntryCacheModel bookmarksEntryCacheModel = new BookmarksEntryCacheModel();

		bookmarksEntryCacheModel.uuid = getUuid();

		String uuid = bookmarksEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			bookmarksEntryCacheModel.uuid = null;
		}

		bookmarksEntryCacheModel.entryId = getEntryId();

		bookmarksEntryCacheModel.groupId = getGroupId();

		bookmarksEntryCacheModel.companyId = getCompanyId();

		bookmarksEntryCacheModel.userId = getUserId();

		bookmarksEntryCacheModel.userName = getUserName();

		String userName = bookmarksEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			bookmarksEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			bookmarksEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			bookmarksEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			bookmarksEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			bookmarksEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		bookmarksEntryCacheModel.folderId = getFolderId();

		bookmarksEntryCacheModel.name = getName();

		String name = bookmarksEntryCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			bookmarksEntryCacheModel.name = null;
		}

		bookmarksEntryCacheModel.url = getUrl();

		String url = bookmarksEntryCacheModel.url;

		if ((url != null) && (url.length() == 0)) {
			bookmarksEntryCacheModel.url = null;
		}

		bookmarksEntryCacheModel.description = getDescription();

		String description = bookmarksEntryCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			bookmarksEntryCacheModel.description = null;
		}

		bookmarksEntryCacheModel.visits = getVisits();

		bookmarksEntryCacheModel.priority = getPriority();

		return bookmarksEntryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", entryId=");
		sb.append(getEntryId());
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
		sb.append(", folderId=");
		sb.append(getFolderId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", url=");
		sb.append(getUrl());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", visits=");
		sb.append(getVisits());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(46);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.bookmarks.model.BookmarksEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>entryId</column-name><column-value><![CDATA[");
		sb.append(getEntryId());
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
			"<column><column-name>folderId</column-name><column-value><![CDATA[");
		sb.append(getFolderId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>url</column-name><column-value><![CDATA[");
		sb.append(getUrl());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>visits</column-name><column-value><![CDATA[");
		sb.append(getVisits());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = BookmarksEntry.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			BookmarksEntry.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _entryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private String _name;
	private String _url;
	private String _description;
	private int _visits;
	private int _priority;
	private transient ExpandoBridge _expandoBridge;
	private BookmarksEntry _escapedModelProxy;
}