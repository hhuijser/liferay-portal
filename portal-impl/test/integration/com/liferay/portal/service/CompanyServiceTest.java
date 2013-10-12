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

import com.liferay.portal.AccountNameException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.CompanyTestUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

import jodd.util.StringPool;

import org.junit.Assert;
import org.junit.Ignore;
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
public class CompanyServiceTest {

	@Test(expected = AccountNameException.class)
	@Transactional
	public void testInValidName() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		String InvalidName = StringPool.EMPTY;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			Account account = AccountLocalServiceUtil.getAccount(
				company.getAccountId());

			company = CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(),
				company.getMx(), company.getHomeURL(), InvalidName,
				account.getLegalName(), account.getLegalId(),
				account.getLegalType(), account.getSicCode(),
				account.getTickerSymbol(), account.getIndustry(),
				account.getType(), account.getSize());
		}
		finally {
			field.set(null, value);
		}
	}

	@Test(expected = CompanyVirtualHostException.class)
	@Transactional
	public void testInValidVirtualHost() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		String inValidVirtualHostName = StringPool.EMPTY;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), inValidVirtualHostName, company.getMx(),
				company.getMaxUsers(), company.getActive());
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	@Transactional
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

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), invalidMx,
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedMx = company.getMx();

			Assert.assertEquals(originalMx, updatedMx);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	@Transactional
	public void testMailMxUpdatePropertyIsFalseAndMxIsValid() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		String originalMx = company.getMx();

		String validMx = originalMx + ServiceTestUtil.randomString(3);

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), validMx,
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedMx = company.getMx();

			Assert.assertEquals(originalMx, updatedMx);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test(expected = CompanyMxException.class)
	@Transactional
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
		finally {
			field.set(null, value);
		}
	}

	@Test
	@Transactional
	public void testMailMxUpdatePropertyIsTrueAndMxIsValid() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		String originalMx = company.getMx();

		String validMx = originalMx + ServiceTestUtil.randomString(3);

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), validMx,
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedMx = company.getMx();

			Assert.assertNotEquals(originalMx, updatedMx);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	@Transactional
	public void testValidName() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		String originalName = company.getName();

		String validName = originalName + ServiceTestUtil.randomString(3);

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			Account account = AccountLocalServiceUtil.getAccount(
				company.getAccountId());

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(),
				company.getMx(), company.getHomeURL(), validName,
				account.getLegalName(), account.getLegalId(),
				account.getLegalType(), account.getSicCode(),
				account.getTickerSymbol(), account.getIndustry(),
				account.getType(), account.getSize());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedName = company.getName();

			Assert.assertNotEquals(originalName, updatedName);
		}
		finally {
			field.set(null, value);
		}
	}

	@Ignore
	@Test
	public void testValidVirtualHost() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		String originalVirtualHostName = company.getVirtualHostname();

		String validVirtualHostName =
			originalVirtualHostName + "." + ServiceTestUtil.randomString(3);

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), validVirtualHostName, company.getMx(),
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompanyById(
				company.getCompanyId());

			String updatedVirtualHostName = company.getVirtualHostname();

			Assert.assertNotEquals(
				originalVirtualHostName, updatedVirtualHostName);
		}
		finally {
			field.set(null, value);
		}
	}

}