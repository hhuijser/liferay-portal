<%--
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
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
ConfigurationProvider<LDAPImportConfiguration> ldapImportConfigurationProvider = ConfigurationProviderUtil.getLDAPImportConfigurationProvider();

LDAPImportConfiguration ldapImportConfiguration = ldapImportConfigurationProvider.getConfiguration(themeDisplay.getCompanyId());

boolean ldapImportCreateRolePerGroup = ldapImportConfiguration.importCreateRolePerGroup();
boolean ldapImportEnabled = ldapImportConfiguration.importEnabled();
int ldapImportInterval = ldapImportConfiguration.importInterval();
long ldapImportLockExpirationTime = ldapImportConfiguration.importLockExpirationTime();
boolean ldapImportGroupCacheEnabled = ldapImportConfiguration.importGroupCacheEnabled();
String ldapImportMethod = ldapImportConfiguration.importMethod();
boolean ldapImportOnStartup = ldapImportConfiguration.importOnStartup();
String ldapImportUserSyncStrategy = ldapImportConfiguration.importUserSyncStrategy();
boolean ldapImportUserPasswordAutogenerated = ldapImportConfiguration.importUserPasswordAutogenerated();
String ldapImportUserPasswordDefault = ldapImportConfiguration.importUserPasswordDefault();
boolean ldapImportUserPasswordEnabled = ldapImportConfiguration.importUserPasswordEnabled();
%>

<aui:fieldset>
	<aui:input
		name="<%= Constants.CMD %>"
		type="hidden"
		value="<%= LDAPSettingsConstants.CMD_UPDATE_IMPORT %>"
	/>

	<c:choose>
		<c:when test="<%= ldapImportConfiguration.importUserPasswordAutogenerated() %>">
			<aui:input
				helpMessage="import-enabled-user-password-autogenerated-help"
				id="ldapImportEnabled"
				label="enable-import"
				name='<%= "ldap--" + LDAPConstants.IMPORT_ENABLED + "--" %>'
				onClick='<%= renderResponse.getNamespace() + "enableExport()" %>'
				type="checkbox"
				value="<%= ldapImportEnabled %>"
			/>
		</c:when>
		<c:otherwise>
			<aui:input
				id="ldapImportEnabled"
				label="enable-import"
				name='<%= "ldap--" + LDAPConstants.IMPORT_ENABLED + "--" %>'
				type="checkbox"
				value="<%= ldapImportEnabled %>"
			/>
		</c:otherwise>
	</c:choose>

	<div id="<portlet:namespace />importEnabledSettings">
		<aui:input
			label="enable-import-on-startup"
			name='<%= "ldap--" + LDAPConstants.IMPORT_ON_STARTUP + "--" %>'
			type="checkbox"
			value="<%= ldapImportOnStartup %>"
		/>
	</div>

	<aui:input
		label="import-interval"
		name='<%= "ldap--" + LDAPConstants.IMPORT_INTERVAL + "--" %>'
		type="text"
		value="<%= ldapImportInterval %>"
	/>

	<aui:select
		label="select-import-method"
		name='<%= "ldap--" + LDAPConstants.IMPORT_METHOD + "--" %>'
		value="<%= ldapImportMethod %>"
	>
		<aui:option
			label="group"
			value="<%= LDAPSettingsConstants.IMPORT_METHOD_GROUP %>"
		/>

		<aui:option
			label="user"
			value="<%= LDAPSettingsConstants.IMPORT_METHOD_USER %>"
		/>
	</aui:select>

	<aui:input
		label="import-lock-expiration-time"
		name='<%= "ldap--" + LDAPConstants.IMPORT_LOCK_EXPIRATION_TIME + "--" %>'
		type="text"
		value="<%= ldapImportLockExpirationTime %>"
	/>

	<aui:select
		label="import-user-sync-strategy"
		name='<%= "ldap--" + LDAPConstants.IMPORT_USER_SYNC_STRATEGY + "--" %>'
		value="<%= ldapImportUserSyncStrategy %>"
	>
		<aui:option
			label="auth-type"
			value="<%= LDAPSettingsConstants.IMPORT_USER_SYNC_STRATEGY_AUTH_TYPE %>"
		/>

		<aui:option
			label="uuid"
			value="<%= LDAPSettingsConstants.IMPORT_USER_SYNC_STRATEGY_UUID %>"
		/>
	</aui:select>

	<aui:input
		label="enable-user-password-on-import"
		name='<%= "ldap--" + LDAPConstants.IMPORT_USER_PASSWORD_ENABLED + "--" %>'
		type="checkbox"
		value="<%= ldapImportUserPasswordEnabled %>"
	/>

	<aui:input
		label="autogenerate-user-password-on-import"
		name='<%= "ldap--" + LDAPConstants.IMPORT_USER_PASSWORD_AUTOGENERATED + "--" %>'
		type="checkbox"
		value="<%= ldapImportUserPasswordAutogenerated %>"
	/>

	<aui:input
		label="default-user-password"
		name='<%= "ldap--" + LDAPConstants.IMPORT_USER_PASSWORD_DEFAULT + "--" %>'
		type="text"
		value="<%= ldapImportUserPasswordDefault %>"
	/>

	<aui:input
		label="enable-group-cache-on-import"
		name='<%= "ldap--" + LDAPConstants.IMPORT_GROUP_CACHE_ENABLED + "--" %>'
		type="checkbox"
		value="<%= ldapImportGroupCacheEnabled %>"
	/>

	<aui:input
		label="create-role-per-group-on-import"
		name='<%= "ldap--" + LDAPConstants.IMPORT_CREATE_ROLE_PER_GROUP + "--" %>'
		type="checkbox"
		value="<%= ldapImportCreateRolePerGroup %>"
	/>
</aui:fieldset>

<c:if test="<%= ldapImportConfiguration.importUserPasswordAutogenerated() %>">
	<script>
		function <portlet:namespace />enableExport() {
			var exportCheckbox = document.getElementById(
				'<portlet:namespace />ldapExportEnabled'
			);
			var importCheckbox = document.getElementById(
				'<portlet:namespace />ldapImportEnabled'
			);

			if (exportCheckbox && importCheckbox) {
				exportCheckbox.disabled = importCheckbox.checked;
			}
		}
	</script>
</c:if>