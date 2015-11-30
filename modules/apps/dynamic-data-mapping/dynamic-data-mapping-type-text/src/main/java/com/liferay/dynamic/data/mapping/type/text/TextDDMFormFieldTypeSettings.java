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

package com.liferay.dynamic.data.mapping.type.text;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;

/**
 * @author Lino Alves
 */
@DDMForm
@DDMFormLayout( {
	@DDMFormLayoutPage(title = "basic", value = {
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"label"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"tip"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"displayStyle"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"required"})})
	}),
	@DDMFormLayoutPage(title = "advanced", value = {
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"validation"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"showLabel"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"repeatable"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"placeholder"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"predefinedValue"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"visibilityExpression"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"fieldNamespace"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"indexType"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"localizable"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"readOnly"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"dataType"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"type"})}),
		@DDMFormLayoutRow({@DDMFormLayoutColumn({"name"})})
	})
})
public interface TextDDMFormFieldTypeSettings
	extends DefaultDDMFormFieldTypeSettings {

	@DDMFormField(
		label = "%my-text-field-has",
		optionLabels = {"%a-single-line", "%multiple-lines"},
		optionValues = {"singleline", "multiline"},
		properties = {
			"inline=true", "setting.category=basic", "setting.weight=2"
		},
		type = "radio"
	)
	public String displayStyle();

	@DDMFormField(
		dataType = "string", label = "%field-tip",
		properties = {"setting.category=advanced", "setting.weight=2"},
		type = "text"
	)
	public LocalizedValue placeholder();

}