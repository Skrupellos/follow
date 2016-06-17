package com.github.skrupellos.follow.tree;

public abstract class TreeExtNode extends TreeNode {
	
	@Override
	public TreeNode[] getChildren() {
		return new TreeNode[0];
	}
	
	@Override
	public TreeNode setChildren(TreeNode[] children) {
		return this;
	}
}
