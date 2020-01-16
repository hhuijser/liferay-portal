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

package com.liferay.layout.internal.workflow;

import com.liferay.layout.internal.configuration.LayoutWorkflowHandlerConfiguration;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	configurationPid = "com.liferay.layout.internal.configuration.LayoutWorkflowHandlerConfiguration",
	property = "model.class.name=com.liferay.portal.kernel.model.Layout",
	service = WorkflowHandler.class
)
public class LayoutWorkflowHandler extends BaseWorkflowHandler<Layout> {

	@Override
	public String getClassName() {
		return Layout.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "content-page");
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(classPK);

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT) ||
			layout.isHidden() || layout.isSystem()) {

			return null;
		}

		return super.getWorkflowDefinitionLink(companyId, groupId, classPK);
	}

	@Override
	public boolean isVisible() {
		return _layoutConverterConfiguration.enabled();
	}

	@Override
	public Layout updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));

		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		if (status != WorkflowConstants.STATUS_APPROVED) {
			return _layoutLocalService.updateStatus(
				userId, classPK, status, serviceContext);
		}

		Layout layout = _layoutLocalService.getLayout(classPK);

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		try {
			_layoutCopyHelper.copyLayout(draftLayout, layout);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		UnicodeProperties typeSettingsProperties =
			draftLayout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty("published", "true");

		_layoutLocalService.updateLayout(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(), typeSettingsProperties.toString());

		draftLayout.setStatus(WorkflowConstants.STATUS_APPROVED);

		_layoutLocalService.updateLayout(draftLayout);

		return _layoutLocalService.updateStatus(
			userId, classPK, status, serviceContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutConverterConfiguration = ConfigurableUtil.createConfigurable(
			LayoutWorkflowHandlerConfiguration.class, properties);
	}

	private volatile LayoutWorkflowHandlerConfiguration
		_layoutConverterConfiguration;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	private final ResourceBundleLoader _resourceBundleLoader =
		ResourceBundleLoaderUtil.getResourceBundleLoaderByBundleSymbolicName(
			"com.liferay.layout.impl");

}