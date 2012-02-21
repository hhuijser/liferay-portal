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

package com.liferay.portalweb.portlet.documentlibrarydisplay.document.deletedldocumentmultipledldactions;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocument.TearDownDLDocumentTest;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportlet.AddPageDMTest;
import com.liferay.portalweb.portlet.documentlibrary.portlet.addportlet.AddPortletDMTest;
import com.liferay.portalweb.portlet.documentlibrarydisplay.portlet.addportlet.AddPageDLDTest;
import com.liferay.portalweb.portlet.documentlibrarydisplay.portlet.addportlet.AddPortletDLDTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteDLDocumentMultipleDLDActionsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(AddPageDLDTest.class);
		testSuite.addTestSuite(AddPortletDLDTest.class);
		testSuite.addTestSuite(AddDLDocument1Test.class);
		testSuite.addTestSuite(AddDLDocument2Test.class);
		testSuite.addTestSuite(AddDLDocument3Test.class);
		testSuite.addTestSuite(ConfigurePortletDLDShowActionsTest.class);
		testSuite.addTestSuite(DeleteDLDocumentMultipleDLDActionsTest.class);
		testSuite.addTestSuite(TearDownDLDocumentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}