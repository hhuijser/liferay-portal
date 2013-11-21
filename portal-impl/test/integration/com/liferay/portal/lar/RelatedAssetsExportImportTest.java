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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 * @author Andrew Betts
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class RelatedAssetsExportImportTest
	extends AssetPublisherExportImportTest {

	@Override
	public String getPortletId() throws Exception {
		return PortletKeys.RELATED_ASSETS +
			PortletConstants.INSTANCE_SEPARATOR +
				ServiceTestUtil.randomString();
	}

	@Test
	public void testOneAssetVocabularyAndAssetCategory() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				serviceContext.getUserId(), "TestVocabulary", serviceContext);

		AssetCategory assetCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				serviceContext.getUserId(), "TestCategory",
				assetVocabulary.getVocabularyId(), serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put("queryName0", new String[] {"assetCategories"});
		preferenceMap.put(
			"queryValues0",
			new String[] {StringUtil.valueOf(assetCategory.getCategoryId())});
		preferenceMap.put(
			"assetVocabularyId",
			new String[] {StringUtil.valueOf(
				assetVocabulary.getVocabularyId())});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long importedAssetVocabularyId = GetterUtil.getLong(
			portletPreferences.getValue("assetVocabularyId", null));

		Assert.assertNotNull(
			"Portlet preference \"assetVocabularyId\" is null",
			importedAssetVocabularyId);

		AssetVocabulary importedVocabulary =
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
				importedAssetVocabularyId);

		Assert.assertNotNull(
			"Vocabulary " + importedAssetVocabularyId + " does not exist",
			importedVocabulary);

		long importedCategoryId = GetterUtil.getLong(
			portletPreferences.getValue("queryValues0", null));

		Assert.assertNotNull(
			"Portlet preference for query by Category is null",
			importedCategoryId);

		Assert.assertNotEquals(
			"Portlet preference for query by Category was not updated",
			assetCategory.getCategoryId(), importedCategoryId);

		AssetCategory importedCategory =
			AssetCategoryLocalServiceUtil.fetchCategory(importedCategoryId);

		Assert.assertNotNull(
			"Category " + importedCategoryId + " does not exist",
			importedCategory);

		AssetCategoryLocalServiceUtil.deleteAssetCategory(assetCategory);

		AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(assetVocabulary);
	}

}