/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.admin.web.internal.type;

import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context.CSDiagramSettingDisplayContext;
import com.liferay.commerce.shop.by.diagram.configuration.CSDiagramSettingImageConfiguration;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingService;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramTypeRegistry;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.shop.by.diagram.configuration.CSDiagramSettingImageConfiguration",
	enabled = false, immediate = true,
	property = {
		"commerce.product.definition.diagram.type.key=" + SVGCSDiagramType.KEY,
		"commerce.product.definition.diagram.type.order:Integer=200"
	},
	service = CSDiagramType.class
)
public class SVGCSDiagramType implements CSDiagramType {

	public static final String KEY = "diagram.type.svg";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "svg");
	}

	@Override
	public void render(
			CSDiagramSetting csDiagramSetting,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new CSDiagramSettingDisplayContext(
				_actionHelper, httpServletRequest,
				_csDiagramSettingImageConfiguration, _csDiagramSettingService,
				_csDiagramTypeRegistry, _dlURLHelper, _itemSelector));

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/diagram_type/svg.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_csDiagramSettingImageConfiguration =
			ConfigurableUtil.createConfigurable(
				CSDiagramSettingImageConfiguration.class, properties);
	}

	@Reference
	private ActionHelper _actionHelper;

	private volatile CSDiagramSettingImageConfiguration
		_csDiagramSettingImageConfiguration;

	@Reference
	private CSDiagramSettingService _csDiagramSettingService;

	@Reference
	private CSDiagramTypeRegistry _csDiagramTypeRegistry;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.shop.by.diagram.web)"
	)
	private ServletContext _servletContext;

}