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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the Role service. Represents a row in the &quot;Role_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.RoleModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.RoleImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Role
 * @see com.liferay.portal.model.impl.RoleImpl
 * @see com.liferay.portal.model.impl.RoleModelImpl
 * @generated
 */
@ProviderType
public interface RoleModel extends AttachedModel, BaseModel<Role>, LocalizedModel,
	MVCCModel, StagedAuditedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a role model instance should use the {@link Role} interface instead.
	 */

	/**
	 * Returns the primary key of this role.
	 *
	 * @return the primary key of this role
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this role.
	 *
	 * @param primaryKey the primary key of this role
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this role.
	 *
	 * @return the mvcc version of this role
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this role.
	 *
	 * @param mvccVersion the mvcc version of this role
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this role.
	 *
	 * @return the uuid of this role
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this role.
	 *
	 * @param uuid the uuid of this role
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the role ID of this role.
	 *
	 * @return the role ID of this role
	 */
	public long getRoleId();

	/**
	 * Sets the role ID of this role.
	 *
	 * @param roleId the role ID of this role
	 */
	public void setRoleId(long roleId);

	/**
	 * Returns the company ID of this role.
	 *
	 * @return the company ID of this role
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this role.
	 *
	 * @param companyId the company ID of this role
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this role.
	 *
	 * @return the user ID of this role
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this role.
	 *
	 * @param userId the user ID of this role
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this role.
	 *
	 * @return the user uuid of this role
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this role.
	 *
	 * @param userUuid the user uuid of this role
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this role.
	 *
	 * @return the user name of this role
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this role.
	 *
	 * @param userName the user name of this role
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this role.
	 *
	 * @return the create date of this role
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this role.
	 *
	 * @param createDate the create date of this role
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this role.
	 *
	 * @return the modified date of this role
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this role.
	 *
	 * @param modifiedDate the modified date of this role
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the fully qualified class name of this role.
	 *
	 * @return the fully qualified class name of this role
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this role.
	 *
	 * @return the class name ID of this role
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this role.
	 *
	 * @param classNameId the class name ID of this role
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class p k of this role.
	 *
	 * @return the class p k of this role
	 */
	@Override
	public long getClassPK();

	/**
	 * Sets the class p k of this role.
	 *
	 * @param classPK the class p k of this role
	 */
	@Override
	public void setClassPK(long classPK);

	/**
	 * Returns the name of this role.
	 *
	 * @return the name of this role
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this role.
	 *
	 * @param name the name of this role
	 */
	public void setName(String name);

	/**
	 * Returns the title of this role.
	 *
	 * @return the title of this role
	 */
	public String getTitle();

	/**
	 * Returns the localized title of this role in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this role
	 */
	@AutoEscape
	public String getTitle(Locale locale);

	/**
	 * Returns the localized title of this role in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this role. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getTitle(Locale locale, boolean useDefault);

	/**
	 * Returns the localized title of this role in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this role
	 */
	@AutoEscape
	public String getTitle(String languageId);

	/**
	 * Returns the localized title of this role in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this role
	 */
	@AutoEscape
	public String getTitle(String languageId, boolean useDefault);

	@AutoEscape
	public String getTitleCurrentLanguageId();

	@AutoEscape
	public String getTitleCurrentValue();

	/**
	 * Returns a map of the locales and localized titles of this role.
	 *
	 * @return the locales and localized titles of this role
	 */
	public Map<Locale, String> getTitleMap();

	/**
	 * Sets the title of this role.
	 *
	 * @param title the title of this role
	 */
	public void setTitle(String title);

	/**
	 * Sets the localized title of this role in the language.
	 *
	 * @param title the localized title of this role
	 * @param locale the locale of the language
	 */
	public void setTitle(String title, Locale locale);

	/**
	 * Sets the localized title of this role in the language, and sets the default locale.
	 *
	 * @param title the localized title of this role
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setTitle(String title, Locale locale, Locale defaultLocale);

	public void setTitleCurrentLanguageId(String languageId);

	/**
	 * Sets the localized titles of this role from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this role
	 */
	public void setTitleMap(Map<Locale, String> titleMap);

	/**
	 * Sets the localized titles of this role from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this role
	 * @param defaultLocale the default locale
	 */
	public void setTitleMap(Map<Locale, String> titleMap, Locale defaultLocale);

	/**
	 * Returns the description of this role.
	 *
	 * @return the description of this role
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this role in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this role
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this role in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this role. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this role in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this role
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this role in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this role
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this role.
	 *
	 * @return the locales and localized descriptions of this role
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this role.
	 *
	 * @param description the description of this role
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this role in the language.
	 *
	 * @param description the localized description of this role
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this role in the language, and sets the default locale.
	 *
	 * @param description the localized description of this role
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(String description, Locale locale,
		Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this role from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this role
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this role from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this role
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale);

	/**
	 * Returns the type of this role.
	 *
	 * @return the type of this role
	 */
	public int getType();

	/**
	 * Sets the type of this role.
	 *
	 * @param type the type of this role
	 */
	public void setType(int type);

	/**
	 * Returns the subtype of this role.
	 *
	 * @return the subtype of this role
	 */
	@AutoEscape
	public String getSubtype();

	/**
	 * Sets the subtype of this role.
	 *
	 * @param subtype the subtype of this role
	 */
	public void setSubtype(String subtype);

	/**
	 * Returns the last publish date of this role.
	 *
	 * @return the last publish date of this role
	 */
	public Date getLastPublishDate();

	/**
	 * Sets the last publish date of this role.
	 *
	 * @param lastPublishDate the last publish date of this role
	 */
	public void setLastPublishDate(Date lastPublishDate);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	@Override
	public Object clone();

	@Override
	public int compareTo(com.liferay.portal.model.Role role);

	@Override
	public int hashCode();

	@Override
	public CacheModel<com.liferay.portal.model.Role> toCacheModel();

	@Override
	public com.liferay.portal.model.Role toEscapedModel();

	@Override
	public com.liferay.portal.model.Role toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}