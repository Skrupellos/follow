package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeNode;
import java.util.List;


public abstract class RegexNode extends TreeNode<RegexNode> {
	// Nothing
	public RegexNode() {
		super();
	}
	
	
	// Only parent
	public RegexNode(RegexIntNode parent) {
		super(parent);
	}
	
	
	// Only children
	public RegexNode(List<RegexNode> children) {
		super(children);
	}
	
	
	// Parent & children
	public RegexNode(RegexIntNode parent, RegexNode... children) {
		super(parent, children);
	}
	
	
	public RegexNode(RegexIntNode parent, List<RegexNode> children) {
		super(parent, children);
	}
	
	
	public abstract RegexNode accept(RegexVisitor visitor);
	
	
	protected RegexNode uncheckedSelf() {
		return this;
	}
}
