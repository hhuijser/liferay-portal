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

import React, {useEffect, useState} from 'react';

import ErrorBoundary from '../shared/ErrorBoundary';
import ThemeContext from '../shared/ThemeContext';
import {fetchData} from '../utils/fetch';
import {getSXPBlueprintForm} from '../utils/utils';
import EditSXPElementForm from './EditSXPElementForm';

export default function ({
	defaultLocale,
	namespace,
	redirectURL,
	sxpElementId,
}) {
	const [sxpElements, setSXPElements] = useState(null);
	const [predefinedVariables, setPredefinedVariables] = useState(null);

	useEffect(() => {
		fetchData(
			`/o/search-experiences-rest/v1.0/sxp-elements/${sxpElementId}`,
			{
				method: 'GET',
			},
			(responseContent) => setSXPElements(responseContent),
			() => setSXPElements({})
		);

		fetchData(
			'/o/search-experiences-rest/v1.0/sxp-parameter-contributor-definitions',
			{
				method: 'GET',
			},
			(responseContent) => setPredefinedVariables(responseContent.items),
			() => setPredefinedVariables([])
		);
	}, []); //eslint-disable-line

	if (!sxpElements || !predefinedVariables) {
		return null;
	}

	return (
		<ThemeContext.Provider
			value={{
				defaultLocale,
				namespace,
				redirectURL,
			}}
		>
			<div className="edit-sxp-element-root">
				<ErrorBoundary>
					<EditSXPElementForm
						initialConfiguration={getSXPBlueprintForm(sxpElements)}
						initialDescription={
							sxpElements.description_i18n || {
								[defaultLocale]: sxpElements.description,
							}
						}
						initialTitle={
							sxpElements.title_i18n || {
								[defaultLocale]: sxpElements.title,
							}
						}
						predefinedVariables={predefinedVariables}
						readOnly={sxpElements.readOnly}
						sxpElementId={sxpElementId}
						type={sxpElements.type}
					/>
				</ErrorBoundary>
			</div>
		</ThemeContext.Provider>
	);
}
