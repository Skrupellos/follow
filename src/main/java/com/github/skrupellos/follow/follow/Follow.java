package com.github.skrupellos.follow.follow;

import com.github.skrupellos.follow.nfa.NfaState;
import com.github.skrupellos.follow.regex.RegexNode;




public final class Follow<T>  {
	private Follow() {}
	
	
	public static <T> NfaState<T> apply(RegexNode<T> root) {
		NfaState<T> start;
		
		root  = Algorithm1a.<T>apply(root);
		root  = Algorithm1b.<T>apply(root).tree;
		root  = Algorithm1c.<T>apply(root);
		start = Algorithm4 .<T>apply(root).start;
		        Algorithm20.<T>apply(start); // _Modifies_ start
		
		return start;
	}
}
