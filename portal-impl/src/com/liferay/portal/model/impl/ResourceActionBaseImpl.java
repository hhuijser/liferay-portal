/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;

/**
 * The extended model base implementation for the ResourceAction service. Represents a row in the &quot;ResourceAction&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ResourceActionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceActionImpl
 * @see com.liferay.portal.model.ResourceAction
 * @generated
 */
@ProviderType
public abstract class ResourceActionBaseImpl extends ResourceActionModelImpl
	implements ResourceAction {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a resource action model instance should use the {@link ResourceAction} interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			ResourceActionLocalServiceUtil.addResourceAction(this);
		}
		else {
			ResourceActionLocalServiceUtil.updateResourceAction(this);
		}
	}
}