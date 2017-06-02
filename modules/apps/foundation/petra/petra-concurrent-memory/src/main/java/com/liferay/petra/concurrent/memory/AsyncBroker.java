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

package com.liferay.petra.concurrent.memory;

import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.FutureListener;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.memory.FinalizeAction;
import com.liferay.petra.memory.FinalizeManager;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shuyang Zhou
 */
public class AsyncBroker<K, V> {

	public Map<K, NoticeableFuture<V>> getOpenBids() {
		return Collections.<K, NoticeableFuture<V>>unmodifiableMap(
			_defaultNoticeableFutures);
	}

	public NoticeableFuture<V> post(K key) {
		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		NoticeableFuture<V> previousNoticeableFuture = post(
			key, defaultNoticeableFuture);

		if (previousNoticeableFuture == null) {
			return defaultNoticeableFuture;
		}

		return previousNoticeableFuture;
	}

	public NoticeableFuture<V> post(
		final K key, final DefaultNoticeableFuture<V> defaultNoticeableFuture) {

		DefaultNoticeableFuture<V> previousDefaultNoticeableFuture =
			_defaultNoticeableFutures.putIfAbsent(key, defaultNoticeableFuture);

		if (previousDefaultNoticeableFuture != null) {
			return previousDefaultNoticeableFuture;
		}

		defaultNoticeableFuture.addFutureListener(
			new FutureListener<V>() {

				@Override
				public void complete(Future<V> future) {
					_defaultNoticeableFutures.remove(
						key, defaultNoticeableFuture);
				}

			});

		if (_REFERENT_FIELD != null) {
			FinalizeManager.register(
				defaultNoticeableFuture, new CancellationFinalizeAction(key),
				FinalizeManager.PHANTOM_REFERENCE_FACTORY);
		}

		return null;
	}

	public NoticeableFuture<V> take(K key) {
		return _defaultNoticeableFutures.remove(key);
	}

	public boolean takeWithException(K key, Throwable throwable) {
		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			_defaultNoticeableFutures.remove(key);

		if (defaultNoticeableFuture == null) {
			return false;
		}

		defaultNoticeableFuture.setException(throwable);

		return true;
	}

	public boolean takeWithResult(K key, V result) {
		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			_defaultNoticeableFutures.remove(key);

		if (defaultNoticeableFuture == null) {
			return false;
		}

		defaultNoticeableFuture.set(result);

		return true;
	}

	private static Field _getDeclaredField(Class<?> clazz, String name)
		throws Exception {

		Field field = clazz.getDeclaredField(name);

		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		return _unfinalField(field);
	}

	private static Field _unfinalField(Field field) throws Exception {
		int modifiers = field.getModifiers();

		if ((modifiers & Modifier.FINAL) == Modifier.FINAL) {
			Field modifiersField = _getDeclaredField(Field.class, "modifiers");

			modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
		}

		return field;
	}

	private static final Field _REFERENT_FIELD;

	private static final Logger _log = LoggerFactory.getLogger(
		AsyncBroker.class);

	static {
		Field referentField = null;

		try {
			referentField = _getDeclaredField(Reference.class, "referent");
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cancellation of orphaned noticeable futures is disabled " +
						"because the JVM does not support phantom reference " +
							"resurrection",
					t);
			}
		}

		_REFERENT_FIELD = referentField;
	}

	private final ConcurrentMap<K, DefaultNoticeableFuture<V>>
		_defaultNoticeableFutures = new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

	private static class CancellationFinalizeAction implements FinalizeAction {

		public CancellationFinalizeAction(Object key) {
			_key = key;
		}

		@Override
		public void doFinalize(final Reference<?> reference) {
			try {
				NoticeableFuture<?> noticeableFuture =
					(NoticeableFuture<?>)_REFERENT_FIELD.get(reference);

				if (noticeableFuture.cancel(true) && _log.isWarnEnabled()) {
					_log.warn(
						"Cancelled orphan noticeable future " +
							noticeableFuture + " with key " + _key);
				}
			}
			catch (Exception e) {
				_log.error("Unable to access referent of " + reference, e);
			}
		}

		private final Object _key;

	}

}