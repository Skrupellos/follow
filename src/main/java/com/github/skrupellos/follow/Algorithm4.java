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



class Algorithm4 implements RegexVisitor {
	private final Map<RegexNode, Nfa> map = new HashMap<RegexNode, Nfa>();
	private final RegexNode regex;
	
	
	public static Nfa apply(RegexNode regex) {
		return (new Algorithm4(regex)).nfa();
	}
	
	
	public Algorithm4(RegexNode regex) {
		this.regex = regex;
		regex.accept(this);
	}
	
	
	public Nfa nfa() {
		Nfa nfa = map.get(regex);
		assert(nfa != null);
		return nfa;
	}
	
	
	private Nfa nfaForFreshRegex(RegexNode regex) {
		if(regex == null) {
			throw new IllegalArgumentException("null");
		}
		
		Nfa nfa = new Nfa();
		
		Nfa ret = map.putIfAbsent(regex, nfa);
		if(ret != null) {
			throw new RuntimeException("Not a new regex");
		}
		
		return nfa;
	}
	
	
	private Nfa nfaForExistingRegex(RegexNode regex) {
		if(regex == null) {
			throw new IllegalArgumentException("null");
		}
		
		Nfa nfa = map.get(regex);
		if(nfa == null) {
			throw new RuntimeException("regex does not exist");
		}
		
		return nfa;
	}
	
	
	public void post(RegexCatenation regex) {
		Nfa nfa = nfaForFreshRegex(regex);
		Nfa leftNfa = nfaForExistingRegex(regex.left());
		Nfa rightNfa = nfaForExistingRegex(regex.right());
		NfaNode mid = new NfaNode();
		
		leftNfa.start.replaceBy(nfa.start);
		leftNfa.end.replaceBy(mid);
		
		rightNfa.start.replaceBy(mid);
		rightNfa.end.replaceBy(nfa.end);
	}
	
	
	public void post(RegexStar regex) {
		Nfa nfa = nfaForFreshRegex(regex);
		Nfa subNfa = nfaForExistingRegex(regex.sub());
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
		Nfa leftNfa = nfaForExistingRegex(regex.left());
		Nfa rightNfa = nfaForExistingRegex(regex.right());
		
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
