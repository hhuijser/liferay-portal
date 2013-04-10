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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupModel;
import com.liferay.portal.model.GroupSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the Group service. Represents a row in the &quot;Group_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.GroupModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link GroupImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupImpl
 * @see com.liferay.portal.model.Group
 * @see com.liferay.portal.model.GroupModel
 * @generated
 */
@JSON(strict = true)
public class GroupModelImpl extends BaseModelImpl<Group> implements GroupModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a group model instance should use the {@link com.liferay.portal.model.Group} interface instead.
	 */
	public static final String TABLE_NAME = "Group_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "creatorUserId", Types.BIGINT },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "parentGroupId", Types.BIGINT },
			{ "liveGroupId", Types.BIGINT },
			{ "treePath", Types.VARCHAR },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "type_", Types.INTEGER },
			{ "typeSettings", Types.VARCHAR },
			{ "friendlyURL", Types.VARCHAR },
			{ "site", Types.BOOLEAN },
			{ "active_", Types.BOOLEAN }
		};
	public static final String TABLE_SQL_CREATE = "create table Group_ (uuid_ VARCHAR(75) null,groupId LONG not null primary key,companyId LONG,creatorUserId LONG,classNameId LONG,classPK LONG,parentGroupId LONG,liveGroupId LONG,treePath VARCHAR(75) null,name VARCHAR(150) null,description STRING null,type_ INTEGER,typeSettings STRING null,friendlyURL VARCHAR(255) null,site BOOLEAN,active_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table Group_";
	public static final String ORDER_BY_JPQL = " ORDER BY group_.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Group_.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Group"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Group"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.model.Group"),
			true);
	public static long ACTIVE_COLUMN_BITMASK = 1L;
	public static long CLASSNAMEID_COLUMN_BITMASK = 2L;
	public static long CLASSPK_COLUMN_BITMASK = 4L;
	public static long COMPANYID_COLUMN_BITMASK = 8L;
	public static long FRIENDLYURL_COLUMN_BITMASK = 16L;
	public static long GROUPID_COLUMN_BITMASK = 32L;
	public static long LIVEGROUPID_COLUMN_BITMASK = 64L;
	public static long NAME_COLUMN_BITMASK = 128L;
	public static long PARENTGROUPID_COLUMN_BITMASK = 256L;
	public static long SITE_COLUMN_BITMASK = 512L;
	public static long TYPE_COLUMN_BITMASK = 1024L;
	public static long UUID_COLUMN_BITMASK = 2048L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Group toModel(GroupSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Group model = new GroupImpl();

		model.setUuid(soapModel.getUuid());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setCreatorUserId(soapModel.getCreatorUserId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setParentGroupId(soapModel.getParentGroupId());
		model.setLiveGroupId(soapModel.getLiveGroupId());
		model.setTreePath(soapModel.getTreePath());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setType(soapModel.getType());
		model.setTypeSettings(soapModel.getTypeSettings());
		model.setFriendlyURL(soapModel.getFriendlyURL());
		model.setSite(soapModel.getSite());
		model.setActive(soapModel.getActive());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Group> toModels(GroupSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Group> models = new ArrayList<Group>(soapModels.length);

		for (GroupSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_GROUPS_ORGS_NAME = "Groups_Orgs";
	public static final Object[][] MAPPING_TABLE_GROUPS_ORGS_COLUMNS = {
			{ "groupId", Types.BIGINT },
			{ "organizationId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_GROUPS_ORGS_SQL_CREATE = "create table Groups_Orgs (groupId LONG not null,organizationId LONG not null,primary key (groupId, organizationId))";
	public static final boolean FINDER_CACHE_ENABLED_GROUPS_ORGS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Groups_Orgs"), true);
	public static final String MAPPING_TABLE_GROUPS_ROLES_NAME = "Groups_Roles";
	public static final Object[][] MAPPING_TABLE_GROUPS_ROLES_COLUMNS = {
			{ "groupId", Types.BIGINT },
			{ "roleId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_GROUPS_ROLES_SQL_CREATE = "create table Groups_Roles (groupId LONG not null,roleId LONG not null,primary key (groupId, roleId))";
	public static final boolean FINDER_CACHE_ENABLED_GROUPS_ROLES = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Groups_Roles"), true);
	public static final String MAPPING_TABLE_GROUPS_USERGROUPS_NAME = "Groups_UserGroups";
	public static final Object[][] MAPPING_TABLE_GROUPS_USERGROUPS_COLUMNS = {
			{ "groupId", Types.BIGINT },
			{ "userGroupId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_GROUPS_USERGROUPS_SQL_CREATE = "create table Groups_UserGroups (groupId LONG not null,userGroupId LONG not null,primary key (groupId, userGroupId))";
	public static final boolean FINDER_CACHE_ENABLED_GROUPS_USERGROUPS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Groups_UserGroups"), true);
	public static final String MAPPING_TABLE_USERS_GROUPS_NAME = "Users_Groups";
	public static final Object[][] MAPPING_TABLE_USERS_GROUPS_COLUMNS = {
			{ "userId", Types.BIGINT },
			{ "groupId", Types.BIGINT }
		};
	public static final String MAPPING_TABLE_USERS_GROUPS_SQL_CREATE = "create table Users_Groups (userId LONG not null,groupId LONG not null,primary key (userId, groupId))";
	public static final boolean FINDER_CACHE_ENABLED_USERS_GROUPS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Users_Groups"), true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Group"));

	public GroupModelImpl() {
	}

	public long getPrimaryKey() {
		return _groupId;
	}

	public void setPrimaryKey(long primaryKey) {
		setGroupId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return _groupId;
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return Group.class;
	}

	public String getModelClassName() {
		return Group.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("creatorUserId", getCreatorUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("parentGroupId", getParentGroupId());
		attributes.put("liveGroupId", getLiveGroupId());
		attributes.put("treePath", getTreePath());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("friendlyURL", getFriendlyURL());
		attributes.put("site", getSite());
		attributes.put("active", getActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long creatorUserId = (Long)attributes.get("creatorUserId");

		if (creatorUserId != null) {
			setCreatorUserId(creatorUserId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long parentGroupId = (Long)attributes.get("parentGroupId");

		if (parentGroupId != null) {
			setParentGroupId(parentGroupId);
		}

		Long liveGroupId = (Long)attributes.get("liveGroupId");

		if (liveGroupId != null) {
			setLiveGroupId(liveGroupId);
		}

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		String friendlyURL = (String)attributes.get("friendlyURL");

		if (friendlyURL != null) {
			setFriendlyURL(friendlyURL);
		}

		Boolean site = (Boolean)attributes.get("site");

		if (site != null) {
			setSite(site);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
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
	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

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
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public void setCreatorUserId(long creatorUserId) {
		_creatorUserId = creatorUserId;
	}

	public String getCreatorUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getCreatorUserId(), "uuid",
			_creatorUserUuid);
	}

	public void setCreatorUserUuid(String creatorUserUuid) {
		_creatorUserUuid = creatorUserUuid;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	public long getParentGroupId() {
		return _parentGroupId;
	}

	public void setParentGroupId(long parentGroupId) {
		_columnBitmask |= PARENTGROUPID_COLUMN_BITMASK;

		if (!_setOriginalParentGroupId) {
			_setOriginalParentGroupId = true;

			_originalParentGroupId = _parentGroupId;
		}

		_parentGroupId = parentGroupId;
	}

	public long getOriginalParentGroupId() {
		return _originalParentGroupId;
	}

	@JSON
	public long getLiveGroupId() {
		return _liveGroupId;
	}

	public void setLiveGroupId(long liveGroupId) {
		_columnBitmask |= LIVEGROUPID_COLUMN_BITMASK;

		if (!_setOriginalLiveGroupId) {
			_setOriginalLiveGroupId = true;

			_originalLiveGroupId = _liveGroupId;
		}

		_liveGroupId = liveGroupId;
	}

	public long getOriginalLiveGroupId() {
		return _originalLiveGroupId;
	}

	@JSON
	public String getTreePath() {
		if (_treePath == null) {
			return StringPool.BLANK;
		}
		else {
			return _treePath;
		}
	}

	public void setTreePath(String treePath) {
		_treePath = treePath;
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
		_columnBitmask = -1L;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
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
	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_columnBitmask |= TYPE_COLUMN_BITMASK;

		if (!_setOriginalType) {
			_setOriginalType = true;

			_originalType = _type;
		}

		_type = type;
	}

	public int getOriginalType() {
		return _originalType;
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
	public String getFriendlyURL() {
		if (_friendlyURL == null) {
			return StringPool.BLANK;
		}
		else {
			return _friendlyURL;
		}
	}

	public void setFriendlyURL(String friendlyURL) {
		_columnBitmask |= FRIENDLYURL_COLUMN_BITMASK;

		if (_originalFriendlyURL == null) {
			_originalFriendlyURL = _friendlyURL;
		}

		_friendlyURL = friendlyURL;
	}

	public String getOriginalFriendlyURL() {
		return GetterUtil.getString(_originalFriendlyURL);
	}

	@JSON
	public boolean getSite() {
		return _site;
	}

	public boolean isSite() {
		return _site;
	}

	public void setSite(boolean site) {
		_columnBitmask |= SITE_COLUMN_BITMASK;

		if (!_setOriginalSite) {
			_setOriginalSite = true;

			_originalSite = _site;
		}

		_site = site;
	}

	public boolean getOriginalSite() {
		return _originalSite;
	}

	@JSON
	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_columnBitmask |= ACTIVE_COLUMN_BITMASK;

		if (!_setOriginalActive) {
			_setOriginalActive = true;

			_originalActive = _active;
		}

		_active = active;
	}

	public boolean getOriginalActive() {
		return _originalActive;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			Group.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Group toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Group)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		GroupImpl groupImpl = new GroupImpl();

		groupImpl.setUuid(getUuid());
		groupImpl.setGroupId(getGroupId());
		groupImpl.setCompanyId(getCompanyId());
		groupImpl.setCreatorUserId(getCreatorUserId());
		groupImpl.setClassNameId(getClassNameId());
		groupImpl.setClassPK(getClassPK());
		groupImpl.setParentGroupId(getParentGroupId());
		groupImpl.setLiveGroupId(getLiveGroupId());
		groupImpl.setTreePath(getTreePath());
		groupImpl.setName(getName());
		groupImpl.setDescription(getDescription());
		groupImpl.setType(getType());
		groupImpl.setTypeSettings(getTypeSettings());
		groupImpl.setFriendlyURL(getFriendlyURL());
		groupImpl.setSite(getSite());
		groupImpl.setActive(getActive());

		groupImpl.resetOriginalValues();

		return groupImpl;
	}

	public int compareTo(Group group) {
		int value = 0;

		value = getName().compareToIgnoreCase(group.getName());

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

		Group group = null;

		try {
			group = (Group)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = group.getPrimaryKey();

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
		GroupModelImpl groupModelImpl = this;

		groupModelImpl._originalUuid = groupModelImpl._uuid;

		groupModelImpl._originalGroupId = groupModelImpl._groupId;

		groupModelImpl._setOriginalGroupId = false;

		groupModelImpl._originalCompanyId = groupModelImpl._companyId;

		groupModelImpl._setOriginalCompanyId = false;

		groupModelImpl._originalClassNameId = groupModelImpl._classNameId;

		groupModelImpl._setOriginalClassNameId = false;

		groupModelImpl._originalClassPK = groupModelImpl._classPK;

		groupModelImpl._setOriginalClassPK = false;

		groupModelImpl._originalParentGroupId = groupModelImpl._parentGroupId;

		groupModelImpl._setOriginalParentGroupId = false;

		groupModelImpl._originalLiveGroupId = groupModelImpl._liveGroupId;

		groupModelImpl._setOriginalLiveGroupId = false;

		groupModelImpl._originalName = groupModelImpl._name;

		groupModelImpl._originalType = groupModelImpl._type;

		groupModelImpl._setOriginalType = false;

		groupModelImpl._originalFriendlyURL = groupModelImpl._friendlyURL;

		groupModelImpl._originalSite = groupModelImpl._site;

		groupModelImpl._setOriginalSite = false;

		groupModelImpl._originalActive = groupModelImpl._active;

		groupModelImpl._setOriginalActive = false;

		groupModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Group> toCacheModel() {
		GroupCacheModel groupCacheModel = new GroupCacheModel();

		groupCacheModel.uuid = getUuid();

		String uuid = groupCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			groupCacheModel.uuid = null;
		}

		groupCacheModel.groupId = getGroupId();

		groupCacheModel.companyId = getCompanyId();

		groupCacheModel.creatorUserId = getCreatorUserId();

		groupCacheModel.classNameId = getClassNameId();

		groupCacheModel.classPK = getClassPK();

		groupCacheModel.parentGroupId = getParentGroupId();

		groupCacheModel.liveGroupId = getLiveGroupId();

		groupCacheModel.treePath = getTreePath();

		String treePath = groupCacheModel.treePath;

		if ((treePath != null) && (treePath.length() == 0)) {
			groupCacheModel.treePath = null;
		}

		groupCacheModel.name = getName();

		String name = groupCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			groupCacheModel.name = null;
		}

		groupCacheModel.description = getDescription();

		String description = groupCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			groupCacheModel.description = null;
		}

		groupCacheModel.type = getType();

		groupCacheModel.typeSettings = getTypeSettings();

		String typeSettings = groupCacheModel.typeSettings;

		if ((typeSettings != null) && (typeSettings.length() == 0)) {
			groupCacheModel.typeSettings = null;
		}

		groupCacheModel.friendlyURL = getFriendlyURL();

		String friendlyURL = groupCacheModel.friendlyURL;

		if ((friendlyURL != null) && (friendlyURL.length() == 0)) {
			groupCacheModel.friendlyURL = null;
		}

		groupCacheModel.site = getSite();

		groupCacheModel.active = getActive();

		return groupCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", creatorUserId=");
		sb.append(getCreatorUserId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", parentGroupId=");
		sb.append(getParentGroupId());
		sb.append(", liveGroupId=");
		sb.append(getLiveGroupId());
		sb.append(", treePath=");
		sb.append(getTreePath());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", typeSettings=");
		sb.append(getTypeSettings());
		sb.append(", friendlyURL=");
		sb.append(getFriendlyURL());
		sb.append(", site=");
		sb.append(getSite());
		sb.append(", active=");
		sb.append(getActive());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Group");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
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
			"<column><column-name>creatorUserId</column-name><column-value><![CDATA[");
		sb.append(getCreatorUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentGroupId</column-name><column-value><![CDATA[");
		sb.append(getParentGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>liveGroupId</column-name><column-value><![CDATA[");
		sb.append(getLiveGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>treePath</column-name><column-value><![CDATA[");
		sb.append(getTreePath());
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
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeSettings</column-name><column-value><![CDATA[");
		sb.append(getTypeSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>friendlyURL</column-name><column-value><![CDATA[");
		sb.append(getFriendlyURL());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>site</column-name><column-value><![CDATA[");
		sb.append(getSite());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>active</column-name><column-value><![CDATA[");
		sb.append(getActive());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Group.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] { Group.class };
	private String _uuid;
	private String _originalUuid;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _creatorUserId;
	private String _creatorUserUuid;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _parentGroupId;
	private long _originalParentGroupId;
	private boolean _setOriginalParentGroupId;
	private long _liveGroupId;
	private long _originalLiveGroupId;
	private boolean _setOriginalLiveGroupId;
	private String _treePath;
	private String _name;
	private String _originalName;
	private String _description;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private String _typeSettings;
	private String _friendlyURL;
	private String _originalFriendlyURL;
	private boolean _site;
	private boolean _originalSite;
	private boolean _setOriginalSite;
	private boolean _active;
	private boolean _originalActive;
	private boolean _setOriginalActive;
	private long _columnBitmask;
	private Group _escapedModel;
}