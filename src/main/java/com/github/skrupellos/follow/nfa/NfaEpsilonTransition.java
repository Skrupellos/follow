package com.github.skrupellos.follow.nfa;


public class NfaEpsilonTransition<T> extends NfaTransition<T> {
	public NfaEpsilonTransition(NfaState<T> tail, NfaState<T> head) {
		super(tail, head);
	}
	
	
	public NfaEpsilonTransition(NfaState<T> state) {
		super(state);
	}
	
	
	@Override
	public String toString() {
		return "ε";
	}
	
	
	public NfaTransition<T> accept(NfaVisitor<T> visitor) {
		visitor.visitTransition(this);
		return this;
	}
}
