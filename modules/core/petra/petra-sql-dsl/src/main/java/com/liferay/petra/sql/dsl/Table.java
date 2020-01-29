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

package com.liferay.petra.sql.dsl;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.ast.impl.BaseASTNode;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Preston Crary
 */
public abstract class Table<T extends Table<T>> extends BaseASTNode {

	public Table(String tableName, Supplier<T> tableSupplier) {
		_tableName = tableName;
		_tableSupplier = Objects.requireNonNull(tableSupplier);
	}

	public T as(String alias) {
		T table = _tableSupplier.get();

		table.setAlias(alias);

		return table;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Table<?>)) {
			return false;
		}

		Table<?> table = (Table<?>)object;

		return Objects.equals(_tableName, table._tableName);
	}

	public String getAlias() {
		return _alias;
	}

	public Column<T, ?> getColumn(String columnName) {
		return _columnMap.get(columnName);
	}

	@SuppressWarnings("unchecked")
	public <C> Column<T, C> getColumn(String columnName, Class<C> clazz) {
		Column<T, ?> column = _columnMap.get(columnName);

		if ((column == null) ||
			!clazz.isAssignableFrom(column.getColumnType())) {

			return null;
		}

		return (Column<T, C>)column;
	}

	public Collection<Column<T, ?>> getColumns() {
		return Collections.unmodifiableCollection(_columnMap.values());
	}

	public String getName() {
		if (_alias == null) {
			return _tableName;
		}

		return _alias;
	}

	public String getTableName() {
		return _tableName;
	}

	@Override
	public int hashCode() {
		return _tableName.hashCode();
	}

	protected <C> Column<T, C> aliasColumn(
		Column<T, C> column, String columnAlias) {

		T table = _tableSupplier.get();

		table.setAlias(_alias);

		column = new Column<>(
			table, column.getColumnName(), column.getColumnType(),
			column.getSQLType());

		table.putColumn(columnAlias, column);

		return column;
	}

	protected <C> Column<T, C> createColumn(
		String columnName, Class<C> columnType, int sqlType) {

		@SuppressWarnings("unchecked")
		Column<T, C> column = new Column<>(
			(T)this, columnName, columnType, sqlType);

		_columnMap.put(columnName, column);

		return column;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_tableName);

		if (_alias != null) {
			consumer.accept(" ");
			consumer.accept(_alias);
		}
	}

	protected <C> void putColumn(String columnName, Column<T, C> column) {
		_columnMap.put(columnName, column);
	}

	protected void setAlias(String alias) {
		_alias = alias;
	}

	private String _alias;
	private final Map<String, Column<T, ?>> _columnMap = new LinkedHashMap<>();
	private final String _tableName;
	private final Supplier<T> _tableSupplier;

}