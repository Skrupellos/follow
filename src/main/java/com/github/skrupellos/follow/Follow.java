package com.github.skrupellos.follow;

import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.nfa.Nfa;




public final class Follow<T>  {
	private Follow() {}
	
	
	public static <T> Nfa<T> apply(RegexNode<T> root) {
		root = Algorithm1a.<T>apply(root);
		root = Algorithm1b.<T>apply(root).tree;
		root = Algorithm1c.<T>apply(root);
		return Algorithm4.<T>apply(root);
	}
}
