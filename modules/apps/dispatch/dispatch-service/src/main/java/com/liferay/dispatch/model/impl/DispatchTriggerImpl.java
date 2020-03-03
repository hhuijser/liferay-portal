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

package com.liferay.dispatch.model.impl;

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alessio Antonio Rendina
 */
public class DispatchTriggerImpl extends DispatchTriggerBaseImpl {

	public DispatchTriggerImpl() {
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = new UnicodeProperties(true);

			_typeSettingsUnicodeProperties.fastLoad(getTypeSettings());
		}

		return _typeSettingsUnicodeProperties;
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		super.setTypeSettings(typeSettings);

		_typeSettingsUnicodeProperties = null;
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsUnicodeProperties) {

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;

		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = new UnicodeProperties();
		}

		super.setTypeSettings(_typeSettingsUnicodeProperties.toString());
	}

	private transient UnicodeProperties _typeSettingsUnicodeProperties;

}