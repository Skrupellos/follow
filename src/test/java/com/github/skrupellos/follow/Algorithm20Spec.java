package com.github.skrupellos.follow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import com.github.skrupellos.follow.nfa.Nfa;
import com.github.skrupellos.follow.nfa.NfaArrow;
import com.github.skrupellos.follow.nfa.NfaNode;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexIntNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;

@FixMethodOrder(MethodSorters.JVM)
public class Algorithm20Spec {

	private static Set<NfaNode<String>> markedNodes;
	private static StringBuilder builder;
	private static Map<Integer, Integer> nodeMap;
	private static int nodeId;
	
	@Before
	public void init() {
		markedNodes = new HashSet<>();
		builder = new StringBuilder();
		nodeMap = new HashMap<>();
		nodeId = 0;
	}
	
	private Nfa<String> getBaseNFA() {
		return Algorithm4.apply(getTeta());
	}
	
	private RegexIntNode<String> getTeta() {
		return new RegexCatenation<String>(
			new RegexUnion<String>(
				new RegexSymbol<String>("a"),
				new RegexSymbol<String>("b")
			),
			new RegexStar<String>(
				new RegexUnion<String>(
					new RegexUnion<String>(
						new RegexStar<String>(
							new RegexSymbol<String>("a")
						),
						new RegexCatenation<String>(
							new RegexSymbol<String>("b"),
							new RegexStar<String>(
								new RegexSymbol<String>("a")
							)
						)
					),
					new RegexStar<String>(
						new RegexSymbol<String>("b")
					)
				)
			)
		);
	}
	
	@Test
	public void printUnsimplifiedEpsilonNFA() {
		Nfa<String> nfa = getBaseNFA();
		lookUpTargetNodes(nfa.start.tails().arrows(), nfa.start);
		System.out.println(builder);
	}
	
	@Test
	public void simplifyEpsilonNFA() {
		Nfa<String> nfa = getBaseNFA();
		Algorithm20.apply(nfa.start);
		lookUpTargetNodes(nfa.start.tails().arrows(), nfa.start);
		System.out.println(builder);
	}

	private void lookUpTargetNodes(Set<NfaArrow<String>> arrows, NfaNode<String> currentNode) {
		Iterator<NfaArrow<String>> arrow;
		NfaNode<String> nextNode;
		if(nodeMap.putIfAbsent(currentNode.hashCode(), nodeId) == null) {
			nodeId++;
		}
		for(arrow = arrows.iterator(); arrow.hasNext(); ) {
			NfaArrow<String> currentArrow = arrow.next();
			nextNode = currentArrow.head();
			if(nodeMap.putIfAbsent(nextNode.hashCode(), nodeId) == null) {
				nodeId++;
			}
			builder.append("\t").append(nodeMap.get(currentNode.hashCode())).append(" --")
					.append(currentArrow).append("--> ").append(nodeMap.get(nextNode.hashCode()))
					.append("\n");
		}
		for(arrow = arrows.iterator(); arrow.hasNext(); ) {
			NfaArrow<String> currentArrow = arrow.next();
			nextNode = currentArrow.head();
			if(markedNodes.contains(nextNode)) {
				continue;
			}
			markedNodes.add(nextNode);
			Set<NfaArrow<String>> nextArrows = nextNode.tails().arrows();
			lookUpTargetNodes(nextArrows, nextNode);
		}
	}
}
