package com.github.skrupellos.follow.nfa;

public class EpsilonNfa<T> {
	public final NfaState<T> start = new NfaState<T>(false);
	public final NfaState<T> end   = new NfaState<T>(true);
}
