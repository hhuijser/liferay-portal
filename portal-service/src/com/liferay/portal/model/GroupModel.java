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

package com.liferay.portal.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the Group service. Represents a row in the &quot;Group_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.GroupModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.GroupImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Group
 * @see com.liferay.portal.model.impl.GroupImpl
 * @see com.liferay.portal.model.impl.GroupModelImpl
 * @generated
 */
public interface GroupModel extends AttachedModel, BaseModel<Group> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a group model instance should use the {@link Group} interface instead.
	 */

	/**
	 * Returns the primary key of this group.
	 *
	 * @return the primary key of this group
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this group.
	 *
	 * @param primaryKey the primary key of this group
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this group.
	 *
	 * @return the uuid of this group
	 */
	@AutoEscape
	public String getUuid();

	/**
	 * Sets the uuid of this group.
	 *
	 * @param uuid the uuid of this group
	 */
	public void setUuid(String uuid);

	/**
	 * Returns the group ID of this group.
	 *
	 * @return the group ID of this group
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this group.
	 *
	 * @param groupId the group ID of this group
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this group.
	 *
	 * @return the company ID of this group
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this group.
	 *
	 * @param companyId the company ID of this group
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the creator user ID of this group.
	 *
	 * @return the creator user ID of this group
	 */
	public long getCreatorUserId();

	/**
	 * Sets the creator user ID of this group.
	 *
	 * @param creatorUserId the creator user ID of this group
	 */
	public void setCreatorUserId(long creatorUserId);

	/**
	 * Returns the creator user uuid of this group.
	 *
	 * @return the creator user uuid of this group
	 * @throws SystemException if a system exception occurred
	 */
	public String getCreatorUserUuid() throws SystemException;

	/**
	 * Sets the creator user uuid of this group.
	 *
	 * @param creatorUserUuid the creator user uuid of this group
	 */
	public void setCreatorUserUuid(String creatorUserUuid);

	/**
	 * Returns the fully qualified class name of this group.
	 *
	 * @return the fully qualified class name of this group
	 */
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this group.
	 *
	 * @return the class name ID of this group
	 */
	public long getClassNameId();

	/**
	 * Sets the class name ID of this group.
	 *
	 * @param classNameId the class name ID of this group
	 */
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class p k of this group.
	 *
	 * @return the class p k of this group
	 */
	public long getClassPK();

	/**
	 * Sets the class p k of this group.
	 *
	 * @param classPK the class p k of this group
	 */
	public void setClassPK(long classPK);

	/**
	 * Returns the parent group ID of this group.
	 *
	 * @return the parent group ID of this group
	 */
	public long getParentGroupId();

	/**
	 * Sets the parent group ID of this group.
	 *
	 * @param parentGroupId the parent group ID of this group
	 */
	public void setParentGroupId(long parentGroupId);

	/**
	 * Returns the live group ID of this group.
	 *
	 * @return the live group ID of this group
	 */
	public long getLiveGroupId();

	/**
	 * Sets the live group ID of this group.
	 *
	 * @param liveGroupId the live group ID of this group
	 */
	public void setLiveGroupId(long liveGroupId);

	/**
	 * Returns the tree path of this group.
	 *
	 * @return the tree path of this group
	 */
	@AutoEscape
	public String getTreePath();

	/**
	 * Sets the tree path of this group.
	 *
	 * @param treePath the tree path of this group
	 */
	public void setTreePath(String treePath);

	/**
	 * Returns the name of this group.
	 *
	 * @return the name of this group
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this group.
	 *
	 * @param name the name of this group
	 */
	public void setName(String name);

	/**
	 * Returns the description of this group.
	 *
	 * @return the description of this group
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this group.
	 *
	 * @param description the description of this group
	 */
	public void setDescription(String description);

	/**
	 * Returns the type of this group.
	 *
	 * @return the type of this group
	 */
	public int getType();

	/**
	 * Sets the type of this group.
	 *
	 * @param type the type of this group
	 */
	public void setType(int type);

	/**
	 * Returns the type settings of this group.
	 *
	 * @return the type settings of this group
	 */
	@AutoEscape
	public String getTypeSettings();

	/**
	 * Sets the type settings of this group.
	 *
	 * @param typeSettings the type settings of this group
	 */
	public void setTypeSettings(String typeSettings);

	/**
	 * Returns the friendly u r l of this group.
	 *
	 * @return the friendly u r l of this group
	 */
	@AutoEscape
	public String getFriendlyURL();

	/**
	 * Sets the friendly u r l of this group.
	 *
	 * @param friendlyURL the friendly u r l of this group
	 */
	public void setFriendlyURL(String friendlyURL);

	/**
	 * Returns the site of this group.
	 *
	 * @return the site of this group
	 */
	public boolean getSite();

	/**
	 * Returns <code>true</code> if this group is site.
	 *
	 * @return <code>true</code> if this group is site; <code>false</code> otherwise
	 */
	public boolean isSite();

	/**
	 * Sets whether this group is site.
	 *
	 * @param site the site of this group
	 */
	public void setSite(boolean site);

	/**
	 * Returns the active of this group.
	 *
	 * @return the active of this group
	 */
	public boolean getActive();

	/**
	 * Returns <code>true</code> if this group is active.
	 *
	 * @return <code>true</code> if this group is active; <code>false</code> otherwise
	 */
	public boolean isActive();

	/**
	 * Sets whether this group is active.
	 *
	 * @param active the active of this group
	 */
	public void setActive(boolean active);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(Group group);

	public int hashCode();

	public CacheModel<Group> toCacheModel();

	public Group toEscapedModel();

	public Group toUnescapedModel();

	public String toString();

	public String toXmlString();
}