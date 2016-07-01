package com.github.skrupellos.follow.regex;

import java.util.List;


public abstract class RegexIntNode<T> extends RegexNode<T> {
	// Only children
	public RegexIntNode(List<RegexNode<T>> children) {
		super(children);
	}
	
	
	// Parent & children
	public RegexIntNode(RegexIntNode<T> parent, List<RegexNode<T>> children) {
		super(parent, children);
	}
}
