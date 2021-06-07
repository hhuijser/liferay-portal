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

package com.liferay.petra.portlet.url.builder;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.MimeResponse;
import javax.portlet.MutableResourceParameters;
import javax.portlet.PortletParameters;
import javax.portlet.PortletSecurityException;
import javax.portlet.ResourceURL;

/**
 * @author Hugo Huijser
 * @author Neil Griffin
 */
public class ResourceURLBuilder {

	public static ResourceURLStep createResourceURL(
		LiferayPortletResponse liferayPortletResponse) {

		return new ResourceURLStep(liferayPortletResponse.createResourceURL());
	}

	public static ResourceURLStep createResourceURL(
		LiferayPortletResponse liferayPortletResponse, String portletName) {

		return new ResourceURLStep(
			(ResourceURL)liferayPortletResponse.createResourceURL(portletName));
	}

	public static ResourceURLStep createResourceURL(MimeResponse mimeResponse) {
		return new ResourceURLStep(mimeResponse.createResourceURL());
	}

	public static ResourceURLStep createResourceURL(ResourceURL renderURL) {
		return new ResourceURLStep(renderURL);
	}

	public static class ResourceURLStep
		implements AfterBackURLStep, AfterCMDStep, AfterKeywordsStep,
				   AfterMVCPathStep, AfterMVCResourceCommandNameStep,
				   AfterNavigationStep, AfterRedirectStep,
				   AfterResourceParameterStep, AfterSecureStep, AfterTabs1Step,
				   AfterTabs2Step, BackURLStep, BuildStep, CMDStep,
				   KeywordsStep, MVCPathStep, MVCResourceCommandNameStep,
				   NavigationStep, RedirectStep, ResourceParameterStep,
				   SecureStep, Tabs1Step, Tabs2Step {

		public ResourceURLStep(ResourceURL renderURL) {
			_renderURL = renderURL;
		}

		@Override
		public ResourceURL build() {
			return _renderURL;
		}

		@Override
		public String buildString() {
			return _renderURL.toString();
		}

		@Override
		public AfterResourceParameterStep removeResourceParameter(String name) {
			MutableResourceParameters mutableResourceParameters =
				_renderURL.getResourceParameters();

			mutableResourceParameters.removeParameter(name);

			return this;
		}

		@Override
		public AfterBackURLStep setBackURL(String value) {
			_setResourceParameter("backURL", value, false);

			return this;
		}

		@Override
		public AfterBackURLStep setBackURL(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter("backURL", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterCMDStep setCMD(String value) {
			_setResourceParameter(Constants.CMD, value, false);

			return this;
		}

		@Override
		public AfterCMDStep setCMD(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter(Constants.CMD, valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterKeywordsStep setKeywords(String value) {
			_setResourceParameter("keywords", value, false);

			return this;
		}

		@Override
		public AfterKeywordsStep setKeywords(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter("keywords", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterMVCPathStep setMVCPath(String value) {
			_setResourceParameter("mvcPath", value, false);

			return this;
		}

		@Override
		public AfterMVCPathStep setMVCPath(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter("mvcPath", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterMVCResourceCommandNameStep setMVCResourceCommandName(
			String value) {

			_setResourceParameter("mvcResourceCommandName", value, false);

			return this;
		}

		@Override
		public AfterMVCResourceCommandNameStep setMVCResourceCommandName(
			String value, boolean allowNullValue) {

			if (allowNullValue || Validator.isNotNull(value)) {
				_setResourceParameter("mvcResourceCommandName", value, false);
			}

			return this;
		}

		@Override
		public AfterMVCResourceCommandNameStep setMVCResourceCommandName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter(
				"mvcResourceCommandName", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterNavigationStep setNavigation(String value) {
			_setResourceParameter("navigation", value, false);

			return this;
		}

		@Override
		public AfterNavigationStep setNavigation(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter("navigation", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterRedirectStep setRedirect(String value) {
			_setResourceParameter("redirect", value, false);

			return this;
		}

		@Override
		public AfterRedirectStep setRedirect(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter("redirect", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterResourceParameterStep setResourceParameter(
			String key, Object value) {

			_setResourceParameter(key, String.valueOf(value), true);

			return this;
		}

		@Override
		public AfterResourceParameterStep setResourceParameter(
			String name, Object value, boolean allowNullValue) {

			setResourceParameter(name, String.valueOf(value), allowNullValue);

			return this;
		}

		@Override
		public AfterResourceParameterStep setResourceParameter(
			String key, String value) {

			_setResourceParameter(key, value, true);

			return this;
		}

		@Override
		public AfterResourceParameterStep setResourceParameter(
			String key, String... values) {

			MutableResourceParameters mutableResourceParameters =
				_renderURL.getResourceParameters();

			mutableResourceParameters.setValues(key, values);

			return this;
		}

		@Override
		public AfterResourceParameterStep setResourceParameter(
			String name, String value, boolean allowNullValue) {

			if (allowNullValue || Validator.isNotNull(value)) {
				_setResourceParameter(name, value, true);
			}

			return this;
		}

		@Override
		public AfterResourceParameterStep setResourceParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter(key, valueUnsafeSupplier, true);

			return this;
		}

		@Override
		public AfterResourceParameterStep setResourceParameters(
			PortletParameters portletParameters) {

			MutableResourceParameters mutableResourceParameters =
				_renderURL.getResourceParameters();

			mutableResourceParameters.set(portletParameters);

			return this;
		}

		@Override
		public AfterSecureStep setSecure(boolean secure) {
			try {
				_renderURL.setSecure(secure);
			}
			catch (PortletSecurityException portletSecurityException) {
				throw new SystemException(portletSecurityException);
			}

			return this;
		}

		@Override
		public AfterTabs1Step setTabs1(String value) {
			_setResourceParameter("tabs1", value, false);

			return this;
		}

		@Override
		public AfterTabs1Step setTabs1(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter("tabs1", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterTabs2Step setTabs2(String value) {
			_setResourceParameter("tabs2", value, false);

			return this;
		}

		@Override
		public AfterTabs2Step setTabs2(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setResourceParameter("tabs2", valueUnsafeSupplier, false);

			return this;
		}

		private void _setResourceParameter(
			String key, String value, boolean validateKey) {

			if (validateKey) {
				_validateKey(key);
			}

			MutableResourceParameters mutableResourceParameters =
				_renderURL.getResourceParameters();

			mutableResourceParameters.setValue(key, value);
		}

		private void _setResourceParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier,
			boolean validateKey) {

			if (validateKey) {
				_validateKey(key);
			}

			try {
				Object value = valueUnsafeSupplier.get();

				if (value == null) {
					return;
				}

				MutableResourceParameters mutableResourceParameters =
					_renderURL.getResourceParameters();

				if (value instanceof String[]) {
					mutableResourceParameters.setValues(key, (String[])value);
				}
				else {
					mutableResourceParameters.setValue(
						key, String.valueOf(value));
				}
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		private void _validateKey(String key) {
			if (key == null) {
				return;
			}

			for (String[] reservedKeywordArray : _RESERVED_KEYWORDS) {
				String reservedKey = reservedKeywordArray[0];

				if (key.equals(reservedKey)) {
					throw new RuntimeException(
						StringBundler.concat(
							"Use method \"", reservedKeywordArray[1],
							"\" when setting value for \"", reservedKey, "\""));
				}
			}
		}

		private static final String[][] _RESERVED_KEYWORDS = {
			{Constants.CMD, "setCMD"}, {"backURL", "setBackURL"},
			{"keywords", "setKeywords"}, {"mvcPath", "setMVCPath"},
			{"mvcResourceCommandName", "setMVCResourceCommandName"},
			{"navigation", "setNavigation"}, {"redirect", "setRedirect"},
			{"tabs1", "setTabs1"}, {"tabs2", "setTabs2"}
		};

		private final ResourceURL _renderURL;

	}

	public interface AfterBackURLStep
		extends BuildStep, KeywordsStep, NavigationStep, ResourceParameterStep,
				SecureStep, Tabs1Step, Tabs2Step {
	}

	public interface AfterCMDStep
		extends BackURLStep, BuildStep, KeywordsStep, NavigationStep,
				RedirectStep, ResourceParameterStep, SecureStep, Tabs1Step,
				Tabs2Step {
	}

	public interface AfterKeywordsStep
		extends BuildStep, NavigationStep, ResourceParameterStep, SecureStep,
				Tabs1Step, Tabs2Step {
	}

	public interface AfterMVCPathStep
		extends BackURLStep, BuildStep, CMDStep, KeywordsStep,
				MVCResourceCommandNameStep, NavigationStep, RedirectStep,
				ResourceParameterStep, SecureStep, Tabs1Step, Tabs2Step {
	}

	public interface AfterMVCResourceCommandNameStep
		extends BackURLStep, BuildStep, CMDStep, KeywordsStep, NavigationStep,
				RedirectStep, ResourceParameterStep, SecureStep, Tabs1Step,
				Tabs2Step {
	}

	public interface AfterNavigationStep
		extends BuildStep, ResourceParameterStep, SecureStep, Tabs1Step,
				Tabs2Step {
	}

	public interface AfterRedirectStep
		extends BackURLStep, BuildStep, KeywordsStep, NavigationStep,
				ResourceParameterStep, SecureStep, Tabs1Step, Tabs2Step {
	}

	public interface AfterResourceParameterStep
		extends BuildStep, ResourceParameterStep, SecureStep {
	}

	public interface AfterSecureStep extends BuildStep {
	}

	public interface AfterTabs1Step
		extends BuildStep, ResourceParameterStep, SecureStep, Tabs2Step {
	}

	public interface AfterTabs2Step
		extends BuildStep, ResourceParameterStep, SecureStep {
	}

	public interface BackURLStep {

		public AfterBackURLStep setBackURL(String value);

		public AfterBackURLStep setBackURL(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface BuildStep {

		public ResourceURL build();

		public String buildString();

	}

	public interface CMDStep {

		public AfterCMDStep setCMD(String value);

		public AfterCMDStep setCMD(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface KeywordsStep {

		public AfterKeywordsStep setKeywords(String value);

		public AfterKeywordsStep setKeywords(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface MVCPathStep {

		public AfterMVCPathStep setMVCPath(String value);

		public AfterMVCPathStep setMVCPath(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface MVCResourceCommandNameStep {

		public AfterMVCResourceCommandNameStep setMVCResourceCommandName(
			String value);

		public AfterMVCResourceCommandNameStep setMVCResourceCommandName(
			String value, boolean allowNullValue);

		public AfterMVCResourceCommandNameStep setMVCResourceCommandName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface NavigationStep {

		public AfterNavigationStep setNavigation(String value);

		public AfterNavigationStep setNavigation(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface RedirectStep {

		public AfterRedirectStep setRedirect(String value);

		public AfterRedirectStep setRedirect(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface ResourceParameterStep {

		public AfterResourceParameterStep removeResourceParameter(String name);

		public AfterResourceParameterStep setResourceParameter(
			String key, Object value);

		public AfterResourceParameterStep setResourceParameter(
			String key, Object value, boolean allowNullValue);

		public AfterResourceParameterStep setResourceParameter(
			String key, String value);

		public AfterResourceParameterStep setResourceParameter(
			String key, String... values);

		public AfterResourceParameterStep setResourceParameter(
			String key, String value, boolean allowNullValue);

		public AfterResourceParameterStep setResourceParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

		public AfterResourceParameterStep setResourceParameters(
			PortletParameters portletParameters);

	}

	public interface SecureStep {

		public AfterSecureStep setSecure(boolean secure);

	}

	public interface Tabs1Step {

		public AfterTabs1Step setTabs1(String value);

		public AfterTabs1Step setTabs1(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface Tabs2Step {

		public AfterTabs2Step setTabs2(String value);

		public AfterTabs2Step setTabs2(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	@FunctionalInterface
	public interface UnsafeSupplier<T, E extends Throwable> {

		public T get() throws E;

	}

}