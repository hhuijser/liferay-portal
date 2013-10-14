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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileVersionTest extends BaseDLAppTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeLocalServiceUtil.getFileEntryTypes(
				PortalUtil.getSiteAndCompanyGroupIds(group.getGroupId()));

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			String name = dlFileEntryType.getName(LocaleUtil.getSiteDefault());

			if (name.equals(DLFileEntryTypeConstants.NAME_CONTRACT)) {
				_contractDLFileEntryTypeId =
					dlFileEntryType.getFileEntryTypeId();
			}
		}

		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.addDefaultTable(
				PortalUtil.getDefaultCompanyId(), DLFileEntry.class.getName());

		ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), _EXPANDO_ATTRIBUTE_NAME,
			ExpandoColumnConstants.STRING, StringPool.BLANK);

		_serviceContext = getServiceContext();

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
			ContentTypes.APPLICATION_OCTET_STREAM, _FILE_NAME, StringPool.BLANK,
			StringPool.BLANK, _INITIAL_CONTENT.getBytes(), _serviceContext);

		_fileEntryId = fileEntry.getFileEntryId();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		ExpandoTable expandoTable =
			ExpandoTableLocalServiceUtil.getDefaultTable(
				PortalUtil.getDefaultCompanyId(), DLFileEntry.class.getName());

		ExpandoTableLocalServiceUtil.deleteTable(expandoTable);
	}

	@Test
	public void testUpdateChecksum() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_FILE_NAME, StringPool.BLANK, StringPool.BLANK, false,
			_UPDATE_CONTENT.getBytes(), _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateDescription() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_FILE_NAME, _UPDATE_VALUE, StringPool.BLANK, false,
			_INITIAL_CONTENT.getBytes(), _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateExpando() throws Exception {
		updateServiceContext(
			_UPDATE_VALUE, _contractDLFileEntryTypeId, StringPool.BLANK);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_FILE_NAME, StringPool.BLANK, StringPool.BLANK, false,
			_INITIAL_CONTENT.getBytes(), _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateFileEntryType() throws Exception {
		updateServiceContext(
			StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			StringPool.BLANK);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_FILE_NAME, StringPool.BLANK, StringPool.BLANK, false,
			_INITIAL_CONTENT.getBytes(), _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateMajorVersion() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_FILE_NAME, StringPool.BLANK, StringPool.BLANK, true,
			_INITIAL_CONTENT.getBytes(), _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateMetadata() throws Exception {
		updateServiceContext(
			StringPool.BLANK, _contractDLFileEntryTypeId, _UPDATE_VALUE);

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_FILE_NAME, StringPool.BLANK, StringPool.BLANK, false,
			_INITIAL_CONTENT.getBytes(), _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateNothing() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_FILE_NAME, StringPool.BLANK, StringPool.BLANK, false,
			_INITIAL_CONTENT.getBytes(), _serviceContext);

		Assert.assertEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateSize() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_FILE_NAME, StringPool.BLANK, StringPool.BLANK, false,
			CONTENT.getBytes(), _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testUpdateTitle() throws Exception {
		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			_fileEntryId, _FILE_NAME, ContentTypes.APPLICATION_OCTET_STREAM,
			_UPDATE_VALUE, StringPool.BLANK, StringPool.BLANK, false,
			_INITIAL_CONTENT.getBytes(), _serviceContext);

		Assert.assertNotEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	protected ServiceContext getServiceContext()
		throws PortalException, SystemException {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setAttribute(
			"fileEntryTypeId", _contractDLFileEntryTypeId);

		Map<String, Serializable> expandoBridgeAttributes =
			serviceContext.getExpandoBridgeAttributes();

		expandoBridgeAttributes.put(_EXPANDO_ATTRIBUTE_NAME, StringPool.BLANK);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				_contractDLFileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			Fields fields = new Fields();

			Set<String> names = ddmStructure.getFieldNames();

			for (String name : names) {
				if (!ddmStructure.isFieldPrivate(name)) {
					Field field = new Field(
						ddmStructure.getStructureId(), name, StringPool.BLANK);

					fields.put(field);
				}
			}

			serviceContext.setAttribute(
				Fields.class.getName() + ddmStructure.getStructureId(), fields);
		}

		return serviceContext;
	}

	protected void updateServiceContext(
			String expando, long fileEntryTypeId, String metadata)
		throws PortalException, SystemException {

		_serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);

		Map<String, Serializable> expandoBridgeAttributes =
			_serviceContext.getExpandoBridgeAttributes();

		expandoBridgeAttributes.put(_EXPANDO_ATTRIBUTE_NAME, expando);

		_serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		if (fileEntryTypeId <= 0) {
			return;
		}

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			Fields fields = (Fields)_serviceContext.getAttribute(
				Fields.class.getName() + ddmStructure.getStructureId());

			for (Field field : fields) {
				String type = field.getType();

				if (!field.isPrivate() && type.equals("text")) {
					field.setValue(metadata);
				}
			}

			_serviceContext.setAttribute(
				Fields.class.getName() + ddmStructure.getStructureId(), fields);
		}
	}

	private static final String _EXPANDO_ATTRIBUTE_NAME = "Expando";

	private static final String _FILE_NAME = "Test.txt";

	private static final String _INITIAL_CONTENT = "ContentX";

	private static final String _UPDATE_CONTENT = "ContentY";

	private static final String _UPDATE_VALUE = "Update Value";

	private long _contractDLFileEntryTypeId;
	private long _fileEntryId;
	private ServiceContext _serviceContext;

}