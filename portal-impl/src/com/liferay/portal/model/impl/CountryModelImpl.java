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
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryModel;
import com.liferay.portal.kernel.model.CountrySoap;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

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
 * The base model implementation for the Country service. Represents a row in the &quot;Country&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CountryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CountryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CountryImpl
 * @generated
 */
@JSON(strict = true)
public class CountryModelImpl
	extends BaseModelImpl<Country> implements CountryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a country model instance should use the <code>Country</code> interface instead.
	 */
	public static final String TABLE_NAME = "Country";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"countryId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"a2", Types.VARCHAR}, {"a3", Types.VARCHAR},
		{"number_", Types.VARCHAR}, {"idd_", Types.VARCHAR},
		{"zipRequired", Types.BOOLEAN}, {"active_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("countryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("a2", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("a3", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("number_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("idd_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("zipRequired", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Country (mvccVersion LONG default 0 not null,countryId LONG not null primary key,name VARCHAR(75) null,a2 VARCHAR(75) null,a3 VARCHAR(75) null,number_ VARCHAR(75) null,idd_ VARCHAR(75) null,zipRequired BOOLEAN,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Country";

	public static final String ORDER_BY_JPQL = " ORDER BY country.name ASC";

	public static final String ORDER_BY_SQL = " ORDER BY Country.name ASC";

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
	public static final long A2_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long A3_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long ACTIVE_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long NAME_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static Country toModel(CountrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Country model = new CountryImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setCountryId(soapModel.getCountryId());
		model.setName(soapModel.getName());
		model.setA2(soapModel.getA2());
		model.setA3(soapModel.getA3());
		model.setNumber(soapModel.getNumber());
		model.setIdd(soapModel.getIdd());
		model.setZipRequired(soapModel.isZipRequired());
		model.setActive(soapModel.isActive());

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
	public static List<Country> toModels(CountrySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Country> models = new ArrayList<Country>(soapModels.length);

		for (CountrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.Country"));

	public CountryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _countryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCountryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _countryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Country.class;
	}

	@Override
	public String getModelClassName() {
		return Country.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Country, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Country, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Country, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Country)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Country, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Country, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Country)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Country, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Country, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Country>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Country.class.getClassLoader(), Country.class, ModelWrapper.class);

		try {
			Constructor<Country> constructor =
				(Constructor<Country>)proxyClass.getConstructor(
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

	private static final Map<String, Function<Country, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Country, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Country, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Country, Object>>();
		Map<String, BiConsumer<Country, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Country, ?>>();

		attributeGetterFunctions.put("mvccVersion", Country::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion", (BiConsumer<Country, Long>)Country::setMvccVersion);
		attributeGetterFunctions.put("countryId", Country::getCountryId);
		attributeSetterBiConsumers.put(
			"countryId", (BiConsumer<Country, Long>)Country::setCountryId);
		attributeGetterFunctions.put("name", Country::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<Country, String>)Country::setName);
		attributeGetterFunctions.put("a2", Country::getA2);
		attributeSetterBiConsumers.put(
			"a2", (BiConsumer<Country, String>)Country::setA2);
		attributeGetterFunctions.put("a3", Country::getA3);
		attributeSetterBiConsumers.put(
			"a3", (BiConsumer<Country, String>)Country::setA3);
		attributeGetterFunctions.put("number", Country::getNumber);
		attributeSetterBiConsumers.put(
			"number", (BiConsumer<Country, String>)Country::setNumber);
		attributeGetterFunctions.put("idd", Country::getIdd);
		attributeSetterBiConsumers.put(
			"idd", (BiConsumer<Country, String>)Country::setIdd);
		attributeGetterFunctions.put("zipRequired", Country::getZipRequired);
		attributeSetterBiConsumers.put(
			"zipRequired",
			(BiConsumer<Country, Boolean>)Country::setZipRequired);
		attributeGetterFunctions.put("active", Country::getActive);
		attributeSetterBiConsumers.put(
			"active", (BiConsumer<Country, Boolean>)Country::setActive);

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
	public long getCountryId() {
		return _countryId;
	}

	@Override
	public void setCountryId(long countryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_countryId = countryId;
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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_name = name;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalName() {
		return getColumnOriginalValue("name");
	}

	@JSON
	@Override
	public String getA2() {
		if (_a2 == null) {
			return "";
		}
		else {
			return _a2;
		}
	}

	@Override
	public void setA2(String a2) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_a2 = a2;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalA2() {
		return getColumnOriginalValue("a2");
	}

	@JSON
	@Override
	public String getA3() {
		if (_a3 == null) {
			return "";
		}
		else {
			return _a3;
		}
	}

	@Override
	public void setA3(String a3) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_a3 = a3;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalA3() {
		return getColumnOriginalValue("a3");
	}

	@JSON
	@Override
	public String getNumber() {
		if (_number == null) {
			return "";
		}
		else {
			return _number;
		}
	}

	@Override
	public void setNumber(String number) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_number = number;
	}

	@JSON
	@Override
	public String getIdd() {
		if (_idd == null) {
			return "";
		}
		else {
			return _idd;
		}
	}

	@Override
	public void setIdd(String idd) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_idd = idd;
	}

	@JSON
	@Override
	public boolean getZipRequired() {
		return _zipRequired;
	}

	@JSON
	@Override
	public boolean isZipRequired() {
		return _zipRequired;
	}

	@Override
	public void setZipRequired(boolean zipRequired) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_zipRequired = zipRequired;
	}

	@JSON
	@Override
	public boolean getActive() {
		return _active;
	}

	@JSON
	@Override
	public boolean isActive() {
		return _active;
	}

	@Override
	public void setActive(boolean active) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_active = active;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public boolean getOriginalActive() {
		return GetterUtil.getBoolean(
			this.<Boolean>getColumnOriginalValue("active_"));
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
			0, Country.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Country toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Country>
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
		CountryImpl countryImpl = new CountryImpl();

		countryImpl.setMvccVersion(getMvccVersion());
		countryImpl.setCountryId(getCountryId());
		countryImpl.setName(getName());
		countryImpl.setA2(getA2());
		countryImpl.setA3(getA3());
		countryImpl.setNumber(getNumber());
		countryImpl.setIdd(getIdd());
		countryImpl.setZipRequired(isZipRequired());
		countryImpl.setActive(isActive());

		countryImpl.resetOriginalValues();

		return countryImpl;
	}

	@Override
	public int compareTo(Country country) {
		int value = 0;

		value = getName().compareTo(country.getName());

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

		if (!(object instanceof Country)) {
			return false;
		}

		Country country = (Country)object;

		long primaryKey = country.getPrimaryKey();

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
	public CacheModel<Country> toCacheModel() {
		CountryCacheModel countryCacheModel = new CountryCacheModel();

		countryCacheModel.mvccVersion = getMvccVersion();

		countryCacheModel.countryId = getCountryId();

		countryCacheModel.name = getName();

		String name = countryCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			countryCacheModel.name = null;
		}

		countryCacheModel.a2 = getA2();

		String a2 = countryCacheModel.a2;

		if ((a2 != null) && (a2.length() == 0)) {
			countryCacheModel.a2 = null;
		}

		countryCacheModel.a3 = getA3();

		String a3 = countryCacheModel.a3;

		if ((a3 != null) && (a3.length() == 0)) {
			countryCacheModel.a3 = null;
		}

		countryCacheModel.number = getNumber();

		String number = countryCacheModel.number;

		if ((number != null) && (number.length() == 0)) {
			countryCacheModel.number = null;
		}

		countryCacheModel.idd = getIdd();

		String idd = countryCacheModel.idd;

		if ((idd != null) && (idd.length() == 0)) {
			countryCacheModel.idd = null;
		}

		countryCacheModel.zipRequired = isZipRequired();

		countryCacheModel.active = isActive();

		return countryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Country, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Country, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Country, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Country)this));
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
		Map<String, Function<Country, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Country, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Country, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Country)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Country>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _countryId;
	private String _name;
	private String _a2;
	private String _a3;
	private String _number;
	private String _idd;
	private boolean _zipRequired;
	private boolean _active;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<Country, Object> function = _attributeGetterFunctions.get(
			columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((Country)this);
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
		_columnOriginalValues.put("countryId", _countryId);
		_columnOriginalValues.put("name", _name);
		_columnOriginalValues.put("a2", _a2);
		_columnOriginalValues.put("a3", _a3);
		_columnOriginalValues.put("number_", _number);
		_columnOriginalValues.put("idd_", _idd);
		_columnOriginalValues.put("zipRequired", _zipRequired);
		_columnOriginalValues.put("active_", _active);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("number_", "number");
		attributeNames.put("idd_", "idd");
		attributeNames.put("active_", "active");

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

		columnBitmasks.put("countryId", 2L);

		columnBitmasks.put("name", 4L);

		columnBitmasks.put("a2", 8L);

		columnBitmasks.put("a3", 16L);

		columnBitmasks.put("number_", 32L);

		columnBitmasks.put("idd_", 64L);

		columnBitmasks.put("zipRequired", 128L);

		columnBitmasks.put("active_", 256L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private Country _escapedModel;

}