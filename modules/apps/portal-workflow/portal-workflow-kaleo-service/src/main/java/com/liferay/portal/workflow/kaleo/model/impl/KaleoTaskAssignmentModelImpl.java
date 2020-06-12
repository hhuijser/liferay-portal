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

package com.liferay.portal.workflow.kaleo.model.impl;

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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentModel;

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
 * The base model implementation for the KaleoTaskAssignment service. Represents a row in the &quot;KaleoTaskAssignment&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>KaleoTaskAssignmentModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoTaskAssignmentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignmentImpl
 * @generated
 */
public class KaleoTaskAssignmentModelImpl
	extends BaseModelImpl<KaleoTaskAssignment>
	implements KaleoTaskAssignmentModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo task assignment model instance should use the <code>KaleoTaskAssignment</code> interface instead.
	 */
	public static final String TABLE_NAME = "KaleoTaskAssignment";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"kaleoTaskAssignmentId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"kaleoClassName", Types.VARCHAR}, {"kaleoClassPK", Types.BIGINT},
		{"kaleoDefinitionId", Types.BIGINT},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoNodeId", Types.BIGINT}, {"assigneeClassName", Types.VARCHAR},
		{"assigneeClassPK", Types.BIGINT}, {"assigneeActionId", Types.VARCHAR},
		{"assigneeScript", Types.CLOB},
		{"assigneeScriptLanguage", Types.VARCHAR},
		{"assigneeScriptRequiredContexts", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskAssignmentId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("kaleoClassName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("kaleoClassPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoNodeId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("assigneeClassName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assigneeClassPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("assigneeActionId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assigneeScript", Types.CLOB);
		TABLE_COLUMNS_MAP.put("assigneeScriptLanguage", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assigneeScriptRequiredContexts", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KaleoTaskAssignment (mvccVersion LONG default 0 not null,kaleoTaskAssignmentId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionId LONG,kaleoDefinitionVersionId LONG,kaleoNodeId LONG,assigneeClassName VARCHAR(200) null,assigneeClassPK LONG,assigneeActionId VARCHAR(75) null,assigneeScript TEXT null,assigneeScriptLanguage VARCHAR(75) null,assigneeScriptRequiredContexts STRING null)";

	public static final String TABLE_SQL_DROP =
		"drop table KaleoTaskAssignment";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kaleoTaskAssignment.kaleoTaskAssignmentId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KaleoTaskAssignment.kaleoTaskAssignmentId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long ASSIGNEECLASSNAME_COLUMN_BITMASK = 1L;

	public static final long COMPANYID_COLUMN_BITMASK = 2L;

	public static final long KALEOCLASSNAME_COLUMN_BITMASK = 4L;

	public static final long KALEOCLASSPK_COLUMN_BITMASK = 8L;

	public static final long KALEODEFINITIONVERSIONID_COLUMN_BITMASK = 16L;

	public static final long KALEOTASKASSIGNMENTID_COLUMN_BITMASK = 32L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public KaleoTaskAssignmentModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kaleoTaskAssignmentId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKaleoTaskAssignmentId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoTaskAssignmentId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoTaskAssignment.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoTaskAssignment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KaleoTaskAssignment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KaleoTaskAssignment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskAssignment, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((KaleoTaskAssignment)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KaleoTaskAssignment, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KaleoTaskAssignment, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KaleoTaskAssignment)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KaleoTaskAssignment, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KaleoTaskAssignment, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, KaleoTaskAssignment>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			KaleoTaskAssignment.class.getClassLoader(),
			KaleoTaskAssignment.class, ModelWrapper.class);

		try {
			Constructor<KaleoTaskAssignment> constructor =
				(Constructor<KaleoTaskAssignment>)proxyClass.getConstructor(
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

	private static final Map<String, Function<KaleoTaskAssignment, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<KaleoTaskAssignment, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<KaleoTaskAssignment, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<KaleoTaskAssignment, Object>>();
		Map<String, BiConsumer<KaleoTaskAssignment, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<KaleoTaskAssignment, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", KaleoTaskAssignment::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setMvccVersion);
		attributeGetterFunctions.put(
			"kaleoTaskAssignmentId",
			KaleoTaskAssignment::getKaleoTaskAssignmentId);
		attributeSetterBiConsumers.put(
			"kaleoTaskAssignmentId",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setKaleoTaskAssignmentId);
		attributeGetterFunctions.put(
			"groupId", KaleoTaskAssignment::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setGroupId);
		attributeGetterFunctions.put(
			"companyId", KaleoTaskAssignment::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setCompanyId);
		attributeGetterFunctions.put("userId", KaleoTaskAssignment::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setUserId);
		attributeGetterFunctions.put(
			"userName", KaleoTaskAssignment::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<KaleoTaskAssignment, String>)
				KaleoTaskAssignment::setUserName);
		attributeGetterFunctions.put(
			"createDate", KaleoTaskAssignment::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<KaleoTaskAssignment, Date>)
				KaleoTaskAssignment::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", KaleoTaskAssignment::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<KaleoTaskAssignment, Date>)
				KaleoTaskAssignment::setModifiedDate);
		attributeGetterFunctions.put(
			"kaleoClassName", KaleoTaskAssignment::getKaleoClassName);
		attributeSetterBiConsumers.put(
			"kaleoClassName",
			(BiConsumer<KaleoTaskAssignment, String>)
				KaleoTaskAssignment::setKaleoClassName);
		attributeGetterFunctions.put(
			"kaleoClassPK", KaleoTaskAssignment::getKaleoClassPK);
		attributeSetterBiConsumers.put(
			"kaleoClassPK",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setKaleoClassPK);
		attributeGetterFunctions.put(
			"kaleoDefinitionId", KaleoTaskAssignment::getKaleoDefinitionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionId",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setKaleoDefinitionId);
		attributeGetterFunctions.put(
			"kaleoDefinitionVersionId",
			KaleoTaskAssignment::getKaleoDefinitionVersionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionVersionId",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setKaleoDefinitionVersionId);
		attributeGetterFunctions.put(
			"kaleoNodeId", KaleoTaskAssignment::getKaleoNodeId);
		attributeSetterBiConsumers.put(
			"kaleoNodeId",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setKaleoNodeId);
		attributeGetterFunctions.put(
			"assigneeClassName", KaleoTaskAssignment::getAssigneeClassName);
		attributeSetterBiConsumers.put(
			"assigneeClassName",
			(BiConsumer<KaleoTaskAssignment, String>)
				KaleoTaskAssignment::setAssigneeClassName);
		attributeGetterFunctions.put(
			"assigneeClassPK", KaleoTaskAssignment::getAssigneeClassPK);
		attributeSetterBiConsumers.put(
			"assigneeClassPK",
			(BiConsumer<KaleoTaskAssignment, Long>)
				KaleoTaskAssignment::setAssigneeClassPK);
		attributeGetterFunctions.put(
			"assigneeActionId", KaleoTaskAssignment::getAssigneeActionId);
		attributeSetterBiConsumers.put(
			"assigneeActionId",
			(BiConsumer<KaleoTaskAssignment, String>)
				KaleoTaskAssignment::setAssigneeActionId);
		attributeGetterFunctions.put(
			"assigneeScript", KaleoTaskAssignment::getAssigneeScript);
		attributeSetterBiConsumers.put(
			"assigneeScript",
			(BiConsumer<KaleoTaskAssignment, String>)
				KaleoTaskAssignment::setAssigneeScript);
		attributeGetterFunctions.put(
			"assigneeScriptLanguage",
			KaleoTaskAssignment::getAssigneeScriptLanguage);
		attributeSetterBiConsumers.put(
			"assigneeScriptLanguage",
			(BiConsumer<KaleoTaskAssignment, String>)
				KaleoTaskAssignment::setAssigneeScriptLanguage);
		attributeGetterFunctions.put(
			"assigneeScriptRequiredContexts",
			KaleoTaskAssignment::getAssigneeScriptRequiredContexts);
		attributeSetterBiConsumers.put(
			"assigneeScriptRequiredContexts",
			(BiConsumer<KaleoTaskAssignment, String>)
				KaleoTaskAssignment::setAssigneeScriptRequiredContexts);

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
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getKaleoTaskAssignmentId() {
		return _kaleoTaskAssignmentId;
	}

	@Override
	public void setKaleoTaskAssignmentId(long kaleoTaskAssignmentId) {
		_columnBitmask = -1L;

		_kaleoTaskAssignmentId = kaleoTaskAssignmentId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

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
	public String getKaleoClassName() {
		if (_kaleoClassName == null) {
			return "";
		}
		else {
			return _kaleoClassName;
		}
	}

	@Override
	public void setKaleoClassName(String kaleoClassName) {
		_columnBitmask |= KALEOCLASSNAME_COLUMN_BITMASK;

		if (_originalKaleoClassName == null) {
			_originalKaleoClassName = _kaleoClassName;
		}

		_kaleoClassName = kaleoClassName;
	}

	public String getOriginalKaleoClassName() {
		return GetterUtil.getString(_originalKaleoClassName);
	}

	@Override
	public long getKaleoClassPK() {
		return _kaleoClassPK;
	}

	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		_columnBitmask |= KALEOCLASSPK_COLUMN_BITMASK;

		if (!_setOriginalKaleoClassPK) {
			_setOriginalKaleoClassPK = true;

			_originalKaleoClassPK = _kaleoClassPK;
		}

		_kaleoClassPK = kaleoClassPK;
	}

	public long getOriginalKaleoClassPK() {
		return _originalKaleoClassPK;
	}

	@Override
	public long getKaleoDefinitionId() {
		return _kaleoDefinitionId;
	}

	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		_kaleoDefinitionId = kaleoDefinitionId;
	}

	@Override
	public long getKaleoDefinitionVersionId() {
		return _kaleoDefinitionVersionId;
	}

	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		_columnBitmask |= KALEODEFINITIONVERSIONID_COLUMN_BITMASK;

		if (!_setOriginalKaleoDefinitionVersionId) {
			_setOriginalKaleoDefinitionVersionId = true;

			_originalKaleoDefinitionVersionId = _kaleoDefinitionVersionId;
		}

		_kaleoDefinitionVersionId = kaleoDefinitionVersionId;
	}

	public long getOriginalKaleoDefinitionVersionId() {
		return _originalKaleoDefinitionVersionId;
	}

	@Override
	public long getKaleoNodeId() {
		return _kaleoNodeId;
	}

	@Override
	public void setKaleoNodeId(long kaleoNodeId) {
		_kaleoNodeId = kaleoNodeId;
	}

	@Override
	public String getAssigneeClassName() {
		if (_assigneeClassName == null) {
			return "";
		}
		else {
			return _assigneeClassName;
		}
	}

	@Override
	public void setAssigneeClassName(String assigneeClassName) {
		_columnBitmask |= ASSIGNEECLASSNAME_COLUMN_BITMASK;

		if (_originalAssigneeClassName == null) {
			_originalAssigneeClassName = _assigneeClassName;
		}

		_assigneeClassName = assigneeClassName;
	}

	public String getOriginalAssigneeClassName() {
		return GetterUtil.getString(_originalAssigneeClassName);
	}

	@Override
	public long getAssigneeClassPK() {
		return _assigneeClassPK;
	}

	@Override
	public void setAssigneeClassPK(long assigneeClassPK) {
		_assigneeClassPK = assigneeClassPK;
	}

	@Override
	public String getAssigneeActionId() {
		if (_assigneeActionId == null) {
			return "";
		}
		else {
			return _assigneeActionId;
		}
	}

	@Override
	public void setAssigneeActionId(String assigneeActionId) {
		_assigneeActionId = assigneeActionId;
	}

	@Override
	public String getAssigneeScript() {
		if (_assigneeScript == null) {
			return "";
		}
		else {
			return _assigneeScript;
		}
	}

	@Override
	public void setAssigneeScript(String assigneeScript) {
		_assigneeScript = assigneeScript;
	}

	@Override
	public String getAssigneeScriptLanguage() {
		if (_assigneeScriptLanguage == null) {
			return "";
		}
		else {
			return _assigneeScriptLanguage;
		}
	}

	@Override
	public void setAssigneeScriptLanguage(String assigneeScriptLanguage) {
		_assigneeScriptLanguage = assigneeScriptLanguage;
	}

	@Override
	public String getAssigneeScriptRequiredContexts() {
		if (_assigneeScriptRequiredContexts == null) {
			return "";
		}
		else {
			return _assigneeScriptRequiredContexts;
		}
	}

	@Override
	public void setAssigneeScriptRequiredContexts(
		String assigneeScriptRequiredContexts) {

		_assigneeScriptRequiredContexts = assigneeScriptRequiredContexts;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), KaleoTaskAssignment.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KaleoTaskAssignment toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, KaleoTaskAssignment>
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
		KaleoTaskAssignmentImpl kaleoTaskAssignmentImpl =
			new KaleoTaskAssignmentImpl();

		kaleoTaskAssignmentImpl.setMvccVersion(getMvccVersion());
		kaleoTaskAssignmentImpl.setKaleoTaskAssignmentId(
			getKaleoTaskAssignmentId());
		kaleoTaskAssignmentImpl.setGroupId(getGroupId());
		kaleoTaskAssignmentImpl.setCompanyId(getCompanyId());
		kaleoTaskAssignmentImpl.setUserId(getUserId());
		kaleoTaskAssignmentImpl.setUserName(getUserName());
		kaleoTaskAssignmentImpl.setCreateDate(getCreateDate());
		kaleoTaskAssignmentImpl.setModifiedDate(getModifiedDate());
		kaleoTaskAssignmentImpl.setKaleoClassName(getKaleoClassName());
		kaleoTaskAssignmentImpl.setKaleoClassPK(getKaleoClassPK());
		kaleoTaskAssignmentImpl.setKaleoDefinitionId(getKaleoDefinitionId());
		kaleoTaskAssignmentImpl.setKaleoDefinitionVersionId(
			getKaleoDefinitionVersionId());
		kaleoTaskAssignmentImpl.setKaleoNodeId(getKaleoNodeId());
		kaleoTaskAssignmentImpl.setAssigneeClassName(getAssigneeClassName());
		kaleoTaskAssignmentImpl.setAssigneeClassPK(getAssigneeClassPK());
		kaleoTaskAssignmentImpl.setAssigneeActionId(getAssigneeActionId());
		kaleoTaskAssignmentImpl.setAssigneeScript(getAssigneeScript());
		kaleoTaskAssignmentImpl.setAssigneeScriptLanguage(
			getAssigneeScriptLanguage());
		kaleoTaskAssignmentImpl.setAssigneeScriptRequiredContexts(
			getAssigneeScriptRequiredContexts());

		kaleoTaskAssignmentImpl.resetOriginalValues();

		return kaleoTaskAssignmentImpl;
	}

	@Override
	public int compareTo(KaleoTaskAssignment kaleoTaskAssignment) {
		int value = 0;

		if (getKaleoTaskAssignmentId() <
				kaleoTaskAssignment.getKaleoTaskAssignmentId()) {

			value = -1;
		}
		else if (getKaleoTaskAssignmentId() >
					kaleoTaskAssignment.getKaleoTaskAssignmentId()) {

			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoTaskAssignment)) {
			return false;
		}

		KaleoTaskAssignment kaleoTaskAssignment = (KaleoTaskAssignment)obj;

		long primaryKey = kaleoTaskAssignment.getPrimaryKey();

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
		KaleoTaskAssignmentModelImpl kaleoTaskAssignmentModelImpl = this;

		kaleoTaskAssignmentModelImpl._originalCompanyId =
			kaleoTaskAssignmentModelImpl._companyId;

		kaleoTaskAssignmentModelImpl._setOriginalCompanyId = false;

		kaleoTaskAssignmentModelImpl._setModifiedDate = false;

		kaleoTaskAssignmentModelImpl._originalKaleoClassName =
			kaleoTaskAssignmentModelImpl._kaleoClassName;

		kaleoTaskAssignmentModelImpl._originalKaleoClassPK =
			kaleoTaskAssignmentModelImpl._kaleoClassPK;

		kaleoTaskAssignmentModelImpl._setOriginalKaleoClassPK = false;

		kaleoTaskAssignmentModelImpl._originalKaleoDefinitionVersionId =
			kaleoTaskAssignmentModelImpl._kaleoDefinitionVersionId;

		kaleoTaskAssignmentModelImpl._setOriginalKaleoDefinitionVersionId =
			false;

		kaleoTaskAssignmentModelImpl._originalAssigneeClassName =
			kaleoTaskAssignmentModelImpl._assigneeClassName;

		kaleoTaskAssignmentModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<KaleoTaskAssignment> toCacheModel() {
		KaleoTaskAssignmentCacheModel kaleoTaskAssignmentCacheModel =
			new KaleoTaskAssignmentCacheModel();

		kaleoTaskAssignmentCacheModel.mvccVersion = getMvccVersion();

		kaleoTaskAssignmentCacheModel.kaleoTaskAssignmentId =
			getKaleoTaskAssignmentId();

		kaleoTaskAssignmentCacheModel.groupId = getGroupId();

		kaleoTaskAssignmentCacheModel.companyId = getCompanyId();

		kaleoTaskAssignmentCacheModel.userId = getUserId();

		kaleoTaskAssignmentCacheModel.userName = getUserName();

		String userName = kaleoTaskAssignmentCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			kaleoTaskAssignmentCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			kaleoTaskAssignmentCacheModel.createDate = createDate.getTime();
		}
		else {
			kaleoTaskAssignmentCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			kaleoTaskAssignmentCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			kaleoTaskAssignmentCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		kaleoTaskAssignmentCacheModel.kaleoClassName = getKaleoClassName();

		String kaleoClassName = kaleoTaskAssignmentCacheModel.kaleoClassName;

		if ((kaleoClassName != null) && (kaleoClassName.length() == 0)) {
			kaleoTaskAssignmentCacheModel.kaleoClassName = null;
		}

		kaleoTaskAssignmentCacheModel.kaleoClassPK = getKaleoClassPK();

		kaleoTaskAssignmentCacheModel.kaleoDefinitionId =
			getKaleoDefinitionId();

		kaleoTaskAssignmentCacheModel.kaleoDefinitionVersionId =
			getKaleoDefinitionVersionId();

		kaleoTaskAssignmentCacheModel.kaleoNodeId = getKaleoNodeId();

		kaleoTaskAssignmentCacheModel.assigneeClassName =
			getAssigneeClassName();

		String assigneeClassName =
			kaleoTaskAssignmentCacheModel.assigneeClassName;

		if ((assigneeClassName != null) && (assigneeClassName.length() == 0)) {
			kaleoTaskAssignmentCacheModel.assigneeClassName = null;
		}

		kaleoTaskAssignmentCacheModel.assigneeClassPK = getAssigneeClassPK();

		kaleoTaskAssignmentCacheModel.assigneeActionId = getAssigneeActionId();

		String assigneeActionId =
			kaleoTaskAssignmentCacheModel.assigneeActionId;

		if ((assigneeActionId != null) && (assigneeActionId.length() == 0)) {
			kaleoTaskAssignmentCacheModel.assigneeActionId = null;
		}

		kaleoTaskAssignmentCacheModel.assigneeScript = getAssigneeScript();

		String assigneeScript = kaleoTaskAssignmentCacheModel.assigneeScript;

		if ((assigneeScript != null) && (assigneeScript.length() == 0)) {
			kaleoTaskAssignmentCacheModel.assigneeScript = null;
		}

		kaleoTaskAssignmentCacheModel.assigneeScriptLanguage =
			getAssigneeScriptLanguage();

		String assigneeScriptLanguage =
			kaleoTaskAssignmentCacheModel.assigneeScriptLanguage;

		if ((assigneeScriptLanguage != null) &&
			(assigneeScriptLanguage.length() == 0)) {

			kaleoTaskAssignmentCacheModel.assigneeScriptLanguage = null;
		}

		kaleoTaskAssignmentCacheModel.assigneeScriptRequiredContexts =
			getAssigneeScriptRequiredContexts();

		String assigneeScriptRequiredContexts =
			kaleoTaskAssignmentCacheModel.assigneeScriptRequiredContexts;

		if ((assigneeScriptRequiredContexts != null) &&
			(assigneeScriptRequiredContexts.length() == 0)) {

			kaleoTaskAssignmentCacheModel.assigneeScriptRequiredContexts = null;
		}

		return kaleoTaskAssignmentCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KaleoTaskAssignment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KaleoTaskAssignment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskAssignment, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((KaleoTaskAssignment)this));
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
		Map<String, Function<KaleoTaskAssignment, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<KaleoTaskAssignment, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskAssignment, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((KaleoTaskAssignment)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, KaleoTaskAssignment>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private long _kaleoTaskAssignmentId;
	private long _groupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _kaleoClassName;
	private String _originalKaleoClassName;
	private long _kaleoClassPK;
	private long _originalKaleoClassPK;
	private boolean _setOriginalKaleoClassPK;
	private long _kaleoDefinitionId;
	private long _kaleoDefinitionVersionId;
	private long _originalKaleoDefinitionVersionId;
	private boolean _setOriginalKaleoDefinitionVersionId;
	private long _kaleoNodeId;
	private String _assigneeClassName;
	private String _originalAssigneeClassName;
	private long _assigneeClassPK;
	private String _assigneeActionId;
	private String _assigneeScript;
	private String _assigneeScriptLanguage;
	private String _assigneeScriptRequiredContexts;
	private long _columnBitmask;
	private KaleoTaskAssignment _escapedModel;

}