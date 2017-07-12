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

package com.liferay.exportimport.lar;

import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author     Mate Thurzo
 * @deprecated As of com.liferay.exportimport.service#NEXT-VERSION
 */
@Deprecated
public abstract class BaseStagedModelDataHandler<T extends StagedModel>
	extends com.liferay.exportimport.data.handler.base.
		BaseStagedModelDataHandler<T> {
}