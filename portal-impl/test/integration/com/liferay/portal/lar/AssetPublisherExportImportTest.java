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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Eduardo Garcia
 */
@PrepareForTest({PortletLocalServiceUtil.class})

@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetPublisherExportImportTest extends BaseExportImportTestCase {

	@Before
	public void setUp() throws Exception {

		_group = ServiceTestUtil.addGroup();

		_layout = ServiceTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		// Deletion of the layout to ensure scenario where layoutIds don't match

		LayoutLocalServiceUtil.deleteLayout(
			_layout, true, new ServiceContext());

		_layout = ServiceTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		//FinderCacheUtil.clearCache();

	}

	@Test
	public void testDefaultScopeId() throws Exception {
		Map<String, String> preferences = new HashMap<String, String>();

		preferences.put("defaultScopeId", Boolean.TRUE.toString());

		javax.portlet.PortletPreferences newPreferences =
			getImportedPortletPreferences(_layout, preferences);

		Assert.assertEquals(
			Boolean.TRUE.toString(),
			newPreferences.getValue("defaultScopeId", null));
		Assert.assertEquals(null, newPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, newPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLayoutScopeId() throws Exception {
		Map<String, String> preferences = new HashMap<String, String>();

		getScopeGroup(TestPropsValues.getUserId(), _layout);

		preferences.put("defaultScopeId", "Layout_" + _layout.getLayoutId());

		javax.portlet.PortletPreferences newPreferences =
			getImportedPortletPreferences(_layout, preferences);

		Assert.assertEquals(
			"Layout_" + _importedLayout.getLayoutId(),
			newPreferences.getValue("defaultScopeId", null));
		Assert.assertEquals(null, newPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, newPreferences.getValue("scopeId", null));
	}

	@Test
	public void testSeveralLayoutScopeIds() throws Exception {
		Layout secondLayout = ServiceTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		getScopeGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String> preferences = new HashMap<String, String>();

		getScopeGroup(TestPropsValues.getUserId(), _layout);

		preferences.put("defaultScopeId", Boolean.FALSE.toString());
		preferences.put(
			"scopeIds", "Layout_" + _layout.getLayoutId() + ",Layout_" +
			secondLayout.getLayoutId());

		javax.portlet.PortletPreferences newPreferences =
			getImportedPortletPreferences(_layout, preferences);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), _importedGroup.getGroupId(),
				_importedLayout.isPrivateLayout());

		Assert.assertEquals(
			Boolean.FALSE.toString(),
			newPreferences.getValue("defaultScopeId", null));

		Assert.assertEquals(
			"Layout_" + _importedLayout.getLayoutId() + ",Layout_" +
				importedSecondLayout.getLayoutId(),
			newPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, newPreferences.getValue("scopeId", null));
	}

	protected String addAssetPublisherPortletToLayout(
			long userId, Layout layout, String columnId,
			Map<String, String> preferences)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String assetPublisherPortletId = layoutTypePortlet.addPortletId(
			userId, PortletKeys.ASSET_PUBLISHER, columnId, -1);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		javax.portlet.PortletPreferences prefs = getPortletPreferences(
			layout.getCompanyId(), layout.getPlid(), assetPublisherPortletId);

		for (String key : preferences.keySet()) {
			prefs.setValue(key, preferences.get(key));
		}

		updatePortletPreferences(
			layout.getPlid(), assetPublisherPortletId, prefs);

		return assetPublisherPortletId;
	}

	protected javax.portlet.PortletPreferences getImportedPortletPreferences(
			Layout layout, Map<String, String> preferences)
		throws Exception {

		String assetPublisherPortletId = addAssetPublisherPortletToLayout(
			TestPropsValues.getUserId(), _layout, "column-1", preferences);

		// Export Site LAR

		Map<String, String[]> parameterMap =  new HashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});

		File larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			layout.getGroupId(), layout.isPrivateLayout(), null, parameterMap,
			null, null);

		_importedGroup = ServiceTestUtil.addGroup();

		// Import Site LAR

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), _importedGroup.getGroupId(),
			layout.isPrivateLayout(), parameterMap, larFile);

		_importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), _importedGroup.getGroupId(),
			layout.isPrivateLayout());

		Assert.assertNotNull(_importedLayout);

		return getPortletPreferences(
			_importedLayout.getCompanyId(), _importedLayout.getPlid(),
			assetPublisherPortletId);
	}

	protected javax.portlet.PortletPreferences getPortletPreferences(
			long companyId, long plid, String portletId)
		throws Exception {

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);
	}

	protected Group getScopeGroup(long userId, Layout layout) throws Exception {
		Group scopeGroup = layout.getScopeGroup();

		if (scopeGroup != null) {
			return scopeGroup;
		}

		return GroupLocalServiceUtil.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			String.valueOf(layout.getPlid()), null, 0, null, false, true, null);
	}

	protected PortletPreferences updatePortletPreferences(
			long plid, String portletId,
			javax.portlet.PortletPreferences jxPreferences)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.updatePreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
				jxPreferences);

		return portletPreferences;
	}

	private Group _group;
	private Group _importedGroup;

	private Layout _importedLayout;
	private Layout _layout;
	private Layout _portletId;

}