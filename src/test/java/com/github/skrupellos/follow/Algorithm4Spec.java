package com.github.skrupellos.follow;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.github.skrupellos.follow.graph.GraphArrow;
import com.github.skrupellos.follow.graph.GraphArrowSet;
import com.github.skrupellos.follow.nfa.Nfa;
import com.github.skrupellos.follow.nfa.NfaEpsilonArrow;
import com.github.skrupellos.follow.nfa.NfaSymbolArrow;
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
		Nfa<String> nfa =  Algorithm4.apply(getTeta());
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
		Nfa<String> nfa = Algorithm4.apply(new RegexSymbol<String>("a"));
		GraphArrowSet<?, ?> set = nfa.start.tails();
		assertEquals(1, set.arrows().size());
		GraphArrow[] arrow = set.arrows().toArray(new GraphArrow[1]);
		assertEquals(NfaSymbolArrow.class, arrow[0].getClass());
		NfaSymbolArrow symbolArrow = (NfaSymbolArrow) arrow[0];
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
		Nfa<String> nfa = Algorithm4.apply(new RegexCatenation<String>(new RegexSymbol<String>("a"), new RegexSymbol<String>("b")));
		GraphArrowSet<?, ?> set = nfa.start.tails();
		assertEquals(1, set.arrows().size());
		GraphArrow[] arrow = set.arrows().toArray(new GraphArrow[1]);
		assertEquals(NfaSymbolArrow.class, arrow[0].getClass());
		NfaSymbolArrow symbolArrow = (NfaSymbolArrow) arrow[0];
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
		Nfa<String> nfa = Algorithm4.apply(new RegexUnion<String>(new RegexSymbol<String>("a"), new RegexSymbol<String>("b")));
		GraphArrowSet<?, ?> set = nfa.start.tails();
		assertEquals(2, set.arrows().size());
		GraphArrow[] arrow = set.arrows().toArray(new GraphArrow[2]);
		assertEquals(NfaSymbolArrow.class, arrow[0].getClass());
		assertEquals(NfaSymbolArrow.class, arrow[1].getClass());
		
		NfaSymbolArrow symbolArrow1 = (NfaSymbolArrow) arrow[0];
		NfaSymbolArrow symbolArrow2 = (NfaSymbolArrow) arrow[1];
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
		Nfa<String> nfa = Algorithm4.apply(new RegexStar<String>(new RegexSymbol<String>("a")));
		GraphArrowSet<?, ?> outSet = nfa.start.tails();
		assertEquals(2, outSet.arrows().size());
		GraphArrow[] arrow = outSet.arrows().toArray(new GraphArrow[2]);
		assertTrue((arrow[0].getClass().equals(NfaSymbolArrow.class) && arrow[1].getClass().equals(NfaEpsilonArrow.class))
				|| (arrow[0].getClass().equals(NfaEpsilonArrow.class) && arrow[1].getClass().equals(NfaSymbolArrow.class)));
		
		GraphArrowSet<?, ?> inSet = nfa.start.heads();
		assertEquals(1, inSet.arrows().size());
		arrow = inSet.arrows().toArray(new GraphArrow[1]);
		assertEquals(NfaSymbolArrow.class, arrow[0].getClass());
		NfaSymbolArrow symbolArrow = (NfaSymbolArrow) arrow[0];
		assertEquals("a", symbolArrow.symbol());
	}
}
