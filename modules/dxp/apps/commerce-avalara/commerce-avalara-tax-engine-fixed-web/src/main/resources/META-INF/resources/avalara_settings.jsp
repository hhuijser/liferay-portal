<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTaxAvalaraTypeConfiguration commerceTaxAvalaraTypeConfiguration = (CommerceTaxAvalaraTypeConfiguration)request.getAttribute(CommerceTaxAvalaraTypeConfiguration.class.getName());
%>

<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_avalara" var="editCommerceTaxAvalaraActionURL" />

<commerce-ui:side-panel-content
	title='<%= LanguageUtil.get(resourceBundle, "edit-avalara-settings") %>'
>
	<aui:form action="<%= editCommerceTaxAvalaraActionURL %>" method="post" name="fm">
		<commerce-ui:panel>
			<%@ include file="/edit_avalara_settings.jspf" %>
		</commerce-ui:panel>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />
		</aui:button-row>
	</aui:form>
</commerce-ui:side-panel-content>