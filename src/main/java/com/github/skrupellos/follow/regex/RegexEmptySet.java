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
}
