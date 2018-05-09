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

package com.liferay.portal.struts.bundle.strutsactionregistryutil;

import com.liferay.portal.kernel.struts.StrutsPortletAction;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {
		"path=TestStrutsPortletAction",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestStrutsPortletAction implements StrutsPortletAction {

	@Override
	public boolean isCheckMethodOnProcessAction() {
		_atomicBoolean.set(Boolean.TRUE);

		return false;
	}

	@Override
	public void processAction(
		PortletConfig portletConfig, ActionRequest actionRequest,
		ActionResponse actionResponse) {
	}

	@Override
	public void processAction(
		StrutsPortletAction originalStrutsPortletAction,
		PortletConfig portletConfig, ActionRequest actionRequest,
		ActionResponse actionResponse) {
	}

	@Override
	public String render(
		PortletConfig portletConfig, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		return null;
	}

	@Override
	public String render(
		StrutsPortletAction originalStrutsPortletAction,
		PortletConfig portletConfig, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		return null;
	}

	@Override
	public void serveResource(
		PortletConfig portletConfig, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse) {
	}

	@Override
	public void serveResource(
		StrutsPortletAction originalStrutsPortletAction,
		PortletConfig portletConfig, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse) {
	}

	@Reference(target = "(test=AtomicState)", unbind = "-")
	protected void setAtomicBoolean(AtomicBoolean atomicBoolean) {
		_atomicBoolean = atomicBoolean;
	}

	private AtomicBoolean _atomicBoolean;

}