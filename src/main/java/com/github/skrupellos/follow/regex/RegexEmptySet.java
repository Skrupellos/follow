package com.github.skrupellos.follow.regex;


public class RegexEmptySet<T> extends RegexExtNode<T> {
	// Nothing
	public RegexEmptySet() {
		super();
	}
	
	
	// Only parent
	public RegexEmptySet(RegexIntNode<T> parent) {
		super(parent);
	}
	
	
	@Override
	public String toString() {
		return "Ø";
	}
	
	
	public RegexNode<T> deepCopy() {
		return new RegexEmptySet<T>();
	}
	
	
	public RegexNode<T> accept(RegexVisitor<T> visitor) {
		visitor.pre(this);
		visitor.post(this);
		return this;
	}
}
