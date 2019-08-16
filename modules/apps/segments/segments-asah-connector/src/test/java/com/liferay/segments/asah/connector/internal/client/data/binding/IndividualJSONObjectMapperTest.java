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

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.liferay.segments.asah.connector.internal.client.model.Individual;
import com.liferay.segments.asah.connector.internal.client.model.Results;

import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Arques
 */
public class IndividualJSONObjectMapperTest {

	@Test
	public void testMap() throws Exception {
		Individual individual = _individualJSONObjectMapper.map(
			_read("get-individual.json"));

		Assert.assertNotNull(individual);

		Assert.assertEquals("352412408151322817", individual.getId());

		List<Individual.DataSourceIndividualPK> dataSourceIndividualPKs =
			individual.getDataSourceIndividualPKs();

		Assert.assertEquals(
			dataSourceIndividualPKs.toString(), 1,
			dataSourceIndividualPKs.size());

		Individual.DataSourceIndividualPK dataSourceIndividualPK =
			dataSourceIndividualPKs.get(0);

		Assert.assertEquals(
			"352371782732600843", dataSourceIndividualPK.getDataSourceId());
		Assert.assertEquals(
			"LIFERAY", dataSourceIndividualPK.getDataSourceType());

		List<String> individualPKs = dataSourceIndividualPK.getIndividualPKs();

		Assert.assertEquals(individualPKs.toString(), 4, individualPKs.size());
		Assert.assertEquals(
			"b44ed31a-baad-bb17-b0e2-c9baaa7ab65e", individualPKs.get(2));
		Assert.assertEquals(
			"2724f980-6a85-11e9-8b49-890d26f7ce31", individualPKs.get(0));
		Assert.assertEquals(
			"91918ae0-6a85-11e9-b959-a7d2bf7a2eec", individualPKs.get(1));
		Assert.assertEquals(
			"ba91b030-6a87-11e9-b8af-c12bc5a9fb8e", individualPKs.get(3));

		List<String> individualSegmentIds =
			individual.getIndividualSegmentIds();

		Assert.assertEquals(
			individualSegmentIds.toString(), 4, individualSegmentIds.size());
		Assert.assertEquals("335470926072595570", individualSegmentIds.get(0));
		Assert.assertEquals("352373609633549750", individualSegmentIds.get(1));
		Assert.assertEquals("352374896208779109", individualSegmentIds.get(2));
		Assert.assertEquals("352416809884371310", individualSegmentIds.get(3));
	}

	@Test(expected = IOException.class)
	public void testMapThrowsIOException() throws Exception {
		_individualJSONObjectMapper.map("invalid json");
	}

	@Test
	public void testMapToResults() throws Exception {
		Results<Individual> results = _individualJSONObjectMapper.mapToResults(
			_read("get-individuals.json"));

		Assert.assertEquals(2, results.getTotal());

		List<Individual> individuals = results.getItems();

		Individual individual = individuals.get(1);

		Assert.assertEquals("337338657439093764", individual.getId());

		List<Individual.DataSourceIndividualPK> dataSourceIndividualPKs =
			individual.getDataSourceIndividualPKs();

		Assert.assertEquals(
			dataSourceIndividualPKs.toString(), 8,
			dataSourceIndividualPKs.size());

		Individual.DataSourceIndividualPK dataSourceIndividualPK =
			dataSourceIndividualPKs.get(0);

		Assert.assertEquals(
			"346826100957927660", dataSourceIndividualPK.getDataSourceId());
		Assert.assertEquals(
			"LIFERAY", dataSourceIndividualPK.getDataSourceType());

		List<String> individualPKs = dataSourceIndividualPK.getIndividualPKs();

		Assert.assertEquals(individualPKs.toString(), 8, individualPKs.size());

		Assert.assertTrue(
			individualPKs.contains("3b94a790-6057-11e9-ae03-53ae91f61164"));

		List<String> individualSegmentIds =
			individual.getIndividualSegmentIds();

		Assert.assertEquals(
			individualSegmentIds.toString(), 3, individualSegmentIds.size());
		Assert.assertEquals("335470926072595570", individualSegmentIds.get(0));
	}

	@Test(expected = IOException.class)
	public void testMapToResultsThrowsIOException() throws Exception {
		_individualJSONObjectMapper.mapToResults("invalid json");
	}

	@Test
	public void testMapToResultsWithNoResults() throws Exception {
		Results<Individual> results = _individualJSONObjectMapper.mapToResults(
			_read("get-individuals-no-results.json"));

		Assert.assertEquals(0, results.getTotal());

		List<Individual> individuals = results.getItems();

		Assert.assertEquals(individuals.toString(), 0, individuals.size());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));

		return new String(bytes, StandardCharsets.UTF_8);
	}

	private static final IndividualJSONObjectMapper
		_individualJSONObjectMapper = new IndividualJSONObjectMapper();

}