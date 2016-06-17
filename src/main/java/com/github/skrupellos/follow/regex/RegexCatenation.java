package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;
import com.github.skrupellos.follow.tree.TreeNode;

public class RegexCatenation extends TreeIntNode {
	private TreeNode subA;
	private TreeNode subB;
	
	public RegexCatenation(TreeNode subA, TreeNode subB) throws IllegalArgumentException {
		setSubA(subA);
		setSubB(subB);
	}
	
	public TreeNode getSubA() {
		return subA;
	}
	
	public TreeNode getSubB() {
		return subB;
	}
	
	public RegexCatenation setSubA(TreeNode subA) throws IllegalArgumentException {
		if(subA == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.subA = subA;
		
		return this;
	}
	
	public RegexCatenation setSubB(TreeNode subB) throws IllegalArgumentException {
		if(subB == null) {
			throw new IllegalArgumentException("Subtree can't be null");
		}
		
		this.subB = subB;
		
		return this;
	}
}
