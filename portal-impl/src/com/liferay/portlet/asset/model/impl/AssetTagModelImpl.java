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

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagModel;
import com.liferay.asset.kernel.model.AssetTagSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the AssetTag service. Represents a row in the &quot;AssetTag&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>AssetTagModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssetTagImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagImpl
 * @generated
 */
@JSON(strict = true)
public class AssetTagModelImpl
	extends BaseModelImpl<AssetTag> implements AssetTagModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset tag model instance should use the <code>AssetTag</code> interface instead.
	 */
	public static final String TABLE_NAME = "AssetTag";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"tagId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"assetCount", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("tagId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assetCount", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AssetTag (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,tagId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,assetCount INTEGER,lastPublishDate DATE null,primary key (tagId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table AssetTag";

	public static final String ORDER_BY_JPQL = " ORDER BY assetTag.name ASC";

	public static final String ORDER_BY_SQL = " ORDER BY AssetTag.name ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.asset.kernel.model.AssetTag"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.asset.kernel.model.AssetTag"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.asset.kernel.model.AssetTag"),
		true);

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long GROUPID_COLUMN_BITMASK = 2L;

	public static final long NAME_COLUMN_BITMASK = 4L;

	public static final long UUID_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static AssetTag toModel(AssetTagSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		AssetTag model = new AssetTagImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setCtCollectionId(soapModel.getCtCollectionId());
		model.setUuid(soapModel.getUuid());
		model.setTagId(soapModel.getTagId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());
		model.setAssetCount(soapModel.getAssetCount());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<AssetTag> toModels(AssetTagSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<AssetTag> models = new ArrayList<AssetTag>(soapModels.length);

		for (AssetTagSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME =
		"AssetEntries_AssetTags";

	public static final Object[][]
		MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_COLUMNS = {
			{"companyId", Types.BIGINT}, {"entryId", Types.BIGINT},
			{"tagId", Types.BIGINT}
		};

	public static final String MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_SQL_CREATE =
		"create table AssetEntries_AssetTags (companyId LONG not null,entryId LONG not null,tagId LONG not null,ctCollectionId LONG default 0 not null,ctChangeType BOOLEAN,primary key (entryId, tagId, ctCollectionId))";

	public static final boolean FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS =
		GetterUtil.getBoolean(
			com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.AssetEntries_AssetTags"),
			true);

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.asset.kernel.model.AssetTag"));

	public AssetTagModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _tagId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setTagId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _tagId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AssetTag.class;
	}

	@Override
	public String getModelClassName() {
		return AssetTag.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AssetTag, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AssetTag, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetTag, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((AssetTag)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AssetTag, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AssetTag, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AssetTag)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AssetTag, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AssetTag, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AssetTag>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AssetTag.class.getClassLoader(), AssetTag.class,
			ModelWrapper.class);

		try {
			Constructor<AssetTag> constructor =
				(Constructor<AssetTag>)proxyClass.getConstructor(
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

	private static final Map<String, Function<AssetTag, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<AssetTag, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<AssetTag, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<AssetTag, Object>>();
		Map<String, BiConsumer<AssetTag, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<AssetTag, ?>>();

		attributeGetterFunctions.put("mvccVersion", AssetTag::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<AssetTag, Long>)AssetTag::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", AssetTag::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<AssetTag, Long>)AssetTag::setCtCollectionId);
		attributeGetterFunctions.put("uuid", AssetTag::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<AssetTag, String>)AssetTag::setUuid);
		attributeGetterFunctions.put("tagId", AssetTag::getTagId);
		attributeSetterBiConsumers.put(
			"tagId", (BiConsumer<AssetTag, Long>)AssetTag::setTagId);
		attributeGetterFunctions.put("groupId", AssetTag::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId", (BiConsumer<AssetTag, Long>)AssetTag::setGroupId);
		attributeGetterFunctions.put("companyId", AssetTag::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<AssetTag, Long>)AssetTag::setCompanyId);
		attributeGetterFunctions.put("userId", AssetTag::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<AssetTag, Long>)AssetTag::setUserId);
		attributeGetterFunctions.put("userName", AssetTag::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<AssetTag, String>)AssetTag::setUserName);
		attributeGetterFunctions.put("createDate", AssetTag::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate", (BiConsumer<AssetTag, Date>)AssetTag::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", AssetTag::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<AssetTag, Date>)AssetTag::setModifiedDate);
		attributeGetterFunctions.put("name", AssetTag::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<AssetTag, String>)AssetTag::setName);
		attributeGetterFunctions.put("assetCount", AssetTag::getAssetCount);
		attributeSetterBiConsumers.put(
			"assetCount",
			(BiConsumer<AssetTag, Integer>)AssetTag::setAssetCount);
		attributeGetterFunctions.put(
			"lastPublishDate", AssetTag::getLastPublishDate);
		attributeSetterBiConsumers.put(
			"lastPublishDate",
			(BiConsumer<AssetTag, Date>)AssetTag::setLastPublishDate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	@JSON
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

	@JSON
	@Override
	public long getTagId() {
		return _tagId;
	}

	@Override
	public void setTagId(long tagId) {
		_tagId = tagId;
	}

	@JSON
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

	@JSON
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

	@JSON
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

	@JSON
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

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
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

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_columnBitmask = -1L;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	@JSON
	@Override
	public int getAssetCount() {
		return _assetCount;
	}

	@Override
	public void setAssetCount(int assetCount) {
		_assetCount = assetCount;
	}

	@JSON
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
			PortalUtil.getClassNameId(AssetTag.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), AssetTag.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AssetTag toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AssetTag>
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
		AssetTagImpl assetTagImpl = new AssetTagImpl();

		assetTagImpl.setMvccVersion(getMvccVersion());
		assetTagImpl.setCtCollectionId(getCtCollectionId());
		assetTagImpl.setUuid(getUuid());
		assetTagImpl.setTagId(getTagId());
		assetTagImpl.setGroupId(getGroupId());
		assetTagImpl.setCompanyId(getCompanyId());
		assetTagImpl.setUserId(getUserId());
		assetTagImpl.setUserName(getUserName());
		assetTagImpl.setCreateDate(getCreateDate());
		assetTagImpl.setModifiedDate(getModifiedDate());
		assetTagImpl.setName(getName());
		assetTagImpl.setAssetCount(getAssetCount());
		assetTagImpl.setLastPublishDate(getLastPublishDate());

		assetTagImpl.resetOriginalValues();

		return assetTagImpl;
	}

	@Override
	public int compareTo(AssetTag assetTag) {
		int value = 0;

		value = getName().compareTo(assetTag.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssetTag)) {
			return false;
		}

		AssetTag assetTag = (AssetTag)object;

		long primaryKey = assetTag.getPrimaryKey();

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
		AssetTagModelImpl assetTagModelImpl = this;

		assetTagModelImpl._originalUuid = assetTagModelImpl._uuid;

		assetTagModelImpl._originalGroupId = assetTagModelImpl._groupId;

		assetTagModelImpl._setOriginalGroupId = false;

		assetTagModelImpl._originalCompanyId = assetTagModelImpl._companyId;

		assetTagModelImpl._setOriginalCompanyId = false;

		assetTagModelImpl._setModifiedDate = false;

		assetTagModelImpl._originalName = assetTagModelImpl._name;

		assetTagModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AssetTag> toCacheModel() {
		AssetTagCacheModel assetTagCacheModel = new AssetTagCacheModel();

		assetTagCacheModel.mvccVersion = getMvccVersion();

		assetTagCacheModel.ctCollectionId = getCtCollectionId();

		assetTagCacheModel.uuid = getUuid();

		String uuid = assetTagCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			assetTagCacheModel.uuid = null;
		}

		assetTagCacheModel.tagId = getTagId();

		assetTagCacheModel.groupId = getGroupId();

		assetTagCacheModel.companyId = getCompanyId();

		assetTagCacheModel.userId = getUserId();

		assetTagCacheModel.userName = getUserName();

		String userName = assetTagCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			assetTagCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			assetTagCacheModel.createDate = createDate.getTime();
		}
		else {
			assetTagCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			assetTagCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			assetTagCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		assetTagCacheModel.name = getName();

		String name = assetTagCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			assetTagCacheModel.name = null;
		}

		assetTagCacheModel.assetCount = getAssetCount();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			assetTagCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			assetTagCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return assetTagCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AssetTag, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AssetTag, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetTag, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((AssetTag)this));
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
		Map<String, Function<AssetTag, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AssetTag, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetTag, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((AssetTag)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, AssetTag>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private String _originalUuid;
	private long _tagId;
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
	private boolean _setModifiedDate;
	private String _name;
	private String _originalName;
	private int _assetCount;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private AssetTag _escapedModel;

}