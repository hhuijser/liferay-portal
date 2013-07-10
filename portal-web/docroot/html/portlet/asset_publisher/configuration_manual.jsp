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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List<AssetRendererFactory> classTypesAssetRendererFactories = (List<AssetRendererFactory>)request.getAttribute("configuration.jsp-classTypesAssetRendererFactories");
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");
String redirect = (String)request.getAttribute("configuration.jsp-redirect");
String rootPortletId = (String)request.getAttribute("configuration.jsp-rootPortletId");
String selectScope = (String)request.getAttribute("configuration.jsp-selectScope");
String selectStyle = (String)request.getAttribute("configuration.jsp-selectStyle");
%>

<liferay-ui:tabs
	formName="fm"
	names="asset-selection,display-settings,subscriptions"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="asset-selection" />

		<%= selectStyle %>

		<aui:fieldset label="scope">
			<%= selectScope %>
		</aui:fieldset>

		<aui:fieldset label="model.resource.com.liferay.portlet.asset">

			<%
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("title");
			headerNames.add("type");
			headerNames.add("modified-date");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(renderRequest, new DisplayTerms(renderRequest), new DisplayTerms(renderRequest), SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, configurationRenderURL, headerNames, LanguageUtil.get(pageContext, "no-assets-selected"));

			Tuple tuple = AssetPublisherUtil.getAssetEntries(renderRequest, portletPreferences, permissionChecker, groupIds, assetEntryXmls, true, false);

			List<AssetEntry> assetEntries = (List<AssetEntry>)tuple.getObject(0);

			int total = assetEntries.size();

			searchContainer.setTotal(total);

			int end = (assetEntries.size() < searchContainer.getEnd()) ? assetEntries.size() : searchContainer.getEnd();
			int start = searchContainer.getStart();

			assetEntries = assetEntries.subList(start, end);

			searchContainer.setResults(assetEntries);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < assetEntries.size(); i++) {
				String assetEntryXml = assetEntryXmls[start + i];

				Document doc = SAXReaderUtil.read(assetEntryXml);

				Element root = doc.getRootElement();

				int assetEntryOrder = start + i;

				DocUtil.add(root, "asset-order", assetEntryOrder);

				if (assetEntryOrder == (assetEntryXmls.length - 1)) {
					DocUtil.add(root, "last", true);
				}
				else {
					DocUtil.add(root, "last", false);
				}

				AssetEntry assetEntry = assetEntries.get(i);

				AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetEntry.getClassName());

				ResultRow row = new ResultRow(doc, null, assetEntryOrder);

				// Title

				AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

				StringBundler sb = new StringBundler(4);

				sb.append("<img alt=\"\" src=\"");
				sb.append(assetRenderer.getIconPath(renderRequest));
				sb.append("\" />");
				sb.append(HtmlUtil.escape(assetRenderer.getTitle(locale)));

				row.addText(sb.toString());

				// Type

				row.addText(assetRendererFactory.getTypeName(locale, false));

				// Modified Date

				Date modifiedDate = assetEntry.getModifiedDate();

				row.addText(LanguageUtil.format(pageContext, "x-ago", LanguageUtil.getTimeDescription(pageContext, System.currentTimeMillis() - modifiedDate.getTime(), true)));

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/asset_publisher/asset_selection_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<c:if test='<%= SessionMessages.contains(renderRequest, "removedSelectedAssets") %>'>
				<div class="alert alert-info">
					<liferay-ui:message key="the-selected-assets-have-been-removed-from-the-list-because-they-do-not-belong-in-the-scope-of-this-portlet" />
				</div>
			</c:if>

			<liferay-ui:search-iterator paginate="<%= total > SearchContainer.DEFAULT_DELTA %>" searchContainer="<%= searchContainer %>" />

			<%
			classNameIds = availableClassNameIds;

			String portletId = portletResource;

			for (long groupId : groupIds) {
			%>

				<div class="select-asset-selector">
					<div class="lfr-meta-actions edit-controls">
						<liferay-ui:icon-menu cssClass="select-existing-selector" direction="right" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message='<%= LanguageUtil.format(pageContext, (groupIds.length == 1) ? "select" : "select-in-x", new Object[] {HtmlUtil.escape((GroupLocalServiceUtil.getGroup(groupId)).getDescriptiveName(locale))}) %>' showWhenSingleIcon="<%= true %>">

							<%
							PortletURL assetBrowserURL = PortletURLFactoryUtil.create(request, PortletKeys.ASSET_BROWSER, PortalUtil.getControlPanelPlid(company.getCompanyId()), PortletRequest.RENDER_PHASE);

							assetBrowserURL.setParameter("struts_action", "/asset_browser/view");
							assetBrowserURL.setParameter("groupId", String.valueOf(groupId));
							assetBrowserURL.setParameter("callback", liferayPortletResponse.getNamespace() + "selectAsset");
							assetBrowserURL.setPortletMode(PortletMode.VIEW);
							assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);

							for (AssetRendererFactory curRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId())) {
								if (!curRendererFactory.isSelectable()) {
									continue;
								}

								assetBrowserURL.setParameter("typeSelection", curRendererFactory.getClassName());

								String taglibURL = "javascript:Liferay.Util.openWindow({id: '" + liferayPortletResponse.getNamespace() + "selectAsset', title: '" + LanguageUtil.format(pageContext, "select-x", curRendererFactory.getTypeName(locale, false)) + "', uri:'" + HtmlUtil.escapeURL(assetBrowserURL.toString()) + "'});";
							%>

								<liferay-ui:icon message="<%= curRendererFactory.getTypeName(locale, false) %>" src="<%= curRendererFactory.getIconPath(renderRequest) %>" url="<%= taglibURL %>" />

							<%
							}
							%>

						</liferay-ui:icon-menu>
					</div>
				</div>

			<%
			}
			%>

		</aui:fieldset>
	</liferay-ui:section>
	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="display-settings" />

		<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
	</liferay-ui:section>
	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="subscriptions" />

		<%@ include file="/html/portlet/asset_publisher/email_subscription_settings.jspf" %>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
</aui:button-row>