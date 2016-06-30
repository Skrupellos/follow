package com.github.skrupellos.follow;

import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.nfa.Nfa;




public final class Follow  {
	private Follow(RegexNode root) {}
	
	
	public static Nfa apply(RegexNode root) {
		root = Algorithm1a.apply(root);
		root = Algorithm1b.apply(root).tree;
		root = Algorithm1c.apply(root);
		return Algorithm4.apply(root);
	}
}
