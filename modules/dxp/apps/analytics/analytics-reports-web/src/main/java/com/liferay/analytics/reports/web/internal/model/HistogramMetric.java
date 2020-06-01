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

package com.liferay.analytics.reports.web.internal.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Objects;

/**
 * @author Cistina González
 */
public class HistogramMetric {

	public HistogramMetric() {
	}

	public HistogramMetric(Date key, double value) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		_key = key;
		_value = value;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof HistogramMetric)) {
			return false;
		}

		HistogramMetric histogramMetric = (HistogramMetric)object;

		if (Objects.equals(_key, histogramMetric._key) &&
			Objects.equals(_value, histogramMetric._value)) {

			return true;
		}

		return false;
	}

	public Date getKey() {
		return _key;
	}

	public Double getValue() {
		return _value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_key, _value);
	}

	public void setKey(Date key) {
		_key = key;
	}

	public void setValue(double value) {
		_value = value;
	}

	public JSONObject toJSONObject() {
		LocalDateTime localDateTime = _toLocalDateTime(_key);

		return JSONUtil.put(
			"key", localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
		).put(
			"value", _value
		);
	}

	private LocalDateTime _toLocalDateTime(Date date) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		return zonedDateTime.toLocalDateTime();
	}

	@JsonFormat(
		pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
		shape = JsonFormat.Shape.STRING, timezone = "UTC"
	)
	private Date _key;

	private double _value;

}