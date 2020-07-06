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

package com.liferay.source.formatter.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.net.UnknownHostException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JIRAUtil {

	private static final int _STATUS_NO_INTERNET_CONNECTION = 0;

	private static final int _STATUS_NONEXISTING_TICKET = 1;

	private static final int _STATUS_PRIVATE_TICKET = 2;

	private static final int _STATUS_PUBLIC_TICKET = 3;

	public static void validateJIRAProjectNames(
			List<String> commitMessages, List<String> projectNames)
		throws Exception {

		if (projectNames.isEmpty()) {
			return;
		}

		outerLoop:
		for (String commitMessage : commitMessages) {
			if (commitMessage.startsWith("Revert ")) {
				continue;
			}

			for (String projectName : projectNames) {
				if (commitMessage.startsWith(projectName)) {
					continue outerLoop;
				}
			}

			throw new Exception(
				StringBundler.concat(
					"At least one commit message is missing a reference to a ",
					"required JIRA project: ",
					StringUtil.merge(projectNames, StringPool.COMMA_AND_SPACE),
					". Please verify that the JIRA project keys are specified",
					"in ci.properties in the liferay-portal repository."));
		}
	}

	private static String _getJIRATicketId(String commitMessage) {
		Matcher matcher = _jiraTicketIdPattern.matcher(commitMessage);

		if (matcher.find()) {
			return matcher.group();
		}

		return null;
	}

	private static int _getJIRATicketStatus(String jiraTicketId) {
		try {
			URL url = new URL(
				"https://issues.liferay.com/rest/api/2/issue/" + jiraTicketId);

			url.openStream();
		}
		catch (IOException ioException) {
			if (ioException instanceof FileNotFoundException) {
				return _STATUS_NONEXISTING_TICKET;
			}

			if (ioException instanceof UnknownHostException) {
				return _STATUS_NO_INTERNET_CONNECTION;
			}

			return _STATUS_PRIVATE_TICKET;
		}

		return _STATUS_PUBLIC_TICKET;
	}

	public static void validateJIRATicketIds(
			List<String> commitMessages, int maxNumberOfTickets)
		throws Exception {

		Set<String> validatedTicketIds = new HashSet<>();

		for (String commitMessage : commitMessages) {
			if (validatedTicketIds.size() == maxNumberOfTickets) {
				return;
			}

			String jiraTicketId = _getJIRATicketId(commitMessage);

			if (!validatedTicketIds.add(jiraTicketId)) {
				continue;
			}

			int jiraTicketStatus = _getJIRATicketStatus(jiraTicketId);

			if (jiraTicketStatus == _STATUS_NO_INTERNET_CONNECTION) {
				return;
			}

			if (jiraTicketStatus == _STATUS_NONEXISTING_TICKET) {
				throw new Exception(
					"Commit message is pointing to non-existing JIRA " +
						"issue: " + jiraTicketId);
			}
		}
	}

	private static final Pattern _jiraTicketIdPattern = Pattern.compile(
		"^[A-Z0-9]+-[0-9]+");

}