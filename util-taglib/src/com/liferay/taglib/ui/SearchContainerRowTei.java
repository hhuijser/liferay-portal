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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Raymond Augé
 */
public class SearchContainerRowTei extends TagExtraInfo {

	@Override
	public VariableInfo[] getVariableInfo(TagData tagData) {
		String className = tagData.getAttributeString("className");

		String indexVar = GetterUtil.getString(
			tagData.getAttributeString("indexVar"),
			SearchContainerRowTag.DEFAULT_INDEX_VAR);

		String modelVar = GetterUtil.getString(
			tagData.getAttributeString("modelVar"),
			SearchContainerRowTag.DEFAULT_MODEL_VAR);

		String rowVar = GetterUtil.getString(
			tagData.getAttributeString("rowVar"),
			SearchContainerRowTag.DEFAULT_ROW_VAR);

		return new VariableInfo[] {
			new VariableInfo(
				indexVar, Integer.class.getName(), true, VariableInfo.NESTED),
			new VariableInfo(modelVar, className, true, VariableInfo.NESTED),
			new VariableInfo(
				rowVar, ResultRow.class.getName(), true, VariableInfo.NESTED)
		};
	}

}