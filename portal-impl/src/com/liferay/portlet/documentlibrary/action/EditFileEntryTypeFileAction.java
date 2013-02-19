/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Dale Shan
 */
public class EditFileEntryTypeFileAction extends PortletAction {

	@Override
	public void processAction(
		ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
		ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.DELETE)) {
			deleteFileEntryFieldFile(actionRequest);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	public ActionForward render(
		ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
		RenderRequest renderRequest, RenderResponse renderResponse)
			throws Exception {

		return mapping.findForward("portlet.document_library.view");
	}

	protected void deleteFileEntryFieldFile(PortletRequest portletRequest)
			throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long classPK = ParamUtil.getLong(portletRequest, "classPK");

		String fieldName = ParamUtil.getString(portletRequest, "fieldName");

		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.getDLFileEntryMetadata(classPK);

		long storageId = dlFileEntryMetadata.getDDMStorageId();

		Fields fields = StorageEngineUtil.getFields(storageId);

		if (fields.contains(fieldName)) {
			fields.remove(fieldName);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		StorageEngineUtil.update(storageId, fields, false, serviceContext);

		String dirName = DDMUtil.getFileUploadPath(dlFileEntryMetadata);

		String fileName = dirName + StringPool.SLASH + fieldName;

		DLStoreUtil.deleteFile(
			themeDisplay.getCompanyId(), CompanyConstants.SYSTEM, fileName);
	}

}