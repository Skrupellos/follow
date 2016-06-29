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

public class Algorithm1cSpec {
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
	
	
	@Test
	public void teta() {
		RegexNode in = getTeta();
		RegexNode out = getTeta();
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void catenationNoEpsilon() {
		RegexNode in = new RegexCatenation(getTeta(), getTeta());
		RegexNode out = new RegexCatenation(getTeta(), getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void emptySet() {
		RegexNode in = new RegexEmptySet();
		RegexNode out = new RegexEmptySet();
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void epsilon() {
		RegexNode in = new RegexEpsilon();
		RegexNode out = new RegexEpsilon();
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void star1() {
		RegexNode in = new RegexStar(getTeta());
		RegexNode out = new RegexStar(getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void star2() {
		RegexNode in = new RegexStar(new RegexStar(getTeta()));
		RegexNode out = new RegexStar(getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void star3() {
		RegexNode in = new RegexStar(new RegexStar(new RegexStar(getTeta())));
		RegexNode out = new RegexStar(getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void starNoEpsilon() {
		RegexNode in = new RegexStar(getTeta());
		RegexNode out = new RegexStar(getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@SuppressFBWarnings(value="DM_STRING_CTOR")
	@Test
	public void symbol() {
		RegexNode in = new RegexSymbol<String>(new String("follow"));
		RegexNode out = new RegexSymbol<String>(new String("follow"));
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
	
	
	@Test
	public void union() {
		RegexNode in = new RegexUnion(getTeta(), getTeta());
		RegexNode out = new RegexUnion(getTeta(), getTeta());
		assertTrue("", Algorithm1c.apply(in).isIsomorphTo(out));
	}
}
