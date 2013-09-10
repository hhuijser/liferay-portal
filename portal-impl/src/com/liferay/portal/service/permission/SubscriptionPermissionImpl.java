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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.comparator.SubscriptionClassNameIdComparator;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.service.permission.MBPermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

import java.util.List;

/**
 * @author Mate Thurzo
 * @author Raymond Augé
 */
public class SubscriptionPermissionImpl implements SubscriptionPermission {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #check(PermissionChecker,
	 *             String, long, String, long)}
	 */
	@Override
	public void check(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException {

		check(permissionChecker, className, classPK, null, 0);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, subscriptionClassName, subscriptionClassPK,
				inferredClassName, inferredClassPK)) {

			throw new PrincipalException();
		}
	}

	@Override
	public void cleanUpUserSubscriptions(long userId)
		throws PortalException, SystemException {

		User user = UserLocalServiceUtil.getUserById(userId);

		PermissionChecker permissionChecker = null;

		try {
			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		}
		catch (Exception e) {
			throw new PrincipalException(e);
		}

		int subscriptionsCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(userId);

		List<Subscription> subscriptions =
			SubscriptionLocalServiceUtil.getUserSubscriptions(
				userId, 0, subscriptionsCount,
				new SubscriptionClassNameIdComparator(true));

		for (Subscription subscription : subscriptions) {
			if (!contains(
					permissionChecker, subscription.getClassName(),
					subscription.getClassPK(), null, 0)) {

				SubscriptionLocalServiceUtil.deleteSubscription(
					subscription.getSubscriptionId());
			}
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             String, long, String, long)}
	 */
	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String className, long classPK)
		throws PortalException, SystemException {

		return contains(permissionChecker, className, classPK, null, 0);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, String inferredClassName,
			long inferredClassPK)
		throws PortalException, SystemException {

		if (subscriptionClassName == null) {
			return false;
		}

		try {
			MBDiscussion discussion =
				MBDiscussionLocalServiceUtil.getDiscussion(
					subscriptionClassName, subscriptionClassPK);

			MBThread thread = MBThreadLocalServiceUtil.fetchThread(
				discussion.getThreadId());

			return hasPermission(
				permissionChecker, subscriptionClassName, subscriptionClassPK,
				thread);
		}
		catch (NoSuchDiscussionException nsde) {
		}

		if (subscriptionClassName.equals(Folder.class.getName())) {
			DLFolder dlFolder = DLFolderLocalServiceUtil.fetchDLFolder(
				subscriptionClassPK);

			if (dlFolder != null) {
				return DLFolderPermission.contains(
					permissionChecker, dlFolder, ActionKeys.VIEW);
			}

			return DLPermission.contains(
				permissionChecker, subscriptionClassPK, ActionKeys.VIEW);
		}

		if (Validator.isNotNull(inferredClassName)) {
			Boolean hasPermission = hasPermission(
				permissionChecker, inferredClassName, inferredClassPK,
				ActionKeys.VIEW);

			if ((hasPermission == null) || !hasPermission) {
				return false;
			}
		}

		Boolean hasPermission = hasPermission(
			permissionChecker, subscriptionClassName, subscriptionClassPK,
			ActionKeys.SUBSCRIBE);

		if (hasPermission != null) {
			return hasPermission;
		}

		return true;
	}

	protected boolean hasPermission(
			PermissionChecker permissionChecker, String subscriptionClassName,
			long subscriptionClassPK, MBThread thread)
		throws PortalException, SystemException {

		if (subscriptionClassName.equals(Layout.class.getName())) {
			return LayoutPermissionUtil.contains(
				permissionChecker, subscriptionClassPK, ActionKeys.VIEW);
		}
		else if (subscriptionClassName.equals(
					WorkflowInstance.class.getName())) {

			return permissionChecker.hasPermission(
				thread.getGroupId(), PortletKeys.WORKFLOW_DEFINITIONS,
				thread.getGroupId(), ActionKeys.VIEW);
		}

		return MBDiscussionPermission.contains(
			permissionChecker, thread.getCompanyId(), thread.getGroupId(),
			subscriptionClassName, subscriptionClassPK, thread.getUserId(),
			ActionKeys.VIEW);
	}

	protected Boolean hasPermission(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException, SystemException {

		if (className.equals(BlogsEntry.class.getName())) {
			return BlogsPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return JournalPermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBCategory.class.getName())) {
			Group group = GroupLocalServiceUtil.fetchGroup(classPK);

			if (group == null) {
				return MBCategoryPermission.contains(
					permissionChecker, classPK, actionId);
			}

			return MBPermission.contains(permissionChecker, classPK, actionId);
		}
		else if (className.equals(MBThread.class.getName())) {
			MBThread mbThread = MBThreadLocalServiceUtil.fetchThread(classPK);

			if (mbThread == null) {
				return false;
			}

			return MBMessagePermission.contains(
				permissionChecker, mbThread.getRootMessageId(), actionId);
		}
		else if (className.equals(WikiNode.class.getName())) {
			return WikiNodePermission.contains(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(WikiPage.class.getName())) {
			return WikiPagePermission.contains(
				permissionChecker, classPK, actionId);
		}

		return null;
	}

}