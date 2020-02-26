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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useMemo, useRef, useState} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../prop-types/index';
import {switchSidebarPanel} from '../actions/index';
import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {config} from '../config/index';
import selectShowLayoutItemRemoveButton from '../selectors/selectShowLayoutItemRemoveButton';
import {useDispatch, useSelector} from '../store/index';
import deleteItem from '../thunks/deleteItem';
import moveItem from '../thunks/moveItem';
import {
	useActiveItemId,
	useHoverItem,
	useHoveredItemId,
	useIsHovered,
	useIsSelected,
	useSelectItem,
} from './Controls';
import useDragAndDrop, {
	DragDropManagerImpl,
	TARGET_POSITION,
} from './useDragAndDrop';

const TOPPER_BAR_HEIGHT = 24;

const TopperListItem = React.forwardRef(
	({children, className, expand, ...props}, ref) => (
		<li
			{...props}
			className={classNames(
				'page-editor__topper__item',
				'tbar-item',
				{'tbar-item-expand': expand},
				className
			)}
			ref={ref}
		>
			{children}
		</li>
	)
);

TopperListItem.propTypes = {
	expand: PropTypes.bool,
};

export default function Topper({children, item, itemRef, layoutData}) {
	const containerRef = useRef(null);
	const dispatch = useDispatch();
	const store = useSelector(state => state);
	const activeItemId = useActiveItemId();
	const hoveredItemId = useHoveredItemId();
	const hoverItem = useHoverItem();
	const isHovered = useIsHovered();
	const isSelected = useIsSelected();
	const selectItem = useSelectItem();

	const {
		store: {dropTargetItemId, targetPosition},
	} = useContext(DragDropManagerImpl);

	const {canDrop, drag, drop, isDragging, isOver} = useDragAndDrop({
		containerRef,
		item,
		layoutData,
		onDragEnd: data =>
			dispatch(
				moveItem({
					...data,
					store,
				})
			),
	});

	const itemIsRemovable = useMemo(() => isRemovable(item, layoutData), [
		item,
		layoutData,
	]);
	const showRemoveButton =
		useSelector(selectShowLayoutItemRemoveButton) && itemIsRemovable;

	const childrenElement = children({canDrop, isOver});

	const commentsPanelId = config.sidebarPanels.comments.sidebarPanelId;

	const fragmentEntryLinks = store.fragmentEntryLinks;

	const [isInset, setIsInset] = useState(false);
	const [windowScrollPosition, setWindowScrollPosition] = useState(0);

	const getName = (item, fragmentEntryLinks) => {
		let name;

		if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
			name = fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
		}
		else if (item.type === LAYOUT_DATA_ITEM_TYPES.container) {
			name = LAYOUT_DATA_ITEM_TYPE_LABELS.container;
		}
		else if (item.type === LAYOUT_DATA_ITEM_TYPES.column) {
			name = LAYOUT_DATA_ITEM_TYPE_LABELS.column;
		}
		else if (item.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
			name = LAYOUT_DATA_ITEM_TYPE_LABELS.dropZone;
		}
		else if (item.type === LAYOUT_DATA_ITEM_TYPES.row) {
			name = LAYOUT_DATA_ITEM_TYPE_LABELS.row;
		}

		return name;
	};

	const fragmentShouldBeHovered = () => {
		const [activeItemfragmentEntryLinkId] = activeItemId
			? activeItemId.split('-')
			: '';
		const [hoveredItemfragmentEntryLinkId] = hoveredItemId
			? hoveredItemId.split('-')
			: '';

		const childIsActive =
			Number(activeItemfragmentEntryLinkId) ===
			item.config.fragmentEntryLinkId;
		const childIsHovered =
			Number(hoveredItemfragmentEntryLinkId) ===
			item.config.fragmentEntryLinkId;

		return (
			item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
			(hoveredItemId === item.itemId || (childIsActive && childIsHovered))
		);
	};

	useEffect(() => {
		const handleWindowScroll = () => {
			setWindowScrollPosition(window.scrollY);
		};

		window.addEventListener('scroll', handleWindowScroll);

		return () => {
			window.removeEventListener('scroll', handleWindowScroll);
		};
	}, []);

	useEffect(() => {
		if (itemRef && itemRef.current) {
			const itemTop =
				itemRef.current.getBoundingClientRect().y - TOPPER_BAR_HEIGHT;
			const controlMenuHeight = document
				.getElementById('ControlMenu')
				.getBoundingClientRect().height;
			const managementToolbarHeight = document
				.querySelector('.page-editor__toolbar')
				.getBoundingClientRect().height;
			const pageEditorTop = document
				.getElementById('page-editor')
				.getBoundingClientRect().y;

			if (
				itemTop < pageEditorTop ||
				itemTop < controlMenuHeight + managementToolbarHeight
			) {
				setIsInset(true);
			}
			else {
				setIsInset(false);
			}
		}
	}, [itemRef, layoutData, windowScrollPosition]);

	return (
		<div
			className={classNames({
				active: isSelected(item.itemId),
				'drag-over-bottom':
					targetPosition === TARGET_POSITION.BOTTOM &&
					dropTargetItemId === item.itemId,
				'drag-over-middle':
					targetPosition === TARGET_POSITION.MIDDLE &&
					dropTargetItemId === item.itemId,
				'drag-over-top':
					targetPosition === TARGET_POSITION.TOP &&
					dropTargetItemId === item.itemId,
				dragged: isDragging,
				hovered: isHovered(item.itemId) || fragmentShouldBeHovered(),
				'page-editor__topper': true,
			})}
			onClick={event => {
				event.stopPropagation();

				if (isDragging) {
					return;
				}

				const multiSelect = event.shiftKey;

				selectItem(item.itemId, {multiSelect});
			}}
			onMouseLeave={event => {
				event.stopPropagation();

				if (isDragging) {
					return;
				}

				if (isHovered(item.itemId)) {
					hoverItem(null);
				}
			}}
			onMouseOver={event => {
				event.stopPropagation();

				if (isDragging) {
					return;
				}

				hoverItem(item.itemId);
			}}
			ref={containerRef}
		>
			<div
				className={classNames('page-editor__topper__bar', 'tbar', {
					'page-editor__topper__bar--inset': isInset,
				})}
			>
				<ul className="tbar-nav">
					<TopperListItem
						className="page-editor__topper__drag-handler"
						ref={drag}
					>
						<ClayIcon
							className="page-editor__topper__drag-icon page-editor__topper__icon"
							symbol="drag"
						/>
					</TopperListItem>
					<TopperListItem
						className="page-editor__topper__title"
						expand
					>
						{getName(item, fragmentEntryLinks) ||
							Liferay.Language.get('element')}
					</TopperListItem>
					{item.type === LAYOUT_DATA_ITEM_TYPES.fragment && (
						<TopperListItem>
							<ClayButton
								displayType="unstyled"
								small
								title={Liferay.Language.get('comments')}
							>
								<ClayIcon
									className="page-editor__topper__icon"
									onClick={() => {
										dispatch(
											switchSidebarPanel({
												sidebarOpen: true,
												sidebarPanelId: commentsPanelId,
											})
										);
									}}
									symbol="comments"
								/>
							</ClayButton>
						</TopperListItem>
					)}
					{showRemoveButton && (
						<TopperListItem>
							<ClayButton
								displayType="unstyled"
								onClick={event => {
									event.stopPropagation();

									dispatch(
										deleteItem({
											itemId: item.itemId,
											store,
										})
									);
								}}
								small
								title={Liferay.Language.get('remove')}
							>
								<ClayIcon
									className="page-editor__topper__icon"
									symbol="times-circle"
								/>
							</ClayButton>
						</TopperListItem>
					)}
				</ul>
			</div>

			<div className="page-editor__topper__content" ref={drop}>
				{childrenElement}
			</div>
		</div>
	);
}

Topper.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

function isRemovable(item, layoutData) {
	function hasDropZoneChildren(item, layoutData) {
		return item.children.some(childrenId => {
			const children = layoutData.items[childrenId];

			return children.type === LAYOUT_DATA_ITEM_TYPES.dropZone
				? true
				: hasDropZoneChildren(children, layoutData);
		});
	}

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.dropZone ||
		item.type === LAYOUT_DATA_ITEM_TYPES.column
	) {
		return false;
	}

	return !hasDropZoneChildren(item, layoutData);
}
