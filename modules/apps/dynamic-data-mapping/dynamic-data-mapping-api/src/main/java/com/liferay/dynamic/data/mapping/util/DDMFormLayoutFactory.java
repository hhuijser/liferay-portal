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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.lang.annotation.Annotation;

/**
 * @author Leonardo Barros
 */
public class DDMFormLayoutFactory {

	public static com.liferay.dynamic.data.mapping.model.DDMFormLayout create(
		Class<?> clazz) {

		if (!clazz.isAnnotationPresent(_DDM_FORM_LAYOUT_ANNOTATION)) {
			throw new IllegalArgumentException(
				"Unsupported class " + clazz.getName());
		}

		return createDDMFormLayout(clazz);
	}

	protected static com.liferay.dynamic.data.mapping.model.DDMFormLayout
		createDDMFormLayout(Class<?> clazz) {

		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout =
			new com.liferay.dynamic.data.mapping.model.DDMFormLayout();

		DDMFormLayout ddmFormLayoutAnnotation =
			(DDMFormLayout)clazz.getAnnotation(_DDM_FORM_LAYOUT_ANNOTATION);

		DDMFormLayoutPage[] ddmFormLayoutPages =
			ddmFormLayoutAnnotation.value();

		for (DDMFormLayoutPage ddmFormLayoutPage : ddmFormLayoutPages) {
			ddmFormLayout.addDDMFormLayoutPage(
				createDDMFormLayoutPage(ddmFormLayoutPage));
		}

		return ddmFormLayout;
	}

	protected static com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn
		createDDMFormLayoutColumn(DDMFormLayoutColumn ddmFormLayoutColumnAnnotation) {

		com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn
			ddmFormLayoutColumn = new com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn();

		String[] formFields = ddmFormLayoutColumnAnnotation.value();

		ddmFormLayoutColumn.setDDMFormFieldNames(
			ListUtil.fromArray(formFields));
		ddmFormLayoutColumn.setSize(
			com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn.FULL);

		return ddmFormLayoutColumn;
	}

	protected static com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage
		createDDMFormLayoutPage(DDMFormLayoutPage ddmFormLayoutPageAnnotation) {

		com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage
			ddmFormLayoutPage = new com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage();

		DDMFormLayoutRow[] ddmFormLayoutRows = ddmFormLayoutPageAnnotation.value();

		LocalizedValue title = new LocalizedValue();
		title.addString(
			LocaleUtil.getDefault(), ddmFormLayoutPageAnnotation.title());

		ddmFormLayoutPage.setTitle(title);

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			ddmFormLayoutPage.addDDMFormLayoutRow(
				createDDMFormLayoutRow(ddmFormLayoutRow));
		}

		return ddmFormLayoutPage;
	}

	protected static com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow
		createDDMFormLayoutRow(DDMFormLayoutRow ddmFormLayoutRowAnnotation) {

		com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow
			ddmFormLayoutRow = new com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow();

		DDMFormLayoutColumn[] ddmFormLayoutColumns =
			ddmFormLayoutRowAnnotation.value();

		for (DDMFormLayoutColumn ddmFormLayoutColumn : ddmFormLayoutColumns) {
			ddmFormLayoutRow.addDDMFormLayoutColumn(
				createDDMFormLayoutColumn(ddmFormLayoutColumn));
		}

		return ddmFormLayoutRow;
	}

	private static final Class<? extends Annotation>
		_DDM_FORM_LAYOUT_ANNOTATION =
			com.liferay.dynamic.data.mapping.annotations.DDMFormLayout.class;

}