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

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.info.item.renderer.InfoItemTemplatedRenderer;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = FragmentRenderer.class)
public class ContentObjectFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "content"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						).put(
							"typeOptions",
							JSONUtil.put("enableSelectTemplate", true)
						))))
		).toString();
	}

	@Override
	public String getIcon() {
		return "web-content";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "content");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		JSONObject jsonObject = _getFieldValueJSONObject(
			fragmentRendererContext);

		Optional<Object> displayObjectOptional =
			fragmentRendererContext.getDisplayObjectOptional();

		if (!displayObjectOptional.isPresent() && (jsonObject == null)) {
			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"the-selected-content-will-be-shown-here");
			}

			return;
		}

		Object displayObject = null;

		if (jsonObject != null) {
			displayObject = _getDisplayObject(
				jsonObject.getString("className"),
				jsonObject.getLong("classPK"), displayObjectOptional);
		}
		else {
			displayObject = displayObjectOptional.orElse(null);
		}

		if (displayObject == null) {
			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"the-selected-content-is-no-longer-available.-please-" +
						"select-another");
			}

			return;
		}

		Tuple tuple = _getTuple(
			displayObject.getClass(), fragmentRendererContext);

		InfoItemRenderer infoItemRenderer = (InfoItemRenderer)tuple.getObject(
			0);

		if (infoItemRenderer == null) {
			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"there-are-no-available-renderers-for-the-selected-" +
						"content");
			}

			return;
		}

		if (infoItemRenderer instanceof InfoItemTemplatedRenderer) {
			InfoItemTemplatedRenderer<Object> infoItemTemplatedRenderer =
				(InfoItemTemplatedRenderer<Object>)infoItemRenderer;

			infoItemTemplatedRenderer.render(
				displayObject, (String)tuple.getObject(1), httpServletRequest,
				httpServletResponse);
		}
		else {
			infoItemRenderer.render(
				displayObject, httpServletRequest, httpServletResponse);
		}
	}

	private Object _getDisplayObject(
		String className, long classPK,
		Optional<Object> displayObjectOptional) {

		InfoDisplayContributor<?> infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		try {
			InfoDisplayObjectProvider<?> infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

			if (infoDisplayObjectProvider == null) {
				return displayObjectOptional.orElse(null);
			}

			return infoDisplayObjectProvider.getDisplayObject();
		}
		catch (Exception exception) {
		}

		return displayObjectOptional.orElse(null);
	}

	private JSONObject _getFieldValueJSONObject(
		FragmentRendererContext fragmentRendererContext) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		return (JSONObject)_fragmentEntryConfigurationParser.getFieldValue(
			getConfiguration(fragmentRendererContext),
			fragmentEntryLink.getEditableValues(), "itemSelector");
	}

	private Tuple _getTuple(
		Class<?> displayObjectClass,
		FragmentRendererContext fragmentRendererContext) {

		List<InfoItemRenderer> infoItemRenderers =
			FragmentRendererUtil.getInfoItemRenderers(
				displayObjectClass, _infoItemRendererTracker);

		if (infoItemRenderers == null) {
			return null;
		}

		InfoItemRenderer defaultInfoItemRenderer = infoItemRenderers.get(0);

		JSONObject jsonObject = _getFieldValueJSONObject(
			fragmentRendererContext);

		if (jsonObject == null) {
			return new Tuple(defaultInfoItemRenderer);
		}

		JSONObject templateJSONObject = jsonObject.getJSONObject("template");

		if (templateJSONObject == null) {
			return new Tuple(defaultInfoItemRenderer);
		}

		String templateKey = templateJSONObject.getString("templateKey");

		String infoItemRendererKey = templateJSONObject.getString(
			"infoItemRendererKey");

		InfoItemRenderer infoItemRenderer =
			_infoItemRendererTracker.getInfoItemRenderer(infoItemRendererKey);

		if (infoItemRenderer != null) {
			return new Tuple(infoItemRenderer, templateKey);
		}

		return new Tuple(defaultInfoItemRenderer);
	}

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private InfoItemRendererTracker _infoItemRendererTracker;

}