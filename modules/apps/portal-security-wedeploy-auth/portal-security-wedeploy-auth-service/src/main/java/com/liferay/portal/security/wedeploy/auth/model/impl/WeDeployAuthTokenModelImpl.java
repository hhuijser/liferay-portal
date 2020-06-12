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

package com.liferay.portal.security.wedeploy.auth.model.impl;

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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthTokenModel;

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
 * The base model implementation for the WeDeployAuthToken service. Represents a row in the &quot;WeDeployAuth_WeDeployAuthToken&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>WeDeployAuthTokenModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WeDeployAuthTokenImpl}.
 * </p>
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthTokenImpl
 * @generated
 */
public class WeDeployAuthTokenModelImpl
	extends BaseModelImpl<WeDeployAuthToken> implements WeDeployAuthTokenModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a we deploy auth token model instance should use the <code>WeDeployAuthToken</code> interface instead.
	 */
	public static final String TABLE_NAME = "WeDeployAuth_WeDeployAuthToken";

	public static final Object[][] TABLE_COLUMNS = {
		{"weDeployAuthTokenId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"clientId", Types.VARCHAR}, {"token", Types.VARCHAR},
		{"type_", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("weDeployAuthTokenId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("clientId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("token", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table WeDeployAuth_WeDeployAuthToken (weDeployAuthTokenId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,clientId VARCHAR(75) null,token VARCHAR(75) null,type_ INTEGER)";

	public static final String TABLE_SQL_DROP =
		"drop table WeDeployAuth_WeDeployAuthToken";

	public static final String ORDER_BY_JPQL =
		" ORDER BY weDeployAuthToken.weDeployAuthTokenId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY WeDeployAuth_WeDeployAuthToken.weDeployAuthTokenId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long CLIENTID_COLUMN_BITMASK = 1L;

	public static final long TOKEN_COLUMN_BITMASK = 2L;

	public static final long TYPE_COLUMN_BITMASK = 4L;

	public static final long WEDEPLOYAUTHTOKENID_COLUMN_BITMASK = 8L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public WeDeployAuthTokenModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _weDeployAuthTokenId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setWeDeployAuthTokenId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _weDeployAuthTokenId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return WeDeployAuthToken.class;
	}

	@Override
	public String getModelClassName() {
		return WeDeployAuthToken.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<WeDeployAuthToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<WeDeployAuthToken, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WeDeployAuthToken, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((WeDeployAuthToken)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<WeDeployAuthToken, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<WeDeployAuthToken, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(WeDeployAuthToken)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<WeDeployAuthToken, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<WeDeployAuthToken, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, WeDeployAuthToken>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			WeDeployAuthToken.class.getClassLoader(), WeDeployAuthToken.class,
			ModelWrapper.class);

		try {
			Constructor<WeDeployAuthToken> constructor =
				(Constructor<WeDeployAuthToken>)proxyClass.getConstructor(
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

	private static final Map<String, Function<WeDeployAuthToken, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<WeDeployAuthToken, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<WeDeployAuthToken, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<WeDeployAuthToken, Object>>();
		Map<String, BiConsumer<WeDeployAuthToken, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<WeDeployAuthToken, ?>>();

		attributeGetterFunctions.put(
			"weDeployAuthTokenId", WeDeployAuthToken::getWeDeployAuthTokenId);
		attributeSetterBiConsumers.put(
			"weDeployAuthTokenId",
			(BiConsumer<WeDeployAuthToken, Long>)
				WeDeployAuthToken::setWeDeployAuthTokenId);
		attributeGetterFunctions.put(
			"companyId", WeDeployAuthToken::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<WeDeployAuthToken, Long>)
				WeDeployAuthToken::setCompanyId);
		attributeGetterFunctions.put("userId", WeDeployAuthToken::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<WeDeployAuthToken, Long>)WeDeployAuthToken::setUserId);
		attributeGetterFunctions.put(
			"userName", WeDeployAuthToken::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<WeDeployAuthToken, String>)
				WeDeployAuthToken::setUserName);
		attributeGetterFunctions.put(
			"createDate", WeDeployAuthToken::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<WeDeployAuthToken, Date>)
				WeDeployAuthToken::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", WeDeployAuthToken::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<WeDeployAuthToken, Date>)
				WeDeployAuthToken::setModifiedDate);
		attributeGetterFunctions.put(
			"clientId", WeDeployAuthToken::getClientId);
		attributeSetterBiConsumers.put(
			"clientId",
			(BiConsumer<WeDeployAuthToken, String>)
				WeDeployAuthToken::setClientId);
		attributeGetterFunctions.put("token", WeDeployAuthToken::getToken);
		attributeSetterBiConsumers.put(
			"token",
			(BiConsumer<WeDeployAuthToken, String>)WeDeployAuthToken::setToken);
		attributeGetterFunctions.put("type", WeDeployAuthToken::getType);
		attributeSetterBiConsumers.put(
			"type",
			(BiConsumer<WeDeployAuthToken, Integer>)WeDeployAuthToken::setType);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getWeDeployAuthTokenId() {
		return _weDeployAuthTokenId;
	}

	@Override
	public void setWeDeployAuthTokenId(long weDeployAuthTokenId) {
		_weDeployAuthTokenId = weDeployAuthTokenId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
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
		_userName = userName;
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
	public String getClientId() {
		if (_clientId == null) {
			return "";
		}
		else {
			return _clientId;
		}
	}

	@Override
	public void setClientId(String clientId) {
		_columnBitmask |= CLIENTID_COLUMN_BITMASK;

		if (_originalClientId == null) {
			_originalClientId = _clientId;
		}

		_clientId = clientId;
	}

	public String getOriginalClientId() {
		return GetterUtil.getString(_originalClientId);
	}

	@Override
	public String getToken() {
		if (_token == null) {
			return "";
		}
		else {
			return _token;
		}
	}

	@Override
	public void setToken(String token) {
		_columnBitmask |= TOKEN_COLUMN_BITMASK;

		if (_originalToken == null) {
			_originalToken = _token;
		}

		_token = token;
	}

	public String getOriginalToken() {
		return GetterUtil.getString(_originalToken);
	}

	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (!_setOriginalType) {
			_setOriginalType = true;

			_originalType = _type;
		}

		_type = type;
	}

	public int getOriginalType() {
		return _originalType;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), WeDeployAuthToken.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public WeDeployAuthToken toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, WeDeployAuthToken>
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
		WeDeployAuthTokenImpl weDeployAuthTokenImpl =
			new WeDeployAuthTokenImpl();

		weDeployAuthTokenImpl.setWeDeployAuthTokenId(getWeDeployAuthTokenId());
		weDeployAuthTokenImpl.setCompanyId(getCompanyId());
		weDeployAuthTokenImpl.setUserId(getUserId());
		weDeployAuthTokenImpl.setUserName(getUserName());
		weDeployAuthTokenImpl.setCreateDate(getCreateDate());
		weDeployAuthTokenImpl.setModifiedDate(getModifiedDate());
		weDeployAuthTokenImpl.setClientId(getClientId());
		weDeployAuthTokenImpl.setToken(getToken());
		weDeployAuthTokenImpl.setType(getType());

		weDeployAuthTokenImpl.resetOriginalValues();

		return weDeployAuthTokenImpl;
	}

	@Override
	public int compareTo(WeDeployAuthToken weDeployAuthToken) {
		long primaryKey = weDeployAuthToken.getPrimaryKey();

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

		if (!(obj instanceof WeDeployAuthToken)) {
			return false;
		}

		WeDeployAuthToken weDeployAuthToken = (WeDeployAuthToken)obj;

		long primaryKey = weDeployAuthToken.getPrimaryKey();

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
		WeDeployAuthTokenModelImpl weDeployAuthTokenModelImpl = this;

		weDeployAuthTokenModelImpl._setModifiedDate = false;

		weDeployAuthTokenModelImpl._originalClientId =
			weDeployAuthTokenModelImpl._clientId;

		weDeployAuthTokenModelImpl._originalToken =
			weDeployAuthTokenModelImpl._token;

		weDeployAuthTokenModelImpl._originalType =
			weDeployAuthTokenModelImpl._type;

		weDeployAuthTokenModelImpl._setOriginalType = false;

		weDeployAuthTokenModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<WeDeployAuthToken> toCacheModel() {
		WeDeployAuthTokenCacheModel weDeployAuthTokenCacheModel =
			new WeDeployAuthTokenCacheModel();

		weDeployAuthTokenCacheModel.weDeployAuthTokenId =
			getWeDeployAuthTokenId();

		weDeployAuthTokenCacheModel.companyId = getCompanyId();

		weDeployAuthTokenCacheModel.userId = getUserId();

		weDeployAuthTokenCacheModel.userName = getUserName();

		String userName = weDeployAuthTokenCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			weDeployAuthTokenCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			weDeployAuthTokenCacheModel.createDate = createDate.getTime();
		}
		else {
			weDeployAuthTokenCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			weDeployAuthTokenCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			weDeployAuthTokenCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		weDeployAuthTokenCacheModel.clientId = getClientId();

		String clientId = weDeployAuthTokenCacheModel.clientId;

		if ((clientId != null) && (clientId.length() == 0)) {
			weDeployAuthTokenCacheModel.clientId = null;
		}

		weDeployAuthTokenCacheModel.token = getToken();

		String token = weDeployAuthTokenCacheModel.token;

		if ((token != null) && (token.length() == 0)) {
			weDeployAuthTokenCacheModel.token = null;
		}

		weDeployAuthTokenCacheModel.type = getType();

		return weDeployAuthTokenCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<WeDeployAuthToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<WeDeployAuthToken, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WeDeployAuthToken, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((WeDeployAuthToken)this));
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
		Map<String, Function<WeDeployAuthToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<WeDeployAuthToken, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WeDeployAuthToken, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((WeDeployAuthToken)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, WeDeployAuthToken>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _weDeployAuthTokenId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _clientId;
	private String _originalClientId;
	private String _token;
	private String _originalToken;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private long _columnBitmask;
	private WeDeployAuthToken _escapedModel;

}