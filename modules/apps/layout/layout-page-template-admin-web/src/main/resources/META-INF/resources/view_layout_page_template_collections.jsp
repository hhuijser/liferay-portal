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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(request, renderRequest, renderResponse);

List<LayoutPageTemplateCollection> layoutPageTemplateCollections = layoutPageTemplateDisplayContext.getLayoutPageTemplateCollections();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= layoutPageTemplatesAdminDisplayContext.getNavigationItems() %>"
/>

<liferay-ui:success key="layoutPageTemplatePublished" message="the-page-template-was-published-succesfully" />

<clay:container-fluid
	cssClass="container-view"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<portlet:renderURL var="editLayoutPageTemplateCollectionURL">
							<portlet:param name="mvcRenderCommandName" value="/layout_page_template/edit_layout_page_template_collection" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:renderURL>

						<c:choose>
							<c:when test="<%= ListUtil.isNotEmpty(layoutPageTemplateCollections) %>">
								<clay:content-row
									verticalAlign="center"
								>
									<clay:content-col
										expand="<%= true %>"
									>
										<strong class="text-uppercase">
											<liferay-ui:message key="collections" />
										</strong>
									</clay:content-col>

									<clay:content-col
										verticalAlign="end"
									>
										<ul class="navbar-nav">
											<c:if test="<%= layoutPageTemplateDisplayContext.isShowAddButton(LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_COLLECTION) %>">
												<li>
													<liferay-ui:icon
														icon="plus"
														iconCssClass="btn btn-monospaced btn-outline-borderless btn-outline-secondary"
														markupView="lexicon"
														url="<%= editLayoutPageTemplateCollectionURL %>"
													/>
												</li>
											</c:if>

											<li>
												<clay:dropdown-actions
													componentId="actionsComponent"
													dropdownItems="<%= layoutPageTemplateDisplayContext.getCollectionsDropdownItems() %>"
												/>
											</li>
										</ul>
									</clay:content-col>
								</clay:content-row>

								<ul class="nav nav-stacked">

									<%
									for (LayoutPageTemplateCollection layoutPageTemplateCollection : layoutPageTemplateCollections) {
									%>

										<li class="nav-item">

											<%
											PortletURL layoutPageTemplateCollectionURL = renderResponse.createRenderURL();

											layoutPageTemplateCollectionURL.setParameter("layoutPageTemplateCollectionId", String.valueOf(layoutPageTemplateCollection.getLayoutPageTemplateCollectionId()));
											layoutPageTemplateCollectionURL.setParameter("tabs1", "page-templates");
											%>

											<a class='nav-link text-truncate <%= (layoutPageTemplateCollection.getLayoutPageTemplateCollectionId() == layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionId()) ? "active" : StringPool.BLANK %>' href="<%= layoutPageTemplateCollectionURL.toString() %>">
												<%= HtmlUtil.escape(layoutPageTemplateCollection.getName()) %>
											</a>
										</li>

									<%
									}
									%>

								</ul>
							</c:when>
							<c:otherwise>
								<p class="text-uppercase">
									<strong><liferay-ui:message key="collections" /></strong>
								</p>

								<liferay-frontend:empty-result-message
									actionDropdownItems="<%= layoutPageTemplateDisplayContext.isShowAddButton(LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_COLLECTION) ? layoutPageTemplateDisplayContext.getActionDropdownItems() : null %>"
									animationType="<%= EmptyResultMessageKeys.AnimationType.NONE %>"
									description='<%= LanguageUtil.get(request, "collections-are-needed-to-create-page-templates") %>'
									elementType='<%= LanguageUtil.get(request, "collections") %>'
								/>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>

			<%
			LayoutPageTemplateCollection layoutPageTemplateCollection = layoutPageTemplateDisplayContext.getLayoutPageTemplateCollection();
			%>

			<c:if test="<%= layoutPageTemplateCollection != null %>">
				<clay:sheet>
					<h2 class="sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col
								expand="<%= true %>"
							>
								<span class="text-uppercase">
									<%= HtmlUtil.escape(layoutPageTemplateCollection.getName()) %>
								</span>
							</clay:content-col>

							<clay:content-col
								cssClass="inline-item-after"
								verticalAlign="end"
							>
								<liferay-util:include page="/layout_page_template_collection_action.jsp" servletContext="<%= application %>" />
							</clay:content-col>
						</clay:content-row>
					</h2>

					<clay:sheet-section>
						<liferay-util:include page="/view_layout_page_template_entries.jsp" servletContext="<%= application %>" />
					</clay:sheet-section>
				</clay:sheet>
			</c:if>
		</clay:col>
	</clay:row>
</clay:container-fluid>

<aui:form cssClass="hide" name="layoutPageTemplateCollectionsFm">
</aui:form>

<aui:script require="metal-dom/src/dom as dom, frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var deleteCollections = function () {
		var layoutPageTemplateCollectionsFm =
			document.<portlet:namespace />layoutPageTemplateCollectionsFm;

		if (layoutPageTemplateCollectionsFm) {
			var itemSelectorDialog = new ItemSelectorDialog.default({
				buttonAddLabel: '<liferay-ui:message key="delete" />',
				eventName: '<portlet:namespace />selectCollections',
				title: '<liferay-ui:message key="delete-collection" />',
				url:
					'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/layout_page_template/select_layout_page_template_collections" /></portlet:renderURL>',
			});

			itemSelectorDialog.on('selectedItemChange', function (event) {
				var selectedItems = event.selectedItem;

				if (selectedItems) {
					if (
						confirm(
							'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />'
						)
					) {
						Array.prototype.forEach.call(selectedItems, function (
							item,
							index
						) {
							dom.append(layoutPageTemplateCollectionsFm, item);
						});

						<portlet:renderURL var="redirectURL">
							<portlet:param name="tabs1" value="page-templates" />
						</portlet:renderURL>

						<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="/layout_page_template/delete_layout_page_template_collection" var="deleteLayoutPageTemplateCollectionURL">
							<portlet:param name="redirect" value="<%= redirectURL %>" />
						</liferay-portlet:actionURL>

						submitForm(
							layoutPageTemplateCollectionsFm,
							'<%= deleteLayoutPageTemplateCollectionURL %>'
						);
					}
				}
			});

			itemSelectorDialog.open();
		}
	};

	var ACTIONS = {
		deleteCollections: deleteCollections,
	};

	Liferay.componentReady('actionsComponent').then(function (actionsComponent) {
		actionsComponent.on(['click', 'itemClicked'], function (event, facade) {
			var itemData;

			if (event.data && event.data.item) {
				itemData = event.data.item.data;
			}
			else if (!event.data && facade && facade.target) {
				itemData = facade.target.data;
			}

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>