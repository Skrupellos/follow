package com.github.skrupellos.follow.regex;

import com.github.skrupellos.follow.exceptions.AlterJungeException;


public class RegexSymbol<T> extends RegexExtNode {
	private T symbol;
	
	// Nothing
	public RegexSymbol(T symbol) {
		this(symbol, null);
	}
	
	// Only parent
	public RegexSymbol(T symbol, RegexIntNode parent) {
		super(parent);
		checkInputValidity(symbol);
		this.symbol = symbol;
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

	public T getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return symbol.toString();
	}
	
	public RegexNode accept(RegexVisitor visitor) {
		visitor.pre(this);
		visitor.post(this);
		return this;
	}
}
