package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphNode;


public class NfaNode<T> extends GraphNode<NfaNode<T>, NfaArrow<T>> {
	public boolean isFinal = false;
	
	
	public NfaNode() {
		this(false);
	}
	
	
	public NfaNode(boolean isFinal) {
		super();
		this.isFinal = isFinal;
	}
	
	
	public NfaNode(Iterable<NfaArrow<T>> tails, Iterable<NfaArrow<T>> heads) {
		this(tails, heads, false);
	}
	
	
	public NfaNode(Iterable<NfaArrow<T>> tails, Iterable<NfaArrow<T>> heads, boolean isFinal) {
		super(tails, heads);
		this.isFinal = isFinal;
	}
	
	
	protected NfaNode<T> uncheckedSelf() {
		return this;
	}
	
	
	public NfaNode<T> accept(NfaVisitor<T> visitor) {
		visitor.visitNode(this);
		return this;
	}
}
