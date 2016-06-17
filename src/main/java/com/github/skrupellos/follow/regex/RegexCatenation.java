package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;

public class RegexCatenation extends RegexIntNode {
	
	public RegexCatenation() {
		this(null);
	}
	
	public RegexCatenation(TreeIntNode parent) {
		super(parent);
	}
	
	@Override
	public String toString() {
		return "meow";
	}
}
