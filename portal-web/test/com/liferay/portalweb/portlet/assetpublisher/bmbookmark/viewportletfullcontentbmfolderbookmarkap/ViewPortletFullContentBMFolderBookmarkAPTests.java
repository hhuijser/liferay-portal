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

package com.liferay.portalweb.portlet.assetpublisher.bmbookmark.viewportletfullcontentbmfolderbookmarkap;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.addnewbmfolderbookmarkapactions.AddBMFolderTest;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.addnewbmfolderbookmarkapactions.AddNewBMFolderBookmarkAPActionsTest;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.addnewbmfolderbookmarkapactions.TearDownBMEntryTest;
import com.liferay.portalweb.portlet.assetpublisher.bmbookmark.addnewbmfolderbookmarkapactions.TearDownBMFolderTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPortletAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdisplaystylefullcontent.ConfigurePortletDisplayStyleFullContentTest;
import com.liferay.portalweb.portlet.bookmarks.portlet.addportletbookmarks.AddPageBookmarksTest;
import com.liferay.portalweb.portlet.bookmarks.portlet.addportletbookmarks.AddPortletBookmarksTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletFullContentBMFolderBookmarkAPTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAPTest.class);
		testSuite.addTestSuite(AddPortletAPTest.class);
		testSuite.addTestSuite(AddPageBookmarksTest.class);
		testSuite.addTestSuite(AddPortletBookmarksTest.class);
		testSuite.addTestSuite(AddBMFolderTest.class);
		testSuite.addTestSuite(AddNewBMFolderBookmarkAPActionsTest.class);
		testSuite.addTestSuite(ConfigurePortletDisplayStyleFullContentTest.class);
		testSuite.addTestSuite(ViewPortletFullContentBMFolderBookmarkAPTest.class);
		testSuite.addTestSuite(TearDownBMFolderTest.class);
		testSuite.addTestSuite(TearDownBMEntryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}