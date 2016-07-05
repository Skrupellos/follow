package com.github.skrupellos.follow.regex;

public class RegexSymbol<T> extends RegexExtNode<T> {
	private T symbol;
	
	
	// Nothing
	public RegexSymbol(T symbol) {
		this(symbol, null);
	}
	
	
	// Only parent
	public RegexSymbol(T symbol, RegexIntNode<T> parent) {
		super(parent);
		this.symbol = symbol;
	}
	
	
	public T symbol() {
		return symbol;
	}
	
	
	public RegexSymbol<T> setSymbol(T symbol) {
		this.symbol = symbol;
		return this;
	}
	
	
	public boolean shallowEquivalent(RegexNode<T> other) {
		return this.getClass() == other.getClass() && other instanceof RegexSymbol && symbol.equals(((RegexSymbol<T>)other).symbol());
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
