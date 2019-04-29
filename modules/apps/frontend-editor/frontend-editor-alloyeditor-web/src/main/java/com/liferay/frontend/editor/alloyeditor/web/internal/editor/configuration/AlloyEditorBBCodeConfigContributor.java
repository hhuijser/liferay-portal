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

package com.liferay.frontend.editor.alloyeditor.web.internal.editor.configuration;

import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=alloyeditor_bbcode",
	service = EditorConfigContributor.class
)
public class AlloyEditorBBCodeConfigContributor
	extends BaseAlloyEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		jsonObject.put(
			"allowedContent", Boolean.TRUE
		).put(
			"enterMode", 1
		);

		String extraPlugins = jsonObject.getString("extraPlugins");

		extraPlugins = extraPlugins.concat(",bbcode,itemselector");

		jsonObject.put(
			"extraPlugins", extraPlugins
		).put(
			"forceEnterMode", Boolean.TRUE
		).put(
			"format_tags", "p;pre"
		).put(
			"lang", getLangJSONObject(inputEditorTaglibAttributes)
		).put(
			"newThreadURL", MBThreadConstants.NEW_THREAD_URL
		);

		String removePlugins = jsonObject.getString("removePlugins");

		StringBundler sb = new StringBundler(3);

		sb.append("bidi,div,flash,font,forms,indentblock,keystrokes,maximize,");
		sb.append("newpage,pagebreak,preview,print,save,showblocks,smiley,");
		sb.append("stylescombo,templates,video");

		jsonObject.put(
			"removePlugins",
			removePlugins.concat(
				","
			).concat(
				sb.toString()
			)
		).put(
			"smiley_images",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonFiles())
		).put(
			"smiley_path",
			HtmlUtil.escape(themeDisplay.getPathThemeImages()) + "/emoticons/"
		).put(
			"smiley_symbols",
			toJSONArray(BBCodeTranslatorUtil.getEmoticonSymbols())
		).put(
			"toolbars", getToolbarsJSONObject(themeDisplay.getLocale())
		);
	}

	protected JSONObject getLangJSONObject(
		Map<String, Object> inputEditorTaglibAttributes) {

		JSONObject jsonObject = JSONUtil.put(
			"code",
			LanguageUtil.get(
				getContentsLocale(inputEditorTaglibAttributes), "code"));

		return jsonObject;
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element, String cssClass, int type) {

		JSONObject jsonObject = JSONUtil.put(
			"name", styleFormatName
		).put(
			"style", getStyleJSONObject(element, cssClass, type)
		);

		return jsonObject;
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = _resourceBundleLoader.loadResourceBundle(locale);
		}
		catch (MissingResourceException mre) {
			resourceBundle = ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE;
		}

		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p", null,
				_CKEDITOR_STYLE_BLOCK),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "cited-work"), "cite", null,
				_CKEDITOR_STYLE_INLINE),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "code", null,
				_CKEDITOR_STYLE_INLINE));
	}

	protected JSONObject getStyleFormatsJSONObject(Locale locale) {
		JSONObject styleFormatsJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject stylesJSONObject = JSONUtil.put(
			"styles", getStyleFormatsJSONArray(locale));

		styleFormatsJSONObject.put(
			"cfg", stylesJSONObject
		).put(
			"name", "styles"
		);

		return styleFormatsJSONObject;
	}

	protected JSONObject getStyleJSONObject(
		String element, String cssClass, int type) {

		JSONObject styleJSONObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(cssClass)) {
			JSONObject attributesJSONObject = JSONUtil.put("class", cssClass);

			styleJSONObject.put("attributes", attributesJSONObject);
		}

		styleJSONObject.put(
			"element", element
		).put(
			"type", type
		);

		return styleJSONObject;
	}

	protected JSONObject getToolbarsAddJSONObject() {
		JSONObject jsonObject = JSONUtil.put(
			"buttons", JSONUtil.put("image")
		).put(
			"tabIndex", 2
		);

		return jsonObject;
	}

	protected JSONObject getToolbarsJSONObject(Locale locale) {
		JSONObject jsonObject = JSONUtil.put(
			"add", getToolbarsAddJSONObject()
		).put(
			"styles", getToolbarsStylesJSONObject(locale)
		);

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesJSONObject(Locale locale) {
		JSONObject jsonObject = JSONUtil.put(
			"selections", getToolbarsStylesSelectionsJSONArray(locale)
		).put(
			"tabIndex", 1
		);

		return jsonObject;
	}

	protected JSONArray getToolbarsStylesSelectionsJSONArray(Locale locale) {
		return JSONUtil.putAll(
			getToolbarsStylesSelectionsLinkJSONObject(),
			getToolbarsStylesSelectionsTextJSONObject(locale));
	}

	protected JSONObject getToolbarsStylesSelectionsLinkJSONObject() {
		JSONObject jsonObject = JSONUtil.put(
			"buttons", toJSONArray("['linkEditBrowse']")
		).put(
			"name", "link"
		).put(
			"test", "AlloyEditor.SelectionTest.link"
		);

		return jsonObject;
	}

	protected JSONObject getToolbarsStylesSelectionsTextJSONObject(
		Locale locale) {

		JSONObject jsonObject = JSONUtil.put(
			"buttons",
			JSONUtil.putAll(
				getStyleFormatsJSONObject(locale), "bold", "italic",
				"underline", "ol", "ul", "linkBrowse", "quote")
		).put(
			"name", "text"
		).put(
			"test", "AlloyEditor.SelectionTest.text"
		);

		return jsonObject;
	}

	private static final int _CKEDITOR_STYLE_BLOCK = 1;

	private static final int _CKEDITOR_STYLE_INLINE = 2;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.frontend.editor.lang)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}