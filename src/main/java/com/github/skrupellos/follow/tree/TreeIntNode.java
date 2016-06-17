package com.github.skrupellos.follow.tree;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public abstract class TreeIntNode extends TreeNode {
	private List<TreeNode> children = new LinkedList<TreeNode>();
	
	public TreeNode addChild(TreeNode child) throws Exception {
		if(children.contains(child)) {
			throw new Exception("Was already child");
		}
		
		children.add(child);
		child._setParent(this);
		
		return this;
	}
	
	public TreeNode removeChild(TreeNode child) throws Exception {
		if(children.remove(child)) {
			child._setParent(null);
			return this;
		}
		else {
			throw new Exception("Was not a child");
		}
	}
	
	/// @TODO Direct access to package private children attribute?
	/*package*/ void _addChild(TreeNode child) {
		children.add(child);
	}
	
	/// @TODO Direct access to package private children attribute?
	/*package*/ void _removeChild(TreeNode child) {
		children.remove(child);
	}
	
	public List<TreeNode> getChildren() {
		return new ArrayList<TreeNode>(children);
	}
}
