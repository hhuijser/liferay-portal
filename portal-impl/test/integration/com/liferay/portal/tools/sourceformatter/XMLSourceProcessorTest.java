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

package com.liferay.portal.tools.sourceformatter;

import org.junit.Test;

/**
 * @author Karen Dang
 */
public class XMLSourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testIncorrectTabs() throws Exception {
		test("IncorrectTabs1.testxml");
		test("IncorrectTabs2.testxml");
		test("IncorrectTabs3.testxml");
	}

	@Test
	public void testIncorrectPoshiCharacters() throws Exception {
		test("PoshiCharactersAfterDefinitionElement1.testxml");
		test("PoshiCharactersAfterDefinitionElement2.testxml");
		test("PoshiCharactersAfterDefinitionElement3.testxml");
		test("PoshiCharactersAfterDefinitionElement4.testxml");
		test("PoshiCharactersBeforeDefinitionElement1.testxml");
		test("PoshiCharactersBeforeDefinitionElement2.testxml");
		test("PoshiCharactersBeforeDefinitionElement3.testxml");
		test("PoshiCharactersBeforeDefinitionElement4.testxml");
	}

}