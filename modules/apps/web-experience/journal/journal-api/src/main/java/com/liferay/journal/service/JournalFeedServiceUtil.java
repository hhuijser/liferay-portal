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

package com.liferay.journal.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for JournalFeed. This utility wraps
 * {@link com.liferay.journal.service.impl.JournalFeedServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedService
 * @see com.liferay.journal.service.base.JournalFeedServiceBaseImpl
 * @see com.liferay.journal.service.impl.JournalFeedServiceImpl
 * @generated
 */
@ProviderType
public class JournalFeedServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.journal.service.impl.JournalFeedServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.journal.model.JournalFeed addFeed(long groupId,
		String feedId, boolean autoFeedId, String name, String description,
		String ddmStructureKey, String ddmTemplateKey,
		String ddmRendererTemplateKey, int delta, String orderByCol,
		String orderByType, String targetLayoutFriendlyUrl,
		String targetPortletId, String contentField, String feedType,
		double feedVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFeed(groupId, feedId, autoFeedId, name, description,
			ddmStructureKey, ddmTemplateKey, ddmRendererTemplateKey, delta,
			orderByCol, orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedType, feedVersion, serviceContext);
	}

	public static void deleteFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFeed(feedId);
	}

	public static void deleteFeed(long groupId, String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFeed(groupId, feedId);
	}

	public static com.liferay.journal.model.JournalFeed getFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFeed(feedId);
	}

	public static com.liferay.journal.model.JournalFeed getFeed(long groupId,
		String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFeed(groupId, feedId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.journal.model.JournalFeed updateFeed(
		long groupId, String feedId, String name, String description,
		String ddmStructureKey, String ddmTemplateKey,
		String ddmRendererTemplateKey, int delta, String orderByCol,
		String orderByType, String targetLayoutFriendlyUrl,
		String targetPortletId, String contentField, String feedType,
		double feedVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFeed(groupId, feedId, name, description,
			ddmStructureKey, ddmTemplateKey, ddmRendererTemplateKey, delta,
			orderByCol, orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedType, feedVersion, serviceContext);
	}

	public static JournalFeedService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<JournalFeedService, JournalFeedService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(JournalFeedService.class);

		ServiceTracker<JournalFeedService, JournalFeedService> serviceTracker = new ServiceTracker<JournalFeedService, JournalFeedService>(bundle.getBundleContext(),
				JournalFeedService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}