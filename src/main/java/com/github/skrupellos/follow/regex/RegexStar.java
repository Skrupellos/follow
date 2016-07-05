package com.github.skrupellos.follow.regex;

import java.util.Arrays;
import java.util.List;


public class RegexStar<T> extends RegexIntNode<T> {
	// Only children
	public RegexStar(RegexNode<T> sub) {
		super(null, Arrays.asList(sub));
	}
	
	public RegexStar(List<RegexNode<T>> subs) {
		super(subs);
	}
	
	// Parent & children
	public RegexStar(RegexNode<T> sub, RegexIntNode<T> parent) {
		super(parent, Arrays.asList(sub));
	}
	
	public RegexStar(List<RegexNode<T>> subs, RegexIntNode<T> parent) {
		super(parent, subs);
	}
	
	
	@Override
	public String toString() {
		return "*";
	}
	
	
	public RegexNode<T> sub() {
		return getChild(0);
	}
	
	
	public RegexNode<T> replaceSub(RegexNode<T> sub) {
		return replaceChild(0, sub);
	}
	
	
	@Override
	protected void invariant(List<RegexNode<T>> newChildren) {
		if(newChildren.size() != 1) {
			throw new IllegalArgumentException("RegexStar must have exactly 1 child");
		}
	}
	
	
	public RegexNode<T> deepCopy() {
		return new RegexStar<T>(sub().deepCopy());
	}
	
	
	public RegexNode<T> accept(RegexVisitor<T> visitor) {
		visitor.pre(this);
		getChild(0).accept(visitor);
		visitor.post(this);
		return this;
	}
}
