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

package com.liferay.commerce.order.rule.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.order.rule.service.http.CommerceOrderRuleEntryServiceSoap}.
 *
 * @author Luca Pellizzon
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceOrderRuleEntrySoap implements Serializable {

	public static CommerceOrderRuleEntrySoap toSoapModel(
		CommerceOrderRuleEntry model) {

		CommerceOrderRuleEntrySoap soapModel = new CommerceOrderRuleEntrySoap();

		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setCommerceOrderRuleEntryId(
			model.getCommerceOrderRuleEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setActive(model.isActive());
		soapModel.setDescription(model.getDescription());
		soapModel.setName(model.getName());
		soapModel.setPriority(model.getPriority());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());

		return soapModel;
	}

	public static CommerceOrderRuleEntrySoap[] toSoapModels(
		CommerceOrderRuleEntry[] models) {

		CommerceOrderRuleEntrySoap[] soapModels =
			new CommerceOrderRuleEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderRuleEntrySoap[][] toSoapModels(
		CommerceOrderRuleEntry[][] models) {

		CommerceOrderRuleEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CommerceOrderRuleEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceOrderRuleEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceOrderRuleEntrySoap[] toSoapModels(
		List<CommerceOrderRuleEntry> models) {

		List<CommerceOrderRuleEntrySoap> soapModels =
			new ArrayList<CommerceOrderRuleEntrySoap>(models.size());

		for (CommerceOrderRuleEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceOrderRuleEntrySoap[soapModels.size()]);
	}

	public CommerceOrderRuleEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commerceOrderRuleEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceOrderRuleEntryId(pk);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getCommerceOrderRuleEntryId() {
		return _commerceOrderRuleEntryId;
	}

	public void setCommerceOrderRuleEntryId(long commerceOrderRuleEntryId) {
		_commerceOrderRuleEntryId = commerceOrderRuleEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	private String _externalReferenceCode;
	private long _commerceOrderRuleEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _active;
	private String _description;
	private String _name;
	private int _priority;
	private String _type;
	private String _typeSettings;

}