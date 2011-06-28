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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.AccountModel;
import com.liferay.portal.model.AccountSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the Account service. Represents a row in the &quot;Account_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.AccountModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AccountImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountImpl
 * @see com.liferay.portal.model.Account
 * @see com.liferay.portal.model.AccountModel
 * @generated
 */
@JSON(strict = true)
public class AccountModelImpl extends BaseModelImpl<Account>
	implements AccountModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a account model instance should use the {@link com.liferay.portal.model.Account} interface instead.
	 */
	public static final String TABLE_NAME = "Account_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "accountId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "parentAccountId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "legalName", Types.VARCHAR },
			{ "legalId", Types.VARCHAR },
			{ "legalType", Types.VARCHAR },
			{ "sicCode", Types.VARCHAR },
			{ "tickerSymbol", Types.VARCHAR },
			{ "industry", Types.VARCHAR },
			{ "type_", Types.VARCHAR },
			{ "size_", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table Account_ (accountId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentAccountId LONG,name VARCHAR(75) null,legalName VARCHAR(75) null,legalId VARCHAR(75) null,legalType VARCHAR(75) null,sicCode VARCHAR(75) null,tickerSymbol VARCHAR(75) null,industry VARCHAR(75) null,type_ VARCHAR(75) null,size_ VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table Account_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Account"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Account"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Account toModel(AccountSoap soapModel) {
		Account model = new AccountImpl();

		model.setAccountId(soapModel.getAccountId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setParentAccountId(soapModel.getParentAccountId());
		model.setName(soapModel.getName());
		model.setLegalName(soapModel.getLegalName());
		model.setLegalId(soapModel.getLegalId());
		model.setLegalType(soapModel.getLegalType());
		model.setSicCode(soapModel.getSicCode());
		model.setTickerSymbol(soapModel.getTickerSymbol());
		model.setIndustry(soapModel.getIndustry());
		model.setType(soapModel.getType());
		model.setSize(soapModel.getSize());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Account> toModels(AccountSoap[] soapModels) {
		List<Account> models = new ArrayList<Account>(soapModels.length);

		for (AccountSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public Class<?> getModelClass() {
		return Account.class;
	}

	public String getModelClassName() {
		return Account.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Account"));

	public AccountModelImpl() {
	}

	public long getPrimaryKey() {
		return _accountId;
	}

	public void setPrimaryKey(long primaryKey) {
		setAccountId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_accountId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@JSON
	public long getAccountId() {
		return _accountId;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
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

	@JSON
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@JSON
	public long getParentAccountId() {
		return _parentAccountId;
	}

	public void setParentAccountId(long parentAccountId) {
		_parentAccountId = parentAccountId;
	}

	@JSON
	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	@JSON
	public String getLegalName() {
		if (_legalName == null) {
			return StringPool.BLANK;
		}
		else {
			return _legalName;
		}
	}

	public void setLegalName(String legalName) {
		_legalName = legalName;
	}

	@JSON
	public String getLegalId() {
		if (_legalId == null) {
			return StringPool.BLANK;
		}
		else {
			return _legalId;
		}
	}

	public void setLegalId(String legalId) {
		_legalId = legalId;
	}

	@JSON
	public String getLegalType() {
		if (_legalType == null) {
			return StringPool.BLANK;
		}
		else {
			return _legalType;
		}
	}

	public void setLegalType(String legalType) {
		_legalType = legalType;
	}

	@JSON
	public String getSicCode() {
		if (_sicCode == null) {
			return StringPool.BLANK;
		}
		else {
			return _sicCode;
		}
	}

	public void setSicCode(String sicCode) {
		_sicCode = sicCode;
	}

	@JSON
	public String getTickerSymbol() {
		if (_tickerSymbol == null) {
			return StringPool.BLANK;
		}
		else {
			return _tickerSymbol;
		}
	}

	public void setTickerSymbol(String tickerSymbol) {
		_tickerSymbol = tickerSymbol;
	}

	@JSON
	public String getIndustry() {
		if (_industry == null) {
			return StringPool.BLANK;
		}
		else {
			return _industry;
		}
	}

	public void setIndustry(String industry) {
		_industry = industry;
	}

	@JSON
	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;
	}

	@JSON
	public String getSize() {
		if (_size == null) {
			return StringPool.BLANK;
		}
		else {
			return _size;
		}
	}

	public void setSize(String size) {
		_size = size;
	}

	@Override
	public Account toEscapedModel() {
		if (isEscapedModel()) {
			return (Account)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (Account)Proxy.newProxyInstance(_classLoader,
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
					Account.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		AccountImpl accountImpl = new AccountImpl();

		accountImpl.setAccountId(getAccountId());
		accountImpl.setCompanyId(getCompanyId());
		accountImpl.setUserId(getUserId());
		accountImpl.setUserName(getUserName());
		accountImpl.setCreateDate(getCreateDate());
		accountImpl.setModifiedDate(getModifiedDate());
		accountImpl.setParentAccountId(getParentAccountId());
		accountImpl.setName(getName());
		accountImpl.setLegalName(getLegalName());
		accountImpl.setLegalId(getLegalId());
		accountImpl.setLegalType(getLegalType());
		accountImpl.setSicCode(getSicCode());
		accountImpl.setTickerSymbol(getTickerSymbol());
		accountImpl.setIndustry(getIndustry());
		accountImpl.setType(getType());
		accountImpl.setSize(getSize());

		accountImpl.resetOriginalValues();

		return accountImpl;
	}

	public int compareTo(Account account) {
		long primaryKey = account.getPrimaryKey();

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

		Account account = null;

		try {
			account = (Account)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = account.getPrimaryKey();

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
		StringBundler sb = new StringBundler(33);

		sb.append("{accountId=");
		sb.append(getAccountId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", parentAccountId=");
		sb.append(getParentAccountId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", legalName=");
		sb.append(getLegalName());
		sb.append(", legalId=");
		sb.append(getLegalId());
		sb.append(", legalType=");
		sb.append(getLegalType());
		sb.append(", sicCode=");
		sb.append(getSicCode());
		sb.append(", tickerSymbol=");
		sb.append(getTickerSymbol());
		sb.append(", industry=");
		sb.append(getIndustry());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", size=");
		sb.append(getSize());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Account");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>accountId</column-name><column-value><![CDATA[");
		sb.append(getAccountId());
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
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentAccountId</column-name><column-value><![CDATA[");
		sb.append(getParentAccountId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>legalName</column-name><column-value><![CDATA[");
		sb.append(getLegalName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>legalId</column-name><column-value><![CDATA[");
		sb.append(getLegalId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>legalType</column-name><column-value><![CDATA[");
		sb.append(getLegalType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sicCode</column-name><column-value><![CDATA[");
		sb.append(getSicCode());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tickerSymbol</column-name><column-value><![CDATA[");
		sb.append(getTickerSymbol());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>industry</column-name><column-value><![CDATA[");
		sb.append(getIndustry());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>size</column-name><column-value><![CDATA[");
		sb.append(getSize());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Account.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			Account.class
		};
	private long _accountId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _parentAccountId;
	private String _name;
	private String _legalName;
	private String _legalId;
	private String _legalType;
	private String _sicCode;
	private String _tickerSymbol;
	private String _industry;
	private String _type;
	private String _size;
	private transient ExpandoBridge _expandoBridge;
	private Account _escapedModelProxy;
}