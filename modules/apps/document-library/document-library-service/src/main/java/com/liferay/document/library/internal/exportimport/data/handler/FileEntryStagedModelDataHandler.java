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

package com.liferay.document.library.internal.exportimport.data.handler;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.document.library.exportimport.data.handler.DLPluggableContentDataHandler;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLProcessorThreadLocal;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.util.RepositoryUtil;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.trash.TrashHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = StagedModelDataHandler.class)
public class FileEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<FileEntry> {

	public static final String[] CLASS_NAMES = {
		DLFileEntry.class.getName(), FileEntry.class.getName(),
		LiferayFileEntry.class.getName()
	};

	@Override
	public void deleteStagedModel(FileEntry fileEntry) throws PortalException {
		_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		FileEntry fileEntry = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (fileEntry != null) {
			deleteStagedModel(fileEntry);
		}
	}

	@Override
	public FileEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		try {
			return _dlAppLocalService.getFileEntryByUuidAndGroupId(
				uuid, groupId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return null;
		}
	}

	@Override
	public List<FileEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DLFileEntry> dlFileEntries =
			_dlFileEntryLocalService.getDLFileEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());

		List<FileEntry> fileEntries = new ArrayList<>();

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			fileEntries.add(new LiferayFileEntry(dlFileEntry));
		}

		return fileEntries;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(FileEntry fileEntry) {
		if (fileEntry.isInTrash()) {
			return _trashHelper.getOriginalTitle(fileEntry.getTitle());
		}

		return fileEntry.getTitle();
	}

	@Override
	public void importStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		boolean dlProcessorEnabled = DLProcessorThreadLocal.isEnabled();

		try {
			DLProcessorThreadLocal.setEnabled(false);

			super.importStagedModel(portletDataContext, fileEntry);
		}
		finally {
			DLProcessorThreadLocal.setEnabled(dlProcessorEnabled);
		}
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, FileEntry stagedModel)
		throws PortletDataException {

		try {
			doRestoreStagedModel(portletDataContext, stagedModel);
		}
		catch (PortletDataException portletDataException) {
			throw portletDataException;
		}
		catch (Exception exception) {
			throw new PortletDataException(exception);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, DLPluggableContentDataHandler.class,
			"(model.class.name=" + FileEntry.class.getName() + ")");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	protected com.liferay.dynamic.data.mapping.storage.DDMFormValues
		deserialize(String content, DDMForm ddmForm) {

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_jsonDDMFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws Exception {

		Element fileEntryElement = portletDataContext.getExportDataElement(
			fileEntry);

		String fileEntryPath = ExportImportPathUtil.getModelPath(fileEntry);

		if (!fileEntry.isDefaultRepository()) {
			Repository repository = _repositoryLocalService.getRepository(
				fileEntry.getRepositoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileEntry, repository,
				PortletDataContext.REFERENCE_TYPE_STRONG);

			long portletRepositoryClassNameId = _portal.getClassNameId(
				PortletRepository.class.getName());

			if (repository.getClassNameId() != portletRepositoryClassNameId) {
				return;
			}
		}

		if (fileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileEntry, fileEntry.getFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		fileEntryElement.addAttribute("fileVersionUuid", fileVersion.getUuid());

		fileEntryElement.addAttribute("version", fileEntry.getVersion());

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		liferayFileEntry.setCachedFileVersion(fileEntry.getFileVersion());

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			InputStream is = null;

			try {
				is = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to retrieve content for file entry " +
							fileEntry.getFileEntryId(),
						exception);
				}
			}

			if (is == null) {
				fileEntryElement.detach();

				return;
			}

			try {
				String binPath = ExportImportPathUtil.getModelPath(
					fileEntry, fileEntry.getVersion());

				portletDataContext.addZipEntry(binPath, is);

				fileEntryElement.addAttribute("bin-path", binPath);
			}
			finally {
				try {
					is.close();
				}
				catch (IOException ioException) {
					_log.error(ioException, ioException);
				}
			}
		}

		for (DLPluggableContentDataHandler dlPluggableContentDataHandler :
				_serviceTrackerList) {

			dlPluggableContentDataHandler.exportContent(
				portletDataContext, fileEntryElement, fileEntry);
		}

		exportMetaData(portletDataContext, fileEntryElement, fileEntry);

		_exportAssetDisplayPage(portletDataContext, fileEntry);

		portletDataContext.addClassedModel(
			fileEntryElement, fileEntryPath, liferayFileEntry,
			DLFileEntry.class);
	}

	@Override
	protected void doImportMissingReference(
		PortletDataContext portletDataContext, String uuid, long groupId,
		long fileEntryId) {

		FileEntry existingFileEntry = fetchMissingReference(uuid, groupId);

		if (existingFileEntry == null) {
			return;
		}

		Map<Long, Long> dlFileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		dlFileEntryIds.put(fileEntryId, existingFileEntry.getFileEntryId());

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		fileEntryIds.put(fileEntryId, existingFileEntry.getFileEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws Exception {

		if (RepositoryUtil.isExternalRepository(fileEntry.getRepositoryId())) {

			// References has been automatically imported, nothing to do here

			return;
		}

		Map<Long, Long> repositoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Repository.class);

		long repositoryId = MapUtil.getLong(
			repositoryIds, fileEntry.getRepositoryId(),
			portletDataContext.getScopeGroupId());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long folderId = MapUtil.getLong(
			folderIds, fileEntry.getFolderId(), fileEntry.getFolderId());

		long[] assetCategoryIds = portletDataContext.getAssetCategoryIds(
			DLFileEntry.class, fileEntry.getFileEntryId());
		String[] assetTagNames = portletDataContext.getAssetTagNames(
			DLFileEntry.class, fileEntry.getFileEntryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntry, DLFileEntry.class);

		serviceContext.setAttribute(
			"sourceFileName", "A." + fileEntry.getExtension());

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		serviceContext.setUserId(userId);

		Element fileEntryElement = portletDataContext.getImportDataElement(
			fileEntry);

		String binPath = fileEntryElement.attributeValue("bin-path");
		String fileVersionUuid = fileEntryElement.attributeValue(
			"fileVersionUuid");
		String version = fileEntryElement.attributeValue("version");

		Serializable validateDDMFormValues = serviceContext.getAttribute(
			"validateDDMFormValues");

		serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);

		InputStream is = null;

		try {
			if (Validator.isNull(binPath) &&
				portletDataContext.isPerformDirectBinaryImport()) {

				try {
					is = FileEntryUtil.getContentStream(fileEntry);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to retrieve content for file entry " +
								fileEntry.getFileEntryId(),
							exception);
					}

					return;
				}
			}
			else {
				is = portletDataContext.getZipEntryAsInputStream(binPath);
			}

			if (is == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No file found for file entry " +
							fileEntry.getFileEntryId());
				}

				return;
			}

			importMetaData(
				portletDataContext, fileEntryElement, fileEntry,
				serviceContext);

			FileEntry importedFileEntry = null;

			if (portletDataContext.isDataStrategyMirror()) {
				FileEntry existingFileEntry = fetchStagedModelByUuidAndGroupId(
					fileEntry.getUuid(), portletDataContext.getScopeGroupId());

				if (existingFileEntry == null) {
					if (portletDataContext.
							isDataStrategyMirrorWithOverwriting()) {

						FileEntry existingTitleFileEntry =
							FileEntryUtil.fetchByR_F_T(
								repositoryId, folderId, fileEntry.getTitle());

						if (existingTitleFileEntry == null) {
							existingTitleFileEntry =
								FileEntryUtil.fetchByR_F_FN(
									repositoryId, folderId,
									fileEntry.getFileName());
						}

						if (existingTitleFileEntry != null) {
							_dlAppLocalService.deleteFileEntry(
								existingTitleFileEntry.getFileEntryId());
						}
					}

					serviceContext.setAttribute(
						"fileVersionUuid", fileVersionUuid);
					serviceContext.setUuid(fileEntry.getUuid());

					String fileEntryTitle =
						_dlFileEntryLocalService.getUniqueTitle(
							portletDataContext.getScopeGroupId(), folderId, 0,
							fileEntry.getTitle(), fileEntry.getExtension());

					importedFileEntry = _dlAppLocalService.addFileEntry(
						userId, repositoryId, folderId, fileEntry.getFileName(),
						fileEntry.getMimeType(), fileEntryTitle,
						fileEntry.getDescription(), null, is,
						fileEntry.getSize(), serviceContext);

					if (fileEntry.isInTrash()) {
						importedFileEntry =
							_dlTrashService.moveFileEntryToTrash(
								importedFileEntry.getFileEntryId());
					}
				}
				else {
					FileVersion latestExistingFileVersion =
						existingFileEntry.getLatestFileVersion(true);

					boolean indexEnabled = serviceContext.isIndexingEnabled();

					boolean deleteFileEntry = false;
					boolean updateFileEntry = false;

					if (!Objects.equals(
							fileVersionUuid,
							latestExistingFileVersion.getUuid())) {

						deleteFileEntry = true;
						updateFileEntry = true;
					}
					else {
						InputStream existingFileVersionInputStream = null;

						try {
							existingFileVersionInputStream =
								latestExistingFileVersion.getContentStream(
									false);
						}
						catch (Exception exception) {
							if (_log.isDebugEnabled()) {
								_log.debug(exception, exception);
							}
						}
						finally {
							if (existingFileVersionInputStream != null) {
								existingFileVersionInputStream.close();
							}
						}

						if (existingFileVersionInputStream == null) {
							updateFileEntry = true;
						}
					}

					try {
						serviceContext.setIndexingEnabled(false);

						if (updateFileEntry) {
							DLFileVersion alreadyExistingFileVersion =
								_dlFileVersionLocalService.
									getFileVersionByUuidAndGroupId(
										fileVersionUuid,
										existingFileEntry.getGroupId());

							if (alreadyExistingFileVersion != null) {
								serviceContext.setAttribute(
									"existingDLFileVersionId",
									alreadyExistingFileVersion.
										getFileVersionId());
							}

							serviceContext.setUuid(fileVersionUuid);

							String fileEntryTitle =
								_dlFileEntryLocalService.getUniqueTitle(
									portletDataContext.getScopeGroupId(),
									existingFileEntry.getFolderId(),
									existingFileEntry.getFileEntryId(),
									fileEntry.getTitle(),
									fileEntry.getExtension());

							importedFileEntry =
								_dlAppLocalService.updateFileEntry(
									userId, existingFileEntry.getFileEntryId(),
									fileEntry.getFileName(),
									fileEntry.getMimeType(), fileEntryTitle,
									fileEntry.getDescription(), null,
									DLVersionNumberIncrease.MINOR, is,
									fileEntry.getSize(), serviceContext);
						}
						else {
							_dlAppLocalService.updateAsset(
								userId, existingFileEntry,
								latestExistingFileVersion, assetCategoryIds,
								assetTagNames, null);

							importedFileEntry = existingFileEntry;
						}

						if (importedFileEntry.getFolderId() != folderId) {
							importedFileEntry =
								_dlAppLocalService.moveFileEntry(
									userId, importedFileEntry.getFileEntryId(),
									folderId, serviceContext);
						}

						if (importedFileEntry instanceof LiferayFileEntry) {
							LiferayFileEntry liferayFileEntry =
								(LiferayFileEntry)importedFileEntry;

							Indexer<DLFileEntry> indexer =
								IndexerRegistryUtil.nullSafeGetIndexer(
									DLFileEntry.class);

							indexer.reindex(
								(DLFileEntry)liferayFileEntry.getModel());
						}

						if (deleteFileEntry &&
							ExportImportThreadLocal.isStagingInProcess()) {

							String latestExistingVersion =
								latestExistingFileVersion.getVersion();

							if (!latestExistingVersion.equals(
									importedFileEntry.getVersion()) &&
								!latestExistingVersion.equals(
									DLFileEntryConstants.
										PRIVATE_WORKING_COPY_VERSION)) {

								_dlAppService.deleteFileVersion(
									latestExistingFileVersion.getFileEntryId(),
									latestExistingFileVersion.getVersion());
							}
						}
					}
					finally {
						serviceContext.setIndexingEnabled(indexEnabled);
					}
				}

				if (ExportImportThreadLocal.isStagingInProcess()) {
					importedFileEntry = _overrideFileVersion(
						importedFileEntry, version, serviceContext);
				}
			}
			else {
				String fileEntryTitle = _dlFileEntryLocalService.getUniqueTitle(
					portletDataContext.getScopeGroupId(), folderId, 0,
					fileEntry.getTitle(), fileEntry.getExtension());

				importedFileEntry = _dlAppLocalService.addFileEntry(
					userId, repositoryId, folderId, fileEntry.getFileName(),
					fileEntry.getMimeType(), fileEntryTitle,
					fileEntry.getDescription(), null, is, fileEntry.getSize(),
					serviceContext);
			}

			for (DLPluggableContentDataHandler dlPluggableContentDataHandler :
					_serviceTrackerList) {

				dlPluggableContentDataHandler.importContent(
					portletDataContext, fileEntryElement, fileEntry,
					importedFileEntry);
			}

			portletDataContext.importClassedModel(
				fileEntry, importedFileEntry, DLFileEntry.class);

			Map<Long, Long> fileEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					FileEntry.class);

			fileEntryIds.put(
				fileEntry.getFileEntryId(), importedFileEntry.getFileEntryId());

			_importAssetDisplayPage(
				portletDataContext, fileEntry, importedFileEntry);
		}
		finally {
			serviceContext.setAttribute(
				"validateDDMFormValues", validateDDMFormValues);

			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException ioException) {
				_log.error(ioException, ioException);
			}
		}
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws Exception {

		FileEntry existingFileEntry = fetchStagedModelByUuidAndGroupId(
			fileEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFileEntry == null) || !existingFileEntry.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFileEntry.class.getName());

		if (trashHandler.isRestorable(existingFileEntry.getFileEntryId())) {
			long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

			trashHandler.restoreTrashEntry(
				userId, existingFileEntry.getFileEntryId());
		}
	}

	protected void exportDDMFormValues(
			PortletDataContext portletDataContext, DDMStructure ddmStructure,
			FileEntry fileEntry, Element fileEntryElement)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

		DLFileEntryMetadata dlFileEntryMetadata =
			_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
				ddmStructure.getStructureId(), fileVersion.getFileVersionId());

		if (dlFileEntryMetadata == null) {
			return;
		}

		Element structureFields = fileEntryElement.addElement(
			"structure-fields");

		String ddmFormValuesPath = ExportImportPathUtil.getModelPath(
			ddmStructure,
			String.valueOf(dlFileEntryMetadata.getDDMStorageId()));

		structureFields.addAttribute("ddm-form-values-path", ddmFormValuesPath);

		structureFields.addAttribute("structureUuid", ddmStructure.getUuid());

		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues =
			_storageEngine.getDDMFormValues(
				dlFileEntryMetadata.getDDMStorageId());

		ddmFormValues =
			_ddmFormValuesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, fileEntry, ddmFormValues, true, false);

		portletDataContext.addZipEntry(
			ddmFormValuesPath, serialize(ddmFormValues));
	}

	protected void exportMetaData(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry)
		throws Exception {

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.fetchFileEntryType(
				dlFileEntry.getFileEntryTypeId());

		if ((dlFileEntryType == null) || !dlFileEntryType.isExportable()) {
			return;
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, fileEntry, dlFileEntryType,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			exportDDMFormValues(
				portletDataContext, ddmStructure, fileEntry, fileEntryElement);
		}
	}

	protected DDMFormValues getImportDDMFormValues(
			PortletDataContext portletDataContext,
			Element structureFieldsElement, DDMStructure ddmStructure)
		throws Exception {

		String ddmFormValuesPath = structureFieldsElement.attributeValue(
			"ddm-form-values-path");

		String serializedDDMFormValues = portletDataContext.getZipEntryAsString(
			ddmFormValuesPath);

		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues =
			deserialize(
				serializedDDMFormValues,
				DDMBeanTranslatorUtil.translate(ddmStructure.getDDMForm()));

		ddmFormValues =
			_ddmFormValuesExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, ddmStructure, ddmFormValues);

		return DDMBeanTranslatorUtil.translate(ddmFormValues);
	}

	@Override
	protected String[] getSkipImportReferenceStagedModelNames() {
		return new String[] {AssetDisplayPageEntry.class.getName()};
	}

	protected void importMetaData(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileEntry fileEntry, ServiceContext serviceContext)
		throws Exception {

		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		Map<Long, Long> dlFileEntryTypeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntryType.class);

		long dlFileEntryTypeId = MapUtil.getLong(
			dlFileEntryTypeIds, dlFileEntry.getFileEntryTypeId(),
			dlFileEntry.getFileEntryTypeId());

		DLFileEntryType existingDLFileEntryType =
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
				dlFileEntryTypeId);

		if (existingDLFileEntryType == null) {
			serviceContext.setAttribute("fileEntryTypeId", -1);

			return;
		}

		serviceContext.setAttribute(
			"fileEntryTypeId", existingDLFileEntryType.getFileEntryTypeId());

		List<DDMStructure> ddmStructures =
			existingDLFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			Element structureFieldsElement =
				(Element)fileEntryElement.selectSingleNode(
					"structure-fields[@structureUuid='".concat(
						ddmStructure.getUuid()
					).concat(
						"']"
					));

			if (structureFieldsElement == null) {
				continue;
			}

			DDMFormValues ddmFormValues = getImportDDMFormValues(
				portletDataContext, structureFieldsElement, ddmStructure);

			serviceContext.setAttribute(
				DDMFormValues.class.getName() + StringPool.POUND +
					ddmStructure.getStructureId(),
				ddmFormValues);
		}
	}

	@Override
	protected boolean isStagedModelInTrash(FileEntry fileEntry) {
		return fileEntry.isInTrash();
	}

	protected String serialize(
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues) {

		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_jsonDDMFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	@Reference(
		target = "(&(verify.process.name=com.liferay.document.library.service))",
		unbind = "-"
	)
	protected void setVerifyProcessCompletionMarker(Object object) {
	}

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		if ((fileEntry.getGroupId() != portletDataContext.getGroupId()) &&
			(fileEntry.getGroupId() != portletDataContext.getScopeGroupId())) {

			PortletDataException portletDataException =
				new PortletDataException(PortletDataException.INVALID_GROUP);

			portletDataException.setStagedModelDisplayName(
				getDisplayName(fileEntry));
			portletDataException.setStagedModelClassName(
				fileEntry.getModelClassName());
			portletDataException.setStagedModelClassPK(
				GetterUtil.getString(fileEntry.getFileEntryId()));

			throw portletDataException;
		}

		try {
			FileVersion fileVersion = fileEntry.getFileVersion();

			if (!portletDataContext.isInitialPublication() &&
				!ArrayUtil.contains(
					getExportableStatuses(), fileVersion.getStatus())) {

				PortletDataException portletDataException =
					new PortletDataException(
						PortletDataException.STATUS_UNAVAILABLE);

				portletDataException.setStagedModelDisplayName(
					getDisplayName(fileEntry));
				portletDataException.setStagedModelClassName(
					fileVersion.getModelClassName());
				portletDataException.setStagedModelClassPK(
					GetterUtil.getString(fileVersion.getFileVersionId()));

				throw portletDataException;
			}
		}
		catch (PortletDataException portletDataException) {
			throw portletDataException;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check workflow status for file entry " +
						fileEntry.getFileEntryId());
			}
		}

		if (fileEntry.isInTrash() || fileEntry.isInTrashContainer()) {
			PortletDataException portletDataException =
				new PortletDataException(PortletDataException.STATUS_IN_TRASH);

			portletDataException.setStagedModel(fileEntry);

			throw portletDataException;
		}
	}

	private void _exportAssetDisplayPage(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				fileEntry.getGroupId(),
				_portal.getClassNameId(DLFileEntry.class),
				fileEntry.getFileEntryId());

		if (assetDisplayPageEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileEntry, assetDisplayPageEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private void _importAssetDisplayPage(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry)
		throws PortalException {

		List<Element> assetDisplayPageEntryElements =
			portletDataContext.getReferenceDataElements(
				fileEntry, AssetDisplayPageEntry.class);

		Map<Long, Long> articleNewPrimaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		articleNewPrimaryKeys.put(
			fileEntry.getFileEntryId(), importedFileEntry.getFileEntryId());

		for (Element assetDisplayPageEntryElement :
				assetDisplayPageEntryElements) {

			String path = assetDisplayPageEntryElement.attributeValue("path");

			AssetDisplayPageEntry assetDisplayPageEntry =
				(AssetDisplayPageEntry)portletDataContext.getZipEntryAsObject(
					path);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, assetDisplayPageEntryElement);

			Map<Long, Long> assetDisplayPageEntries =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					AssetDisplayPageEntry.class);

			long assetDisplayPageEntryId = MapUtil.getLong(
				assetDisplayPageEntries,
				assetDisplayPageEntry.getAssetDisplayPageEntryId(),
				assetDisplayPageEntry.getAssetDisplayPageEntryId());

			AssetDisplayPageEntry existingAssetDisplayPageEntry =
				_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
					assetDisplayPageEntryId);

			if (existingAssetDisplayPageEntry != null) {
				existingAssetDisplayPageEntry.setClassPK(
					importedFileEntry.getFileEntryId());

				_assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
					existingAssetDisplayPageEntry);
			}
		}
	}

	private FileEntry _overrideFileVersion(
			final FileEntry importedFileEntry, final String version,
			ServiceContext serviceContext)
		throws PortalException {

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					DLFileEntry dlFileEntry =
						_dlFileEntryLocalService.getDLFileEntry(
							importedFileEntry.getFileEntryId());

					if (version.equals(dlFileEntry.getVersion())) {
						return importedFileEntry;
					}

					DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

					String oldVersion = dlFileVersion.getVersion();

					dlFileVersion.setVersion(version);

					_dlFileVersionLocalService.updateDLFileVersion(
						dlFileVersion);

					dlFileEntry.setVersion(version);

					dlFileEntry = _dlFileEntryLocalService.updateDLFileEntry(
						dlFileEntry);

					if (DLStoreUtil.hasFile(
							dlFileEntry.getCompanyId(),
							dlFileEntry.getDataRepositoryId(),
							dlFileEntry.getName(), oldVersion)) {

						DLStoreUtil.updateFileVersion(
							dlFileEntry.getCompanyId(),
							dlFileEntry.getDataRepositoryId(),
							dlFileEntry.getName(), oldVersion, version);
					}

					return _dlAppLocalService.getFileEntry(
						dlFileEntry.getFileEntryId());
				});
		}
		catch (PortalException | SystemException exception) {
			throw exception;
		}
		catch (Throwable t) {
			throw new PortalException(t);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryStagedModelDataHandler.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Reference
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.storage.DDMFormValues)"
	)
	private ExportImportContentProcessor
		<com.liferay.dynamic.data.mapping.storage.DDMFormValues>
			_ddmFormValuesExportImportContentProcessor;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private DLTrashService _dlTrashService;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

	@Reference(target = "(ddm.form.values.serializer.type=json)")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

	@Reference
	private Portal _portal;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	private ServiceTrackerList
		<DLPluggableContentDataHandler, DLPluggableContentDataHandler>
			_serviceTrackerList;

	@Reference
	private StorageEngine _storageEngine;

	@Reference
	private TrashHelper _trashHelper;

}