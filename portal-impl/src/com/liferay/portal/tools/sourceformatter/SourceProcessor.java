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

package com.liferay.portal.tools.sourceformatter;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public interface SourceProcessor {

	public void format(
			boolean useProperties, boolean printErrors, boolean autoFix,
			String mainReleaseVersion)
		throws Exception;

	public String format(
			String fileName, boolean useProperties, boolean printErrors,
			boolean autoFix, String mainReleaseVersion)
		throws Exception;

	public List<String> getErrorMessages();

	/**
	 * Returns the first occurrence of a source file where the formatted,
	 * auto-corrected code mismatched the original. <p>
	 *
	 * Why only the first mismatch, instead of them all? <p>
	 *
	 * <ol>
	 * <li> On IDEs we want to render the diff graphically, therefore
	 * SourceFormatterTest converts the mismatch to a JUnit ComparisonFailure.
	 * That takes a single diff only, so the others would be discarded anyway.
	 *
	 * <li> On the command line there is no mismatch reporting because
	 * autocorrection is already applied, so we don't need any.
	 *
	 * <li> The mismatch contains both the full original and formatted content.
	 * Holding the full list of mismatches would risk an OutOfMemoryError when
	 * there's a lot of them.
	 * </ol>
	 */
	public MismatchException getFirstMismatch();

}