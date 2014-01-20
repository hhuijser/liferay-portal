package ${packagePath}.model;

import aQute.bnd.annotation.ProviderType;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.model.AttachedModel;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ResourcedModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.model.TypedModel;
import com.liferay.portal.model.StagedAuditedModel;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.WorkflowedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.trash.model.TrashEntry;

import java.io.Serializable;

import java.sql.Blob;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the ${entity.name} service. Represents a row in the &quot;${entity.table}&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link ${packagePath}.model.impl.${entity.name}ModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ${packagePath}.model.impl.${entity.name}Impl}.
 * </p>
 *
 * @author ${author}
 * @see ${entity.name}
 * @see ${packagePath}.model.impl.${entity.name}Impl
 * @see ${packagePath}.model.impl.${entity.name}ModelImpl
 * @generated
 */

<#if pluginName == "">
	@ProviderType
</#if>

public interface ${entity.name}Model extends
	<#assign overrideColumnNames = []>

	<#if entity.isAttachedModel()>
		AttachedModel,

		<#assign overrideColumnNames = overrideColumnNames + ["className", "classNameId", "classPK"]>
	</#if>

	<#if entity.isAuditedModel() && !entity.isGroupedModel() && !entity.isStagedAuditedModel()>
		AuditedModel,

		<#assign overrideColumnNames = overrideColumnNames + ["companyId", "createDate", "modifiedDate", "userId", "userName", "userUuid"]>
	</#if>

	BaseModel<${entity.name}>

	<#if entity.isContainerModel()>
		, ContainerModel
	</#if>

	<#if entity.isGroupedModel() && !entity.isStagedGroupedModel()>
		, GroupedModel

		<#assign overrideColumnNames = overrideColumnNames + ["companyId", "createDate", "groupId", "modifiedDate", "userId", "userName", "userUuid"]>
	</#if>

	<#if entity.isMvccEnabled()>
		, MVCCModel

		<#assign overrideColumnNames = overrideColumnNames + ["mvccVersion"]>
	</#if>

	<#if entity.isResourcedModel()>
		, ResourcedModel

		<#assign overrideColumnNames = overrideColumnNames + ["resourcePrimKey"]>
	</#if>

	<#if entity.isStagedGroupedModel()>
		, StagedGroupedModel

		<#assign overrideColumnNames = overrideColumnNames + ["companyId", "createDate", "groupId", "modifiedDate", "stagedModelType", "userId", "userName", "userUuid", "uuid"]>
	</#if>

	<#if entity.isStagedAuditedModel() && !entity.isStagedGroupedModel()>
		, StagedAuditedModel

		<#assign overrideColumnNames = overrideColumnNames + ["companyId", "createDate", "modifiedDate", "stagedModelType", "userId", "userName", "userUuid", "uuid"]>
	</#if>

	<#if !entity.isStagedAuditedModel() && !entity.isStagedGroupedModel() && entity.isStagedModel()>
		, StagedModel

		<#assign overrideColumnNames = overrideColumnNames + ["companyId", "createDate", "modifiedDate", "stagedModelType", "uuid"]>
	</#if>

	<#if entity.isTrashEnabled()>
		, TrashedModel

		<#assign overrideColumnNames = overrideColumnNames + ["status"]>
	</#if>

	<#if entity.isTypedModel() && !entity.isAttachedModel()>
		, TypedModel

		<#assign overrideColumnNames = overrideColumnNames + ["className", "classNameId"]>
	</#if>

	<#if entity.isWorkflowEnabled()>
		, WorkflowedModel

		<#assign overrideColumnNames = overrideColumnNames + ["status", "statusByUserId", "statusByUserName", "statusByUserUuid", "statusDate"]>
	</#if>

	{

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a ${entity.humanName} model instance should use the {@link ${entity.name}} interface instead.
	 */

	/**
	 * Returns the primary key of this ${entity.humanName}.
	 *
	 * @return the primary key of this ${entity.humanName}
	 */
	public ${entity.PKClassName} getPrimaryKey();

	/**
	 * Sets the primary key of this ${entity.humanName}.
	 *
	 * @param primaryKey the primary key of this ${entity.humanName}
	 */
	public void setPrimaryKey(${entity.PKClassName} primaryKey);

	<#list entity.regularColList as column>
		<#if column.name == "classNameId">
			/**
			 * Returns the fully qualified class name of this ${entity.humanName}.
			 *
			 * @return the fully qualified class name of this ${entity.humanName}
			 */

			<#if overrideColumnNames?seq_index_of(column.name) != -1>
				@Override
			</#if>

			public String getClassName();

			public void setClassName(String className);
		</#if>

		<#assign autoEscape = true>

		<#assign modelName = packagePath + ".model." + entity.name>

		<#if modelHintsUtil.getHints(modelName, column.name)??>
			<#assign hints = modelHintsUtil.getHints(modelName, column.name)>

			<#if hints["auto-escape"]??>
				<#assign autoEscapeHintValue = hints["auto-escape"]>

				<#if autoEscapeHintValue == "false">
					<#assign autoEscape = false>
				</#if>
			</#if>
		</#if>

		/**
		 * Returns the ${column.humanName} of this ${entity.humanName}.
		 *
		 * @return the ${column.humanName} of this ${entity.humanName}
		 */

		<#if autoEscape && (column.type == "String") && (column.localized == false)>
			@AutoEscape
		</#if>

		<#if overrideColumnNames?seq_index_of(column.name) != -1>
			@Override
		</#if>

		public ${column.type} get${column.methodName}();

		<#if column.localized>
			/**
			 * Returns the localized ${column.humanName} of this ${entity.humanName} in the language. Uses the default language if no localization exists for the requested language.
			 *
			 * @param locale the locale of the language
			 * @return the localized ${column.humanName} of this ${entity.humanName}
			 */
			@AutoEscape
			public String get${column.methodName}(Locale locale);

			/**
			 * Returns the localized ${column.humanName} of this ${entity.humanName} in the language, optionally using the default language if no localization exists for the requested language.
			 *
			 * @param locale the local of the language
			 * @param useDefault whether to use the default language if no localization exists for the requested language
			 * @return the localized ${column.humanName} of this ${entity.humanName}. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
			 */
			@AutoEscape
			public String get${column.methodName}(Locale locale, boolean useDefault);

			/**
			 * Returns the localized ${column.humanName} of this ${entity.humanName} in the language. Uses the default language if no localization exists for the requested language.
			 *
			 * @param languageId the ID of the language
			 * @return the localized ${column.humanName} of this ${entity.humanName}
			 */
			@AutoEscape
			public String get${column.methodName}(String languageId);

			/**
			 * Returns the localized ${column.humanName} of this ${entity.humanName} in the language, optionally using the default language if no localization exists for the requested language.
			 *
			 * @param languageId the ID of the language
			 * @param useDefault whether to use the default language if no localization exists for the requested language
			 * @return the localized ${column.humanName} of this ${entity.humanName}
			 */
			@AutoEscape
			public String get${column.methodName}(String languageId, boolean useDefault);

			@AutoEscape
			public String get${column.methodName}CurrentLanguageId();

			@AutoEscape
			public String get${column.methodName}CurrentValue();

			/**
			 * Returns a map of the locales and localized ${column.humanNames} of this ${entity.humanName}.
			 *
			 * @return the locales and localized ${column.humanNames} of this ${entity.humanName}
			 */
			public Map<Locale, String> get${column.methodName}Map();
		</#if>

		<#if column.type == "boolean">
			/**
			 * Returns <code>true</code> if this ${entity.humanName} is ${column.humanName}.
			 *
			 * @return <code>true</code> if this ${entity.humanName} is ${column.humanName}; <code>false</code> otherwise
			 */
			public boolean is${column.methodName}();
		</#if>

		/**
		<#if column.type == "boolean">
		 * Sets whether this ${entity.humanName} is ${column.humanName}.
		<#else>
		 * Sets the ${column.humanName} of this ${entity.humanName}.
		</#if>
		 *
		 * @param ${column.name} the ${column.humanName} of this ${entity.humanName}
		 */
		<#if overrideColumnNames?seq_index_of(column.name) != -1>
			@Override
		</#if>
		public void set${column.methodName}(${column.type} ${column.name});

		<#if column.localized>
			/**
			 * Sets the localized ${column.humanName} of this ${entity.humanName} in the language.
			 *
			 * @param ${column.name} the localized ${column.humanName} of this ${entity.humanName}
			 * @param locale the locale of the language
			 */
			public void set${column.methodName}(String ${column.name}, Locale locale);

			/**
			 * Sets the localized ${column.humanName} of this ${entity.humanName} in the language, and sets the default locale.
			 *
			 * @param ${column.name} the localized ${column.humanName} of this ${entity.humanName}
			 * @param locale the locale of the language
			 * @param defaultLocale the default locale
			 */
			public void set${column.methodName}(String ${column.name}, Locale locale, Locale defaultLocale);

			public void set${column.methodName}CurrentLanguageId(String languageId);

			/**
			 * Sets the localized ${column.humanNames} of this ${entity.humanName} from the map of locales and localized ${column.humanNames}.
			 *
			 * @param ${column.name}Map the locales and localized ${column.humanNames} of this ${entity.humanName}
			 */
			public void set${column.methodName}Map(Map<Locale, String> ${column.name}Map);

			/**
			 * Sets the localized ${column.humanNames} of this ${entity.humanName} from the map of locales and localized ${column.humanNames}, and sets the default locale.
			 *
			 * @param ${column.name}Map the locales and localized ${column.humanNames} of this ${entity.humanName}
			 * @param defaultLocale the default locale
			 */
			public void set${column.methodName}Map(Map<Locale, String> ${column.name}Map, Locale defaultLocale);
		</#if>

		<#if (column.name == "resourcePrimKey") && entity.isResourcedModel()>
			@Override
			public boolean isResourceMain();
		</#if>

		<#if column.userUuid>
			/**
			 * Returns the ${column.userUuidHumanName} of this ${entity.humanName}.
			 *
			 * @return the ${column.userUuidHumanName} of this ${entity.humanName}
			 * @throws SystemException if a system exception occurred
			 */

			<#if overrideColumnNames?seq_index_of(column.userUuidName) != -1>
				@Override
			</#if>

			public String get${column.methodUserUuidName}() throws SystemException;

			/**
			 * Sets the ${column.userUuidHumanName} of this ${entity.humanName}.
			 *
			 * @param ${column.userUuidName} the ${column.userUuidHumanName} of this ${entity.humanName}
			 */

			<#if overrideColumnNames?seq_index_of(column.userUuidName) != -1>
				@Override
			</#if>

			public void set${column.methodUserUuidName}(String ${column.userUuidName});
		</#if>
	</#list>

	<#if entity.isTrashEnabled()>
		<#if !entity.isWorkflowEnabled()>
			/**
			 * Returns the status of this ${entity.humanName}.
			 *
			 * @return the status of this ${entity.humanName}
			 */
			@Override
			public int getStatus();
		</#if>

		/**
		 * Returns the trash entry created when this ${entity.humanName} was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this ${entity.humanName}.
		 *
		 * @return the trash entry created when this ${entity.humanName} was moved to the Recycle Bin
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public TrashEntry getTrashEntry() throws PortalException, SystemException;

		/**
		 * Returns the class primary key of the trash entry for this ${entity.humanName}.
		 *
		 * @return the class primary key of the trash entry for this ${entity.humanName}
		 */
		@Override
		public long getTrashEntryClassPK();

		/**
		 * Returns the trash handler for this ${entity.humanName}.
		 *
		 * @return the trash handler for this ${entity.humanName}
		 */
		@Override
		public TrashHandler getTrashHandler();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is in the Recycle Bin.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is in the Recycle Bin; <code>false</code> otherwise
		 */
		@Override
		public boolean isInTrash();

		/**
		 * Returns <code>true</code> if the parent of this ${entity.humanName} is in the Recycle Bin.
		 *
		 * @return <code>true</code> if the parent of this ${entity.humanName} is in the Recycle Bin; <code>false</code> otherwise
		 * @throws SystemException if a system exception occurred
		 */
		@Override
		public boolean isInTrashContainer();

		@Override
		public boolean isInTrashExplicitly() throws SystemException;
	</#if>

	<#if entity.isWorkflowEnabled()>
		/**
		 * @deprecated As of 6.1.0, replaced by {@link #isApproved()}
		 */
		@Deprecated
		@Override
		public boolean getApproved();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is approved.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is approved; <code>false</code> otherwise
		 */
		@Override
		public boolean isApproved();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is denied.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is denied; <code>false</code> otherwise
		 */
		@Override
		public boolean isDenied();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is a draft.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is a draft; <code>false</code> otherwise
		 */
		@Override
		public boolean isDraft();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is expired.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is expired; <code>false</code> otherwise
		 */
		@Override
		public boolean isExpired();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is inactive.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is inactive; <code>false</code> otherwise
		 */
		@Override
		public boolean isInactive();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is incomplete.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is incomplete; <code>false</code> otherwise
		 */
		@Override
		public boolean isIncomplete();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is pending.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is pending; <code>false</code> otherwise
		 */
		@Override
		public boolean isPending();

		/**
		 * Returns <code>true</code> if this ${entity.humanName} is scheduled.
		 *
		 * @return <code>true</code> if this ${entity.humanName} is scheduled; <code>false</code> otherwise
		 */
		@Override
		public boolean isScheduled();
	</#if>

	<#if entity.isContainerModel()>
		<#if !entity.hasColumn("containerModelId")>
			/**
			 * Returns the container model ID of this ${entity.humanName}.
			 *
			 * @return the container model ID of this ${entity.humanName}
			 */
			@Override
			public long getContainerModelId();

			/**
			 * Sets the container model ID of this ${entity.humanName}.
			 *
			 * @param containerModelId the container model ID of this ${entity.humanName}
			 */
			@Override
			public void setContainerModelId(long containerModelId);
		</#if>

		/**
		 * Returns the container name of this ${entity.humanName}.
		 *
		 * @return the container name of this ${entity.humanName}
		 */
		@Override
		public String getContainerModelName();

		<#if !entity.hasColumn("parentContainerModelId")>
			/**
			 * Returns the parent container model ID of this ${entity.humanName}.
			 *
			 * @return the parent container model ID of this ${entity.humanName}
			 */
			@Override
			public long getParentContainerModelId();

			/**
			 * Sets the parent container model ID of this ${entity.humanName}.
			 *
			 * @param parentContainerModelId the parent container model ID of this ${entity.humanName}
			 */
			@Override
			public void setParentContainerModelId(long parentContainerModelId);
		</#if>
	</#if>

	<#--
	Copy methods from com.liferay.portal.model.BaseModel and java.lang.Object to
	correctly generate wrappers.
	-->

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	<#if entity.hasLocalizedColumn()>
		public String[] getAvailableLanguageIds();

		public String getDefaultLanguageId();

		public void prepareLocalizedFieldsForImport() throws LocaleException;

		public void prepareLocalizedFieldsForImport(Locale defaultImportLocale) throws LocaleException;
	</#if>

	@Override
	public Object clone();

	@Override
	public int compareTo(${entity.name} ${entity.varName});

	@Override
	public int hashCode();

	@Override
	public CacheModel<${entity.name}> toCacheModel();

	@Override
	public ${entity.name} toEscapedModel();

	@Override
	public ${entity.name} toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();

}