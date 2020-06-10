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

package com.liferay.oauth2.provider.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationModel;
import com.liferay.oauth2.provider.model.OAuth2ApplicationSoap;
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
 * The base model implementation for the OAuth2Application service. Represents a row in the &quot;OAuth2Application&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>OAuth2ApplicationModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link OAuth2ApplicationImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationImpl
 * @generated
 */
@JSON(strict = true)
public class OAuth2ApplicationModelImpl
	extends BaseModelImpl<OAuth2Application> implements OAuth2ApplicationModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a o auth2 application model instance should use the <code>OAuth2Application</code> interface instead.
	 */
	public static final String TABLE_NAME = "OAuth2Application";

	public static final Object[][] TABLE_COLUMNS = {
		{"oAuth2ApplicationId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"oA2AScopeAliasesId", Types.BIGINT},
		{"allowedGrantTypes", Types.VARCHAR},
		{"clientCredentialUserId", Types.BIGINT},
		{"clientCredentialUserName", Types.VARCHAR},
		{"clientId", Types.VARCHAR}, {"clientProfile", Types.INTEGER},
		{"clientSecret", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"features", Types.VARCHAR}, {"homePageURL", Types.VARCHAR},
		{"iconFileEntryId", Types.BIGINT}, {"name", Types.VARCHAR},
		{"privacyPolicyURL", Types.VARCHAR}, {"redirectURIs", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("oAuth2ApplicationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("oA2AScopeAliasesId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("allowedGrantTypes", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("clientCredentialUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("clientCredentialUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("clientId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("clientProfile", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("clientSecret", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("features", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("homePageURL", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("iconFileEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("privacyPolicyURL", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("redirectURIs", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table OAuth2Application (oAuth2ApplicationId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,oA2AScopeAliasesId LONG,allowedGrantTypes VARCHAR(75) null,clientCredentialUserId LONG,clientCredentialUserName VARCHAR(75) null,clientId VARCHAR(75) null,clientProfile INTEGER,clientSecret VARCHAR(75) null,description STRING null,features STRING null,homePageURL STRING null,iconFileEntryId LONG,name VARCHAR(75) null,privacyPolicyURL STRING null,redirectURIs STRING null)";

	public static final String TABLE_SQL_DROP = "drop table OAuth2Application";

	public static final String ORDER_BY_JPQL =
		" ORDER BY oAuth2Application.oAuth2ApplicationId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY OAuth2Application.oAuth2ApplicationId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long CLIENTID_COLUMN_BITMASK = 1L;

	public static final long COMPANYID_COLUMN_BITMASK = 2L;

	public static final long OAUTH2APPLICATIONID_COLUMN_BITMASK = 4L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static OAuth2Application toModel(OAuth2ApplicationSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		OAuth2Application model = new OAuth2ApplicationImpl();

		model.setOAuth2ApplicationId(soapModel.getOAuth2ApplicationId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setOAuth2ApplicationScopeAliasesId(
			soapModel.getOAuth2ApplicationScopeAliasesId());
		model.setAllowedGrantTypes(soapModel.getAllowedGrantTypes());
		model.setClientCredentialUserId(soapModel.getClientCredentialUserId());
		model.setClientCredentialUserName(
			soapModel.getClientCredentialUserName());
		model.setClientId(soapModel.getClientId());
		model.setClientProfile(soapModel.getClientProfile());
		model.setClientSecret(soapModel.getClientSecret());
		model.setDescription(soapModel.getDescription());
		model.setFeatures(soapModel.getFeatures());
		model.setHomePageURL(soapModel.getHomePageURL());
		model.setIconFileEntryId(soapModel.getIconFileEntryId());
		model.setName(soapModel.getName());
		model.setPrivacyPolicyURL(soapModel.getPrivacyPolicyURL());
		model.setRedirectURIs(soapModel.getRedirectURIs());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<OAuth2Application> toModels(
		OAuth2ApplicationSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<OAuth2Application> models = new ArrayList<OAuth2Application>(
			soapModels.length);

		for (OAuth2ApplicationSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public OAuth2ApplicationModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _oAuth2ApplicationId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setOAuth2ApplicationId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuth2ApplicationId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return OAuth2Application.class;
	}

	@Override
	public String getModelClassName() {
		return OAuth2Application.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<OAuth2Application, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<OAuth2Application, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OAuth2Application, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((OAuth2Application)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<OAuth2Application, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<OAuth2Application, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(OAuth2Application)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<OAuth2Application, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<OAuth2Application, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, OAuth2Application>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			OAuth2Application.class.getClassLoader(), OAuth2Application.class,
			ModelWrapper.class);

		try {
			Constructor<OAuth2Application> constructor =
				(Constructor<OAuth2Application>)proxyClass.getConstructor(
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

	private static final Map<String, Function<OAuth2Application, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<OAuth2Application, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<OAuth2Application, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<OAuth2Application, Object>>();
		Map<String, BiConsumer<OAuth2Application, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<OAuth2Application, ?>>();

		attributeGetterFunctions.put(
			"oAuth2ApplicationId", OAuth2Application::getOAuth2ApplicationId);
		attributeSetterBiConsumers.put(
			"oAuth2ApplicationId",
			(BiConsumer<OAuth2Application, Long>)
				OAuth2Application::setOAuth2ApplicationId);
		attributeGetterFunctions.put(
			"companyId", OAuth2Application::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<OAuth2Application, Long>)
				OAuth2Application::setCompanyId);
		attributeGetterFunctions.put("userId", OAuth2Application::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<OAuth2Application, Long>)OAuth2Application::setUserId);
		attributeGetterFunctions.put(
			"userName", OAuth2Application::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setUserName);
		attributeGetterFunctions.put(
			"createDate", OAuth2Application::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<OAuth2Application, Date>)
				OAuth2Application::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", OAuth2Application::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<OAuth2Application, Date>)
				OAuth2Application::setModifiedDate);
		attributeGetterFunctions.put(
			"oAuth2ApplicationScopeAliasesId",
			OAuth2Application::getOAuth2ApplicationScopeAliasesId);
		attributeSetterBiConsumers.put(
			"oAuth2ApplicationScopeAliasesId",
			(BiConsumer<OAuth2Application, Long>)
				OAuth2Application::setOAuth2ApplicationScopeAliasesId);
		attributeGetterFunctions.put(
			"allowedGrantTypes", OAuth2Application::getAllowedGrantTypes);
		attributeSetterBiConsumers.put(
			"allowedGrantTypes",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setAllowedGrantTypes);
		attributeGetterFunctions.put(
			"clientCredentialUserId",
			OAuth2Application::getClientCredentialUserId);
		attributeSetterBiConsumers.put(
			"clientCredentialUserId",
			(BiConsumer<OAuth2Application, Long>)
				OAuth2Application::setClientCredentialUserId);
		attributeGetterFunctions.put(
			"clientCredentialUserName",
			OAuth2Application::getClientCredentialUserName);
		attributeSetterBiConsumers.put(
			"clientCredentialUserName",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setClientCredentialUserName);
		attributeGetterFunctions.put(
			"clientId", OAuth2Application::getClientId);
		attributeSetterBiConsumers.put(
			"clientId",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setClientId);
		attributeGetterFunctions.put(
			"clientProfile", OAuth2Application::getClientProfile);
		attributeSetterBiConsumers.put(
			"clientProfile",
			(BiConsumer<OAuth2Application, Integer>)
				OAuth2Application::setClientProfile);
		attributeGetterFunctions.put(
			"clientSecret", OAuth2Application::getClientSecret);
		attributeSetterBiConsumers.put(
			"clientSecret",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setClientSecret);
		attributeGetterFunctions.put(
			"description", OAuth2Application::getDescription);
		attributeSetterBiConsumers.put(
			"description",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setDescription);
		attributeGetterFunctions.put(
			"features", OAuth2Application::getFeatures);
		attributeSetterBiConsumers.put(
			"features",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setFeatures);
		attributeGetterFunctions.put(
			"homePageURL", OAuth2Application::getHomePageURL);
		attributeSetterBiConsumers.put(
			"homePageURL",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setHomePageURL);
		attributeGetterFunctions.put(
			"iconFileEntryId", OAuth2Application::getIconFileEntryId);
		attributeSetterBiConsumers.put(
			"iconFileEntryId",
			(BiConsumer<OAuth2Application, Long>)
				OAuth2Application::setIconFileEntryId);
		attributeGetterFunctions.put("name", OAuth2Application::getName);
		attributeSetterBiConsumers.put(
			"name",
			(BiConsumer<OAuth2Application, String>)OAuth2Application::setName);
		attributeGetterFunctions.put(
			"privacyPolicyURL", OAuth2Application::getPrivacyPolicyURL);
		attributeSetterBiConsumers.put(
			"privacyPolicyURL",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setPrivacyPolicyURL);
		attributeGetterFunctions.put(
			"redirectURIs", OAuth2Application::getRedirectURIs);
		attributeSetterBiConsumers.put(
			"redirectURIs",
			(BiConsumer<OAuth2Application, String>)
				OAuth2Application::setRedirectURIs);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getOAuth2ApplicationId() {
		return _oAuth2ApplicationId;
	}

	@Override
	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2ApplicationId = oAuth2ApplicationId;
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
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
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

	@JSON
	@Override
	public long getOAuth2ApplicationScopeAliasesId() {
		return _oAuth2ApplicationScopeAliasesId;
	}

	@Override
	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {

		_oAuth2ApplicationScopeAliasesId = oAuth2ApplicationScopeAliasesId;
	}

	@JSON
	@Override
	public String getAllowedGrantTypes() {
		if (_allowedGrantTypes == null) {
			return "";
		}
		else {
			return _allowedGrantTypes;
		}
	}

	@Override
	public void setAllowedGrantTypes(String allowedGrantTypes) {
		_allowedGrantTypes = allowedGrantTypes;
	}

	@JSON
	@Override
	public long getClientCredentialUserId() {
		return _clientCredentialUserId;
	}

	@Override
	public void setClientCredentialUserId(long clientCredentialUserId) {
		_clientCredentialUserId = clientCredentialUserId;
	}

	@Override
	public String getClientCredentialUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(
				getClientCredentialUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setClientCredentialUserUuid(String clientCredentialUserUuid) {
	}

	@JSON
	@Override
	public String getClientCredentialUserName() {
		if (_clientCredentialUserName == null) {
			return "";
		}
		else {
			return _clientCredentialUserName;
		}
	}

	@Override
	public void setClientCredentialUserName(String clientCredentialUserName) {
		_clientCredentialUserName = clientCredentialUserName;
	}

	@JSON
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

	@JSON
	@Override
	public int getClientProfile() {
		return _clientProfile;
	}

	@Override
	public void setClientProfile(int clientProfile) {
		_clientProfile = clientProfile;
	}

	@JSON
	@Override
	public String getClientSecret() {
		if (_clientSecret == null) {
			return "";
		}
		else {
			return _clientSecret;
		}
	}

	@Override
	public void setClientSecret(String clientSecret) {
		_clientSecret = clientSecret;
	}

	@JSON
	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	@Override
	public String getFeatures() {
		if (_features == null) {
			return "";
		}
		else {
			return _features;
		}
	}

	@Override
	public void setFeatures(String features) {
		_features = features;
	}

	@JSON
	@Override
	public String getHomePageURL() {
		if (_homePageURL == null) {
			return "";
		}
		else {
			return _homePageURL;
		}
	}

	@Override
	public void setHomePageURL(String homePageURL) {
		_homePageURL = homePageURL;
	}

	@JSON
	@Override
	public long getIconFileEntryId() {
		return _iconFileEntryId;
	}

	@Override
	public void setIconFileEntryId(long iconFileEntryId) {
		_iconFileEntryId = iconFileEntryId;
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
		_name = name;
	}

	@JSON
	@Override
	public String getPrivacyPolicyURL() {
		if (_privacyPolicyURL == null) {
			return "";
		}
		else {
			return _privacyPolicyURL;
		}
	}

	@Override
	public void setPrivacyPolicyURL(String privacyPolicyURL) {
		_privacyPolicyURL = privacyPolicyURL;
	}

	@JSON
	@Override
	public String getRedirectURIs() {
		if (_redirectURIs == null) {
			return "";
		}
		else {
			return _redirectURIs;
		}
	}

	@Override
	public void setRedirectURIs(String redirectURIs) {
		_redirectURIs = redirectURIs;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), OAuth2Application.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public OAuth2Application toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, OAuth2Application>
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
		OAuth2ApplicationImpl oAuth2ApplicationImpl =
			new OAuth2ApplicationImpl();

		oAuth2ApplicationImpl.setOAuth2ApplicationId(getOAuth2ApplicationId());
		oAuth2ApplicationImpl.setCompanyId(getCompanyId());
		oAuth2ApplicationImpl.setUserId(getUserId());
		oAuth2ApplicationImpl.setUserName(getUserName());
		oAuth2ApplicationImpl.setCreateDate(getCreateDate());
		oAuth2ApplicationImpl.setModifiedDate(getModifiedDate());
		oAuth2ApplicationImpl.setOAuth2ApplicationScopeAliasesId(
			getOAuth2ApplicationScopeAliasesId());
		oAuth2ApplicationImpl.setAllowedGrantTypes(getAllowedGrantTypes());
		oAuth2ApplicationImpl.setClientCredentialUserId(
			getClientCredentialUserId());
		oAuth2ApplicationImpl.setClientCredentialUserName(
			getClientCredentialUserName());
		oAuth2ApplicationImpl.setClientId(getClientId());
		oAuth2ApplicationImpl.setClientProfile(getClientProfile());
		oAuth2ApplicationImpl.setClientSecret(getClientSecret());
		oAuth2ApplicationImpl.setDescription(getDescription());
		oAuth2ApplicationImpl.setFeatures(getFeatures());
		oAuth2ApplicationImpl.setHomePageURL(getHomePageURL());
		oAuth2ApplicationImpl.setIconFileEntryId(getIconFileEntryId());
		oAuth2ApplicationImpl.setName(getName());
		oAuth2ApplicationImpl.setPrivacyPolicyURL(getPrivacyPolicyURL());
		oAuth2ApplicationImpl.setRedirectURIs(getRedirectURIs());

		oAuth2ApplicationImpl.resetOriginalValues();

		return oAuth2ApplicationImpl;
	}

	@Override
	public int compareTo(OAuth2Application oAuth2Application) {
		long primaryKey = oAuth2Application.getPrimaryKey();

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

		if (!(object instanceof OAuth2Application)) {
			return false;
		}

		OAuth2Application oAuth2Application = (OAuth2Application)object;

		long primaryKey = oAuth2Application.getPrimaryKey();

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
		OAuth2ApplicationModelImpl oAuth2ApplicationModelImpl = this;

		oAuth2ApplicationModelImpl._originalCompanyId =
			oAuth2ApplicationModelImpl._companyId;

		oAuth2ApplicationModelImpl._setOriginalCompanyId = false;

		oAuth2ApplicationModelImpl._setModifiedDate = false;

		oAuth2ApplicationModelImpl._originalClientId =
			oAuth2ApplicationModelImpl._clientId;

		oAuth2ApplicationModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<OAuth2Application> toCacheModel() {
		OAuth2ApplicationCacheModel oAuth2ApplicationCacheModel =
			new OAuth2ApplicationCacheModel();

		oAuth2ApplicationCacheModel.oAuth2ApplicationId =
			getOAuth2ApplicationId();

		oAuth2ApplicationCacheModel.companyId = getCompanyId();

		oAuth2ApplicationCacheModel.userId = getUserId();

		oAuth2ApplicationCacheModel.userName = getUserName();

		String userName = oAuth2ApplicationCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			oAuth2ApplicationCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			oAuth2ApplicationCacheModel.createDate = createDate.getTime();
		}
		else {
			oAuth2ApplicationCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			oAuth2ApplicationCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			oAuth2ApplicationCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		oAuth2ApplicationCacheModel.oAuth2ApplicationScopeAliasesId =
			getOAuth2ApplicationScopeAliasesId();

		oAuth2ApplicationCacheModel.allowedGrantTypes = getAllowedGrantTypes();

		String allowedGrantTypes =
			oAuth2ApplicationCacheModel.allowedGrantTypes;

		if ((allowedGrantTypes != null) && (allowedGrantTypes.length() == 0)) {
			oAuth2ApplicationCacheModel.allowedGrantTypes = null;
		}

		oAuth2ApplicationCacheModel.clientCredentialUserId =
			getClientCredentialUserId();

		oAuth2ApplicationCacheModel.clientCredentialUserName =
			getClientCredentialUserName();

		String clientCredentialUserName =
			oAuth2ApplicationCacheModel.clientCredentialUserName;

		if ((clientCredentialUserName != null) &&
			(clientCredentialUserName.length() == 0)) {

			oAuth2ApplicationCacheModel.clientCredentialUserName = null;
		}

		oAuth2ApplicationCacheModel.clientId = getClientId();

		String clientId = oAuth2ApplicationCacheModel.clientId;

		if ((clientId != null) && (clientId.length() == 0)) {
			oAuth2ApplicationCacheModel.clientId = null;
		}

		oAuth2ApplicationCacheModel.clientProfile = getClientProfile();

		oAuth2ApplicationCacheModel.clientSecret = getClientSecret();

		String clientSecret = oAuth2ApplicationCacheModel.clientSecret;

		if ((clientSecret != null) && (clientSecret.length() == 0)) {
			oAuth2ApplicationCacheModel.clientSecret = null;
		}

		oAuth2ApplicationCacheModel.description = getDescription();

		String description = oAuth2ApplicationCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			oAuth2ApplicationCacheModel.description = null;
		}

		oAuth2ApplicationCacheModel.features = getFeatures();

		String features = oAuth2ApplicationCacheModel.features;

		if ((features != null) && (features.length() == 0)) {
			oAuth2ApplicationCacheModel.features = null;
		}

		oAuth2ApplicationCacheModel.homePageURL = getHomePageURL();

		String homePageURL = oAuth2ApplicationCacheModel.homePageURL;

		if ((homePageURL != null) && (homePageURL.length() == 0)) {
			oAuth2ApplicationCacheModel.homePageURL = null;
		}

		oAuth2ApplicationCacheModel.iconFileEntryId = getIconFileEntryId();

		oAuth2ApplicationCacheModel.name = getName();

		String name = oAuth2ApplicationCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			oAuth2ApplicationCacheModel.name = null;
		}

		oAuth2ApplicationCacheModel.privacyPolicyURL = getPrivacyPolicyURL();

		String privacyPolicyURL = oAuth2ApplicationCacheModel.privacyPolicyURL;

		if ((privacyPolicyURL != null) && (privacyPolicyURL.length() == 0)) {
			oAuth2ApplicationCacheModel.privacyPolicyURL = null;
		}

		oAuth2ApplicationCacheModel.redirectURIs = getRedirectURIs();

		String redirectURIs = oAuth2ApplicationCacheModel.redirectURIs;

		if ((redirectURIs != null) && (redirectURIs.length() == 0)) {
			oAuth2ApplicationCacheModel.redirectURIs = null;
		}

		return oAuth2ApplicationCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<OAuth2Application, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<OAuth2Application, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OAuth2Application, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((OAuth2Application)this));
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
		Map<String, Function<OAuth2Application, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<OAuth2Application, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OAuth2Application, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((OAuth2Application)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, OAuth2Application>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _oAuth2ApplicationId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _oAuth2ApplicationScopeAliasesId;
	private String _allowedGrantTypes;
	private long _clientCredentialUserId;
	private String _clientCredentialUserName;
	private String _clientId;
	private String _originalClientId;
	private int _clientProfile;
	private String _clientSecret;
	private String _description;
	private String _features;
	private String _homePageURL;
	private long _iconFileEntryId;
	private String _name;
	private String _privacyPolicyURL;
	private String _redirectURIs;
	private long _columnBitmask;
	private OAuth2Application _escapedModel;

}