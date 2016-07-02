package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphNode;


public class NfaNode<T> extends GraphNode<NfaNode<T>, NfaArrow<T>> {
	public boolean accepting = false;
	
	
	public NfaNode() {
		this(false);
	}
	
	
	public NfaNode(boolean accepting) {
		super();
		this.accepting = accepting;
	}
	
	
	public NfaNode(Iterable<NfaArrow<T>> tails, Iterable<NfaArrow<T>> heads) {
		this(tails, heads, false);
	}
	
	
	public NfaNode(Iterable<NfaArrow<T>> tails, Iterable<NfaArrow<T>> heads, boolean accepting) {
		super(tails, heads);
		this.accepting = accepting;
	}
	
	
	protected NfaNode<T> uncheckedSelf() {
		return this;
	}
	
	
	public NfaNode<T> accept(NfaVisitor<T> visitor) {
		visitor.visitNode(this);
		return this;
	}
}
