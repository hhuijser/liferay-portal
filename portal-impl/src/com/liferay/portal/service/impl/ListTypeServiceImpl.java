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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.ListType;
import com.liferay.portal.service.base.ListTypeServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ListTypeServiceImpl extends ListTypeServiceBaseImpl {

	@Override
	public ListType getListType(long listTypeId) throws PortalException {
		return listTypeLocalService.getListType(listTypeId);
	}

	@Override
	public List<ListType> getListTypes(String type) {
		return listTypeLocalService.getListTypes(type);
	}

	@Override
	public void validate(long listTypeId, long classNameId, String type)
		throws PortalException {

		listTypeLocalService.validate(listTypeId, classNameId, type);
	}

	@Override
	public void validate(long listTypeId, String type) throws PortalException {
		listTypeLocalService.validate(listTypeId, type);
	}

}