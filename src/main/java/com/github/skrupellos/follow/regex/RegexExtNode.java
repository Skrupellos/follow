package com.github.skrupellos.follow.regex;

import java.util.List;


public abstract class RegexExtNode extends RegexNode {
	public RegexExtNode() {
		super();
	}
	
	
	public RegexExtNode(RegexIntNode parent) {
		super(parent);
	}
	
	
	@Override
	protected final void invariant(List<RegexNode> newChildren) {
		if(newChildren.size() != 0) {
			throw new IllegalArgumentException("RegexExtNodes can't have any children");
		}
	}
}
