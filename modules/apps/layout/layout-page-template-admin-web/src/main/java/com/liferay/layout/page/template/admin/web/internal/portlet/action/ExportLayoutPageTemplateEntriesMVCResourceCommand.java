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

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.portlet.util.ExportHelper;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template/export_layout_page_template_entry"
	},
	service = MVCResourceCommand.class
)
public class ExportLayoutPageTemplateEntriesMVCResourceCommand
	implements MVCResourceCommand {

	public File getFile(ResourceRequest resourceRequest)
		throws PortletException {

		long[] exportLayoutPageTemplateEntryIds = null;

		long layoutPageTemplateEntryEntryId = ParamUtil.getLong(
			resourceRequest, "layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryEntryId > 0) {
			exportLayoutPageTemplateEntryIds = new long[] {
				layoutPageTemplateEntryEntryId
			};
		}
		else {
			exportLayoutPageTemplateEntryIds = ParamUtil.getLongValues(
				resourceRequest, "rowIds");
		}

		try {
			List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
				new ArrayList<>();

			for (long exportLayoutPageTemplateEntryId :
					exportLayoutPageTemplateEntryIds) {

				LayoutPageTemplateEntry layoutPageTemplateEntry =
					_layoutPageTemplateEntryLocalService.
						fetchLayoutPageTemplateEntry(
							exportLayoutPageTemplateEntryId);

				layoutPageTemplateEntries.add(layoutPageTemplateEntry);
			}

			return _exportUtil.exportPageTemplateDefinitions(
				layoutPageTemplateEntries);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse,
				"page-templates-" + Time.getTimestamp() + ".zip",
				new FileInputStream(getFile(resourceRequest)),
				ContentTypes.APPLICATION_ZIP);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return false;
	}

	@Reference
	private ExportHelper _exportUtil;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}