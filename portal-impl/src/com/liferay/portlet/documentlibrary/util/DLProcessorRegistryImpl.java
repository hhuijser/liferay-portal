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

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLProcessorConstants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mika Koivisto
 */
public class DLProcessorRegistryImpl implements DLProcessorRegistry {

	public void cleanUp(FileEntry fileEntry) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		for (DLProcessor dlProcessor : _dlProcessors.values()) {
			dlProcessor.cleanUp(fileEntry);
		}
	}

	public void cleanUp(FileVersion fileVersion) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		for (DLProcessor dlProcessor : _dlProcessors.values()) {
			dlProcessor.cleanUp(fileVersion);
		}
	}

	public void exportGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement)
		throws Exception {

		if ((fileEntry == null) || (fileEntry.getSize() == 0)) {
			return;
		}

		FileVersion latestFileVersion = _getLatestFileVersion(fileEntry);

		if (latestFileVersion == null) {
			return;
		}

		for (DLProcessor dlProcessor : _dlProcessors.values()) {
			if (dlProcessor.isSupported(latestFileVersion)) {
				dlProcessor.exportGeneratedFiles(
					portletDataContext, fileEntry, fileEntryElement);
			}
		}
	}

	public DLProcessor getDLProcessor(String dlProcessorType) {
		return _dlProcessors.get(dlProcessorType);
	}

	public void importGeneratedFiles(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement)
		throws Exception {

		if ((importedFileEntry == null) || (importedFileEntry.getSize() == 0)) {
			return;
		}

		FileVersion fileVersion = importedFileEntry.getFileVersion();

		if (fileVersion == null) {
			return;
		}

		for (DLProcessor dlProcessor : _dlProcessors.values()) {
			if (dlProcessor.isSupported(fileVersion)) {
				dlProcessor.importGeneratedFiles(
					portletDataContext, fileEntry, importedFileEntry,
					fileEntryElement);
			}
		}
	}

	public void init() throws Exception {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (String dlProcessorClassName : _DL_FILE_ENTRY_PROCESSORS) {
			DLProcessor dlProcessor = (DLProcessor)InstanceFactory.newInstance(
				classLoader, dlProcessorClassName);

			register(dlProcessor);
		}
	}

	public void register(DLProcessor dlProcessor) {
		String type = getType(dlProcessor);

		_dlProcessors.put(type, dlProcessor);
	}

	public void trigger(FileEntry fileEntry, FileVersion fileVersion) {
		if (!DLProcessorThreadLocal.isEnabled()) {
			return;
		}

		if ((fileEntry == null) || (fileEntry.getSize() == 0)) {
			return;
		}

		FileVersion latestFileVersion = _getLatestFileVersion(fileEntry);

		if (latestFileVersion == null) {
			return;
		}

		for (DLProcessor dlProcessor : _dlProcessors.values()) {
			if (dlProcessor.isSupported(latestFileVersion)) {
				dlProcessor.trigger(fileVersion, latestFileVersion);
			}
		}
	}

	public void unregister(DLProcessor dlProcessor) {
		String type = getType(dlProcessor);

		_dlProcessors.remove(type);
	}

	protected String getType(DLProcessor dlProcessor) {
		String type = null;

		if (dlProcessor instanceof AudioProcessor) {
			type = DLProcessorConstants.AUDIO_PROCESSOR;
		}
		else if (dlProcessor instanceof ImageProcessor) {
			type = DLProcessorConstants.IMAGE_PROCESSOR;
		}
		else if (dlProcessor instanceof PDFProcessor) {
			type = DLProcessorConstants.PDF_PROCESSOR;
		}
		else if (dlProcessor instanceof RawMetadataProcessor) {
			type = DLProcessorConstants.RAW_METADATA_PROCESSOR;
		}
		else if (dlProcessor instanceof VideoProcessor) {
			type = DLProcessorConstants.VIDEO_PROCESSOR;
		}

		return type;
	}

	private FileVersion _getLatestFileVersion(FileEntry fileEntry) {
		FileVersion latestFileVersion = null;

		try {
			if (fileEntry.getModel() instanceof DLFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

				latestFileVersion = new LiferayFileVersion(
					dlFileEntry.getLatestFileVersion(false));
			}
			else {
				latestFileVersion = fileEntry.getLatestFileVersion();
			}

			return latestFileVersion;
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	private static final String[] _DL_FILE_ENTRY_PROCESSORS =
		PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PROCESSORS);

	private static Log _log = LogFactoryUtil.getLog(
		DLProcessorRegistryImpl.class);

	private Map<String, DLProcessor> _dlProcessors =
		new ConcurrentHashMap<String, DLProcessor>();

}