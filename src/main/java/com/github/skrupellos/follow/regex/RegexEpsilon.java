package com.github.skrupellos.follow.regex;


public class RegexEpsilon extends RegexExtNode {
	public RegexEpsilon() {
		super();
	}
	
	
	public RegexEpsilon(RegexIntNode parent) {
		super(parent);
	}
	
	
	@Override
	public String toString() {
		return "ε";
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
