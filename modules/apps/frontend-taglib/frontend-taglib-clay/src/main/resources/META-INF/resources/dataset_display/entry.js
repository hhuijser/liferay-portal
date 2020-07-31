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

import {render, useThunk} from 'frontend-js-react-web';
import React, {useReducer} from 'react';

import DatasetDisplay from './DatasetDisplay';
import {initializeConfig} from './config';
import ViewsContext, {viewsReducer} from './views/ViewsContext';

const App = ({apiURL, appURL, ...props}) => {
	const {activeViewSettings, portletId, views} = props;
	const activeView = activeViewSettings.name
		? views.find(({name}) => name === activeViewSettings.name)
		: views[0];
	const [state, dispatch] = useThunk(
		useReducer(viewsReducer, {
			activeView,
			views,
		})
	);

	initializeConfig({apiURL, appURL, portletId});

	return (
		<ViewsContext.Provider value={[state, dispatch]}>
			<DatasetDisplay {...props} />
		</ViewsContext.Provider>
	);
};

export default (...data) => render(App, ...data);
