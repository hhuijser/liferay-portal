/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.messaging;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.service.persistence.UserFinderImpl;
import com.liferay.portal.util.PropsValues;

/**
 * @author Eric Chin
 */
public class UserCacheMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		if (PropsValues.VALUE_OBJECT_FINDER_CACHE_REBUILD_INTERVAL_ENABLED) {
			FinderCacheUtil.clearCache(
				UserFinderImpl.FINDER_COUNT_BY_KEYWORDS_CACHE_NAME);
		}
	}

}