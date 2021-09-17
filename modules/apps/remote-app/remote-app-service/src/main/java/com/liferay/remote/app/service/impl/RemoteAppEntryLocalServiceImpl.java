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

package com.liferay.remote.app.service.impl;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.deployer.RemoteAppEntryDeployer;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementCSSURLsException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementHTMLElementNameException;
import com.liferay.remote.app.exception.RemoteAppEntryCustomElementURLsException;
import com.liferay.remote.app.exception.RemoteAppEntryIFrameURLException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.base.RemoteAppEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.remote.app.model.RemoteAppEntry",
	service = AopService.class
)
public class RemoteAppEntryLocalServiceImpl
	extends RemoteAppEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry addCustomElementRemoteAppEntry(
			long userId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			Map<Locale, String> nameMap)
		throws PortalException {

		customElementCSSURLs = StringUtil.trim(customElementCSSURLs);
		customElementHTMLElementName = StringUtil.trim(
			customElementHTMLElementName);
		customElementURLs = StringUtil.trim(customElementURLs);

		_validateCustomElement(
			customElementCSSURLs, customElementHTMLElementName,
			customElementURLs);

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		remoteAppEntry.setCompanyId(user.getCompanyId());
		remoteAppEntry.setUserId(user.getUserId());
		remoteAppEntry.setUserName(user.getFullName());

		remoteAppEntry.setCustomElementCSSURLs(customElementCSSURLs);
		remoteAppEntry.setCustomElementHTMLElementName(
			customElementHTMLElementName);
		remoteAppEntry.setCustomElementURLs(customElementURLs);
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT);

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry addIFrameRemoteAppEntry(
			long userId, String iFrameURL, Map<Locale, String> nameMap)
		throws PortalException {

		iFrameURL = StringUtil.trim(iFrameURL);

		_validateIFrameURL(iFrameURL);

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		remoteAppEntry.setCompanyId(user.getCompanyId());
		remoteAppEntry.setUserId(user.getUserId());
		remoteAppEntry.setUserName(user.getFullName());

		remoteAppEntry.setIFrameURL(iFrameURL);
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setType(RemoteAppConstants.TYPE_IFRAME);

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public RemoteAppEntry deleteRemoteAppEntry(long remoteAppEntryId)
		throws PortalException {

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		return deleteRemoteAppEntry(remoteAppEntry);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public RemoteAppEntry deleteRemoteAppEntry(RemoteAppEntry remoteAppEntry)
		throws PortalException {

		remoteAppEntryPersistence.remove(remoteAppEntry);

		remoteAppEntryLocalService.undeployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Clusterable
	@Override
	public void deployRemoteAppEntry(RemoteAppEntry remoteAppEntry) {
		undeployRemoteAppEntry(remoteAppEntry);

		_serviceRegistrations.put(
			remoteAppEntry.getRemoteAppEntryId(),
			_remoteAppEntryDeployer.deploy(remoteAppEntry));
	}

	@Override
	public List<RemoteAppEntry> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, start, end, sort);

		Indexer<RemoteAppEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(RemoteAppEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<RemoteAppEntry> remoteAppEntries = _getRemoteAppEntries(hits);

			if (remoteAppEntries != null) {
				return remoteAppEntries;
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Override
	public int searchCount(long companyId, String keywords)
		throws PortalException {

		Indexer<RemoteAppEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(RemoteAppEntry.class);

		SearchContext searchContext = _buildSearchContext(
			companyId, keywords, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		super.setAopProxy(aopProxy);

		List<RemoteAppEntry> remoteAppEntries =
			remoteAppEntryLocalService.getRemoteAppEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (RemoteAppEntry remoteAppEntry : remoteAppEntries) {
			deployRemoteAppEntry(remoteAppEntry);
		}
	}

	@Clusterable
	@Override
	public void undeployRemoteAppEntry(RemoteAppEntry remoteAppEntry) {
		ServiceRegistration<Portlet> serviceRegistration =
			_serviceRegistrations.remove(remoteAppEntry.getRemoteAppEntryId());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry updateCustomElementRemoteAppEntry(
			long remoteAppEntryId, String customElementCSSURLs,
			String customElementHTMLElementName, String customElementURLs,
			Map<Locale, String> nameMap)
		throws PortalException {

		customElementCSSURLs = StringUtil.trim(customElementCSSURLs);
		customElementHTMLElementName = StringUtil.trim(
			customElementHTMLElementName);
		customElementURLs = StringUtil.trim(customElementURLs);

		_validateCustomElement(
			customElementCSSURLs, customElementHTMLElementName,
			customElementURLs);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		remoteAppEntry.setCustomElementCSSURLs(customElementCSSURLs);
		remoteAppEntry.setCustomElementHTMLElementName(
			customElementHTMLElementName);
		remoteAppEntry.setCustomElementURLs(customElementURLs);
		remoteAppEntry.setNameMap(nameMap);

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry updateIFrameRemoteAppEntry(
			long remoteAppEntryId, String iFrameURL,
			Map<Locale, String> nameMap)
		throws PortalException {

		iFrameURL = StringUtil.trim(iFrameURL);

		_validateIFrameURL(iFrameURL);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		remoteAppEntry.setIFrameURL(iFrameURL);
		remoteAppEntry.setNameMap(nameMap);

		remoteAppEntry = remoteAppEntryPersistence.update(remoteAppEntry);

		remoteAppEntryLocalService.deployRemoteAppEntry(remoteAppEntry);

		return remoteAppEntry;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private SearchContext _buildSearchContext(
		long companyId, String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).put(
				Field.URL, keywords
			).build());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setKeywords(keywords);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	private List<RemoteAppEntry> _getRemoteAppEntries(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<RemoteAppEntry> remoteAppEntries = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long remoteAppEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			RemoteAppEntry remoteAppEntry =
				remoteAppEntryPersistence.fetchByPrimaryKey(remoteAppEntryId);

			if (remoteAppEntry == null) {
				remoteAppEntries = null;

				Indexer<RemoteAppEntry> indexer =
					IndexerRegistryUtil.getIndexer(RemoteAppEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else {
				remoteAppEntries.add(remoteAppEntry);
			}
		}

		return remoteAppEntries;
	}

	private void _validateCustomElement(
			String customElementCSSURLs, String customElementHTMLElementName,
			String customElementURLs)
		throws PortalException {

		if (Validator.isNull(customElementCSSURLs)) {

			// TODO Allow this to be empty. But if it is not empty, make sure
			// each line is a valid URL.

			throw new RemoteAppEntryCustomElementCSSURLsException();
		}

		if (Validator.isNull(customElementHTMLElementName)) {
			throw new RemoteAppEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name is null");
		}

		char[] customElementHTMLElementNameCharArray =
			customElementHTMLElementName.toCharArray();

		if (!Validator.isChar(customElementHTMLElementNameCharArray[0]) ||
			!Character.isLowerCase(customElementHTMLElementNameCharArray[0])) {

			throw new RemoteAppEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name must start with a " +
					"lowercase letter");
		}

		boolean containsDash = false;

		for (char c : customElementHTMLElementNameCharArray) {
			if (c == CharPool.DASH) {
				containsDash = true;
			}

			if ((Validator.isChar(c) && Character.isLowerCase(c)) ||
				(c == CharPool.DASH)) {
			}
			else {
				throw new RemoteAppEntryCustomElementHTMLElementNameException(
					"Custom element HTML element name must only contain " +
						"lowercase letters or hyphens");
			}
		}

		if (!containsDash) {
			throw new RemoteAppEntryCustomElementHTMLElementNameException(
				"Custom element HTML element name must contain at least one " +
					"hyphen");
		}

		if (Validator.isNull(customElementURLs)) {

			// TODO Make sure each line is a valid URL and that there is at
			// least one URL

			throw new RemoteAppEntryCustomElementURLsException();
		}
	}

	private void _validateIFrameURL(String iFrameURL) throws PortalException {
		if (!Validator.isUrl(iFrameURL)) {
			throw new RemoteAppEntryIFrameURLException(
				"Invalid IFrame URL " + iFrameURL);
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private RemoteAppEntryDeployer _remoteAppEntryDeployer;

	private final Map<Long, ServiceRegistration<Portlet>>
		_serviceRegistrations = new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}