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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class BasicFragmentManagementToolbarDisplayContext
	extends FragmentManagementToolbarDisplayContext {

	public BasicFragmentManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		FragmentDisplayContext fragmentDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			fragmentDisplayContext.getFragmentEntriesSearchContainer(),
			fragmentDisplayContext);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean hasManageFragmentEntriesPermission =
			FragmentPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "exportFragmentCompositionsAndFragmentEntries");
				dropdownItem.setIcon("import-export");
				dropdownItem.setLabel(LanguageUtil.get(request, "export"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> hasManageFragmentEntriesPermission,
			dropdownItem -> {
				dropdownItem.putData(
					"action", "moveFragmentCompositionsAndFragmentEntries");
				dropdownItem.setIcon("move-folder");
				dropdownItem.setLabel(LanguageUtil.get(request, "move"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> hasManageFragmentEntriesPermission,
			dropdownItem -> {
				dropdownItem.putData("action", "copySelectedFragmentEntries");
				dropdownItem.setIcon("paste");
				dropdownItem.setLabel(LanguageUtil.get(request, "make-a-copy"));
				dropdownItem.setQuickAction(true);
			}
		).add(
			() -> hasManageFragmentEntriesPermission,
			dropdownItem -> {
				dropdownItem.putData(
					"action", "deleteFragmentCompositionsAndFragmentEntries");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(LanguageUtil.get(request, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public Map<String, Object> getComponentContext() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return HashMapBuilder.<String, Object>put(
			"copyFragmentEntryURL",
			() -> {
				PortletURL copyFragmentEntryURL =
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/fragment/copy_fragment_entry"
					).setRedirect(
						themeDisplay.getURLCurrent()
					).build();

				return copyFragmentEntryURL.toString();
			}
		).put(
			"deleteFragmentCompositionsAndFragmentEntriesURL",
			() -> {
				PortletURL deleteFragmentCompositionsAndFragmentEntriesURL =
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/fragment/delete_fragment_compositions_and_fragment_" +
							"entries"
					).setRedirect(
						themeDisplay.getURLCurrent()
					).build();

				return deleteFragmentCompositionsAndFragmentEntriesURL.
					toString();
			}
		).put(
			"exportFragmentCompositionsAndFragmentEntriesURL",
			() -> {
				ResourceURL exportFragmentCompositionsAndFragmentEntriesURL =
					liferayPortletResponse.createResourceURL();

				exportFragmentCompositionsAndFragmentEntriesURL.setResourceID(
					"/fragment/export_fragment_compositions_and_fragment_" +
						"entries");

				return exportFragmentCompositionsAndFragmentEntriesURL.
					toString();
			}
		).put(
			"fragmentCollectionId",
			ParamUtil.getLong(liferayPortletRequest, "fragmentCollectionId")
		).put(
			"moveFragmentCompositionsAndFragmentEntriesURL",
			() -> {
				PortletURL moveFragmentCompositionsAndFragmentEntriesURL =
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/fragment/move_fragment_compositions_and_fragment_" +
							"entries"
					).setRedirect(
						themeDisplay.getURLCurrent()
					).build();

				return moveFragmentCompositionsAndFragmentEntriesURL.toString();
			}
		).put(
			"selectFragmentCollectionURL",
			() -> {
				PortletURL selectFragmentCollectionURL =
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setMVCRenderCommandName(
						"/fragment/select_fragment_collection"
					).setWindowState(
						LiferayWindowState.POP_UP
					).build();

				return selectFragmentCollectionURL.toString();
			}
		).build();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addFragmentEntry");

				PortletURL addFragmentEntryURL =
					PortletURLBuilder.createActionURL(
						liferayPortletResponse
					).setActionName(
						"/fragment/add_fragment_entry"
					).setParameter(
						"fragmentCollectionId",
						fragmentDisplayContext.getFragmentCollectionId()
					).setParameter(
						"type", FragmentConstants.TYPE_COMPONENT
					).build();

				dropdownItem.putData(
					"addFragmentEntryURL", addFragmentEntryURL.toString());

				dropdownItem.putData(
					"title",
					LanguageUtil.format(
						request, "add-x",
						FragmentConstants.TYPE_COMPONENT_LABEL, true));
				dropdownItem.setLabel(LanguageUtil.get(request, "add"));
			}
		).build();
	}

	@Override
	public Boolean isShowCreationMenu() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (FragmentPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

			return true;
		}

		return false;
	}

}