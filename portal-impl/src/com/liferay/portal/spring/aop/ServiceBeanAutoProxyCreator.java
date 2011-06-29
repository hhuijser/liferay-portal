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

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAutoProxyCreator extends AbstractAutoProxyCreator {

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		_methodInterceptor = methodInterceptor;
	}

	protected void customizeProxyFactory(ProxyFactory proxyFactory) {
		proxyFactory.setAopProxyFactory(
			new AopProxyFactory() {

				public AopProxy createAopProxy(AdvisedSupport advisedSupport)
					throws AopConfigException {

					return new ServiceBeanAopProxy(
						advisedSupport, _methodInterceptor);
				}

			}
		);
	}

	protected Object[] getAdvicesAndAdvisorsForBean(
			Class<?> beanClass, String beanName, TargetSource targetSource)
		throws BeansException {

		if (beanName.endsWith(_SERVICE_SUFFIX)) {
			return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
		}
		else {
			return DO_NOT_PROXY;
		}
	}

	private static final String _SERVICE_SUFFIX = "Service";

	private MethodInterceptor _methodInterceptor;

}