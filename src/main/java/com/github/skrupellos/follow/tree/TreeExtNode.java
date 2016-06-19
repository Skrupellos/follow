package com.github.skrupellos.follow.tree;

import java.util.List;
import java.util.Collections;

public abstract class TreeExtNode extends TreeNode {
	
	public TreeExtNode() {
		this(null);
	}
	
	public TreeExtNode(TreeIntNode parent) {
		super(parent);
	}

	@Override
	public List<TreeNode> getChildren() {
		return Collections.<TreeNode>emptyList();
	}
}
