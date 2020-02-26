/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import {usePrevious} from 'frontend-js-react-web';
import React, {useCallback, useContext, useEffect, useMemo} from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import QuickActionKebab from '../../shared/components/quick-action-kebab/QuickActionKebab.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import ToolbarWithSelection from '../../shared/components/toolbar-with-selection/ToolbarWithSelection.es';
import AssigneeFilter from '../filter/AssigneeFilter.es';
import ProcessStatusFilter, {
	processStatusConstants,
} from '../filter/ProcessStatusFilter.es';
import ProcessStepFilter from '../filter/ProcessStepFilter.es';
import SLAStatusFilter from '../filter/SLAStatusFilter.es';
import TimeRangeFilter from '../filter/TimeRangeFilter.es';
import {ModalContext} from './modal/ModalContext.es';
import {InstanceListContext} from './store/InstanceListPageStore.es';

const Header = ({
	filterKeys,
	items = [],
	routeParams,
	selectedFilters,
	totalCount,
}) => {
	const {
		selectAll,
		selectedItems,
		setSelectAll,
		setSelectedItems,
	} = useContext(InstanceListContext);
	const previousCount = usePrevious(totalCount);
	const {bulkModal, setBulkModal, setSingleModal} = useContext(ModalContext);

	const kebabItems = [
		{
			icon: 'change',
			label: Liferay.Language.get('reassign-task'),
			onClick: () => {
				if (
					selectedItems.length > 1 ||
					selectedItems[0].taskNames.length > 1
				) {
					setBulkModal({...bulkModal, visible: true});
				}
				else {
					setSingleModal({
						selectedItem: selectedItems[0],
						visible: true,
					});
				}
			},
		},
	];

	const selectedOnPage = useMemo(
		() =>
			selectedItems.filter(item => items.find(({id}) => id === item.id)),
		[items, selectedItems]
	);

	const allPageSelected =
		items.length > 0 && items.length === selectedOnPage.length;

	const checkbox = {
		checked: allPageSelected || selectAll,
		indeterminate:
			selectedOnPage.length > 0 && !allPageSelected && !selectAll,
	};

	const remainingItems = useMemo(() => {
		return items.filter(
			item =>
				!selectedItems.find(({id}) => item.id === id) &&
				item.status !== processStatusConstants.completed
		);
	}, [items, selectedItems]);

	const toolbarActive = useMemo(() => selectedItems.length > 0, [
		selectedItems,
	]);

	useEffect(() => {
		if (
			selectAll &&
			remainingItems.length > 0 &&
			previousCount === totalCount
		) {
			setSelectedItems(
				items.filter(
					item => item.status !== processStatusConstants.completed
				)
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items]);

	useEffect(() => {
		setSelectAll(totalCount > 0 && totalCount === selectedItems.length);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [totalCount]);

	const handleClear = () => {
		setSelectedItems([]);
		setSelectAll(false);
	};

	const handleCheck = useCallback(
		checked => () => {
			const updatedItems = checked
				? [...selectedItems, ...remainingItems]
				: selectedItems.filter(
						item => !items.find(({id}) => item.id === id)
				  );

			setSelectAll(totalCount > 0 && totalCount === updatedItems.length);
			setSelectedItems(updatedItems);
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[items, remainingItems, selectedItems]
	);

	const statusesFilterItem = useMemo(
		() => selectedFilters.find(filter => filter.key === 'statuses'),
		[selectedFilters]
	);
	const {name} = statusesFilterItem ? statusesFilterItem.items[0] : {};
	const completedStatusSelected = useMemo(
		() =>
			selectedFilters.length > 0 && statusesFilterItem
				? name === processStatusConstants.completed
				: false,
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[name]
	);

	const selectedFilterItems = useMemo(
		() =>
			selectedFilters.filter(
				filter =>
					completedStatusSelected ||
					filter.key !== filterConstants.timeRange.key
			),
		[completedStatusSelected, selectedFilters]
	);

	return (
		<>
			<ToolbarWithSelection
				{...checkbox}
				active={toolbarActive}
				handleCheck={handleCheck(
					!checkbox.indeterminate && !selectAll && !allPageSelected
				)}
				handleClear={handleClear}
				handleSelectAll={() => {
					setSelectedItems(items);
					setSelectAll(true);
				}}
				selectAll={selectAll}
				selectedCount={selectedItems.length}
				totalCount={totalCount}
			>
				{toolbarActive ? (
					<ClayManagementToolbar.Item className="navbar-nav-last">
						<div
							className="autofit-col"
							data-testid="headerQuickAction"
						>
							<QuickActionKebab items={kebabItems} />
						</div>
					</ClayManagementToolbar.Item>
				) : (
					<>
						<ClayManagementToolbar.Item>
							<strong className="ml-0 mr-0 navbar-text">
								{Liferay.Language.get('filter-by')}
							</strong>
						</ClayManagementToolbar.Item>

						<SLAStatusFilter />

						<ProcessStatusFilter />

						{completedStatusSelected && (
							<TimeRangeFilter
								options={{
									withSelectionTitle: false,
								}}
							/>
						)}

						<ProcessStepFilter processId={routeParams.processId} />

						<AssigneeFilter processId={routeParams.processId} />
					</>
				)}
			</ToolbarWithSelection>

			{selectedFilterItems.length > 0 && (
				<ResultsBar>
					<ResultsBar.TotalCount
						search={routeParams.search}
						totalCount={totalCount}
					/>

					<ResultsBar.FilterItems
						filters={selectedFilterItems}
						{...routeParams}
					/>

					<ResultsBar.Clear
						filterKeys={filterKeys}
						filters={selectedFilters}
						{...routeParams}
					/>
				</ResultsBar>
			)}
		</>
	);
};

export {Header};
