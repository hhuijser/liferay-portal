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

package com.liferay.sync.internal.upgrade;

import com.liferay.document.library.sync.service.DLSyncEventLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.sync.internal.upgrade.v1_0_6.UpgradeSyncDLObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class SyncServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new DummyUpgradeStep());

		registry.register("1.0.0", "1.0.1", new DummyUpgradeStep());

		registry.register(
			"1.0.1", "1.0.2",
			new com.liferay.sync.internal.upgrade.v1_0_2.UpgradeSchema());

		registry.register(
			"1.0.2", "1.0.3",
			new com.liferay.sync.internal.upgrade.v1_0_3.UpgradeSchema());

		registry.register(
			"1.0.3", "1.0.4",
			new com.liferay.sync.internal.upgrade.v1_0_4.UpgradeSchema());

		// SYNC-1807 The following upgrade steps populate the SyncDLObject table
		// for first time installations but not for upgrades.

		registry.register("1.0.4", "1.0.6", new DummyUpgradeStep());

		registry.register(
			"1.0.5", "1.0.6",
			new UpgradeSyncDLObject(
				_dlSyncEventLocalService, _groupLocalService));
	}

	@Reference(unbind = "-")
	protected void setDLSyncEventLocalService(
		DLSyncEventLocalService dlSyncEventLocalService) {

		_dlSyncEventLocalService = dlSyncEventLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private DLSyncEventLocalService _dlSyncEventLocalService;
	private GroupLocalService _groupLocalService;

}