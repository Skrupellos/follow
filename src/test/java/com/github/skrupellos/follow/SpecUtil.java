package com.github.skrupellos.follow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.github.skrupellos.follow.nfa.NfaArrow;
import com.github.skrupellos.follow.nfa.NfaNode;

public class SpecUtil {

	private final static StringBuilder BUILDER = new StringBuilder();
	
	private SpecUtil() {
		// do not instantiate
	}
	
	public static Set<String> evaluateGraph(Set<NfaArrow<String>> arrows, NfaNode<String> currentNode) {
		Set<NfaNode<String>> markedNodes = new HashSet<>(); 
		Map<Integer, Integer> nodeMap = new HashMap<>();
		Set<String> transitionSet = new HashSet<>();
		Counter nodeId = new Counter();
		lookUpTargetNodes(arrows, currentNode, markedNodes, nodeMap, transitionSet, nodeId);
		
		return transitionSet;
	}
	
	private static void lookUpTargetNodes(Set<NfaArrow<String>> arrows, NfaNode<String> currentNode, Set<NfaNode<String>> markedNodes, Map<Integer, Integer> nodeMap, Set<String> transitionSet, Counter nodeId) {
		Iterator<NfaArrow<String>> arrow;
		NfaNode<String> nextNode;
		if(nodeMap.putIfAbsent(currentNode.hashCode(), nodeId.getValue()) == null) {
			nodeId.increment();
		}
		for(arrow = arrows.iterator(); arrow.hasNext(); ) {
			NfaArrow<String> currentArrow = arrow.next();
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
			NfaArrow<String> currentArrow = arrow.next();
			nextNode = currentArrow.head();
			if(markedNodes.contains(nextNode)) {
				continue;
			}
			markedNodes.add(nextNode);
			Set<NfaArrow<String>> nextArrows = nextNode.tails().arrows();
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
