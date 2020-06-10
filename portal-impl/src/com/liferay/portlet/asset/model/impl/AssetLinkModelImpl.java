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

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetLinkModel;
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
 * The base model implementation for the AssetLink service. Represents a row in the &quot;AssetLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>AssetLinkModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssetLinkImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkImpl
 * @generated
 */
public class AssetLinkModelImpl
	extends BaseModelImpl<AssetLink> implements AssetLinkModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset link model instance should use the <code>AssetLink</code> interface instead.
	 */
	public static final String TABLE_NAME = "AssetLink";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"linkId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"entryId1", Types.BIGINT},
		{"entryId2", Types.BIGINT}, {"type_", Types.INTEGER},
		{"weight", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("linkId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("entryId1", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("entryId2", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("weight", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AssetLink (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,linkId LONG not null,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,entryId1 LONG,entryId2 LONG,type_ INTEGER,weight INTEGER,primary key (linkId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table AssetLink";

	public static final String ORDER_BY_JPQL = " ORDER BY assetLink.weight ASC";

	public static final String ORDER_BY_SQL = " ORDER BY AssetLink.weight ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.asset.kernel.model.AssetLink"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.asset.kernel.model.AssetLink"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.asset.kernel.model.AssetLink"),
		true);

	public static final long ENTRYID1_COLUMN_BITMASK = 1L;

	public static final long ENTRYID2_COLUMN_BITMASK = 2L;

	public static final long TYPE_COLUMN_BITMASK = 4L;

	public static final long WEIGHT_COLUMN_BITMASK = 8L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.asset.kernel.model.AssetLink"));

	public AssetLinkModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _linkId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setLinkId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _linkId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AssetLink.class;
	}

	@Override
	public String getModelClassName() {
		return AssetLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AssetLink, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AssetLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetLink, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((AssetLink)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AssetLink, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AssetLink, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AssetLink)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AssetLink, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AssetLink, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AssetLink>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AssetLink.class.getClassLoader(), AssetLink.class,
			ModelWrapper.class);

		try {
			Constructor<AssetLink> constructor =
				(Constructor<AssetLink>)proxyClass.getConstructor(
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

	private static final Map<String, Function<AssetLink, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<AssetLink, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<AssetLink, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<AssetLink, Object>>();
		Map<String, BiConsumer<AssetLink, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<AssetLink, ?>>();

		attributeGetterFunctions.put("mvccVersion", AssetLink::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<AssetLink, Long>)AssetLink::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", AssetLink::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<AssetLink, Long>)AssetLink::setCtCollectionId);
		attributeGetterFunctions.put("linkId", AssetLink::getLinkId);
		attributeSetterBiConsumers.put(
			"linkId", (BiConsumer<AssetLink, Long>)AssetLink::setLinkId);
		attributeGetterFunctions.put("companyId", AssetLink::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<AssetLink, Long>)AssetLink::setCompanyId);
		attributeGetterFunctions.put("userId", AssetLink::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<AssetLink, Long>)AssetLink::setUserId);
		attributeGetterFunctions.put("userName", AssetLink::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<AssetLink, String>)AssetLink::setUserName);
		attributeGetterFunctions.put("createDate", AssetLink::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<AssetLink, Date>)AssetLink::setCreateDate);
		attributeGetterFunctions.put("entryId1", AssetLink::getEntryId1);
		attributeSetterBiConsumers.put(
			"entryId1", (BiConsumer<AssetLink, Long>)AssetLink::setEntryId1);
		attributeGetterFunctions.put("entryId2", AssetLink::getEntryId2);
		attributeSetterBiConsumers.put(
			"entryId2", (BiConsumer<AssetLink, Long>)AssetLink::setEntryId2);
		attributeGetterFunctions.put("type", AssetLink::getType);
		attributeSetterBiConsumers.put(
			"type", (BiConsumer<AssetLink, Integer>)AssetLink::setType);
		attributeGetterFunctions.put("weight", AssetLink::getWeight);
		attributeSetterBiConsumers.put(
			"weight", (BiConsumer<AssetLink, Integer>)AssetLink::setWeight);

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
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	@Override
	public long getLinkId() {
		return _linkId;
	}

	@Override
	public void setLinkId(long linkId) {
		_linkId = linkId;
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
	public long getEntryId1() {
		return _entryId1;
	}

	@Override
	public void setEntryId1(long entryId1) {
		_columnBitmask |= ENTRYID1_COLUMN_BITMASK;

		if (!_setOriginalEntryId1) {
			_setOriginalEntryId1 = true;

			_originalEntryId1 = _entryId1;
		}

		_entryId1 = entryId1;
	}

	public long getOriginalEntryId1() {
		return _originalEntryId1;
	}

	@Override
	public long getEntryId2() {
		return _entryId2;
	}

	@Override
	public void setEntryId2(long entryId2) {
		_columnBitmask |= ENTRYID2_COLUMN_BITMASK;

		if (!_setOriginalEntryId2) {
			_setOriginalEntryId2 = true;

			_originalEntryId2 = _entryId2;
		}

		_entryId2 = entryId2;
	}

	public long getOriginalEntryId2() {
		return _originalEntryId2;
	}

	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (!_setOriginalType) {
			_setOriginalType = true;

			_originalType = _type;
		}

		_type = type;
	}

	public int getOriginalType() {
		return _originalType;
	}

	@Override
	public int getWeight() {
		return _weight;
	}

	@Override
	public void setWeight(int weight) {
		_columnBitmask = -1L;

		_weight = weight;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), AssetLink.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AssetLink toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AssetLink>
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
		AssetLinkImpl assetLinkImpl = new AssetLinkImpl();

		assetLinkImpl.setMvccVersion(getMvccVersion());
		assetLinkImpl.setCtCollectionId(getCtCollectionId());
		assetLinkImpl.setLinkId(getLinkId());
		assetLinkImpl.setCompanyId(getCompanyId());
		assetLinkImpl.setUserId(getUserId());
		assetLinkImpl.setUserName(getUserName());
		assetLinkImpl.setCreateDate(getCreateDate());
		assetLinkImpl.setEntryId1(getEntryId1());
		assetLinkImpl.setEntryId2(getEntryId2());
		assetLinkImpl.setType(getType());
		assetLinkImpl.setWeight(getWeight());

		assetLinkImpl.resetOriginalValues();

		return assetLinkImpl;
	}

	@Override
	public int compareTo(AssetLink assetLink) {
		int value = 0;

		if (getWeight() < assetLink.getWeight()) {
			value = -1;
		}
		else if (getWeight() > assetLink.getWeight()) {
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

		if (!(object instanceof AssetLink)) {
			return false;
		}

		AssetLink assetLink = (AssetLink)object;

		long primaryKey = assetLink.getPrimaryKey();

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
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		AssetLinkModelImpl assetLinkModelImpl = this;

		assetLinkModelImpl._originalEntryId1 = assetLinkModelImpl._entryId1;

		assetLinkModelImpl._setOriginalEntryId1 = false;

		assetLinkModelImpl._originalEntryId2 = assetLinkModelImpl._entryId2;

		assetLinkModelImpl._setOriginalEntryId2 = false;

		assetLinkModelImpl._originalType = assetLinkModelImpl._type;

		assetLinkModelImpl._setOriginalType = false;

		assetLinkModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AssetLink> toCacheModel() {
		AssetLinkCacheModel assetLinkCacheModel = new AssetLinkCacheModel();

		assetLinkCacheModel.mvccVersion = getMvccVersion();

		assetLinkCacheModel.ctCollectionId = getCtCollectionId();

		assetLinkCacheModel.linkId = getLinkId();

		assetLinkCacheModel.companyId = getCompanyId();

		assetLinkCacheModel.userId = getUserId();

		assetLinkCacheModel.userName = getUserName();

		String userName = assetLinkCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			assetLinkCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			assetLinkCacheModel.createDate = createDate.getTime();
		}
		else {
			assetLinkCacheModel.createDate = Long.MIN_VALUE;
		}

		assetLinkCacheModel.entryId1 = getEntryId1();

		assetLinkCacheModel.entryId2 = getEntryId2();

		assetLinkCacheModel.type = getType();

		assetLinkCacheModel.weight = getWeight();

		return assetLinkCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AssetLink, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AssetLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetLink, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((AssetLink)this));
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
		Map<String, Function<AssetLink, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AssetLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AssetLink, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((AssetLink)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, AssetLink>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _linkId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _entryId1;
	private long _originalEntryId1;
	private boolean _setOriginalEntryId1;
	private long _entryId2;
	private long _originalEntryId2;
	private boolean _setOriginalEntryId2;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private int _weight;
	private long _columnBitmask;
	private AssetLink _escapedModel;

}