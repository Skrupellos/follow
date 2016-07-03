package com.github.skrupellos.follow.regex;


public class RegexEpsilon<T> extends RegexExtNode<T> {
	// Nothing
	public RegexEpsilon() {
		super();
	}
	
	
	// Only parent
	public RegexEpsilon(RegexIntNode<T> parent) {
		super(parent);
	}
	
	
	@Override
	public String toString() {
		return "ε";
	}
	
	
	public RegexNode<T> deepCopy() {
		return new RegexEpsilon<T>();
	}
	
	
	public RegexNode<T> accept(RegexVisitor<T> visitor) {
		visitor.pre(this);
		visitor.post(this);
		return this;
	}
}
