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

package com.liferay.portal.kernel.servlet;

/**
 * @author Brian Wing Shun Chan
 */
public interface ServletResponseConstants {

	public static final int
		SC_A_FILE_WITH_THE_SAME_NAME_IS_ALREADY_AVAILABLE = 201;

	public static final int SC_AN_INTERNAL_SERVER_ERROR_OCCURED = 209;

	public static final int SC_AN_UNEXPECTED_ERROR_OCCURRED = 203;

	public static final int SC_AUDIO_PREVIEW_DISABLED_EXCEPTION = 212;

	public static final int SC_DUPLICATE_FILE_EXCEPTION = 490;

	public static final int SC_FILE_CUSTOM_EXCEPTION = 499;

	public static final int SC_FILE_EXTENSION_EXCEPTION = 491;

	public static final int SC_FILE_NAME_EXCEPTION = 492;

	public static final int SC_FILE_SIZE_EXCEEDS_UPLOAD_LIMIT = 208;

	public static final int SC_FILE_SIZE_EXCEPTION = 493;

	public static final int SC_FOLDER_ALREADY_EXISTS = 101;

	public static final int SC_INVALID_FILE = 202;

	public static final int SC_INVALID_FOLDER_NAME = 102;

	public static final int SC_PLEASE_ENTER_A_VALID_FILE_NAME = 206;

	public static final int SC_PLEASE_ENTER_A_VALID_IMAGE_NAME = 205;

	public static final int SC_UNKNOWN_ERROR_CREATING_FOLDER = 110;

	public static final int SC_VIDEO_PREVIEW_DISABLED_EXCEPTION = 211;

	public static final int
		SC_YOU_CANNOT_UPLOAD_A_FILE_IF_CATEGORY_IS_REQUIRED = 210;

	public static final int SC_YOU_CANNOT_UPLOAD_INTO_THE_ROOT_FOLDER = 204;

	public static final int
		SC_YOU_DO_NOT_HAVE_THE_PERMISSION_TO_UPLOAD_TO_THIS_FOLDER = 207;

	public static final int
		SC_YOU_HAVE_NO_PERMISSIONS_TO_CREATE_THE_FOLDER = 103;

}