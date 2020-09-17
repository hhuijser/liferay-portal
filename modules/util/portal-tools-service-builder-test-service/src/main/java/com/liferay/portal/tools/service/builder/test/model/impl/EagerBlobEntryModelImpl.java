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

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntryModel;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntrySoap;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Blob;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the EagerBlobEntry service. Represents a row in the &quot;EagerBlobEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>EagerBlobEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link EagerBlobEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EagerBlobEntryImpl
 * @generated
 */
@JSON(strict = true)
public class EagerBlobEntryModelImpl
	extends BaseModelImpl<EagerBlobEntry> implements EagerBlobEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a eager blob entry model instance should use the <code>EagerBlobEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "EagerBlobEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"eagerBlobEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"blob_", Types.BLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("eagerBlobEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("blob_", Types.BLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table EagerBlobEntry (uuid_ VARCHAR(75) null,eagerBlobEntryId LONG not null primary key,groupId LONG,blob_ BLOB)";

	public static final String TABLE_SQL_DROP = "drop table EagerBlobEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY eagerBlobEntry.eagerBlobEntryId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY EagerBlobEntry.eagerBlobEntryId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean ENTITY_CACHE_ENABLED = false;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean FINDER_CACHE_ENABLED = false;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static EagerBlobEntry toModel(EagerBlobEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		EagerBlobEntry model = new EagerBlobEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setEagerBlobEntryId(soapModel.getEagerBlobEntryId());
		model.setGroupId(soapModel.getGroupId());
		model.setBlob(soapModel.getBlob());

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
	public static List<EagerBlobEntry> toModels(
		EagerBlobEntrySoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<EagerBlobEntry> models = new ArrayList<EagerBlobEntry>(
			soapModels.length);

		for (EagerBlobEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.tools.service.builder.test.service.util.ServiceProps.
			get(
				"lock.expiration.time.com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry"));

	public EagerBlobEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _eagerBlobEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setEagerBlobEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _eagerBlobEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return EagerBlobEntry.class;
	}

	@Override
	public String getModelClassName() {
		return EagerBlobEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<EagerBlobEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<EagerBlobEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<EagerBlobEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((EagerBlobEntry)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<EagerBlobEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<EagerBlobEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(EagerBlobEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<EagerBlobEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<EagerBlobEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, EagerBlobEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			EagerBlobEntry.class.getClassLoader(), EagerBlobEntry.class,
			ModelWrapper.class);

		try {
			Constructor<EagerBlobEntry> constructor =
				(Constructor<EagerBlobEntry>)proxyClass.getConstructor(
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

	private static final Map<String, Function<EagerBlobEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<EagerBlobEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<EagerBlobEntry, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<EagerBlobEntry, Object>>();
		Map<String, BiConsumer<EagerBlobEntry, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<EagerBlobEntry, ?>>();

		attributeGetterFunctions.put("uuid", EagerBlobEntry::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<EagerBlobEntry, String>)EagerBlobEntry::setUuid);
		attributeGetterFunctions.put(
			"eagerBlobEntryId", EagerBlobEntry::getEagerBlobEntryId);
		attributeSetterBiConsumers.put(
			"eagerBlobEntryId",
			(BiConsumer<EagerBlobEntry, Long>)
				EagerBlobEntry::setEagerBlobEntryId);
		attributeGetterFunctions.put("groupId", EagerBlobEntry::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<EagerBlobEntry, Long>)EagerBlobEntry::setGroupId);
		attributeGetterFunctions.put("blob", EagerBlobEntry::getBlob);
		attributeSetterBiConsumers.put(
			"blob", (BiConsumer<EagerBlobEntry, Blob>)EagerBlobEntry::setBlob);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
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
	public long getEagerBlobEntryId() {
		return _eagerBlobEntryId;
	}

	@Override
	public void setEagerBlobEntryId(long eagerBlobEntryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_eagerBlobEntryId = eagerBlobEntryId;
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
	public Blob getBlob() {
		return _blob;
	}

	@Override
	public void setBlob(Blob blob) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_blob = blob;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			0, EagerBlobEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public EagerBlobEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, EagerBlobEntry>
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
		EagerBlobEntryImpl eagerBlobEntryImpl = new EagerBlobEntryImpl();

		eagerBlobEntryImpl.setUuid(getUuid());
		eagerBlobEntryImpl.setEagerBlobEntryId(getEagerBlobEntryId());
		eagerBlobEntryImpl.setGroupId(getGroupId());

		eagerBlobEntryImpl.resetOriginalValues();

		return eagerBlobEntryImpl;
	}

	@Override
	public int compareTo(EagerBlobEntry eagerBlobEntry) {
		long primaryKey = eagerBlobEntry.getPrimaryKey();

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

		if (!(object instanceof EagerBlobEntry)) {
			return false;
		}

		EagerBlobEntry eagerBlobEntry = (EagerBlobEntry)object;

		long primaryKey = eagerBlobEntry.getPrimaryKey();

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
		return ENTITY_CACHE_ENABLED;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();
	}

	@Override
	public CacheModel<EagerBlobEntry> toCacheModel() {
		EagerBlobEntryCacheModel eagerBlobEntryCacheModel =
			new EagerBlobEntryCacheModel();

		eagerBlobEntryCacheModel.uuid = getUuid();

		String uuid = eagerBlobEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			eagerBlobEntryCacheModel.uuid = null;
		}

		eagerBlobEntryCacheModel.eagerBlobEntryId = getEagerBlobEntryId();

		eagerBlobEntryCacheModel.groupId = getGroupId();

		return eagerBlobEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<EagerBlobEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<EagerBlobEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<EagerBlobEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((EagerBlobEntry)this));
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
		Map<String, Function<EagerBlobEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<EagerBlobEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<EagerBlobEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((EagerBlobEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, EagerBlobEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private String _uuid;
	private long _eagerBlobEntryId;
	private long _groupId;
	private Blob _blob;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<EagerBlobEntry, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((EagerBlobEntry)this);
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

		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put("eagerBlobEntryId", _eagerBlobEntryId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("blob_", _blob);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");
		attributeNames.put("blob_", "blob");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;
	private EagerBlobEntry _escapedModel;

}