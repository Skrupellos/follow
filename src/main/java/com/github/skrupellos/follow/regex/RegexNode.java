package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeNode;
import java.util.List;


public abstract class RegexNode<T> extends TreeNode<RegexNode<T>> {
	// Nothing
	public RegexNode() {
		super();
	}
	
	
	// Only parent
	public RegexNode(RegexIntNode<T> parent) {
		super(parent);
	}
	
	
	// Only children
	public RegexNode(List<RegexNode<T>> children) {
		super(children);
	}
	
	
	// Parent & children
	public RegexNode(RegexIntNode<T> parent, List<RegexNode<T>> children) {
		super(parent, children);
	}
	
	
	public abstract RegexNode<T> deepCopy();
	
	
	public abstract RegexNode<T> accept(RegexVisitor<T> visitor);
	
	
	protected RegexNode<T> uncheckedSelf() {
		return this;
	}
}
