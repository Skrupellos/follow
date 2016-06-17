package com.github.skrupellos.follow.tree;

public abstract class TreeNode {
	private TreeNode parent;

	public TreeNode() {
		this(null);
	}
	
	public TreeNode(TreeNode parent) {
		setParent(parent);
	}
	
	public TreeNode setParent(TreeNode parent) {
		this.parent = parent;
		return this;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	
	public TreeNode getRoot() {
		return parent == null ? this : parent.getRoot();
	}
	
	public abstract TreeNode[] getChildren();
	
	public abstract TreeNode setChildren(TreeNode[] children);
}
