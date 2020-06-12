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

package com.liferay.html.preview.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.model.HtmlPreviewEntryModel;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

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
 * The base model implementation for the HtmlPreviewEntry service. Represents a row in the &quot;HtmlPreviewEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>HtmlPreviewEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link HtmlPreviewEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntryImpl
 * @generated
 */
public class HtmlPreviewEntryModelImpl
	extends BaseModelImpl<HtmlPreviewEntry> implements HtmlPreviewEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a html preview entry model instance should use the <code>HtmlPreviewEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "HtmlPreviewEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"htmlPreviewEntryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"fileEntryId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("htmlPreviewEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table HtmlPreviewEntry (htmlPreviewEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,fileEntryId LONG)";

	public static final String TABLE_SQL_DROP = "drop table HtmlPreviewEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY htmlPreviewEntry.htmlPreviewEntryId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY HtmlPreviewEntry.htmlPreviewEntryId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long GROUPID_COLUMN_BITMASK = 4L;

	public static final long HTMLPREVIEWENTRYID_COLUMN_BITMASK = 8L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public HtmlPreviewEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _htmlPreviewEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setHtmlPreviewEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _htmlPreviewEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return HtmlPreviewEntry.class;
	}

	@Override
	public String getModelClassName() {
		return HtmlPreviewEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<HtmlPreviewEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<HtmlPreviewEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<HtmlPreviewEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((HtmlPreviewEntry)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<HtmlPreviewEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<HtmlPreviewEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(HtmlPreviewEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<HtmlPreviewEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<HtmlPreviewEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, HtmlPreviewEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			HtmlPreviewEntry.class.getClassLoader(), HtmlPreviewEntry.class,
			ModelWrapper.class);

		try {
			Constructor<HtmlPreviewEntry> constructor =
				(Constructor<HtmlPreviewEntry>)proxyClass.getConstructor(
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

	private static final Map<String, Function<HtmlPreviewEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<HtmlPreviewEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<HtmlPreviewEntry, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<HtmlPreviewEntry, Object>>();
		Map<String, BiConsumer<HtmlPreviewEntry, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<HtmlPreviewEntry, ?>>();

		attributeGetterFunctions.put(
			"htmlPreviewEntryId", HtmlPreviewEntry::getHtmlPreviewEntryId);
		attributeSetterBiConsumers.put(
			"htmlPreviewEntryId",
			(BiConsumer<HtmlPreviewEntry, Long>)
				HtmlPreviewEntry::setHtmlPreviewEntryId);
		attributeGetterFunctions.put("groupId", HtmlPreviewEntry::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<HtmlPreviewEntry, Long>)HtmlPreviewEntry::setGroupId);
		attributeGetterFunctions.put(
			"companyId", HtmlPreviewEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<HtmlPreviewEntry, Long>)HtmlPreviewEntry::setCompanyId);
		attributeGetterFunctions.put("userId", HtmlPreviewEntry::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<HtmlPreviewEntry, Long>)HtmlPreviewEntry::setUserId);
		attributeGetterFunctions.put("userName", HtmlPreviewEntry::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<HtmlPreviewEntry, String>)
				HtmlPreviewEntry::setUserName);
		attributeGetterFunctions.put(
			"createDate", HtmlPreviewEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<HtmlPreviewEntry, Date>)
				HtmlPreviewEntry::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", HtmlPreviewEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<HtmlPreviewEntry, Date>)
				HtmlPreviewEntry::setModifiedDate);
		attributeGetterFunctions.put(
			"classNameId", HtmlPreviewEntry::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<HtmlPreviewEntry, Long>)
				HtmlPreviewEntry::setClassNameId);
		attributeGetterFunctions.put("classPK", HtmlPreviewEntry::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<HtmlPreviewEntry, Long>)HtmlPreviewEntry::setClassPK);
		attributeGetterFunctions.put(
			"fileEntryId", HtmlPreviewEntry::getFileEntryId);
		attributeSetterBiConsumers.put(
			"fileEntryId",
			(BiConsumer<HtmlPreviewEntry, Long>)
				HtmlPreviewEntry::setFileEntryId);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getHtmlPreviewEntryId() {
		return _htmlPreviewEntryId;
	}

	@Override
	public void setHtmlPreviewEntryId(long htmlPreviewEntryId) {
		_htmlPreviewEntryId = htmlPreviewEntryId;
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
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@Override
	public long getFileEntryId() {
		return _fileEntryId;
	}

	@Override
	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), HtmlPreviewEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public HtmlPreviewEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, HtmlPreviewEntry>
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
		HtmlPreviewEntryImpl htmlPreviewEntryImpl = new HtmlPreviewEntryImpl();

		htmlPreviewEntryImpl.setHtmlPreviewEntryId(getHtmlPreviewEntryId());
		htmlPreviewEntryImpl.setGroupId(getGroupId());
		htmlPreviewEntryImpl.setCompanyId(getCompanyId());
		htmlPreviewEntryImpl.setUserId(getUserId());
		htmlPreviewEntryImpl.setUserName(getUserName());
		htmlPreviewEntryImpl.setCreateDate(getCreateDate());
		htmlPreviewEntryImpl.setModifiedDate(getModifiedDate());
		htmlPreviewEntryImpl.setClassNameId(getClassNameId());
		htmlPreviewEntryImpl.setClassPK(getClassPK());
		htmlPreviewEntryImpl.setFileEntryId(getFileEntryId());

		htmlPreviewEntryImpl.resetOriginalValues();

		return htmlPreviewEntryImpl;
	}

	@Override
	public int compareTo(HtmlPreviewEntry htmlPreviewEntry) {
		long primaryKey = htmlPreviewEntry.getPrimaryKey();

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

		if (!(obj instanceof HtmlPreviewEntry)) {
			return false;
		}

		HtmlPreviewEntry htmlPreviewEntry = (HtmlPreviewEntry)obj;

		long primaryKey = htmlPreviewEntry.getPrimaryKey();

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
		HtmlPreviewEntryModelImpl htmlPreviewEntryModelImpl = this;

		htmlPreviewEntryModelImpl._originalGroupId =
			htmlPreviewEntryModelImpl._groupId;

		htmlPreviewEntryModelImpl._setOriginalGroupId = false;

		htmlPreviewEntryModelImpl._setModifiedDate = false;

		htmlPreviewEntryModelImpl._originalClassNameId =
			htmlPreviewEntryModelImpl._classNameId;

		htmlPreviewEntryModelImpl._setOriginalClassNameId = false;

		htmlPreviewEntryModelImpl._originalClassPK =
			htmlPreviewEntryModelImpl._classPK;

		htmlPreviewEntryModelImpl._setOriginalClassPK = false;

		htmlPreviewEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<HtmlPreviewEntry> toCacheModel() {
		HtmlPreviewEntryCacheModel htmlPreviewEntryCacheModel =
			new HtmlPreviewEntryCacheModel();

		htmlPreviewEntryCacheModel.htmlPreviewEntryId = getHtmlPreviewEntryId();

		htmlPreviewEntryCacheModel.groupId = getGroupId();

		htmlPreviewEntryCacheModel.companyId = getCompanyId();

		htmlPreviewEntryCacheModel.userId = getUserId();

		htmlPreviewEntryCacheModel.userName = getUserName();

		String userName = htmlPreviewEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			htmlPreviewEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			htmlPreviewEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			htmlPreviewEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			htmlPreviewEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			htmlPreviewEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		htmlPreviewEntryCacheModel.classNameId = getClassNameId();

		htmlPreviewEntryCacheModel.classPK = getClassPK();

		htmlPreviewEntryCacheModel.fileEntryId = getFileEntryId();

		return htmlPreviewEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<HtmlPreviewEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<HtmlPreviewEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<HtmlPreviewEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((HtmlPreviewEntry)this));
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
		Map<String, Function<HtmlPreviewEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<HtmlPreviewEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<HtmlPreviewEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((HtmlPreviewEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, HtmlPreviewEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _htmlPreviewEntryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _fileEntryId;
	private long _columnBitmask;
	private HtmlPreviewEntry _escapedModel;

}