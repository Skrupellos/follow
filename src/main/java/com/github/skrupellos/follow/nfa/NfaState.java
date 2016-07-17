package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphNode;


public class NfaState<T> extends GraphNode<NfaState<T>, NfaTransition<T>> {
	public boolean isFinal = false;
	
	
	public NfaState() {
		this(false);
	}
	
	
	public NfaState(boolean isFinal) {
		super();
		this.isFinal = isFinal;
	}
	
	
	public NfaState(Iterable<NfaTransition<T>> tails, Iterable<NfaTransition<T>> heads) {
		this(tails, heads, false);
	}
	
	
	public NfaState(Iterable<NfaTransition<T>> tails, Iterable<NfaTransition<T>> heads, boolean isFinal) {
		super(tails, heads);
		this.isFinal = isFinal;
	}
	
	
	protected NfaState<T> uncheckedSelf() {
		return this;
	}
	
	
	public NfaState<T> accept(NfaVisitor<T> visitor) {
		visitor.visitState(this);
		return this;
	}
}
