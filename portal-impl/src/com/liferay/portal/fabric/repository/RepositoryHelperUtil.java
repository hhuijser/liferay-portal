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

package com.liferay.portal.fabric.repository;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.nio.file.Path;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class RepositoryHelperUtil {

	public static Path getRepositoryFilePath(
		Path repositoryPath, Path remoteFilePath) {

		String name = String.valueOf(remoteFilePath.getFileName());

		int index = name.lastIndexOf(CharPool.PERIOD);

		if (index == -1) {
			return repositoryPath.resolve(
				StringBundler.concat(
					name, StringPool.DASH, System.currentTimeMillis(),
					StringPool.DASH, idGenerator.getAndIncrement()));
		}

		return repositoryPath.resolve(
			StringBundler.concat(
				name.substring(0, index), StringPool.DASH,
				System.currentTimeMillis(), StringPool.DASH,
				idGenerator.getAndIncrement(), name.substring(index)));
	}

	protected static final AtomicLong idGenerator = new AtomicLong();

}