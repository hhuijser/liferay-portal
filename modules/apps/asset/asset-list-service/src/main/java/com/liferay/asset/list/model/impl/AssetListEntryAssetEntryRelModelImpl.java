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

package com.liferay.asset.list.model.impl;

import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRelModel;
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
 * The base model implementation for the AssetListEntryAssetEntryRel service. Represents a row in the &quot;AssetListEntryAssetEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>AssetListEntryAssetEntryRelModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssetListEntryAssetEntryRelImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryAssetEntryRelImpl
 * @generated
 */
public class AssetListEntryAssetEntryRelModelImpl
	extends BaseModelImpl<AssetListEntryAssetEntryRel>
	implements AssetListEntryAssetEntryRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset list entry asset entry rel model instance should use the <code>AssetListEntryAssetEntryRel</code> interface instead.
	 */
	public static final String TABLE_NAME = "AssetListEntryAssetEntryRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR},
		{"assetListEntryAssetEntryRelId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"assetListEntryId", Types.BIGINT}, {"assetEntryId", Types.BIGINT},
		{"segmentsEntryId", Types.BIGINT}, {"position", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assetListEntryAssetEntryRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("assetListEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("assetEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("segmentsEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("position", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AssetListEntryAssetEntryRel (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,assetListEntryAssetEntryRelId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,assetListEntryId LONG,assetEntryId LONG,segmentsEntryId LONG,position INTEGER,lastPublishDate DATE null,primary key (assetListEntryAssetEntryRelId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
		"drop table AssetListEntryAssetEntryRel";

	public static final String ORDER_BY_JPQL =
		" ORDER BY assetListEntryAssetEntryRel.position ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY AssetListEntryAssetEntryRel.position ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long ASSETLISTENTRYID_COLUMN_BITMASK = 1L;

	public static final long COMPANYID_COLUMN_BITMASK = 2L;

	public static final long GROUPID_COLUMN_BITMASK = 4L;

	public static final long POSITION_COLUMN_BITMASK = 8L;

	public static final long SEGMENTSENTRYID_COLUMN_BITMASK = 16L;

	public static final long UUID_COLUMN_BITMASK = 32L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public AssetListEntryAssetEntryRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _assetListEntryAssetEntryRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAssetListEntryAssetEntryRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetListEntryAssetEntryRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AssetListEntryAssetEntryRel.class;
	}

	@Override
	public String getModelClassName() {
		return AssetListEntryAssetEntryRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AssetListEntryAssetEntryRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AssetListEntryAssetEntryRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetListEntryAssetEntryRel, Object>
				attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply(
					(AssetListEntryAssetEntryRel)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AssetListEntryAssetEntryRel, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AssetListEntryAssetEntryRel, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AssetListEntryAssetEntryRel)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AssetListEntryAssetEntryRel, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AssetListEntryAssetEntryRel, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AssetListEntryAssetEntryRel>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AssetListEntryAssetEntryRel.class.getClassLoader(),
			AssetListEntryAssetEntryRel.class, ModelWrapper.class);

		try {
			Constructor<AssetListEntryAssetEntryRel> constructor =
				(Constructor<AssetListEntryAssetEntryRel>)
					proxyClass.getConstructor(InvocationHandler.class);

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

	private static final Map
		<String, Function<AssetListEntryAssetEntryRel, Object>>
			_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<AssetListEntryAssetEntryRel, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<AssetListEntryAssetEntryRel, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<AssetListEntryAssetEntryRel, Object>>();
		Map<String, BiConsumer<AssetListEntryAssetEntryRel, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<AssetListEntryAssetEntryRel, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", AssetListEntryAssetEntryRel::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", AssetListEntryAssetEntryRel::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setCtCollectionId);
		attributeGetterFunctions.put(
			"uuid", AssetListEntryAssetEntryRel::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<AssetListEntryAssetEntryRel, String>)
				AssetListEntryAssetEntryRel::setUuid);
		attributeGetterFunctions.put(
			"assetListEntryAssetEntryRelId",
			AssetListEntryAssetEntryRel::getAssetListEntryAssetEntryRelId);
		attributeSetterBiConsumers.put(
			"assetListEntryAssetEntryRelId",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setAssetListEntryAssetEntryRelId);
		attributeGetterFunctions.put(
			"groupId", AssetListEntryAssetEntryRel::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setGroupId);
		attributeGetterFunctions.put(
			"companyId", AssetListEntryAssetEntryRel::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setCompanyId);
		attributeGetterFunctions.put(
			"userId", AssetListEntryAssetEntryRel::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setUserId);
		attributeGetterFunctions.put(
			"userName", AssetListEntryAssetEntryRel::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<AssetListEntryAssetEntryRel, String>)
				AssetListEntryAssetEntryRel::setUserName);
		attributeGetterFunctions.put(
			"createDate", AssetListEntryAssetEntryRel::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<AssetListEntryAssetEntryRel, Date>)
				AssetListEntryAssetEntryRel::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", AssetListEntryAssetEntryRel::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<AssetListEntryAssetEntryRel, Date>)
				AssetListEntryAssetEntryRel::setModifiedDate);
		attributeGetterFunctions.put(
			"assetListEntryId",
			AssetListEntryAssetEntryRel::getAssetListEntryId);
		attributeSetterBiConsumers.put(
			"assetListEntryId",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setAssetListEntryId);
		attributeGetterFunctions.put(
			"assetEntryId", AssetListEntryAssetEntryRel::getAssetEntryId);
		attributeSetterBiConsumers.put(
			"assetEntryId",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setAssetEntryId);
		attributeGetterFunctions.put(
			"segmentsEntryId", AssetListEntryAssetEntryRel::getSegmentsEntryId);
		attributeSetterBiConsumers.put(
			"segmentsEntryId",
			(BiConsumer<AssetListEntryAssetEntryRel, Long>)
				AssetListEntryAssetEntryRel::setSegmentsEntryId);
		attributeGetterFunctions.put(
			"position", AssetListEntryAssetEntryRel::getPosition);
		attributeSetterBiConsumers.put(
			"position",
			(BiConsumer<AssetListEntryAssetEntryRel, Integer>)
				AssetListEntryAssetEntryRel::setPosition);
		attributeGetterFunctions.put(
			"lastPublishDate", AssetListEntryAssetEntryRel::getLastPublishDate);
		attributeSetterBiConsumers.put(
			"lastPublishDate",
			(BiConsumer<AssetListEntryAssetEntryRel, Date>)
				AssetListEntryAssetEntryRel::setLastPublishDate);

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
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
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
	public long getAssetListEntryAssetEntryRelId() {
		return _assetListEntryAssetEntryRelId;
	}

	@Override
	public void setAssetListEntryAssetEntryRelId(
		long assetListEntryAssetEntryRelId) {

		_assetListEntryAssetEntryRelId = assetListEntryAssetEntryRelId;
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
	public long getAssetListEntryId() {
		return _assetListEntryId;
	}

	@Override
	public void setAssetListEntryId(long assetListEntryId) {
		_columnBitmask |= ASSETLISTENTRYID_COLUMN_BITMASK;

		if (!_setOriginalAssetListEntryId) {
			_setOriginalAssetListEntryId = true;

			_originalAssetListEntryId = _assetListEntryId;
		}

		_assetListEntryId = assetListEntryId;
	}

	public long getOriginalAssetListEntryId() {
		return _originalAssetListEntryId;
	}

	@Override
	public long getAssetEntryId() {
		return _assetEntryId;
	}

	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	@Override
	public long getSegmentsEntryId() {
		return _segmentsEntryId;
	}

	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		_columnBitmask |= SEGMENTSENTRYID_COLUMN_BITMASK;

		if (!_setOriginalSegmentsEntryId) {
			_setOriginalSegmentsEntryId = true;

			_originalSegmentsEntryId = _segmentsEntryId;
		}

		_segmentsEntryId = segmentsEntryId;
	}

	public long getOriginalSegmentsEntryId() {
		return _originalSegmentsEntryId;
	}

	@Override
	public int getPosition() {
		return _position;
	}

	@Override
	public void setPosition(int position) {
		_columnBitmask = -1L;

		if (!_setOriginalPosition) {
			_setOriginalPosition = true;

			_originalPosition = _position;
		}

		_position = position;
	}

	public int getOriginalPosition() {
		return _originalPosition;
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
			PortalUtil.getClassNameId(
				AssetListEntryAssetEntryRel.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), AssetListEntryAssetEntryRel.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AssetListEntryAssetEntryRel toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AssetListEntryAssetEntryRel>
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
		AssetListEntryAssetEntryRelImpl assetListEntryAssetEntryRelImpl =
			new AssetListEntryAssetEntryRelImpl();

		assetListEntryAssetEntryRelImpl.setMvccVersion(getMvccVersion());
		assetListEntryAssetEntryRelImpl.setCtCollectionId(getCtCollectionId());
		assetListEntryAssetEntryRelImpl.setUuid(getUuid());
		assetListEntryAssetEntryRelImpl.setAssetListEntryAssetEntryRelId(
			getAssetListEntryAssetEntryRelId());
		assetListEntryAssetEntryRelImpl.setGroupId(getGroupId());
		assetListEntryAssetEntryRelImpl.setCompanyId(getCompanyId());
		assetListEntryAssetEntryRelImpl.setUserId(getUserId());
		assetListEntryAssetEntryRelImpl.setUserName(getUserName());
		assetListEntryAssetEntryRelImpl.setCreateDate(getCreateDate());
		assetListEntryAssetEntryRelImpl.setModifiedDate(getModifiedDate());
		assetListEntryAssetEntryRelImpl.setAssetListEntryId(
			getAssetListEntryId());
		assetListEntryAssetEntryRelImpl.setAssetEntryId(getAssetEntryId());
		assetListEntryAssetEntryRelImpl.setSegmentsEntryId(
			getSegmentsEntryId());
		assetListEntryAssetEntryRelImpl.setPosition(getPosition());
		assetListEntryAssetEntryRelImpl.setLastPublishDate(
			getLastPublishDate());

		assetListEntryAssetEntryRelImpl.resetOriginalValues();

		return assetListEntryAssetEntryRelImpl;
	}

	@Override
	public int compareTo(
		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel) {

		int value = 0;

		if (getPosition() < assetListEntryAssetEntryRel.getPosition()) {
			value = -1;
		}
		else if (getPosition() > assetListEntryAssetEntryRel.getPosition()) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		if (!(obj instanceof AssetListEntryAssetEntryRel)) {
			return false;
		}

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			(AssetListEntryAssetEntryRel)obj;

		long primaryKey = assetListEntryAssetEntryRel.getPrimaryKey();

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
		AssetListEntryAssetEntryRelModelImpl
			assetListEntryAssetEntryRelModelImpl = this;

		assetListEntryAssetEntryRelModelImpl._originalUuid =
			assetListEntryAssetEntryRelModelImpl._uuid;

		assetListEntryAssetEntryRelModelImpl._originalGroupId =
			assetListEntryAssetEntryRelModelImpl._groupId;

		assetListEntryAssetEntryRelModelImpl._setOriginalGroupId = false;

		assetListEntryAssetEntryRelModelImpl._originalCompanyId =
			assetListEntryAssetEntryRelModelImpl._companyId;

		assetListEntryAssetEntryRelModelImpl._setOriginalCompanyId = false;

		assetListEntryAssetEntryRelModelImpl._setModifiedDate = false;

		assetListEntryAssetEntryRelModelImpl._originalAssetListEntryId =
			assetListEntryAssetEntryRelModelImpl._assetListEntryId;

		assetListEntryAssetEntryRelModelImpl._setOriginalAssetListEntryId =
			false;

		assetListEntryAssetEntryRelModelImpl._originalSegmentsEntryId =
			assetListEntryAssetEntryRelModelImpl._segmentsEntryId;

		assetListEntryAssetEntryRelModelImpl._setOriginalSegmentsEntryId =
			false;

		assetListEntryAssetEntryRelModelImpl._originalPosition =
			assetListEntryAssetEntryRelModelImpl._position;

		assetListEntryAssetEntryRelModelImpl._setOriginalPosition = false;

		assetListEntryAssetEntryRelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AssetListEntryAssetEntryRel> toCacheModel() {
		AssetListEntryAssetEntryRelCacheModel
			assetListEntryAssetEntryRelCacheModel =
				new AssetListEntryAssetEntryRelCacheModel();

		assetListEntryAssetEntryRelCacheModel.mvccVersion = getMvccVersion();

		assetListEntryAssetEntryRelCacheModel.ctCollectionId =
			getCtCollectionId();

		assetListEntryAssetEntryRelCacheModel.uuid = getUuid();

		String uuid = assetListEntryAssetEntryRelCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			assetListEntryAssetEntryRelCacheModel.uuid = null;
		}

		assetListEntryAssetEntryRelCacheModel.assetListEntryAssetEntryRelId =
			getAssetListEntryAssetEntryRelId();

		assetListEntryAssetEntryRelCacheModel.groupId = getGroupId();

		assetListEntryAssetEntryRelCacheModel.companyId = getCompanyId();

		assetListEntryAssetEntryRelCacheModel.userId = getUserId();

		assetListEntryAssetEntryRelCacheModel.userName = getUserName();

		String userName = assetListEntryAssetEntryRelCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			assetListEntryAssetEntryRelCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			assetListEntryAssetEntryRelCacheModel.createDate =
				createDate.getTime();
		}
		else {
			assetListEntryAssetEntryRelCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			assetListEntryAssetEntryRelCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			assetListEntryAssetEntryRelCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		assetListEntryAssetEntryRelCacheModel.assetListEntryId =
			getAssetListEntryId();

		assetListEntryAssetEntryRelCacheModel.assetEntryId = getAssetEntryId();

		assetListEntryAssetEntryRelCacheModel.segmentsEntryId =
			getSegmentsEntryId();

		assetListEntryAssetEntryRelCacheModel.position = getPosition();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			assetListEntryAssetEntryRelCacheModel.lastPublishDate =
				lastPublishDate.getTime();
		}
		else {
			assetListEntryAssetEntryRelCacheModel.lastPublishDate =
				Long.MIN_VALUE;
		}

		return assetListEntryAssetEntryRelCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AssetListEntryAssetEntryRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AssetListEntryAssetEntryRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetListEntryAssetEntryRel, Object>
				attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply(
					(AssetListEntryAssetEntryRel)this));
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
		Map<String, Function<AssetListEntryAssetEntryRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AssetListEntryAssetEntryRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetListEntryAssetEntryRel, Object>
				attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply(
					(AssetListEntryAssetEntryRel)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, AssetListEntryAssetEntryRel>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private String _originalUuid;
	private long _assetListEntryAssetEntryRelId;
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
	private long _assetListEntryId;
	private long _originalAssetListEntryId;
	private boolean _setOriginalAssetListEntryId;
	private long _assetEntryId;
	private long _segmentsEntryId;
	private long _originalSegmentsEntryId;
	private boolean _setOriginalSegmentsEntryId;
	private int _position;
	private int _originalPosition;
	private boolean _setOriginalPosition;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private AssetListEntryAssetEntryRel _escapedModel;

}