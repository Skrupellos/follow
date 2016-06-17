package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;

public class RegexUnion extends RegexIntNode {
	
	public RegexUnion() {
		this(null);
	}
	
	public RegexUnion(TreeIntNode parent) {
		super(parent);
	}

	@Override
	public String toString() {
		return "+";
	}
}
