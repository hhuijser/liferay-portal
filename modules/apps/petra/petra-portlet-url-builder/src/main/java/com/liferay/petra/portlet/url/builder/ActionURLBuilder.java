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

import javax.portlet.ActionURL;
import javax.portlet.MimeResponse;
import javax.portlet.MutableActionParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletParameters;
import javax.portlet.PortletSecurityException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Hugo Huijser
 * @author Neil Griffin
 */
public class ActionURLBuilder {

	public static ActionURLStep createActionURL(ActionURL actionURL) {
		return new ActionURLStep(actionURL);
	}

	public static ActionURLStep createActionURL(
		LiferayPortletResponse liferayPortletResponse) {

		return new ActionURLStep(liferayPortletResponse.createActionURL());
	}

	public static ActionURLStep createActionURL(
		LiferayPortletResponse liferayPortletResponse, MimeResponse.Copy copy) {

		return new ActionURLStep(liferayPortletResponse.createActionURL(copy));
	}

	public static ActionURLStep createActionURL(
		LiferayPortletResponse liferayPortletResponse, String portletName) {

		return new ActionURLStep(
			(ActionURL)liferayPortletResponse.createActionURL(portletName));
	}

	public static ActionURLStep createActionURL(
		LiferayPortletResponse liferayPortletResponse, String portletName,
		MimeResponse.Copy copy) {

		return new ActionURLStep(
			(ActionURL)liferayPortletResponse.createActionURL(
				portletName, copy));
	}

	public static ActionURLStep createActionURL(MimeResponse mimeResponse) {
		return new ActionURLStep(mimeResponse.createActionURL());
	}

	public static ActionURLStep createActionURL(
		MimeResponse mimeResponse, MimeResponse.Copy copy) {

		return new ActionURLStep(mimeResponse.createActionURL(copy));
	}

	public static class ActionURLStep
		implements ActionParameterStep, AfterActionParameterStep,
				   AfterBackURLStep, AfterCMDStep, AfterKeywordsStep,
				   AfterMVCActionCommandNameStep, AfterMVCPathStep,
				   AfterNavigationStep, AfterPortletModeStep, AfterRedirectStep,
				   AfterSecureStep, AfterTabs1Step, AfterTabs2Step,
				   AfterWindowStateStep, BackURLStep, BuildStep, CMDStep,
				   KeywordsStep, MVCActionCommandNameStep, MVCPathStep,
				   NavigationStep, PortletModeStep, RedirectStep, SecureStep,
				   Tabs1Step, Tabs2Step, WindowStateStep {

		public ActionURLStep(ActionURL actionURL) {
			_actionURL = actionURL;
		}

		@Override
		public ActionURL build() {
			return _actionURL;
		}

		@Override
		public String buildString() {
			return _actionURL.toString();
		}

		@Override
		public AfterActionParameterStep removeActionParameter(String name) {
			MutableActionParameters mutableActionParameters =
				_actionURL.getActionParameters();

			mutableActionParameters.removeParameter(name);

			return this;
		}

		@Override
		public AfterActionParameterStep setActionParameter(
			String key, Object value) {

			_setActionParameter(key, String.valueOf(value), true);

			return this;
		}

		@Override
		public AfterActionParameterStep setActionParameter(
			String name, Object value, boolean allowNullValue) {

			setActionParameter(name, String.valueOf(value), allowNullValue);

			return this;
		}

		@Override
		public AfterActionParameterStep setActionParameter(
			String key, String value) {

			_setActionParameter(key, value, true);

			return this;
		}

		@Override
		public AfterActionParameterStep setActionParameter(
			String key, String... values) {

			MutableActionParameters mutableActionParameters =
				_actionURL.getActionParameters();

			mutableActionParameters.setValues(key, values);

			return this;
		}

		@Override
		public AfterActionParameterStep setActionParameter(
			String name, String value, boolean allowNullValue) {

			if (allowNullValue || Validator.isNotNull(value)) {
				_setActionParameter(name, value, true);
			}

			return this;
		}

		@Override
		public AfterActionParameterStep setActionParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter(key, valueUnsafeSupplier, true);

			return this;
		}

		@Override
		public AfterActionParameterStep setActionParameters(
			PortletParameters portletParameters) {

			MutableActionParameters mutableActionParameters =
				_actionURL.getActionParameters();

			mutableActionParameters.set(portletParameters);

			return this;
		}

		@Override
		public AfterBackURLStep setBackURL(String value) {
			_setActionParameter("backURL", value, false);

			return this;
		}

		@Override
		public AfterBackURLStep setBackURL(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter("backURL", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterCMDStep setCMD(String value) {
			_setActionParameter(Constants.CMD, value, false);

			return this;
		}

		@Override
		public AfterCMDStep setCMD(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter(Constants.CMD, valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterKeywordsStep setKeywords(String value) {
			_setActionParameter("keywords", value, false);

			return this;
		}

		@Override
		public AfterKeywordsStep setKeywords(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter("keywords", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterMVCActionCommandNameStep setMVCActionCommandName(
			String value) {

			_setActionParameter("mvcActionCommandName", value, false);

			return this;
		}

		@Override
		public AfterMVCActionCommandNameStep setMVCActionCommandName(
			String value, boolean allowNullValue) {

			if (allowNullValue || Validator.isNotNull(value)) {
				_setActionParameter("mvcActionCommandName", value, false);
			}

			return this;
		}

		@Override
		public AfterMVCActionCommandNameStep setMVCActionCommandName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter(
				"mvcActionCommandName", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterMVCPathStep setMVCPath(String value) {
			_setActionParameter("mvcPath", value, false);

			return this;
		}

		@Override
		public AfterMVCPathStep setMVCPath(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter("mvcPath", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterNavigationStep setNavigation(String value) {
			_setActionParameter("navigation", value, false);

			return this;
		}

		@Override
		public AfterNavigationStep setNavigation(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter("navigation", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterPortletModeStep setPortletMode(PortletMode portletMode) {
			try {
				_actionURL.setPortletMode(portletMode);
			}
			catch (PortletModeException portletModeException) {
				throw new SystemException(portletModeException);
			}

			return this;
		}

		@Override
		public AfterRedirectStep setRedirect(String value) {
			_setActionParameter("redirect", value, false);

			return this;
		}

		@Override
		public AfterRedirectStep setRedirect(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter("redirect", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterSecureStep setSecure(boolean secure) {
			try {
				_actionURL.setSecure(secure);
			}
			catch (PortletSecurityException portletSecurityException) {
				throw new SystemException(portletSecurityException);
			}

			return this;
		}

		@Override
		public AfterTabs1Step setTabs1(String value) {
			_setActionParameter("tabs1", value, false);

			return this;
		}

		@Override
		public AfterTabs1Step setTabs1(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter("tabs1", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterTabs2Step setTabs2(String value) {
			_setActionParameter("tabs2", value, false);

			return this;
		}

		@Override
		public AfterTabs2Step setTabs2(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier) {

			_setActionParameter("tabs2", valueUnsafeSupplier, false);

			return this;
		}

		@Override
		public AfterWindowStateStep setWindowState(WindowState windowState) {
			try {
				_actionURL.setWindowState(windowState);
			}
			catch (WindowStateException windowStateException) {
				throw new SystemException(windowStateException);
			}

			return this;
		}

		private void _setActionParameter(
			String key, String value, boolean validateKey) {

			if (validateKey) {
				_validateKey(key);
			}

			MutableActionParameters mutableActionParameters =
				_actionURL.getActionParameters();

			mutableActionParameters.setValue(key, value);
		}

		private void _setActionParameter(
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

				MutableActionParameters mutableActionParameters =
					_actionURL.getActionParameters();

				if (value instanceof String[]) {
					mutableActionParameters.setValues(key, (String[])value);
				}
				else {
					mutableActionParameters.setValue(
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
			{"mvcActionCommandName", "setMVCActionCommandName"},
			{"navigation", "setNavigation"}, {"p_p_mode", "setPortletMode"},
			{"p_p_state", "setWindowState"}, {"redirect", "setRedirect"},
			{"tabs1", "setTabs1"}, {"tabs2", "setTabs2"}
		};

		private final ActionURL _actionURL;

	}

	public interface ActionParameterStep {

		public AfterActionParameterStep removeActionParameter(String name);

		public AfterActionParameterStep setActionParameter(
			String key, Object value);

		public AfterActionParameterStep setActionParameter(
			String key, Object value, boolean allowNullValue);

		public AfterActionParameterStep setActionParameter(
			String key, String value);

		public AfterActionParameterStep setActionParameter(
			String key, String... values);

		public AfterActionParameterStep setActionParameter(
			String key, String value, boolean allowNullValue);

		public AfterActionParameterStep setActionParameter(
			String key, UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

		public AfterActionParameterStep setActionParameters(
			PortletParameters portletParameters);

	}

	public interface AfterActionParameterStep
		extends ActionParameterStep, BuildStep, PortletModeStep, SecureStep,
				WindowStateStep {
	}

	public interface AfterBackURLStep
		extends ActionParameterStep, BuildStep, KeywordsStep, NavigationStep,
				PortletModeStep, SecureStep, Tabs1Step, Tabs2Step,
				WindowStateStep {
	}

	public interface AfterCMDStep
		extends ActionParameterStep, BackURLStep, BuildStep, KeywordsStep,
				NavigationStep, PortletModeStep, RedirectStep, SecureStep,
				Tabs1Step, Tabs2Step, WindowStateStep {
	}

	public interface AfterKeywordsStep
		extends ActionParameterStep, BuildStep, NavigationStep, PortletModeStep,
				SecureStep, Tabs1Step, Tabs2Step, WindowStateStep {
	}

	public interface AfterMVCActionCommandNameStep
		extends ActionParameterStep, BackURLStep, BuildStep, CMDStep,
				KeywordsStep, NavigationStep, PortletModeStep, RedirectStep,
				SecureStep, Tabs1Step, Tabs2Step, WindowStateStep {
	}

	public interface AfterMVCPathStep
		extends ActionParameterStep, BackURLStep, BuildStep, CMDStep,
				KeywordsStep, MVCActionCommandNameStep, NavigationStep,
				PortletModeStep, RedirectStep, SecureStep, Tabs1Step, Tabs2Step,
				WindowStateStep {
	}

	public interface AfterNavigationStep
		extends ActionParameterStep, BuildStep, PortletModeStep, SecureStep,
				Tabs1Step, Tabs2Step, WindowStateStep {
	}

	public interface AfterPortletModeStep
		extends BuildStep, SecureStep, WindowStateStep {
	}

	public interface AfterRedirectStep
		extends ActionParameterStep, BackURLStep, BuildStep, KeywordsStep,
				NavigationStep, PortletModeStep, SecureStep, Tabs1Step,
				Tabs2Step, WindowStateStep {
	}

	public interface AfterSecureStep extends BuildStep, WindowStateStep {
	}

	public interface AfterTabs1Step
		extends ActionParameterStep, BuildStep, PortletModeStep, SecureStep,
				Tabs2Step, WindowStateStep {
	}

	public interface AfterTabs2Step
		extends ActionParameterStep, BuildStep, PortletModeStep, SecureStep,
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

		public ActionURL build();

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

	public interface MVCActionCommandNameStep {

		public AfterMVCActionCommandNameStep setMVCActionCommandName(
			String value);

		public AfterMVCActionCommandNameStep setMVCActionCommandName(
			String value, boolean allowNullValue);

		public AfterMVCActionCommandNameStep setMVCActionCommandName(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface MVCPathStep {

		public AfterMVCPathStep setMVCPath(String value);

		public AfterMVCPathStep setMVCPath(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

	}

	public interface NavigationStep {

		public AfterNavigationStep setNavigation(String value);

		public AfterNavigationStep setNavigation(
			UnsafeSupplier<Object, Exception> valueUnsafeSupplier);

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