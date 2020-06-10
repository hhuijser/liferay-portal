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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.OrgGroupRole;
import com.liferay.portal.kernel.model.OrgGroupRoleModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.persistence.OrgGroupRolePK;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the OrgGroupRole service. Represents a row in the &quot;OrgGroupRole&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>OrgGroupRoleModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link OrgGroupRoleImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupRoleImpl
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class OrgGroupRoleModelImpl
	extends BaseModelImpl<OrgGroupRole> implements OrgGroupRoleModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a org group role model instance should use the <code>OrgGroupRole</code> interface instead.
	 */
	public static final String TABLE_NAME = "OrgGroupRole";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"organizationId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"roleId", Types.BIGINT},
		{"companyId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("organizationId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("roleId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table OrgGroupRole (mvccVersion LONG default 0 not null,organizationId LONG not null,groupId LONG not null,roleId LONG not null,companyId LONG,primary key (organizationId, groupId, roleId))";

	public static final String TABLE_SQL_DROP = "drop table OrgGroupRole";

	public static final String ORDER_BY_JPQL =
		" ORDER BY orgGroupRole.id.organizationId ASC, orgGroupRole.id.groupId ASC, orgGroupRole.id.roleId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY OrgGroupRole.organizationId ASC, OrgGroupRole.groupId ASC, OrgGroupRole.roleId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.OrgGroupRole"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.OrgGroupRole"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.OrgGroupRole"),
		true);

	public static final long GROUPID_COLUMN_BITMASK = 1L;

	public static final long ROLEID_COLUMN_BITMASK = 2L;

	public static final long ORGANIZATIONID_COLUMN_BITMASK = 4L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.OrgGroupRole"));

	public OrgGroupRoleModelImpl() {
	}

	@Override
	public OrgGroupRolePK getPrimaryKey() {
		return new OrgGroupRolePK(_organizationId, _groupId, _roleId);
	}

	@Override
	public void setPrimaryKey(OrgGroupRolePK primaryKey) {
		setOrganizationId(primaryKey.organizationId);
		setGroupId(primaryKey.groupId);
		setRoleId(primaryKey.roleId);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return new OrgGroupRolePK(_organizationId, _groupId, _roleId);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey((OrgGroupRolePK)primaryKeyObj);
	}

	@Override
	public Class<?> getModelClass() {
		return OrgGroupRole.class;
	}

	@Override
	public String getModelClassName() {
		return OrgGroupRole.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<OrgGroupRole, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<OrgGroupRole, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OrgGroupRole, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((OrgGroupRole)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<OrgGroupRole, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<OrgGroupRole, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(OrgGroupRole)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<OrgGroupRole, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<OrgGroupRole, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, OrgGroupRole>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			OrgGroupRole.class.getClassLoader(), OrgGroupRole.class,
			ModelWrapper.class);

		try {
			Constructor<OrgGroupRole> constructor =
				(Constructor<OrgGroupRole>)proxyClass.getConstructor(
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

	private static final Map<String, Function<OrgGroupRole, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<OrgGroupRole, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<OrgGroupRole, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<OrgGroupRole, Object>>();
		Map<String, BiConsumer<OrgGroupRole, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<OrgGroupRole, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", OrgGroupRole::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<OrgGroupRole, Long>)OrgGroupRole::setMvccVersion);
		attributeGetterFunctions.put(
			"organizationId", OrgGroupRole::getOrganizationId);
		attributeSetterBiConsumers.put(
			"organizationId",
			(BiConsumer<OrgGroupRole, Long>)OrgGroupRole::setOrganizationId);
		attributeGetterFunctions.put("groupId", OrgGroupRole::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<OrgGroupRole, Long>)OrgGroupRole::setGroupId);
		attributeGetterFunctions.put("roleId", OrgGroupRole::getRoleId);
		attributeSetterBiConsumers.put(
			"roleId", (BiConsumer<OrgGroupRole, Long>)OrgGroupRole::setRoleId);
		attributeGetterFunctions.put("companyId", OrgGroupRole::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<OrgGroupRole, Long>)OrgGroupRole::setCompanyId);

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
	public long getOrganizationId() {
		return _organizationId;
	}

	@Override
	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
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
	public long getRoleId() {
		return _roleId;
	}

	@Override
	public void setRoleId(long roleId) {
		_columnBitmask |= ROLEID_COLUMN_BITMASK;

		if (!_setOriginalRoleId) {
			_setOriginalRoleId = true;

			_originalRoleId = _roleId;
		}

		_roleId = roleId;
	}

	public long getOriginalRoleId() {
		return _originalRoleId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public OrgGroupRole toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, OrgGroupRole>
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
		OrgGroupRoleImpl orgGroupRoleImpl = new OrgGroupRoleImpl();

		orgGroupRoleImpl.setMvccVersion(getMvccVersion());
		orgGroupRoleImpl.setOrganizationId(getOrganizationId());
		orgGroupRoleImpl.setGroupId(getGroupId());
		orgGroupRoleImpl.setRoleId(getRoleId());
		orgGroupRoleImpl.setCompanyId(getCompanyId());

		orgGroupRoleImpl.resetOriginalValues();

		return orgGroupRoleImpl;
	}

	@Override
	public int compareTo(OrgGroupRole orgGroupRole) {
		OrgGroupRolePK primaryKey = orgGroupRole.getPrimaryKey();

		return getPrimaryKey().compareTo(primaryKey);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OrgGroupRole)) {
			return false;
		}

		OrgGroupRole orgGroupRole = (OrgGroupRole)object;

		OrgGroupRolePK primaryKey = orgGroupRole.getPrimaryKey();

		if (getPrimaryKey().equals(primaryKey)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		OrgGroupRoleModelImpl orgGroupRoleModelImpl = this;

		orgGroupRoleModelImpl._originalGroupId = orgGroupRoleModelImpl._groupId;

		orgGroupRoleModelImpl._setOriginalGroupId = false;

		orgGroupRoleModelImpl._originalRoleId = orgGroupRoleModelImpl._roleId;

		orgGroupRoleModelImpl._setOriginalRoleId = false;

		orgGroupRoleModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<OrgGroupRole> toCacheModel() {
		OrgGroupRoleCacheModel orgGroupRoleCacheModel =
			new OrgGroupRoleCacheModel();

		orgGroupRoleCacheModel.orgGroupRolePK = getPrimaryKey();

		orgGroupRoleCacheModel.mvccVersion = getMvccVersion();

		orgGroupRoleCacheModel.organizationId = getOrganizationId();

		orgGroupRoleCacheModel.groupId = getGroupId();

		orgGroupRoleCacheModel.roleId = getRoleId();

		orgGroupRoleCacheModel.companyId = getCompanyId();

		return orgGroupRoleCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<OrgGroupRole, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<OrgGroupRole, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OrgGroupRole, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((OrgGroupRole)this));
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
		Map<String, Function<OrgGroupRole, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<OrgGroupRole, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<OrgGroupRole, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((OrgGroupRole)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, OrgGroupRole>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _organizationId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _roleId;
	private long _originalRoleId;
	private boolean _setOriginalRoleId;
	private long _companyId;
	private long _columnBitmask;
	private OrgGroupRole _escapedModel;

}