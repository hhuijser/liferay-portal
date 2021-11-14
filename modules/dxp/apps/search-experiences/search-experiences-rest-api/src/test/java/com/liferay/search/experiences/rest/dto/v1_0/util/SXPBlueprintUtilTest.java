/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SXPBlueprintUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToSXPBlueprint() throws Exception {
		String[] fileNames = {
			"advanced_configuration_test",
			"aggregations_test",

			// TODO "content_driven_test",

			"highlights_test",
			"personalization_test",
			"sorts_tests"
		};

		for (String fileName : fileNames) {
			_testToSXPBlueprint(fileName);
		}
	}

	private void _testToSXPBlueprint(String fileName) throws Exception {
		String json = StringUtil.read(
			getClass(),
			"dependencies/SXPBlueprintUtilTest." + fileName + ".json");

		SXPBlueprint sxpBlueprint = SXPBlueprintUtil.toSXPBlueprint(json);

		Assert.assertEquals(
			fileName, sxpBlueprint.toString(),
			String.valueOf(
				SXPBlueprintUtil.toSXPBlueprint(sxpBlueprint.toString())));
	}

}