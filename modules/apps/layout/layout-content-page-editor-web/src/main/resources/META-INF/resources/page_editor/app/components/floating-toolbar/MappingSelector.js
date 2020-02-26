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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import ItemSelector from '../../../common/components/ItemSelector';
import {addMappedInfoItem} from '../../actions/index';
import {COMPATIBLE_TYPES} from '../../config/constants/compatibleTypes';
import {PAGE_TYPES} from '../../config/constants/pageTypes';
import {config} from '../../config/index';
import InfoItemService from '../../services/InfoItemService';
import {useDispatch, useSelector} from '../../store/index';

const MAPPING_SOURCE_TYPE_IDS = {
	content: 'content',
	structure: 'structure',
};

const UNMAPPED_OPTION = {
	label: `-- ${Liferay.Language.get('unmapped')} --`,
	value: 'unmapped',
};

function loadFields({
	dispatch,
	fieldType,
	selectedItem,
	selectedMappingTypes,
	selectedSourceTypeId,
}) {
	let promise;

	if (selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure) {
		promise = InfoItemService.getAvailableStructureMappingFields({
			classNameId: selectedMappingTypes.type.id,
			classTypeId: selectedMappingTypes.subtype.id,
			onNetworkStatus: dispatch,
		});
	}
	else if (
		selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content &&
		selectedItem.classNameId &&
		selectedItem.classPK
	) {
		promise = InfoItemService.getAvailableAssetMappingFields({
			classNameId: selectedItem.classNameId,
			classPK: selectedItem.classPK,
			onNetworkStatus: dispatch,
		});
	}

	if (promise) {
		return promise.then(response => {
			if (Array.isArray(response)) {
				return response.filter(
					field =>
						COMPATIBLE_TYPES[fieldType].indexOf(field.type) !== -1
				);
			}

			return [];
		});
	}

	return Promise.resolve([]);
}

export default function MappingSelector({
	fieldType,
	mappedItem,
	onMappingSelect,
}) {
	const dispatch = useDispatch();
	const mappedInfoItems = useSelector(state => state.mappedInfoItems);

	const {selectedMappingTypes} = config;

	const [fields, setFields] = useState([]);
	const [mappedItemTitle, setMappedItemTitle] = useState('');
	const [selectedItem, setSelectedItem] = useState(mappedItem);
	const [selectedSourceTypeId, setSelectedSourceTypeId] = useState(
		MAPPING_SOURCE_TYPE_IDS.content
	);

	const onInfoItemSelect = selectedItem => {
		loadFields({
			dispatch,
			fieldType,
			selectedItem: {
				className: selectedItem.className,
				classNameId: selectedItem.classNameId,
				classPK: selectedItem.classPK,
				title: selectedItem.title,
			},
			selectedSourceTypeId,
		}).then(newFields => {
			setFields(newFields);
		});

		setSelectedItem({...selectedItem, fieldId: '', mappedField: ''});
	};

	const onFieldSelect = event => {
		const data =
			event.target.value === UNMAPPED_OPTION.value
				? {
						classNameId: '',
						classPK: '',
						fieldId: '',
						mappedField: '',
				  }
				: selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content
				? {
						classNameId: selectedItem.classNameId,
						classPK: selectedItem.classPK,
						fieldId: event.target.value,
				  }
				: {mappedField: event.target.value};

		if (selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content) {
			const mappedInfoItem = mappedInfoItems.find(
				item =>
					item.classNameId === selectedItem.classNameId &&
					item.classPK === selectedItem.classPK
			);

			if (!mappedInfoItem) {
				dispatch(
					addMappedInfoItem({title: selectedItem.title, ...data})
				);
			}
		}

		onMappingSelect(data);
	};

	useEffect(() => {
		const infoItem = mappedInfoItems.find(
			infoItem =>
				infoItem.classNameId === selectedItem.classNameId &&
				infoItem.classPK === selectedItem.classPK
		);

		if (infoItem) {
			setMappedItemTitle(infoItem.title);
		}
	}, [mappedInfoItems, selectedItem]);

	useEffect(() => {
		setSelectedSourceTypeId(
			mappedItem.mappedField
				? MAPPING_SOURCE_TYPE_IDS.structure
				: MAPPING_SOURCE_TYPE_IDS.content
		);

		setSelectedItem(mappedItem);
	}, [mappedItem]);

	useEffect(() => {
		const data =
			selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.structure
				? {
						dispatch,
						fieldType,
						selectedMappingTypes,
						selectedSourceTypeId,
				  }
				: {
						dispatch,
						fieldType,
						selectedItem,
						selectedSourceTypeId,
				  };

		loadFields(data).then(newFields => {
			setFields(newFields);
		});
	}, [
		dispatch,
		fieldType,
		selectedItem,
		selectedMappingTypes,
		selectedSourceTypeId,
	]);

	return (
		<>
			{config.pageType === PAGE_TYPES.display && (
				<ClayForm.Group small>
					<label htmlFor="mappingSelectorSourceSelect">
						{Liferay.Language.get('source')}
					</label>
					<ClaySelectWithOption
						aria-label={Liferay.Language.get('source')}
						id="mappingSelectorSourceSelect"
						onChange={event => {
							setSelectedSourceTypeId(event.target.value);
						}}
						options={[
							{
								label: Liferay.Util.sub(
									Liferay.Language.get('x-default'),
									selectedMappingTypes.subtype
										? selectedMappingTypes.subtype.label
										: selectedMappingTypes.type.label
								),
								value: MAPPING_SOURCE_TYPE_IDS.structure,
							},
							{
								label: Liferay.Language.get('specific-content'),
								value: MAPPING_SOURCE_TYPE_IDS.content,
							},
						]}
						value={selectedSourceTypeId}
					/>
				</ClayForm.Group>
			)}
			{selectedSourceTypeId === MAPPING_SOURCE_TYPE_IDS.content && (
				<ClayForm.Group small>
					<ItemSelector
						label={Liferay.Language.get('content')}
						onItemSelect={onInfoItemSelect}
						selectedItemTitle={
							selectedItem.title || mappedItemTitle
						}
					/>
				</ClayForm.Group>
			)}
			<ClayForm.Group small>
				<label htmlFor="mappingSelectorFieldSelect">
					{Liferay.Language.get('field')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('field')}
					disabled={!(fields && fields.length)}
					id="mappingSelectorFieldSelect"
					onChange={onFieldSelect}
					options={
						fields && fields.length
							? [
									UNMAPPED_OPTION,
									...fields.map(({key, label}) => ({
										label,
										value: key,
									})),
							  ]
							: [UNMAPPED_OPTION]
					}
					value={selectedItem.mappedField || selectedItem.fieldId}
				/>
			</ClayForm.Group>
		</>
	);
}

MappingSelector.propTypes = {
	fieldType: PropTypes.oneOf(Object.keys(COMPATIBLE_TYPES)),
	mappedItem: PropTypes.oneOfType([
		PropTypes.shape({
			classNameId: PropTypes.string,
			classPK: PropTypes.string,
			fieldId: PropTypes.string,
		}),
		PropTypes.shape({mappedField: PropTypes.string}),
	]),
	onMappingSelect: PropTypes.func.isRequired,
};
