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

package com.liferay.commerce.avalara.tax.engine.fixed.web.internal.portlet.action;

import com.liferay.commerce.avalara.tax.engine.fixed.internal.configuration.CommerceTaxAvalaraTypeConfiguration;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Calvin Keum
 */
@Component(
	enabled = true, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_TAX_METHODS,
		"mvc.command.name=/commerce_tax_methods/edit_commerce_tax_avalara"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTaxAvalaraMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.UPDATE)) {
			_updateCommerceTaxAvalara(actionRequest);
		}
	}

	private void _updateCommerceTaxAvalara(ActionRequest actionRequest)
		throws Exception {

		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");

		CommerceTaxMethod commerceTaxMethod =
			_commerceTaxMethodService.getCommerceTaxMethod(commerceTaxMethodId);

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				commerceTaxMethod.getGroupId(),
				CommerceTaxAvalaraTypeConfiguration.class.getName()));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String accountNumber = ParamUtil.getString(
			actionRequest, "accountNumber");

		modifiableSettings.setValue("accountNumber", accountNumber);

		String companyCode = ParamUtil.getString(actionRequest, "companyCode");

		modifiableSettings.setValue("companyCode", companyCode);

		Boolean disabledDocumentRecording = ParamUtil.getBoolean(
			actionRequest, "disabledDocumentRecording");

		modifiableSettings.setValue(
			"disabledDocumentRecording",
			String.valueOf(disabledDocumentRecording));

		String licenseKey = ParamUtil.getString(actionRequest, "licenseKey");

		modifiableSettings.setValue("licenseKey", licenseKey);

		String serviceURL = ParamUtil.getString(actionRequest, "serviceURL");

		modifiableSettings.setValue("serviceURL", serviceURL);

		modifiableSettings.store();
	}

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

	@Reference
	private Portal _portal;

	@Reference
	private SettingsFactory _settingsFactory;

}