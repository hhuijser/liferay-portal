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

package com.liferay.portal.search.elasticsearch6.internal.background.task;

import com.liferay.portal.search.elasticsearch6.internal.ElasticsearchSearchEngineFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.index.FieldMappingAssert;
import com.liferay.portal.search.elasticsearch6.internal.index.constants.LiferayTypeMappingsConstants;
import com.liferay.portal.search.test.util.background.task.BaseReindexSingleIndexerBackgroundTaskExecutorTestCase;
import com.liferay.portal.search.test.util.search.engine.SearchEngineFixture;

/**
 * @author Adam Brandizzi
 */
public class ReindexSingleIndexerBackgroundTaskExecutorTest
	extends BaseReindexSingleIndexerBackgroundTaskExecutorTestCase {

	@Override
	protected void assertFieldType(String fieldName, String fieldType)
		throws Exception {

		ElasticsearchFixture elasticsearchFixture =
			_elasticsearchSearchEngineFixture.getElasticsearchFixture();

		FieldMappingAssert.assertType(
			fieldType, fieldName,
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE, getIndexName(),
			elasticsearchFixture.getIndicesAdminClient());
	}

	@Override
	protected SearchEngineFixture getSearchEngineFixture() {
		_elasticsearchSearchEngineFixture =
			new ElasticsearchSearchEngineFixture(
				ReindexSingleIndexerBackgroundTaskExecutorTest.class.
					getSimpleName());

		return _elasticsearchSearchEngineFixture;
	}

	private ElasticsearchSearchEngineFixture _elasticsearchSearchEngineFixture;

}