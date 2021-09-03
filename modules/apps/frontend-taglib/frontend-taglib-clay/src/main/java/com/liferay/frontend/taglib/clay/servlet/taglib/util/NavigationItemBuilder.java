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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Hugo Huijser
 */
public class NavigationItemBuilder {

	public static AfterPutDataStep putData(String key, String value) {
		NavigationItemStep navigationItemStep = new NavigationItemStep();

		return navigationItemStep.putData(key, value);
	}

	public static AfterActiveStep setActive(boolean active) {
		NavigationItemStep navigationItemStep = new NavigationItemStep();

		return navigationItemStep.setActive(active);
	}

	public static AfterSetDataStep setData(Map<String, Object> data) {
		NavigationItemStep navigationItemStep = new NavigationItemStep();

		return navigationItemStep.setData(data);
	}

	public static AfterDisabledStep setDisabled(boolean disabled) {
		NavigationItemStep navigationItemStep = new NavigationItemStep();

		return navigationItemStep.setDisabled(disabled);
	}

	public static AfterHrefStep setHref(Object href) {
		NavigationItemStep navigationItemStep = new NavigationItemStep();

		return navigationItemStep.setHref(href);
	}

	public static AfterHrefStep setHref(
		PortletURL portletURL, Object... parameters) {

		NavigationItemStep navigationItemStep = new NavigationItemStep();

		return navigationItemStep.setHref(parameters);
	}

	public static AfterLabelStep setLabel(String label) {
		NavigationItemStep navigationItemStep = new NavigationItemStep();

		return navigationItemStep.setLabel(label);
	}

	public static class NavigationItemStep
		implements ActiveStep, AfterActiveStep, AfterDisabledStep,
				   AfterHrefStep, AfterLabelStep, AfterPutDataStep,
				   AfterSetDataStep, BuildStep, DisabledStep, HrefStep,
				   LabelStep, PutDataStep, SetDataStep {

		@Override
		public NavigationItem build() {
			return _navigationItem;
		}

		@Override
		public AfterPutDataStep putData(String key, String value) {
			_navigationItem.putData(key, value);

			return this;
		}

		@Override
		public AfterActiveStep setActive(boolean active) {
			_navigationItem.setActive(active);

			return this;
		}

		@Override
		public AfterSetDataStep setData(Map<String, Object> data) {
			_navigationItem.setData(data);

			return this;
		}

		@Override
		public AfterDisabledStep setDisabled(boolean disabled) {
			_navigationItem.setDisabled(disabled);

			return this;
		}

		@Override
		public AfterHrefStep setHref(Object href) {
			_navigationItem.setHref(href);

			return this;
		}

		@Override
		public AfterHrefStep setHref(
			PortletURL portletURL, Object... parameters) {

			_navigationItem.setHref(portletURL, parameters);

			return this;
		}

		@Override
		public AfterLabelStep setLabel(String label) {
			_navigationItem.setLabel(label);

			return this;
		}

		private final NavigationItem _navigationItem = new NavigationItem();

	}

	public interface ActiveStep {

		public AfterActiveStep setActive(boolean active);

	}

	public interface AfterActiveStep
		extends BuildStep, DisabledStep, HrefStep, LabelStep, SetDataStep {
	}

	public interface AfterDisabledStep extends BuildStep, HrefStep, LabelStep {
	}

	public interface AfterHrefStep extends BuildStep, LabelStep {
	}

	public interface AfterLabelStep extends BuildStep {
	}

	public interface AfterPutDataStep
		extends ActiveStep, BuildStep, DisabledStep, HrefStep, LabelStep,
				PutDataStep, SetDataStep {
	}

	public interface AfterSetDataStep
		extends BuildStep, DisabledStep, HrefStep, LabelStep {
	}

	public interface BuildStep {

		public NavigationItem build();

	}

	public interface DisabledStep {

		public AfterDisabledStep setDisabled(boolean disabled);

	}

	public interface HrefStep {

		public AfterHrefStep setHref(Object href);

		public AfterHrefStep setHref(
			PortletURL portletURL, Object... parameters);

	}

	public interface LabelStep {

		public AfterLabelStep setLabel(String label);

	}

	public interface PutDataStep {

		public AfterPutDataStep putData(String key, String value);

	}

	public interface SetDataStep {

		public AfterSetDataStep setData(Map<String, Object> data);

	}

}