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

package com.liferay.portal.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the UserIdMapper service. Represents a row in the &quot;UserIdMapper&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.UserIdMapperModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.UserIdMapperImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapper
 * @see com.liferay.portal.model.impl.UserIdMapperImpl
 * @see com.liferay.portal.model.impl.UserIdMapperModelImpl
 * @generated
 */
public interface UserIdMapperModel extends BaseModel<UserIdMapper> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a user ID mapper model instance should use the {@link UserIdMapper} interface instead.
	 */

	/**
	 * Returns the primary key of this user ID mapper.
	 *
	 * @return the primary key of this user ID mapper
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this user ID mapper.
	 *
	 * @param primaryKey the primary key of this user ID mapper
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the user ID mapper ID of this user ID mapper.
	 *
	 * @return the user ID mapper ID of this user ID mapper
	 */
	public long getUserIdMapperId();

	/**
	 * Sets the user ID mapper ID of this user ID mapper.
	 *
	 * @param userIdMapperId the user ID mapper ID of this user ID mapper
	 */
	public void setUserIdMapperId(long userIdMapperId);

	/**
	 * Returns the user ID of this user ID mapper.
	 *
	 * @return the user ID of this user ID mapper
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this user ID mapper.
	 *
	 * @param userId the user ID of this user ID mapper
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this user ID mapper.
	 *
	 * @return the user uuid of this user ID mapper
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this user ID mapper.
	 *
	 * @param userUuid the user uuid of this user ID mapper
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the type of this user ID mapper.
	 *
	 * @return the type of this user ID mapper
	 */
	@AutoEscape
	public String getType();

	/**
	 * Sets the type of this user ID mapper.
	 *
	 * @param type the type of this user ID mapper
	 */
	public void setType(String type);

	/**
	 * Returns the description of this user ID mapper.
	 *
	 * @return the description of this user ID mapper
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this user ID mapper.
	 *
	 * @param description the description of this user ID mapper
	 */
	public void setDescription(String description);

	/**
	 * Returns the external user ID of this user ID mapper.
	 *
	 * @return the external user ID of this user ID mapper
	 */
	@AutoEscape
	public String getExternalUserId();

	/**
	 * Sets the external user ID of this user ID mapper.
	 *
	 * @param externalUserId the external user ID of this user ID mapper
	 */
	public void setExternalUserId(String externalUserId);

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

	public int compareTo(UserIdMapper userIdMapper);

	public int hashCode();

	public CacheModel<UserIdMapper> toCacheModel();

	public UserIdMapper toEscapedModel();

	public UserIdMapper toUnescapedModel();

	public String toString();

	public String toXmlString();
}