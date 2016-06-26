package com.github.skrupellos.follow.nfa;


class NfaEpsilonArrow extends NfaArrow {
	NfaEpsilonArrow(NfaNode tail, NfaNode head) {
		super(tail, head);
	}
	
	
	NfaEpsilonArrow(NfaNode node) {
		super(node);
	}
	
	
	@Override
	public String toString() {
		return "ε";
	}
}
