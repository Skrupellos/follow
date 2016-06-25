package com.github.skrupellos.follow.regex;

import java.util.List;


public abstract class RegexExtNode extends RegexNode {
	// Nothing
	public RegexExtNode() {
		super();
	}
	
	
	// Only parent
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
