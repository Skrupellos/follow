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

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.github.skrupellos.follow.follow.Algorithm4;
import com.github.skrupellos.follow.graph.GraphArrow;
import com.github.skrupellos.follow.graph.GraphArrowSet;
import com.github.skrupellos.follow.nfa.EpsilonNfa;
import com.github.skrupellos.follow.nfa.NfaEpsilonTransition;
import com.github.skrupellos.follow.nfa.NfaSymbolTransition;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexIntNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;

@SuppressWarnings("rawtypes")
public class Algorithm4Spec {

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
	
	private Set<String> getExpectedTetaResult_1() {
		Set<String> expected = new HashSet<>();
		expected.add("0 --a--> 1");
		expected.add("0 --b--> 1");
		expected.add("1 --a--> 1");
		expected.add("1 --b--> 1");
		expected.add("1 --ε--> 3");
		expected.add("1 --b--> 2");
		expected.add("2 --ε--> 1");
		expected.add("2 --a--> 2");
		return expected;
	}
	
	/**
	 * Alternative to {@link Algorithm4Spec#getExpectedTetaResult_1()}, since {@link SpecUtil#evaluateGraph(Set, com.github.skrupellos.follow.nfa.NfaNode)}
	 * does not necessarily evaluate the transitions from second node to either of the other two nodes in deterministic order.
	 * 
	 * @return
	 */
	private Set<String> getExpectedTetaResult_2() {
		Set<String> expected = new HashSet<>();
		expected.add("0 --a--> 1");
		expected.add("0 --b--> 1");
		expected.add("1 --a--> 1");
		expected.add("1 --b--> 1");
		expected.add("1 --ε--> 2");
		expected.add("1 --b--> 3");
		expected.add("3 --ε--> 1");
		expected.add("3 --a--> 3");
		return expected;
	}
	
	/**
	 * Compare the output of {@link Algorithm4#apply(com.github.skrupellos.follow.regex.RegexNode)} to {@link Set}s of transitions
	 * derived from Ilie and Yu, Follow automata, page 6 figure 2.
	 */
	@Test
	public void constructEpislonNFA() {
		EpsilonNfa<String> nfa =  Algorithm4.apply(getTeta());
		Set<String> actual = SpecUtil.evaluateGraph(nfa.start.tails().arrows(), nfa.start);
		Set<String> expected_1 = getExpectedTetaResult_1();
		Set<String> expected_2 = getExpectedTetaResult_2();
		
		// since size of expected_1 and expected_2 is the same this check suffices
		assertEquals(expected_1.size(), actual.size());
		
		// either of the two possible solutions has to match
		assertTrue(expected_1.equals(actual) || expected_2.equals(actual));
	}
	
	/**
	 * Expected εNFA:<p>
	 * 
	 * ---->o---a--->(o)
	 * 
	 */
	@Test
	public void symbolToEpsilonNFA() {
		EpsilonNfa<String> nfa = Algorithm4.apply(new RegexSymbol<String>("a"));
		GraphArrowSet<?, ?> set = nfa.start.tails();
		assertEquals(1, set.arrows().size());
		GraphArrow[] arrow = set.arrows().toArray(new GraphArrow[1]);
		assertEquals(NfaSymbolTransition.class, arrow[0].getClass());
		NfaSymbolTransition symbolArrow = (NfaSymbolTransition) arrow[0];
		assertEquals("a", symbolArrow.symbol());
	}
	
	/**
	 * Expected εNFA:<p>
	 * 
	 * ---->o---a--->o---b--->(o)
	 * 
	 */
	@Test
	public void catenationToEpsilonNFA() {
		EpsilonNfa<String> nfa = Algorithm4.apply(new RegexCatenation<String>(new RegexSymbol<String>("a"), new RegexSymbol<String>("b")));
		GraphArrowSet<?, ?> set = nfa.start.tails();
		assertEquals(1, set.arrows().size());
		GraphArrow[] arrow = set.arrows().toArray(new GraphArrow[1]);
		assertEquals(NfaSymbolTransition.class, arrow[0].getClass());
		NfaSymbolTransition symbolArrow = (NfaSymbolTransition) arrow[0];
		assertEquals("a", symbolArrow.symbol());
	}
	
	/**
	 * Expected εNFA:<p>
	 * 
	 *         -------a------
	 *        /              \
	 *       /                v
	 * ---->o                  (o)
	 *       \                🡭
	 *        \              /
	 *         -------b------
	 */
	@Test
	public void unionToEpsilonNFA() {
		EpsilonNfa<String> nfa = Algorithm4.apply(new RegexUnion<String>(new RegexSymbol<String>("a"), new RegexSymbol<String>("b")));
		GraphArrowSet<?, ?> set = nfa.start.tails();
		assertEquals(2, set.arrows().size());
		GraphArrow[] arrow = set.arrows().toArray(new GraphArrow[2]);
		assertEquals(NfaSymbolTransition.class, arrow[0].getClass());
		assertEquals(NfaSymbolTransition.class, arrow[1].getClass());
		
		NfaSymbolTransition symbolArrow1 = (NfaSymbolTransition) arrow[0];
		NfaSymbolTransition symbolArrow2 = (NfaSymbolTransition) arrow[1];
		assertTrue((symbolArrow1.symbol().equals("a") && symbolArrow2.symbol().equals("b"))
				|| (symbolArrow1.symbol().equals("b") && symbolArrow2.symbol().equals("a")));
	}
	
	/**
	 * Expected εNFA:<p>
	 * 
	 *    --a--
	 *    \   /
	 *     \ v
	 * ---->o--ε-->(o)
	 * 
	 */
	@Test
	public void iterationToEpsilonNFA() {
		EpsilonNfa<String> nfa = Algorithm4.apply(new RegexStar<String>(new RegexSymbol<String>("a")));
		GraphArrowSet<?, ?> outSet = nfa.start.tails();
		assertEquals(2, outSet.arrows().size());
		GraphArrow[] arrow = outSet.arrows().toArray(new GraphArrow[2]);
		assertTrue((arrow[0].getClass().equals(NfaSymbolTransition.class) && arrow[1].getClass().equals(NfaEpsilonTransition.class))
				|| (arrow[0].getClass().equals(NfaEpsilonTransition.class) && arrow[1].getClass().equals(NfaSymbolTransition.class)));
		
		GraphArrowSet<?, ?> inSet = nfa.start.heads();
		assertEquals(1, inSet.arrows().size());
		arrow = inSet.arrows().toArray(new GraphArrow[1]);
		assertEquals(NfaSymbolTransition.class, arrow[0].getClass());
		NfaSymbolTransition symbolArrow = (NfaSymbolTransition) arrow[0];
		assertEquals("a", symbolArrow.symbol());
	}
}
