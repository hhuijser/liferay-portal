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

package com.liferay.portal.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutBranchModel;
import com.liferay.portal.kernel.model.LayoutBranchSoap;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the LayoutBranch service. Represents a row in the &quot;LayoutBranch&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>LayoutBranchModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link LayoutBranchImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchImpl
 * @generated
 */
@JSON(strict = true)
public class LayoutBranchModelImpl
	extends BaseModelImpl<LayoutBranch> implements LayoutBranchModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a layout branch model instance should use the <code>LayoutBranch</code> interface instead.
	 */
	public static final String TABLE_NAME = "LayoutBranch";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"layoutBranchId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"layoutSetBranchId", Types.BIGINT}, {"plid", Types.BIGINT},
		{"name", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"master", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("layoutBranchId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("layoutSetBranchId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("master", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE =
		"create table LayoutBranch (mvccVersion LONG default 0 not null,layoutBranchId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,layoutSetBranchId LONG,plid LONG,name VARCHAR(75) null,description STRING null,master BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table LayoutBranch";

	public static final String ORDER_BY_JPQL =
		" ORDER BY layoutBranch.layoutBranchId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY LayoutBranch.layoutBranchId ASC";

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
	public static final long LAYOUTSETBRANCHID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long MASTER_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long NAME_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long PLID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long LAYOUTBRANCHID_COLUMN_BITMASK = 16L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static LayoutBranch toModel(LayoutBranchSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		LayoutBranch model = new LayoutBranchImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setLayoutBranchId(soapModel.getLayoutBranchId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setLayoutSetBranchId(soapModel.getLayoutSetBranchId());
		model.setPlid(soapModel.getPlid());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setMaster(soapModel.isMaster());

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
	public static List<LayoutBranch> toModels(LayoutBranchSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<LayoutBranch> models = new ArrayList<LayoutBranch>(
			soapModels.length);

		for (LayoutBranchSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.LayoutBranch"));

	public LayoutBranchModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _layoutBranchId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLayoutBranchId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutBranchId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutBranch.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutBranch.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<LayoutBranch, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<LayoutBranch, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LayoutBranch, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((LayoutBranch)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<LayoutBranch, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<LayoutBranch, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(LayoutBranch)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<LayoutBranch, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<LayoutBranch, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, LayoutBranch>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			LayoutBranch.class.getClassLoader(), LayoutBranch.class,
			ModelWrapper.class);

		try {
			Constructor<LayoutBranch> constructor =
				(Constructor<LayoutBranch>)proxyClass.getConstructor(
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

	private static final Map<String, Function<LayoutBranch, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<LayoutBranch, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<LayoutBranch, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<LayoutBranch, Object>>();
		Map<String, BiConsumer<LayoutBranch, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<LayoutBranch, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", LayoutBranch::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<LayoutBranch, Long>)LayoutBranch::setMvccVersion);
		attributeGetterFunctions.put(
			"layoutBranchId", LayoutBranch::getLayoutBranchId);
		attributeSetterBiConsumers.put(
			"layoutBranchId",
			(BiConsumer<LayoutBranch, Long>)LayoutBranch::setLayoutBranchId);
		attributeGetterFunctions.put("groupId", LayoutBranch::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<LayoutBranch, Long>)LayoutBranch::setGroupId);
		attributeGetterFunctions.put("companyId", LayoutBranch::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<LayoutBranch, Long>)LayoutBranch::setCompanyId);
		attributeGetterFunctions.put("userId", LayoutBranch::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<LayoutBranch, Long>)LayoutBranch::setUserId);
		attributeGetterFunctions.put("userName", LayoutBranch::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<LayoutBranch, String>)LayoutBranch::setUserName);
		attributeGetterFunctions.put(
			"layoutSetBranchId", LayoutBranch::getLayoutSetBranchId);
		attributeSetterBiConsumers.put(
			"layoutSetBranchId",
			(BiConsumer<LayoutBranch, Long>)LayoutBranch::setLayoutSetBranchId);
		attributeGetterFunctions.put("plid", LayoutBranch::getPlid);
		attributeSetterBiConsumers.put(
			"plid", (BiConsumer<LayoutBranch, Long>)LayoutBranch::setPlid);
		attributeGetterFunctions.put("name", LayoutBranch::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<LayoutBranch, String>)LayoutBranch::setName);
		attributeGetterFunctions.put(
			"description", LayoutBranch::getDescription);
		attributeSetterBiConsumers.put(
			"description",
			(BiConsumer<LayoutBranch, String>)LayoutBranch::setDescription);
		attributeGetterFunctions.put("master", LayoutBranch::getMaster);
		attributeSetterBiConsumers.put(
			"master",
			(BiConsumer<LayoutBranch, Boolean>)LayoutBranch::setMaster);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
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

	@JSON
	@Override
	public long getLayoutBranchId() {
		return _layoutBranchId;
	}

	@Override
	public void setLayoutBranchId(long layoutBranchId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_layoutBranchId = layoutBranchId;
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
	public long getLayoutSetBranchId() {
		return _layoutSetBranchId;
	}

	@Override
	public void setLayoutSetBranchId(long layoutSetBranchId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_layoutSetBranchId = layoutSetBranchId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalLayoutSetBranchId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("layoutSetBranchId"));
	}

	@JSON
	@Override
	public long getPlid() {
		return _plid;
	}

	@Override
	public void setPlid(long plid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_plid = plid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalPlid() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("plid"));
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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_name = name;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalName() {
		return getColumnOriginalValue("name");
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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_description = description;
	}

	@JSON
	@Override
	public boolean getMaster() {
		return _master;
	}

	@JSON
	@Override
	public boolean isMaster() {
		return _master;
	}

	@Override
	public void setMaster(boolean master) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_master = master;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public boolean getOriginalMaster() {
		return GetterUtil.getBoolean(
			this.<Boolean>getColumnOriginalValue("master"));
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
			getCompanyId(), LayoutBranch.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public LayoutBranch toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, LayoutBranch>
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
		LayoutBranchImpl layoutBranchImpl = new LayoutBranchImpl();

		layoutBranchImpl.setMvccVersion(getMvccVersion());
		layoutBranchImpl.setLayoutBranchId(getLayoutBranchId());
		layoutBranchImpl.setGroupId(getGroupId());
		layoutBranchImpl.setCompanyId(getCompanyId());
		layoutBranchImpl.setUserId(getUserId());
		layoutBranchImpl.setUserName(getUserName());
		layoutBranchImpl.setLayoutSetBranchId(getLayoutSetBranchId());
		layoutBranchImpl.setPlid(getPlid());
		layoutBranchImpl.setName(getName());
		layoutBranchImpl.setDescription(getDescription());
		layoutBranchImpl.setMaster(isMaster());

		layoutBranchImpl.resetOriginalValues();

		return layoutBranchImpl;
	}

	@Override
	public int compareTo(LayoutBranch layoutBranch) {
		long primaryKey = layoutBranch.getPrimaryKey();

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

		if (!(object instanceof LayoutBranch)) {
			return false;
		}

		LayoutBranch layoutBranch = (LayoutBranch)object;

		long primaryKey = layoutBranch.getPrimaryKey();

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

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<LayoutBranch> toCacheModel() {
		LayoutBranchCacheModel layoutBranchCacheModel =
			new LayoutBranchCacheModel();

		layoutBranchCacheModel.mvccVersion = getMvccVersion();

		layoutBranchCacheModel.layoutBranchId = getLayoutBranchId();

		layoutBranchCacheModel.groupId = getGroupId();

		layoutBranchCacheModel.companyId = getCompanyId();

		layoutBranchCacheModel.userId = getUserId();

		layoutBranchCacheModel.userName = getUserName();

		String userName = layoutBranchCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			layoutBranchCacheModel.userName = null;
		}

		layoutBranchCacheModel.layoutSetBranchId = getLayoutSetBranchId();

		layoutBranchCacheModel.plid = getPlid();

		layoutBranchCacheModel.name = getName();

		String name = layoutBranchCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			layoutBranchCacheModel.name = null;
		}

		layoutBranchCacheModel.description = getDescription();

		String description = layoutBranchCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			layoutBranchCacheModel.description = null;
		}

		layoutBranchCacheModel.master = isMaster();

		return layoutBranchCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<LayoutBranch, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<LayoutBranch, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LayoutBranch, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((LayoutBranch)this));
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
		Map<String, Function<LayoutBranch, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<LayoutBranch, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<LayoutBranch, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((LayoutBranch)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, LayoutBranch>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _layoutBranchId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _layoutSetBranchId;
	private long _plid;
	private String _name;
	private String _description;
	private boolean _master;

	public <T> T getColumnValue(String columnName) {
		Function<LayoutBranch, Object> function = _attributeGetterFunctions.get(
			columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((LayoutBranch)this);
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
		_columnOriginalValues.put("layoutBranchId", _layoutBranchId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("layoutSetBranchId", _layoutSetBranchId);
		_columnOriginalValues.put("plid", _plid);
		_columnOriginalValues.put("name", _name);
		_columnOriginalValues.put("description", _description);
		_columnOriginalValues.put("master", _master);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("layoutBranchId", 2L);

		columnBitmasks.put("groupId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("userName", 32L);

		columnBitmasks.put("layoutSetBranchId", 64L);

		columnBitmasks.put("plid", 128L);

		columnBitmasks.put("name", 256L);

		columnBitmasks.put("description", 512L);

		columnBitmasks.put("master", 1024L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private LayoutBranch _escapedModel;

}