/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Mate Thurzo
 */
public class MBCategoryPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, category, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if ((categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) ||
			(categoryId == MBCategoryConstants.DISCUSSION_CATEGORY_ID)) {

			return MBPermission.contains(permissionChecker, groupId, actionId);
		}
		else {
			MBCategory category = MBCategoryLocalServiceUtil.getCategory(
				categoryId);

			return contains(permissionChecker, category, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		return contains(permissionChecker, category, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBCategory category,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_CATEGORY)) {
			actionId = ActionKeys.ADD_SUBCATEGORY;
		}

		if (MBBanLocalServiceUtil.hasBan(
				category.getGroupId(), permissionChecker.getUserId())) {

			return false;
		}

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
			MBCategory originalCategory = category;

			try {
				if (PropsValues.
						PERMISSIONS_PARENT_INHERITANCE_MESSAGE_BOARDS_ENABLED &&
					!category.isRoot()) {

					MBCategory parentCategory = category.getParentCategory();

					while (parentCategory != null) {
						category = parentCategory;
						parentCategory = parentCategory.getParentCategory();
					}

					if (!_hasPermission(
							permissionChecker, category,
							ActionKeys.VIEW)) {

						return false;
					}
				}
				else {
					while (category != null) {
						if (!_hasPermission(
								permissionChecker, category, ActionKeys.VIEW)) {

							return false;
						}

						category = category.getParentCategory();
					}
				}
			}
			catch (NoSuchCategoryException nsce) {
				if (!category.isInTrash()) {
					throw nsce;
				}
			}

			if (actionId.equals(ActionKeys.VIEW)) {
				return true;
			}

			category = originalCategory;
		}

		try {
			if (_hasPermission(permissionChecker, category, actionId)) {
				return true;
			}

			if (PropsValues.
					PERMISSIONS_PARENT_INHERITANCE_MESSAGE_BOARDS_ENABLED) {

				category = category.getParentCategory();

				while (category != null) {
					if (_hasPermission(permissionChecker, category, actionId)) {
						return true;
					}

					category = category.getParentCategory();
				}
			}
		}
		catch (NoSuchCategoryException nsce) {
			if (!category.isInTrash()) {
				throw nsce;
			}
		}

		return false;
	}

	private static boolean _hasPermission(
		PermissionChecker permissionChecker, MBCategory category,
		String actionId) {

		return
			permissionChecker.hasOwnerPermission(
				category.getCompanyId(), MBCategory.class.getName(),
				category.getCategoryId(), category.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				category.getGroupId(), MBCategory.class.getName(),
				category.getCategoryId(), actionId);
	}

}