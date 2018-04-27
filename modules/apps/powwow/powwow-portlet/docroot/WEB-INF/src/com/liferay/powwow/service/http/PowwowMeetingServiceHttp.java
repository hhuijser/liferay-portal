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

package com.liferay.powwow.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.liferay.powwow.service.PowwowMeetingServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link PowwowMeetingServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowMeetingServiceSoap
 * @see HttpPrincipal
 * @see PowwowMeetingServiceUtil
 * @generated
 */
@ProviderType
public class PowwowMeetingServiceHttp {
	public static com.liferay.powwow.model.PowwowMeeting addPowwowMeeting(
		HttpPrincipal httpPrincipal, long groupId, String portletId,
		long powwowServerId, String name, String description,
		String providerType,
		java.util.Map<String, java.io.Serializable> providerTypeMetadataMap,
		String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PowwowMeetingServiceUtil.class,
					"addPowwowMeeting", _addPowwowMeetingParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					portletId, powwowServerId, name, description, providerType,
					providerTypeMetadataMap, languageId, calendarBookingId,
					status, powwowParticipants, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.powwow.model.PowwowMeeting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.powwow.model.PowwowMeeting deletePowwowMeeting(
		HttpPrincipal httpPrincipal, long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PowwowMeetingServiceUtil.class,
					"deletePowwowMeeting", _deletePowwowMeetingParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					powwowMeetingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.powwow.model.PowwowMeeting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.powwow.model.PowwowMeeting getPowwowMeeting(
		HttpPrincipal httpPrincipal, long powwowMeetingId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PowwowMeetingServiceUtil.class,
					"getPowwowMeeting", _getPowwowMeetingParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					powwowMeetingId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.powwow.model.PowwowMeeting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.powwow.model.PowwowMeeting> getPowwowMeetings(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc) {
		try {
			MethodKey methodKey = new MethodKey(PowwowMeetingServiceUtil.class,
					"getPowwowMeetings", _getPowwowMeetingsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, obc);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.powwow.model.PowwowMeeting>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getPowwowMeetingsCount(HttpPrincipal httpPrincipal,
		long groupId) {
		try {
			MethodKey methodKey = new MethodKey(PowwowMeetingServiceUtil.class,
					"getPowwowMeetingsCount",
					_getPowwowMeetingsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.powwow.model.PowwowMeeting updatePowwowMeeting(
		HttpPrincipal httpPrincipal, long powwowMeetingId, long powwowServerId,
		String name, String description, String providerType,
		java.util.Map<String, java.io.Serializable> providerTypeMetadataMap,
		String languageId, long calendarBookingId, int status,
		java.util.List<com.liferay.powwow.model.PowwowParticipant> powwowParticipants,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(PowwowMeetingServiceUtil.class,
					"updatePowwowMeeting", _updatePowwowMeetingParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					powwowMeetingId, powwowServerId, name, description,
					providerType, providerTypeMetadataMap, languageId,
					calendarBookingId, status, powwowParticipants,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.powwow.model.PowwowMeeting)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PowwowMeetingServiceHttp.class);
	private static final Class<?>[] _addPowwowMeetingParameterTypes0 = new Class[] {
			long.class, String.class, long.class, String.class, String.class,
			String.class, java.util.Map.class, String.class, long.class,
			int.class, java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deletePowwowMeetingParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getPowwowMeetingParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getPowwowMeetingsParameterTypes3 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getPowwowMeetingsCountParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updatePowwowMeetingParameterTypes5 = new Class[] {
			long.class, long.class, String.class, String.class, String.class,
			java.util.Map.class, String.class, long.class, int.class,
			java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}