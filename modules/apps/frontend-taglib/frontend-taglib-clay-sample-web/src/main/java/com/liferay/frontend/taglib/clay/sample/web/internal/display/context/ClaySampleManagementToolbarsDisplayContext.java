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

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.BaseManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class ClaySampleManagementToolbarsDisplayContext
	extends BaseManagementToolbarDisplayContext {

	public ClaySampleManagementToolbarsDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse);
	}

	public List<DropdownItem> getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		_actionDropdownItems = DropdownItemListBuilder.add(
			DropdownItemBuilder.setHref(
				"#edit"
			).setLabel(
				"Edit"
			).build()
		).add(
			DropdownItemBuilder.setIcon(
				"download"
			).setLabel(
				"Download"
			).setQuickAction(
				true
			).build()
		).add(
			DropdownItemBuilder.setHref(
				"#delete"
			).setIcon(
				"trash"
			).setLabel(
				"Delete"
			).setQuickAction(
				true
			).build()
		).build();

		return _actionDropdownItems;
	}

	public CreationMenu getCreationMenu() {
		if (_creationMenu != null) {
			return _creationMenu;
		}

		_creationMenu = CreationMenuBuilder.addPrimaryDropdownItem(
			DropdownItemBuilder.setHref(
				"#1"
			).setLabel(
				"Sample 1"
			).build()
		).addPrimaryDropdownItem(
			DropdownItemBuilder.setHref(
				"#2"
			).setLabel(
				"Sample 2"
			).build()
		).addFavoriteDropdownItem(
			DropdownItemBuilder.setHref(
				"#3"
			).setLabel(
				"Favorite 1"
			).build()
		).addFavoriteDropdownItem(
			DropdownItemBuilder.setHref(
				"#4"
			).setLabel(
				"Favorite 2"
			).build()
		).addRestDropdownItem(
			DropdownItemBuilder.setHref(
				"#5"
			).setLabel(
				"Secondary 1"
			).build()
		).addRestDropdownItem(
			DropdownItemBuilder.setHref(
				"#6"
			).setLabel(
				"Secondary 2"
			).build()
		).addRestDropdownItem(
			DropdownItemBuilder.setHref(
				"#7"
			).setLabel(
				"Secondary 3"
			).build()
		).addRestDropdownItem(
			DropdownItemBuilder.setHref(
				"#8"
			).setLabel(
				"Secondary 4"
			).build()
		).addRestDropdownItem(
			DropdownItemBuilder.setHref(
				"#9"
			).setLabel(
				"Secondary 5"
			).build()
		).addRestDropdownItem(
			DropdownItemBuilder.setHref(
				"#10"
			).setLabel(
				"Secondary 6"
			).build()
		).build();

		_creationMenu.put("maxTotalItems", 8);

		return _creationMenu;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		if (_filterDropdownItems != null) {
			return _filterDropdownItems;
		}

		_filterDropdownItems = DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						DropdownItemBuilder.setHref(
							"#1"
						).setLabel(
							"Filter 1"
						).build()
					).add(
						DropdownItemBuilder.setHref(
							"#2"
						).setLabel(
							"Filter 2"
						).build()
					).build());

				dropdownGroupItem.setLabel("Filter By");
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						DropdownItemBuilder.setHref(
							"#3"
						).setLabel(
							"Order 1"
						).build()
					).add(
						DropdownItemBuilder.setHref(
							"#4"
						).setLabel(
							"Order 2"
						).build()
					).build());

				dropdownGroupItem.setLabel("Order By");
			}
		).build();

		return _filterDropdownItems;
	}

	public String getSearchActionURL() {
		return "#search-action-url";
	}

	public Boolean getSupportsBulkActions() {
		return true;
	}

	public List<ViewTypeItem> getViewTypeItems() {
		if (_viewTypeItems != null) {
			return _viewTypeItems;
		}

		_viewTypeItems = new ViewTypeItemList() {
			{
				addCardViewTypeItem(
					viewTypeItem -> {
						viewTypeItem.setActive(true);
						viewTypeItem.setLabel("Card");
					});

				addListViewTypeItem(
					viewTypeItem -> viewTypeItem.setLabel("List"));

				addTableViewTypeItem(
					viewTypeItem -> viewTypeItem.setLabel("Table"));
			}
		};

		return _viewTypeItems;
	}

	public Boolean isShowInfoButton() {
		return true;
	}

	private List<DropdownItem> _actionDropdownItems;
	private CreationMenu _creationMenu;
	private List<DropdownItem> _filterDropdownItems;
	private List<ViewTypeItem> _viewTypeItems;

}