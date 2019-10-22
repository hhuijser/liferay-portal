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

package com.liferay.document.library.web.internal.display.context.logic;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.DeleteMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.JavaScriptUIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLUIItem;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class UIItemsBuilder {

	public UIItemsBuilder(
		HttpServletRequest httpServletRequest, FileEntry fileEntry,
		FileVersion fileVersion, ResourceBundle resourceBundle,
		DLTrashUtil dlTrashUtil, VersioningStrategy versioningStrategy,
		DLURLHelper dlURLHelper) {

		this(
			httpServletRequest, fileEntry, null, fileVersion, resourceBundle,
			dlTrashUtil, versioningStrategy, dlURLHelper);
	}

	public UIItemsBuilder(
			HttpServletRequest httpServletRequest, FileShortcut fileShortcut,
			ResourceBundle resourceBundle, DLTrashUtil dlTrashUtil,
			VersioningStrategy versioningStrategy, DLURLHelper dlURLHelper)
		throws PortalException {

		this(
			httpServletRequest, null, fileShortcut,
			fileShortcut.getFileVersion(), resourceBundle, dlTrashUtil,
			versioningStrategy, dlURLHelper);
	}

	public UIItemsBuilder(
		HttpServletRequest httpServletRequest, FileVersion fileVersion,
		ResourceBundle resourceBundle, DLTrashUtil dlTrashUtil,
		VersioningStrategy versioningStrategy, DLURLHelper dlURLHelper) {

		this(
			httpServletRequest, null, null, fileVersion, resourceBundle,
			dlTrashUtil, versioningStrategy, dlURLHelper);
	}

	public void addCancelCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if ((_fileShortcut != null) ||
			!_fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable()) {

			return;
		}

		PortletURL portletURL = _getActionURL(
			"/document_library/edit_file_entry", Constants.CANCEL_CHECKOUT);

		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		_addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.CANCEL_CHECKOUT,
			"cancel-checkout[document]", portletURL.toString());
	}

	public void addCancelCheckoutToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable()) {

			return;
		}

		_addJavaScriptUIItem(
			new JavaScriptToolbarItem(), toolbarItems,
			DLUIItemKeys.CANCEL_CHECKOUT,
			LanguageUtil.get(_resourceBundle, "cancel-checkout[document]"),
			getSubmitFormJavaScript(Constants.CANCEL_CHECKOUT, null));
	}

	public void addCheckinMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if ((_fileShortcut != null) ||
			!_fileEntryDisplayContextHelper.isCheckinActionAvailable()) {

			return;
		}

		menuItems.add(getCheckinMenuItem());
	}

	public void addCheckinToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isCheckinActionAvailable()) {
			return;
		}

		PortletURL portletURL = _getActionURL(
			"/document_library/edit_file_entry", Constants.CHECKIN);

		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		JavaScriptToolbarItem javaScriptToolbarItem = _addJavaScriptUIItem(
			new JavaScriptToolbarItem(), toolbarItems, DLUIItemKeys.CHECKIN,
			LanguageUtil.get(_resourceBundle, "checkin"),
			StringBundler.concat(
				getNamespace(), "showVersionDetailsDialog('", portletURL,
				"');"));

		String javaScript =
			"/com/liferay/document/library/web/display/context/dependencies" +
				"/checkin_js.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put("namespace", getNamespace());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		javaScriptToolbarItem.setJavaScript(unsyncStringWriter.toString());
	}

	public void addCheckoutMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if ((_fileShortcut != null) ||
			!_fileEntryDisplayContextHelper.
				isCheckoutDocumentActionAvailable()) {

			return;
		}

		PortletURL portletURL = _getActionURL(
			"/document_library/edit_file_entry", Constants.CHECKOUT);

		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		_addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.CHECKOUT,
			"checkout[document]", portletURL.toString());
	}

	public void addCheckoutToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.
				isCheckoutDocumentActionAvailable()) {

			return;
		}

		_addJavaScriptUIItem(
			new JavaScriptToolbarItem(), toolbarItems, DLUIItemKeys.CHECKOUT,
			LanguageUtil.get(_resourceBundle, "checkout[document]"),
			getSubmitFormJavaScript(Constants.CHECKOUT, null));
	}

	public void addCompareToMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!DocumentConversionUtil.isComparableVersion(
				_fileVersion.getExtension())) {

			return;
		}

		PortletURL viewFileEntryURL = _getRenderURL(
			"/document_library/view_file_entry", _getRedirect());

		PortletURL selectFileVersionURL = _getRenderURL(
			"/document_library/select_file_version",
			viewFileEntryURL.toString());

		try {
			selectFileVersionURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			throw new PortalException(wse);
		}

		selectFileVersionURL.setParameter("version", _fileVersion.getVersion());

		Map<String, Object> data = HashMapBuilder.put(
			"uri", selectFileVersionURL
		).build();

		PortletURL compareVersionURL = _getRenderURL(
			"/document_library/compare_versions", null);

		compareVersionURL.setParameter("backURL", _getCurrentURL());

		String jsNamespace = getNamespace() + _fileVersion.getFileVersionId();

		StringBundler sb = new StringBundler(4);

		sb.append(jsNamespace);
		sb.append("compareVersionDialog('");
		sb.append(selectFileVersionURL.toString());
		sb.append("');");

		JavaScriptMenuItem javaScriptMenuItem = _addJavaScriptUIItem(
			new JavaScriptMenuItem(), menuItems, DLUIItemKeys.COMPARE_TO,
			"compare-to", sb.toString());

		javaScriptMenuItem.setData(data);

		String javaScript =
			"/com/liferay/document/library/web/display/context/dependencies" +
				"/compare_to_js.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put("compareVersionURL", compareVersionURL.toString());
		template.put(
			"dialogTitle",
			UnicodeLanguageUtil.get(_httpServletRequest, "compare-versions"));
		template.put("jsNamespace", jsNamespace);
		template.put("namespace", getNamespace());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		javaScriptMenuItem.setJavaScript(unsyncStringWriter.toString());
	}

	public void addDeleteMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		String cmd = null;

		if (isDeleteActionAvailable()) {
			cmd = Constants.DELETE;
		}
		else if (isMoveToTheRecycleBinActionAvailable()) {
			cmd = Constants.MOVE_TO_TRASH;
		}
		else {
			return;
		}

		DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

		deleteMenuItem.setKey(DLUIItemKeys.DELETE);

		if (cmd.equals(Constants.MOVE_TO_TRASH)) {
			deleteMenuItem.setTrash(true);
		}

		String mvcActionCommandName = "/document_library/edit_file_entry";

		if (_fileShortcut != null) {
			mvcActionCommandName = "/document_library/edit_file_shortcut";
		}

		PortletURL portletURL = _getDeleteActionURL(mvcActionCommandName, cmd);

		if (_fileShortcut == null) {
			portletURL.setParameter(
				"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));
		}
		else {
			portletURL.setParameter(
				"fileShortcutId",
				String.valueOf(_fileShortcut.getFileShortcutId()));
		}

		deleteMenuItem.setURL(portletURL.toString());

		menuItems.add(deleteMenuItem);
	}

	public void addDeleteToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isDeleteActionAvailable()) {
			return;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("if (confirm('");
		sb.append(
			UnicodeLanguageUtil.get(
				_resourceBundle, "are-you-sure-you-want-to-delete-this"));
		sb.append("')) {");

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		long folderId = _fileEntry.getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		portletURL.setParameter("folderId", String.valueOf(folderId));

		sb.append(
			getSubmitFormJavaScript(Constants.DELETE, portletURL.toString()));

		sb.append("}");

		_addJavaScriptUIItem(
			new JavaScriptToolbarItem(), toolbarItems, DLUIItemKeys.DELETE,
			LanguageUtil.get(_resourceBundle, "delete"), sb.toString());
	}

	public void addDeleteVersionMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if ((_fileEntry == null) ||
			(_fileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) ||
			!_fileEntryDisplayContextHelper.hasDeletePermission() ||
			!(_fileEntry.getModel() instanceof DLFileEntry)) {

			return;
		}

		int fileVersionsCount = _fileEntry.getFileVersionsCount(
			WorkflowConstants.STATUS_APPROVED);

		if (fileVersionsCount <= 1) {
			return;
		}

		PortletURL viewFileEntryURL = _getRenderURL(
			"/document_library/view_file_entry", _getRedirect());

		DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

		deleteMenuItem.setKey(DLUIItemKeys.DELETE_VERSION);
		deleteMenuItem.setLabel("delete-version");

		PortletURL portletURL = _getActionURL(
			"/document_library/edit_file_entry", Constants.DELETE,
			viewFileEntryURL.toString());

		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));
		portletURL.setParameter("version", _fileVersion.getVersion());

		deleteMenuItem.setURL(portletURL.toString());

		menuItems.add(deleteMenuItem);
	}

	public void addDownloadMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isDownloadActionAvailable()) {
			return;
		}

		String label = TextFormatter.formatStorageSize(
			_fileVersion.getSize(), _themeDisplay.getLocale());

		label = StringBundler.concat(
			_themeDisplay.translate("download"), " (", label, ")");

		final boolean appendVersion;

		if (StringUtil.equalsIgnoreCase(
				_fileEntry.getVersion(), _fileVersion.getVersion())) {

			appendVersion = false;
		}
		else {
			appendVersion = true;
		}

		String url = _dlURLHelper.getDownloadURL(
			_fileEntry, _fileVersion, _themeDisplay, StringPool.BLANK,
			appendVersion, true);

		URLMenuItem urlMenuItem = _addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.DOWNLOAD, label, url);

		Map<String, Object> data = HashMapBuilder.put(
			"analytics-file-entry-id", _fileEntry.getFileEntryId()
		).put(
			"senna-off", "true"
		).build();

		urlMenuItem.setData(data);

		urlMenuItem.setMethod("get");
	}

	public void addDownloadToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isDownloadActionAvailable()) {
			return;
		}

		String label = TextFormatter.formatStorageSize(
			_fileVersion.getSize(), _themeDisplay.getLocale());

		URLToolbarItem urlToolbarItem = new URLToolbarItem();

		urlToolbarItem.setData(
			new HashMap<String, Object>() {
				{
					put("analytics-file-entry-id", _fileEntry.getFileEntryId());
				}
			});

		_addURLUIItem(
			urlToolbarItem, toolbarItems, DLUIItemKeys.DOWNLOAD,
			StringBundler.concat(
				LanguageUtil.get(_resourceBundle, "download"), " (", label,
				")"),
			_dlURLHelper.getDownloadURL(
				_fileEntry, _fileVersion, _themeDisplay, StringPool.BLANK));
	}

	public void addEditMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (((_fileShortcut != null) &&
			 !_fileShortcutDisplayContextHelper.isEditActionAvailable()) ||
			((_fileShortcut == null) &&
			 !_fileEntryDisplayContextHelper.isEditActionAvailable())) {

			return;
		}

		PortletURL portletURL = null;

		if (_fileShortcut == null) {
			portletURL = _getControlPanelRenderURL(
				"/document_library/edit_file_entry");
		}
		else {
			portletURL = _getControlPanelRenderURL(
				"/document_library/edit_file_shortcut");
		}

		portletURL.setParameter("backURL", _getCurrentURL());

		_addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.EDIT, "edit",
			portletURL.toString());
	}

	public void addEditToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isEditActionAvailable()) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/edit_file_entry");

		_addURLUIItem(
			new URLToolbarItem(), toolbarItems, DLUIItemKeys.EDIT,
			LanguageUtil.get(_resourceBundle, "edit"), portletURL.toString());
	}

	public void addMoveMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (((_fileShortcut != null) &&
			 !_fileShortcutDisplayContextHelper.isMoveActionAvailable()) ||
			((_fileShortcut == null) &&
			 !_fileEntryDisplayContextHelper.isMoveActionAvailable())) {

			return;
		}

		_addJavaScriptUIItem(
			new JavaScriptMenuItem(), menuItems, DLUIItemKeys.MOVE,
			LanguageUtil.get(_resourceBundle, "move"),
			_getMoveEntryOnClickJavaScript());
	}

	public void addMoveToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isMoveActionAvailable()) {
			return;
		}

		_addJavaScriptUIItem(
			new JavaScriptToolbarItem(), toolbarItems, DLUIItemKeys.MOVE,
			LanguageUtil.get(_resourceBundle, "move"),
			_getMoveEntryOnClickJavaScript());
	}

	public void addMoveToTheRecycleBinToolbarItem(
			List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isMoveToTheRecycleBinActionAvailable()) {
			return;
		}

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		long folderId = _fileEntry.getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		portletURL.setParameter("folderId", String.valueOf(folderId));
		portletURL.setParameter(
			"folderId", String.valueOf(_fileEntry.getFolderId()));

		_addJavaScriptUIItem(
			new JavaScriptToolbarItem(), toolbarItems,
			DLUIItemKeys.MOVE_TO_THE_RECYCLE_BIN,
			LanguageUtil.get(_resourceBundle, "move-to-recycle-bin"),
			getSubmitFormJavaScript(
				Constants.MOVE_TO_TRASH, portletURL.toString()));
	}

	public void addOpenInMsOfficeMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (!isOpenInMsOfficeActionAvailable()) {
			return;
		}

		String webDavURL = _dlURLHelper.getWebDavURL(
			_themeDisplay, _fileEntry.getFolder(), _fileEntry,
			PropsValues.
				DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED,
			true);

		String onClick = StringBundler.concat(
			getNamespace(), "openDocument('", webDavURL, "');");

		JavaScriptMenuItem javaScriptMenuItem = _addJavaScriptUIItem(
			new JavaScriptMenuItem(), menuItems, DLUIItemKeys.OPEN_IN_MS_OFFICE,
			"open-in-ms-office", onClick);

		String javaScript =
			"/com/liferay/document/library/web/display/context/dependencies" +
				"/open_in_ms_office_js.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put(
			"errorMessage",
			UnicodeLanguageUtil.get(
				_resourceBundle,
				"cannot-open-the-requested-document-due-to-the-following-" +
					"reason"));
		template.put("namespace", getNamespace());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		javaScriptMenuItem.setJavaScript(unsyncStringWriter.toString());
	}

	public void addOpenInMsOfficeToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!isOpenInMsOfficeActionAvailable()) {
			return;
		}

		String webDavURL = _dlURLHelper.getWebDavURL(
			_themeDisplay, _fileEntry.getFolder(), _fileEntry,
			PropsValues.
				DL_FILE_ENTRY_OPEN_IN_MS_OFFICE_MANUAL_CHECK_IN_REQUIRED);

		StringBundler sb = new StringBundler(4);

		sb.append(getNamespace());
		sb.append("openDocument('");
		sb.append(webDavURL);
		sb.append("');");

		_addJavaScriptUIItem(
			new JavaScriptToolbarItem(), toolbarItems,
			DLUIItemKeys.OPEN_IN_MS_OFFICE,
			LanguageUtil.get(_resourceBundle, "open-in-ms-office"),
			sb.toString());
	}

	public void addPermissionsMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if (((_fileShortcut != null) &&
			 !_fileShortcutDisplayContextHelper.isPermissionsButtonVisible()) ||
			((_fileShortcut == null) &&
			 !_fileEntryDisplayContextHelper.isPermissionsButtonVisible())) {

			return;
		}

		String url = null;

		try {
			if (_fileShortcut != null) {
				url = PermissionsURLTag.doTag(
					null, DLFileShortcutConstants.getClassName(),
					HtmlUtil.unescape(_fileShortcut.getToTitle()), null,
					String.valueOf(_fileShortcut.getFileShortcutId()),
					LiferayWindowState.POP_UP.toString(), null,
					_httpServletRequest);
			}
			else {
				url = PermissionsURLTag.doTag(
					null, DLFileEntryConstants.getClassName(),
					HtmlUtil.unescape(_fileEntry.getTitle()), null,
					String.valueOf(_fileEntry.getFileEntryId()),
					LiferayWindowState.POP_UP.toString(), null,
					_httpServletRequest);
			}
		}
		catch (Exception e) {
			throw new SystemException("Unable to create permissions URL", e);
		}

		URLMenuItem urlMenuItem = _addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.PERMISSIONS,
			"permissions", url);

		urlMenuItem.setMethod("get");
		urlMenuItem.setUseDialog(true);
	}

	public void addPermissionsToolbarItem(List<ToolbarItem> toolbarItems)
		throws PortalException {

		if (!_fileEntryDisplayContextHelper.isPermissionsButtonVisible()) {
			return;
		}

		String permissionsURL = null;

		try {
			permissionsURL = PermissionsURLTag.doTag(
				null, DLFileEntryConstants.getClassName(),
				HtmlUtil.unescape(_fileEntry.getTitle()), null,
				String.valueOf(_fileEntry.getFileEntryId()),
				LiferayWindowState.POP_UP.toString(), null,
				_httpServletRequest);
		}
		catch (Exception e) {
			throw new SystemException("Unable to create permissions URL", e);
		}

		StringBundler sb = new StringBundler(6);

		sb.append("Liferay.Util.openWindow({dialogIframe: {bodyCssClass: ");
		sb.append("'dialog-with-footer'}, title: '");
		sb.append(UnicodeLanguageUtil.get(_resourceBundle, "permissions"));
		sb.append("', uri: '");
		sb.append(permissionsURL);
		sb.append("'});");

		_addJavaScriptUIItem(
			new JavaScriptToolbarItem(), toolbarItems, DLUIItemKeys.PERMISSIONS,
			LanguageUtil.get(_resourceBundle, "permissions"), sb.toString());
	}

	public void addPublishMenuItem(
			List<MenuItem> menuItems, boolean latestVersion)
		throws PortalException {

		if (!_isFileVersionExportable(latestVersion)) {
			return;
		}

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (!stagingGroupHelper.isStagingGroup(
				_themeDisplay.getScopeGroupId()) ||
			!stagingGroupHelper.isStagedPortlet(
				_themeDisplay.getScopeGroupId(),
				DLPortletKeys.DOCUMENT_LIBRARY)) {

			return;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (!portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return;
		}

		if (((_fileEntry == null) ||
			 !_fileEntryDisplayContextHelper.hasExportImportPermission()) &&
			((_fileShortcut == null) ||
			 _fileShortcutDisplayContextHelper.hasExportImportPermission())) {

			return;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("javascript:if (confirm('");
		sb.append(
			UnicodeLanguageUtil.get(
				_resourceBundle,
				"are-you-sure-you-want-to-publish-the-selected-document"));
		sb.append("')){location.href = '");

		PortletURL portletURL = null;

		if (_fileShortcut == null) {
			portletURL = _getActionURL("/document_library/publish_file_entry");

			portletURL.setParameter(
				"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));
		}
		else {
			portletURL = _getActionURL(
				"/document_library/publish_file_shortcut");

			portletURL.setParameter(
				"fileShortcutId",
				String.valueOf(_fileShortcut.getFileShortcutId()));
		}

		portletURL.setParameter("redirect", StringPool.BLANK);
		portletURL.setParameter("backURL", _getCurrentURL());

		sb.append(portletURL);

		sb.append("';}");

		_addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.PUBLISH,
			"publish-to-live", sb.toString());
	}

	public void addRevertToVersionMenuItem(List<MenuItem> menuItems)
		throws PortalException {

		if ((_fileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) ||
			!_fileEntryDisplayContextHelper.hasUpdatePermission()) {

			return;
		}

		FileVersion latestFileVersion = _fileEntry.getLatestFileVersion();

		String latestFileVersionVersion = latestFileVersion.getVersion();

		if (latestFileVersionVersion.equals(_fileVersion.getVersion())) {
			return;
		}

		PortletURL viewFileEntryURL = _getRenderURL(
			"/document_library/view_file_entry", _getRedirect());

		PortletURL portletURL = _getActionURL(
			"/document_library/edit_file_entry", Constants.REVERT,
			viewFileEntryURL.toString());

		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));
		portletURL.setParameter("version", _fileVersion.getVersion());

		_addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.REVERT, "revert",
			portletURL.toString());
	}

	public void addViewOriginalFileMenuItem(List<MenuItem> menuItems) {
		if (_fileShortcut == null) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/view_file_entry");

		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileShortcut.getToFileEntryId()));

		_addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.VIEW_ORIGINAL_FILE,
			"view-original-file", portletURL.toString());
	}

	public void addViewVersionMenuItem(List<MenuItem> menuItems) {
		if (_fileShortcut != null) {
			return;
		}

		PortletURL portletURL = _getRenderURL(
			"/document_library/view_file_entry", _getRedirect());

		portletURL.setParameter("version", _fileVersion.getVersion());

		_addURLUIItem(
			new URLMenuItem(), menuItems, DLUIItemKeys.VIEW_VERSION,
			"view[action]", portletURL.toString());
	}

	public MenuItem getCheckinMenuItem() throws PortalException {
		PortletURL portletURL = _getActionURL(
			"/document_library/edit_file_entry", Constants.CHECKIN);

		portletURL.setParameter(
			"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));

		if (!_versioningStrategy.isOverridable()) {
			URLMenuItem urlMenuItem = new URLMenuItem();

			urlMenuItem.setKey(DLUIItemKeys.CHECKIN);
			urlMenuItem.setLabel("checkin");
			urlMenuItem.setURL(portletURL.toString());

			return urlMenuItem;
		}

		JavaScriptMenuItem javaScriptMenuItem = new JavaScriptMenuItem();

		javaScriptMenuItem.setKey(DLUIItemKeys.CHECKIN);
		javaScriptMenuItem.setLabel("checkin");
		javaScriptMenuItem.setOnClick(
			StringBundler.concat(
				getNamespace(), "showVersionDetailsDialog('", portletURL,
				"');"));

		String javaScript =
			"/com/liferay/document/library/web/display/context/dependencies" +
				"/checkin_js.ftl";

		Class<?> clazz = getClass();

		URLTemplateResource urlTemplateResource = new URLTemplateResource(
			javaScript, clazz.getResource(javaScript));

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, urlTemplateResource, false);

		template.put("namespace", getNamespace());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		javaScriptMenuItem.setJavaScript(unsyncStringWriter.toString());

		return javaScriptMenuItem;
	}

	public boolean isOpenInMsOfficeActionAvailable() throws PortalException {
		if (_fileEntryDisplayContextHelper.hasViewPermission() &&
			_fileVersionDisplayContextHelper.isMsOffice() &&
			_isWebDAVEnabled() && _isIEOnWin32()) {

			return true;
		}

		return false;
	}

	protected String getNamespace() {
		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		return liferayPortletResponse.getNamespace();
	}

	protected String getSubmitFormJavaScript(String cmd, String redirect) {
		StringBundler sb = new StringBundler(18);

		sb.append("document.");
		sb.append(getNamespace());
		sb.append("fm.");
		sb.append(getNamespace());
		sb.append(Constants.CMD);
		sb.append(".value = '");
		sb.append(cmd);
		sb.append("';");

		if (redirect != null) {
			sb.append("document.");
			sb.append(getNamespace());
			sb.append("fm.");
			sb.append(getNamespace());
			sb.append("redirect.value = '");
			sb.append(redirect);
			sb.append("';");
		}

		sb.append("submitForm(document.");
		sb.append(getNamespace());
		sb.append("fm);");

		return sb.toString();
	}

	protected boolean isDeleteActionAvailable() throws PortalException {
		if (((_fileShortcut != null) &&
			 _fileShortcutDisplayContextHelper.isFileShortcutDeletable() &&
			 !_isFileShortcutTrashable()) ||
			((_fileShortcut == null) &&
			 _fileEntryDisplayContextHelper.isFileEntryDeletable() &&
			 !_isFileEntryTrashable())) {

			return true;
		}

		return false;
	}

	protected boolean isMoveToTheRecycleBinActionAvailable()
		throws PortalException {

		if (!isDeleteActionAvailable() &&
			(((_fileShortcut != null) &&
			  _fileShortcutDisplayContextHelper.isFileShortcutDeletable()) ||
			 ((_fileShortcut == null) &&
			  _fileEntryDisplayContextHelper.isFileEntryDeletable()))) {

			return true;
		}

		return false;
	}

	private UIItemsBuilder(
		HttpServletRequest httpServletRequest, FileEntry fileEntry,
		FileShortcut fileShortcut, FileVersion fileVersion,
		ResourceBundle resourceBundle, DLTrashUtil dlTrashUtil,
		VersioningStrategy versioningStrategy, DLURLHelper dlURLHelper) {

		try {
			_httpServletRequest = httpServletRequest;

			if ((fileEntry == null) && (fileVersion != null)) {
				fileEntry = fileVersion.getFileEntry();
			}

			_fileEntry = fileEntry;

			_fileShortcut = fileShortcut;
			_fileVersion = fileVersion;
			_resourceBundle = resourceBundle;
			_dlTrashUtil = dlTrashUtil;
			_versioningStrategy = versioningStrategy;
			_dlURLHelper = dlURLHelper;

			_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

			_fileEntryDisplayContextHelper = new FileEntryDisplayContextHelper(
				_themeDisplay.getPermissionChecker(), _fileEntry);

			_fileShortcutDisplayContextHelper =
				new FileShortcutDisplayContextHelper(
					_themeDisplay.getPermissionChecker(), _fileShortcut);

			_fileVersionDisplayContextHelper =
				new FileVersionDisplayContextHelper(fileVersion);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to build UIItemsBuilder for " + fileVersion, pe);
		}
	}

	private <T extends JavaScriptUIItem> T _addJavaScriptUIItem(
		T javascriptUIItem, List<? super T> javascriptUIItems, String key,
		String label, String onClick) {

		javascriptUIItem.setKey(key);
		javascriptUIItem.setLabel(label);
		javascriptUIItem.setOnClick(onClick);

		javascriptUIItems.add(javascriptUIItem);

		return javascriptUIItem;
	}

	private <T extends URLUIItem> T _addURLUIItem(
		T urlUIItem, List<? super T> urlUIItems, String key, String label,
		String url) {

		urlUIItem.setKey(key);
		urlUIItem.setLabel(label);
		urlUIItem.setURL(url);

		urlUIItems.add(urlUIItem);

		return urlUIItem;
	}

	private PortletURL _getActionURL(String mvcActionCommandName) {
		return _getActionURL(mvcActionCommandName, null);
	}

	private PortletURL _getActionURL(String mvcActionCommandName, String cmd) {
		return _getActionURL(mvcActionCommandName, cmd, _getCurrentURL());
	}

	private PortletURL _getActionURL(
		String mvcActionCommandName, String cmd, String redirect) {

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, mvcActionCommandName);

		if (Validator.isNotNull(cmd)) {
			portletURL.setParameter(Constants.CMD, cmd);
		}

		portletURL.setParameter("redirect", redirect);

		return portletURL;
	}

	private PortletURL _getControlPanelRenderURL(String mvcRenderCommandName) {
		return _getControlPanelRenderURL(
			mvcRenderCommandName, _getCurrentURL());
	}

	private PortletURL _getControlPanelRenderURL(
		String mvcRenderCommandName, String redirect) {

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			_getLiferayPortletRequest(), _themeDisplay.getScopeGroup(),
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		if (_fileShortcut != null) {
			portletURL.setParameter(
				"fileShortcutId",
				String.valueOf(_fileShortcut.getFileShortcutId()));
		}
		else {
			portletURL.setParameter(
				"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));
		}

		return portletURL;
	}

	private String _getCurrentURL() {
		if (_currentURL != null) {
			return _currentURL;
		}

		LiferayPortletRequest liferayPortletRequest =
			_getLiferayPortletRequest();

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_currentURL = portletURL.toString();

		return _currentURL;
	}

	private PortletURL _getDeleteActionURL(
		String mvcActionCommandName, String cmd) {

		String currentMVCRenderCommandName = ParamUtil.getString(
			_httpServletRequest, "mvcRenderCommandName");

		if (currentMVCRenderCommandName.equals(
				"/document_library/view_file_entry")) {

			String redirect = ParamUtil.getString(
				_httpServletRequest, "redirect");

			if (Validator.isNull(redirect)) {
				LiferayPortletResponse liferayPortletResponse =
					_getLiferayPortletResponse();

				PortletURL portletURL =
					liferayPortletResponse.createRenderURL();

				redirect = portletURL.toString();
			}

			return _getActionURL(mvcActionCommandName, cmd, redirect);
		}

		return _getActionURL(mvcActionCommandName, cmd);
	}

	private LiferayPortletRequest _getLiferayPortletRequest() {
		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		return PortalUtil.getLiferayPortletRequest(portletRequest);
	}

	private LiferayPortletResponse _getLiferayPortletResponse() {
		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortalUtil.getLiferayPortletResponse(portletResponse);
	}

	private String _getMoveEntryOnClickJavaScript() {
		StringBundler sb = new StringBundler(5);

		sb.append(getNamespace());
		sb.append("move(1, ");

		if (_fileShortcut != null) {
			sb.append("'rowIdsDLFileShortcut', ");
			sb.append(_fileShortcut.getFileShortcutId());
		}
		else {
			sb.append("'rowIdsFileEntry', ");
			sb.append(_fileEntry.getFileEntryId());
		}

		sb.append(");");

		return sb.toString();
	}

	private String _getRedirect() {
		if (_redirect == null) {
			_redirect = ParamUtil.getString(_httpServletRequest, "redirect");
		}

		return _redirect;
	}

	private PortletURL _getRenderURL(String mvcRenderCommandName) {
		return _getRenderURL(mvcRenderCommandName, _getCurrentURL());
	}

	private PortletURL _getRenderURL(
		String mvcRenderCommandName, String redirect) {

		LiferayPortletResponse liferayPortletResponse =
			_getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		if (_fileShortcut != null) {
			portletURL.setParameter(
				"fileShortcutId",
				String.valueOf(_fileShortcut.getFileShortcutId()));
		}
		else {
			portletURL.setParameter(
				"fileEntryId", String.valueOf(_fileEntry.getFileEntryId()));
		}

		return portletURL;
	}

	private boolean _isFileEntryTrashable() throws PortalException {
		if (_fileEntry.isRepositoryCapabilityProvided(TrashCapability.class) &&
			_isTrashEnabled()) {

			return true;
		}

		return false;
	}

	private boolean _isFileShortcutTrashable() throws PortalException {
		if (_fileShortcutDisplayContextHelper.isDLFileShortcut() &&
			_isTrashEnabled()) {

			return true;
		}

		return false;
	}

	private boolean _isFileVersionExportable(boolean latestVersion) {
		try {
			FileVersion fileVersion = _fileVersion;

			if (latestVersion) {
				if (_fileEntry == null) {
					return false;
				}

				fileVersion = _fileEntry.getLatestFileVersion();
			}

			if (fileVersion == null) {
				return false;
			}

			StagedModelDataHandler stagedModelDataHandler =
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					FileEntry.class.getName());

			if (ArrayUtil.contains(
					stagedModelDataHandler.getExportableStatuses(),
					fileVersion.getStatus())) {

				return true;
			}

			return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	private boolean _isIEOnWin32() {
		if (_ieOnWin32 == null) {
			_ieOnWin32 = BrowserSnifferUtil.isIeOnWin32(_httpServletRequest);
		}

		return _ieOnWin32;
	}

	private boolean _isTrashEnabled() throws PortalException {
		if (_trashEnabled != null) {
			return _trashEnabled;
		}

		_trashEnabled = false;

		if (_dlTrashUtil == null) {
			return _trashEnabled;
		}

		_trashEnabled = _dlTrashUtil.isTrashEnabled(
			_themeDisplay.getScopeGroupId(), _fileEntry.getRepositoryId());

		return _trashEnabled;
	}

	private boolean _isWebDAVEnabled() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.isWebDAVEnabled();
	}

	private String _currentURL;
	private final DLTrashUtil _dlTrashUtil;
	private final DLURLHelper _dlURLHelper;
	private final FileEntry _fileEntry;
	private final FileEntryDisplayContextHelper _fileEntryDisplayContextHelper;
	private FileShortcut _fileShortcut;
	private final FileShortcutDisplayContextHelper
		_fileShortcutDisplayContextHelper;
	private final FileVersion _fileVersion;
	private final FileVersionDisplayContextHelper
		_fileVersionDisplayContextHelper;
	private final HttpServletRequest _httpServletRequest;
	private Boolean _ieOnWin32;
	private String _redirect;
	private final ResourceBundle _resourceBundle;
	private final ThemeDisplay _themeDisplay;
	private Boolean _trashEnabled;
	private final VersioningStrategy _versioningStrategy;

}