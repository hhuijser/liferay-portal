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

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.commerce.model.CommerceOrderPaymentModel;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the CommerceOrderPayment service. Represents a row in the &quot;CommerceOrderPayment&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CommerceOrderPaymentModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceOrderPaymentImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPaymentImpl
 * @generated
 */
public class CommerceOrderPaymentModelImpl
	extends BaseModelImpl<CommerceOrderPayment>
	implements CommerceOrderPaymentModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce order payment model instance should use the <code>CommerceOrderPayment</code> interface instead.
	 */
	public static final String TABLE_NAME = "CommerceOrderPayment";

	public static final Object[][] TABLE_COLUMNS = {
		{"commerceOrderPaymentId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"commerceOrderId", Types.BIGINT},
		{"commercePaymentMethodKey", Types.VARCHAR}, {"content", Types.CLOB},
		{"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("commerceOrderPaymentId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceOrderId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("commercePaymentMethodKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("content", Types.CLOB);
		TABLE_COLUMNS_MAP.put("status", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table CommerceOrderPayment (commerceOrderPaymentId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceOrderId LONG,commercePaymentMethodKey VARCHAR(75) null,content TEXT null,status INTEGER)";

	public static final String TABLE_SQL_DROP =
		"drop table CommerceOrderPayment";

	public static final String ORDER_BY_JPQL =
		" ORDER BY commerceOrderPayment.createDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY CommerceOrderPayment.createDate DESC";

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
	public static final long COMMERCEORDERID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CREATEDATE_COLUMN_BITMASK = 2L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.commerce.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.commerce.model.CommerceOrderPayment"));

	public CommerceOrderPaymentModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceOrderPaymentId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceOrderPaymentId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceOrderPaymentId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceOrderPayment.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceOrderPayment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<CommerceOrderPayment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<CommerceOrderPayment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceOrderPayment, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((CommerceOrderPayment)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<CommerceOrderPayment, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<CommerceOrderPayment, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(CommerceOrderPayment)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<CommerceOrderPayment, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<CommerceOrderPayment, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, CommerceOrderPayment>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			CommerceOrderPayment.class.getClassLoader(),
			CommerceOrderPayment.class, ModelWrapper.class);

		try {
			Constructor<CommerceOrderPayment> constructor =
				(Constructor<CommerceOrderPayment>)proxyClass.getConstructor(
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

	private static final Map<String, Function<CommerceOrderPayment, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<CommerceOrderPayment, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<CommerceOrderPayment, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<CommerceOrderPayment, Object>>();
		Map<String, BiConsumer<CommerceOrderPayment, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<CommerceOrderPayment, ?>>();

		attributeGetterFunctions.put(
			"commerceOrderPaymentId",
			CommerceOrderPayment::getCommerceOrderPaymentId);
		attributeSetterBiConsumers.put(
			"commerceOrderPaymentId",
			(BiConsumer<CommerceOrderPayment, Long>)
				CommerceOrderPayment::setCommerceOrderPaymentId);
		attributeGetterFunctions.put(
			"groupId", CommerceOrderPayment::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<CommerceOrderPayment, Long>)
				CommerceOrderPayment::setGroupId);
		attributeGetterFunctions.put(
			"companyId", CommerceOrderPayment::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<CommerceOrderPayment, Long>)
				CommerceOrderPayment::setCompanyId);
		attributeGetterFunctions.put("userId", CommerceOrderPayment::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<CommerceOrderPayment, Long>)
				CommerceOrderPayment::setUserId);
		attributeGetterFunctions.put(
			"userName", CommerceOrderPayment::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<CommerceOrderPayment, String>)
				CommerceOrderPayment::setUserName);
		attributeGetterFunctions.put(
			"createDate", CommerceOrderPayment::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<CommerceOrderPayment, Date>)
				CommerceOrderPayment::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", CommerceOrderPayment::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<CommerceOrderPayment, Date>)
				CommerceOrderPayment::setModifiedDate);
		attributeGetterFunctions.put(
			"commerceOrderId", CommerceOrderPayment::getCommerceOrderId);
		attributeSetterBiConsumers.put(
			"commerceOrderId",
			(BiConsumer<CommerceOrderPayment, Long>)
				CommerceOrderPayment::setCommerceOrderId);
		attributeGetterFunctions.put(
			"commercePaymentMethodKey",
			CommerceOrderPayment::getCommercePaymentMethodKey);
		attributeSetterBiConsumers.put(
			"commercePaymentMethodKey",
			(BiConsumer<CommerceOrderPayment, String>)
				CommerceOrderPayment::setCommercePaymentMethodKey);
		attributeGetterFunctions.put(
			"content", CommerceOrderPayment::getContent);
		attributeSetterBiConsumers.put(
			"content",
			(BiConsumer<CommerceOrderPayment, String>)
				CommerceOrderPayment::setContent);
		attributeGetterFunctions.put("status", CommerceOrderPayment::getStatus);
		attributeSetterBiConsumers.put(
			"status",
			(BiConsumer<CommerceOrderPayment, Integer>)
				CommerceOrderPayment::setStatus);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getCommerceOrderPaymentId() {
		return _commerceOrderPaymentId;
	}

	@Override
	public void setCommerceOrderPaymentId(long commerceOrderPaymentId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceOrderPaymentId = commerceOrderPaymentId;
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
	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commerceOrderId = commerceOrderId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCommerceOrderId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("commerceOrderId"));
	}

	@Override
	public String getCommercePaymentMethodKey() {
		if (_commercePaymentMethodKey == null) {
			return "";
		}
		else {
			return _commercePaymentMethodKey;
		}
	}

	@Override
	public void setCommercePaymentMethodKey(String commercePaymentMethodKey) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_commercePaymentMethodKey = commercePaymentMethodKey;
	}

	@Override
	public String getContent() {
		if (_content == null) {
			return "";
		}
		else {
			return _content;
		}
	}

	@Override
	public void setContent(String content) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_content = content;
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_status = status;
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
			getCompanyId(), CommerceOrderPayment.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CommerceOrderPayment toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, CommerceOrderPayment>
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
		CommerceOrderPaymentImpl commerceOrderPaymentImpl =
			new CommerceOrderPaymentImpl();

		commerceOrderPaymentImpl.setCommerceOrderPaymentId(
			getCommerceOrderPaymentId());
		commerceOrderPaymentImpl.setGroupId(getGroupId());
		commerceOrderPaymentImpl.setCompanyId(getCompanyId());
		commerceOrderPaymentImpl.setUserId(getUserId());
		commerceOrderPaymentImpl.setUserName(getUserName());
		commerceOrderPaymentImpl.setCreateDate(getCreateDate());
		commerceOrderPaymentImpl.setModifiedDate(getModifiedDate());
		commerceOrderPaymentImpl.setCommerceOrderId(getCommerceOrderId());
		commerceOrderPaymentImpl.setCommercePaymentMethodKey(
			getCommercePaymentMethodKey());
		commerceOrderPaymentImpl.setContent(getContent());
		commerceOrderPaymentImpl.setStatus(getStatus());

		commerceOrderPaymentImpl.resetOriginalValues();

		return commerceOrderPaymentImpl;
	}

	@Override
	public int compareTo(CommerceOrderPayment commerceOrderPayment) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), commerceOrderPayment.getCreateDate());

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

		if (!(object instanceof CommerceOrderPayment)) {
			return false;
		}

		CommerceOrderPayment commerceOrderPayment =
			(CommerceOrderPayment)object;

		long primaryKey = commerceOrderPayment.getPrimaryKey();

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
	public CacheModel<CommerceOrderPayment> toCacheModel() {
		CommerceOrderPaymentCacheModel commerceOrderPaymentCacheModel =
			new CommerceOrderPaymentCacheModel();

		commerceOrderPaymentCacheModel.commerceOrderPaymentId =
			getCommerceOrderPaymentId();

		commerceOrderPaymentCacheModel.groupId = getGroupId();

		commerceOrderPaymentCacheModel.companyId = getCompanyId();

		commerceOrderPaymentCacheModel.userId = getUserId();

		commerceOrderPaymentCacheModel.userName = getUserName();

		String userName = commerceOrderPaymentCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceOrderPaymentCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceOrderPaymentCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceOrderPaymentCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceOrderPaymentCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			commerceOrderPaymentCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceOrderPaymentCacheModel.commerceOrderId = getCommerceOrderId();

		commerceOrderPaymentCacheModel.commercePaymentMethodKey =
			getCommercePaymentMethodKey();

		String commercePaymentMethodKey =
			commerceOrderPaymentCacheModel.commercePaymentMethodKey;

		if ((commercePaymentMethodKey != null) &&
			(commercePaymentMethodKey.length() == 0)) {

			commerceOrderPaymentCacheModel.commercePaymentMethodKey = null;
		}

		commerceOrderPaymentCacheModel.content = getContent();

		String content = commerceOrderPaymentCacheModel.content;

		if ((content != null) && (content.length() == 0)) {
			commerceOrderPaymentCacheModel.content = null;
		}

		commerceOrderPaymentCacheModel.status = getStatus();

		return commerceOrderPaymentCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<CommerceOrderPayment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<CommerceOrderPayment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceOrderPayment, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((CommerceOrderPayment)this));
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
		Map<String, Function<CommerceOrderPayment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<CommerceOrderPayment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CommerceOrderPayment, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((CommerceOrderPayment)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, CommerceOrderPayment>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _commerceOrderPaymentId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceOrderId;
	private String _commercePaymentMethodKey;
	private String _content;
	private int _status;

	public <T> T getColumnValue(String columnName) {
		Function<CommerceOrderPayment, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((CommerceOrderPayment)this);
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
			"commerceOrderPaymentId", _commerceOrderPaymentId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("commerceOrderId", _commerceOrderId);
		_columnOriginalValues.put(
			"commercePaymentMethodKey", _commercePaymentMethodKey);
		_columnOriginalValues.put("content", _content);
		_columnOriginalValues.put("status", _status);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("commerceOrderPaymentId", 1L);

		columnBitmasks.put("groupId", 2L);

		columnBitmasks.put("companyId", 4L);

		columnBitmasks.put("userId", 8L);

		columnBitmasks.put("userName", 16L);

		columnBitmasks.put("createDate", 32L);

		columnBitmasks.put("modifiedDate", 64L);

		columnBitmasks.put("commerceOrderId", 128L);

		columnBitmasks.put("commercePaymentMethodKey", 256L);

		columnBitmasks.put("content", 512L);

		columnBitmasks.put("status", 1024L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private CommerceOrderPayment _escapedModel;

}