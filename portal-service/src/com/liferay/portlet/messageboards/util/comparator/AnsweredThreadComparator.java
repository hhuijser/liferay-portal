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

package com.liferay.portlet.messageboards.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.messageboards.model.MBStatsUser;

/**
 * @author Jonathan McCann
 */
public class AnsweredThreadComparator extends OrderByComparator {

	public static final String ORDER_BY_ASC = "answerCount ASC";

	public static final String ORDER_BY_DESC = "answerCount DESC";

	public static final String[] ORDER_BY_FIELDS = {"answerCount"};

	public AnsweredThreadComparator() {
		this(false);
	}

	public AnsweredThreadComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		MBStatsUser mbStatsUser1 = (MBStatsUser)obj1;
		MBStatsUser mbStatsUser2 = (MBStatsUser)obj2;

		int answerCount1 = mbStatsUser1.getAnswerCount();
		int answerCount2 = mbStatsUser2.getAnswerCount();

		int value = 0;

		if (answerCount1 < answerCount2) {
			value = -1;
		}
		else if (answerCount1 > answerCount2) {
			value = 1;
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}