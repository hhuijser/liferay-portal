/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.PasswordPolicyModel;
import com.liferay.portal.model.PasswordPolicySoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the PasswordPolicy service. Represents a row in the &quot;PasswordPolicy&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.PasswordPolicyModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PasswordPolicyImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyImpl
 * @see com.liferay.portal.model.PasswordPolicy
 * @see com.liferay.portal.model.PasswordPolicyModel
 * @generated
 */
@JSON(strict = true)
public class PasswordPolicyModelImpl extends BaseModelImpl<PasswordPolicy>
	implements PasswordPolicyModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a password policy model instance should use the {@link com.liferay.portal.model.PasswordPolicy} interface instead.
	 */
	public static final String TABLE_NAME = "PasswordPolicy";
	public static final Object[][] TABLE_COLUMNS = {
			{ "passwordPolicyId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "defaultPolicy", Types.BOOLEAN },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "changeable", Types.BOOLEAN },
			{ "changeRequired", Types.BOOLEAN },
			{ "minAge", Types.BIGINT },
			{ "checkSyntax", Types.BOOLEAN },
			{ "allowDictionaryWords", Types.BOOLEAN },
			{ "minAlphanumeric", Types.INTEGER },
			{ "minLength", Types.INTEGER },
			{ "minLowerCase", Types.INTEGER },
			{ "minNumbers", Types.INTEGER },
			{ "minSymbols", Types.INTEGER },
			{ "minUpperCase", Types.INTEGER },
			{ "history", Types.BOOLEAN },
			{ "historyCount", Types.INTEGER },
			{ "expireable", Types.BOOLEAN },
			{ "maxAge", Types.BIGINT },
			{ "warningTime", Types.BIGINT },
			{ "graceLimit", Types.INTEGER },
			{ "lockout", Types.BOOLEAN },
			{ "maxFailure", Types.INTEGER },
			{ "lockoutDuration", Types.BIGINT },
			{ "requireUnlock", Types.BOOLEAN },
			{ "resetFailureCount", Types.BIGINT },
			{ "resetTicketMaxAge", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table PasswordPolicy (passwordPolicyId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,defaultPolicy BOOLEAN,name VARCHAR(75) null,description STRING null,changeable BOOLEAN,changeRequired BOOLEAN,minAge LONG,checkSyntax BOOLEAN,allowDictionaryWords BOOLEAN,minAlphanumeric INTEGER,minLength INTEGER,minLowerCase INTEGER,minNumbers INTEGER,minSymbols INTEGER,minUpperCase INTEGER,history BOOLEAN,historyCount INTEGER,expireable BOOLEAN,maxAge LONG,warningTime LONG,graceLimit INTEGER,lockout BOOLEAN,maxFailure INTEGER,lockoutDuration LONG,requireUnlock BOOLEAN,resetFailureCount LONG,resetTicketMaxAge LONG)";
	public static final String TABLE_SQL_DROP = "drop table PasswordPolicy";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.PasswordPolicy"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.PasswordPolicy"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.PasswordPolicy"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long DEFAULTPOLICY_COLUMN_BITMASK = 2L;
	public static long NAME_COLUMN_BITMASK = 4L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static PasswordPolicy toModel(PasswordPolicySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		PasswordPolicy model = new PasswordPolicyImpl();

		model.setPasswordPolicyId(soapModel.getPasswordPolicyId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setDefaultPolicy(soapModel.getDefaultPolicy());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setChangeable(soapModel.getChangeable());
		model.setChangeRequired(soapModel.getChangeRequired());
		model.setMinAge(soapModel.getMinAge());
		model.setCheckSyntax(soapModel.getCheckSyntax());
		model.setAllowDictionaryWords(soapModel.getAllowDictionaryWords());
		model.setMinAlphanumeric(soapModel.getMinAlphanumeric());
		model.setMinLength(soapModel.getMinLength());
		model.setMinLowerCase(soapModel.getMinLowerCase());
		model.setMinNumbers(soapModel.getMinNumbers());
		model.setMinSymbols(soapModel.getMinSymbols());
		model.setMinUpperCase(soapModel.getMinUpperCase());
		model.setHistory(soapModel.getHistory());
		model.setHistoryCount(soapModel.getHistoryCount());
		model.setExpireable(soapModel.getExpireable());
		model.setMaxAge(soapModel.getMaxAge());
		model.setWarningTime(soapModel.getWarningTime());
		model.setGraceLimit(soapModel.getGraceLimit());
		model.setLockout(soapModel.getLockout());
		model.setMaxFailure(soapModel.getMaxFailure());
		model.setLockoutDuration(soapModel.getLockoutDuration());
		model.setRequireUnlock(soapModel.getRequireUnlock());
		model.setResetFailureCount(soapModel.getResetFailureCount());
		model.setResetTicketMaxAge(soapModel.getResetTicketMaxAge());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<PasswordPolicy> toModels(PasswordPolicySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<PasswordPolicy> models = new ArrayList<PasswordPolicy>(soapModels.length);

		for (PasswordPolicySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PasswordPolicy"));

	public PasswordPolicyModelImpl() {
	}

	public long getPrimaryKey() {
		return _passwordPolicyId;
	}

	public void setPrimaryKey(long primaryKey) {
		setPasswordPolicyId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_passwordPolicyId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return PasswordPolicy.class;
	}

	public String getModelClassName() {
		return PasswordPolicy.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("passwordPolicyId", getPasswordPolicyId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("defaultPolicy", getDefaultPolicy());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("changeable", getChangeable());
		attributes.put("changeRequired", getChangeRequired());
		attributes.put("minAge", getMinAge());
		attributes.put("checkSyntax", getCheckSyntax());
		attributes.put("allowDictionaryWords", getAllowDictionaryWords());
		attributes.put("minAlphanumeric", getMinAlphanumeric());
		attributes.put("minLength", getMinLength());
		attributes.put("minLowerCase", getMinLowerCase());
		attributes.put("minNumbers", getMinNumbers());
		attributes.put("minSymbols", getMinSymbols());
		attributes.put("minUpperCase", getMinUpperCase());
		attributes.put("history", getHistory());
		attributes.put("historyCount", getHistoryCount());
		attributes.put("expireable", getExpireable());
		attributes.put("maxAge", getMaxAge());
		attributes.put("warningTime", getWarningTime());
		attributes.put("graceLimit", getGraceLimit());
		attributes.put("lockout", getLockout());
		attributes.put("maxFailure", getMaxFailure());
		attributes.put("lockoutDuration", getLockoutDuration());
		attributes.put("requireUnlock", getRequireUnlock());
		attributes.put("resetFailureCount", getResetFailureCount());
		attributes.put("resetTicketMaxAge", getResetTicketMaxAge());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long passwordPolicyId = (Long)attributes.get("passwordPolicyId");

		if (passwordPolicyId != null) {
			setPasswordPolicyId(passwordPolicyId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Boolean defaultPolicy = (Boolean)attributes.get("defaultPolicy");

		if (defaultPolicy != null) {
			setDefaultPolicy(defaultPolicy);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean changeable = (Boolean)attributes.get("changeable");

		if (changeable != null) {
			setChangeable(changeable);
		}

		Boolean changeRequired = (Boolean)attributes.get("changeRequired");

		if (changeRequired != null) {
			setChangeRequired(changeRequired);
		}

		Long minAge = (Long)attributes.get("minAge");

		if (minAge != null) {
			setMinAge(minAge);
		}

		Boolean checkSyntax = (Boolean)attributes.get("checkSyntax");

		if (checkSyntax != null) {
			setCheckSyntax(checkSyntax);
		}

		Boolean allowDictionaryWords = (Boolean)attributes.get(
				"allowDictionaryWords");

		if (allowDictionaryWords != null) {
			setAllowDictionaryWords(allowDictionaryWords);
		}

		Integer minAlphanumeric = (Integer)attributes.get("minAlphanumeric");

		if (minAlphanumeric != null) {
			setMinAlphanumeric(minAlphanumeric);
		}

		Integer minLength = (Integer)attributes.get("minLength");

		if (minLength != null) {
			setMinLength(minLength);
		}

		Integer minLowerCase = (Integer)attributes.get("minLowerCase");

		if (minLowerCase != null) {
			setMinLowerCase(minLowerCase);
		}

		Integer minNumbers = (Integer)attributes.get("minNumbers");

		if (minNumbers != null) {
			setMinNumbers(minNumbers);
		}

		Integer minSymbols = (Integer)attributes.get("minSymbols");

		if (minSymbols != null) {
			setMinSymbols(minSymbols);
		}

		Integer minUpperCase = (Integer)attributes.get("minUpperCase");

		if (minUpperCase != null) {
			setMinUpperCase(minUpperCase);
		}

		Boolean history = (Boolean)attributes.get("history");

		if (history != null) {
			setHistory(history);
		}

		Integer historyCount = (Integer)attributes.get("historyCount");

		if (historyCount != null) {
			setHistoryCount(historyCount);
		}

		Boolean expireable = (Boolean)attributes.get("expireable");

		if (expireable != null) {
			setExpireable(expireable);
		}

		Long maxAge = (Long)attributes.get("maxAge");

		if (maxAge != null) {
			setMaxAge(maxAge);
		}

		Long warningTime = (Long)attributes.get("warningTime");

		if (warningTime != null) {
			setWarningTime(warningTime);
		}

		Integer graceLimit = (Integer)attributes.get("graceLimit");

		if (graceLimit != null) {
			setGraceLimit(graceLimit);
		}

		Boolean lockout = (Boolean)attributes.get("lockout");

		if (lockout != null) {
			setLockout(lockout);
		}

		Integer maxFailure = (Integer)attributes.get("maxFailure");

		if (maxFailure != null) {
			setMaxFailure(maxFailure);
		}

		Long lockoutDuration = (Long)attributes.get("lockoutDuration");

		if (lockoutDuration != null) {
			setLockoutDuration(lockoutDuration);
		}

		Boolean requireUnlock = (Boolean)attributes.get("requireUnlock");

		if (requireUnlock != null) {
			setRequireUnlock(requireUnlock);
		}

		Long resetFailureCount = (Long)attributes.get("resetFailureCount");

		if (resetFailureCount != null) {
			setResetFailureCount(resetFailureCount);
		}

		Long resetTicketMaxAge = (Long)attributes.get("resetTicketMaxAge");

		if (resetTicketMaxAge != null) {
			setResetTicketMaxAge(resetTicketMaxAge);
		}
	}

	@JSON
	public long getPasswordPolicyId() {
		return _passwordPolicyId;
	}

	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicyId = passwordPolicyId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

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
	public boolean getDefaultPolicy() {
		return _defaultPolicy;
	}

	public boolean isDefaultPolicy() {
		return _defaultPolicy;
	}

	public void setDefaultPolicy(boolean defaultPolicy) {
		_columnBitmask |= DEFAULTPOLICY_COLUMN_BITMASK;

		if (!_setOriginalDefaultPolicy) {
			_setOriginalDefaultPolicy = true;

			_originalDefaultPolicy = _defaultPolicy;
		}

		_defaultPolicy = defaultPolicy;
	}

	public boolean getOriginalDefaultPolicy() {
		return _originalDefaultPolicy;
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
		_columnBitmask |= NAME_COLUMN_BITMASK;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	@JSON
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	public boolean getChangeable() {
		return _changeable;
	}

	public boolean isChangeable() {
		return _changeable;
	}

	public void setChangeable(boolean changeable) {
		_changeable = changeable;
	}

	@JSON
	public boolean getChangeRequired() {
		return _changeRequired;
	}

	public boolean isChangeRequired() {
		return _changeRequired;
	}

	public void setChangeRequired(boolean changeRequired) {
		_changeRequired = changeRequired;
	}

	@JSON
	public long getMinAge() {
		return _minAge;
	}

	public void setMinAge(long minAge) {
		_minAge = minAge;
	}

	@JSON
	public boolean getCheckSyntax() {
		return _checkSyntax;
	}

	public boolean isCheckSyntax() {
		return _checkSyntax;
	}

	public void setCheckSyntax(boolean checkSyntax) {
		_checkSyntax = checkSyntax;
	}

	@JSON
	public boolean getAllowDictionaryWords() {
		return _allowDictionaryWords;
	}

	public boolean isAllowDictionaryWords() {
		return _allowDictionaryWords;
	}

	public void setAllowDictionaryWords(boolean allowDictionaryWords) {
		_allowDictionaryWords = allowDictionaryWords;
	}

	@JSON
	public int getMinAlphanumeric() {
		return _minAlphanumeric;
	}

	public void setMinAlphanumeric(int minAlphanumeric) {
		_minAlphanumeric = minAlphanumeric;
	}

	@JSON
	public int getMinLength() {
		return _minLength;
	}

	public void setMinLength(int minLength) {
		_minLength = minLength;
	}

	@JSON
	public int getMinLowerCase() {
		return _minLowerCase;
	}

	public void setMinLowerCase(int minLowerCase) {
		_minLowerCase = minLowerCase;
	}

	@JSON
	public int getMinNumbers() {
		return _minNumbers;
	}

	public void setMinNumbers(int minNumbers) {
		_minNumbers = minNumbers;
	}

	@JSON
	public int getMinSymbols() {
		return _minSymbols;
	}

	public void setMinSymbols(int minSymbols) {
		_minSymbols = minSymbols;
	}

	@JSON
	public int getMinUpperCase() {
		return _minUpperCase;
	}

	public void setMinUpperCase(int minUpperCase) {
		_minUpperCase = minUpperCase;
	}

	@JSON
	public boolean getHistory() {
		return _history;
	}

	public boolean isHistory() {
		return _history;
	}

	public void setHistory(boolean history) {
		_history = history;
	}

	@JSON
	public int getHistoryCount() {
		return _historyCount;
	}

	public void setHistoryCount(int historyCount) {
		_historyCount = historyCount;
	}

	@JSON
	public boolean getExpireable() {
		return _expireable;
	}

	public boolean isExpireable() {
		return _expireable;
	}

	public void setExpireable(boolean expireable) {
		_expireable = expireable;
	}

	@JSON
	public long getMaxAge() {
		return _maxAge;
	}

	public void setMaxAge(long maxAge) {
		_maxAge = maxAge;
	}

	@JSON
	public long getWarningTime() {
		return _warningTime;
	}

	public void setWarningTime(long warningTime) {
		_warningTime = warningTime;
	}

	@JSON
	public int getGraceLimit() {
		return _graceLimit;
	}

	public void setGraceLimit(int graceLimit) {
		_graceLimit = graceLimit;
	}

	@JSON
	public boolean getLockout() {
		return _lockout;
	}

	public boolean isLockout() {
		return _lockout;
	}

	public void setLockout(boolean lockout) {
		_lockout = lockout;
	}

	@JSON
	public int getMaxFailure() {
		return _maxFailure;
	}

	public void setMaxFailure(int maxFailure) {
		_maxFailure = maxFailure;
	}

	@JSON
	public long getLockoutDuration() {
		return _lockoutDuration;
	}

	public void setLockoutDuration(long lockoutDuration) {
		_lockoutDuration = lockoutDuration;
	}

	@JSON
	public boolean getRequireUnlock() {
		return _requireUnlock;
	}

	public boolean isRequireUnlock() {
		return _requireUnlock;
	}

	public void setRequireUnlock(boolean requireUnlock) {
		_requireUnlock = requireUnlock;
	}

	@JSON
	public long getResetFailureCount() {
		return _resetFailureCount;
	}

	public void setResetFailureCount(long resetFailureCount) {
		_resetFailureCount = resetFailureCount;
	}

	@JSON
	public long getResetTicketMaxAge() {
		return _resetTicketMaxAge;
	}

	public void setResetTicketMaxAge(long resetTicketMaxAge) {
		_resetTicketMaxAge = resetTicketMaxAge;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			PasswordPolicy.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public PasswordPolicy toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (PasswordPolicy)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public PasswordPolicy toUnescapedModel() {
		if (ProxyUtil.isProxyClass(getClass())) {
			InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(this);

			AutoEscapeBeanHandler autoEscapeBeanHandler = (AutoEscapeBeanHandler)invocationHandler;

			_unescapedModel = (PasswordPolicy)autoEscapeBeanHandler.getBean();
		}
		else {
			_unescapedModel = (PasswordPolicy)this;
		}

		return _unescapedModel;
	}

	@Override
	public Object clone() {
		PasswordPolicyImpl passwordPolicyImpl = new PasswordPolicyImpl();

		passwordPolicyImpl.setPasswordPolicyId(getPasswordPolicyId());
		passwordPolicyImpl.setCompanyId(getCompanyId());
		passwordPolicyImpl.setUserId(getUserId());
		passwordPolicyImpl.setUserName(getUserName());
		passwordPolicyImpl.setCreateDate(getCreateDate());
		passwordPolicyImpl.setModifiedDate(getModifiedDate());
		passwordPolicyImpl.setDefaultPolicy(getDefaultPolicy());
		passwordPolicyImpl.setName(getName());
		passwordPolicyImpl.setDescription(getDescription());
		passwordPolicyImpl.setChangeable(getChangeable());
		passwordPolicyImpl.setChangeRequired(getChangeRequired());
		passwordPolicyImpl.setMinAge(getMinAge());
		passwordPolicyImpl.setCheckSyntax(getCheckSyntax());
		passwordPolicyImpl.setAllowDictionaryWords(getAllowDictionaryWords());
		passwordPolicyImpl.setMinAlphanumeric(getMinAlphanumeric());
		passwordPolicyImpl.setMinLength(getMinLength());
		passwordPolicyImpl.setMinLowerCase(getMinLowerCase());
		passwordPolicyImpl.setMinNumbers(getMinNumbers());
		passwordPolicyImpl.setMinSymbols(getMinSymbols());
		passwordPolicyImpl.setMinUpperCase(getMinUpperCase());
		passwordPolicyImpl.setHistory(getHistory());
		passwordPolicyImpl.setHistoryCount(getHistoryCount());
		passwordPolicyImpl.setExpireable(getExpireable());
		passwordPolicyImpl.setMaxAge(getMaxAge());
		passwordPolicyImpl.setWarningTime(getWarningTime());
		passwordPolicyImpl.setGraceLimit(getGraceLimit());
		passwordPolicyImpl.setLockout(getLockout());
		passwordPolicyImpl.setMaxFailure(getMaxFailure());
		passwordPolicyImpl.setLockoutDuration(getLockoutDuration());
		passwordPolicyImpl.setRequireUnlock(getRequireUnlock());
		passwordPolicyImpl.setResetFailureCount(getResetFailureCount());
		passwordPolicyImpl.setResetTicketMaxAge(getResetTicketMaxAge());

		passwordPolicyImpl.resetOriginalValues();

		return passwordPolicyImpl;
	}

	public int compareTo(PasswordPolicy passwordPolicy) {
		long primaryKey = passwordPolicy.getPrimaryKey();

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

		PasswordPolicy passwordPolicy = null;

		try {
			passwordPolicy = (PasswordPolicy)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = passwordPolicy.getPrimaryKey();

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
		PasswordPolicyModelImpl passwordPolicyModelImpl = this;

		passwordPolicyModelImpl._originalCompanyId = passwordPolicyModelImpl._companyId;

		passwordPolicyModelImpl._setOriginalCompanyId = false;

		passwordPolicyModelImpl._originalDefaultPolicy = passwordPolicyModelImpl._defaultPolicy;

		passwordPolicyModelImpl._setOriginalDefaultPolicy = false;

		passwordPolicyModelImpl._originalName = passwordPolicyModelImpl._name;

		passwordPolicyModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<PasswordPolicy> toCacheModel() {
		PasswordPolicyCacheModel passwordPolicyCacheModel = new PasswordPolicyCacheModel();

		passwordPolicyCacheModel.passwordPolicyId = getPasswordPolicyId();

		passwordPolicyCacheModel.companyId = getCompanyId();

		passwordPolicyCacheModel.userId = getUserId();

		passwordPolicyCacheModel.userName = getUserName();

		String userName = passwordPolicyCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			passwordPolicyCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			passwordPolicyCacheModel.createDate = createDate.getTime();
		}
		else {
			passwordPolicyCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			passwordPolicyCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			passwordPolicyCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		passwordPolicyCacheModel.defaultPolicy = getDefaultPolicy();

		passwordPolicyCacheModel.name = getName();

		String name = passwordPolicyCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			passwordPolicyCacheModel.name = null;
		}

		passwordPolicyCacheModel.description = getDescription();

		String description = passwordPolicyCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			passwordPolicyCacheModel.description = null;
		}

		passwordPolicyCacheModel.changeable = getChangeable();

		passwordPolicyCacheModel.changeRequired = getChangeRequired();

		passwordPolicyCacheModel.minAge = getMinAge();

		passwordPolicyCacheModel.checkSyntax = getCheckSyntax();

		passwordPolicyCacheModel.allowDictionaryWords = getAllowDictionaryWords();

		passwordPolicyCacheModel.minAlphanumeric = getMinAlphanumeric();

		passwordPolicyCacheModel.minLength = getMinLength();

		passwordPolicyCacheModel.minLowerCase = getMinLowerCase();

		passwordPolicyCacheModel.minNumbers = getMinNumbers();

		passwordPolicyCacheModel.minSymbols = getMinSymbols();

		passwordPolicyCacheModel.minUpperCase = getMinUpperCase();

		passwordPolicyCacheModel.history = getHistory();

		passwordPolicyCacheModel.historyCount = getHistoryCount();

		passwordPolicyCacheModel.expireable = getExpireable();

		passwordPolicyCacheModel.maxAge = getMaxAge();

		passwordPolicyCacheModel.warningTime = getWarningTime();

		passwordPolicyCacheModel.graceLimit = getGraceLimit();

		passwordPolicyCacheModel.lockout = getLockout();

		passwordPolicyCacheModel.maxFailure = getMaxFailure();

		passwordPolicyCacheModel.lockoutDuration = getLockoutDuration();

		passwordPolicyCacheModel.requireUnlock = getRequireUnlock();

		passwordPolicyCacheModel.resetFailureCount = getResetFailureCount();

		passwordPolicyCacheModel.resetTicketMaxAge = getResetTicketMaxAge();

		return passwordPolicyCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(65);

		sb.append("{passwordPolicyId=");
		sb.append(getPasswordPolicyId());
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
		sb.append(", defaultPolicy=");
		sb.append(getDefaultPolicy());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", changeable=");
		sb.append(getChangeable());
		sb.append(", changeRequired=");
		sb.append(getChangeRequired());
		sb.append(", minAge=");
		sb.append(getMinAge());
		sb.append(", checkSyntax=");
		sb.append(getCheckSyntax());
		sb.append(", allowDictionaryWords=");
		sb.append(getAllowDictionaryWords());
		sb.append(", minAlphanumeric=");
		sb.append(getMinAlphanumeric());
		sb.append(", minLength=");
		sb.append(getMinLength());
		sb.append(", minLowerCase=");
		sb.append(getMinLowerCase());
		sb.append(", minNumbers=");
		sb.append(getMinNumbers());
		sb.append(", minSymbols=");
		sb.append(getMinSymbols());
		sb.append(", minUpperCase=");
		sb.append(getMinUpperCase());
		sb.append(", history=");
		sb.append(getHistory());
		sb.append(", historyCount=");
		sb.append(getHistoryCount());
		sb.append(", expireable=");
		sb.append(getExpireable());
		sb.append(", maxAge=");
		sb.append(getMaxAge());
		sb.append(", warningTime=");
		sb.append(getWarningTime());
		sb.append(", graceLimit=");
		sb.append(getGraceLimit());
		sb.append(", lockout=");
		sb.append(getLockout());
		sb.append(", maxFailure=");
		sb.append(getMaxFailure());
		sb.append(", lockoutDuration=");
		sb.append(getLockoutDuration());
		sb.append(", requireUnlock=");
		sb.append(getRequireUnlock());
		sb.append(", resetFailureCount=");
		sb.append(getResetFailureCount());
		sb.append(", resetTicketMaxAge=");
		sb.append(getResetTicketMaxAge());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(100);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.PasswordPolicy");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>passwordPolicyId</column-name><column-value><![CDATA[");
		sb.append(getPasswordPolicyId());
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
			"<column><column-name>defaultPolicy</column-name><column-value><![CDATA[");
		sb.append(getDefaultPolicy());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>changeable</column-name><column-value><![CDATA[");
		sb.append(getChangeable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>changeRequired</column-name><column-value><![CDATA[");
		sb.append(getChangeRequired());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minAge</column-name><column-value><![CDATA[");
		sb.append(getMinAge());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>checkSyntax</column-name><column-value><![CDATA[");
		sb.append(getCheckSyntax());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allowDictionaryWords</column-name><column-value><![CDATA[");
		sb.append(getAllowDictionaryWords());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minAlphanumeric</column-name><column-value><![CDATA[");
		sb.append(getMinAlphanumeric());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minLength</column-name><column-value><![CDATA[");
		sb.append(getMinLength());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minLowerCase</column-name><column-value><![CDATA[");
		sb.append(getMinLowerCase());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minNumbers</column-name><column-value><![CDATA[");
		sb.append(getMinNumbers());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minSymbols</column-name><column-value><![CDATA[");
		sb.append(getMinSymbols());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minUpperCase</column-name><column-value><![CDATA[");
		sb.append(getMinUpperCase());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>history</column-name><column-value><![CDATA[");
		sb.append(getHistory());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>historyCount</column-name><column-value><![CDATA[");
		sb.append(getHistoryCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>expireable</column-name><column-value><![CDATA[");
		sb.append(getExpireable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>maxAge</column-name><column-value><![CDATA[");
		sb.append(getMaxAge());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>warningTime</column-name><column-value><![CDATA[");
		sb.append(getWarningTime());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>graceLimit</column-name><column-value><![CDATA[");
		sb.append(getGraceLimit());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lockout</column-name><column-value><![CDATA[");
		sb.append(getLockout());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>maxFailure</column-name><column-value><![CDATA[");
		sb.append(getMaxFailure());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lockoutDuration</column-name><column-value><![CDATA[");
		sb.append(getLockoutDuration());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>requireUnlock</column-name><column-value><![CDATA[");
		sb.append(getRequireUnlock());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>resetFailureCount</column-name><column-value><![CDATA[");
		sb.append(getResetFailureCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>resetTicketMaxAge</column-name><column-value><![CDATA[");
		sb.append(getResetTicketMaxAge());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = PasswordPolicy.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			PasswordPolicy.class
		};
	private long _passwordPolicyId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _defaultPolicy;
	private boolean _originalDefaultPolicy;
	private boolean _setOriginalDefaultPolicy;
	private String _name;
	private String _originalName;
	private String _description;
	private boolean _changeable;
	private boolean _changeRequired;
	private long _minAge;
	private boolean _checkSyntax;
	private boolean _allowDictionaryWords;
	private int _minAlphanumeric;
	private int _minLength;
	private int _minLowerCase;
	private int _minNumbers;
	private int _minSymbols;
	private int _minUpperCase;
	private boolean _history;
	private int _historyCount;
	private boolean _expireable;
	private long _maxAge;
	private long _warningTime;
	private int _graceLimit;
	private boolean _lockout;
	private int _maxFailure;
	private long _lockoutDuration;
	private boolean _requireUnlock;
	private long _resetFailureCount;
	private long _resetTicketMaxAge;
	private long _columnBitmask;
	private PasswordPolicy _escapedModel;
	private PasswordPolicy _unescapedModel;
}