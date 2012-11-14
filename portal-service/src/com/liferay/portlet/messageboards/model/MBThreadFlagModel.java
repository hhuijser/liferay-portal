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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the MBThreadFlag service. Represents a row in the &quot;MBThreadFlag&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.messageboards.model.impl.MBThreadFlagImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlag
 * @see com.liferay.portlet.messageboards.model.impl.MBThreadFlagImpl
 * @see com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl
 * @generated
 */
public interface MBThreadFlagModel extends BaseModel<MBThreadFlag> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a message boards thread flag model instance should use the {@link MBThreadFlag} interface instead.
	 */

	/**
	 * Returns the primary key of this message boards thread flag.
	 *
	 * @return the primary key of this message boards thread flag
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this message boards thread flag.
	 *
	 * @param primaryKey the primary key of this message boards thread flag
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the thread flag ID of this message boards thread flag.
	 *
	 * @return the thread flag ID of this message boards thread flag
	 */
	public long getThreadFlagId();

	/**
	 * Sets the thread flag ID of this message boards thread flag.
	 *
	 * @param threadFlagId the thread flag ID of this message boards thread flag
	 */
	public void setThreadFlagId(long threadFlagId);

	/**
	 * Returns the user ID of this message boards thread flag.
	 *
	 * @return the user ID of this message boards thread flag
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this message boards thread flag.
	 *
	 * @param userId the user ID of this message boards thread flag
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this message boards thread flag.
	 *
	 * @return the user uuid of this message boards thread flag
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this message boards thread flag.
	 *
	 * @param userUuid the user uuid of this message boards thread flag
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the modified date of this message boards thread flag.
	 *
	 * @return the modified date of this message boards thread flag
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this message boards thread flag.
	 *
	 * @param modifiedDate the modified date of this message boards thread flag
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the thread ID of this message boards thread flag.
	 *
	 * @return the thread ID of this message boards thread flag
	 */
	public long getThreadId();

	/**
	 * Sets the thread ID of this message boards thread flag.
	 *
	 * @param threadId the thread ID of this message boards thread flag
	 */
	public void setThreadId(long threadId);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(MBThreadFlag mbThreadFlag);

	public int hashCode();

	public CacheModel<MBThreadFlag> toCacheModel();

	public MBThreadFlag toEscapedModel();

	public MBThreadFlag toUnescapedModel();

	public String toString();

	public String toXmlString();
}