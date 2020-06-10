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
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerModel;

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
 * The base model implementation for the KaleoTimer service. Represents a row in the &quot;KaleoTimer&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>KaleoTimerModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoTimerImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTimerImpl
 * @generated
 */
public class KaleoTimerModelImpl
	extends BaseModelImpl<KaleoTimer> implements KaleoTimerModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo timer model instance should use the <code>KaleoTimer</code> interface instead.
	 */
	public static final String TABLE_NAME = "KaleoTimer";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"kaleoTimerId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"kaleoClassName", Types.VARCHAR}, {"kaleoClassPK", Types.BIGINT},
		{"kaleoDefinitionId", Types.BIGINT},
		{"kaleoDefinitionVersionId", Types.BIGINT}, {"name", Types.VARCHAR},
		{"blocking", Types.BOOLEAN}, {"description", Types.VARCHAR},
		{"duration", Types.DOUBLE}, {"scale", Types.VARCHAR},
		{"recurrenceDuration", Types.DOUBLE}, {"recurrenceScale", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTimerId", Types.BIGINT);
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
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("blocking", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("duration", Types.DOUBLE);
		TABLE_COLUMNS_MAP.put("scale", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("recurrenceDuration", Types.DOUBLE);
		TABLE_COLUMNS_MAP.put("recurrenceScale", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KaleoTimer (mvccVersion LONG default 0 not null,kaleoTimerId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionId LONG,kaleoDefinitionVersionId LONG,name VARCHAR(75) null,blocking BOOLEAN,description STRING null,duration DOUBLE,scale VARCHAR(75) null,recurrenceDuration DOUBLE,recurrenceScale VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTimer";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kaleoTimer.kaleoTimerId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KaleoTimer.kaleoTimerId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long BLOCKING_COLUMN_BITMASK = 1L;

	public static final long KALEOCLASSNAME_COLUMN_BITMASK = 2L;

	public static final long KALEOCLASSPK_COLUMN_BITMASK = 4L;

	public static final long KALEOTIMERID_COLUMN_BITMASK = 8L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public KaleoTimerModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kaleoTimerId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKaleoTimerId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoTimerId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoTimer.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoTimer.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KaleoTimer, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KaleoTimer, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTimer, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((KaleoTimer)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KaleoTimer, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KaleoTimer, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KaleoTimer)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KaleoTimer, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KaleoTimer, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, KaleoTimer>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			KaleoTimer.class.getClassLoader(), KaleoTimer.class,
			ModelWrapper.class);

		try {
			Constructor<KaleoTimer> constructor =
				(Constructor<KaleoTimer>)proxyClass.getConstructor(
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

	private static final Map<String, Function<KaleoTimer, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<KaleoTimer, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<KaleoTimer, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<KaleoTimer, Object>>();
		Map<String, BiConsumer<KaleoTimer, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<KaleoTimer, ?>>();

		attributeGetterFunctions.put("mvccVersion", KaleoTimer::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<KaleoTimer, Long>)KaleoTimer::setMvccVersion);
		attributeGetterFunctions.put(
			"kaleoTimerId", KaleoTimer::getKaleoTimerId);
		attributeSetterBiConsumers.put(
			"kaleoTimerId",
			(BiConsumer<KaleoTimer, Long>)KaleoTimer::setKaleoTimerId);
		attributeGetterFunctions.put("groupId", KaleoTimer::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId", (BiConsumer<KaleoTimer, Long>)KaleoTimer::setGroupId);
		attributeGetterFunctions.put("companyId", KaleoTimer::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<KaleoTimer, Long>)KaleoTimer::setCompanyId);
		attributeGetterFunctions.put("userId", KaleoTimer::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<KaleoTimer, Long>)KaleoTimer::setUserId);
		attributeGetterFunctions.put("userName", KaleoTimer::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<KaleoTimer, String>)KaleoTimer::setUserName);
		attributeGetterFunctions.put("createDate", KaleoTimer::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<KaleoTimer, Date>)KaleoTimer::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", KaleoTimer::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<KaleoTimer, Date>)KaleoTimer::setModifiedDate);
		attributeGetterFunctions.put(
			"kaleoClassName", KaleoTimer::getKaleoClassName);
		attributeSetterBiConsumers.put(
			"kaleoClassName",
			(BiConsumer<KaleoTimer, String>)KaleoTimer::setKaleoClassName);
		attributeGetterFunctions.put(
			"kaleoClassPK", KaleoTimer::getKaleoClassPK);
		attributeSetterBiConsumers.put(
			"kaleoClassPK",
			(BiConsumer<KaleoTimer, Long>)KaleoTimer::setKaleoClassPK);
		attributeGetterFunctions.put(
			"kaleoDefinitionId", KaleoTimer::getKaleoDefinitionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionId",
			(BiConsumer<KaleoTimer, Long>)KaleoTimer::setKaleoDefinitionId);
		attributeGetterFunctions.put(
			"kaleoDefinitionVersionId",
			KaleoTimer::getKaleoDefinitionVersionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionVersionId",
			(BiConsumer<KaleoTimer, Long>)
				KaleoTimer::setKaleoDefinitionVersionId);
		attributeGetterFunctions.put("name", KaleoTimer::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<KaleoTimer, String>)KaleoTimer::setName);
		attributeGetterFunctions.put("blocking", KaleoTimer::getBlocking);
		attributeSetterBiConsumers.put(
			"blocking",
			(BiConsumer<KaleoTimer, Boolean>)KaleoTimer::setBlocking);
		attributeGetterFunctions.put("description", KaleoTimer::getDescription);
		attributeSetterBiConsumers.put(
			"description",
			(BiConsumer<KaleoTimer, String>)KaleoTimer::setDescription);
		attributeGetterFunctions.put("duration", KaleoTimer::getDuration);
		attributeSetterBiConsumers.put(
			"duration",
			(BiConsumer<KaleoTimer, Double>)KaleoTimer::setDuration);
		attributeGetterFunctions.put("scale", KaleoTimer::getScale);
		attributeSetterBiConsumers.put(
			"scale", (BiConsumer<KaleoTimer, String>)KaleoTimer::setScale);
		attributeGetterFunctions.put(
			"recurrenceDuration", KaleoTimer::getRecurrenceDuration);
		attributeSetterBiConsumers.put(
			"recurrenceDuration",
			(BiConsumer<KaleoTimer, Double>)KaleoTimer::setRecurrenceDuration);
		attributeGetterFunctions.put(
			"recurrenceScale", KaleoTimer::getRecurrenceScale);
		attributeSetterBiConsumers.put(
			"recurrenceScale",
			(BiConsumer<KaleoTimer, String>)KaleoTimer::setRecurrenceScale);

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
	public long getKaleoTimerId() {
		return _kaleoTimerId;
	}

	@Override
	public void setKaleoTimerId(long kaleoTimerId) {
		_columnBitmask = -1L;

		_kaleoTimerId = kaleoTimerId;
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
		_kaleoDefinitionVersionId = kaleoDefinitionVersionId;
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
		_name = name;
	}

	@Override
	public boolean getBlocking() {
		return _blocking;
	}

	@Override
	public boolean isBlocking() {
		return _blocking;
	}

	@Override
	public void setBlocking(boolean blocking) {
		_columnBitmask |= BLOCKING_COLUMN_BITMASK;

		if (!_setOriginalBlocking) {
			_setOriginalBlocking = true;

			_originalBlocking = _blocking;
		}

		_blocking = blocking;
	}

	public boolean getOriginalBlocking() {
		return _originalBlocking;
	}

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

	@Override
	public double getDuration() {
		return _duration;
	}

	@Override
	public void setDuration(double duration) {
		_duration = duration;
	}

	@Override
	public String getScale() {
		if (_scale == null) {
			return "";
		}
		else {
			return _scale;
		}
	}

	@Override
	public void setScale(String scale) {
		_scale = scale;
	}

	@Override
	public double getRecurrenceDuration() {
		return _recurrenceDuration;
	}

	@Override
	public void setRecurrenceDuration(double recurrenceDuration) {
		_recurrenceDuration = recurrenceDuration;
	}

	@Override
	public String getRecurrenceScale() {
		if (_recurrenceScale == null) {
			return "";
		}
		else {
			return _recurrenceScale;
		}
	}

	@Override
	public void setRecurrenceScale(String recurrenceScale) {
		_recurrenceScale = recurrenceScale;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), KaleoTimer.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KaleoTimer toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, KaleoTimer>
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
		KaleoTimerImpl kaleoTimerImpl = new KaleoTimerImpl();

		kaleoTimerImpl.setMvccVersion(getMvccVersion());
		kaleoTimerImpl.setKaleoTimerId(getKaleoTimerId());
		kaleoTimerImpl.setGroupId(getGroupId());
		kaleoTimerImpl.setCompanyId(getCompanyId());
		kaleoTimerImpl.setUserId(getUserId());
		kaleoTimerImpl.setUserName(getUserName());
		kaleoTimerImpl.setCreateDate(getCreateDate());
		kaleoTimerImpl.setModifiedDate(getModifiedDate());
		kaleoTimerImpl.setKaleoClassName(getKaleoClassName());
		kaleoTimerImpl.setKaleoClassPK(getKaleoClassPK());
		kaleoTimerImpl.setKaleoDefinitionId(getKaleoDefinitionId());
		kaleoTimerImpl.setKaleoDefinitionVersionId(
			getKaleoDefinitionVersionId());
		kaleoTimerImpl.setName(getName());
		kaleoTimerImpl.setBlocking(isBlocking());
		kaleoTimerImpl.setDescription(getDescription());
		kaleoTimerImpl.setDuration(getDuration());
		kaleoTimerImpl.setScale(getScale());
		kaleoTimerImpl.setRecurrenceDuration(getRecurrenceDuration());
		kaleoTimerImpl.setRecurrenceScale(getRecurrenceScale());

		kaleoTimerImpl.resetOriginalValues();

		return kaleoTimerImpl;
	}

	@Override
	public int compareTo(KaleoTimer kaleoTimer) {
		int value = 0;

		if (getKaleoTimerId() < kaleoTimer.getKaleoTimerId()) {
			value = -1;
		}
		else if (getKaleoTimerId() > kaleoTimer.getKaleoTimerId()) {
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

		if (!(object instanceof KaleoTimer)) {
			return false;
		}

		KaleoTimer kaleoTimer = (KaleoTimer)object;

		long primaryKey = kaleoTimer.getPrimaryKey();

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
		KaleoTimerModelImpl kaleoTimerModelImpl = this;

		kaleoTimerModelImpl._setModifiedDate = false;

		kaleoTimerModelImpl._originalKaleoClassName =
			kaleoTimerModelImpl._kaleoClassName;

		kaleoTimerModelImpl._originalKaleoClassPK =
			kaleoTimerModelImpl._kaleoClassPK;

		kaleoTimerModelImpl._setOriginalKaleoClassPK = false;

		kaleoTimerModelImpl._originalBlocking = kaleoTimerModelImpl._blocking;

		kaleoTimerModelImpl._setOriginalBlocking = false;

		kaleoTimerModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<KaleoTimer> toCacheModel() {
		KaleoTimerCacheModel kaleoTimerCacheModel = new KaleoTimerCacheModel();

		kaleoTimerCacheModel.mvccVersion = getMvccVersion();

		kaleoTimerCacheModel.kaleoTimerId = getKaleoTimerId();

		kaleoTimerCacheModel.groupId = getGroupId();

		kaleoTimerCacheModel.companyId = getCompanyId();

		kaleoTimerCacheModel.userId = getUserId();

		kaleoTimerCacheModel.userName = getUserName();

		String userName = kaleoTimerCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			kaleoTimerCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			kaleoTimerCacheModel.createDate = createDate.getTime();
		}
		else {
			kaleoTimerCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			kaleoTimerCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			kaleoTimerCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		kaleoTimerCacheModel.kaleoClassName = getKaleoClassName();

		String kaleoClassName = kaleoTimerCacheModel.kaleoClassName;

		if ((kaleoClassName != null) && (kaleoClassName.length() == 0)) {
			kaleoTimerCacheModel.kaleoClassName = null;
		}

		kaleoTimerCacheModel.kaleoClassPK = getKaleoClassPK();

		kaleoTimerCacheModel.kaleoDefinitionId = getKaleoDefinitionId();

		kaleoTimerCacheModel.kaleoDefinitionVersionId =
			getKaleoDefinitionVersionId();

		kaleoTimerCacheModel.name = getName();

		String name = kaleoTimerCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			kaleoTimerCacheModel.name = null;
		}

		kaleoTimerCacheModel.blocking = isBlocking();

		kaleoTimerCacheModel.description = getDescription();

		String description = kaleoTimerCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			kaleoTimerCacheModel.description = null;
		}

		kaleoTimerCacheModel.duration = getDuration();

		kaleoTimerCacheModel.scale = getScale();

		String scale = kaleoTimerCacheModel.scale;

		if ((scale != null) && (scale.length() == 0)) {
			kaleoTimerCacheModel.scale = null;
		}

		kaleoTimerCacheModel.recurrenceDuration = getRecurrenceDuration();

		kaleoTimerCacheModel.recurrenceScale = getRecurrenceScale();

		String recurrenceScale = kaleoTimerCacheModel.recurrenceScale;

		if ((recurrenceScale != null) && (recurrenceScale.length() == 0)) {
			kaleoTimerCacheModel.recurrenceScale = null;
		}

		return kaleoTimerCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KaleoTimer, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KaleoTimer, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTimer, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((KaleoTimer)this));
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
		Map<String, Function<KaleoTimer, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<KaleoTimer, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTimer, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((KaleoTimer)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, KaleoTimer>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private long _kaleoTimerId;
	private long _groupId;
	private long _companyId;
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
	private String _name;
	private boolean _blocking;
	private boolean _originalBlocking;
	private boolean _setOriginalBlocking;
	private String _description;
	private double _duration;
	private String _scale;
	private double _recurrenceDuration;
	private String _recurrenceScale;
	private long _columnBitmask;
	private KaleoTimer _escapedModel;

}