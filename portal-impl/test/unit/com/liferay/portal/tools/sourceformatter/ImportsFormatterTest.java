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

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImportsFormatterTest {

	@Test
	public void testNoImports__nothingHappens()
		throws IOException {

		String original = "";
		String expected = "";

		assertFormat(original, expected);
	}

	@Test
	public void testSingleImport__newlineIsAppended()
		throws IOException {

		String original = "import org.junit.Test;";
		String expected = original + '\n';

		assertFormat(original, expected);
	}

	@Test
	public void testMultipleImports__areSorted()
		throws IOException {

		String original =
			"import org.junit.Test;" + "\n"
				+ "import org.junit.Assert;";
		String expected =
			"import org.junit.Assert;" + "\n"
				+ "import org.junit.Test;"
				+ '\n';

		assertFormat(original, expected);
	}

	@Test
	public void testDifferentPackages__areSeparated()
		throws IOException {

		String original =
			"import org.mockito.Mockito;" + "\n"
				+ "import org.junit.Assert;";
		String expected =
			"import org.junit.Assert;" + "\n"
				+ '\n'
				+ "import org.mockito.Mockito;"
				+ '\n';

		assertFormat(original, expected);
	}

	@Test
	public void testDuplicates__areRemoved()
		throws IOException {

		String original =
			"import org.junit.Test;" + "\n"
				+ "import org.junit.Assert;" + "\n"
				+ "import org.junit.Assert;";
		String expected =
			"import org.junit.Assert;" + "\n"
				+ "import org.junit.Test;"
				+ '\n';

		assertFormat(original, expected);
	}

	@Test
	public void testClassAndInnerClass__innerClassMustBeSecond()
		throws IOException {

		String original =
			"import javax.servlet.FilterRegistration.Dynamic;" + "\n"
				+ "import javax.servlet.FilterRegistration;";
		String expected =
			"import javax.servlet.FilterRegistration;" + "\n"
				+ "import javax.servlet.FilterRegistration.Dynamic;"
				+ '\n';

		assertFormat(original, expected);
	}

	@Ignore("See the bug manifest: activate this test.")
	@Test
	public void testMultipleStaticImports__shouldBePreserved()
		throws IOException {

		String original =
			"import static org.junit.Assert.*;" + "\n" +
				'\n' +
				"import static org.mockito.Mockito.*;";
		String expected = original + '\n';

		assertFormat(original, expected);
	}

	private void assertFormat(String original, String expected)
		throws IOException {

		assertFormat(original, 0, expected);
	}

	private void assertFormat(
		String original, int classStartPos, String expected)
	throws IOException {

		String formatted =
			ImportsFormatter.formatImports(original, classStartPos);
		assertEquals(expected, formatted);
	}

}