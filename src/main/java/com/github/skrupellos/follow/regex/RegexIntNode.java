package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;

public class RegexIntNode extends TreeIntNode implements RegexNode {

	public RegexIntNode() {
		this(null);
	}
	
	public RegexIntNode(TreeIntNode parent) {
		super(parent);
	}
}
