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

package com.liferay.users.admin.web.internal.manager;

import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.service.OrgLaborLocalService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Samuel Trong Tran
 */
public class OrgLaborContactInfoManager
	extends BaseContactInfoManager<OrgLabor> {

	public OrgLaborContactInfoManager(
		long classPK, OrgLaborLocalService orgLaborLocalService,
		OrgLaborService orgLaborService) {

		_classPK = classPK;
		_orgLaborLocalService = orgLaborLocalService;
		_orgLaborService = orgLaborService;
	}

	@Override
	protected OrgLabor construct(ActionRequest actionRequest) throws Exception {
		long orgLaborId = ParamUtil.getLong(actionRequest, "primaryKey");

		OrgLabor orgLabor = _orgLaborLocalService.createOrgLabor(orgLaborId);

		orgLabor.setTypeId(ParamUtil.getLong(actionRequest, "orgLaborTypeId"));
		orgLabor.setSunOpen(ParamUtil.getInteger(actionRequest, "sunOpen", -1));
		orgLabor.setSunClose(
			ParamUtil.getInteger(actionRequest, "sunClose", -1));
		orgLabor.setMonOpen(ParamUtil.getInteger(actionRequest, "monOpen", -1));
		orgLabor.setMonClose(
			ParamUtil.getInteger(actionRequest, "monClose", -1));
		orgLabor.setTueOpen(ParamUtil.getInteger(actionRequest, "tueOpen", -1));
		orgLabor.setTueClose(
			ParamUtil.getInteger(actionRequest, "tueClose", -1));
		orgLabor.setWedOpen(ParamUtil.getInteger(actionRequest, "wedOpen", -1));
		orgLabor.setWedClose(
			ParamUtil.getInteger(actionRequest, "wedClose", -1));
		orgLabor.setThuOpen(ParamUtil.getInteger(actionRequest, "thuOpen", -1));
		orgLabor.setThuClose(
			ParamUtil.getInteger(actionRequest, "thuClose", -1));
		orgLabor.setFriOpen(ParamUtil.getInteger(actionRequest, "friOpen", -1));
		orgLabor.setFriClose(
			ParamUtil.getInteger(actionRequest, "friClose", -1));
		orgLabor.setSatOpen(ParamUtil.getInteger(actionRequest, "satOpen", -1));
		orgLabor.setSatClose(
			ParamUtil.getInteger(actionRequest, "satClose", -1));

		return orgLabor;
	}

	@Override
	protected OrgLabor doAdd(OrgLabor orgLabor) throws Exception {
		return _orgLaborService.addOrgLabor(
			_classPK, orgLabor.getTypeId(), orgLabor.getSunOpen(),
			orgLabor.getSunClose(), orgLabor.getMonOpen(),
			orgLabor.getMonClose(), orgLabor.getTueOpen(),
			orgLabor.getTueClose(), orgLabor.getWedOpen(),
			orgLabor.getWedClose(), orgLabor.getThuOpen(),
			orgLabor.getThuClose(), orgLabor.getFriOpen(),
			orgLabor.getFriClose(), orgLabor.getSatOpen(),
			orgLabor.getSatClose());
	}

	@Override
	protected void doDelete(long orgLaborId) throws Exception {
		_orgLaborService.deleteOrgLabor(orgLaborId);
	}

	@Override
	protected void doUpdate(OrgLabor orgLabor) throws Exception {
		_orgLaborService.updateOrgLabor(
			orgLabor.getOrgLaborId(), orgLabor.getTypeId(),
			orgLabor.getSunOpen(), orgLabor.getSunClose(),
			orgLabor.getMonOpen(), orgLabor.getMonClose(),
			orgLabor.getTueOpen(), orgLabor.getTueClose(),
			orgLabor.getWedOpen(), orgLabor.getWedClose(),
			orgLabor.getThuOpen(), orgLabor.getThuClose(),
			orgLabor.getFriOpen(), orgLabor.getFriClose(),
			orgLabor.getSatOpen(), orgLabor.getSatClose());
	}

	@Override
	protected OrgLabor get(long orgLaborId) throws Exception {
		return _orgLaborService.getOrgLabor(orgLaborId);
	}

	@Override
	protected List<OrgLabor> getAll() throws Exception {
		return _orgLaborService.getOrgLabors(_classPK);
	}

	@Override
	protected long getPrimaryKey(OrgLabor orgLabor) {
		return orgLabor.getOrgLaborId();
	}

	@Override
	protected boolean isPrimary(OrgLabor orgLabor) {
		return false;
	}

	@Override
	protected void setPrimary(OrgLabor orgLabor, boolean primary) {
	}

	private final long _classPK;
	private final OrgLaborLocalService _orgLaborLocalService;
	private final OrgLaborService _orgLaborService;

}