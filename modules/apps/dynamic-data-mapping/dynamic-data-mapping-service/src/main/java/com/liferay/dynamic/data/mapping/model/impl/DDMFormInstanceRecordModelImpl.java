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

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordModel;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordSoap;
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
 * The base model implementation for the DDMFormInstanceRecord service. Represents a row in the &quot;DDMFormInstanceRecord&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>DDMFormInstanceRecordModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDMFormInstanceRecordImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordImpl
 * @generated
 */
@JSON(strict = true)
public class DDMFormInstanceRecordModelImpl
	extends BaseModelImpl<DDMFormInstanceRecord>
	implements DDMFormInstanceRecordModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ddm form instance record model instance should use the <code>DDMFormInstanceRecord</code> interface instead.
	 */
	public static final String TABLE_NAME = "DDMFormInstanceRecord";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"formInstanceRecordId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"versionUserId", Types.BIGINT}, {"versionUserName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"formInstanceId", Types.BIGINT},
		{"formInstanceVersion", Types.VARCHAR}, {"storageId", Types.BIGINT},
		{"version", Types.VARCHAR}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("formInstanceRecordId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("formInstanceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("formInstanceVersion", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("storageId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table DDMFormInstanceRecord (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,formInstanceRecordId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,formInstanceId LONG,formInstanceVersion VARCHAR(75) null,storageId LONG,version VARCHAR(75) null,lastPublishDate DATE null,primary key (formInstanceRecordId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
		"drop table DDMFormInstanceRecord";

	public static final String ORDER_BY_JPQL =
		" ORDER BY ddmFormInstanceRecord.formInstanceRecordId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY DDMFormInstanceRecord.formInstanceRecordId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long FORMINSTANCEID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long FORMINSTANCEVERSION_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long USERID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 32L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long FORMINSTANCERECORDID_COLUMN_BITMASK = 64L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static DDMFormInstanceRecord toModel(
		DDMFormInstanceRecordSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		DDMFormInstanceRecord model = new DDMFormInstanceRecordImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setCtCollectionId(soapModel.getCtCollectionId());
		model.setUuid(soapModel.getUuid());
		model.setFormInstanceRecordId(soapModel.getFormInstanceRecordId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setVersionUserId(soapModel.getVersionUserId());
		model.setVersionUserName(soapModel.getVersionUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFormInstanceId(soapModel.getFormInstanceId());
		model.setFormInstanceVersion(soapModel.getFormInstanceVersion());
		model.setStorageId(soapModel.getStorageId());
		model.setVersion(soapModel.getVersion());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static List<DDMFormInstanceRecord> toModels(
		DDMFormInstanceRecordSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<DDMFormInstanceRecord> models =
			new ArrayList<DDMFormInstanceRecord>(soapModels.length);

		for (DDMFormInstanceRecordSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public DDMFormInstanceRecordModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _formInstanceRecordId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFormInstanceRecordId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _formInstanceRecordId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DDMFormInstanceRecord.class;
	}

	@Override
	public String getModelClassName() {
		return DDMFormInstanceRecord.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<DDMFormInstanceRecord, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<DDMFormInstanceRecord, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<DDMFormInstanceRecord, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((DDMFormInstanceRecord)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<DDMFormInstanceRecord, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<DDMFormInstanceRecord, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(DDMFormInstanceRecord)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<DDMFormInstanceRecord, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<DDMFormInstanceRecord, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, DDMFormInstanceRecord>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			DDMFormInstanceRecord.class.getClassLoader(),
			DDMFormInstanceRecord.class, ModelWrapper.class);

		try {
			Constructor<DDMFormInstanceRecord> constructor =
				(Constructor<DDMFormInstanceRecord>)proxyClass.getConstructor(
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

	private static final Map<String, Function<DDMFormInstanceRecord, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<DDMFormInstanceRecord, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<DDMFormInstanceRecord, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<DDMFormInstanceRecord, Object>>();
		Map<String, BiConsumer<DDMFormInstanceRecord, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<DDMFormInstanceRecord, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", DDMFormInstanceRecord::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", DDMFormInstanceRecord::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setCtCollectionId);
		attributeGetterFunctions.put("uuid", DDMFormInstanceRecord::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<DDMFormInstanceRecord, String>)
				DDMFormInstanceRecord::setUuid);
		attributeGetterFunctions.put(
			"formInstanceRecordId",
			DDMFormInstanceRecord::getFormInstanceRecordId);
		attributeSetterBiConsumers.put(
			"formInstanceRecordId",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setFormInstanceRecordId);
		attributeGetterFunctions.put(
			"groupId", DDMFormInstanceRecord::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setGroupId);
		attributeGetterFunctions.put(
			"companyId", DDMFormInstanceRecord::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setCompanyId);
		attributeGetterFunctions.put(
			"userId", DDMFormInstanceRecord::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setUserId);
		attributeGetterFunctions.put(
			"userName", DDMFormInstanceRecord::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<DDMFormInstanceRecord, String>)
				DDMFormInstanceRecord::setUserName);
		attributeGetterFunctions.put(
			"versionUserId", DDMFormInstanceRecord::getVersionUserId);
		attributeSetterBiConsumers.put(
			"versionUserId",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setVersionUserId);
		attributeGetterFunctions.put(
			"versionUserName", DDMFormInstanceRecord::getVersionUserName);
		attributeSetterBiConsumers.put(
			"versionUserName",
			(BiConsumer<DDMFormInstanceRecord, String>)
				DDMFormInstanceRecord::setVersionUserName);
		attributeGetterFunctions.put(
			"createDate", DDMFormInstanceRecord::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<DDMFormInstanceRecord, Date>)
				DDMFormInstanceRecord::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", DDMFormInstanceRecord::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<DDMFormInstanceRecord, Date>)
				DDMFormInstanceRecord::setModifiedDate);
		attributeGetterFunctions.put(
			"formInstanceId", DDMFormInstanceRecord::getFormInstanceId);
		attributeSetterBiConsumers.put(
			"formInstanceId",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setFormInstanceId);
		attributeGetterFunctions.put(
			"formInstanceVersion",
			DDMFormInstanceRecord::getFormInstanceVersion);
		attributeSetterBiConsumers.put(
			"formInstanceVersion",
			(BiConsumer<DDMFormInstanceRecord, String>)
				DDMFormInstanceRecord::setFormInstanceVersion);
		attributeGetterFunctions.put(
			"storageId", DDMFormInstanceRecord::getStorageId);
		attributeSetterBiConsumers.put(
			"storageId",
			(BiConsumer<DDMFormInstanceRecord, Long>)
				DDMFormInstanceRecord::setStorageId);
		attributeGetterFunctions.put(
			"version", DDMFormInstanceRecord::getVersion);
		attributeSetterBiConsumers.put(
			"version",
			(BiConsumer<DDMFormInstanceRecord, String>)
				DDMFormInstanceRecord::setVersion);
		attributeGetterFunctions.put(
			"lastPublishDate", DDMFormInstanceRecord::getLastPublishDate);
		attributeSetterBiConsumers.put(
			"lastPublishDate",
			(BiConsumer<DDMFormInstanceRecord, Date>)
				DDMFormInstanceRecord::setLastPublishDate);

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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_uuid = uuid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalUuid() {
		return getColumnOriginalValue("uuid_");
	}

	@JSON
	@Override
	public long getFormInstanceRecordId() {
		return _formInstanceRecordId;
	}

	@Override
	public void setFormInstanceRecordId(long formInstanceRecordId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_formInstanceRecordId = formInstanceRecordId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_groupId = groupId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalGroupId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("groupId"));
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("companyId"));
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalUserId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("userId"));
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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@JSON
	@Override
	public long getVersionUserId() {
		return _versionUserId;
	}

	@Override
	public void setVersionUserId(long versionUserId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_versionUserId = versionUserId;
	}

	@Override
	public String getVersionUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getVersionUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setVersionUserUuid(String versionUserUuid) {
	}

	@JSON
	@Override
	public String getVersionUserName() {
		if (_versionUserName == null) {
			return "";
		}
		else {
			return _versionUserName;
		}
	}

	@Override
	public void setVersionUserName(String versionUserName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_versionUserName = versionUserName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

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

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getFormInstanceId() {
		return _formInstanceId;
	}

	@Override
	public void setFormInstanceId(long formInstanceId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_formInstanceId = formInstanceId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalFormInstanceId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("formInstanceId"));
	}

	@JSON
	@Override
	public String getFormInstanceVersion() {
		if (_formInstanceVersion == null) {
			return "";
		}
		else {
			return _formInstanceVersion;
		}
	}

	@Override
	public void setFormInstanceVersion(String formInstanceVersion) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_formInstanceVersion = formInstanceVersion;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalFormInstanceVersion() {
		return getColumnOriginalValue("formInstanceVersion");
	}

	@JSON
	@Override
	public long getStorageId() {
		return _storageId;
	}

	@Override
	public void setStorageId(long storageId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_storageId = storageId;
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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_version = version;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(DDMFormInstanceRecord.class.getName()));
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (entry.getValue() != getColumnValue(entry.getKey())) {
				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), DDMFormInstanceRecord.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public DDMFormInstanceRecord toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, DDMFormInstanceRecord>
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
		DDMFormInstanceRecordImpl ddmFormInstanceRecordImpl =
			new DDMFormInstanceRecordImpl();

		ddmFormInstanceRecordImpl.setMvccVersion(getMvccVersion());
		ddmFormInstanceRecordImpl.setCtCollectionId(getCtCollectionId());
		ddmFormInstanceRecordImpl.setUuid(getUuid());
		ddmFormInstanceRecordImpl.setFormInstanceRecordId(
			getFormInstanceRecordId());
		ddmFormInstanceRecordImpl.setGroupId(getGroupId());
		ddmFormInstanceRecordImpl.setCompanyId(getCompanyId());
		ddmFormInstanceRecordImpl.setUserId(getUserId());
		ddmFormInstanceRecordImpl.setUserName(getUserName());
		ddmFormInstanceRecordImpl.setVersionUserId(getVersionUserId());
		ddmFormInstanceRecordImpl.setVersionUserName(getVersionUserName());
		ddmFormInstanceRecordImpl.setCreateDate(getCreateDate());
		ddmFormInstanceRecordImpl.setModifiedDate(getModifiedDate());
		ddmFormInstanceRecordImpl.setFormInstanceId(getFormInstanceId());
		ddmFormInstanceRecordImpl.setFormInstanceVersion(
			getFormInstanceVersion());
		ddmFormInstanceRecordImpl.setStorageId(getStorageId());
		ddmFormInstanceRecordImpl.setVersion(getVersion());
		ddmFormInstanceRecordImpl.setLastPublishDate(getLastPublishDate());

		ddmFormInstanceRecordImpl.resetOriginalValues();

		return ddmFormInstanceRecordImpl;
	}

	@Override
	public int compareTo(DDMFormInstanceRecord ddmFormInstanceRecord) {
		long primaryKey = ddmFormInstanceRecord.getPrimaryKey();

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMFormInstanceRecord)) {
			return false;
		}

		DDMFormInstanceRecord ddmFormInstanceRecord =
			(DDMFormInstanceRecord)object;

		long primaryKey = ddmFormInstanceRecord.getPrimaryKey();

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

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<DDMFormInstanceRecord> toCacheModel() {
		DDMFormInstanceRecordCacheModel ddmFormInstanceRecordCacheModel =
			new DDMFormInstanceRecordCacheModel();

		ddmFormInstanceRecordCacheModel.mvccVersion = getMvccVersion();

		ddmFormInstanceRecordCacheModel.ctCollectionId = getCtCollectionId();

		ddmFormInstanceRecordCacheModel.uuid = getUuid();

		String uuid = ddmFormInstanceRecordCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			ddmFormInstanceRecordCacheModel.uuid = null;
		}

		ddmFormInstanceRecordCacheModel.formInstanceRecordId =
			getFormInstanceRecordId();

		ddmFormInstanceRecordCacheModel.groupId = getGroupId();

		ddmFormInstanceRecordCacheModel.companyId = getCompanyId();

		ddmFormInstanceRecordCacheModel.userId = getUserId();

		ddmFormInstanceRecordCacheModel.userName = getUserName();

		String userName = ddmFormInstanceRecordCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			ddmFormInstanceRecordCacheModel.userName = null;
		}

		ddmFormInstanceRecordCacheModel.versionUserId = getVersionUserId();

		ddmFormInstanceRecordCacheModel.versionUserName = getVersionUserName();

		String versionUserName =
			ddmFormInstanceRecordCacheModel.versionUserName;

		if ((versionUserName != null) && (versionUserName.length() == 0)) {
			ddmFormInstanceRecordCacheModel.versionUserName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			ddmFormInstanceRecordCacheModel.createDate = createDate.getTime();
		}
		else {
			ddmFormInstanceRecordCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ddmFormInstanceRecordCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			ddmFormInstanceRecordCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ddmFormInstanceRecordCacheModel.formInstanceId = getFormInstanceId();

		ddmFormInstanceRecordCacheModel.formInstanceVersion =
			getFormInstanceVersion();

		String formInstanceVersion =
			ddmFormInstanceRecordCacheModel.formInstanceVersion;

		if ((formInstanceVersion != null) &&
			(formInstanceVersion.length() == 0)) {

			ddmFormInstanceRecordCacheModel.formInstanceVersion = null;
		}

		ddmFormInstanceRecordCacheModel.storageId = getStorageId();

		ddmFormInstanceRecordCacheModel.version = getVersion();

		String version = ddmFormInstanceRecordCacheModel.version;

		if ((version != null) && (version.length() == 0)) {
			ddmFormInstanceRecordCacheModel.version = null;
		}

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			ddmFormInstanceRecordCacheModel.lastPublishDate =
				lastPublishDate.getTime();
		}
		else {
			ddmFormInstanceRecordCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return ddmFormInstanceRecordCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<DDMFormInstanceRecord, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<DDMFormInstanceRecord, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<DDMFormInstanceRecord, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((DDMFormInstanceRecord)this));
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
		Map<String, Function<DDMFormInstanceRecord, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<DDMFormInstanceRecord, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<DDMFormInstanceRecord, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((DDMFormInstanceRecord)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, DDMFormInstanceRecord>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _formInstanceRecordId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _formInstanceId;
	private String _formInstanceVersion;
	private long _storageId;
	private String _version;
	private Date _lastPublishDate;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<DDMFormInstanceRecord, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((DDMFormInstanceRecord)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("mvccVersion", _mvccVersion);
		_columnOriginalValues.put("ctCollectionId", _ctCollectionId);
		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put(
			"formInstanceRecordId", _formInstanceRecordId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("versionUserId", _versionUserId);
		_columnOriginalValues.put("versionUserName", _versionUserName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("formInstanceId", _formInstanceId);
		_columnOriginalValues.put("formInstanceVersion", _formInstanceVersion);
		_columnOriginalValues.put("storageId", _storageId);
		_columnOriginalValues.put("version", _version);
		_columnOriginalValues.put("lastPublishDate", _lastPublishDate);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("ctCollectionId", 2L);

		columnBitmasks.put("uuid_", 4L);

		columnBitmasks.put("formInstanceRecordId", 8L);

		columnBitmasks.put("groupId", 16L);

		columnBitmasks.put("companyId", 32L);

		columnBitmasks.put("userId", 64L);

		columnBitmasks.put("userName", 128L);

		columnBitmasks.put("versionUserId", 256L);

		columnBitmasks.put("versionUserName", 512L);

		columnBitmasks.put("createDate", 1024L);

		columnBitmasks.put("modifiedDate", 2048L);

		columnBitmasks.put("formInstanceId", 4096L);

		columnBitmasks.put("formInstanceVersion", 8192L);

		columnBitmasks.put("storageId", 16384L);

		columnBitmasks.put("version", 32768L);

		columnBitmasks.put("lastPublishDate", 65536L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private DDMFormInstanceRecord _escapedModel;

}