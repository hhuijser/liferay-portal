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

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Marcellus Tavares
 */
public class DDMFormTemplateSynchonizer {

	public DDMFormTemplateSynchonizer(
		DDMForm structureDDMForm, DDMFormDeserializer ddmFormDeserializer,
		DDMFormSerializer ddmFormSerializer,
		DDMTemplateLocalService ddmTemplateLocalService) {

		_structureDDMForm = structureDDMForm;
		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormSerializer = ddmFormSerializer;
		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	public void setDDMFormTemplates(List<DDMTemplate> ddmFormTemplates) {
		_ddmFormTemplates = ddmFormTemplates;
	}

	public void synchronize() throws PortalException {
		for (DDMTemplate ddmTemplate : _getDDMFormTemplates()) {
			DDMForm templateDDMForm = deserialize(ddmTemplate.getScript());

			_synchronizeDDMFormFields(
				_structureDDMForm.getDDMFormFieldsMap(true),
				templateDDMForm.getDDMFormFields(), ddmTemplate.getMode());

			String mode = ddmTemplate.getMode();

			if (mode.equals(DDMTemplateConstants.TEMPLATE_MODE_CREATE)) {
				_addRequiredDDMFormFields(
					_structureDDMForm.getDDMFormFields(),
					templateDDMForm.getDDMFormFields());
			}

			_updateDDMTemplate(ddmTemplate, templateDDMForm);
		}
	}

	protected DDMForm deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	protected DDMFormField getDDMFormField(
		List<DDMFormField> ddmFormFields, String name) {

		Queue<DDMFormField> queue = new LinkedList<>(ddmFormFields);

		DDMFormField ddmFormField = null;

		while ((ddmFormField = queue.poll()) != null) {
			if (name.equals(ddmFormField.getName())) {
				return ddmFormField;
			}

			queue.addAll(ddmFormField.getNestedDDMFormFields());
		}

		return null;
	}

	protected String serialize(DDMForm ddmForm) {
		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	private void _addRequiredDDMFormFields(
		List<DDMFormField> structureDDMFormFields,
		List<DDMFormField> templateDDMFormFields) {

		for (DDMFormField structureDDMFormField : structureDDMFormFields) {
			DDMFormField templateDDMFormField = getDDMFormField(
				templateDDMFormFields, structureDDMFormField.getName());

			if (templateDDMFormField == null) {
				if (structureDDMFormField.isRequired()) {
					templateDDMFormFields.add(structureDDMFormField);
				}
			}
			else {
				_addRequiredDDMFormFields(
					structureDDMFormField.getNestedDDMFormFields(),
					templateDDMFormField.getNestedDDMFormFields());
			}
		}
	}

	private List<DDMTemplate> _getDDMFormTemplates() {
		return _ddmFormTemplates;
	}

	private void _synchronizeDDMFormFieldOptions(
		DDMFormField structureDDMFormField, DDMFormField templateDDMFormField) {

		if (structureDDMFormField == null) {
			return;
		}

		String fieldType = structureDDMFormField.getType();

		if (fieldType.equals(DDMFormFieldType.SELECT) ||
			fieldType.equals(DDMFormFieldType.RADIO)) {

			templateDDMFormField.setDDMFormFieldOptions(
				structureDDMFormField.getDDMFormFieldOptions());
		}
	}

	private void _synchronizeDDMFormFieldRequiredProperty(
		DDMFormField structureDDMFormField, DDMFormField templateDDMFormField,
		String templateMode) {

		if ((structureDDMFormField == null) ||
			!templateMode.equals(DDMTemplateConstants.TEMPLATE_MODE_CREATE)) {

			return;
		}

		templateDDMFormField.setRequired(structureDDMFormField.isRequired());
	}

	private void _synchronizeDDMFormFields(
		Map<String, DDMFormField> structureDDMFormFieldsMap,
		List<DDMFormField> templateDDMFormFields, String templateMode) {

		Iterator<DDMFormField> iterator = templateDDMFormFields.iterator();

		while (iterator.hasNext()) {
			DDMFormField templateDDMFormField = iterator.next();

			String name = templateDDMFormField.getName();

			if (Validator.isNotNull(templateDDMFormField.getDataType()) &&
				!structureDDMFormFieldsMap.containsKey(name)) {

				iterator.remove();

				continue;
			}

			_synchronizeDDMFormFieldOptions(
				structureDDMFormFieldsMap.get(name), templateDDMFormField);

			_synchronizeDDMFormFieldRequiredProperty(
				structureDDMFormFieldsMap.get(name), templateDDMFormField,
				templateMode);

			_synchronizeDDMFormFields(
				structureDDMFormFieldsMap,
				templateDDMFormField.getNestedDDMFormFields(), templateMode);
		}
	}

	private void _updateDDMTemplate(
		DDMTemplate ddmTemplate, DDMForm templateDDMForm) {

		ddmTemplate.setScript(serialize(templateDDMForm));

		_ddmTemplateLocalService.updateDDMTemplate(ddmTemplate);
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormSerializer _ddmFormSerializer;
	private List<DDMTemplate> _ddmFormTemplates = new ArrayList<>();
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final DDMForm _structureDDMForm;

}