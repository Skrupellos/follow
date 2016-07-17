package com.github.skrupellos.follow.nfa;


public interface NfaVisitor<T> {
	default void visitTransition(NfaEpsilonTransition<T> transition)   { visitTransitionAll(transition); }
	default void visitTransition(NfaSymbolTransition<T> transition) { visitTransitionAll(transition); }
	default void visitTransitionAll(NfaTransition<T> transition)       {                       }
	
	default void visitState(NfaState<T> state)             {                       }
}
