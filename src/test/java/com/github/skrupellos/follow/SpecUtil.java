package com.github.skrupellos.follow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.github.skrupellos.follow.nfa.NfaTransition;
import com.github.skrupellos.follow.nfa.NfaState;

public class SpecUtil {

	private final static StringBuilder BUILDER = new StringBuilder();
	
	private SpecUtil() {
		// do not instantiate
	}
	
	public static Set<String> evaluateGraph(Set<NfaTransition<String>> arrows, NfaState<String> currentNode) {
		Set<NfaState<String>> markedNodes = new HashSet<>(); 
		Map<Integer, Integer> nodeMap = new HashMap<>();
		Set<String> transitionSet = new HashSet<>();
		Counter nodeId = new Counter();
		lookUpTargetNodes(arrows, currentNode, markedNodes, nodeMap, transitionSet, nodeId);
		
		return transitionSet;
	}
	
	private static void lookUpTargetNodes(Set<NfaTransition<String>> arrows, NfaState<String> currentNode, Set<NfaState<String>> markedNodes, Map<Integer, Integer> nodeMap, Set<String> transitionSet, Counter nodeId) {
		Iterator<NfaTransition<String>> arrow;
		NfaState<String> nextNode;
		if(nodeMap.putIfAbsent(currentNode.hashCode(), nodeId.getValue()) == null) {
			nodeId.increment();
		}
		for(arrow = arrows.iterator(); arrow.hasNext(); ) {
			NfaTransition<String> currentArrow = arrow.next();
			nextNode = currentArrow.head();
			if(nodeMap.putIfAbsent(nextNode.hashCode(), nodeId.getValue()) == null) {
				nodeId.increment();
			}
			BUILDER.append(nodeMap.get(currentNode.hashCode())).append(" --")
					.append(currentArrow).append("--> ").append(nodeMap.get(nextNode.hashCode()));
			transitionSet.add(BUILDER.toString());
			BUILDER.setLength(0);
		}
		for(arrow = arrows.iterator(); arrow.hasNext(); ) {
			NfaTransition<String> currentArrow = arrow.next();
			nextNode = currentArrow.head();
			if(markedNodes.contains(nextNode)) {
				continue;
			}
			markedNodes.add(nextNode);
			Set<NfaTransition<String>> nextArrows = nextNode.tails().arrows();
			lookUpTargetNodes(nextArrows, nextNode, markedNodes, nodeMap, transitionSet, nodeId);
		}
	}
	
	private static class Counter {
		private int counter;
		
		Counter() {
			counter = 0;
		}
		
		void increment() {
			counter++;
		}
		
		int getValue() {
			return counter;
		}
	}
}
