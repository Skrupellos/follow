package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphArrow;


public abstract class NfaArrow<T> extends GraphArrow<NfaNode<T>, NfaArrow<T>> {
	public NfaArrow(NfaNode<T> tail, NfaNode<T> head) {
		super(tail, head);
	}
	
	
	public NfaArrow(NfaNode<T> node) {
		super(node);
	}
	
	
	protected NfaNode<T> createNode() {
		return new NfaNode<T>();
	}
	
	
	protected NfaArrow<T> uncheckedSelf() {
		return this;
	}
	
	
	public abstract NfaArrow<T> accept(NfaVisitor<T> visitor);
}
