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

package com.liferay.frontend.taglib.form.navigator.internal.frontend.taglib.form.navigator;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class WrapperFormNavigatorEntry<T> implements FormNavigatorEntry<T> {

	public WrapperFormNavigatorEntry(
		com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry<T>
			formNavigatorEntry) {

		_formNavigatorEntry = formNavigatorEntry;
	}

	@Override
	public String getCategoryKey() {
		return _formNavigatorEntry.getCategoryKey();
	}

	@Override
	public String getFormNavigatorId() {
		return _formNavigatorEntry.getFormNavigatorId();
	}

	@Override
	public String getKey() {
		return _formNavigatorEntry.getKey();
	}

	@Override
	public String getLabel(Locale locale) {
		return _formNavigatorEntry.getLabel(locale);
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_formNavigatorEntry.include(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isVisible(User user, T formModelBean) {
		return _formNavigatorEntry.isVisible(user, formModelBean);
	}

	private final com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry
		<T> _formNavigatorEntry;

}