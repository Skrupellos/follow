package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeNode;
import java.util.List;
import java.util.LinkedList;

public class RegexStar extends RegexIntNode {
	private static List<TreeNode> cnstHeler(TreeNode sub) {
		List<TreeNode> subs = new LinkedList<TreeNode>();
		subs.add(sub);
		return subs;
	}
	
	public RegexStar(TreeNode sub) {
		this(sub, null);
	}
	
	public RegexStar(TreeNode sub, RegexIntNode parent) {
		super(parent, cnstHeler(sub));
	}
	
	@Override
	public String toString() {
		return "*";
	}
	
	public TreeNode replaceSub(TreeNode sub) {
		return replaceChild(0, sub);
	}
	
	@Override
	protected void invariant(List<TreeNode> newChildren) {
		if(newChildren.size() != 1) {
			throw new IllegalArgumentException("RegexStar must have exactly 1 child");
		}
	}
}
