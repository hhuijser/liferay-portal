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

package com.liferay.oauth2.provider.web.internal.util;

import com.liferay.oauth2.provider.scope.internal.spi.scope.matcher.ChunkScopeMatcherFactory;
import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcherFactory;
import com.liferay.oauth2.provider.web.internal.tree.BaseTree;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marta Medio
 */
public class ScopeTreeUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMultipleLevelsScopeTree() {
		List<String> scopeAliasesList = Arrays.asList(
			"everything.read", "everything.write", "everything",
			"everything.read.user", "everything.read.user.documents");

		BaseTree.Node<String> node = ScopeTreeUtil.getScopeAliasTreeNode(
			scopeAliasesList, _scopeMatcherFactory);

		Assert.assertEquals(StringPool.BLANK, node.getValue());

		BaseTree<String> tree = _getTree(node, 0);

		Assert.assertEquals("everything", tree.getValue());
		Assert.assertFalse(tree instanceof BaseTree.Leaf);

		BaseTree<String> firstChildTree = _getTree(
			(BaseTree.Node<String>)tree, 0);

		Assert.assertEquals("everything.read", firstChildTree.getValue());
		Assert.assertFalse(firstChildTree instanceof BaseTree.Leaf);

		BaseTree<String> firstGrandChildTree = _getTree(
			(BaseTree.Node<String>)firstChildTree, 0);

		Assert.assertEquals(
			"everything.read.user", firstGrandChildTree.getValue());
		Assert.assertFalse(firstGrandChildTree instanceof BaseTree.Leaf);

		BaseTree<String> greatGrandChildTree = _getTree(
			(BaseTree.Node<String>)firstGrandChildTree, 0);

		Assert.assertEquals(
			"everything.read.user.documents", greatGrandChildTree.getValue());

		BaseTree<String> lastChildTree = _getLastTree(
			(BaseTree.Node<String>)tree);

		Assert.assertEquals("everything.write", lastChildTree.getValue());
		Assert.assertTrue(lastChildTree instanceof BaseTree.Leaf);
	}

	@Test
	public void testMultipleParentsScopeTree() {
		List<String> scopeAliasesList = Arrays.asList(
			"everything.read", "everything.write", "everything",
			"everything.read.user", "analytics.read", "analytics");

		BaseTree.Node<String> node = ScopeTreeUtil.getScopeAliasTreeNode(
			scopeAliasesList, _scopeMatcherFactory);

		Assert.assertEquals(StringPool.BLANK, node.getValue());

		BaseTree<String> tree = _getTree(node, 0);

		Assert.assertEquals("analytics", tree.getValue());
		Assert.assertFalse(tree instanceof BaseTree.Leaf);

		BaseTree<String> firstChildTree = _getTree(
			(BaseTree.Node<String>)tree, 0);

		Assert.assertEquals("analytics.read", firstChildTree.getValue());
		Assert.assertTrue(firstChildTree instanceof BaseTree.Leaf);

		BaseTree<String> lastTree = _getLastTree(node);

		Assert.assertEquals("everything", lastTree.getValue());
		Assert.assertFalse(lastTree instanceof BaseTree.Leaf);

		BaseTree<String> lastChildTree = _getTree(
			(BaseTree.Node<String>)lastTree, 0);

		Assert.assertEquals("everything.read", lastChildTree.getValue());
		Assert.assertFalse(lastChildTree instanceof BaseTree.Leaf);
	}

	@Test
	public void testOneLevelScopeTree() {
		List<String> scopeAliasesList = Arrays.asList(
			"everything.read", "everything.write", "everything");

		BaseTree.Node<String> node = ScopeTreeUtil.getScopeAliasTreeNode(
			scopeAliasesList, _scopeMatcherFactory);

		Assert.assertEquals(StringPool.BLANK, node.getValue());

		BaseTree<String> tree = _getTree(node, 0);

		Assert.assertEquals("everything", tree.getValue());
		Assert.assertFalse(tree instanceof BaseTree.Leaf);

		BaseTree<String> firstChildTree = _getTree(
			(BaseTree.Node<String>)tree, 0);

		Assert.assertEquals("everything.read", firstChildTree.getValue());
		Assert.assertTrue(firstChildTree instanceof BaseTree.Leaf);

		BaseTree<String> lastChildTree = _getLastTree(
			(BaseTree.Node<String>)tree);

		Assert.assertEquals("everything.write", lastChildTree.getValue());
		Assert.assertTrue(lastChildTree instanceof BaseTree.Leaf);
	}

	private BaseTree<String> _getLastTree(BaseTree.Node<String> node) {
		Collection<BaseTree<String>> trees = node.getTrees();

		return _getTree(node, trees.size() - 1);
	}

	private List<BaseTree<String>> _getSortedTrees(BaseTree.Node<String> node) {
		return ListUtil.sort(
			node.getTrees(),
			Comparator.comparing(
				BaseTree::getValue, String.CASE_INSENSITIVE_ORDER));
	}

	private BaseTree<String> _getTree(BaseTree.Node<String> node, int index) {
		List<BaseTree<String>> trees = _getSortedTrees(node);

		return trees.get(index);
	}

	private final ScopeMatcherFactory _scopeMatcherFactory =
		new ChunkScopeMatcherFactory();

}