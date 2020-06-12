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
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.PortletPreferencesModel;
import com.liferay.portal.kernel.model.PortletPreferencesSoap;
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
 * The base model implementation for the PortletPreferences service. Represents a row in the &quot;PortletPreferences&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>PortletPreferencesModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PortletPreferencesImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesImpl
 * @generated
 */
@JSON(strict = true)
public class PortletPreferencesModelImpl
	extends BaseModelImpl<PortletPreferences>
	implements PortletPreferencesModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a portlet preferences model instance should use the <code>PortletPreferences</code> interface instead.
	 */
	public static final String TABLE_NAME = "PortletPreferences";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"portletPreferencesId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"ownerId", Types.BIGINT}, {"ownerType", Types.INTEGER},
		{"plid", Types.BIGINT}, {"portletId", Types.VARCHAR},
		{"preferences", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("portletPreferencesId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ownerId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ownerType", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("portletId", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("preferences", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table PortletPreferences (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,portletPreferencesId LONG not null,companyId LONG,ownerId LONG,ownerType INTEGER,plid LONG,portletId VARCHAR(200) null,preferences TEXT null,primary key (portletPreferencesId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table PortletPreferences";

	public static final String ORDER_BY_JPQL =
		" ORDER BY portletPreferences.portletPreferencesId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY PortletPreferences.portletPreferencesId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.PortletPreferences"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.PortletPreferences"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.PortletPreferences"),
		true);

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long OWNERID_COLUMN_BITMASK = 2L;

	public static final long OWNERTYPE_COLUMN_BITMASK = 4L;

	public static final long PLID_COLUMN_BITMASK = 8L;

	public static final long PORTLETID_COLUMN_BITMASK = 16L;

	public static final long PORTLETPREFERENCESID_COLUMN_BITMASK = 32L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static PortletPreferences toModel(PortletPreferencesSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		PortletPreferences model = new PortletPreferencesImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setCtCollectionId(soapModel.getCtCollectionId());
		model.setPortletPreferencesId(soapModel.getPortletPreferencesId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setOwnerId(soapModel.getOwnerId());
		model.setOwnerType(soapModel.getOwnerType());
		model.setPlid(soapModel.getPlid());
		model.setPortletId(soapModel.getPortletId());
		model.setPreferences(soapModel.getPreferences());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<PortletPreferences> toModels(
		PortletPreferencesSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<PortletPreferences> models = new ArrayList<PortletPreferences>(
			soapModels.length);

		for (PortletPreferencesSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.PortletPreferences"));

	public PortletPreferencesModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _portletPreferencesId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setPortletPreferencesId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _portletPreferencesId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return PortletPreferences.class;
	}

	@Override
	public String getModelClassName() {
		return PortletPreferences.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<PortletPreferences, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<PortletPreferences, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PortletPreferences, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((PortletPreferences)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<PortletPreferences, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<PortletPreferences, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(PortletPreferences)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<PortletPreferences, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<PortletPreferences, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, PortletPreferences>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			PortletPreferences.class.getClassLoader(), PortletPreferences.class,
			ModelWrapper.class);

		try {
			Constructor<PortletPreferences> constructor =
				(Constructor<PortletPreferences>)proxyClass.getConstructor(
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

	private static final Map<String, Function<PortletPreferences, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<PortletPreferences, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<PortletPreferences, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<PortletPreferences, Object>>();
		Map<String, BiConsumer<PortletPreferences, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<PortletPreferences, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", PortletPreferences::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<PortletPreferences, Long>)
				PortletPreferences::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", PortletPreferences::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<PortletPreferences, Long>)
				PortletPreferences::setCtCollectionId);
		attributeGetterFunctions.put(
			"portletPreferencesId",
			PortletPreferences::getPortletPreferencesId);
		attributeSetterBiConsumers.put(
			"portletPreferencesId",
			(BiConsumer<PortletPreferences, Long>)
				PortletPreferences::setPortletPreferencesId);
		attributeGetterFunctions.put(
			"companyId", PortletPreferences::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<PortletPreferences, Long>)
				PortletPreferences::setCompanyId);
		attributeGetterFunctions.put("ownerId", PortletPreferences::getOwnerId);
		attributeSetterBiConsumers.put(
			"ownerId",
			(BiConsumer<PortletPreferences, Long>)
				PortletPreferences::setOwnerId);
		attributeGetterFunctions.put(
			"ownerType", PortletPreferences::getOwnerType);
		attributeSetterBiConsumers.put(
			"ownerType",
			(BiConsumer<PortletPreferences, Integer>)
				PortletPreferences::setOwnerType);
		attributeGetterFunctions.put("plid", PortletPreferences::getPlid);
		attributeSetterBiConsumers.put(
			"plid",
			(BiConsumer<PortletPreferences, Long>)PortletPreferences::setPlid);
		attributeGetterFunctions.put(
			"portletId", PortletPreferences::getPortletId);
		attributeSetterBiConsumers.put(
			"portletId",
			(BiConsumer<PortletPreferences, String>)
				PortletPreferences::setPortletId);
		attributeGetterFunctions.put(
			"preferences", PortletPreferences::getPreferences);
		attributeSetterBiConsumers.put(
			"preferences",
			(BiConsumer<PortletPreferences, String>)
				PortletPreferences::setPreferences);

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
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	@JSON
	@Override
	public long getPortletPreferencesId() {
		return _portletPreferencesId;
	}

	@Override
	public void setPortletPreferencesId(long portletPreferencesId) {
		_portletPreferencesId = portletPreferencesId;
	}

	@JSON
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

	@JSON
	@Override
	public long getOwnerId() {
		return _ownerId;
	}

	@Override
	public void setOwnerId(long ownerId) {
		_columnBitmask |= OWNERID_COLUMN_BITMASK;

		if (!_setOriginalOwnerId) {
			_setOriginalOwnerId = true;

			_originalOwnerId = _ownerId;
		}

		_ownerId = ownerId;
	}

	public long getOriginalOwnerId() {
		return _originalOwnerId;
	}

	@JSON
	@Override
	public int getOwnerType() {
		return _ownerType;
	}

	@Override
	public void setOwnerType(int ownerType) {
		_columnBitmask |= OWNERTYPE_COLUMN_BITMASK;

		if (!_setOriginalOwnerType) {
			_setOriginalOwnerType = true;

			_originalOwnerType = _ownerType;
		}

		_ownerType = ownerType;
	}

	public int getOriginalOwnerType() {
		return _originalOwnerType;
	}

	@JSON
	@Override
	public long getPlid() {
		return _plid;
	}

	@Override
	public void setPlid(long plid) {
		_columnBitmask |= PLID_COLUMN_BITMASK;

		if (!_setOriginalPlid) {
			_setOriginalPlid = true;

			_originalPlid = _plid;
		}

		_plid = plid;
	}

	public long getOriginalPlid() {
		return _originalPlid;
	}

	@JSON
	@Override
	public String getPortletId() {
		if (_portletId == null) {
			return "";
		}
		else {
			return _portletId;
		}
	}

	@Override
	public void setPortletId(String portletId) {
		_columnBitmask |= PORTLETID_COLUMN_BITMASK;

		if (_originalPortletId == null) {
			_originalPortletId = _portletId;
		}

		_portletId = portletId;
	}

	public String getOriginalPortletId() {
		return GetterUtil.getString(_originalPortletId);
	}

	@JSON
	@Override
	public String getPreferences() {
		if (_preferences == null) {
			return "";
		}
		else {
			return _preferences;
		}
	}

	@Override
	public void setPreferences(String preferences) {
		_preferences = preferences;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), PortletPreferences.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public PortletPreferences toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, PortletPreferences>
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
		PortletPreferencesImpl portletPreferencesImpl =
			new PortletPreferencesImpl();

		portletPreferencesImpl.setMvccVersion(getMvccVersion());
		portletPreferencesImpl.setCtCollectionId(getCtCollectionId());
		portletPreferencesImpl.setPortletPreferencesId(
			getPortletPreferencesId());
		portletPreferencesImpl.setCompanyId(getCompanyId());
		portletPreferencesImpl.setOwnerId(getOwnerId());
		portletPreferencesImpl.setOwnerType(getOwnerType());
		portletPreferencesImpl.setPlid(getPlid());
		portletPreferencesImpl.setPortletId(getPortletId());
		portletPreferencesImpl.setPreferences(getPreferences());

		portletPreferencesImpl.resetOriginalValues();

		return portletPreferencesImpl;
	}

	@Override
	public int compareTo(PortletPreferences portletPreferences) {
		long primaryKey = portletPreferences.getPrimaryKey();

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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletPreferences)) {
			return false;
		}

		PortletPreferences portletPreferences = (PortletPreferences)obj;

		long primaryKey = portletPreferences.getPrimaryKey();

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
		PortletPreferencesModelImpl portletPreferencesModelImpl = this;

		portletPreferencesModelImpl._originalCompanyId =
			portletPreferencesModelImpl._companyId;

		portletPreferencesModelImpl._setOriginalCompanyId = false;

		portletPreferencesModelImpl._originalOwnerId =
			portletPreferencesModelImpl._ownerId;

		portletPreferencesModelImpl._setOriginalOwnerId = false;

		portletPreferencesModelImpl._originalOwnerType =
			portletPreferencesModelImpl._ownerType;

		portletPreferencesModelImpl._setOriginalOwnerType = false;

		portletPreferencesModelImpl._originalPlid =
			portletPreferencesModelImpl._plid;

		portletPreferencesModelImpl._setOriginalPlid = false;

		portletPreferencesModelImpl._originalPortletId =
			portletPreferencesModelImpl._portletId;

		portletPreferencesModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<PortletPreferences> toCacheModel() {
		PortletPreferencesCacheModel portletPreferencesCacheModel =
			new PortletPreferencesCacheModel();

		portletPreferencesCacheModel.mvccVersion = getMvccVersion();

		portletPreferencesCacheModel.ctCollectionId = getCtCollectionId();

		portletPreferencesCacheModel.portletPreferencesId =
			getPortletPreferencesId();

		portletPreferencesCacheModel.companyId = getCompanyId();

		portletPreferencesCacheModel.ownerId = getOwnerId();

		portletPreferencesCacheModel.ownerType = getOwnerType();

		portletPreferencesCacheModel.plid = getPlid();

		portletPreferencesCacheModel.portletId = getPortletId();

		String portletId = portletPreferencesCacheModel.portletId;

		if ((portletId != null) && (portletId.length() == 0)) {
			portletPreferencesCacheModel.portletId = null;
		}

		portletPreferencesCacheModel.preferences = getPreferences();

		String preferences = portletPreferencesCacheModel.preferences;

		if ((preferences != null) && (preferences.length() == 0)) {
			portletPreferencesCacheModel.preferences = null;
		}

		return portletPreferencesCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<PortletPreferences, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<PortletPreferences, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PortletPreferences, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((PortletPreferences)this));
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
		Map<String, Function<PortletPreferences, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<PortletPreferences, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PortletPreferences, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((PortletPreferences)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, PortletPreferences>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _portletPreferencesId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _ownerId;
	private long _originalOwnerId;
	private boolean _setOriginalOwnerId;
	private int _ownerType;
	private int _originalOwnerType;
	private boolean _setOriginalOwnerType;
	private long _plid;
	private long _originalPlid;
	private boolean _setOriginalPlid;
	private String _portletId;
	private String _originalPortletId;
	private String _preferences;
	private long _columnBitmask;
	private PortletPreferences _escapedModel;

}