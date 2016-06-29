package com.github.skrupellos.follow;

import org.junit.Test;

import com.github.skrupellos.follow.exceptions.AlterJungeException;
import com.github.skrupellos.follow.tree.TreeNode;
import com.github.skrupellos.follow.tree.SimpleTree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import com.github.skrupellos.follow.regex.RegexVisitor;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexExtNode;
import com.github.skrupellos.follow.regex.RegexIntNode;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import static org.junit.Assert.assertEquals;

public class Algorithm1bSpec {
	private RegexIntNode getTeta() {
		return new RegexCatenation(
			new RegexUnion(
				new RegexSymbol<String>("a"),
				new RegexSymbol<String>("b")
			),
			new RegexStar(
				new RegexUnion(
					new RegexUnion(
						new RegexStar(
							new RegexSymbol<String>("a")
						),
						new RegexCatenation(
							new RegexSymbol<String>("b"),
							new RegexStar(
								new RegexSymbol<String>("a")
							)
						)
					),
					new RegexStar(
						new RegexSymbol<String>("b")
					)
				)
			)
		);
	}
	
	
	private RegexIntNode epsilonRegex() {
		return new RegexUnion(
			new RegexSymbol<String>("a"),
			new RegexStar(
				new RegexCatenation(
					new RegexEpsilon(),
					new RegexEpsilon()
				)
			)
		);
	}
	
	
	@Test
	public void teta() {
		RegexNode in = getTeta();
		RegexNode out = getTeta();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationEpsilonLeft() {
		RegexNode in = new RegexCatenation(new RegexEpsilon(), getTeta());
		RegexNode out = getTeta();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationEpsilonRight() {
		RegexNode in = new RegexCatenation(getTeta(), new RegexEpsilon());
		RegexNode out = getTeta();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationNoEpsilon() {
		RegexNode in = new RegexCatenation(getTeta(), getTeta());
		RegexNode out = new RegexCatenation(getTeta(), getTeta());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void emptySet() {
		RegexNode in = new RegexEmptySet();
		RegexNode out = new RegexEmptySet();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void epsilon() {
		RegexNode in = new RegexEpsilon();
		RegexNode out = new RegexEpsilon();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void starEpsilon() {
		RegexNode in = new RegexStar(new RegexEpsilon());
		RegexNode out = new RegexEpsilon();
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void starNoEpsilon() {
		RegexNode in = new RegexStar(getTeta());
		RegexNode out = new RegexStar(getTeta());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@SuppressFBWarnings(value="DM_STRING_CTOR")
	@Test
	public void symbol() {
		RegexNode in = new RegexSymbol<String>(new String("follow"));
		RegexNode out = new RegexSymbol<String>(new String("follow"));
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEpsilonLeftSingle() {
		RegexNode in = new RegexUnion(new RegexEpsilon(), getTeta());
		RegexNode out = new RegexUnion(new RegexEpsilon(), getTeta());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEpsilonRightSingle() {
		RegexNode in = new RegexUnion(getTeta(), new RegexEpsilon());
		RegexNode out = new RegexUnion(getTeta(), new RegexEpsilon());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEpsilonLeftMultiple() {
		RegexNode in = new RegexUnion(new RegexEpsilon(), epsilonRegex());
		RegexNode out = new RegexUnion(new RegexSymbol<String>("a"), new RegexEpsilon());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionEpsilonRightMultiple() {
		RegexNode in = new RegexUnion(epsilonRegex(), new RegexEpsilon());
		RegexNode out = new RegexUnion(new RegexSymbol<String>("a"), new RegexEpsilon());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionNoEpsilon() {
		RegexNode in = new RegexUnion(getTeta(), getTeta());
		RegexNode out = new RegexUnion(getTeta(), getTeta());
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
	
	
	@Test
	public void unionIndirectEpsilon() {
		RegexNode in = new RegexUnion(epsilonRegex(), epsilonRegex());
		RegexNode out = new RegexUnion(
			new RegexUnion(new RegexSymbol<String>("a"), new RegexEpsilon()),
			new RegexUnion(new RegexSymbol<String>("a"), new RegexEpsilon())
		);
		assertTrue("", Algorithm1b.apply(in).tree.isIsomorphTo(out));
	}
}
