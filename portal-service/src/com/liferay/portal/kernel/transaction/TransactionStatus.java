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

package com.liferay.portal.kernel.transaction;

/**
 * @author Shuyang Zhou
 */
public class TransactionStatus {

	public TransactionStatus(
		boolean newTransaction, boolean rollbackOnly, boolean completed) {

		_newTransaction = newTransaction;
		_rollbackOnly = rollbackOnly;
		_completed = completed;
	}

	public boolean isCompleted() {
		return _completed;
	}

	public boolean isNewTransaction() {
		return _newTransaction;
	}

	public boolean isRollbackOnly() {
		return _rollbackOnly;
	}

	private boolean _completed;
	private boolean _newTransaction;
	private boolean _rollbackOnly;

}