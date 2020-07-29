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

package com.liferay.documentum.repository.search;

import com.liferay.petra.string.StringBundler;

/**
 * @author Mika Koivisto
 */
public class DQLNotExpression implements DQLCriterion {

	public DQLNotExpression(DQLCriterion dqlCriterion) {
		_dqlCriterion = dqlCriterion;
	}

	@Override
	public String toQueryFragment() {
		return StringBundler.concat(
			"NOT(", _dqlCriterion.toQueryFragment(), ")");
	}

	private final DQLCriterion _dqlCriterion;

}