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
import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletParameters;
import javax.portlet.PortletSecurityException;
import javax.portlet.RenderURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Hugo Huijser
 * @author Neil Griffin
 */
public class RenderURLBuilder {

	public static RenderURLStep createRenderURL(
		LiferayPortletResponse liferayPortletResponse) {

		return new RenderURLStep(liferayPortletResponse.createRenderURL());
	}

	public static RenderURLStep createRenderURL(
		LiferayPortletResponse liferayPortletResponse, MimeResponse.Copy copy) {

		return new RenderURLStep(liferayPortletResponse.createRenderURL(copy));
	}

	public static RenderURLStep createRenderURL(
		LiferayPortletResponse liferayPortletResponse, String portletName) {

		return new RenderURLStep(
			(RenderURL)liferayPortletResponse.createRenderURL(portletName));
	}

	public static RenderURLStep createRenderURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		MimeResponse.Copy copy) {

		return new RenderURLStep(
			(RenderURL)liferayPortletResponse.createRenderURL(
				portletName, copy));
	}

	public static RenderURLStep createRenderURL(MimeResponse mimeResponse) {
		return new RenderURLStep(mimeResponse.createRenderURL());
	}

	public static RenderURLStep createRenderURL(
		MimeResponse mimeResponse, MimeResponse.Copy copy) {

		return new RenderURLStep(mimeResponse.createRenderURL(copy));
	}

	public static RenderURLStep createRenderURL(RenderURL renderURL) {
		return new RenderURLStep(renderURL);
	}

	public static class RenderURLStep
		implements AfterBackURLStep, AfterCMDStep, AfterKeywordsStep,
				   AfterMVCPathStep, AfterMVCRenderCommandNameStep,
				   AfterNavigationStep, AfterParameterStep,
				   AfterPortletModeStep, AfterRedirectStep, AfterSecureStep,
				   AfterTabs1Step, AfterTabs2Step, AfterWindowStateStep,
				   BackURLStep, BuildStep, CMDStep, KeywordsStep, MVCPathStep,
				   MVCRenderCommandNameStep, NavigationStep, ParameterStep,
				   PortletModeStep, RedirectStep, SecureStep, Tabs1Step,
				   Tabs2Step, WindowStateStep {

		public RenderURLStep(RenderURL renderURL) {
			_renderURL = renderURL;
		}

		@Override
		public RenderURL build() {
			return _renderURL;
		}

		@Override
		public String buildString() {
			return _renderURL.toString();
		}

		@Override
		public AfterParameterStep removeRenderParameter(String name) {
			MutableRenderParameters mutableRenderParameters =
				_renderURL.getRenderParameters();

			mutableRenderParameters.removeParameter(name);

			return this;
		}

		@Override
		public AfterBackURLStep setBackURL(String value) {
			_setRenderParameter("backURL", value, false);

			return this;
		}

		@Override
		public AfterBackURLStep setBackURL(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter("backURL", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterCMDStep setCMD(String value) {
			_setRenderParameter(Constants.CMD, value, false);

			return this;
		}

		@Override
		public AfterCMDStep setCMD(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter(Constants.CMD, valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterKeywordsStep setKeywords(String value) {
			_setRenderParameter("keywords", value, false);

			return this;
		}

		@Override
		public AfterKeywordsStep setKeywords(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter("keywords", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterMVCPathStep setMVCPath(String value) {
			_setRenderParameter("mvcPath", value, false);

			return this;
		}

		@Override
		public AfterMVCPathStep setMVCPath(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter("mvcPath", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			String value) {

			_setRenderParameter("mvcRenderCommandName", value, false);

			return this;
		}

		@Override
		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			String value, boolean allowNullValue) {

			if (allowNullValue || Validator.isNotNull(value)) {
				_setRenderParameter("mvcRenderCommandName", value, false);
			}

			return this;
		}

		@Override
		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter(
				"mvcRenderCommandName", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterNavigationStep setNavigation(String value) {
			_setRenderParameter("navigation", value, false);

			return this;
		}

		@Override
		public AfterNavigationStep setNavigation(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter("navigation", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterPortletModeStep setPortletMode(PortletMode portletMode) {
			try {
				_renderURL.setPortletMode(portletMode);
			}
			catch (PortletModeException portletModeException) {
				throw new SystemException(portletModeException);
			}

			return this;
		}

		@Override
		public AfterRedirectStep setRedirect(String value) {
			_setRenderParameter("redirect", value, false);

			return this;
		}

		@Override
		public AfterRedirectStep setRedirect(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter("redirect", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterParameterStep setRenderParameter(String key, Object value) {
			_setRenderParameter(key, String.valueOf(value), true);

			return this;
		}

		@Override
		public AfterParameterStep setRenderParameter(
			String name, Object value, boolean allowNullValue) {

			setRenderParameter(name, String.valueOf(value), allowNullValue);

			return this;
		}

		@Override
		public AfterParameterStep setRenderParameter(String key, String value) {
			_setRenderParameter(key, value, true);

			return this;
		}

		@Override
		public AfterParameterStep setRenderParameter(
			String key, String... values) {

			MutableRenderParameters mutableRenderParameters =
				_renderURL.getRenderParameters();

			mutableRenderParameters.setValues(key, values);

			return this;
		}

		@Override
		public AfterParameterStep setRenderParameter(
			String name, String value, boolean allowNullValue) {

			if (allowNullValue || Validator.isNotNull(value)) {
				_setRenderParameter(name, value, true);
			}

			return this;
		}

		@Override
		public AfterParameterStep setRenderParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter(key, valueUnsafeSupplier, true);

			return this;
		}

		@Override
		public AfterParameterStep setRenderParameters(
			PortletParameters portletParameters) {

			MutableRenderParameters mutableRenderParameters =
				_renderURL.getRenderParameters();

			mutableRenderParameters.set(portletParameters);

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
			_setRenderParameter("tabs1", value, false);

			return this;
		}

		@Override
		public AfterTabs1Step setTabs1(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter("tabs1", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterTabs2Step setTabs2(String value) {
			_setRenderParameter("tabs2", value, false);

			return this;
		}

		@Override
		public AfterTabs2Step setTabs2(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setRenderParameter("tabs2", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterWindowStateStep setWindowState(WindowState windowState) {
			try {
				_renderURL.setWindowState(windowState);
			}
			catch (WindowStateException windowStateException) {
				throw new SystemException(windowStateException);
			}

			return this;
		}

		private void _setRenderParameter(
			String key, String value, boolean validateKey) {

			if (validateKey) {
				_validateKey(key);
			}

			MutableRenderParameters mutableRenderParameters =
				_renderURL.getRenderParameters();

			mutableRenderParameters.setValue(key, value);
		}

		private void _setRenderParameter(
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

				MutableRenderParameters mutableRenderParameters =
					_renderURL.getRenderParameters();

				if (value instanceof String[]) {
					mutableRenderParameters.setValues(key, (String[])value);
				}
				else {
					mutableRenderParameters.setValue(
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
			{"mvcRenderCommandName", "setMVCRenderCommandName"},
			{"navigation", "setNavigation"}, {"p_p_mode", "setPortletMode"},
			{"p_p_state", "setWindowState"}, {"redirect", "setRedirect"},
			{"tabs1", "setTabs1"}, {"tabs2", "setTabs2"}
		};

		private final RenderURL _renderURL;

	}

	public interface AfterBackURLStep
		extends BuildStep, KeywordsStep, NavigationStep, ParameterStep,
				PortletModeStep, SecureStep, Tabs1Step, Tabs2Step,
				WindowStateStep {
	}

	public interface AfterCMDStep
		extends BackURLStep, BuildStep, KeywordsStep, NavigationStep,
				ParameterStep, PortletModeStep, RedirectStep, SecureStep,
				Tabs1Step, Tabs2Step, WindowStateStep {
	}

	public interface AfterKeywordsStep
		extends BuildStep, NavigationStep, ParameterStep, PortletModeStep,
				SecureStep, Tabs1Step, Tabs2Step, WindowStateStep {
	}

	public interface AfterMVCPathStep
		extends BackURLStep, BuildStep, CMDStep, KeywordsStep,
				MVCRenderCommandNameStep, NavigationStep, ParameterStep,
				PortletModeStep, RedirectStep, SecureStep, Tabs1Step, Tabs2Step,
				WindowStateStep {
	}

	public interface AfterMVCRenderCommandNameStep
		extends BackURLStep, BuildStep, CMDStep, KeywordsStep, NavigationStep,
				ParameterStep, PortletModeStep, RedirectStep, SecureStep,
				Tabs1Step, Tabs2Step, WindowStateStep {
	}

	public interface AfterNavigationStep
		extends BuildStep, ParameterStep, PortletModeStep, SecureStep,
				Tabs1Step, Tabs2Step, WindowStateStep {
	}

	public interface AfterParameterStep
		extends BuildStep, ParameterStep, PortletModeStep, SecureStep,
				WindowStateStep {
	}

	public interface AfterPortletModeStep
		extends BuildStep, SecureStep, WindowStateStep {
	}

	public interface AfterRedirectStep
		extends BackURLStep, BuildStep, KeywordsStep, NavigationStep,
				ParameterStep, PortletModeStep, SecureStep, Tabs1Step,
				Tabs2Step, WindowStateStep {
	}

	public interface AfterSecureStep extends BuildStep, WindowStateStep {
	}

	public interface AfterTabs1Step
		extends BuildStep, ParameterStep, PortletModeStep, SecureStep,
				Tabs2Step, WindowStateStep {
	}

	public interface AfterTabs2Step
		extends BuildStep, ParameterStep, PortletModeStep, SecureStep,
				WindowStateStep {
	}

	public interface AfterWindowStateStep extends BuildStep {
	}

	public interface BackURLStep {

		public AfterBackURLStep setBackURL(String value);

		public AfterBackURLStep setBackURL(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface BuildStep {

		public RenderURL build();

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

	public interface MVCRenderCommandNameStep {

		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			String value);

		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			String value, boolean allowNullValue);

		public AfterMVCRenderCommandNameStep setMVCRenderCommandName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface NavigationStep {

		public AfterNavigationStep setNavigation(String value);

		public AfterNavigationStep setNavigation(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface ParameterStep {

		public AfterParameterStep removeRenderParameter(String name);

		public AfterParameterStep setRenderParameter(String key, Object value);

		public AfterParameterStep setRenderParameter(
			String key, Object value, boolean allowNullValue);

		public AfterParameterStep setRenderParameter(String key, String value);

		public AfterParameterStep setRenderParameter(
			String key, String... values);

		public AfterParameterStep setRenderParameter(
			String key, String value, boolean allowNullValue);

		public AfterParameterStep setRenderParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

		public AfterParameterStep setRenderParameters(
			PortletParameters portletParameters);

	}

	public interface PortletModeStep {

		public AfterPortletModeStep setPortletMode(PortletMode portletMode);

	}

	public interface RedirectStep {

		public AfterRedirectStep setRedirect(String value);

		public AfterRedirectStep setRedirect(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

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

	public interface WindowStateStep {

		public AfterWindowStateStep setWindowState(WindowState windowState);

	}

}