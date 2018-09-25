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

package com.liferay.sharing.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Collection;

/**
 * Provides an interface that defines whether the user has permission to share
 * a resource to another user with sharing entry actions.
 *
 * <p>
 * Tipically, implementations of this interface will map the sharing entry
 * actions with the resource actions and delegate the permission check to the
 * corresponding {@link ModelResourcePermission}.
 * </p>
 *
 * <p>
 * Implementations of this interface must be registered as OSGi components using
 * the service {@link SharingPermissionChecker}. The
 * <code>model.class.name</code> property defines the class name that the
 * permission checker can handle.
 * </p>
 *
 * @author Sergio González
 * @review
 */
public interface SharingPermissionChecker {

	/**
	 * Returns <code>true</code> if the user has permission to share a resource
	 * to another user with sharing entry actions.
	 *
	 * @param  permissionChecker the permission checker of the user sharing the
	 *         resource
	 * @param  classPK the primary key of the shared resource
	 * @param  groupId the primary key of the group containing the shared
	 *         resource
	 * @param  sharingEntryActions a collection of sharing entry actions to
	 *         be checked for permission
	 * @return <code>true</code> if the user has permission to share the
	 *         resource with sharing entry actions; <code>false</code>
	 *         otherwise
	 */
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, long groupId,
			Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException;

}