/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.documentlibrary.sharepoint;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.sharepoint.BaseSharepointStorageImpl;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;
import com.liferay.portal.sharepoint.Tree;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * <a href="DLSharepointStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class DLSharepointStorageImpl extends BaseSharepointStorageImpl {

	public void addDocumentElements(
			SharepointRequest sharepointRequest, Element element)
		throws Exception {

		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);

		if (parentFolderId == DLFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		List<DLFileEntry> fileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(parentFolderId);

		for (DLFileEntry fileEntry : fileEntries) {
			StringBuilder sb = new StringBuilder();

			sb.append(parentFolderPath);
			sb.append(StringPool.SLASH);
			sb.append(fileEntry.getTitleWithExtension());

			addDocumentElement(
				element, sb.toString(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(), fileEntry.getUserName());
		}
	}

	public void createFolder(SharepointRequest sharepointRequest)
		throws Exception {

		String folderPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(folderPath);

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);
		String folderName = getResourceName(folderPath);
		String description = StringPool.BLANK;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		DLFolderServiceUtil.addFolder(
			groupId, parentFolderId, folderName, description, serviceContext);
	}

	public InputStream getDocumentInputStream(
			SharepointRequest sharepointRequest)
		throws Exception {

		DLFileEntry fileEntry = getFileEntry(sharepointRequest);

		return DLFileEntryLocalServiceUtil.getFileAsStream(
			sharepointRequest.getCompanyId(), sharepointRequest.getUserId(),
			fileEntry.getFolderId(), fileEntry.getName());
	}

	public Tree getDocumentTree(SharepointRequest sharepointRequest)
		throws Exception {

		String documentPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(documentPath);

		DLFileEntry fileEntry = getFileEntry(sharepointRequest);

		return getFileEntryTree(fileEntry, parentFolderPath);
	}

	public Tree getDocumentsTree(SharepointRequest sharepointRequest)
		throws Exception {

		Tree documentsTree = new Tree();

		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);

		if (parentFolderId != DLFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
			List<DLFileEntry> fileEntries =
				DLFileEntryLocalServiceUtil.getFileEntries(parentFolderId);

			for (DLFileEntry fileEntry : fileEntries) {
				documentsTree.addChild(
					getFileEntryTree(fileEntry, parentFolderPath));
			}
		}

		return documentsTree;
	}

	public Tree getFolderTree(SharepointRequest sharepointRequest)
		throws Exception {

		String folderPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(folderPath);

		long groupId = SharepointUtil.getGroupId(folderPath);
		long folderId = getLastFolderId(
			groupId, folderPath, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);

		DLFolder folder = DLFolderServiceUtil.getFolder(folderId);

		return getFolderTree(folder, parentFolderPath);
	}

	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception {

		Tree foldersTree = new Tree();

		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);

		List<DLFolder> folders = DLFolderServiceUtil.getFolders(
			groupId, parentFolderId);

		for (DLFolder folder : folders) {
			foldersTree.addChild(getFolderTree(folder, parentFolderPath));
		}

		foldersTree.addChild(getFolderTree(parentFolderPath));

		return foldersTree;
	}

	public void getParentFolderIds(
			long groupId, String path, List<Long> folderIds)
		throws Exception {

		String[] pathArray = SharepointUtil.getPathArray(path);

		if (pathArray.length == 0) {
			return;
		}

		long parentFolderId = folderIds.get(folderIds.size() - 1);
		long folderId = DLFolderServiceUtil.getFolderId(
			groupId, parentFolderId, pathArray[0]);

		folderIds.add(folderId);

		if (pathArray.length > 1) {
			path = removeFoldersFromPath(path, 1);

			getParentFolderIds(groupId, path, folderIds);
		}
	}

	public Tree[] moveDocument(SharepointRequest sharepointRequest)
		throws Exception {

		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);

		DLFolder folder = null;
		DLFileEntry fileEntry = null;

		try {
			long parentFolderId = getLastFolderId(
				groupId, parentFolderPath,
				DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);

			folder = DLFolderServiceUtil.getFolder(parentFolderId);
		}
		catch (Exception e1) {
			if (e1 instanceof NoSuchFolderException) {
				try {
					fileEntry = getFileEntry(sharepointRequest);
				}
				catch (Exception e2) {
				}
			}
		}

		Tree movedDocsTree = new Tree();
		Tree movedDirsTree = new Tree();

		String newPath = sharepointRequest.getParameterValue("newUrl");
		String newParentFolderPath = getParentFolderPath(newPath);

		ServiceContext serviceContext = new ServiceContext();

		long newGroupId = SharepointUtil.getGroupId(newParentFolderPath);

		long newParentFolderId = getLastFolderId(
			newGroupId, newParentFolderPath,
			DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);

		String newName = getResourceName(newPath);

		if (fileEntry != null) {
			long folderId = fileEntry.getFolderId();
			String name = fileEntry.getName();
			String description = fileEntry.getDescription();
			String[] tagsEntries = TagsEntryLocalServiceUtil.getEntryNames(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());
			serviceContext.setTagsEntries(tagsEntries);
			String extraSettings = fileEntry.getExtraSettings();

			InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(
				sharepointRequest.getCompanyId(), sharepointRequest.getUserId(),
				fileEntry.getFolderId(), fileEntry.getName());

			byte[] bytes = FileUtil.getBytes(is);

			fileEntry = DLFileEntryServiceUtil.updateFileEntry(
				folderId, newParentFolderId, name, newName, newName,
				description, extraSettings, bytes, serviceContext);

			Tree documentTree = getFileEntryTree(
				fileEntry, newParentFolderPath);

			movedDocsTree.addChild(documentTree);
		}
		else if (folder != null) {
			long folderId = folder.getFolderId();
			String description = folder.getDescription();

			folder = DLFolderServiceUtil.updateFolder(
				folderId, newParentFolderId, newName, description,
				serviceContext);

			Tree folderTree = getFolderTree(folder, newParentFolderPath);

			movedDirsTree.addChild(folderTree);
		}

		return new Tree[] {movedDocsTree, movedDirsTree};
	}

	public void putDocument(SharepointRequest sharepointRequest)
		throws Exception {

		String documentPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(documentPath);

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);
		String name = getResourceName(documentPath);
		String title = name;
		String description = StringPool.BLANK;
		String extraSettings = StringPool.BLANK;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			DLFileEntry fileEntry = getFileEntry(sharepointRequest);

			name = fileEntry.getName();
			description = fileEntry.getDescription();
			String[] tagsEntries = TagsEntryLocalServiceUtil.getEntryNames(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());
			serviceContext.setTagsEntries(tagsEntries);
			extraSettings = fileEntry.getExtraSettings();

			DLFileEntryServiceUtil.updateFileEntry(
				parentFolderId, parentFolderId, name, title, title,
				description, extraSettings, sharepointRequest.getBytes(),
				serviceContext);
		}
		catch (NoSuchFileEntryException nsfee) {
			File file = FileUtil.createTempFile(FileUtil.getExtension(name));

			FileUtil.write(file, sharepointRequest.getBytes());

			DLFileEntryServiceUtil.addFileEntry(
				parentFolderId, name, title, description, extraSettings, file,
				serviceContext);
		}
	}

	public Tree[] removeDocument(SharepointRequest sharepointRequest) {
		String parentFolderPath = sharepointRequest.getRootPath();

		long groupId = SharepointUtil.getGroupId(parentFolderPath);

		DLFolder folder = null;
		DLFileEntry fileEntry = null;

		try {
			long parentFolderId = getLastFolderId(
				groupId, parentFolderPath,
				DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);

			folder = DLFolderServiceUtil.getFolder(parentFolderId);
		}
		catch (Exception e1) {
			if (e1 instanceof NoSuchFolderException) {
				try {
					fileEntry = getFileEntry(sharepointRequest);
				}
				catch (Exception e2) {
				}
			}
		}

		Tree documentTree = new Tree();

		Tree removedDocsTree = new Tree();
		Tree failedDocsTree = new Tree();

		Tree folderTree = new Tree();

		Tree removedDirsTree = new Tree();
		Tree failedDirsTree = new Tree();

		if (fileEntry != null) {
			try {
				documentTree = getFileEntryTree(fileEntry, parentFolderPath);

				DLFileEntryServiceUtil.deleteFileEntry(
					fileEntry.getFolderId(), fileEntry.getName());

				removedDocsTree.addChild(documentTree);
			}
			catch (Exception e1) {
				try {
					failedDocsTree.addChild(documentTree);
				}
				catch (Exception e2) {
				}
			}
		}
		else if (folder != null) {
			try {
				folderTree = getFolderTree(folder, parentFolderPath);

				DLFolderServiceUtil.deleteFolder(folder.getFolderId());

				removedDirsTree.addChild(folderTree);
			}
			catch (Exception e1) {
				try {
					failedDirsTree.addChild(folderTree);
				}
				catch (Exception e2) {
				}
			}
		}

		return new Tree[] {
			removedDocsTree, removedDirsTree, failedDocsTree, failedDirsTree};
	}

	protected Tree getFolderTree(DLFolder folder, String parentFolderPath) {
		StringBuilder sb = new StringBuilder();

		sb.append(parentFolderPath);
		sb.append(StringPool.SLASH);
		sb.append(folder.getName());

		return getFolderTree(
			sb.toString(), folder.getCreateDate(), folder.getModifiedDate(),
			folder.getLastPostDate());
	}

	protected DLFileEntry getFileEntry(SharepointRequest sharepointRequest)
		throws Exception {

		String documentPath = sharepointRequest.getRootPath();
		String parentFolderPath = getParentFolderPath(documentPath);

		long groupId = SharepointUtil.getGroupId(parentFolderPath);
		long parentFolderId = getLastFolderId(
			groupId, parentFolderPath, DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);
		String title = getResourceName(documentPath);

		return DLFileEntryServiceUtil.getFileEntryByTitle(
			parentFolderId, title);
	}

	protected Tree getFileEntryTree(
		DLFileEntry fileEntry, String parentFolderPath) {

		StringBuilder sb = new StringBuilder();

		sb.append(parentFolderPath);
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getTitleWithExtension());

		return getDocumentTree(
			sb.toString(), fileEntry.getCreateDate(),
			fileEntry.getModifiedDate(), fileEntry.getSize(),
			fileEntry.getUserName(), fileEntry.getVersion());
	}

}