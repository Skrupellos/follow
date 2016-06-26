package com.github.skrupellos.follow.nfa;


class NfaSymbolArrow<T> extends NfaArrow {
	private T symbol;
	
	
	NfaSymbolArrow(T symbol, NfaNode tail, NfaNode head) {
		super(tail, head);
		setSymbol(symbol);
	}
	
	
	NfaSymbolArrow(T symbol, NfaNode node) {
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
}
