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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFolderPermission {

	public static void check(
			PermissionChecker permissionChecker, DLFolder dlFolder,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, dlFolder, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, Folder folder, String actionId)
		throws PortalException, SystemException {

		if (!folder.containsPermission(permissionChecker, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, folderId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, DLFolder dlFolder,
			String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.ADD_FOLDER)) {
			actionId = ActionKeys.ADD_SUBFOLDER;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, dlFolder.getGroupId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), PortletKeys.DOCUMENT_LIBRARY, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
			DLFolder originalFolder = dlFolder;

			try {
				if (PropsValues.PERMISSIONS_PARENT_INHERITANCE_DL_ENABLED &&
					!dlFolder.isRoot()) {

					DLFolder dlParentFolder = dlFolder.getParentFolder();

					while (dlParentFolder != null) {
						dlFolder = dlParentFolder;
						dlParentFolder = dlParentFolder.getParentFolder();
					}

					if (!_hasPermission(
							permissionChecker, dlFolder,
							ActionKeys.VIEW)) {

						return false;
					}
				}
				else {
					while (dlFolder != null) {
						if (!_hasPermission(
								permissionChecker, dlFolder, ActionKeys.VIEW)) {

							return false;
						}

						dlFolder = dlFolder.getParentFolder();
					}
				}
			}
			catch (NoSuchFolderException nsfe) {
				if (!dlFolder.isInTrash()) {
					throw nsfe;
				}
			}

			if (actionId.equals(ActionKeys.VIEW)) {
				return true;
			}

			dlFolder = originalFolder;
		}

		try {
			if (_hasPermission(permissionChecker, dlFolder, actionId)) {
				return true;
			}

			if (PropsValues.PERMISSIONS_PARENT_INHERITANCE_DL_ENABLED) {
				dlFolder = dlFolder.getParentFolder();

				while (dlFolder != null) {
					if (_hasPermission(permissionChecker, dlFolder, actionId)) {
						return true;
					}

					dlFolder = dlFolder.getParentFolder();
				}
			}
		}
		catch (NoSuchFolderException nsfe) {
			if (!dlFolder.isInTrash()) {
				throw nsfe;
			}
		}

		return false;
	}

	public static boolean contains(
			PermissionChecker permissionChecker, Folder folder, String actionId)
		throws PortalException, SystemException {

		return folder.containsPermission(permissionChecker, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException, SystemException {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Prevent the propagation of checks for actions that are not
			// supported at the application resource level. See LPS-24245.

			if (actionId.equals(ActionKeys.ACCESS) ||
				actionId.equals(ActionKeys.ADD_SUBFOLDER) ||
				actionId.equals(ActionKeys.DELETE)) {

				return false;
			}

			return DLPermission.contains(permissionChecker, groupId, actionId);
		}
		else {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			return folder.containsPermission(permissionChecker, actionId);
		}
	}

	private static boolean _hasPermission(
		PermissionChecker permissionChecker, DLFolder dlFolder,
		String actionId) {

		return
			permissionChecker.hasOwnerPermission(
				dlFolder.getCompanyId(), DLFolder.class.getName(),
				dlFolder.getFolderId(), dlFolder.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				dlFolder.getGroupId(), DLFolder.class.getName(),
				dlFolder.getFolderId(), actionId);
	}

}