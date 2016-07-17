package com.github.skrupellos.follow.follow;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.github.skrupellos.follow.nfa.NfaTransition;
import com.github.skrupellos.follow.nfa.NfaEpsilonTransition;
import com.github.skrupellos.follow.nfa.NfaState;
import com.github.skrupellos.follow.nfa.NfaSymbolTransition;


public class Algorithm20<T> {
	private List<NfaState<T>>  topSortSorted;
	private Queue<NfaState<T>> topSortUnmarked;
	private Set<NfaState<T>>   topSortTmpMark;
	
	
	public static <T> void apply(NfaState<T> start) {
		new Algorithm20<T>(start);
	}
	
	
	public Algorithm20(NfaState<T> start) {
		//> F_f ← {q_f}
		// Nothing to do, since we modify the input data structure, where the
		// final state is already marked.
		
		//> sort topologically Q^ε_f w.r.t. the order p ⩽ q iff p -ε→ q ∈ δ^ε_f;
		//> denote the ordered Q^ε_f = (q_1, q_2, ..., q_r)
		topSort(start);
		
		//> for i from r down to 1 do
		// Order was already changed in the construction of topSortSorted by
		// topSortNode().
		for(NfaState<T> state1 : topSortSorted) {
			//> for each transition q_i -ε→ p do
			for(NfaTransition<T> transition_1_2_tmp : state1.tails()) {
				if(transition_1_2_tmp instanceof NfaEpsilonTransition) {
					NfaEpsilonTransition<T> transition_1_2 = (NfaEpsilonTransition<T>)transition_1_2_tmp;
					NfaState<T> state2 = transition_1_2.head();
					
					//> for each transition p -a→ q do
					for(NfaTransition<T> transition_2_3_tmp : state2.tails()) {
						if(transition_2_3_tmp instanceof NfaSymbolTransition) {
							NfaSymbolTransition<T> transition_2_3 = (NfaSymbolTransition<T>)transition_2_3_tmp;
							NfaState<T> state3 = transition_2_3.head();
							
							//> if q_i -a→ q ∉ \delta then add q_i -a→ q to \delta
							boolean found = false;
							for(NfaTransition<T> transition_1_4_tmp : state1.tails()) {
								if(transition_1_4_tmp instanceof NfaSymbolTransition) {
									NfaSymbolTransition<T> transition_1_4 = (NfaSymbolTransition<T>)transition_1_4_tmp;
									NfaState<T> node4 = transition_1_4.head();
									
									if(state3 == node4 && transition_1_4.symbol().equals(transition_2_3.symbol())) {
										found = true;
										break;
									}
								}
							}

							if(!found) {
								new NfaSymbolTransition<T>(transition_2_3.symbol(), state1, state3);
							}
						}
					}
					
					//> if p ∈ F then add q_i to F
					if(state2.isFinal) {
						state1.isFinal = true;
					}
					
					//> remove the transition q_i -ε→ p
					transition_1_2.delete();
				}
			}
		}
		
		//> for each q ∈ Q - {0_f} such that there is no p -a→ q in \delta do
		//>     eliminate q from Q and all transitions involving q from \delta
		// This is just a cleanup section, which could be savely skipped. The
		// implementations does not stick to the algorithm in the paper, but
		// has the same result. Since the Nfa datastructure don't have a central
		// list of all States (Nodes), we can't iterate over _all_ States, only
		// the reachable ones. Cutting incomeing edges from unreachable States
		// results also in a cleand graph (and the garbage collector does the
		// rest).
		Set<NfaState<T>> reachable = start.reachable();
		for(NfaState<T> state : reachable) {
			if(state == start) {
				continue;
			}
			
			for(NfaTransition<T> transition : state.heads()) {
				if(reachable.contains(transition.tail()) == false) {
					transition.delete();
				}
			}
		}
		
		//> Q_f ← Q^ε_f; δ_f ← δ^ε_f
		//> return A_f(α) = (Q_f, A, δ_f, 0_f, F_f)
		// Since we descrutively modified the input datastructre, there is no
		// need to copy anything
	}
	
	
	private void topSort(NfaState<T> start) {
		// https://en.wikipedia.org/wiki/Topological_sorting#Depth-first_search
		//> L ← Empty list that will contain the sorted nodes
		topSortSorted   = new LinkedList<NfaState<T>>();
		topSortUnmarked = new LinkedList<NfaState<T>>(start.reachable());
		topSortTmpMark  = new HashSet<NfaState<T>>();
		
		//> while there are unmarked nodes do
		//>     select an unmarked node n
		NfaState<T> state;
		while((state = topSortUnmarked.peek()) != null) {
			//> visit(n)
			topSortState(state);
		}
	}
	
	
	//> function visit(node n)
	private void topSortState(NfaState<T> state) {
		//> if n has a temporary mark then stop (not a DAG)
		if(topSortTmpMark.contains(state)) {
			throw new RuntimeException("Not a DAG");
		}
		
		//> if n is not marked (i.e. has not been visited yet) then
		if(topSortUnmarked.contains(state)) {
			//> mark n temporarily
			topSortUnmarked.remove(state);
			topSortTmpMark.add(state);
			
			//> for each node m with an edge from n to m do
			for(NfaTransition<T> transition : state.tails()) {
				//> visit(m)
				// Since, in our case, the sort is solely based on epsilon
				// transitions we filter other transitions out.
				if(transition instanceof NfaEpsilonTransition) {
					topSortState(transition.head());
				}
			}
			
			//> mark n permanently
			//> unmark n temporarily
			// Not temporarily marked & not unmarked => permanently marked
			topSortTmpMark.remove(state);
			
			//> add n to head of L
			// Since the paper iterates over the list from the end, we simply
			// change the order here and iterate later from the beginning.
			topSortSorted.add(state);
		}
	}
}
