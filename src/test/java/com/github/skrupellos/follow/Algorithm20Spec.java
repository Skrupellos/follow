package com.github.skrupellos.follow;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	private Set<String> getExpectedBaseNfaResult() {
		Set<String> result = new HashSet<>();
		result.add("0 --a--> 1");
		result.add("0 --b--> 1");
		result.add("1 --a--> 1");
		result.add("1 --b--> 1");
		result.add("1 --b--> 2");
		result.add("2 --a--> 2");
		result.add("2 --b--> 2");
		result.add("2 --a--> 1");
		result.add("2 --b--> 1");
		return result;
	}
	
	@Test
	public void simplifyEpsilonNFA() {
		Nfa<String> nfa = getBaseNFA();
		Algorithm20.apply(nfa.start);
		Set<String> actual = SpecUtil.evaluateGraph(nfa.start.tails().arrows(), nfa.start);
		Set<String> expected = getExpectedBaseNfaResult();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected, actual);
		
		assertFalse(nfa.start.isFinal);
		
		List<NfaNode<String>> nodes = new ArrayList<>();
		nodes.add(nfa.start);
		checkFinal(nfa.start, nodes);
	}

	/**
	 * In the example of the paper every node accept the initial one results to be a finite state.
	 * 
	 * @param nfa
	 * @param nodes
	 */
	private void checkFinal(NfaNode<String> nfa, List<NfaNode<String>> nodes) {
		for(NfaArrow<String> arrow : nfa.tails().arrows()) {
			NfaNode<String> node = arrow.head();
			if(!nodes.contains(node)) {
				nodes.add(node);
				assertTrue(node.isFinal);
				checkFinal(node, nodes);
			}
		}
	}
}
