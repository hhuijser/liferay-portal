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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletItemModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the PortletItem service. Represents a row in the &quot;PortletItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.PortletItemModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PortletItemImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletItemImpl
 * @see com.liferay.portal.model.PortletItem
 * @see com.liferay.portal.model.PortletItemModel
 * @generated
 */
public class PortletItemModelImpl extends BaseModelImpl<PortletItem>
	implements PortletItemModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a portlet item model instance should use the {@link com.liferay.portal.model.PortletItem} interface instead.
	 */
	public static final String TABLE_NAME = "PortletItem";
	public static final Object[][] TABLE_COLUMNS = {
			{ "portletItemId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "name", Types.VARCHAR },
			{ "portletId", Types.VARCHAR },
			{ "classNameId", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table PortletItem (portletItemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(255) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,portletId VARCHAR(75) null,classNameId LONG)";
	public static final String TABLE_SQL_DROP = "drop table PortletItem";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.PortletItem"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.PortletItem"),
			true);

	public Class<?> getModelClass() {
		return PortletItem.class;
	}

	public String getModelClassName() {
		return PortletItem.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PortletItem"));

	public PortletItemModelImpl() {
	}

	public long getPrimaryKey() {
		return _portletItemId;
	}

	public void setPrimaryKey(long primaryKey) {
		setPortletItemId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_portletItemId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public long getPortletItemId() {
		return _portletItemId;
	}

	public void setPortletItemId(long portletItemId) {
		_portletItemId = portletItemId;
	}

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

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	public String getPortletId() {
		if (_portletId == null) {
			return StringPool.BLANK;
		}
		else {
			return _portletId;
		}
	}

	public void setPortletId(String portletId) {
		if (_originalPortletId == null) {
			_originalPortletId = _portletId;
		}

		_portletId = portletId;
	}

	public String getOriginalPortletId() {
		return GetterUtil.getString(_originalPortletId);
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@Override
	public PortletItem toEscapedModel() {
		if (isEscapedModel()) {
			return (PortletItem)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (PortletItem)Proxy.newProxyInstance(_classLoader,
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
					PortletItem.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		PortletItemImpl portletItemImpl = new PortletItemImpl();

		portletItemImpl.setPortletItemId(getPortletItemId());
		portletItemImpl.setGroupId(getGroupId());
		portletItemImpl.setCompanyId(getCompanyId());
		portletItemImpl.setUserId(getUserId());
		portletItemImpl.setUserName(getUserName());
		portletItemImpl.setCreateDate(getCreateDate());
		portletItemImpl.setModifiedDate(getModifiedDate());
		portletItemImpl.setName(getName());
		portletItemImpl.setPortletId(getPortletId());
		portletItemImpl.setClassNameId(getClassNameId());

		portletItemImpl.resetOriginalValues();

		return portletItemImpl;
	}

	public int compareTo(PortletItem portletItem) {
		long primaryKey = portletItem.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PortletItem portletItem = null;

		try {
			portletItem = (PortletItem)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = portletItem.getPrimaryKey();

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
		PortletItemModelImpl portletItemModelImpl = this;

		portletItemModelImpl._originalGroupId = portletItemModelImpl._groupId;

		portletItemModelImpl._setOriginalGroupId = false;

		portletItemModelImpl._originalName = portletItemModelImpl._name;

		portletItemModelImpl._originalPortletId = portletItemModelImpl._portletId;

		portletItemModelImpl._originalClassNameId = portletItemModelImpl._classNameId;

		portletItemModelImpl._setOriginalClassNameId = false;
	}

	@Override
	public CacheModel<PortletItem> toCacheModel() {
		PortletItemCacheModel portletItemCacheModel = new PortletItemCacheModel();

		portletItemCacheModel.portletItemId = getPortletItemId();

		portletItemCacheModel.groupId = getGroupId();

		portletItemCacheModel.companyId = getCompanyId();

		portletItemCacheModel.userId = getUserId();

		portletItemCacheModel.userName = getUserName();

		String userName = portletItemCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			portletItemCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			portletItemCacheModel.createDate = createDate.getTime();
		}
		else {
			portletItemCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			portletItemCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			portletItemCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		portletItemCacheModel.name = getName();

		String name = portletItemCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			portletItemCacheModel.name = null;
		}

		portletItemCacheModel.portletId = getPortletId();

		String portletId = portletItemCacheModel.portletId;

		if ((portletId != null) && (portletId.length() == 0)) {
			portletItemCacheModel.portletId = null;
		}

		portletItemCacheModel.classNameId = getClassNameId();

		return portletItemCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{portletItemId=");
		sb.append(getPortletItemId());
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
		sb.append(", name=");
		sb.append(getName());
		sb.append(", portletId=");
		sb.append(getPortletId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.PortletItem");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>portletItemId</column-name><column-value><![CDATA[");
		sb.append(getPortletItemId());
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
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>portletId</column-name><column-value><![CDATA[");
		sb.append(getPortletId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = PortletItem.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			PortletItem.class
		};
	private long _portletItemId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _originalName;
	private String _portletId;
	private String _originalPortletId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private transient ExpandoBridge _expandoBridge;
	private PortletItem _escapedModelProxy;
}