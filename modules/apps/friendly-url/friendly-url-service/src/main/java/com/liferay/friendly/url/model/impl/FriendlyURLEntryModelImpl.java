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

package com.liferay.friendly.url.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLEntryModel;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the FriendlyURLEntry service. Represents a row in the &quot;FriendlyURLEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>FriendlyURLEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link FriendlyURLEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryImpl
 * @generated
 */
public class FriendlyURLEntryModelImpl
	extends BaseModelImpl<FriendlyURLEntry> implements FriendlyURLEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a friendly url entry model instance should use the <code>FriendlyURLEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "FriendlyURLEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"defaultLanguageId", Types.VARCHAR},
		{"friendlyURLEntryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("defaultLanguageId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("friendlyURLEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table FriendlyURLEntry (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,defaultLanguageId VARCHAR(75) null,friendlyURLEntryId LONG not null,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,primary key (friendlyURLEntryId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table FriendlyURLEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY friendlyURLEntry.friendlyURLEntryId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY FriendlyURLEntry.friendlyURLEntryId ASC";

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
	public static final long UUID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long FRIENDLYURLENTRYID_COLUMN_BITMASK = 32L;

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

	public FriendlyURLEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _friendlyURLEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFriendlyURLEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLEntry.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<FriendlyURLEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<FriendlyURLEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<FriendlyURLEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((FriendlyURLEntry)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<FriendlyURLEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<FriendlyURLEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(FriendlyURLEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<FriendlyURLEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<FriendlyURLEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, FriendlyURLEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			FriendlyURLEntry.class.getClassLoader(), FriendlyURLEntry.class,
			ModelWrapper.class);

		try {
			Constructor<FriendlyURLEntry> constructor =
				(Constructor<FriendlyURLEntry>)proxyClass.getConstructor(
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

	private static final Map<String, Function<FriendlyURLEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<FriendlyURLEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<FriendlyURLEntry, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<FriendlyURLEntry, Object>>();
		Map<String, BiConsumer<FriendlyURLEntry, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<FriendlyURLEntry, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", FriendlyURLEntry::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<FriendlyURLEntry, Long>)
				FriendlyURLEntry::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", FriendlyURLEntry::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<FriendlyURLEntry, Long>)
				FriendlyURLEntry::setCtCollectionId);
		attributeGetterFunctions.put("uuid", FriendlyURLEntry::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<FriendlyURLEntry, String>)FriendlyURLEntry::setUuid);
		attributeGetterFunctions.put(
			"defaultLanguageId", FriendlyURLEntry::getDefaultLanguageId);
		attributeSetterBiConsumers.put(
			"defaultLanguageId",
			(BiConsumer<FriendlyURLEntry, String>)
				FriendlyURLEntry::setDefaultLanguageId);
		attributeGetterFunctions.put(
			"friendlyURLEntryId", FriendlyURLEntry::getFriendlyURLEntryId);
		attributeSetterBiConsumers.put(
			"friendlyURLEntryId",
			(BiConsumer<FriendlyURLEntry, Long>)
				FriendlyURLEntry::setFriendlyURLEntryId);
		attributeGetterFunctions.put("groupId", FriendlyURLEntry::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<FriendlyURLEntry, Long>)FriendlyURLEntry::setGroupId);
		attributeGetterFunctions.put(
			"companyId", FriendlyURLEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<FriendlyURLEntry, Long>)FriendlyURLEntry::setCompanyId);
		attributeGetterFunctions.put(
			"createDate", FriendlyURLEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<FriendlyURLEntry, Date>)
				FriendlyURLEntry::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", FriendlyURLEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<FriendlyURLEntry, Date>)
				FriendlyURLEntry::setModifiedDate);
		attributeGetterFunctions.put(
			"classNameId", FriendlyURLEntry::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<FriendlyURLEntry, Long>)
				FriendlyURLEntry::setClassNameId);
		attributeGetterFunctions.put("classPK", FriendlyURLEntry::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<FriendlyURLEntry, Long>)FriendlyURLEntry::setClassPK);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
			FriendlyURLEntryLocalServiceUtil.getFriendlyURLEntryLocalizations(
				getPrimaryKey());

		String[] availableLanguageIds =
			new String[friendlyURLEntryLocalizations.size()];

		for (int i = 0; i < availableLanguageIds.length; i++) {
			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				friendlyURLEntryLocalizations.get(i);

			availableLanguageIds[i] =
				friendlyURLEntryLocalization.getLanguageId();
		}

		return availableLanguageIds;
	}

	@Override
	public String getUrlTitle() {
		return getUrlTitle(getDefaultLanguageId(), false);
	}

	@Override
	public String getUrlTitle(String languageId) {
		return getUrlTitle(languageId, true);
	}

	@Override
	public String getUrlTitle(String languageId, boolean useDefault) {
		if (useDefault) {
			return LocalizationUtil.getLocalization(
				new Function<String, String>() {

					@Override
					public String apply(String languageId) {
						return _getUrlTitle(languageId);
					}

				},
				languageId, getDefaultLanguageId());
		}

		return _getUrlTitle(languageId);
	}

	@Override
	public String getUrlTitleMapAsXML() {
		return LocalizationUtil.getXml(
			getLanguageIdToUrlTitleMap(), getDefaultLanguageId(), "UrlTitle");
	}

	@Override
	public Map<String, String> getLanguageIdToUrlTitleMap() {
		Map<String, String> languageIdToUrlTitleMap =
			new HashMap<String, String>();

		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
			FriendlyURLEntryLocalServiceUtil.getFriendlyURLEntryLocalizations(
				getPrimaryKey());

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
				friendlyURLEntryLocalizations) {

			languageIdToUrlTitleMap.put(
				friendlyURLEntryLocalization.getLanguageId(),
				friendlyURLEntryLocalization.getUrlTitle());
		}

		return languageIdToUrlTitleMap;
	}

	private String _getUrlTitle(String languageId) {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			FriendlyURLEntryLocalServiceUtil.fetchFriendlyURLEntryLocalization(
				getPrimaryKey(), languageId);

		if (friendlyURLEntryLocalization == null) {
			return "";
		}

		return friendlyURLEntryLocalization.getUrlTitle();
	}

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

	@Override
	public String getDefaultLanguageId() {
		if (_defaultLanguageId == null) {
			return "";
		}
		else {
			return _defaultLanguageId;
		}
	}

	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_defaultLanguageId = defaultLanguageId;
	}

	@Override
	public long getFriendlyURLEntryId() {
		return _friendlyURLEntryId;
	}

	@Override
	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_friendlyURLEntryId = friendlyURLEntryId;
	}

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

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(FriendlyURLEntry.class.getName()),
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
			getCompanyId(), FriendlyURLEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public FriendlyURLEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, FriendlyURLEntry>
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
		FriendlyURLEntryImpl friendlyURLEntryImpl = new FriendlyURLEntryImpl();

		friendlyURLEntryImpl.setMvccVersion(getMvccVersion());
		friendlyURLEntryImpl.setCtCollectionId(getCtCollectionId());
		friendlyURLEntryImpl.setUuid(getUuid());
		friendlyURLEntryImpl.setDefaultLanguageId(getDefaultLanguageId());
		friendlyURLEntryImpl.setFriendlyURLEntryId(getFriendlyURLEntryId());
		friendlyURLEntryImpl.setGroupId(getGroupId());
		friendlyURLEntryImpl.setCompanyId(getCompanyId());
		friendlyURLEntryImpl.setCreateDate(getCreateDate());
		friendlyURLEntryImpl.setModifiedDate(getModifiedDate());
		friendlyURLEntryImpl.setClassNameId(getClassNameId());
		friendlyURLEntryImpl.setClassPK(getClassPK());

		friendlyURLEntryImpl.resetOriginalValues();

		return friendlyURLEntryImpl;
	}

	@Override
	public int compareTo(FriendlyURLEntry friendlyURLEntry) {
		long primaryKey = friendlyURLEntry.getPrimaryKey();

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

		if (!(object instanceof FriendlyURLEntry)) {
			return false;
		}

		FriendlyURLEntry friendlyURLEntry = (FriendlyURLEntry)object;

		long primaryKey = friendlyURLEntry.getPrimaryKey();

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
	public CacheModel<FriendlyURLEntry> toCacheModel() {
		FriendlyURLEntryCacheModel friendlyURLEntryCacheModel =
			new FriendlyURLEntryCacheModel();

		friendlyURLEntryCacheModel.mvccVersion = getMvccVersion();

		friendlyURLEntryCacheModel.ctCollectionId = getCtCollectionId();

		friendlyURLEntryCacheModel.uuid = getUuid();

		String uuid = friendlyURLEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			friendlyURLEntryCacheModel.uuid = null;
		}

		friendlyURLEntryCacheModel.defaultLanguageId = getDefaultLanguageId();

		String defaultLanguageId = friendlyURLEntryCacheModel.defaultLanguageId;

		if ((defaultLanguageId != null) && (defaultLanguageId.length() == 0)) {
			friendlyURLEntryCacheModel.defaultLanguageId = null;
		}

		friendlyURLEntryCacheModel.friendlyURLEntryId = getFriendlyURLEntryId();

		friendlyURLEntryCacheModel.groupId = getGroupId();

		friendlyURLEntryCacheModel.companyId = getCompanyId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			friendlyURLEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			friendlyURLEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			friendlyURLEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			friendlyURLEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		friendlyURLEntryCacheModel.classNameId = getClassNameId();

		friendlyURLEntryCacheModel.classPK = getClassPK();

		return friendlyURLEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<FriendlyURLEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<FriendlyURLEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<FriendlyURLEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((FriendlyURLEntry)this));
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
		Map<String, Function<FriendlyURLEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<FriendlyURLEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<FriendlyURLEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((FriendlyURLEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, FriendlyURLEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private String _defaultLanguageId;
	private long _friendlyURLEntryId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _classPK;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<FriendlyURLEntry, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((FriendlyURLEntry)this);
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
		_columnOriginalValues.put("defaultLanguageId", _defaultLanguageId);
		_columnOriginalValues.put("friendlyURLEntryId", _friendlyURLEntryId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("classNameId", _classNameId);
		_columnOriginalValues.put("classPK", _classPK);
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

		columnBitmasks.put("defaultLanguageId", 8L);

		columnBitmasks.put("friendlyURLEntryId", 16L);

		columnBitmasks.put("groupId", 32L);

		columnBitmasks.put("companyId", 64L);

		columnBitmasks.put("createDate", 128L);

		columnBitmasks.put("modifiedDate", 256L);

		columnBitmasks.put("classNameId", 512L);

		columnBitmasks.put("classPK", 1024L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private FriendlyURLEntry _escapedModel;

}