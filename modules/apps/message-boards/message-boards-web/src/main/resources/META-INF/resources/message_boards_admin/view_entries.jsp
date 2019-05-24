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

<%@ include file="/message_boards/init.jsp" %>

<%
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));

MBCategoryDisplay categoryDisplay = new MBCategoryDisplay(scopeGroupId, categoryId);

MBEntriesManagementToolbarDisplayContext mbEntriesManagementToolbarDisplayContext = (MBEntriesManagementToolbarDisplayContext)request.getAttribute("view.jsp-mbEntriesManagementToolbarDisplayContext");

SearchContainer entriesSearchContainer = (SearchContainer)request.getAttribute("view.jsp-entriesSearchContainer");
%>

<div class="container-fluid-1280 view-entries-container">
	<c:if test="<%= category != null %>">

		<%
		long parentCategoryId = category.getParentCategoryId();

		if (!category.isRoot()) {
			MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

			parentCategoryId = parentCategory.getCategoryId();
		}
		%>

		<portlet:renderURL var="backURL">
			<c:choose>
				<c:when test="<%= parentCategoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID %>">
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
				</c:when>
				<c:otherwise>
					<portlet:param name="mvcRenderCommandName" value="/message_boards/view_category" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" />
				</c:otherwise>
			</c:choose>
		</portlet:renderURL>

		<%
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(backURL.toString());

		renderResponse.setTitle(category.getName());
		%>

	</c:if>

	<%
	MBBreadcrumbUtil.addPortletBreadcrumbEntries(categoryId, request, renderResponse);
	%>

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<aui:form method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />

		<liferay-ui:search-container
			searchContainer="<%= entriesSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="Object"
				escapedModel="<%= true %>"
				keyProperty="categoryId"
				modelVar="result"
			>
				<%@ include file="/message_boards/cast_result.jspf" %>

				<c:choose>
					<c:when test="<%= curCategory != null %>">

						<%
						Map<String, Object> rowData = new HashMap<String, Object>();

						rowData.put("actions", String.join(StringPool.COMMA, mbEntriesManagementToolbarDisplayContext.getAvailableActions(curCategory)));

						row.setData(rowData);

						row.setPrimaryKey(String.valueOf(curCategory.getCategoryId()));
						%>

						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="mvcRenderCommandName" value="/message_boards/view_category" />
							<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:search-container-column-icon
							icon="folder"
							toggleRowChecker="<%= true %>"
						/>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>
							<h2 class="h5">
								<aui:a href="<%= rowURL.toString() %>">
									<%= curCategory.getName() %>
								</aui:a>
							</h2>

							<div>
								<%= curCategory.getDescription() %>
							</div>

							<%
							int subcategoriesCount = categoryDisplay.getSubcategoriesCount(curCategory);
							int threadsCount = categoryDisplay.getSubcategoriesThreadsCount(curCategory);
							%>

							<div>
								<liferay-ui:message arguments="<%= subcategoriesCount %>" key='<%= (subcategoriesCount == 1) ? "x-category" : "x-categories" %>' />
							</div>

							<div>
								<liferay-ui:message arguments="<%= threadsCount %>" key='<%= (threadsCount == 1) ? "x-thread" : "x-threads" %>' />
							</div>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-jsp
							path="/message_boards/category_action.jsp"
						/>
					</c:when>
					<c:otherwise>

						<%
						MBMessage message = MBMessageLocalServiceUtil.fetchMBMessage(thread.getRootMessageId());

						if (message == null) {
							_log.error("Thread requires missing root message id " + thread.getRootMessageId());

							row.setSkip(true);
						}

						if (message != null) {
							message = message.toEscapedModel();

							row.setPrimaryKey(String.valueOf(thread.getThreadId()));
							row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));
						}

						Map<String, Object> rowData = new HashMap<String, Object>();

						rowData.put("actions", String.join(StringPool.COMMA, mbEntriesManagementToolbarDisplayContext.getAvailableActions(message)));

						row.setData(rowData);
						%>

						<liferay-portlet:renderURL varImpl="rowURL">
							<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
							<portlet:param name="messageId" value="<%= (message != null) ? String.valueOf(message.getMessageId()) : String.valueOf(MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:search-container-column-text>
							<liferay-ui:user-portrait
								userId="<%= thread.getLastPostByUserId() %>"
							/>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							colspan="<%= 2 %>"
						>
							<h2 class="h5">
								<aui:a href="<%= rowURL.toString() %>">
									<c:if test="<%= message != null %>">
										<c:choose>
											<c:when test="<%= !MBThreadFlagLocalServiceUtil.hasThreadFlag(themeDisplay.getUserId(), thread) %>">
												<strong><%= message.getSubject() %></strong>
											</c:when>
											<c:otherwise>
												<%= message.getSubject() %>
											</c:otherwise>
										</c:choose>
									</c:if>
								</aui:a>

								<%
								String[] threadPriority = MBUtil.getThreadPriority(mbGroupServiceSettings, themeDisplay.getLanguageId(), thread.getPriority());
								%>

								<c:if test="<%= (threadPriority != null) && (thread.getPriority() > 0) %>">
									<div class="<%= threadPriority[1] %>" title="<%= HtmlUtil.escapeAttribute(threadPriority[0]) %>"></div>
								</c:if>

								<c:if test="<%= thread.isQuestion() %>">
									<aui:icon cssClass="icon-monospaced" image="question-circle" markupView="lexicon" message="question" />
								</c:if>
							</h2>

							<c:choose>
								<c:when test="<%= (message != null) && (thread.getMessageCount() == 1) %>">

									<%
									String messageUserName = "anonymous";

									if (!message.isAnonymous()) {
										messageUserName = message.getUserName();
									}

									Date modifiedDate = message.getModifiedDate();

									String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
									%>

									<div>
										<liferay-ui:message arguments="<%= new String[] {messageUserName, modifiedDateDescription} %>" key="x-modified-x-ago" />
									</div>
								</c:when>
								<c:otherwise>

									<%
									String messageUserName = "anonymous";

									if (thread.getLastPostByUserId() != 0) {
										messageUserName = HtmlUtil.escape(PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK));

										if (Validator.isNull(messageUserName)) {
											MBMessage lastThreadMessage = MBMessageLocalServiceUtil.getLastThreadMessage(thread.getThreadId(), thread.getStatus());

											messageUserName = HtmlUtil.escape(PortalUtil.getUserName(lastThreadMessage.getUserId(), lastThreadMessage.getUserName()));
										}
									}

									Date lastPostDate = thread.getLastPostDate();

									String lastPostDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - lastPostDate.getTime(), true);
									%>

									<div>
										<liferay-ui:message arguments="<%= new String[] {messageUserName, lastPostDateDescription} %>" key="x-replied-x-ago" />
									</div>
								</c:otherwise>
							</c:choose>

							<div>
								<aui:workflow-status bean="<%= message %>" markupView="lexicon" model="<%= MBMessage.class %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= message.getStatus() %>" />
							</div>

							<%
							int repliesCount = Math.max(thread.getMessageCount() - 1, 0);
							int viewCount = thread.getViewCount();
							%>

							<div>
								<liferay-ui:message arguments="<%= repliesCount %>" key='<%= (repliesCount == 1) ? "x-reply" : "x-replies" %>' />
							</div>

							<div>
								<liferay-ui:message arguments="<%= viewCount %>" key='<%= (viewCount == 1) ? "x-view" : "x-views" %>' />
							</div>

							<c:if test="<%= thread.isQuestion() %>">

								<%
								int threadAnswersCount = MBMessageServiceUtil.getThreadAnswersCount(thread.getGroupId(), thread.getCategoryId(), thread.getThreadId());
								%>

								<div>
									<%= threadAnswersCount %>

									<liferay-ui:message key='<%= (threadAnswersCount == 1) ? "answer" : "answers" %>' />
								</div>
							</c:if>

							<c:if test="<%= thread.isLocked() %>">
								<div>
									<liferay-ui:message key="locked" />
								</div>
							</c:if>
						</liferay-ui:search-container-column-text>

						<%
						row.setObject(new Object[] {message});
						%>

						<c:if test="<%= message != null %>">
							<liferay-ui:search-container-column-jsp
								path="/message_boards/message_action.jsp"
							/>
						</c:if>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="descriptive"
				markupView="lexicon"
				resultRowSplitter="<%= new MBResultRowSplitter() %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_message_boards_web.message_boards_admin.view_entries_jsp");
%>