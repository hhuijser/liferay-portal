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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Website;
import com.liferay.portal.model.WebsiteModel;
import com.liferay.portal.model.WebsiteSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the Website service. Represents a row in the &quot;Website&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.WebsiteModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WebsiteImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebsiteImpl
 * @see com.liferay.portal.model.Website
 * @see com.liferay.portal.model.WebsiteModel
 * @generated
 */
@JSON(strict = true)
public class WebsiteModelImpl extends BaseModelImpl<Website>
	implements WebsiteModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a website model instance should use the {@link com.liferay.portal.model.Website} interface instead.
	 */
	public static final String TABLE_NAME = "Website";
	public static final Object[][] TABLE_COLUMNS = {
			{ "websiteId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "url", Types.VARCHAR },
			{ "typeId", Types.INTEGER },
			{ "primary_", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table Website (websiteId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,url STRING null,typeId INTEGER,primary_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table Website";
	public static final String ORDER_BY_JPQL = " ORDER BY website.createDate ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Website.createDate ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Website"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Website"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.Website"),
			true);
	public static long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static long CLASSPK_COLUMN_BITMASK = 2L;
	public static long COMPANYID_COLUMN_BITMASK = 4L;
	public static long PRIMARY_COLUMN_BITMASK = 8L;
	public static long USERID_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Website toModel(WebsiteSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Website model = new WebsiteImpl();

		model.setWebsiteId(soapModel.getWebsiteId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setUrl(soapModel.getUrl());
		model.setTypeId(soapModel.getTypeId());
		model.setPrimary(soapModel.getPrimary());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Website> toModels(WebsiteSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Website> models = new ArrayList<Website>(soapModels.length);

		for (WebsiteSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Website"));

	public WebsiteModelImpl() {
	}

	public long getPrimaryKey() {
		return _websiteId;
	}

	public void setPrimaryKey(long primaryKey) {
		setWebsiteId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_websiteId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return Website.class;
	}

	public String getModelClassName() {
		return Website.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("websiteId", getWebsiteId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("url", getUrl());
		attributes.put("typeId", getTypeId());
		attributes.put("primary", getPrimary());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long websiteId = (Long)attributes.get("websiteId");

		if (websiteId != null) {
			setWebsiteId(websiteId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		Integer typeId = (Integer)attributes.get("typeId");

		if (typeId != null) {
			setTypeId(typeId);
		}

		Boolean primary = (Boolean)attributes.get("primary");

		if (primary != null) {
			setPrimary(primary);
		}
	}

	@JSON
	public long getWebsiteId() {
		return _websiteId;
	}

	public void setWebsiteId(long websiteId) {
		_websiteId = websiteId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

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

	@JSON
	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
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
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@JSON
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
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
	public int getTypeId() {
		return _typeId;
	}

	public void setTypeId(int typeId) {
		_typeId = typeId;
	}

	@JSON
	public boolean getPrimary() {
		return _primary;
	}

	public boolean isPrimary() {
		return _primary;
	}

	public void setPrimary(boolean primary) {
		_columnBitmask |= PRIMARY_COLUMN_BITMASK;

		if (!_setOriginalPrimary) {
			_setOriginalPrimary = true;

			_originalPrimary = _primary;
		}

		_primary = primary;
	}

	public boolean getOriginalPrimary() {
		return _originalPrimary;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			Website.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Website toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Website)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Website toUnescapedModel() {
		if (ProxyUtil.isProxyClass(getClass())) {
			InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(this);

			AutoEscapeBeanHandler autoEscapeBeanHandler = (AutoEscapeBeanHandler)invocationHandler;

			_unescapedModel = (Website)autoEscapeBeanHandler.getBean();
		}
		else {
			_unescapedModel = (Website)this;
		}

		return _unescapedModel;
	}

	@Override
	public Object clone() {
		WebsiteImpl websiteImpl = new WebsiteImpl();

		websiteImpl.setWebsiteId(getWebsiteId());
		websiteImpl.setCompanyId(getCompanyId());
		websiteImpl.setUserId(getUserId());
		websiteImpl.setUserName(getUserName());
		websiteImpl.setCreateDate(getCreateDate());
		websiteImpl.setModifiedDate(getModifiedDate());
		websiteImpl.setClassNameId(getClassNameId());
		websiteImpl.setClassPK(getClassPK());
		websiteImpl.setUrl(getUrl());
		websiteImpl.setTypeId(getTypeId());
		websiteImpl.setPrimary(getPrimary());

		websiteImpl.resetOriginalValues();

		return websiteImpl;
	}

	public int compareTo(Website website) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(), website.getCreateDate());

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

		Website website = null;

		try {
			website = (Website)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = website.getPrimaryKey();

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
		WebsiteModelImpl websiteModelImpl = this;

		websiteModelImpl._originalCompanyId = websiteModelImpl._companyId;

		websiteModelImpl._setOriginalCompanyId = false;

		websiteModelImpl._originalUserId = websiteModelImpl._userId;

		websiteModelImpl._setOriginalUserId = false;

		websiteModelImpl._originalClassNameId = websiteModelImpl._classNameId;

		websiteModelImpl._setOriginalClassNameId = false;

		websiteModelImpl._originalClassPK = websiteModelImpl._classPK;

		websiteModelImpl._setOriginalClassPK = false;

		websiteModelImpl._originalPrimary = websiteModelImpl._primary;

		websiteModelImpl._setOriginalPrimary = false;

		websiteModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Website> toCacheModel() {
		WebsiteCacheModel websiteCacheModel = new WebsiteCacheModel();

		websiteCacheModel.websiteId = getWebsiteId();

		websiteCacheModel.companyId = getCompanyId();

		websiteCacheModel.userId = getUserId();

		websiteCacheModel.userName = getUserName();

		String userName = websiteCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			websiteCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			websiteCacheModel.createDate = createDate.getTime();
		}
		else {
			websiteCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			websiteCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			websiteCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		websiteCacheModel.classNameId = getClassNameId();

		websiteCacheModel.classPK = getClassPK();

		websiteCacheModel.url = getUrl();

		String url = websiteCacheModel.url;

		if ((url != null) && (url.length() == 0)) {
			websiteCacheModel.url = null;
		}

		websiteCacheModel.typeId = getTypeId();

		websiteCacheModel.primary = getPrimary();

		return websiteCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{websiteId=");
		sb.append(getWebsiteId());
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
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", url=");
		sb.append(getUrl());
		sb.append(", typeId=");
		sb.append(getTypeId());
		sb.append(", primary=");
		sb.append(getPrimary());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Website");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>websiteId</column-name><column-value><![CDATA[");
		sb.append(getWebsiteId());
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
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>url</column-name><column-value><![CDATA[");
		sb.append(getUrl());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeId</column-name><column-value><![CDATA[");
		sb.append(getTypeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>primary</column-name><column-value><![CDATA[");
		sb.append(getPrimary());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Website.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			Website.class
		};
	private long _websiteId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _url;
	private int _typeId;
	private boolean _primary;
	private boolean _originalPrimary;
	private boolean _setOriginalPrimary;
	private long _columnBitmask;
	private Website _escapedModel;
	private Website _unescapedModel;
}