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

package com.liferay.taglib.ui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 */
public class ErrorTag extends IncludeTag implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		if (_hasError && _isShowAlert()) {
			return super.doEndTag();
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (SessionErrors.isEmpty(portletRequest)) {
			return SKIP_BODY;
		}

		_hasError = true;

		if (!SessionErrors.contains(portletRequest, _key)) {
			return SKIP_BODY;
		}

		Object value = getException(portletRequest);

		if (value == null) {
			return SKIP_BODY;
		}

		pageContext.setAttribute("errorException", value);

		return super.doStartTag();
	}

	public String getFocusField() {
		return _focusField;
	}

	public String getKey() {
		return _key;
	}

	public String getMessage() {
		return _message;
	}

	public String getRowBreak() {
		return _rowBreak;
	}

	public String getTargetNode() {
		return _targetNode;
	}

	public boolean isEmbed() {
		return _embed;
	}

	public boolean isTranslateMessage() {
		return _translateMessage;
	}

	public void setEmbed(boolean embed) {
		_embed = embed;
	}

	public void setException(Class<?> exception) {
		_exception = exception;

		if (_exception != null) {
			_key = _exception.getName();
		}
	}

	public void setFocusField(String focusField) {
		_focusField = focusField;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setRowBreak(String rowBreak) {
		_rowBreak = HtmlUtil.unescape(rowBreak);
	}

	public void setTargetNode(String targetNode) {
		_targetNode = targetNode;
	}

	public void setTranslateMessage(boolean translateMessage) {
		_translateMessage = translateMessage;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_embed = true;
		_exception = null;
		_focusField = null;
		_hasError = false;
		_key = null;
		_message = null;
		_rowBreak = StringPool.BLANK;
		_targetNode = null;
		_translateMessage = true;
	}

	protected Object getException(PortletRequest portletRequest) {
		Object value = null;

		if (_exception != null) {
			value = SessionErrors.get(portletRequest, _exception.getName());
		}
		else {
			value = SessionErrors.get(portletRequest, _key);
		}

		return value;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		httpServletRequest.setAttribute(
			"liferay-ui:error:alertIcon", _getAlertIcon());
		httpServletRequest.setAttribute(
			"liferay-ui:error:alertMessage", _getAlertMessage());
		httpServletRequest.setAttribute(
			"liferay-ui:error:alertStyle", _getAlertStyle());
		httpServletRequest.setAttribute(
			"liferay-ui:error:alertTitle", _getAlertTitle());
		httpServletRequest.setAttribute(
			"liferay-ui:error:embed", String.valueOf(_embed));
		httpServletRequest.setAttribute("liferay-ui:error:rowBreak", _rowBreak);

		if (SessionErrors.contains(portletRequest, _key)) {
			String errorMarkerKey = (String)httpServletRequest.getAttribute(
				"liferay-ui:error-marker:key");
			String errorMarkerValue = (String)httpServletRequest.getAttribute(
				"liferay-ui:error-marker:value");

			if (Validator.isNotNull(errorMarkerKey) &&
				Validator.isNotNull(errorMarkerValue)) {

				httpServletRequest.setAttribute(
					errorMarkerKey, errorMarkerValue);

				Object exception = getException(portletRequest);

				if (exception instanceof Exception) {
					httpServletRequest.setAttribute(
						"liferay-ui:error:exception", exception);
				}

				httpServletRequest.setAttribute(
					"liferay-ui:error:focusField", _focusField);
			}
		}
	}

	private String _getAlertIcon() {
		if ((_key != null) && Validator.isNull(_message)) {
			return "exclamation-full";
		}

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (SessionErrors.contains(portletRequest, "warning")) {
			return "warning-full";
		}

		return "exclamation-full";
	}

	private String _getAlertMessage() {
		if ((_key != null) && Validator.isNull(_message)) {
			return _getBodyContentString();
		}

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (SessionErrors.contains(portletRequest, "warning")) {
			String alertMessage = _message;

			if (_message == null) {
				alertMessage = (String)SessionErrors.get(
					portletRequest, "warning");
			}

			if (_translateMessage) {
				ResourceBundle resourceBundle =
					TagResourceBundleUtil.getResourceBundle(pageContext);

				alertMessage = LanguageUtil.get(
					request, resourceBundle, alertMessage);
			}

			return alertMessage;
		}

		if (_key == null) {
			return LanguageUtil.get(request, "your-request-failed-to-complete");
		}

		if (SessionErrors.contains(portletRequest, _key)) {
			String alertMessage = _message;

			if (_translateMessage) {
				ResourceBundle resourceBundle =
					TagResourceBundleUtil.getResourceBundle(pageContext);

				alertMessage = LanguageUtil.get(
					request, resourceBundle, _message);
			}

			return alertMessage;
		}

		return _getBodyContentString();
	}

	private String _getAlertStyle() {
		if ((_key != null) && Validator.isNull(_message)) {
			return "danger";
		}

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (SessionErrors.contains(portletRequest, "warning")) {
			return "warning";
		}

		return "danger";
	}

	private String _getAlertTitle() {
		if ((_key != null) && Validator.isNull(_message)) {
			return LanguageUtil.get(request, "error-colon");
		}

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (SessionErrors.contains(portletRequest, "warning")) {
			return LanguageUtil.get(request, "warning-colon");
		}

		return LanguageUtil.get(request, "error-colon");
	}

	private String _getBodyContentString() {
		if (bodyContent != null) {
			return bodyContent.getString();
		}

		return StringPool.BLANK;
	}

	private boolean _isShowAlert() {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if ((_key != null) && Validator.isNull(_message)) {
			if (SessionErrors.contains(portletRequest, _key) &&
				Validator.isNotNull(_getBodyContentString())) {

				return true;
			}

			return false;
		}

		if (SessionErrors.contains(portletRequest, "warning")) {
			return true;
		}

		if (_key == null) {
			return true;
		}

		if (SessionErrors.contains(portletRequest, _key)) {
			return true;
		}

		return false;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "liferay-ui:error:";

	private static final String _PAGE = "/html/taglib/ui/error/page.jsp";

	private boolean _embed = true;
	private Class<?> _exception;
	private String _focusField;
	private boolean _hasError;
	private String _key;
	private String _message;
	private String _rowBreak = StringPool.BLANK;
	private String _targetNode;
	private boolean _translateMessage = true;

}