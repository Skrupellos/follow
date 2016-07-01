package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphNode;


public class NfaNode<T> extends GraphNode<NfaNode<T>, NfaArrow<T>> {
	public NfaNode() {
		super();
	}
	
	
	public NfaNode(Iterable<NfaArrow<T>> tails, Iterable<NfaArrow<T>> heads) {
		super(tails, heads);
	}
	
	
	protected NfaNode<T> uncheckedSelf() {
		return this;
	}
	
	
	public NfaNode<T> accept(NfaVisitor<T> visitor) {
		visitor.visitNode(this);
		return this;
	}
}
