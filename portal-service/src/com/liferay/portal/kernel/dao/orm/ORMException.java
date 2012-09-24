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

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public class ORMException extends RuntimeException {

	public ORMException() {
		super();
	}

	public ORMException(String msg) {
		super(msg);
	}

	public ORMException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ORMException(Throwable cause) {
		super(cause);
	}

}