package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphArrow;

public class NfaSymbolArrow<T> extends NfaArrow<T> {
	private T symbol;
	
	
	public NfaSymbolArrow(T symbol, NfaNode<T> tail, NfaNode<T> head) {
		super(tail, head);
		setSymbol(symbol);
	}
	
	
	public NfaSymbolArrow(T symbol, NfaNode<T> node) {
		super(node);
		setSymbol(symbol);
	}
	
	
	public T symbol() {
		return symbol;
	}
	
	
	public NfaSymbolArrow<T> setSymbol(T symbol) {
		this.symbol = symbol;
		return this;
	}
	
	
	@Override
	public String toString() {
		return symbol.toString();
	}
	
	
	public NfaArrow<T> accept(NfaVisitor<T> visitor) {
		visitor.visitArrow(this);
		return this;
	}
	
	
	@Override
	public boolean equalContents(GraphArrow other) {
		return
			super.equalContents(other) &&
			other instanceof NfaSymbolArrow &&
			symbol.equals( ((NfaSymbolArrow)other).symbol );
	}
}
