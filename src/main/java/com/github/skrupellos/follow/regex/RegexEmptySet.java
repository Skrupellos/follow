package com.github.skrupellos.follow.regex;


public class RegexEmptySet extends RegexExtNode {
	public RegexEmptySet() {
		super();
	}
	
	
	public RegexEmptySet(RegexIntNode parent) {
		super(parent);
	}
	
	
	@Override
	public String toString() {
		return "Ø";
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
