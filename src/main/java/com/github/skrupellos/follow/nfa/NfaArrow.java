package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphArrow;


public class NfaArrow extends GraphArrow<NfaNode, NfaArrow> {
	public NfaArrow(NfaNode tail, NfaNode head) {
		super(tail, head);
	}
	
	
	public NfaArrow(NfaNode node) {
		super(node);
	}
	
	
	protected NfaNode createNode() {
		return new NfaNode();
	}
	
	
	protected NfaArrow uncheckedSelf() {
		return this;
	}
}
