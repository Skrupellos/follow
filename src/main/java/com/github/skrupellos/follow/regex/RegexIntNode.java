package com.github.skrupellos.follow.regex;

import java.util.List;


public abstract class RegexIntNode extends RegexNode {
	// Only children
	public RegexIntNode(List<RegexNode> children) {
		super(children);
	}
	
	
	// Parent & children
	public RegexIntNode(RegexIntNode parent, RegexNode... children) {
		super(parent, children);
	}
	
	
	public RegexIntNode(RegexIntNode parent, List<RegexNode> children) {
		super(parent, children);
	}
}
