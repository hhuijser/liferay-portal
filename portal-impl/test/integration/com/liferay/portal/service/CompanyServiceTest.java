/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.CompanyMxException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.CompanyTestUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

import jodd.util.StringPool;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dale Shan
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class CompanyServiceTest {

	@Test
	public void testMailMxUpdatePropertyIsFalseAndMxIsInvalid()
			throws Exception {

		Company company = CompanyTestUtil.addCompany();

		String originalMx = company.getMx();

		String invalidMx = StringPool.EMPTY;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			company = CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), invalidMx,
				company.getMaxUsers(), company.getActive());

			String updatedMx = company.getMx();

			Assert.assertEquals(originalMx, updatedMx);
		}
		catch (ReservedUserEmailAddressException rueae) {
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testMailMxUpdatePropertyIsFalseAndMxIsValid()
			throws Exception {

		Company company = CompanyTestUtil.addCompany();

		String originalMx = company.getMx();

		String validMx = ServiceTestUtil.randomString();

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			company = CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), validMx,
				company.getMaxUsers(), company.getActive());

			String updatedMx = company.getMx();

			Assert.assertEquals(originalMx, updatedMx);
		}
		catch (ReservedUserEmailAddressException rueae) {
		}
		finally {
			field.set(null, value);
		}
	}

	@Test(expected = CompanyMxException.class)
	public void testMailMxUpdatePropertyIsTrueAndMxIsInvalid()
			throws Exception {

		Company company = CompanyTestUtil.addCompany();

		String invalidMx = StringPool.EMPTY;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			company = CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), invalidMx,
				company.getMaxUsers(), company.getActive());
		}
		catch (ReservedUserEmailAddressException rueae) {
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testMailMxUpdatePropertyIsTrueAndMxIsValid()
			throws Exception {

		Company company = CompanyTestUtil.addCompany();

		String originalMx = company.getMx();

		String validMx = ServiceTestUtil.randomString();

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			company = CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), validMx,
				company.getMaxUsers(), company.getActive());

			String updatedMx = company.getMx();

			Assert.assertNotEquals(originalMx, updatedMx);
		}
		catch (ReservedUserEmailAddressException rueae) {
		}
		finally {
			field.set(null, value);
		}
	}

}