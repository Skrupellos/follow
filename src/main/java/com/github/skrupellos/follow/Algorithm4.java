package com.github.skrupellos.follow;

import java.util.Map;
import java.util.HashMap;
import com.github.skrupellos.follow.nfa.Nfa;
import com.github.skrupellos.follow.nfa.NfaNode;
import com.github.skrupellos.follow.nfa.NfaEpsilonArrow;
import com.github.skrupellos.follow.nfa.NfaSymbolArrow;
import com.github.skrupellos.follow.regex.RegexVisitor;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;



class Algorithm4 extends AlgorithmBase<RegexNode, Nfa> implements RegexVisitor {
	public static Nfa apply(RegexNode root) {
		return (new Algorithm4(root)).result();
	}
	
	
	public Algorithm4(RegexNode root) {
		super(root);
		root.accept(this);
	}
	
	
	private Nfa nfaForFreshRegex(RegexNode key) {
		Nfa nfa = new Nfa();
		define(key, nfa);
		return nfa;
	}
	
	
	public void post(RegexCatenation regex) {
		Nfa nfa = nfaForFreshRegex(regex);
		Nfa leftNfa = lookup(regex.left());
		Nfa rightNfa = lookup(regex.right());
		NfaNode mid = new NfaNode();
		
		leftNfa.start.replaceBy(nfa.start);
		leftNfa.end.replaceBy(mid);
		
		rightNfa.start.replaceBy(mid);
		rightNfa.end.replaceBy(nfa.end);
	}
	
	
	public void post(RegexStar regex) {
		Nfa nfa = nfaForFreshRegex(regex);
		Nfa subNfa = lookup(regex.sub());
		NfaNode mid = new NfaNode();
		
		// Adding the epsilon transitions first will result in a faster
		// depth-first search for the end node in GraphNode.reachable().
		new NfaEpsilonArrow(nfa.start, mid);
		new NfaEpsilonArrow(mid, nfa.end);
		
		subNfa.start.replaceBy(mid);
		subNfa.end.replaceBy(mid);
	}
	
	
	public void post(RegexUnion regex) {
		Nfa nfa = nfaForFreshRegex(regex);
		Nfa leftNfa = lookup(regex.left());
		Nfa rightNfa = lookup(regex.right());
		
		leftNfa.start.replaceBy(nfa.start);
		leftNfa.end.replaceBy(nfa.end);
		
		rightNfa.start.replaceBy(nfa.start);
		rightNfa.end.replaceBy(nfa.end);
	}
	
	
	public void post(RegexEmptySet regex) {
		/*Nfa nfa =*/ nfaForFreshRegex(regex);
		/* NOP */
	}
	
	
	public void post(RegexEpsilon regex) {
		Nfa nfa = nfaForFreshRegex(regex);
		new NfaEpsilonArrow(nfa.start, nfa.end);
	}
	
	
	public void post(RegexSymbol regex) {
		Nfa nfa = nfaForFreshRegex(regex);
		new NfaSymbolArrow(regex.symbol(), nfa.start, nfa.end);
	}
}
