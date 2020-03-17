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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Julien Castelain
 */
public class CardsDisplayContext {

	public List<DropdownItem> getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		_actionDropdownItems = DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref("#1");
				dropdownItem.setLabel("Edit");
				dropdownItem.setSeparator(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#2");
				dropdownItem.setLabel("Save");
			}
		).build();

		return _actionDropdownItems;
	}

	public List<LabelItem> getLabelItems() {
		return new LabelItemList() {
			{
				int numItems = 1 + RandomUtil.nextInt(3);

				LabelItem labelItem1 = new LabelItem();

				labelItem1.setLabel("Approved");
				labelItem1.setStyle("success");

				add(labelItem1);

				if (numItems > 1) {
					LabelItem labelItem2 = new LabelItem();

					labelItem2.setLabel("Pending");

					add(labelItem2);
				}

				if (numItems > 2) {
					LabelItem labelItem3 = new LabelItem();

					labelItem3.setLabel("Canceled");
					labelItem3.setStyle("danger");

					add(labelItem3);
				}
			}
		};
	}

	public Map<String, String> getLabelStylesMap() {
		if (_labelStylesMap != null) {
			return _labelStylesMap;
		}

		_labelStylesMap = HashMapBuilder.put(
			"Pending", "warning"
		).build();

		return _labelStylesMap;
	}

	private List<DropdownItem> _actionDropdownItems;
	private Map<String, String> _labelStylesMap;

}