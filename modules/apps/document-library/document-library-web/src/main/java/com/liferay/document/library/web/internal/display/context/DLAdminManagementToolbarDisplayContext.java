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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.logic.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.util.DLRequestHelper;
import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryPermission;
import com.liferay.document.library.web.internal.security.permission.resource.DLFolderPermission;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.document.library.web.internal.util.DLTrashUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.RepositoryUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class DLAdminManagementToolbarDisplayContext {

	public DLAdminManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest request,
			DLAdminDisplayContext dlAdminDisplayContext)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_request = request;
		_dlAdminDisplayContext = dlAdminDisplayContext;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);

		_dlRequestHelper = new DLRequestHelper(_request);

		_dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			_dlRequestHelper);

		_dlTrashUtil = (DLTrashUtil)_request.getAttribute(
			DLWebKeys.DOCUMENT_LIBRARY_TRASH_UTIL);

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_hasValidAssetVocabularies = _hasValidAssetVocabularies(
			_themeDisplay.getScopeGroupId());
	}

	public List<DropdownItem> getActionDropdownItems() {
		if (!_dlPortletInstanceSettingsHelper.isShowActions()) {
			return null;
		}

		return new DropdownItemList() {
			{
				boolean stagedActions = false;

				Group scopeGroup = _themeDisplay.getScopeGroup();
				StagingGroupHelper stagingGroupHelper =
					StagingGroupHelperUtil.getStagingGroupHelper();

				if (!stagingGroupHelper.isLiveGroup(scopeGroup) ||
					!stagingGroupHelper.isStagedPortlet(
						scopeGroup, DLPortletKeys.DOCUMENT_LIBRARY)) {

					stagedActions = true;
				}

				if (stagedActions) {
					add(
						dropdownItem -> {
							dropdownItem.putData("action", "download");
							dropdownItem.setIcon("download");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "download"));
							dropdownItem.setQuickAction(true);
						});
				}

				User user = _themeDisplay.getUser();

				if (stagedActions && !user.isDefaultUser()) {
					add(
						dropdownItem -> {
							dropdownItem.putData("action", "move");
							dropdownItem.setIcon("change");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "move"));
							dropdownItem.setQuickAction(true);
						});
					add(
						dropdownItem -> {
							dropdownItem.putData("action", "editTags");
							dropdownItem.setIcon("tag");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "edit-tags"));
							dropdownItem.setQuickAction(true);
						});

					if (_hasValidAssetVocabularies) {
						add(
							dropdownItem -> {
								dropdownItem.putData(
									"action", "editCategories");
								dropdownItem.setIcon("categories");
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, "edit-categories"));
								dropdownItem.setQuickAction(true);
							});
					}
				}

				if (!user.isDefaultUser()) {
					add(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteEntries");

							if (_dlTrashUtil.isTrashEnabled(
									scopeGroup.getGroupId(),
									_getRepositoryId())) {

								dropdownItem.setIcon("trash");
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, "move-to-recycle-bin"));
							}
							else {
								dropdownItem.setIcon("times");
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "delete"));
							}

							dropdownItem.setQuickAction(true);
						});
				}

				if (stagedActions && !user.isDefaultUser()) {
					add(
						dropdownItem -> {
							dropdownItem.putData("action", "checkin");
							dropdownItem.setIcon("unlock");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "checkin"));
							dropdownItem.setQuickAction(false);
						});

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "checkout");
							dropdownItem.setIcon("lock");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_request, "checkout[document]"));
							dropdownItem.setQuickAction(false);
						});
				}
			}
		};
	}

	public List<String> getAvailableActionDropdownItems(FileEntry fileEntry)
		throws PortalException {

		List<String> availableActionDropdownItems = new ArrayList<>();

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.DELETE)) {

			availableActionDropdownItems.add("deleteEntries");
		}

		if (DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.UPDATE)) {

			availableActionDropdownItems.add("move");

			if (fileEntry.isCheckedOut()) {
				availableActionDropdownItems.add("checkin");
			}
			else {
				availableActionDropdownItems.add("checkout");
			}

			if (_hasValidAssetVocabularies) {
				availableActionDropdownItems.add("editCategories");
			}

			availableActionDropdownItems.add("editTags");
		}

		if (DLFileEntryPermission.contains(
				permissionChecker, fileEntry, ActionKeys.VIEW)) {

			availableActionDropdownItems.add("download");
		}

		return availableActionDropdownItems;
	}

	public List<String> getAvailableActionDropdownItems(Folder folder)
		throws PortalException {

		List<String> availableActionDropdownItems = new ArrayList<>();

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (DLFolderPermission.contains(
				permissionChecker, folder, ActionKeys.DELETE)) {

			availableActionDropdownItems.add("deleteEntries");
		}

		if (DLFolderPermission.contains(
				permissionChecker, folder, ActionKeys.UPDATE) &&
			!folder.isMountPoint()) {

			availableActionDropdownItems.add("move");
		}

		if (DLFolderPermission.contains(
				permissionChecker, folder, ActionKeys.VIEW) &&
			!RepositoryUtil.isExternalRepository(folder.getRepositoryId())) {

			availableActionDropdownItems.add("download");
		}

		return availableActionDropdownItems;
	}

	public String getClearResultsURL() {
		PortletURL clearResultsURL = _liferayPortletResponse.createRenderURL();

		clearResultsURL.setParameter(
			"mvcRenderCommandName", "/document_library/view");
		clearResultsURL.setParameter(
			"folderId", String.valueOf(_getFolderId()));

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() {
		String portletName = _liferayPortletRequest.getPortletName();

		if (!portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
			return null;
		}

		PortletToolbarContributor dlPortletToolbarContributor =
			(PortletToolbarContributor)_request.getAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_PORTLET_TOOLBAR_CONTRIBUTOR);

		List<Menu> menus = dlPortletToolbarContributor.getPortletTitleMenus(
			_liferayPortletRequest, _liferayPortletResponse);

		if (menus.isEmpty()) {
			return null;
		}

		CreationMenu creationMenu = new CreationMenu();

		for (Menu menu : menus) {
			List<URLMenuItem> urlMenuItems =
				(List<URLMenuItem>)(List<?>)menu.getMenuItems();

			for (URLMenuItem urlMenuItem : urlMenuItems) {
				creationMenu.addDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(urlMenuItem.getURL());
						dropdownItem.setLabel(urlMenuItem.getLabel());
						dropdownItem.setSeparator(urlMenuItem.hasSeparator());
					});
			}
		}

		return creationMenu;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		if (_isSearch()) {
			return null;
		}

		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				long fileEntryTypeId = _getFileEntryTypeId();

				if (fileEntryTypeId != -1) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								_currentURLObj, _liferayPortletResponse);

							removeLabelURL.setParameter(
								"fileEntryTypeId", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							String fileEntryTypeName = LanguageUtil.get(
								_request, "basic-document");

							if (fileEntryTypeId !=
									DLFileEntryTypeConstants.
										FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

								DLFileEntryType fileEntryType =
									DLFileEntryTypeLocalServiceUtil.
										getFileEntryType(fileEntryTypeId);

								fileEntryTypeName = fileEntryType.getName(
									_request.getLocale());
							}

							String label = String.format(
								"%s: %s",
								LanguageUtil.get(_request, "document-type"),
								fileEntryTypeName);

							labelItem.setLabel(label);
						});
				}

				String navigation = _getNavigation();

				if (navigation.equals("mine")) {
					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								_currentURLObj, _liferayPortletResponse);

							removeLabelURL.setParameter(
								"navigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);

							User user = _themeDisplay.getUser();

							String label = String.format(
								"%s: %s", LanguageUtil.get(_request, "owner"),
								user.getFullName());

							labelItem.setLabel(label);
						});
				}
			}
		};
	}

	public PortletURL getSearchURL() {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter(
			"mvcRenderCommandName", "/document_library/search");

		long repositoryId = _getRepositoryId();

		searchURL.setParameter("repositoryId", String.valueOf(repositoryId));

		long searchRepositoryId = ParamUtil.getLong(
			_request, "searchRepositoryId", repositoryId);

		searchURL.setParameter(
			"searchRepositoryId", String.valueOf(searchRepositoryId));

		long folderId = _getFolderId();

		searchURL.setParameter("folderId", String.valueOf(folderId));

		long searchFolderId = ParamUtil.getLong(
			_request, "searchFolderId", folderId);

		searchURL.setParameter(
			"searchFolderId", String.valueOf(searchFolderId));

		searchURL.setParameter("showSearchInfo", Boolean.TRUE.toString());

		return searchURL;
	}

	public String getSortingOrder() {
		if (_isSearch()) {
			return null;
		}

		return _dlAdminDisplayContext.getOrderByType();
	}

	public PortletURL getSortingURL() {
		if (_isSearch()) {
			return null;
		}

		PortletURL sortingURL = _getCurrentSortingURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(_getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL;
	}

	public int getTotalItems() {
		SearchContainer searchContainer =
			_dlAdminDisplayContext.getSearchContainer();

		return searchContainer.getTotal();
	}

	public ViewTypeItemList getViewTypes() {
		if (_isSearch()) {
			return null;
		}

		String navigation = ParamUtil.getString(_request, "navigation", "home");

		int curEntry = ParamUtil.getInteger(_request, "curEntry");
		int deltaEntry = ParamUtil.getInteger(_request, "deltaEntry");

		long folderId = _getFolderId();

		long fileEntryTypeId = _getFileEntryTypeId();

		String keywords = ParamUtil.getString(_request, "keywords");

		PortletURL displayStyleURL = _liferayPortletResponse.createRenderURL();

		String mvcRenderCommandName = "/document_library/search";

		if (Validator.isNull(keywords)) {
			if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				mvcRenderCommandName = "/document_library/view";
			}
			else {
				mvcRenderCommandName = "/document_library/view_folder";
			}
		}

		displayStyleURL.setParameter(
			"mvcRenderCommandName", mvcRenderCommandName);

		displayStyleURL.setParameter(
			"navigation", HtmlUtil.escapeJS(navigation));

		if (curEntry > 0) {
			displayStyleURL.setParameter("curEntry", String.valueOf(curEntry));
		}

		if (deltaEntry > 0) {
			displayStyleURL.setParameter(
				"deltaEntry", String.valueOf(deltaEntry));
		}

		displayStyleURL.setParameter("folderId", String.valueOf(folderId));

		if (fileEntryTypeId != -1) {
			displayStyleURL.setParameter(
				"fileEntryTypeId", String.valueOf(fileEntryTypeId));
		}

		return new ViewTypeItemList(displayStyleURL, _getDisplayStyle()) {
			{
				if (ArrayUtil.contains(_getDisplayViews(), "icon")) {
					addCardViewTypeItem();
				}

				if (ArrayUtil.contains(_getDisplayViews(), "descriptive")) {
					addListViewTypeItem();
				}

				if (ArrayUtil.contains(_getDisplayViews(), "list")) {
					addTableViewTypeItem();
				}
			}
		};
	}

	public boolean isDisabled() {
		try {
			int count =
				DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
					_getRepositoryId(), _getFolderId(),
					WorkflowConstants.STATUS_ANY, true);

			if (count <= 0) {
				return true;
			}

			return false;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public boolean isSelectable() {
		return true;
	}

	public boolean isShowSearch() {
		return _dlPortletInstanceSettingsHelper.isShowSearch();
	}

	private PortletURL _getCurrentSortingURL() {
		int deltaEntry = ParamUtil.getInteger(_request, "deltaEntry");

		PortletURL sortingURL = _liferayPortletResponse.createRenderURL();

		long folderId = _getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			sortingURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			sortingURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		sortingURL.setParameter("navigation", _getNavigation());

		if (deltaEntry > 0) {
			sortingURL.setParameter("deltaEntry", String.valueOf(deltaEntry));
		}

		sortingURL.setParameter("folderId", String.valueOf(folderId));

		long fileEntryTypeId = _getFileEntryTypeId();

		sortingURL.setParameter(
			"fileEntryTypeId", String.valueOf(fileEntryTypeId));

		return sortingURL;
	}

	private String _getDisplayStyle() {
		return _dlAdminDisplayContext.getDisplayStyle();
	}

	private String[] _getDisplayViews() {
		DLPortletInstanceSettings dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();

		return dlPortletInstanceSettings.getDisplayViews();
	}

	private long _getFileEntryTypeId() {
		return ParamUtil.getLong(_request, "fileEntryTypeId", -1);
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		long fileEntryTypeId = _getFileEntryTypeId();
		final String navigation = ParamUtil.getString(
			_request, "navigation", "home");

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							navigation.equals("home") &&
							(fileEntryTypeId == -1));

						PortletURL viewAllDocumentsURL = PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse);

						viewAllDocumentsURL.setParameter(
							"mvcRenderCommandName", "/document_library/view");
						viewAllDocumentsURL.setParameter("navigation", "home");
						viewAllDocumentsURL.setParameter(
							"browseBy", (String)null);
						viewAllDocumentsURL.setParameter(
							"fileEntryTypeId", (String)null);

						dropdownItem.setHref(viewAllDocumentsURL);

						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});

				if (_themeDisplay.isSignedIn()) {
					add(
						dropdownItem -> {
							dropdownItem.setActive(navigation.equals("mine"));

							PortletURL viewMyDocumentsURL =
								PortletURLUtil.clone(
									_currentURLObj, _liferayPortletResponse);

							viewMyDocumentsURL.setParameter(
								"mvcRenderCommandName",
								"/document_library/view");
							viewMyDocumentsURL.setParameter(
								"navigation", "mine");

							dropdownItem.setHref(viewMyDocumentsURL);

							dropdownItem.setLabel(
								LanguageUtil.get(_request, "mine"));
						});
				}

				add(
					dropdownItem -> {
						dropdownItem.setActive(fileEntryTypeId != -1);

						dropdownItem.putData(
							"action", "openDocumentTypesSelector");

						String label = LanguageUtil.get(
							_request, "document-type");

						if (fileEntryTypeId != -1) {
							String fileEntryTypeName = LanguageUtil.get(
								_request, "basic-document");

							if (fileEntryTypeId !=
									DLFileEntryTypeConstants.
										FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

								DLFileEntryType fileEntryType =
									DLFileEntryTypeLocalServiceUtil.
										getFileEntryType(fileEntryTypeId);

								fileEntryTypeName = fileEntryType.getName(
									_request.getLocale());
							}

							label = String.format(
								"%s: %s", label, fileEntryTypeName);
						}

						dropdownItem.setLabel(label);
					});
			}
		};
	}

	private long _getFolderId() {
		if (_isSearch()) {
			return ParamUtil.getLong(_request, "folderId");
		}

		return _dlAdminDisplayContext.getFolderId();
	}

	private String _getNavigation() {
		return ParamUtil.getString(_request, "navigation", "home");
	}

	private String _getOrderByCol() {
		return _dlAdminDisplayContext.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		final Map<String, String> orderColumns = new HashMap<>();

		orderColumns.put("creationDate", "create-date");

		if (_getFileEntryTypeId() == -1) {
			orderColumns.put("downloads", "downloads");
		}

		orderColumns.put("modifiedDate", "modified-date");
		orderColumns.put("size", "size");
		orderColumns.put("title", "title");

		return new DropdownItemList() {
			{
				for (Map.Entry<String, String> orderByColEntry :
						orderColumns.entrySet()) {

					String orderByCol = orderByColEntry.getKey();

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								orderByCol.equals(_getOrderByCol()));
							dropdownItem.setHref(
								_getCurrentSortingURL(), "orderByCol",
								orderByCol);
							dropdownItem.setLabel(
								LanguageUtil.get(
									_request, orderByColEntry.getValue()));
						});
				}
			}
		};
	}

	private String _getOrderByType() {
		return _dlAdminDisplayContext.getOrderByType();
	}

	private long _getRepositoryId() {
		return _dlAdminDisplayContext.getRepositoryId();
	}

	private boolean _hasValidAssetVocabularies(long scopeGroupId)
		throws PortalException {

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId));

		Stream<AssetVocabulary> stream = assetVocabularies.stream();

		return stream.anyMatch(
			assetVocabulary -> {
				if (!assetVocabulary.isAssociatedToClassNameId(
						ClassNameLocalServiceUtil.getClassNameId(
							DLFileEntry.class.getName()))) {

					return false;
				}

				int count =
					AssetCategoryServiceUtil.getVocabularyCategoriesCount(
						assetVocabulary.getGroupId(),
						assetVocabulary.getVocabularyId());

				if (count > 0) {
					return true;
				}

				return false;
			});
	}

	private boolean _isSearch() {
		return _dlAdminDisplayContext.isSearch();
	}

	private final PortletURL _currentURLObj;
	private final DLAdminDisplayContext _dlAdminDisplayContext;
	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private final DLRequestHelper _dlRequestHelper;
	private final DLTrashUtil _dlTrashUtil;
	private final boolean _hasValidAssetVocabularies;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}