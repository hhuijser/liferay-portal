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

package com.liferay.commerce.order.rule.internal.entry.type;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.entry.type.COREntryType;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.order.rule.entry.type.key=" + COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT,
		"commerce.order.rule.entry.type.order:Integer=1"
	},
	service = COREntryType.class
)
public class MinimumAmountCOREntryTypeImpl implements COREntryType {

	@Override
	public boolean evaluate(COREntry corEntry, CommerceOrder commerceOrder)
		throws PortalException {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				corEntry.getTypeSettings()
			).build();

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				commerceOrder.getCompanyId(),
				typeSettingsUnicodeProperties.getProperty(
					COREntryConstants.
						TYPE_MINIMUM_ORDER_AMOUNT_FIELD_CURRENCY_CODE));

		CommerceCurrency orderCommerceCurrency =
			commerceOrder.getCommerceCurrency();

		BigDecimal minimumAmount = BigDecimal.valueOf(
			GetterUtil.getDouble(
				typeSettingsUnicodeProperties.getProperty(
					COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_AMOUNT)));
		BigDecimal orderTotal = commerceOrder.getTotal();

		if (!Objects.equals(
				commerceCurrency.getCode(), orderCommerceCurrency.getCode())) {

			minimumAmount = minimumAmount.multiply(commerceCurrency.getRate());
			orderTotal = orderTotal.multiply(orderCommerceCurrency.getRate());
		}

		if (minimumAmount.compareTo(orderTotal) > 0) {
			return false;
		}

		return true;
	}

	@Override
	public String getErrorMessage(
			COREntry corEntry, CommerceOrder commerceOrder, Locale locale)
		throws PortalException {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				corEntry.getTypeSettings()
			).build();

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.getCommerceCurrency(
				corEntry.getCompanyId(),
				typeSettingsUnicodeProperties.getProperty(
					COREntryConstants.
						TYPE_MINIMUM_ORDER_AMOUNT_FIELD_CURRENCY_CODE));

		CommerceCurrency orderCommerceCurrency =
			commerceOrder.getCommerceCurrency();

		BigDecimal minimumAmount = BigDecimal.valueOf(
			GetterUtil.getDouble(
				typeSettingsUnicodeProperties.getProperty(
					COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT_FIELD_AMOUNT)));

		BigDecimal baseCurrencyAmount = minimumAmount.multiply(
			commerceCurrency.getRate());

		BigDecimal orderCurrencyAmount = baseCurrencyAmount.divide(
			orderCommerceCurrency.getRate());

		String[] arguments = {
			_commercePriceFormatter.format(
				orderCommerceCurrency, orderCurrencyAmount, locale),
			_commercePriceFormatter.format(
				orderCommerceCurrency,
				orderCurrencyAmount.subtract(commerceOrder.getTotal()), locale)
		};

		return LanguageUtil.format(
			locale,
			"the-minimum-order-amount-is-x.-this-order-needs-an-additional-x-" +
				"to-meet-the-requirement",
			arguments);
	}

	@Override
	public String getKey() {
		return COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "minimum-order-amount");
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

}