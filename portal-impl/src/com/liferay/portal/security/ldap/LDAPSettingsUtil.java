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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.ldap.LDAPUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;

/**
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class LDAPSettingsUtil {

	public static String getAuthSearchFilter(
			long ldapServerId, long companyId, String emailAddress,
			String screenName, String userId)
		throws Exception {

		String postfix = getPropertyPostfix(ldapServerId);

		String filter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_AUTH_SEARCH_FILTER + postfix);

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter before transformation " + filter);
		}

		filter = StringUtil.replace(
			filter,
			new String[] {
				"@company_id@", "@email_address@", "@screen_name@", "@user_id@"
			},
			new String[] {
				String.valueOf(companyId), emailAddress, screenName, userId
			});

		LDAPUtil.validateFilter(filter);

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter after transformation " + filter);
		}

		return filter;
	}

	public static Properties getContactExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties contactExpandoMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_CONTACT_CUSTOM_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, contactExpandoMappings);

		return contactExpandoMappings;
	}

	public static Properties getContactMappings(
			long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties contactMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_CONTACT_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, contactMappings);

		return contactMappings;
	}

	public static Properties getGroupMappings(long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties groupMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_GROUP_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, groupMappings);

		return groupMappings;
	}

	public static long getPreferredLDAPServerId(
		long companyId, String screenName) {

		User user = UserLocalServiceUtil.fetchUserByScreenName(
			companyId, screenName);

		if (user == null) {
			return -1;
		}

		return user.getLdapServerId();
	}

	public static String getPropertyPostfix(long ldapServerId) {
		return StringPool.PERIOD + ldapServerId;
	}

	public static Properties getUserExpandoMappings(
			long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties userExpandoMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_USER_CUSTOM_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, userExpandoMappings);

		return userExpandoMappings;
	}

	public static Properties getUserMappings(long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		Properties userMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_USER_MAPPINGS + postfix,
				StringPool.BLANK));

		LogUtil.debug(_log, userMappings);

		return userMappings;
	}

	public static boolean isExportEnabled(long companyId) {
		if (isImportEnabled(companyId) &&
			PropsValues.LDAP_IMPORT_USER_PASSWORD_AUTOGENERATED) {

			return false;
		}

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_EXPORT_ENABLED,
				PropsValues.LDAP_EXPORT_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isExportGroupEnabled(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_EXPORT_GROUP_ENABLED,
				PropsValues.LDAP_EXPORT_GROUP_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isImportEnabled(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_IMPORT_ENABLED,
				PropsValues.LDAP_IMPORT_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isImportOnStartup(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_IMPORT_ON_STARTUP)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isPasswordPolicyEnabled(long companyId) {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_PASSWORD_POLICY_ENABLED,
				PropsValues.LDAP_PASSWORD_POLICY_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPSettingsUtil.class);

}