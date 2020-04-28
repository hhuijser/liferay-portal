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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
@Sync
public class AddFragmentCompositionMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addLayout(_group);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@Test
	public void testAddFragmentCompositionDefaultCollection() throws Exception {
		MockLiferayPortletActionRequest actionRequest = _getMockActionRequest();
		MockLiferayPortletActionResponse actionResponse =
			new MockLiferayPortletActionResponse();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(),
					PortalUtil.getClassNameId(Layout.class.getName()),
					_layout.getPlid(), true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		actionRequest.addParameter("fragmentCollectionId", String.valueOf(0));
		actionRequest.addParameter("name", "test name");
		actionRequest.addParameter("description", "test description");
		actionRequest.addParameter("itemId", layoutStructure.getMainItemId());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "doTransactionalCommand",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, actionResponse);

		Assert.assertNotNull(jsonObject);

		FragmentComposition fragmentComposition =
			_fragmentCompositionLocalService.fetchFragmentComposition(
				_group.getGroupId(), jsonObject.getString("fragmentEntryKey"));

		Assert.assertNotNull(fragmentComposition);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.getFragmentCollection(
				fragmentComposition.getFragmentCollectionId());

		Assert.assertNotNull(fragmentCollection);

		Assert.assertEquals(
			"saved-fragments", fragmentCollection.getFragmentCollectionKey());
	}

	@Test
	public void testAddFragmentCompositionExistingCollection()
		throws Exception {

		MockLiferayPortletActionRequest actionRequest = _getMockActionRequest();
		MockLiferayPortletActionResponse actionResponse =
			new MockLiferayPortletActionResponse();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(),
					PortalUtil.getClassNameId(Layout.class.getName()),
					_layout.getPlid(), true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		FragmentCollection newFragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK,
				ServiceContextThreadLocal.getServiceContext());

		actionRequest.addParameter(
			"fragmentCollectionId",
			String.valueOf(newFragmentCollection.getFragmentCollectionId()));

		actionRequest.addParameter("name", "test name");
		actionRequest.addParameter("description", "test description");
		actionRequest.addParameter("itemId", layoutStructure.getMainItemId());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "doTransactionalCommand",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, actionResponse);

		Assert.assertNotNull(jsonObject);

		FragmentComposition fragmentComposition =
			_fragmentCompositionLocalService.fetchFragmentComposition(
				_group.getGroupId(), jsonObject.getString("fragmentEntryKey"));

		Assert.assertNotNull(fragmentComposition);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.getFragmentCollection(
				fragmentComposition.getFragmentCollectionId());

		Assert.assertNotNull(fragmentCollection);

		Assert.assertEquals(
			newFragmentCollection.getFragmentCollectionKey(),
			fragmentCollection.getFragmentCollectionKey());
	}

	@Test
	public void testAddFragmentCompositionWithThumbnail() throws Exception {
		MockLiferayPortletActionRequest actionRequest = _getMockActionRequest();
		MockLiferayPortletActionResponse actionResponse =
			new MockLiferayPortletActionResponse();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(),
					PortalUtil.getClassNameId(Layout.class.getName()),
					_layout.getPlid(), true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		FragmentCollection newFragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK,
				ServiceContextThreadLocal.getServiceContext());

		actionRequest.addParameter(
			"fragmentCollectionId",
			String.valueOf(newFragmentCollection.getFragmentCollectionId()));

		actionRequest.addParameter("name", "test name");
		actionRequest.addParameter("description", "test description");
		actionRequest.addParameter("itemId", layoutStructure.getMainItemId());
		actionRequest.addParameter("previewImageURL", _THUMBNAIL_DATA);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "doTransactionalCommand",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, actionResponse);

		Assert.assertNotNull(jsonObject);

		FragmentComposition fragmentComposition =
			_fragmentCompositionLocalService.fetchFragmentComposition(
				_group.getGroupId(), jsonObject.getString("fragmentEntryKey"));

		Assert.assertNotNull(fragmentComposition);
		Assert.assertTrue(fragmentComposition.getPreviewFileEntryId() > 0);
	}

	private MockActionRequest _getMockActionRequest() throws Exception {
		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		return mockActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private static final String _THUMBNAIL_DATA =
		"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAA" +
			"AADUlEQVQYV2M4c+bMfwAIMANkq3cY2wAAAABJRU5ErkJggg==";

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/content_layout/add_fragment_composition"
	)
	private MVCActionCommand _mvcActionCommand;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

	}

}