package com.github.skrupellos.follow.nfa;


public interface NfaVisitor<T> {
	default void visitArrow(NfaEpsilonArrow<T> arrow)   { visitArrowAll(arrow); }
	default void visitArrow(NfaSymbolArrow<T> arrow) { visitArrowAll(arrow); }
	default void visitArrowAll(NfaArrow<T> arrow)       {                       }
	
	default void visitNode(NfaNode<T> node)             {                       }
}
