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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

export default function SidebarPanelContent({padded = true, ...props}) {
	return (
		<div
			{...props}
			className={classNames({
				[props.className]: !!props.className,
				'px-3': padded,
			})}
		/>
	);
}

SidebarPanelContent.propTypes = {
	padded: PropTypes.bool,
};
