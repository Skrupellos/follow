package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphNode;


public class NfaNode extends GraphNode<NfaNode, NfaArrow> {
	public NfaNode() {
		super();
	}
	
	
	public NfaNode(Iterable<NfaArrow> tails, Iterable<NfaArrow> heads) {
		super(tails, heads);
	}
	
	
	protected NfaNode uncheckedSelf() {
		return this;
	}
}
