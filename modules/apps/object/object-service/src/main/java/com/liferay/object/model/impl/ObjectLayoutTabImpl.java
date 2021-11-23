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

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectLayoutBox;

import java.util.List;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectLayoutTabImpl extends ObjectLayoutTabBaseImpl {

	@Override
	public List<ObjectLayoutBox> getObjectLayoutBoxes() {
		return _objectLayoutBoxes;
	}

	@Override
	public void setObjectLayoutBoxes(List<ObjectLayoutBox> objectLayoutBoxes) {
		_objectLayoutBoxes = objectLayoutBoxes;
	}

	private List<ObjectLayoutBox> _objectLayoutBoxes;

}