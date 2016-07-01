package com.github.skrupellos.follow.nfa;



public class Nfa<T> {
	public final NfaNode<T> start = new NfaNode<T>();
	public final NfaNode<T> end   = new NfaNode<T>();
}
