package com.github.skrupellos.follow.regex;


public class RegexEmptySet extends RegexExtNode {
	// Nothing
	public RegexEmptySet() {
		super();
	}
	
	
	// Only parent
	public RegexEmptySet(RegexIntNode parent) {
		super(parent);
	}
	
	
	@Override
	public String toString() {
		return "Ø";
	}
	
	
	public RegexNode deepCopy() {
		return new RegexEmptySet();
	}
	
	
	public RegexNode accept(RegexVisitor visitor) {
		visitor.pre(this);
		visitor.post(this);
		return this;
	}
}
