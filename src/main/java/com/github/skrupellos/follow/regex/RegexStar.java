package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;

public class RegexStar extends RegexIntNode {
	
	public RegexStar() {
		this(null);
	}
	
	public RegexStar(TreeIntNode parent) {
		super(parent);
	}
	
	@Override
	public String toString() {
		return "\"*\"";
	}
}
