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

package com.liferay.messaging.impl.internal;

import com.liferay.messaging.Destination;
import com.liferay.messaging.DestinationConfiguration;
import com.liferay.messaging.DestinationFactory;
import com.liferay.messaging.DestinationPrototype;
import com.liferay.messaging.ExecutorServiceRegistrar;
import com.liferay.petra.io.convert.Maps;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(service = DestinationFactory.class)
public class DefaultDestinationFactory implements DestinationFactory {

	@Override
	public Destination createDestination(
		DestinationConfiguration destinationConfiguration) {

		String type = destinationConfiguration.getDestinationType();

		DestinationPrototype destinationPrototype = _destinationPrototypes.get(
			type);

		if (destinationPrototype == null) {
			throw new IllegalArgumentException(
				"No destination prototype configured for " + type);
		}

		return destinationPrototype.createDestination(destinationConfiguration);
	}

	@Override
	public Collection<String> getDestinationTypes() {
		return Collections.unmodifiableCollection(
			_destinationPrototypes.keySet());
	}

	@Activate
	protected void activate() {
		_destinationPrototypes.put(
			DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
			new ParallelDestinationPrototype(_executorServiceRegistrar));
		_destinationPrototypes.put(
			DestinationConfiguration.DESTINATION_TYPE_SERIAL,
			new SerialDestinationPrototype(_executorServiceRegistrar));
		_destinationPrototypes.put(
			DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
			new SynchronousDestinationPrototype());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeDestinationPrototype"
	)
	protected void addDestinationPrototype(
		DestinationPrototype destinationPrototype,
		Map<String, Object> properties) {

		_destinationPrototypes.put(
			Maps.getString(properties, "destination.type"),
			destinationPrototype);
	}

	@Deactivate
	protected void deactivate() {
		_destinationPrototypes.clear();
	}

	protected void removeDestinationPrototype(
		DestinationPrototype destinationPrototype,
		Map<String, Object> properties) {

		_destinationPrototypes.remove(
			Maps.getString(properties, "destination.type"),
			destinationPrototype);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private ExecutorServiceRegistrar _executorServiceRegistrar;

	private final ConcurrentMap<String, DestinationPrototype>
		_destinationPrototypes = new ConcurrentHashMap<>();

}