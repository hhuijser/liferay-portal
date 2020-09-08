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

<%@ include file="/init.jsp" %>

<aui:field-wrapper helpMessage='<%= LanguageUtil.get(resourceBundle, "terms-of-use-web-content-help") %>' label='<%= LanguageUtil.get(resourceBundle, "terms-of-use-web-content") %>'>
	<aui:fieldset>
		<aui:input label='<%= LanguageUtil.get(resourceBundle, "group-id") %>' name='<%= "settings--" + JournalServiceConfigurationKeys.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID + "--" %>' type="text" value="<%= PrefsPropsUtil.getLong(themeDisplay.getCompanyId(), JournalServiceConfigurationKeys.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID, JournalServiceConfigurationValues.TERMS_OF_USE_JOURNAL_ARTICLE_GROUP_ID) %>" />

		<aui:input label='<%= LanguageUtil.get(resourceBundle, "article-id") %>' name='<%= "settings--" + JournalServiceConfigurationKeys.TERMS_OF_USE_JOURNAL_ARTICLE_ID + "--" %>' type="text" value="<%= PrefsPropsUtil.getString(themeDisplay.getCompanyId(), JournalServiceConfigurationKeys.TERMS_OF_USE_JOURNAL_ARTICLE_ID, JournalServiceConfigurationValues.TERMS_OF_USE_JOURNAL_ARTICLE_ID) %>" />
	</aui:fieldset>
</aui:field-wrapper>