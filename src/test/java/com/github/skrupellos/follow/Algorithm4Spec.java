package com.github.skrupellos.follow;

import static org.junit.Assert.*;

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
	
	@SuppressWarnings("unused")
	@Test
	public void constructEpislonNFA() {
		Nfa<String> nfa = Algorithm4.apply(getTeta());
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
