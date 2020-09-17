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
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.ResourcePermissionModel;
import com.liferay.portal.kernel.model.ResourcePermissionSoap;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
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
 * The base model implementation for the ResourcePermission service. Represents a row in the &quot;ResourcePermission&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>ResourcePermissionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ResourcePermissionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionImpl
 * @generated
 */
@JSON(strict = true)
public class ResourcePermissionModelImpl
	extends BaseModelImpl<ResourcePermission>
	implements ResourcePermissionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a resource permission model instance should use the <code>ResourcePermission</code> interface instead.
	 */
	public static final String TABLE_NAME = "ResourcePermission";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"resourcePermissionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"scope", Types.INTEGER},
		{"primKey", Types.VARCHAR}, {"primKeyId", Types.BIGINT},
		{"roleId", Types.BIGINT}, {"ownerId", Types.BIGINT},
		{"actionIds", Types.BIGINT}, {"viewActionId", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("resourcePermissionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("scope", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("primKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("primKeyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("roleId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ownerId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("actionIds", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("viewActionId", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE =
		"create table ResourcePermission (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,resourcePermissionId LONG not null,companyId LONG,name VARCHAR(255) null,scope INTEGER,primKey VARCHAR(255) null,primKeyId LONG,roleId LONG,ownerId LONG,actionIds LONG,viewActionId BOOLEAN,primary key (resourcePermissionId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table ResourcePermission";

	public static final String ORDER_BY_JPQL =
		" ORDER BY resourcePermission.resourcePermissionId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY ResourcePermission.resourcePermissionId ASC";

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
	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long NAME_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long PRIMKEY_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long PRIMKEYID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long ROLEID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long SCOPE_COLUMN_BITMASK = 32L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long VIEWACTIONID_COLUMN_BITMASK = 64L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long RESOURCEPERMISSIONID_COLUMN_BITMASK = 128L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static ResourcePermission toModel(ResourcePermissionSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		ResourcePermission model = new ResourcePermissionImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setCtCollectionId(soapModel.getCtCollectionId());
		model.setResourcePermissionId(soapModel.getResourcePermissionId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setName(soapModel.getName());
		model.setScope(soapModel.getScope());
		model.setPrimKey(soapModel.getPrimKey());
		model.setPrimKeyId(soapModel.getPrimKeyId());
		model.setRoleId(soapModel.getRoleId());
		model.setOwnerId(soapModel.getOwnerId());
		model.setActionIds(soapModel.getActionIds());
		model.setViewActionId(soapModel.isViewActionId());

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
	public static List<ResourcePermission> toModels(
		ResourcePermissionSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<ResourcePermission> models = new ArrayList<ResourcePermission>(
			soapModels.length);

		for (ResourcePermissionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.ResourcePermission"));

	public ResourcePermissionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _resourcePermissionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setResourcePermissionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _resourcePermissionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return ResourcePermission.class;
	}

	@Override
	public String getModelClassName() {
		return ResourcePermission.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<ResourcePermission, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<ResourcePermission, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ResourcePermission, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((ResourcePermission)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<ResourcePermission, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<ResourcePermission, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(ResourcePermission)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<ResourcePermission, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<ResourcePermission, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, ResourcePermission>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			ResourcePermission.class.getClassLoader(), ResourcePermission.class,
			ModelWrapper.class);

		try {
			Constructor<ResourcePermission> constructor =
				(Constructor<ResourcePermission>)proxyClass.getConstructor(
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

	private static final Map<String, Function<ResourcePermission, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<ResourcePermission, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<ResourcePermission, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<ResourcePermission, Object>>();
		Map<String, BiConsumer<ResourcePermission, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<ResourcePermission, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", ResourcePermission::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<ResourcePermission, Long>)
				ResourcePermission::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", ResourcePermission::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<ResourcePermission, Long>)
				ResourcePermission::setCtCollectionId);
		attributeGetterFunctions.put(
			"resourcePermissionId",
			ResourcePermission::getResourcePermissionId);
		attributeSetterBiConsumers.put(
			"resourcePermissionId",
			(BiConsumer<ResourcePermission, Long>)
				ResourcePermission::setResourcePermissionId);
		attributeGetterFunctions.put(
			"companyId", ResourcePermission::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<ResourcePermission, Long>)
				ResourcePermission::setCompanyId);
		attributeGetterFunctions.put("name", ResourcePermission::getName);
		attributeSetterBiConsumers.put(
			"name",
			(BiConsumer<ResourcePermission, String>)
				ResourcePermission::setName);
		attributeGetterFunctions.put("scope", ResourcePermission::getScope);
		attributeSetterBiConsumers.put(
			"scope",
			(BiConsumer<ResourcePermission, Integer>)
				ResourcePermission::setScope);
		attributeGetterFunctions.put("primKey", ResourcePermission::getPrimKey);
		attributeSetterBiConsumers.put(
			"primKey",
			(BiConsumer<ResourcePermission, String>)
				ResourcePermission::setPrimKey);
		attributeGetterFunctions.put(
			"primKeyId", ResourcePermission::getPrimKeyId);
		attributeSetterBiConsumers.put(
			"primKeyId",
			(BiConsumer<ResourcePermission, Long>)
				ResourcePermission::setPrimKeyId);
		attributeGetterFunctions.put("roleId", ResourcePermission::getRoleId);
		attributeSetterBiConsumers.put(
			"roleId",
			(BiConsumer<ResourcePermission, Long>)
				ResourcePermission::setRoleId);
		attributeGetterFunctions.put("ownerId", ResourcePermission::getOwnerId);
		attributeSetterBiConsumers.put(
			"ownerId",
			(BiConsumer<ResourcePermission, Long>)
				ResourcePermission::setOwnerId);
		attributeGetterFunctions.put(
			"actionIds", ResourcePermission::getActionIds);
		attributeSetterBiConsumers.put(
			"actionIds",
			(BiConsumer<ResourcePermission, Long>)
				ResourcePermission::setActionIds);
		attributeGetterFunctions.put(
			"viewActionId", ResourcePermission::getViewActionId);
		attributeSetterBiConsumers.put(
			"viewActionId",
			(BiConsumer<ResourcePermission, Boolean>)
				ResourcePermission::setViewActionId);

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

	@JSON
	@Override
	public long getResourcePermissionId() {
		return _resourcePermissionId;
	}

	@Override
	public void setResourcePermissionId(long resourcePermissionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_resourcePermissionId = resourcePermissionId;
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("companyId"));
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
	public int getScope() {
		return _scope;
	}

	@Override
	public void setScope(int scope) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_scope = scope;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public int getOriginalScope() {
		return GetterUtil.getInteger(
			this.<Integer>getColumnOriginalValue("scope"));
	}

	@JSON
	@Override
	public String getPrimKey() {
		if (_primKey == null) {
			return "";
		}
		else {
			return _primKey;
		}
	}

	@Override
	public void setPrimKey(String primKey) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_primKey = primKey;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalPrimKey() {
		return getColumnOriginalValue("primKey");
	}

	@JSON
	@Override
	public long getPrimKeyId() {
		return _primKeyId;
	}

	@Override
	public void setPrimKeyId(long primKeyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_primKeyId = primKeyId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalPrimKeyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("primKeyId"));
	}

	@JSON
	@Override
	public long getRoleId() {
		return _roleId;
	}

	@Override
	public void setRoleId(long roleId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_roleId = roleId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalRoleId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("roleId"));
	}

	@JSON
	@Override
	public long getOwnerId() {
		return _ownerId;
	}

	@Override
	public void setOwnerId(long ownerId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_ownerId = ownerId;
	}

	@JSON
	@Override
	public long getActionIds() {
		return _actionIds;
	}

	@Override
	public void setActionIds(long actionIds) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_actionIds = actionIds;
	}

	@JSON
	@Override
	public boolean getViewActionId() {
		return _viewActionId;
	}

	@JSON
	@Override
	public boolean isViewActionId() {
		return _viewActionId;
	}

	@Override
	public void setViewActionId(boolean viewActionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_viewActionId = viewActionId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public boolean getOriginalViewActionId() {
		return GetterUtil.getBoolean(
			this.<Boolean>getColumnOriginalValue("viewActionId"));
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
			getCompanyId(), ResourcePermission.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public ResourcePermission toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, ResourcePermission>
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
		ResourcePermissionImpl resourcePermissionImpl =
			new ResourcePermissionImpl();

		resourcePermissionImpl.setMvccVersion(getMvccVersion());
		resourcePermissionImpl.setCtCollectionId(getCtCollectionId());
		resourcePermissionImpl.setResourcePermissionId(
			getResourcePermissionId());
		resourcePermissionImpl.setCompanyId(getCompanyId());
		resourcePermissionImpl.setName(getName());
		resourcePermissionImpl.setScope(getScope());
		resourcePermissionImpl.setPrimKey(getPrimKey());
		resourcePermissionImpl.setPrimKeyId(getPrimKeyId());
		resourcePermissionImpl.setRoleId(getRoleId());
		resourcePermissionImpl.setOwnerId(getOwnerId());
		resourcePermissionImpl.setActionIds(getActionIds());
		resourcePermissionImpl.setViewActionId(isViewActionId());

		resourcePermissionImpl.resetOriginalValues();

		return resourcePermissionImpl;
	}

	@Override
	public int compareTo(ResourcePermission resourcePermission) {
		long primaryKey = resourcePermission.getPrimaryKey();

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

		if (!(object instanceof ResourcePermission)) {
			return false;
		}

		ResourcePermission resourcePermission = (ResourcePermission)object;

		long primaryKey = resourcePermission.getPrimaryKey();

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
	public CacheModel<ResourcePermission> toCacheModel() {
		ResourcePermissionCacheModel resourcePermissionCacheModel =
			new ResourcePermissionCacheModel();

		resourcePermissionCacheModel.mvccVersion = getMvccVersion();

		resourcePermissionCacheModel.ctCollectionId = getCtCollectionId();

		resourcePermissionCacheModel.resourcePermissionId =
			getResourcePermissionId();

		resourcePermissionCacheModel.companyId = getCompanyId();

		resourcePermissionCacheModel.name = getName();

		String name = resourcePermissionCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			resourcePermissionCacheModel.name = null;
		}

		resourcePermissionCacheModel.scope = getScope();

		resourcePermissionCacheModel.primKey = getPrimKey();

		String primKey = resourcePermissionCacheModel.primKey;

		if ((primKey != null) && (primKey.length() == 0)) {
			resourcePermissionCacheModel.primKey = null;
		}

		resourcePermissionCacheModel.primKeyId = getPrimKeyId();

		resourcePermissionCacheModel.roleId = getRoleId();

		resourcePermissionCacheModel.ownerId = getOwnerId();

		resourcePermissionCacheModel.actionIds = getActionIds();

		resourcePermissionCacheModel.viewActionId = isViewActionId();

		return resourcePermissionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<ResourcePermission, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<ResourcePermission, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ResourcePermission, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((ResourcePermission)this));
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
		Map<String, Function<ResourcePermission, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<ResourcePermission, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<ResourcePermission, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((ResourcePermission)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, ResourcePermission>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _resourcePermissionId;
	private long _companyId;
	private String _name;
	private int _scope;
	private String _primKey;
	private long _primKeyId;
	private long _roleId;
	private long _ownerId;
	private long _actionIds;
	private boolean _viewActionId;

	public <T> T getColumnValue(String columnName) {
		Function<ResourcePermission, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((ResourcePermission)this);
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
		_columnOriginalValues.put(
			"resourcePermissionId", _resourcePermissionId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("name", _name);
		_columnOriginalValues.put("scope", _scope);
		_columnOriginalValues.put("primKey", _primKey);
		_columnOriginalValues.put("primKeyId", _primKeyId);
		_columnOriginalValues.put("roleId", _roleId);
		_columnOriginalValues.put("ownerId", _ownerId);
		_columnOriginalValues.put("actionIds", _actionIds);
		_columnOriginalValues.put("viewActionId", _viewActionId);
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

		columnBitmasks.put("resourcePermissionId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("name", 16L);

		columnBitmasks.put("scope", 32L);

		columnBitmasks.put("primKey", 64L);

		columnBitmasks.put("primKeyId", 128L);

		columnBitmasks.put("roleId", 256L);

		columnBitmasks.put("ownerId", 512L);

		columnBitmasks.put("actionIds", 1024L);

		columnBitmasks.put("viewActionId", 2048L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private ResourcePermission _escapedModel;

}