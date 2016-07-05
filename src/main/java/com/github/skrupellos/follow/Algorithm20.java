package com.github.skrupellos.follow;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.github.skrupellos.follow.nfa.NfaArrow;
import com.github.skrupellos.follow.nfa.NfaEpsilonArrow;
import com.github.skrupellos.follow.nfa.NfaNode;
import com.github.skrupellos.follow.nfa.NfaSymbolArrow;


public class Algorithm20<T> {
	private List<NfaNode<T>>  topSortSorted;
	private Queue<NfaNode<T>> topSortUnmarked;
	private Set<NfaNode<T>>   topSortTmpMark;
	
	
	public static <T> void apply(NfaNode<T> start) {
		new Algorithm20<T>(start);
	}
	
	
	public Algorithm20(NfaNode<T> start) {
		//> F_f ← {q_f}
		// Nothing to do, since we modify the input data structure, where the
		// final state is already marked.
		
		//> sort topologically Q^ε_f w.r.t. the order p ⩽ q iff p -ε→ q ∈ δ^ε_f;
		//> denote the ordered Q^ε_f = (q_1, q_2, ..., q_r)
		topSort(start);
		
		//> for i from r down to 1 do
		// Order was already changed in the construction of topSortSorted by
		// topSortNode().
		for(NfaNode<T> node1 : topSortSorted) {
			//> for each transition q_i -ε→ p do
			for(NfaArrow<T> arrow_1_2_tmp : node1.tails()) {
				if(arrow_1_2_tmp instanceof NfaEpsilonArrow) {
					NfaEpsilonArrow<T> arrow_1_2 = (NfaEpsilonArrow<T>)arrow_1_2_tmp;
					NfaNode<T> node2 = arrow_1_2.head();
					
					//> for each transition p -a→ q do
					for(NfaArrow<T> arrow_2_3_tmp : node2.tails()) {
						if(arrow_2_3_tmp instanceof NfaSymbolArrow) {
							NfaSymbolArrow<T> arrow_2_3 = (NfaSymbolArrow<T>)arrow_2_3_tmp;
							NfaNode<T> node3 = arrow_2_3.head();
							
							//> if q_i -a→ q ∉ \delta then add q_i -a→ q to \delta
							boolean found = false;
							for(NfaArrow<T> arrow_1_4_tmp : node1.tails()) {
								if(arrow_1_4_tmp instanceof NfaSymbolArrow) {
									NfaSymbolArrow<T> arrow_1_4 = (NfaSymbolArrow<T>)arrow_1_4_tmp;
									NfaNode<T> node4 = arrow_1_4.head();
									
									if(node3 == node4 && arrow_1_4.symbol().equals(arrow_2_3.symbol())) {
										found = true;
										break;
									}
								}
							}

							if(!found) {
								new NfaSymbolArrow<T>(arrow_2_3.symbol(), node1, node3);
							}
						}
					}
					
					//> if p ∈ F then add q_i to F
					if(node2.accepting) {
						node1.accepting = true;
					}
					
					//> remove the transition q_i -ε→ p
					arrow_1_2.delete();
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
		Set<NfaNode<T>> reachable = start.reachable();
		for(NfaNode<T> node : reachable) {
			if(node == start) {
				continue;
			}
			
			for(NfaArrow<T> arrow : node.heads()) {
				if(reachable.contains(arrow.tail()) == false) {
					arrow.delete();
				}
			}
		}
		
		//> Q_f ← Q^ε_f; δ_f ← δ^ε_f
		//> return A_f(α) = (Q_f, A, δ_f, 0_f, F_f)
		// Since we descrutively modified the input datastructre, there is no
		// need to copy anything
	}
	
	
	private void topSort(NfaNode<T> start) {
		// https://en.wikipedia.org/wiki/Topological_sorting#Depth-first_search
		//> L ← Empty list that will contain the sorted nodes
		topSortSorted   = new LinkedList<NfaNode<T>>();
		topSortUnmarked = new LinkedList<NfaNode<T>>(start.reachable());
		topSortTmpMark  = new HashSet<NfaNode<T>>();
		
		//> while there are unmarked nodes do
		//>     select an unmarked node n
		NfaNode<T> node;
		while((node = topSortUnmarked.peek()) != null) {
			//> visit(n)
			topSortNode(node);
		}
	}
	
	
	//> function visit(node n)
	private void topSortNode(NfaNode<T> node) {
		//> if n has a temporary mark then stop (not a DAG)
		if(topSortTmpMark.contains(node)) {
			// so what??? We don't expect an acyclic graph!
			throw new RuntimeException("Not a DAG");
		}
		
		//> if n is not marked (i.e. has not been visited yet) then
		if(topSortUnmarked.contains(node)) {
			//> mark n temporarily
			topSortUnmarked.remove(node);
			topSortTmpMark.add(node);
			
			//> for each node m with an edge from n to m do
			for(NfaArrow<T> arrow : node.tails()) {
				//> visit(m)
				// Since, in our case, the sort is solely based on epsilon
				// transitions we filter other transitions out.
				if(arrow instanceof NfaEpsilonArrow) {
					topSortNode(arrow.head());
				}
			}
			
			//> mark n permanently
			//> unmark n temporarily
			// Not temporarily marked & not unmarked => permanently marked
			topSortTmpMark.remove(node);
			
			//> add n to head of L
			// Since the paper iterates over the list from the end, we simply
			// change the order here and iterate later from the beginning.
			topSortSorted.add(node);
		}
	}
}
