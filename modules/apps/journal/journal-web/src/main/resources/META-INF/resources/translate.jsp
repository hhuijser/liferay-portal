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
String redirect = ParamUtil.getString(request, "redirect");

JournalTranslateDisplayContext journalTranslateDisplayContext = new JournalTranslateDisplayContext(liferayPortletRequest, liferayPortletResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(journalTranslateDisplayContext.getTitle());
%>

<aui:form action="<%= journalTranslateDisplayContext.getUpdateTranslationPortletURL() %>" cssClass="translate-article" name="translate_fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="targetLanguageId" type="hidden" value="<%= journalTranslateDisplayContext.getTargetLanguageId() %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />

	<nav class="component-tbar subnav-tbar-light tbar">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<div class="tbar-section text-left">
						<react:component
							data="<%= journalTranslateDisplayContext.getTranslateLanguagesSelectorData() %>"
							module="js/translate/TranslateLanguagesSelector"
						/>
					</div>
				</li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" id="saveDraftBtn" primary="<%= false %>" type="submit" value="<%= journalTranslateDisplayContext.getSaveButtonLabel() %>" />

						<aui:button cssClass="btn-sm" disabled="<%= journalTranslateDisplayContext.isPending() %>" id="submitBtnId" primary="<%= true %>" type="submit" value="<%= journalTranslateDisplayContext.getPublishButtonLabel() %>" />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="translate-body-form">
			<clay:row>
				<clay:col
					md="6"
				>

					<%
					String sourceLanguageIdTitle = journalTranslateDisplayContext.getLanguageIdTitle(journalTranslateDisplayContext.getSourceLanguageId());
					%>

					<clay:icon
						symbol="<%= StringUtil.toLowerCase(sourceLanguageIdTitle) %>"
					/>

					<span class="ml-1"> <%= sourceLanguageIdTitle %> </span>

					<div class="separator"><!-- --></div>
				</clay:col>

				<clay:col
					md="6"
				>

					<%
					String targetLanguageIdTitle = journalTranslateDisplayContext.getLanguageIdTitle(journalTranslateDisplayContext.getTargetLanguageId());
					%>

					<clay:icon
						symbol="<%= StringUtil.toLowerCase(targetLanguageIdTitle) %>"
					/>

					<span class="ml-1"> <%= targetLanguageIdTitle %> </span>

					<div class="separator"><!-- --></div>
				</clay:col>
			</clay:row>

			<%
			for (InfoFieldSetEntry infoFieldSetEntry : journalTranslateDisplayContext.getInfoFieldSetEntries()) {
				List<InfoField> infoFields = journalTranslateDisplayContext.getInfoFields(infoFieldSetEntry);

				if (ListUtil.isEmpty(infoFields)) {
					continue;
				}

				String infoFieldSetLabel = journalTranslateDisplayContext.getInfoFieldSetLabel(infoFieldSetEntry, locale);

				if (Validator.isNotNull(infoFieldSetLabel)) {
			%>

					<clay:row>
						<clay:col
							md="6"
						>
							<div class="fieldset-title">
								<%= infoFieldSetLabel %>
							</div>
						</clay:col>

						<clay:col
							md="6"
						>
							<div class="fieldset-title">
								<%= infoFieldSetLabel %>
							</div>
						</clay:col>
					</clay:row>

				<%
				}

				for (InfoField<TextInfoFieldType> infoField : infoFields) {
					String id = "infoField--" + infoField.getName() + "--";
					String label = journalTranslateDisplayContext.getInfoFieldLabel(infoField);

					String sourceContent = journalTranslateDisplayContext.getStringValue(infoField, journalTranslateDisplayContext.getSourceLocale());
					String targetContent = journalTranslateDisplayContext.getStringValue(infoField, journalTranslateDisplayContext.getTargetLocale());
				%>

					<clay:row>
						<clay:col
							md="6"
						>
							<c:choose>
								<c:when test="<%= infoField.getAttributeOptional(TextInfoFieldType.RICH).orElse(false) %>">
									<label class="control-label">
										<%= label %>
									</label>
									<div contenteditable="false" role="textbox" tabIndex="-1">
										<%= sourceContent %>
									</div>
								</c:when>
								<c:otherwise>
									<aui:input dir='<%= LanguageUtil.get(journalTranslateDisplayContext.getSourceLocale(), "lang.dir") %>' label="<%= label %>" name="<%= label %>" readonly="true" tabIndex="-1" value="<%= sourceContent %>" />
								</c:otherwise>
							</c:choose>
						</clay:col>

						<clay:col
							md="6"
						>
							<c:choose>
								<c:when test="<%= infoField.getAttributeOptional(TextInfoFieldType.RICH).orElse(false) %>">
									<liferay-editor:editor
										contents="<%= targetContent %>"
										name='<%= id + "TargetEditor" %>'
										placeholder="<%= label %>"
									/>
								</c:when>
								<c:otherwise>
									<aui:input dir='<%= LanguageUtil.get(journalTranslateDisplayContext.getTargetLocale(), "lang.dir") %>' label="<%= label %>" name="<%= id %>" value="<%= targetContent %>" />
								</c:otherwise>
							</c:choose>
						</clay:col>
					</clay:row>

			<%
				}
			}
			%>

		</div>
	</clay:container-fluid>
</aui:form>

<script>
	var saveDraftBtn = document.getElementById('<portlet:namespace />saveDraftBtn');

	saveDraftBtn.addEventListener('click', function () {
		var workflowActionInput = document.getElementById(
			'<portlet:namespace />workflowAction'
		);

		workflowActionInput.value = '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>';
	});
</script>