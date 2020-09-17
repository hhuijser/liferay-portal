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

package com.liferay.layout.page.template.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelModel;
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
import com.liferay.portal.kernel.util.PortalUtil;
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
 * The base model implementation for the LayoutPageTemplateStructureRel service. Represents a row in the &quot;LayoutPageTemplateStructureRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>LayoutPageTemplateStructureRelModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LayoutPageTemplateStructureRelImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRelImpl
 * @generated
 */
public class LayoutPageTemplateStructureRelModelImpl
	extends BaseModelImpl<LayoutPageTemplateStructureRel>
	implements LayoutPageTemplateStructureRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout page template structure rel model instance should use the <code>LayoutPageTemplateStructureRel</code> interface instead.
	 */
	public static final String TABLE_NAME = "LayoutPageTemplateStructureRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"lPageTemplateStructureRelId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"layoutPageTemplateStructureId", Types.BIGINT},
		{"segmentsExperienceId", Types.BIGINT}, {"data_", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("lPageTemplateStructureRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("layoutPageTemplateStructureId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("segmentsExperienceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("data_", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table LayoutPageTemplateStructureRel (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,lPageTemplateStructureRelId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,layoutPageTemplateStructureId LONG,segmentsExperienceId LONG,data_ TEXT null,primary key (lPageTemplateStructureRelId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
		"drop table LayoutPageTemplateStructureRel";

	public static final String ORDER_BY_JPQL =
		" ORDER BY layoutPageTemplateStructureRel.layoutPageTemplateStructureRelId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY LayoutPageTemplateStructureRel.lPageTemplateStructureRelId ASC";

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
	public static final long GROUPID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long LAYOUTPAGETEMPLATESTRUCTUREID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long SEGMENTSEXPERIENCEID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long LAYOUTPAGETEMPLATESTRUCTURERELID_COLUMN_BITMASK =
		32L;

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

	public LayoutPageTemplateStructureRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _layoutPageTemplateStructureRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLayoutPageTemplateStructureRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutPageTemplateStructureRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPageTemplateStructureRel.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPageTemplateStructureRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<LayoutPageTemplateStructureRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<LayoutPageTemplateStructureRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LayoutPageTemplateStructureRel, Object>
				attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply(
					(LayoutPageTemplateStructureRel)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<LayoutPageTemplateStructureRel, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<LayoutPageTemplateStructureRel, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(LayoutPageTemplateStructureRel)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<LayoutPageTemplateStructureRel, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<LayoutPageTemplateStructureRel, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, LayoutPageTemplateStructureRel>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			LayoutPageTemplateStructureRel.class.getClassLoader(),
			LayoutPageTemplateStructureRel.class, ModelWrapper.class);

		try {
			Constructor<LayoutPageTemplateStructureRel> constructor =
				(Constructor<LayoutPageTemplateStructureRel>)
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
		<String, Function<LayoutPageTemplateStructureRel, Object>>
			_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<LayoutPageTemplateStructureRel, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<LayoutPageTemplateStructureRel, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String,
					 Function<LayoutPageTemplateStructureRel, Object>>();
		Map<String, BiConsumer<LayoutPageTemplateStructureRel, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<LayoutPageTemplateStructureRel, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", LayoutPageTemplateStructureRel::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<LayoutPageTemplateStructureRel, Long>)
				LayoutPageTemplateStructureRel::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId",
			LayoutPageTemplateStructureRel::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<LayoutPageTemplateStructureRel, Long>)
				LayoutPageTemplateStructureRel::setCtCollectionId);
		attributeGetterFunctions.put(
			"uuid", LayoutPageTemplateStructureRel::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<LayoutPageTemplateStructureRel, String>)
				LayoutPageTemplateStructureRel::setUuid);
		attributeGetterFunctions.put(
			"layoutPageTemplateStructureRelId",
			LayoutPageTemplateStructureRel::
				getLayoutPageTemplateStructureRelId);
		attributeSetterBiConsumers.put(
			"layoutPageTemplateStructureRelId",
			(BiConsumer<LayoutPageTemplateStructureRel, Long>)
				LayoutPageTemplateStructureRel::
					setLayoutPageTemplateStructureRelId);
		attributeGetterFunctions.put(
			"groupId", LayoutPageTemplateStructureRel::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<LayoutPageTemplateStructureRel, Long>)
				LayoutPageTemplateStructureRel::setGroupId);
		attributeGetterFunctions.put(
			"companyId", LayoutPageTemplateStructureRel::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<LayoutPageTemplateStructureRel, Long>)
				LayoutPageTemplateStructureRel::setCompanyId);
		attributeGetterFunctions.put(
			"userId", LayoutPageTemplateStructureRel::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<LayoutPageTemplateStructureRel, Long>)
				LayoutPageTemplateStructureRel::setUserId);
		attributeGetterFunctions.put(
			"userName", LayoutPageTemplateStructureRel::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<LayoutPageTemplateStructureRel, String>)
				LayoutPageTemplateStructureRel::setUserName);
		attributeGetterFunctions.put(
			"createDate", LayoutPageTemplateStructureRel::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<LayoutPageTemplateStructureRel, Date>)
				LayoutPageTemplateStructureRel::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", LayoutPageTemplateStructureRel::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<LayoutPageTemplateStructureRel, Date>)
				LayoutPageTemplateStructureRel::setModifiedDate);
		attributeGetterFunctions.put(
			"layoutPageTemplateStructureId",
			LayoutPageTemplateStructureRel::getLayoutPageTemplateStructureId);
		attributeSetterBiConsumers.put(
			"layoutPageTemplateStructureId",
			(BiConsumer<LayoutPageTemplateStructureRel, Long>)
				LayoutPageTemplateStructureRel::
					setLayoutPageTemplateStructureId);
		attributeGetterFunctions.put(
			"segmentsExperienceId",
			LayoutPageTemplateStructureRel::getSegmentsExperienceId);
		attributeSetterBiConsumers.put(
			"segmentsExperienceId",
			(BiConsumer<LayoutPageTemplateStructureRel, Long>)
				LayoutPageTemplateStructureRel::setSegmentsExperienceId);
		attributeGetterFunctions.put(
			"data", LayoutPageTemplateStructureRel::getData);
		attributeSetterBiConsumers.put(
			"data",
			(BiConsumer<LayoutPageTemplateStructureRel, String>)
				LayoutPageTemplateStructureRel::setData);

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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_ctCollectionId = ctCollectionId;
	}

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

	@Override
	public long getLayoutPageTemplateStructureRelId() {
		return _layoutPageTemplateStructureRelId;
	}

	@Override
	public void setLayoutPageTemplateStructureRelId(
		long layoutPageTemplateStructureRelId) {

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_layoutPageTemplateStructureRelId = layoutPageTemplateStructureRelId;
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalGroupId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("groupId"));
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
	public long getLayoutPageTemplateStructureId() {
		return _layoutPageTemplateStructureId;
	}

	@Override
	public void setLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId) {

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_layoutPageTemplateStructureId = layoutPageTemplateStructureId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalLayoutPageTemplateStructureId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("layoutPageTemplateStructureId"));
	}

	@Override
	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	@Override
	public void setSegmentsExperienceId(long segmentsExperienceId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_segmentsExperienceId = segmentsExperienceId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalSegmentsExperienceId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("segmentsExperienceId"));
	}

	@Override
	public String getData() {
		if (_data == null) {
			return "";
		}
		else {
			return _data;
		}
	}

	@Override
	public void setData(String data) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_data = data;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(
				LayoutPageTemplateStructureRel.class.getName()));
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
			getCompanyId(), LayoutPageTemplateStructureRel.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public LayoutPageTemplateStructureRel toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, LayoutPageTemplateStructureRel>
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
		LayoutPageTemplateStructureRelImpl layoutPageTemplateStructureRelImpl =
			new LayoutPageTemplateStructureRelImpl();

		layoutPageTemplateStructureRelImpl.setMvccVersion(getMvccVersion());
		layoutPageTemplateStructureRelImpl.setCtCollectionId(
			getCtCollectionId());
		layoutPageTemplateStructureRelImpl.setUuid(getUuid());
		layoutPageTemplateStructureRelImpl.setLayoutPageTemplateStructureRelId(
			getLayoutPageTemplateStructureRelId());
		layoutPageTemplateStructureRelImpl.setGroupId(getGroupId());
		layoutPageTemplateStructureRelImpl.setCompanyId(getCompanyId());
		layoutPageTemplateStructureRelImpl.setUserId(getUserId());
		layoutPageTemplateStructureRelImpl.setUserName(getUserName());
		layoutPageTemplateStructureRelImpl.setCreateDate(getCreateDate());
		layoutPageTemplateStructureRelImpl.setModifiedDate(getModifiedDate());
		layoutPageTemplateStructureRelImpl.setLayoutPageTemplateStructureId(
			getLayoutPageTemplateStructureId());
		layoutPageTemplateStructureRelImpl.setSegmentsExperienceId(
			getSegmentsExperienceId());
		layoutPageTemplateStructureRelImpl.setData(getData());

		layoutPageTemplateStructureRelImpl.resetOriginalValues();

		return layoutPageTemplateStructureRelImpl;
	}

	@Override
	public int compareTo(
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel) {

		long primaryKey = layoutPageTemplateStructureRel.getPrimaryKey();

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

		if (!(object instanceof LayoutPageTemplateStructureRel)) {
			return false;
		}

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			(LayoutPageTemplateStructureRel)object;

		long primaryKey = layoutPageTemplateStructureRel.getPrimaryKey();

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
	public CacheModel<LayoutPageTemplateStructureRel> toCacheModel() {
		LayoutPageTemplateStructureRelCacheModel
			layoutPageTemplateStructureRelCacheModel =
				new LayoutPageTemplateStructureRelCacheModel();

		layoutPageTemplateStructureRelCacheModel.mvccVersion = getMvccVersion();

		layoutPageTemplateStructureRelCacheModel.ctCollectionId =
			getCtCollectionId();

		layoutPageTemplateStructureRelCacheModel.uuid = getUuid();

		String uuid = layoutPageTemplateStructureRelCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			layoutPageTemplateStructureRelCacheModel.uuid = null;
		}

		layoutPageTemplateStructureRelCacheModel.
			layoutPageTemplateStructureRelId =
				getLayoutPageTemplateStructureRelId();

		layoutPageTemplateStructureRelCacheModel.groupId = getGroupId();

		layoutPageTemplateStructureRelCacheModel.companyId = getCompanyId();

		layoutPageTemplateStructureRelCacheModel.userId = getUserId();

		layoutPageTemplateStructureRelCacheModel.userName = getUserName();

		String userName = layoutPageTemplateStructureRelCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			layoutPageTemplateStructureRelCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			layoutPageTemplateStructureRelCacheModel.createDate =
				createDate.getTime();
		}
		else {
			layoutPageTemplateStructureRelCacheModel.createDate =
				Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			layoutPageTemplateStructureRelCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			layoutPageTemplateStructureRelCacheModel.modifiedDate =
				Long.MIN_VALUE;
		}

		layoutPageTemplateStructureRelCacheModel.layoutPageTemplateStructureId =
			getLayoutPageTemplateStructureId();

		layoutPageTemplateStructureRelCacheModel.segmentsExperienceId =
			getSegmentsExperienceId();

		layoutPageTemplateStructureRelCacheModel.data = getData();

		String data = layoutPageTemplateStructureRelCacheModel.data;

		if ((data != null) && (data.length() == 0)) {
			layoutPageTemplateStructureRelCacheModel.data = null;
		}

		return layoutPageTemplateStructureRelCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<LayoutPageTemplateStructureRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<LayoutPageTemplateStructureRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LayoutPageTemplateStructureRel, Object>
				attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply(
					(LayoutPageTemplateStructureRel)this));
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
		Map<String, Function<LayoutPageTemplateStructureRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<LayoutPageTemplateStructureRel, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LayoutPageTemplateStructureRel, Object>
				attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply(
					(LayoutPageTemplateStructureRel)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, LayoutPageTemplateStructureRel>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _layoutPageTemplateStructureRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _layoutPageTemplateStructureId;
	private long _segmentsExperienceId;
	private String _data;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<LayoutPageTemplateStructureRel, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((LayoutPageTemplateStructureRel)this);
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

		_columnOriginalValues.put("mvccVersion", _mvccVersion);
		_columnOriginalValues.put("ctCollectionId", _ctCollectionId);
		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put(
			"lPageTemplateStructureRelId", _layoutPageTemplateStructureRelId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put(
			"layoutPageTemplateStructureId", _layoutPageTemplateStructureId);
		_columnOriginalValues.put(
			"segmentsExperienceId", _segmentsExperienceId);
		_columnOriginalValues.put("data_", _data);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");
		attributeNames.put(
			"lPageTemplateStructureRelId", "layoutPageTemplateStructureRelId");
		attributeNames.put("data_", "data");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("ctCollectionId", 2L);

		columnBitmasks.put("uuid_", 4L);

		columnBitmasks.put("lPageTemplateStructureRelId", 8L);

		columnBitmasks.put("groupId", 16L);

		columnBitmasks.put("companyId", 32L);

		columnBitmasks.put("userId", 64L);

		columnBitmasks.put("userName", 128L);

		columnBitmasks.put("createDate", 256L);

		columnBitmasks.put("modifiedDate", 512L);

		columnBitmasks.put("layoutPageTemplateStructureId", 1024L);

		columnBitmasks.put("segmentsExperienceId", 2048L);

		columnBitmasks.put("data_", 4096L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private LayoutPageTemplateStructureRel _escapedModel;

}