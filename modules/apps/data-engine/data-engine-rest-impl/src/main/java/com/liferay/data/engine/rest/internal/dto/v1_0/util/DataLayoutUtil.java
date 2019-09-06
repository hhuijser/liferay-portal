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

package com.liferay.data.engine.rest.internal.dto.v1_0.util;

import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v1_0.DataLayoutRow;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutUtil {

	public static JSONObject getFieldTypeMetadataJSONObject(
		AcceptLanguage acceptLanguage,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormTemplateContextFactory ddmFormTemplateContextFactory,
		DDMFormValuesFactory ddmFormValuesFactory, FieldType fieldType,
		FieldTypeTracker fieldTypeTracker,
		HttpServletRequest httpServletRequest, NPMResolver npmResolver) {

		Map<String, Object> fieldTypeProperties =
			fieldTypeTracker.getFieldTypeProperties(fieldType.getName());

		return JSONUtil.put(
			"description",
			_getLanguageTerm(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.description"),
				acceptLanguage.getPreferredLocale())
		).put(
			"group",
			MapUtil.getString(
				fieldTypeProperties, "data.engine.field.type.group")
		).put(
			"icon",
			MapUtil.getString(
				fieldTypeProperties, "data.engine.field.type.icon")
		).put(
			"javaScriptModule",
			_getJavaScriptModule(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.js.module"),
				npmResolver)
		).put(
			"label",
			_getLanguageTerm(
				MapUtil.getString(
					fieldTypeProperties, "data.engine.field.type.label"),
				acceptLanguage.getPreferredLocale())
		).put(
			"name", fieldType.getName()
		).put(
			"settingsContext",
			_createFieldContext(
				ddmFormFieldTypeServicesTracker, ddmFormTemplateContextFactory,
				ddmFormValuesFactory, httpServletRequest,
				acceptLanguage.getPreferredLocale(), fieldType.getName())
		).put(
			"system",
			MapUtil.getBoolean(
				fieldTypeProperties, "data.engine.field.type.system")
		);
	}

	public static DataLayout toDataLayout(String json) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		return new DataLayout() {
			{
				dataLayoutPages = JSONUtil.toArray(
					jsonObject.getJSONArray("pages"),
					pageJSONObject -> _toDataLayoutPage(pageJSONObject),
					DataLayoutPage.class);
				paginationMode = jsonObject.getString("paginationMode");
			}
		};
	}

	public static String toJSON(DataLayout dataLayout) throws Exception {
		if (!Objects.equals(dataLayout.getPaginationMode(), "pagination") &&
			!Objects.equals(dataLayout.getPaginationMode(), "wizard")) {

			throw new Exception(
				"Pagination mode must be \"pagination\" or \"wizard\"");
		}

		return JSONUtil.put(
			"pages",
			JSONUtil.toJSONArray(
				dataLayout.getDataLayoutPages(),
				dataLayoutPage -> _toJSONObject(dataLayoutPage))
		).put(
			"paginationMode", dataLayout.getPaginationMode()
		).toString();
	}

	private static JSONObject _createFieldContext(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormTemplateContextFactory ddmFormTemplateContextFactory,
		DDMFormValuesFactory ddmFormValuesFactory,
		HttpServletRequest httpServletRequest, Locale locale, String type) {

		try {
			Class<?> ddmFormFieldTypeSettings = _getDDMFormFieldTypeSettings(
				ddmFormFieldTypeServicesTracker, type);

			DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
				ddmFormFieldTypeSettings);

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setContainerId("settings");

			DDMFormValues ddmFormValues = ddmFormValuesFactory.create(
				httpServletRequest, ddmFormFieldTypeSettingsDDMForm);

			_setTypeDDMFormFieldValue(ddmFormValues, type);

			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);

			ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
			ddmFormRenderingContext.setLocale(locale);
			ddmFormRenderingContext.setPortletNamespace(
				ParamUtil.getString(httpServletRequest, "portletNamespace"));
			ddmFormRenderingContext.setReturnFullContext(true);

			return JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerializeDeep(
					ddmFormTemplateContextFactory.create(
						ddmFormFieldTypeSettingsDDMForm,
						DDMFormLayoutFactory.create(ddmFormFieldTypeSettings),
						ddmFormRenderingContext)));
		}
		catch (Exception e) {
		}

		return null;
	}

	private static Class<?> _getDDMFormFieldTypeSettings(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		String type) {

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		return ddmFormFieldType.getDDMFormFieldTypeSettings();
	}

	private static String _getJavaScriptModule(
		String moduleName, NPMResolver npmResolver) {

		if (Validator.isNull(moduleName)) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(moduleName);
	}

	private static String _getLanguageTerm(String key, Locale locale) {
		if (Validator.isNull(key)) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(
			ResourceBundleUtil.getString(_getResourceBundle(locale), key), key);
	}

	private static ResourceBundle _getResourceBundle(Locale locale) {
		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, DataLayoutUtil.class.getClass()),
			PortalUtil.getResourceBundle(locale));
	}

	private static void _setTypeDDMFormFieldValue(
		DDMFormValues ddmFormValues, String type) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"type");

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		ddmFormFieldValue.setValue(new UnlocalizedValue(type));
	}

	private static DataLayoutColumn _toDataLayoutColumn(JSONObject jsonObject) {
		return new DataLayoutColumn() {
			{
				columnSize = jsonObject.getInt("columnSize");
				fieldNames = JSONUtil.toStringArray(
					jsonObject.getJSONArray("fieldNames"));
			}
		};
	}

	private static DataLayoutPage _toDataLayoutPage(JSONObject jsonObject)
		throws Exception {

		return new DataLayoutPage() {
			{
				dataLayoutRows = JSONUtil.toArray(
					jsonObject.getJSONArray("rows"),
					rowJSONObject -> _toDataLayoutRow(rowJSONObject),
					DataLayoutRow.class);
				description = LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("description"));
				title = LocalizedValueUtil.toLocalizedValues(
					jsonObject.getJSONObject("title"));
			}
		};
	}

	private static DataLayoutRow _toDataLayoutRow(JSONObject jsonObject)
		throws Exception {

		return new DataLayoutRow() {
			{
				dataLayoutColumns = JSONUtil.toArray(
					jsonObject.getJSONArray("columns"),
					columnJSONObject -> _toDataLayoutColumn(columnJSONObject),
					DataLayoutColumn.class);
			}
		};
	}

	private static JSONObject _toJSONObject(DataLayoutColumn dataLayoutColumn)
		throws Exception {

		return JSONUtil.put(
			"columnSize", dataLayoutColumn.getColumnSize()
		).put(
			"fieldNames", JSONUtil.put(dataLayoutColumn.getFieldNames())
		);
	}

	private static JSONObject _toJSONObject(DataLayoutPage dataLayoutPage)
		throws Exception {

		if (MapUtil.isEmpty(dataLayoutPage.getTitle())) {
			throw new Exception("Title is required");
		}

		return JSONUtil.put(
			"description",
			LocalizedValueUtil.toJSONObject(dataLayoutPage.getDescription())
		).put(
			"rows",
			JSONUtil.toJSONArray(
				dataLayoutPage.getDataLayoutRows(),
				dataLayoutRow -> _toJSONObject(dataLayoutRow))
		).put(
			"title", LocalizedValueUtil.toJSONObject(dataLayoutPage.getTitle())
		);
	}

	private static JSONObject _toJSONObject(DataLayoutRow dataLayoutRow)
		throws Exception {

		return JSONUtil.put(
			"columns",
			JSONUtil.toJSONArray(
				dataLayoutRow.getDataLayoutColumns(),
				dataLayoutColumn -> _toJSONObject(dataLayoutColumn)));
	}

}