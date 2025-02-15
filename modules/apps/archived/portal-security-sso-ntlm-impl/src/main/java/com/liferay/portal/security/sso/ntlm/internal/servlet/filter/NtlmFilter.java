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

package com.liferay.portal.security.sso.ntlm.internal.servlet.filter;

import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration;
import com.liferay.portal.security.sso.ntlm.constants.NtlmConstants;
import com.liferay.portal.security.sso.ntlm.internal.NetlogonConnectionManager;
import com.liferay.portal.security.sso.ntlm.internal.NtlmManager;
import com.liferay.portal.security.sso.ntlm.internal.NtlmUserAccount;
import com.liferay.portal.security.sso.ntlm.internal.constants.NtlmWebKeys;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.Config;

import jcifs.util.Base64;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * Participates in every login that triggers an HTTP request to Liferay Portal.
 * It carries out one of the following tasks:
 *
 * <ol>
 * <li>
 * The filter looks for an Authorization request header with a value
 * starting with the string <code>NTLM</code>. If found, and the 9th byte (
 * <code>NTLM message type</code>) of the header value is <code>1</code> then
 * this is a type 1 message from the client. The filter then prepares and
 * returns a type 2 message which contains a nonce (a.k.a. challenge), and the
 * configured Domain for which clients should provide credentials. The type 2
 * message is placed into the WWW-Authenticate response header and HTTP response
 * code 401 is issued. The nonce is associated with the HTTP session.
 * </li>
 * <li>
 * If no nonce is found associated with the HTTP session, a simple value of
 * <code>NTLM</code> is placed into the WWW-Authenticate response header and
 * HTTP response code 401 is issued. This simulates case 1 when the client
 * responds.
 * </li>
 * <li>
 * At this stage, the Authorization request header has been received, but
 * the 8th byte is not equal to <code>1</code>, and a nonce was previously
 * associated with the HTTP session (see case 1). This means that the client has
 * now sent its username, domain, workstation name, and the result of encrypting
 * the hashed password with the NONCE. This is sent in the Authorization header.
 * This is known as a type 3 message. This is now authenticated against the
 * configured Domain Controller by passing it the type 3 message together with
 * the NONCE. To achieve this, the Domain Controller retrieves the user's actual
 * hashed password from the users directory and then repeats the same steps that
 * the client took previously. If the same encryption result is achieved, then
 * authentication is successful and the request attribute
 * <code>NTLM_REMOTE_USER</code> is set equal to the username (known as screen
 * name in Liferay terminology), in preparation for the
 * {@link
 * com.liferay.portal.security.sso.ntlm.internal.auto.login.NTLMAutoLogin} class
 * to log the user in (see above).
 * </li>
 * </ol>
 *
 * @author Bruno Farache
 * @author Marcus Schmidke
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Marcellus Tavares
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration",
	immediate = true,
	property = {
		"before-filter=Auto Login Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SSO Ntlm Filter", "url-pattern=/c/portal/login"
	},
	service = {Filter.class, NtlmFilter.class}
)
public class NtlmFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (!_browserSniffer.isIe(httpServletRequest)) {
			return false;
		}

		long companyId = _portalInstancesLocalService.getCompanyId(
			httpServletRequest);

		try {
			NtlmConfiguration ntlmConfiguration =
				_configurationProvider.getConfiguration(
					NtlmConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, NtlmConstants.SERVICE_NAME));

			return ntlmConfiguration.enabled();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return false;
	}

	@Reference(unbind = "-")
	public void setNetlogonConnectionManager(
		NetlogonConnectionManager netlogonConnectionManager) {

		_netlogonConnectionManager = netlogonConnectionManager;
	}

	@Reference(unbind = "-")
	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_portalCache = (PortalCache<String, byte[]>)singleVMPool.getPortalCache(
			NtlmFilter.class.getName());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();

			if (key.contains("jcifs.")) {
				String value = (String)entry.getValue();

				Config.setProperty(key, value);
			}
		}

		_ntlmManagers.clear();
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		// Type 1 NTLM requests from browser can (and should) always immediately
		// be replied to with an Type 2 NTLM response, no matter whether we're
		// yet logging in or whether it is much later in the session.

		HttpSession httpSession = httpServletRequest.getSession(false);

		String authorization = GetterUtil.getString(
			httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));

		if (authorization.startsWith("NTLM")) {
			NtlmManager ntlmManager = _getNtlmManager(
				_portalInstancesLocalService.getCompanyId(httpServletRequest));

			String portalCacheKey = _getPortalCacheKey(httpServletRequest);

			byte[] src = Base64.decode(authorization.substring(5));

			if (src[8] == 1) {
				byte[] serverChallenge = new byte[8];

				BigEndianCodec.putLong(
					serverChallenge, 0, SecureRandomUtil.nextLong());

				byte[] challengeMessage = ntlmManager.negotiate(
					src, serverChallenge);

				authorization = Base64.encode(challengeMessage);

				httpServletResponse.setContentLength(0);
				httpServletResponse.setHeader(
					HttpHeaders.WWW_AUTHENTICATE, "NTLM " + authorization);
				httpServletResponse.setStatus(
					HttpServletResponse.SC_UNAUTHORIZED);

				httpServletResponse.flushBuffer();

				_portalCache.put(portalCacheKey, serverChallenge);

				// Interrupt filter chain, send response. Browser will
				// immediately post a new request.

				return;
			}

			byte[] serverChallenge = _portalCache.get(portalCacheKey);

			if (serverChallenge == null) {
				httpServletResponse.setContentLength(0);
				httpServletResponse.setHeader(
					HttpHeaders.WWW_AUTHENTICATE, "NTLM");
				httpServletResponse.setStatus(
					HttpServletResponse.SC_UNAUTHORIZED);

				httpServletResponse.flushBuffer();

				return;
			}

			NtlmUserAccount ntlmUserAccount = null;

			try {
				ntlmUserAccount = ntlmManager.authenticate(
					src, serverChallenge);
			}
			catch (Exception exception) {
				_log.error("Unable to perform NTLM authentication", exception);
			}
			finally {
				_portalCache.remove(portalCacheKey);
			}

			if (ntlmUserAccount == null) {
				httpServletResponse.setContentLength(0);
				httpServletResponse.setHeader(
					HttpHeaders.WWW_AUTHENTICATE, "NTLM");
				httpServletResponse.setStatus(
					HttpServletResponse.SC_UNAUTHORIZED);

				httpServletResponse.flushBuffer();

				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("NTLM remote user " + ntlmUserAccount.getUserName());
			}

			httpServletRequest.setAttribute(
				NtlmWebKeys.NTLM_REMOTE_USER, ntlmUserAccount.getUserName());

			if (httpSession != null) {
				httpSession.setAttribute(
					NtlmWebKeys.NTLM_USER_ACCOUNT, ntlmUserAccount);
			}
		}

		String path = httpServletRequest.getPathInfo();

		if ((path != null) && path.endsWith("/login")) {
			NtlmUserAccount ntlmUserAccount = null;

			if (httpSession != null) {
				ntlmUserAccount = (NtlmUserAccount)httpSession.getAttribute(
					NtlmWebKeys.NTLM_USER_ACCOUNT);
			}

			if (ntlmUserAccount == null) {
				httpServletResponse.setContentLength(0);
				httpServletResponse.setHeader(
					HttpHeaders.WWW_AUTHENTICATE, "NTLM");
				httpServletResponse.setStatus(
					HttpServletResponse.SC_UNAUTHORIZED);

				httpServletResponse.flushBuffer();

				return;
			}

			httpServletRequest.setAttribute(
				NtlmWebKeys.NTLM_REMOTE_USER, ntlmUserAccount.getUserName());
		}

		processFilter(
			NtlmPostFilter.class.getName(), httpServletRequest,
			httpServletResponse, filterChain);
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private NtlmManager _getNtlmManager(long companyId) throws Exception {
		NtlmConfiguration ntlmConfiguration =
			_configurationProvider.getConfiguration(
				NtlmConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId, NtlmConstants.SERVICE_NAME));

		String domain = ntlmConfiguration.domain();
		String domainController = ntlmConfiguration.domainController();
		String domainControllerName = ntlmConfiguration.domainControllerName();
		String serviceAccount = ntlmConfiguration.serviceAccount();
		String servicePassword = ntlmConfiguration.servicePassword();

		NtlmManager ntlmManager = _ntlmManagers.get(companyId);

		if (ntlmManager == null) {
			ntlmManager = new NtlmManager(
				_netlogonConnectionManager, domain, domainController,
				domainControllerName, serviceAccount, servicePassword);

			_ntlmManagers.put(companyId, ntlmManager);
		}
		else {
			if (!Objects.equals(ntlmManager.getDomain(), domain) ||
				!Objects.equals(
					ntlmManager.getDomainController(), domainController) ||
				!Objects.equals(
					ntlmManager.getDomainControllerName(),
					domainControllerName) ||
				!Objects.equals(
					ntlmManager.getServiceAccount(), serviceAccount) ||
				!Objects.equals(
					ntlmManager.getServicePassword(), servicePassword)) {

				ntlmManager.setConfiguration(
					domain, domainController, domainControllerName,
					serviceAccount, servicePassword);
			}
		}

		return ntlmManager;
	}

	private String _getPortalCacheKey(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession(false);

		if (httpSession == null) {
			return httpServletRequest.getRemoteAddr();
		}

		return httpSession.getId();
	}

	private static final Log _log = LogFactoryUtil.getLog(NtlmFilter.class);

	@Reference
	private BrowserSniffer _browserSniffer;

	private ConfigurationProvider _configurationProvider;
	private NetlogonConnectionManager _netlogonConnectionManager;
	private final Map<Long, NtlmManager> _ntlmManagers =
		new ConcurrentHashMap<>();
	private PortalCache<String, byte[]> _portalCache;

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}