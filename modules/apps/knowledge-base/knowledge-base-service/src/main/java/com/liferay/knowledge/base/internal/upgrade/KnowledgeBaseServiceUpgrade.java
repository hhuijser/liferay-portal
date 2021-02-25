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

package com.liferay.knowledge.base.internal.upgrade;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.knowledge.base.internal.upgrade.v2_0_2.KBArticleUpgradeProcess;
import com.liferay.knowledge.base.internal.upgrade.v3_0_0.util.KBArticleTable;
import com.liferay.knowledge.base.internal.upgrade.v3_0_0.util.KBCommentTable;
import com.liferay.knowledge.base.internal.upgrade.v3_0_0.util.KBFolderTable;
import com.liferay.knowledge.base.internal.upgrade.v3_0_0.util.KBTemplateTable;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.kernel.upgrade.UpgradeViewCount;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;
import com.liferay.view.count.service.ViewCountEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = UpgradeStepRegistrator.class)
public class KnowledgeBaseServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		try {
			BaseUpgradeServiceModuleRelease baseUpgradeServiceModuleRelease =
				new BaseUpgradeServiceModuleRelease() {

					@Override
					protected String getNamespace() {
						return "KB";
					}

					@Override
					protected String getNewBundleSymbolicName() {
						return "com.liferay.knowledge.base.service";
					}

					@Override
					protected String getOldBundleSymbolicName() {
						return "knowledge-base-portlet";
					}

				};

			baseUpgradeServiceModuleRelease.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw new RuntimeException(upgradeException);
		}

		registry.register(
			"0.0.1", "1.0.0",
			new com.liferay.knowledge.base.internal.upgrade.v1_0_0.
				RatingsEntryUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_0_0.
				RatingsStatsUpgradeProcess());

		registry.register(
			"1.0.0", "1.1.0",
			new com.liferay.knowledge.base.internal.upgrade.v1_1_0.
				ClassNameUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_1_0.
				ExpandoTableUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_1_0.
				KBArticleUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_1_0.
				KBCommentUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_1_0.
				KBTemplateUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_1_0.
				ResourceActionUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_1_0.
				ResourcePermissionUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_1_0.
				UpgradePortletPreferences());

		registry.register(
			"1.1.0", "1.2.0",
			new com.liferay.knowledge.base.internal.upgrade.v1_2_0.
				KBArticleUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_2_0.
				KBStructureUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_2_0.
				KBTemplateUpgradeProcess());

		registry.register(
			"1.2.0", "1.3.0",
			new com.liferay.knowledge.base.internal.upgrade.v1_3_0.
				KBAttachmentsUpgradeProcess(_store),
			new com.liferay.knowledge.base.internal.upgrade.v1_3_0.
				UpgradePortletPreferences());

		registry.register(
			"1.3.0", "1.3.1",
			new com.liferay.knowledge.base.internal.upgrade.v1_3_1.
				KBArticleUpgradeProcess());

		registry.register(
			"1.3.1", "1.3.2",
			new com.liferay.knowledge.base.internal.upgrade.v1_3_2.
				KBArticleUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_3_2.
				KBFolderUpgradeProcess());

		registry.register(
			"1.3.2", "1.3.3",
			new com.liferay.knowledge.base.internal.upgrade.v1_3_3.
				KBFolderUpgradeProcess());

		registry.register(
			"1.3.3", "1.3.4",
			new com.liferay.knowledge.base.internal.upgrade.v1_3_4.
				KBArticleUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_3_4.
				KBCommentUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_3_4.
				ResourceActionUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v1_3_4.
				UpgradePortletPreferences());

		registry.register(
			"1.3.4", "1.3.5",
			new com.liferay.knowledge.base.internal.upgrade.v1_3_5.
				UpgradeLastPublishDate());

		registry.register(
			"1.3.5", "2.0.0",
			new com.liferay.knowledge.base.internal.upgrade.v2_0_0.
				UpgradeClassNames(),
			new com.liferay.knowledge.base.internal.upgrade.v2_0_0.
				KBCommentUpgradeProcess(),
			new com.liferay.knowledge.base.internal.upgrade.v2_0_0.
				UpgradeRepository());

		registry.register(
			"2.0.0", "2.0.1",
			new com.liferay.knowledge.base.internal.upgrade.v2_0_1.
				UpgradePortletSettings(_settingsFactory));

		registry.register("2.0.1", "2.0.2", new KBArticleUpgradeProcess());

		registry.register(
			"2.0.2", "3.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {
					KBArticleTable.class, KBCommentTable.class,
					KBFolderTable.class, KBTemplateTable.class
				}));

		registry.register(
			"3.0.0", "3.1.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"KBArticle", "KBComment", "KBFolder", "KBTemplate"
					};
				}

			});

		registry.register(
			"3.1.0", "4.0.0",
			new UpgradeViewCount(
				"KBArticle", KBArticle.class, "kbArticleId", "viewCount"));
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private SettingsFactory _settingsFactory;

	@Reference(target = "(dl.store.upgrade=true)")
	private Store _store;

	/**
	 * See LPS-101085. The ViewCount table needs to exist.
	 */
	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

}