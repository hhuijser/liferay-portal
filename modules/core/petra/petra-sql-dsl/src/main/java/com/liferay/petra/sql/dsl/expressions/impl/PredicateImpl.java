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
import com.liferay.petra.sql.dsl.expressions.Expression;
import com.liferay.petra.sql.dsl.expressions.Predicate;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class PredicateImpl extends BaseASTNode implements Predicate {

	public PredicateImpl(
		Expression<?> leftExpression, Operand operand,
		Expression<?> rightExpression) {

		this(leftExpression, operand, rightExpression, false);
	}

	@Override
	public Predicate and(Expression<Boolean> expression) {
		if (expression == null) {
			return this;
		}

		return new PredicateImpl(this, Operand.AND, expression);
	}

	public Expression<?> getLeftExpression() {
		return _leftExpression;
	}

	public Operand getOperand() {
		return _operand;
	}

	public Expression<?> getRightExpression() {
		return _rightExpression;
	}

	public boolean isWrapParentheses() {
		return _wrapParentheses;
	}

	@Override
	public Predicate or(Expression<Boolean> expression) {
		if (expression == null) {
			return this;
		}

		return new PredicateImpl(this, Operand.OR, expression);
	}

	@Override
	public Predicate withParentheses() {
		if (_wrapParentheses) {
			return this;
		}

		return new PredicateImpl(
			_leftExpression, _operand, _rightExpression, true);
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		if (_wrapParentheses) {
			consumer.accept("(");
		}

		_leftExpression.toSQL(consumer, astNodeListener);

		consumer.accept(" ");
		consumer.accept(_operand.toString());
		consumer.accept(" ");

		_rightExpression.toSQL(consumer, astNodeListener);

		if (_wrapParentheses) {
			consumer.accept(")");
		}
	}

	private PredicateImpl(
		Expression<?> leftExpression, Operand operand,
		Expression<?> rightExpression, boolean wrapParentheses) {

		_leftExpression = Objects.requireNonNull(leftExpression);
		_operand = Objects.requireNonNull(operand);
		_rightExpression = Objects.requireNonNull(rightExpression);
		_wrapParentheses = wrapParentheses;
	}

	private final Expression<?> _leftExpression;
	private final Operand _operand;
	private final Expression<?> _rightExpression;
	private final boolean _wrapParentheses;

}