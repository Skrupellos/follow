package com.github.skrupellos.follow.regex;

import java.util.List;
import java.util.LinkedList;

public class RegexCatenation extends RegexIntNode {
	private static List<RegexNode> cnstHeler(RegexNode left, RegexNode right) {
		List<RegexNode> subs = new LinkedList<RegexNode>();
		subs.add(left);
		subs.add(right);
		return subs;
	}
	
	public RegexCatenation(RegexNode left, RegexNode right) {
		this(cnstHeler(left, right), null);
	}
	
	public RegexCatenation(List<RegexNode> subs) {
		this(subs, null);
	}
	
	public RegexCatenation(RegexNode left, RegexNode right, RegexIntNode parent) {
		this(cnstHeler(left, right), parent);
	}
	
	public RegexCatenation(List<RegexNode> subs, RegexIntNode parent) {
		super(parent, subs);
	}
	
	@Override
	public String toString() {
		return "meow";
	}
	
	public RegexNode getLeft() {
		return (RegexNode) getChild(0);
	}
	
	public RegexNode getRight() {
		return (RegexNode) getChild(1);
	}
	
	public RegexNode replaceLeft(RegexNode sub) {
		return (RegexNode) replaceChild(0, sub);
	}
	
	public RegexNode replaceRight(RegexNode sub) {
		return (RegexNode) replaceChild(1, sub);
	}
	
	@Override
	protected void invariant(List<RegexNode> newChildren) {
		if(newChildren.size() != 2) {
			throw new IllegalArgumentException("RegexCatenation must have exactly 2 children");
		}
	}
	
	public void accept(RegexVisitor visitor) {
		visitor.pre(this);
		for(RegexNode child : this) {
			if(child != getChild(0)) {
				visitor.inter(this);
			}
			child.accept(visitor);
		}
		visitor.post(this);
	}
}
