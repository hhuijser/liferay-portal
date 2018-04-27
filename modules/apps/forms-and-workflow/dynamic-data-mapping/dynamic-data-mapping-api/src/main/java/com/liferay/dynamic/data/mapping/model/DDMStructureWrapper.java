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
 * This class is a wrapper for {@link DDMStructure}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructure
 * @generated
 */
@ProviderType
public class DDMStructureWrapper implements DDMStructure,
	ModelWrapper<DDMStructure> {
	public DDMStructureWrapper(DDMStructure ddmStructure) {
		_ddmStructure = ddmStructure;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMStructure.class;
	}

	@Override
	public String getModelClassName() {
		return DDMStructure.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("structureId", getStructureId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("versionUserId", getVersionUserId());
		attributes.put("versionUserName", getVersionUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentStructureId", getParentStructureId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("structureKey", getStructureKey());
		attributes.put("version", getVersion());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("definition", getDefinition());
		attributes.put("storageType", getStorageType());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
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

		Long versionUserId = (Long)attributes.get("versionUserId");

		if (versionUserId != null) {
			setVersionUserId(versionUserId);
		}

		String versionUserName = (String)attributes.get("versionUserName");

		if (versionUserName != null) {
			setVersionUserName(versionUserName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long parentStructureId = (Long)attributes.get("parentStructureId");

		if (parentStructureId != null) {
			setParentStructureId(parentStructureId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String structureKey = (String)attributes.get("structureKey");

		if (structureKey != null) {
			setStructureKey(structureKey);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String definition = (String)attributes.get("definition");

		if (definition != null) {
			setDefinition(definition);
		}

		String storageType = (String)attributes.get("storageType");

		if (storageType != null) {
			setStorageType(storageType);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new DDMStructureWrapper((DDMStructure)_ddmStructure.clone());
	}

	@Override
	public int compareTo(DDMStructure ddmStructure) {
		return _ddmStructure.compareTo(ddmStructure);
	}

	@Override
	public DDMForm createFullHierarchyDDMForm()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.createFullHierarchyDDMForm();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _ddmStructure.getAvailableLanguageIds();
	}

	@Override
	public java.util.List<String> getChildrenFieldNames(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getChildrenFieldNames(fieldName);
	}

	/**
	* Returns the fully qualified class name of this ddm structure.
	*
	* @return the fully qualified class name of this ddm structure
	*/
	@Override
	public String getClassName() {
		return _ddmStructure.getClassName();
	}

	/**
	* Returns the class name ID of this ddm structure.
	*
	* @return the class name ID of this ddm structure
	*/
	@Override
	public long getClassNameId() {
		return _ddmStructure.getClassNameId();
	}

	/**
	* Returns the company ID of this ddm structure.
	*
	* @return the company ID of this ddm structure
	*/
	@Override
	public long getCompanyId() {
		return _ddmStructure.getCompanyId();
	}

	/**
	* Returns the create date of this ddm structure.
	*
	* @return the create date of this ddm structure
	*/
	@Override
	public Date getCreateDate() {
		return _ddmStructure.getCreateDate();
	}

	@Override
	public DDMForm getDDMForm() {
		return _ddmStructure.getDDMForm();
	}

	@Override
	public DDMFormField getDDMFormField(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getDDMFormField(fieldName);
	}

	@Override
	public java.util.List<DDMFormField> getDDMFormFields(
		boolean includeTransientFields) {
		return _ddmStructure.getDDMFormFields(includeTransientFields);
	}

	@Override
	public DDMFormLayout getDDMFormLayout()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getDDMFormLayout();
	}

	@Override
	public String getDefaultLanguageId() {
		return _ddmStructure.getDefaultLanguageId();
	}

	/**
	* Returns the definition of this ddm structure.
	*
	* @return the definition of this ddm structure
	*/
	@Override
	public String getDefinition() {
		return _ddmStructure.getDefinition();
	}

	/**
	* Returns the description of this ddm structure.
	*
	* @return the description of this ddm structure
	*/
	@Override
	public String getDescription() {
		return _ddmStructure.getDescription();
	}

	/**
	* Returns the localized description of this ddm structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this ddm structure
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return _ddmStructure.getDescription(locale);
	}

	/**
	* Returns the localized description of this ddm structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this ddm structure. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return _ddmStructure.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this ddm structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this ddm structure
	*/
	@Override
	public String getDescription(String languageId) {
		return _ddmStructure.getDescription(languageId);
	}

	/**
	* Returns the localized description of this ddm structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this ddm structure
	*/
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _ddmStructure.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _ddmStructure.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _ddmStructure.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this ddm structure.
	*
	* @return the locales and localized descriptions of this ddm structure
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _ddmStructure.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmStructure.getExpandoBridge();
	}

	@Override
	public String getFieldDataType(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldDataType(fieldName);
	}

	@Override
	public String getFieldLabel(String fieldName, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldLabel(fieldName, locale);
	}

	@Override
	public String getFieldLabel(String fieldName, String locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldLabel(fieldName, locale);
	}

	@Override
	public java.util.Set<String> getFieldNames() {
		return _ddmStructure.getFieldNames();
	}

	@Override
	public String getFieldProperty(String fieldName, String property)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldProperty(fieldName, property);
	}

	@Override
	public boolean getFieldRepeatable(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldRepeatable(fieldName);
	}

	@Override
	public boolean getFieldRequired(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldRequired(fieldName);
	}

	@Override
	public String getFieldTip(String fieldName, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldTip(fieldName, locale);
	}

	@Override
	public String getFieldTip(String fieldName, String locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldTip(fieldName, locale);
	}

	@Override
	public String getFieldType(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getFieldType(fieldName);
	}

	@Override
	public DDMForm getFullHierarchyDDMForm() {
		return _ddmStructure.getFullHierarchyDDMForm();
	}

	@Override
	public Map<String, DDMFormField> getFullHierarchyDDMFormFieldsMap(
		boolean includeNestedDDMFormFields) {
		return _ddmStructure.getFullHierarchyDDMFormFieldsMap(includeNestedDDMFormFields);
	}

	/**
	* Returns the group ID of this ddm structure.
	*
	* @return the group ID of this ddm structure
	*/
	@Override
	public long getGroupId() {
		return _ddmStructure.getGroupId();
	}

	/**
	* Returns the last publish date of this ddm structure.
	*
	* @return the last publish date of this ddm structure
	*/
	@Override
	public Date getLastPublishDate() {
		return _ddmStructure.getLastPublishDate();
	}

	@Override
	public DDMStructureVersion getLatestStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getLatestStructureVersion();
	}

	/**
	* Returns the modified date of this ddm structure.
	*
	* @return the modified date of this ddm structure
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmStructure.getModifiedDate();
	}

	/**
	* Returns the name of this ddm structure.
	*
	* @return the name of this ddm structure
	*/
	@Override
	public String getName() {
		return _ddmStructure.getName();
	}

	/**
	* Returns the localized name of this ddm structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this ddm structure
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return _ddmStructure.getName(locale);
	}

	/**
	* Returns the localized name of this ddm structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm structure. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return _ddmStructure.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this ddm structure in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this ddm structure
	*/
	@Override
	public String getName(String languageId) {
		return _ddmStructure.getName(languageId);
	}

	/**
	* Returns the localized name of this ddm structure in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this ddm structure
	*/
	@Override
	public String getName(String languageId, boolean useDefault) {
		return _ddmStructure.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _ddmStructure.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _ddmStructure.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this ddm structure.
	*
	* @return the locales and localized names of this ddm structure
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _ddmStructure.getNameMap();
	}

	/**
	* Returns the parent structure ID of this ddm structure.
	*
	* @return the parent structure ID of this ddm structure
	*/
	@Override
	public long getParentStructureId() {
		return _ddmStructure.getParentStructureId();
	}

	/**
	* Returns the primary key of this ddm structure.
	*
	* @return the primary key of this ddm structure
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmStructure.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmStructure.getPrimaryKeyObj();
	}

	@Override
	public java.util.List<String> getRootFieldNames() {
		return _ddmStructure.getRootFieldNames();
	}

	/**
	* Returns the storage type of this ddm structure.
	*
	* @return the storage type of this ddm structure
	*/
	@Override
	public String getStorageType() {
		return _ddmStructure.getStorageType();
	}

	/**
	* Returns the structure ID of this ddm structure.
	*
	* @return the structure ID of this ddm structure
	*/
	@Override
	public long getStructureId() {
		return _ddmStructure.getStructureId();
	}

	/**
	* Returns the structure key of this ddm structure.
	*
	* @return the structure key of this ddm structure
	*/
	@Override
	public String getStructureKey() {
		return _ddmStructure.getStructureKey();
	}

	@Override
	public DDMStructureVersion getStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getStructureVersion();
	}

	@Override
	public java.util.List<DDMTemplate> getTemplates() {
		return _ddmStructure.getTemplates();
	}

	/**
	* Returns the type of this ddm structure.
	*
	* @return the type of this ddm structure
	*/
	@Override
	public int getType() {
		return _ddmStructure.getType();
	}

	@Override
	public String getUnambiguousName(java.util.List<DDMStructure> structures,
		long groupId, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.getUnambiguousName(structures, groupId, locale);
	}

	/**
	* Returns the user ID of this ddm structure.
	*
	* @return the user ID of this ddm structure
	*/
	@Override
	public long getUserId() {
		return _ddmStructure.getUserId();
	}

	/**
	* Returns the user name of this ddm structure.
	*
	* @return the user name of this ddm structure
	*/
	@Override
	public String getUserName() {
		return _ddmStructure.getUserName();
	}

	/**
	* Returns the user uuid of this ddm structure.
	*
	* @return the user uuid of this ddm structure
	*/
	@Override
	public String getUserUuid() {
		return _ddmStructure.getUserUuid();
	}

	/**
	* Returns the uuid of this ddm structure.
	*
	* @return the uuid of this ddm structure
	*/
	@Override
	public String getUuid() {
		return _ddmStructure.getUuid();
	}

	/**
	* Returns the version of this ddm structure.
	*
	* @return the version of this ddm structure
	*/
	@Override
	public String getVersion() {
		return _ddmStructure.getVersion();
	}

	/**
	* Returns the version user ID of this ddm structure.
	*
	* @return the version user ID of this ddm structure
	*/
	@Override
	public long getVersionUserId() {
		return _ddmStructure.getVersionUserId();
	}

	/**
	* Returns the version user name of this ddm structure.
	*
	* @return the version user name of this ddm structure
	*/
	@Override
	public String getVersionUserName() {
		return _ddmStructure.getVersionUserName();
	}

	/**
	* Returns the version user uuid of this ddm structure.
	*
	* @return the version user uuid of this ddm structure
	*/
	@Override
	public String getVersionUserUuid() {
		return _ddmStructure.getVersionUserUuid();
	}

	/**
	* Returns the WebDAV URL to access the structure.
	*
	* @param themeDisplay the theme display needed to build the URL. It can
	set HTTPS access, the server name, the server port, the path
	context, and the scope group.
	* @param webDAVToken the WebDAV token for the URL
	* @return the WebDAV URL
	*/
	@Override
	public String getWebDavURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay,
		String webDAVToken) {
		return _ddmStructure.getWebDavURL(themeDisplay, webDAVToken);
	}

	@Override
	public boolean hasField(String fieldName) {
		return _ddmStructure.hasField(fieldName);
	}

	@Override
	public int hashCode() {
		return _ddmStructure.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmStructure.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmStructure.isEscapedModel();
	}

	@Override
	public boolean isFieldRepeatable(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.isFieldRepeatable(fieldName);
	}

	@Override
	public boolean isFieldTransient(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructure.isFieldTransient(fieldName);
	}

	@Override
	public boolean isNew() {
		return _ddmStructure.isNew();
	}

	@Override
	public void persist() {
		_ddmStructure.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddmStructure.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_ddmStructure.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmStructure.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_ddmStructure.setClassName(className);
	}

	/**
	* Sets the class name ID of this ddm structure.
	*
	* @param classNameId the class name ID of this ddm structure
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_ddmStructure.setClassNameId(classNameId);
	}

	/**
	* Sets the company ID of this ddm structure.
	*
	* @param companyId the company ID of this ddm structure
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmStructure.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this ddm structure.
	*
	* @param createDate the create date of this ddm structure
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmStructure.setCreateDate(createDate);
	}

	@Override
	public void setDDMForm(DDMForm ddmForm) {
		_ddmStructure.setDDMForm(ddmForm);
	}

	/**
	* Sets the definition of this ddm structure.
	*
	* @param definition the definition of this ddm structure
	*/
	@Override
	public void setDefinition(String definition) {
		_ddmStructure.setDefinition(definition);
	}

	/**
	* Sets the description of this ddm structure.
	*
	* @param description the description of this ddm structure
	*/
	@Override
	public void setDescription(String description) {
		_ddmStructure.setDescription(description);
	}

	/**
	* Sets the localized description of this ddm structure in the language.
	*
	* @param description the localized description of this ddm structure
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_ddmStructure.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this ddm structure in the language, and sets the default locale.
	*
	* @param description the localized description of this ddm structure
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmStructure.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_ddmStructure.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this ddm structure from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this ddm structure
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_ddmStructure.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this ddm structure from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this ddm structure
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		_ddmStructure.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_ddmStructure.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmStructure.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmStructure.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this ddm structure.
	*
	* @param groupId the group ID of this ddm structure
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmStructure.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this ddm structure.
	*
	* @param lastPublishDate the last publish date of this ddm structure
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddmStructure.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this ddm structure.
	*
	* @param modifiedDate the modified date of this ddm structure
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmStructure.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this ddm structure.
	*
	* @param name the name of this ddm structure
	*/
	@Override
	public void setName(String name) {
		_ddmStructure.setName(name);
	}

	/**
	* Sets the localized name of this ddm structure in the language.
	*
	* @param name the localized name of this ddm structure
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		_ddmStructure.setName(name, locale);
	}

	/**
	* Sets the localized name of this ddm structure in the language, and sets the default locale.
	*
	* @param name the localized name of this ddm structure
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_ddmStructure.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_ddmStructure.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this ddm structure from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this ddm structure
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_ddmStructure.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this ddm structure from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this ddm structure
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		_ddmStructure.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmStructure.setNew(n);
	}

	/**
	* Sets the parent structure ID of this ddm structure.
	*
	* @param parentStructureId the parent structure ID of this ddm structure
	*/
	@Override
	public void setParentStructureId(long parentStructureId) {
		_ddmStructure.setParentStructureId(parentStructureId);
	}

	/**
	* Sets the primary key of this ddm structure.
	*
	* @param primaryKey the primary key of this ddm structure
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStructure.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmStructure.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the storage type of this ddm structure.
	*
	* @param storageType the storage type of this ddm structure
	*/
	@Override
	public void setStorageType(String storageType) {
		_ddmStructure.setStorageType(storageType);
	}

	/**
	* Sets the structure ID of this ddm structure.
	*
	* @param structureId the structure ID of this ddm structure
	*/
	@Override
	public void setStructureId(long structureId) {
		_ddmStructure.setStructureId(structureId);
	}

	/**
	* Sets the structure key of this ddm structure.
	*
	* @param structureKey the structure key of this ddm structure
	*/
	@Override
	public void setStructureKey(String structureKey) {
		_ddmStructure.setStructureKey(structureKey);
	}

	/**
	* Sets the type of this ddm structure.
	*
	* @param type the type of this ddm structure
	*/
	@Override
	public void setType(int type) {
		_ddmStructure.setType(type);
	}

	/**
	* Sets the user ID of this ddm structure.
	*
	* @param userId the user ID of this ddm structure
	*/
	@Override
	public void setUserId(long userId) {
		_ddmStructure.setUserId(userId);
	}

	/**
	* Sets the user name of this ddm structure.
	*
	* @param userName the user name of this ddm structure
	*/
	@Override
	public void setUserName(String userName) {
		_ddmStructure.setUserName(userName);
	}

	/**
	* Sets the user uuid of this ddm structure.
	*
	* @param userUuid the user uuid of this ddm structure
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_ddmStructure.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this ddm structure.
	*
	* @param uuid the uuid of this ddm structure
	*/
	@Override
	public void setUuid(String uuid) {
		_ddmStructure.setUuid(uuid);
	}

	/**
	* Sets the version of this ddm structure.
	*
	* @param version the version of this ddm structure
	*/
	@Override
	public void setVersion(String version) {
		_ddmStructure.setVersion(version);
	}

	/**
	* Sets the version user ID of this ddm structure.
	*
	* @param versionUserId the version user ID of this ddm structure
	*/
	@Override
	public void setVersionUserId(long versionUserId) {
		_ddmStructure.setVersionUserId(versionUserId);
	}

	/**
	* Sets the version user name of this ddm structure.
	*
	* @param versionUserName the version user name of this ddm structure
	*/
	@Override
	public void setVersionUserName(String versionUserName) {
		_ddmStructure.setVersionUserName(versionUserName);
	}

	/**
	* Sets the version user uuid of this ddm structure.
	*
	* @param versionUserUuid the version user uuid of this ddm structure
	*/
	@Override
	public void setVersionUserUuid(String versionUserUuid) {
		_ddmStructure.setVersionUserUuid(versionUserUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DDMStructure> toCacheModel() {
		return _ddmStructure.toCacheModel();
	}

	@Override
	public DDMStructure toEscapedModel() {
		return new DDMStructureWrapper(_ddmStructure.toEscapedModel());
	}

	@Override
	public String toString() {
		return _ddmStructure.toString();
	}

	@Override
	public DDMStructure toUnescapedModel() {
		return new DDMStructureWrapper(_ddmStructure.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _ddmStructure.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureWrapper)) {
			return false;
		}

		DDMStructureWrapper ddmStructureWrapper = (DDMStructureWrapper)obj;

		if (Objects.equals(_ddmStructure, ddmStructureWrapper._ddmStructure)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmStructure.getStagedModelType();
	}

	@Override
	public DDMStructure getWrappedModel() {
		return _ddmStructure;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmStructure.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmStructure.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmStructure.resetOriginalValues();
	}

	private final DDMStructure _ddmStructure;
}