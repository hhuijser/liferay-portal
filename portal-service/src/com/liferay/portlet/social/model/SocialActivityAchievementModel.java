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

package com.liferay.portlet.social.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the SocialActivityAchievement service. Represents a row in the &quot;SocialActivityAchievement&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.social.model.impl.SocialActivityAchievementImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievement
 * @see com.liferay.portlet.social.model.impl.SocialActivityAchievementImpl
 * @see com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl
 * @generated
 */
public interface SocialActivityAchievementModel extends BaseModel<SocialActivityAchievement> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a social activity achievement model instance should use the {@link SocialActivityAchievement} interface instead.
	 */

	/**
	 * Returns the primary key of this social activity achievement.
	 *
	 * @return the primary key of this social activity achievement
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this social activity achievement.
	 *
	 * @param primaryKey the primary key of this social activity achievement
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the activity achievement ID of this social activity achievement.
	 *
	 * @return the activity achievement ID of this social activity achievement
	 */
	public long getActivityAchievementId();

	/**
	 * Sets the activity achievement ID of this social activity achievement.
	 *
	 * @param activityAchievementId the activity achievement ID of this social activity achievement
	 */
	public void setActivityAchievementId(long activityAchievementId);

	/**
	 * Returns the group ID of this social activity achievement.
	 *
	 * @return the group ID of this social activity achievement
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this social activity achievement.
	 *
	 * @param groupId the group ID of this social activity achievement
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this social activity achievement.
	 *
	 * @return the company ID of this social activity achievement
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this social activity achievement.
	 *
	 * @param companyId the company ID of this social activity achievement
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this social activity achievement.
	 *
	 * @return the user ID of this social activity achievement
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this social activity achievement.
	 *
	 * @param userId the user ID of this social activity achievement
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this social activity achievement.
	 *
	 * @return the user uuid of this social activity achievement
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this social activity achievement.
	 *
	 * @param userUuid the user uuid of this social activity achievement
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the create date of this social activity achievement.
	 *
	 * @return the create date of this social activity achievement
	 */
	public long getCreateDate();

	/**
	 * Sets the create date of this social activity achievement.
	 *
	 * @param createDate the create date of this social activity achievement
	 */
	public void setCreateDate(long createDate);

	/**
	 * Returns the name of this social activity achievement.
	 *
	 * @return the name of this social activity achievement
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this social activity achievement.
	 *
	 * @param name the name of this social activity achievement
	 */
	public void setName(String name);

	/**
	 * Returns the first in group of this social activity achievement.
	 *
	 * @return the first in group of this social activity achievement
	 */
	public boolean getFirstInGroup();

	/**
	 * Returns <code>true</code> if this social activity achievement is first in group.
	 *
	 * @return <code>true</code> if this social activity achievement is first in group; <code>false</code> otherwise
	 */
	public boolean isFirstInGroup();

	/**
	 * Sets whether this social activity achievement is first in group.
	 *
	 * @param firstInGroup the first in group of this social activity achievement
	 */
	public void setFirstInGroup(boolean firstInGroup);

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

	public int compareTo(SocialActivityAchievement socialActivityAchievement);

	public int hashCode();

	public CacheModel<SocialActivityAchievement> toCacheModel();

	public SocialActivityAchievement toEscapedModel();

	public SocialActivityAchievement toUnescapedModel();

	public String toString();

	public String toXmlString();
}