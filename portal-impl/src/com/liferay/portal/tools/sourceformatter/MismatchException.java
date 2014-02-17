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

package com.liferay.portal.tools.sourceformatter;

import java.io.File;

/**
 * @author André de Oliveira
 */
class MismatchException extends RuntimeException {

	MismatchException(File file, String original, String formatted) {
		_file = file;
		_original = original;
		_formatted = formatted;
	}

	File getFile() {

		return _file;
	}

	String getFormatted() {

		return _formatted;
	}

	String getOriginal() {

		return _original;
	}

	private final File _file;
	private final String _formatted;
	private final String _original;

}