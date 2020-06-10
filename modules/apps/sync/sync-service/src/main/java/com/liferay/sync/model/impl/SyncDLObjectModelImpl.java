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

package com.liferay.sync.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.model.SyncDLObjectModel;
import com.liferay.sync.model.SyncDLObjectSoap;

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
 * The base model implementation for the SyncDLObject service. Represents a row in the &quot;SyncDLObject&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>SyncDLObjectModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SyncDLObjectImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLObjectImpl
 * @generated
 */
@JSON(strict = true)
public class SyncDLObjectModelImpl
	extends BaseModelImpl<SyncDLObject> implements SyncDLObjectModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a sync dl object model instance should use the <code>SyncDLObject</code> interface instead.
	 */
	public static final String TABLE_NAME = "SyncDLObject";

	public static final Object[][] TABLE_COLUMNS = {
		{"syncDLObjectId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createTime", Types.BIGINT}, {"modifiedTime", Types.BIGINT},
		{"repositoryId", Types.BIGINT}, {"parentFolderId", Types.BIGINT},
		{"treePath", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"extension", Types.VARCHAR}, {"mimeType", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"changeLog", Types.VARCHAR},
		{"extraSettings", Types.CLOB}, {"version", Types.VARCHAR},
		{"versionId", Types.BIGINT}, {"size_", Types.BIGINT},
		{"checksum", Types.VARCHAR}, {"event", Types.VARCHAR},
		{"lanTokenKey", Types.VARCHAR},
		{"lastPermissionChangeDate", Types.TIMESTAMP},
		{"lockExpirationDate", Types.TIMESTAMP}, {"lockUserId", Types.BIGINT},
		{"lockUserName", Types.VARCHAR}, {"type_", Types.VARCHAR},
		{"typePK", Types.BIGINT}, {"typeUuid", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("syncDLObjectId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createTime", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("modifiedTime", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("parentFolderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("extension", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("mimeType", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("changeLog", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("extraSettings", Types.CLOB);
		TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("versionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("size_", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("checksum", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("event", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lanTokenKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lastPermissionChangeDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("lockExpirationDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("lockUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("lockUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("typePK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("typeUuid", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table SyncDLObject (syncDLObjectId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createTime LONG,modifiedTime LONG,repositoryId LONG,parentFolderId LONG,treePath STRING null,name VARCHAR(255) null,extension VARCHAR(75) null,mimeType VARCHAR(75) null,description STRING null,changeLog VARCHAR(75) null,extraSettings TEXT null,version VARCHAR(75) null,versionId LONG,size_ LONG,checksum VARCHAR(75) null,event VARCHAR(75) null,lanTokenKey VARCHAR(75) null,lastPermissionChangeDate DATE null,lockExpirationDate DATE null,lockUserId LONG,lockUserName VARCHAR(75) null,type_ VARCHAR(75) null,typePK LONG,typeUuid VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table SyncDLObject";

	public static final String ORDER_BY_JPQL =
		" ORDER BY syncDLObject.modifiedTime ASC, syncDLObject.repositoryId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY SyncDLObject.modifiedTime ASC, SyncDLObject.repositoryId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long EVENT_COLUMN_BITMASK = 1L;

	public static final long MODIFIEDTIME_COLUMN_BITMASK = 2L;

	public static final long PARENTFOLDERID_COLUMN_BITMASK = 4L;

	public static final long REPOSITORYID_COLUMN_BITMASK = 8L;

	public static final long TREEPATH_COLUMN_BITMASK = 16L;

	public static final long TYPE_COLUMN_BITMASK = 32L;

	public static final long TYPEPK_COLUMN_BITMASK = 64L;

	public static final long VERSION_COLUMN_BITMASK = 128L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static SyncDLObject toModel(SyncDLObjectSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		SyncDLObject model = new SyncDLObjectImpl();

		model.setSyncDLObjectId(soapModel.getSyncDLObjectId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateTime(soapModel.getCreateTime());
		model.setModifiedTime(soapModel.getModifiedTime());
		model.setRepositoryId(soapModel.getRepositoryId());
		model.setParentFolderId(soapModel.getParentFolderId());
		model.setTreePath(soapModel.getTreePath());
		model.setName(soapModel.getName());
		model.setExtension(soapModel.getExtension());
		model.setMimeType(soapModel.getMimeType());
		model.setDescription(soapModel.getDescription());
		model.setChangeLog(soapModel.getChangeLog());
		model.setExtraSettings(soapModel.getExtraSettings());
		model.setVersion(soapModel.getVersion());
		model.setVersionId(soapModel.getVersionId());
		model.setSize(soapModel.getSize());
		model.setChecksum(soapModel.getChecksum());
		model.setEvent(soapModel.getEvent());
		model.setLanTokenKey(soapModel.getLanTokenKey());
		model.setLastPermissionChangeDate(
			soapModel.getLastPermissionChangeDate());
		model.setLockExpirationDate(soapModel.getLockExpirationDate());
		model.setLockUserId(soapModel.getLockUserId());
		model.setLockUserName(soapModel.getLockUserName());
		model.setType(soapModel.getType());
		model.setTypePK(soapModel.getTypePK());
		model.setTypeUuid(soapModel.getTypeUuid());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<SyncDLObject> toModels(SyncDLObjectSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<SyncDLObject> models = new ArrayList<SyncDLObject>(
			soapModels.length);

		for (SyncDLObjectSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public SyncDLObjectModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _syncDLObjectId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSyncDLObjectId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _syncDLObjectId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SyncDLObject.class;
	}

	@Override
	public String getModelClassName() {
		return SyncDLObject.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<SyncDLObject, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<SyncDLObject, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SyncDLObject, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((SyncDLObject)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<SyncDLObject, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<SyncDLObject, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(SyncDLObject)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<SyncDLObject, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<SyncDLObject, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, SyncDLObject>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			SyncDLObject.class.getClassLoader(), SyncDLObject.class,
			ModelWrapper.class);

		try {
			Constructor<SyncDLObject> constructor =
				(Constructor<SyncDLObject>)proxyClass.getConstructor(
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

	private static final Map<String, Function<SyncDLObject, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<SyncDLObject, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<SyncDLObject, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<SyncDLObject, Object>>();
		Map<String, BiConsumer<SyncDLObject, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<SyncDLObject, ?>>();

		attributeGetterFunctions.put(
			"syncDLObjectId", SyncDLObject::getSyncDLObjectId);
		attributeSetterBiConsumers.put(
			"syncDLObjectId",
			(BiConsumer<SyncDLObject, Long>)SyncDLObject::setSyncDLObjectId);
		attributeGetterFunctions.put("companyId", SyncDLObject::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<SyncDLObject, Long>)SyncDLObject::setCompanyId);
		attributeGetterFunctions.put("userId", SyncDLObject::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<SyncDLObject, Long>)SyncDLObject::setUserId);
		attributeGetterFunctions.put("userName", SyncDLObject::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setUserName);
		attributeGetterFunctions.put("createTime", SyncDLObject::getCreateTime);
		attributeSetterBiConsumers.put(
			"createTime",
			(BiConsumer<SyncDLObject, Long>)SyncDLObject::setCreateTime);
		attributeGetterFunctions.put(
			"modifiedTime", SyncDLObject::getModifiedTime);
		attributeSetterBiConsumers.put(
			"modifiedTime",
			(BiConsumer<SyncDLObject, Long>)SyncDLObject::setModifiedTime);
		attributeGetterFunctions.put(
			"repositoryId", SyncDLObject::getRepositoryId);
		attributeSetterBiConsumers.put(
			"repositoryId",
			(BiConsumer<SyncDLObject, Long>)SyncDLObject::setRepositoryId);
		attributeGetterFunctions.put(
			"parentFolderId", SyncDLObject::getParentFolderId);
		attributeSetterBiConsumers.put(
			"parentFolderId",
			(BiConsumer<SyncDLObject, Long>)SyncDLObject::setParentFolderId);
		attributeGetterFunctions.put("treePath", SyncDLObject::getTreePath);
		attributeSetterBiConsumers.put(
			"treePath",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setTreePath);
		attributeGetterFunctions.put("name", SyncDLObject::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<SyncDLObject, String>)SyncDLObject::setName);
		attributeGetterFunctions.put("extension", SyncDLObject::getExtension);
		attributeSetterBiConsumers.put(
			"extension",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setExtension);
		attributeGetterFunctions.put("mimeType", SyncDLObject::getMimeType);
		attributeSetterBiConsumers.put(
			"mimeType",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setMimeType);
		attributeGetterFunctions.put(
			"description", SyncDLObject::getDescription);
		attributeSetterBiConsumers.put(
			"description",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setDescription);
		attributeGetterFunctions.put("changeLog", SyncDLObject::getChangeLog);
		attributeSetterBiConsumers.put(
			"changeLog",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setChangeLog);
		attributeGetterFunctions.put(
			"extraSettings", SyncDLObject::getExtraSettings);
		attributeSetterBiConsumers.put(
			"extraSettings",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setExtraSettings);
		attributeGetterFunctions.put("version", SyncDLObject::getVersion);
		attributeSetterBiConsumers.put(
			"version",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setVersion);
		attributeGetterFunctions.put("versionId", SyncDLObject::getVersionId);
		attributeSetterBiConsumers.put(
			"versionId",
			(BiConsumer<SyncDLObject, Long>)SyncDLObject::setVersionId);
		attributeGetterFunctions.put("size", SyncDLObject::getSize);
		attributeSetterBiConsumers.put(
			"size", (BiConsumer<SyncDLObject, Long>)SyncDLObject::setSize);
		attributeGetterFunctions.put("checksum", SyncDLObject::getChecksum);
		attributeSetterBiConsumers.put(
			"checksum",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setChecksum);
		attributeGetterFunctions.put("event", SyncDLObject::getEvent);
		attributeSetterBiConsumers.put(
			"event", (BiConsumer<SyncDLObject, String>)SyncDLObject::setEvent);
		attributeGetterFunctions.put(
			"lanTokenKey", SyncDLObject::getLanTokenKey);
		attributeSetterBiConsumers.put(
			"lanTokenKey",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setLanTokenKey);
		attributeGetterFunctions.put(
			"lastPermissionChangeDate",
			SyncDLObject::getLastPermissionChangeDate);
		attributeSetterBiConsumers.put(
			"lastPermissionChangeDate",
			(BiConsumer<SyncDLObject, Date>)
				SyncDLObject::setLastPermissionChangeDate);
		attributeGetterFunctions.put(
			"lockExpirationDate", SyncDLObject::getLockExpirationDate);
		attributeSetterBiConsumers.put(
			"lockExpirationDate",
			(BiConsumer<SyncDLObject, Date>)
				SyncDLObject::setLockExpirationDate);
		attributeGetterFunctions.put("lockUserId", SyncDLObject::getLockUserId);
		attributeSetterBiConsumers.put(
			"lockUserId",
			(BiConsumer<SyncDLObject, Long>)SyncDLObject::setLockUserId);
		attributeGetterFunctions.put(
			"lockUserName", SyncDLObject::getLockUserName);
		attributeSetterBiConsumers.put(
			"lockUserName",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setLockUserName);
		attributeGetterFunctions.put("type", SyncDLObject::getType);
		attributeSetterBiConsumers.put(
			"type", (BiConsumer<SyncDLObject, String>)SyncDLObject::setType);
		attributeGetterFunctions.put("typePK", SyncDLObject::getTypePK);
		attributeSetterBiConsumers.put(
			"typePK", (BiConsumer<SyncDLObject, Long>)SyncDLObject::setTypePK);
		attributeGetterFunctions.put("typeUuid", SyncDLObject::getTypeUuid);
		attributeSetterBiConsumers.put(
			"typeUuid",
			(BiConsumer<SyncDLObject, String>)SyncDLObject::setTypeUuid);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getSyncDLObjectId() {
		return _syncDLObjectId;
	}

	@Override
	public void setSyncDLObjectId(long syncDLObjectId) {
		_syncDLObjectId = syncDLObjectId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
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
	public long getCreateTime() {
		return _createTime;
	}

	@Override
	public void setCreateTime(long createTime) {
		_createTime = createTime;
	}

	@JSON
	@Override
	public long getModifiedTime() {
		return _modifiedTime;
	}

	@Override
	public void setModifiedTime(long modifiedTime) {
		_columnBitmask = -1L;

		if (!_setOriginalModifiedTime) {
			_setOriginalModifiedTime = true;

			_originalModifiedTime = _modifiedTime;
		}

		_modifiedTime = modifiedTime;
	}

	public long getOriginalModifiedTime() {
		return _originalModifiedTime;
	}

	@JSON
	@Override
	public long getRepositoryId() {
		return _repositoryId;
	}

	@Override
	public void setRepositoryId(long repositoryId) {
		_columnBitmask = -1L;

		if (!_setOriginalRepositoryId) {
			_setOriginalRepositoryId = true;

			_originalRepositoryId = _repositoryId;
		}

		_repositoryId = repositoryId;
	}

	public long getOriginalRepositoryId() {
		return _originalRepositoryId;
	}

	@JSON
	@Override
	public long getParentFolderId() {
		return _parentFolderId;
	}

	@Override
	public void setParentFolderId(long parentFolderId) {
		_columnBitmask |= PARENTFOLDERID_COLUMN_BITMASK;

		if (!_setOriginalParentFolderId) {
			_setOriginalParentFolderId = true;

			_originalParentFolderId = _parentFolderId;
		}

		_parentFolderId = parentFolderId;
	}

	public long getOriginalParentFolderId() {
		return _originalParentFolderId;
	}

	@JSON(include = false)
	@Override
	public String getTreePath() {
		if (_treePath == null) {
			return "";
		}
		else {
			return _treePath;
		}
	}

	@Override
	public void setTreePath(String treePath) {
		_columnBitmask |= TREEPATH_COLUMN_BITMASK;

		if (_originalTreePath == null) {
			_originalTreePath = _treePath;
		}

		_treePath = treePath;
	}

	public String getOriginalTreePath() {
		return GetterUtil.getString(_originalTreePath);
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
		_name = name;
	}

	@JSON
	@Override
	public String getExtension() {
		if (_extension == null) {
			return "";
		}
		else {
			return _extension;
		}
	}

	@Override
	public void setExtension(String extension) {
		_extension = extension;
	}

	@JSON
	@Override
	public String getMimeType() {
		if (_mimeType == null) {
			return "";
		}
		else {
			return _mimeType;
		}
	}

	@Override
	public void setMimeType(String mimeType) {
		_mimeType = mimeType;
	}

	@JSON
	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	@Override
	public String getChangeLog() {
		if (_changeLog == null) {
			return "";
		}
		else {
			return _changeLog;
		}
	}

	@Override
	public void setChangeLog(String changeLog) {
		_changeLog = changeLog;
	}

	@JSON
	@Override
	public String getExtraSettings() {
		if (_extraSettings == null) {
			return "";
		}
		else {
			return _extraSettings;
		}
	}

	@Override
	public void setExtraSettings(String extraSettings) {
		_extraSettings = extraSettings;
	}

	@JSON
	@Override
	public String getVersion() {
		if (_version == null) {
			return "";
		}
		else {
			return _version;
		}
	}

	@Override
	public void setVersion(String version) {
		_columnBitmask |= VERSION_COLUMN_BITMASK;

		if (_originalVersion == null) {
			_originalVersion = _version;
		}

		_version = version;
	}

	public String getOriginalVersion() {
		return GetterUtil.getString(_originalVersion);
	}

	@JSON
	@Override
	public long getVersionId() {
		return _versionId;
	}

	@Override
	public void setVersionId(long versionId) {
		_versionId = versionId;
	}

	@JSON
	@Override
	public long getSize() {
		return _size;
	}

	@Override
	public void setSize(long size) {
		_size = size;
	}

	@JSON
	@Override
	public String getChecksum() {
		if (_checksum == null) {
			return "";
		}
		else {
			return _checksum;
		}
	}

	@Override
	public void setChecksum(String checksum) {
		_checksum = checksum;
	}

	@JSON
	@Override
	public String getEvent() {
		if (_event == null) {
			return "";
		}
		else {
			return _event;
		}
	}

	@Override
	public void setEvent(String event) {
		_columnBitmask |= EVENT_COLUMN_BITMASK;

		if (_originalEvent == null) {
			_originalEvent = _event;
		}

		_event = event;
	}

	public String getOriginalEvent() {
		return GetterUtil.getString(_originalEvent);
	}

	@JSON
	@Override
	public String getLanTokenKey() {
		if (_lanTokenKey == null) {
			return "";
		}
		else {
			return _lanTokenKey;
		}
	}

	@Override
	public void setLanTokenKey(String lanTokenKey) {
		_lanTokenKey = lanTokenKey;
	}

	@JSON(include = false)
	@Override
	public Date getLastPermissionChangeDate() {
		return _lastPermissionChangeDate;
	}

	@Override
	public void setLastPermissionChangeDate(Date lastPermissionChangeDate) {
		_lastPermissionChangeDate = lastPermissionChangeDate;
	}

	@JSON
	@Override
	public Date getLockExpirationDate() {
		return _lockExpirationDate;
	}

	@Override
	public void setLockExpirationDate(Date lockExpirationDate) {
		_lockExpirationDate = lockExpirationDate;
	}

	@JSON
	@Override
	public long getLockUserId() {
		return _lockUserId;
	}

	@Override
	public void setLockUserId(long lockUserId) {
		_lockUserId = lockUserId;
	}

	@Override
	public String getLockUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getLockUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setLockUserUuid(String lockUserUuid) {
	}

	@JSON
	@Override
	public String getLockUserName() {
		if (_lockUserName == null) {
			return "";
		}
		else {
			return _lockUserName;
		}
	}

	@Override
	public void setLockUserName(String lockUserName) {
		_lockUserName = lockUserName;
	}

	@JSON
	@Override
	public String getType() {
		if (_type == null) {
			return "";
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

	@JSON
	@Override
	public long getTypePK() {
		return _typePK;
	}

	@Override
	public void setTypePK(long typePK) {
		_columnBitmask |= TYPEPK_COLUMN_BITMASK;

		if (!_setOriginalTypePK) {
			_setOriginalTypePK = true;

			_originalTypePK = _typePK;
		}

		_typePK = typePK;
	}

	public long getOriginalTypePK() {
		return _originalTypePK;
	}

	@JSON
	@Override
	public String getTypeUuid() {
		if (_typeUuid == null) {
			return "";
		}
		else {
			return _typeUuid;
		}
	}

	@Override
	public void setTypeUuid(String typeUuid) {
		_typeUuid = typeUuid;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), SyncDLObject.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SyncDLObject toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, SyncDLObject>
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
		SyncDLObjectImpl syncDLObjectImpl = new SyncDLObjectImpl();

		syncDLObjectImpl.setSyncDLObjectId(getSyncDLObjectId());
		syncDLObjectImpl.setCompanyId(getCompanyId());
		syncDLObjectImpl.setUserId(getUserId());
		syncDLObjectImpl.setUserName(getUserName());
		syncDLObjectImpl.setCreateTime(getCreateTime());
		syncDLObjectImpl.setModifiedTime(getModifiedTime());
		syncDLObjectImpl.setRepositoryId(getRepositoryId());
		syncDLObjectImpl.setParentFolderId(getParentFolderId());
		syncDLObjectImpl.setTreePath(getTreePath());
		syncDLObjectImpl.setName(getName());
		syncDLObjectImpl.setExtension(getExtension());
		syncDLObjectImpl.setMimeType(getMimeType());
		syncDLObjectImpl.setDescription(getDescription());
		syncDLObjectImpl.setChangeLog(getChangeLog());
		syncDLObjectImpl.setExtraSettings(getExtraSettings());
		syncDLObjectImpl.setVersion(getVersion());
		syncDLObjectImpl.setVersionId(getVersionId());
		syncDLObjectImpl.setSize(getSize());
		syncDLObjectImpl.setChecksum(getChecksum());
		syncDLObjectImpl.setEvent(getEvent());
		syncDLObjectImpl.setLanTokenKey(getLanTokenKey());
		syncDLObjectImpl.setLastPermissionChangeDate(
			getLastPermissionChangeDate());
		syncDLObjectImpl.setLockExpirationDate(getLockExpirationDate());
		syncDLObjectImpl.setLockUserId(getLockUserId());
		syncDLObjectImpl.setLockUserName(getLockUserName());
		syncDLObjectImpl.setType(getType());
		syncDLObjectImpl.setTypePK(getTypePK());
		syncDLObjectImpl.setTypeUuid(getTypeUuid());

		syncDLObjectImpl.resetOriginalValues();

		return syncDLObjectImpl;
	}

	@Override
	public int compareTo(SyncDLObject syncDLObject) {
		int value = 0;

		if (getModifiedTime() < syncDLObject.getModifiedTime()) {
			value = -1;
		}
		else if (getModifiedTime() > syncDLObject.getModifiedTime()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getRepositoryId() < syncDLObject.getRepositoryId()) {
			value = -1;
		}
		else if (getRepositoryId() > syncDLObject.getRepositoryId()) {
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SyncDLObject)) {
			return false;
		}

		SyncDLObject syncDLObject = (SyncDLObject)object;

		long primaryKey = syncDLObject.getPrimaryKey();

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
		SyncDLObjectModelImpl syncDLObjectModelImpl = this;

		syncDLObjectModelImpl._originalModifiedTime =
			syncDLObjectModelImpl._modifiedTime;

		syncDLObjectModelImpl._setOriginalModifiedTime = false;

		syncDLObjectModelImpl._originalRepositoryId =
			syncDLObjectModelImpl._repositoryId;

		syncDLObjectModelImpl._setOriginalRepositoryId = false;

		syncDLObjectModelImpl._originalParentFolderId =
			syncDLObjectModelImpl._parentFolderId;

		syncDLObjectModelImpl._setOriginalParentFolderId = false;

		syncDLObjectModelImpl._originalTreePath =
			syncDLObjectModelImpl._treePath;

		syncDLObjectModelImpl._originalVersion = syncDLObjectModelImpl._version;

		syncDLObjectModelImpl._originalEvent = syncDLObjectModelImpl._event;

		syncDLObjectModelImpl._originalType = syncDLObjectModelImpl._type;

		syncDLObjectModelImpl._originalTypePK = syncDLObjectModelImpl._typePK;

		syncDLObjectModelImpl._setOriginalTypePK = false;

		syncDLObjectModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SyncDLObject> toCacheModel() {
		SyncDLObjectCacheModel syncDLObjectCacheModel =
			new SyncDLObjectCacheModel();

		syncDLObjectCacheModel.syncDLObjectId = getSyncDLObjectId();

		syncDLObjectCacheModel.companyId = getCompanyId();

		syncDLObjectCacheModel.userId = getUserId();

		syncDLObjectCacheModel.userName = getUserName();

		String userName = syncDLObjectCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			syncDLObjectCacheModel.userName = null;
		}

		syncDLObjectCacheModel.createTime = getCreateTime();

		syncDLObjectCacheModel.modifiedTime = getModifiedTime();

		syncDLObjectCacheModel.repositoryId = getRepositoryId();

		syncDLObjectCacheModel.parentFolderId = getParentFolderId();

		syncDLObjectCacheModel.treePath = getTreePath();

		String treePath = syncDLObjectCacheModel.treePath;

		if ((treePath != null) && (treePath.length() == 0)) {
			syncDLObjectCacheModel.treePath = null;
		}

		syncDLObjectCacheModel.name = getName();

		String name = syncDLObjectCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			syncDLObjectCacheModel.name = null;
		}

		syncDLObjectCacheModel.extension = getExtension();

		String extension = syncDLObjectCacheModel.extension;

		if ((extension != null) && (extension.length() == 0)) {
			syncDLObjectCacheModel.extension = null;
		}

		syncDLObjectCacheModel.mimeType = getMimeType();

		String mimeType = syncDLObjectCacheModel.mimeType;

		if ((mimeType != null) && (mimeType.length() == 0)) {
			syncDLObjectCacheModel.mimeType = null;
		}

		syncDLObjectCacheModel.description = getDescription();

		String description = syncDLObjectCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			syncDLObjectCacheModel.description = null;
		}

		syncDLObjectCacheModel.changeLog = getChangeLog();

		String changeLog = syncDLObjectCacheModel.changeLog;

		if ((changeLog != null) && (changeLog.length() == 0)) {
			syncDLObjectCacheModel.changeLog = null;
		}

		syncDLObjectCacheModel.extraSettings = getExtraSettings();

		String extraSettings = syncDLObjectCacheModel.extraSettings;

		if ((extraSettings != null) && (extraSettings.length() == 0)) {
			syncDLObjectCacheModel.extraSettings = null;
		}

		syncDLObjectCacheModel.version = getVersion();

		String version = syncDLObjectCacheModel.version;

		if ((version != null) && (version.length() == 0)) {
			syncDLObjectCacheModel.version = null;
		}

		syncDLObjectCacheModel.versionId = getVersionId();

		syncDLObjectCacheModel.size = getSize();

		syncDLObjectCacheModel.checksum = getChecksum();

		String checksum = syncDLObjectCacheModel.checksum;

		if ((checksum != null) && (checksum.length() == 0)) {
			syncDLObjectCacheModel.checksum = null;
		}

		syncDLObjectCacheModel.event = getEvent();

		String event = syncDLObjectCacheModel.event;

		if ((event != null) && (event.length() == 0)) {
			syncDLObjectCacheModel.event = null;
		}

		syncDLObjectCacheModel.lanTokenKey = getLanTokenKey();

		String lanTokenKey = syncDLObjectCacheModel.lanTokenKey;

		if ((lanTokenKey != null) && (lanTokenKey.length() == 0)) {
			syncDLObjectCacheModel.lanTokenKey = null;
		}

		Date lastPermissionChangeDate = getLastPermissionChangeDate();

		if (lastPermissionChangeDate != null) {
			syncDLObjectCacheModel.lastPermissionChangeDate =
				lastPermissionChangeDate.getTime();
		}
		else {
			syncDLObjectCacheModel.lastPermissionChangeDate = Long.MIN_VALUE;
		}

		Date lockExpirationDate = getLockExpirationDate();

		if (lockExpirationDate != null) {
			syncDLObjectCacheModel.lockExpirationDate =
				lockExpirationDate.getTime();
		}
		else {
			syncDLObjectCacheModel.lockExpirationDate = Long.MIN_VALUE;
		}

		syncDLObjectCacheModel.lockUserId = getLockUserId();

		syncDLObjectCacheModel.lockUserName = getLockUserName();

		String lockUserName = syncDLObjectCacheModel.lockUserName;

		if ((lockUserName != null) && (lockUserName.length() == 0)) {
			syncDLObjectCacheModel.lockUserName = null;
		}

		syncDLObjectCacheModel.type = getType();

		String type = syncDLObjectCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			syncDLObjectCacheModel.type = null;
		}

		syncDLObjectCacheModel.typePK = getTypePK();

		syncDLObjectCacheModel.typeUuid = getTypeUuid();

		String typeUuid = syncDLObjectCacheModel.typeUuid;

		if ((typeUuid != null) && (typeUuid.length() == 0)) {
			syncDLObjectCacheModel.typeUuid = null;
		}

		return syncDLObjectCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<SyncDLObject, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<SyncDLObject, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SyncDLObject, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((SyncDLObject)this));
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
		Map<String, Function<SyncDLObject, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<SyncDLObject, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SyncDLObject, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((SyncDLObject)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, SyncDLObject>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _syncDLObjectId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _createTime;
	private long _modifiedTime;
	private long _originalModifiedTime;
	private boolean _setOriginalModifiedTime;
	private long _repositoryId;
	private long _originalRepositoryId;
	private boolean _setOriginalRepositoryId;
	private long _parentFolderId;
	private long _originalParentFolderId;
	private boolean _setOriginalParentFolderId;
	private String _treePath;
	private String _originalTreePath;
	private String _name;
	private String _extension;
	private String _mimeType;
	private String _description;
	private String _changeLog;
	private String _extraSettings;
	private String _version;
	private String _originalVersion;
	private long _versionId;
	private long _size;
	private String _checksum;
	private String _event;
	private String _originalEvent;
	private String _lanTokenKey;
	private Date _lastPermissionChangeDate;
	private Date _lockExpirationDate;
	private long _lockUserId;
	private String _lockUserName;
	private String _type;
	private String _originalType;
	private long _typePK;
	private long _originalTypePK;
	private boolean _setOriginalTypePK;
	private String _typeUuid;
	private long _columnBitmask;
	private SyncDLObject _escapedModel;

}