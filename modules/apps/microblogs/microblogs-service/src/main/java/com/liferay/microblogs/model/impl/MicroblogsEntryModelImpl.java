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

package com.liferay.microblogs.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.MicroblogsEntryModel;
import com.liferay.microblogs.model.MicroblogsEntrySoap;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the MicroblogsEntry service. Represents a row in the &quot;MicroblogsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>MicroblogsEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MicroblogsEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryImpl
 * @generated
 */
@JSON(strict = true)
public class MicroblogsEntryModelImpl
	extends BaseModelImpl<MicroblogsEntry> implements MicroblogsEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a microblogs entry model instance should use the <code>MicroblogsEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "MicroblogsEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"microblogsEntryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"creatorClassNameId", Types.BIGINT}, {"creatorClassPK", Types.BIGINT},
		{"content", Types.VARCHAR}, {"type_", Types.INTEGER},
		{"parentMicroblogsEntryId", Types.BIGINT},
		{"socialRelationType", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("microblogsEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("creatorClassNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("creatorClassPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("content", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("parentMicroblogsEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("socialRelationType", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table MicroblogsEntry (microblogsEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,creatorClassNameId LONG,creatorClassPK LONG,content STRING null,type_ INTEGER,parentMicroblogsEntryId LONG,socialRelationType INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table MicroblogsEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY microblogsEntry.createDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY MicroblogsEntry.createDate DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long CREATEDATE_COLUMN_BITMASK = 2L;

	public static final long CREATORCLASSNAMEID_COLUMN_BITMASK = 4L;

	public static final long CREATORCLASSPK_COLUMN_BITMASK = 8L;

	public static final long PARENTMICROBLOGSENTRYID_COLUMN_BITMASK = 16L;

	public static final long SOCIALRELATIONTYPE_COLUMN_BITMASK = 32L;

	public static final long TYPE_COLUMN_BITMASK = 64L;

	public static final long USERID_COLUMN_BITMASK = 128L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static MicroblogsEntry toModel(MicroblogsEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		MicroblogsEntry model = new MicroblogsEntryImpl();

		model.setMicroblogsEntryId(soapModel.getMicroblogsEntryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCreatorClassNameId(soapModel.getCreatorClassNameId());
		model.setCreatorClassPK(soapModel.getCreatorClassPK());
		model.setContent(soapModel.getContent());
		model.setType(soapModel.getType());
		model.setParentMicroblogsEntryId(
			soapModel.getParentMicroblogsEntryId());
		model.setSocialRelationType(soapModel.getSocialRelationType());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<MicroblogsEntry> toModels(
		MicroblogsEntrySoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<MicroblogsEntry> models = new ArrayList<MicroblogsEntry>(
			soapModels.length);

		for (MicroblogsEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public MicroblogsEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _microblogsEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setMicroblogsEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _microblogsEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return MicroblogsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return MicroblogsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<MicroblogsEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<MicroblogsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MicroblogsEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((MicroblogsEntry)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<MicroblogsEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<MicroblogsEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(MicroblogsEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<MicroblogsEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<MicroblogsEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, MicroblogsEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			MicroblogsEntry.class.getClassLoader(), MicroblogsEntry.class,
			ModelWrapper.class);

		try {
			Constructor<MicroblogsEntry> constructor =
				(Constructor<MicroblogsEntry>)proxyClass.getConstructor(
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

	private static final Map<String, Function<MicroblogsEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<MicroblogsEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<MicroblogsEntry, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<MicroblogsEntry, Object>>();
		Map<String, BiConsumer<MicroblogsEntry, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<MicroblogsEntry, ?>>();

		attributeGetterFunctions.put(
			"microblogsEntryId", MicroblogsEntry::getMicroblogsEntryId);
		attributeSetterBiConsumers.put(
			"microblogsEntryId",
			(BiConsumer<MicroblogsEntry, Long>)
				MicroblogsEntry::setMicroblogsEntryId);
		attributeGetterFunctions.put(
			"companyId", MicroblogsEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<MicroblogsEntry, Long>)MicroblogsEntry::setCompanyId);
		attributeGetterFunctions.put("userId", MicroblogsEntry::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<MicroblogsEntry, Long>)MicroblogsEntry::setUserId);
		attributeGetterFunctions.put("userName", MicroblogsEntry::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<MicroblogsEntry, String>)MicroblogsEntry::setUserName);
		attributeGetterFunctions.put(
			"createDate", MicroblogsEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<MicroblogsEntry, Date>)MicroblogsEntry::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", MicroblogsEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<MicroblogsEntry, Date>)
				MicroblogsEntry::setModifiedDate);
		attributeGetterFunctions.put(
			"creatorClassNameId", MicroblogsEntry::getCreatorClassNameId);
		attributeSetterBiConsumers.put(
			"creatorClassNameId",
			(BiConsumer<MicroblogsEntry, Long>)
				MicroblogsEntry::setCreatorClassNameId);
		attributeGetterFunctions.put(
			"creatorClassPK", MicroblogsEntry::getCreatorClassPK);
		attributeSetterBiConsumers.put(
			"creatorClassPK",
			(BiConsumer<MicroblogsEntry, Long>)
				MicroblogsEntry::setCreatorClassPK);
		attributeGetterFunctions.put("content", MicroblogsEntry::getContent);
		attributeSetterBiConsumers.put(
			"content",
			(BiConsumer<MicroblogsEntry, String>)MicroblogsEntry::setContent);
		attributeGetterFunctions.put("type", MicroblogsEntry::getType);
		attributeSetterBiConsumers.put(
			"type",
			(BiConsumer<MicroblogsEntry, Integer>)MicroblogsEntry::setType);
		attributeGetterFunctions.put(
			"parentMicroblogsEntryId",
			MicroblogsEntry::getParentMicroblogsEntryId);
		attributeSetterBiConsumers.put(
			"parentMicroblogsEntryId",
			(BiConsumer<MicroblogsEntry, Long>)
				MicroblogsEntry::setParentMicroblogsEntryId);
		attributeGetterFunctions.put(
			"socialRelationType", MicroblogsEntry::getSocialRelationType);
		attributeSetterBiConsumers.put(
			"socialRelationType",
			(BiConsumer<MicroblogsEntry, Integer>)
				MicroblogsEntry::setSocialRelationType);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMicroblogsEntryId() {
		return _microblogsEntryId;
	}

	@Override
	public void setMicroblogsEntryId(long microblogsEntryId) {
		_microblogsEntryId = microblogsEntryId;
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
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
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

	public long getOriginalUserId() {
		return _originalUserId;
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
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		if (_originalCreateDate == null) {
			_originalCreateDate = _createDate;
		}

		_createDate = createDate;
	}

	public Date getOriginalCreateDate() {
		return _originalCreateDate;
	}

	@JSON
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

	@JSON
	@Override
	public long getCreatorClassNameId() {
		return _creatorClassNameId;
	}

	@Override
	public void setCreatorClassNameId(long creatorClassNameId) {
		_columnBitmask |= CREATORCLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalCreatorClassNameId) {
			_setOriginalCreatorClassNameId = true;

			_originalCreatorClassNameId = _creatorClassNameId;
		}

		_creatorClassNameId = creatorClassNameId;
	}

	public long getOriginalCreatorClassNameId() {
		return _originalCreatorClassNameId;
	}

	@JSON
	@Override
	public long getCreatorClassPK() {
		return _creatorClassPK;
	}

	@Override
	public void setCreatorClassPK(long creatorClassPK) {
		_columnBitmask |= CREATORCLASSPK_COLUMN_BITMASK;

		if (!_setOriginalCreatorClassPK) {
			_setOriginalCreatorClassPK = true;

			_originalCreatorClassPK = _creatorClassPK;
		}

		_creatorClassPK = creatorClassPK;
	}

	public long getOriginalCreatorClassPK() {
		return _originalCreatorClassPK;
	}

	@JSON
	@Override
	public String getContent() {
		if (_content == null) {
			return "";
		}
		else {
			return _content;
		}
	}

	@Override
	public void setContent(String content) {
		_content = content;
	}

	@JSON
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

	@JSON
	@Override
	public long getParentMicroblogsEntryId() {
		return _parentMicroblogsEntryId;
	}

	@Override
	public void setParentMicroblogsEntryId(long parentMicroblogsEntryId) {
		_columnBitmask |= PARENTMICROBLOGSENTRYID_COLUMN_BITMASK;

		if (!_setOriginalParentMicroblogsEntryId) {
			_setOriginalParentMicroblogsEntryId = true;

			_originalParentMicroblogsEntryId = _parentMicroblogsEntryId;
		}

		_parentMicroblogsEntryId = parentMicroblogsEntryId;
	}

	public long getOriginalParentMicroblogsEntryId() {
		return _originalParentMicroblogsEntryId;
	}

	@JSON
	@Override
	public int getSocialRelationType() {
		return _socialRelationType;
	}

	@Override
	public void setSocialRelationType(int socialRelationType) {
		_columnBitmask |= SOCIALRELATIONTYPE_COLUMN_BITMASK;

		if (!_setOriginalSocialRelationType) {
			_setOriginalSocialRelationType = true;

			_originalSocialRelationType = _socialRelationType;
		}

		_socialRelationType = socialRelationType;
	}

	public int getOriginalSocialRelationType() {
		return _originalSocialRelationType;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), MicroblogsEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public MicroblogsEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, MicroblogsEntry>
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
		MicroblogsEntryImpl microblogsEntryImpl = new MicroblogsEntryImpl();

		microblogsEntryImpl.setMicroblogsEntryId(getMicroblogsEntryId());
		microblogsEntryImpl.setCompanyId(getCompanyId());
		microblogsEntryImpl.setUserId(getUserId());
		microblogsEntryImpl.setUserName(getUserName());
		microblogsEntryImpl.setCreateDate(getCreateDate());
		microblogsEntryImpl.setModifiedDate(getModifiedDate());
		microblogsEntryImpl.setCreatorClassNameId(getCreatorClassNameId());
		microblogsEntryImpl.setCreatorClassPK(getCreatorClassPK());
		microblogsEntryImpl.setContent(getContent());
		microblogsEntryImpl.setType(getType());
		microblogsEntryImpl.setParentMicroblogsEntryId(
			getParentMicroblogsEntryId());
		microblogsEntryImpl.setSocialRelationType(getSocialRelationType());

		microblogsEntryImpl.resetOriginalValues();

		return microblogsEntryImpl;
	}

	@Override
	public int compareTo(MicroblogsEntry microblogsEntry) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), microblogsEntry.getCreateDate());

		value = value * -1;

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

		if (!(obj instanceof MicroblogsEntry)) {
			return false;
		}

		MicroblogsEntry microblogsEntry = (MicroblogsEntry)obj;

		long primaryKey = microblogsEntry.getPrimaryKey();

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
		MicroblogsEntryModelImpl microblogsEntryModelImpl = this;

		microblogsEntryModelImpl._originalCompanyId =
			microblogsEntryModelImpl._companyId;

		microblogsEntryModelImpl._setOriginalCompanyId = false;

		microblogsEntryModelImpl._originalUserId =
			microblogsEntryModelImpl._userId;

		microblogsEntryModelImpl._setOriginalUserId = false;

		microblogsEntryModelImpl._originalCreateDate =
			microblogsEntryModelImpl._createDate;

		microblogsEntryModelImpl._setModifiedDate = false;

		microblogsEntryModelImpl._originalCreatorClassNameId =
			microblogsEntryModelImpl._creatorClassNameId;

		microblogsEntryModelImpl._setOriginalCreatorClassNameId = false;

		microblogsEntryModelImpl._originalCreatorClassPK =
			microblogsEntryModelImpl._creatorClassPK;

		microblogsEntryModelImpl._setOriginalCreatorClassPK = false;

		microblogsEntryModelImpl._originalType = microblogsEntryModelImpl._type;

		microblogsEntryModelImpl._setOriginalType = false;

		microblogsEntryModelImpl._originalParentMicroblogsEntryId =
			microblogsEntryModelImpl._parentMicroblogsEntryId;

		microblogsEntryModelImpl._setOriginalParentMicroblogsEntryId = false;

		microblogsEntryModelImpl._originalSocialRelationType =
			microblogsEntryModelImpl._socialRelationType;

		microblogsEntryModelImpl._setOriginalSocialRelationType = false;

		microblogsEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<MicroblogsEntry> toCacheModel() {
		MicroblogsEntryCacheModel microblogsEntryCacheModel =
			new MicroblogsEntryCacheModel();

		microblogsEntryCacheModel.microblogsEntryId = getMicroblogsEntryId();

		microblogsEntryCacheModel.companyId = getCompanyId();

		microblogsEntryCacheModel.userId = getUserId();

		microblogsEntryCacheModel.userName = getUserName();

		String userName = microblogsEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			microblogsEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			microblogsEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			microblogsEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			microblogsEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			microblogsEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		microblogsEntryCacheModel.creatorClassNameId = getCreatorClassNameId();

		microblogsEntryCacheModel.creatorClassPK = getCreatorClassPK();

		microblogsEntryCacheModel.content = getContent();

		String content = microblogsEntryCacheModel.content;

		if ((content != null) && (content.length() == 0)) {
			microblogsEntryCacheModel.content = null;
		}

		microblogsEntryCacheModel.type = getType();

		microblogsEntryCacheModel.parentMicroblogsEntryId =
			getParentMicroblogsEntryId();

		microblogsEntryCacheModel.socialRelationType = getSocialRelationType();

		return microblogsEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<MicroblogsEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<MicroblogsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MicroblogsEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((MicroblogsEntry)this));
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
		Map<String, Function<MicroblogsEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<MicroblogsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MicroblogsEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((MicroblogsEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, MicroblogsEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _microblogsEntryId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _originalCreateDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _creatorClassNameId;
	private long _originalCreatorClassNameId;
	private boolean _setOriginalCreatorClassNameId;
	private long _creatorClassPK;
	private long _originalCreatorClassPK;
	private boolean _setOriginalCreatorClassPK;
	private String _content;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private long _parentMicroblogsEntryId;
	private long _originalParentMicroblogsEntryId;
	private boolean _setOriginalParentMicroblogsEntryId;
	private int _socialRelationType;
	private int _originalSocialRelationType;
	private boolean _setOriginalSocialRelationType;
	private long _columnBitmask;
	private MicroblogsEntry _escapedModel;

}