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

package com.github.skrupellos.follow;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import com.github.skrupellos.follow.follow.Algorithm20;
import com.github.skrupellos.follow.follow.Algorithm4;
import com.github.skrupellos.follow.nfa.EpsilonNfa;
import com.github.skrupellos.follow.nfa.NfaTransition;
import com.github.skrupellos.follow.nfa.NfaState;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexIntNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;

@FixMethodOrder(MethodSorters.JVM)
public class Algorithm20Spec {
	
	private EpsilonNfa<String> getBaseNFA() {
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
		EpsilonNfa<String> nfa = getBaseNFA();
		Algorithm20.apply(nfa.start);
		Set<String> actual = SpecUtil.evaluateGraph(nfa.start.tails().arrows(), nfa.start);
		Set<String> expected = getExpectedBaseNfaResult();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected, actual);
		
		assertFalse(nfa.start.isFinal);
		
		List<NfaState<String>> nodes = new ArrayList<>();
		nodes.add(nfa.start);
		checkFinal(nfa.start, nodes);
	}

	/**
	 * In the example of the paper every node accept the initial one results to be a finite state.
	 * 
	 * @param nfa
	 * @param nodes
	 */
	private void checkFinal(NfaState<String> nfa, List<NfaState<String>> nodes) {
		for(NfaTransition<String> arrow : nfa.tails().arrows()) {
			NfaState<String> node = arrow.head();
			if(!nodes.contains(node)) {
				nodes.add(node);
				assertTrue(node.isFinal);
				checkFinal(node, nodes);
			}
		}
	}
}
