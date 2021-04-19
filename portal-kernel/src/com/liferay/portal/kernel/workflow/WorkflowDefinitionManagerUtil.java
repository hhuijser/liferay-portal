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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 * @author Raymond Augé
 */
public class WorkflowDefinitionManagerUtil {

	public static WorkflowDefinition deployWorkflowDefinition(
			long companyId, long userId, String title, String name,
			byte[] bytes)
		throws WorkflowException {

		return _workflowDefinitionManager.deployWorkflowDefinition(
			companyId, userId, title, name, bytes);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getActiveWorkflowDefinitionsCount(long)}
	 */
	@Deprecated
	public static int getActiveWorkflowDefinitionCount(long companyId)
		throws WorkflowException {

		return _workflowDefinitionManager.getActiveWorkflowDefinitionCount(
			companyId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static int getActiveWorkflowDefinitionCount(
			long companyId, String name)
		throws WorkflowException {

		return _workflowDefinitionManager.getActiveWorkflowDefinitionCount(
			companyId, name);
	}

	public static List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		return _workflowDefinitionManager.getActiveWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		return _workflowDefinitionManager.getActiveWorkflowDefinitions(
			companyId, name, start, end, orderByComparator);
	}

	public static int getActiveWorkflowDefinitionsCount(long companyId)
		throws WorkflowException {

		return _workflowDefinitionManager.getActiveWorkflowDefinitionsCount(
			companyId);
	}

	public static WorkflowDefinition getLatestWorkflowDefinition(
			long companyId, String name)
		throws WorkflowException {

		return _workflowDefinitionManager.getLatestWorkflowDefinition(
			companyId, name);
	}

	public static List<WorkflowDefinition> getLatestWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		return _workflowDefinitionManager.getLatestWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static int getLatestWorkflowDefinitionsCount(long companyId)
		throws WorkflowException {

		return _workflowDefinitionManager.getLatestWorkflowDefinitionsCount(
			companyId);
	}

	public static WorkflowDefinition getWorkflowDefinition(
			long companyId, String name, int version)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinition(
			companyId, name, version);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static int getWorkflowDefinitionCount(long companyId)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitionCount(companyId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getWorkflowDefinitionsCount(long, String)}
	 */
	@Deprecated
	public static int getWorkflowDefinitionCount(long companyId, String name)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitionCount(
			companyId, name);
	}

	public static WorkflowDefinitionManager getWorkflowDefinitionManager() {
		return _workflowDefinitionManager;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitions(
			companyId, name, start, end, orderByComparator);
	}

	public static int getWorkflowDefinitionsCount(long companyId, String name)
		throws WorkflowException {

		return _workflowDefinitionManager.getWorkflowDefinitionsCount(
			companyId, name);
	}

	/**
	 * Saves a workflow definition without activating it or validating its data.
	 * To save the definition, validate its data, and activate it, use {@link
	 * #deployWorkflowDefinition(long, long, String, String, byte[])} instead.
	 *
	 * @param  companyId the company ID of the workflow definition
	 * @param  userId the ID of the user saving the workflow definition
	 * @param  title the workflow definition's title
	 * @param  name the workflow definition's name
	 * @param  bytes the data saved as the workflow definition's content
	 * @return the workflow definition
	 * @throws WorkflowException if there was an issue saving the workflow
	 *         definition
	 */
	public static WorkflowDefinition saveWorkflowDefinition(
			long companyId, long userId, String title, String name,
			byte[] bytes)
		throws WorkflowException {

		return _workflowDefinitionManager.saveWorkflowDefinition(
			companyId, userId, title, name, bytes);
	}

	public static void undeployWorkflowDefinition(
			long companyId, long userId, String name, int version)
		throws WorkflowException {

		_workflowDefinitionManager.undeployWorkflowDefinition(
			companyId, userId, name, version);
	}

	public static WorkflowDefinition updateActive(
			long companyId, long userId, String name, int version,
			boolean active)
		throws WorkflowException {

		return _workflowDefinitionManager.updateActive(
			companyId, userId, name, version, active);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement}
	 */
	@Deprecated
	public static WorkflowDefinition updateTitle(
			long companyId, long userId, String name, int version, String title)
		throws WorkflowException {

		return _workflowDefinitionManager.updateTitle(
			companyId, userId, name, version, title);
	}

	public static void validateWorkflowDefinition(byte[] bytes)
		throws WorkflowException {

		_workflowDefinitionManager.validateWorkflowDefinition(bytes);
	}

	public void setWorkflowDefinitionManager(
		WorkflowDefinitionManager workflowDefinitionManager) {

		_workflowDefinitionManager = workflowDefinitionManager;
	}

	private static WorkflowDefinitionManager _workflowDefinitionManager;

}