package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;

public class RegexEmptySet extends RegexExtNode {
	
	public RegexEmptySet() {
		this(null);
	}
	
	public RegexEmptySet(TreeIntNode parent) {
		super(parent);
	}

	@Override
	public String toString() {
		return "Ø";
	}
}
