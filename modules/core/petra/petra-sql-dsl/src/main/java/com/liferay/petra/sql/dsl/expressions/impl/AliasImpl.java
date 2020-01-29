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

package com.liferay.petra.sql.dsl.expressions.impl;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.ast.impl.BaseASTNode;
import com.liferay.petra.sql.dsl.expressions.Alias;
import com.liferay.petra.sql.dsl.expressions.Expression;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class AliasImpl<T> extends BaseASTNode implements Alias<T> {

	public AliasImpl(Expression<T> expression, String name) {
		_expression = Objects.requireNonNull(expression);
		_name = Objects.requireNonNull(name);
	}

	@Override
	public Expression<T> getExpression() {
		return _expression;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_name);
	}

	private final Expression<T> _expression;
	private final String _name;

}