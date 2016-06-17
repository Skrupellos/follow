package com.github.skrupellos.follow.tree;

import java.util.Collections;
import java.util.List;

public abstract class TreeExtNode extends TreeNode {
	public List<TreeNode> getChildren() {
		return Collections.<TreeNode>emptyList();
	}
}
