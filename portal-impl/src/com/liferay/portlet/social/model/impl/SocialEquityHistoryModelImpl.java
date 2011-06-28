/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.social.model.SocialEquityHistory;
import com.liferay.portlet.social.model.SocialEquityHistoryModel;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the SocialEquityHistory service. Represents a row in the &quot;SocialEquityHistory&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.social.model.SocialEquityHistoryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SocialEquityHistoryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityHistoryImpl
 * @see com.liferay.portlet.social.model.SocialEquityHistory
 * @see com.liferay.portlet.social.model.SocialEquityHistoryModel
 * @generated
 */
public class SocialEquityHistoryModelImpl extends BaseModelImpl<SocialEquityHistory>
	implements SocialEquityHistoryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a social equity history model instance should use the {@link com.liferay.portlet.social.model.SocialEquityHistory} interface instead.
	 */
	public static final String TABLE_NAME = "SocialEquityHistory";
	public static final Object[][] TABLE_COLUMNS = {
			{ "equityHistoryId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "personalEquity", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialEquityHistory (equityHistoryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,personalEquity INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table SocialEquityHistory";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialEquityHistory"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialEquityHistory"),
			true);

	public Class<?> getModelClass() {
		return SocialEquityHistory.class;
	}

	public String getModelClassName() {
		return SocialEquityHistory.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialEquityHistory"));

	public SocialEquityHistoryModelImpl() {
	}

	public long getPrimaryKey() {
		return _equityHistoryId;
	}

	public void setPrimaryKey(long primaryKey) {
		setEquityHistoryId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_equityHistoryId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public long getEquityHistoryId() {
		return _equityHistoryId;
	}

	public void setEquityHistoryId(long equityHistoryId) {
		_equityHistoryId = equityHistoryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public int getPersonalEquity() {
		return _personalEquity;
	}

	public void setPersonalEquity(int personalEquity) {
		_personalEquity = personalEquity;
	}

	@Override
	public SocialEquityHistory toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialEquityHistory)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (SocialEquityHistory)Proxy.newProxyInstance(_classLoader,
						_escapedModelProxyInterfaces,
						new AutoEscapeBeanHandler(this));
			}

			return _escapedModelProxy;
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					SocialEquityHistory.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		SocialEquityHistoryImpl socialEquityHistoryImpl = new SocialEquityHistoryImpl();

		socialEquityHistoryImpl.setEquityHistoryId(getEquityHistoryId());
		socialEquityHistoryImpl.setGroupId(getGroupId());
		socialEquityHistoryImpl.setCompanyId(getCompanyId());
		socialEquityHistoryImpl.setUserId(getUserId());
		socialEquityHistoryImpl.setCreateDate(getCreateDate());
		socialEquityHistoryImpl.setPersonalEquity(getPersonalEquity());

		socialEquityHistoryImpl.resetOriginalValues();

		return socialEquityHistoryImpl;
	}

	public int compareTo(SocialEquityHistory socialEquityHistory) {
		long primaryKey = socialEquityHistory.getPrimaryKey();

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
		if (obj == null) {
			return false;
		}

		SocialEquityHistory socialEquityHistory = null;

		try {
			socialEquityHistory = (SocialEquityHistory)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = socialEquityHistory.getPrimaryKey();

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
	public void resetOriginalValues() {
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{equityHistoryId=");
		sb.append(getEquityHistoryId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", personalEquity=");
		sb.append(getPersonalEquity());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialEquityHistory");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>equityHistoryId</column-name><column-value><![CDATA[");
		sb.append(getEquityHistoryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>personalEquity</column-name><column-value><![CDATA[");
		sb.append(getPersonalEquity());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = SocialEquityHistory.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			SocialEquityHistory.class
		};
	private long _equityHistoryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private Date _createDate;
	private int _personalEquity;
	private transient ExpandoBridge _expandoBridge;
	private SocialEquityHistory _escapedModelProxy;
}