/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for Words. This utility wraps
 * {@link com.liferay.portal.service.impl.WordsServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see WordsService
 * @see com.liferay.portal.service.base.WordsServiceBaseImpl
 * @see com.liferay.portal.service.impl.WordsServiceImpl
 * @generated
 */
@ProviderType
public class WordsServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.WordsServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Checks the spelling of a block of text.
	*
	* <p>
	* This method handles spell checking of text. It takes a block of text
	* as input,and returns a list of any incorrectly spelled words found.
	* </p>
	*
	* @param text the block of text to be spell checked.
	* @return the mis-spelled words
	*/
	public static com.liferay.portal.kernel.json.JSONObject checkSpelling(
		java.lang.String text) {
		return getService().checkSpelling(text);
	}

	/**
	* Finds suggestions for a misspelled word.
	*
	* <p>
	* This method finds suggested spellings for a misspelled word.
	* It takes a misspelled word as input,and returns a list of
	* suggested correctly spelled words.
	* </p>
	*
	* @param word the misspelled word.
	* @return the suggested corrections.
	*/
	public static java.util.List<java.lang.String> getSuggestions(
		java.lang.String word) {
		return getService().getSuggestions(word);
	}

	public static WordsService getService() {
		if (_service == null) {
			_service = (WordsService)PortalBeanLocatorUtil.locate(WordsService.class.getName());

			ReferenceRegistry.registerReference(WordsServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(WordsService service) {
	}

	private static WordsService _service;
}