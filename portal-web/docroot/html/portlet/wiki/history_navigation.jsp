<%--
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
--%>

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
long nodeId = (Long)request.getAttribute(WebKeys.WIKI_NODE_ID);
String title = (String)request.getAttribute(WebKeys.TITLE);
Version sourceVersion = (Version)request.getAttribute(WebKeys.SOURCE_VERSION);
Version targetVersion = (Version)request.getAttribute(WebKeys.TARGET_VERSION);

Version previousVersion = null;
Version nextVersion = null;

List<WikiPage> allPages = WikiPageLocalServiceUtil.getPages(nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator(true));

int sourceIndex = 0;
int targetIndex = allPages.size();

for (int i = 0; i < allPages.size(); i++) {
	WikiPage wikiPage = allPages.get(i);

	Version version = wikiPage.getVersion();

	if (version.equals(sourceVersion) && (sourceIndex < i)) {
		WikiPage previousPage = allPages.get(i - 1);

		previousVersion = previousPage.getVersion();

		sourceIndex = i;
	}

	if (version.equals(targetVersion) && (i < (targetIndex - 1))) {
		WikiPage nextPage = allPages.get(i + 1);

		nextVersion = nextPage.getVersion();

		targetIndex = i;

		break;
	}
}

List<WikiPage> intermediatePages = allPages.subList(sourceIndex, targetIndex);

String sourceVersionString = (previousVersion != null) ? sourceVersion.toString() : sourceVersion.toString() + " (" + LanguageUtil.get(pageContext, "first-version") + ")";
String targetVersionString = (nextVersion != null) ? targetVersion.toString() : targetVersion.toString() + " (" + LanguageUtil.get(pageContext, "last-version") + ")";

String type = ParamUtil.getString(request, "type", "text");

boolean htmlMode = false;

if (type.equals("html")) {
	htmlMode = true;
}
%>

<portlet:renderURL var="changeMode">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= sourceVersion.toString() %>" />
	<portlet:param name="targetVersion" value="<%= targetVersion.toString() %>" />
	<portlet:param name="type" value='<%= htmlMode ? "text" : "html" %>' />
</portlet:renderURL>

<portlet:renderURL var="previousChange">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= (previousVersion != null) ? previousVersion.toString() : StringPool.BLANK %>" />
	<portlet:param name="targetVersion" value="<%= sourceVersion.toString() %>" />
	<portlet:param name="type" value="<%= type %>" />
</portlet:renderURL>

<portlet:renderURL var="nextChange">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= targetVersion.toString() %>" />
	<portlet:param name="targetVersion" value="<%= (nextVersion != null) ? nextVersion.toString() : StringPool.BLANK %>" />
	<portlet:param name="type" value="<%= type %>" />
</portlet:renderURL>

<div class="history-navigation">
	<c:choose>
		<c:when test="<%= previousVersion != null %>">
			<aui:a cssClass="previous" href="<%= previousChange %>" label="previous-change" />
		</c:when>
		<c:otherwise>
			<span class="previous"><liferay-ui:message key="previous-change" /></span>
		</c:otherwise>
	</c:choose>

	<div class="central-info">
		<liferay-ui:icon
			cssClass="central-title"
			image="pages"
			label="<%= true %>"
			message='<%= LanguageUtil.format(pageContext, "comparing-versions-x-and-x", new Object[] {sourceVersionString, targetVersionString}) %>'
		/>

		<c:choose>
			<c:when test="<%= !htmlMode %>">
				<aui:a cssClass="change-mode" href="<%= changeMode %>" label="html-mode" />
			</c:when>
			<c:otherwise>
				<span class="change-mode"><liferay-ui:message key="html-mode" /></span>
			</c:otherwise>
		</c:choose>

		<%= StringPool.PIPE %>

		<c:choose>
			<c:when test="<%= htmlMode %>">
				<aui:a cssClass="change-mode" href="<%= changeMode %>" label="text-mode" />
			</c:when>
			<c:otherwise>
				<span class="change-mode"><liferay-ui:message key="text-mode" /></span>
			</c:otherwise>
		</c:choose>

		<div class="central-author">
			<c:choose>
				<c:when test="<%= intermediatePages.size() > 1 %>">

					<%
					StringBundler sb = new StringBundler(intermediatePages.size() * 7);

					for (WikiPage wikiPage: intermediatePages) {
						Version version = wikiPage.getVersion();

						sb.append(HtmlUtil.escape(wikiPage.getUserName()));
						sb.append(StringPool.SPACE);
						sb.append(StringPool.OPEN_PARENTHESIS);
						sb.append(version.toString());
						sb.append(StringPool.CLOSE_PARENTHESIS);
						sb.append(StringPool.COMMA);
						sb.append(StringPool.SPACE);
					}

					sb.setIndex(sb.index() - 2);

					sb.append(StringPool.SPACE);
					%>

					<liferay-ui:icon
						image="user_icon"
						label="<%= true %>"
						message="<%= sb.toString() %>"
						toolTip="authors"
					/>
				</c:when>
				<c:otherwise>

					<%
					WikiPage wikiPage = intermediatePages.get(0);
					%>

					<liferay-ui:icon
						cssClass="central-username"
						image="user_icon"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(wikiPage.getUserName()) %>"
						toolTip="author"
					/>

					<c:if test="<%= Validator.isNotNull(wikiPage.getSummary()) %>">
						<%= StringPool.COLON + StringPool.SPACE + wikiPage.getSummary() %>
					</c:if>

					<c:if test="<%= wikiPage.isMinorEdit() %>">
						<%= StringPool.OPEN_PARENTHESIS + LanguageUtil.get(pageContext, "minor-edit") + StringPool.CLOSE_PARENTHESIS %>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<c:choose>
		<c:when test="<%= nextVersion != null %>">
			<aui:a cssClass="next" href="<%= nextChange %>" label="next-change" />
		</c:when>
		<c:otherwise>
			<span class="next"><liferay-ui:message key="next-change" /></span>
		</c:otherwise>
	</c:choose>
</div>