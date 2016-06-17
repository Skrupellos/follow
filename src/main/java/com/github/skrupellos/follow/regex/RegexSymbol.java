package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.tree.TreeIntNode;
import com.github.skrupellos.follow.tree.TreeNode;

public class RegexSymbol<T> extends RegexExtNode {
	
	private T symbol;
	
	public RegexSymbol(T symbol) {
		this(null, symbol);
	}
	
	public RegexSymbol(TreeIntNode parent, T symbol) {
		super(parent);
		this.symbol = symbol;
	}

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
