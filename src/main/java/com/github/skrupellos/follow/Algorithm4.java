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



class Algorithm4<T> extends AlgorithmBase<RegexNode<T>, Nfa<T>> implements RegexVisitor<T> {
	public static <T> Nfa<T> apply(RegexNode<T> root) {
		return (new Algorithm4<T>(root)).result();
	}
	
	
	public Algorithm4(RegexNode<T> root) {
		super(root);
		root.accept(this);
	}
	
	
	private Nfa<T> nfaForFreshRegex(RegexNode<T> key) {
		Nfa<T> nfa = new Nfa<T>();
		define(key, nfa);
		return nfa;
	}
	
	
	public void post(RegexCatenation<T> regex) {
		Nfa<T> nfa = nfaForFreshRegex(regex);
		Nfa<T> leftNfa = lookup(regex.left());
		Nfa<T> rightNfa = lookup(regex.right());
		NfaNode<T> mid = new NfaNode<T>();
		
		leftNfa.start.replaceBy(nfa.start);
		leftNfa.end.replaceBy(mid);
		
		rightNfa.start.replaceBy(mid);
		rightNfa.end.replaceBy(nfa.end);
	}
	
	
	public void post(RegexStar<T> regex) {
		Nfa<T> nfa = nfaForFreshRegex(regex);
		Nfa<T> subNfa = lookup(regex.sub());
		NfaNode<T> mid = new NfaNode<T>();
		
		// Adding the epsilon transitions first will result in a faster
		// depth-first search for the end node in GraphNode.reachable().
		new NfaEpsilonArrow<T>(nfa.start, mid);
		new NfaEpsilonArrow<T>(mid, nfa.end);
		
		subNfa.start.replaceBy(mid);
		subNfa.end.replaceBy(mid);
	}
	
	
	public void post(RegexUnion<T> regex) {
		Nfa<T> nfa = nfaForFreshRegex(regex);
		Nfa<T> leftNfa = lookup(regex.left());
		Nfa<T> rightNfa = lookup(regex.right());
		
		leftNfa.start.replaceBy(nfa.start);
		leftNfa.end.replaceBy(nfa.end);
		
		rightNfa.start.replaceBy(nfa.start);
		rightNfa.end.replaceBy(nfa.end);
	}
	
	
	public void post(RegexEmptySet<T> regex) {
		/*Nfa nfa =*/ nfaForFreshRegex(regex);
		/* NOP */
	}
	
	
	public void post(RegexEpsilon<T> regex) {
		Nfa<T> nfa = nfaForFreshRegex(regex);
		new NfaEpsilonArrow<T>(nfa.start, nfa.end);
	}
	
	
	public void post(RegexSymbol<T> regex) {
		Nfa<T> nfa = nfaForFreshRegex(regex);
		new NfaSymbolArrow<T>(regex.symbol(), nfa.start, nfa.end);
	}
}
