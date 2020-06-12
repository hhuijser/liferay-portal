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

package com.liferay.site.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.model.SiteFriendlyURLModel;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the SiteFriendlyURL service. Represents a row in the &quot;SiteFriendlyURL&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>SiteFriendlyURLModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SiteFriendlyURLImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURLImpl
 * @generated
 */
public class SiteFriendlyURLModelImpl
	extends BaseModelImpl<SiteFriendlyURL> implements SiteFriendlyURLModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a site friendly url model instance should use the <code>SiteFriendlyURL</code> interface instead.
	 */
	public static final String TABLE_NAME = "SiteFriendlyURL";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"siteFriendlyURLId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"groupId", Types.BIGINT}, {"friendlyURL", Types.VARCHAR},
		{"languageId", Types.VARCHAR}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("siteFriendlyURLId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("friendlyURL", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("languageId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table SiteFriendlyURL (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,siteFriendlyURLId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,groupId LONG,friendlyURL VARCHAR(75) null,languageId VARCHAR(75) null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table SiteFriendlyURL";

	public static final String ORDER_BY_JPQL =
		" ORDER BY siteFriendlyURL.siteFriendlyURLId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY SiteFriendlyURL.siteFriendlyURLId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long FRIENDLYURL_COLUMN_BITMASK = 2L;

	public static final long GROUPID_COLUMN_BITMASK = 4L;

	public static final long LANGUAGEID_COLUMN_BITMASK = 8L;

	public static final long UUID_COLUMN_BITMASK = 16L;

	public static final long SITEFRIENDLYURLID_COLUMN_BITMASK = 32L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public SiteFriendlyURLModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _siteFriendlyURLId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSiteFriendlyURLId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _siteFriendlyURLId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SiteFriendlyURL.class;
	}

	@Override
	public String getModelClassName() {
		return SiteFriendlyURL.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<SiteFriendlyURL, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<SiteFriendlyURL, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SiteFriendlyURL, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((SiteFriendlyURL)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<SiteFriendlyURL, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<SiteFriendlyURL, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(SiteFriendlyURL)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<SiteFriendlyURL, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<SiteFriendlyURL, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, SiteFriendlyURL>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			SiteFriendlyURL.class.getClassLoader(), SiteFriendlyURL.class,
			ModelWrapper.class);

		try {
			Constructor<SiteFriendlyURL> constructor =
				(Constructor<SiteFriendlyURL>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<SiteFriendlyURL, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<SiteFriendlyURL, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<SiteFriendlyURL, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<SiteFriendlyURL, Object>>();
		Map<String, BiConsumer<SiteFriendlyURL, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<SiteFriendlyURL, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", SiteFriendlyURL::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<SiteFriendlyURL, Long>)SiteFriendlyURL::setMvccVersion);
		attributeGetterFunctions.put("uuid", SiteFriendlyURL::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<SiteFriendlyURL, String>)SiteFriendlyURL::setUuid);
		attributeGetterFunctions.put(
			"siteFriendlyURLId", SiteFriendlyURL::getSiteFriendlyURLId);
		attributeSetterBiConsumers.put(
			"siteFriendlyURLId",
			(BiConsumer<SiteFriendlyURL, Long>)
				SiteFriendlyURL::setSiteFriendlyURLId);
		attributeGetterFunctions.put(
			"companyId", SiteFriendlyURL::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<SiteFriendlyURL, Long>)SiteFriendlyURL::setCompanyId);
		attributeGetterFunctions.put("userId", SiteFriendlyURL::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<SiteFriendlyURL, Long>)SiteFriendlyURL::setUserId);
		attributeGetterFunctions.put("userName", SiteFriendlyURL::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<SiteFriendlyURL, String>)SiteFriendlyURL::setUserName);
		attributeGetterFunctions.put(
			"createDate", SiteFriendlyURL::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<SiteFriendlyURL, Date>)SiteFriendlyURL::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", SiteFriendlyURL::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<SiteFriendlyURL, Date>)
				SiteFriendlyURL::setModifiedDate);
		attributeGetterFunctions.put("groupId", SiteFriendlyURL::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<SiteFriendlyURL, Long>)SiteFriendlyURL::setGroupId);
		attributeGetterFunctions.put(
			"friendlyURL", SiteFriendlyURL::getFriendlyURL);
		attributeSetterBiConsumers.put(
			"friendlyURL",
			(BiConsumer<SiteFriendlyURL, String>)
				SiteFriendlyURL::setFriendlyURL);
		attributeGetterFunctions.put(
			"languageId", SiteFriendlyURL::getLanguageId);
		attributeSetterBiConsumers.put(
			"languageId",
			(BiConsumer<SiteFriendlyURL, String>)
				SiteFriendlyURL::setLanguageId);
		attributeGetterFunctions.put(
			"lastPublishDate", SiteFriendlyURL::getLastPublishDate);
		attributeSetterBiConsumers.put(
			"lastPublishDate",
			(BiConsumer<SiteFriendlyURL, Date>)
				SiteFriendlyURL::setLastPublishDate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getSiteFriendlyURLId() {
		return _siteFriendlyURLId;
	}

	@Override
	public void setSiteFriendlyURLId(long siteFriendlyURLId) {
		_siteFriendlyURLId = siteFriendlyURLId;
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
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
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

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
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
	public String getFriendlyURL() {
		if (_friendlyURL == null) {
			return "";
		}
		else {
			return _friendlyURL;
		}
	}

	@Override
	public void setFriendlyURL(String friendlyURL) {
		_columnBitmask |= FRIENDLYURL_COLUMN_BITMASK;

		if (_originalFriendlyURL == null) {
			_originalFriendlyURL = _friendlyURL;
		}

		_friendlyURL = friendlyURL;
	}

	public String getOriginalFriendlyURL() {
		return GetterUtil.getString(_originalFriendlyURL);
	}

	@Override
	public String getLanguageId() {
		if (_languageId == null) {
			return "";
		}
		else {
			return _languageId;
		}
	}

	@Override
	public void setLanguageId(String languageId) {
		_columnBitmask |= LANGUAGEID_COLUMN_BITMASK;

		if (_originalLanguageId == null) {
			_originalLanguageId = _languageId;
		}

		_languageId = languageId;
	}

	public String getOriginalLanguageId() {
		return GetterUtil.getString(_originalLanguageId);
	}

	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(SiteFriendlyURL.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), SiteFriendlyURL.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SiteFriendlyURL toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, SiteFriendlyURL>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		SiteFriendlyURLImpl siteFriendlyURLImpl = new SiteFriendlyURLImpl();

		siteFriendlyURLImpl.setMvccVersion(getMvccVersion());
		siteFriendlyURLImpl.setUuid(getUuid());
		siteFriendlyURLImpl.setSiteFriendlyURLId(getSiteFriendlyURLId());
		siteFriendlyURLImpl.setCompanyId(getCompanyId());
		siteFriendlyURLImpl.setUserId(getUserId());
		siteFriendlyURLImpl.setUserName(getUserName());
		siteFriendlyURLImpl.setCreateDate(getCreateDate());
		siteFriendlyURLImpl.setModifiedDate(getModifiedDate());
		siteFriendlyURLImpl.setGroupId(getGroupId());
		siteFriendlyURLImpl.setFriendlyURL(getFriendlyURL());
		siteFriendlyURLImpl.setLanguageId(getLanguageId());
		siteFriendlyURLImpl.setLastPublishDate(getLastPublishDate());

		siteFriendlyURLImpl.resetOriginalValues();

		return siteFriendlyURLImpl;
	}

	@Override
	public int compareTo(SiteFriendlyURL siteFriendlyURL) {
		long primaryKey = siteFriendlyURL.getPrimaryKey();

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
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SiteFriendlyURL)) {
			return false;
		}

		SiteFriendlyURL siteFriendlyURL = (SiteFriendlyURL)obj;

		long primaryKey = siteFriendlyURL.getPrimaryKey();

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
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		SiteFriendlyURLModelImpl siteFriendlyURLModelImpl = this;

		siteFriendlyURLModelImpl._originalUuid = siteFriendlyURLModelImpl._uuid;

		siteFriendlyURLModelImpl._originalCompanyId =
			siteFriendlyURLModelImpl._companyId;

		siteFriendlyURLModelImpl._setOriginalCompanyId = false;

		siteFriendlyURLModelImpl._setModifiedDate = false;

		siteFriendlyURLModelImpl._originalGroupId =
			siteFriendlyURLModelImpl._groupId;

		siteFriendlyURLModelImpl._setOriginalGroupId = false;

		siteFriendlyURLModelImpl._originalFriendlyURL =
			siteFriendlyURLModelImpl._friendlyURL;

		siteFriendlyURLModelImpl._originalLanguageId =
			siteFriendlyURLModelImpl._languageId;

		siteFriendlyURLModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SiteFriendlyURL> toCacheModel() {
		SiteFriendlyURLCacheModel siteFriendlyURLCacheModel =
			new SiteFriendlyURLCacheModel();

		siteFriendlyURLCacheModel.mvccVersion = getMvccVersion();

		siteFriendlyURLCacheModel.uuid = getUuid();

		String uuid = siteFriendlyURLCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			siteFriendlyURLCacheModel.uuid = null;
		}

		siteFriendlyURLCacheModel.siteFriendlyURLId = getSiteFriendlyURLId();

		siteFriendlyURLCacheModel.companyId = getCompanyId();

		siteFriendlyURLCacheModel.userId = getUserId();

		siteFriendlyURLCacheModel.userName = getUserName();

		String userName = siteFriendlyURLCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			siteFriendlyURLCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			siteFriendlyURLCacheModel.createDate = createDate.getTime();
		}
		else {
			siteFriendlyURLCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			siteFriendlyURLCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			siteFriendlyURLCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		siteFriendlyURLCacheModel.groupId = getGroupId();

		siteFriendlyURLCacheModel.friendlyURL = getFriendlyURL();

		String friendlyURL = siteFriendlyURLCacheModel.friendlyURL;

		if ((friendlyURL != null) && (friendlyURL.length() == 0)) {
			siteFriendlyURLCacheModel.friendlyURL = null;
		}

		siteFriendlyURLCacheModel.languageId = getLanguageId();

		String languageId = siteFriendlyURLCacheModel.languageId;

		if ((languageId != null) && (languageId.length() == 0)) {
			siteFriendlyURLCacheModel.languageId = null;
		}

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			siteFriendlyURLCacheModel.lastPublishDate =
				lastPublishDate.getTime();
		}
		else {
			siteFriendlyURLCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return siteFriendlyURLCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<SiteFriendlyURL, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<SiteFriendlyURL, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SiteFriendlyURL, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((SiteFriendlyURL)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<SiteFriendlyURL, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<SiteFriendlyURL, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SiteFriendlyURL, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((SiteFriendlyURL)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, SiteFriendlyURL>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private String _uuid;
	private String _originalUuid;
	private long _siteFriendlyURLId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private String _friendlyURL;
	private String _originalFriendlyURL;
	private String _languageId;
	private String _originalLanguageId;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private SiteFriendlyURL _escapedModel;

}