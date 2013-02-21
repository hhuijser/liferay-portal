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

package com.liferay.portlet.shopping.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the ShoppingItemField service. Represents a row in the &quot;ShoppingItemField&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.portlet.shopping.model.impl.ShoppingItemFieldModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.portlet.shopping.model.impl.ShoppingItemFieldImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemField
 * @see com.liferay.portlet.shopping.model.impl.ShoppingItemFieldImpl
 * @see com.liferay.portlet.shopping.model.impl.ShoppingItemFieldModelImpl
 * @generated
 */
public interface ShoppingItemFieldModel extends BaseModel<ShoppingItemField> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a shopping item field model instance should use the {@link ShoppingItemField} interface instead.
	 */

	/**
	 * Returns the primary key of this shopping item field.
	 *
	 * @return the primary key of this shopping item field
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this shopping item field.
	 *
	 * @param primaryKey the primary key of this shopping item field
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the item field ID of this shopping item field.
	 *
	 * @return the item field ID of this shopping item field
	 */
	public long getItemFieldId();

	/**
	 * Sets the item field ID of this shopping item field.
	 *
	 * @param itemFieldId the item field ID of this shopping item field
	 */
	public void setItemFieldId(long itemFieldId);

	/**
	 * Returns the item ID of this shopping item field.
	 *
	 * @return the item ID of this shopping item field
	 */
	public long getItemId();

	/**
	 * Sets the item ID of this shopping item field.
	 *
	 * @param itemId the item ID of this shopping item field
	 */
	public void setItemId(long itemId);

	/**
	 * Returns the name of this shopping item field.
	 *
	 * @return the name of this shopping item field
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this shopping item field.
	 *
	 * @param name the name of this shopping item field
	 */
	public void setName(String name);

	/**
	 * Returns the values of this shopping item field.
	 *
	 * @return the values of this shopping item field
	 */
	@AutoEscape
	public String getValues();

	/**
	 * Sets the values of this shopping item field.
	 *
	 * @param values the values of this shopping item field
	 */
	public void setValues(String values);

	/**
	 * Returns the description of this shopping item field.
	 *
	 * @return the description of this shopping item field
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this shopping item field.
	 *
	 * @param description the description of this shopping item field
	 */
	public void setDescription(String description);

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

	public int compareTo(ShoppingItemField shoppingItemField);

	public int hashCode();

	public CacheModel<ShoppingItemField> toCacheModel();

	public ShoppingItemField toEscapedModel();

	public ShoppingItemField toUnescapedModel();

	public String toString();

	public String toXmlString();
}