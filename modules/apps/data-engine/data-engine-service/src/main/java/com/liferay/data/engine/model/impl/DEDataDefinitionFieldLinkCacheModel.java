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

package com.liferay.data.engine.model.impl;

import com.liferay.data.engine.model.DEDataDefinitionFieldLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DEDataDefinitionFieldLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DEDataDefinitionFieldLinkCacheModel
	implements CacheModel<DEDataDefinitionFieldLink>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataDefinitionFieldLinkCacheModel)) {
			return false;
		}

		DEDataDefinitionFieldLinkCacheModel
			deDataDefinitionFieldLinkCacheModel =
				(DEDataDefinitionFieldLinkCacheModel)obj;

		if (deDataDefinitionFieldLinkId ==
				deDataDefinitionFieldLinkCacheModel.
					deDataDefinitionFieldLinkId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, deDataDefinitionFieldLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deDataDefinitionFieldLinkId=");
		sb.append(deDataDefinitionFieldLinkId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);
		sb.append(", fieldName=");
		sb.append(fieldName);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DEDataDefinitionFieldLink toEntityModel() {
		DEDataDefinitionFieldLinkImpl deDataDefinitionFieldLinkImpl =
			new DEDataDefinitionFieldLinkImpl();

		if (uuid == null) {
			deDataDefinitionFieldLinkImpl.setUuid("");
		}
		else {
			deDataDefinitionFieldLinkImpl.setUuid(uuid);
		}

		deDataDefinitionFieldLinkImpl.setDeDataDefinitionFieldLinkId(
			deDataDefinitionFieldLinkId);
		deDataDefinitionFieldLinkImpl.setGroupId(groupId);
		deDataDefinitionFieldLinkImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			deDataDefinitionFieldLinkImpl.setCreateDate(null);
		}
		else {
			deDataDefinitionFieldLinkImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			deDataDefinitionFieldLinkImpl.setModifiedDate(null);
		}
		else {
			deDataDefinitionFieldLinkImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		deDataDefinitionFieldLinkImpl.setClassNameId(classNameId);
		deDataDefinitionFieldLinkImpl.setClassPK(classPK);
		deDataDefinitionFieldLinkImpl.setDdmStructureId(ddmStructureId);

		if (fieldName == null) {
			deDataDefinitionFieldLinkImpl.setFieldName("");
		}
		else {
			deDataDefinitionFieldLinkImpl.setFieldName(fieldName);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			deDataDefinitionFieldLinkImpl.setLastPublishDate(null);
		}
		else {
			deDataDefinitionFieldLinkImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		deDataDefinitionFieldLinkImpl.resetOriginalValues();

		return deDataDefinitionFieldLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		deDataDefinitionFieldLinkId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		ddmStructureId = objectInput.readLong();
		fieldName = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(deDataDefinitionFieldLinkId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(ddmStructureId);

		if (fieldName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fieldName);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long deDataDefinitionFieldLinkId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long ddmStructureId;
	public String fieldName;
	public long lastPublishDate;

}