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

package com.liferay.source.formatter.checks;

/**
 * @author Alan Huang
 */
public class PoshiEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = content.replaceAll(
			"(?<!\n)(\n\t(?!else|if)\\w+ \\{)", "\n$1");

		content = content.replaceAll("(?<!\n)(\n\t*//.*\n)", "\n$1");
		content = content.replaceAll("((\n|^)\t*//.*\n)(?!(\n|\t*//))", "$1\n");
		content = content.replaceAll(
			"((\n|^)\t*//.*)\n{2,}(\t*//.*)", "$1\n$3");

		content = content.replaceFirst("(definition \\{\n)(?!\n)", "$1\n");
		content = content.replaceFirst("(?<!\n)(\n\\})$", "\n$1");
		content = content.replaceFirst(
			"(\n\t*[^@\\s].*\n)((\t@.+\n)*\t(function|macro|test) )", "$1\n$2");

		return content;
	}

}