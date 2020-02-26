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

import {useEffect, useMemo} from 'react';

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import {config} from '../../config/index';
import Processors from '../../processors/index';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';
import {useActiveItemId} from '../Controls';
import {
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from './EditableProcessorContext';
import getAllEditables from './getAllEditables';
import getEditableElementId from './getEditableElementId';
import getEditableUniqueId from './getEditableUniqueId';

export default function FragmentContentProcessor({
	element,
	fragmentEntryLinkId,
}) {
	const activeItemId = useActiveItemId();
	const dispatch = useDispatch();
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const languageId = useSelector(
		state => state.languageId || config.defaultLanguageId
	);
	const segmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);

	const editableElement = useMemo(
		() =>
			element
				? getAllEditables(element).find(
						editableElement =>
							getEditableUniqueId(
								fragmentEntryLinkId,
								getEditableElementId(editableElement)
							) === editableProcessorUniqueId
				  )
				: null,
		[editableProcessorUniqueId, element, fragmentEntryLinkId]
	);

	const editableValues = useSelector(
		state =>
			state.fragmentEntryLinks[fragmentEntryLinkId] &&
			state.fragmentEntryLinks[fragmentEntryLinkId].editableValues
	);

	useEffect(() => {
		setEditableProcessorUniqueId(null);
	}, [activeItemId, setEditableProcessorUniqueId]);

	useEffect(() => {
		if (!editableElement || !editableValues) {
			return;
		}

		const editableId = getEditableElementId(editableElement);
		const editableType =
			editableElement.getAttribute('type') ||
			EDITABLE_TYPES.backgroundImage;

		const processorKey =
			editableType === EDITABLE_TYPES.backgroundImage
				? BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
				: EDITABLE_FRAGMENT_ENTRY_PROCESSOR;

		const editableValue = editableValues[processorKey][editableId];
		const processor = Processors[editableType] || Processors.fallback;

		processor.createEditor(
			editableElement,
			value => {
				let nextEditableValue = {
					...editableValue,
				};

				if (segmentsExperienceId) {
					nextEditableValue = {
						...nextEditableValue,

						[segmentsExperienceId]: {
							...(nextEditableValue[segmentsExperienceId] || {}),
							[languageId]: value,
						},
					};
				}
				else {
					nextEditableValue = {
						...nextEditableValue,
						[languageId]: value,
					};
				}

				dispatch(
					updateEditableValues({
						editableValues: {
							...editableValues,
							[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
								...editableValues[
									EDITABLE_FRAGMENT_ENTRY_PROCESSOR
								],
								[editableId]: nextEditableValue,
							},
						},
						fragmentEntryLinkId,
						segmentsExperienceId,
					})
				);
			},
			() => {
				processor.destroyEditor(editableElement, editableValue.config);
				setEditableProcessorUniqueId(null);
			}
		);

		return () => {
			if (!editableProcessorUniqueId) {
				processor.destroyEditor(editableElement, editableValue.config);
			}
		};
	}, [
		dispatch,
		editableElement,
		editableProcessorUniqueId,
		editableValues,
		fragmentEntryLinkId,
		languageId,
		segmentsExperienceId,
		setEditableProcessorUniqueId,
	]);

	return null;
}
