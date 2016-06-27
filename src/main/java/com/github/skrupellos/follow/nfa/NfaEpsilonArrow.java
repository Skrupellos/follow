package com.github.skrupellos.follow.nfa;


public class NfaEpsilonArrow extends NfaArrow {
	public NfaEpsilonArrow(NfaNode tail, NfaNode head) {
		super(tail, head);
	}
	
	
	public NfaEpsilonArrow(NfaNode node) {
		super(node);
	}
	
	
	@Override
	public String toString() {
		return "ε";
	}
}
