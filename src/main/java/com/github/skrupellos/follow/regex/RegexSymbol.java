package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
import com.github.skrupellos.follow.tree.TreeIntNode;
import com.github.skrupellos.follow.tree.TreeNode;

public class RegexSymbol<T> extends RegexExtNode {
	
	private T symbol;
	
	public RegexSymbol(T symbol) {
		this(null, symbol);
	}
	
	public RegexSymbol(TreeIntNode parent, T symbol) {
		super(parent);
		checkInputValidity(symbol);
		this.symbol = symbol;
	}

	public TreeNode setSymbol(T symbol) {
		checkInputValidity(symbol);
		this.symbol = symbol;
		return this;
	}
	
	private void checkInputValidity(T symbol) {
		if(symbol.getClass().equals(String.class)) {
			String testString = (String) symbol;
			if(testString.contains("meow") || testString.contains("+") || testString.contains("*") || testString.contains("-") || testString.contains(" ") || testString.contains("3")) {
				throw new AlterJungeException();
			}
		}
	}

	public T getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return symbol.toString();
	}
}
