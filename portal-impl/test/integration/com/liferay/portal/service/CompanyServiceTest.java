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
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.CompanyTestUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

import jodd.util.StringPool;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Dale Shan
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CompanyServiceTest {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_company = CompanyTestUtil.addCompany();
	}

	@After
	public void tearDown() throws Exception {
		CompanyLocalServiceUtil.deleteCompany(_company);
	}

	@Test(expected = AccountNameException.class)
	public void testInValidName() throws Exception {
		String InvalidName = StringPool.EMPTY;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			Account account = AccountLocalServiceUtil.getAccount(
				_company.getAccountId());

			_company = CompanyLocalServiceUtil.updateCompany(
				_company.getCompanyId(), _company.getVirtualHostname(),
				_company.getMx(), _company.getHomeURL(), InvalidName,
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
	public void testInValidVirtualHost() throws Exception {
		String inValidVirtualHostName = StringPool.EMPTY;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			CompanyLocalServiceUtil.updateCompany(
				_company.getCompanyId(), inValidVirtualHostName,
				_company.getMx(), _company.getMaxUsers(), _company.getActive());
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testMailMxUpdatePropertyIsFalseAndMxIsInvalid()
		throws Exception {

		String originalMx = _company.getMx();

		String invalidMx = StringPool.EMPTY;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			CompanyLocalServiceUtil.updateCompany(
				_company.getCompanyId(), _company.getVirtualHostname(),
				invalidMx, _company.getMaxUsers(), _company.getActive());

			_company = CompanyLocalServiceUtil.getCompany(
				_company.getCompanyId());

			String updatedMx = _company.getMx();

			Assert.assertEquals(originalMx, updatedMx);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testMailMxUpdatePropertyIsFalseAndMxIsValid() throws Exception {
		String originalMx = _company.getMx();

		String validMx = originalMx + ServiceTestUtil.randomString(3);

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			CompanyLocalServiceUtil.updateCompany(
				_company.getCompanyId(), _company.getVirtualHostname(), validMx,
				_company.getMaxUsers(), _company.getActive());

			_company = CompanyLocalServiceUtil.getCompany(
				_company.getCompanyId());

			String updatedMx = _company.getMx();

			Assert.assertEquals(originalMx, updatedMx);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test(expected = CompanyMxException.class)
	public void testMailMxUpdatePropertyIsTrueAndMxIsInvalid()
		throws Exception {

		String invalidMx = StringPool.EMPTY;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			_company = CompanyLocalServiceUtil.updateCompany(
				_company.getCompanyId(), _company.getVirtualHostname(),
				invalidMx, _company.getMaxUsers(), _company.getActive());
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testMailMxUpdatePropertyIsTrueAndMxIsValid() throws Exception {
		String originalMx = _company.getMx();

		String validMx = originalMx + ServiceTestUtil.randomString(3);

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			CompanyLocalServiceUtil.updateCompany(
				_company.getCompanyId(), _company.getVirtualHostname(), validMx,
				_company.getMaxUsers(), _company.getActive());

			_company = CompanyLocalServiceUtil.getCompany(
				_company.getCompanyId());

			String updatedMx = _company.getMx();

			Assert.assertNotEquals(originalMx, updatedMx);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testValidName() throws Exception {
		String originalName = _company.getName();

		String validName = originalName + ServiceTestUtil.randomString(3);

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			Account account = AccountLocalServiceUtil.getAccount(
				_company.getAccountId());

			CompanyLocalServiceUtil.updateCompany(
				_company.getCompanyId(), _company.getVirtualHostname(),
				_company.getMx(), _company.getHomeURL(), validName,
				account.getLegalName(), account.getLegalId(),
				account.getLegalType(), account.getSicCode(),
				account.getTickerSymbol(), account.getIndustry(),
				account.getType(), account.getSize());

			_company = CompanyLocalServiceUtil.getCompany(
				_company.getCompanyId());

			String updatedName = _company.getName();

			Assert.assertNotEquals(originalName, updatedName);
		}
		finally {
			field.set(null, value);
		}
	}

	@Test
	public void testValidVirtualHost() throws Exception {
		String originalVirtualHostName = _company.getVirtualHostname();

		String validVirtualHostName =
			originalVirtualHostName + "." + ServiceTestUtil.randomString(3);

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			CompanyLocalServiceUtil.updateCompany(
				_company.getCompanyId(), validVirtualHostName, _company.getMx(),
				_company.getMaxUsers(), _company.getActive());

			_company = CompanyLocalServiceUtil.getCompanyById(
				_company.getCompanyId());

			String updatedVirtualHostName = _company.getVirtualHostname();

			Assert.assertNotEquals(
				originalVirtualHostName, updatedVirtualHostName);
		}
		finally {
			field.set(null, value);
		}
	}

	private Company _company;

}