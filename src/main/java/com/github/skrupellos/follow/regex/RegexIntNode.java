package com.github.skrupellos.follow.regex;

import java.util.List;


public abstract class RegexIntNode extends RegexNode {
	public RegexIntNode() {
		super();
	}
	
	public RegexIntNode(RegexIntNode parent) {
		super(parent);
	}
	
	public RegexIntNode(List<RegexNode> children) {
		super(children);
	}
	
	public RegexIntNode(RegexIntNode parent, List<RegexNode> children) {
		super(parent, children);
	}
}
