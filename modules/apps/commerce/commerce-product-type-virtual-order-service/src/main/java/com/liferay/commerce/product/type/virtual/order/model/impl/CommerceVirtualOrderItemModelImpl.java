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

package com.liferay.commerce.product.type.virtual.order.model.impl;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemModel;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemSoap;
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
import com.liferay.portal.kernel.util.DateUtil;
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
 * The base model implementation for the CommerceVirtualOrderItem service. Represents a row in the &quot;CommerceVirtualOrderItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CommerceVirtualOrderItemModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceVirtualOrderItemImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemImpl
 * @generated
 */
@JSON(strict = true)
public class CommerceVirtualOrderItemModelImpl
	extends BaseModelImpl<CommerceVirtualOrderItem>
	implements CommerceVirtualOrderItemModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce virtual order item model instance should use the <code>CommerceVirtualOrderItem</code> interface instead.
	 */
	public static final String TABLE_NAME = "CommerceVirtualOrderItem";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"commerceVirtualOrderItemId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"commerceOrderItemId", Types.BIGINT}, {"fileEntryId", Types.BIGINT},
		{"url", Types.VARCHAR}, {"activationStatus", Types.INTEGER},
		{"duration", Types.BIGINT}, {"usages", Types.INTEGER},
		{"maxUsages", Types.INTEGER}, {"active_", Types.BOOLEAN},
		{"startDate", Types.TIMESTAMP}, {"endDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("commerceVirtualOrderItemId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceOrderItemId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("url", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("activationStatus", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("duration", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("usages", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("maxUsages", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("endDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table CommerceVirtualOrderItem (uuid_ VARCHAR(75) null,commerceVirtualOrderItemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceOrderItemId LONG,fileEntryId LONG,url VARCHAR(75) null,activationStatus INTEGER,duration LONG,usages INTEGER,maxUsages INTEGER,active_ BOOLEAN,startDate DATE null,endDate DATE null)";

	public static final String TABLE_SQL_DROP =
		"drop table CommerceVirtualOrderItem";

	public static final String ORDER_BY_JPQL =
		" ORDER BY commerceVirtualOrderItem.createDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY CommerceVirtualOrderItem.createDate DESC";

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
	public static final long COMMERCEORDERITEMID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CREATEDATE_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static CommerceVirtualOrderItem toModel(
		CommerceVirtualOrderItemSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		CommerceVirtualOrderItem model = new CommerceVirtualOrderItemImpl();

		model.setUuid(soapModel.getUuid());
		model.setCommerceVirtualOrderItemId(
			soapModel.getCommerceVirtualOrderItemId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceOrderItemId(soapModel.getCommerceOrderItemId());
		model.setFileEntryId(soapModel.getFileEntryId());
		model.setUrl(soapModel.getUrl());
		model.setActivationStatus(soapModel.getActivationStatus());
		model.setDuration(soapModel.getDuration());
		model.setUsages(soapModel.getUsages());
		model.setMaxUsages(soapModel.getMaxUsages());
		model.setActive(soapModel.isActive());
		model.setStartDate(soapModel.getStartDate());
		model.setEndDate(soapModel.getEndDate());

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
	public static List<CommerceVirtualOrderItem> toModels(
		CommerceVirtualOrderItemSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<CommerceVirtualOrderItem> models =
			new ArrayList<CommerceVirtualOrderItem>(soapModels.length);

		for (CommerceVirtualOrderItemSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.commerce.product.type.virtual.order.service.util.
			ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem"));

	public CommerceVirtualOrderItemModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceVirtualOrderItemId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceVirtualOrderItemId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceVirtualOrderItemId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceVirtualOrderItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceVirtualOrderItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<CommerceVirtualOrderItem, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<CommerceVirtualOrderItem, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceVirtualOrderItem, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((CommerceVirtualOrderItem)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<CommerceVirtualOrderItem, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<CommerceVirtualOrderItem, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(CommerceVirtualOrderItem)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<CommerceVirtualOrderItem, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<CommerceVirtualOrderItem, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, CommerceVirtualOrderItem>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			CommerceVirtualOrderItem.class.getClassLoader(),
			CommerceVirtualOrderItem.class, ModelWrapper.class);

		try {
			Constructor<CommerceVirtualOrderItem> constructor =
				(Constructor<CommerceVirtualOrderItem>)
					proxyClass.getConstructor(InvocationHandler.class);

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

	private static final Map<String, Function<CommerceVirtualOrderItem, Object>>
		_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<CommerceVirtualOrderItem, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<CommerceVirtualOrderItem, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<CommerceVirtualOrderItem, Object>>();
		Map<String, BiConsumer<CommerceVirtualOrderItem, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<CommerceVirtualOrderItem, ?>>();

		attributeGetterFunctions.put("uuid", CommerceVirtualOrderItem::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<CommerceVirtualOrderItem, String>)
				CommerceVirtualOrderItem::setUuid);
		attributeGetterFunctions.put(
			"commerceVirtualOrderItemId",
			CommerceVirtualOrderItem::getCommerceVirtualOrderItemId);
		attributeSetterBiConsumers.put(
			"commerceVirtualOrderItemId",
			(BiConsumer<CommerceVirtualOrderItem, Long>)
				CommerceVirtualOrderItem::setCommerceVirtualOrderItemId);
		attributeGetterFunctions.put(
			"groupId", CommerceVirtualOrderItem::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<CommerceVirtualOrderItem, Long>)
				CommerceVirtualOrderItem::setGroupId);
		attributeGetterFunctions.put(
			"companyId", CommerceVirtualOrderItem::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<CommerceVirtualOrderItem, Long>)
				CommerceVirtualOrderItem::setCompanyId);
		attributeGetterFunctions.put(
			"userId", CommerceVirtualOrderItem::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<CommerceVirtualOrderItem, Long>)
				CommerceVirtualOrderItem::setUserId);
		attributeGetterFunctions.put(
			"userName", CommerceVirtualOrderItem::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<CommerceVirtualOrderItem, String>)
				CommerceVirtualOrderItem::setUserName);
		attributeGetterFunctions.put(
			"createDate", CommerceVirtualOrderItem::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<CommerceVirtualOrderItem, Date>)
				CommerceVirtualOrderItem::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", CommerceVirtualOrderItem::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<CommerceVirtualOrderItem, Date>)
				CommerceVirtualOrderItem::setModifiedDate);
		attributeGetterFunctions.put(
			"commerceOrderItemId",
			CommerceVirtualOrderItem::getCommerceOrderItemId);
		attributeSetterBiConsumers.put(
			"commerceOrderItemId",
			(BiConsumer<CommerceVirtualOrderItem, Long>)
				CommerceVirtualOrderItem::setCommerceOrderItemId);
		attributeGetterFunctions.put(
			"fileEntryId", CommerceVirtualOrderItem::getFileEntryId);
		attributeSetterBiConsumers.put(
			"fileEntryId",
			(BiConsumer<CommerceVirtualOrderItem, Long>)
				CommerceVirtualOrderItem::setFileEntryId);
		attributeGetterFunctions.put("url", CommerceVirtualOrderItem::getUrl);
		attributeSetterBiConsumers.put(
			"url",
			(BiConsumer<CommerceVirtualOrderItem, String>)
				CommerceVirtualOrderItem::setUrl);
		attributeGetterFunctions.put(
			"activationStatus", CommerceVirtualOrderItem::getActivationStatus);
		attributeSetterBiConsumers.put(
			"activationStatus",
			(BiConsumer<CommerceVirtualOrderItem, Integer>)
				CommerceVirtualOrderItem::setActivationStatus);
		attributeGetterFunctions.put(
			"duration", CommerceVirtualOrderItem::getDuration);
		attributeSetterBiConsumers.put(
			"duration",
			(BiConsumer<CommerceVirtualOrderItem, Long>)
				CommerceVirtualOrderItem::setDuration);
		attributeGetterFunctions.put(
			"usages", CommerceVirtualOrderItem::getUsages);
		attributeSetterBiConsumers.put(
			"usages",
			(BiConsumer<CommerceVirtualOrderItem, Integer>)
				CommerceVirtualOrderItem::setUsages);
		attributeGetterFunctions.put(
			"maxUsages", CommerceVirtualOrderItem::getMaxUsages);
		attributeSetterBiConsumers.put(
			"maxUsages",
			(BiConsumer<CommerceVirtualOrderItem, Integer>)
				CommerceVirtualOrderItem::setMaxUsages);
		attributeGetterFunctions.put(
			"active", CommerceVirtualOrderItem::getActive);
		attributeSetterBiConsumers.put(
			"active",
			(BiConsumer<CommerceVirtualOrderItem, Boolean>)
				CommerceVirtualOrderItem::setActive);
		attributeGetterFunctions.put(
			"startDate", CommerceVirtualOrderItem::getStartDate);
		attributeSetterBiConsumers.put(
			"startDate",
			(BiConsumer<CommerceVirtualOrderItem, Date>)
				CommerceVirtualOrderItem::setStartDate);
		attributeGetterFunctions.put(
			"endDate", CommerceVirtualOrderItem::getEndDate);
		attributeSetterBiConsumers.put(
			"endDate",
			(BiConsumer<CommerceVirtualOrderItem, Date>)
				CommerceVirtualOrderItem::setEndDate);

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
	public long getCommerceVirtualOrderItemId() {
		return _commerceVirtualOrderItemId;
	}

	@Override
	public void setCommerceVirtualOrderItemId(long commerceVirtualOrderItemId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceVirtualOrderItemId = commerceVirtualOrderItemId;
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

	@JSON
	@Override
	public long getCommerceOrderItemId() {
		return _commerceOrderItemId;
	}

	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceOrderItemId = commerceOrderItemId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCommerceOrderItemId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("commerceOrderItemId"));
	}

	@JSON
	@Override
	public long getFileEntryId() {
		return _fileEntryId;
	}

	@Override
	public void setFileEntryId(long fileEntryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_fileEntryId = fileEntryId;
	}

	@JSON
	@Override
	public String getUrl() {
		if (_url == null) {
			return "";
		}
		else {
			return _url;
		}
	}

	@Override
	public void setUrl(String url) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_url = url;
	}

	@JSON
	@Override
	public int getActivationStatus() {
		return _activationStatus;
	}

	@Override
	public void setActivationStatus(int activationStatus) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_activationStatus = activationStatus;
	}

	@JSON
	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public void setDuration(long duration) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_duration = duration;
	}

	@JSON
	@Override
	public int getUsages() {
		return _usages;
	}

	@Override
	public void setUsages(int usages) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_usages = usages;
	}

	@JSON
	@Override
	public int getMaxUsages() {
		return _maxUsages;
	}

	@Override
	public void setMaxUsages(int maxUsages) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_maxUsages = maxUsages;
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

	@JSON
	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_startDate = startDate;
	}

	@JSON
	@Override
	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_endDate = endDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(
				CommerceVirtualOrderItem.class.getName()));
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
			getCompanyId(), CommerceVirtualOrderItem.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceVirtualOrderItem toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, CommerceVirtualOrderItem>
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
		CommerceVirtualOrderItemImpl commerceVirtualOrderItemImpl =
			new CommerceVirtualOrderItemImpl();

		commerceVirtualOrderItemImpl.setUuid(getUuid());
		commerceVirtualOrderItemImpl.setCommerceVirtualOrderItemId(
			getCommerceVirtualOrderItemId());
		commerceVirtualOrderItemImpl.setGroupId(getGroupId());
		commerceVirtualOrderItemImpl.setCompanyId(getCompanyId());
		commerceVirtualOrderItemImpl.setUserId(getUserId());
		commerceVirtualOrderItemImpl.setUserName(getUserName());
		commerceVirtualOrderItemImpl.setCreateDate(getCreateDate());
		commerceVirtualOrderItemImpl.setModifiedDate(getModifiedDate());
		commerceVirtualOrderItemImpl.setCommerceOrderItemId(
			getCommerceOrderItemId());
		commerceVirtualOrderItemImpl.setFileEntryId(getFileEntryId());
		commerceVirtualOrderItemImpl.setUrl(getUrl());
		commerceVirtualOrderItemImpl.setActivationStatus(getActivationStatus());
		commerceVirtualOrderItemImpl.setDuration(getDuration());
		commerceVirtualOrderItemImpl.setUsages(getUsages());
		commerceVirtualOrderItemImpl.setMaxUsages(getMaxUsages());
		commerceVirtualOrderItemImpl.setActive(isActive());
		commerceVirtualOrderItemImpl.setStartDate(getStartDate());
		commerceVirtualOrderItemImpl.setEndDate(getEndDate());

		commerceVirtualOrderItemImpl.resetOriginalValues();

		return commerceVirtualOrderItemImpl;
	}

	@Override
	public int compareTo(CommerceVirtualOrderItem commerceVirtualOrderItem) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), commerceVirtualOrderItem.getCreateDate());

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

		if (!(object instanceof CommerceVirtualOrderItem)) {
			return false;
		}

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			(CommerceVirtualOrderItem)object;

		long primaryKey = commerceVirtualOrderItem.getPrimaryKey();

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

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceVirtualOrderItem> toCacheModel() {
		CommerceVirtualOrderItemCacheModel commerceVirtualOrderItemCacheModel =
			new CommerceVirtualOrderItemCacheModel();

		commerceVirtualOrderItemCacheModel.uuid = getUuid();

		String uuid = commerceVirtualOrderItemCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			commerceVirtualOrderItemCacheModel.uuid = null;
		}

		commerceVirtualOrderItemCacheModel.commerceVirtualOrderItemId =
			getCommerceVirtualOrderItemId();

		commerceVirtualOrderItemCacheModel.groupId = getGroupId();

		commerceVirtualOrderItemCacheModel.companyId = getCompanyId();

		commerceVirtualOrderItemCacheModel.userId = getUserId();

		commerceVirtualOrderItemCacheModel.userName = getUserName();

		String userName = commerceVirtualOrderItemCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceVirtualOrderItemCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceVirtualOrderItemCacheModel.createDate =
				createDate.getTime();
		}
		else {
			commerceVirtualOrderItemCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceVirtualOrderItemCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			commerceVirtualOrderItemCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceVirtualOrderItemCacheModel.commerceOrderItemId =
			getCommerceOrderItemId();

		commerceVirtualOrderItemCacheModel.fileEntryId = getFileEntryId();

		commerceVirtualOrderItemCacheModel.url = getUrl();

		String url = commerceVirtualOrderItemCacheModel.url;

		if ((url != null) && (url.length() == 0)) {
			commerceVirtualOrderItemCacheModel.url = null;
		}

		commerceVirtualOrderItemCacheModel.activationStatus =
			getActivationStatus();

		commerceVirtualOrderItemCacheModel.duration = getDuration();

		commerceVirtualOrderItemCacheModel.usages = getUsages();

		commerceVirtualOrderItemCacheModel.maxUsages = getMaxUsages();

		commerceVirtualOrderItemCacheModel.active = isActive();

		Date startDate = getStartDate();

		if (startDate != null) {
			commerceVirtualOrderItemCacheModel.startDate = startDate.getTime();
		}
		else {
			commerceVirtualOrderItemCacheModel.startDate = Long.MIN_VALUE;
		}

		Date endDate = getEndDate();

		if (endDate != null) {
			commerceVirtualOrderItemCacheModel.endDate = endDate.getTime();
		}
		else {
			commerceVirtualOrderItemCacheModel.endDate = Long.MIN_VALUE;
		}

		return commerceVirtualOrderItemCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<CommerceVirtualOrderItem, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<CommerceVirtualOrderItem, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceVirtualOrderItem, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((CommerceVirtualOrderItem)this));
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
		Map<String, Function<CommerceVirtualOrderItem, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<CommerceVirtualOrderItem, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceVirtualOrderItem, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((CommerceVirtualOrderItem)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, CommerceVirtualOrderItem>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private String _uuid;
	private long _commerceVirtualOrderItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceOrderItemId;
	private long _fileEntryId;
	private String _url;
	private int _activationStatus;
	private long _duration;
	private int _usages;
	private int _maxUsages;
	private boolean _active;
	private Date _startDate;
	private Date _endDate;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<CommerceVirtualOrderItem, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((CommerceVirtualOrderItem)this);
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
		_columnOriginalValues.put(
			"commerceVirtualOrderItemId", _commerceVirtualOrderItemId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("commerceOrderItemId", _commerceOrderItemId);
		_columnOriginalValues.put("fileEntryId", _fileEntryId);
		_columnOriginalValues.put("url", _url);
		_columnOriginalValues.put("activationStatus", _activationStatus);
		_columnOriginalValues.put("duration", _duration);
		_columnOriginalValues.put("usages", _usages);
		_columnOriginalValues.put("maxUsages", _maxUsages);
		_columnOriginalValues.put("active_", _active);
		_columnOriginalValues.put("startDate", _startDate);
		_columnOriginalValues.put("endDate", _endDate);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");
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

		columnBitmasks.put("uuid_", 1L);

		columnBitmasks.put("commerceVirtualOrderItemId", 2L);

		columnBitmasks.put("groupId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("userName", 32L);

		columnBitmasks.put("createDate", 64L);

		columnBitmasks.put("modifiedDate", 128L);

		columnBitmasks.put("commerceOrderItemId", 256L);

		columnBitmasks.put("fileEntryId", 512L);

		columnBitmasks.put("url", 1024L);

		columnBitmasks.put("activationStatus", 2048L);

		columnBitmasks.put("duration", 4096L);

		columnBitmasks.put("usages", 8192L);

		columnBitmasks.put("maxUsages", 16384L);

		columnBitmasks.put("active_", 32768L);

		columnBitmasks.put("startDate", 65536L);

		columnBitmasks.put("endDate", 131072L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private CommerceVirtualOrderItem _escapedModel;

}