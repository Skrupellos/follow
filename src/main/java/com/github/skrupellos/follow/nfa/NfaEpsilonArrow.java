package com.github.skrupellos.follow.nfa;


public class NfaEpsilonArrow<T> extends NfaArrow<T> {
	public NfaEpsilonArrow(NfaNode<T> tail, NfaNode<T> head) {
		super(tail, head);
	}
	
	
	public NfaEpsilonArrow(NfaNode<T> node) {
		super(node);
	}
	
	
	@Override
	public String toString() {
		return "ε";
	}
}
