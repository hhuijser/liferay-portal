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

package com.liferay.asset.publisher.web.internal.upgrade;

import com.liferay.asset.publisher.web.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.asset.publisher.web.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AssetPublisherWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.asset.publisher.web", "0.0.0", "1.0.0",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.asset.publisher.web", "0.0.1", "1.0.0",
			new UpgradePortletId(),
			new UpgradePortletPreferences(
				_ddmStructureLocalService, _ddmStructureLinkLocalService,
				_saxReader));

		registry.register(
			"com.liferay.asset.publisher.web", "1.0.0", "1.1.0",
			new com.liferay.asset.publisher.web.upgrade.v1_1_0.
				UpgradePortletPreferences());
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLinkLocalService(
		DDMStructureLinkLocalService ddmStructureLinkLocalService) {

		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setSAXReader(SAXReader saxReader) {
		_saxReader = saxReader;
	}

	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private SAXReader _saxReader;

}