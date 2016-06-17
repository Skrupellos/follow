package com.github.skrupellos.follow.regex.abstraction;

import java.util.List;

public abstract class RegexNode {
	private RegexNode parent;
	
	public abstract List<RegexNode> getChildren();
	
	public RegexNode getParent() {
		return parent;
	}
	
	public RegexNode setParent(RegexIntNode parent) {
		if(parent != null) {
			parent._removeChild(this);
		}
		parent._addChild(this);
		
		this.parent = parent;
		return this;
	}
	
	// @TODO Direct access to package private parent attribute?
	/*package*/ void _setParent(RegexNode parent) {
		this.parent = parent;
	}
	
	public RegexNode getRoot() {
		RegexNode parent = getParent();
		
		if(parent == null) {
			return this;
		}
		else {
			return parent.getRoot();
		}
	}
}
