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

package com.liferay.adaptive.media.image.model.impl;

import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.model.AMImageEntryModel;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
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
 * The base model implementation for the AMImageEntry service. Represents a row in the &quot;AMImageEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>AMImageEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AMImageEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AMImageEntryImpl
 * @generated
 */
public class AMImageEntryModelImpl
	extends BaseModelImpl<AMImageEntry> implements AMImageEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a am image entry model instance should use the <code>AMImageEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "AMImageEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"amImageEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"configurationUuid", Types.VARCHAR},
		{"fileVersionId", Types.BIGINT}, {"mimeType", Types.VARCHAR},
		{"height", Types.INTEGER}, {"width", Types.INTEGER},
		{"size_", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("amImageEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("configurationUuid", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("fileVersionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("mimeType", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("height", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("width", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("size_", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AMImageEntry (uuid_ VARCHAR(75) null,amImageEntryId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,configurationUuid VARCHAR(75) null,fileVersionId LONG,mimeType VARCHAR(75) null,height INTEGER,width INTEGER,size_ LONG)";

	public static final String TABLE_SQL_DROP = "drop table AMImageEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY amImageEntry.amImageEntryId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY AMImageEntry.amImageEntryId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long CONFIGURATIONUUID_COLUMN_BITMASK = 2L;

	public static final long FILEVERSIONID_COLUMN_BITMASK = 4L;

	public static final long GROUPID_COLUMN_BITMASK = 8L;

	public static final long UUID_COLUMN_BITMASK = 16L;

	public static final long AMIMAGEENTRYID_COLUMN_BITMASK = 32L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public AMImageEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _amImageEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAmImageEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _amImageEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AMImageEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AMImageEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AMImageEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AMImageEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AMImageEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((AMImageEntry)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AMImageEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AMImageEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AMImageEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AMImageEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AMImageEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AMImageEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AMImageEntry.class.getClassLoader(), AMImageEntry.class,
			ModelWrapper.class);

		try {
			Constructor<AMImageEntry> constructor =
				(Constructor<AMImageEntry>)proxyClass.getConstructor(
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

	private static final Map<String, Function<AMImageEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<AMImageEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<AMImageEntry, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<AMImageEntry, Object>>();
		Map<String, BiConsumer<AMImageEntry, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<AMImageEntry, ?>>();

		attributeGetterFunctions.put("uuid", AMImageEntry::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<AMImageEntry, String>)AMImageEntry::setUuid);
		attributeGetterFunctions.put(
			"amImageEntryId", AMImageEntry::getAmImageEntryId);
		attributeSetterBiConsumers.put(
			"amImageEntryId",
			(BiConsumer<AMImageEntry, Long>)AMImageEntry::setAmImageEntryId);
		attributeGetterFunctions.put("groupId", AMImageEntry::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<AMImageEntry, Long>)AMImageEntry::setGroupId);
		attributeGetterFunctions.put("companyId", AMImageEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<AMImageEntry, Long>)AMImageEntry::setCompanyId);
		attributeGetterFunctions.put("createDate", AMImageEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<AMImageEntry, Date>)AMImageEntry::setCreateDate);
		attributeGetterFunctions.put(
			"configurationUuid", AMImageEntry::getConfigurationUuid);
		attributeSetterBiConsumers.put(
			"configurationUuid",
			(BiConsumer<AMImageEntry, String>)
				AMImageEntry::setConfigurationUuid);
		attributeGetterFunctions.put(
			"fileVersionId", AMImageEntry::getFileVersionId);
		attributeSetterBiConsumers.put(
			"fileVersionId",
			(BiConsumer<AMImageEntry, Long>)AMImageEntry::setFileVersionId);
		attributeGetterFunctions.put("mimeType", AMImageEntry::getMimeType);
		attributeSetterBiConsumers.put(
			"mimeType",
			(BiConsumer<AMImageEntry, String>)AMImageEntry::setMimeType);
		attributeGetterFunctions.put("height", AMImageEntry::getHeight);
		attributeSetterBiConsumers.put(
			"height",
			(BiConsumer<AMImageEntry, Integer>)AMImageEntry::setHeight);
		attributeGetterFunctions.put("width", AMImageEntry::getWidth);
		attributeSetterBiConsumers.put(
			"width", (BiConsumer<AMImageEntry, Integer>)AMImageEntry::setWidth);
		attributeGetterFunctions.put("size", AMImageEntry::getSize);
		attributeSetterBiConsumers.put(
			"size", (BiConsumer<AMImageEntry, Long>)AMImageEntry::setSize);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
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
	public long getAmImageEntryId() {
		return _amImageEntryId;
	}

	@Override
	public void setAmImageEntryId(long amImageEntryId) {
		_amImageEntryId = amImageEntryId;
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
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public String getConfigurationUuid() {
		if (_configurationUuid == null) {
			return "";
		}
		else {
			return _configurationUuid;
		}
	}

	@Override
	public void setConfigurationUuid(String configurationUuid) {
		_columnBitmask |= CONFIGURATIONUUID_COLUMN_BITMASK;

		if (_originalConfigurationUuid == null) {
			_originalConfigurationUuid = _configurationUuid;
		}

		_configurationUuid = configurationUuid;
	}

	public String getOriginalConfigurationUuid() {
		return GetterUtil.getString(_originalConfigurationUuid);
	}

	@Override
	public long getFileVersionId() {
		return _fileVersionId;
	}

	@Override
	public void setFileVersionId(long fileVersionId) {
		_columnBitmask |= FILEVERSIONID_COLUMN_BITMASK;

		if (!_setOriginalFileVersionId) {
			_setOriginalFileVersionId = true;

			_originalFileVersionId = _fileVersionId;
		}

		_fileVersionId = fileVersionId;
	}

	public long getOriginalFileVersionId() {
		return _originalFileVersionId;
	}

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

	@Override
	public int getHeight() {
		return _height;
	}

	@Override
	public void setHeight(int height) {
		_height = height;
	}

	@Override
	public int getWidth() {
		return _width;
	}

	@Override
	public void setWidth(int width) {
		_width = width;
	}

	@Override
	public long getSize() {
		return _size;
	}

	@Override
	public void setSize(long size) {
		_size = size;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), AMImageEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AMImageEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AMImageEntry>
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
		AMImageEntryImpl amImageEntryImpl = new AMImageEntryImpl();

		amImageEntryImpl.setUuid(getUuid());
		amImageEntryImpl.setAmImageEntryId(getAmImageEntryId());
		amImageEntryImpl.setGroupId(getGroupId());
		amImageEntryImpl.setCompanyId(getCompanyId());
		amImageEntryImpl.setCreateDate(getCreateDate());
		amImageEntryImpl.setConfigurationUuid(getConfigurationUuid());
		amImageEntryImpl.setFileVersionId(getFileVersionId());
		amImageEntryImpl.setMimeType(getMimeType());
		amImageEntryImpl.setHeight(getHeight());
		amImageEntryImpl.setWidth(getWidth());
		amImageEntryImpl.setSize(getSize());

		amImageEntryImpl.resetOriginalValues();

		return amImageEntryImpl;
	}

	@Override
	public int compareTo(AMImageEntry amImageEntry) {
		long primaryKey = amImageEntry.getPrimaryKey();

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

		if (!(obj instanceof AMImageEntry)) {
			return false;
		}

		AMImageEntry amImageEntry = (AMImageEntry)obj;

		long primaryKey = amImageEntry.getPrimaryKey();

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
		AMImageEntryModelImpl amImageEntryModelImpl = this;

		amImageEntryModelImpl._originalUuid = amImageEntryModelImpl._uuid;

		amImageEntryModelImpl._originalGroupId = amImageEntryModelImpl._groupId;

		amImageEntryModelImpl._setOriginalGroupId = false;

		amImageEntryModelImpl._originalCompanyId =
			amImageEntryModelImpl._companyId;

		amImageEntryModelImpl._setOriginalCompanyId = false;

		amImageEntryModelImpl._originalConfigurationUuid =
			amImageEntryModelImpl._configurationUuid;

		amImageEntryModelImpl._originalFileVersionId =
			amImageEntryModelImpl._fileVersionId;

		amImageEntryModelImpl._setOriginalFileVersionId = false;

		amImageEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AMImageEntry> toCacheModel() {
		AMImageEntryCacheModel amImageEntryCacheModel =
			new AMImageEntryCacheModel();

		amImageEntryCacheModel.uuid = getUuid();

		String uuid = amImageEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			amImageEntryCacheModel.uuid = null;
		}

		amImageEntryCacheModel.amImageEntryId = getAmImageEntryId();

		amImageEntryCacheModel.groupId = getGroupId();

		amImageEntryCacheModel.companyId = getCompanyId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			amImageEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			amImageEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		amImageEntryCacheModel.configurationUuid = getConfigurationUuid();

		String configurationUuid = amImageEntryCacheModel.configurationUuid;

		if ((configurationUuid != null) && (configurationUuid.length() == 0)) {
			amImageEntryCacheModel.configurationUuid = null;
		}

		amImageEntryCacheModel.fileVersionId = getFileVersionId();

		amImageEntryCacheModel.mimeType = getMimeType();

		String mimeType = amImageEntryCacheModel.mimeType;

		if ((mimeType != null) && (mimeType.length() == 0)) {
			amImageEntryCacheModel.mimeType = null;
		}

		amImageEntryCacheModel.height = getHeight();

		amImageEntryCacheModel.width = getWidth();

		amImageEntryCacheModel.size = getSize();

		return amImageEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AMImageEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AMImageEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AMImageEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((AMImageEntry)this));
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
		Map<String, Function<AMImageEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AMImageEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AMImageEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((AMImageEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, AMImageEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private String _uuid;
	private String _originalUuid;
	private long _amImageEntryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private Date _createDate;
	private String _configurationUuid;
	private String _originalConfigurationUuid;
	private long _fileVersionId;
	private long _originalFileVersionId;
	private boolean _setOriginalFileVersionId;
	private String _mimeType;
	private int _height;
	private int _width;
	private long _size;
	private long _columnBitmask;
	private AMImageEntry _escapedModel;

}