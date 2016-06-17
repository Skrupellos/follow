package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;

public class RegexEpsilon extends RegexExtNode {
	
	public RegexEpsilon() {
		this(null);
	}
	
	public RegexEpsilon(TreeIntNode parent) {
		super(parent);
	}
	
	@Override
	public String toString() {
		return "ε";
	}
}
