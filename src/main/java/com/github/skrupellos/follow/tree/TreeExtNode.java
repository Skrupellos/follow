package com.github.skrupellos.follow.tree;

public abstract class TreeExtNode extends TreeNode {
	
	public TreeExtNode() {
		this(null);
	}
	
	public TreeExtNode(TreeIntNode parent) {
		super(parent);
	}

	@Override
	public TreeNode[] getChildren() {
		return new TreeNode[0];
	}
}
