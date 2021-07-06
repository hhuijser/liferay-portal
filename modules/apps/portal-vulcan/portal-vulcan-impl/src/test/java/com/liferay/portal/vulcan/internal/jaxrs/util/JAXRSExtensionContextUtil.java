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

package com.liferay.portal.vulcan.internal.jaxrs.util;

import com.liferay.portal.vulcan.jaxrs.context.EntityExtensionContext;
import com.liferay.portal.vulcan.jaxrs.context.ExtensionContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Providers;

/**
 * @author Javier de Arcos
 */
public class JAXRSExtensionContextUtil {

	public static Providers getNoContextResolverProviders() {
		return new NoContextResolverProviders();
	}

	public static Map<String, Object> getTestExtendedProperties() {
		return _extendedProperties;
	}

	public static TestObject getTestObject() {
		return new TestObject();
	}

	public static Providers getTestProviders() {
		return new TestProviders();
	}

	public static class TestObject {
	}

	private static final Map<String, Object> _extendedProperties =
		Collections.singletonMap("extendedProperty", "test");

	private static class NoContextResolverProviders implements Providers {

		@Override
		public <T> ContextResolver<T> getContextResolver(
			Class<T> clazz, MediaType mediaType) {

			return null;
		}

		@Override
		public <T extends Throwable> ExceptionMapper<T> getExceptionMapper(
			Class<T> clazz) {

			return null;
		}

		@Override
		public <T> MessageBodyReader<T> getMessageBodyReader(
			Class<T> clazz, Type type, Annotation[] annotations,
			MediaType mediaType) {

			return null;
		}

		@Override
		public <T> MessageBodyWriter<T> getMessageBodyWriter(
			Class<T> clazz, Type type, Annotation[] annotations,
			MediaType mediaType) {

			return null;
		}

	}

	private static class TestExtensionContext
		extends EntityExtensionContext<TestObject> {

		@Override
		public Map<String, Object> getEntityExtendedProperties(
			TestObject entity) {

			return _extendedProperties;
		}

		@Override
		public Set<String> getEntityFilteredPropertyKeys(TestObject entity) {
			return null;
		}

	}

	private static class TestExtensionContextResolver
		implements ContextResolver<ExtensionContext> {

		@Override
		public ExtensionContext getContext(Class<?> clazz) {
			if (TestObject.class.isAssignableFrom(clazz)) {
				return new TestExtensionContext();
			}

			return null;
		}

	}

	private static class TestProviders implements Providers {

		@Override
		public <T> ContextResolver<T> getContextResolver(
			Class<T> clazz, MediaType mediaType) {

			if (clazz.isAssignableFrom(ExtensionContext.class)) {
				return (ContextResolver)new TestExtensionContextResolver();
			}

			return null;
		}

		@Override
		public <T extends Throwable> ExceptionMapper<T> getExceptionMapper(
			Class<T> clazz) {

			return null;
		}

		@Override
		public <T> MessageBodyReader<T> getMessageBodyReader(
			Class<T> clazz, Type type, Annotation[] annotations,
			MediaType mediaType) {

			return null;
		}

		@Override
		public <T> MessageBodyWriter<T> getMessageBodyWriter(
			Class<T> clazz, Type type, Annotation[] annotations,
			MediaType mediaType) {

			return null;
		}

	}

}