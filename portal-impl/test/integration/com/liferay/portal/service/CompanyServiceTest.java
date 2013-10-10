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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Company;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.CompanyTestUtil;

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
	public void testAddCompany() throws Exception {
		CompanyTestUtil.addCompany();
	}

	@Test
	public void testUpdateCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		updateCompany(company);
	}

	protected Company updateCompany(Company company) throws Exception {
		company.setAccountId(ServiceTestUtil.nextLong());

		company.setWebId(ServiceTestUtil.randomString());

		company.setKey(ServiceTestUtil.randomString());

		company.setMx(ServiceTestUtil.randomString());

		company.setHomeURL(ServiceTestUtil.randomString());

		company.setLogoId(ServiceTestUtil.nextLong());

		company.setSystem(ServiceTestUtil.randomBoolean());

		company.setMaxUsers(ServiceTestUtil.nextInt());

		company.setActive(ServiceTestUtil.randomBoolean());

		CompanyLocalServiceUtil.updateCompany(company);

		Company existingCompany = CompanyLocalServiceUtil.getCompany(
			company.getPrimaryKey());

		Assert.assertEquals(
			company.getCompanyId(), existingCompany.getCompanyId());
		Assert.assertEquals(
			company.getAccountId(), existingCompany.getAccountId());
		Assert.assertEquals(company.getWebId(), existingCompany.getWebId());
		Assert.assertEquals(company.getKey(), existingCompany.getKey());
		Assert.assertEquals(company.getMx(), existingCompany.getMx());
		Assert.assertEquals(company.getHomeURL(), existingCompany.getHomeURL());
		Assert.assertEquals(company.getLogoId(), existingCompany.getLogoId());
		Assert.assertEquals(company.getSystem(), existingCompany.getSystem());
		Assert.assertEquals(
			company.getMaxUsers(), existingCompany.getMaxUsers());
		Assert.assertEquals(company.getActive(), existingCompany.getActive());

		return company;
	}

}