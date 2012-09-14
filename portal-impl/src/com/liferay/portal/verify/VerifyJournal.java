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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletPreferences;

/**
 * @author Alexander Chow
 * @author Shinn Lok
 */
public class VerifyJournal extends VerifyProcess {

	public static final long DEFAULT_GROUP_ID = 14;

	public static final int NUM_OF_ARTICLES = 5;

	@Override
	protected void doVerify() throws Exception {

		// Oracle new line

		verifyOracleNewLine();

		// Structures

		List<JournalStructure> structures =
			JournalStructureLocalServiceUtil.getStructures();

		for (JournalStructure structure : structures) {
			ResourceLocalServiceUtil.addResources(
				structure.getCompanyId(), 0, 0,
				JournalStructure.class.getName(), structure.getId(), false,
				false, false);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions verified for structures");
		}

		// Templates

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getTemplates();

		for (JournalTemplate template : templates) {
			ResourceLocalServiceUtil.addResources(
				template.getCompanyId(), 0, 0, JournalTemplate.class.getName(),
				template.getId(), false, false, false);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions verified for templates");
		}

		// Articles

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles();

		for (JournalArticle article : articles) {
			long groupId = article.getGroupId();
			String articleId = article.getArticleId();
			double version = article.getVersion();
			String structureId = article.getStructureId();

			if (article.getResourcePrimKey() <= 0) {
				article =
					JournalArticleLocalServiceUtil.checkArticleResourcePrimKey(
						groupId, articleId, version);
			}

			ResourceLocalServiceUtil.addResources(
				article.getCompanyId(), 0, 0, JournalArticle.class.getName(),
				article.getResourcePrimKey(), false, false, false);

			try {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
					JournalArticle.class.getName(),
					article.getResourcePrimKey());

				if ((article.getStatus() == WorkflowConstants.STATUS_DRAFT) &&
					(article.getVersion() ==
						JournalArticleConstants.VERSION_DEFAULT)) {

					AssetEntryLocalServiceUtil.updateEntry(
						assetEntry.getClassName(), assetEntry.getClassPK(),
						null, assetEntry.isVisible());
				}
			}
			catch (NoSuchEntryException nsee) {
				try {
					JournalArticleLocalServiceUtil.updateAsset(
						article.getUserId(), article, null, null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update asset for article " +
								article.getId() + ": " + e.getMessage());
					}
				}
			}

			String content = GetterUtil.getString(article.getContent());

			String newContent = HtmlUtil.replaceMsWordCharacters(content);

			if (Validator.isNotNull(structureId)) {
				/*JournalStructure structure =
					JournalStructureLocalServiceUtil.getStructure(
						groupId, structureId);

				newContent = JournalUtil.removeOldContent(
					newContent, structure.getXsd());*/
			}

			if (!content.equals(newContent)) {
				JournalArticleLocalServiceUtil.updateContent(
					groupId, articleId, version, newContent);
			}

			JournalArticleLocalServiceUtil.checkStructure(
				groupId, articleId, version);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions and assets verified for articles");
		}

		// Content searches

		verifyContentSearch();
	}

	protected void verifyContentSearch() throws Exception {
		List<JournalContentSearch> contentSearches =
			JournalContentSearchLocalServiceUtil.getArticleContentSearches();

		Set<String> portletIds = new HashSet<String>();

		for (JournalContentSearch contentSearch : contentSearches) {
			portletIds.add(contentSearch.getPortletId());
		}

		for (String portletId : portletIds) {
			verifyContentSearch(portletId);
		}
	}

	protected void verifyContentSearch(String portletId) throws Exception {
		List<JournalContentSearch> contentSearches =
			JournalContentSearchLocalServiceUtil.getPortletContentSearches(
				portletId);

		if (contentSearches.size() <= 1) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select preferences from PortletPreferences where portletId " +
					"= ?");

			ps.setString(1, portletId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String xml = rs.getString("preferences");

				PortletPreferences portletPreferences =
					PortletPreferencesFactoryUtil.fromDefaultXML(xml);

				String articleId = portletPreferences.getValue(
					"articleId", null);

				JournalContentSearch contentSearch = contentSearches.get(1);

				JournalContentSearchLocalServiceUtil.updateContentSearch(
					contentSearch.getGroupId(), contentSearch.isPrivateLayout(),
					contentSearch.getLayoutId(), contentSearch.getPortletId(),
					articleId, true);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void verifyOracleNewLine() throws Exception {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_ORACLE)) {
			return;
		}

		// This is a workaround for a limitation in Oracle sqlldr's inability
		// insert new line characters for long varchar columns. See
		// http://forums.liferay.com/index.php?showtopic=2761&hl=oracle for more
		// information. Check several articles because some articles may not
		// have new lines.

		boolean checkNewLine = false;

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles(
				DEFAULT_GROUP_ID, 0, NUM_OF_ARTICLES);

		for (JournalArticle article : articles) {
			String content = article.getContent();

			if ((content != null) && content.contains("\\n")) {
				articles = JournalArticleLocalServiceUtil.getArticles(
					DEFAULT_GROUP_ID);

				for (int j = 0; j < articles.size(); j++) {
					article = articles.get(j);

					JournalArticleLocalServiceUtil.checkNewLine(
						article.getGroupId(), article.getArticleId(),
						article.getVersion());
				}

				checkNewLine = true;

				break;
			}
		}

		// Only process this once

		if (!checkNewLine) {
			if (_log.isInfoEnabled()) {
				_log.debug("Do not fix oracle new line");
			}

			return;
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Fix oracle new line");
			}
		}

		List<JournalStructure> structures =
			JournalStructureLocalServiceUtil.getStructures(
				DEFAULT_GROUP_ID, 0, 1);

		if (structures.size() == 1) {
			JournalStructure structure = structures.get(0);

			String xsd = structure.getXsd();

			if ((xsd != null) && xsd.contains("\\n")) {
				structures = JournalStructureLocalServiceUtil.getStructures(
					DEFAULT_GROUP_ID);

				for (int i = 0; i < structures.size(); i++) {
					structure = structures.get(i);

					JournalStructureLocalServiceUtil.checkNewLine(
						structure.getGroupId(), structure.getStructureId());
				}
			}
		}

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getTemplates(
				DEFAULT_GROUP_ID, 0, 1);

		if (templates.size() == 1) {
			JournalTemplate template = templates.get(0);

			String xsl = template.getXsl();

			if ((xsl != null) && xsl.contains("\\n")) {
				templates = JournalTemplateLocalServiceUtil.getTemplates(
					DEFAULT_GROUP_ID);

				for (int i = 0; i < templates.size(); i++) {
					template = templates.get(i);

					JournalTemplateLocalServiceUtil.checkNewLine(
						template.getGroupId(), template.getTemplateId());
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyJournal.class);

}