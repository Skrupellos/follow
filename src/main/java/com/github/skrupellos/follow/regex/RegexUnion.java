package com.github.skrupellos.follow.regex;

import java.util.Arrays;
import java.util.List;


public class RegexUnion<T> extends RegexIntNode<T> {
	// Only children
	public RegexUnion(RegexNode<T> left, RegexNode<T> right) {
		super(null, Arrays.asList(left, right));
	}
	
	public RegexUnion(List<RegexNode<T>> subs) {
		super(subs);
	}
	
	// Parent & children
	public RegexUnion(RegexNode<T> left, RegexNode<T> right, RegexIntNode<T> parent) {
		super(parent, Arrays.asList(left, right));
	}
	
	public RegexUnion(List<RegexNode<T>> subs, RegexIntNode<T> parent) {
		super(parent, subs);
	}
	
	@Override
	public String toString() {
		return "+";
	}
	
	public RegexNode<T> left() {
		return getChild(0);
	}
	
	public RegexNode<T> right() {
		return getChild(1);
	}
	
	public RegexNode<T> replaceLeft(RegexNode<T> sub) {
		return replaceChild(0, sub);
	}
	
	public RegexNode<T> replaceRight(RegexNode<T> sub) {
		return replaceChild(1, sub);
	}
	
	@Override
	protected void invariant(List<RegexNode<T>> newChildren) {
		if(newChildren.size() != 2) {
			throw new IllegalArgumentException("RegexUnion must have exactly 2 children");
		}
	}
	
	public RegexNode<T> deepCopy() {
		return new RegexUnion<T>(left().deepCopy(), right().deepCopy());
	}
	
	
	public RegexNode<T> accept(RegexVisitor<T> visitor) {
		visitor.pre(this);
		left().accept(visitor);
		visitor.inter(this);
		right().accept(visitor);
		visitor.post(this);
		return this;
	}
}
