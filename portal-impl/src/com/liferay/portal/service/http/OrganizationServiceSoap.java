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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.OrganizationServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portal.service.OrganizationServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.model.OrganizationSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.model.Organization}, that is translated to a
 * {@link com.liferay.portal.model.OrganizationSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrganizationServiceHttp
 * @see       com.liferay.portal.model.OrganizationSoap
 * @see       com.liferay.portal.service.OrganizationServiceUtil
 * @generated
 */
public class OrganizationServiceSoap {
	public static void addGroupOrganizations(long groupId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.addGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.OrgLaborSoap[] orgLabors,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.addOrganization(parentOrganizationId,
					name, type, recursable, regionId, countryId, statusId,
					comments, site,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.OrgLaborModelImpl.toModels(
						orgLabors),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites), serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.addOrganization(parentOrganizationId,
					name, type, recursable, regionId, countryId, statusId,
					comments, site, serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.addPasswordPolicyOrganizations(passwordPolicyId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteLogo(long organizationId)
		throws RemoteException {
		try {
			OrganizationServiceUtil.deleteLogo(organizationId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteOrganization(long organizationId)
		throws RemoteException {
		try {
			OrganizationServiceUtil.deleteOrganization(organizationId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap[] getManageableOrganizations(
		java.lang.String actionId, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getManageableOrganizations(actionId,
					max);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap getOrganization(
		long organizationId) throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.getOrganization(organizationId);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long getOrganizationId(long companyId, java.lang.String name)
		throws RemoteException {
		try {
			long returnValue = OrganizationServiceUtil.getOrganizationId(companyId,
					name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap[] getOrganizations(
		long companyId, long parentOrganizationId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getOrganizations(companyId,
					parentOrganizationId);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap[] getOrganizations(
		long companyId, long parentOrganizationId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getOrganizations(companyId,
					parentOrganizationId, start, end);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getOrganizationsCount(long companyId,
		long parentOrganizationId) throws RemoteException {
		try {
			int returnValue = OrganizationServiceUtil.getOrganizationsCount(companyId,
					parentOrganizationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap[] getUserOrganizations(
		long userId) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getUserOrganizations(userId);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap[] getUserOrganizations(
		long userId, boolean inheritUserGroups) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Organization> returnValue = OrganizationServiceUtil.getUserOrganizations(userId,
					inheritUserGroups);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void setGroupOrganizations(long groupId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.setGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetGroupOrganizations(long groupId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.unsetGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.unsetPasswordPolicyOrganizations(passwordPolicyId,
				organizationIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.model.AddressSoap[] addresses,
		com.liferay.portal.model.EmailAddressSoap[] emailAddresses,
		com.liferay.portal.model.OrgLaborSoap[] orgLabors,
		com.liferay.portal.model.PhoneSoap[] phones,
		com.liferay.portal.model.WebsiteSoap[] websites,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
					parentOrganizationId, name, type, recursable, regionId,
					countryId, statusId, comments, site,
					com.liferay.portal.model.impl.AddressModelImpl.toModels(
						addresses),
					com.liferay.portal.model.impl.EmailAddressModelImpl.toModels(
						emailAddresses),
					com.liferay.portal.model.impl.OrgLaborModelImpl.toModels(
						orgLabors),
					com.liferay.portal.model.impl.PhoneModelImpl.toModels(
						phones),
					com.liferay.portal.model.impl.WebsiteModelImpl.toModels(
						websites), serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrganizationSoap updateOrganization(
		long organizationId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
					parentOrganizationId, name, type, recursable, regionId,
					countryId, statusId, comments, site, serviceContext);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OrganizationServiceSoap.class);
}