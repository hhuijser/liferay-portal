/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Mika Koivisto
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SamlSpSessionSoap implements Serializable {

	public static SamlSpSessionSoap toSoapModel(SamlSpSession model) {
		SamlSpSessionSoap soapModel = new SamlSpSessionSoap();

		soapModel.setSamlSpSessionId(model.getSamlSpSessionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSamlPeerBindingId(model.getSamlPeerBindingId());
		soapModel.setAssertionXml(model.getAssertionXml());
		soapModel.setJSessionId(model.getJSessionId());
		soapModel.setSamlSpSessionKey(model.getSamlSpSessionKey());
		soapModel.setSessionIndex(model.getSessionIndex());
		soapModel.setTerminated(model.isTerminated());

		return soapModel;
	}

	public static SamlSpSessionSoap[] toSoapModels(SamlSpSession[] models) {
		SamlSpSessionSoap[] soapModels = new SamlSpSessionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SamlSpSessionSoap[][] toSoapModels(SamlSpSession[][] models) {
		SamlSpSessionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SamlSpSessionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SamlSpSessionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SamlSpSessionSoap[] toSoapModels(List<SamlSpSession> models) {
		List<SamlSpSessionSoap> soapModels = new ArrayList<SamlSpSessionSoap>(
			models.size());

		for (SamlSpSession model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SamlSpSessionSoap[soapModels.size()]);
	}

	public SamlSpSessionSoap() {
	}

	public long getPrimaryKey() {
		return _samlSpSessionId;
	}

	public void setPrimaryKey(long pk) {
		setSamlSpSessionId(pk);
	}

	public long getSamlSpSessionId() {
		return _samlSpSessionId;
	}

	public void setSamlSpSessionId(long samlSpSessionId) {
		_samlSpSessionId = samlSpSessionId;
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

	public long getSamlPeerBindingId() {
		return _samlPeerBindingId;
	}

	public void setSamlPeerBindingId(long samlPeerBindingId) {
		_samlPeerBindingId = samlPeerBindingId;
	}

	public String getAssertionXml() {
		return _assertionXml;
	}

	public void setAssertionXml(String assertionXml) {
		_assertionXml = assertionXml;
	}

	public String getJSessionId() {
		return _jSessionId;
	}

	public void setJSessionId(String jSessionId) {
		_jSessionId = jSessionId;
	}

	public String getSamlSpSessionKey() {
		return _samlSpSessionKey;
	}

	public void setSamlSpSessionKey(String samlSpSessionKey) {
		_samlSpSessionKey = samlSpSessionKey;
	}

	public String getSessionIndex() {
		return _sessionIndex;
	}

	public void setSessionIndex(String sessionIndex) {
		_sessionIndex = sessionIndex;
	}

	public boolean getTerminated() {
		return _terminated;
	}

	public boolean isTerminated() {
		return _terminated;
	}

	public void setTerminated(boolean terminated) {
		_terminated = terminated;
	}

	private long _samlSpSessionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _samlPeerBindingId;
	private String _assertionXml;
	private String _jSessionId;
	private String _samlSpSessionKey;
	private String _sessionIndex;
	private boolean _terminated;

}