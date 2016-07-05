package com.github.skrupellos.follow;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexIntNode;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class Algorithm1cSpec {
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
	public void teta() {
		RegexNode<String> in = getTeta();
		RegexNode<String> out = getTeta();
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationNoEpsilon() {
		RegexNode<String> in = new RegexCatenation<String>(getTeta(), getTeta());
		RegexNode<String> out = new RegexCatenation<String>(getTeta(), getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void emptySet() {
		RegexNode<String> in = new RegexEmptySet<String>();
		RegexNode<String> out = new RegexEmptySet<String>();
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void epsilon() {
		RegexNode<String> in = new RegexEpsilon<String>();
		RegexNode<String> out = new RegexEpsilon<String>();
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void star1() {
		RegexNode<String> in = new RegexStar<String>(getTeta());
		RegexNode<String> out = new RegexStar<String>(getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void star2() {
		RegexNode<String> in = new RegexStar<String>(new RegexStar<String>(getTeta()));
		RegexNode<String> out = new RegexStar<String>(getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void star3() {
		RegexNode<String> in = new RegexStar<String>(new RegexStar<String>(new RegexStar<String>(getTeta())));
		RegexNode<String> out = new RegexStar<String>(getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void starNoEpsilon() {
		RegexNode<String> in = new RegexStar<String>(getTeta());
		RegexNode<String> out = new RegexStar<String>(getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@SuppressFBWarnings(value="DM_STRING_CTOR")
	@Test
	public void symbol() {
		RegexNode<String> in = new RegexSymbol<String>(new String("follow"));
		RegexNode<String> out = new RegexSymbol<String>(new String("follow"));
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void union() {
		RegexNode<String> in = new RegexUnion<String>(getTeta(), getTeta());
		RegexNode<String> out = new RegexUnion<String>(getTeta(), getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
}
