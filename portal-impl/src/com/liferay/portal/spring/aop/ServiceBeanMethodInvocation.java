/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanMethodInvocation implements MethodInvocation {

	public ServiceBeanMethodInvocation(
		Object target, Class<?> targetClass, Method method,
		Object[] arguments) {

		_target = target;
		_targetClass = targetClass;
		_method = method;
		_arguments = arguments;

		_method.setAccessible(true);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceBeanMethodInvocation)) {
			return false;
		}

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			(ServiceBeanMethodInvocation)obj;

		if ((_method == serviceBeanMethodInvocation._method) &&
			Validator.equals(_method, serviceBeanMethodInvocation._method)) {

			return true;
		}

		return false;
	}

	public Object[] getArguments() {
		return _arguments;
	}

	public Method getMethod() {
		return _method;
	}

	public AccessibleObject getStaticPart() {
		return _method;
	}

	public Object getThis() {
		return _target;
	}

	public int hashCode() {
		if (_hashCode == 0) {
			_hashCode = _method.hashCode();
		}

		return _hashCode;
	}

	public Object proceed() throws Throwable {
		try {
			return _method.invoke(_target, _arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		Class<?>[] parameterTypes = _method.getParameterTypes();

		StringBundler sb = new StringBundler(parameterTypes.length * 2 + 6);

		Class<?> declaringClass = _method.getDeclaringClass();

		sb.append(declaringClass.getName());

		sb.append(StringPool.PERIOD);
		sb.append(_method.getName());
		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> parameterType = parameterTypes[i];

			sb.append(parameterType.getName());

			if ((i + 1) < parameterTypes.length) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		if (_targetClass != null) {
			sb.append(StringPool.AT);
			sb.append(_targetClass.getName());
		}

		_toString = sb.toString();

		return _toString;
	}

	private Object[] _arguments;
	private int _hashCode;
	private Method _method;
	private Object _target;
	private Class<?> _targetClass;
	private String _toString;

}