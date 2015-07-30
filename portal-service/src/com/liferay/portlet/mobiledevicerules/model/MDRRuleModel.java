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

package com.liferay.portlet.mobiledevicerules.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LocalizedModel;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the MDRRule service. Represents a row in the &quot;MDRRule&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleImpl}.
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRule
 * @see com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleImpl
 * @see com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl
 * @generated
 */
@ProviderType
public interface MDRRuleModel extends BaseModel<MDRRule>, LocalizedModel,
	StagedGroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a m d r rule model instance should use the {@link MDRRule} interface instead.
	 */

	/**
	 * Returns the primary key of this m d r rule.
	 *
	 * @return the primary key of this m d r rule
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this m d r rule.
	 *
	 * @param primaryKey the primary key of this m d r rule
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this m d r rule.
	 *
	 * @return the uuid of this m d r rule
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this m d r rule.
	 *
	 * @param uuid the uuid of this m d r rule
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the rule ID of this m d r rule.
	 *
	 * @return the rule ID of this m d r rule
	 */
	public long getRuleId();

	/**
	 * Sets the rule ID of this m d r rule.
	 *
	 * @param ruleId the rule ID of this m d r rule
	 */
	public void setRuleId(long ruleId);

	/**
	 * Returns the group ID of this m d r rule.
	 *
	 * @return the group ID of this m d r rule
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this m d r rule.
	 *
	 * @param groupId the group ID of this m d r rule
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this m d r rule.
	 *
	 * @return the company ID of this m d r rule
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this m d r rule.
	 *
	 * @param companyId the company ID of this m d r rule
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this m d r rule.
	 *
	 * @return the user ID of this m d r rule
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this m d r rule.
	 *
	 * @param userId the user ID of this m d r rule
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this m d r rule.
	 *
	 * @return the user uuid of this m d r rule
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this m d r rule.
	 *
	 * @param userUuid the user uuid of this m d r rule
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this m d r rule.
	 *
	 * @return the user name of this m d r rule
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this m d r rule.
	 *
	 * @param userName the user name of this m d r rule
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this m d r rule.
	 *
	 * @return the create date of this m d r rule
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this m d r rule.
	 *
	 * @param createDate the create date of this m d r rule
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this m d r rule.
	 *
	 * @return the modified date of this m d r rule
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this m d r rule.
	 *
	 * @param modifiedDate the modified date of this m d r rule
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the rule group ID of this m d r rule.
	 *
	 * @return the rule group ID of this m d r rule
	 */
	public long getRuleGroupId();

	/**
	 * Sets the rule group ID of this m d r rule.
	 *
	 * @param ruleGroupId the rule group ID of this m d r rule
	 */
	public void setRuleGroupId(long ruleGroupId);

	/**
	 * Returns the name of this m d r rule.
	 *
	 * @return the name of this m d r rule
	 */
	public String getName();

	/**
	 * Returns the localized name of this m d r rule in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this m d r rule
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this m d r rule in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this m d r rule. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this m d r rule in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this m d r rule
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this m d r rule in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this m d r rule
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this m d r rule.
	 *
	 * @return the locales and localized names of this m d r rule
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this m d r rule.
	 *
	 * @param name the name of this m d r rule
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this m d r rule in the language.
	 *
	 * @param name the localized name of this m d r rule
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this m d r rule in the language, and sets the default locale.
	 *
	 * @param name the localized name of this m d r rule
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this m d r rule from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this m d r rule
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this m d r rule from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this m d r rule
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the description of this m d r rule.
	 *
	 * @return the description of this m d r rule
	 */
	public String getDescription();

	/**
	 * Returns the localized description of this m d r rule in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized description of this m d r rule
	 */
	@AutoEscape
	public String getDescription(Locale locale);

	/**
	 * Returns the localized description of this m d r rule in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this m d r rule. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized description of this m d r rule in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized description of this m d r rule
	 */
	@AutoEscape
	public String getDescription(String languageId);

	/**
	 * Returns the localized description of this m d r rule in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized description of this m d r rule
	 */
	@AutoEscape
	public String getDescription(String languageId, boolean useDefault);

	@AutoEscape
	public String getDescriptionCurrentLanguageId();

	@AutoEscape
	public String getDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized descriptions of this m d r rule.
	 *
	 * @return the locales and localized descriptions of this m d r rule
	 */
	public Map<Locale, String> getDescriptionMap();

	/**
	 * Sets the description of this m d r rule.
	 *
	 * @param description the description of this m d r rule
	 */
	public void setDescription(String description);

	/**
	 * Sets the localized description of this m d r rule in the language.
	 *
	 * @param description the localized description of this m d r rule
	 * @param locale the locale of the language
	 */
	public void setDescription(String description, Locale locale);

	/**
	 * Sets the localized description of this m d r rule in the language, and sets the default locale.
	 *
	 * @param description the localized description of this m d r rule
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setDescription(String description, Locale locale,
		Locale defaultLocale);

	public void setDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized descriptions of this m d r rule from the map of locales and localized descriptions.
	 *
	 * @param descriptionMap the locales and localized descriptions of this m d r rule
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	/**
	 * Sets the localized descriptions of this m d r rule from the map of locales and localized descriptions, and sets the default locale.
	 *
	 * @param descriptionMap the locales and localized descriptions of this m d r rule
	 * @param defaultLocale the default locale
	 */
	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale);

	/**
	 * Returns the type of this m d r rule.
	 *
	 * @return the type of this m d r rule
	 */
	@AutoEscape
	public String getType();

	/**
	 * Sets the type of this m d r rule.
	 *
	 * @param type the type of this m d r rule
	 */
	public void setType(String type);

	/**
	 * Returns the type settings of this m d r rule.
	 *
	 * @return the type settings of this m d r rule
	 */
	@AutoEscape
	public String getTypeSettings();

	/**
	 * Sets the type settings of this m d r rule.
	 *
	 * @param typeSettings the type settings of this m d r rule
	 */
	public void setTypeSettings(String typeSettings);

	/**
	 * Returns the last publish date of this m d r rule.
	 *
	 * @return the last publish date of this m d r rule
	 */
	public Date getLastPublishDate();

	/**
	 * Sets the last publish date of this m d r rule.
	 *
	 * @param lastPublishDate the last publish date of this m d r rule
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
	public int compareTo(
		com.liferay.portlet.mobiledevicerules.model.MDRRule mdrRule);

	@Override
	public int hashCode();

	@Override
	public CacheModel<com.liferay.portlet.mobiledevicerules.model.MDRRule> toCacheModel();

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRRule toEscapedModel();

	@Override
	public com.liferay.portlet.mobiledevicerules.model.MDRRule toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}