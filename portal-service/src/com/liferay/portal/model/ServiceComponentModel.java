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
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the ServiceComponent service. Represents a row in the &quot;ServiceComponent&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portal.model.impl.ServiceComponentModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portal.model.impl.ServiceComponentImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponent
 * @see com.liferay.portal.model.impl.ServiceComponentImpl
 * @see com.liferay.portal.model.impl.ServiceComponentModelImpl
 * @generated
 */
public interface ServiceComponentModel extends BaseModel<ServiceComponent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a service component model instance should use the {@link ServiceComponent} interface instead.
	 */

	/**
	 * Returns the primary key of this service component.
	 *
	 * @return the primary key of this service component
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this service component.
	 *
	 * @param primaryKey the primary key of this service component
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the service component ID of this service component.
	 *
	 * @return the service component ID of this service component
	 */
	public long getServiceComponentId();

	/**
	 * Sets the service component ID of this service component.
	 *
	 * @param serviceComponentId the service component ID of this service component
	 */
	public void setServiceComponentId(long serviceComponentId);

	/**
	 * Returns the build namespace of this service component.
	 *
	 * @return the build namespace of this service component
	 */
	@AutoEscape
	public String getBuildNamespace();

	/**
	 * Sets the build namespace of this service component.
	 *
	 * @param buildNamespace the build namespace of this service component
	 */
	public void setBuildNamespace(String buildNamespace);

	/**
	 * Returns the build number of this service component.
	 *
	 * @return the build number of this service component
	 */
	public long getBuildNumber();

	/**
	 * Sets the build number of this service component.
	 *
	 * @param buildNumber the build number of this service component
	 */
	public void setBuildNumber(long buildNumber);

	/**
	 * Returns the build date of this service component.
	 *
	 * @return the build date of this service component
	 */
	public long getBuildDate();

	/**
	 * Sets the build date of this service component.
	 *
	 * @param buildDate the build date of this service component
	 */
	public void setBuildDate(long buildDate);

	/**
	 * Returns the data of this service component.
	 *
	 * @return the data of this service component
	 */
	@AutoEscape
	public String getData();

	/**
	 * Sets the data of this service component.
	 *
	 * @param data the data of this service component
	 */
	public void setData(String data);

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

	public int compareTo(ServiceComponent serviceComponent);

	public int hashCode();

	public CacheModel<ServiceComponent> toCacheModel();

	public ServiceComponent toEscapedModel();

	public ServiceComponent toUnescapedModel();

	public String toString();

	public String toXmlString();
}