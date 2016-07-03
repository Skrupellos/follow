package com.github.skrupellos.follow;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import com.github.skrupellos.follow.nfa.Nfa;
import com.github.skrupellos.follow.nfa.NfaNode;
import com.github.skrupellos.follow.nfa.NfaArrow;
import com.github.skrupellos.follow.nfa.NfaEpsilonArrow;
import com.github.skrupellos.follow.nfa.NfaSymbolArrow;
import com.github.skrupellos.follow.graph.GraphArrowSet;
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
		
		
		//> (c) After the end of all steps in Fig. 1; if there is only one
		//>     transition leaving the initial state and is labelled ε, say
		//>     q_0 -ε→ p, then the transition is removed and q_0 and p merged.
		// The regex ε* would produces two following ε transitions, where,
		// according to (c), only the first one would be removed. Algorithm 1b
		// will prevent those cases.
		NfaNode<T> start = result().start;
		Set<NfaArrow<T>> arrows;
		NfaArrow<T> arrow;
		
		if(
			(arrows = start.tails().arrows()).size() == 1 &&
			(arrow  = arrows.iterator().next())      instanceof NfaEpsilonArrow
		) {
			start.takeover(arrow.head());
			arrow.delete(); // Delete has to be the last action.
		}
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
		Set<NfaArrow<T>> arrows;
		NfaArrow<T> arrow;
		
		//> (a) After catenation: denote the state common to the two automata
		//>     by p; (a1) if there is a single transition outgoing from p, say
		//>     [sic] p -ε→ q, then the transition is removed and p and q
		//>     merged; otherwise (a2) if there is a single transition incoming
		//>     to p, say [sic] q -ε→ p, then the transition is removed and p
		//>     and q merged.
		// Sk: I'm pretty shure this works _only_ for ε transitions.
		// Don't remove/merge incoming _and_ outgoing transitions. This would
		// fail for the simple regex a*·b*. This also procuces an NFA which
		// starts and ends with ε.
		if(
			(arrows = rightNfa.start.tails().arrows()).size() == 1 &&
			(arrow  = arrows.iterator().next())               instanceof NfaEpsilonArrow
		) {
			mid.takeover(leftNfa.end);
			mid.takeover(arrow.head());
			arrow.delete(); // Delete has to be the last action.
		}
		else if(
			(arrows = leftNfa.end.heads().arrows()).size() == 1 &&
			(arrow  = arrows.iterator().next())            instanceof NfaEpsilonArrow
		) {
			mid.takeover(arrow.tail());
			mid.takeover(rightNfa.start);
			arrow.delete(); // Delete has to be the last action.
		}
		else {
			mid.takeover(leftNfa.end);
			mid.takeover(rightNfa.start);
		}
		
		nfa.start.takeover(leftNfa.start);
		nfa.end.takeover(rightNfa.end);
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
		
		//> (b) After iteration, denote the middle state by p. If there is a
		//>     cycle containing p such that all its transitions are labelled
		//>     by ε, then all transitions in the cycle are removed and all
		//>     states in the cycle are merged.
		// There can be multiple ε cycles (pathes).
		
		// Those sets collect the found items. It can happen that an item is
		// added twice, but we don't want duplicates. Hence: Sets.
		Set<NfaNode<T>>              nodesToMerge   = new HashSet<NfaNode<T>>();
		Set<NfaArrow<T>>             arrowsToDelete = new HashSet<NfaArrow<T>>();
		
		// Some sort of stack frame
		Stack<Iterator<NfaArrow<T>>> iterators      = new Stack<Iterator<NfaArrow<T>>>();
		Stack<NfaNode<T>>            nodes          = new Stack<NfaNode<T>>();
		Stack<NfaArrow<T>>           arrows         = new Stack<NfaArrow<T>>();
		
		// The start and end state of an sub nfa are always merged.
		nodesToMerge.add(subNfa.start);
		nodesToMerge.add(subNfa.end);
		
		// Find ε pathes from the start to the end state of the sub nfa by
		// back-tracking. This is done with an emulated recusive aproach. Since
		// at the end, when doing back-tracking, the whole stack needs to be
		// read.
		iterators.push(subNfa.start.tails().iterator());
		while(iterators.isEmpty() == false) {
			Iterator<NfaArrow<T>> iterator = iterators.peek();
			
			if(iterator.hasNext()) {
				NfaArrow<T> arrow = iterator.next();
				if((arrow instanceof NfaEpsilonArrow) == false)
					continue;
				
				// Found final ε-transition => Extend merge/delete list.
				if(arrow.head() == subNfa.end) {
					nodesToMerge  .addAll(nodes);
					arrowsToDelete.addAll(arrows);
					arrowsToDelete.add(arrow);
				}
				// Found intermediate ε-transition => go one node down.
				else {
					iterators.push(arrow.head().tails().iterator());
					nodes    .push(arrow.head());
					arrows   .push(arrow);
				}
			}
			// No further transitions => go one node up.
			else {
				iterators.pop();
				nodes    .pop();
				arrows   .pop();
			}
		}
		
		// Deleting arrows first results in less arrows to move, when taking
		// over nodes in the next step.
		for(NfaArrow<T> arrow : arrowsToDelete) {
			arrow.delete();
		}
		
		for(NfaNode<T> node : nodesToMerge) {
			mid.takeover(node);
		}
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
