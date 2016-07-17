/* This file is part of Follow (https://github.com/Skrupellos/follow).
 * Copyright (c) 2016 Skruppy <skruppy@onmars.eu> and kratl.
 *
 * Follow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Follow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Follow. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.skrupellos.follow.follow;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import com.github.skrupellos.follow.nfa.EpsilonNfa;
import com.github.skrupellos.follow.nfa.NfaTransition;
import com.github.skrupellos.follow.nfa.NfaEpsilonTransition;
import com.github.skrupellos.follow.nfa.NfaState;
import com.github.skrupellos.follow.nfa.NfaSymbolTransition;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexVisitor;



public class Algorithm4<T> extends AlgorithmBase<RegexNode<T>, EpsilonNfa<T>> implements RegexVisitor<T> {
	public static <T> EpsilonNfa<T> apply(RegexNode<T> root) {
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
		NfaState<T> start = result().start;
		Set<NfaTransition<T>> transitions;
		NfaTransition<T> transition;
		
		if(
			(transitions = start.tails().arrows()).size() == 1 &&
			(transition  = transitions.iterator().next())      instanceof NfaEpsilonTransition
		) {
			start.takeover(transition.head());
			transition.delete(); // Delete has to be the last action.
		}
	}
	
	
	private EpsilonNfa<T> nfaForFreshRegex(RegexNode<T> key) {
		EpsilonNfa<T> nfa = new EpsilonNfa<T>();
		define(key, nfa);
		return nfa;
	}
	
	
	public void post(RegexCatenation<T> regex) {
		EpsilonNfa<T> nfa = nfaForFreshRegex(regex);
		EpsilonNfa<T> leftNfa = lookup(regex.left());
		EpsilonNfa<T> rightNfa = lookup(regex.right());
		NfaState<T> mid = new NfaState<T>();
		Set<NfaTransition<T>> transitions;
		NfaTransition<T> transition;
		
		//> (a) After catenation: denote the state common to the two automata
		//>     by p; (a1) if there is a single transition outgoing from p, say
		//>     [sic] p -ε→ q, then the transition is removed and p and q
		//>     merged; otherwise (a2) if there is a single transition incoming
		//>     to p, say [sic] q -ε→ p, then the transition is removed and p
		//>     and q merged.
		// Sk: I'm pretty sure this works _only_ for ε transitions.
		// Don't remove/merge incoming _and_ outgoing transitions. This would
		// fail for the simple regex a*·b*. This also produces an NFA which
		// starts and ends with ε.
		if(
			(transitions = rightNfa.start.tails().arrows()).size() == 1 &&
			(transition  = transitions.iterator().next())               instanceof NfaEpsilonTransition
		) {
			mid.takeover(leftNfa.end);
			mid.takeover(transition.head());
			transition.delete(); // Delete has to be the last action.
		}
		else if(
			(transitions = leftNfa.end.heads().arrows()).size() == 1 &&
			(transition  = transitions.iterator().next())            instanceof NfaEpsilonTransition
		) {
			mid.takeover(transition.tail());
			mid.takeover(rightNfa.start);
			transition.delete(); // Delete has to be the last action.
		}
		else {
			mid.takeover(leftNfa.end);
			mid.takeover(rightNfa.start);
		}
		
		nfa.start.takeover(leftNfa.start);
		nfa.end.takeover(rightNfa.end);
	}
	
	
	public void post(RegexStar<T> regex) {
		EpsilonNfa<T> nfa = nfaForFreshRegex(regex);
		EpsilonNfa<T> subNfa = lookup(regex.sub());
		NfaState<T> mid = new NfaState<T>();
		
		// Adding the epsilon transitions first will result in a faster
		// depth-first search for the end node in GraphNode.reachable().
		new NfaEpsilonTransition<T>(nfa.start, mid);
		new NfaEpsilonTransition<T>(mid, nfa.end);
		
		subNfa.start.replaceBy(mid);
		subNfa.end.replaceBy(mid);
		
		//> (b) After iteration, denote the middle state by p. If there is a
		//>     cycle containing p such that all its transitions are labelled
		//>     by ε, then all transitions in the cycle are removed and all
		//>     states in the cycle are merged.
		// There can be multiple ε cycles (pathes).
		
		// Those sets collect the found items. It can happen that an item is
		// added twice, but we don't want duplicates. Hence: Sets.
		Set<NfaState<T>>              statesToMerge   = new HashSet<NfaState<T>>();
		Set<NfaTransition<T>>             transitionsToDelete = new HashSet<NfaTransition<T>>();
		
		// Some sort of stack frame
		Stack<Iterator<NfaTransition<T>>> iterators      = new Stack<Iterator<NfaTransition<T>>>();
		Stack<NfaState<T>>            states          = new Stack<NfaState<T>>();
		Stack<NfaTransition<T>>           transitions         = new Stack<NfaTransition<T>>();
		
		// The start and end state of an sub nfa are always merged.
		statesToMerge.add(subNfa.start);
		statesToMerge.add(subNfa.end);
		
		// Find ε pathes from the start to the end state of the sub nfa by
		// back-tracking. This is done with an emulated recusive aproach. Since
		// at the end, when doing back-tracking, the whole stack needs to be
		// read.
		// Since start and end already have been merged into mid, use mid as a 
		// placeholder for both of them instead.
		iterators.push(mid.tails().iterator());
		while(iterators.isEmpty() == false) {
			Iterator<NfaTransition<T>> iterator = iterators.peek();
			
			if(iterator.hasNext()) {
				NfaTransition<T> transition = iterator.next();
				if((transition instanceof NfaEpsilonTransition) == false)
					continue;
				
				// Found final ε-transition => Extend merge/delete list.
				if(transition.head() == mid) {
					statesToMerge  .addAll(states);
					transitionsToDelete.addAll(transitions);
					transitionsToDelete.add(transition);
				}
				// Found intermediate ε-transition => go one node down.
				else {
					iterators.push(transition.head().tails().iterator());
					states    .push(transition.head());
					transitions   .push(transition);
				}
			} else {
				// No further transitions => go one node up.
				iterators.pop();
				if(iterators.isEmpty() == false) {
					states.pop();
					transitions.pop();
				}
			}
		}
		
		// Deleting arrows first results in less arrows to move, when taking
		// over nodes in the next step.
		for(NfaTransition<T> transition : transitionsToDelete) {
			transition.delete();
		}
		
		for(NfaState<T> state : statesToMerge) {
			mid.takeover(state);
		}
	}
	
	
	public void post(RegexUnion<T> regex) {
		EpsilonNfa<T> nfa = nfaForFreshRegex(regex);
		EpsilonNfa<T> leftNfa = lookup(regex.left());
		EpsilonNfa<T> rightNfa = lookup(regex.right());
		
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
		EpsilonNfa<T> nfa = nfaForFreshRegex(regex);
		new NfaEpsilonTransition<T>(nfa.start, nfa.end);
	}
	
	
	public void post(RegexSymbol<T> regex) {
		EpsilonNfa<T> nfa = nfaForFreshRegex(regex);
		new NfaSymbolTransition<T>(regex.symbol(), nfa.start, nfa.end);
	}
}
