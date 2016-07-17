package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphArrow;

public class NfaSymbolTransition<T> extends NfaTransition<T> {
	private T symbol;
	
	
	public NfaSymbolTransition(T symbol, NfaState<T> tail, NfaState<T> head) {
		super(tail, head);
		setSymbol(symbol);
	}
	
	
	public NfaSymbolTransition(T symbol, NfaState<T> state) {
		super(state);
		setSymbol(symbol);
	}
	
	
	public T symbol() {
		return symbol;
	}
	
	
	public NfaSymbolTransition<T> setSymbol(T symbol) {
		this.symbol = symbol;
		return this;
	}
	
	
	@Override
	public String toString() {
		return symbol.toString();
	}
	
	
	public NfaTransition<T> accept(NfaVisitor<T> visitor) {
		visitor.visitTransition(this);
		return this;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equalContents(GraphArrow other) {
		return
			super.equalContents(other) &&
			other instanceof NfaSymbolTransition &&
			symbol.equals( ((NfaSymbolTransition)other).symbol );
	}
}
