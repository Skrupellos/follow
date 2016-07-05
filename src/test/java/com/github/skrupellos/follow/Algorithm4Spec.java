package com.github.skrupellos.follow;

import static org.junit.Assert.assertEquals;

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
	 * ---->o---a--->o---ε--->o---b--->o---ε--->(o)
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
	 *           o---a--->o
	 *          🡭                         \
	 *         /            ε
	 *        ε              \
	 *       /                v
	 * ---->o                  o---ε--->(o)
	 *       \                🡭
	 *        ε              /
	 *         \            ε
	 *          v          /
	 *           o---b--->o
	 */
	@Test
	public void unionToEpsilonNFA() {
		Nfa<String> nfa = Algorithm4.apply(new RegexUnion<String>(new RegexSymbol<String>("a"), new RegexSymbol<String>("b")));
		GraphArrowSet<?, ?> set = nfa.start.tails();
		assertEquals(2, set.arrows().size());
		GraphArrow[] arrow = set.arrows().toArray(new GraphArrow[2]);
		assertEquals(NfaEpsilonArrow.class, arrow[0].getClass());
		assertEquals(NfaEpsilonArrow.class, arrow[1].getClass());
	}
	
	/**
	 * Expected εNFA:<p>
	 * 
	 *  o---a--->o
	 *  🡬                 /
	 *   \     ε   
	 *    ε   /
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
		assertEquals(NfaEpsilonArrow.class, arrow[0].getClass());
		assertEquals(NfaEpsilonArrow.class, arrow[1].getClass());
		
		GraphArrowSet<?, ?> inSet = nfa.start.heads();
		assertEquals(1, inSet.arrows().size());
		arrow = inSet.arrows().toArray(new GraphArrow[1]);
		assertEquals(NfaEpsilonArrow.class, arrow[0].getClass());
	}
}
