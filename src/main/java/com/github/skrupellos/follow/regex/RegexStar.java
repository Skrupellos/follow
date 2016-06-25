package com.github.skrupellos.follow.regex;

import java.util.List;
import java.util.LinkedList;

public class RegexStar extends RegexIntNode {
	// Only children
	public RegexStar(RegexNode sub) {
		super(null, sub);
	}
	
	public RegexStar(List<RegexNode> subs) {
		super(subs);
	}
	
	// Parent & children
	public RegexStar(RegexNode sub, RegexIntNode parent) {
		super(parent, sub);
	}
	
	public RegexStar(List<RegexNode> subs, RegexIntNode parent) {
		super(parent, subs);
	}
	
	
	@Override
	public String toString() {
		return "*";
	}
	
	
	public RegexNode sub() {
		return getChild(0);
	}
	
	
	public RegexNode replaceSub(RegexNode sub) {
		return replaceChild(0, sub);
	}
	
	
	@Override
	protected void invariant(List<RegexNode> newChildren) {
		if(newChildren.size() != 1) {
			throw new IllegalArgumentException("RegexStar must have exactly 1 child");
		}
	}
	
	
	public RegexNode accept(RegexVisitor visitor) {
		visitor.pre(this);
		getChild(0).accept(visitor);
		visitor.post(this);
		return this;
	}
}
