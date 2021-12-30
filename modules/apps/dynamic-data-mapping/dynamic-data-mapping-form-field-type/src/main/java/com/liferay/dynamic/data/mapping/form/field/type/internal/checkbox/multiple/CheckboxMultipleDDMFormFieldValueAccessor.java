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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE,
	service = {
		CheckboxMultipleDDMFormFieldValueAccessor.class,
		DDMFormFieldValueAccessor.class
	}
)
public class CheckboxMultipleDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<JSONArray> {

	@Override
	public IntFunction<JSONArray[]> getArrayGeneratorIntFunction() {
		return JSONArray[]::new;
	}

	@Override
	public JSONArray getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		return _getOptionsValuesJSONArray(ddmFormFieldValue, locale);
	}

	@Override
	public JSONArray getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		JSONArray optionsValuesJSONArray = _getOptionsValuesJSONArray(
			ddmFormFieldValue, locale);

		if (ddmFormFieldValue.getDDMFormValues() == null) {
			return optionsValuesJSONArray;
		}

		for (int i = 0; i < optionsValuesJSONArray.length(); i++) {
			Matcher matcher = _autoGeneratedIDPattern.matcher(
				optionsValuesJSONArray.getString(i));

			if (matcher.matches()) {
				return createJSONArray(
					StringBundler.concat(
						StringPool.OPEN_BRACKET,
						getOptionsLabels(ddmFormFieldValue, locale),
						StringPool.CLOSE_BRACKET));
			}
		}

		return optionsValuesJSONArray;
	}

	@Override
	public boolean isEmpty(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONArray jsonArray = getValue(ddmFormFieldValue, locale);

		if (jsonArray.length() > 0) {
			return false;
		}

		return true;
	}

	@Override
	public Object map(Object value) {
		if (Validator.isNull(value)) {
			return value;
		}

		try {
			JSONArray jsonArray = jsonFactory.createJSONArray(value.toString());

			StringBundler sb = new StringBundler((jsonArray.length() * 2) - 1);

			for (int i = 0; i < jsonArray.length(); i++) {
				sb.append(jsonArray.get(i));

				if (i < (jsonArray.length() - 1)) {
					sb.append(CharPool.COMMA);
				}
			}

			return sb.toString();
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON array", jsonException);
			}

			return StringPool.BLANK;
		}
	}

	protected JSONArray createJSONArray(String json) {
		try {
			return jsonFactory.createJSONArray(json);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON array", jsonException);
			}

			return jsonFactory.createJSONArray();
		}
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(
		DDMFormFieldValue ddmFormFieldValue) {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		return ddmFormField.getDDMFormFieldOptions();
	}

	protected String getOptionsLabels(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		JSONArray optionsValuesJSONArray = _getOptionsValuesJSONArray(
			ddmFormFieldValue, locale);

		if (optionsValuesJSONArray.length() == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			(optionsValuesJSONArray.length() * 2) - 1);

		DDMFormFieldOptions ddmFormFieldOptions = getDDMFormFieldOptions(
			ddmFormFieldValue);

		for (int i = 0; i < optionsValuesJSONArray.length(); i++) {
			String optionValue = optionsValuesJSONArray.getString(i);

			LocalizedValue optionLabel = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			if (optionLabel != null) {
				sb.append(HtmlUtil.escape(optionLabel.getString(locale)));
			}
			else {
				sb.append(optionValue);
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	@Reference
	protected JSONFactory jsonFactory;

	private JSONArray _getOptionsValuesJSONArray(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return createJSONArray("[]");
		}

		return createJSONArray(value.getString(locale));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckboxMultipleDDMFormFieldValueAccessor.class);

	private static final Pattern _autoGeneratedIDPattern = Pattern.compile(
		"^[\\D]+[\\d]{8}$");

}