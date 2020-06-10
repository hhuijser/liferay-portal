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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstanceModel;

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
 * The base model implementation for the KaleoTaskAssignmentInstance service. Represents a row in the &quot;KaleoTaskAssignmentInstance&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>KaleoTaskAssignmentInstanceModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoTaskAssignmentInstanceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignmentInstanceImpl
 * @generated
 */
public class KaleoTaskAssignmentInstanceModelImpl
	extends BaseModelImpl<KaleoTaskAssignmentInstance>
	implements KaleoTaskAssignmentInstanceModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo task assignment instance model instance should use the <code>KaleoTaskAssignmentInstance</code> interface instead.
	 */
	public static final String TABLE_NAME = "KaleoTaskAssignmentInstance";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"kaleoTaskAssignmentInstanceId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionId", Types.BIGINT},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoInstanceId", Types.BIGINT},
		{"kaleoInstanceTokenId", Types.BIGINT},
		{"kaleoTaskInstanceTokenId", Types.BIGINT},
		{"kaleoTaskId", Types.BIGINT}, {"kaleoTaskName", Types.VARCHAR},
		{"assigneeClassName", Types.VARCHAR}, {"assigneeClassPK", Types.BIGINT},
		{"completed", Types.BOOLEAN}, {"completionDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskAssignmentInstanceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("kaleoDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoInstanceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoInstanceTokenId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskInstanceTokenId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assigneeClassName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("assigneeClassPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("completed", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("completionDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KaleoTaskAssignmentInstance (mvccVersion LONG default 0 not null,kaleoTaskAssignmentInstanceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionId LONG,kaleoDefinitionVersionId LONG,kaleoInstanceId LONG,kaleoInstanceTokenId LONG,kaleoTaskInstanceTokenId LONG,kaleoTaskId LONG,kaleoTaskName VARCHAR(200) null,assigneeClassName VARCHAR(200) null,assigneeClassPK LONG,completed BOOLEAN,completionDate DATE null)";

	public static final String TABLE_SQL_DROP =
		"drop table KaleoTaskAssignmentInstance";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kaleoTaskAssignmentInstance.kaleoTaskAssignmentInstanceId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KaleoTaskAssignmentInstance.kaleoTaskAssignmentInstanceId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long ASSIGNEECLASSNAME_COLUMN_BITMASK = 1L;

	public static final long ASSIGNEECLASSPK_COLUMN_BITMASK = 2L;

	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	public static final long GROUPID_COLUMN_BITMASK = 8L;

	public static final long KALEODEFINITIONVERSIONID_COLUMN_BITMASK = 16L;

	public static final long KALEOINSTANCEID_COLUMN_BITMASK = 32L;

	public static final long KALEOTASKINSTANCETOKENID_COLUMN_BITMASK = 64L;

	public static final long KALEOTASKASSIGNMENTINSTANCEID_COLUMN_BITMASK =
		128L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public KaleoTaskAssignmentInstanceModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kaleoTaskAssignmentInstanceId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKaleoTaskAssignmentInstanceId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoTaskAssignmentInstanceId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoTaskAssignmentInstance.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoTaskAssignmentInstance.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KaleoTaskAssignmentInstance, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KaleoTaskAssignmentInstance, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskAssignmentInstance, Object>
				attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply(
					(KaleoTaskAssignmentInstance)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KaleoTaskAssignmentInstance, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KaleoTaskAssignmentInstance, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KaleoTaskAssignmentInstance)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KaleoTaskAssignmentInstance, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KaleoTaskAssignmentInstance, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, KaleoTaskAssignmentInstance>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			KaleoTaskAssignmentInstance.class.getClassLoader(),
			KaleoTaskAssignmentInstance.class, ModelWrapper.class);

		try {
			Constructor<KaleoTaskAssignmentInstance> constructor =
				(Constructor<KaleoTaskAssignmentInstance>)
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

	private static final Map
		<String, Function<KaleoTaskAssignmentInstance, Object>>
			_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<KaleoTaskAssignmentInstance, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<KaleoTaskAssignmentInstance, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<KaleoTaskAssignmentInstance, Object>>();
		Map<String, BiConsumer<KaleoTaskAssignmentInstance, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<KaleoTaskAssignmentInstance, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", KaleoTaskAssignmentInstance::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setMvccVersion);
		attributeGetterFunctions.put(
			"kaleoTaskAssignmentInstanceId",
			KaleoTaskAssignmentInstance::getKaleoTaskAssignmentInstanceId);
		attributeSetterBiConsumers.put(
			"kaleoTaskAssignmentInstanceId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setKaleoTaskAssignmentInstanceId);
		attributeGetterFunctions.put(
			"groupId", KaleoTaskAssignmentInstance::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setGroupId);
		attributeGetterFunctions.put(
			"companyId", KaleoTaskAssignmentInstance::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setCompanyId);
		attributeGetterFunctions.put(
			"userId", KaleoTaskAssignmentInstance::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setUserId);
		attributeGetterFunctions.put(
			"userName", KaleoTaskAssignmentInstance::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<KaleoTaskAssignmentInstance, String>)
				KaleoTaskAssignmentInstance::setUserName);
		attributeGetterFunctions.put(
			"createDate", KaleoTaskAssignmentInstance::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<KaleoTaskAssignmentInstance, Date>)
				KaleoTaskAssignmentInstance::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", KaleoTaskAssignmentInstance::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<KaleoTaskAssignmentInstance, Date>)
				KaleoTaskAssignmentInstance::setModifiedDate);
		attributeGetterFunctions.put(
			"kaleoDefinitionId",
			KaleoTaskAssignmentInstance::getKaleoDefinitionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setKaleoDefinitionId);
		attributeGetterFunctions.put(
			"kaleoDefinitionVersionId",
			KaleoTaskAssignmentInstance::getKaleoDefinitionVersionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionVersionId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setKaleoDefinitionVersionId);
		attributeGetterFunctions.put(
			"kaleoInstanceId", KaleoTaskAssignmentInstance::getKaleoInstanceId);
		attributeSetterBiConsumers.put(
			"kaleoInstanceId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setKaleoInstanceId);
		attributeGetterFunctions.put(
			"kaleoInstanceTokenId",
			KaleoTaskAssignmentInstance::getKaleoInstanceTokenId);
		attributeSetterBiConsumers.put(
			"kaleoInstanceTokenId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setKaleoInstanceTokenId);
		attributeGetterFunctions.put(
			"kaleoTaskInstanceTokenId",
			KaleoTaskAssignmentInstance::getKaleoTaskInstanceTokenId);
		attributeSetterBiConsumers.put(
			"kaleoTaskInstanceTokenId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setKaleoTaskInstanceTokenId);
		attributeGetterFunctions.put(
			"kaleoTaskId", KaleoTaskAssignmentInstance::getKaleoTaskId);
		attributeSetterBiConsumers.put(
			"kaleoTaskId",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setKaleoTaskId);
		attributeGetterFunctions.put(
			"kaleoTaskName", KaleoTaskAssignmentInstance::getKaleoTaskName);
		attributeSetterBiConsumers.put(
			"kaleoTaskName",
			(BiConsumer<KaleoTaskAssignmentInstance, String>)
				KaleoTaskAssignmentInstance::setKaleoTaskName);
		attributeGetterFunctions.put(
			"assigneeClassName",
			KaleoTaskAssignmentInstance::getAssigneeClassName);
		attributeSetterBiConsumers.put(
			"assigneeClassName",
			(BiConsumer<KaleoTaskAssignmentInstance, String>)
				KaleoTaskAssignmentInstance::setAssigneeClassName);
		attributeGetterFunctions.put(
			"assigneeClassPK", KaleoTaskAssignmentInstance::getAssigneeClassPK);
		attributeSetterBiConsumers.put(
			"assigneeClassPK",
			(BiConsumer<KaleoTaskAssignmentInstance, Long>)
				KaleoTaskAssignmentInstance::setAssigneeClassPK);
		attributeGetterFunctions.put(
			"completed", KaleoTaskAssignmentInstance::getCompleted);
		attributeSetterBiConsumers.put(
			"completed",
			(BiConsumer<KaleoTaskAssignmentInstance, Boolean>)
				KaleoTaskAssignmentInstance::setCompleted);
		attributeGetterFunctions.put(
			"completionDate", KaleoTaskAssignmentInstance::getCompletionDate);
		attributeSetterBiConsumers.put(
			"completionDate",
			(BiConsumer<KaleoTaskAssignmentInstance, Date>)
				KaleoTaskAssignmentInstance::setCompletionDate);

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
	public long getKaleoTaskAssignmentInstanceId() {
		return _kaleoTaskAssignmentInstanceId;
	}

	@Override
	public void setKaleoTaskAssignmentInstanceId(
		long kaleoTaskAssignmentInstanceId) {

		_columnBitmask = -1L;

		_kaleoTaskAssignmentInstanceId = kaleoTaskAssignmentInstanceId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
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
	public long getKaleoInstanceId() {
		return _kaleoInstanceId;
	}

	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		_columnBitmask |= KALEOINSTANCEID_COLUMN_BITMASK;

		if (!_setOriginalKaleoInstanceId) {
			_setOriginalKaleoInstanceId = true;

			_originalKaleoInstanceId = _kaleoInstanceId;
		}

		_kaleoInstanceId = kaleoInstanceId;
	}

	public long getOriginalKaleoInstanceId() {
		return _originalKaleoInstanceId;
	}

	@Override
	public long getKaleoInstanceTokenId() {
		return _kaleoInstanceTokenId;
	}

	@Override
	public void setKaleoInstanceTokenId(long kaleoInstanceTokenId) {
		_kaleoInstanceTokenId = kaleoInstanceTokenId;
	}

	@Override
	public long getKaleoTaskInstanceTokenId() {
		return _kaleoTaskInstanceTokenId;
	}

	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		_columnBitmask |= KALEOTASKINSTANCETOKENID_COLUMN_BITMASK;

		if (!_setOriginalKaleoTaskInstanceTokenId) {
			_setOriginalKaleoTaskInstanceTokenId = true;

			_originalKaleoTaskInstanceTokenId = _kaleoTaskInstanceTokenId;
		}

		_kaleoTaskInstanceTokenId = kaleoTaskInstanceTokenId;
	}

	public long getOriginalKaleoTaskInstanceTokenId() {
		return _originalKaleoTaskInstanceTokenId;
	}

	@Override
	public long getKaleoTaskId() {
		return _kaleoTaskId;
	}

	@Override
	public void setKaleoTaskId(long kaleoTaskId) {
		_kaleoTaskId = kaleoTaskId;
	}

	@Override
	public String getKaleoTaskName() {
		if (_kaleoTaskName == null) {
			return "";
		}
		else {
			return _kaleoTaskName;
		}
	}

	@Override
	public void setKaleoTaskName(String kaleoTaskName) {
		_kaleoTaskName = kaleoTaskName;
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
		_columnBitmask |= ASSIGNEECLASSPK_COLUMN_BITMASK;

		if (!_setOriginalAssigneeClassPK) {
			_setOriginalAssigneeClassPK = true;

			_originalAssigneeClassPK = _assigneeClassPK;
		}

		_assigneeClassPK = assigneeClassPK;
	}

	public long getOriginalAssigneeClassPK() {
		return _originalAssigneeClassPK;
	}

	@Override
	public boolean getCompleted() {
		return _completed;
	}

	@Override
	public boolean isCompleted() {
		return _completed;
	}

	@Override
	public void setCompleted(boolean completed) {
		_completed = completed;
	}

	@Override
	public Date getCompletionDate() {
		return _completionDate;
	}

	@Override
	public void setCompletionDate(Date completionDate) {
		_completionDate = completionDate;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), KaleoTaskAssignmentInstance.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KaleoTaskAssignmentInstance toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, KaleoTaskAssignmentInstance>
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
		KaleoTaskAssignmentInstanceImpl kaleoTaskAssignmentInstanceImpl =
			new KaleoTaskAssignmentInstanceImpl();

		kaleoTaskAssignmentInstanceImpl.setMvccVersion(getMvccVersion());
		kaleoTaskAssignmentInstanceImpl.setKaleoTaskAssignmentInstanceId(
			getKaleoTaskAssignmentInstanceId());
		kaleoTaskAssignmentInstanceImpl.setGroupId(getGroupId());
		kaleoTaskAssignmentInstanceImpl.setCompanyId(getCompanyId());
		kaleoTaskAssignmentInstanceImpl.setUserId(getUserId());
		kaleoTaskAssignmentInstanceImpl.setUserName(getUserName());
		kaleoTaskAssignmentInstanceImpl.setCreateDate(getCreateDate());
		kaleoTaskAssignmentInstanceImpl.setModifiedDate(getModifiedDate());
		kaleoTaskAssignmentInstanceImpl.setKaleoDefinitionId(
			getKaleoDefinitionId());
		kaleoTaskAssignmentInstanceImpl.setKaleoDefinitionVersionId(
			getKaleoDefinitionVersionId());
		kaleoTaskAssignmentInstanceImpl.setKaleoInstanceId(
			getKaleoInstanceId());
		kaleoTaskAssignmentInstanceImpl.setKaleoInstanceTokenId(
			getKaleoInstanceTokenId());
		kaleoTaskAssignmentInstanceImpl.setKaleoTaskInstanceTokenId(
			getKaleoTaskInstanceTokenId());
		kaleoTaskAssignmentInstanceImpl.setKaleoTaskId(getKaleoTaskId());
		kaleoTaskAssignmentInstanceImpl.setKaleoTaskName(getKaleoTaskName());
		kaleoTaskAssignmentInstanceImpl.setAssigneeClassName(
			getAssigneeClassName());
		kaleoTaskAssignmentInstanceImpl.setAssigneeClassPK(
			getAssigneeClassPK());
		kaleoTaskAssignmentInstanceImpl.setCompleted(isCompleted());
		kaleoTaskAssignmentInstanceImpl.setCompletionDate(getCompletionDate());

		kaleoTaskAssignmentInstanceImpl.resetOriginalValues();

		return kaleoTaskAssignmentInstanceImpl;
	}

	@Override
	public int compareTo(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		int value = 0;

		if (getKaleoTaskAssignmentInstanceId() <
				kaleoTaskAssignmentInstance.
					getKaleoTaskAssignmentInstanceId()) {

			value = -1;
		}
		else if (getKaleoTaskAssignmentInstanceId() >
					kaleoTaskAssignmentInstance.
						getKaleoTaskAssignmentInstanceId()) {

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoTaskAssignmentInstance)) {
			return false;
		}

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			(KaleoTaskAssignmentInstance)object;

		long primaryKey = kaleoTaskAssignmentInstance.getPrimaryKey();

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
		KaleoTaskAssignmentInstanceModelImpl
			kaleoTaskAssignmentInstanceModelImpl = this;

		kaleoTaskAssignmentInstanceModelImpl._originalGroupId =
			kaleoTaskAssignmentInstanceModelImpl._groupId;

		kaleoTaskAssignmentInstanceModelImpl._setOriginalGroupId = false;

		kaleoTaskAssignmentInstanceModelImpl._originalCompanyId =
			kaleoTaskAssignmentInstanceModelImpl._companyId;

		kaleoTaskAssignmentInstanceModelImpl._setOriginalCompanyId = false;

		kaleoTaskAssignmentInstanceModelImpl._setModifiedDate = false;

		kaleoTaskAssignmentInstanceModelImpl._originalKaleoDefinitionVersionId =
			kaleoTaskAssignmentInstanceModelImpl._kaleoDefinitionVersionId;

		kaleoTaskAssignmentInstanceModelImpl.
			_setOriginalKaleoDefinitionVersionId = false;

		kaleoTaskAssignmentInstanceModelImpl._originalKaleoInstanceId =
			kaleoTaskAssignmentInstanceModelImpl._kaleoInstanceId;

		kaleoTaskAssignmentInstanceModelImpl._setOriginalKaleoInstanceId =
			false;

		kaleoTaskAssignmentInstanceModelImpl._originalKaleoTaskInstanceTokenId =
			kaleoTaskAssignmentInstanceModelImpl._kaleoTaskInstanceTokenId;

		kaleoTaskAssignmentInstanceModelImpl.
			_setOriginalKaleoTaskInstanceTokenId = false;

		kaleoTaskAssignmentInstanceModelImpl._originalAssigneeClassName =
			kaleoTaskAssignmentInstanceModelImpl._assigneeClassName;

		kaleoTaskAssignmentInstanceModelImpl._originalAssigneeClassPK =
			kaleoTaskAssignmentInstanceModelImpl._assigneeClassPK;

		kaleoTaskAssignmentInstanceModelImpl._setOriginalAssigneeClassPK =
			false;

		kaleoTaskAssignmentInstanceModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<KaleoTaskAssignmentInstance> toCacheModel() {
		KaleoTaskAssignmentInstanceCacheModel
			kaleoTaskAssignmentInstanceCacheModel =
				new KaleoTaskAssignmentInstanceCacheModel();

		kaleoTaskAssignmentInstanceCacheModel.mvccVersion = getMvccVersion();

		kaleoTaskAssignmentInstanceCacheModel.kaleoTaskAssignmentInstanceId =
			getKaleoTaskAssignmentInstanceId();

		kaleoTaskAssignmentInstanceCacheModel.groupId = getGroupId();

		kaleoTaskAssignmentInstanceCacheModel.companyId = getCompanyId();

		kaleoTaskAssignmentInstanceCacheModel.userId = getUserId();

		kaleoTaskAssignmentInstanceCacheModel.userName = getUserName();

		String userName = kaleoTaskAssignmentInstanceCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			kaleoTaskAssignmentInstanceCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			kaleoTaskAssignmentInstanceCacheModel.createDate =
				createDate.getTime();
		}
		else {
			kaleoTaskAssignmentInstanceCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			kaleoTaskAssignmentInstanceCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			kaleoTaskAssignmentInstanceCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		kaleoTaskAssignmentInstanceCacheModel.kaleoDefinitionId =
			getKaleoDefinitionId();

		kaleoTaskAssignmentInstanceCacheModel.kaleoDefinitionVersionId =
			getKaleoDefinitionVersionId();

		kaleoTaskAssignmentInstanceCacheModel.kaleoInstanceId =
			getKaleoInstanceId();

		kaleoTaskAssignmentInstanceCacheModel.kaleoInstanceTokenId =
			getKaleoInstanceTokenId();

		kaleoTaskAssignmentInstanceCacheModel.kaleoTaskInstanceTokenId =
			getKaleoTaskInstanceTokenId();

		kaleoTaskAssignmentInstanceCacheModel.kaleoTaskId = getKaleoTaskId();

		kaleoTaskAssignmentInstanceCacheModel.kaleoTaskName =
			getKaleoTaskName();

		String kaleoTaskName =
			kaleoTaskAssignmentInstanceCacheModel.kaleoTaskName;

		if ((kaleoTaskName != null) && (kaleoTaskName.length() == 0)) {
			kaleoTaskAssignmentInstanceCacheModel.kaleoTaskName = null;
		}

		kaleoTaskAssignmentInstanceCacheModel.assigneeClassName =
			getAssigneeClassName();

		String assigneeClassName =
			kaleoTaskAssignmentInstanceCacheModel.assigneeClassName;

		if ((assigneeClassName != null) && (assigneeClassName.length() == 0)) {
			kaleoTaskAssignmentInstanceCacheModel.assigneeClassName = null;
		}

		kaleoTaskAssignmentInstanceCacheModel.assigneeClassPK =
			getAssigneeClassPK();

		kaleoTaskAssignmentInstanceCacheModel.completed = isCompleted();

		Date completionDate = getCompletionDate();

		if (completionDate != null) {
			kaleoTaskAssignmentInstanceCacheModel.completionDate =
				completionDate.getTime();
		}
		else {
			kaleoTaskAssignmentInstanceCacheModel.completionDate =
				Long.MIN_VALUE;
		}

		return kaleoTaskAssignmentInstanceCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KaleoTaskAssignmentInstance, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KaleoTaskAssignmentInstance, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskAssignmentInstance, Object>
				attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply(
					(KaleoTaskAssignmentInstance)this));
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
		Map<String, Function<KaleoTaskAssignmentInstance, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<KaleoTaskAssignmentInstance, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTaskAssignmentInstance, Object>
				attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply(
					(KaleoTaskAssignmentInstance)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, KaleoTaskAssignmentInstance>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private long _kaleoTaskAssignmentInstanceId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _kaleoDefinitionId;
	private long _kaleoDefinitionVersionId;
	private long _originalKaleoDefinitionVersionId;
	private boolean _setOriginalKaleoDefinitionVersionId;
	private long _kaleoInstanceId;
	private long _originalKaleoInstanceId;
	private boolean _setOriginalKaleoInstanceId;
	private long _kaleoInstanceTokenId;
	private long _kaleoTaskInstanceTokenId;
	private long _originalKaleoTaskInstanceTokenId;
	private boolean _setOriginalKaleoTaskInstanceTokenId;
	private long _kaleoTaskId;
	private String _kaleoTaskName;
	private String _assigneeClassName;
	private String _originalAssigneeClassName;
	private long _assigneeClassPK;
	private long _originalAssigneeClassPK;
	private boolean _setOriginalAssigneeClassPK;
	private boolean _completed;
	private Date _completionDate;
	private long _columnBitmask;
	private KaleoTaskAssignmentInstance _escapedModel;

}