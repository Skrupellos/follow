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
	
	public TreeNode addChild(TreeNode child) {
		if(child.getParent() != this) {
			child.setParent(this);
		} else {
			if(children[0] == null) {
				children[0] = child;
			} else if(children[1] == null){
				children[1] = child;
			} else {
				throw new IllegalStateException("\t[EE] Can't add child to already full node.");
			}
		}
		return this;
	}
	
	public TreeNode removeChild(TreeNode child) {
		if(children[0] == child) {
			children[0] = null;
		} else if(children[1] == child) {
			children[1] = null;
		} else {
			throw new IllegalStateException("\t[EE] Can't remove an object that is not a child of this node.");
		}
		return this;
	}
}
