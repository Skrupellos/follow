package com.github.skrupellos.follow.regex;


public class RegexEpsilon extends RegexExtNode {
	// Nothing
	public RegexEpsilon() {
		super();
	}
	
	
	// Only parent
	public RegexEpsilon(RegexIntNode parent) {
		super(parent);
	}
	
	
	@Override
	public String toString() {
		return "ε";
	}
	
	
	public RegexNode deepCopy() {
		return new RegexEpsilon();
	}
	
	
	public RegexNode accept(RegexVisitor visitor) {
		visitor.pre(this);
		visitor.post(this);
		return this;
	}
}
