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
}
