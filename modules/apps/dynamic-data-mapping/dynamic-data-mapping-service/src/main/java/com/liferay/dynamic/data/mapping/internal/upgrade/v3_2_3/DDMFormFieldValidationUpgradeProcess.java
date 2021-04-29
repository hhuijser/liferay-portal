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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_2_3;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormFieldValidationUpgradeProcess extends UpgradeProcess {

	public DDMFormFieldValidationUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select structureId, definition from DDMStructure where " +
					"classNameId = ? ");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement ps3 = connection.prepareStatement(
				"select structureVersionId, definition from " +
					"DDMStructureVersion where structureId = ?");
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(
					"com.liferay.dynamic.data.mapping.model.DDMFormInstance"));

			try (ResultSet resultSet = ps1.executeQuery()) {
				while (resultSet.next()) {
					long structureId = resultSet.getLong("structureId");

					String definition = resultSet.getString("definition");

					JSONObject jsonObject1 = _jsonFactory.createJSONObject(
						definition);

					if (_upgradeValidation(
							jsonObject1.getJSONArray("fields"))) {

						ps2.setString(1, jsonObject1.toJSONString());

						ps2.setLong(2, structureId);

						ps2.addBatch();
					}

					ps3.setLong(1, structureId);

					try (ResultSet resultSet2 = ps3.executeQuery()) {
						while (resultSet2.next()) {
							definition = resultSet2.getString("definition");

							JSONObject jsonObject2 =
								_jsonFactory.createJSONObject(definition);

							if (_upgradeValidation(
									jsonObject2.getJSONArray("fields"))) {

								ps4.setString(1, jsonObject2.toJSONString());

								long structureVersionId = resultSet2.getLong(
									"structureVersionId");

								ps4.setLong(2, structureVersionId);

								ps4.addBatch();
							}
						}
					}
				}
			}

			ps2.executeBatch();

			ps4.executeBatch();
		}
	}

	private String _replaceFieldName(
		String expression, String fieldName, String regex) {

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(expression);

		matcher.find();

		String oldFieldName = matcher.group(1);

		return StringUtil.replace(expression, oldFieldName, fieldName);
	}

	private String _upgradeExpression(String expression, String fieldName) {
		if (expression.matches("NOT\\(contains\\((.+), \"(.*)\"\\)\\)")) {
			expression = _replaceFieldName(
				expression, fieldName, "NOT\\(contains\\((.+), \"(.*)\"\\)\\)");
		}
		else if (expression.matches("contains\\((.+), \"(.*)\"\\)")) {
			expression = _replaceFieldName(
				expression, fieldName, "contains\\((.+), \"(.*)\"\\)");
		}
		else if (expression.matches("isURL\\((.+)\\)")) {
			expression = _replaceFieldName(
				expression, fieldName, "isURL\\((.+)\\)");
		}
		else if (expression.matches("isEmailAddress\\((.+)\\)")) {
			expression = _replaceFieldName(
				expression, fieldName, "isEmailAddress\\((.+)\\)");
		}
		else if (expression.matches("match\\((.+), \"(.*)\"\\)")) {
			expression = _replaceFieldName(
				expression, fieldName, "match\\((.+), \"(.*)\"\\)");
		}
		else if (expression.matches("(.+)<(\\d+\\.?\\d*)?")) {
			expression = _replaceFieldName(
				expression, fieldName, "(.+)<(\\d+\\.?\\d*)?");
		}
		else if (expression.matches("(.+)<=(\\d+\\.?\\d*)?")) {
			expression = _replaceFieldName(
				expression, fieldName, "(.+)<=(\\d+\\.?\\d*)?");
		}
		else if (expression.matches("(.+)==(\\d+\\.?\\d*)?")) {
			expression = _replaceFieldName(
				expression, fieldName, "(.+)==(\\d+\\.?\\d*)?");
		}
		else if (expression.matches("(.+)>(\\d+\\.?\\d*)?")) {
			expression = _replaceFieldName(
				expression, fieldName, "(.+)>(\\d+\\.?\\d*)?");
		}
		else if (expression.matches("(.+)>=(\\d+\\.?\\d*)?")) {
			expression = _replaceFieldName(
				expression, fieldName, "(.+)>=(\\d+\\.?\\d*)?");
		}

		return expression;
	}

	private boolean _upgradeValidation(JSONArray fieldsJSONArray) {
		boolean upgraded = false;

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject jsonObject = fieldsJSONArray.getJSONObject(i);

			JSONObject validationJSONObject = jsonObject.getJSONObject(
				"validation");

			if (validationJSONObject != null) {
				JSONObject expressionJSONObject =
					validationJSONObject.getJSONObject("expression");

				String value = expressionJSONObject.getString("value");

				String upgradedValue = _upgradeExpression(
					value, jsonObject.getString("name"));

				expressionJSONObject.put("value", upgradedValue);

				upgraded = upgraded || !StringUtil.equals(value, upgradedValue);
			}

			JSONArray nestedFieldsJSONArray = jsonObject.getJSONArray(
				"nestedFields");

			if (nestedFieldsJSONArray != null) {
				upgraded =
					_upgradeValidation(nestedFieldsJSONArray) || upgraded;
			}
		}

		return upgraded;
	}

	private final JSONFactory _jsonFactory;

}