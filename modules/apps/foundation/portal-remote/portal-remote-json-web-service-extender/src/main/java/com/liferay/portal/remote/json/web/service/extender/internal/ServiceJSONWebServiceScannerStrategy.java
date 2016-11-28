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

package com.liferay.portal.remote.json.web.service.extender.internal;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceScannerStrategy;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AdvisedSupportProxy;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Miguel Pastor
 */
public class ServiceJSONWebServiceScannerStrategy
	implements JSONWebServiceScannerStrategy {

	@Override
	public MethodDescriptor[] scan(Object service) {
		Class<?> clazz = null;

		try {
			clazz = getTargetClass(service);
		}
		catch (Exception e) {
			return new MethodDescriptor[0];
		}

		Method[] methods = clazz.getMethods();

		List<MethodDescriptor> methodDescriptors = new ArrayList<>(
			methods.length);

		for (Method method : methods) {
			Class<?> declaringClass = method.getDeclaringClass();

			if (declaringClass != clazz) {
				continue;
			}

			methodDescriptors.add(new MethodDescriptor(method));
		}

		return methodDescriptors.toArray(
			new MethodDescriptor[methodDescriptors.size()]);
	}

	/**
	 * @see com.liferay.portal.jsonwebservice.SpringJSONWebServiceScannerStrategy#getTargetClass(
	 *      Object)
	 */
	protected Class<?> getTargetClass(Object service) throws Exception {
		while (ProxyUtil.isProxyClass(service.getClass())) {
			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(service);

			if (invocationHandler instanceof AdvisedSupportProxy) {
				AdvisedSupport advisedSupport =
					ServiceBeanAopProxy.getAdvisedSupport(service);

				TargetSource targetSource = advisedSupport.getTargetSource();

				service = targetSource.getTarget();
			}
			else if (invocationHandler instanceof ClassLoaderBeanHandler) {
				ClassLoaderBeanHandler classLoaderBeanHandler =
					(ClassLoaderBeanHandler)invocationHandler;

				Object bean = classLoaderBeanHandler.getBean();

				if (bean instanceof ServiceWrapper) {
					ServiceWrapper<?> serviceWrapper = (ServiceWrapper<?>)bean;

					service = serviceWrapper.getWrappedService();
				}
				else {
					service = bean;
				}
			}
		}

		return service.getClass();
	}

}