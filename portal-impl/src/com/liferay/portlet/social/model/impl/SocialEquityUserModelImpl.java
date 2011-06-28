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
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.SocialEquityUserModel;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * The base model implementation for the SocialEquityUser service. Represents a row in the &quot;SocialEquityUser&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.social.model.SocialEquityUserModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SocialEquityUserImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityUserImpl
 * @see com.liferay.portlet.social.model.SocialEquityUser
 * @see com.liferay.portlet.social.model.SocialEquityUserModel
 * @generated
 */
public class SocialEquityUserModelImpl extends BaseModelImpl<SocialEquityUser>
	implements SocialEquityUserModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a social equity user model instance should use the {@link com.liferay.portlet.social.model.SocialEquityUser} interface instead.
	 */
	public static final String TABLE_NAME = "SocialEquityUser";
	public static final Object[][] TABLE_COLUMNS = {
			{ "equityUserId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "contributionK", Types.DOUBLE },
			{ "contributionB", Types.DOUBLE },
			{ "participationK", Types.DOUBLE },
			{ "participationB", Types.DOUBLE },
			{ "rank", Types.INTEGER }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialEquityUser (equityUserId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,contributionK DOUBLE,contributionB DOUBLE,participationK DOUBLE,participationB DOUBLE,rank INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table SocialEquityUser";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialEquityUser"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialEquityUser"),
			true);

	public Class<?> getModelClass() {
		return SocialEquityUser.class;
	}

	public String getModelClassName() {
		return SocialEquityUser.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialEquityUser"));

	public SocialEquityUserModelImpl() {
	}

	public long getPrimaryKey() {
		return _equityUserId;
	}

	public void setPrimaryKey(long primaryKey) {
		setEquityUserId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_equityUserId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public long getEquityUserId() {
		return _equityUserId;
	}

	public void setEquityUserId(long equityUserId) {
		_equityUserId = equityUserId;
	}

	public String getEquityUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getEquityUserId(), "uuid",
			_equityUserUuid);
	}

	public void setEquityUserUuid(String equityUserUuid) {
		_equityUserUuid = equityUserUuid;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
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
		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public double getContributionK() {
		return _contributionK;
	}

	public void setContributionK(double contributionK) {
		_contributionK = contributionK;
	}

	public double getContributionB() {
		return _contributionB;
	}

	public void setContributionB(double contributionB) {
		_contributionB = contributionB;
	}

	public double getParticipationK() {
		return _participationK;
	}

	public void setParticipationK(double participationK) {
		_participationK = participationK;
	}

	public double getParticipationB() {
		return _participationB;
	}

	public void setParticipationB(double participationB) {
		_participationB = participationB;
	}

	public int getRank() {
		return _rank;
	}

	public void setRank(int rank) {
		_rank = rank;
	}

	@Override
	public SocialEquityUser toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialEquityUser)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (SocialEquityUser)Proxy.newProxyInstance(_classLoader,
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
					SocialEquityUser.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		SocialEquityUserImpl socialEquityUserImpl = new SocialEquityUserImpl();

		socialEquityUserImpl.setEquityUserId(getEquityUserId());
		socialEquityUserImpl.setGroupId(getGroupId());
		socialEquityUserImpl.setCompanyId(getCompanyId());
		socialEquityUserImpl.setUserId(getUserId());
		socialEquityUserImpl.setContributionK(getContributionK());
		socialEquityUserImpl.setContributionB(getContributionB());
		socialEquityUserImpl.setParticipationK(getParticipationK());
		socialEquityUserImpl.setParticipationB(getParticipationB());
		socialEquityUserImpl.setRank(getRank());

		socialEquityUserImpl.resetOriginalValues();

		return socialEquityUserImpl;
	}

	public int compareTo(SocialEquityUser socialEquityUser) {
		long primaryKey = socialEquityUser.getPrimaryKey();

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

		SocialEquityUser socialEquityUser = null;

		try {
			socialEquityUser = (SocialEquityUser)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = socialEquityUser.getPrimaryKey();

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
		SocialEquityUserModelImpl socialEquityUserModelImpl = this;

		socialEquityUserModelImpl._originalGroupId = socialEquityUserModelImpl._groupId;

		socialEquityUserModelImpl._setOriginalGroupId = false;

		socialEquityUserModelImpl._originalUserId = socialEquityUserModelImpl._userId;

		socialEquityUserModelImpl._setOriginalUserId = false;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{equityUserId=");
		sb.append(getEquityUserId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", contributionK=");
		sb.append(getContributionK());
		sb.append(", contributionB=");
		sb.append(getContributionB());
		sb.append(", participationK=");
		sb.append(getParticipationK());
		sb.append(", participationB=");
		sb.append(getParticipationB());
		sb.append(", rank=");
		sb.append(getRank());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialEquityUser");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>equityUserId</column-name><column-value><![CDATA[");
		sb.append(getEquityUserId());
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
			"<column><column-name>contributionK</column-name><column-value><![CDATA[");
		sb.append(getContributionK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>contributionB</column-name><column-value><![CDATA[");
		sb.append(getContributionB());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>participationK</column-name><column-value><![CDATA[");
		sb.append(getParticipationK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>participationB</column-name><column-value><![CDATA[");
		sb.append(getParticipationB());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>rank</column-name><column-value><![CDATA[");
		sb.append(getRank());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = SocialEquityUser.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			SocialEquityUser.class
		};
	private long _equityUserId;
	private String _equityUserUuid;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private double _contributionK;
	private double _contributionB;
	private double _participationK;
	private double _participationB;
	private int _rank;
	private transient ExpandoBridge _expandoBridge;
	private SocialEquityUser _escapedModelProxy;
}