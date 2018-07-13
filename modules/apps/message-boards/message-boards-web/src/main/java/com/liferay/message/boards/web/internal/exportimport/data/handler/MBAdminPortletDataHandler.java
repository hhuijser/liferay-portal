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

package com.liferay.message.boards.web.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.model.MBThreadFlag;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.message.boards.service.MBThreadFlagLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Daniel Kocsis
 */
@Component(
	property = "javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS_ADMIN,
	service = PortletDataHandler.class
)
public class MBAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "message_boards";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getServiceName() {
		return MBConstants.SERVICE_NAME;
	}

	@Activate
	protected void activate() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(MBBan.class),
			new StagedModelType(MBCategory.class),
			new StagedModelType(MBMessage.class),
			new StagedModelType(MBThread.class),
			new StagedModelType(MBThreadFlag.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "categories", true, false, null,
				MBCategory.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "messages", true, false, null,
				MBMessage.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL),
			new PortletDataHandlerBoolean(
				NAMESPACE, "thread-flags", true, false, null,
				MBThreadFlag.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "user-bans", true, false, null,
				MBBan.class.getName()));
		setPublishToLiveByDefault(
			PropsValues.MESSAGE_BOARDS_PUBLISH_TO_LIVE_BY_DEFAULT);
		setStagingControls(getExportControls());
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				MBPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		mbBanLocalService.deleteBansByGroupId(
			portletDataContext.getScopeGroupId());

		mbCategoryLocalService.deleteCategories(
			portletDataContext.getScopeGroupId());

		mbStatsUserLocalService.deleteStatsUsersByGroupId(
			portletDataContext.getScopeGroupId());

		mbThreadLocalService.deleteThreads(
			portletDataContext.getScopeGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(MBConstants.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "categories") ||
			portletDataContext.getBooleanParameter(NAMESPACE, "messages")) {

			ActionableDynamicQuery categoryActionableDynamicQuery =
				mbCategoryLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			categoryActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "messages")) {
			ActionableDynamicQuery messageActionableDynamicQuery =
				getMessageActionableDynamicQuery(portletDataContext);

			messageActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "thread-flags")) {
			ActionableDynamicQuery threadFlagActionableDynamicQuery =
				mbThreadFlagLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			threadFlagActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			ActionableDynamicQuery banActionableDynamicQuery =
				mbBanLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			banActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(MBConstants.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "categories") ||
			portletDataContext.getBooleanParameter(NAMESPACE, "messages")) {

			Element categoriesElement =
				portletDataContext.getImportDataGroupElement(MBCategory.class);

			List<Element> categoryElements = categoriesElement.elements();

			for (Element categoryElement : categoryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, categoryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "messages")) {
			Element messagesElement =
				portletDataContext.getImportDataGroupElement(MBMessage.class);

			List<Element> messageElements = messagesElement.elements();

			for (Element messageElement : messageElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, messageElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "thread-flags")) {
			Element threadFlagsElement =
				portletDataContext.getImportDataGroupElement(
					MBThreadFlag.class);

			List<Element> threadFlagElements = threadFlagsElement.elements();

			for (Element threadFlagElement : threadFlagElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, threadFlagElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "user-bans")) {
			Element userBansElement =
				portletDataContext.getImportDataGroupElement(MBBan.class);

			List<Element> userBanElements = userBansElement.elements();

			for (Element userBanElement : userBanElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, userBanElement);
			}
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		if (ExportImportDateUtil.isRangeFromLastPublishDate(
				portletDataContext)) {

			staging.populateLastPublishDateCounts(
				portletDataContext,
				new StagedModelType[] {
					new StagedModelType(MBBan.class.getName()),
					new StagedModelType(MBCategory.class.getName()),
					new StagedModelType(MBMessage.class.getName()),
					new StagedModelType(MBThread.class.getName()),
					new StagedModelType(MBThreadFlag.class.getName())
				});

			return;
		}

		ActionableDynamicQuery banActionableDynamicQuery =
			mbBanLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		banActionableDynamicQuery.performCount();

		ActionableDynamicQuery categoryActionableDynamicQuery =
			mbCategoryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		categoryActionableDynamicQuery.performCount();

		ActionableDynamicQuery messageActionableDynamicQuery =
			getMessageActionableDynamicQuery(portletDataContext);

		messageActionableDynamicQuery.performCount();

		ActionableDynamicQuery threadActionableDynamicQuery =
			mbThreadLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		threadActionableDynamicQuery.performCount();

		ActionableDynamicQuery threadFlagActionableDynamicQuery =
			mbThreadFlagLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		threadFlagActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getMessageActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery actionableDynamicQuery =
			mbMessageLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Criterion modifiedDateCriterion =
						portletDataContext.getDateRangeCriteria("modifiedDate");
					Criterion statusDateCriterion =
						portletDataContext.getDateRangeCriteria("statusDate");

					if ((modifiedDateCriterion != null) &&
						(statusDateCriterion != null)) {

						Disjunction disjunction =
							RestrictionsFactoryUtil.disjunction();

						disjunction.add(modifiedDateCriterion);
						disjunction.add(statusDateCriterion);

						dynamicQuery.add(disjunction);
					}

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(classNameIdProperty.eq(0L));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					if (portletDataContext.isInitialPublication()) {
						dynamicQuery.add(
							statusProperty.ne(
								WorkflowConstants.STATUS_IN_TRASH));
					}
					else {
						StagedModelDataHandler<?> stagedModelDataHandler =
							StagedModelDataHandlerRegistryUtil.
								getStagedModelDataHandler(
									MBMessage.class.getName());

						dynamicQuery.add(
							statusProperty.in(
								stagedModelDataHandler.
									getExportableStatuses()));
					}
				}

			});

		return actionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setMBBanLocalService(MBBanLocalService mbBanLocalService) {
		this.mbBanLocalService = mbBanLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBCategoryLocalService(
		MBCategoryLocalService mbCategoryLocalService) {

		this.mbCategoryLocalService = mbCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {

		this.mbMessageLocalService = mbMessageLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBStatsUserLocalService(
		MBStatsUserLocalService mbStatsUserLocalService) {

		this.mbStatsUserLocalService = mbStatsUserLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadFlagLocalService(
		MBThreadFlagLocalService mbThreadFlagLocalService) {

		this.mbThreadFlagLocalService = mbThreadFlagLocalService;
	}

	@Reference(unbind = "-")
	protected void setMBThreadLocalService(
		MBThreadLocalService mbThreadLocalService) {

		this.mbThreadLocalService = mbThreadLocalService;
	}

	protected MBBanLocalService mbBanLocalService;
	protected MBCategoryLocalService mbCategoryLocalService;
	protected MBMessageLocalService mbMessageLocalService;
	protected MBStatsUserLocalService mbStatsUserLocalService;
	protected MBThreadFlagLocalService mbThreadFlagLocalService;
	protected MBThreadLocalService mbThreadLocalService;

	@Reference
	protected Staging staging;

}