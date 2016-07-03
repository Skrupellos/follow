package com.github.skrupellos.follow.tree;


import java.lang.Iterable;
import java.util.List;


public class SimpleTree extends TreeNode<SimpleTree> {
	// Nothing
	public SimpleTree() {
		super();
	}
	
	
	// Only parent
	public SimpleTree(SimpleTree parent) {
		super(parent);
	}
	
	
	// Only children
	public SimpleTree(List<SimpleTree> children) {
		super(children);
	}
	
	
	// Parent & children
	public SimpleTree(SimpleTree parent, List<SimpleTree> children) {
		super(parent, children);
	}
	
	
	protected SimpleTree uncheckedSelf() {
		return this;
	}
}
