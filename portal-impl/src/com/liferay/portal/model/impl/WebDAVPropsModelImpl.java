/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.model.WebDAVPropsModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * The base model implementation for the WebDAVProps service. Represents a row in the &quot;WebDAVProps&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.portal.model.WebDAVPropsModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WebDAVPropsImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsImpl
 * @see com.liferay.portal.model.WebDAVProps
 * @see com.liferay.portal.model.WebDAVPropsModel
 * @generated
 */
public class WebDAVPropsModelImpl extends BaseModelImpl<WebDAVProps>
	implements WebDAVPropsModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a web d a v props model instance should use the {@link com.liferay.portal.model.WebDAVProps} interface instead.
	 */
	public static final String TABLE_NAME = "WebDAVProps";
	public static final Object[][] TABLE_COLUMNS = {
			{ "webDavPropsId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "props", Types.CLOB }
		};
	public static final String TABLE_SQL_CREATE = "create table WebDAVProps (webDavPropsId LONG not null primary key,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,props TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table WebDAVProps";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.WebDAVProps"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.WebDAVProps"),
			true);

	public Class<?> getModelClass() {
		return WebDAVProps.class;
	}

	public String getModelClassName() {
		return WebDAVProps.class.getName();
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.WebDAVProps"));

	public WebDAVPropsModelImpl() {
	}

	public long getPrimaryKey() {
		return _webDavPropsId;
	}

	public void setPrimaryKey(long primaryKey) {
		setWebDavPropsId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_webDavPropsId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public long getWebDavPropsId() {
		return _webDavPropsId;
	}

	public void setWebDavPropsId(long webDavPropsId) {
		_webDavPropsId = webDavPropsId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	public String getProps() {
		if (_props == null) {
			return StringPool.BLANK;
		}
		else {
			return _props;
		}
	}

	public void setProps(String props) {
		_props = props;
	}

	@Override
	public WebDAVProps toEscapedModel() {
		if (isEscapedModel()) {
			return (WebDAVProps)this;
		}
		else {
			if (_escapedModelProxy == null) {
				_escapedModelProxy = (WebDAVProps)Proxy.newProxyInstance(_classLoader,
						_escapedModelProxyInterfaces,
						new AutoEscapeBeanHandler(this));
			}

			return _escapedModelProxy;
		}
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					WebDAVProps.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		WebDAVPropsImpl webDAVPropsImpl = new WebDAVPropsImpl();

		webDAVPropsImpl.setWebDavPropsId(getWebDavPropsId());
		webDAVPropsImpl.setCompanyId(getCompanyId());
		webDAVPropsImpl.setCreateDate(getCreateDate());
		webDAVPropsImpl.setModifiedDate(getModifiedDate());
		webDAVPropsImpl.setClassNameId(getClassNameId());
		webDAVPropsImpl.setClassPK(getClassPK());
		webDAVPropsImpl.setProps(getProps());

		webDAVPropsImpl.resetOriginalValues();

		return webDAVPropsImpl;
	}

	public int compareTo(WebDAVProps webDAVProps) {
		long primaryKey = webDAVProps.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		WebDAVProps webDAVProps = null;

		try {
			webDAVProps = (WebDAVProps)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = webDAVProps.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		WebDAVPropsModelImpl webDAVPropsModelImpl = this;

		webDAVPropsModelImpl._originalClassNameId = webDAVPropsModelImpl._classNameId;

		webDAVPropsModelImpl._setOriginalClassNameId = false;

		webDAVPropsModelImpl._originalClassPK = webDAVPropsModelImpl._classPK;

		webDAVPropsModelImpl._setOriginalClassPK = false;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{webDavPropsId=");
		sb.append(getWebDavPropsId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", props=");
		sb.append(getProps());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.WebDAVProps");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>webDavPropsId</column-name><column-value><![CDATA[");
		sb.append(getWebDavPropsId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>props</column-name><column-value><![CDATA[");
		sb.append(getProps());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = WebDAVProps.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			WebDAVProps.class
		};
	private long _webDavPropsId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _props;
	private transient ExpandoBridge _expandoBridge;
	private WebDAVProps _escapedModelProxy;
}