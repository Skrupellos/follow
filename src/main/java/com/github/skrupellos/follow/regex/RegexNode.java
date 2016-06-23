package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeNode;
import java.util.List;


public abstract class RegexNode extends TreeNode<RegexNode> {
	public RegexNode() {
		super();
	}
	
	
	public RegexNode(RegexNode parent) {
		super(parent);
	}
	
	
	public RegexNode(List<RegexNode> children) {
		super(children);
	}
	
	
	public RegexNode(RegexNode parent, List<RegexNode> children) {
		super(parent, children);
	}
	
	
	public abstract void accept(RegexVisitor visitor);
}
