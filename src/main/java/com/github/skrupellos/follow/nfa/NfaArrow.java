package com.github.skrupellos.follow.nfa;

import com.github.skrupellos.follow.graph.GraphArrow;


class NfaArrow extends GraphArrow<NfaNode, NfaArrow> {
	NfaArrow(NfaNode tail, NfaNode head) {
		super(tail, head);
	}
	
	
	NfaArrow(NfaNode node) {
		super(node);
	}
	
	
	protected NfaNode createNode() {
		return new NfaNode();
	}
}
