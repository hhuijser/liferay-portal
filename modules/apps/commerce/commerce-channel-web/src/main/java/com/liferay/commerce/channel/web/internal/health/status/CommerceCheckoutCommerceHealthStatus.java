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

package com.liferay.commerce.channel.web.internal.health.status;

import com.liferay.commerce.constants.CommerceHealthStatusConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.product.channel.CommerceChannelHealthStatus;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ResourcePermissionService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.channel.health.status.display.order:Integer=30",
		"commerce.channel.health.status.key=" + CommerceHealthStatusConstants.COMMERCE_CHECKOUT_COMMERCE_HEALTH_STATUS_KEY
	},
	service = CommerceChannelHealthStatus.class
)
public class CommerceCheckoutCommerceHealthStatus
	implements CommerceChannelHealthStatus {

	@Override
	public void fixIssue(long companyId, long commerceChannelId)
		throws PortalException {

		if (isFixed(companyId, commerceChannelId)) {
			return;
		}

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		String name = "Checkout";

		String friendlyURL =
			StringPool.FORWARD_SLASH + StringUtil.toLowerCase(name);

		boolean privateLayout = true;

		List<Layout> layouts = _layoutService.getLayouts(
			commerceChannel.getSiteGroupId(), true);

		if (layouts.isEmpty()) {
			privateLayout = false;
		}

		Layout layout = _layoutService.addLayout(
			commerceChannel.getSiteGroupId(), privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, name, null,
			LayoutConstants.TYPE_PORTLET, true, friendlyURL,
			new ServiceContext());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.addPortletId(
			PrincipalThreadLocal.getUserId(),
			CommercePortletKeys.COMMERCE_CHECKOUT);

		_layoutService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		Role role = _roleLocalService.fetchRole(
			layout.getCompanyId(), RoleConstants.GUEST);

		if (role != null) {
			_resourcePermissionService.setIndividualResourcePermissions(
				layout.getGroupId(), layout.getCompanyId(),
				layout.getModelClassName(), String.valueOf(layout.getPlid()),
				role.getRoleId(), new String[0]);
		}
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			locale,
			CommerceHealthStatusConstants.
				COMMERCE_CHECKOUT_COMMERCE_HEALTH_STATUS_DESCRIPTION);
	}

	@Override
	public String getKey() {
		return CommerceHealthStatusConstants.
			COMMERCE_CHECKOUT_COMMERCE_HEALTH_STATUS_KEY;
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			locale,
			CommerceHealthStatusConstants.
				COMMERCE_CHECKOUT_COMMERCE_HEALTH_STATUS_KEY);
	}

	@Override
	public boolean isFixed(long companyId, long commerceChannelId)
		throws PortalException {

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(commerceChannelId);

		String commerceChannelType = commerceChannel.getType();

		if (!commerceChannelType.equals(
				CommerceChannelConstants.CHANNEL_TYPE_SITE)) {

			return true;
		}

		long plid = _portal.getPlidFromPortletId(
			commerceChannel.getSiteGroupId(),
			CommercePortletKeys.COMMERCE_CHECKOUT);

		if (plid > 0) {
			return true;
		}

		return false;
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionService _resourcePermissionService;

	@Reference
	private RoleLocalService _roleLocalService;

}