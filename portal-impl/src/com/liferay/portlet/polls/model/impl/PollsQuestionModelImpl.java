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

package com.liferay.portlet.polls.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsQuestionModel;
import com.liferay.portlet.polls.model.PollsQuestionSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The base model implementation for the PollsQuestion service. Represents a row in the &quot;PollsQuestion&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.polls.model.PollsQuestionModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PollsQuestionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsQuestionImpl
 * @see com.liferay.portlet.polls.model.PollsQuestion
 * @see com.liferay.portlet.polls.model.PollsQuestionModel
 * @generated
 */
@JSON(strict = true)
public class PollsQuestionModelImpl extends BaseModelImpl<PollsQuestion>
	implements PollsQuestionModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a polls question model instance should use the {@link com.liferay.portlet.polls.model.PollsQuestion} interface instead.
	 */
	public static final String TABLE_NAME = "PollsQuestion";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "questionId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "title", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "expirationDate", Types.TIMESTAMP },
			{ "lastVoteDate", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table PollsQuestion (uuid_ VARCHAR(75) null,questionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(255) null,createDate DATE null,modifiedDate DATE null,title STRING null,description STRING null,expirationDate DATE null,lastVoteDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table PollsQuestion";
	public static final String ORDER_BY_JPQL = " ORDER BY pollsQuestion.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY PollsQuestion.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.polls.model.PollsQuestion"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.polls.model.PollsQuestion"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static PollsQuestion toModel(PollsQuestionSoap soapModel) {
		PollsQuestion model = new PollsQuestionImpl();

		model.setUuid(soapModel.getUuid());
		model.setQuestionId(soapModel.getQuestionId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setExpirationDate(soapModel.getExpirationDate());
		model.setLastVoteDate(soapModel.getLastVoteDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<PollsQuestion> toModels(PollsQuestionSoap[] soapModels) {
		List<PollsQuestion> models = new ArrayList<PollsQuestion>(soapModels.length);

		for (PollsQuestionSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public Class<?> getModelClass() {
		return PollsQuestion.class;
	}

	public String getModelClassName() {
		return PollsQuestion.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.polls.model.PollsQuestion"));

	public PollsQuestionModelImpl() {
	}

	public long getPrimaryKey() {
		return _questionId;
	}

	public void setPrimaryKey(long primaryKey) {
		setQuestionId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_questionId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@JSON
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	public long getQuestionId() {
		return _questionId;
	}

	public void setQuestionId(long questionId) {
		_questionId = questionId;
	}

	@JSON
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
	public String getTitle() {
		if (_title == null) {
			return StringPool.BLANK;
		}
		else {
			return _title;
		}
	}

	public String getTitle(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId);
	}

	public String getTitle(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId, useDefault);
	}

	public String getTitle(String languageId) {
		String value = LocalizationUtil.getLocalization(getTitle(), languageId);

		if (isEscapedModel()) {
			return HtmlUtil.escape(value);
		}
		else {
			return value;
		}
	}

	public String getTitle(String languageId, boolean useDefault) {
		String value = LocalizationUtil.getLocalization(getTitle(), languageId,
				useDefault);

		if (isEscapedModel()) {
			return HtmlUtil.escape(value);
		}
		else {
			return value;
		}
	}

	public Map<Locale, String> getTitleMap() {
		return LocalizationUtil.getLocalizationMap(getTitle());
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setTitle(String title, Locale locale) {
		setTitle(title, locale, LocaleUtil.getDefault());
	}

	public void setTitle(String title, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(title)) {
			setTitle(LocalizationUtil.updateLocalization(getTitle(), "Title",
					title, languageId, defaultLanguageId));
		}
		else {
			setTitle(LocalizationUtil.removeLocalization(getTitle(), "Title",
					languageId));
		}
	}

	public void setTitleMap(Map<Locale, String> titleMap) {
		setTitleMap(titleMap, LocaleUtil.getDefault());
	}

	public void setTitleMap(Map<Locale, String> titleMap, Locale defaultLocale) {
		if (titleMap == null) {
			return;
		}

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String title = titleMap.get(locale);

			setTitle(title, locale, defaultLocale);
		}
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

	public String getDescription(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId);
	}

	public String getDescription(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId, useDefault);
	}

	public String getDescription(String languageId) {
		String value = LocalizationUtil.getLocalization(getDescription(),
				languageId);

		if (isEscapedModel()) {
			return HtmlUtil.escape(value);
		}
		else {
			return value;
		}
	}

	public String getDescription(String languageId, boolean useDefault) {
		String value = LocalizationUtil.getLocalization(getDescription(),
				languageId, useDefault);

		if (isEscapedModel()) {
			return HtmlUtil.escape(value);
		}
		else {
			return value;
		}
	}

	public Map<Locale, String> getDescriptionMap() {
		return LocalizationUtil.getLocalizationMap(getDescription());
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDescription(String description, Locale locale) {
		setDescription(description, locale, LocaleUtil.getDefault());
	}

	public void setDescription(String description, Locale locale,
		Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(description)) {
			setDescription(LocalizationUtil.updateLocalization(
					getDescription(), "Description", description, languageId,
					defaultLanguageId));
		}
		else {
			setDescription(LocalizationUtil.removeLocalization(
					getDescription(), "Description", languageId));
		}
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		setDescriptionMap(descriptionMap, LocaleUtil.getDefault());
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale) {
		if (descriptionMap == null) {
			return;
		}

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String description = descriptionMap.get(locale);

			setDescription(description, locale, defaultLocale);
		}
	}

	@JSON
	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	@JSON
	public Date getLastVoteDate() {
		return _lastVoteDate;
	}

	public void setLastVoteDate(Date lastVoteDate) {
		_lastVoteDate = lastVoteDate;
	}

	@Override
	public PollsQuestion toEscapedModel() {
		if (isEscapedModel()) {
			return (PollsQuestion)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (PollsQuestion)Proxy.newProxyInstance(_classLoader,
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
					PollsQuestion.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		PollsQuestionImpl pollsQuestionImpl = new PollsQuestionImpl();

		pollsQuestionImpl.setUuid(getUuid());
		pollsQuestionImpl.setQuestionId(getQuestionId());
		pollsQuestionImpl.setGroupId(getGroupId());
		pollsQuestionImpl.setCompanyId(getCompanyId());
		pollsQuestionImpl.setUserId(getUserId());
		pollsQuestionImpl.setUserName(getUserName());
		pollsQuestionImpl.setCreateDate(getCreateDate());
		pollsQuestionImpl.setModifiedDate(getModifiedDate());
		pollsQuestionImpl.setTitle(getTitle());
		pollsQuestionImpl.setDescription(getDescription());
		pollsQuestionImpl.setExpirationDate(getExpirationDate());
		pollsQuestionImpl.setLastVoteDate(getLastVoteDate());

		pollsQuestionImpl.resetOriginalValues();

		return pollsQuestionImpl;
	}

	public int compareTo(PollsQuestion pollsQuestion) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				pollsQuestion.getCreateDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PollsQuestion pollsQuestion = null;

		try {
			pollsQuestion = (PollsQuestion)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = pollsQuestion.getPrimaryKey();

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
		PollsQuestionModelImpl pollsQuestionModelImpl = this;

		pollsQuestionModelImpl._originalUuid = pollsQuestionModelImpl._uuid;

		pollsQuestionModelImpl._originalGroupId = pollsQuestionModelImpl._groupId;

		pollsQuestionModelImpl._setOriginalGroupId = false;
	}

	@Override
	public CacheModel<PollsQuestion> toCacheModel() {
		PollsQuestionCacheModel pollsQuestionCacheModel = new PollsQuestionCacheModel();

		pollsQuestionCacheModel.uuid = getUuid();

		String uuid = pollsQuestionCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			pollsQuestionCacheModel.uuid = null;
		}

		pollsQuestionCacheModel.questionId = getQuestionId();

		pollsQuestionCacheModel.groupId = getGroupId();

		pollsQuestionCacheModel.companyId = getCompanyId();

		pollsQuestionCacheModel.userId = getUserId();

		pollsQuestionCacheModel.userName = getUserName();

		String userName = pollsQuestionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			pollsQuestionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			pollsQuestionCacheModel.createDate = createDate.getTime();
		}
		else {
			pollsQuestionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			pollsQuestionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			pollsQuestionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		pollsQuestionCacheModel.title = getTitle();

		String title = pollsQuestionCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			pollsQuestionCacheModel.title = null;
		}

		pollsQuestionCacheModel.description = getDescription();

		String description = pollsQuestionCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			pollsQuestionCacheModel.description = null;
		}

		Date expirationDate = getExpirationDate();

		if (expirationDate != null) {
			pollsQuestionCacheModel.expirationDate = expirationDate.getTime();
		}
		else {
			pollsQuestionCacheModel.expirationDate = Long.MIN_VALUE;
		}

		Date lastVoteDate = getLastVoteDate();

		if (lastVoteDate != null) {
			pollsQuestionCacheModel.lastVoteDate = lastVoteDate.getTime();
		}
		else {
			pollsQuestionCacheModel.lastVoteDate = Long.MIN_VALUE;
		}

		return pollsQuestionCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", questionId=");
		sb.append(getQuestionId());
		sb.append(", groupId=");
		sb.append(getGroupId());
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
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", expirationDate=");
		sb.append(getExpirationDate());
		sb.append(", lastVoteDate=");
		sb.append(getLastVoteDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.polls.model.PollsQuestion");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>questionId</column-name><column-value><![CDATA[");
		sb.append(getQuestionId());
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
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>expirationDate</column-name><column-value><![CDATA[");
		sb.append(getExpirationDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastVoteDate</column-name><column-value><![CDATA[");
		sb.append(getLastVoteDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = PollsQuestion.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			PollsQuestion.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _questionId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private Date _expirationDate;
	private Date _lastVoteDate;
	private transient ExpandoBridge _expandoBridge;
	private PollsQuestion _escapedModelProxy;
}