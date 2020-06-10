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

package com.liferay.invitation.invite.members.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.invitation.invite.members.model.MemberRequest;
import com.liferay.invitation.invite.members.model.MemberRequestModel;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
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
 * The base model implementation for the MemberRequest service. Represents a row in the &quot;IM_MemberRequest&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>MemberRequestModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MemberRequestImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MemberRequestImpl
 * @generated
 */
public class MemberRequestModelImpl
	extends BaseModelImpl<MemberRequest> implements MemberRequestModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a member request model instance should use the <code>MemberRequest</code> interface instead.
	 */
	public static final String TABLE_NAME = "IM_MemberRequest";

	public static final Object[][] TABLE_COLUMNS = {
		{"memberRequestId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"key_", Types.VARCHAR},
		{"receiverUserId", Types.BIGINT}, {"invitedRoleId", Types.BIGINT},
		{"invitedTeamId", Types.BIGINT}, {"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("memberRequestId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("receiverUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("invitedRoleId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("invitedTeamId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("status", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table IM_MemberRequest (memberRequestId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,key_ VARCHAR(75) null,receiverUserId LONG,invitedRoleId LONG,invitedTeamId LONG,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table IM_MemberRequest";

	public static final String ORDER_BY_JPQL =
		" ORDER BY memberRequest.createDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY IM_MemberRequest.createDate DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long GROUPID_COLUMN_BITMASK = 1L;

	public static final long KEY_COLUMN_BITMASK = 2L;

	public static final long RECEIVERUSERID_COLUMN_BITMASK = 4L;

	public static final long STATUS_COLUMN_BITMASK = 8L;

	public static final long CREATEDATE_COLUMN_BITMASK = 16L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	public MemberRequestModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _memberRequestId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setMemberRequestId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _memberRequestId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return MemberRequest.class;
	}

	@Override
	public String getModelClassName() {
		return MemberRequest.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<MemberRequest, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<MemberRequest, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MemberRequest, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((MemberRequest)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<MemberRequest, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<MemberRequest, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(MemberRequest)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<MemberRequest, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<MemberRequest, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, MemberRequest>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			MemberRequest.class.getClassLoader(), MemberRequest.class,
			ModelWrapper.class);

		try {
			Constructor<MemberRequest> constructor =
				(Constructor<MemberRequest>)proxyClass.getConstructor(
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

	private static final Map<String, Function<MemberRequest, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<MemberRequest, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<MemberRequest, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<MemberRequest, Object>>();
		Map<String, BiConsumer<MemberRequest, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<MemberRequest, ?>>();

		attributeGetterFunctions.put(
			"memberRequestId", MemberRequest::getMemberRequestId);
		attributeSetterBiConsumers.put(
			"memberRequestId",
			(BiConsumer<MemberRequest, Long>)MemberRequest::setMemberRequestId);
		attributeGetterFunctions.put("groupId", MemberRequest::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<MemberRequest, Long>)MemberRequest::setGroupId);
		attributeGetterFunctions.put("companyId", MemberRequest::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<MemberRequest, Long>)MemberRequest::setCompanyId);
		attributeGetterFunctions.put("userId", MemberRequest::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<MemberRequest, Long>)MemberRequest::setUserId);
		attributeGetterFunctions.put("userName", MemberRequest::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<MemberRequest, String>)MemberRequest::setUserName);
		attributeGetterFunctions.put(
			"createDate", MemberRequest::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<MemberRequest, Date>)MemberRequest::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", MemberRequest::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<MemberRequest, Date>)MemberRequest::setModifiedDate);
		attributeGetterFunctions.put("key", MemberRequest::getKey);
		attributeSetterBiConsumers.put(
			"key", (BiConsumer<MemberRequest, String>)MemberRequest::setKey);
		attributeGetterFunctions.put(
			"receiverUserId", MemberRequest::getReceiverUserId);
		attributeSetterBiConsumers.put(
			"receiverUserId",
			(BiConsumer<MemberRequest, Long>)MemberRequest::setReceiverUserId);
		attributeGetterFunctions.put(
			"invitedRoleId", MemberRequest::getInvitedRoleId);
		attributeSetterBiConsumers.put(
			"invitedRoleId",
			(BiConsumer<MemberRequest, Long>)MemberRequest::setInvitedRoleId);
		attributeGetterFunctions.put(
			"invitedTeamId", MemberRequest::getInvitedTeamId);
		attributeSetterBiConsumers.put(
			"invitedTeamId",
			(BiConsumer<MemberRequest, Long>)MemberRequest::setInvitedTeamId);
		attributeGetterFunctions.put("status", MemberRequest::getStatus);
		attributeSetterBiConsumers.put(
			"status",
			(BiConsumer<MemberRequest, Integer>)MemberRequest::setStatus);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMemberRequestId() {
		return _memberRequestId;
	}

	@Override
	public void setMemberRequestId(long memberRequestId) {
		_memberRequestId = memberRequestId;
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
		_columnBitmask = -1L;

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
	public String getKey() {
		if (_key == null) {
			return "";
		}
		else {
			return _key;
		}
	}

	@Override
	public void setKey(String key) {
		_columnBitmask |= KEY_COLUMN_BITMASK;

		if (_originalKey == null) {
			_originalKey = _key;
		}

		_key = key;
	}

	public String getOriginalKey() {
		return GetterUtil.getString(_originalKey);
	}

	@Override
	public long getReceiverUserId() {
		return _receiverUserId;
	}

	@Override
	public void setReceiverUserId(long receiverUserId) {
		_columnBitmask |= RECEIVERUSERID_COLUMN_BITMASK;

		if (!_setOriginalReceiverUserId) {
			_setOriginalReceiverUserId = true;

			_originalReceiverUserId = _receiverUserId;
		}

		_receiverUserId = receiverUserId;
	}

	@Override
	public String getReceiverUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getReceiverUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setReceiverUserUuid(String receiverUserUuid) {
	}

	public long getOriginalReceiverUserId() {
		return _originalReceiverUserId;
	}

	@Override
	public long getInvitedRoleId() {
		return _invitedRoleId;
	}

	@Override
	public void setInvitedRoleId(long invitedRoleId) {
		_invitedRoleId = invitedRoleId;
	}

	@Override
	public long getInvitedTeamId() {
		return _invitedTeamId;
	}

	@Override
	public void setInvitedTeamId(long invitedTeamId) {
		_invitedTeamId = invitedTeamId;
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_columnBitmask |= STATUS_COLUMN_BITMASK;

		if (!_setOriginalStatus) {
			_setOriginalStatus = true;

			_originalStatus = _status;
		}

		_status = status;
	}

	public int getOriginalStatus() {
		return _originalStatus;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), MemberRequest.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public MemberRequest toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, MemberRequest>
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
		MemberRequestImpl memberRequestImpl = new MemberRequestImpl();

		memberRequestImpl.setMemberRequestId(getMemberRequestId());
		memberRequestImpl.setGroupId(getGroupId());
		memberRequestImpl.setCompanyId(getCompanyId());
		memberRequestImpl.setUserId(getUserId());
		memberRequestImpl.setUserName(getUserName());
		memberRequestImpl.setCreateDate(getCreateDate());
		memberRequestImpl.setModifiedDate(getModifiedDate());
		memberRequestImpl.setKey(getKey());
		memberRequestImpl.setReceiverUserId(getReceiverUserId());
		memberRequestImpl.setInvitedRoleId(getInvitedRoleId());
		memberRequestImpl.setInvitedTeamId(getInvitedTeamId());
		memberRequestImpl.setStatus(getStatus());

		memberRequestImpl.resetOriginalValues();

		return memberRequestImpl;
	}

	@Override
	public int compareTo(MemberRequest memberRequest) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), memberRequest.getCreateDate());

		value = value * -1;

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

		if (!(object instanceof MemberRequest)) {
			return false;
		}

		MemberRequest memberRequest = (MemberRequest)object;

		long primaryKey = memberRequest.getPrimaryKey();

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
		MemberRequestModelImpl memberRequestModelImpl = this;

		memberRequestModelImpl._originalGroupId =
			memberRequestModelImpl._groupId;

		memberRequestModelImpl._setOriginalGroupId = false;

		memberRequestModelImpl._setModifiedDate = false;

		memberRequestModelImpl._originalKey = memberRequestModelImpl._key;

		memberRequestModelImpl._originalReceiverUserId =
			memberRequestModelImpl._receiverUserId;

		memberRequestModelImpl._setOriginalReceiverUserId = false;

		memberRequestModelImpl._originalStatus = memberRequestModelImpl._status;

		memberRequestModelImpl._setOriginalStatus = false;

		memberRequestModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<MemberRequest> toCacheModel() {
		MemberRequestCacheModel memberRequestCacheModel =
			new MemberRequestCacheModel();

		memberRequestCacheModel.memberRequestId = getMemberRequestId();

		memberRequestCacheModel.groupId = getGroupId();

		memberRequestCacheModel.companyId = getCompanyId();

		memberRequestCacheModel.userId = getUserId();

		memberRequestCacheModel.userName = getUserName();

		String userName = memberRequestCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			memberRequestCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			memberRequestCacheModel.createDate = createDate.getTime();
		}
		else {
			memberRequestCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			memberRequestCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			memberRequestCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		memberRequestCacheModel.key = getKey();

		String key = memberRequestCacheModel.key;

		if ((key != null) && (key.length() == 0)) {
			memberRequestCacheModel.key = null;
		}

		memberRequestCacheModel.receiverUserId = getReceiverUserId();

		memberRequestCacheModel.invitedRoleId = getInvitedRoleId();

		memberRequestCacheModel.invitedTeamId = getInvitedTeamId();

		memberRequestCacheModel.status = getStatus();

		return memberRequestCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<MemberRequest, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<MemberRequest, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MemberRequest, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((MemberRequest)this));
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
		Map<String, Function<MemberRequest, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<MemberRequest, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MemberRequest, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((MemberRequest)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, MemberRequest>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _memberRequestId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _key;
	private String _originalKey;
	private long _receiverUserId;
	private long _originalReceiverUserId;
	private boolean _setOriginalReceiverUserId;
	private long _invitedRoleId;
	private long _invitedTeamId;
	private int _status;
	private int _originalStatus;
	private boolean _setOriginalStatus;
	private long _columnBitmask;
	private MemberRequest _escapedModel;

}