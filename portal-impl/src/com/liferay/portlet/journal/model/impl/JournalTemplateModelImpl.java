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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.JournalTemplateModel;
import com.liferay.portlet.journal.model.JournalTemplateSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the JournalTemplate service. Represents a row in the &quot;JournalTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portlet.journal.model.JournalTemplateModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link JournalTemplateImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplateImpl
 * @see com.liferay.portlet.journal.model.JournalTemplate
 * @see com.liferay.portlet.journal.model.JournalTemplateModel
 * @generated
 */
@JSON(strict = true)
public class JournalTemplateModelImpl extends BaseModelImpl<JournalTemplate>
	implements JournalTemplateModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a journal template model instance should use the {@link com.liferay.portlet.journal.model.JournalTemplate} interface instead.
	 */
	public static final String TABLE_NAME = "JournalTemplate";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "id_", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "templateId", Types.VARCHAR },
			{ "structureId", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "xsl", Types.CLOB },
			{ "langType", Types.VARCHAR },
			{ "cacheable", Types.BOOLEAN },
			{ "smallImage", Types.BOOLEAN },
			{ "smallImageId", Types.BIGINT },
			{ "smallImageURL", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table JournalTemplate (uuid_ VARCHAR(75) null,id_ LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(255) null,createDate DATE null,modifiedDate DATE null,templateId VARCHAR(75) null,structureId VARCHAR(75) null,name VARCHAR(75) null,description STRING null,xsl TEXT null,langType VARCHAR(75) null,cacheable BOOLEAN,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null)";
	public static final String TABLE_SQL_DROP = "drop table JournalTemplate";
	public static final String ORDER_BY_JPQL = " ORDER BY journalTemplate.templateId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY JournalTemplate.templateId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.journal.model.JournalTemplate"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.journal.model.JournalTemplate"),
			true);

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static JournalTemplate toModel(JournalTemplateSoap soapModel) {
		JournalTemplate model = new JournalTemplateImpl();

		model.setUuid(soapModel.getUuid());
		model.setId(soapModel.getId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setTemplateId(soapModel.getTemplateId());
		model.setStructureId(soapModel.getStructureId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setXsl(soapModel.getXsl());
		model.setLangType(soapModel.getLangType());
		model.setCacheable(soapModel.getCacheable());
		model.setSmallImage(soapModel.getSmallImage());
		model.setSmallImageId(soapModel.getSmallImageId());
		model.setSmallImageURL(soapModel.getSmallImageURL());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<JournalTemplate> toModels(
		JournalTemplateSoap[] soapModels) {
		List<JournalTemplate> models = new ArrayList<JournalTemplate>(soapModels.length);

		for (JournalTemplateSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public Class<?> getModelClass() {
		return JournalTemplate.class;
	}

	public String getModelClassName() {
		return JournalTemplate.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalTemplate"));

	public JournalTemplateModelImpl() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long primaryKey) {
		setId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_id);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@JSON
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	@JSON
	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@JSON
	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@JSON
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	public void setUserName(String userName) {
		_userName = userName;
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

	@JSON
	public String getTemplateId() {
		if (_templateId == null) {
			return StringPool.BLANK;
		}
		else {
			return _templateId;
		}
	}

	public void setTemplateId(String templateId) {
		if (_originalTemplateId == null) {
			_originalTemplateId = _templateId;
		}

		_templateId = templateId;
	}

	public String getOriginalTemplateId() {
		return GetterUtil.getString(_originalTemplateId);
	}

	@JSON
	public String getStructureId() {
		if (_structureId == null) {
			return StringPool.BLANK;
		}
		else {
			return _structureId;
		}
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
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
	public String getXsl() {
		if (_xsl == null) {
			return StringPool.BLANK;
		}
		else {
			return _xsl;
		}
	}

	public void setXsl(String xsl) {
		_xsl = xsl;
	}

	@JSON
	public String getLangType() {
		if (_langType == null) {
			return StringPool.BLANK;
		}
		else {
			return _langType;
		}
	}

	public void setLangType(String langType) {
		_langType = langType;
	}

	@JSON
	public boolean getCacheable() {
		return _cacheable;
	}

	public boolean isCacheable() {
		return _cacheable;
	}

	public void setCacheable(boolean cacheable) {
		_cacheable = cacheable;
	}

	@JSON
	public boolean getSmallImage() {
		return _smallImage;
	}

	public boolean isSmallImage() {
		return _smallImage;
	}

	public void setSmallImage(boolean smallImage) {
		_smallImage = smallImage;
	}

	@JSON
	public long getSmallImageId() {
		return _smallImageId;
	}

	public void setSmallImageId(long smallImageId) {
		if (!_setOriginalSmallImageId) {
			_setOriginalSmallImageId = true;

			_originalSmallImageId = _smallImageId;
		}

		_smallImageId = smallImageId;
	}

	public long getOriginalSmallImageId() {
		return _originalSmallImageId;
	}

	@JSON
	public String getSmallImageURL() {
		if (_smallImageURL == null) {
			return StringPool.BLANK;
		}
		else {
			return _smallImageURL;
		}
	}

	public void setSmallImageURL(String smallImageURL) {
		_smallImageURL = smallImageURL;
	}

	@Override
	public JournalTemplate toEscapedModel() {
		if (isEscapedModel()) {
			return (JournalTemplate)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (JournalTemplate)Proxy.newProxyInstance(_classLoader,
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
					JournalTemplate.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		JournalTemplateImpl journalTemplateImpl = new JournalTemplateImpl();

		journalTemplateImpl.setUuid(getUuid());
		journalTemplateImpl.setId(getId());
		journalTemplateImpl.setGroupId(getGroupId());
		journalTemplateImpl.setCompanyId(getCompanyId());
		journalTemplateImpl.setUserId(getUserId());
		journalTemplateImpl.setUserName(getUserName());
		journalTemplateImpl.setCreateDate(getCreateDate());
		journalTemplateImpl.setModifiedDate(getModifiedDate());
		journalTemplateImpl.setTemplateId(getTemplateId());
		journalTemplateImpl.setStructureId(getStructureId());
		journalTemplateImpl.setName(getName());
		journalTemplateImpl.setDescription(getDescription());
		journalTemplateImpl.setXsl(getXsl());
		journalTemplateImpl.setLangType(getLangType());
		journalTemplateImpl.setCacheable(getCacheable());
		journalTemplateImpl.setSmallImage(getSmallImage());
		journalTemplateImpl.setSmallImageId(getSmallImageId());
		journalTemplateImpl.setSmallImageURL(getSmallImageURL());

		journalTemplateImpl.resetOriginalValues();

		return journalTemplateImpl;
	}

	public int compareTo(JournalTemplate journalTemplate) {
		int value = 0;

		value = getTemplateId().compareTo(journalTemplate.getTemplateId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalTemplate journalTemplate = null;

		try {
			journalTemplate = (JournalTemplate)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = journalTemplate.getPrimaryKey();

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
		JournalTemplateModelImpl journalTemplateModelImpl = this;

		journalTemplateModelImpl._originalUuid = journalTemplateModelImpl._uuid;

		journalTemplateModelImpl._originalGroupId = journalTemplateModelImpl._groupId;

		journalTemplateModelImpl._setOriginalGroupId = false;

		journalTemplateModelImpl._originalTemplateId = journalTemplateModelImpl._templateId;

		journalTemplateModelImpl._originalSmallImageId = journalTemplateModelImpl._smallImageId;

		journalTemplateModelImpl._setOriginalSmallImageId = false;
	}

	@Override
	public CacheModel<JournalTemplate> toCacheModel() {
		JournalTemplateCacheModel journalTemplateCacheModel = new JournalTemplateCacheModel();

		journalTemplateCacheModel.uuid = getUuid();

		String uuid = journalTemplateCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			journalTemplateCacheModel.uuid = null;
		}

		journalTemplateCacheModel.id = getId();

		journalTemplateCacheModel.groupId = getGroupId();

		journalTemplateCacheModel.companyId = getCompanyId();

		journalTemplateCacheModel.userId = getUserId();

		journalTemplateCacheModel.userName = getUserName();

		String userName = journalTemplateCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			journalTemplateCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			journalTemplateCacheModel.createDate = createDate.getTime();
		}
		else {
			journalTemplateCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			journalTemplateCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			journalTemplateCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		journalTemplateCacheModel.templateId = getTemplateId();

		String templateId = journalTemplateCacheModel.templateId;

		if ((templateId != null) && (templateId.length() == 0)) {
			journalTemplateCacheModel.templateId = null;
		}

		journalTemplateCacheModel.structureId = getStructureId();

		String structureId = journalTemplateCacheModel.structureId;

		if ((structureId != null) && (structureId.length() == 0)) {
			journalTemplateCacheModel.structureId = null;
		}

		journalTemplateCacheModel.name = getName();

		String name = journalTemplateCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			journalTemplateCacheModel.name = null;
		}

		journalTemplateCacheModel.description = getDescription();

		String description = journalTemplateCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			journalTemplateCacheModel.description = null;
		}

		journalTemplateCacheModel.xsl = getXsl();

		String xsl = journalTemplateCacheModel.xsl;

		if ((xsl != null) && (xsl.length() == 0)) {
			journalTemplateCacheModel.xsl = null;
		}

		journalTemplateCacheModel.langType = getLangType();

		String langType = journalTemplateCacheModel.langType;

		if ((langType != null) && (langType.length() == 0)) {
			journalTemplateCacheModel.langType = null;
		}

		journalTemplateCacheModel.cacheable = getCacheable();

		journalTemplateCacheModel.smallImage = getSmallImage();

		journalTemplateCacheModel.smallImageId = getSmallImageId();

		journalTemplateCacheModel.smallImageURL = getSmallImageURL();

		String smallImageURL = journalTemplateCacheModel.smallImageURL;

		if ((smallImageURL != null) && (smallImageURL.length() == 0)) {
			journalTemplateCacheModel.smallImageURL = null;
		}

		return journalTemplateCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", id=");
		sb.append(getId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", templateId=");
		sb.append(getTemplateId());
		sb.append(", structureId=");
		sb.append(getStructureId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", xsl=");
		sb.append(getXsl());
		sb.append(", langType=");
		sb.append(getLangType());
		sb.append(", cacheable=");
		sb.append(getCacheable());
		sb.append(", smallImage=");
		sb.append(getSmallImage());
		sb.append(", smallImageId=");
		sb.append(getSmallImageId());
		sb.append(", smallImageURL=");
		sb.append(getSmallImageURL());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(58);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.journal.model.JournalTemplate");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>id</column-name><column-value><![CDATA[");
		sb.append(getId());
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
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
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
			"<column><column-name>templateId</column-name><column-value><![CDATA[");
		sb.append(getTemplateId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>structureId</column-name><column-value><![CDATA[");
		sb.append(getStructureId());
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
			"<column><column-name>xsl</column-name><column-value><![CDATA[");
		sb.append(getXsl());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>langType</column-name><column-value><![CDATA[");
		sb.append(getLangType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>cacheable</column-name><column-value><![CDATA[");
		sb.append(getCacheable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>smallImage</column-name><column-value><![CDATA[");
		sb.append(getSmallImage());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>smallImageId</column-name><column-value><![CDATA[");
		sb.append(getSmallImageId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>smallImageURL</column-name><column-value><![CDATA[");
		sb.append(getSmallImageURL());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = JournalTemplate.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			JournalTemplate.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _id;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _templateId;
	private String _originalTemplateId;
	private String _structureId;
	private String _name;
	private String _description;
	private String _xsl;
	private String _langType;
	private boolean _cacheable;
	private boolean _smallImage;
	private long _smallImageId;
	private long _originalSmallImageId;
	private boolean _setOriginalSmallImageId;
	private String _smallImageURL;
	private transient ExpandoBridge _expandoBridge;
	private JournalTemplate _escapedModelProxy;
}