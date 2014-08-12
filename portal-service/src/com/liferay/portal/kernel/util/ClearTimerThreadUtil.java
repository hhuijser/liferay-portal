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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class ClearTimerThreadUtil {

	public static void clearTimerThread() throws Exception {
		if (!_initialized) {
			return;
		}

		Thread[] threads = ThreadUtil.getThreads();

		for (Thread thread : threads) {
			if (thread == null) {
				continue;
			}

			Class<?> threadClass = thread.getClass();

			String threadClassName = threadClass.getName();

			if (!threadClassName.equals("java.util.TimerThread")) {
				continue;
			}

			Object queue = _queueField.get(thread);

			synchronized (queue) {
				_newTasksMayBeScheduledField.setBoolean(thread, false);

				_clearMethod.invoke(queue);

				queue.notify();
			}
		}
	}

	private static final Method _clearMethod;
	private static final boolean _initialized;
	private static final Log _log = LogFactoryUtil.getLog(ClearTimerThreadUtil.class);
	private static final Field _newTasksMayBeScheduledField;
	private static final Field _queueField;

	static {
		Field newTasksMayBeScheduledField = null;
		Field queueField = null;
		Method clearMethod = null;
		boolean initialized = false;

		try {
			Class<?> timeThreadClass = Class.forName("java.util.TimerThread");

			newTasksMayBeScheduledField = ReflectionUtil.getDeclaredField(
				timeThreadClass, "newTasksMayBeScheduled");
			queueField = ReflectionUtil.getDeclaredField(
				timeThreadClass, "queue");

			Class<?> taskQueueClass = Class.forName("java.util.TaskQueue");

			clearMethod = ReflectionUtil.getDeclaredMethod(
				taskQueueClass, "clear");

			initialized = true;
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn("Failed to initialize ClearTimerThreadUtil");
			}
		}

		_newTasksMayBeScheduledField = newTasksMayBeScheduledField;
		_queueField = queueField;
		_clearMethod = clearMethod;
		_initialized = initialized;
	}

}