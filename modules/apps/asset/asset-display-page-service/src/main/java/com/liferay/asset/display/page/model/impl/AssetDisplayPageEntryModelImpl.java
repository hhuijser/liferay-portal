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

package com.liferay.asset.display.page.model.impl;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.model.AssetDisplayPageEntryModel;
import com.liferay.asset.display.page.model.AssetDisplayPageEntrySoap;
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
import com.liferay.portal.kernel.util.Validator;

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
 * The base model implementation for the AssetDisplayPageEntry service. Represents a row in the &quot;AssetDisplayPageEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>AssetDisplayPageEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssetDisplayPageEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntryImpl
 * @generated
 */
@JSON(strict = true)
public class AssetDisplayPageEntryModelImpl
	extends BaseModelImpl<AssetDisplayPageEntry>
	implements AssetDisplayPageEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset display page entry model instance should use the <code>AssetDisplayPageEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "AssetDisplayPageEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"assetDisplayPageEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"layoutPageTemplateEntryId", Types.BIGINT}, {"type_", Types.INTEGER},
		{"plid", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assetDisplayPageEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("layoutPageTemplateEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AssetDisplayPageEntry (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,assetDisplayPageEntryId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,layoutPageTemplateEntryId LONG,type_ INTEGER,plid LONG,primary key (assetDisplayPageEntryId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
		"drop table AssetDisplayPageEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY assetDisplayPageEntry.assetDisplayPageEntryId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY AssetDisplayPageEntry.assetDisplayPageEntryId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long LAYOUTPAGETEMPLATEENTRYID_COLUMN_BITMASK = 16L;

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
	public static final long ASSETDISPLAYPAGEENTRYID_COLUMN_BITMASK = 64L;

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
	public static AssetDisplayPageEntry toModel(
		AssetDisplayPageEntrySoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		AssetDisplayPageEntry model = new AssetDisplayPageEntryImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setCtCollectionId(soapModel.getCtCollectionId());
		model.setUuid(soapModel.getUuid());
		model.setAssetDisplayPageEntryId(
			soapModel.getAssetDisplayPageEntryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setLayoutPageTemplateEntryId(
			soapModel.getLayoutPageTemplateEntryId());
		model.setType(soapModel.getType());
		model.setPlid(soapModel.getPlid());

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
	public static List<AssetDisplayPageEntry> toModels(
		AssetDisplayPageEntrySoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<AssetDisplayPageEntry> models =
			new ArrayList<AssetDisplayPageEntry>(soapModels.length);

		for (AssetDisplayPageEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public AssetDisplayPageEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _assetDisplayPageEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAssetDisplayPageEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetDisplayPageEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AssetDisplayPageEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AssetDisplayPageEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AssetDisplayPageEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AssetDisplayPageEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetDisplayPageEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((AssetDisplayPageEntry)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AssetDisplayPageEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AssetDisplayPageEntry, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AssetDisplayPageEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AssetDisplayPageEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AssetDisplayPageEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AssetDisplayPageEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AssetDisplayPageEntry.class.getClassLoader(),
			AssetDisplayPageEntry.class, ModelWrapper.class);

		try {
			Constructor<AssetDisplayPageEntry> constructor =
				(Constructor<AssetDisplayPageEntry>)proxyClass.getConstructor(
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

	private static final Map<String, Function<AssetDisplayPageEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<AssetDisplayPageEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<AssetDisplayPageEntry, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<AssetDisplayPageEntry, Object>>();
		Map<String, BiConsumer<AssetDisplayPageEntry, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<AssetDisplayPageEntry, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", AssetDisplayPageEntry::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", AssetDisplayPageEntry::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setCtCollectionId);
		attributeGetterFunctions.put("uuid", AssetDisplayPageEntry::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<AssetDisplayPageEntry, String>)
				AssetDisplayPageEntry::setUuid);
		attributeGetterFunctions.put(
			"assetDisplayPageEntryId",
			AssetDisplayPageEntry::getAssetDisplayPageEntryId);
		attributeSetterBiConsumers.put(
			"assetDisplayPageEntryId",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setAssetDisplayPageEntryId);
		attributeGetterFunctions.put(
			"groupId", AssetDisplayPageEntry::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setGroupId);
		attributeGetterFunctions.put(
			"companyId", AssetDisplayPageEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setCompanyId);
		attributeGetterFunctions.put(
			"userId", AssetDisplayPageEntry::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setUserId);
		attributeGetterFunctions.put(
			"userName", AssetDisplayPageEntry::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<AssetDisplayPageEntry, String>)
				AssetDisplayPageEntry::setUserName);
		attributeGetterFunctions.put(
			"createDate", AssetDisplayPageEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<AssetDisplayPageEntry, Date>)
				AssetDisplayPageEntry::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", AssetDisplayPageEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<AssetDisplayPageEntry, Date>)
				AssetDisplayPageEntry::setModifiedDate);
		attributeGetterFunctions.put(
			"classNameId", AssetDisplayPageEntry::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setClassNameId);
		attributeGetterFunctions.put(
			"classPK", AssetDisplayPageEntry::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setClassPK);
		attributeGetterFunctions.put(
			"layoutPageTemplateEntryId",
			AssetDisplayPageEntry::getLayoutPageTemplateEntryId);
		attributeSetterBiConsumers.put(
			"layoutPageTemplateEntryId",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setLayoutPageTemplateEntryId);
		attributeGetterFunctions.put("type", AssetDisplayPageEntry::getType);
		attributeSetterBiConsumers.put(
			"type",
			(BiConsumer<AssetDisplayPageEntry, Integer>)
				AssetDisplayPageEntry::setType);
		attributeGetterFunctions.put("plid", AssetDisplayPageEntry::getPlid);
		attributeSetterBiConsumers.put(
			"plid",
			(BiConsumer<AssetDisplayPageEntry, Long>)
				AssetDisplayPageEntry::setPlid);

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
	public long getAssetDisplayPageEntryId() {
		return _assetDisplayPageEntryId;
	}

	@Override
	public void setAssetDisplayPageEntryId(long assetDisplayPageEntryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_assetDisplayPageEntryId = assetDisplayPageEntryId;
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

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_classNameId = classNameId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalClassNameId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("classNameId"));
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_classPK = classPK;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalClassPK() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("classPK"));
	}

	@JSON
	@Override
	public long getLayoutPageTemplateEntryId() {
		return _layoutPageTemplateEntryId;
	}

	@Override
	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_layoutPageTemplateEntryId = layoutPageTemplateEntryId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalLayoutPageTemplateEntryId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("layoutPageTemplateEntryId"));
	}

	@JSON
	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_type = type;
	}

	@JSON
	@Override
	public long getPlid() {
		return _plid;
	}

	@Override
	public void setPlid(long plid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_plid = plid;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(AssetDisplayPageEntry.class.getName()),
			getClassNameId());
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
			getCompanyId(), AssetDisplayPageEntry.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AssetDisplayPageEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AssetDisplayPageEntry>
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
		AssetDisplayPageEntryImpl assetDisplayPageEntryImpl =
			new AssetDisplayPageEntryImpl();

		assetDisplayPageEntryImpl.setMvccVersion(getMvccVersion());
		assetDisplayPageEntryImpl.setCtCollectionId(getCtCollectionId());
		assetDisplayPageEntryImpl.setUuid(getUuid());
		assetDisplayPageEntryImpl.setAssetDisplayPageEntryId(
			getAssetDisplayPageEntryId());
		assetDisplayPageEntryImpl.setGroupId(getGroupId());
		assetDisplayPageEntryImpl.setCompanyId(getCompanyId());
		assetDisplayPageEntryImpl.setUserId(getUserId());
		assetDisplayPageEntryImpl.setUserName(getUserName());
		assetDisplayPageEntryImpl.setCreateDate(getCreateDate());
		assetDisplayPageEntryImpl.setModifiedDate(getModifiedDate());
		assetDisplayPageEntryImpl.setClassNameId(getClassNameId());
		assetDisplayPageEntryImpl.setClassPK(getClassPK());
		assetDisplayPageEntryImpl.setLayoutPageTemplateEntryId(
			getLayoutPageTemplateEntryId());
		assetDisplayPageEntryImpl.setType(getType());
		assetDisplayPageEntryImpl.setPlid(getPlid());

		assetDisplayPageEntryImpl.resetOriginalValues();

		return assetDisplayPageEntryImpl;
	}

	@Override
	public int compareTo(AssetDisplayPageEntry assetDisplayPageEntry) {
		long primaryKey = assetDisplayPageEntry.getPrimaryKey();

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

		if (!(object instanceof AssetDisplayPageEntry)) {
			return false;
		}

		AssetDisplayPageEntry assetDisplayPageEntry =
			(AssetDisplayPageEntry)object;

		long primaryKey = assetDisplayPageEntry.getPrimaryKey();

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
	public CacheModel<AssetDisplayPageEntry> toCacheModel() {
		AssetDisplayPageEntryCacheModel assetDisplayPageEntryCacheModel =
			new AssetDisplayPageEntryCacheModel();

		assetDisplayPageEntryCacheModel.mvccVersion = getMvccVersion();

		assetDisplayPageEntryCacheModel.ctCollectionId = getCtCollectionId();

		assetDisplayPageEntryCacheModel.uuid = getUuid();

		String uuid = assetDisplayPageEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			assetDisplayPageEntryCacheModel.uuid = null;
		}

		assetDisplayPageEntryCacheModel.assetDisplayPageEntryId =
			getAssetDisplayPageEntryId();

		assetDisplayPageEntryCacheModel.groupId = getGroupId();

		assetDisplayPageEntryCacheModel.companyId = getCompanyId();

		assetDisplayPageEntryCacheModel.userId = getUserId();

		assetDisplayPageEntryCacheModel.userName = getUserName();

		String userName = assetDisplayPageEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			assetDisplayPageEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			assetDisplayPageEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			assetDisplayPageEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			assetDisplayPageEntryCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			assetDisplayPageEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		assetDisplayPageEntryCacheModel.classNameId = getClassNameId();

		assetDisplayPageEntryCacheModel.classPK = getClassPK();

		assetDisplayPageEntryCacheModel.layoutPageTemplateEntryId =
			getLayoutPageTemplateEntryId();

		assetDisplayPageEntryCacheModel.type = getType();

		assetDisplayPageEntryCacheModel.plid = getPlid();

		return assetDisplayPageEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AssetDisplayPageEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AssetDisplayPageEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetDisplayPageEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((AssetDisplayPageEntry)this));
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
		Map<String, Function<AssetDisplayPageEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AssetDisplayPageEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetDisplayPageEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((AssetDisplayPageEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, AssetDisplayPageEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _assetDisplayPageEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _layoutPageTemplateEntryId;
	private int _type;
	private long _plid;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<AssetDisplayPageEntry, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((AssetDisplayPageEntry)this);
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
			"assetDisplayPageEntryId", _assetDisplayPageEntryId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("classNameId", _classNameId);
		_columnOriginalValues.put("classPK", _classPK);
		_columnOriginalValues.put(
			"layoutPageTemplateEntryId", _layoutPageTemplateEntryId);
		_columnOriginalValues.put("type_", _type);
		_columnOriginalValues.put("plid", _plid);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");
		attributeNames.put("type_", "type");

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

		columnBitmasks.put("assetDisplayPageEntryId", 8L);

		columnBitmasks.put("groupId", 16L);

		columnBitmasks.put("companyId", 32L);

		columnBitmasks.put("userId", 64L);

		columnBitmasks.put("userName", 128L);

		columnBitmasks.put("createDate", 256L);

		columnBitmasks.put("modifiedDate", 512L);

		columnBitmasks.put("classNameId", 1024L);

		columnBitmasks.put("classPK", 2048L);

		columnBitmasks.put("layoutPageTemplateEntryId", 4096L);

		columnBitmasks.put("type_", 8192L);

		columnBitmasks.put("plid", 16384L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private AssetDisplayPageEntry _escapedModel;

}