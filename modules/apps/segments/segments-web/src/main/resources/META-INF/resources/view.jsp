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

<%
SegmentsDisplayContext segmentsDisplayContext = (SegmentsDisplayContext)request.getAttribute(SegmentsWebKeys.SEGMENTS_DISPLAY_CONTEXT);
%>

<%@ include file="/init.jsp" %>

<clay:management-toolbar
	actionDropdownItems="<%= segmentsDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= segmentsDisplayContext.getClearResultsURL() %>"
	componentId="segmentsEntriesManagementToolbar"
	creationMenu="<%= segmentsDisplayContext.getCreationMenu() %>"
	disabled="<%= segmentsDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= segmentsDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= segmentsDisplayContext.getTotalItems() %>"
	searchActionURL="<%= segmentsDisplayContext.getSearchActionURL() %>"
	searchContainerId="segmentsEntries"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showCreationMenu="<%= segmentsDisplayContext.isShowCreationMenu() %>"
	sortingOrder="<%= segmentsDisplayContext.getOrderByType() %>"
	sortingURL="<%= segmentsDisplayContext.getSortingURL() %>"
/>

<portlet:actionURL name="deleteSegmentsEntry" var="deleteSegmentsEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteSegmentsEntryURL %>" cssClass="container-fluid-1280" method="post" name="fmSegmentsEntries">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:error exception="<%= RequiredSegmentsEntryException.MustNotDeleteSegmentsEntryReferencedBySegmentsExperiences.class %>" message="the-segment-cannot-be-deleted-because-it-is-required-by-one-or-more-experiences" />

	<liferay-ui:search-container
		id="segmentsEntries"
		searchContainer="<%= segmentsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.segments.model.SegmentsEntry"
			keyProperty="segmentsEntryId"
			modelVar="segmentsEntry"
		>

			<%
			Map<String, Object> rowData = HashMapBuilder.<String, Object>put(
				"actions", segmentsDisplayContext.getAvailableActions(segmentsEntry)
			).build();

			row.setData(rowData);
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-title"
				href="<%= segmentsDisplayContext.getSegmentsEntryURL(segmentsEntry) %>"
				name="name"
				target="<%= segmentsDisplayContext.getSegmentsEntryURLTarget(segmentsEntry) %>"
				value="<%= HtmlUtil.escape(segmentsEntry.getName(locale)) %>"
			/>

			<c:if test="<%= segmentsDisplayContext.isAsahEnabled(themeDisplay.getCompanyId()) %>">
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-cell-minw-150"
					name="source"
				>
					<c:choose>
						<c:when test="<%= Objects.equals(segmentsEntry.getSource(), SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND) %>">
							<liferay-ui:icon
								message="source.analytics-cloud"
								src='<%= PortalUtil.getPathContext(request) + "/assets/ac-icon.svg" %>'
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								message="source.dxp"
								src='<%= PortalUtil.getPathContext(request) + "/assets/dxp-icon.svg" %>'
							/>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>
			</c:if>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-150"
				name="scope"
			>
				<c:choose>
					<c:when test="<%= segmentsEntry.getGroupId() == themeDisplay.getCompanyGroupId() %>">
						<liferay-ui:message key="global" />
					</c:when>
					<c:when test="<%= segmentsEntry.getGroupId() == themeDisplay.getScopeGroupId() %>">
						<liferay-ui:message key="current-site" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="parent-site" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-minw-150 table-cell-ws-nowrap"
				name="modified-date"
				value="<%= segmentsEntry.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/segments_entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script sandbox="<%= true %>">
	var deleteSegmentsEntries = function() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
			)
		) {
			submitForm(
				document.querySelector('#<portlet:namespace />fmSegmentsEntries')
			);
		}
	};

	var ACTIONS = {
		deleteSegmentsEntries: deleteSegmentsEntries,
	};

	Liferay.componentReady('segmentsEntriesManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>

<%
ItemSelector itemSelector = (ItemSelector)request.getAttribute(SegmentsWebKeys.ITEM_SELECTOR);

ItemSelectorCriterion itemSelectorCriterion = new RoleItemSelectorCriterion(RoleConstants.TYPE_SITE);

itemSelectorCriterion.setDesiredItemSelectorReturnTypes(new UUIDItemSelectorReturnType());

String eventName = renderResponse.getNamespace() + "assignSiteRoles";

PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(renderRequest), eventName, itemSelectorCriterion);
%>

<portlet:actionURL name="updateSegmentsEntrySiteRoles" var="updateSegmentsEntrySiteRolesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateSegmentsEntrySiteRolesURL %>" cssClass="hide" method="post" name="updateSegmentsEntrySiteRolesFm">
	<aui:input name="segmentsEntryId" type="hidden" />
	<aui:input name="siteRoleIds" type="hidden" />
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom, frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var form = document.getElementById(
		'<portlet:namespace/>updateSegmentsEntrySiteRolesFm'
	);

	dom.delegate(document, 'click', '.assign-site-roles-link', function(event) {
		var link = dom.closest(event.target, '.assign-site-roles-link');

		var segmentsEntryId = link.dataset.segmentsentryid;
		var roleIds = link.dataset.roleids;

		var parameters = {
			p_p_id: '<%= ItemSelectorPortletKeys.ITEM_SELECTOR %>',
		};

		if (roleIds) {
			parameters.checkedRoleIds = roleIds;
		}

		var url = Liferay.Util.PortletURL.createRenderURL(
			'<%= itemSelectorURL.toString() %>',
			parameters
		);

		var itemSelectorDialog = new ItemSelectorDialog.default({
			eventName: '<%= eventName %>',
			title: '<liferay-ui:message key="assign-site-roles" />',
			url: url.toString(),
		});

		itemSelectorDialog.on('selectedItemChange', function(event) {
			var selectedItem = event.selectedItem;

			if (selectedItem) {
				var data = {
					segmentsEntryId: segmentsEntryId,
					siteRoleIds: selectedItem.value,
				};

				Liferay.Util.postForm(form, {data: data});
			}
		});

		itemSelectorDialog.open();
	});
</aui:script>