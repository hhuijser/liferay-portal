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
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressModel;
import com.liferay.portal.kernel.model.AddressSoap;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
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
 * The base model implementation for the Address service. Represents a row in the &quot;Address&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>AddressModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AddressImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AddressImpl
 * @generated
 */
@JSON(strict = true)
public class AddressModelImpl
	extends BaseModelImpl<Address> implements AddressModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a address model instance should use the <code>Address</code> interface instead.
	 */
	public static final String TABLE_NAME = "Address";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"addressId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"street1", Types.VARCHAR}, {"street2", Types.VARCHAR},
		{"street3", Types.VARCHAR}, {"city", Types.VARCHAR},
		{"zip", Types.VARCHAR}, {"regionId", Types.BIGINT},
		{"countryId", Types.BIGINT}, {"typeId", Types.BIGINT},
		{"mailing", Types.BOOLEAN}, {"primary_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("addressId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("street1", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("street2", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("street3", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("city", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("zip", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("regionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("countryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("typeId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("mailing", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("primary_", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Address (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,addressId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,street1 VARCHAR(75) null,street2 VARCHAR(75) null,street3 VARCHAR(75) null,city VARCHAR(75) null,zip VARCHAR(75) null,regionId LONG,countryId LONG,typeId LONG,mailing BOOLEAN,primary_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Address";

	public static final String ORDER_BY_JPQL =
		" ORDER BY address.createDate ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Address.createDate ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.Address"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.Address"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.Address"),
		true);

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	public static final long MAILING_COLUMN_BITMASK = 8L;

	public static final long PRIMARY_COLUMN_BITMASK = 16L;

	public static final long USERID_COLUMN_BITMASK = 32L;

	public static final long UUID_COLUMN_BITMASK = 64L;

	public static final long CREATEDATE_COLUMN_BITMASK = 128L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Address toModel(AddressSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Address model = new AddressImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setUuid(soapModel.getUuid());
		model.setAddressId(soapModel.getAddressId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setStreet1(soapModel.getStreet1());
		model.setStreet2(soapModel.getStreet2());
		model.setStreet3(soapModel.getStreet3());
		model.setCity(soapModel.getCity());
		model.setZip(soapModel.getZip());
		model.setRegionId(soapModel.getRegionId());
		model.setCountryId(soapModel.getCountryId());
		model.setTypeId(soapModel.getTypeId());
		model.setMailing(soapModel.isMailing());
		model.setPrimary(soapModel.isPrimary());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Address> toModels(AddressSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Address> models = new ArrayList<Address>(soapModels.length);

		for (AddressSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.Address"));

	public AddressModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _addressId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAddressId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _addressId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Address.class;
	}

	@Override
	public String getModelClassName() {
		return Address.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Address, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Address, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Address, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Address)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Address, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Address, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Address)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Address, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Address, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Address>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Address.class.getClassLoader(), Address.class, ModelWrapper.class);

		try {
			Constructor<Address> constructor =
				(Constructor<Address>)proxyClass.getConstructor(
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

	private static final Map<String, Function<Address, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Address, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Address, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Address, Object>>();
		Map<String, BiConsumer<Address, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Address, ?>>();

		attributeGetterFunctions.put("mvccVersion", Address::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion", (BiConsumer<Address, Long>)Address::setMvccVersion);
		attributeGetterFunctions.put("uuid", Address::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<Address, String>)Address::setUuid);
		attributeGetterFunctions.put("addressId", Address::getAddressId);
		attributeSetterBiConsumers.put(
			"addressId", (BiConsumer<Address, Long>)Address::setAddressId);
		attributeGetterFunctions.put("companyId", Address::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<Address, Long>)Address::setCompanyId);
		attributeGetterFunctions.put("userId", Address::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<Address, Long>)Address::setUserId);
		attributeGetterFunctions.put("userName", Address::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<Address, String>)Address::setUserName);
		attributeGetterFunctions.put("createDate", Address::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate", (BiConsumer<Address, Date>)Address::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", Address::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<Address, Date>)Address::setModifiedDate);
		attributeGetterFunctions.put("classNameId", Address::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId", (BiConsumer<Address, Long>)Address::setClassNameId);
		attributeGetterFunctions.put("classPK", Address::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK", (BiConsumer<Address, Long>)Address::setClassPK);
		attributeGetterFunctions.put("street1", Address::getStreet1);
		attributeSetterBiConsumers.put(
			"street1", (BiConsumer<Address, String>)Address::setStreet1);
		attributeGetterFunctions.put("street2", Address::getStreet2);
		attributeSetterBiConsumers.put(
			"street2", (BiConsumer<Address, String>)Address::setStreet2);
		attributeGetterFunctions.put("street3", Address::getStreet3);
		attributeSetterBiConsumers.put(
			"street3", (BiConsumer<Address, String>)Address::setStreet3);
		attributeGetterFunctions.put("city", Address::getCity);
		attributeSetterBiConsumers.put(
			"city", (BiConsumer<Address, String>)Address::setCity);
		attributeGetterFunctions.put("zip", Address::getZip);
		attributeSetterBiConsumers.put(
			"zip", (BiConsumer<Address, String>)Address::setZip);
		attributeGetterFunctions.put("regionId", Address::getRegionId);
		attributeSetterBiConsumers.put(
			"regionId", (BiConsumer<Address, Long>)Address::setRegionId);
		attributeGetterFunctions.put("countryId", Address::getCountryId);
		attributeSetterBiConsumers.put(
			"countryId", (BiConsumer<Address, Long>)Address::setCountryId);
		attributeGetterFunctions.put("typeId", Address::getTypeId);
		attributeSetterBiConsumers.put(
			"typeId", (BiConsumer<Address, Long>)Address::setTypeId);
		attributeGetterFunctions.put("mailing", Address::getMailing);
		attributeSetterBiConsumers.put(
			"mailing", (BiConsumer<Address, Boolean>)Address::setMailing);
		attributeGetterFunctions.put("primary", Address::getPrimary);
		attributeSetterBiConsumers.put(
			"primary", (BiConsumer<Address, Boolean>)Address::setPrimary);

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
		_mvccVersion = mvccVersion;
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
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getAddressId() {
		return _addressId;
	}

	@Override
	public void setAddressId(long addressId) {
		_addressId = addressId;
	}

	@JSON
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

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
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

	public long getOriginalUserId() {
		return _originalUserId;
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
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

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
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	@Override
	public String getStreet1() {
		if (_street1 == null) {
			return "";
		}
		else {
			return _street1;
		}
	}

	@Override
	public void setStreet1(String street1) {
		_street1 = street1;
	}

	@JSON
	@Override
	public String getStreet2() {
		if (_street2 == null) {
			return "";
		}
		else {
			return _street2;
		}
	}

	@Override
	public void setStreet2(String street2) {
		_street2 = street2;
	}

	@JSON
	@Override
	public String getStreet3() {
		if (_street3 == null) {
			return "";
		}
		else {
			return _street3;
		}
	}

	@Override
	public void setStreet3(String street3) {
		_street3 = street3;
	}

	@JSON
	@Override
	public String getCity() {
		if (_city == null) {
			return "";
		}
		else {
			return _city;
		}
	}

	@Override
	public void setCity(String city) {
		_city = city;
	}

	@JSON
	@Override
	public String getZip() {
		if (_zip == null) {
			return "";
		}
		else {
			return _zip;
		}
	}

	@Override
	public void setZip(String zip) {
		_zip = zip;
	}

	@JSON
	@Override
	public long getRegionId() {
		return _regionId;
	}

	@Override
	public void setRegionId(long regionId) {
		_regionId = regionId;
	}

	@JSON
	@Override
	public long getCountryId() {
		return _countryId;
	}

	@Override
	public void setCountryId(long countryId) {
		_countryId = countryId;
	}

	@JSON
	@Override
	public long getTypeId() {
		return _typeId;
	}

	@Override
	public void setTypeId(long typeId) {
		_typeId = typeId;
	}

	@JSON
	@Override
	public boolean getMailing() {
		return _mailing;
	}

	@JSON
	@Override
	public boolean isMailing() {
		return _mailing;
	}

	@Override
	public void setMailing(boolean mailing) {
		_columnBitmask |= MAILING_COLUMN_BITMASK;

		if (!_setOriginalMailing) {
			_setOriginalMailing = true;

			_originalMailing = _mailing;
		}

		_mailing = mailing;
	}

	public boolean getOriginalMailing() {
		return _originalMailing;
	}

	@JSON
	@Override
	public boolean getPrimary() {
		return _primary;
	}

	@JSON
	@Override
	public boolean isPrimary() {
		return _primary;
	}

	@Override
	public void setPrimary(boolean primary) {
		_columnBitmask |= PRIMARY_COLUMN_BITMASK;

		if (!_setOriginalPrimary) {
			_setOriginalPrimary = true;

			_originalPrimary = _primary;
		}

		_primary = primary;
	}

	public boolean getOriginalPrimary() {
		return _originalPrimary;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(Address.class.getName()),
			getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Address.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Address toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Address>
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
		AddressImpl addressImpl = new AddressImpl();

		addressImpl.setMvccVersion(getMvccVersion());
		addressImpl.setUuid(getUuid());
		addressImpl.setAddressId(getAddressId());
		addressImpl.setCompanyId(getCompanyId());
		addressImpl.setUserId(getUserId());
		addressImpl.setUserName(getUserName());
		addressImpl.setCreateDate(getCreateDate());
		addressImpl.setModifiedDate(getModifiedDate());
		addressImpl.setClassNameId(getClassNameId());
		addressImpl.setClassPK(getClassPK());
		addressImpl.setStreet1(getStreet1());
		addressImpl.setStreet2(getStreet2());
		addressImpl.setStreet3(getStreet3());
		addressImpl.setCity(getCity());
		addressImpl.setZip(getZip());
		addressImpl.setRegionId(getRegionId());
		addressImpl.setCountryId(getCountryId());
		addressImpl.setTypeId(getTypeId());
		addressImpl.setMailing(isMailing());
		addressImpl.setPrimary(isPrimary());

		addressImpl.resetOriginalValues();

		return addressImpl;
	}

	@Override
	public int compareTo(Address address) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(), address.getCreateDate());

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

		if (!(object instanceof Address)) {
			return false;
		}

		Address address = (Address)object;

		long primaryKey = address.getPrimaryKey();

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
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		AddressModelImpl addressModelImpl = this;

		addressModelImpl._originalUuid = addressModelImpl._uuid;

		addressModelImpl._originalCompanyId = addressModelImpl._companyId;

		addressModelImpl._setOriginalCompanyId = false;

		addressModelImpl._originalUserId = addressModelImpl._userId;

		addressModelImpl._setOriginalUserId = false;

		addressModelImpl._setModifiedDate = false;

		addressModelImpl._originalClassNameId = addressModelImpl._classNameId;

		addressModelImpl._setOriginalClassNameId = false;

		addressModelImpl._originalClassPK = addressModelImpl._classPK;

		addressModelImpl._setOriginalClassPK = false;

		addressModelImpl._originalMailing = addressModelImpl._mailing;

		addressModelImpl._setOriginalMailing = false;

		addressModelImpl._originalPrimary = addressModelImpl._primary;

		addressModelImpl._setOriginalPrimary = false;

		addressModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Address> toCacheModel() {
		AddressCacheModel addressCacheModel = new AddressCacheModel();

		addressCacheModel.mvccVersion = getMvccVersion();

		addressCacheModel.uuid = getUuid();

		String uuid = addressCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			addressCacheModel.uuid = null;
		}

		addressCacheModel.addressId = getAddressId();

		addressCacheModel.companyId = getCompanyId();

		addressCacheModel.userId = getUserId();

		addressCacheModel.userName = getUserName();

		String userName = addressCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			addressCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			addressCacheModel.createDate = createDate.getTime();
		}
		else {
			addressCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			addressCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			addressCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		addressCacheModel.classNameId = getClassNameId();

		addressCacheModel.classPK = getClassPK();

		addressCacheModel.street1 = getStreet1();

		String street1 = addressCacheModel.street1;

		if ((street1 != null) && (street1.length() == 0)) {
			addressCacheModel.street1 = null;
		}

		addressCacheModel.street2 = getStreet2();

		String street2 = addressCacheModel.street2;

		if ((street2 != null) && (street2.length() == 0)) {
			addressCacheModel.street2 = null;
		}

		addressCacheModel.street3 = getStreet3();

		String street3 = addressCacheModel.street3;

		if ((street3 != null) && (street3.length() == 0)) {
			addressCacheModel.street3 = null;
		}

		addressCacheModel.city = getCity();

		String city = addressCacheModel.city;

		if ((city != null) && (city.length() == 0)) {
			addressCacheModel.city = null;
		}

		addressCacheModel.zip = getZip();

		String zip = addressCacheModel.zip;

		if ((zip != null) && (zip.length() == 0)) {
			addressCacheModel.zip = null;
		}

		addressCacheModel.regionId = getRegionId();

		addressCacheModel.countryId = getCountryId();

		addressCacheModel.typeId = getTypeId();

		addressCacheModel.mailing = isMailing();

		addressCacheModel.primary = isPrimary();

		return addressCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Address, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Address, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Address, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Address)this));
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
		Map<String, Function<Address, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Address, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Address, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Address)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Address>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private String _uuid;
	private String _originalUuid;
	private long _addressId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _street1;
	private String _street2;
	private String _street3;
	private String _city;
	private String _zip;
	private long _regionId;
	private long _countryId;
	private long _typeId;
	private boolean _mailing;
	private boolean _originalMailing;
	private boolean _setOriginalMailing;
	private boolean _primary;
	private boolean _originalPrimary;
	private boolean _setOriginalPrimary;
	private long _columnBitmask;
	private Address _escapedModel;

}