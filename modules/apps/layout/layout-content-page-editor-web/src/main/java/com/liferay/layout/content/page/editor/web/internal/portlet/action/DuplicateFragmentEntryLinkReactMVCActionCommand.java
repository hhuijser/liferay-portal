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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.exception.NoSuchEntryLinkException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletIdException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/duplicate_fragment_entry_link_react"
	},
	service = MVCActionCommand.class
)
public class DuplicateFragmentEntryLinkReactMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _duplicateFragmentEntryLink(
			actionRequest, actionResponse);

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private JSONObject _addDuplicateFragmentEntryLinkToLayoutDataJSONObject(
			ActionRequest actionRequest, FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId",
			SegmentsExperienceConstants.ID_DEFAULT);
		String itemId = ParamUtil.getString(actionRequest, "itemId");

		return LayoutStructureUtil.updateLayoutPageTemplateData(
			themeDisplay.getScopeGroupId(), segmentsExperienceId,
			themeDisplay.getPlid(),
			layoutStructure -> layoutStructure.duplicateLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(), itemId));
	}

	private void _copyPortletPreferences(
			HttpServletRequest httpServletRequest, String portletId,
			String oldInstanceId, String newInstanceId)
		throws PortalException {

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getPortletPreferences(
				httpServletRequest,
				PortletIdCodec.encode(portletId, oldInstanceId));

		PortletPreferencesIds portletPreferencesIds =
			_portletPreferencesFactory.getPortletPreferencesIds(
				httpServletRequest,
				PortletIdCodec.encode(portletId, oldInstanceId));

		_portletPreferencesLocalService.addPortletPreferences(
			portletPreferencesIds.getCompanyId(),
			portletPreferencesIds.getOwnerId(),
			portletPreferencesIds.getOwnerType(),
			portletPreferencesIds.getPlid(),
			PortletIdCodec.encode(portletId, newInstanceId), null,
			PortletPreferencesFactoryUtil.toXML(portletPreferences));
	}

	private JSONObject _duplicateFragmentEntryLink(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		long fragmentEntryLinkId = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.getFragmentEntryLink(
					fragmentEntryLinkId);

			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues());

			String portletId = editableValuesJSONObject.getString("portletId");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			if (Validator.isNotNull(portletId)) {
				Portlet portlet = _portletLocalService.getPortletById(
					portletId);

				if (!portlet.isInstanceable()) {
					throw new PortletIdException();
				}

				String oldInstanceId = editableValuesJSONObject.getString(
					"instanceId");

				String newInstanceId = PortletIdCodec.generateInstanceId();

				editableValuesJSONObject.put("instanceId", newInstanceId);

				_copyPortletPreferences(
					serviceContext.getRequest(), portletId, oldInstanceId,
					newInstanceId);
			}

			FragmentEntryLink duplicateFragmentEntryLink =
				_fragmentEntryLinkService.addFragmentEntryLink(
					fragmentEntryLink.getGroupId(),
					fragmentEntryLink.getOriginalFragmentEntryLinkId(),
					fragmentEntryLink.getFragmentEntryId(),
					fragmentEntryLink.getClassNameId(),
					fragmentEntryLink.getClassPK(), fragmentEntryLink.getCss(),
					fragmentEntryLink.getHtml(), fragmentEntryLink.getJs(),
					fragmentEntryLink.getConfiguration(),
					editableValuesJSONObject.toString(), StringUtil.randomId(),
					0, fragmentEntryLink.getRendererKey(), serviceContext);

			SessionMessages.add(actionRequest, "fragmentEntryLinkDuplicated");

			jsonObject = JSONUtil.put(
				"fragmentEntryLink",
				FragmentEntryLinkUtil.getFragmentEntryLinkJSONObject(
					actionRequest, actionResponse,
					_fragmentEntryConfigurationParser,
					duplicateFragmentEntryLink,
					_fragmentCollectionContributorTracker,
					_fragmentRendererController, _fragmentRendererTracker,
					portletId)
			).put(
				"layoutData",
				_addDuplicateFragmentEntryLinkToLayoutDataJSONObject(
					actionRequest, duplicateFragmentEntryLink)
			);
		}
		catch (Exception exception) {
			String errorMessage = "an-unexpected-error-occurred";

			if (exception instanceof NoSuchEntryLinkException) {
				errorMessage =
					"the-section-could-not-be-duplicated-because-it-has-been-" +
						"deleted";
			}
			else if (exception instanceof PortletIdException) {
				errorMessage = "uninstanceable-widget-cannot-be-duplicated";
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			jsonObject.put(
				"error",
				LanguageUtil.get(themeDisplay.getRequest(), errorMessage));
		}

		return jsonObject;
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}