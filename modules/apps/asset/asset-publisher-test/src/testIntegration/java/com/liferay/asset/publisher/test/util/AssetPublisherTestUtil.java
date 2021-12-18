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

package com.liferay.asset.publisher.test.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.petra.string.StringBundler;

/**
 * Provides a utility method to convert an asset entry to XML format so it can
 * be saved in the Asset Publisher's portlet preferences.
 *
 * @author Tamas Molnar
 */
public class AssetPublisherTestUtil {

	public static String getAssetEntryXml(AssetEntry assetEntry) {
		return StringBundler.concat(
			"<?xml version=\"1.0\"?><asset-entry><asset-entry-type>",
			assetEntry.getClassName(), "</asset-entry-type><asset-entry-uuid>",
			assetEntry.getClassUuid(), "</asset-entry-uuid></asset-entry>");
	}

}