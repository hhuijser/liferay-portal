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

package com.liferay.wiki.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.web.internal.display.context.util.WikiURLHelper;
import com.liferay.wiki.web.internal.portlet.toolbar.item.WikiPortletToolbarContributor;
import com.liferay.wiki.web.internal.security.permission.resource.WikiPagePermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class WikiPagesManagementToolbarDisplayContext {

	public WikiPagesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, String displayStyle,
		SearchContainer<WikiPage> searchContainer, TrashHelper trashHelper,
		WikiURLHelper wikiURLHelper) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_displayStyle = displayStyle;
		_searchContainer = searchContainer;
		_trashHelper = trashHelper;
		_wikiURLHelper = wikiURLHelper;

		_currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		_httpServletRequest = liferayPortletRequest.getHttpServletRequest();

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deletePages");

				if (_trashHelper.isTrashEnabled(
						_themeDisplay.getScopeGroupId())) {

					dropdownItem.setIcon("trash");
					dropdownItem.setLabel(
						LanguageUtil.get(
							_httpServletRequest, "move-to-recycle-bin"));
				}
				else {
					dropdownItem.setIcon("times-circle");
					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "delete"));
				}

				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public List<String> getAvailableActions(WikiPage wikiPage)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (WikiPagePermission.contains(
				_themeDisplay.getPermissionChecker(), wikiPage,
				ActionKeys.DELETE)) {

			availableActions.add("deletePages");
		}

		return availableActions;
	}

	public PortletURL getClearResultsURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/wiki/view_pages"
		).setRedirect(
			_currentURLObj.toString()
		).setParameter(
			"nodeId",
			() -> {
				WikiNode node = (WikiNode)_httpServletRequest.getAttribute(
					WikiWebKeys.WIKI_NODE);

				return node.getNodeId();
			}
		).build();

		return portletURL;
	}

	public CreationMenu getCreationMenu() {
		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			return null;
		}

		WikiPortletToolbarContributor wikiPortletToolbarContributor =
			(WikiPortletToolbarContributor)_httpServletRequest.getAttribute(
				WikiWebKeys.WIKI_PORTLET_TOOLBAR_CONTRIBUTOR);

		List<Menu> menus = wikiPortletToolbarContributor.getPortletTitleMenus(
			_liferayPortletRequest, _liferayPortletResponse);

		if (menus.isEmpty()) {
			return null;
		}

		return new CreationMenu() {
			{
				for (Menu menu : menus) {
					List<URLMenuItem> urlMenuItems =
						(List<URLMenuItem>)(List<?>)menu.getMenuItems();

					for (URLMenuItem urlMenuItem : urlMenuItems) {
						addDropdownItem(
							dropdownItem -> {
								dropdownItem.setHref(urlMenuItem.getURL());
								dropdownItem.setLabel(urlMenuItem.getLabel());
							});
					}
				}
			}
		};
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			() -> Validator.isNull(
				ParamUtil.getString(_httpServletRequest, "keywords")),
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public List<LabelItem> getFilterLabelItems() {
		String navigation = _getNavigation();

		return LabelItemListBuilder.add(
			() -> !navigation.equals("all-pages"),
			labelItem -> {
				PortletURL removeLabelURL = PortletURLBuilder.create(
					PortletURLUtil.clone(
						_getPortletURL(), _liferayPortletResponse)
				).setParameter(
					"navigation", (String)null
				).build();

				labelItem.putData("removeLabelURL", removeLabelURL.toString());

				labelItem.setCloseable(true);

				labelItem.setLabel(
					LanguageUtil.get(_httpServletRequest, navigation));
			}
		).build();
	}

	public PortletURL getSearchActionURL() {
		PortletURL searchActionURL = PortletURLBuilder.create(
			_wikiURLHelper.getSearchURL()
		).setRedirect(
			_currentURLObj.toString()
		).setParameter(
			"nodeId",
			() -> {
				WikiNode node = (WikiNode)_httpServletRequest.getAttribute(
					WikiWebKeys.WIKI_NODE);

				return node.getNodeId();
			}
		).build();

		return searchActionURL;
	}

	public String getSortingOrder() {
		return _getOrderByType();
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLBuilder.create(
			_getPortletURL()
		).setParameter(
			"orderByCol", _getOrderByCol()
		).setParameter(
			"orderByType",
			Objects.equals(_getOrderByType(), "asc") ? "desc" : "asc"
		).build();

		return sortingURL;
	}

	public int getTotalItems() {
		return _searchContainer.getTotal();
	}

	public ViewTypeItemList getViewTypes() {
		return new ViewTypeItemList(_currentURLObj, _displayStyle) {
			{
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean isDisabled() {
		String navigation = _getNavigation();

		if (navigation.equals("all-pages") && !_searchContainer.hasResults()) {
			return true;
		}

		return false;
	}

	public boolean isSelectable() {
		return true;
	}

	public boolean isShowInfoButton() {
		if (Validator.isNull(
				ParamUtil.getString(_httpServletRequest, "keywords"))) {

			return true;
		}

		return false;
	}

	public boolean isShowSearch() {
		return true;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems()
		throws PortletException {

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			return null;
		}

		return new DropdownItemList() {
			{
				String navigation = _getNavigation();

				String[] navigationKeys = {
					"all-pages", "draft-pages", "frontpage", "orphan-pages",
					"pending-pages", "recent-changes"
				};

				PortletURL portletURL = _getPortletURL();

				for (String navigationKey : navigationKeys) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(
								navigation.equals(navigationKey));

							PortletURL navigationPortletURL =
								PortletURLUtil.clone(
									portletURL, _liferayPortletResponse);

							dropdownItem.setHref(
								navigationPortletURL, "navigation",
								navigationKey);

							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, navigationKey));
						});
				}
			}
		};
	}

	private String _getNavigation() {
		return ParamUtil.getString(
			_httpServletRequest, "navigation", "all-pages");
	}

	private String _getOrderByCol() {
		return _searchContainer.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems()
		throws PortletException {

		return new DropdownItemList() {
			{
				Map<String, String> orderColumns = HashMapBuilder.put(
					"modifiedDate", "modified-date"
				).put(
					"title", "title"
				).build();

				PortletURL portletURL = _getPortletURL();

				for (Map.Entry<String, String> orderByColEntry :
						orderColumns.entrySet()) {

					String orderByCol = orderByColEntry.getKey();

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(_getOrderByCol()));

							PortletURL orderByPortletURL = PortletURLUtil.clone(
								portletURL, _liferayPortletResponse);

							dropdownItem.setHref(
								orderByPortletURL, "orderByCol", orderByCol);

							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest,
									orderByColEntry.getValue()));
						});
				}
			}
		};
	}

	private String _getOrderByType() {
		return _searchContainer.getOrderByType();
	}

	private PortletURL _getPortletURL() throws PortletException {
		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLUtil.clone(_currentURLObj, _liferayPortletResponse)
		).setMVCRenderCommandName(
			"/wiki/view_pages"
		).setRedirect(
			_currentURLObj.toString()
		).build();

		return portletURL;
	}

	private final PortletURL _currentURLObj;
	private final String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SearchContainer<WikiPage> _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;
	private final WikiURLHelper _wikiURLHelper;

}