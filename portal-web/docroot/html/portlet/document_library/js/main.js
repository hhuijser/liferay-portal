AUI.add(
	'liferay-document-library',
	function(A) {
		var AObject = A.Object;
		var History = Liferay.HistoryManager;
		var Lang = A.Lang;
		var QueryString = A.QueryString;

		var formatSelectorNS = A.Node.formatSelectorNS;

		var owns = AObject.owns;

		var UA = A.UA;

		var PAIR_SEPARATOR = History.PAIR_SEPARATOR;

		var VALUE_SEPARATOR = History.VALUE_SEPARATOR;

		var ATTR_CHECKED = 'checked';

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_ACTIVE_AREA_PROXY = 'active-area-proxy';

		var CSS_DOCUMENT_DISPLAY_STYLE = '.document-display-style';

		var CSS_DOCUMENT_DISPLAY_STYLE_SELECTABLE = '.document-display-style.selectable';

		var CSS_DOCUMENT_DISPLAY_STYLE_SELECTED = '.document-display-style.selected';

		var CSS_SYNC_MESSAGE_HIDDEN = 'sync-message-hidden';

		var CSS_RESULT_ROW = '.results-row';

		var CSS_SELECTED = 'selected';

		var DATA_DIRECTION_RIGHT = 'data-direction-right';

		var DATA_FOLDER_ID = 'data-folder-id';

		var DATA_REPOSITORY_ID = 'data-repository-id';

		var DATA_VIEW_ENTRIES = 'data-view-entries';

		var DATA_VIEW_FOLDERS = 'data-view-folders';

		var DEFAULT_FOLDER_ID = 0;

		var DISPLAY_STYLE_LIST = 'list';

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var DOCUMENT_DRAGGABLE = '[data-draggable]';

		var DOCUMENT_LIBRARY_GROUP = 'document-library';

		var EXPAND_FOLDER = 'expandFolder';

		var MESSAGE_TYPE_ERROR = 'error';

		var PARENT_NODE = 'parentNode';

		var ROWS_PER_PAGE = 'rowsPerPage';

		var SEARCH_REPOSITORY_ID = 'searchRepositoryId';

		var SEARCH_TYPE = 'searchType';

		var SEARCH_TYPE_SINGLE = 1;

		var STR_ACTIVE = 'active';

		var STR_AJAX_REQUEST = 'ajax';

		var STR_BLANK = '';

		var STR_CLICK = 'click';

		var STR_DATA = 'data';

		var STR_DATA_SEARCH_PROCESSED = 'data-searchProcessed';

		var STR_DRAG_NODE = 'dragNode';

		var STR_ENTRY_END = 'entryEnd';

		var STR_ENTRY_START = 'entryStart';

		var STR_FOCUS = 'focus';

		var STR_FOLDER_CONTAINER = 'folderContainer';

		var STR_FOLDER_END = 'folderEnd';

		var STR_FOLDER_ID = 'folderId';

		var STR_FOLDER_START = 'folderStart';

		var STR_KEYWORDS = 'keywords';

		var STR_TOGGLE_ACTIONS_BUTTON = 'toggleActionsButton';

		var STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX = 'rowIdsDLFileShortcutCheckbox';

		var STR_ROW_IDS_FOLDER_CHECKBOX = 'rowIdsFolderCheckbox';

		var STR_ROW_IDS_FILE_ENTRY_CHECKBOX = 'rowIdsFileEntryCheckbox';

		var STR_SEARCH_FOLDER_ID = 'searchFolderId';

		var STR_SEARCH_RESULTS_CONTAINER = 'searchResultsContainer';

		var STR_SHOW_REPOSITORY_TABS = 'showRepositoryTabs';

		var STR_SHOW_SEARCH_INFO = 'showSearchInfo';

		var STRUTS_ACTION = 'struts_action';

		var SRC_DISPLAY_STYLE_BUTTONS = 0;

		var SRC_ENTRIES_PAGINATOR = 1;

		var SRC_GLOBAL = 0;

		var SRC_HISTORY = 2;

		var SRC_RESTORE_STATE = 4;

		var SRC_SEARCH = 3;

		var SRC_SEARCH_END = 4;

		var SRC_SEARCH_FRAGMENT = 2;

		var SRC_SEARCH_MULTIPLE = 0;

		var SRC_SEARCH_SINGLE = 1;

		var TOUCH = UA.touch;

		var TPL_MESSAGE_RESPONSE = '<div class="lfr-message-response" />';

		var TPL_MESSAGE_SEARCHING = '<div class="portlet-msg-info">{0}</div><div class="loading-animation" />';

		var VIEW_ENTRIES = 'viewEntries';

		var VIEW_ENTRIES_PAGE = 'viewEntriesPage';

		var VIEW_FOLDERS = 'viewFolders';

		var WIN = A.config.win;

		Liferay.DL_DISPLAY_STYLE_BUTTONS = SRC_DISPLAY_STYLE_BUTTONS;

		Liferay.DL_ENTRIES_PAGINATOR = SRC_ENTRIES_PAGINATOR;

		Liferay.DL_GLOBAL = SRC_GLOBAL;

		Liferay.DL_HISTORY = SRC_HISTORY;

		Liferay.DL_SEARCH = SRC_SEARCH;

		Liferay.DL_SEARCH_END = SRC_SEARCH_END;

		Liferay.DL_SEARCH_FRAGMENT = SRC_SEARCH_FRAGMENT;

		Liferay.DL_SEARCH_MULTIPLE = SRC_SEARCH_MULTIPLE;

		Liferay.DL_SEARCH_SINGLE = SRC_SEARCH_SINGLE;

		var DocumentLibrary = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'documentlibrary',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var documentLibraryContainer = instance.byId('documentLibraryContainer');

						instance._documentLibraryContainer = documentLibraryContainer;

						instance._dataRetrieveFailure = instance.ns('dataRetrieveFailure');
						instance._eventDataRequest = instance.ns('dataRequest');
						instance._eventDataRetrieveSuccess = instance.ns('dataRetrieveSuccess');
						instance._eventEditFileEntry = instance.ns('editFileEntry');
						instance._eventOpenDocument = instance.ns('openDocument');
						instance._eventPageLoaded = instance.ns('pageLoaded');
						instance._eventChangeSearchFolder = instance.ns('changeSearchFolder');

						instance._displayStyleToolbarNode = instance.byId(DISPLAY_STYLE_TOOLBAR);
						instance._entriesContainer = instance.byId('documentContainer');

						instance._selectAllCheckbox = instance.byId('allRowIdsCheckbox');

						instance._portletMessageContainer = A.Node.create(TPL_MESSAGE_RESPONSE);

						instance._displayStyle = instance.ns('displayStyle');
						instance._folderId = instance.ns(STR_FOLDER_ID);

						instance._keywordsNode = instance.byId(STR_KEYWORDS);

						if (!config.syncMessageDisabled) {
							instance._syncMessage = new Liferay.Message(
								{
									boundingBox: instance.byId('syncNotification'),
									contentBox: instance.byId('syncNotificationContent'),
									id: instance.NS + 'show-sync-message',
									trigger: instance.one('#showSyncMessageIconContainer'),
									visible: !config.syncMessageSuppressed
								}
							).render();
						}

						var entryPage = 0;

						if (config.entriesTotal > 0) {
							entryPage = config.entryEnd / config.entryRowsPerPage;
						}

						var entryPaginator = new A.Paginator(
							{
								circular: false,
								containers: '.document-entries-paginator',
								firstPageLinkLabel: '&lt;&lt;',
								lastPageLinkLabel: '&gt;&gt;',
								nextPageLinkLabel: '&gt;',
								page: entryPage,
								prevPageLinkLabel: '&lt;',
								rowsPerPage: config.entryRowsPerPage,
								rowsPerPageOptions: config.entryRowsPerPageOptions,
								total: config.entriesTotal
							}
						).render();

						entryPaginator.on('changeRequest', instance._onEntryPaginatorChangeRequest, instance);

						var folderPage = 0;

						if (config.foldersTotal > 0) {
							folderPage = config.folderEnd / config.folderRowsPerPage;
						}

						var folderPaginator = new A.Paginator(
							{
								alwaysVisible: false,
								circular: false,
								containers: '.folder-paginator',
								firstPageLinkLabel: '&lt;&lt;',
								lastPageLinkLabel: '&gt;&gt;',
								nextPageLinkLabel: '&gt;',
								page: folderPage,
								prevPageLinkLabel: '&lt;',
								rowsPerPage: config.folderRowsPerPage,
								rowsPerPageOptions: config.folderRowsPerPageOptions,
								total: config.foldersTotal
							}
						).render();

						folderPaginator.on('changeRequest', instance._onFolderPaginatorChangeRequest, instance);

						var eventHandles = [
							Liferay.after(instance._eventDataRequest, instance._afterDataRequest, instance),
							Liferay.on(instance._dataRetrieveFailure, instance._onDataRetrieveFailure, instance),
							Liferay.on(instance._eventDataRequest, instance._onDataRequest, instance),
							Liferay.on(instance._eventDataRetrieveSuccess, instance._onDataRetrieveSuccess, instance),
							Liferay.on(instance._eventEditFileEntry, instance._editFileEntry, instance),
							Liferay.on(instance._eventOpenDocument, instance._openDocument, instance),
							Liferay.on(instance._eventPageLoaded, instance._onPageLoaded, instance),
							Liferay.on(instance._eventChangeSearchFolder, instance._onChangeSearchFolder, instance)
						];

						var folderContainer = instance.byId(STR_FOLDER_CONTAINER);

						instance._listView = new Liferay.ListView(
							{
								boundingBox: formatSelectorNS(instance.NS, '#listViewContainer'),
								cssClass: 'folder-display-style lfr-list-view-content',
								itemSelector: '.folder a.browse-folder, .folder a.expand-folder',
								contentBox: folderContainer,
								srcNode: folderContainer
							}
						).render();

						instance._listView.after('transitionComplete', instance._initDropTargets, instance);

						instance._listView.after('itemChange', instance._afterListViewItemChange, instance);

						documentLibraryContainer.delegate(
							STR_CLICK,
							A.bind(instance._onDocumentLibraryContainerClick, instance),
							formatSelectorNS(instance.NS, '#documentContainer a[data-folder=true], #breadcrumbContainer a')
						);

						eventHandles.push(
							History.after('stateChange', instance._afterStateChange, instance),
							Liferay.on('showTab', instance._onShowTab, instance)
						);

						documentLibraryContainer.plug(A.LoadingMask);

						instance._config = config;

						instance._displayViews = config.displayViews;

						instance._folderContainer = folderContainer;

						instance._entryPaginator = entryPaginator;
						instance._folderPaginator = folderPaginator;

						instance._eventHandles = eventHandles;

						instance._initHover();

						if (themeDisplay.isSignedIn()) {
							instance._initDragDrop();

							instance._initSelectAllCheckbox();

							instance._initToggleSelect();
						}

						instance._repositoriesData = {};

						eventHandles.push(Liferay.on(config.portletId + ':portletRefreshed', A.bind(instance.destructor, instance)));

						var searchFormNode = instance.one('#fm1');

						if (searchFormNode) {
							searchFormNode.on('submit', instance._onSearchFormSubmit, instance);
						}

						instance._toggleSyncNotification();

						instance._restoreState();
					},

					destructor: function() {
						var instance = this;

						instance._entryPaginator.destroy();
						instance._folderPaginator.destroy();
						instance._listView.destroy();
						instance._ddHandler.destroy();

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._documentLibraryContainer.purge(true);
					},

					_addHistoryState: function(data) {
						var instance = this;

						var historyState = A.clone(data);

						var currentHistoryState = History.get();

						var defaultParams = instance._config.defaultParams;

						AObject.each(
							currentHistoryState,
							function(index, item, collection) {
								if (!owns(historyState, item) && !owns(defaultParams, item)) {
									historyState[item] = null;
								}
							}
						);

						if (!AObject.isEmpty(historyState)) {
							History.add(historyState);
						}
					},

					_afterDataRequest: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						var config = instance._config;

						var data = {};

						var displayStyle = instance._displayStyle;

						data[instance._folderId] = config.defaultParentFolderId;

						data[displayStyle] = History.get(displayStyle) || config.displayStyle;

						data[instance.ns(VIEW_ENTRIES)] = true;

						data[instance.ns(VIEW_FOLDERS)] = true;

						A.mix(data, requestParams, true);

						var src = event.src;

						if (src !== SRC_HISTORY) {
							instance._addHistoryState(data);
						}

						if (src !== SRC_RESTORE_STATE) {
							data[STR_AJAX_REQUEST] = true;
						}

						var ioRequest = A.io.request(
							instance._config.mainUrl,
							{
								autoLoad: false,
								method: 'get'
							}
						);

						var sendIOResponse = A.bind(instance._sendIOResponse, instance, ioRequest);

						ioRequest.after(['failure', 'success'], sendIOResponse);

						ioRequest.set(STR_DATA, data);

						if (src === SRC_SEARCH) {
							var repositoryId = event.requestParams[instance.NS + SEARCH_REPOSITORY_ID];

							var repositoriesData = instance._repositoriesData;

							var repositoryData = repositoriesData[repositoryId];

							if (!repositoryData) {
								repositoryData = {};

								repositoriesData[repositoryId] = repositoryData;
							}

							repositoryData.dataRequest = data;
						}
						else {
							instance._documentLibraryContainer.loadingmask.show();
						}

						ioRequest.start();

						delete data[STR_AJAX_REQUEST];

						instance._lastDataRequest = data;
					},

					_afterStateChange: function(event) {
						var instance = this;

						var namespace = instance.NS;

						var requestParams = {};

						var state = History.get();

						AObject.each(
							state,
							function(item, index, collection) {
								if (index.indexOf(namespace) === 0) {
									requestParams[index] = item;
								}
							}
						);

						instance._tuneStateChangeParams(requestParams);

						if (AObject.isEmpty(requestParams)) {
							requestParams = instance._getDefaultHistoryState();
						}

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: SRC_HISTORY
							}
						);
					},

					_afterListViewItemChange: function(event) {
						var instance = this;

						var selFolder = A.one('.folder.selected');

						if (selFolder) {
							selFolder.removeClass(CSS_SELECTED);
						}

						var item = event.newVal;

						item.ancestor('.folder').addClass(CSS_SELECTED);

						var dataExpandFolder = item.attr('data-expand-folder');
						var dataFileEntryTypeId = item.attr('data-file-entry-type-id');
						var dataFolderId = item.attr(DATA_FOLDER_ID);
						var dataNavigation = item.attr('data-navigation');
						var dataViewEntries = item.attr(DATA_VIEW_ENTRIES);
						var dataViewFolders = item.attr(DATA_VIEW_FOLDERS);

						var direction = 'left';

						if (item.attr(DATA_DIRECTION_RIGHT)) {
							direction = 'right';
						}

						instance._listView.set('direction', direction);

						var config = instance._config;

						var requestParams = {};

						requestParams[instance.ns(STRUTS_ACTION)] = config.strutsAction;
						requestParams[instance.ns(STR_ENTRY_END)] = config.entryRowsPerPage || instance._entryPaginator.get(ROWS_PER_PAGE);
						requestParams[instance.ns(STR_ENTRY_START)] = 0;
						requestParams[instance.ns(STR_FOLDER_END)] = config.folderRowsPerPage || instance._folderPaginator.get(ROWS_PER_PAGE);
						requestParams[instance.ns(STR_FOLDER_START)] = 0;

						if (dataExpandFolder) {
							requestParams[instance.ns(EXPAND_FOLDER)] = dataExpandFolder;
						}

						if (dataFolderId) {
							requestParams[instance._folderId] = dataFolderId;
						}

						if (dataNavigation) {
							requestParams[instance.ns('navigation')] = dataNavigation;
						}

						if (dataViewEntries) {
							requestParams[instance.ns(VIEW_ENTRIES)] = dataViewEntries;
						}

						if (dataFileEntryTypeId) {
							requestParams[instance.ns('fileEntryTypeId')] = dataFileEntryTypeId;
						}

						if (dataViewFolders) {
							requestParams[instance.ns(VIEW_FOLDERS)] = dataViewFolders;
						}

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_editFileEntry: function(event) {
						var instance = this;

						var config = instance._config;

						var action = event.action;

						var url = config.editEntryUrl;

						if (action == config.actions.MOVE) {
							url = config.moveEntryRenderUrl;
						}

						instance._processFileEntryAction(action, url);
					},

					_getDefaultHistoryState: function() {
						var instance = this;

						var initialState = History.get();

						if (AObject.isEmpty(initialState)) {
							initialState = instance._getDefaultParams();
						}

						return initialState;
					},

					_getDefaultParams: function() {
						var instance = this;

						var config = instance._config;

						var params = {};

						params[instance.ns(STR_ENTRY_END)] = config[STR_ENTRY_END];
						params[instance.ns(STR_ENTRY_START)] = config[STR_ENTRY_START];
						params[instance.ns(STR_FOLDER_END)] = config[STR_FOLDER_END];
						params[instance.ns(STR_FOLDER_START)] = config[STR_FOLDER_START];
						params[instance.ns(STR_FOLDER_ID)] = config[STR_FOLDER_ID];

						var namespace = instance.NS;

						var tmpParams = QueryString.parse(location.search, PAIR_SEPARATOR, VALUE_SEPARATOR);

						A.mix(tmpParams, QueryString.parse(location.hash, PAIR_SEPARATOR, VALUE_SEPARATOR));

						for (var paramName in tmpParams) {
							if (owns(tmpParams, paramName) && paramName.indexOf(namespace) == 0) {
								params[paramName] = tmpParams[paramName];
							}
						}

						return params;
					},

					_getDisplayStyle: function(style) {
						var instance = this;

						var displayStyle = History.get(instance._displayStyle) || instance._config.displayStyle;

						if (style) {
							displayStyle = (displayStyle == style);
						}

						return displayStyle;
					},

					_getMoveText: function(selectedItemsCount, targetAvailable) {
						var moveText = STR_BLANK;

						if (targetAvailable) {
							moveText = Liferay.Language.get('x-item-is-ready-to-be-moved-to-x');

							if (selectedItemsCount > 1) {
								moveText = Liferay.Language.get('x-items-are-ready-to-be-moved-to-x');
							}
						}
						else {
							moveText = Liferay.Language.get('x-item-is-ready-to-be-moved');

							if (selectedItemsCount > 1) {
								moveText = Liferay.Language.get('x-items-are-ready-to-be-moved');
							}
						}

						return moveText;
					},

					_getRepositoryName: function(repositoryId) {
						var instance = this;

						var repositoryName = null;

						var repositories = instance._config.repositories;

						var length = repositories.length;

						for (var i = 0; i < length; i++) {
							var repository = repositories[i];

							if (repository.id == repositoryId) {
								repositoryName = repository.name;

								break;
							}
						}

						return repositoryName;
					},

					_getResultsStartEnd: function(paginator, page, rowsPerPage) {
						var instance = this;

						if (!Lang.isValue(page)) {
							page = 0;

							var curPage = paginator.get('page') - 1;

							if (curPage > 0) {
								page = curPage;
							}
						}

						if (!Lang.isValue(rowsPerPage)) {
							rowsPerPage = paginator.get(ROWS_PER_PAGE);
						}

						var start = page * rowsPerPage;
						var end = start + rowsPerPage;

						return [start, end];
					},

					_getSelectedFolder: function() {
						var instance = this;

						var selectedFolderNode = instance._folderContainer.one('.selected .browse-folder');

						var selectedFolderId = 0;

						if (selectedFolderNode) {
							selectedFolderId = selectedFolderNode.attr(DATA_FOLDER_ID);
						}

						var repositoryId = selectedFolderNode.attr(DATA_REPOSITORY_ID);

						if (!repositoryId) {
							repositoryId = instance._config.repositories[0].id;
						}

						return {
							id: selectedFolderId,
							repositoryId: repositoryId
						};
					},

					_initDragDrop: function() {
						var instance = this;

						var ddHandler = new A.DD.Delegate(
							{
								container: instance._documentLibraryContainer,
								nodes: DOCUMENT_DRAGGABLE,
								on: {
									'drag:drophit': A.bind(instance._onDragDropHit, instance),
									'drag:enter': A.bind(instance._onDragEnter, instance),
									'drag:exit': A.bind(instance._onDragExit, instance),
									'drag:start': A.bind(instance._onDragStart, instance)
								}
							}
						);

						var dd = ddHandler.dd;

						dd.set('offsetNode', false);

						dd.removeInvalid('a');

						dd.set('groups', [DOCUMENT_LIBRARY_GROUP]);

						dd.plug(
							[
								{
									cfg: {
										moveOnEnd: false
									},
									fn: A.Plugin.DDProxy
								},
								{
									cfg: {
										constrain2node: instance._documentLibraryContainer
									},
									fn: A.Plugin.DDConstrained
								}
							]
						);

						if (TOUCH) {
							instance._dragTask = A.debounce(
								function(entryLink) {
									if (entryLink) {
										entryLink.simulate('click');
									}
								},
								A.DD.DDM.get('clickTimeThresh')
							);

							dd.after(
								'afterMouseDown',
								function(event) {
									instance._dragTask(event.target.get('node').one('.document-link'));
								},
								instance
							);
						}

						instance._initDropTargets();

						instance._ddHandler = ddHandler;
					},

					_initDropTargets: function() {
						var instance = this;

						if (themeDisplay.isSignedIn()) {
							var items = instance._documentLibraryContainer.all('[data-folder="true"]');

							items.each(
								function(item, index, collection) {
									item.plug(
										A.Plugin.Drop,
										{
											groups: [DOCUMENT_LIBRARY_GROUP],
											padding: '-1px'
										}
									);
								}
							);
						}
					},

					_initHover: function() {
						var instance = this;

						instance._entriesContainer.on([STR_FOCUS, 'blur'], instance._toggleHovered, instance);
					},

					_initSelectAllCheckbox: function() {
						var instance = this;

						instance._selectAllCheckbox.on(STR_CLICK, instance._onSelectAllCheckboxChange, instance);
					},

					_initToggleSelect: function() {
						var instance = this;

						instance._entriesContainer.delegate(
							'change',
							instance._onDocumentSelectorChange,
							'.document-selector',
							instance
						);
					},

					_moveEntries: function(folderId) {
						var instance = this;

						var config = instance._config;

						var form = config.form.node;

						form.get(instance.ns('newFolderId')).val(folderId);

						instance._processFileEntryAction(config.moveConstant, config.moveEntryRenderUrl);
					},

					_onChangeSearchFolder: function(event) {
						var instance = this;

						var selectedFolder = instance._getSelectedFolder();

						var searchData = {
							folderId: selectedFolder.id,
							keywords: instance._keywordsNode.get('value'),
							repositoryId: selectedFolder.repositoryId,
							showSearchInfo: true
						};

						if (event.searchEverywhere) {
							searchData[SEARCH_REPOSITORY_ID] = instance._config.repositories[0].id;
							searchData[STR_SEARCH_FOLDER_ID] = DEFAULT_FOLDER_ID;
							searchData[STR_SHOW_REPOSITORY_TABS] = true;
						}
						else {
							searchData[SEARCH_REPOSITORY_ID] = selectedFolder.repositoryId;
							searchData[STR_SEARCH_FOLDER_ID] = selectedFolder.id;
							searchData[STR_SHOW_REPOSITORY_TABS] = false;
						}

						instance._searchFileEntry(searchData);
					},

					_onDataRetrieveSuccess: function(event) {
						var instance = this;

						var responseData = event.responseData;

						instance._documentLibraryContainer.loadingmask.hide();

						var content = A.Node.create(responseData);

						if (content) {
							instance._setBreadcrumb(content);
							instance._setButtons(content);
							instance._setEntries(content);
							instance._setFolders(content);
							instance._setParentFolderTitle(content);
							instance._syncDisplayStyleToolbar(content);
							instance._setSearchResults(content);
						}
					},

					_onDataRequest: function(event) {
						var instance = this;

						var src = event.src;

						if (src === SRC_DISPLAY_STYLE_BUTTONS || src === SRC_ENTRIES_PAGINATOR) {
							var selectedEntries;

							var entriesSelector = CSS_DOCUMENT_DISPLAY_STYLE_SELECTED + ' :checkbox';

							if (instance._getDisplayStyle(DISPLAY_STYLE_LIST)) {
								entriesSelector = 'td > :checkbox:checked';
							}

							selectedEntries = instance._entriesContainer.all(entriesSelector);

							if (selectedEntries.size()) {
								instance._selectedEntries = selectedEntries.val();
							}
						}
						else if (src === SRC_SEARCH) {
							instance._entryPaginator.setState(
								{
									page: 1
								}
							);
						}

						instance._processDefaultParams(event);

						instance._updatePaginatorValues(event);
					},

					_onDataRetrieveFailure: function(event) {
						var instance = this;

						instance._documentLibraryContainer.loadingmask.hide();

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onDocumentLibraryContainerClick: function(event) {
						var instance = this;

						event.preventDefault();

						var config = instance._config;

						var requestParams = {};

						requestParams[instance.ns(STRUTS_ACTION)] = config.strutsAction;
						requestParams[instance.ns('action')] = 'browseFolder';
						requestParams[instance.ns(STR_ENTRY_END)] = instance._entryPaginator.get(ROWS_PER_PAGE);
						requestParams[instance.ns(STR_FOLDER_END)] = instance._folderPaginator.get(ROWS_PER_PAGE);
						requestParams[instance._folderId] = event.currentTarget.attr(DATA_FOLDER_ID);
						requestParams[instance.ns(EXPAND_FOLDER)] = false;
						requestParams[instance.ns(STR_ENTRY_START)] = 0;
						requestParams[instance.ns(STR_FOLDER_START)] = 0;

						var viewEntries = event.currentTarget.attr(DATA_VIEW_ENTRIES);

						if (viewEntries) {
							requestParams[instance.ns(VIEW_ENTRIES)] = viewEntries;
						}

						var viewFolders = event.currentTarget.attr(DATA_VIEW_FOLDERS);

						if (viewFolders) {
							requestParams[instance.ns(VIEW_FOLDERS)] = viewFolders;
						}

						var direction = 'left';

						if (event.currentTarget.attr(DATA_DIRECTION_RIGHT)) {
							direction = 'right';
						}

						instance._listView.set('direction', direction);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_onDocumentSelectorChange: function(event) {
						var instance = this;

						instance._toggleSelected(event.currentTarget, true);

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						Liferay.Util.checkAllBox(
							instance._entriesContainer,
							[
								instance.ns(STR_ROW_IDS_FILE_ENTRY_CHECKBOX),
								instance.ns(STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX),
								instance.ns(STR_ROW_IDS_FOLDER_CHECKBOX)
							],
							instance._selectAllCheckbox
						);
					},

					_onDragDropHit: function(event) {
						var instance = this;

						var proxyNode = event.target.get(STR_DRAG_NODE);

						proxyNode.removeClass(CSS_ACTIVE_AREA_PROXY);

						proxyNode.empty();

						var dropTarget = event.drop.get('node');

						var dd = instance._ddHandler.dd;

						var selectedItemsCount = dd.get(STR_DATA).selectedItemsCount;

						if (selectedItemsCount > 0) {
							var folderId = dropTarget.attr(DATA_FOLDER_ID);

							var folderContainer = dropTarget.ancestor('.document-display-style');

							var selectedItems = instance._ddHandler.dd.get(STR_DATA).selectedItems;

							if (selectedItems.indexOf(folderContainer) == -1) {
								instance._moveEntries(folderId);
							}
						}
					},

					_onDragEnter: function(event) {
						var instance = this;

						var dragNode = event.drag.get('node');
						var dropTarget = event.drop.get('node');

						dropTarget = dropTarget.ancestor(CSS_DOCUMENT_DISPLAY_STYLE) || dropTarget;

						if (!dragNode.compareTo(dropTarget)) {
							var dd = instance._ddHandler.dd;

							var selectedItemsCount = dd.get(STR_DATA).selectedItemsCount;

							if (selectedItemsCount > 0) {
								dropTarget.addClass(CSS_ACTIVE_AREA);

								var proxyNode = event.target.get(STR_DRAG_NODE);

								var moveText = instance._getMoveText(selectedItemsCount, true);

								var itemTitle = Lang.trim(dropTarget.attr('data-title'));

								proxyNode.html(Lang.sub(moveText, [selectedItemsCount, itemTitle]));
							}
						}
					},

					_onDragExit: function(event) {
						var instance = this;

						var dropTarget = event.drop.get('node');

						dropTarget = dropTarget.ancestor(CSS_DOCUMENT_DISPLAY_STYLE) || dropTarget;

						dropTarget.removeClass(CSS_ACTIVE_AREA);

						var selectedItemsCount = instance._ddHandler.dd.get(STR_DATA).selectedItemsCount;

						if (selectedItemsCount > 0) {
							var proxyNode = event.target.get(STR_DRAG_NODE);

							var moveText = instance._getMoveText(selectedItemsCount);

							proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));
						}
					},

					_onDragStart: function(event) {
						var instance = this;

						if (instance._dragTask) {
							instance._dragTask.cancel();
						}

						var target = event.target;

						var node = target.get('node');

						if (!node.hasClass(CSS_SELECTED)) {
							instance._unselectAllEntries();

							if (node.one('.document-selector :checkbox')) {
								instance._toggleSelected(node);
							}
						}

						var proxyNode = target.get(STR_DRAG_NODE);

						proxyNode.setStyles(
							{
								height: STR_BLANK,
								width: STR_BLANK
							}
						);

						var selectedItems = instance._entriesContainer.all(CSS_DOCUMENT_DISPLAY_STYLE_SELECTED);

						var selectedItemsCount = selectedItems.size();

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));

						proxyNode.addClass(CSS_ACTIVE_AREA_PROXY);

						var dd = instance._ddHandler.dd;

						dd.set(
							STR_DATA,
							{
								selectedItemsCount: selectedItemsCount,
								selectedItems: selectedItems
							}
						);
					},

					_onEntryPaginatorChangeRequest: function(event) {
						var instance = this;

						var startEndParams = instance._getResultsStartEnd(instance._entryPaginator);

						var requestParams = instance._lastDataRequest || instance._getDefaultParams();

						var customParams = {};

						customParams[instance.ns(STR_ENTRY_START)] = startEndParams[0];
						customParams[instance.ns(STR_ENTRY_END)] = startEndParams[1];
						customParams[instance.ns(VIEW_ENTRIES)] = false;
						customParams[instance.ns(VIEW_ENTRIES_PAGE)] = true;
						customParams[instance.ns(VIEW_FOLDERS)] = false;

						if (AObject.owns(requestParams, instance.ns(SEARCH_TYPE))) {
							customParams[instance.ns(SEARCH_TYPE)] = SRC_SEARCH_FRAGMENT;
						}

						A.mix(requestParams, customParams, true);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: SRC_ENTRIES_PAGINATOR
							}
						);
					},

					_onFolderPaginatorChangeRequest: function(event) {
						var instance = this;

						var startEndParams = instance._getResultsStartEnd(instance._folderPaginator);

						var requestParams = instance._lastDataRequest || {};

						var customParams = {};

						customParams[instance.ns(STR_FOLDER_START)] = startEndParams[0];
						customParams[instance.ns(STR_FOLDER_END)] = startEndParams[1];
						customParams[instance.ns(VIEW_ENTRIES)] = false;
						customParams[instance.ns(VIEW_FOLDERS)] = true;

						A.mix(requestParams, customParams, true);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_onPageLoaded: function(event) {
						var instance = this;

						var paginatorData = event.paginator;

						if (paginatorData) {
							if (event.src == SRC_SEARCH) {
								var repositoriesData = instance._repositoriesData;

								var repositoryData = repositoriesData[event.repositoryId];

								if (!repositoryData) {
									repositoryData = {};

									instance._repositoriesData[event.repositoryId] = repositoryData;
								}

								repositoryData.paginatorData = paginatorData;

								instance._setPaginatorData(paginatorData);
							}
							else {
								instance._setPaginatorData(paginatorData);
							}

							instance._toggleSyncNotification();
						}
					},

					_onSearchFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var selectedFolder = instance._getSelectedFolder();

						var showTabs = (selectedFolder.id == DEFAULT_FOLDER_ID);

						var searchData = {
							folderId: selectedFolder.id,
							keywords: instance._keywordsNode.get('value'),
							repositoryId: selectedFolder.repositoryId,
							searchFolderId: selectedFolder.id,
							searchRepositoryId: selectedFolder.repositoryId,
							showRepositoryTabs: showTabs,
							showSearchInfo: true
						};

						instance._searchFileEntry(searchData);
					},

					_onSelectAllCheckboxChange: function() {
						var instance = this;

						instance._toggleEntriesSelection();
					},

					_onShowTab: function(event) {
						var instance = this;

						var tabSection = event.tabSection;

						var searchResultsWrapper = tabSection.one('[data-repositoryId]');

						var repositoryId = searchResultsWrapper.attr('data-repositoryId');

						var repositoryData = instance._repositoriesData[repositoryId];

						if (repositoryData) {
							var paginatorData = repositoryData.paginatorData;

							if (paginatorData) {
								instance._setPaginatorData(paginatorData);
							}

							instance._lastDataRequest = repositoryData.dataRequest;
						}

						if (!searchResultsWrapper.hasAttribute(STR_DATA_SEARCH_PROCESSED)) {
							searchResultsWrapper.setAttribute(STR_DATA_SEARCH_PROCESSED, true);

							var selectedFolder = instance._getSelectedFolder();

							var searchData = {
								folderId: selectedFolder.id,
								keywords: instance._keywordsNode.get('value'),
								repositoryId: selectedFolder.repositoryId,
								searchFolderId: DEFAULT_FOLDER_ID,
								searchRepositoryId: repositoryId
							};

							instance._searchFileEntry(searchData);
						}
						else {
							instance._documentLibraryContainer.all('.document-entries-paginator').show();
						}
					},

					_openDocument: function(event) {
						var instance = this;

						var webDavUrl = event.webDavUrl;

						if (webDavUrl && UA.ie) {
							try {
								var executor = new WIN.ActiveXObject('SharePoint.OpenDocuments');

								executor.EditDocument(webDavUrl);
							}
							catch (exception) {
								var errorMessage = Lang.sub(
									Liferay.Language.get('cannot-open-the-requested-document-due-to-the-following-reason'),
									[exception.message]
								);

								instance._sendMessage(MESSAGE_TYPE_ERROR, errorMessage);
							}
						}
					},

					_processDefaultParams: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						AObject.each(
							instance._config.defaultParams,
							function(item, index, collection) {
								if (!Lang.isValue(History.get(index))) {
									requestParams[index] = item;
								}
							}
						);
					},

					_processFileEntryAction: function(action, url) {
						var instance = this;

						var config = instance._config;

						var form = config.form.node;

						var redirectUrl = location.href;

						if (action === config.actions.DELETE && !History.HTML5 && location.hash) {
							redirectUrl = instance._updateFolderIdRedirectUrl(redirectUrl);
						}

						form.attr('method', config.form.method);

						form.get(instance.ns('cmd')).val(action);
						form.get(instance.ns('redirect')).val(redirectUrl);

						var allRowIds = config.allRowIds;
						var rowIds = config.rowIds;

						var allRowsIdCheckbox = instance.ns(allRowIds + 'Checkbox');

						var folderIds = Liferay.Util.listCheckedExcept(form, allRowsIdCheckbox, instance.ns(rowIds + 'FolderCheckbox'));
						var fileEntryIds = Liferay.Util.listCheckedExcept(form, allRowsIdCheckbox, instance.ns(rowIds + 'FileEntryCheckbox'));
						var fileShortcutIds = Liferay.Util.listCheckedExcept(form, allRowsIdCheckbox, instance.ns(rowIds + 'DLFileShortcutCheckbox'));

						form.get(instance.ns('folderIds')).val(folderIds);
						form.get(instance.ns('fileEntryIds')).val(fileEntryIds);
						form.get(instance.ns('fileShortcutIds')).val(fileShortcutIds);

						submitForm(form, url);
					},

					_restoreState: function() {
						var instance = this;

						if (!History.HTML5) {
							var initialState = History.get();

							if (!AObject.isEmpty(initialState)) {
								var namespace = instance.NS;

								var requestParams = {};

								AObject.each(
									initialState,
									function(item, index, collection) {
										if (index.indexOf(namespace) === 0) {
											requestParams[index] = item;
										}
									}
								);

								Liferay.fire(
									instance._eventDataRequest,
									{
										requestParams: requestParams,
										src: SRC_RESTORE_STATE
									}
								);
							}
						}
					},

					_searchFileEntry: function(searchData) {
						var instance = this;

						if (searchData.showRepositoryTabs || searchData.showSearchInfo) {
							var entriesContainer = instance._entriesContainer;

							entriesContainer.empty();

							var searchingTPL = Lang.sub(TPL_MESSAGE_SEARCHING, [Liferay.Language.get('searching,-please-wait')]);

							entriesContainer.html(searchingTPL);
						}

						instance._documentLibraryContainer.all('.document-entries-paginator').hide();

						var requestParams = {};

						requestParams[instance.ns(STRUTS_ACTION)] = '/document_library/search';
						requestParams[instance.ns('repositoryId')] =  searchData.repositoryId;
						requestParams[instance.ns(SEARCH_REPOSITORY_ID)] = searchData.searchRepositoryId;
						requestParams[instance.ns(STR_FOLDER_ID)] = searchData.folderId;
						requestParams[instance.ns(STR_SEARCH_FOLDER_ID)] = searchData.searchFolderId;
						requestParams[instance.ns(STR_KEYWORDS)] = searchData.keywords;
						requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						requestParams[instance.ns(STR_SHOW_REPOSITORY_TABS)] = searchData.showRepositoryTabs;
						requestParams[instance.ns(STR_SHOW_SEARCH_INFO)] = searchData.showSearchInfo;

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: Liferay.DL_SEARCH
							}
						);
					},

					_setBreadcrumb: function(content) {
						var instance = this;

						var breadcrumb = instance.one('#breadcrumb', content);

						if (breadcrumb) {
							var breadcrumbContainer;

							var dlBreadcrumb = breadcrumb.one('.portlet-breadcrumb ul');

							if (dlBreadcrumb) {
								breadcrumbContainer = instance.byId('breadcrumbContainer');

								breadcrumbContainer.setContent(dlBreadcrumb);
							}

							var portalBreadcrumb = breadcrumb.one('.portal-breadcrumb ul');

							if (portalBreadcrumb) {
								breadcrumbContainer = A.one('#breadcrumbs ul');

								if (breadcrumbContainer) {
									breadcrumbContainer.setContent(portalBreadcrumb.html());
								}
							}
						}
					},

					_setButtons: function(content) {
						var instance = this;

						var addButton = instance.one('#addButton', content);

						if (addButton) {
							var addButtonContainer = instance.byId('addButtonContainer');

							addButtonContainer.plug(A.Plugin.ParseContent);

							addButtonContainer.setContent(addButton);
						}

						var displayStyleButtons = instance.one('#displayStyleButtons', content);

						if (displayStyleButtons) {
							instance._displayStyleToolbarNode.empty();

							var displayStyleButtonsContainer = instance.byId('displayStyleButtonsContainer');

							displayStyleButtonsContainer.plug(A.Plugin.ParseContent);

							displayStyleButtonsContainer.setContent(displayStyleButtons);
						}

						var sortButton = instance.one('#sortButton', content);

						if (sortButton) {
							var sortButtonContainer = instance.byId('sortButtonContainer');

							sortButtonContainer.plug(A.Plugin.ParseContent);

							sortButtonContainer.setContent(sortButton);
						}
					},

					_setEntries: function(content) {
						var instance = this;

						var entries = instance.one('#entries', content);

						if (entries) {
							var entriesContainer = instance._entriesContainer;

							entriesContainer.empty();

							entriesContainer.plug(A.Plugin.ParseContent);

							entriesContainer.setContent(entries);

							instance._initDropTargets();

							instance._updateSelectedEntriesStatus();
						}
					},

					_setFolders: function(content) {
						var instance = this;

						var folders = instance.one('#folderContainer', content);

						if (folders) {
							var listViewDataContainer = A.one('.lfr-list-view-data-container');

							listViewDataContainer.plug(A.Plugin.ParseContent);

							instance._listView.set(STR_DATA, folders.html());
						}
					},

					_setPaginatorData: function(paginatorData) {
						var instance = this;

						var paginator = instance['_' + paginatorData.name];

						if (A.instanceOf(paginator, A.Paginator)) {
							paginator.setState(paginatorData.state);
						}
					},

					_setParentFolderTitle: function(content) {
						var instance = this;

						var parentFolderTitle = instance.one('#parentFolderTitle', content);

						if (parentFolderTitle) {
							var parentFolderTitleContainer = instance.byId('parentFolderTitleContainer');

							parentFolderTitleContainer.setContent(parentFolderTitle);
						}
					},

					_setSearchResults: function(content) {
						var instance = this;

						var repositoryId;

						var repositoryIdNode = instance.one('#' + instance.ns(SEARCH_REPOSITORY_ID), content);

						if (repositoryIdNode) {
							repositoryId = repositoryIdNode.val();
						}

						var searchInfo = instance.one('#' + instance.ns('searchInfo'), content);

						var entriesContainer = instance._entriesContainer;

						if (searchInfo) {
							entriesContainer.empty();

							entriesContainer.plug(A.Plugin.ParseContent);

							entriesContainer.setContent(searchInfo);
						}

						var fragmentSearchResults = instance.one('#fragmentSearchResults', content);

						var searchResults;

						if (fragmentSearchResults) {
							searchResults = instance.one('#' + STR_SEARCH_RESULTS_CONTAINER + repositoryId, entriesContainer);

							if (searchResults) {
								searchResults.empty();

								searchResults.plug(A.Plugin.ParseContent);

								searchResults.setContent(fragmentSearchResults.html());
							}
						}

						searchResults = instance.one('.local-search-results', content);

						if (searchResults) {
							var searchResultsContainer = instance.one('#' + STR_SEARCH_RESULTS_CONTAINER, content);

							if (!searchInfo) {
								entriesContainer.empty();
							}

							entriesContainer.plug(A.Plugin.ParseContent);

							entriesContainer.append(searchResultsContainer);
						}

						var repositorySearchResults = instance.one('.repository-search-results', content);

						if (repositorySearchResults) {
							var resultsContainer = instance.one('#' + STR_SEARCH_RESULTS_CONTAINER + repositoryId, entriesContainer);

							if (!resultsContainer) {
								resultsContainer = entriesContainer;
							}

							if (!searchInfo) {
								resultsContainer.empty();
							}

							resultsContainer.plug(A.Plugin.ParseContent);

							resultsContainer.append(repositorySearchResults);
						}

						var repositoryName = instance._getRepositoryName(repositoryId);

						if (repositoryName) {
							var tabLinkSelector = 'li[id$="' + Liferay.Util.toCharCode(repositoryName) + 'TabsId' + '"] a';

							var tabLink = entriesContainer.one(tabLinkSelector);

							if (tabLink) {
								tabLink.simulate(STR_CLICK);
							}
						}
					},

					_sendIOResponse: function(ioRequest, event) {
						var instance = this;

						var data = ioRequest.get(STR_DATA);
						var reponseData = ioRequest.get('responseData');

						var eventType = instance._eventDataRetrieveSuccess;

						if (event.type.indexOf('success') == -1) {
							eventType = instance._dataRetrieveFailure;
						}

						Liferay.fire(
							eventType,
							{
								data: data,
								responseData: reponseData
							}
						);
					},

					_sendMessage: function(type, message) {
						var instance = this;

						var output = instance._portletMessageContainer;

						output.removeClass('portlet-msg-error').removeClass('portlet-msg-success');

						output.addClass('portlet-msg-' + type);

						output.html(message);

						output.show();

						instance._entriesContainer.setContent(output);
					},

					_syncDisplayStyleToolbar: function(content) {
						var instance = this;

						var displayViews = instance._displayViews;

						var length = displayViews.length;

						if (length > 1) {
							var displayStyleToolbar = instance._displayStyleToolbarNode.getData(DISPLAY_STYLE_TOOLBAR);

							var displayStyle = instance._getDisplayStyle();

							for (var i = 0; i < length; i++) {
								displayStyleToolbar.item(i).StateInteraction.set(STR_ACTIVE, displayStyle === displayViews[i]);
							}
						}
					},

					_toggleEntriesSelection: function() {
						var instance = this;

						var documentContainer = A.one('.document-container');

						var selectAllCheckbox = instance._selectAllCheckbox;

						Liferay.Util.checkAll(documentContainer, instance.ns(STR_ROW_IDS_FOLDER_CHECKBOX), selectAllCheckbox, CSS_RESULT_ROW);
						Liferay.Util.checkAll(documentContainer, instance.ns(STR_ROW_IDS_FILE_ENTRY_CHECKBOX), selectAllCheckbox, CSS_RESULT_ROW);
						Liferay.Util.checkAll(documentContainer, instance.ns(STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX), selectAllCheckbox, CSS_RESULT_ROW);

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						if (!instance._getDisplayStyle(DISPLAY_STYLE_LIST)) {
							var documentDisplayStyle = A.all(CSS_DOCUMENT_DISPLAY_STYLE_SELECTABLE);

							documentDisplayStyle.toggleClass(CSS_SELECTED, instance._selectAllCheckbox.attr(ATTR_CHECKED));
						}
					},

					_toggleHovered: function(event) {
						var instance = this;

						if (!instance._getDisplayStyle(DISPLAY_STYLE_LIST)) {
							var documentDisplayStyle = event.target.ancestor(CSS_DOCUMENT_DISPLAY_STYLE);

							if (documentDisplayStyle) {
								documentDisplayStyle.toggleClass('hover', (event.type == STR_FOCUS));
							}
						}
					},

					_toggleSelected: function(node, preventUpdate) {
						var instance = this;

						if (instance._getDisplayStyle(DISPLAY_STYLE_LIST)) {
							if (!preventUpdate) {
								var input = node.one('input') || node;

								input.attr(ATTR_CHECKED, !node.attr(ATTR_CHECKED));
							}
						}
						else {
							node = node.ancestor(CSS_DOCUMENT_DISPLAY_STYLE) || node;

							if (!preventUpdate) {
								var selectElement = node.one('.document-selector :checkbox');

								selectElement.attr(ATTR_CHECKED, !selectElement.attr(ATTR_CHECKED));

								Liferay.Util.updateCheckboxValue(selectElement);
							}
						}

						node.toggleClass(CSS_SELECTED);
					},

					_tuneStateChangeParams: function(requestParams) {
						var instance = this;

						var entriesContainer = instance._entriesContainer;

						var namespacedShowRepositoryTabs = instance.ns(STR_SHOW_REPOSITORY_TABS);

						if (AObject.owns(requestParams, namespacedShowRepositoryTabs) &&
							!requestParams[namespacedShowRepositoryTabs] &&
							!entriesContainer.one('ul.aui-tabview-list')) {

							requestParams[namespacedShowRepositoryTabs] = true;

							requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						}

						var namespacedShowSearchInfo = instance.ns(STR_SHOW_SEARCH_INFO);

						if (AObject.owns(requestParams, namespacedShowSearchInfo) &&
							!requestParams[namespacedShowSearchInfo] &&
							!entriesContainer.one('.search-info')) {

							requestParams[namespacedShowSearchInfo] = true;

							requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						}
					},

					_toggleSyncNotification: function() {
						var instance = this;

						if (instance._syncMessage) {
							var entriesPaginatorState = instance._entryPaginator.get('state');

							var syncMessageBoundingBox = instance._syncMessage.get('boundingBox');

							syncMessageBoundingBox.toggleClass(CSS_SYNC_MESSAGE_HIDDEN, entriesPaginatorState.total <= 0);
						}
					},

					_unselectAllEntries: function() {
						var instance = this;

						instance._selectAllCheckbox.attr(CSS_SELECTED, false);

						instance._toggleEntriesSelection();
					},

					_updateFolderIdRedirectUrl: function(redirectUrl) {
						var instance = this;

						var config = instance._config;

						var currentFolderMatch = config.folderIdHashRegEx.exec(redirectUrl);

						if (currentFolderMatch) {
							var currentFolderId = currentFolderMatch[1];

							redirectUrl = redirectUrl.replace(
								config.folderIdRegEx,
								function(match, folderId) {
									return match.replace(folderId, currentFolderId);
								}
							);
						}

						return redirectUrl;
					},

					_updateSelectedEntriesStatus: function() {
						var instance = this;

						var selectedEntries = instance._selectedEntries;

						if (selectedEntries && selectedEntries.length) {
							var entriesContainer = instance._entriesContainer;

							A.each(
								selectedEntries,
								function(item, index, collection) {
									var entry = entriesContainer.one('input[value="' + item + '"]');

									if (entry) {
										instance._toggleSelected(entry);
									}
								}
							);

							selectedEntries.length = 0;
						}
					},

					_updatePaginatorValues: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						var entryStartEndParams = instance._getResultsStartEnd(instance._entryPaginator);
						var folderStartEndParams = instance._getResultsStartEnd(instance._folderPaginator);

						var customParams = {};

						if (requestParams) {
							if (!owns(requestParams, instance.ns(STR_ENTRY_START)) && !owns(requestParams, instance.ns(STR_ENTRY_END))) {
								customParams[instance.ns(STR_ENTRY_START)] = entryStartEndParams[0];
								customParams[instance.ns(STR_ENTRY_END)] = entryStartEndParams[1];
							}

							if (!owns(requestParams, instance.ns(STR_FOLDER_START)) && !owns(requestParams, instance.ns(STR_FOLDER_END))) {
								customParams[instance.ns(STR_FOLDER_START)] = folderStartEndParams[0];
								customParams[instance.ns(STR_FOLDER_END)] = folderStartEndParams[1];
							}

							if (!AObject.isEmpty(customParams)) {
								A.mix(requestParams, customParams, true);
							}
						}
					}
				}
			}
		);

		Liferay.Portlet.DocumentLibrary = DocumentLibrary;
	},
	'',
	{
		requires: ['aui-paginator', 'dd-constrain', 'dd-delegate', 'dd-drag', 'dd-drop', 'dd-proxy', 'event-simulate', 'liferay-history-manager', 'liferay-list-view', 'liferay-message', 'liferay-portlet-base', 'querystring-parse-simple']
	}
);