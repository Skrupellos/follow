package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeExtNode;
import com.github.skrupellos.follow.tree.TreeIntNode;

public class RegexExtNode extends TreeExtNode implements RegexNode {

	public RegexExtNode() {
		this(null);
	}
	
	public RegexExtNode(TreeIntNode parent) {
		super(parent);
	}
}
