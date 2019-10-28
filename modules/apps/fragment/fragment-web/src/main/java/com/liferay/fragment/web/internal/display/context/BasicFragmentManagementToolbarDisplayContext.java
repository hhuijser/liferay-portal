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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class BasicFragmentManagementToolbarDisplayContext
	extends FragmentManagementToolbarDisplayContext {

	public BasicFragmentManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		FragmentDisplayContext fragmentDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			fragmentDisplayContext.getFragmentEntriesSearchContainer(),
			fragmentDisplayContext);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "exportSelectedFragmentEntries");
						dropdownItem.setIcon("import-export");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "export"));
						dropdownItem.setQuickAction(true);
					});

				if (FragmentPermission.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getScopeGroupId(),
						FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

					add(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "moveSelectedFragmentEntries");
							dropdownItem.setIcon("move-folder");
							dropdownItem.setLabel(
								LanguageUtil.get(request, "move"));
							dropdownItem.setQuickAction(true);
						});
					add(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "copySelectedFragmentEntries");
							dropdownItem.setIcon("paste");
							dropdownItem.setLabel(
								LanguageUtil.get(request, "make-a-copy"));
							dropdownItem.setQuickAction(true);
						});
					add(
						dropdownItem -> {
							dropdownItem.putData(
								"action", "deleteSelectedFragmentEntries");
							dropdownItem.setIcon("times-circle");
							dropdownItem.setLabel(
								LanguageUtil.get(request, "delete"));
							dropdownItem.setQuickAction(true);
						});
				}
			}
		};
	}

	@Override
	public Map<String, Object> getComponentContext() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL copyFragmentEntryURL =
			liferayPortletResponse.createActionURL();

		copyFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/copy_fragment_entry");
		copyFragmentEntryURL.setParameter(
			"redirect", themeDisplay.getURLCurrent());

		Map<String, Object> componentContext =
			HashMapBuilder.<String, Object>put(
				"copyFragmentEntryURL", copyFragmentEntryURL.toString()
			).build();

		PortletURL deleteFragmentEntriesURL =
			liferayPortletResponse.createActionURL();

		deleteFragmentEntriesURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/delete_fragment_entries");
		deleteFragmentEntriesURL.setParameter(
			"redirect", themeDisplay.getURLCurrent());

		componentContext.put(
			"deleteFragmentEntriesURL", deleteFragmentEntriesURL.toString());

		ResourceURL exportFragmentEntriesURL =
			liferayPortletResponse.createResourceURL();

		exportFragmentEntriesURL.setResourceID(
			"/fragment/export_fragment_entries");

		componentContext.put(
			"exportFragmentEntriesURL", exportFragmentEntriesURL.toString());

		long fragmentCollectionId = ParamUtil.getLong(
			liferayPortletRequest, "fragmentCollectionId");

		componentContext.put("fragmentCollectionId", fragmentCollectionId);

		PortletURL moveFragmentEntryURL =
			liferayPortletResponse.createActionURL();

		moveFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/move_fragment_entry");
		moveFragmentEntryURL.setParameter(
			"redirect", themeDisplay.getURLCurrent());

		componentContext.put(
			"moveFragmentEntryURL", moveFragmentEntryURL.toString());

		PortletURL selectFragmentCollectionURL =
			liferayPortletResponse.createActionURL();

		selectFragmentCollectionURL.setParameter(
			"mvcRenderCommandName", "/fragment/select_fragment_collection");
		selectFragmentCollectionURL.setWindowState(LiferayWindowState.POP_UP);

		componentContext.put(
			"selectFragmentCollectionURL",
			selectFragmentCollectionURL.toString());

		return componentContext;
	}

	@Override
	public CreationMenu getCreationMenu() {
		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					_getAddFragmentEntryDropdownItem(
						FragmentConstants.TYPE_SECTION,
						FragmentConstants.TYPE_SECTION_LABEL));

				addPrimaryDropdownItem(
					_getAddFragmentEntryDropdownItem(
						FragmentConstants.TYPE_COMPONENT,
						FragmentConstants.TYPE_COMPONENT_LABEL));
			}
		};
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

	private UnsafeConsumer<DropdownItem, Exception>
		_getAddFragmentEntryDropdownItem(int type, String label) {

		return dropdownItem -> {
			dropdownItem.putData("action", "addFragmentEntry");
			dropdownItem.putData(
				"addFragmentEntryURL", _getAddFragmentEntryURL(type));
			dropdownItem.putData(
				"title", LanguageUtil.format(request, "add-x", label, true));
			dropdownItem.setHref("#");
			dropdownItem.setLabel(LanguageUtil.get(request, label));
		};
	}

	private String _getAddFragmentEntryURL(int type) {
		PortletURL addFragmentEntryURL =
			liferayPortletResponse.createActionURL();

		addFragmentEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/fragment/add_fragment_entry");
		addFragmentEntryURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentDisplayContext.getFragmentCollectionId()));
		addFragmentEntryURL.setParameter("type", String.valueOf(type));

		return addFragmentEntryURL.toString();
	}

}