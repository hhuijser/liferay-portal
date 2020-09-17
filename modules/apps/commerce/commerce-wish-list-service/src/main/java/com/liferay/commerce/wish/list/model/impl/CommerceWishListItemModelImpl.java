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

package com.liferay.commerce.wish.list.model.impl;

import com.liferay.commerce.wish.list.model.CommerceWishListItem;
import com.liferay.commerce.wish.list.model.CommerceWishListItemModel;
import com.liferay.commerce.wish.list.model.CommerceWishListItemSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
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
 * The base model implementation for the CommerceWishListItem service. Represents a row in the &quot;CommerceWishListItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CommerceWishListItemModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceWishListItemImpl}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemImpl
 * @generated
 */
@JSON(strict = true)
public class CommerceWishListItemModelImpl
	extends BaseModelImpl<CommerceWishListItem>
	implements CommerceWishListItemModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce wish list item model instance should use the <code>CommerceWishListItem</code> interface instead.
	 */
	public static final String TABLE_NAME = "CommerceWishListItem";

	public static final Object[][] TABLE_COLUMNS = {
		{"commerceWishListItemId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"commerceWishListId", Types.BIGINT},
		{"CPInstanceUuid", Types.VARCHAR}, {"CProductId", Types.BIGINT},
		{"json", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("commerceWishListItemId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceWishListId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("CPInstanceUuid", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("CProductId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("json", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table CommerceWishListItem (commerceWishListItemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceWishListId LONG,CPInstanceUuid VARCHAR(75) null,CProductId LONG,json TEXT null)";

	public static final String TABLE_SQL_DROP =
		"drop table CommerceWishListItem";

	public static final String ORDER_BY_JPQL =
		" ORDER BY commerceWishListItem.createDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY CommerceWishListItem.createDate DESC";

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
	public static final long CPINSTANCEUUID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CPRODUCTID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMMERCEWISHLISTID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CREATEDATE_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static CommerceWishListItem toModel(
		CommerceWishListItemSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		CommerceWishListItem model = new CommerceWishListItemImpl();

		model.setCommerceWishListItemId(soapModel.getCommerceWishListItemId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceWishListId(soapModel.getCommerceWishListId());
		model.setCPInstanceUuid(soapModel.getCPInstanceUuid());
		model.setCProductId(soapModel.getCProductId());
		model.setJson(soapModel.getJson());

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
	public static List<CommerceWishListItem> toModels(
		CommerceWishListItemSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<CommerceWishListItem> models = new ArrayList<CommerceWishListItem>(
			soapModels.length);

		for (CommerceWishListItemSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.commerce.wish.list.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.commerce.wish.list.model.CommerceWishListItem"));

	public CommerceWishListItemModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceWishListItemId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceWishListItemId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceWishListItemId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceWishListItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceWishListItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<CommerceWishListItem, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<CommerceWishListItem, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceWishListItem, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((CommerceWishListItem)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<CommerceWishListItem, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<CommerceWishListItem, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(CommerceWishListItem)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<CommerceWishListItem, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<CommerceWishListItem, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, CommerceWishListItem>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			CommerceWishListItem.class.getClassLoader(),
			CommerceWishListItem.class, ModelWrapper.class);

		try {
			Constructor<CommerceWishListItem> constructor =
				(Constructor<CommerceWishListItem>)proxyClass.getConstructor(
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

	private static final Map<String, Function<CommerceWishListItem, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<CommerceWishListItem, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<CommerceWishListItem, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<CommerceWishListItem, Object>>();
		Map<String, BiConsumer<CommerceWishListItem, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<CommerceWishListItem, ?>>();

		attributeGetterFunctions.put(
			"commerceWishListItemId",
			CommerceWishListItem::getCommerceWishListItemId);
		attributeSetterBiConsumers.put(
			"commerceWishListItemId",
			(BiConsumer<CommerceWishListItem, Long>)
				CommerceWishListItem::setCommerceWishListItemId);
		attributeGetterFunctions.put(
			"groupId", CommerceWishListItem::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<CommerceWishListItem, Long>)
				CommerceWishListItem::setGroupId);
		attributeGetterFunctions.put(
			"companyId", CommerceWishListItem::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<CommerceWishListItem, Long>)
				CommerceWishListItem::setCompanyId);
		attributeGetterFunctions.put("userId", CommerceWishListItem::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<CommerceWishListItem, Long>)
				CommerceWishListItem::setUserId);
		attributeGetterFunctions.put(
			"userName", CommerceWishListItem::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<CommerceWishListItem, String>)
				CommerceWishListItem::setUserName);
		attributeGetterFunctions.put(
			"createDate", CommerceWishListItem::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<CommerceWishListItem, Date>)
				CommerceWishListItem::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", CommerceWishListItem::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<CommerceWishListItem, Date>)
				CommerceWishListItem::setModifiedDate);
		attributeGetterFunctions.put(
			"commerceWishListId", CommerceWishListItem::getCommerceWishListId);
		attributeSetterBiConsumers.put(
			"commerceWishListId",
			(BiConsumer<CommerceWishListItem, Long>)
				CommerceWishListItem::setCommerceWishListId);
		attributeGetterFunctions.put(
			"CPInstanceUuid", CommerceWishListItem::getCPInstanceUuid);
		attributeSetterBiConsumers.put(
			"CPInstanceUuid",
			(BiConsumer<CommerceWishListItem, String>)
				CommerceWishListItem::setCPInstanceUuid);
		attributeGetterFunctions.put(
			"CProductId", CommerceWishListItem::getCProductId);
		attributeSetterBiConsumers.put(
			"CProductId",
			(BiConsumer<CommerceWishListItem, Long>)
				CommerceWishListItem::setCProductId);
		attributeGetterFunctions.put("json", CommerceWishListItem::getJson);
		attributeSetterBiConsumers.put(
			"json",
			(BiConsumer<CommerceWishListItem, String>)
				CommerceWishListItem::setJson);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getCommerceWishListItemId() {
		return _commerceWishListItemId;
	}

	@Override
	public void setCommerceWishListItemId(long commerceWishListItemId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceWishListItemId = commerceWishListItemId;
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
	public long getCommerceWishListId() {
		return _commerceWishListId;
	}

	@Override
	public void setCommerceWishListId(long commerceWishListId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceWishListId = commerceWishListId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCommerceWishListId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("commerceWishListId"));
	}

	@JSON
	@Override
	public String getCPInstanceUuid() {
		if (_CPInstanceUuid == null) {
			return "";
		}
		else {
			return _CPInstanceUuid;
		}
	}

	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CPInstanceUuid = CPInstanceUuid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalCPInstanceUuid() {
		return getColumnOriginalValue("CPInstanceUuid");
	}

	@JSON
	@Override
	public long getCProductId() {
		return _CProductId;
	}

	@Override
	public void setCProductId(long CProductId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CProductId = CProductId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCProductId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("CProductId"));
	}

	@JSON
	@Override
	public String getJson() {
		if (_json == null) {
			return "";
		}
		else {
			return _json;
		}
	}

	@Override
	public void setJson(String json) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_json = json;
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
			getCompanyId(), CommerceWishListItem.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceWishListItem toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, CommerceWishListItem>
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
		CommerceWishListItemImpl commerceWishListItemImpl =
			new CommerceWishListItemImpl();

		commerceWishListItemImpl.setCommerceWishListItemId(
			getCommerceWishListItemId());
		commerceWishListItemImpl.setGroupId(getGroupId());
		commerceWishListItemImpl.setCompanyId(getCompanyId());
		commerceWishListItemImpl.setUserId(getUserId());
		commerceWishListItemImpl.setUserName(getUserName());
		commerceWishListItemImpl.setCreateDate(getCreateDate());
		commerceWishListItemImpl.setModifiedDate(getModifiedDate());
		commerceWishListItemImpl.setCommerceWishListId(getCommerceWishListId());
		commerceWishListItemImpl.setCPInstanceUuid(getCPInstanceUuid());
		commerceWishListItemImpl.setCProductId(getCProductId());
		commerceWishListItemImpl.setJson(getJson());

		commerceWishListItemImpl.resetOriginalValues();

		return commerceWishListItemImpl;
	}

	@Override
	public int compareTo(CommerceWishListItem commerceWishListItem) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), commerceWishListItem.getCreateDate());

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

		if (!(object instanceof CommerceWishListItem)) {
			return false;
		}

		CommerceWishListItem commerceWishListItem =
			(CommerceWishListItem)object;

		long primaryKey = commerceWishListItem.getPrimaryKey();

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
	public CacheModel<CommerceWishListItem> toCacheModel() {
		CommerceWishListItemCacheModel commerceWishListItemCacheModel =
			new CommerceWishListItemCacheModel();

		commerceWishListItemCacheModel.commerceWishListItemId =
			getCommerceWishListItemId();

		commerceWishListItemCacheModel.groupId = getGroupId();

		commerceWishListItemCacheModel.companyId = getCompanyId();

		commerceWishListItemCacheModel.userId = getUserId();

		commerceWishListItemCacheModel.userName = getUserName();

		String userName = commerceWishListItemCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceWishListItemCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceWishListItemCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceWishListItemCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceWishListItemCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			commerceWishListItemCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceWishListItemCacheModel.commerceWishListId =
			getCommerceWishListId();

		commerceWishListItemCacheModel.CPInstanceUuid = getCPInstanceUuid();

		String CPInstanceUuid = commerceWishListItemCacheModel.CPInstanceUuid;

		if ((CPInstanceUuid != null) && (CPInstanceUuid.length() == 0)) {
			commerceWishListItemCacheModel.CPInstanceUuid = null;
		}

		commerceWishListItemCacheModel.CProductId = getCProductId();

		commerceWishListItemCacheModel.json = getJson();

		String json = commerceWishListItemCacheModel.json;

		if ((json != null) && (json.length() == 0)) {
			commerceWishListItemCacheModel.json = null;
		}

		return commerceWishListItemCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<CommerceWishListItem, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<CommerceWishListItem, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceWishListItem, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((CommerceWishListItem)this));
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
		Map<String, Function<CommerceWishListItem, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<CommerceWishListItem, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceWishListItem, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((CommerceWishListItem)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, CommerceWishListItem>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _commerceWishListItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceWishListId;
	private String _CPInstanceUuid;
	private long _CProductId;
	private String _json;

	public <T> T getColumnValue(String columnName) {
		Function<CommerceWishListItem, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((CommerceWishListItem)this);
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

		_columnOriginalValues.put(
			"commerceWishListItemId", _commerceWishListItemId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("commerceWishListId", _commerceWishListId);
		_columnOriginalValues.put("CPInstanceUuid", _CPInstanceUuid);
		_columnOriginalValues.put("CProductId", _CProductId);
		_columnOriginalValues.put("json", _json);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("commerceWishListItemId", 1L);

		columnBitmasks.put("groupId", 2L);

		columnBitmasks.put("companyId", 4L);

		columnBitmasks.put("userId", 8L);

		columnBitmasks.put("userName", 16L);

		columnBitmasks.put("createDate", 32L);

		columnBitmasks.put("modifiedDate", 64L);

		columnBitmasks.put("commerceWishListId", 128L);

		columnBitmasks.put("CPInstanceUuid", 256L);

		columnBitmasks.put("CProductId", 512L);

		columnBitmasks.put("json", 1024L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private CommerceWishListItem _escapedModel;

}