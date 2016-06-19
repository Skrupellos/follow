package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;
import com.github.skrupellos.follow.tree.TreeNode;
import java.util.List;

public class RegexIntNode extends TreeIntNode implements RegexNode {
	public RegexIntNode() {
		super();
	}
	
	public RegexIntNode(RegexIntNode parent) {
		super(parent);
	}
	
	public RegexIntNode(List<TreeNode> children) {
		super(children);
	}
	
	public RegexIntNode(RegexIntNode parent, List<TreeNode> children) {
		super(parent, children);
	}
}
