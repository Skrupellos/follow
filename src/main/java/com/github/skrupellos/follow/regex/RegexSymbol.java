package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
import com.github.skrupellos.follow.tree.TreeNode;


public class RegexSymbol<T> extends RegexExtNode<T> {
	private T symbol;
	
	
	// Nothing
	public RegexSymbol(T symbol) {
		this(symbol, null);
	}
	
	
	// Only parent
	public RegexSymbol(T symbol, RegexIntNode<T> parent) {
		super(parent);
		checkInputValidity(symbol);
		this.symbol = symbol;
	}
	
	
	public T symbol() {
		return symbol;
	}
	
	
	public RegexSymbol<T> setSymbol(T symbol) {
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
	
	
	public boolean shallowEquivalent(RegexNode<T> other) {
		return this.getClass() == other.getClass() && symbol.equals(((RegexSymbol<T>)other).symbol());
	}
	
	
	@Override
	public String toString() {
		return symbol.toString();
	}
	
	
	public RegexNode<T> deepCopy() {
		return new RegexSymbol<T>(symbol);
	}
	
	
	public RegexNode<T> accept(RegexVisitor<T> visitor) {
		visitor.pre(this);
		visitor.post(this);
		return this;
	}
}
