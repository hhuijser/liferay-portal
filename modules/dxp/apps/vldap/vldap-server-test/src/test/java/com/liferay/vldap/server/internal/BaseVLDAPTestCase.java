/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactory;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ImageService;
import com.liferay.portal.kernel.service.ImageServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.util.PortletPropsKeys;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author William Newbury
 */
@RunWith(PowerMockRunner.class)
public abstract class BaseVLDAPTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpPortal();

		_setUpConfiguration();
		_setUpCompany();
		_setUpORM();
		setUpPropsUtil();
		_setUpSearchBase();
	}

	@After
	public void tearDown() {
		for (Class<?> serviceUtilClass : _serviceUtilClasses) {
			ReflectionTestUtil.setFieldValue(
				serviceUtilClass, "_service", null);
		}
	}

	protected <T> T getMockPortalService(
		Class<?> serviceUtilClass, Class<T> serviceClass) {

		_serviceUtilClasses.add(serviceUtilClass);

		T serviceMock = mock(serviceClass);

		ReflectionTestUtil.setFieldValue(
			serviceUtilClass, "_service", serviceMock);

		return serviceMock;
	}

	protected void setUpPasswordPolicy(PasswordPolicy passwordPolicy) {
		when(
			passwordPolicy.getGraceLimit()
		).thenReturn(
			_GRACE_LIMIT
		);

		when(
			passwordPolicy.getHistoryCount()
		).thenReturn(
			_HISTORY_COUNT
		);

		when(
			passwordPolicy.getLockoutDuration()
		).thenReturn(
			_LOCKOUT_DURATION
		);

		when(
			passwordPolicy.getMaxAge()
		).thenReturn(
			_MAX_AGE
		);

		when(
			passwordPolicy.getMinAge()
		).thenReturn(
			_MIN_AGE
		);

		when(
			passwordPolicy.getResetFailureCount()
		).thenReturn(
			_RESET_FAILURE_COUNT
		);

		when(
			passwordPolicy.isExpireable()
		).thenReturn(
			false
		);

		when(
			passwordPolicy.isLockout()
		).thenReturn(
			true
		);

		when(
			passwordPolicy.isRequireUnlock()
		).thenReturn(
			true
		);
	}

	protected void setUpPortalUtil() {
		Portal portal = mock(Portal.class);

		when(
			portal.getClassNameId(Mockito.any(Class.class))
		).thenReturn(
			PRIMARY_KEY
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
	}

	protected void setUpPropsUtil() {
		props = mock(Props.class);

		PropsUtil.setProps(props);

		when(
			props.get(PortletPropsKeys.SEARCH_MAX_SIZE)
		).thenReturn(
			"42"
		);
	}

	protected static final long PRIMARY_KEY = 42;

	protected Company company;
	protected GroupLocalService groupLocalService;
	protected ImageService imageService;
	protected OrganizationLocalService organizationLocalService;
	protected Props props;
	protected RoleLocalService roleLocalService;
	protected SearchBase searchBase;
	protected UserGroupLocalService userGroupLocalService;
	protected UserLocalService userLocalService;

	private void _setUpCompany() throws Exception {
		company = mock(Company.class);

		when(
			company.getCompanyId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			company.getWebId()
		).thenReturn(
			"liferay.com"
		);

		_companies.add(company);

		CompanyLocalService companyLocalService = getMockPortalService(
			CompanyLocalServiceUtil.class, CompanyLocalService.class);

		when(
			companyLocalService.getCompanies()
		).thenReturn(
			_companies
		);

		when(
			companyLocalService.getCompanies(Mockito.anyBoolean())
		).thenReturn(
			_companies
		);

		when(
			companyLocalService.getCompanyByWebId(Mockito.eq("liferay.com"))
		).thenReturn(
			company
		);
	}

	private void _setUpConfiguration() {
		PortletClassLoaderUtil.setServletContextName("vldap-server");

		Thread currentThread = Thread.currentThread();

		ClassLoaderPool.register(
			"vldap-server", currentThread.getContextClassLoader());

		Configuration configuration = mock(Configuration.class);

		when(
			configuration.getArray(PortletPropsKeys.SAMBA_DOMAIN_NAMES)
		).thenReturn(
			new String[] {"testDomainName"}
		);

		when(
			configuration.getArray(PortletPropsKeys.SAMBA_HOSTS_ALLOWED)
		).thenReturn(
			new String[0]
		);

		ConfigurationFactory configurationFactory = mock(
			ConfigurationFactory.class);

		when(
			configurationFactory.getConfiguration(
				Mockito.any(ClassLoader.class), Mockito.eq("portlet"))
		).thenReturn(
			configuration
		);

		when(
			configurationFactory.getConfiguration(
				Mockito.any(ClassLoader.class), Mockito.eq("service"))
		).thenReturn(
			configuration
		);

		ConfigurationFactoryUtil.setConfigurationFactory(configurationFactory);
	}

	private void _setUpORM() {
		Criterion criterion = mock(Criterion.class);

		DynamicQuery dynamicQuery = mock(DynamicQuery.class);

		DynamicQueryFactory dynamicQueryFactory = mock(
			DynamicQueryFactory.class);

		when(
			dynamicQueryFactory.forClass(
				Mockito.any(Class.class), Mockito.any(ClassLoader.class))
		).thenReturn(
			dynamicQuery
		);

		DynamicQueryFactoryUtil dynamicQueryFactoryUtil =
			new DynamicQueryFactoryUtil();

		dynamicQueryFactoryUtil.setDynamicQueryFactory(dynamicQueryFactory);

		RestrictionsFactory restrictionsFactory = mock(
			RestrictionsFactory.class);

		when(
			restrictionsFactory.eq(
				Mockito.anyString(), Mockito.any(Object.class))
		).thenReturn(
			criterion
		);

		when(
			restrictionsFactory.ilike(
				Mockito.anyString(), Mockito.any(Object.class))
		).thenReturn(
			criterion
		);

		RestrictionsFactoryUtil restrictionsFactoryUtil =
			new RestrictionsFactoryUtil();

		restrictionsFactoryUtil.setRestrictionsFactory(restrictionsFactory);
	}

	private void _setUpPortal() {
		groupLocalService = getMockPortalService(
			GroupLocalServiceUtil.class, GroupLocalService.class);
		imageService = getMockPortalService(
			ImageServiceUtil.class, ImageService.class);
		organizationLocalService = getMockPortalService(
			OrganizationLocalServiceUtil.class, OrganizationLocalService.class);
		roleLocalService = getMockPortalService(
			RoleLocalServiceUtil.class, RoleLocalService.class);
		userGroupLocalService = getMockPortalService(
			UserGroupLocalServiceUtil.class, UserGroupLocalService.class);
		userLocalService = getMockPortalService(
			UserLocalServiceUtil.class, UserLocalService.class);
	}

	private void _setUpSearchBase() {
		searchBase = mock(SearchBase.class);

		when(
			searchBase.getCompanies()
		).thenReturn(
			_companies
		);

		when(
			searchBase.getSizeLimit()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			searchBase.getTop()
		).thenReturn(
			"Liferay"
		);
	}

	private static final int _GRACE_LIMIT = 7200000;

	private static final int _HISTORY_COUNT = 3600000;

	private static final long _LOCKOUT_DURATION = 7200000;

	private static final long _MAX_AGE = 14400000;

	private static final long _MIN_AGE = 3600000;

	private static final long _RESET_FAILURE_COUNT = 3600000;

	private final List<Company> _companies = new ArrayList<>();
	private final List<Class<?>> _serviceUtilClasses = new ArrayList<>();

}