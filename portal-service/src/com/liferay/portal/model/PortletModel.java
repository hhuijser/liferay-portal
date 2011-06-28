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

package com.liferay.portal.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the Portlet service. Represents a row in the &quot;Portlet&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.PortletModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.PortletImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Portlet
 * @see com.liferay.portal.model.impl.PortletImpl
 * @see com.liferay.portal.model.impl.PortletModelImpl
 * @generated
 */
public interface PortletModel extends BaseModel<Portlet> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a portlet model instance should use the {@link Portlet} interface instead.
	 */

	/**
	 * Returns the primary key of this portlet.
	 *
	 * @return the primary key of this portlet
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this portlet.
	 *
	 * @param primaryKey the primary key of this portlet
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the ID of this portlet.
	 *
	 * @return the ID of this portlet
	 */
	public long getId();

	/**
	 * Sets the ID of this portlet.
	 *
	 * @param id the ID of this portlet
	 */
	public void setId(long id);

	/**
	 * Returns the company ID of this portlet.
	 *
	 * @return the company ID of this portlet
	 */
	public long getCompanyId();

	/**
	 * Sets the company ID of this portlet.
	 *
	 * @param companyId the company ID of this portlet
	 */
	public void setCompanyId(long companyId);

	/**
	 * Returns the portlet ID of this portlet.
	 *
	 * @return the portlet ID of this portlet
	 */
	@AutoEscape
	public String getPortletId();

	/**
	 * Sets the portlet ID of this portlet.
	 *
	 * @param portletId the portlet ID of this portlet
	 */
	public void setPortletId(String portletId);

	/**
	 * Returns the roles of this portlet.
	 *
	 * @return the roles of this portlet
	 */
	@AutoEscape
	public String getRoles();

	/**
	 * Sets the roles of this portlet.
	 *
	 * @param roles the roles of this portlet
	 */
	public void setRoles(String roles);

	/**
	 * Returns the active of this portlet.
	 *
	 * @return the active of this portlet
	 */
	public boolean getActive();

	/**
	 * Returns <code>true</code> if this portlet is active.
	 *
	 * @return <code>true</code> if this portlet is active; <code>false</code> otherwise
	 */
	public boolean isActive();

	/**
	 * Sets whether this portlet is active.
	 *
	 * @param active the active of this portlet
	 */
	public void setActive(boolean active);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(Portlet portlet);

	public int hashCode();

	public Portlet toEscapedModel();

	public String toString();

	public String toXmlString();
}