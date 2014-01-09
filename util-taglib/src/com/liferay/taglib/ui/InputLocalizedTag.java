/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsConstants;
import com.liferay.taglib.aui.ValidatorTagImpl;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Julio Camarero
 */
public class InputLocalizedTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		updateFormValidators();

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		addRequiredValidatorTag();

		return super.doStartTag();
	}

	public Locale[] getAvailableLocales() {
		return _availableLocales;
	}

	public void setAutoFocus(boolean autoFocus) {
		_autoFocus = autoFocus;
	}

	public void setAutoSize(boolean autoSize) {
		_autoSize = autoSize;
	}

	public void setAvailableLocales(Locale[] availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setDisplayWidth(String displayWidth) {
		_displayWidth = displayWidth;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setMaxLength(String maxLength) {
		_maxLength = maxLength;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setXml(String xml) {
		_xml = xml;
	}

	protected void addRequiredValidatorTag() {
		if (!_required) {
			return;
		}

		ValidatorTag validatorTag = new ValidatorTagImpl(
			"required", null, null, false);

		addValidatorTag("required", validatorTag);
	}

	protected void addValidatorTag(
		String validatorName, ValidatorTag validatorTag) {

		if (_validators == null) {
			_validators = new HashMap<String, ValidatorTag>();
		}

		_validators.put(validatorName, validatorTag);
	}

	@Override
	protected void cleanUp() {
		_autoFocus = false;
		_autoSize = false;
		_cssClass = null;
		_disabled = false;
		_displayWidth = ModelHintsConstants.TEXT_DISPLAY_WIDTH;
		_formName = null;
		_id = null;
		_ignoreRequestValue = false;
		_languageId = null;
		_maxLength = null;
		_name = null;
		_required = false;
		_type = "input";
		_validators = null;
		_xml = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		Locale[] availableLocales = _availableLocales;

		if (availableLocales == null) {
			availableLocales = LanguageUtil.getAvailableLocales();
		}

		String formName = _formName;

		if (Validator.isNull(formName)) {
			formName = "fm";
		}

		String id = _id;

		if (Validator.isNull(id)) {
			id = _name;
		}

		request.setAttribute(
			"liferay-ui:input-localized:autoFocus", String.valueOf(_autoFocus));
		request.setAttribute(
			"liferay-ui:input-localized:autoSize", String.valueOf(_autoSize));
		request.setAttribute(
			"liferay-ui:input-localized:availableLocales", availableLocales);
		request.setAttribute("liferay-ui:input-localized:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:input-localized:defaultLanguageId", _defaultLanguageId);
		request.setAttribute(
			"liferay-ui:input-localized:displayWidth", _displayWidth);
		request.setAttribute(
			"liferay-ui:input-localized:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"liferay-ui:input-localized:dynamicAttributes",
			getDynamicAttributes());
		request.setAttribute("liferay-ui:input-localized:formName", formName);
		request.setAttribute("liferay-ui:input-localized:id", id);
		request.setAttribute(
			"liferay-ui:input-localized:ignoreRequestValue",
			String.valueOf(_ignoreRequestValue));
		request.setAttribute(
			"liferay-ui:input-localized:languageId", _languageId);
		request.setAttribute(
			"liferay-ui:input-localized:maxLength", _maxLength);
		request.setAttribute("liferay-ui:input-localized:name", _name);
		request.setAttribute(
			"liferay-ui:input-localized:required", String.valueOf(_required));
		request.setAttribute("liferay-ui:input-localized:type", _type);
		request.setAttribute("liferay-ui:input-localized:xml", _xml);
	}

	protected void updateFormValidators() {
		if (_validators == null) {
			return;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		Map<String, List<ValidatorTag>> validatorTagsMap =
			(Map<String, List<ValidatorTag>>)request.getAttribute(
				"aui:form:validatorTagsMap");

		if (validatorTagsMap != null) {
			List<ValidatorTag> validatorTags = ListUtil.fromMapValues(
				_validators);

			validatorTagsMap.put(_name, validatorTags);
		}
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_localized/page.jsp";

	private boolean _autoFocus;
	private boolean _autoSize;
	private Locale[] _availableLocales;
	private String _cssClass;
	private String _defaultLanguageId;
	private boolean _disabled;
	private String _displayWidth = ModelHintsConstants.TEXT_DISPLAY_WIDTH;
	private String _formName;
	private String _id;
	private boolean _ignoreRequestValue;
	private String _languageId;
	private String _maxLength;
	private String _name;
	private boolean _required;
	private String _type = "input";
	private Map<String, ValidatorTag> _validators;
	private String _xml;

}