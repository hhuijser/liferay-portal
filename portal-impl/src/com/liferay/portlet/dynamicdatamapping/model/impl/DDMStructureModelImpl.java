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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureModel;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The base model implementation for the DDMStructure service. Represents a row in the &quot;DDMStructure&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.dynamicdatamapping.model.DDMStructureModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDMStructureImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureImpl
 * @see com.liferay.portlet.dynamicdatamapping.model.DDMStructure
 * @see com.liferay.portlet.dynamicdatamapping.model.DDMStructureModel
 * @generated
 */
@JSON(strict = true)
public class DDMStructureModelImpl extends BaseModelImpl<DDMStructure>
	implements DDMStructureModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a d d m structure model instance should use the {@link com.liferay.portlet.dynamicdatamapping.model.DDMStructure} interface instead.
	 */
	public static final String TABLE_NAME = "DDMStructure";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "structureId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "parentStructureId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "structureKey", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "xsd", Types.CLOB },
			{ "storageType", Types.VARCHAR },
			{ "type_", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table DDMStructure (uuid_ VARCHAR(75) null,structureId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentStructureId LONG,classNameId LONG,structureKey VARCHAR(75) null,name STRING null,description STRING null,xsd TEXT null,storageType VARCHAR(75) null,type_ INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table DDMStructure";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.dynamicdatamapping.model.DDMStructure"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.dynamicdatamapping.model.DDMStructure"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portlet.dynamicdatamapping.model.DDMStructure"),
			true);
	public static long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static long COMPANYID_COLUMN_BITMASK = 2L;
	public static long DESCRIPTION_COLUMN_BITMASK = 4L;
	public static long GROUPID_COLUMN_BITMASK = 8L;
	public static long NAME_COLUMN_BITMASK = 16L;
	public static long STRUCTUREKEY_COLUMN_BITMASK = 32L;
	public static long UUID_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static DDMStructure toModel(DDMStructureSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		DDMStructure model = new DDMStructureImpl();

		model.setUuid(soapModel.getUuid());
		model.setStructureId(soapModel.getStructureId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setParentStructureId(soapModel.getParentStructureId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setStructureKey(soapModel.getStructureKey());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setXsd(soapModel.getXsd());
		model.setStorageType(soapModel.getStorageType());
		model.setType(soapModel.getType());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<DDMStructure> toModels(DDMStructureSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<DDMStructure> models = new ArrayList<DDMStructure>(soapModels.length);

		for (DDMStructureSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.dynamicdatamapping.model.DDMStructure"));

	public DDMStructureModelImpl() {
	}

	public long getPrimaryKey() {
		return _structureId;
	}

	public void setPrimaryKey(long primaryKey) {
		setStructureId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_structureId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return DDMStructure.class;
	}

	public String getModelClassName() {
		return DDMStructure.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("structureId", getStructureId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentStructureId", getParentStructureId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("structureKey", getStructureKey());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("xsd", getXsd());
		attributes.put("storageType", getStorageType());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Long parentStructureId = (Long)attributes.get("parentStructureId");

		if (parentStructureId != null) {
			setParentStructureId(parentStructureId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String structureKey = (String)attributes.get("structureKey");

		if (structureKey != null) {
			setStructureKey(structureKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String xsd = (String)attributes.get("xsd");

		if (xsd != null) {
			setXsd(xsd);
		}

		String storageType = (String)attributes.get("storageType");

		if (storageType != null) {
			setStorageType(storageType);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
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
	public long getStructureId() {
		return _structureId;
	}

	public void setStructureId(long structureId) {
		_structureId = structureId;
	}

	@JSON
	public long getGroupId() {
		return _groupId;
	}

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
	public long getParentStructureId() {
		return _parentStructureId;
	}

	public void setParentStructureId(long parentStructureId) {
		_parentStructureId = parentStructureId;
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
	public String getStructureKey() {
		if (_structureKey == null) {
			return StringPool.BLANK;
		}
		else {
			return _structureKey;
		}
	}

	public void setStructureKey(String structureKey) {
		_columnBitmask |= STRUCTUREKEY_COLUMN_BITMASK;

		if (_originalStructureKey == null) {
			_originalStructureKey = _structureKey;
		}

		_structureKey = structureKey;
	}

	public String getOriginalStructureKey() {
		return GetterUtil.getString(_originalStructureKey);
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

	public String getName(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId);
	}

	public String getName(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId, useDefault);
	}

	public String getName(String languageId) {
		return LocalizationUtil.getLocalization(getName(), languageId);
	}

	public String getName(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getName(), languageId,
			useDefault);
	}

	public String getNameCurrentLanguageId() {
		return _nameCurrentLanguageId;
	}

	@JSON
	public String getNameCurrentValue() {
		Locale locale = getLocale(_nameCurrentLanguageId);

		return getName(locale);
	}

	public Map<Locale, String> getNameMap() {
		return LocalizationUtil.getLocalizationMap(getName());
	}

	public void setName(String name) {
		_columnBitmask |= NAME_COLUMN_BITMASK;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public void setName(String name, Locale locale) {
		setName(name, locale, LocaleUtil.getDefault());
	}

	public void setName(String name, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(name)) {
			setName(LocalizationUtil.updateLocalization(getName(), "Name",
					name, languageId, defaultLanguageId));
		}
		else {
			setName(LocalizationUtil.removeLocalization(getName(), "Name",
					languageId));
		}
	}

	public void setNameCurrentLanguageId(String languageId) {
		_nameCurrentLanguageId = languageId;
	}

	public void setNameMap(Map<Locale, String> nameMap) {
		setNameMap(nameMap, LocaleUtil.getDefault());
	}

	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		if (nameMap == null) {
			return;
		}

		setName(LocalizationUtil.updateLocalization(nameMap, getName(), "Name",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
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

	public String getDescription(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId);
	}

	public String getDescription(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId, useDefault);
	}

	public String getDescription(String languageId) {
		return LocalizationUtil.getLocalization(getDescription(), languageId);
	}

	public String getDescription(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getDescription(), languageId,
			useDefault);
	}

	public String getDescriptionCurrentLanguageId() {
		return _descriptionCurrentLanguageId;
	}

	@JSON
	public String getDescriptionCurrentValue() {
		Locale locale = getLocale(_descriptionCurrentLanguageId);

		return getDescription(locale);
	}

	public Map<Locale, String> getDescriptionMap() {
		return LocalizationUtil.getLocalizationMap(getDescription());
	}

	public void setDescription(String description) {
		_columnBitmask |= DESCRIPTION_COLUMN_BITMASK;

		if (_originalDescription == null) {
			_originalDescription = _description;
		}

		_description = description;
	}

	public void setDescription(String description, Locale locale) {
		setDescription(description, locale, LocaleUtil.getDefault());
	}

	public void setDescription(String description, Locale locale,
		Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(description)) {
			setDescription(LocalizationUtil.updateLocalization(
					getDescription(), "Description", description, languageId,
					defaultLanguageId));
		}
		else {
			setDescription(LocalizationUtil.removeLocalization(
					getDescription(), "Description", languageId));
		}
	}

	public void setDescriptionCurrentLanguageId(String languageId) {
		_descriptionCurrentLanguageId = languageId;
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		setDescriptionMap(descriptionMap, LocaleUtil.getDefault());
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale) {
		if (descriptionMap == null) {
			return;
		}

		setDescription(LocalizationUtil.updateLocalization(descriptionMap,
				getDescription(), "Description",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	public String getOriginalDescription() {
		return GetterUtil.getString(_originalDescription);
	}

	@JSON
	public String getXsd() {
		if (_xsd == null) {
			return StringPool.BLANK;
		}
		else {
			return _xsd;
		}
	}

	public void setXsd(String xsd) {
		_xsd = xsd;
	}

	@JSON
	public String getStorageType() {
		if (_storageType == null) {
			return StringPool.BLANK;
		}
		else {
			return _storageType;
		}
	}

	public void setStorageType(String storageType) {
		_storageType = storageType;
	}

	@JSON
	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public com.liferay.portal.kernel.xml.Document getDocument() {
		return null;
	}

	public void setDocument(com.liferay.portal.kernel.xml.Document document) {
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			DDMStructure.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@SuppressWarnings("unused")
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {
		setName(getName(defaultImportLocale), defaultImportLocale,
			defaultImportLocale);
		setDescription(getDescription(defaultImportLocale),
			defaultImportLocale, defaultImportLocale);
	}

	@Override
	public DDMStructure toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (DDMStructure)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public Object clone() {
		DDMStructureImpl ddmStructureImpl = new DDMStructureImpl();

		ddmStructureImpl.setUuid(getUuid());
		ddmStructureImpl.setStructureId(getStructureId());
		ddmStructureImpl.setGroupId(getGroupId());
		ddmStructureImpl.setCompanyId(getCompanyId());
		ddmStructureImpl.setUserId(getUserId());
		ddmStructureImpl.setUserName(getUserName());
		ddmStructureImpl.setCreateDate(getCreateDate());
		ddmStructureImpl.setModifiedDate(getModifiedDate());
		ddmStructureImpl.setParentStructureId(getParentStructureId());
		ddmStructureImpl.setClassNameId(getClassNameId());
		ddmStructureImpl.setStructureKey(getStructureKey());
		ddmStructureImpl.setName(getName());
		ddmStructureImpl.setDescription(getDescription());
		ddmStructureImpl.setXsd(getXsd());
		ddmStructureImpl.setStorageType(getStorageType());
		ddmStructureImpl.setType(getType());

		ddmStructureImpl.resetOriginalValues();

		return ddmStructureImpl;
	}

	public int compareTo(DDMStructure ddmStructure) {
		long primaryKey = ddmStructure.getPrimaryKey();

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

		DDMStructure ddmStructure = null;

		try {
			ddmStructure = (DDMStructure)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = ddmStructure.getPrimaryKey();

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
		DDMStructureModelImpl ddmStructureModelImpl = this;

		ddmStructureModelImpl._originalUuid = ddmStructureModelImpl._uuid;

		ddmStructureModelImpl._originalGroupId = ddmStructureModelImpl._groupId;

		ddmStructureModelImpl._setOriginalGroupId = false;

		ddmStructureModelImpl._originalCompanyId = ddmStructureModelImpl._companyId;

		ddmStructureModelImpl._setOriginalCompanyId = false;

		ddmStructureModelImpl._originalClassNameId = ddmStructureModelImpl._classNameId;

		ddmStructureModelImpl._setOriginalClassNameId = false;

		ddmStructureModelImpl._originalStructureKey = ddmStructureModelImpl._structureKey;

		ddmStructureModelImpl._originalName = ddmStructureModelImpl._name;

		ddmStructureModelImpl._originalDescription = ddmStructureModelImpl._description;

		ddmStructureModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DDMStructure> toCacheModel() {
		DDMStructureCacheModel ddmStructureCacheModel = new DDMStructureCacheModel();

		ddmStructureCacheModel.uuid = getUuid();

		String uuid = ddmStructureCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			ddmStructureCacheModel.uuid = null;
		}

		ddmStructureCacheModel.structureId = getStructureId();

		ddmStructureCacheModel.groupId = getGroupId();

		ddmStructureCacheModel.companyId = getCompanyId();

		ddmStructureCacheModel.userId = getUserId();

		ddmStructureCacheModel.userName = getUserName();

		String userName = ddmStructureCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			ddmStructureCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			ddmStructureCacheModel.createDate = createDate.getTime();
		}
		else {
			ddmStructureCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ddmStructureCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			ddmStructureCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ddmStructureCacheModel.parentStructureId = getParentStructureId();

		ddmStructureCacheModel.classNameId = getClassNameId();

		ddmStructureCacheModel.structureKey = getStructureKey();

		String structureKey = ddmStructureCacheModel.structureKey;

		if ((structureKey != null) && (structureKey.length() == 0)) {
			ddmStructureCacheModel.structureKey = null;
		}

		ddmStructureCacheModel.name = getName();

		String name = ddmStructureCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			ddmStructureCacheModel.name = null;
		}

		ddmStructureCacheModel.description = getDescription();

		String description = ddmStructureCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			ddmStructureCacheModel.description = null;
		}

		ddmStructureCacheModel.xsd = getXsd();

		String xsd = ddmStructureCacheModel.xsd;

		if ((xsd != null) && (xsd.length() == 0)) {
			ddmStructureCacheModel.xsd = null;
		}

		ddmStructureCacheModel.storageType = getStorageType();

		String storageType = ddmStructureCacheModel.storageType;

		if ((storageType != null) && (storageType.length() == 0)) {
			ddmStructureCacheModel.storageType = null;
		}

		ddmStructureCacheModel.type = getType();

		ddmStructureCacheModel._document = getDocument();

		return ddmStructureCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", structureId=");
		sb.append(getStructureId());
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
		sb.append(", parentStructureId=");
		sb.append(getParentStructureId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", structureKey=");
		sb.append(getStructureKey());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", xsd=");
		sb.append(getXsd());
		sb.append(", storageType=");
		sb.append(getStorageType());
		sb.append(", type=");
		sb.append(getType());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.dynamicdatamapping.model.DDMStructure");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>structureId</column-name><column-value><![CDATA[");
		sb.append(getStructureId());
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
			"<column><column-name>parentStructureId</column-name><column-value><![CDATA[");
		sb.append(getParentStructureId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>structureKey</column-name><column-value><![CDATA[");
		sb.append(getStructureKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>xsd</column-name><column-value><![CDATA[");
		sb.append(getXsd());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>storageType</column-name><column-value><![CDATA[");
		sb.append(getStorageType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = DDMStructure.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			DDMStructure.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _structureId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _parentStructureId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private String _structureKey;
	private String _originalStructureKey;
	private String _name;
	private String _nameCurrentLanguageId;
	private String _originalName;
	private String _description;
	private String _descriptionCurrentLanguageId;
	private String _originalDescription;
	private String _xsd;
	private String _storageType;
	private int _type;
	private long _columnBitmask;
	private DDMStructure _escapedModelProxy;
}