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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DDMContent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMContent
 * @generated
 */
@ProviderType
public class DDMContentWrapper implements DDMContent, ModelWrapper<DDMContent> {
	public DDMContentWrapper(DDMContent ddmContent) {
		_ddmContent = ddmContent;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMContent.class;
	}

	@Override
	public String getModelClassName() {
		return DDMContent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("contentId", getContentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("data", getData());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long contentId = (Long)attributes.get("contentId");

		if (contentId != null) {
			setContentId(contentId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}
	}

	@Override
	public Object clone() {
		return new DDMContentWrapper((DDMContent)_ddmContent.clone());
	}

	@Override
	public int compareTo(DDMContent ddmContent) {
		return _ddmContent.compareTo(ddmContent);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _ddmContent.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this ddm content.
	*
	* @return the company ID of this ddm content
	*/
	@Override
	public long getCompanyId() {
		return _ddmContent.getCompanyId();
	}

	/**
	* Returns the content ID of this ddm content.
	*
	* @return the content ID of this ddm content
	*/
	@Override
	public long getContentId() {
		return _ddmContent.getContentId();
	}

	/**
	* Returns the create date of this ddm content.
	*
	* @return the create date of this ddm content
	*/
	@Override
	public Date getCreateDate() {
		return _ddmContent.getCreateDate();
	}

	/**
	* Returns the data of this ddm content.
	*
	* @return the data of this ddm content
	*/
	@Override
	public String getData() {
		return _ddmContent.getData();
	}

	@Override
	public String getDefaultLanguageId() {
		return _ddmContent.getDefaultLanguageId();
	}

	/**
	* Returns the description of this ddm content.
	*
	* @return the description of this ddm content
	*/
	@Override
	public String getDescription() {
		return _ddmContent.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmContent.getExpandoBridge();
	}

	/**
	* Returns the group ID of this ddm content.
	*
	* @return the group ID of this ddm content
	*/
	@Override
	public long getGroupId() {
		return _ddmContent.getGroupId();
	}

	/**
	* Returns the modified date of this ddm content.
	*
	* @return the modified date of this ddm content
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmContent.getModifiedDate();
	}

	/**
	* Returns the name of this ddm content.
	*
	* @return the name of this ddm content
	*/
	@Override
	public String getName() {
		return _ddmContent.getName();
	}

	/**
	* Returns the localized name of this ddm content in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this ddm content
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return _ddmContent.getName(locale);
	}

	/**
	* Returns the localized name of this ddm content in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm content. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmContent.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this ddm content in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this ddm content
	*/
	@Override
	public String getName(String languageId) {
		return _ddmContent.getName(languageId);
	}

	/**
	* Returns the localized name of this ddm content in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm content
	*/
	@Override
	public String getName(String languageId, boolean useDefault) {
		return _ddmContent.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _ddmContent.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _ddmContent.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this ddm content.
	*
	* @return the locales and localized names of this ddm content
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _ddmContent.getNameMap();
	}

	/**
	* Returns the primary key of this ddm content.
	*
	* @return the primary key of this ddm content
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmContent.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmContent.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this ddm content.
	*
	* @return the user ID of this ddm content
	*/
	@Override
	public long getUserId() {
		return _ddmContent.getUserId();
	}

	/**
	* Returns the user name of this ddm content.
	*
	* @return the user name of this ddm content
	*/
	@Override
	public String getUserName() {
		return _ddmContent.getUserName();
	}

	/**
	* Returns the user uuid of this ddm content.
	*
	* @return the user uuid of this ddm content
	*/
	@Override
	public String getUserUuid() {
		return _ddmContent.getUserUuid();
	}

	/**
	* Returns the uuid of this ddm content.
	*
	* @return the uuid of this ddm content
	*/
	@Override
	public String getUuid() {
		return _ddmContent.getUuid();
	}

	@Override
	public int hashCode() {
		return _ddmContent.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmContent.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmContent.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmContent.isNew();
	}

	@Override
	public void persist() {
		_ddmContent.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddmContent.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddmContent.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmContent.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this ddm content.
	*
	* @param companyId the company ID of this ddm content
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmContent.setCompanyId(companyId);
	}

	/**
	* Sets the content ID of this ddm content.
	*
	* @param contentId the content ID of this ddm content
	*/
	@Override
	public void setContentId(long contentId) {
		_ddmContent.setContentId(contentId);
	}

	/**
	* Sets the create date of this ddm content.
	*
	* @param createDate the create date of this ddm content
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmContent.setCreateDate(createDate);
	}

	/**
	* Sets the data of this ddm content.
	*
	* @param data the data of this ddm content
	*/
	@Override
	public void setData(String data) {
		_ddmContent.setData(data);
	}

	/**
	* Sets the description of this ddm content.
	*
	* @param description the description of this ddm content
	*/
	@Override
	public void setDescription(String description) {
		_ddmContent.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddmContent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmContent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmContent.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this ddm content.
	*
	* @param groupId the group ID of this ddm content
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmContent.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this ddm content.
	*
	* @param modifiedDate the modified date of this ddm content
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmContent.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this ddm content.
	*
	* @param name the name of this ddm content
	*/
	@Override
	public void setName(String name) {
		_ddmContent.setName(name);
	}

	/**
	* Sets the localized name of this ddm content in the language.
	*
	* @param name the localized name of this ddm content
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		_ddmContent.setName(name, locale);
	}

	/**
	* Sets the localized name of this ddm content in the language, and sets the default locale.
	*
	* @param name the localized name of this ddm content
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmContent.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_ddmContent.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this ddm content from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this ddm content
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_ddmContent.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this ddm content from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this ddm content
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmContent.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmContent.setNew(n);
	}

	/**
	* Sets the primary key of this ddm content.
	*
	* @param primaryKey the primary key of this ddm content
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmContent.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmContent.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this ddm content.
	*
	* @param userId the user ID of this ddm content
	*/
	@Override
	public void setUserId(long userId) {
		_ddmContent.setUserId(userId);
	}

	/**
	* Sets the user name of this ddm content.
	*
	* @param userName the user name of this ddm content
	*/
	@Override
	public void setUserName(String userName) {
		_ddmContent.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddm content.
	*
	* @param userUuid the user uuid of this ddm content
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_ddmContent.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this ddm content.
	*
	* @param uuid the uuid of this ddm content
	*/
	@Override
	public void setUuid(String uuid) {
		_ddmContent.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDMContent> toCacheModel() {
		return _ddmContent.toCacheModel();
	}

	@Override
	public DDMContent toEscapedModel() {
		return new DDMContentWrapper(_ddmContent.toEscapedModel());
	}

	@Override
	public String toString() {
		return _ddmContent.toString();
	}

	@Override
	public DDMContent toUnescapedModel() {
		return new DDMContentWrapper(_ddmContent.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _ddmContent.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMContentWrapper)) {
			return false;
		}

		DDMContentWrapper ddmContentWrapper = (DDMContentWrapper)obj;

		if (Objects.equals(_ddmContent, ddmContentWrapper._ddmContent)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmContent.getStagedModelType();
	}

	@Override
	public DDMContent getWrappedModel() {
		return _ddmContent;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmContent.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmContent.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmContent.resetOriginalValues();
	}

	private final DDMContent _ddmContent;
}