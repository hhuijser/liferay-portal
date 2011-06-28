/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryModel;
import com.liferay.portal.model.RepositorySoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the Repository service. Represents a row in the &quot;Repository&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.RepositoryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link RepositoryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryImpl
 * @see com.liferay.portal.model.Repository
 * @see com.liferay.portal.model.RepositoryModel
 * @generated
 */
@JSON(strict = true)
public class RepositoryModelImpl extends BaseModelImpl<Repository>
	implements RepositoryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a repository model instance should use the {@link com.liferay.portal.model.Repository} interface instead.
	 */
	public static final String TABLE_NAME = "Repository";
	public static final Object[][] TABLE_COLUMNS = {
			{ "repositoryId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "portletId", Types.VARCHAR },
			{ "typeSettings", Types.CLOB },
			{ "dlFolderId", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table Repository (repositoryId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,name VARCHAR(75) null,description STRING null,portletId VARCHAR(75) null,typeSettings TEXT null,dlFolderId LONG)";
	public static final String TABLE_SQL_DROP = "drop table Repository";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Repository"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Repository"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Repository toModel(RepositorySoap soapModel) {
		Repository model = new RepositoryImpl();

		model.setRepositoryId(soapModel.getRepositoryId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setPortletId(soapModel.getPortletId());
		model.setTypeSettings(soapModel.getTypeSettings());
		model.setDlFolderId(soapModel.getDlFolderId());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Repository> toModels(RepositorySoap[] soapModels) {
		List<Repository> models = new ArrayList<Repository>(soapModels.length);

		for (RepositorySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public Class<?> getModelClass() {
		return Repository.class;
	}

	public String getModelClassName() {
		return Repository.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Repository"));

	public RepositoryModelImpl() {
	}

	public long getPrimaryKey() {
		return _repositoryId;
	}

	public void setPrimaryKey(long primaryKey) {
		setRepositoryId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_repositoryId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@JSON
	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	@JSON
	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@JSON
	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	@JSON
	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	@JSON
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	@JSON
	public String getPortletId() {
		if (_portletId == null) {
			return StringPool.BLANK;
		}
		else {
			return _portletId;
		}
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	@JSON
	public String getTypeSettings() {
		if (_typeSettings == null) {
			return StringPool.BLANK;
		}
		else {
			return _typeSettings;
		}
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	@JSON
	public long getDlFolderId() {
		return _dlFolderId;
	}

	public void setDlFolderId(long dlFolderId) {
		_dlFolderId = dlFolderId;
	}

	@Override
	public Repository toEscapedModel() {
		if (isEscapedModel()) {
			return (Repository)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (Repository)Proxy.newProxyInstance(_classLoader,
						_escapedModelProxyInterfaces,
						new AutoEscapeBeanHandler(this));
			}

			return _escapedModelProxy;
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					Repository.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		RepositoryImpl repositoryImpl = new RepositoryImpl();

		repositoryImpl.setRepositoryId(getRepositoryId());
		repositoryImpl.setGroupId(getGroupId());
		repositoryImpl.setCompanyId(getCompanyId());
		repositoryImpl.setCreateDate(getCreateDate());
		repositoryImpl.setModifiedDate(getModifiedDate());
		repositoryImpl.setClassNameId(getClassNameId());
		repositoryImpl.setName(getName());
		repositoryImpl.setDescription(getDescription());
		repositoryImpl.setPortletId(getPortletId());
		repositoryImpl.setTypeSettings(getTypeSettings());
		repositoryImpl.setDlFolderId(getDlFolderId());

		repositoryImpl.resetOriginalValues();

		return repositoryImpl;
	}

	public int compareTo(Repository repository) {
		long primaryKey = repository.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Repository repository = null;

		try {
			repository = (Repository)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = repository.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{repositoryId=");
		sb.append(getRepositoryId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", portletId=");
		sb.append(getPortletId());
		sb.append(", typeSettings=");
		sb.append(getTypeSettings());
		sb.append(", dlFolderId=");
		sb.append(getDlFolderId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Repository");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>repositoryId</column-name><column-value><![CDATA[");
		sb.append(getRepositoryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>portletId</column-name><column-value><![CDATA[");
		sb.append(getPortletId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeSettings</column-name><column-value><![CDATA[");
		sb.append(getTypeSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>dlFolderId</column-name><column-value><![CDATA[");
		sb.append(getDlFolderId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Repository.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			Repository.class
		};
	private long _repositoryId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private String _name;
	private String _description;
	private String _portletId;
	private String _typeSettings;
	private long _dlFolderId;
	private transient ExpandoBridge _expandoBridge;
	private Repository _escapedModelProxy;
}