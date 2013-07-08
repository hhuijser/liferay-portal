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

package com.liferay.portlet.bookmarks.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, BookmarksEntry entry,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, entryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, BookmarksEntry entry,
			String actionId)
		throws PortalException, SystemException {

		if (entry.getFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			try {
				BookmarksFolder folder =
					BookmarksFolderLocalServiceUtil.getFolder(
						entry.getFolderId());

				if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE &&
					!BookmarksFolderPermission.contains(
						permissionChecker, folder, ActionKeys.ACCESS) &&
					!BookmarksFolderPermission.contains(
						permissionChecker, folder, ActionKeys.VIEW)) {

					return false;
				}

				if (PropsValues.
						PERMISSIONS_PARENT_INHERITANCE_BOOKMARKS_ENABLED &&
					BookmarksFolderPermission.contains(
						permissionChecker, folder, actionId)) {

					return true;
				}
			}
			catch (NoSuchFolderException nsfe) {
				if (!entry.isInTrash()) {
					throw nsfe;
				}
			}
		}

		return
			permissionChecker.hasOwnerPermission(
				entry.getCompanyId(), BookmarksEntry.class.getName(),
				entry.getEntryId(), entry.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				entry.getGroupId(), BookmarksEntry.class.getName(),
				entry.getEntryId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(entryId);

		return contains(permissionChecker, entry, actionId);
	}

}