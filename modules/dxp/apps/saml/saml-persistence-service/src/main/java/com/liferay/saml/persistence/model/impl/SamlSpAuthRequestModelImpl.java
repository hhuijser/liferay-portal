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
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;
import com.liferay.saml.persistence.model.SamlSpAuthRequestModel;

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
 * The base model implementation for the SamlSpAuthRequest service. Represents a row in the &quot;SamlSpAuthRequest&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>SamlSpAuthRequestModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SamlSpAuthRequestImpl}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequestImpl
 * @generated
 */
public class SamlSpAuthRequestModelImpl
	extends BaseModelImpl<SamlSpAuthRequest> implements SamlSpAuthRequestModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a saml sp auth request model instance should use the <code>SamlSpAuthRequest</code> interface instead.
	 */
	public static final String TABLE_NAME = "SamlSpAuthRequest";

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpAuthnRequestId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"samlIdpEntityId", Types.VARCHAR},
		{"samlSpAuthRequestKey", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("samlSpAuthnRequestId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("samlIdpEntityId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("samlSpAuthRequestKey", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table SamlSpAuthRequest (samlSpAuthnRequestId LONG not null primary key,companyId LONG,createDate DATE null,samlIdpEntityId VARCHAR(1024) null,samlSpAuthRequestKey VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table SamlSpAuthRequest";

	public static final String ORDER_BY_JPQL =
		" ORDER BY samlSpAuthRequest.samlSpAuthnRequestId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY SamlSpAuthRequest.samlSpAuthnRequestId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long CREATEDATE_COLUMN_BITMASK = 1L;

	public static final long SAMLIDPENTITYID_COLUMN_BITMASK = 2L;

	public static final long SAMLSPAUTHREQUESTKEY_COLUMN_BITMASK = 4L;

	public static final long SAMLSPAUTHNREQUESTID_COLUMN_BITMASK = 8L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public SamlSpAuthRequestModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _samlSpAuthnRequestId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSamlSpAuthnRequestId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlSpAuthnRequestId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SamlSpAuthRequest.class;
	}

	@Override
	public String getModelClassName() {
		return SamlSpAuthRequest.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<SamlSpAuthRequest, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<SamlSpAuthRequest, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpAuthRequest, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((SamlSpAuthRequest)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<SamlSpAuthRequest, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<SamlSpAuthRequest, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(SamlSpAuthRequest)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<SamlSpAuthRequest, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<SamlSpAuthRequest, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, SamlSpAuthRequest>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			SamlSpAuthRequest.class.getClassLoader(), SamlSpAuthRequest.class,
			ModelWrapper.class);

		try {
			Constructor<SamlSpAuthRequest> constructor =
				(Constructor<SamlSpAuthRequest>)proxyClass.getConstructor(
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

	private static final Map<String, Function<SamlSpAuthRequest, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<SamlSpAuthRequest, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<SamlSpAuthRequest, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<SamlSpAuthRequest, Object>>();
		Map<String, BiConsumer<SamlSpAuthRequest, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<SamlSpAuthRequest, ?>>();

		attributeGetterFunctions.put(
			"samlSpAuthnRequestId", SamlSpAuthRequest::getSamlSpAuthnRequestId);
		attributeSetterBiConsumers.put(
			"samlSpAuthnRequestId",
			(BiConsumer<SamlSpAuthRequest, Long>)
				SamlSpAuthRequest::setSamlSpAuthnRequestId);
		attributeGetterFunctions.put(
			"companyId", SamlSpAuthRequest::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<SamlSpAuthRequest, Long>)
				SamlSpAuthRequest::setCompanyId);
		attributeGetterFunctions.put(
			"createDate", SamlSpAuthRequest::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<SamlSpAuthRequest, Date>)
				SamlSpAuthRequest::setCreateDate);
		attributeGetterFunctions.put(
			"samlIdpEntityId", SamlSpAuthRequest::getSamlIdpEntityId);
		attributeSetterBiConsumers.put(
			"samlIdpEntityId",
			(BiConsumer<SamlSpAuthRequest, String>)
				SamlSpAuthRequest::setSamlIdpEntityId);
		attributeGetterFunctions.put(
			"samlSpAuthRequestKey", SamlSpAuthRequest::getSamlSpAuthRequestKey);
		attributeSetterBiConsumers.put(
			"samlSpAuthRequestKey",
			(BiConsumer<SamlSpAuthRequest, String>)
				SamlSpAuthRequest::setSamlSpAuthRequestKey);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getSamlSpAuthnRequestId() {
		return _samlSpAuthnRequestId;
	}

	@Override
	public void setSamlSpAuthnRequestId(long samlSpAuthnRequestId) {
		_samlSpAuthnRequestId = samlSpAuthnRequestId;
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
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask |= CREATEDATE_COLUMN_BITMASK;

		if (_originalCreateDate == null) {
			_originalCreateDate = _createDate;
		}

		_createDate = createDate;
	}

	public Date getOriginalCreateDate() {
		return _originalCreateDate;
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
		_columnBitmask |= SAMLIDPENTITYID_COLUMN_BITMASK;

		if (_originalSamlIdpEntityId == null) {
			_originalSamlIdpEntityId = _samlIdpEntityId;
		}

		_samlIdpEntityId = samlIdpEntityId;
	}

	public String getOriginalSamlIdpEntityId() {
		return GetterUtil.getString(_originalSamlIdpEntityId);
	}

	@Override
	public String getSamlSpAuthRequestKey() {
		if (_samlSpAuthRequestKey == null) {
			return "";
		}
		else {
			return _samlSpAuthRequestKey;
		}
	}

	@Override
	public void setSamlSpAuthRequestKey(String samlSpAuthRequestKey) {
		_columnBitmask |= SAMLSPAUTHREQUESTKEY_COLUMN_BITMASK;

		if (_originalSamlSpAuthRequestKey == null) {
			_originalSamlSpAuthRequestKey = _samlSpAuthRequestKey;
		}

		_samlSpAuthRequestKey = samlSpAuthRequestKey;
	}

	public String getOriginalSamlSpAuthRequestKey() {
		return GetterUtil.getString(_originalSamlSpAuthRequestKey);
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), SamlSpAuthRequest.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SamlSpAuthRequest toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, SamlSpAuthRequest>
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
		SamlSpAuthRequestImpl samlSpAuthRequestImpl =
			new SamlSpAuthRequestImpl();

		samlSpAuthRequestImpl.setSamlSpAuthnRequestId(
			getSamlSpAuthnRequestId());
		samlSpAuthRequestImpl.setCompanyId(getCompanyId());
		samlSpAuthRequestImpl.setCreateDate(getCreateDate());
		samlSpAuthRequestImpl.setSamlIdpEntityId(getSamlIdpEntityId());
		samlSpAuthRequestImpl.setSamlSpAuthRequestKey(
			getSamlSpAuthRequestKey());

		samlSpAuthRequestImpl.resetOriginalValues();

		return samlSpAuthRequestImpl;
	}

	@Override
	public int compareTo(SamlSpAuthRequest samlSpAuthRequest) {
		long primaryKey = samlSpAuthRequest.getPrimaryKey();

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

		if (!(object instanceof SamlSpAuthRequest)) {
			return false;
		}

		SamlSpAuthRequest samlSpAuthRequest = (SamlSpAuthRequest)object;

		long primaryKey = samlSpAuthRequest.getPrimaryKey();

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
		SamlSpAuthRequestModelImpl samlSpAuthRequestModelImpl = this;

		samlSpAuthRequestModelImpl._originalCreateDate =
			samlSpAuthRequestModelImpl._createDate;

		samlSpAuthRequestModelImpl._originalSamlIdpEntityId =
			samlSpAuthRequestModelImpl._samlIdpEntityId;

		samlSpAuthRequestModelImpl._originalSamlSpAuthRequestKey =
			samlSpAuthRequestModelImpl._samlSpAuthRequestKey;

		samlSpAuthRequestModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<SamlSpAuthRequest> toCacheModel() {
		SamlSpAuthRequestCacheModel samlSpAuthRequestCacheModel =
			new SamlSpAuthRequestCacheModel();

		samlSpAuthRequestCacheModel.samlSpAuthnRequestId =
			getSamlSpAuthnRequestId();

		samlSpAuthRequestCacheModel.companyId = getCompanyId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			samlSpAuthRequestCacheModel.createDate = createDate.getTime();
		}
		else {
			samlSpAuthRequestCacheModel.createDate = Long.MIN_VALUE;
		}

		samlSpAuthRequestCacheModel.samlIdpEntityId = getSamlIdpEntityId();

		String samlIdpEntityId = samlSpAuthRequestCacheModel.samlIdpEntityId;

		if ((samlIdpEntityId != null) && (samlIdpEntityId.length() == 0)) {
			samlSpAuthRequestCacheModel.samlIdpEntityId = null;
		}

		samlSpAuthRequestCacheModel.samlSpAuthRequestKey =
			getSamlSpAuthRequestKey();

		String samlSpAuthRequestKey =
			samlSpAuthRequestCacheModel.samlSpAuthRequestKey;

		if ((samlSpAuthRequestKey != null) &&
			(samlSpAuthRequestKey.length() == 0)) {

			samlSpAuthRequestCacheModel.samlSpAuthRequestKey = null;
		}

		return samlSpAuthRequestCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<SamlSpAuthRequest, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<SamlSpAuthRequest, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpAuthRequest, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((SamlSpAuthRequest)this));
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
		Map<String, Function<SamlSpAuthRequest, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<SamlSpAuthRequest, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SamlSpAuthRequest, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((SamlSpAuthRequest)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, SamlSpAuthRequest>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _samlSpAuthnRequestId;
	private long _companyId;
	private Date _createDate;
	private Date _originalCreateDate;
	private String _samlIdpEntityId;
	private String _originalSamlIdpEntityId;
	private String _samlSpAuthRequestKey;
	private String _originalSamlSpAuthRequestKey;
	private long _columnBitmask;
	private SamlSpAuthRequest _escapedModel;

}