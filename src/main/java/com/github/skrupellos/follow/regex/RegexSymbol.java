package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeNode;

public class RegexSymbol<T> extends RegexExtNode {
	
	private T symbol;
	
	public TreeNode setSymbol(T symbol) {
		this.symbol = symbol;
		return this;
	}
	
	public T getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return symbol.toString();
	}
}
