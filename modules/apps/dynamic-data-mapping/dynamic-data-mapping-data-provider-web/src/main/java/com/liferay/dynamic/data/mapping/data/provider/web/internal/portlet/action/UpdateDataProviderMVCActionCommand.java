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

package com.liferay.dynamic.data.mapping.data.provider.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.constants.DDMDataProviderPortletKeys;
import com.liferay.dynamic.data.mapping.data.provider.web.internal.util.DDMDataProviderPortletUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMDataProviderPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER,
		"mvc.command.name=updateDataProvider"
	},
	service = MVCActionCommand.class
)
public class UpdateDataProviderMVCActionCommand
	extends AddDataProviderMVCActionCommand {

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
				"json");

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long dataProviderInstanceId = ParamUtil.getLong(
			actionRequest, "dataProviderInstanceId");

		DDMFormValues ddmFormValues = getDDMFormValues(
			actionRequest, actionResponse);

		restorePasswordDDMFormFieldValues(
			dataProviderInstanceId, ddmFormValues);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMDataProviderInstance.class.getName(), actionRequest);

		ddmDataProviderInstanceService.updateDataProviderInstance(
			dataProviderInstanceId,
			getLocalizedMap(themeDisplay.getSiteDefaultLocale(), name),
			getLocalizedMap(themeDisplay.getSiteDefaultLocale(), description),
			ddmFormValues, serviceContext);
	}

	protected Optional<DDMFormFieldValue> findStoredDDMFormFieldValue(
		String name, String instanceId, DDMFormValues storedDDMFormValues) {

		Set<DDMFormFieldValue> storedDDMFormFieldValues = new HashSet<>();

		flattenDDMFormFieldValues(
			storedDDMFormValues.getDDMFormFieldValues(),
			storedDDMFormFieldValues);

		Stream<DDMFormFieldValue> storedDDMFormFieldValuesStream =
			storedDDMFormFieldValues.stream();

		Predicate<DDMFormFieldValue> predicate = ddmFormFieldValue ->
			Objects.equals(ddmFormFieldValue.getName(), name) &&
			Objects.equals(ddmFormFieldValue.getInstanceId(), instanceId);

		return storedDDMFormFieldValuesStream.filter(
			predicate
		).findFirst();
	}

	protected void flattenDDMFormFieldValues(
		List<DDMFormFieldValue> storedDDMFormFieldValues,
		Set<DDMFormFieldValue> collectedDDMFormFieldValues) {

		for (DDMFormFieldValue storedDDMFormFieldValue :
				storedDDMFormFieldValues) {

			collectedDDMFormFieldValues.add(storedDDMFormFieldValue);

			flattenDDMFormFieldValues(
				storedDDMFormFieldValue.getNestedDDMFormFieldValues(),
				collectedDDMFormFieldValues);
		}
	}

	protected DDMForm getDataProviderInstanceSettingsDDMForm(
		DDMDataProviderInstance dataProviderInstance) {

		DDMDataProvider ddmDataProvider =
			ddmDataProviderTracker.getDDMDataProvider(
				dataProviderInstance.getType());

		Class<?> clazz = ddmDataProvider.getSettings();

		return DDMFormFactory.create(clazz);
	}

	protected DDMFormValues getStoredDDMFormValues(
		DDMForm dataProviderInstanceSettingsDDMForm,
		String dataProviderInstanceDefinition) {

		return deserialize(
			dataProviderInstanceDefinition,
			dataProviderInstanceSettingsDDMForm);
	}

	protected void restoreDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue,
		DDMFormFieldValue storedDDMFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		Value storedValue = storedDDMFormFieldValue.getValue();

		for (Locale availableLocale : value.getAvailableLocales()) {
			if (Objects.equals(
					value.getString(availableLocale),
					Portal.TEMP_OBFUSCATION_VALUE)) {

				value.addString(
					availableLocale, storedValue.getString(availableLocale));
			}
		}
	}

	protected void restoreDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue,
		DDMFormValues storedDDMFormValues) {

		Optional<DDMFormFieldValue> storedFormFieldValueOptional =
			findStoredDDMFormFieldValue(
				ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId(),
				storedDDMFormValues);

		storedFormFieldValueOptional.ifPresent(
			storedDDMFormFieldValue -> restoreDDMFormFieldValue(
				ddmFormFieldValue, storedDDMFormFieldValue));
	}

	protected void restoreDDMFormFieldValues(
		Set<String> ddmFormFieldNamesToBeRestored,
		List<DDMFormFieldValue> ddmFormFieldValues,
		DDMFormValues storedDDMFormValues) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (ddmFormFieldNamesToBeRestored.contains(
					ddmFormFieldValue.getName())) {

				restoreDDMFormFieldValue(
					ddmFormFieldValue, storedDDMFormValues);
			}

			restoreDDMFormFieldValues(
				ddmFormFieldNamesToBeRestored,
				ddmFormFieldValue.getNestedDDMFormFieldValues(),
				storedDDMFormValues);
		}
	}

	protected void restorePasswordDDMFormFieldValues(
			long dataProviderInstanceId, DDMFormValues ddmFormValues)
		throws PortalException {

		DDMDataProviderInstance dataProviderInstance =
			ddmDataProviderInstanceService.getDataProviderInstance(
				dataProviderInstanceId);

		DDMForm dataProviderInstanceSettingsDDMForm =
			getDataProviderInstanceSettingsDDMForm(dataProviderInstance);

		Set<String> passwordDDMFormFieldNames =
			DDMDataProviderPortletUtil.getDDMFormFieldNamesByType(
				dataProviderInstanceSettingsDDMForm, "password");

		DDMFormValues storedDDMFormValues = getStoredDDMFormValues(
			dataProviderInstanceSettingsDDMForm,
			dataProviderInstance.getDefinition());

		restoreDDMFormFieldValues(
			passwordDDMFormFieldNames, ddmFormValues.getDDMFormFieldValues(),
			storedDDMFormValues);
	}

	@Reference
	protected DDMFormValuesDeserializerTracker ddmFormValuesDeserializerTracker;

}