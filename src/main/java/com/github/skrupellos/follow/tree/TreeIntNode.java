package com.github.skrupellos.follow.tree;

public abstract class TreeIntNode extends TreeNode {

	private TreeNode[] children = new TreeNode[2];
	
	@Override
	public TreeNode[] getChildren() {
		return children;
	}
	
	public TreeNode setChildren(TreeNode[] children) {
		if(children.length == 2) {
			this.children = children;
			return this;
		}
		throw new IllegalArgumentException("\t[EE] Children array of TreeNode have to have length 2");
	}
}
