package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;
import com.github.skrupellos.follow.tree.TreeNode;

public class RegexStar extends TreeIntNode {
	private TreeNode sub;
	
	public RegexStar(TreeNode sub) throws IllegalArgumentException {
		setSub(sub);
	}
	
	public TreeNode getSub() {
		return sub;
	}
	
	public RegexStar setSub(TreeNode sub) throws IllegalArgumentException {
		if(sub == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.sub = sub;
		
		return this;
	}
}
