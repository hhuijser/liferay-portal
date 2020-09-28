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

package com.liferay.portal.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.model.ServiceComponentModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the ServiceComponent service. Represents a row in the &quot;ServiceComponent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>ServiceComponentModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ServiceComponentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponentImpl
 * @generated
 */
public class ServiceComponentModelImpl
	extends BaseModelImpl<ServiceComponent> implements ServiceComponentModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a service component model instance should use the <code>ServiceComponent</code> interface instead.
	 */
	public static final String TABLE_NAME = "ServiceComponent";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"serviceComponentId", Types.BIGINT},
		{"buildNamespace", Types.VARCHAR}, {"buildNumber", Types.BIGINT},
		{"buildDate", Types.BIGINT}, {"data_", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("serviceComponentId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("buildNamespace", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("buildNumber", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("buildDate", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("data_", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table ServiceComponent (mvccVersion LONG default 0 not null,serviceComponentId LONG not null primary key,buildNamespace VARCHAR(75) null,buildNumber LONG,buildDate LONG,data_ TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table ServiceComponent";

	public static final String ORDER_BY_JPQL =
		" ORDER BY serviceComponent.buildNamespace DESC, serviceComponent.buildNumber DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY ServiceComponent.buildNamespace DESC, ServiceComponent.buildNumber DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean ENTITY_CACHE_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean FINDER_CACHE_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean COLUMN_BITMASK_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long BUILDNAMESPACE_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long BUILDNUMBER_COLUMN_BITMASK = 2L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.ServiceComponent"));

	public ServiceComponentModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _serviceComponentId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setServiceComponentId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _serviceComponentId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return ServiceComponent.class;
	}

	@Override
	public String getModelClassName() {
		return ServiceComponent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<ServiceComponent, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<ServiceComponent, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ServiceComponent, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((ServiceComponent)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<ServiceComponent, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<ServiceComponent, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(ServiceComponent)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<ServiceComponent, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<ServiceComponent, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, ServiceComponent>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			ServiceComponent.class.getClassLoader(), ServiceComponent.class,
			ModelWrapper.class);

		try {
			Constructor<ServiceComponent> constructor =
				(Constructor<ServiceComponent>)proxyClass.getConstructor(
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

	private static final Map<String, Function<ServiceComponent, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<ServiceComponent, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<ServiceComponent, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<ServiceComponent, Object>>();
		Map<String, BiConsumer<ServiceComponent, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<ServiceComponent, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", ServiceComponent::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<ServiceComponent, Long>)
				ServiceComponent::setMvccVersion);
		attributeGetterFunctions.put(
			"serviceComponentId", ServiceComponent::getServiceComponentId);
		attributeSetterBiConsumers.put(
			"serviceComponentId",
			(BiConsumer<ServiceComponent, Long>)
				ServiceComponent::setServiceComponentId);
		attributeGetterFunctions.put(
			"buildNamespace", ServiceComponent::getBuildNamespace);
		attributeSetterBiConsumers.put(
			"buildNamespace",
			(BiConsumer<ServiceComponent, String>)
				ServiceComponent::setBuildNamespace);
		attributeGetterFunctions.put(
			"buildNumber", ServiceComponent::getBuildNumber);
		attributeSetterBiConsumers.put(
			"buildNumber",
			(BiConsumer<ServiceComponent, Long>)
				ServiceComponent::setBuildNumber);
		attributeGetterFunctions.put(
			"buildDate", ServiceComponent::getBuildDate);
		attributeSetterBiConsumers.put(
			"buildDate",
			(BiConsumer<ServiceComponent, Long>)ServiceComponent::setBuildDate);
		attributeGetterFunctions.put("data", ServiceComponent::getData);
		attributeSetterBiConsumers.put(
			"data",
			(BiConsumer<ServiceComponent, String>)ServiceComponent::setData);

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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@Override
	public long getServiceComponentId() {
		return _serviceComponentId;
	}

	@Override
	public void setServiceComponentId(long serviceComponentId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_serviceComponentId = serviceComponentId;
	}

	@Override
	public String getBuildNamespace() {
		if (_buildNamespace == null) {
			return "";
		}
		else {
			return _buildNamespace;
		}
	}

	@Override
	public void setBuildNamespace(String buildNamespace) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_buildNamespace = buildNamespace;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalBuildNamespace() {
		return getColumnOriginalValue("buildNamespace");
	}

	@Override
	public long getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public void setBuildNumber(long buildNumber) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_buildNumber = buildNumber;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalBuildNumber() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("buildNumber"));
	}

	@Override
	public long getBuildDate() {
		return _buildDate;
	}

	@Override
	public void setBuildDate(long buildDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_buildDate = buildDate;
	}

	@Override
	public String getData() {
		if (_data == null) {
			return "";
		}
		else {
			return _data;
		}
	}

	@Override
	public void setData(String data) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_data = data;
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
			0, ServiceComponent.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public ServiceComponent toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, ServiceComponent>
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
		ServiceComponentImpl serviceComponentImpl = new ServiceComponentImpl();

		serviceComponentImpl.setMvccVersion(getMvccVersion());
		serviceComponentImpl.setServiceComponentId(getServiceComponentId());
		serviceComponentImpl.setBuildNamespace(getBuildNamespace());
		serviceComponentImpl.setBuildNumber(getBuildNumber());
		serviceComponentImpl.setBuildDate(getBuildDate());
		serviceComponentImpl.setData(getData());

		serviceComponentImpl.resetOriginalValues();

		return serviceComponentImpl;
	}

	@Override
	public int compareTo(ServiceComponent serviceComponent) {
		int value = 0;

		value = getBuildNamespace().compareTo(
			serviceComponent.getBuildNamespace());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		if (getBuildNumber() < serviceComponent.getBuildNumber()) {
			value = -1;
		}
		else if (getBuildNumber() > serviceComponent.getBuildNumber()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

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

		if (!(object instanceof ServiceComponent)) {
			return false;
		}

		ServiceComponent serviceComponent = (ServiceComponent)object;

		long primaryKey = serviceComponent.getPrimaryKey();

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

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<ServiceComponent> toCacheModel() {
		ServiceComponentCacheModel serviceComponentCacheModel =
			new ServiceComponentCacheModel();

		serviceComponentCacheModel.mvccVersion = getMvccVersion();

		serviceComponentCacheModel.serviceComponentId = getServiceComponentId();

		serviceComponentCacheModel.buildNamespace = getBuildNamespace();

		String buildNamespace = serviceComponentCacheModel.buildNamespace;

		if ((buildNamespace != null) && (buildNamespace.length() == 0)) {
			serviceComponentCacheModel.buildNamespace = null;
		}

		serviceComponentCacheModel.buildNumber = getBuildNumber();

		serviceComponentCacheModel.buildDate = getBuildDate();

		serviceComponentCacheModel.data = getData();

		String data = serviceComponentCacheModel.data;

		if ((data != null) && (data.length() == 0)) {
			serviceComponentCacheModel.data = null;
		}

		return serviceComponentCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<ServiceComponent, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<ServiceComponent, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ServiceComponent, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((ServiceComponent)this));
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
		Map<String, Function<ServiceComponent, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<ServiceComponent, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ServiceComponent, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((ServiceComponent)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, ServiceComponent>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _serviceComponentId;
	private String _buildNamespace;
	private long _buildNumber;
	private long _buildDate;
	private String _data;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<ServiceComponent, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((ServiceComponent)this);
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
		_columnOriginalValues.put("serviceComponentId", _serviceComponentId);
		_columnOriginalValues.put("buildNamespace", _buildNamespace);
		_columnOriginalValues.put("buildNumber", _buildNumber);
		_columnOriginalValues.put("buildDate", _buildDate);
		_columnOriginalValues.put("data_", _data);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("data_", "data");

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

		columnBitmasks.put("serviceComponentId", 2L);

		columnBitmasks.put("buildNamespace", 4L);

		columnBitmasks.put("buildNumber", 8L);

		columnBitmasks.put("buildDate", 16L);

		columnBitmasks.put("data_", 32L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private ServiceComponent _escapedModel;

}