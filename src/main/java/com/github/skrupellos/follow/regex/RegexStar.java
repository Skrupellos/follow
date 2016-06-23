package com.github.skrupellos.follow.regex;

import java.util.List;
import java.util.LinkedList;

public class RegexStar extends RegexIntNode {
	private static List<RegexNode> cnstHeler(RegexNode sub) {
		List<RegexNode> subs = new LinkedList<RegexNode>();
		subs.add(sub);
		return subs;
	}
	
	
	public RegexStar(RegexNode sub) {
		super(cnstHeler(sub));
	}
	
	
	public RegexStar(RegexNode sub, RegexIntNode parent) {
		super(parent, cnstHeler(sub));
	}
	
	
	@Override
	public String toString() {
		return "*";
	}
	
	
	public RegexNode replaceSub(RegexNode sub) {
		return (RegexNode) replaceChild(0, sub);
	}
	
	
	@Override
	protected void invariant(List<RegexNode> newChildren) {
		if(newChildren.size() != 1) {
			throw new IllegalArgumentException("RegexStar must have exactly 1 child");
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
