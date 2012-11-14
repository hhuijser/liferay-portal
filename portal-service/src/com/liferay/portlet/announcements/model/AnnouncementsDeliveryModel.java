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

package com.liferay.portlet.announcements.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the AnnouncementsDelivery service. Represents a row in the &quot;AnnouncementsDelivery&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDelivery
 * @see com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl
 * @see com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl
 * @generated
 */
public interface AnnouncementsDeliveryModel extends BaseModel<AnnouncementsDelivery> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a announcements delivery model instance should use the {@link AnnouncementsDelivery} interface instead.
	 */

	/**
	 * Returns the primary key of this announcements delivery.
	 *
	 * @return the primary key of this announcements delivery
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this announcements delivery.
	 *
	 * @param primaryKey the primary key of this announcements delivery
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the delivery ID of this announcements delivery.
	 *
	 * @return the delivery ID of this announcements delivery
	 */
	public long getDeliveryId();

	/**
	 * Sets the delivery ID of this announcements delivery.
	 *
	 * @param deliveryId the delivery ID of this announcements delivery
	 */
	public void setDeliveryId(long deliveryId);

	/**
	 * Returns the company ID of this announcements delivery.
	 *
	 * @return the company ID of this announcements delivery
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this announcements delivery.
	 *
	 * @param companyId the company ID of this announcements delivery
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this announcements delivery.
	 *
	 * @return the user ID of this announcements delivery
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this announcements delivery.
	 *
	 * @param userId the user ID of this announcements delivery
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this announcements delivery.
	 *
	 * @return the user uuid of this announcements delivery
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this announcements delivery.
	 *
	 * @param userUuid the user uuid of this announcements delivery
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the type of this announcements delivery.
	 *
	 * @return the type of this announcements delivery
	 */
	@AutoEscape
	public String getType();

	/**
	 * Sets the type of this announcements delivery.
	 *
	 * @param type the type of this announcements delivery
	 */
	public void setType(String type);

	/**
	 * Returns the email of this announcements delivery.
	 *
	 * @return the email of this announcements delivery
	 */
	public boolean getEmail();

	/**
	 * Returns <code>true</code> if this announcements delivery is email.
	 *
	 * @return <code>true</code> if this announcements delivery is email; <code>false</code> otherwise
	 */
	public boolean isEmail();

	/**
	 * Sets whether this announcements delivery is email.
	 *
	 * @param email the email of this announcements delivery
	 */
	public void setEmail(boolean email);

	/**
	 * Returns the sms of this announcements delivery.
	 *
	 * @return the sms of this announcements delivery
	 */
	public boolean getSms();

	/**
	 * Returns <code>true</code> if this announcements delivery is sms.
	 *
	 * @return <code>true</code> if this announcements delivery is sms; <code>false</code> otherwise
	 */
	public boolean isSms();

	/**
	 * Sets whether this announcements delivery is sms.
	 *
	 * @param sms the sms of this announcements delivery
	 */
	public void setSms(boolean sms);

	/**
	 * Returns the website of this announcements delivery.
	 *
	 * @return the website of this announcements delivery
	 */
	public boolean getWebsite();

	/**
	 * Returns <code>true</code> if this announcements delivery is website.
	 *
	 * @return <code>true</code> if this announcements delivery is website; <code>false</code> otherwise
	 */
	public boolean isWebsite();

	/**
	 * Sets whether this announcements delivery is website.
	 *
	 * @param website the website of this announcements delivery
	 */
	public void setWebsite(boolean website);

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

	public int compareTo(AnnouncementsDelivery announcementsDelivery);

	public int hashCode();

	public CacheModel<AnnouncementsDelivery> toCacheModel();

	public AnnouncementsDelivery toEscapedModel();

	public AnnouncementsDelivery toUnescapedModel();

	public String toString();

	public String toXmlString();
}