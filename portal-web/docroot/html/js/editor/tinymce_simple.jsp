<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}
%>

<liferay-util:html-top outputKey="js_editor_tinymce">

	<%
	long javaScriptLastModified = ServletContextUtil.getLastModified(application, "/html/js/", true);
	%>

	<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathJavaScript() + "/editor/tiny_mce/tiny_mce.js", javaScriptLastModified)) %>" type="text/javascript"></script>
</liferay-util:html-top>

<div class="<%= cssClass %>">
	<textarea id="<%= name %>" name="<%= name %>" style="height: 100%; width: 100%;"></textarea>
</div>

<aui:script>
	window['<%= name %>'] = {
		onChangeCallbackCounter: 0,

		destroy: function() {
			tinyMCE.editors['<%= name %>'].destroy();
		},

		focus: function() {
			tinyMCE.editors['<%= name %>'].focus();
		},

		fileBrowserCallback: function(field_name, url, type) {
		},

		getHTML: function() {
			return tinyMCE.editors['<%= name %>'].getContent();
		},

		init: function(value) {
			if (typeof value == 'string') {
				value = decodeURIComponent(value);
			}
			 else {
				 value = '';
			 }

			window['<%= name %>'].setHTML(value);
		},

		initInstanceCallback: function() {
			<c:if test="<%= Validator.isNotNull(initMethod) %>">
				window['<%= name %>'].init(<%= HtmlUtil.escape(namespace + initMethod) %>());
			</c:if>
		},

		<%
		if (Validator.isNotNull(onChangeMethod)) {
		%>

			onChangeCallback: function(tinyMCE) {

				// This purposely ignores the first callback event because each call
				// to setContent triggers an undo level which fires the callback
				// when no changes have yet been made.

				// setContent is not really the correct way of initializing this
				// editor with content. The content should be placed statically
				// (from the editor's perspective) within the textarea. This is a
				// problem from the portal's perspective because it's passing the
				// content via a javascript method (initMethod).

				var onChangeCallbackCounter = window['<%= name %>'].onChangeCallbackCounter;

				if (onChangeCallbackCounter > 0) {

					<%= HtmlUtil.escape(onChangeMethod) %>(window['<%= name %>'].getHTML());

				}

				onChangeCallbackCounter++;
			},

		<%
		}
		%>

		setHTML: function(value) {
			tinyMCE.editors['<%= name %>'].setContent(value);
		}
	};

	tinyMCE.init(
		{
			convert_urls: false,
			elements: '<%= name %>',
			extended_valid_elements: 'a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name|usemap],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]',
			file_browser_callback: window['<%= name %>'].fileBrowserCallback,
			init_instance_callback: window['<%= name %>'].initInstanceCallback,
			invalid_elements: 'script',
			language: '<%= HtmlUtil.escape(locale.getLanguage()) %>',
			mode: 'textareas',

			<%
			if (Validator.isNotNull(onChangeMethod)) {
			%>

				onchange_callback: window['<%= name %>'].onChangeCallback,

			<%
			}
			%>

			plugins: 'preview,print,contextmenu',
			relative_urls: false,
			remove_script_host: false,
			theme: 'advanced',
			theme_advanced_buttons1_add: 'code,preview,print',
			theme_advanced_buttons2: '',
			theme_advanced_buttons3: '',
			theme_advanced_disable: 'formatselect,styleselect,help,strikethrough',
			theme_advanced_toolbar_align: 'left',
			theme_advanced_toolbar_location: 'top'
		}
	);
</aui:script>