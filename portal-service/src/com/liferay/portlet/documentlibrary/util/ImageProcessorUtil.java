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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLProcessorConstants;

import java.io.InputStream;

import java.util.Set;

/**
 * @author Sergio González
 */
public class ImageProcessorUtil {

	/**
	 * @deprecated
	 */
	public static void cleanUp(FileEntry fileEntry) {
		getImageProcessor().cleanUp(fileEntry);
	}

	/**
	 * @deprecated
	 */
	public static void cleanUp(FileVersion fileVersion) {
		getImageProcessor().cleanUp(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static void generateImages(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		getImageProcessor().generateImages(
			sourceFileVersion, destinationFileVersion);
	}

	/**
	 * @deprecated
	 */
	public static Set<String> getImageMimeTypes() {
		return getImageProcessor().getImageMimeTypes();
	}

	/**
	 * @deprecated
	 */
	public static ImageProcessor getImageProcessor() {
		return _imageProcessor;
	}

	/**
	 * @deprecated
	 */
	public static InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return getImageProcessor().getPreviewAsStream(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static long getPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return getImageProcessor().getPreviewFileSize(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static String getPreviewType(FileVersion fileVersion) {
		return getImageProcessor().getPreviewType(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static InputStream getThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		return getImageProcessor().getThumbnailAsStream(fileVersion, index);
	}

	/**
	 * @deprecated
	 */
	public static long getThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return getImageProcessor().getThumbnailFileSize(fileVersion, index);
	}

	/**
	 * @deprecated
	 */
	public static String getThumbnailType(FileVersion fileVersion) {
		return getImageProcessor().getThumbnailType(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static boolean hasImages(FileVersion fileVersion) {
		return getImageProcessor().hasImages(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static boolean isImageSupported(FileVersion fileVersion) {
		return getImageProcessor().isImageSupported(fileVersion);
	}

	/**
	 * @deprecated
	 */
	public static boolean isImageSupported(String mimeType) {
		return getImageProcessor().isImageSupported(mimeType);
	}

	/**
	 * @deprecated
	 */
	public static boolean isSupported(String mimeType) {
		return getImageProcessor().isSupported(mimeType);
	}

	/**
	 * @deprecated
	 */
	public static void storeThumbnail(
			long companyId, long groupId, long fileEntryId, long fileVersionId,
			long custom1ImageId, long custom2ImageId, InputStream is,
			String type)
		throws Exception {

		getImageProcessor().storeThumbnail(
			companyId, groupId, fileEntryId, fileVersionId, custom1ImageId,
			custom2ImageId, is, type);
	}

	/**
	 * @deprecated
	 */
	public static void trigger(
		FileVersion sourceFileVersion, FileVersion destinationFileVersion) {

		getImageProcessor().trigger(sourceFileVersion, destinationFileVersion);
	}

	/**
	 * @deprecated
	 */
	public void setImageProcessor(ImageProcessor imageProcessor) {
		_imageProcessor = imageProcessor;
	}

	private static ImageProcessor _imageProcessor =
		(ImageProcessor)DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.IMAGE_PROCESSOR);

}