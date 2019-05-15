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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.util.StringPlus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sergio González
 * @author Raymond Augé
 */
public class MVCCommandCache<T extends MVCCommand> {

	public MVCCommandCache(
		T emptyMVCCommand, String packagePrefix, String portletName,
		Class<T> mvcCommandClass, String mvcCommandPostFix) {

		this(
			emptyMVCCommand, packagePrefix, portletName, portletName,
			mvcCommandClass, mvcCommandPostFix);
	}

	public MVCCommandCache(
		T emptyMVCCommand, String packagePrefix, String portletName,
		String portletId, Class<T> mvcCommandClass, String mvcCommandPostFix) {

		_emptyMVCCommand = emptyMVCCommand;
		_mvcCommandClass = mvcCommandClass;
		_mvcCommandPostFix = mvcCommandPostFix;

		if (Validator.isNotNull(packagePrefix) &&
			!packagePrefix.endsWith(StringPool.PERIOD)) {

			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;

		if (portletId.equals(portletName)) {
			_filterString = StringBundler.concat(
				"(&(mvc.command.name=*)(javax.portlet.name=", portletId, "))");
		}
		else {
			_filterString = StringBundler.concat(
				"(&(mvc.command.name=*)(|(javax.portlet.name=", portletName,
				")(javax.portlet.name=", portletId, ")))");
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #MVCCommandCache(MVCCommand, String, String, Class, String)}
	 */
	@Deprecated
	public MVCCommandCache(
		T emptyMVCCommand, String packagePrefix, String portletName,
		String mvcCommandClassName, String mvcCommandPostFix) {

		this(
			emptyMVCCommand, packagePrefix, portletName,
			_getMVCCommandClass(mvcCommandClassName), mvcCommandPostFix);
	}

	public void close() {
		ServiceTrackerMap<String, T> serviceTrackerMap = _serviceTrackerMap;

		if (serviceTrackerMap != null) {
			serviceTrackerMap.close();
		}
	}

	public T getMVCCommand(String mvcCommandName) {
		ServiceTrackerMap<String, T> serviceTrackerMap =
			_getServiceTrackerMap();

		T mvcCommand = serviceTrackerMap.getService(mvcCommandName);

		if (mvcCommand != null) {
			return mvcCommand;
		}

		String className = null;

		try {
			mvcCommand = _mvcCommandCache.get(mvcCommandName);

			if (mvcCommand != null) {
				return mvcCommand;
			}

			if (Validator.isNull(_packagePrefix)) {
				return _emptyMVCCommand;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(_packagePrefix);
			sb.append(Character.toUpperCase(mvcCommandName.charAt(0)));
			sb.append(mvcCommandName.substring(1));
			sb.append(_mvcCommandPostFix);

			className = sb.toString();

			mvcCommand = (T)InstanceFactory.newInstance(className);

			_mvcCommandCache.put(mvcCommandName, mvcCommand);

			return mvcCommand;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to instantiate MVCCommand " + className);
			}

			_mvcCommandCache.put(mvcCommandName, _emptyMVCCommand);

			return _emptyMVCCommand;
		}
	}

	public Set<String> getMVCCommandNames() {
		ServiceTrackerMap<String, T> serviceTrackerMap =
			_getServiceTrackerMap();

		return serviceTrackerMap.keySet();
	}

	public List<T> getMVCCommands(String key) {
		List<T> mvcCommands = _mvcCommands.get(key);

		String[] mvcCommandNames = StringUtil.split(key);

		if ((mvcCommands != null) &&
			(mvcCommands.size() == mvcCommandNames.length)) {

			return mvcCommands;
		}

		mvcCommands = new ArrayList<>();

		for (String mvcCommandName : mvcCommandNames) {
			T mvcCommand = getMVCCommand(mvcCommandName);

			if (mvcCommand != _emptyMVCCommand) {
				mvcCommands.add(mvcCommand);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to find MVCCommand " + key);
				}
			}
		}

		_mvcCommands.put(key, mvcCommands);

		for (MVCCommand mvcCommand : mvcCommands) {
			String mvcCommandClassName = ClassUtil.getClassName(mvcCommand);

			List<String> keys = _mvcCommandKeys.get(mvcCommandClassName);

			if (keys == null) {
				keys = new ArrayList<>();

				_mvcCommandKeys.put(mvcCommandClassName, keys);
			}

			keys.add(key);
		}

		return mvcCommands;
	}

	public boolean isEmpty() {
		return _mvcCommandCache.isEmpty();
	}

	private static <T extends MVCCommand> Class<T> _getMVCCommandClass(
		String mvcCommandClassName) {

		Class<T> mvcCommandClass = null;

		for (Class<? extends MVCCommand> curMVCCommandClass :
				_mvcCommandClasses) {

			if (mvcCommandClassName.equals(curMVCCommandClass.getName())) {
				mvcCommandClass = (Class<T>)curMVCCommandClass;

				break;
			}
		}

		if (mvcCommandClass == null) {
			throw new IllegalArgumentException();
		}

		return mvcCommandClass;
	}

	private ServiceTrackerMap<String, T> _getServiceTrackerMap() {
		ServiceTrackerMap<String, T> serviceTrackerMap = _serviceTrackerMap;

		if (serviceTrackerMap == null) {
			synchronized (this) {
				if (_serviceTrackerMap == null) {
					_serviceTrackerMap =
						ServiceTrackerCollections.openSingleValueMap(
							_mvcCommandClass, _filterString,
							_SERVICE_REFERENCE_MAPPER);
				}

				serviceTrackerMap = _serviceTrackerMap;
			}
		}

		return serviceTrackerMap;
	}

	private static final ServiceReferenceMapper<String, MVCCommand>
		_SERVICE_REFERENCE_MAPPER = (serviceReference, emitter) -> {
			List<String> mvcCommandNames = StringPlus.asList(
				serviceReference.getProperty("mvc.command.name"));

			for (String mvcCommandName : mvcCommandNames) {
				emitter.emit(mvcCommandName);
			}
		};

	private static final Log _log = LogFactoryUtil.getLog(
		MVCCommandCache.class);

	private static final List<Class<? extends MVCCommand>> _mvcCommandClasses =
		Arrays.asList(
			MVCActionCommand.class, MVCRenderCommand.class,
			MVCResourceCommand.class);

	private final T _emptyMVCCommand;
	private final String _filterString;
	private final Map<String, T> _mvcCommandCache = new ConcurrentHashMap<>();
	private final Class<T> _mvcCommandClass;
	private final Map<String, List<String>> _mvcCommandKeys =
		new ConcurrentHashMap<>();
	private final String _mvcCommandPostFix;
	private final Map<String, List<T>> _mvcCommands = new ConcurrentHashMap<>();
	private final String _packagePrefix;
	private volatile ServiceTrackerMap<String, T> _serviceTrackerMap;

}