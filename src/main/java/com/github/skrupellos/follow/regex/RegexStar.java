package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;
import com.github.skrupellos.follow.tree.TreeNode;

public class RegexStar extends RegexIntNode {
	
	public RegexStar() {
		this(null);
	}
	
	public RegexStar(TreeIntNode parent) {
		super(parent);
		setChildren(new TreeNode[1]);
	}
	
	@Override
	public String toString() {
		return "*";
	}
	
	@Override
	public TreeNode setChildren(TreeNode[] children) {
		if(children.length == 1) {
			this.children = children.clone();
			return this;
		}
		throw new IllegalArgumentException("\t[EE] Children array of TreeNode have to have length 2");
	}
	
	@Override
	public TreeNode addChild(TreeNode child) {
		if(child.getParent() != this) {
			child.setParent(this);
		} else {
			if(children[0] == null) {
				children[0] = child;
			} else {
				throw new IllegalStateException("\t[EE] Can't add child to already full node.");
			}
		}
		return this;
	}
	
	@Override
	public TreeNode removeChild(TreeNode child) {
		if(children[0] == child) {
			children[0] = null;
		} else {
			throw new IllegalStateException("\t[EE] Can't remove an object that is not a child of this node.");
		}
		return this;
	}
}
