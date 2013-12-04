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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

/**
 * @author Preston Crary
 */
public class VersionType implements CompositeUserType, Serializable {

	@Override
	public Object assemble(
			Serializable cached, SessionImplementor session, Object owner) {

		return cached;
	}

	@Override
	public Object deepCopy(Object obj) {
		return obj;
	}

	@Override
	public Serializable disassemble(Object value, SessionImplementor session) {
		return (Serializable)value;
	}

	@Override
	public boolean equals(Object x, Object y) {
		return Validator.equals(x, y);
	}

	@Override
	public String[] getPropertyNames() {
		return new String[] { "version" };
	}

	@Override
	public Type[] getPropertyTypes() {
		return new Type[] {StandardBasicTypes.STRING};
	}

	@Override
	public Object getPropertyValue(Object component, int property) {
		return component.toString();
	}

	@Override
	public int hashCode(Object x) {
		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(
			ResultSet rs, String[] names, SessionImplementor session,
			Object owner)
		throws SQLException {

		if (rs.wasNull()) {
			return Version.getInstance(StringPool.BLANK);
		}

		return Version.getInstance(rs.getString(names[0]));
	}

	@Override
	public void nullSafeSet(
			PreparedStatement ps, Object target, int index,
			SessionImplementor session)
		throws SQLException {

		if (target == null) {
			StandardBasicTypes.STRING.nullSafeSet(ps, target, index, session);
		}
		else {
			StandardBasicTypes.STRING.nullSafeSet(
				ps, target.toString(), index, session);
		}
	}

	@Override
	public Object replace(
			Object original, Object target, SessionImplementor session,
			Object owner) {

		return original;
	}

	@Override
	public Class<Version> returnedClass() {
		return Version.class;
	}

	@Override
	public void setPropertyValue(Object component, int property, Object value) {
	}

}