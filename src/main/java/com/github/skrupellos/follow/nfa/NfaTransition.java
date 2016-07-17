package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphArrow;


public abstract class NfaTransition<T> extends GraphArrow<NfaState<T>, NfaTransition<T>> {
	public NfaTransition(NfaState<T> tail, NfaState<T> head) {
		super(tail, head);
	}
	
	
	public NfaTransition(NfaState<T> state) {
		super(state);
	}
	
	
	protected NfaState<T> createState() {
		return new NfaState<T>();
	}
	
	
	protected NfaTransition<T> uncheckedSelf() {
		return this;
	}
	
	
	public abstract NfaTransition<T> accept(NfaVisitor<T> visitor);
}
