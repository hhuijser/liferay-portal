/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.security.permission;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.BasePermissionPropagator;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Kenneth Chang
 */
public class MBPermissionPropagatorImpl extends BasePermissionPropagator {

	public void propagateRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws Exception {

		if (!className.equals(MBCategory.class.getName())) {
			return;
		}

		long categoryId = GetterUtil.getLong(primKey);

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		List<MBMessage> mbMessages =
			MBMessageLocalServiceUtil.getCategoryMessages(
			category.getGroupId(), categoryId, WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MBMessage mbMessage : mbMessages) {
			for (long roleId : roleIds) {
				propagateRolePermissions(
					actionRequest, roleId, MBCategory.class.getName(),
					categoryId, MBMessage.class.getName(),
					mbMessage.getPrimaryKey());
			}
		}
	}

}