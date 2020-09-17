/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.model.impl;

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
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnectionModel;

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
 * The base model implementation for the SamlSpIdpConnection service. Represents a row in the &quot;SamlSpIdpConnection&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>SamlSpIdpConnectionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SamlSpIdpConnectionImpl}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionImpl
 * @generated
 */
public class SamlSpIdpConnectionModelImpl
	extends BaseModelImpl<SamlSpIdpConnection>
	implements SamlSpIdpConnectionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a saml sp idp connection model instance should use the <code>SamlSpIdpConnection</code> interface instead.
	 */
	public static final String TABLE_NAME = "SamlSpIdpConnection";

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpIdpConnectionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"samlIdpEntityId", Types.VARCHAR},
		{"assertionSignatureRequired", Types.BOOLEAN},
		{"clockSkew", Types.BIGINT}, {"enabled", Types.BOOLEAN},
		{"forceAuthn", Types.BOOLEAN}, {"ldapImportEnabled", Types.BOOLEAN},
		{"metadataUpdatedDate", Types.TIMESTAMP},
		{"metadataUrl", Types.VARCHAR}, {"metadataXml", Types.CLOB},
		{"name", Types.VARCHAR}, {"nameIdFormat", Types.VARCHAR},
		{"signAuthnRequest", Types.BOOLEAN},
		{"unknownUsersAreStrangers", Types.BOOLEAN},
		{"userAttributeMappings", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("samlSpIdpConnectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("samlIdpEntityId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assertionSignatureRequired", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("clockSkew", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("enabled", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("forceAuthn", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("ldapImportEnabled", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("metadataUpdatedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("metadataUrl", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("metadataXml", Types.CLOB);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("nameIdFormat", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("signAuthnRequest", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("unknownUsersAreStrangers", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("userAttributeMappings", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table SamlSpIdpConnection (samlSpIdpConnectionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,samlIdpEntityId VARCHAR(1024) null,assertionSignatureRequired BOOLEAN,clockSkew LONG,enabled BOOLEAN,forceAuthn BOOLEAN,ldapImportEnabled BOOLEAN,metadataUpdatedDate DATE null,metadataUrl VARCHAR(1024) null,metadataXml TEXT null,name VARCHAR(75) null,nameIdFormat VARCHAR(1024) null,signAuthnRequest BOOLEAN,unknownUsersAreStrangers BOOLEAN,userAttributeMappings STRING null)";

	public static final String TABLE_SQL_DROP =
		"drop table SamlSpIdpConnection";

	public static final String ORDER_BY_JPQL =
		" ORDER BY samlSpIdpConnection.samlSpIdpConnectionId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY SamlSpIdpConnection.samlSpIdpConnectionId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long SAMLIDPENTITYID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long SAMLSPIDPCONNECTIONID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	public SamlSpIdpConnectionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _samlSpIdpConnectionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSamlSpIdpConnectionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlSpIdpConnectionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SamlSpIdpConnection.class;
	}

	@Override
	public String getModelClassName() {
		return SamlSpIdpConnection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<SamlSpIdpConnection, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<SamlSpIdpConnection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpIdpConnection, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((SamlSpIdpConnection)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<SamlSpIdpConnection, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<SamlSpIdpConnection, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(SamlSpIdpConnection)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<SamlSpIdpConnection, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<SamlSpIdpConnection, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, SamlSpIdpConnection>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			SamlSpIdpConnection.class.getClassLoader(),
			SamlSpIdpConnection.class, ModelWrapper.class);

		try {
			Constructor<SamlSpIdpConnection> constructor =
				(Constructor<SamlSpIdpConnection>)proxyClass.getConstructor(
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

	private static final Map<String, Function<SamlSpIdpConnection, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<SamlSpIdpConnection, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<SamlSpIdpConnection, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<SamlSpIdpConnection, Object>>();
		Map<String, BiConsumer<SamlSpIdpConnection, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<SamlSpIdpConnection, ?>>();

		attributeGetterFunctions.put(
			"samlSpIdpConnectionId",
			SamlSpIdpConnection::getSamlSpIdpConnectionId);
		attributeSetterBiConsumers.put(
			"samlSpIdpConnectionId",
			(BiConsumer<SamlSpIdpConnection, Long>)
				SamlSpIdpConnection::setSamlSpIdpConnectionId);
		attributeGetterFunctions.put(
			"companyId", SamlSpIdpConnection::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<SamlSpIdpConnection, Long>)
				SamlSpIdpConnection::setCompanyId);
		attributeGetterFunctions.put("userId", SamlSpIdpConnection::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<SamlSpIdpConnection, Long>)
				SamlSpIdpConnection::setUserId);
		attributeGetterFunctions.put(
			"userName", SamlSpIdpConnection::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<SamlSpIdpConnection, String>)
				SamlSpIdpConnection::setUserName);
		attributeGetterFunctions.put(
			"createDate", SamlSpIdpConnection::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<SamlSpIdpConnection, Date>)
				SamlSpIdpConnection::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", SamlSpIdpConnection::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<SamlSpIdpConnection, Date>)
				SamlSpIdpConnection::setModifiedDate);
		attributeGetterFunctions.put(
			"samlIdpEntityId", SamlSpIdpConnection::getSamlIdpEntityId);
		attributeSetterBiConsumers.put(
			"samlIdpEntityId",
			(BiConsumer<SamlSpIdpConnection, String>)
				SamlSpIdpConnection::setSamlIdpEntityId);
		attributeGetterFunctions.put(
			"assertionSignatureRequired",
			SamlSpIdpConnection::getAssertionSignatureRequired);
		attributeSetterBiConsumers.put(
			"assertionSignatureRequired",
			(BiConsumer<SamlSpIdpConnection, Boolean>)
				SamlSpIdpConnection::setAssertionSignatureRequired);
		attributeGetterFunctions.put(
			"clockSkew", SamlSpIdpConnection::getClockSkew);
		attributeSetterBiConsumers.put(
			"clockSkew",
			(BiConsumer<SamlSpIdpConnection, Long>)
				SamlSpIdpConnection::setClockSkew);
		attributeGetterFunctions.put(
			"enabled", SamlSpIdpConnection::getEnabled);
		attributeSetterBiConsumers.put(
			"enabled",
			(BiConsumer<SamlSpIdpConnection, Boolean>)
				SamlSpIdpConnection::setEnabled);
		attributeGetterFunctions.put(
			"forceAuthn", SamlSpIdpConnection::getForceAuthn);
		attributeSetterBiConsumers.put(
			"forceAuthn",
			(BiConsumer<SamlSpIdpConnection, Boolean>)
				SamlSpIdpConnection::setForceAuthn);
		attributeGetterFunctions.put(
			"ldapImportEnabled", SamlSpIdpConnection::getLdapImportEnabled);
		attributeSetterBiConsumers.put(
			"ldapImportEnabled",
			(BiConsumer<SamlSpIdpConnection, Boolean>)
				SamlSpIdpConnection::setLdapImportEnabled);
		attributeGetterFunctions.put(
			"metadataUpdatedDate", SamlSpIdpConnection::getMetadataUpdatedDate);
		attributeSetterBiConsumers.put(
			"metadataUpdatedDate",
			(BiConsumer<SamlSpIdpConnection, Date>)
				SamlSpIdpConnection::setMetadataUpdatedDate);
		attributeGetterFunctions.put(
			"metadataUrl", SamlSpIdpConnection::getMetadataUrl);
		attributeSetterBiConsumers.put(
			"metadataUrl",
			(BiConsumer<SamlSpIdpConnection, String>)
				SamlSpIdpConnection::setMetadataUrl);
		attributeGetterFunctions.put(
			"metadataXml", SamlSpIdpConnection::getMetadataXml);
		attributeSetterBiConsumers.put(
			"metadataXml",
			(BiConsumer<SamlSpIdpConnection, String>)
				SamlSpIdpConnection::setMetadataXml);
		attributeGetterFunctions.put("name", SamlSpIdpConnection::getName);
		attributeSetterBiConsumers.put(
			"name",
			(BiConsumer<SamlSpIdpConnection, String>)
				SamlSpIdpConnection::setName);
		attributeGetterFunctions.put(
			"nameIdFormat", SamlSpIdpConnection::getNameIdFormat);
		attributeSetterBiConsumers.put(
			"nameIdFormat",
			(BiConsumer<SamlSpIdpConnection, String>)
				SamlSpIdpConnection::setNameIdFormat);
		attributeGetterFunctions.put(
			"signAuthnRequest", SamlSpIdpConnection::getSignAuthnRequest);
		attributeSetterBiConsumers.put(
			"signAuthnRequest",
			(BiConsumer<SamlSpIdpConnection, Boolean>)
				SamlSpIdpConnection::setSignAuthnRequest);
		attributeGetterFunctions.put(
			"unknownUsersAreStrangers",
			SamlSpIdpConnection::getUnknownUsersAreStrangers);
		attributeSetterBiConsumers.put(
			"unknownUsersAreStrangers",
			(BiConsumer<SamlSpIdpConnection, Boolean>)
				SamlSpIdpConnection::setUnknownUsersAreStrangers);
		attributeGetterFunctions.put(
			"userAttributeMappings",
			SamlSpIdpConnection::getUserAttributeMappings);
		attributeSetterBiConsumers.put(
			"userAttributeMappings",
			(BiConsumer<SamlSpIdpConnection, String>)
				SamlSpIdpConnection::setUserAttributeMappings);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getSamlSpIdpConnectionId() {
		return _samlSpIdpConnectionId;
	}

	@Override
	public void setSamlSpIdpConnectionId(long samlSpIdpConnectionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_samlSpIdpConnectionId = samlSpIdpConnectionId;
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("companyId"));
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
	public String getSamlIdpEntityId() {
		if (_samlIdpEntityId == null) {
			return "";
		}
		else {
			return _samlIdpEntityId;
		}
	}

	@Override
	public void setSamlIdpEntityId(String samlIdpEntityId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_samlIdpEntityId = samlIdpEntityId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalSamlIdpEntityId() {
		return getColumnOriginalValue("samlIdpEntityId");
	}

	@Override
	public boolean getAssertionSignatureRequired() {
		return _assertionSignatureRequired;
	}

	@Override
	public boolean isAssertionSignatureRequired() {
		return _assertionSignatureRequired;
	}

	@Override
	public void setAssertionSignatureRequired(
		boolean assertionSignatureRequired) {

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_assertionSignatureRequired = assertionSignatureRequired;
	}

	@Override
	public long getClockSkew() {
		return _clockSkew;
	}

	@Override
	public void setClockSkew(long clockSkew) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_clockSkew = clockSkew;
	}

	@Override
	public boolean getEnabled() {
		return _enabled;
	}

	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_enabled = enabled;
	}

	@Override
	public boolean getForceAuthn() {
		return _forceAuthn;
	}

	@Override
	public boolean isForceAuthn() {
		return _forceAuthn;
	}

	@Override
	public void setForceAuthn(boolean forceAuthn) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_forceAuthn = forceAuthn;
	}

	@Override
	public boolean getLdapImportEnabled() {
		return _ldapImportEnabled;
	}

	@Override
	public boolean isLdapImportEnabled() {
		return _ldapImportEnabled;
	}

	@Override
	public void setLdapImportEnabled(boolean ldapImportEnabled) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_ldapImportEnabled = ldapImportEnabled;
	}

	@Override
	public Date getMetadataUpdatedDate() {
		return _metadataUpdatedDate;
	}

	@Override
	public void setMetadataUpdatedDate(Date metadataUpdatedDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_metadataUpdatedDate = metadataUpdatedDate;
	}

	@Override
	public String getMetadataUrl() {
		if (_metadataUrl == null) {
			return "";
		}
		else {
			return _metadataUrl;
		}
	}

	@Override
	public void setMetadataUrl(String metadataUrl) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_metadataUrl = metadataUrl;
	}

	@Override
	public String getMetadataXml() {
		if (_metadataXml == null) {
			return "";
		}
		else {
			return _metadataXml;
		}
	}

	@Override
	public void setMetadataXml(String metadataXml) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_metadataXml = metadataXml;
	}

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

	@Override
	public String getNameIdFormat() {
		if (_nameIdFormat == null) {
			return "";
		}
		else {
			return _nameIdFormat;
		}
	}

	@Override
	public void setNameIdFormat(String nameIdFormat) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_nameIdFormat = nameIdFormat;
	}

	@Override
	public boolean getSignAuthnRequest() {
		return _signAuthnRequest;
	}

	@Override
	public boolean isSignAuthnRequest() {
		return _signAuthnRequest;
	}

	@Override
	public void setSignAuthnRequest(boolean signAuthnRequest) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_signAuthnRequest = signAuthnRequest;
	}

	@Override
	public boolean getUnknownUsersAreStrangers() {
		return _unknownUsersAreStrangers;
	}

	@Override
	public boolean isUnknownUsersAreStrangers() {
		return _unknownUsersAreStrangers;
	}

	@Override
	public void setUnknownUsersAreStrangers(boolean unknownUsersAreStrangers) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_unknownUsersAreStrangers = unknownUsersAreStrangers;
	}

	@Override
	public String getUserAttributeMappings() {
		if (_userAttributeMappings == null) {
			return "";
		}
		else {
			return _userAttributeMappings;
		}
	}

	@Override
	public void setUserAttributeMappings(String userAttributeMappings) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userAttributeMappings = userAttributeMappings;
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
			getCompanyId(), SamlSpIdpConnection.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SamlSpIdpConnection toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, SamlSpIdpConnection>
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
		SamlSpIdpConnectionImpl samlSpIdpConnectionImpl =
			new SamlSpIdpConnectionImpl();

		samlSpIdpConnectionImpl.setSamlSpIdpConnectionId(
			getSamlSpIdpConnectionId());
		samlSpIdpConnectionImpl.setCompanyId(getCompanyId());
		samlSpIdpConnectionImpl.setUserId(getUserId());
		samlSpIdpConnectionImpl.setUserName(getUserName());
		samlSpIdpConnectionImpl.setCreateDate(getCreateDate());
		samlSpIdpConnectionImpl.setModifiedDate(getModifiedDate());
		samlSpIdpConnectionImpl.setSamlIdpEntityId(getSamlIdpEntityId());
		samlSpIdpConnectionImpl.setAssertionSignatureRequired(
			isAssertionSignatureRequired());
		samlSpIdpConnectionImpl.setClockSkew(getClockSkew());
		samlSpIdpConnectionImpl.setEnabled(isEnabled());
		samlSpIdpConnectionImpl.setForceAuthn(isForceAuthn());
		samlSpIdpConnectionImpl.setLdapImportEnabled(isLdapImportEnabled());
		samlSpIdpConnectionImpl.setMetadataUpdatedDate(
			getMetadataUpdatedDate());
		samlSpIdpConnectionImpl.setMetadataUrl(getMetadataUrl());
		samlSpIdpConnectionImpl.setMetadataXml(getMetadataXml());
		samlSpIdpConnectionImpl.setName(getName());
		samlSpIdpConnectionImpl.setNameIdFormat(getNameIdFormat());
		samlSpIdpConnectionImpl.setSignAuthnRequest(isSignAuthnRequest());
		samlSpIdpConnectionImpl.setUnknownUsersAreStrangers(
			isUnknownUsersAreStrangers());
		samlSpIdpConnectionImpl.setUserAttributeMappings(
			getUserAttributeMappings());

		samlSpIdpConnectionImpl.resetOriginalValues();

		return samlSpIdpConnectionImpl;
	}

	@Override
	public int compareTo(SamlSpIdpConnection samlSpIdpConnection) {
		long primaryKey = samlSpIdpConnection.getPrimaryKey();

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

		if (!(object instanceof SamlSpIdpConnection)) {
			return false;
		}

		SamlSpIdpConnection samlSpIdpConnection = (SamlSpIdpConnection)object;

		long primaryKey = samlSpIdpConnection.getPrimaryKey();

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
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<SamlSpIdpConnection> toCacheModel() {
		SamlSpIdpConnectionCacheModel samlSpIdpConnectionCacheModel =
			new SamlSpIdpConnectionCacheModel();

		samlSpIdpConnectionCacheModel.samlSpIdpConnectionId =
			getSamlSpIdpConnectionId();

		samlSpIdpConnectionCacheModel.companyId = getCompanyId();

		samlSpIdpConnectionCacheModel.userId = getUserId();

		samlSpIdpConnectionCacheModel.userName = getUserName();

		String userName = samlSpIdpConnectionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			samlSpIdpConnectionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			samlSpIdpConnectionCacheModel.createDate = createDate.getTime();
		}
		else {
			samlSpIdpConnectionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			samlSpIdpConnectionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			samlSpIdpConnectionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		samlSpIdpConnectionCacheModel.samlIdpEntityId = getSamlIdpEntityId();

		String samlIdpEntityId = samlSpIdpConnectionCacheModel.samlIdpEntityId;

		if ((samlIdpEntityId != null) && (samlIdpEntityId.length() == 0)) {
			samlSpIdpConnectionCacheModel.samlIdpEntityId = null;
		}

		samlSpIdpConnectionCacheModel.assertionSignatureRequired =
			isAssertionSignatureRequired();

		samlSpIdpConnectionCacheModel.clockSkew = getClockSkew();

		samlSpIdpConnectionCacheModel.enabled = isEnabled();

		samlSpIdpConnectionCacheModel.forceAuthn = isForceAuthn();

		samlSpIdpConnectionCacheModel.ldapImportEnabled = isLdapImportEnabled();

		Date metadataUpdatedDate = getMetadataUpdatedDate();

		if (metadataUpdatedDate != null) {
			samlSpIdpConnectionCacheModel.metadataUpdatedDate =
				metadataUpdatedDate.getTime();
		}
		else {
			samlSpIdpConnectionCacheModel.metadataUpdatedDate = Long.MIN_VALUE;
		}

		samlSpIdpConnectionCacheModel.metadataUrl = getMetadataUrl();

		String metadataUrl = samlSpIdpConnectionCacheModel.metadataUrl;

		if ((metadataUrl != null) && (metadataUrl.length() == 0)) {
			samlSpIdpConnectionCacheModel.metadataUrl = null;
		}

		samlSpIdpConnectionCacheModel.metadataXml = getMetadataXml();

		String metadataXml = samlSpIdpConnectionCacheModel.metadataXml;

		if ((metadataXml != null) && (metadataXml.length() == 0)) {
			samlSpIdpConnectionCacheModel.metadataXml = null;
		}

		samlSpIdpConnectionCacheModel.name = getName();

		String name = samlSpIdpConnectionCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			samlSpIdpConnectionCacheModel.name = null;
		}

		samlSpIdpConnectionCacheModel.nameIdFormat = getNameIdFormat();

		String nameIdFormat = samlSpIdpConnectionCacheModel.nameIdFormat;

		if ((nameIdFormat != null) && (nameIdFormat.length() == 0)) {
			samlSpIdpConnectionCacheModel.nameIdFormat = null;
		}

		samlSpIdpConnectionCacheModel.signAuthnRequest = isSignAuthnRequest();

		samlSpIdpConnectionCacheModel.unknownUsersAreStrangers =
			isUnknownUsersAreStrangers();

		samlSpIdpConnectionCacheModel.userAttributeMappings =
			getUserAttributeMappings();

		String userAttributeMappings =
			samlSpIdpConnectionCacheModel.userAttributeMappings;

		if ((userAttributeMappings != null) &&
			(userAttributeMappings.length() == 0)) {

			samlSpIdpConnectionCacheModel.userAttributeMappings = null;
		}

		return samlSpIdpConnectionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<SamlSpIdpConnection, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<SamlSpIdpConnection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpIdpConnection, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((SamlSpIdpConnection)this));
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
		Map<String, Function<SamlSpIdpConnection, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<SamlSpIdpConnection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpIdpConnection, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((SamlSpIdpConnection)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, SamlSpIdpConnection>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _samlSpIdpConnectionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _samlIdpEntityId;
	private boolean _assertionSignatureRequired;
	private long _clockSkew;
	private boolean _enabled;
	private boolean _forceAuthn;
	private boolean _ldapImportEnabled;
	private Date _metadataUpdatedDate;
	private String _metadataUrl;
	private String _metadataXml;
	private String _name;
	private String _nameIdFormat;
	private boolean _signAuthnRequest;
	private boolean _unknownUsersAreStrangers;
	private String _userAttributeMappings;

	public <T> T getColumnValue(String columnName) {
		Function<SamlSpIdpConnection, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((SamlSpIdpConnection)this);
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
			"samlSpIdpConnectionId", _samlSpIdpConnectionId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("samlIdpEntityId", _samlIdpEntityId);
		_columnOriginalValues.put(
			"assertionSignatureRequired", _assertionSignatureRequired);
		_columnOriginalValues.put("clockSkew", _clockSkew);
		_columnOriginalValues.put("enabled", _enabled);
		_columnOriginalValues.put("forceAuthn", _forceAuthn);
		_columnOriginalValues.put("ldapImportEnabled", _ldapImportEnabled);
		_columnOriginalValues.put("metadataUpdatedDate", _metadataUpdatedDate);
		_columnOriginalValues.put("metadataUrl", _metadataUrl);
		_columnOriginalValues.put("metadataXml", _metadataXml);
		_columnOriginalValues.put("name", _name);
		_columnOriginalValues.put("nameIdFormat", _nameIdFormat);
		_columnOriginalValues.put("signAuthnRequest", _signAuthnRequest);
		_columnOriginalValues.put(
			"unknownUsersAreStrangers", _unknownUsersAreStrangers);
		_columnOriginalValues.put(
			"userAttributeMappings", _userAttributeMappings);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("samlSpIdpConnectionId", 1L);

		columnBitmasks.put("companyId", 2L);

		columnBitmasks.put("userId", 4L);

		columnBitmasks.put("userName", 8L);

		columnBitmasks.put("createDate", 16L);

		columnBitmasks.put("modifiedDate", 32L);

		columnBitmasks.put("samlIdpEntityId", 64L);

		columnBitmasks.put("assertionSignatureRequired", 128L);

		columnBitmasks.put("clockSkew", 256L);

		columnBitmasks.put("enabled", 512L);

		columnBitmasks.put("forceAuthn", 1024L);

		columnBitmasks.put("ldapImportEnabled", 2048L);

		columnBitmasks.put("metadataUpdatedDate", 4096L);

		columnBitmasks.put("metadataUrl", 8192L);

		columnBitmasks.put("metadataXml", 16384L);

		columnBitmasks.put("name", 32768L);

		columnBitmasks.put("nameIdFormat", 65536L);

		columnBitmasks.put("signAuthnRequest", 131072L);

		columnBitmasks.put("unknownUsersAreStrangers", 262144L);

		columnBitmasks.put("userAttributeMappings", 524288L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private SamlSpIdpConnection _escapedModel;

}