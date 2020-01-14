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

package com.liferay.segments.asah.connector.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientFactory;
import com.liferay.segments.asah.connector.internal.processor.AsahSegmentsExperimentProcessor;
import com.liferay.segments.asah.connector.internal.util.AsahUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(immediate = true, service = ModelListener.class)
public class SegmentsExperimentRelModelListener
	extends BaseModelListener<SegmentsExperimentRel> {

	@Override
	public void onAfterCreate(SegmentsExperimentRel segmentsExperimentRel)
		throws ModelListenerException {

		try {
			if (segmentsExperimentRel.isControl()) {
				return;
			}

			_processUpdateSegmentsExperimentRel(segmentsExperimentRel);
		}
		catch (Exception exception) {
			throw new ModelListenerException(
				"Unable to create segments experiment rel " +
					segmentsExperimentRel.getSegmentsExperimentRelId(),
				exception);
		}
	}

	@Override
	public void onAfterRemove(SegmentsExperimentRel segmentsExperimentRel)
		throws ModelListenerException {

		if (segmentsExperimentRel == null) {
			return;
		}

		try {
			SegmentsExperiment segmentsExperiment =
				_segmentsExperimentLocalService.fetchSegmentsExperiment(
					segmentsExperimentRel.getSegmentsExperimentId());

			if (segmentsExperiment == null) {
				return;
			}

			_processUpdateSegmentsExperimentRel(segmentsExperimentRel);
		}
		catch (Exception exception) {
			throw new ModelListenerException(
				"Unable to remove segments experiment rel " +
					segmentsExperimentRel.getSegmentsExperimentRelId(),
				exception);
		}
	}

	@Activate
	protected void activate() {
		_asahSegmentsExperimentProcessor = new AsahSegmentsExperimentProcessor(
			_asahFaroBackendClientFactory, _companyLocalService,
			_groupLocalService, _layoutLocalService, _portal,
			_segmentsEntryLocalService, _segmentsExperienceLocalService);
	}

	private void _processUpdateSegmentsExperimentRel(
			SegmentsExperimentRel segmentsExperimentRel)
		throws PortalException {

		if (AsahUtil.isSkipAsahEvent(
				segmentsExperimentRel.getCompanyId(),
				segmentsExperimentRel.getGroupId())) {

			return;
		}

		_asahSegmentsExperimentProcessor.processUpdateSegmentsExperimentRel(
			segmentsExperimentRel.getSegmentsExperimentKey(),
			_segmentsExperimentRelLocalService.getSegmentsExperimentRels(
				segmentsExperimentRel.getSegmentsExperimentId()));
	}

	@Reference
	private AsahFaroBackendClientFactory _asahFaroBackendClientFactory;

	private AsahSegmentsExperimentProcessor _asahSegmentsExperimentProcessor;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperimentLocalService _segmentsExperimentLocalService;

	@Reference
	private SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;

}