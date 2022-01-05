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

package com.liferay.portal.search.web.internal.tag.facet.portlet;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.facet.display.builder.AssetTagsSearchFacetDisplayBuilder;
import com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.tag.facet.builder.AssetTagsFacetConfiguration;
import com.liferay.portal.search.web.internal.tag.facet.builder.AssetTagsFacetConfigurationImpl;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lino Alves
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-tag-facet",
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Tag Facet",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/tag/facet/view.jsp",
		"javax.portlet.name=" + TagFacetPortletKeys.TAG_FACET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = Portlet.class
)
public class TagFacetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext =
			_buildDisplayContext(portletSharedSearchResponse, renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			assetTagsSearchFacetDisplayContext);

		if (assetTagsSearchFacetDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected Portal portal;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	private AssetTagsSearchFacetDisplayContext _buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest) {

		Facet facet = portletSharedSearchResponse.getFacet(
			_getAggregationName(renderRequest));

		TagFacetPortletPreferences tagFacetPortletPreferences =
			new TagFacetPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		AssetTagsFacetConfiguration assetTagsFacetConfiguration =
			new AssetTagsFacetConfigurationImpl(facet.getFacetConfiguration());

		AssetTagsSearchFacetDisplayBuilder assetTagsSearchFacetDisplayBuilder =
			_createTagsSearchFacetDisplayBuilder(renderRequest);

		assetTagsSearchFacetDisplayBuilder.setDisplayStyle(
			tagFacetPortletPreferences.getDisplayStyle());
		assetTagsSearchFacetDisplayBuilder.setFacet(facet);
		assetTagsSearchFacetDisplayBuilder.setFrequenciesVisible(
			tagFacetPortletPreferences.isFrequenciesVisible());
		assetTagsSearchFacetDisplayBuilder.setFrequencyThreshold(
			assetTagsFacetConfiguration.getFrequencyThreshold());
		assetTagsSearchFacetDisplayBuilder.setMaxTerms(
			assetTagsFacetConfiguration.getMaxTerms());
		assetTagsSearchFacetDisplayBuilder.setPaginationStartParameterName(
			_getPaginationStartParameterName(portletSharedSearchResponse));

		String parameterName = tagFacetPortletPreferences.getParameterName();

		assetTagsSearchFacetDisplayBuilder.setParameterName(parameterName);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchResponse.getParameterValues(
				parameterName, renderRequest),
			assetTagsSearchFacetDisplayBuilder::setParameterValues);

		return assetTagsSearchFacetDisplayBuilder.build();
	}

	private AssetTagsSearchFacetDisplayBuilder
		_createTagsSearchFacetDisplayBuilder(RenderRequest renderRequest) {

		try {
			return new AssetTagsSearchFacetDisplayBuilder(renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private String _getAggregationName(RenderRequest renderRequest) {
		return portal.getPortletId(renderRequest);
	}

	private String _getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

}