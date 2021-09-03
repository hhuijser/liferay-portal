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
public class DropdownItemBuilder {

	public static AfterPutDataStep putData(String key, String value) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.putData(key, value);
	}

	public static AfterActiveStep setActive(boolean active) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setActive(active);
	}

	public static AfterSetDataStep setData(Map<String, Object> data) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setData(data);
	}

	public static AfterDisabledStep setDisabled(boolean disabled) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setDisabled(disabled);
	}

	public static AfterHrefStep setHref(Object href) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setHref(href);
	}

	public static AfterHrefStep setHref(
		PortletURL portletURL, Object... parameters) {

		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setHref(parameters);
	}

	public static AfterIconStep setIcon(String icon) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setIcon(icon);
	}

	public static AfterLabelStep setLabel(String label) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setLabel(label);
	}

	public static AfterQuickActionStep setQuickAction(boolean quickAction) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setQuickAction(quickAction);
	}

	public static AfterSeparatorStep setSeparator(boolean separator) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setSeparator(separator);
	}

	public static AfterTargetStep setTarget(String target) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setTarget(target);
	}

	public static AfterTypeStep setType(String type) {
		DropdownItemStep dropdownItemStep = new DropdownItemStep();

		return dropdownItemStep.setType(type);
	}

	public static class DropdownItemStep
		implements ActiveStep, AfterActiveStep, AfterDisabledStep,
				   AfterHrefStep, AfterIconStep, AfterLabelStep,
				   AfterPutDataStep, AfterQuickActionStep, AfterSeparatorStep,
				   AfterSetDataStep, AfterTargetStep, AfterTypeStep, BuildStep,
				   DisabledStep, HrefStep, IconStep, LabelStep, PutDataStep,
				   QuickActionStep, SeparatorStep, SetDataStep, TargetStep,
				   TypeStep {

		@Override
		public DropdownItem build() {
			return _dropdownItem;
		}

		@Override
		public AfterPutDataStep putData(String key, String value) {
			_dropdownItem.putData(key, value);

			return this;
		}

		@Override
		public AfterActiveStep setActive(boolean active) {
			_dropdownItem.setActive(active);

			return this;
		}

		@Override
		public AfterSetDataStep setData(Map<String, Object> data) {
			_dropdownItem.setData(data);

			return this;
		}

		@Override
		public AfterDisabledStep setDisabled(boolean disabled) {
			_dropdownItem.setDisabled(disabled);

			return this;
		}

		@Override
		public AfterHrefStep setHref(Object href) {
			_dropdownItem.setHref(href);

			return this;
		}

		@Override
		public AfterHrefStep setHref(
			PortletURL portletURL, Object... parameters) {

			_dropdownItem.setHref(portletURL, parameters);

			return this;
		}

		@Override
		public AfterIconStep setIcon(String icon) {
			_dropdownItem.setIcon(icon);

			return this;
		}

		@Override
		public AfterLabelStep setLabel(String label) {
			_dropdownItem.setLabel(label);

			return this;
		}

		@Override
		public AfterQuickActionStep setQuickAction(boolean quickAction) {
			_dropdownItem.setQuickAction(quickAction);

			return this;
		}

		@Override
		public AfterSeparatorStep setSeparator(boolean separator) {
			_dropdownItem.setSeparator(separator);

			return this;
		}

		@Override
		public AfterTargetStep setTarget(String target) {
			_dropdownItem.setTarget(target);

			return this;
		}

		@Override
		public AfterTypeStep setType(String type) {
			_dropdownItem.setType(type);

			return this;
		}

		private final DropdownItem _dropdownItem = new DropdownItem();

	}

	public interface ActiveStep {

		public AfterActiveStep setActive(boolean active);

	}

	public interface AfterActiveStep
		extends BuildStep, DisabledStep, HrefStep, IconStep, LabelStep,
				QuickActionStep, SeparatorStep, SetDataStep, TargetStep,
				TypeStep {
	}

	public interface AfterDisabledStep
		extends BuildStep, HrefStep, IconStep, LabelStep, QuickActionStep,
				SeparatorStep, TargetStep, TypeStep {
	}

	public interface AfterHrefStep
		extends BuildStep, IconStep, LabelStep, QuickActionStep, SeparatorStep,
				TargetStep, TypeStep {
	}

	public interface AfterIconStep
		extends BuildStep, LabelStep, QuickActionStep, SeparatorStep,
				TargetStep, TypeStep {
	}

	public interface AfterLabelStep
		extends BuildStep, QuickActionStep, SeparatorStep, TargetStep,
				TypeStep {
	}

	public interface AfterPutDataStep
		extends ActiveStep, BuildStep, DisabledStep, HrefStep, IconStep,
				LabelStep, PutDataStep, QuickActionStep, SeparatorStep,
				SetDataStep, TargetStep, TypeStep {
	}

	public interface AfterQuickActionStep
		extends BuildStep, SeparatorStep, TargetStep, TypeStep {
	}

	public interface AfterSeparatorStep
		extends BuildStep, TargetStep, TypeStep {
	}

	public interface AfterSetDataStep
		extends BuildStep, DisabledStep, HrefStep, IconStep, LabelStep,
				QuickActionStep, SeparatorStep, TargetStep, TypeStep {
	}

	public interface AfterTargetStep extends BuildStep, TypeStep {
	}

	public interface AfterTypeStep extends BuildStep {
	}

	public interface BuildStep {

		public DropdownItem build();

	}

	public interface DisabledStep {

		public AfterDisabledStep setDisabled(boolean disabled);

	}

	public interface HrefStep {

		public AfterHrefStep setHref(Object href);

		public AfterHrefStep setHref(
			PortletURL portletURL, Object... parameters);

	}

	public interface IconStep {

		public AfterIconStep setIcon(String icon);

	}

	public interface LabelStep {

		public AfterLabelStep setLabel(String label);

	}

	public interface PutDataStep {

		public AfterPutDataStep putData(String key, String value);

	}

	public interface QuickActionStep {

		public AfterQuickActionStep setQuickAction(boolean quickAction);

	}

	public interface SeparatorStep {

		public AfterSeparatorStep setSeparator(boolean separator);

	}

	public interface SetDataStep {

		public AfterSetDataStep setData(Map<String, Object> data);

	}

	public interface TargetStep {

		public AfterTargetStep setTarget(String target);

	}

	public interface TypeStep {

		public AfterTypeStep setType(String type);

	}

}