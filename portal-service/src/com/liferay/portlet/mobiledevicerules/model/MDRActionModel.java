/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.model;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.AttachedModel;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the MDRAction service. Represents a row in the &quot;MDRAction&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionImpl}.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRAction
 * @see com.liferay.portlet.mobiledevicerules.model.impl.MDRActionImpl
 * @see com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl
 * @generated
 */
public interface MDRActionModel extends AttachedModel, BaseModel<MDRAction>,
	StagedGroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a m d r action model instance should use the {@link MDRAction} interface instead.
	 */

	/**
	 * Returns the primary key of this m d r action.
	 *
	 * @return the primary key of this m d r action
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this m d r action.
	 *
	 * @param primaryKey the primary key of this m d r action
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this m d r action.
	 *
	 * @return the uuid of this m d r action
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this m d r action.
	 *
	 * @param uuid the uuid of this m d r action
	 */
	public void setUuid(String uuid);

	/**
	 * Returns the action ID of this m d r action.
	 *
	 * @return the action ID of this m d r action
	 */
	public long getActionId();

	/**
	 * Sets the action ID of this m d r action.
	 *
	 * @param actionId the action ID of this m d r action
	 */
	public void setActionId(long actionId);

	/**
	 * Returns the group ID of this m d r action.
	 *
	 * @return the group ID of this m d r action
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this m d r action.
	 *
	 * @param groupId the group ID of this m d r action
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this m d r action.
	 *
	 * @return the company ID of this m d r action
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this m d r action.
	 *
	 * @param companyId the company ID of this m d r action
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this m d r action.
	 *
	 * @return the user ID of this m d r action
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this m d r action.
	 *
	 * @param userId the user ID of this m d r action
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this m d r action.
	 *
	 * @return the user uuid of this m d r action
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this m d r action.
	 *
	 * @param userUuid the user uuid of this m d r action
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this m d r action.
	 *
	 * @return the user name of this m d r action
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this m d r action.
	 *
	 * @param userName the user name of this m d r action
	 */
	public void setUserName(String userName);

	/**
	 * Returns the create date of this m d r action.
	 *
	 * @return the create date of this m d r action
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this m d r action.
	 *
	 * @param createDate the create date of this m d r action
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this m d r action.
	 *
	 * @return the modified date of this m d r action
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this m d r action.
	 *
	 * @param modifiedDate the modified date of this m d r action
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the fully qualified class name of this m d r action.
	 *
	 * @return the fully qualified class name of this m d r action
	 */
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this m d r action.
	 *
	 * @return the class name ID of this m d r action
	 */
	public long getClassNameId();

	/**
	 * Sets the class name ID of this m d r action.
	 *
	 * @param classNameId the class name ID of this m d r action
	 */
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class p k of this m d r action.
	 *
	 * @return the class p k of this m d r action
	 */
	public long getClassPK();

	/**
	 * Sets the class p k of this m d r action.
	 *
	 * @param classPK the class p k of this m d r action
	 */
	public void setClassPK(long classPK);

	/**
	 * Returns the rule group instance ID of this m d r action.
	 *
	 * @return the rule group instance ID of this m d r action
	 */
	public long getRuleGroupInstanceId();

	/**
	 * Sets the rule group instance ID of this m d r action.
	 *
	 * @param ruleGroupInstanceId the rule group instance ID of this m d r action
	 */
	public void setRuleGroupInstanceId(long ruleGroupInstanceId);

	/**
	 * Returns the name of this m d r action.
	 *
	 * @return the name of this m d r action
	 */
	public String getName();

	/**
	 * Returns the localized name of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this m d r action
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this m d r action. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this m d r action
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this m d r action
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this m d r action.
	 *
	 * @return the locales and localized names of this m d r action
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this m d r action.
	 *
	 * @param name the name of this m d r action
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this m d r action in the language.
	 *
	 * @param name the localized name of this m d r action
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this m d r action in the language, and sets the default locale.
	 *
	 * @param name the localized name of this m d r action
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this m d r action from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this m d r action
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this m d r action from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this m d r action
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the description of this m d r action.
	 *
	 * @return the description of this m d r action
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this m d r action
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this m d r action. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this m d r action in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this m d r action
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this m d r action in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this m d r action
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this m d r action.
	 *
	 * @return the locales and localized descriptions of this m d r action
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this m d r action.
	 *
	 * @param description the description of this m d r action
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this m d r action in the language.
	 *
	 * @param description the localized description of this m d r action
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this m d r action in the language, and sets the default locale.
	 *
	 * @param description the localized description of this m d r action
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(String description, Locale locale,
		Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this m d r action from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this m d r action
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this m d r action from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this m d r action
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale);

	/**
	 * Returns the type of this m d r action.
	 *
	 * @return the type of this m d r action
	 */
	@AutoEscape
	public String getType();

	/**
	 * Sets the type of this m d r action.
	 *
	 * @param type the type of this m d r action
	 */
	public void setType(String type);

	/**
	 * Returns the type settings of this m d r action.
	 *
	 * @return the type settings of this m d r action
	 */
	@AutoEscape
	public String getTypeSettings();

	/**
	 * Sets the type settings of this m d r action.
	 *
	 * @param typeSettings the type settings of this m d r action
	 */
	public void setTypeSettings(String typeSettings);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	public Object clone();

	public int compareTo(MDRAction mdrAction);

	public int hashCode();

	public CacheModel<MDRAction> toCacheModel();

	public MDRAction toEscapedModel();

	public MDRAction toUnescapedModel();

	public String toString();

	public String toXmlString();
}