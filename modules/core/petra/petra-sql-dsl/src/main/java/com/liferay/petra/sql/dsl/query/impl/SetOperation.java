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

package com.liferay.petra.sql.dsl.query.impl;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.ast.impl.BaseASTNode;
import com.liferay.petra.sql.dsl.query.DSLQuery;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class SetOperation extends BaseASTNode implements DSLQuery {

	public SetOperation(
		DSLQuery leftDSLQuery, SetOperationType setOperationType,
		DSLQuery rightDSLQuery) {

		_leftDSLQuery = Objects.requireNonNull(leftDSLQuery);
		_setOperationType = Objects.requireNonNull(setOperationType);
		_rightDSLQuery = Objects.requireNonNull(rightDSLQuery);
	}

	public DSLQuery getLeftDSLQuery() {
		return _leftDSLQuery;
	}

	public DSLQuery getRightDSLQuery() {
		return _rightDSLQuery;
	}

	public SetOperationType getSetOperationType() {
		return _setOperationType;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		_leftDSLQuery.toSQL(consumer, astNodeListener);

		consumer.accept(" ");

		consumer.accept(_setOperationType.toString());

		consumer.accept(" ");

		_rightDSLQuery.toSQL(consumer, astNodeListener);
	}

	private final DSLQuery _leftDSLQuery;
	private final DSLQuery _rightDSLQuery;
	private final SetOperationType _setOperationType;

}