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

package com.liferay.source.formatter.regex;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class Matcher {

	public Matcher(java.util.regex.Matcher matcher) {
		_matcher = matcher;
	}

	public Matcher appendReplacement(StringBuffer sb, String replacement) {
		_matcher.appendReplacement(sb, replacement);

		return this;
	}

	public StringBuffer appendTail(StringBuffer sb) {
		return _matcher.appendTail(sb);
	}

	public int end() {
		return _matcher.end();
	}

	public int end(int i) {
		return _matcher.end(i);
	}

	public int end(String name) {
		return _matcher.end(name);
	}

	public boolean find() {
		return _matcher.find();
	}

	public boolean find(int start) {
		return _matcher.find(start);
	}

	public String group() {
		return _matcher.group();
	}

	public String group(int i) {
		return _matcher.group(i);
	}

	public String group(String name) {
		return _matcher.group(name);
	}

	public int groupCount() {
		return _matcher.groupCount();
	}

	public boolean hasAnchoringBounds() {
		return _matcher.hasAnchoringBounds();
	}

	public boolean hitEnd() {
		return _matcher.hitEnd();
	}

	public boolean lookingAt() {
		return _matcher.lookingAt();
	}

	public boolean matches() {
		return _matcher.matches();
	}

	public Pattern pattern() {
		return _matcher.pattern();
	}

	public Matcher region(int start, int end) {
		_matcher.region(start, end);

		return this;
	}

	public int regionEnd() {
		return _matcher.regionEnd();
	}

	public int regionStart() {
		return _matcher.regionStart();
	}

	public String replaceAll(String replacement) {
		return _matcher.replaceAll(replacement);
	}

	public String replaceFirst(String replacement) {
		return _matcher.replaceFirst(replacement);
	}

	public boolean requireEnd() {
		return _matcher.requireEnd();
	}

	public Matcher reset() {
		_matcher.reset();

		return this;
	}

	public Matcher reset(CharSequence input) {
		_matcher.reset(input);

		return this;
	}

	public int start() {
		return _matcher.start();
	}

	public int start(int i) {
		return _matcher.start(i);
	}

	public int start(String name) {
		return _matcher.start(name);
	}

	public MatchResult toMatchResult() {
		return _matcher.toMatchResult();
	}

	public Matcher useAnchoringBounds(boolean b) {
		_matcher.useAnchoringBounds(b);

		return this;
	}

	public Matcher usePattern(Pattern newPattern) {
		_matcher.usePattern(newPattern);

		return this;
	}

	public Matcher useTransparentBounds(boolean b) {
		_matcher.useTransparentBounds(b);

		return this;
	}

	private final java.util.regex.Matcher _matcher;

}