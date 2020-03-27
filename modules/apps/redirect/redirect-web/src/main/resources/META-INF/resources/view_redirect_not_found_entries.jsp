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

<%
RedirectNotFoundEntriesDisplayContext redirectNotFoundEntriesDisplayContext = new RedirectNotFoundEntriesDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

SearchContainer<RedirectNotFoundEntry> redirectNotFoundEntriesSearchContainer = redirectNotFoundEntriesDisplayContext.searchContainer();

RedirectNotFountEntriesManagementToolbarDisplayContext redirectNotFoundEntriesManagementToolbarDisplayContext = new RedirectNotFountEntriesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, redirectNotFoundEntriesSearchContainer);
%>

<clay:management-toolbar
	displayContext="<%= redirectNotFoundEntriesManagementToolbarDisplayContext %>"
/>

<aui:form
	action="<%= redirectNotFoundEntriesSearchContainer.getIteratorURL() %>"
	cssClass="container-fluid-1280"
	name="fm"
>

	<%
	List<RedirectNotFoundEntry> results = redirectNotFoundEntriesSearchContainer.getResults();
	%>

	<c:choose>
		<c:when test="<%= results.size() > 0 %>">
			<aui:input
				name="redirect"
				type="hidden"
				value="<%= currentURL %>"
			/>

			<liferay-ui:search-container
				id="<%= redirectNotFoundEntriesDisplayContext.getSearchContainerId() %>"
				searchContainer="<%= redirectNotFoundEntriesSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.redirect.model.RedirectNotFoundEntry"
					keyProperty="redirectNotFoundEntryId"
					modelVar="redirectNotFoundEntry"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="not-found-urls"
					>

						<%
						String url = RedirectUtil.getGroupBaseURL(themeDisplay) + StringPool.SLASH + redirectNotFoundEntry.getUrl();
						%>

						<aui:a
							href="<%= HtmlUtil.escapeAttribute(url) %>"
							target="_blank"
						>
							<%= HtmlUtil.escape(url) %>
						</aui:a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="requests"
					>
						<%= redirectNotFoundEntry.getHits() %>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchContainer="<%= redirectNotFoundEntriesSearchContainer %>"
				/>
			</liferay-ui:search-container>
		</c:when>
		<c:otherwise>
			<liferay-frontend:empty-result-message
				animationType="<%= EmptyResultMessageKeys.AnimationType.SEARCH %>"
				description="<%= LanguageUtil.get(request, redirectNotFoundEntriesSearchContainer.getEmptyResultsMessage()) %>"
				title='<%= LanguageUtil.get(request, "great-job") %>'
			/>
		</c:otherwise>
	</c:choose>
</aui:form>